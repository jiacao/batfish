package org.batfish.question;

import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.batfish.common.Answerer;
import org.batfish.common.BatfishException;
import org.batfish.common.plugin.IBatfish;
import org.batfish.common.util.BatfishObjectMapper;
import org.batfish.datamodel.BgpProcess;
import org.batfish.datamodel.Configuration;
import org.batfish.datamodel.PrefixSpace;
import org.batfish.datamodel.answers.AnswerElement;
import org.batfish.datamodel.questions.Question;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UniqueBgpPrefixOriginationQuestionPlugin extends QuestionPlugin {

   public static class UniqueBgpPrefixOriginationAnswerElement
         implements AnswerElement {

      private SortedMap<String, SortedMap<String, PrefixSpace>> _intersections;

      private SortedMap<String, PrefixSpace> _prefixSpaces;

      public UniqueBgpPrefixOriginationAnswerElement() {
         _intersections = new TreeMap<>();
         _prefixSpaces = new TreeMap<>();
      }

      public void addIntersection(String node1, String node2,
            PrefixSpace intersection) {
         SortedMap<String, PrefixSpace> intersections = _intersections
               .get(node1);
         if (intersections == null) {
            intersections = new TreeMap<>();
            _intersections.put(node1, intersections);
         }
         intersections.put(node2, intersection);
      }

      public SortedMap<String, SortedMap<String, PrefixSpace>> getIntersections() {
         return _intersections;
      }

      public SortedMap<String, PrefixSpace> getPrefixSpaces() {
         return _prefixSpaces;
      }

      @Override
      public String prettyPrint() throws JsonProcessingException {
         // TODO: change this function to pretty print the answer
         ObjectMapper mapper = new BatfishObjectMapper();
         return mapper.writeValueAsString(this);
      }

      public void setIntersections(
            SortedMap<String, SortedMap<String, PrefixSpace>> intersections) {
         _intersections = intersections;
      }

      public void setPrefixSpaces(SortedMap<String, PrefixSpace> prefixSpaces) {
         _prefixSpaces = prefixSpaces;
      }

   }

   public static class UniqueBgpPrefixOriginationAnswerer extends Answerer {

      public UniqueBgpPrefixOriginationAnswerer(Question question,
            IBatfish batfish) {
         super(question, batfish);
      }

      @Override
      public AnswerElement answer() {

         UniqueBgpPrefixOriginationQuestion question = (UniqueBgpPrefixOriginationQuestion) _question;
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

         UniqueBgpPrefixOriginationAnswerElement answerElement = new UniqueBgpPrefixOriginationAnswerElement();
         _batfish.checkConfigurations();
         Map<String, Configuration> configurations = _batfish
               .loadConfigurations();
         _batfish.initBgpOriginationSpaceExplicit(configurations);
         for (Entry<String, Configuration> e : configurations.entrySet()) {
            String node1 = e.getKey();
            if (!nodeRegex.matcher(node1).matches()) {
               continue;
            }
            Configuration c1 = e.getValue();
            BgpProcess proc1 = c1.getBgpProcess();
            if (proc1 != null) {
               PrefixSpace space1 = proc1.getOriginationSpace();
               answerElement.getPrefixSpaces().put(node1, space1);
               for (Entry<String, Configuration> e2 : configurations
                     .entrySet()) {
                  String node2 = e2.getKey();
                  if (!nodeRegex.matcher(node2).matches()
                        || node1.equals(node2)) {
                     continue;
                  }
                  Configuration c2 = e2.getValue();
                  BgpProcess proc2 = c2.getBgpProcess();
                  if (proc2 != null) {
                     PrefixSpace space2 = proc2.getOriginationSpace();
                     if (space1.overlaps(space2)) {
                        PrefixSpace intersection = space1.intersection(space2);
                        answerElement.addIntersection(node1, node2,
                              intersection);
                     }
                  }
               }
            }
         }
         return answerElement;
      }
   }

   public static class UniqueBgpPrefixOriginationQuestion extends Question {

      private static final String NODE_REGEX_VAR = "nodeRegex";

      private String _nodeRegex;

      public UniqueBgpPrefixOriginationQuestion() {
         _nodeRegex = ".*";
      }

      @Override
      public boolean getDataPlane() {
         return false;
      }

      @Override
      public String getName() {
         return "uniquebgpprefixorigination";
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
      return new UniqueBgpPrefixOriginationAnswerer(question, batfish);
   }

   @Override
   protected Question createQuestion() {
      return new UniqueBgpPrefixOriginationQuestion();
   }

}
