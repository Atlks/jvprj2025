package util.jpaqry;

import jakarta.persistence.criteria.Selection;

import java.util.List;

public class SelectionAll<T> implements jakarta.persistence.criteria.Selection<T> {
    @Override
    public Selection<  T> alias(String s) {
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
    public Class<? extends T> getJavaType() {
        return null;
    }

    @Override
    public String getAlias() {
        return "";
    }
}
