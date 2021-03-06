package org.batfish.question;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.batfish.common.Answerer;
import org.batfish.common.BatfishException;
import org.batfish.common.plugin.IBatfish;
import org.batfish.common.util.BatfishObjectMapper;
import org.batfish.datamodel.BgpNeighbor;
import org.batfish.datamodel.Configuration;
import org.batfish.datamodel.Interface;
import org.batfish.datamodel.Ip;
import org.batfish.datamodel.Prefix;
import org.batfish.datamodel.BgpNeighbor.BgpNeighborSummary;
import org.batfish.datamodel.answers.AnswerElement;
import org.batfish.datamodel.questions.Question;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BgpSessionCheckQuestionPlugin extends QuestionPlugin {

   public static class BgpSessionCheckAnswerElement implements AnswerElement {

      private SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> _allBgpNeighborSummarys;

      private SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> _broken;

      private SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> _ebgpBroken;

      private SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> _ebgpHalfOpen;

      private SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> _ebgpLocalIpOnLoopback;

      private SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> _ebgpLocalIpUnknown;

      private SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> _ebgpMissingLocalIp;

      private SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> _ebgpNonUniqueEndpoint;

      private SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> _ebgpRemoteIpOnLoopback;

      private SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> _ebgpRemoteIpUnknown;

      private SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> _halfOpen;

      private SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> _ibgpBroken;

      private SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> _ibgpHalfOpen;

      private SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> _ibgpLocalIpOnNonLoopback;

      private SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> _ibgpLocalIpUnknown;

      private SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> _ibgpMissingLocalIp;

      private SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> _ibgpNonUniqueEndpoint;

      private SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> _ibgpRemoteIpOnNonLoopback;

      private SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> _ibgpRemoteIpUnknown;

      private SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> _ignoredForeignEndpoints;

      private SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> _localIpUnknown;

      private SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> _missingLocalIp;

      private SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> _nonUniqueEndpoint;

      private SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> _remoteIpUnknown;

      public BgpSessionCheckAnswerElement() {
         _allBgpNeighborSummarys = new TreeMap<>();
         _broken = new TreeMap<>();
         _ebgpBroken = new TreeMap<>();
         _ibgpBroken = new TreeMap<>();
         _ebgpHalfOpen = new TreeMap<>();
         _ebgpLocalIpOnLoopback = new TreeMap<>();
         _ebgpLocalIpUnknown = new TreeMap<>();
         _ebgpMissingLocalIp = new TreeMap<>();
         _ebgpNonUniqueEndpoint = new TreeMap<>();
         _ebgpRemoteIpOnLoopback = new TreeMap<>();
         _ebgpRemoteIpUnknown = new TreeMap<>();
         _halfOpen = new TreeMap<>();
         _ibgpHalfOpen = new TreeMap<>();
         _ibgpLocalIpOnNonLoopback = new TreeMap<>();
         _ibgpLocalIpUnknown = new TreeMap<>();
         _ibgpMissingLocalIp = new TreeMap<>();
         _ibgpNonUniqueEndpoint = new TreeMap<>();
         _ibgpRemoteIpOnNonLoopback = new TreeMap<>();
         _ibgpRemoteIpUnknown = new TreeMap<>();
         _ignoredForeignEndpoints = new TreeMap<>();
         _localIpUnknown = new TreeMap<>();
         _missingLocalIp = new TreeMap<>();
         _nonUniqueEndpoint = new TreeMap<>();
         _remoteIpUnknown = new TreeMap<>();
      }

      public void add(
            SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> neighborsByHostname,
            Configuration c, BgpNeighborSummary bgpNeighbor) {
         String hostname = c.getHostname();
         SortedMap<Prefix, BgpNeighborSummary> neighborsByPrefix = neighborsByHostname
               .get(hostname);
         if (neighborsByPrefix == null) {
            neighborsByPrefix = new TreeMap<>();
            neighborsByHostname.put(hostname, neighborsByPrefix);
         }
         Prefix prefix = bgpNeighbor.getPrefix();
         neighborsByPrefix.put(prefix, bgpNeighbor);
      }

      public SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> getAllBgpNeighbors() {
         return _allBgpNeighborSummarys;
      }

      @JsonIdentityReference(alwaysAsId = true)
      public SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> getBroken() {
         return _broken;
      }

      @JsonIdentityReference(alwaysAsId = true)
      public SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> getEbgpBroken() {
         return _ebgpBroken;
      }

      @JsonIdentityReference(alwaysAsId = true)
      public SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> getEbgpHalfOpen() {
         return _ebgpHalfOpen;
      }

      @JsonIdentityReference(alwaysAsId = true)
      public SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> getEbgpLocalIpOnLoopback() {
         return _ebgpLocalIpOnLoopback;
      }

      @JsonIdentityReference(alwaysAsId = true)
      public SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> getEbgpLocalIpUnknown() {
         return _ebgpLocalIpUnknown;
      }

      @JsonIdentityReference(alwaysAsId = true)
      public SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> getEbgpMissingLocalIp() {
         return _ebgpMissingLocalIp;
      }

      @JsonIdentityReference(alwaysAsId = true)
      public SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> getEbgpNonUniqueEndpoint() {
         return _ebgpNonUniqueEndpoint;
      }

      @JsonIdentityReference(alwaysAsId = true)
      public SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> getEbgpRemoteIpOnLoopback() {
         return _ebgpRemoteIpOnLoopback;
      }

      @JsonIdentityReference(alwaysAsId = true)
      public SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> getEbgpRemoteIpUnknown() {
         return _ebgpRemoteIpUnknown;
      }

      @JsonIdentityReference(alwaysAsId = true)
      public SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> getHalfOpen() {
         return _halfOpen;
      }

      @JsonIdentityReference(alwaysAsId = true)
      public SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> getIbgpBroken() {
         return _ibgpBroken;
      }

      @JsonIdentityReference(alwaysAsId = true)
      public SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> getIbgpHalfOpen() {
         return _ibgpHalfOpen;
      }

      @JsonIdentityReference(alwaysAsId = true)
      public SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> getIbgpLocalIpOnNonLoopback() {
         return _ibgpLocalIpOnNonLoopback;
      }

      @JsonIdentityReference(alwaysAsId = true)
      public SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> getIbgpLocalIpUnknown() {
         return _ibgpLocalIpUnknown;
      }

      @JsonIdentityReference(alwaysAsId = true)
      public SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> getIbgpMissingLocalIp() {
         return _ibgpMissingLocalIp;
      }

      @JsonIdentityReference(alwaysAsId = true)
      public SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> getIbgpNonUniqueEndpoint() {
         return _ibgpNonUniqueEndpoint;
      }

      @JsonIdentityReference(alwaysAsId = true)
      public SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> getIbgpRemoteIpOnNonLoopback() {
         return _ibgpRemoteIpOnNonLoopback;
      }

      @JsonIdentityReference(alwaysAsId = true)
      public SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> getIbgpRemoteIpUnknown() {
         return _ibgpRemoteIpUnknown;
      }

      @JsonIdentityReference(alwaysAsId = true)
      public SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> getIgnoredForeignEndpoints() {
         return _ignoredForeignEndpoints;
      }

      @JsonIdentityReference(alwaysAsId = true)
      public SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> getLocalIpUnknown() {
         return _localIpUnknown;
      }

      @JsonIdentityReference(alwaysAsId = true)
      public SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> getMissingLocalIp() {
         return _missingLocalIp;
      }

      @JsonIdentityReference(alwaysAsId = true)
      public SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> getNonUniqueEndpoint() {
         return _nonUniqueEndpoint;
      }

      @JsonIdentityReference(alwaysAsId = true)
      public SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> getRemoteIpUnknown() {
         return _remoteIpUnknown;
      }

      @Override
      public String prettyPrint() throws JsonProcessingException {
         // TODO: change this function to pretty print the answer
         ObjectMapper mapper = new BatfishObjectMapper();
         return mapper.writeValueAsString(this);
      }

      public void setAllBgpNeighborSummarys(
            SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> allBgpNeighborSummarys) {
         _allBgpNeighborSummarys = allBgpNeighborSummarys;
      }

      public void setBroken(
            SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> broken) {
         _broken = broken;
      }

      public void setEbgpBroken(
            SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> ebgpBroken) {
         _ebgpBroken = ebgpBroken;
      }

      public void setEbgpHalfOpen(
            SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> ebgpHalfOpen) {
         _ebgpHalfOpen = ebgpHalfOpen;
      }

      public void setEbgpLocalIpOnLoopback(
            SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> ebgpLocalIpOnLoopback) {
         _ebgpLocalIpOnLoopback = ebgpLocalIpOnLoopback;
      }

      public void setEbgpLocalIpUnknown(
            SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> ebgpLocalIpUnknown) {
         _ebgpLocalIpUnknown = ebgpLocalIpUnknown;
      }

      public void setEbgpMissingLocalIp(
            SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> ebgpMissingLocalIp) {
         _ebgpMissingLocalIp = ebgpMissingLocalIp;
      }

      public void setEbgpNonUniqueEndpoint(
            SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> ebgpNonUniqueEndpoint) {
         _ebgpNonUniqueEndpoint = ebgpNonUniqueEndpoint;
      }

      public void setEbgpRemoteIpOnLoopback(
            SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> ebgpRemoteIpOnLoopback) {
         _ebgpRemoteIpOnLoopback = ebgpRemoteIpOnLoopback;
      }

      public void setEbgpRemoteIpUnknown(
            SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> ebgpRemoteIpUnknown) {
         _ebgpRemoteIpUnknown = ebgpRemoteIpUnknown;
      }

      public void setHalfOpen(
            SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> halfOpen) {
         _halfOpen = halfOpen;
      }

      public void setIbgpBroken(
            SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> ibgpBroken) {
         _ibgpBroken = ibgpBroken;
      }

      public void setIbgpHalfOpen(
            SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> ibgpHalfOpen) {
         _ibgpHalfOpen = ibgpHalfOpen;
      }

      public void setIbgpLocalIpOnNonLoopback(
            SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> ibgpLocalIpOnNonLoopback) {
         _ibgpLocalIpOnNonLoopback = ibgpLocalIpOnNonLoopback;
      }

      public void setIbgpLocalIpUnknown(
            SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> ibgpLocalIpUnknown) {
         _ibgpLocalIpUnknown = ibgpLocalIpUnknown;
      }

      public void setIbgpMissingLocalIp(
            SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> ibgpMissingLocalIp) {
         _ibgpMissingLocalIp = ibgpMissingLocalIp;
      }

      public void setIbgpNonUniqueEndpoint(
            SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> ibgpNonUniqueEndpoint) {
         _ibgpNonUniqueEndpoint = ibgpNonUniqueEndpoint;
      }

      public void setIbgpRemoteIpOnNonLoopback(
            SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> ibgpRemoteIpOnNonLoopback) {
         _ibgpRemoteIpOnNonLoopback = ibgpRemoteIpOnNonLoopback;
      }

      public void setIbgpRemoteIpUnknown(
            SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> ibgpRemoteIpUnknown) {
         _ibgpRemoteIpUnknown = ibgpRemoteIpUnknown;
      }

      public void setIgnoredForeignEndpoints(
            SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> ignoredForeignEndpoints) {
         _ignoredForeignEndpoints = ignoredForeignEndpoints;
      }

      public void setLocalIpUnknown(
            SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> localIpUnknown) {
         _localIpUnknown = localIpUnknown;
      }

      public void setMissingLocalIp(
            SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> missingLocalIp) {
         _missingLocalIp = missingLocalIp;
      }

      public void setNonUniqueEndpoint(
            SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> nonUniqueEndpoint) {
         _nonUniqueEndpoint = nonUniqueEndpoint;
      }

      public void setRemoteIpUnknown(
            SortedMap<String, SortedMap<Prefix, BgpNeighborSummary>> remoteIpUnknown) {
         _remoteIpUnknown = remoteIpUnknown;
      }
   }

   public static class BgpSessionCheckAnswerer extends Answerer {

      public BgpSessionCheckAnswerer(Question question, IBatfish batfish) {
         super(question, batfish);
      }

      @Override
      public AnswerElement answer() {

         BgpSessionCheckQuestion question = (BgpSessionCheckQuestion) _question;

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
         _batfish.checkConfigurations();
         Map<String, Configuration> configurations = _batfish
               .loadConfigurations();

         BgpSessionCheckAnswerElement answerElement = new BgpSessionCheckAnswerElement();
         Set<Ip> allInterfaceIps = new HashSet<>();
         Set<Ip> loopbackIps = new HashSet<>();
         Map<Ip, Set<String>> ipOwners = new HashMap<>();
         for (Configuration c : configurations.values()) {
            for (Interface i : c.getInterfaces().values()) {
               if (i.getActive()) {
                  if (i.getPrefix() != null) {
                     for (Prefix prefix : i.getAllPrefixes()) {
                        Ip address = prefix.getAddress();
                        if (i.isLoopback(c.getConfigurationFormat())) {
                           loopbackIps.add(address);
                        }
                        allInterfaceIps.add(address);
                        Set<String> currentIpOwners = ipOwners.get(address);
                        if (currentIpOwners == null) {
                           currentIpOwners = new HashSet<>();
                           ipOwners.put(address, currentIpOwners);
                        }
                        currentIpOwners.add(c.getHostname());
                     }
                  }
               }
            }
         }
         _batfish.initRemoteBgpNeighbors(configurations, ipOwners);
         for (Configuration c : configurations.values()) {
            if (!node1Regex.matcher(c.getHostname()).matches()) {
               continue;
            }
            if (c.getBgpProcess() != null) {
               for (BgpNeighbor bgpNeighbor : c.getBgpProcess().getNeighbors()
                     .values()) {
                  BgpNeighborSummary bgpNeighborSummary = new BgpNeighborSummary(
                        bgpNeighbor);
                  answerElement.add(answerElement.getAllBgpNeighbors(), c,
                        bgpNeighborSummary);
                  boolean foreign = bgpNeighbor.getGroup() != null && question
                        .getForeignBgpGroups().contains(bgpNeighbor.getGroup());
                  boolean ebgp = bgpNeighbor.getRemoteAs() != bgpNeighbor
                        .getLocalAs();
                  boolean ebgpMultihop = bgpNeighbor.getEbgpMultihop();
                  Ip localIp = bgpNeighbor.getLocalIp();
                  Ip remoteIp = bgpNeighbor.getAddress();
                  if (bgpNeighbor.getPrefix().getPrefixLength() != 32) {
                     continue;
                  }
                  if (ebgp) {
                     if (!ebgpMultihop && loopbackIps.contains(localIp)) {
                        answerElement.add(
                              answerElement.getEbgpLocalIpOnLoopback(), c,
                              bgpNeighborSummary);
                     }
                     if (localIp == null) {
                        answerElement.add(answerElement.getBroken(), c,
                              bgpNeighborSummary);
                        answerElement.add(answerElement.getMissingLocalIp(), c,
                              bgpNeighborSummary);
                        answerElement.add(answerElement.getEbgpBroken(), c,
                              bgpNeighborSummary);
                        answerElement.add(answerElement.getEbgpMissingLocalIp(),
                              c, bgpNeighborSummary);
                     }
                  }
                  else {
                     // ibgp
                     if (!loopbackIps.contains(localIp)) {
                        answerElement.add(
                              answerElement.getIbgpLocalIpOnNonLoopback(), c,
                              bgpNeighborSummary);
                     }
                     if (localIp == null) {
                        answerElement.add(answerElement.getBroken(), c,
                              bgpNeighborSummary);
                        answerElement.add(answerElement.getMissingLocalIp(), c,
                              bgpNeighborSummary);
                        answerElement.add(answerElement.getIbgpBroken(), c,
                              bgpNeighborSummary);
                        answerElement.add(answerElement.getIbgpMissingLocalIp(),
                              c, bgpNeighborSummary);
                     }
                  }
                  if (foreign) {
                     answerElement.add(
                           answerElement.getIgnoredForeignEndpoints(), c,
                           bgpNeighborSummary);
                  }
                  else {
                     // not foreign
                     if (ebgp) {
                        if (localIp != null
                              && !allInterfaceIps.contains(localIp)) {
                           answerElement.add(answerElement.getBroken(), c,
                                 bgpNeighborSummary);
                           answerElement.add(answerElement.getLocalIpUnknown(),
                                 c, bgpNeighborSummary);
                           answerElement.add(answerElement.getEbgpBroken(), c,
                                 bgpNeighborSummary);
                           answerElement.add(
                                 answerElement.getEbgpLocalIpUnknown(), c,
                                 bgpNeighborSummary);
                        }
                        if (!allInterfaceIps.contains(remoteIp)) {
                           answerElement.add(answerElement.getBroken(), c,
                                 bgpNeighborSummary);
                           answerElement.add(answerElement.getRemoteIpUnknown(),
                                 c, bgpNeighborSummary);
                           answerElement.add(answerElement.getEbgpBroken(), c,
                                 bgpNeighborSummary);
                           answerElement.add(
                                 answerElement.getEbgpRemoteIpUnknown(), c,
                                 bgpNeighborSummary);
                        }
                        else {
                           if (!ebgpMultihop && loopbackIps.contains(remoteIp)
                                 && node2RegexMatchesIp(remoteIp, ipOwners,
                                       node2Regex)) {
                              answerElement.add(
                                    answerElement.getEbgpRemoteIpOnLoopback(),
                                    c, bgpNeighborSummary);
                           }
                        }
                        // check half open
                        if (localIp != null
                              && allInterfaceIps.contains(remoteIp)
                              && node2RegexMatchesIp(remoteIp, ipOwners,
                                    node2Regex)) {
                           if (bgpNeighbor.getRemoteBgpNeighbor() == null) {
                              answerElement.add(answerElement.getBroken(), c,
                                    bgpNeighborSummary);
                              answerElement.add(answerElement.getHalfOpen(), c,
                                    bgpNeighborSummary);
                              answerElement.add(answerElement.getEbgpBroken(),
                                    c, bgpNeighborSummary);
                              answerElement.add(answerElement.getEbgpHalfOpen(),
                                    c, bgpNeighborSummary);
                           }
                           else if (bgpNeighbor.getCandidateRemoteBgpNeighbors()
                                 .size() != 1) {
                              answerElement.add(
                                    answerElement.getNonUniqueEndpoint(), c,
                                    bgpNeighborSummary);
                              answerElement.add(
                                    answerElement.getEbgpNonUniqueEndpoint(), c,
                                    bgpNeighborSummary);
                           }
                        }
                     }
                     else {
                        // ibgp
                        if (localIp != null
                              && !allInterfaceIps.contains(localIp)) {
                           answerElement.add(answerElement.getBroken(), c,
                                 bgpNeighborSummary);
                           answerElement.add(answerElement.getIbgpBroken(), c,
                                 bgpNeighborSummary);
                           answerElement.add(
                                 answerElement.getIbgpLocalIpUnknown(), c,
                                 bgpNeighborSummary);
                        }
                        if (!allInterfaceIps.contains(remoteIp)) {
                           answerElement.add(answerElement.getBroken(), c,
                                 bgpNeighborSummary);
                           answerElement.add(answerElement.getIbgpBroken(), c,
                                 bgpNeighborSummary);
                           answerElement.add(
                                 answerElement.getIbgpRemoteIpUnknown(), c,
                                 bgpNeighborSummary);
                        }
                        else {
                           if (!loopbackIps.contains(remoteIp)
                                 && node2RegexMatchesIp(remoteIp, ipOwners,
                                       node2Regex)) {
                              answerElement.add(
                                    answerElement
                                          .getIbgpRemoteIpOnNonLoopback(),
                                    c, bgpNeighborSummary);
                           }
                        }
                        if (localIp != null
                              && allInterfaceIps.contains(remoteIp)
                              && node2RegexMatchesIp(remoteIp, ipOwners,
                                    node2Regex)) {
                           if (bgpNeighbor.getRemoteBgpNeighbor() == null) {
                              answerElement.add(answerElement.getBroken(), c,
                                    bgpNeighborSummary);
                              answerElement.add(answerElement.getHalfOpen(), c,
                                    bgpNeighborSummary);
                              answerElement.add(answerElement.getIbgpBroken(),
                                    c, bgpNeighborSummary);
                              answerElement.add(answerElement.getIbgpHalfOpen(),
                                    c, bgpNeighborSummary);
                           }
                           else if (bgpNeighbor.getCandidateRemoteBgpNeighbors()
                                 .size() != 1) {
                              answerElement.add(
                                    answerElement.getNonUniqueEndpoint(), c,
                                    bgpNeighborSummary);
                              answerElement.add(
                                    answerElement.getIbgpNonUniqueEndpoint(), c,
                                    bgpNeighborSummary);
                           }
                        }
                     }
                  }
               }
            }
         }
         return answerElement;
      }

      private boolean node2RegexMatchesIp(Ip ip, Map<Ip, Set<String>> ipOwners,
            Pattern node2Regex) {
         Set<String> owners = ipOwners.get(ip);
         if (owners == null) {
            throw new BatfishException(
                  "Expected at least one owner of ip: " + ip.toString());
         }
         for (String owner : owners) {
            if (node2Regex.matcher(owner).matches()) {
               return true;
            }
         }
         return false;
      }

   }

   public static class BgpSessionCheckQuestion extends Question {

      private static final String FOREIGN_BGP_GROUPS_VAR = "foreignBgpGroups";

      private static final String NODE1_REGEX_VAR = "node1Regex";

      private static final String NODE2_REGEX_VAR = "node2Regex";

      private Set<String> _foreignBgpGroups;

      private String _node1Regex;

      private String _node2Regex;

      public BgpSessionCheckQuestion() {
         _foreignBgpGroups = new TreeSet<>();
         _node1Regex = ".*";
         _node2Regex = ".*";
      }

      @Override
      public boolean getDataPlane() {
         return false;
      }

      @JsonProperty(FOREIGN_BGP_GROUPS_VAR)
      public Set<String> getForeignBgpGroups() {
         return _foreignBgpGroups;
      }

      @Override
      public String getName() {
         return "bgpsessioncheck";
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

      public void setForeignBgpGroups(Set<String> foreignBgpGroups) {
         _foreignBgpGroups = foreignBgpGroups;
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
               case FOREIGN_BGP_GROUPS_VAR:
                  setForeignBgpGroups(new ObjectMapper()
                        .<Set<String>> readValue(parameters.getString(paramKey),
                              new TypeReference<Set<String>>() {
                              }));
                  break;
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
            catch (JSONException | IOException e) {
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
      return new BgpSessionCheckAnswerer(question, batfish);
   }

   @Override
   protected Question createQuestion() {
      return new BgpSessionCheckQuestion();
   }

}
