package util;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class TransactMng {


        private static final ThreadLocal<Transaction> transactionThreadLocal = new ThreadLocal<>();


        public static Transaction beginTransaction(Session session) {
            Transaction transaction = transactionThreadLocal.get();
            if (transaction == null) {
                transaction = session.beginTransaction();
                transactionThreadLocal.set(transaction);
            }
            return transaction;
        }

        public static void commitTransaction() {
            Transaction transaction = transactionThreadLocal.get();
            if (transaction != null) {
                transaction.commit();
                transactionThreadLocal.remove();
            }
        }

        public static void rollbackTransaction() {
            Transaction transaction = transactionThreadLocal.get();
            if (transaction != null) {
                transaction.rollback();
                transactionThreadLocal.remove();
            }
        }

        public static void closeSession() {
            Session session = sessionThreadLocal.get();
            if (session != null) {
                session.close();
                sessionThreadLocal.remove();
            }
        }
    }

}
