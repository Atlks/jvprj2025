package util.dbuke;

import jakarta.transaction.*;

import javax.transaction.xa.XAResource;
import java.util.ArrayList;
import java.util.List;

public class TrsctImp implements Transaction {

    public List<Runnable> rollback_list=new ArrayList<>();

    public void setRollbackAct(Runnable act) {this.rollback_list.add(act);
    }

    @Override
    public void commit() throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SecurityException, IllegalStateException, SystemException {

    }

    @Override
    public void rollback() throws IllegalStateException, SystemException {
        for(Runnable r:rollback_list)
        {
            r.run();
        }
    }


    @Override
    public boolean delistResource(XAResource xaRes, int flag) throws IllegalStateException, SystemException {
        return false;
    }

    @Override
    public boolean enlistResource(XAResource xaRes) throws RollbackException, IllegalStateException, SystemException {
        return false;
    }

    @Override
    public int getStatus() throws SystemException {
        return 0;
    }

    @Override
    public void registerSynchronization(Synchronization sync) throws RollbackException, IllegalStateException, SystemException {

    }


    @Override
    public void setRollbackOnly() throws IllegalStateException, SystemException {

    }
}
