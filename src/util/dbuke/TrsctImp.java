package util.dbuke;

import jakarta.transaction.*;
import util.algo.Runnablex;

import javax.transaction.xa.XAResource;
import java.util.ArrayList;
import java.util.List;

public class TrsctImp implements Transaction {

    public List<Runnablex> rollback_list=new ArrayList<>();

    public void setRollbackAct(Runnablex act) {this.rollback_list.add(act);
    }

    @Override
    public void commit() throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SecurityException, IllegalStateException, SystemException {

    }

    @Override
    public void rollback()  {
        for(Runnablex r:rollback_list)
        {
            try {
                r.run();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
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
