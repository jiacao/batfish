package org.batfish.question;

import java.util.Iterator;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.batfish.common.Answerer;
import org.batfish.common.BatfishException;
import org.batfish.common.plugin.IBatfish;
import org.batfish.common.util.BatfishObjectMapper;
import org.batfish.datamodel.answers.AnswerElement;
import org.batfish.datamodel.answers.ConvertConfigurationAnswerElement;
import org.batfish.datamodel.questions.Question;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UnusedStructuresQuestionPlugin extends QuestionPlugin {

   public static class UnusedStructuresAnswerElement implements AnswerElement {

      private SortedMap<String, SortedMap<String, SortedSet<String>>> _unusedStructures;

      public UnusedStructuresAnswerElement() {
         _unusedStructures = new TreeMap<>();
      }

      public SortedMap<String, SortedMap<String, SortedSet<String>>> getUnusedStructures() {
         return _unusedStructures;
      }

      @Override
      public String prettyPrint() throws JsonProcessingException {
         // TODO: change this function to pretty print the answer
         ObjectMapper mapper = new BatfishObjectMapper();
         return mapper.writeValueAsString(this);
      }

      public void setUnusedStructures(
            SortedMap<String, SortedMap<String, SortedSet<String>>> undefinedReferences) {
         _unusedStructures = undefinedReferences;
      }
   }

   public static class UnusedStructuresAnswerer extends Answerer {

      public UnusedStructuresAnswerer(Question question, IBatfish batfish) {
         super(question, batfish);
      }

      @Override
      public AnswerElement answer() {

         UnusedStructuresQuestion question = (UnusedStructuresQuestion) _question;

         Pattern nodeRegex;
         try {
            nodeRegex = Pattern.compile(question.getNodeRegex());
         }
         catch (PatternSyntaxException e) {
            throw new BatfishException(
                  "Supplied regex for nodes is not a valid java regex: \""
                        + question.getNodeRegex() + "\"",
                  e);
         }

         _batfish.checkConfigurations();
         UnusedStructuresAnswerElement answerElement = new UnusedStructuresAnswerElement();
         ConvertConfigurationAnswerElement ccae = _batfish
               .getConvertConfigurationAnswerElement();
         for (Entry<String, SortedMap<String, SortedSet<String>>> e : ccae
               .getUnusedStructures().entrySet()) {
            String hostname = e.getKey();
            if (!nodeRegex.matcher(hostname).matches()) {
               continue;
            }
            SortedMap<String, SortedSet<String>> byType = e.getValue();
            answerElement.getUnusedStructures().put(hostname, byType);
         }
         return answerElement;
      }

   }

   public static class UnusedStructuresQuestion extends Question {

      private static final String NODE_REGEX_VAR = "nodeRegex";

      private String _nodeRegex;

      public UnusedStructuresQuestion() {
         _nodeRegex = ".*";
      }

      @Override
      public boolean getDataPlane() {
         return false;
      }

      @Override
      public String getName() {
         return "unusedstructures";
      }

      @JsonProperty(NODE_REGEX_VAR)
      public String getNodeRegex() {
         return _nodeRegex;
      }

      @Override
      public boolean getTraffic() {
         return false;
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
               case NODE_REGEX_VAR:
                  setNodeRegex(parameters.getString(paramKey));
                  break;
               default:
                  throw new BatfishException("Unknown key in "
                        + getClass().getSimpleName() + ": " + paramKey);
               }
            }
            catch (JSONException e) {
               throw new BatfishException("JSONException in parameters", e);
            }
         }
      }

      public void setNodeRegex(String nodeRegex) {
         _nodeRegex = nodeRegex;
      }
   }

   @Override
   protected Answerer createAnswerer(Question question, IBatfish batfish) {
      return new UnusedStructuresAnswerer(question, batfish);
   }

   @Override
   protected Question createQuestion() {
      return new UnusedStructuresQuestion();
   }

}
