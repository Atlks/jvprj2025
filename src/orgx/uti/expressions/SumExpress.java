package orgx.uti.expressions;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Selection;

import java.util.Collection;
import java.util.List;

public class SumExpress extends  ExpressionBase implements Expression {
    public String fld;

    public SumExpress(String fld1) {
        this.fld=fld1;
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
    public Expression as(Class aClass) {
        return null;
    }

    @Override
    public Predicate in(Expression expression) {
        return null;
    }

    @Override
    public Predicate in(Collection collection) {
        return null;
    }

    @Override
    public Predicate in(Expression[] expressions) {
        return null;
    }

    @Override
    public Selection alias(String s) {
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
    public Class getJavaType() {
        return null;
    }

    @Override
    public String getAlias() {
        return "";
    }
}
