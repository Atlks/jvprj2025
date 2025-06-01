//package util.jpaqry;
//
//import jakarta.persistence.criteria.Expression;
//import jakarta.persistence.criteria.Predicate;
//import jakarta.persistence.criteria.Selection;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
////pred implt expres
//public class AndExpress implements Predicate {
//
//    List<Predicate> list = new ArrayList<Predicate>();
//
//    public AndExpress(List<Predicate> list) {
//        this.list = list;
//    }
//
//    @Override
//    public BooleanOperator getOperator() {
//        return Predicate.BooleanOperator.AND;
//    }
//
//    @Override
//    public boolean isNegated() {
//        return false;
//    }
//
//    @Override
//    public List<Expression<Boolean>> getExpressions() {
//        List<Expression<Boolean>> li = new ArrayList<>();
//        for (Predicate predicate : list) {
//            li.add(predicate);
//
//        }
//        return li;
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
