package util;

import jakarta.transaction.Synchronization;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

public class TransactionImp  implements Transaction {
    /**
     * @return
     */
    @Override
    public TransactionStatus getStatus() {
        return null;
    }

    /**
     * @param synchronization
     */
    @Override
    public void registerSynchronization(Synchronization synchronization) {

    }

    /**
     * @param i
     */
    @Override
    public void setTimeout(int i) {

    }

    /**
     * @return
     */
    @Override
    public int getTimeout() {
        return 0;
    }

    /**
     *
     */
    @Override
    public void begin() {

    }

    /**
     *
     */
    @Override
    public void commit() {

    }

    /**
     *
     */
    @Override
    public void rollback() {

    }

    /**
     *
     */
    @Override
    public void setRollbackOnly() {

    }

    /**
     * @return
     */
    @Override
    public boolean getRollbackOnly() {
        return false;
    }

    /**
     * @return
     */
    @Override
    public boolean isActive() {
        return false;
    }
}
