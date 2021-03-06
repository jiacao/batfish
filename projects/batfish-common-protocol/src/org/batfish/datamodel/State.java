package org.batfish.datamodel;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.batfish.common.BatfishException;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum State {
   ESTABLISHED(1),
   INVALID(3),
   NEW(0),
   RELATED(2);

   private final static Map<Integer, State> _map = buildNumberMap();

   private final static Map<String, State> _nameMap = buildNameMap();

   private synchronized static Map<String, State> buildNameMap() {
      Map<String, State> map = new HashMap<>();
      for (State value : State.values()) {
         String name = value.name().toLowerCase();
         map.put(name, value);
      }
      return Collections.unmodifiableMap(map);
   }

   private synchronized static Map<Integer, State> buildNumberMap() {
      Map<Integer, State> map = new HashMap<>();
      for (State value : State.values()) {
         int num = value._num;
         map.put(num, value);
      }
      return Collections.unmodifiableMap(map);
   }

   public static State fromNum(int num) {
      State instance = _map.get(num);
      if (instance == null) {
         throw new BatfishException("Not a valid state number: '" + num + "'");
      }
      return instance;
   }

   @JsonCreator
   public static State fromString(String name) {
      State state = _nameMap.get(name.toLowerCase());
      if (state == null) {
         throw new BatfishException("No state with name: '" + name + "'");
      }
      return state;
   }

   private final int _num;

   private State(int num) {
      _num = num;
   }

   public int number() {
      return _num;
   }
}
