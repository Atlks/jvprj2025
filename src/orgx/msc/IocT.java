package orgx.msc;

import orgx.u.USvs;
import orgx.uti.context.ProcessContext;
import orgx.uti.orm.FunctionX;

import java.util.Map;

import static orgx.uti.context.ProcessContext.*;

public class IocT {

    public static void main(String[] args) throws Throwable {
      //  funMap4httpHdlr.put(FunType.USER_add, USvs:: add1);

        ProcessContext.fun_userAdd=USvs:: add2;
        ProcessContext.fun_userAdd=USvs:: add1;

        call(fun_userAdd,null);


//        FunctionX func = getFun( FunType.USER_add);
//        func.apply(Map.of("k",111));
      //   funMap.get( userAdd).apply(11);
    }

    private static void call(FunctionX  funUserAdd, Object o) throws Throwable {
        fun_userAdd.apply(null);

    }


}
