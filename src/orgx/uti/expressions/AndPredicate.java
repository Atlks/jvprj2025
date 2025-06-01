package orgx.uti.expressions;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Selection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//pred implt expres
public class AndPredicate implements Predicate {
    public String prdctType = "and";
    public List<Predicate> predicateList = new ArrayList<Predicate>();

    public AndPredicate(List<Predicate> list) {
        this.predicateList = list;
    }

    public AndPredicate() {

    }


    @Override
    public BooleanOperator getOperator() {
        return BooleanOperator.AND;
    }

    @Override
    public List<Expression<Boolean>> getExpressions() {
        List<Expression<Boolean>> li = new ArrayList<>();
        for (Predicate predicate : predicateList) {
            li.add(predicate);
        }
        return li;
    }



    @Override
    public boolean isNegated() {
        return false;
    }


    @Override
    public Predicate not() {
        return null;
    }

    @Override
    public Predicate isNull() {
        return null;
    }

    @Override
    public Predicate isNotNull() {
        return null;
    }

    @Override
    public Predicate equalTo(Expression<?> expression) {
        return null;
    }

    @Override
    public Predicate equalTo(Object o) {
        return null;
    }

    @Override
    public Predicate notEqualTo(Expression<?> expression) {
        return null;
    }

    @Override
    public Predicate notEqualTo(Object o) {
        return null;
    }

    @Override
    public Predicate in(Object... objects) {
        return null;
    }

    @Override
    public Predicate in(Expression<?>... expressions) {
        return null;
    }

    @Override
    public Predicate in(Collection<?> collection) {
        return null;
    }

    @Override
    public Predicate in(Expression<Collection<?>> expression) {
        return null;
    }

    @Override
    public <X> Expression<X> as(Class<X> aClass) {
        return null;
    }

    @Override
    public <X> Expression<X> cast(Class<X> aClass) {
        return null;
    }

    @Override
    public Selection<Boolean> alias(String s) {
        return null;
    }

    @Override
    public boolean isCompoundSelection() {
        return false;
    }

    @Override
    public List<Selection<?>> getCompoundSelectionItems() {
        return List.of();
    }

    @Override
    public Class<? extends Boolean> getJavaType() {
        return null;
    }

    @Override
    public String getAlias() {
        return "";
    }
}
