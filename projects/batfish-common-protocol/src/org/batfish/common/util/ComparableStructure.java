package org.batfish.common.util;

import java.io.Serializable;

import org.batfish.common.BatfishException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonInclude(Include.NON_DEFAULT)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public abstract class ComparableStructure<Key extends Comparable<Key>>
      extends ReferenceCountedStructure
      implements Comparable<ComparableStructure<Key>>, Serializable {

   protected static final String NAME_VAR = "name";
   private static final long serialVersionUID = 1L;
   protected Key _key;

   @JsonCreator
   public ComparableStructure(@JsonProperty(NAME_VAR) Key name) {
      _key = name;
   }

   @Override
   public int compareTo(ComparableStructure<Key> rhs) {
      return _key.compareTo(rhs._key);
   }

   @Override
   public boolean equals(Object o) {
      ComparableStructure<?> rhs = (ComparableStructure<?>) o;
      if (rhs._key.getClass().equals(_key.getClass())) {
         return _key.equals(rhs._key);
      }
      else {
         throw new BatfishException("Keys are of incompatible types");
      }
   }

   @JsonProperty(NAME_VAR)
   public Key getName() {
      return _key;
   }

   @Override
   public int hashCode() {
      return _key.hashCode();
   }

   @Override
   public String toString() {
      return getClass().getSimpleName() + "<" + _key.toString() + ">";
   }

}
