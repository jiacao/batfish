package org.batfish.representation.juniper;

import java.util.List;

import org.batfish.common.VendorConversionException;
import org.batfish.datamodel.Configuration;
import org.batfish.datamodel.routing_policy.expr.NamedCommunitySet;
import org.batfish.datamodel.routing_policy.statement.DeleteCommunity;
import org.batfish.datamodel.routing_policy.statement.Statement;
import org.batfish.main.Warnings;

public final class PsThenCommunityDelete extends PsThen {

   /**
    *
    */
   private static final long serialVersionUID = 1L;

   private JuniperConfiguration _configuration;

   private final String _name;

   public PsThenCommunityDelete(String name,
         JuniperConfiguration configuration) {
      _name = name;
      _configuration = configuration;
   }

   @Override
   public void applyTo(List<Statement> statements,
         JuniperConfiguration juniperVendorConfiguration, Configuration c,
         Warnings warnings) {
      CommunityList namedList = _configuration.getCommunityLists().get(_name);
      if (namedList == null) {
         warnings
               .redFlag("Reference to undefined community: \"" + _name + "\"");
      }
      else {
         org.batfish.datamodel.CommunityList list = c.getCommunityLists()
               .get(_name);
         if (list == null) {
            throw new VendorConversionException(
                  "missing community list: \"" + _name + "\"");
         }
         statements.add(new DeleteCommunity(new NamedCommunitySet(_name)));
      }
   }

   public String getName() {
      return _name;
   }

}
