package org.batfish.question;

import java.io.IOException;
import java.util.EnumSet;
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
import org.batfish.common.plugin.IBatfish;
import org.batfish.common.util.BatfishObjectMapper;
import org.batfish.datamodel.Configuration;
import org.batfish.datamodel.ConfigurationFormat;
import org.batfish.datamodel.NodeType;
import org.batfish.datamodel.RoutingProtocol;
import org.batfish.datamodel.answers.AnswerElement;
import org.batfish.datamodel.collections.RoleSet;
import org.batfish.datamodel.questions.Question;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class NodesQuestionPlugin extends QuestionPlugin {

   public static class NodesAnswerElement implements AnswerElement {

      @JsonInclude(Include.NON_DEFAULT)
      public static class NodeSummary {

         private SortedSet<String> _asPathAccessLists;

         private SortedSet<String> _communityLists;

         private ConfigurationFormat _configurationFormat;

         private SortedSet<String> _ikeGateways;

         private SortedSet<String> _ikePolicies;

         private SortedSet<String> _ikeProposals;

         private SortedSet<String> _interfaces;

         private SortedSet<String> _ipAccessLists;

         private SortedSet<String> _ipsecPolicies;

         private SortedSet<String> _ipsecProposals;

         private SortedSet<String> _ipsecVpns;

         private RoleSet _roles;

         private SortedSet<String> _routeFilterLists;

         private SortedSet<String> _routingPolicies;

         private EnumSet<RoutingProtocol> _routingProtocols;

         private SortedSet<String> _zones;

         public NodeSummary() {
         }

         public NodeSummary(Configuration node) {
            if (!node.getAsPathAccessLists().isEmpty()) {
               _asPathAccessLists = node.getAsPathAccessLists()
                     .navigableKeySet();
            }
            if (!node.getCommunityLists().isEmpty()) {
               _communityLists = node.getCommunityLists().navigableKeySet();
            }
            _configurationFormat = node.getConfigurationFormat();
            if (!node.getInterfaces().isEmpty()) {
               _interfaces = node.getInterfaces().navigableKeySet();
            }
            if (!node.getIkeGateways().isEmpty()) {
               _ikeGateways = node.getIkeGateways().navigableKeySet();
            }
            if (!node.getIkePolicies().isEmpty()) {
               _ikePolicies = node.getIkePolicies().navigableKeySet();
            }
            if (!node.getIkeProposals().isEmpty()) {
               _ikeProposals = node.getIkeProposals().navigableKeySet();
            }
            if (!node.getIpAccessLists().isEmpty()) {
               _ipAccessLists = node.getIpAccessLists().navigableKeySet();
            }
            if (!node.getIpsecPolicies().isEmpty()) {
               _ipsecPolicies = node.getIpsecPolicies().navigableKeySet();
            }
            if (!node.getIpsecProposals().isEmpty()) {
               _ipsecProposals = node.getIpsecProposals().navigableKeySet();
            }
            if (!node.getIpsecVpns().isEmpty()) {
               _ipsecVpns = node.getIpsecVpns().navigableKeySet();
            }
            if (!node.getRoutingPolicies().isEmpty()) {
               _routingPolicies = node.getRoutingPolicies().navigableKeySet();
            }
            if (!node.getRoles().isEmpty()) {
               _roles = node.getRoles();
            }
            if (!node.getRouteFilterLists().isEmpty()) {
               _routeFilterLists = node.getRouteFilterLists().navigableKeySet();
            }
            _routingProtocols = EnumSet.noneOf(RoutingProtocol.class);
            if (node.getBgpProcess() != null) {
               _routingProtocols.add(RoutingProtocol.BGP);
            }
            if (node.getOspfProcess() != null) {
               _routingProtocols.add(RoutingProtocol.OSPF);
            }
            if (node.getIsisProcess() != null) {
               _routingProtocols.add(RoutingProtocol.ISIS);
            }
            if (!node.getStaticRoutes().isEmpty()) {
               _routingProtocols.add(RoutingProtocol.STATIC);
            }
            if (!node.getGeneratedRoutes().isEmpty()) {
               _routingProtocols.add(RoutingProtocol.AGGREGATE);
            }
            if (!node.getZones().isEmpty()) {
               _zones = node.getZones().navigableKeySet();
            }
         }

         public SortedSet<String> getAsPathAccessLists() {
            return _asPathAccessLists;
         }

         public SortedSet<String> getCommunityLists() {
            return _communityLists;
         }

         public ConfigurationFormat getConfigurationFormat() {
            return _configurationFormat;
         }

         public SortedSet<String> getIkeGateways() {
            return _ikeGateways;
         }

         public SortedSet<String> getIkePolicies() {
            return _ikePolicies;
         }

         public SortedSet<String> getIkeProposals() {
            return _ikeProposals;
         }

         public SortedSet<String> getInterfaces() {
            return _interfaces;
         }

         public SortedSet<String> getIpAccessLists() {
            return _ipAccessLists;
         }

         public SortedSet<String> getIpsecPolicies() {
            return _ipsecPolicies;
         }

         public SortedSet<String> getIpsecProposals() {
            return _ipsecProposals;
         }

         public SortedSet<String> getIpsecVpns() {
            return _ipsecVpns;
         }

         public SortedSet<String> getPolicySortedMaps() {
            return _routingPolicies;
         }

         public RoleSet getRoles() {
            return _roles;
         }

         public SortedSet<String> getRouteFilterLists() {
            return _routeFilterLists;
         }

         public EnumSet<RoutingProtocol> getRoutingProtocols() {
            return _routingProtocols;
         }

         public SortedSet<String> getZones() {
            return _zones;
         }

         public void setAsPathAccessLists(SortedSet<String> asPathAccessLists) {
            _asPathAccessLists = asPathAccessLists;
         }

         public void setCommunityLists(SortedSet<String> communityLists) {
            _communityLists = communityLists;
         }

         public void setConfigurationFormat(
               ConfigurationFormat configurationFormat) {
            _configurationFormat = configurationFormat;
         }

         public void setIkeGateways(SortedSet<String> ikeGateways) {
            _ikeGateways = ikeGateways;
         }

         public void setIkePolicies(SortedSet<String> ikePolicies) {
            _ikePolicies = ikePolicies;
         }

         public void setIkeProposals(SortedSet<String> ikeProposals) {
            _ikeProposals = ikeProposals;
         }

         public void setInterfaces(SortedSet<String> interfaces) {
            _interfaces = interfaces;
         }

         public void setIpAccessLists(SortedSet<String> ipAccessLists) {
            _ipAccessLists = ipAccessLists;
         }

         public void setIpsecPolicies(SortedSet<String> ipsecPolicies) {
            _ipsecPolicies = ipsecPolicies;
         }

         public void setIpsecProposals(SortedSet<String> ipsecProposals) {
            _ipsecProposals = ipsecProposals;
         }

         public void setIpsecVpns(SortedSet<String> ipsecVpns) {
            _ipsecVpns = ipsecVpns;
         }

         public void setPolicySortedMaps(SortedSet<String> policySortedMaps) {
            _routingPolicies = policySortedMaps;
         }

         public void setRoles(RoleSet roles) {
            _roles = roles;
         }

         public void setRouteFilterLists(SortedSet<String> routeFilterLists) {
            _routeFilterLists = routeFilterLists;
         }

         public void setRoutingProtocols(
               EnumSet<RoutingProtocol> routingProtocols) {
            _routingProtocols = routingProtocols;
         }

         public void setZones(SortedSet<String> zones) {
            _zones = zones;
         }

      }

      private static final String NODES_VAR = "nodes";

      private static final String SUMMARY_VAR = "summary";

      private final SortedMap<String, Configuration> _nodes;

      private final SortedMap<String, NodeSummary> _summary;

      public NodesAnswerElement(SortedMap<String, Configuration> nodes,
            boolean summary) {
         if (summary) {
            _summary = new TreeMap<>();
            for (Entry<String, Configuration> e : nodes.entrySet()) {
               String hostname = e.getKey();
               Configuration node = e.getValue();
               _summary.put(hostname, new NodeSummary(node));
            }
            _nodes = null;
         }
         else {
            _nodes = nodes;
            _summary = null;
         }
      }

      @JsonCreator
      public NodesAnswerElement(
            @JsonProperty(NODES_VAR) SortedMap<String, Configuration> nodes,
            @JsonProperty(SUMMARY_VAR) SortedMap<String, NodeSummary> summary) {
         _nodes = nodes;
         _summary = summary;
      }

      @JsonProperty(NODES_VAR)
      public SortedMap<String, Configuration> getAnswer() {
         return _nodes;
      }

      @JsonProperty(SUMMARY_VAR)
      public SortedMap<String, NodeSummary> getSummary() {
         return _summary;
      }

      @Override
      public String prettyPrint() throws JsonProcessingException {
         // TODO: change this function to pretty print the answer
         ObjectMapper mapper = new BatfishObjectMapper();
         return mapper.writeValueAsString(this);
      }
   }

   public static class NodesAnswerer extends Answerer {

      public NodesAnswerer(Question question, IBatfish batfish) {
         super(question, batfish);
      }

      @Override
      public AnswerElement answer() {
         NodesQuestion question = (NodesQuestion) _question;

         _batfish.checkConfigurations();
         Map<String, Configuration> configurations = _batfish
               .loadConfigurations();

         // collect nodes nodes
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

         Set<String> nodes = new TreeSet<>();
         if (nodeRegex != null) {
            for (String node : configurations.keySet()) {
               if (!nodeRegex.matcher(node).matches()) {
                  continue;
               }
               nodes.addAll(configurations.keySet());
            }
         }
         SortedMap<String, Configuration> answerNodes = new TreeMap<>();
         answerNodes.putAll(configurations);
         answerNodes.keySet().retainAll(nodes);

         return new NodesAnswerElement(answerNodes, question.getSummary());
      }

   }

   public static class NodesQuestion extends Question {

      private static final String NODE_REGEX_VAR = "nodeRegex";

      private static final String NODE_TYPE_VAR = "nodeType";

      private static final String SUMMARY_VAR = "summary";

      private String _nodeRegex;

      private Set<NodeType> _nodeType;

      private boolean _summary;

      public NodesQuestion() {
         _nodeType = EnumSet.noneOf(NodeType.class);
         _nodeRegex = ".*";
         _summary = true;
      }

      @Override
      public boolean getDataPlane() {
         return false;
      }

      @Override
      public String getName() {
         return "nodes";
      }

      @JsonProperty(NODE_REGEX_VAR)
      public String getNodeRegex() {
         return _nodeRegex;
      }

      @JsonProperty(NODE_TYPE_VAR)
      public Set<NodeType> getNodeTypes() {
         return _nodeType;
      }

      public boolean getSummary() {
         return _summary;
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
               case NODE_TYPE_VAR:
                  setNodeTypes(new ObjectMapper().<Set<NodeType>> readValue(
                        parameters.getString(paramKey),
                        new TypeReference<Set<NodeType>>() {
                        }));
                  break;
               case SUMMARY_VAR:
                  setSummary(parameters.getBoolean(paramKey));
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

      public void setNodeRegex(String regex) {
         _nodeRegex = regex;
      }

      public void setNodeTypes(Set<NodeType> nType) {
         _nodeType = nType;
      }

      public void setSummary(boolean summary) {
         _summary = summary;
      }

   }

   @Override
   protected Answerer createAnswerer(Question question, IBatfish batfish) {
      return new NodesAnswerer(question, batfish);
   }

   @Override
   protected Question createQuestion() {
      return new NodesQuestion();
   }

}
