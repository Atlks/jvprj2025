package orgx.orm.oth;

@FunctionalInterface
public interface RunnableThrowing {
    /**
     * Runs this operation.
     */
    void run() throws  Throwable;
}