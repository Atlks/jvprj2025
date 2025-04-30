package util.dbuke;

import jakarta.transaction.*;
import util.algo.Runnablex;

import javax.transaction.xa.XAResource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static util.algo.CopyUti.copyFile;
import static util.algo.CopyUti.moveFile;
import static util.oo.FileUti.delFile;

public class TrsctImp implements Transaction {
    private   String saveDir1bkTx;
    String saveDir;
    private   ConnSession conn;
    public List<Runnablex> rollback_list=new ArrayList<>();

    public TrsctImp(ConnSession connSession) {
        this.conn=connSession;
        this.saveDir=connSession.saveDir;
        this.saveDir1bkTx=connSession.saveDir1bkTx;
    }

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


    public void setTxRollback(String data_id, String collName) throws Exception {

        if(conn.autoCommit==false)
            return;
        String data_loc=collName+"/"+data_id;
        if (existDataInDB(data_loc)) {
            backOlddata(data_loc);
        }
        setRollbackAct(() -> {
            delNewData(data_loc);

            if (existOlddataInTxbekArea(data_loc)) {
                restoreOlddata(data_loc);
            }
        });
    }

    private void delNewData(String data_loc) {
        System.out.println( "fun delNewData(dtlc="+data_loc);

        String delf=saveDir+"/"+data_loc ;
        delFile(delf);

    }


    private boolean existDataInDB(String data_loc) {

        System.out.println( "fun existDataInDB(dtlc="+data_loc);
        File file = new File(saveDir+"/"+ data_loc);
        return (file.exists());
    }

    private void backOlddata(String data_loc) throws Exception {
        System.out.println( "fun backOlddata(dtlc="+data_loc);
        copyFile(saveDir+"/"+ data_loc, saveDir1bkTx+"/"+ data_loc);
    }

    private void restoreOlddata(String data_loc) throws IOException {
        System.out.println( "fun restoreOlddata(dtlc="+data_loc);
        String old=saveDir1bkTx+"/"+data_loc;
        String newf=saveDir+"/"+data_loc;
        moveFile(old, newf);
    }


    private boolean existOlddataInTxbekArea(String data_loc) {
        System.out.println( "fun existOlddataInTxbekArea(dtlc="+data_loc);
        String collDir=saveDir1bkTx+"/"+data_loc;
        return new File(collDir ).exists();
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
