package orgx.uti.orm;

import org.hibernate.CallbackException;
import org.hibernate.Interceptor;
import org.hibernate.Transaction;
import org.hibernate.type.Type;
import java.io.Serializable;

import static orgx.uti.Uti.encodeJson;

/**
 * Interceptor 拦截保存实体后的事件
 * 在 Hibernate 中，如果你想要拦截 实体保存后（persist、update 或 merge 操作完成后）的事件，可以使用 Interceptor 机制。不过，Hibernate 不直接提供 onPersistSuccess 这样的事件，你需要使用 onFlush() 或 afterTransactionCompletion() 来捕获 事务提交后 的行为
 * ✅ onFlush(Transaction tx)：当数据被刷新到数据库时执行（但事务可能还未提交）。 ✅ afterTransactionCompletion(Transaction tx)：当事务真正提交后触发（确保数据已存入数据库）。
 */
public class CustomInterceptor implements Interceptor {


    //this bef save
    // 为什么  merge也执行了,if merge_new ,,,so psst
    @Override
    public boolean onPersist(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types) {
        System.out.println("实体即将保存: " + entity);
        System.out.println("fun psst("+entity.getClass().getName()+"): id=" + id);
        System.out.println("fun psst(id="+id+",o="+encodeJson(entity));
        return false; // 返回 false 继续执行默认行为
    }

    /**
     * 当数据被刷新到数据库时执行（但事务可能还未提交）。
     * @param tx
     */
//    @Override
//    public void onFlush(Transaction tx) {
//        System.out.println("实体已刷新到数据库！事务 ID: " + tx);
//    }

    @Override
    public void afterTransactionCompletion(Transaction tx) {
        System.out.println("事务提交成功，数据已持久化！");
    }


    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        System.out.println("实体即将保存onsave: " + entity);
        return false; // 返回 false 继续执行默认行为
    }

    /**
     * 当数据被刷新到数据库时执行（但事务可能还未提交）。
     * after save evt
     * @param entity
     * @param id
     * @param currentState
     * @param previousState
     * @param propertyNames
     * @param types
     * @return
     */
    //merge_upddt
    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        System.out.println("实体更新: " + entity);
        System.out.println("fun onFlushDirty("+entity.getClass().getName()+"): id=" + id);
        System.out.println("fun onFlushDirty(id="+id+",o="+encodeJson(entity));
        return false;
    }
}
