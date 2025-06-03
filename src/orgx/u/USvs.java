package orgx.u;

import io.javalin.security.Roles;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import lombok.experimental.UtilityClass;
//import org.springframework.context.support.BeanDefinitionDsl;
import orgx.msc.Dtoo;

import javax.management.relation.RoleList;
import java.util.Map;

import static orgx.uti.Uti.encodeJson;
import static orgx.uti.context.ThreadContext.currEttyMngr;
import static orgx.uti.http.HttpUti.setContentType;
import static orgx.uti.orm.JpaUti.persist;
@UtilityClass
public class USvs {

//    private static <T> Object hdl1(T mp) {
//        return 2;
//    }

    public void add2()
    {
        System.out.println("add2");

    }
    public void add1()
    {
        System.out.println("add1");

    }

    public static void main(String[] args) {
        System.out.println(dynmFun());
    }
    public Object dynmFun()
    {
        System.out.println("dynmFun");
        return null;
    }


    public static Object Hdl1(Dto dto) throws Throwable {
        System.out.println(dto.u);
        return  1;

    }

    @PermitAll
    @jakarta.annotation.security.RolesAllowed({Role.adm,Role.reviewer})
    public static Object auth(Object o) {
        setContentType(MediaType.TEXT_PLAIN + "; charset=UTF-8");

        return "ok";
    }



    //    sv?id=14
    public static Object SvUHdl(User o) {

     //   System.out.println("fun svUHdl");
        persist(o);
        return  o;
    }


    public static Object mapDtoF1(Map o) {
        System.out.println(encodeJson(o));
        return  o;
    }


    @Produces(MediaType.TEXT_PLAIN) // 输出纯文本

    public static Object TxtHdl(Object o) {
        setContentType(MediaType.TEXT_PLAIN + "; charset=UTF-8");

        return "ok";
    }

    public static Object add2(Dtoo o) {
        System.out.println("add2");
        return  o;
    }
    public static Object add1(Dtoo o) {
        System.out.println("add1");
        return  "sss";
    }


}
