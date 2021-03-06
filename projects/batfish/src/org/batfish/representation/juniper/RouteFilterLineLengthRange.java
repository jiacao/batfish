package org.batfish.representation.juniper;

import org.batfish.datamodel.LineAction;
import org.batfish.datamodel.Prefix;
import org.batfish.datamodel.RouteFilterList;
import org.batfish.datamodel.SubRange;

public final class RouteFilterLineLengthRange extends RouteFilterLine {

   /**
    *
    */
   private static final long serialVersionUID = 1L;

   private final int _maxPrefixLength;

   private final int _minPrefixLength;

   public RouteFilterLineLengthRange(Prefix prefix, int minPrefixLength,
         int maxPrefixLength) {
      super(prefix);
      _minPrefixLength = minPrefixLength;
      _maxPrefixLength = maxPrefixLength;
   }

   @Override
   public void applyTo(RouteFilterList rfl) {
      org.batfish.datamodel.RouteFilterLine line = new org.batfish.datamodel.RouteFilterLine(
            LineAction.ACCEPT, _prefix,
            new SubRange(_minPrefixLength, _maxPrefixLength));
      rfl.addLine(line);
   }

   @Override
   public boolean equals(Object o) {
      if (!this.getClass().equals(o.getClass())) {
         return false;
      }
      else {
         RouteFilterLineLengthRange rhs = (RouteFilterLineLengthRange) o;
         return _prefix.equals(rhs._prefix)
               && _minPrefixLength == rhs._minPrefixLength
               && _maxPrefixLength == rhs._maxPrefixLength;
      }
   }

   public int getMaxPrefixLength() {
      return _maxPrefixLength;
   }

   public int getMinPrefixLength() {
      return _minPrefixLength;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + _maxPrefixLength;
      result = prime * result + _minPrefixLength;
      result = prime * result + _prefix.hashCode();
      return result;
   }

}
