package orgx.uti;

import java.lang.reflect.InvocationTargetException;

import static orgx.uti.Uti.encodeJson;

public class LogUti {


    public static void prtEndfun(String fname) {
        System.out.println("endfun " + fname);
    }

    public static void prtFun(String fname, Object dto) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        System.out.println("fun " + fname + "(dto=" + encodeJson(dto) + ")"); ;


    }


    public static void prtFun(String fname ) {
        System.out.println("fun " + fname + "()");
    }
}
