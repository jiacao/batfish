package org.batfish.question;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.batfish.common.Answerer;
import org.batfish.common.BatfishException;
import org.batfish.common.plugin.IBatfish;
import org.batfish.common.util.BatfishObjectMapper;
import org.batfish.datamodel.Configuration;
import org.batfish.datamodel.IpsecVpn;
import org.batfish.datamodel.answers.AnswerElement;
import org.batfish.datamodel.questions.Question;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PairwiseVpnConnectivityQuestionPlugin extends QuestionPlugin {

   public static class PairwiseVpnConnectivityAnswerElement
         implements AnswerElement {

      private SortedMap<String, SortedSet<String>> _connectedNeighbors;

      private SortedSet<String> _ipsecVpnNodes;

      private SortedMap<String, SortedSet<String>> _missingNeighbors;

      public PairwiseVpnConnectivityAnswerElement() {
         _connectedNeighbors = new TreeMap<>();
         _ipsecVpnNodes = new TreeSet<>();
         _missingNeighbors = new TreeMap<>();
      }

      public SortedMap<String, SortedSet<String>> getConnectedNeighbors() {
         return _connectedNeighbors;
      }

      public SortedSet<String> getIpsecVpnNodes() {
         return _ipsecVpnNodes;
      }

      public SortedMap<String, SortedSet<String>> getMissingNeighbors() {
         return _missingNeighbors;
      }

      @Override
      public String prettyPrint() throws JsonProcessingException {
         // TODO: change this function to pretty print the answer
         ObjectMapper mapper = new BatfishObjectMapper();
         return mapper.writeValueAsString(this);
      }

      public void setConnectedNeighbors(
            SortedMap<String, SortedSet<String>> connectedNeighbors) {
         _connectedNeighbors = connectedNeighbors;
      }

      public void setIpsecVpnNodes(SortedSet<String> ipsecVpnNodes) {
         _ipsecVpnNodes = ipsecVpnNodes;
      }

      public void setMissingNeighbors(
            SortedMap<String, SortedSet<String>> missingNeighbors) {
         _missingNeighbors = missingNeighbors;
      }

   }

   public static class PairwiseVpnConnectivityAnswerer extends Answerer {

      public PairwiseVpnConnectivityAnswerer(Question question,
            IBatfish batfish) {
         super(question, batfish);
      }

      @Override
      public AnswerElement answer() {
         PairwiseVpnConnectivityQuestion question = (PairwiseVpnConnectivityQuestion) _question;
         Pattern node1Regex;
         Pattern node2Regex;

         try {
            node1Regex = Pattern.compile(question.getNode1Regex());
            node2Regex = Pattern.compile(question.getNode2Regex());
         }
         catch (PatternSyntaxException e) {
            throw new BatfishException(String.format(
                  "One of the supplied regexes (%s  OR  %s) is not a valid java regex.",
                  question.getNode1Regex(), question.getNode2Regex()), e);
         }

         PairwiseVpnConnectivityAnswerElement answerElement = new PairwiseVpnConnectivityAnswerElement();

         _batfish.checkConfigurations();
         Map<String, Configuration> configurations = _batfish
               .loadConfigurations();

         _batfish.initRemoteIpsecVpns(configurations);
         Set<String> ipsecVpnNodes = answerElement.getIpsecVpnNodes();
         Set<String> node2RegexNodes = new HashSet<>();

         for (Configuration c : configurations.values()) {
            String hostname = c.getHostname();
            if (!c.getIpsecVpns().isEmpty()) {
               if (node1Regex.matcher(hostname).matches()) {
                  ipsecVpnNodes.add(c.getHostname());
               }
               if (node2Regex.matcher(hostname).matches()) {
                  node2RegexNodes.add(hostname);
               }
            }
         }
         for (Configuration c : configurations.values()) {
            String hostname = c.getHostname();
            if (!ipsecVpnNodes.contains(hostname)) {
               continue;
            }
            SortedSet<String> currentNeighbors = new TreeSet<>();
            if (!c.getIpsecVpns().isEmpty()) {
               for (IpsecVpn ipsecVpn : c.getIpsecVpns().values()) {
                  if (ipsecVpn.getRemoteIpsecVpn() != null) {
                     for (IpsecVpn remoteIpsecVpn : ipsecVpn
                           .getCandidateRemoteIpsecVpns()) {
                        String remoteHost = remoteIpsecVpn.getOwner()
                              .getHostname();
                        if (node2RegexNodes.contains(remoteHost)) {
                           currentNeighbors.add(remoteHost);
                        }
                     }
                  }
               }
               SortedSet<String> missingNeighbors = new TreeSet<>();
               missingNeighbors.addAll(node2RegexNodes);
               missingNeighbors.removeAll(currentNeighbors);
               missingNeighbors.remove(hostname);
               answerElement.getConnectedNeighbors().put(hostname,
                     currentNeighbors);
               answerElement.getMissingNeighbors().put(hostname,
                     missingNeighbors);
            }
         }

         return answerElement;
      }

   }

   public static class PairwiseVpnConnectivityQuestion extends Question {

      private static final String NODE1_REGEX_VAR = "node1Regex";

      private static final String NODE2_REGEX_VAR = "node2Regex";

      private String _node1Regex;

      private String _node2Regex;

      public PairwiseVpnConnectivityQuestion() {
         _node1Regex = ".*";
         _node2Regex = ".*";
      }

      @Override
      public boolean getDataPlane() {
         return false;
      }

      @Override
      public String getName() {
         return "pairwisevpnconnectivity";
      }

      @JsonProperty(NODE1_REGEX_VAR)
      public String getNode1Regex() {
         return _node1Regex;
      }

      @JsonProperty(NODE2_REGEX_VAR)
      public String getNode2Regex() {
         return _node2Regex;
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
               case NODE1_REGEX_VAR:
                  setNode1Regex(parameters.getString(paramKey));
                  break;
               case NODE2_REGEX_VAR:
                  setNode2Regex(parameters.getString(paramKey));
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

      public void setNode1Regex(String regex) {
         _node1Regex = regex;
      }

      public void setNode2Regex(String regex) {
         _node2Regex = regex;
      }

   }

   @Override
   protected Answerer createAnswerer(Question question, IBatfish batfish) {
      return new PairwiseVpnConnectivityAnswerer(question, batfish);
   }

   @Override
   protected Question createQuestion() {
      return new PairwiseVpnConnectivityQuestion();
   }

}
