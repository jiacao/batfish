package org.batfish.datamodel.routing_policy.statement;

import org.batfish.common.BatfishException;
import org.batfish.datamodel.routing_policy.Environment;
import org.batfish.datamodel.routing_policy.Result;
import org.batfish.datamodel.routing_policy.expr.CommunitySetExpr;

import com.fasterxml.jackson.annotation.JsonCreator;

public class RetainCommunity extends AbstractStatement {

   /**
    *
    */
   private static final long serialVersionUID = 1L;

   private CommunitySetExpr _expr;

   @JsonCreator
   public RetainCommunity() {
   }

   public RetainCommunity(CommunitySetExpr expr) {
      _expr = expr;
   }

   @Override
   public Result execute(Environment environment) {
      throw new BatfishException("unimplemented");
   }

   public CommunitySetExpr getExpr() {
      return _expr;
   }

   public void setExpr(CommunitySetExpr expr) {
      _expr = expr;
   }

}
