package orgx.uti;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.checkerframework.checker.initialization.qual.Initialized;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.UnknownKeyFor;
import org.hibernate.Session;
import org.hibernate.jdbc.ReturningWork;
import orgx.uti.context.ThreadContext;
import orgx.uti.orm.FunctionX;

import java.sql.Connection;

import static orgx.uti.Uti.throwX;
import static orgx.uti.context.ProcessContext.sessionFactory;
import static orgx.uti.context.ThreadContext.*;
import static orgx.uti.context.ThreadContext.currEntityTransaction;

public class TxMng {


    public static void closeConn() {
      try{
          currSession.get().close();
          currEttyMngr.get().close();
      }catch (Exception e) {}

    }

    /**
     * 但这种方式需要 手动管理事务，如果事务失败，开发者必须显式回滚。
     * use   callInTransaction ,runInTx btr
     */
    @Deprecated
    public static void begin() {

        EntityManager em = sessionFactory.createEntityManager();
        ThreadContext.currEttyMngr.set(em);
        //   em.callWithConnection()
        Session session = em.unwrap(Session.class);
        currSession.set(session);
        //  session.doReturningWork()
        // **存储数据**
        EntityTransaction transaction = em.getTransaction();
        currEntityTransaction.set(transaction);
        currEntityTransaction.get().begin();
    }


    public static Object callInTransaction(FunctionX<EntityManager, Object> o) {

        EntityManager em = sessionFactory.createEntityManager();
        ThreadContext.currEttyMngr.set(em);
        Session session = em.unwrap(Session.class);
        currSession.set(session);
        return session.doReturningWork(new ReturningWork<Object>() {

            @Override
            public Object execute(@UnknownKeyFor @NonNull @Initialized Connection connection) {
                try {
                    EntityTransaction transaction = em.getTransaction();
                    transaction.begin();
                    currEntityTransaction.set(transaction);


                    Object rzt = o.apply(null);



                    transaction.commit();
                    return rzt;

                } catch (Throwable e) {
                    // e.printStackTrace();
                    currEntityTransaction.get().rollback();
                    throwX(e);

                } finally {

                }
                return "";
            }
        });

        //  return o.apply(null);
    }

@Deprecated
    public static void commit() {

        currEntityTransaction.get().commit();
    }
    @Deprecated
    public static void rollback() {

        currEntityTransaction.get().rollback();
    }
}
