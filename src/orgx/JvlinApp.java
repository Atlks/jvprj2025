package orgx;

import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.ExceptionHandler;
import io.javalin.http.Handler;
import org.hibernate.SessionFactory;
import orgx.u.USvs;
import orgx.uti.context.ProcessContext;
import orgx.uti.orm.TxMng;

import static orgx.CfgSvs.getSessionFactory;
import static orgx.uti.http.RegMap.registerMapping;


public class JvlinApp {


    public static void main(String[] args) {
        //ini conn,cfg
        SessionFactory sessionFactory = getSessionFactory();

        Javalin app = Javalin.create((JavalinConfig config) -> {
            // config.http.defaultContentType = "application/json; charset=UTF-8";
            config.router.ignoreTrailingSlashes = true;


        }).start(7070);

        ProcessContext.appJvl = (app);
        // Javalin.before()
        Handler handlerBefore = ctx -> {
            //适用于 日志记录、身份验证、请求预处理。
            System.out.println("\n\n\n请求前拦截：" + ctx.path());
            /**
             * 请求前拦截：/sv
             * fun startHttpContextProcs  .....
             */
        };
        app.before(handlerBefore);
//        app.config.accessManager((handler, ctx, permittedRoles) -> {
//            if (!ctx.header("Authorization").equals("valid-token")) {
//                ctx.status(403).result("无权限访问");
//                return;
//            }
//            handler.handle(ctx);
//        });

       // app.
        // 注册全局异常处理器 jvlin
        ExceptionHandler<Exception> exceptionExceptionHandler = (e, ctx) -> {

            System.out.println("---catch by httpgetHdl");
            System.out.flush();
            e.printStackTrace();
            System.err.flush();
            try {
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                // throw new RuntimeException(ex);
            }
            System.out.println("---end catch by httpgetHdl");
            Object rzt = e;

            //cls http conn
            TxMng.closeConn();
            ctx.status(500).result("服务器内部错误：" + e.getMessage());
        };
        app.exception(Exception.class, exceptionExceptionHandler);

        // 示例路由
        app.get("/error", ctx -> {
            throw new RuntimeException("测试异常");
        });
        app.get("/", ctx -> ctx.result("Hello, Javalin!"));
        app.get("/a", ctx -> ctx.result("Hello, Javalin!"));

        registerMapping("/b", USvs::Hdl1);
        registerMapping("/sv", USvs::SvUHdl);
        registerMapping("/exit", Sys::exit);
        registerMapping("/exit", Sys::exit);
        registerMapping ("/mp", USvs::mapDtoF1);
        //swtch fun,,just here swt cfg is ok

        app.post("/pst", ctx -> {
            //javalin 如何接受post参数 name？？
            String name = ctx.formParam("name"); // 获取 POST 参数 "name"
            System.out.println("get pst prm,name=" + name);
            ctx.result("Hello, Javalin!" + name);
        });
    }


    //            String query1 = "SELECT u FROM User u";
//            Class cls= User.class;
//            List<User> li=  getResultList(query1,cls);
//            System.out.println(encodeJson(li));
//




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
