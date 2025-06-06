package util.tx;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.context.internal.ThreadLocalSessionContext;

import static cfg.Containr.sessionFactory;
import static orgx.uti.context.ThreadContext.currSession;
import static orgx.uti.context.ThreadContext.currTxHbnt;


/**
 * 事务管理器
 */
public class TransactMng {


    public static final ThreadLocal<Transaction> transactionThreadLocal = new ThreadLocal<>();


        public static Transaction beginTransaction(Session session) {
            boolean existingTransaction = session.getTransaction().isActive();
            if(existingTransaction)
                return session.getTransaction();

            //
            Transaction transaction = transactionThreadLocal.get();
            if (transaction == null) {
                transaction = session.beginTransaction();
                transactionThreadLocal.set(transaction);
            }
          //  boolean existingTransaction = session.getTransaction().isActive();
            return transaction;
        }

    /**
     *
     */
    public static void commitx() {
        System.out.println("fun commitx");
        currSession.get().flush();
        currTxHbnt.get().commit();
       // commitTransaction(sessionFactory.getCurrentSession());
        sessionFactory.getCurrentSession().close();
        ThreadLocalSessionContext.unbind(sessionFactory);

        System.out.println("endfun commitx");
    }


    /**
     * openSessionBgnTransact
     * todo here by maybe ing trsct..so maybe dont need bgn ts if sometime
     */
    public static void beginx() {
        System.out.println("fun beginx");
        //这里需要新开session。。因为可能复用同一个http线程
        Session session = sessionFactory.openSession();
        currSession.set(session);
        // 2. 手动将 Session 绑定到当前线程
        ThreadLocalSessionContext.bind(session);
        System.out.println("thrdid=" + Thread.currentThread());
        System.out.println("openSession=" + session);
        System.out.println("getCurrentSession=" + sessionFactory.getCurrentSession());
        // commitTransactIfActv(session);
        Transaction tx =  session.beginTransaction();
        transactionThreadLocal.set(tx);
        currTxHbnt.set(tx);
    }
        public static void commitTransaction(Session session) {
            Transaction transaction = transactionThreadLocal.get();

            if (transaction != null) {
                transaction.commit();
                transactionThreadLocal.remove();
            }


            Transaction transaction1 = session.getTransaction();
            if(transaction1!=null)
            {
                boolean existingTransaction = transaction1.isActive();
                if(existingTransaction)
                    transaction1.commit();
            }

        }

//    public static void commitTransactIfActv(Session session){
//        Transaction transaction1 = session.getTransaction();
//        if(transaction1!=null)
//        {
//            boolean existingTransaction = transaction1.isActive();
//            if(existingTransaction)
//                transaction1.commit();
//        }
//    }

    public static void rollbackTx() {
        System.out.println("fun rollbackTx");
            Transaction transaction = transactionThreadLocal.get();
            if (transaction != null) {
                transaction.rollback();
                transactionThreadLocal.remove();
            }

        System.out.println("endfun rollbackTx");
        }

//        public static void closeSession() {
//            Session session = sessionThreadLocal.get();
//            if (session != null) {
//                session.close();
//                sessionThreadLocal.remove();
//            }
//        }
    }


