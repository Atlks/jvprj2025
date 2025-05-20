package util.fp;

import java.io.Serializable;
import java.util.function.BiConsumer;

// 泛型接口，继承 Serializable，方便获取 SerializedLambda
@FunctionalInterface
public interface SerializableBiConsumer<T, U> extends Serializable{
    void accept(T t, U u) throws Throwable;
}