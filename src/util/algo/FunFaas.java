package util.algo;

@FunctionalInterface
public interface FunFaas<T, R> {

    /**
     * Applies this function to the given argument.
     *
     * @param t the function argument
     * @return the function result
     */
    R apply(T t) throws  Throwable;
}