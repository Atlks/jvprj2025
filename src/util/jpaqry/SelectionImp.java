package util.jpaqry;

import jakarta.persistence.criteria.Selection;

import java.util.List;

public class SelectionImp implements Selection {
    public SelectionImp(Class<?> userClass) {
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
