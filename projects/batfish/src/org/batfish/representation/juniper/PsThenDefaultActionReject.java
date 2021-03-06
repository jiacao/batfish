package org.batfish.representation.juniper;

import java.util.List;

import org.batfish.datamodel.Configuration;
import org.batfish.datamodel.routing_policy.statement.Statement;
import org.batfish.datamodel.routing_policy.statement.Statements;
import org.batfish.main.Warnings;

public class PsThenDefaultActionReject extends PsThen {

   /**
    *
    */
   private static final long serialVersionUID = 1L;

   @Override
   public void applyTo(List<Statement> statements,
         JuniperConfiguration juniperVendorConfiguration, Configuration c,
         Warnings warnings) {
      statements.add(Statements.SetDefaultActionReject.toStaticStatement());
   }

}
