package org.batfish.representation.cisco;

import org.batfish.datamodel.Configuration;
import org.batfish.datamodel.Prefix6Space;
import org.batfish.datamodel.routing_policy.expr.ExplicitPrefix6Set;
import org.batfish.datamodel.routing_policy.expr.PrefixSetExpr;
import org.batfish.main.Warnings;

public class RoutePolicyInlinePrefix6Set extends RoutePolicyPrefixSet {

   /**
    *
    */
   private static final long serialVersionUID = 1L;

   private Prefix6Space _prefix6Space;

   public RoutePolicyInlinePrefix6Set(Prefix6Space prefix6Space) {
      _prefix6Space = prefix6Space;
   }

   public Prefix6Space getPrefix6Space() {
      return _prefix6Space;
   }

   @Override
   public PrefixSetExpr toPrefixSetExpr(CiscoConfiguration cc, Configuration c,
         Warnings w) {
      return new ExplicitPrefix6Set(_prefix6Space);
   }

}
