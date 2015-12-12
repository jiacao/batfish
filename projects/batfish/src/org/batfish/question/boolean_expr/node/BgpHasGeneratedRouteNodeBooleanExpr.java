package org.batfish.question.boolean_expr.node;

import org.batfish.question.Environment;
import org.batfish.question.node_expr.NodeExpr;
import org.batfish.representation.BgpProcess;
import org.batfish.representation.Configuration;

public final class BgpHasGeneratedRouteNodeBooleanExpr extends NodeBooleanExpr {

   public BgpHasGeneratedRouteNodeBooleanExpr(NodeExpr caller) {
      super(caller);
   }

   @Override
   public Boolean evaluate(Environment environment) {
      Configuration node = _caller.evaluate(environment);
      BgpProcess bgpProcess = node.getBgpProcess();
      if (bgpProcess == null) {
         return false;
      }
      else {
         return bgpProcess.getGeneratedRoutes().size() > 0;
      }
   }

}