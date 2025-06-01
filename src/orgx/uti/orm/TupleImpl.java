package orgx.uti.orm;

import jakarta.persistence.TupleElement;
import org.hibernate.query.JpaTuple;

import java.util.ArrayList;
import java.util.List;

public class TupleImpl implements JpaTuple {
    //  private final TupleMetadata tupleMetadata;
    public List<String> alias=new ArrayList<>();
    public List<Object> row=new ArrayList<>();

    @Override
    public <X> X get(TupleElement<X> tupleElement) {
        return null;
    }

    @Override
    public <X> X get(String alias, Class<X> aClass) {
        return null;
    }

    @Override
    public Object get(String alias) {
        return null;
    }

    @Override
    public <X> X get(int i, Class<X> aClass) {
        Object result = this.row.get(i);

            return (X)result;

    }

    @Override
    public Object get(int i) {
        Object result = this.row.get(i);

        return result;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public List<TupleElement<?>> getElements() {
        return List.of();
    }
}
