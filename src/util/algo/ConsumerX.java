package util.algo;

@FunctionalInterface
public interface ConsumerX<T> {

    /**
     * Performs this operation on the given argument.
     *
     * @param t the input argument
     */
    void accept(T t) throws Throwable;

}