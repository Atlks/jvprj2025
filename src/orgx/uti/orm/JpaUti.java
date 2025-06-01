package orgx.uti.orm;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import orgx.u.User;

import java.util.List;

import static orgx.uti.context.ThreadContext.currEttyMngr;

public class JpaUti {


    public static void persist(Object alice) {

        currEttyMngr.get().persist(alice);
    }

    public static   <T> T merge(T entt) {

     return   currEttyMngr.get().merge(entt);
    }

    public static void remove(Object entt) {
        currEttyMngr.get().remove(entt);
    }
    public static  < X>   X getSingleResult(String query1, Class<X> rzCls) {
        EntityManager em=currEttyMngr.get();
        TypedQuery<X> query = em.createQuery(query1,rzCls );
        return query.getSingleResult();

    }
    public static <T> List getResultList(String query1, Class<T> cls) {
        EntityManager em=currEttyMngr.get();
        TypedQuery<T> query = em.createQuery(query1,cls );
        List li= query.getResultList();
        return li;
    }
    public static  <T> T find(Class<T> entt, Object id) {
        return currEttyMngr.get().find(entt, id);
    }
}
