package util.tx;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.context.internal.ThreadLocalSessionContext;

import static cfg.AppConfig.sessionFactory;

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

    public static void commitTsact() {
        commitTransaction(sessionFactory.getCurrentSession());
        sessionFactory.getCurrentSession().close();
        ThreadLocalSessionContext.unbind(sessionFactory);
    }

    public static void openSessionBgnTransact() {
        //这里需要新开session。。因为可能复用同一个http线程
        Session session = sessionFactory.openSession();
        // 2. 手动将 Session 绑定到当前线程
        ThreadLocalSessionContext.bind(session);
        System.out.println("thrdid=" + Thread.currentThread());
        System.out.println("openSession=" + session);
        System.out.println("getCurrentSession=" + sessionFactory.getCurrentSession());
        // commitTransactIfActv(session);
        session.beginTransaction();
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

    public static void commitTransactIfActv(Session session){
        Transaction transaction1 = session.getTransaction();
        if(transaction1!=null)
        {
            boolean existingTransaction = transaction1.isActive();
            if(existingTransaction)
                transaction1.commit();
        }
    }

    public static void rollbackTransaction() {
            Transaction transaction = transactionThreadLocal.get();
            if (transaction != null) {
                transaction.rollback();
                transactionThreadLocal.remove();
            }
        }

//        public static void closeSession() {
//            Session session = sessionThreadLocal.get();
//            if (session != null) {
//                session.close();
//                sessionThreadLocal.remove();
//            }
//        }
    }


