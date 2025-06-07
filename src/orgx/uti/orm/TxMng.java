package orgx.uti.orm;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.checkerframework.checker.initialization.qual.Initialized;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.UnknownKeyFor;
import org.hibernate.Session;
import org.hibernate.jdbc.ReturningWork;
import orgx.uti.context.ThreadContext;

import java.sql.Connection;

import static orgx.uti.Uti.throwX;
import static orgx.uti.context.ProcessContext.sessionFactory;
import static orgx.uti.context.ThreadContext.*;
import static orgx.uti.context.ThreadContext.currEntityTransaction;

public class TxMng {


    public static void closeConn() {
      try{
          currSession.get().close();

      }catch (Exception e) {}
      try{

          currEttyMngr.get().close();
      } catch (Exception e) {

      }

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
        System.out.println("callInTransaction");
        EntityManager em = sessionFactory.createEntityManager();
        ThreadContext.currEttyMngr.set(em);
        Session session = em.unwrap(Session.class);
        currSession.set(session);
        ReturningWork<Object> work = new ReturningWork<>() {

            @Override
            public Object execute(@UnknownKeyFor @NonNull @Initialized Connection connection) {
                try {
                    EntityTransaction transaction = em.getTransaction();
                    transaction.begin();
                    System.out.println("begin");
                    currEntityTransaction.set(transaction);


                    Object rzt = o.apply(currEttyMngr.get());


                    transaction.commit();
                    System.out.println("commit");
                    return rzt;

                } catch (Throwable e) {
                    // e.printStackTrace();
                    currEntityTransaction.get().rollback();
                    System.out.println("rollback");
                    throwX(e);

                } finally {
                    //cls http conn
                    TxMng.closeConn();
                }
                return "";
            }
        };
        Object o1 = session.doReturningWork(work);
        System.out.println("endfun callInTransaction");
        return o1;

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
