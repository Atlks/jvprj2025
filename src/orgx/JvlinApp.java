package orgx;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.checkerframework.checker.initialization.qual.Initialized;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.UnknownKeyFor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.ReturningWork;
import org.jetbrains.annotations.NotNull;
import orgx.u.USvs;
import orgx.uti.TxMng;
import orgx.uti.orm.FunctionX;
import orgx.uti.context.ThreadContext;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import static orgx.CfgSvs.getSessionFactory;
import static orgx.uti.TxMng.callInTransaction;
import static orgx.uti.Uti.*;
import static orgx.uti.context.ProcessContext.sessionFactory;
import static orgx.uti.context.ThreadContext.*;


public class JvlinApp {
    static ThreadLocal<Javalin> appJvl = new ThreadLocal<>();

    public static void main(String[] args) {
        //ini conn,cfg
        SessionFactory sessionFactory = getSessionFactory();

        Javalin app = Javalin.create().start(7070);
        appJvl.set(app);
        app.get("/", ctx -> ctx.result("Hello, Javalin!"));
        app.get("/a", ctx -> ctx.result("Hello, Javalin!"));

        setHttpGetHdlTry("/b", USvs::Hdl1);
        setHttpGetHdlTry("/sv", USvs::SvUHdl);

        app.post("/pst", ctx -> {
            //javalin 如何接受post参数 name？？
            String name = ctx.formParam("name"); // 获取 POST 参数 "name"
            System.out.println("get pst prm,name=" + name);
            ctx.result("Hello, Javalin!" + name);
        });
    }

    /**
     * @param path
     * @param fun
     * @param <T>
     */
    private static <T> void setHttpGetHdlTry(String path, FunctionX<T, Object> fun) {
        //get raw fun(dto    name,type .to aop log

        Javalin app = appJvl.get();
        Handler handler = ctx -> {
            Object rzt = null;
            //  begin();
            try {
                rzt=  setHttpGetHdl(path, fun, ctx);
            } catch (Throwable e) {
                System.out.println("---catch by httpgetHdl");
                e.printStackTrace();
                System.out.println("---end catch by httpgetHdl");
                rzt = e;
            } finally {
                //cls http conn
                TxMng. closeConn();
            }
          //  ctx.res.setCharacterEncoding("UTF-8");
            ctx.contentType("text/plain; charset=UTF-8");
            ctx.result("Hello, Javalin! rzt=" + rzt);
        };

        app.get(path, handler);
    }



    //            String query1 = "SELECT u FROM User u";
//            Class cls= User.class;
//            List<User> li=  getResultList(query1,cls);
//            System.out.println(encodeJson(li));
//


    private static <T> Object setHttpGetHdl(String path, FunctionX<T, Object> fun, Context  ctx)
    {
        Class lambdaMethodParamFirstType = fun.getLambdaMethodParamFirstType();
        System.out.println("fun setHttpGetHdl(p=" + path + ",fun=" + fun.getLambdaMethodName() + "<" + lambdaMethodParamFirstType.getName() + ">");
        Context  ct1=(Context)ctx;
        Map mp = ct1.queryParamMap();
        //auto tx ,commit n  roolback
        return callInTransaction(em -> {
            T dto = (T) toDto(mp, lambdaMethodParamFirstType);
            valdt(dto);
            System.out.println("fun "+ fun.getLambdaMethodName()+"("+encodeJson(dto));
            Object apply = fun.apply(dto);
            System.out.println("endfun "+fun.getLambdaMethodName());
            return apply;
        });
    }


    //            try {
//                T dto = (T) toDto(mp, lambdaMethodParamFirstType);
//                valdt(dto);
//                rzt = fun.apply(dto);
//                commit();
//            } catch (Throwable e) {
//
//                rollback();
//                e.printStackTrace();
//            }finally {
//                closeConn();
//            }


}
