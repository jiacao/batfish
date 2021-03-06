package org.batfish.representation.cisco;

import java.util.Collections;

import org.batfish.datamodel.Configuration;
import org.batfish.datamodel.Ip;
import org.batfish.datamodel.routing_policy.expr.NextHopExpr;
import org.batfish.datamodel.routing_policy.expr.NextHopIp;
import org.batfish.main.Warnings;

public class RoutePolicyNextHopIp extends RoutePolicyNextHop {

   private static final long serialVersionUID = 1L;

   private Ip _address;

   public RoutePolicyNextHopIp(Ip address) {
      _address = address;
   }

   public Ip getAddress() {
      return _address;
   }

   @Override
   public NextHopExpr toNextHopExpr(CiscoConfiguration cc, Configuration c,
         Warnings w) {
      return new NextHopIp(Collections.singletonList(_address));
   }

}
