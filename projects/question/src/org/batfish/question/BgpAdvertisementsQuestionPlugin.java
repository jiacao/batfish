package org.batfish.question;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.batfish.common.Answerer;
import org.batfish.common.BatfishException;
import org.batfish.common.plugin.IBatfish;
import org.batfish.common.util.BatfishObjectMapper;
import org.batfish.datamodel.BgpAdvertisement;
import org.batfish.datamodel.Configuration;
import org.batfish.datamodel.PrefixSpace;
import org.batfish.datamodel.answers.AnswerElement;
import org.batfish.datamodel.questions.Question;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BgpAdvertisementsQuestionPlugin extends QuestionPlugin {

   @JsonInclude(Include.NON_DEFAULT)
   @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
   public static class BgpAdvertisementsAnswerElement implements AnswerElement {

      private static final String ALL_REQUESTED_ADVERTISEMENTS_VAR = "allRequestedAdvertisements";

      private static final String RECEIVED_EBGP_ADVERTISEMENTS_VAR = "receivedEbgpAdvertisements";

      private static final String RECEIVED_IBGP_ADVERTISEMENTS_VAR = "receivedIbgpAdvertisements";

      private static final String SENT_EBGP_ADVERTISEMENTS_VAR = "sentEbgpAdvertisements";

      private static final String SENT_IBGP_ADVERTISEMENTS_VAR = "sentIbgpAdvertisements";

      private SortedSet<BgpAdvertisement> _allRequestedAdvertisements;

      private SortedMap<String, SortedSet<BgpAdvertisement>> _receivedEbgpAdvertisements;

      private SortedMap<String, SortedSet<BgpAdvertisement>> _receivedIbgpAdvertisements;

      private SortedMap<String, SortedSet<BgpAdvertisement>> _sentEbgpAdvertisements;

      private SortedMap<String, SortedSet<BgpAdvertisement>> _sentIbgpAdvertisements;

      @JsonCreator
      public BgpAdvertisementsAnswerElement() {
      }

      public BgpAdvertisementsAnswerElement(
            Map<String, Configuration> configurations, Pattern nodeRegex,
            boolean ebgp, boolean ibgp, PrefixSpace prefixSpace,
            boolean received, boolean sent) {
         _allRequestedAdvertisements = new TreeSet<>();
         _receivedEbgpAdvertisements = (received && ebgp) ? new TreeMap<>()
               : null;
         _sentEbgpAdvertisements = (sent && ebgp) ? new TreeMap<>() : null;
         _receivedIbgpAdvertisements = (received && ibgp) ? new TreeMap<>()
               : null;
         _sentIbgpAdvertisements = (sent && ibgp) ? new TreeMap<>() : null;
         for (Entry<String, Configuration> e : configurations.entrySet()) {
            String hostname = e.getKey();
            Matcher nodeMatcher = nodeRegex.matcher(hostname);
            if (!nodeMatcher.matches()) {
               continue;
            }
            Configuration configuration = e.getValue();
            if (received) {
               if (ebgp) {
                  Set<BgpAdvertisement> advertisements = configuration
                        .getReceivedEbgpAdvertisements();
                  fill(_receivedEbgpAdvertisements, hostname, advertisements,
                        prefixSpace);
               }
               if (ibgp) {
                  Set<BgpAdvertisement> advertisements = configuration
                        .getReceivedIbgpAdvertisements();
                  fill(_receivedIbgpAdvertisements, hostname, advertisements,
                        prefixSpace);
               }
            }
            if (sent) {
               if (ebgp) {
                  Set<BgpAdvertisement> advertisements = configuration
                        .getSentEbgpAdvertisements();
                  fill(_sentEbgpAdvertisements, hostname, advertisements,
                        prefixSpace);
               }
               if (ibgp) {
                  Set<BgpAdvertisement> advertisements = configuration
                        .getSentIbgpAdvertisements();
                  fill(_sentIbgpAdvertisements, hostname, advertisements,
                        prefixSpace);
               }
            }
         }
      }

      private void fill(Map<String, SortedSet<BgpAdvertisement>> map,
            String hostname, Set<BgpAdvertisement> advertisements,
            PrefixSpace prefixSpace) {
         SortedSet<BgpAdvertisement> placedAdvertisements = new TreeSet<>();
         map.put(hostname, placedAdvertisements);
         for (BgpAdvertisement advertisement : advertisements) {
            if (prefixSpace.isEmpty()
                  || prefixSpace.containsPrefix(advertisement.getNetwork())) {
               placedAdvertisements.add(advertisement);
               _allRequestedAdvertisements.add(advertisement);
            }
         }
      }

      @JsonProperty(ALL_REQUESTED_ADVERTISEMENTS_VAR)
      public SortedSet<BgpAdvertisement> getAllRequestedAdvertisements() {
         return _allRequestedAdvertisements;
      }

      @JsonIdentityReference(alwaysAsId = true)
      @JsonProperty(RECEIVED_EBGP_ADVERTISEMENTS_VAR)
      public SortedMap<String, SortedSet<BgpAdvertisement>> getReceivedEbgpAdvertisements() {
         return _receivedEbgpAdvertisements;
      }

      @JsonIdentityReference(alwaysAsId = true)
      @JsonProperty(RECEIVED_IBGP_ADVERTISEMENTS_VAR)
      public SortedMap<String, SortedSet<BgpAdvertisement>> getReceivedIbgpAdvertisements() {
         return _receivedIbgpAdvertisements;
      }

      @JsonIdentityReference(alwaysAsId = true)
      @JsonProperty(SENT_EBGP_ADVERTISEMENTS_VAR)
      public SortedMap<String, SortedSet<BgpAdvertisement>> getSentEbgpAdvertisements() {
         return _sentEbgpAdvertisements;
      }

      @JsonIdentityReference(alwaysAsId = true)
      @JsonProperty(SENT_IBGP_ADVERTISEMENTS_VAR)
      public SortedMap<String, SortedSet<BgpAdvertisement>> getSentIbgpAdvertisements() {
         return _sentIbgpAdvertisements;
      }

      @Override
      public String prettyPrint() throws JsonProcessingException {
         // TODO: change this function to pretty print the answer
         ObjectMapper mapper = new BatfishObjectMapper();
         return mapper.writeValueAsString(this);
      }

      @JsonProperty(ALL_REQUESTED_ADVERTISEMENTS_VAR)
      public void setAllRequestedAdvertisements(
            SortedSet<BgpAdvertisement> allRequestedAdvertisements) {
         _allRequestedAdvertisements = allRequestedAdvertisements;
      }

      @JsonProperty(RECEIVED_EBGP_ADVERTISEMENTS_VAR)
      public void setReceivedEbgpAdvertisements(
            SortedMap<String, SortedSet<BgpAdvertisement>> receivedEbgpAdvertisements) {
         _receivedEbgpAdvertisements = receivedEbgpAdvertisements;
      }

      @JsonProperty(RECEIVED_IBGP_ADVERTISEMENTS_VAR)
      public void setReceivedIbgpAdvertisements(
            SortedMap<String, SortedSet<BgpAdvertisement>> receivedIbgpAdvertisements) {
         _receivedIbgpAdvertisements = receivedIbgpAdvertisements;
      }

      @JsonProperty(SENT_EBGP_ADVERTISEMENTS_VAR)
      public void setSentEbgpAdvertisements(
            SortedMap<String, SortedSet<BgpAdvertisement>> sentEbgpAdvertisements) {
         _sentEbgpAdvertisements = sentEbgpAdvertisements;
      }

      @JsonProperty(SENT_IBGP_ADVERTISEMENTS_VAR)
      public void setSentIbgpAdvertisements(
            SortedMap<String, SortedSet<BgpAdvertisement>> sentIbgpAdvertisements) {
         _sentIbgpAdvertisements = sentIbgpAdvertisements;
      }

   }

   public static class BgpAdvertisementsAnswerer extends Answerer {

      public BgpAdvertisementsAnswerer(Question question, IBatfish batfish) {
         super(question, batfish);
      }

      @Override
      public AnswerElement answer() {

         BgpAdvertisementsQuestion question = (BgpAdvertisementsQuestion) _question;

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

         _batfish.checkDataPlaneQuestionDependencies();
         Map<String, Configuration> configurations = _batfish
               .loadConfigurations();
         _batfish.initBgpAdvertisements(configurations);

         BgpAdvertisementsAnswerElement answerElement = new BgpAdvertisementsAnswerElement(
               configurations, nodeRegex, question.getEbgp(),
               question.getIbgp(), question.getPrefixSpace(),
               question.getReceived(), question.getSent());

         return answerElement;
      }

   }

   public static class BgpAdvertisementsQuestion extends Question {

      private static final String EBGP_VAR = "ebgp";

      private static final String IBGP_VAR = "ibgp";

      private static final String NODE_REGEX_VAR = "nodeRegex";

      private static final String PREFIX_RANGE_VAR = "prefixRange";

      private static final String RECEIVED_VAR = "received";

      private static final String SENT_VAR = "sent";

      private boolean _ebgp;

      private boolean _ibgp;

      private String _nodeRegex;

      private PrefixSpace _prefixSpace;

      private boolean _received;

      private boolean _sent;

      public BgpAdvertisementsQuestion() {
         _nodeRegex = ".*";
         _ebgp = true;
         _ibgp = true;
         _nodeRegex = ".*";
         _received = true;
         _sent = true;
         _prefixSpace = new PrefixSpace();
      }

      @Override
      public boolean getDataPlane() {
         return true;
      }

      @JsonProperty(EBGP_VAR)
      public boolean getEbgp() {
         return _ebgp;
      }

      @JsonProperty(IBGP_VAR)
      public boolean getIbgp() {
         return _ibgp;
      }

      @Override
      public String getName() {
         return "bgpadvertisements";
      }

      @JsonProperty(NODE_REGEX_VAR)
      public String getNodeRegex() {
         return _nodeRegex;
      }

      public PrefixSpace getPrefixSpace() {
         return _prefixSpace;
      }

      @JsonProperty(RECEIVED_VAR)
      public boolean getReceived() {
         return _received;
      }

      @JsonProperty(SENT_VAR)
      public boolean getSent() {
         return _sent;
      }

      @Override
      public boolean getTraffic() {
         return false;
      }

      public void setEbgp(boolean ebgp) {
         _ebgp = ebgp;
      }

      public void setIbgp(boolean ibgp) {
         _ibgp = ibgp;
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
               case EBGP_VAR:
                  setEbgp(parameters.getBoolean(paramKey));
                  break;
               case IBGP_VAR:
                  setIbgp(parameters.getBoolean(paramKey));
                  break;
               case NODE_REGEX_VAR:
                  setNodeRegex(parameters.getString(paramKey));
                  break;
               case PREFIX_RANGE_VAR:
                  setPrefixSpace(new ObjectMapper().<PrefixSpace> readValue(
                        parameters.getString(paramKey),
                        new TypeReference<PrefixSpace>() {
                        }));
                  break;
               case RECEIVED_VAR:
                  setReceived(parameters.getBoolean(paramKey));
                  break;
               case SENT_VAR:
                  setSent(parameters.getBoolean(paramKey));
                  break;
               default:
                  throw new BatfishException("Unknown key in "
                        + getClass().getSimpleName() + ": " + paramKey);
               }
            }
            catch (JSONException | IOException e) {
               throw new BatfishException("Exception in parameters", e);
            }
         }
      }

      public void setNodeRegex(String nodeRegex) {
         _nodeRegex = nodeRegex;
      }

      private void setPrefixSpace(PrefixSpace prefixSpace) {
         _prefixSpace = prefixSpace;
      }

      public void setReceived(boolean received) {
         _received = received;
      }

      public void setSent(boolean sent) {
         _sent = sent;
      }

   }

   @Override
   protected Answerer createAnswerer(Question question, IBatfish batfish) {
      return new BgpAdvertisementsAnswerer(question, batfish);
   }

   @Override
   protected Question createQuestion() {
      return new BgpAdvertisementsQuestion();
   }

}
