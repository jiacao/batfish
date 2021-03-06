package org.batfish.question;

import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.batfish.common.Answerer;
import org.batfish.common.BatfishException;
import org.batfish.common.plugin.IBatfish;
import org.batfish.common.util.BatfishObjectMapper;
import org.batfish.datamodel.Configuration;
import org.batfish.datamodel.Interface;
import org.batfish.datamodel.IsisInterfaceMode;
import org.batfish.datamodel.answers.AnswerElement;
import org.batfish.datamodel.questions.Question;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class IsisLoopbacksQuestionPlugin extends QuestionPlugin {

   public static class IsisLoopbacksAnswerElement implements AnswerElement {

      private SortedMap<String, SortedSet<String>> _inactive;

      private SortedMap<String, SortedSet<String>> _l1;

      private SortedMap<String, SortedSet<String>> _l1Active;

      private SortedMap<String, SortedSet<String>> _l1Passive;

      private SortedMap<String, SortedSet<String>> _l2;

      private SortedMap<String, SortedSet<String>> _l2Active;

      private SortedMap<String, SortedSet<String>> _l2Passive;

      private SortedMap<String, SortedSet<String>> _running;

      public IsisLoopbacksAnswerElement() {
         _inactive = new TreeMap<>();
         _l1 = new TreeMap<>();
         _l1Active = new TreeMap<>();
         _l1Passive = new TreeMap<>();
         _l2 = new TreeMap<>();
         _l2Active = new TreeMap<>();
         _l2Passive = new TreeMap<>();
         _running = new TreeMap<>();
      }

      public void add(SortedMap<String, SortedSet<String>> map, String hostname,
            String interfaceName) {
         SortedSet<String> interfacesByHostname = map.get(hostname);
         if (interfacesByHostname == null) {
            interfacesByHostname = new TreeSet<>();
            map.put(hostname, interfacesByHostname);
         }
         interfacesByHostname.add(interfaceName);
      }

      public SortedMap<String, SortedSet<String>> getInactive() {
         return _inactive;
      }

      public SortedMap<String, SortedSet<String>> getL1() {
         return _l1;
      }

      public SortedMap<String, SortedSet<String>> getL1Active() {
         return _l1Active;
      }

      public SortedMap<String, SortedSet<String>> getL1Passive() {
         return _l1Passive;
      }

      public SortedMap<String, SortedSet<String>> getL2() {
         return _l2;
      }

      public SortedMap<String, SortedSet<String>> getL2Active() {
         return _l2Active;
      }

      public SortedMap<String, SortedSet<String>> getL2Passive() {
         return _l2Passive;
      }

      public SortedMap<String, SortedSet<String>> getRunning() {
         return _running;
      }

      @Override
      public String prettyPrint() throws JsonProcessingException {
         // TODO: change this function to pretty print the answer
         ObjectMapper mapper = new BatfishObjectMapper();
         return mapper.writeValueAsString(this);
      }

      public void setInactive(SortedMap<String, SortedSet<String>> inactive) {
         _inactive = inactive;
      }

      public void setL1(SortedMap<String, SortedSet<String>> l1) {
         _l1 = l1;
      }

      public void setL1Active(SortedMap<String, SortedSet<String>> l1Active) {
         _l1Active = l1Active;
      }

      public void setL1Passive(SortedMap<String, SortedSet<String>> l1Passive) {
         _l1Passive = l1Passive;
      }

      public void setL2(SortedMap<String, SortedSet<String>> l2) {
         _l2 = l2;
      }

      public void setL2Active(SortedMap<String, SortedSet<String>> l2Active) {
         _l2Active = l2Active;
      }

      public void setL2Passive(SortedMap<String, SortedSet<String>> l2Passive) {
         _l2Passive = l2Passive;
      }

      public void setRunning(SortedMap<String, SortedSet<String>> running) {
         _running = running;
      }
   }

   public static class IsisLoopbacksAnswerer extends Answerer {

      public IsisLoopbacksAnswerer(Question question, IBatfish batfish) {
         super(question, batfish);
      }

      @Override
      public AnswerElement answer() {

         IsisLoopbacksQuestion question = (IsisLoopbacksQuestion) _question;

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

         IsisLoopbacksAnswerElement answerElement = new IsisLoopbacksAnswerElement();
         _batfish.checkConfigurations();
         Map<String, Configuration> configurations = _batfish
               .loadConfigurations();
         for (Entry<String, Configuration> e : configurations.entrySet()) {
            String hostname = e.getKey();
            if (!nodeRegex.matcher(hostname).matches()) {
               continue;
            }
            Configuration c = e.getValue();
            for (Entry<String, Interface> e2 : c.getInterfaces().entrySet()) {
               String interfaceName = e2.getKey();
               Interface iface = e2.getValue();
               if (iface.isLoopback(c.getConfigurationFormat())) {
                  IsisInterfaceMode l1Mode = iface.getIsisL1InterfaceMode();
                  IsisInterfaceMode l2Mode = iface.getIsisL2InterfaceMode();
                  boolean l1 = false;
                  boolean l2 = false;
                  boolean isis = false;
                  if (l1Mode == IsisInterfaceMode.ACTIVE) {
                     l1 = true;
                     isis = true;
                     answerElement.add(answerElement.getL1Active(), hostname,
                           interfaceName);
                  }
                  else if (l1Mode == IsisInterfaceMode.PASSIVE) {
                     l1 = true;
                     isis = true;
                     answerElement.add(answerElement.getL1Passive(), hostname,
                           interfaceName);
                  }
                  if (l2Mode == IsisInterfaceMode.ACTIVE) {
                     l2 = true;
                     isis = true;
                     answerElement.add(answerElement.getL2Active(), hostname,
                           interfaceName);
                  }
                  else if (l2Mode == IsisInterfaceMode.PASSIVE) {
                     l2 = true;
                     isis = true;
                     answerElement.add(answerElement.getL2Passive(), hostname,
                           interfaceName);
                  }
                  if (l1) {
                     answerElement.add(answerElement.getL1(), hostname,
                           interfaceName);
                  }
                  if (l2) {
                     answerElement.add(answerElement.getL2(), hostname,
                           interfaceName);
                  }
                  if (isis) {
                     answerElement.add(answerElement.getRunning(), hostname,
                           interfaceName);
                  }
                  else {
                     answerElement.add(answerElement.getInactive(), hostname,
                           interfaceName);
                  }
               }
            }
         }

         return answerElement;
      }
   }

   public static class IsisLoopbacksQuestion extends Question {

      private static final String NODE_REGEX_VAR = "nodeRegex";

      private String _nodeRegex;

      public IsisLoopbacksQuestion() {
         _nodeRegex = ".*";
      }

      @Override
      public boolean getDataPlane() {
         return false;
      }

      @Override
      public String getName() {
         return "isisloopbacks";
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
      return new IsisLoopbacksAnswerer(question, batfish);
   }

   @Override
   protected Question createQuestion() {
      return new IsisLoopbacksQuestion();
   }

}
