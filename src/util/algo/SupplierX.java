package util.algo;

@FunctionalInterface
public interface SupplierX<T> {

    /**
     * Gets a result.
     *
     * @return a result
     */
    T get() throws Throwable;
}
