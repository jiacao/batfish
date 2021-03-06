package org.batfish.question;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.batfish.common.Answerer;
import org.batfish.common.BatfishException;
import org.batfish.common.plugin.IBatfish;
import org.batfish.common.util.BatfishObjectMapper;
import org.batfish.datamodel.BgpNeighbor;
import org.batfish.datamodel.BgpProcess;
import org.batfish.datamodel.Configuration;
import org.batfish.datamodel.Edge;
import org.batfish.datamodel.Ip;
import org.batfish.datamodel.NeighborType;
import org.batfish.datamodel.Topology;
import org.batfish.datamodel.answers.AnswerElement;
import org.batfish.datamodel.collections.IpEdge;
import org.batfish.datamodel.questions.Question;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class NeighborsQuestionPlugin extends QuestionPlugin {

   public static class NeighborsAnswerElement implements AnswerElement {

      private static final String EBGP_NEIGHBORS_VAR = "ebgpNeighbors";

      private static final String IBGP_NEIGHBORS_VAR = "ibgpNeighbors";

      private final static String LAN_NEIGHBORS_VAR = "lanNeighbors";

      private SortedSet<IpEdge> _ebgpNeighbors;

      private SortedSet<IpEdge> _ibgpNeighbors;

      private SortedSet<Edge> _lanNeighbors;

      public void addLanEdge(Edge edge) {
         _lanNeighbors.add(edge);
      }

      @JsonProperty(EBGP_NEIGHBORS_VAR)
      public SortedSet<IpEdge> getEbgpNeighbors() {
         return _ebgpNeighbors;
      }

      @JsonProperty(IBGP_NEIGHBORS_VAR)
      public SortedSet<IpEdge> getIbgpNeighbors() {
         return _ibgpNeighbors;
      }

      @JsonProperty(LAN_NEIGHBORS_VAR)
      public SortedSet<Edge> getLanNeighbors() {
         return _lanNeighbors;
      }

      public void initEbgpNeighbors() {
         _ebgpNeighbors = new TreeSet<>();
      }

      public void initIbgpNeighbors() {
         _ibgpNeighbors = new TreeSet<>();
      }

      public void initLanNeighbors() {
         _lanNeighbors = new TreeSet<>();
      }

      @Override
      public String prettyPrint() throws JsonProcessingException {
         // TODO: change this function to pretty print the answer
         ObjectMapper mapper = new BatfishObjectMapper();
         return mapper.writeValueAsString(this);
      }

      @JsonProperty(EBGP_NEIGHBORS_VAR)
      public void setEbgpNeighbors(SortedSet<IpEdge> ebgpNeighbors) {
         _ebgpNeighbors = ebgpNeighbors;
      }

      @JsonProperty(IBGP_NEIGHBORS_VAR)
      public void setIbgpNeighbors(SortedSet<IpEdge> ibgpNeighbors) {
         _ibgpNeighbors = ibgpNeighbors;
      }

      @JsonProperty(LAN_NEIGHBORS_VAR)
      public void setLanNeighbors(SortedSet<Edge> lanNeighbors) {
         _lanNeighbors = lanNeighbors;
      }
   }

   public static class NeighborsAnswerer extends Answerer {

      private boolean _remoteBgpNeighborsInitialized;

      public NeighborsAnswerer(Question question, IBatfish batfish) {
         super(question, batfish);
      }

      @Override
      public AnswerElement answer() {
         NeighborsQuestion question = (NeighborsQuestion) _question;
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

         NeighborsAnswerElement answerElement = new NeighborsAnswerElement();

         Map<String, Configuration> configurations = _batfish
               .loadConfigurations();

         // for (NeighborType nType : question.getNeighborTypes()) {
         // switch (nType) {
         // case EBGP:
         // answerElement.initEbgpNeighbors();
         // initRemoteBgpNeighbors(_batfish, configurations);
         // break;
         // case IBGP:
         // answerElement.initIbgpNeighbors();
         // initRemoteBgpNeighbors(_batfish, configurations);
         // break;
         // case LAN:
         // answerElement.initLanNeighbors();
         // break;
         // default:
         // throw new BatfishException("Unsupported NeighborType: "
         // + nType.toString());
         //
         // }
         // }

         if (question.getNeighborTypes().contains(NeighborType.EBGP)) {
            answerElement.initEbgpNeighbors();
            initRemoteBgpNeighbors(_batfish, configurations);
            for (Configuration c : configurations.values()) {
               String hostname = c.getHostname();
               BgpProcess proc = c.getBgpProcess();
               if (proc != null) {
                  for (BgpNeighbor bgpNeighbor : proc.getNeighbors().values()) {
                     BgpNeighbor remoteBgpNeighbor = bgpNeighbor
                           .getRemoteBgpNeighbor();
                     if (remoteBgpNeighbor != null) {
                        boolean ebgp = !bgpNeighbor.getRemoteAs()
                              .equals(bgpNeighbor.getLocalAs());
                        if (ebgp) {
                           Configuration remoteHost = remoteBgpNeighbor
                                 .getOwner();
                           String remoteHostname = remoteHost.getHostname();
                           Matcher node1Matcher = node1Regex.matcher(hostname);
                           Matcher node2Matcher = node2Regex
                                 .matcher(remoteHostname);
                           if (node1Matcher.matches()
                                 && node2Matcher.matches()) {
                              Ip localIp = bgpNeighbor.getLocalIp();
                              Ip remoteIp = remoteBgpNeighbor.getLocalIp();
                              answerElement.getEbgpNeighbors()
                                    .add(new IpEdge(hostname, localIp,
                                          remoteHostname, remoteIp));
                           }
                        }
                     }
                  }
               }
            }
         }

         if (question.getNeighborTypes().contains(NeighborType.IBGP)) {
            answerElement.initIbgpNeighbors();
            initRemoteBgpNeighbors(_batfish, configurations);
            for (Configuration c : configurations.values()) {
               String hostname = c.getHostname();
               BgpProcess proc = c.getBgpProcess();
               if (proc != null) {
                  for (BgpNeighbor bgpNeighbor : proc.getNeighbors().values()) {
                     BgpNeighbor remoteBgpNeighbor = bgpNeighbor
                           .getRemoteBgpNeighbor();
                     if (remoteBgpNeighbor != null) {
                        boolean ibgp = bgpNeighbor.getRemoteAs()
                              .equals(bgpNeighbor.getLocalAs());
                        if (ibgp) {
                           Configuration remoteHost = remoteBgpNeighbor
                                 .getOwner();
                           String remoteHostname = remoteHost.getHostname();
                           Matcher node1Matcher = node1Regex.matcher(hostname);
                           Matcher node2Matcher = node2Regex
                                 .matcher(remoteHostname);
                           if (node1Matcher.matches()
                                 && node2Matcher.matches()) {
                              Ip localIp = bgpNeighbor.getLocalIp();
                              Ip remoteIp = remoteBgpNeighbor.getLocalIp();
                              answerElement.getIbgpNeighbors()
                                    .add(new IpEdge(hostname, localIp,
                                          remoteHostname, remoteIp));
                           }
                        }
                     }
                  }
               }
            }
         }

         if (question.getNeighborTypes().isEmpty()
               || question.getNeighborTypes().contains(NeighborType.LAN)) {
            answerElement.initLanNeighbors();
            Topology topology = _batfish.computeTopology(configurations);

            for (Edge edge : topology.getEdges()) {
               Matcher node1Matcher = node1Regex.matcher(edge.getNode1());
               Matcher node2Matcher = node2Regex.matcher(edge.getNode2());
               if (node1Matcher.matches() && node2Matcher.matches()) {
                  answerElement.addLanEdge(edge);
               }
            }
         }

         return answerElement;
      }

      private void initRemoteBgpNeighbors(IBatfish batfish,
            Map<String, Configuration> configurations) {
         if (!_remoteBgpNeighborsInitialized) {
            Map<Ip, Set<String>> ipOwners = _batfish
                  .computeIpOwners(configurations);
            batfish.initRemoteBgpNeighbors(configurations, ipOwners);
            _remoteBgpNeighborsInitialized = true;
         }
      }
   }

   public static class NeighborsQuestion extends Question {

      private static final String NEIGHBOR_TYPE_VAR = "neighborType";

      private static final String NODE1_REGEX_VAR = "node1Regex";

      private static final String NODE2_REGEX_VAR = "node2Regex";

      private Set<NeighborType> _neighborTypes;

      private String _node1Regex;

      private String _node2Regex;

      public NeighborsQuestion() {
         _node1Regex = ".*";
         _node2Regex = ".*";
         _neighborTypes = EnumSet.noneOf(NeighborType.class);
      }

      @Override
      public boolean getDataPlane() {
         return false;
      }

      @Override
      public String getName() {
         return "neighbors";
      }

      @JsonProperty(NEIGHBOR_TYPE_VAR)
      public Set<NeighborType> getNeighborTypes() {
         return _neighborTypes;
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
      public String prettyPrint() {
         try {
            String retString = String.format(
                  "neighbors %s%s=%s | %s=%s | %s=%s", prettyPrintBase(),
                  NODE1_REGEX_VAR, _node1Regex, NODE2_REGEX_VAR, _node2Regex,
                  NEIGHBOR_TYPE_VAR, _neighborTypes.toString());
            return retString;
         }
         catch (Exception e) {
            try {
               return "Pretty printing failed. Printing Json\n"
                     + toJsonString();
            }
            catch (JsonProcessingException e1) {
               throw new BatfishException(
                     "Both pretty and json printing failed\n");
            }
         }

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
               case NEIGHBOR_TYPE_VAR:
                  setNeighborTypes(
                        new ObjectMapper().<Set<NeighborType>> readValue(
                              parameters.getString(paramKey),
                              new TypeReference<Set<NeighborType>>() {
                              }));
                  break;
               case NODE2_REGEX_VAR:
                  setNode2Regex(parameters.getString(paramKey));
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

      public void setNeighborTypes(Set<NeighborType> neighborType) {
         _neighborTypes = neighborType;
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
      return new NeighborsAnswerer(question, batfish);
   }

   @Override
   protected Question createQuestion() {
      return new NeighborsQuestion();
   }

}
