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
import org.batfish.datamodel.Ip;
import org.batfish.datamodel.Prefix;
import org.batfish.datamodel.answers.AnswerElement;
import org.batfish.datamodel.collections.MultiSet;
import org.batfish.datamodel.collections.NodeInterfacePair;
import org.batfish.datamodel.collections.TreeMultiSet;
import org.batfish.datamodel.questions.Question;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UniqueIpAssignmentsQuestionPlugin extends QuestionPlugin {

   public static class UniqueIpAssignmentsAnswerElement
         implements AnswerElement {

      private SortedMap<Ip, SortedSet<NodeInterfacePair>> _allIps;

      private SortedMap<Ip, SortedSet<NodeInterfacePair>> _enabledIps;

      public UniqueIpAssignmentsAnswerElement() {
         _allIps = new TreeMap<>();
         _enabledIps = new TreeMap<>();
      }

      public void add(SortedMap<Ip, SortedSet<NodeInterfacePair>> map, Ip ip,
            String hostname, String interfaceName) {
         SortedSet<NodeInterfacePair> interfaces = map.get(ip);
         if (interfaces == null) {
            interfaces = new TreeSet<>();
            map.put(ip, interfaces);
         }
         interfaces.add(new NodeInterfacePair(hostname, interfaceName));
      }

      public SortedMap<Ip, SortedSet<NodeInterfacePair>> getAllIps() {
         return _allIps;
      }

      public SortedMap<Ip, SortedSet<NodeInterfacePair>> getEnabledIps() {
         return _enabledIps;
      }

      @Override
      public String prettyPrint() throws JsonProcessingException {
         // TODO: change this function to pretty print the answer
         ObjectMapper mapper = new BatfishObjectMapper();
         return mapper.writeValueAsString(this);
      }

      public void setAllIps(
            SortedMap<Ip, SortedSet<NodeInterfacePair>> allIps) {
         _allIps = allIps;
      }

      public void setEnabledIps(
            SortedMap<Ip, SortedSet<NodeInterfacePair>> enabledIps) {
         _enabledIps = enabledIps;
      }

   }

   public static class UniqueIpAssignmentsAnswerer extends Answerer {

      // private final Batfish _batfish;
      // private final UniqueIpAssignmentsQuestion _question;
      //
      // public UniqueIpAssignmentsReplier(Batfish batfish,
      // UniqueIpAssignmentsQuestion question) {
      // _batfish = batfish;
      // _question = question;
      // _batfish.checkConfigurations();
      //
      // if (question.getDifferential()) {
      // _batfish.checkEnvironmentExists(_batfish.getBaseTestrigSettings());
      // _batfish.checkEnvironmentExists(_batfish.getDeltaTestrigSettings());
      // UniqueIpAssignmentsAnswerElement before = initAnswerElement(batfish
      // .getBaseTestrigSettings());
      // UniqueIpAssignmentsAnswerElement after = initAnswerElement(batfish
      // .getDeltaTestrigSettings());
      // ObjectMapper mapper = new BatfishObjectMapper();
      // try {
      // String beforeJsonStr = mapper.writeValueAsString(before);
      // String afterJsonStr = mapper.writeValueAsString(after);
      // JSONObject beforeJson = new JSONObject(beforeJsonStr);
      // JSONObject afterJson = new JSONObject(afterJsonStr);
      // JsonDiff diff = new JsonDiff(beforeJson, afterJson);
      // addAnswerElement(new JsonDiffAnswerElement(diff));
      // }
      // catch (JsonProcessingException | JSONException e) {
      // throw new BatfishException(
      // "Could not convert diff element to json string", e);
      // }
      // }
      // else {
      // UniqueIpAssignmentsAnswerElement answerElement =
      // initAnswerElement(batfish
      // .getTestrigSettings());
      // addAnswerElement(answerElement);
      // }
      //
      // }

      public UniqueIpAssignmentsAnswerer(Question question, IBatfish batfish) {
         super(question, batfish);
      }

      @Override
      public AnswerElement answer() {

         UniqueIpAssignmentsQuestion question = (UniqueIpAssignmentsQuestion) _question;

         _batfish.checkConfigurations();

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
         UniqueIpAssignmentsAnswerElement answerElement = new UniqueIpAssignmentsAnswerElement();
         Map<String, Configuration> configurations = _batfish
               .loadConfigurations();
         MultiSet<Ip> allIps = new TreeMultiSet<>();
         MultiSet<Ip> enabledIps = new TreeMultiSet<>();
         for (Entry<String, Configuration> e : configurations.entrySet()) {
            String hostname = e.getKey();
            if (!nodeRegex.matcher(hostname).matches()) {
               continue;
            }
            Configuration c = e.getValue();
            for (Interface iface : c.getInterfaces().values()) {
               for (Prefix prefix : iface.getAllPrefixes()) {
                  Ip ip = prefix.getAddress();
                  allIps.add(ip);
                  if (iface.getActive()) {
                     enabledIps.add(ip);
                  }

               }
            }
         }
         for (Entry<String, Configuration> e : configurations.entrySet()) {
            String hostname = e.getKey();
            if (!nodeRegex.matcher(hostname).matches()) {
               continue;
            }
            Configuration c = e.getValue();
            for (Entry<String, Interface> e2 : c.getInterfaces().entrySet()) {
               String interfaceName = e2.getKey();
               Interface iface = e2.getValue();
               for (Prefix prefix : iface.getAllPrefixes()) {
                  Ip ip = prefix.getAddress();
                  if (allIps.count(ip) != 1) {
                     answerElement.add(answerElement.getAllIps(), ip, hostname,
                           interfaceName);
                  }
                  if (iface.getActive()) {
                     if (enabledIps.count(ip) != 1) {
                        answerElement.add(answerElement.getEnabledIps(), ip,
                              hostname, interfaceName);
                     }
                  }

               }
            }
         }
         return answerElement;
      }
   }

   public static class UniqueIpAssignmentsQuestion extends Question {

      private static final String NODE_REGEX_VAR = "nodeRegex";

      private static final String VERBOSE_VAR = "verbose";

      private String _nodeRegex;

      private boolean _verbose;

      public UniqueIpAssignmentsQuestion() {
         _nodeRegex = ".*";
      }

      @Override
      public boolean getDataPlane() {
         return false;
      }

      @Override
      public String getName() {
         return "uniqueipassignments";
      }

      @JsonProperty(NODE_REGEX_VAR)
      public String getNodeRegex() {
         return _nodeRegex;
      }

      @Override
      public boolean getTraffic() {
         return false;
      }

      @JsonProperty(VERBOSE_VAR)
      public boolean getVerbose() {
         return _verbose;
      }

      @Override
      public String prettyPrint() {
         String retString = String.format(
               "uniqueipassignments %snodeRegex=\"%s\" | verbose=%s",
               prettyPrintBase(), _nodeRegex, _verbose);
         return retString;
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
               case VERBOSE_VAR:
                  setVerbose(parameters.getBoolean(paramKey));
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

      private void setVerbose(boolean verbose) {
         _verbose = verbose;
      }

   }

   @Override
   protected Answerer createAnswerer(Question question, IBatfish batfish) {
      return new UniqueIpAssignmentsAnswerer(question, batfish);
   }

   @Override
   protected Question createQuestion() {
      return new UniqueIpAssignmentsQuestion();
   }

}
