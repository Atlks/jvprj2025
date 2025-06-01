package orgx;

import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import orgx.u.User;
import orgx.uti.orm.JPAQueryX;
import orgx.uti.enttMngrs.IniEnttMngr;

import java.math.BigDecimal;
import java.util.List;

import static orgx.uti.orm.JPAQueryX.*;

public class JpaQryT {

    public static void main(String[] args) {


        EntityManager entityManager = null;
        User user = new User();
        JPAQuery qr;
        EntityManager enttMngr = new IniEnttMngr();
        JPAQueryX qry = new JPAQueryX(enttMngr)
                //not need select flds ,only 4 sql
                //  .select("id,name")
                //  .select("a", fld(JpaQryT::castx, "b"))
                .from(User.class).where(eq("a", 1))
                .and(eq(("b"), 2))
                // .select(sum("age"),count("id"))   //ok
                .select(sum("age"))    //ok

                ;
        System.out.println(qry.getSingleResultTuple());
        System.out.println(qry.getSingleResultTuple().get(0));

        BigDecimal sum = (BigDecimal) qry.getSingleResult();
        System.out.println(sum);
        List<User> users = (List<User>) qry.fetch();
        System.out.println(users);
        System.out.println(qry.toDsl());
    }


    private static Object castx(String fld) {
        return fld.toLowerCase();
    }


}
