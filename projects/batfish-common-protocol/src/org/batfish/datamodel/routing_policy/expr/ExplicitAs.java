package org.batfish.datamodel.routing_policy.expr;

import org.batfish.datamodel.routing_policy.Environment;

public class ExplicitAs implements AsExpr {

   /**
    *
    */
   private static final long serialVersionUID = 1L;

   private int _as;

   public ExplicitAs(int as) {
      _as = as;
   }

   @Override
   public int evaluate(Environment environment) {
      return _as;
   }

   public int getAs() {
      return _as;
   }

   public void setAs(int as) {
      _as = as;
   }

}
