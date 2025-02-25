package util;

import org.hibernate.Session;
import org.hibernate.Transaction;

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


