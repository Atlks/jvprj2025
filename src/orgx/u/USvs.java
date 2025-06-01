package orgx.u;

import static orgx.uti.context.ThreadContext.currEttyMngr;
import static orgx.uti.orm.JpaUti.persist;

public class USvs {

//    private static <T> Object hdl1(T mp) {
//        return 2;
//    }


    public static Object Hdl1(Dto dto) throws Throwable {
        System.out.println(dto.u);
        return  1;

    }

    //    sv?id=14
    public static Object SvUHdl(User o) {

     //   System.out.println("fun svUHdl");
        persist(o);
        return  o;
    }


}
