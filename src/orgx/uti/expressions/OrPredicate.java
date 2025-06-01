package orgx.uti.expressions;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Selection;

import java.util.Collection;
import java.util.List;

public class OrPredicate extends PredicateBase  implements Predicate {
    @Override
    public BooleanOperator getOperator() {
        return null;
    }

    @Override
    public boolean isNegated() {
        return false;
    }

    @Override
    public List<Expression<Boolean>> getExpressions() {
        return List.of();
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
    public Predicate in(Object... objects) {
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
