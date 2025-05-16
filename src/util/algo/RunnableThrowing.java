package util.algo;

@FunctionalInterface
public interface RunnableThrowing {
    /**
     * Runs this operation.
     */
    void run() throws  Throwable;
}