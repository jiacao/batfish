package org.batfish.datamodel.routing_policy.expr;

import java.util.ArrayList;
import java.util.List;

import org.batfish.datamodel.routing_policy.Environment;
import org.batfish.datamodel.routing_policy.Result;

public class Disjunction extends AbstractBooleanExpr {

   /**
   *
   */
   private static final long serialVersionUID = 1L;

   private List<BooleanExpr> _disjuncts;

   public Disjunction() {
      _disjuncts = new ArrayList<>();
   }

   @Override
   public Result evaluate(Environment environment) {
      for (BooleanExpr disjunct : _disjuncts) {
         Result disjunctResult = disjunct.evaluate(environment);
         if (disjunctResult.getExit()) {
            return disjunctResult;
         }
         else if (disjunctResult.getBooleanValue()) {
            disjunctResult.setReturn(false);
            return disjunctResult;
         }
      }
      Result result = new Result();
      result.setBooleanValue(false);
      return result;
   }

   public List<BooleanExpr> getDisjuncts() {
      return _disjuncts;
   }

   public void setDisjuncts(List<BooleanExpr> disjuncts) {
      _disjuncts = disjuncts;
   }

   @Override
   public BooleanExpr simplify() {
      List<BooleanExpr> simpleDisjuncts = new ArrayList<>();
      boolean atLeastOneTrue = false;
      boolean atLeastOneComplex = false;
      for (BooleanExpr disjunct : _disjuncts) {
         BooleanExpr simpleDisjunct = disjunct.simplify();
         if (simpleDisjunct.equals(BooleanExprs.True.toStaticBooleanExpr())) {
            atLeastOneTrue = true;
            if (!atLeastOneComplex) {
               return BooleanExprs.True.toStaticBooleanExpr();
            }
            else if (!atLeastOneTrue) {
               simpleDisjuncts.add(simpleDisjunct);
            }
         }
         else if (!simpleDisjunct
               .equals(BooleanExprs.False.toStaticBooleanExpr())) {
            atLeastOneComplex = true;
            simpleDisjuncts.add(simpleDisjunct);
         }
      }

      if (simpleDisjuncts.isEmpty()) {
         return BooleanExprs.False.toStaticBooleanExpr();
      }
      else if (simpleDisjuncts.size() == 1) {
         return simpleDisjuncts.get(0);
      }
      else {
         Disjunction simple = new Disjunction();
         simple.setDisjuncts(simpleDisjuncts);
         simple.setComment(getComment());
         return simple;
      }
   }

}
