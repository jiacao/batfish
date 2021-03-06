package org.batfish.question;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.batfish.common.Answerer;
import org.batfish.common.BatfishException;
import org.batfish.common.Pair;
import org.batfish.common.plugin.IBatfish;
import org.batfish.common.util.BatfishObjectMapper;
import org.batfish.datamodel.Configuration;
import org.batfish.datamodel.Interface;
import org.batfish.datamodel.Ip;
import org.batfish.datamodel.Prefix;
import org.batfish.datamodel.answers.AnswerElement;
import org.batfish.datamodel.collections.MultiSet;
import org.batfish.datamodel.collections.TreeMultiSet;
import org.batfish.datamodel.questions.Question;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SelfAdjacenciesQuestionPlugin extends QuestionPlugin {

   public static class SelfAdjacenciesAnswerElement implements AnswerElement {

      public static class InterfaceIpPair extends Pair<String, Ip> {

         private static final String INTERFACE_NAME_VAR = "interfaceName";

         private static final String IP_VAR = "ip";
         /**
          *
          */
         private static final long serialVersionUID = 1L;

         @JsonCreator
         public InterfaceIpPair(@JsonProperty(INTERFACE_NAME_VAR) String t1,
               @JsonProperty(IP_VAR) Ip t2) {
            super(t1, t2);
         }

         @JsonProperty(INTERFACE_NAME_VAR)
         public String getInterfaceName() {
            return _first;
         }

         @JsonProperty(IP_VAR)
         public Ip getIp() {
            return _second;
         }

      }

      private SortedMap<String, SortedMap<Prefix, SortedSet<InterfaceIpPair>>> _selfAdjacencies;

      public SelfAdjacenciesAnswerElement() {
         _selfAdjacencies = new TreeMap<>();
      }

      public void add(String hostname, Prefix prefix, String interfaceName,
            Ip address) {
         SortedMap<Prefix, SortedSet<InterfaceIpPair>> prefixMap = _selfAdjacencies
               .get(hostname);
         if (prefixMap == null) {
            prefixMap = new TreeMap<>();
            _selfAdjacencies.put(hostname, prefixMap);
         }
         SortedSet<InterfaceIpPair> interfaces = prefixMap.get(prefix);
         if (interfaces == null) {
            interfaces = new TreeSet<>();
            prefixMap.put(prefix, interfaces);
         }
         interfaces.add(new InterfaceIpPair(interfaceName, address));
      }

      public SortedMap<String, SortedMap<Prefix, SortedSet<InterfaceIpPair>>> getSelfAdjacencies() {
         return _selfAdjacencies;
      }

      @Override
      public String prettyPrint() throws JsonProcessingException {
         // TODO: change this function to pretty print the answer
         ObjectMapper mapper = new BatfishObjectMapper();
         return mapper.writeValueAsString(this);
      }

      public void setSelfAdjacencies(
            SortedMap<String, SortedMap<Prefix, SortedSet<InterfaceIpPair>>> selfAdjacencies) {
         _selfAdjacencies = selfAdjacencies;
      }

   }

   public static class SelfAdjacenciesAnswerer extends Answerer {

      public SelfAdjacenciesAnswerer(Question question, IBatfish batfish) {
         super(question, batfish);
      }

      @Override
      public AnswerElement answer() {

         SelfAdjacenciesQuestion question = (SelfAdjacenciesQuestion) _question;

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

         SelfAdjacenciesAnswerElement answerElement = new SelfAdjacenciesAnswerElement();
         _batfish.checkConfigurations();
         Map<String, Configuration> configurations = _batfish
               .loadConfigurations();
         for (Entry<String, Configuration> e : configurations.entrySet()) {
            String hostname = e.getKey();
            if (!nodeRegex.matcher(hostname).matches()) {
               continue;
            }
            Configuration c = e.getValue();
            MultiSet<Prefix> nodePrefixes = new TreeMultiSet<>();
            for (Interface iface : c.getInterfaces().values()) {
               Set<Prefix> ifaceBasePrefixes = new HashSet<>();
               if (iface.getActive()) {
                  for (Prefix prefix : iface.getAllPrefixes()) {
                     Prefix basePrefix = prefix.getNetworkPrefix();
                     if (!ifaceBasePrefixes.contains(basePrefix)) {
                        ifaceBasePrefixes.add(basePrefix);
                        nodePrefixes.add(basePrefix);
                     }
                  }
               }
            }
            for (Interface iface : c.getInterfaces().values()) {
               for (Prefix prefix : iface.getAllPrefixes()) {
                  Prefix basePrefix = prefix.getNetworkPrefix();
                  if (nodePrefixes.count(basePrefix) > 1) {
                     Ip address = prefix.getAddress();
                     String interfaceName = iface.getName();
                     answerElement.add(hostname, basePrefix, interfaceName,
                           address);
                  }
               }
            }
         }
         return answerElement;
      }
   }

   public static class SelfAdjacenciesQuestion extends Question {

      private static final String NODE_REGEX_VAR = "nodeRegex";

      private String _nodeRegex;

      public SelfAdjacenciesQuestion() {
         _nodeRegex = ".*";
      }

      @Override
      public boolean getDataPlane() {
         return false;
      }

      @Override
      public String getName() {
         return "selfadjacencies";
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
      return new SelfAdjacenciesAnswerer(question, batfish);
   }

   @Override
   protected Question createQuestion() {
      return new SelfAdjacenciesQuestion();
   }

}
