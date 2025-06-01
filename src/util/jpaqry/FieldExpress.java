//package util.jpaqry;
//
//import jakarta.persistence.criteria.Expression;
//import jakarta.persistence.criteria.Predicate;
//import jakarta.persistence.criteria.Selection;
//
//import java.util.Collection;
//import java.util.List;
//
//public class FieldExpress<T> implements Expression<T> {
//    public final String fld;
//
//    public FieldExpress(String a) {
//        this.fld=a;
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
//    public Selection<T> alias(String s) {
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
//
//    @Override
//    public Class<? extends T> getJavaType() {
//        return null;
//    }
//
//    @Override
//    public String getAlias() {
//        return "";
//    }
//}
