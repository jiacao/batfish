package org.batfish.datamodel.routing_policy.statement;

import org.batfish.datamodel.BgpRoute;
import org.batfish.datamodel.collections.CommunitySet;
import org.batfish.datamodel.routing_policy.Environment;
import org.batfish.datamodel.routing_policy.Result;
import org.batfish.datamodel.routing_policy.expr.CommunitySetExpr;

import com.fasterxml.jackson.annotation.JsonCreator;

public class SetCommunity extends AbstractStatement {

   /**
    *
    */
   private static final long serialVersionUID = 1L;

   private CommunitySetExpr _expr;

   @JsonCreator
   public SetCommunity() {
   }

   public SetCommunity(CommunitySetExpr expr) {
      _expr = expr;
   }

   @Override
   public Result execute(Environment environment) {
      Result result = new Result();
      BgpRoute.Builder bgpRoute = (BgpRoute.Builder) environment
            .getOutputRoute();
      CommunitySet communities = _expr.communities(environment);
      bgpRoute.setCommunities(communities);
      return result;
   }

   public CommunitySetExpr getExpr() {
      return _expr;
   }

   public void setExpr(CommunitySetExpr expr) {
      _expr = expr;
   }

}
