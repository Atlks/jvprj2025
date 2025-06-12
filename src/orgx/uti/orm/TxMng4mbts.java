package orgx.uti.orm;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.apache.ibatis.session.SqlSession;
import org.checkerframework.checker.initialization.qual.Initialized;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.UnknownKeyFor;
import org.hibernate.Session;
import org.hibernate.jdbc.ReturningWork;
import orgx.uti.context.FunContext;
import orgx.uti.context.ProcessContext;
import orgx.uti.context.ThreadContext;

import java.sql.Connection;
import java.sql.SQLException;

import static orgx.uti.Uti.throwX;
import static orgx.uti.context.ProcessContext.sessionFactory;
import static orgx.uti.context.ThreadContext.*;

public class TxMng4mbts {





    public static <T> T callInTransaction(orgx.uti.fun.FunctionX<SqlSession, T> fx) throws SQLException {
        System.out.println("runInTransaction");
        SqlSession em = ProcessContext.sqlSessionFactory.openSession(false);
        ThreadContext.sqlSessionThreadLocal.set(em);
        System.out.println("AutoCommit 状态: " + em.getConnection()
                .getAutoCommit());
        //AutoCommit 状态: false
        try {

            System.out.println("begin");
            //  currEntityTransaction.set(transaction);

            FunContext funCtx = new FunContext();
            funCtx.sqlSession = em;
            Object rzt = fx.apply(em, funCtx);


            em.commit();
            System.out.println("commit");
            System.out.println("endfun runInTransaction");
            return (T) rzt;

        } catch (Throwable e) {
            em.rollback();
            System.out.println("rollback");
            throwX(e);

        } finally {
            //cls http conn
            TxMng4mbts.closeConn();
        }




        System.out.println("endfun runInTransaction");
        //  return o1;
        return null;
        //  return o.apply(null);
    }


    public static void closeConn() {
        try {
            ThreadContext.sqlSessionThreadLocal.get().close();
        } catch (Exception e) {
        }

    }

    /**
     * 但这种方式需要 手动管理事务，如果事务失败，开发者必须显式回滚。
     * use   callInTransaction ,runInTx btr
     */
//    @Deprecated
//    public static void begin() {
//
//
//    }


//    public static Object callInTransaction(FunctionX<EntityManager, Object> o) {
//        System.out.println("callInTransaction");
//        EntityManager em = sessionFactory.createEntityManager();
//        ThreadContext.currEttyMngr.set(em);
//        Session session = em.unwrap(Session.class);
//        currSession.set(session);
//        final EntityTransaction[] transaction = new EntityTransaction[1];
//        ReturningWork<Object> work = new ReturningWork<>() {
//
//            @Override
//            public Object execute(@UnknownKeyFor @NonNull @Initialized Connection connection) {
//                try {
//                    transaction[0] = em.getTransaction();
//                    transaction[0].begin();
//                    System.out.println("begin");
//                    //  currEntityTransaction.set(transaction);
//
//
//                    Object rzt = o.apply(em);
//
//
//                    transaction[0].commit();
//                    System.out.println("commit");
//                    return rzt;
//
//                } catch (Throwable e) {
//                    // e.printStackTrace();
//                    transaction[0].rollback();
//                    System.out.println("rollback");
//                    throwX(e);
//
//                } finally {
//                    //cls http conn
//                    TxMng4mbts.closeConn();
//                }
//                return "";
//            }
//        };
//        Object o1 = session.doReturningWork(work);
//        System.out.println("endfun callInTransaction");
//        return o1;
//
//        //  return o.apply(null);
//    }
//
//    @Deprecated
//    public static void commit() {
//
//        currEntityTransaction.get().commit();
//    }
//
//    @Deprecated
//    public static void rollback() {
//
//        currEntityTransaction.get().rollback();
//    }
}
