//package util.jpaqry;
//
//import jakarta.persistence.criteria.Expression;
//import jakarta.persistence.criteria.Predicate;
//import jakarta.persistence.criteria.Selection;
//
//import java.util.Collection;
//import java.util.List;
//
//public class EqualExprs implements Predicate {
//    public   Object rightVal;
//    public   String left;
//
//    public EqualExprs(String lft, Object v) {
//       this.left=lft;
//       this.rightVal=v;
//    }
//    public  String toString() {
//        return this.left+"="+this.rightVal.toString();
//    }
//
//    @Override
//    public BooleanOperator getOperator() {
//        //这里如何实现
//        return BooleanOperator.AND;
//    }
//
//    @Override
//    public boolean isNegated() {
//        return false;
//    }
//
//    @Override
//    public List<Expression<Boolean>> getExpressions() {
//        return List.of();
//    }
//
//    @Override
//    public Predicate not() {
//        return null;
//    }
//
//    @Override
//    public Predicate isNull() {
//        return null;
//    }
//
//    @Override
//    public Predicate isNotNull() {
//        return null;
//    }
//
//    @Override
//    public Predicate in(Object... objects) {
//        return null;
//    }
//
//    @Override
//    public Predicate in(Expression<?>... expressions) {
//        return null;
//    }
//
//    @Override
//    public Predicate in(Collection<?> collection) {
//        return null;
//    }
//
//    @Override
//    public Predicate in(Expression<Collection<?>> expression) {
//        return null;
//    }
//
//    @Override
//    public <X> Expression<X> as(Class<X> aClass) {
//        return null;
//    }
//
//    @Override
//    public Selection<Boolean> alias(String s) {
//        return null;
//    }
//
//    @Override
//    public boolean isCompoundSelection() {
//        return false;
//    }
//
//    @Override
//    public List<Selection<?>> getCompoundSelectionItems() {
//        return List.of();
//    }
//
//    @Override
//    public Class<? extends Boolean> getJavaType() {
//        return null;
//    }
//
//    @Override
//    public String getAlias() {
//        return "";
//    }
//}
