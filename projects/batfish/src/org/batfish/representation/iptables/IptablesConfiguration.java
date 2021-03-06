package org.batfish.representation.iptables;

import java.util.HashMap;
import java.util.Map;

import org.batfish.datamodel.collections.RoleSet;
import org.batfish.representation.VendorConfiguration;
import org.batfish.representation.iptables.IptablesChain.ChainPolicy;

public abstract class IptablesConfiguration extends VendorConfiguration {

   /**
    *
    */
   private static final long serialVersionUID = 1L;

   protected final RoleSet _roles;

   Map<String, IptablesTable> _tables = new HashMap<>();

   public IptablesConfiguration() {
      _roles = new RoleSet();
   }

   public void addChain(String tableName, String chainName) {
      addTable(tableName);
      _tables.get(tableName).addChain(chainName);
   }

   public void addRule(String tableName, String chainName, IptablesRule rule,
         int index) {
      addTable(tableName);
      _tables.get(tableName).addRule(chainName, rule, index);
   }

   public void addTable(String tableName) {
      if (!_tables.containsKey(tableName)) {
         _tables.put(tableName, new IptablesTable(tableName));
      }
   }

   public void setChainPolicy(String tableName, String chainName,
         ChainPolicy policy) {
      addTable(tableName);
      _tables.get(tableName).setChainPolicy(chainName, policy);
   }
}
