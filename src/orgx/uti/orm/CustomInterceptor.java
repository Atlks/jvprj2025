package orgx.uti.orm;

import org.hibernate.CallbackException;
import org.hibernate.Interceptor;
import org.hibernate.type.Type;
import java.io.Serializable;

import static orgx.uti.Uti.encodeJson;

public class CustomInterceptor implements Interceptor {


    // 为什么  merge也执行了,if merge_new ,,,so psst
    @Override
    public boolean onPersist(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types) {
        System.out.println("实体即将保存: " + entity);
        System.out.println("fun psst("+entity.getClass().getName()+"): id=" + id);
        System.out.println("fun psst(id="+id+",o="+encodeJson(entity));
        return false; // 返回 false 继续执行默认行为
    }



    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        System.out.println("实体即将保存onsave: " + entity);
        return false; // 返回 false 继续执行默认行为
    }

    //merge_upddt
    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        System.out.println("实体更新: " + entity);
        System.out.println("fun psst("+entity.getClass().getName()+"): id=" + id);
        System.out.println("fun psst(id="+id+",o="+encodeJson(entity));
        return false;
    }
}
