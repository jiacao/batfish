package org.batfish.representation.cisco;

import java.util.List;

import org.batfish.datamodel.Configuration;
import org.batfish.datamodel.routing_policy.statement.CallStatement;
import org.batfish.datamodel.routing_policy.statement.Statement;
import org.batfish.main.Warnings;

public class RoutePolicyApplyStatement extends RoutePolicyStatement {

   private static final long serialVersionUID = 1L;

   private String _applyName;

   public RoutePolicyApplyStatement(String name) {
      _applyName = name;
   }

   @Override
   public void applyTo(List<Statement> statements, CiscoConfiguration cc,
         Configuration c, Warnings w) {
      statements.add(new CallStatement(_applyName));
   }

   public String getName() {
      return _applyName;
   }

}
