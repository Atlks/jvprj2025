


✅ 方法 2：使用 ThreadLocal 全局事务
如果不想手动传递 Session，可以使用 ThreadLocal 来让同一个线程中的 Service A 和 Service B 共享事务。


public static Transaction beginTransaction() {
Transaction transaction = transactionThreadLocal.get();
if (transaction == null) {
transaction = getSession().beginTransaction();
transactionThreadLocal.set(transaction);
}
return transaction;
}



public class ServiceB {
public void methodB() {
Session session = HibernateUtil.getSession();  // 自动获取 Service A 的 Session
Transaction transaction = HibernateUtil.beginTransaction();  // 复用 Service A 的事务



方法 2：使用 ThreadLocal 全局事务	所有 Service 自动获取同一事务	更简单	✅ Service A 和 Service B 自动共享事务


==检测 当前是否已有事务，
TransactionSynchronizationManager
如果你一定要用拦截器（Interceptor）或者 @Transactional，可以检测 当前是否已有事务，避免重复提交：

java
复制
编辑
public void invokeMethodSafely(String methodName, Object target) {
Session session = sessionFactory.getCurrentSession();
boolean existingTransaction = session.getTransaction().isActive();

    Transaction tx = null;
    if (!existingTransaction) {
        tx = session.beginTransaction();
    }

==Service A 负责事务，Service B 复用 sessionFactory.getCurrentSession()（🔥 最简单）。