//package util.jpaqry;
//
//import jakarta.persistence.criteria.*;
//import jakarta.persistence.metamodel.EntityType;
//
//import java.util.List;
//import java.util.Set;
//
//public class CriteriaQueryImp<T> implements CriteriaQuery<T> {
//    public final Class<T> fromClz;
//    public Expression<Boolean> where_expression;
//
//    public CriteriaQueryImp(Class<T> aClass) {
//        this.fromClz=aClass;
//    }
//
//    @Override
//    public CriteriaQuery<T> select(Selection<? extends T> selection) {
//        return null;
//    }
//
//    @Override
//    public CriteriaQuery<T> multiselect(Selection<?>... selections) {
//        return null;
//    }
//
//    @Override
//    public CriteriaQuery<T> multiselect(List<Selection<?>> list) {
//        return null;
//    }
//
//    @Override
//    public <X> Root<X> from(Class<X> aClass) {
//        return null;
//    }
//
//    @Override
//    public <X> Root<X> from(EntityType<X> entityType) {
//        return null;
//    }
//
//    @Override
//    public CriteriaQuery<T> where(Expression<Boolean> expression) {
//        this.where_expression = expression;
//        return this;
//    }
//
//    @Override
//    public CriteriaQuery<T> where(Predicate... predicates) {
//        return null;
//    }
//
//    @Override
//    public CriteriaQuery<T> groupBy(Expression<?>... expressions) {
//        return null;
//    }
//
//    @Override
//    public CriteriaQuery<T> groupBy(List<Expression<?>> list) {
//        return null;
//    }
//
//    @Override
//    public CriteriaQuery<T> having(Expression<Boolean> expression) {
//        return null;
//    }
//
//    @Override
//    public CriteriaQuery<T> having(Predicate... predicates) {
//        return null;
//    }
//
//    @Override
//    public CriteriaQuery<T> orderBy(Order... orders) {
//        return null;
//    }
//
//    @Override
//    public CriteriaQuery<T> orderBy(List<Order> list) {
//        return null;
//    }
//
//    @Override
//    public CriteriaQuery<T> distinct(boolean b) {
//        return null;
//    }
//
//    @Override
//    public Set<Root<?>> getRoots() {
//        return Set.of();
//    }
//
//    @Override
//    public Selection<T> getSelection() {
//        return null;
//    }
//
//    @Override
//    public List<Expression<?>> getGroupList() {
//        return List.of();
//    }
//
//    @Override
//    public Predicate getGroupRestriction() {
//        return null;
//    }
//
//    @Override
//    public boolean isDistinct() {
//        return false;
//    }
//
//    @Override
//    public Class<T> getResultType() {
//        return null;
//    }
//
//    @Override
//    public List<Order> getOrderList() {
//        return List.of();
//    }
//
//    @Override
//    public Set<ParameterExpression<?>> getParameters() {
//        return Set.of();
//    }
//
//
//    @Override
//    public <U> Subquery<U> subquery(Class<U> aClass) {
//        return null;
//    }
//
//    @Override
//    public Predicate getRestriction() {
//        return (Predicate) this.where_expression;
//    }
//
//}