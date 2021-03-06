package org.batfish.question.assertion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import org.batfish.common.Answerer;
import org.batfish.common.BatfishException;
import org.batfish.common.plugin.IBatfish;
import org.batfish.common.util.BatfishObjectMapper;
import org.batfish.datamodel.answers.AnswerElement;
import org.batfish.datamodel.questions.Question;
import org.batfish.question.QuestionPlugin;
import org.batfish.question.NodesQuestionPlugin.NodesAnswerer;
import org.batfish.question.NodesQuestionPlugin.NodesQuestion;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hamcrest.Matcher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.Configuration.ConfigurationBuilder;
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;

public class AssertQuestionPlugin extends QuestionPlugin {

   public static class AssertAnswerElement implements AnswerElement {

      private Boolean _fail;

      private SortedMap<Integer, Assertion> _failing;

      private SortedMap<Integer, Assertion> _passing;

      public AssertAnswerElement() {
         _failing = new TreeMap<>();
         _passing = new TreeMap<>();
      }

      public Boolean getFail() {
         return _fail;
      }

      public SortedMap<Integer, Assertion> getFailing() {
         return _failing;
      }

      public SortedMap<Integer, Assertion> getPassing() {
         return _passing;
      }

      @Override
      public String prettyPrint() throws JsonProcessingException {
         // TODO: change this function to pretty print the answer
         ObjectMapper mapper = new BatfishObjectMapper();
         return mapper.writeValueAsString(this);
      }

      public void setFail(Boolean fail) {
         _fail = fail;
      }

      public void setFailing(SortedMap<Integer, Assertion> failing) {
         _failing = failing;
      }

      public void setPassing(SortedMap<Integer, Assertion> passing) {
         _passing = passing;
      }

   }

   public static class AssertAnswerer extends Answerer {

      public AssertAnswerer(Question question, IBatfish batfish) {
         super(question, batfish);
      }

      @Override
      public AnswerElement answer() {
         ConfigurationBuilder b = new ConfigurationBuilder();
         b.jsonProvider(new JacksonJsonNodeJsonProvider());
         Configuration c = b.build();

         AssertQuestion question = (AssertQuestion) _question;
         List<Assertion> assertions = question.getAssertions();

         _batfish.checkConfigurations();

         NodesQuestion nodesQuestion = new NodesQuestion();
         nodesQuestion.setSummary(false);
         NodesAnswerer nodesAnswerer = new NodesAnswerer(nodesQuestion,
               _batfish);
         AnswerElement nodesAnswer = nodesAnswerer.answer();
         BatfishObjectMapper mapper = new BatfishObjectMapper();
         String nodesAnswerStr = null;
         try {
            nodesAnswerStr = mapper.writeValueAsString(nodesAnswer);
         }
         catch (IOException e) {
            throw new BatfishException(
                  "Could not get JSON string from nodes answer", e);
         }
         Object jsonObject = JsonPath.parse(nodesAnswerStr, c).json();
         Map<Integer, Assertion> failing = new ConcurrentHashMap<>();
         Map<Integer, Assertion> passing = new ConcurrentHashMap<>();
         List<Integer> indices = new ArrayList<>();
         for (int i = 0; i < assertions.size(); i++) {
            indices.add(i);
         }
         final boolean[] fail = new boolean[1];
         indices.parallelStream().forEach(i -> {
            Assertion assertion = assertions.get(i);
            String path = assertion.getPath();
            Check check = assertion.getCheck();
            if (check == null) {
               throw new BatfishException(
                     "Must supply check for each assertion");
            }
            List<Object> args = assertion.getArgs();
            Object pathResult = null;
            JsonPath jsonPath = JsonPath.compile(path);

            try {
               pathResult = jsonPath.read(jsonObject, c);
            }
            catch (PathNotFoundException e) {
               pathResult = PathResult.EMPTY;
            }
            catch (Exception e) {
               throw new BatfishException("Error reading JSON path: " + path,
                     e);
            }
            Matcher<?> matcher = check.matcher(args);
            if (matcher.matches(pathResult)) {
               passing.put(i, assertion);
            }
            else {
               failing.put(i, assertion);
               synchronized (fail) {
                  fail[0] = true;
               }
            }
         });
         AssertAnswerElement answerElement = new AssertAnswerElement();
         answerElement.setFail(fail[0]);
         answerElement.getFailing().putAll(failing);
         answerElement.getPassing().putAll(passing);
         return answerElement;
      }
   }

   public static class AssertQuestion extends Question {

      private static final String ASSERTIONS_VAR = "assertions";

      private List<Assertion> _assertions;

      public AssertQuestion() {
         _assertions = new ArrayList<>();
      }

      public List<Assertion> getAssertions() {
         return _assertions;
      }

      @Override
      public boolean getDataPlane() {
         return false;
      }

      @Override
      public String getName() {
         return "assert";
      }

      @Override
      public boolean getTraffic() {
         return false;
      }

      @Override
      public String prettyPrint() {
         String retString = String.format("assert %sassertions=\"%s\"",
               prettyPrintBase(), _assertions.toString());
         return retString;
      }

      public void setAssertions(List<Assertion> assertions) {
         _assertions = assertions;
      }

      @Override
      public void setJsonParameters(JSONObject parameters) {
         super.setJsonParameters(parameters);
         Iterator<?> paramKeys = parameters.keys();
         while (paramKeys.hasNext()) {
            String paramKey = (String) paramKeys.next();
            if (isBaseParamKey(paramKey)) {
               continue;
            }
            try {
               switch (paramKey) {
               case ASSERTIONS_VAR:
                  setAssertions(new ObjectMapper().<List<Assertion>> readValue(
                        parameters.getString(paramKey),
                        new TypeReference<List<Assertion>>() {
                        }));
                  break;
               default:
                  throw new BatfishException("Unknown key in "
                        + getClass().getSimpleName() + ": " + paramKey);
               }
            }
            catch (JSONException | IOException e) {
               throw new BatfishException("JSONException in parameters", e);
            }
         }
      }

   }

   @Override
   protected Answerer createAnswerer(Question question, IBatfish batfish) {
      return new AssertAnswerer(question, batfish);
   }

   @Override
   protected Question createQuestion() {
      return new AssertQuestion();
   }

}
