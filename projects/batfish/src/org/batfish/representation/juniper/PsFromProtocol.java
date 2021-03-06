package org.batfish.representation.juniper;

import org.batfish.datamodel.Configuration;
import org.batfish.datamodel.RoutingProtocol;
import org.batfish.datamodel.routing_policy.expr.BooleanExpr;
import org.batfish.datamodel.routing_policy.expr.MatchProtocol;
import org.batfish.main.Warnings;

public final class PsFromProtocol extends PsFrom {

   /**
    *
    */
   private static final long serialVersionUID = 1L;

   private final RoutingProtocol _protocol;

   public PsFromProtocol(RoutingProtocol protocol) {
      _protocol = protocol;
   }

   public RoutingProtocol getProtocol() {
      return _protocol;
   }

   @Override
   public BooleanExpr toBooleanExpr(JuniperConfiguration jc, Configuration c,
         Warnings warnings) {
      return new MatchProtocol(_protocol);
   }

}
