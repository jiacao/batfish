package org.batfish.datamodel;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a bgp process on a router
 */
@JsonInclude(Include.NON_DEFAULT)
public class BgpProcess implements Serializable {

   private static final String GENERATED_ROUTES_VAR = "generatedRoutes";

   private static final String NEIGHBORS_VAR = "neighbors";

   private static final String ROUTER_ID_VAR = "routerId";

   /**
    *
    */
   private static final long serialVersionUID = 1L;

   /**
    * The set of <i>neighbor-independent</i> generated routes that may be
    * advertised by this process if permitted by their respective generation
    * policies
    */
   private Set<GeneratedRoute> _generatedRoutes;

   /**
    * A map of all the bgp neighbors with which the router owning this process
    * is configured to peer, keyed by prefix
    */
   private Map<Prefix, BgpNeighbor> _neighbors;

   private transient PrefixSpace _originationSpace;

   private Ip _routerId;

   /**
    * Constructs a BgpProcess
    */
   public BgpProcess() {
      _neighbors = new HashMap<>();
      _generatedRoutes = new HashSet<>();
   }

   /**
    * @return {@link #_generatedRoutes}
    */
   @JsonProperty(GENERATED_ROUTES_VAR)
   public Set<GeneratedRoute> getGeneratedRoutes() {
      return _generatedRoutes;
   }

   /**
    * @return {@link #_neighbors}
    */
   @JsonProperty(NEIGHBORS_VAR)
   public Map<Prefix, BgpNeighbor> getNeighbors() {
      return _neighbors;
   }

   @JsonIgnore
   public PrefixSpace getOriginationSpace() {
      return _originationSpace;
   }

   @JsonProperty(ROUTER_ID_VAR)
   public Ip getRouterId() {
      return _routerId;
   }

   @JsonProperty(GENERATED_ROUTES_VAR)
   public void setGeneratedRoutes(Set<GeneratedRoute> generatedRoutes) {
      _generatedRoutes = generatedRoutes;
   }

   @JsonProperty(NEIGHBORS_VAR)
   public void setNeighbors(Map<Prefix, BgpNeighbor> neighbors) {
      _neighbors = neighbors;
   }

   public void setOriginationSpace(PrefixSpace originationSpace) {
      _originationSpace = originationSpace;
   }

   @JsonProperty(ROUTER_ID_VAR)
   public void setRouterId(Ip routerId) {
      _routerId = routerId;
   }

}
