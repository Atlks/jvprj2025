package orgx.uti.orm;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import orgx.u.User;

import java.util.List;

import static orgx.uti.context.ThreadContext.currEttyMngr;

public class JpaUti {


    public static void persist(Object alice) {
        System.out.println("fun persist");
        currEttyMngr.get().persist(alice);
        currEttyMngr.get().flush();
        System.out.println("endfun persist");

    }

    public static <T> T merge(T entt) {
        System.out.println("fun merge");
        T rzt = currEttyMngr.get().merge(entt);
        currEttyMngr.get().flush();
        System.out.println("endfun merge");
        return rzt;
    }

    public static void remove(Object entt) {
        System.out.println("fun remove");
        currEttyMngr.get().remove(entt);
        currEttyMngr.get().flush();
        System.out.println("endfun remove");
    }

    public static <X> X getSingleResult(String query1, Class<X> rzCls) {
        System.out.println("fun getSingleResult");
        EntityManager em = currEttyMngr.get();
        TypedQuery<X> query = em.createQuery(query1, rzCls);
        X singleResult = query.getSingleResult();
        System.out.println("endfun getSingleResult");
        return singleResult;

    }

    public static <T> List getResultList(String query1, Class<T> cls) {
        System.out.println("fun getResultList");
        EntityManager em = currEttyMngr.get();
        TypedQuery<T> query = em.createQuery(query1, cls);
        List li = query.getResultList();
        System.out.println("endfun getResultList");
        return li;
    }

    public static <T> T find(Class<T> entt, Object id) {
        System.out.println("fun find");
        T t = currEttyMngr.get().find(entt, id); System.out.println("endfun find");
        return t;

    }
}
