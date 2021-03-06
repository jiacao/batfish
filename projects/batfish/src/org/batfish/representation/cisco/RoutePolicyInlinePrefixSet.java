package org.batfish.representation.cisco;

import org.batfish.datamodel.Configuration;
import org.batfish.datamodel.PrefixSpace;
import org.batfish.datamodel.routing_policy.expr.ExplicitPrefixSet;
import org.batfish.datamodel.routing_policy.expr.PrefixSetExpr;
import org.batfish.main.Warnings;

public class RoutePolicyInlinePrefixSet extends RoutePolicyPrefixSet {

   /**
    *
    */
   private static final long serialVersionUID = 1L;

   private PrefixSpace _prefixSpace;

   public RoutePolicyInlinePrefixSet(PrefixSpace prefixSpace) {
      _prefixSpace = prefixSpace;
   }

   public PrefixSpace getPrefixSpace() {
      return _prefixSpace;
   }

   @Override
   public PrefixSetExpr toPrefixSetExpr(CiscoConfiguration cc, Configuration c,
         Warnings w) {
      return new ExplicitPrefixSet(_prefixSpace);
   }

}
