

# ex  auth use glob fltr mode 



// 注册全局异常处理器 jvlin
app.exception(Exception.class, (e, ctx) -> {

            System.out.println("---catch by httpgetHdl");
            e.printStackTrace();
            System.out.println("---end catch by httpgetHdl");
            Object rzt = e;

            //cls http conn
            TxMng.closeConn();
            ctx.status(500).result("服务器内部错误：" + e.getMessage());
        });


# router use static mthod

       setHttpGetHdlTry("/sv", USvs::SvUHdl);



    public static Object Hdl1(Dto dto) throws Throwable {
        System.out.println(dto.u);
        return  1;

    }

    //  http://localhost:7070/sv?id=164&name=nxx
    //svhdl(dto u)
    public static Object SvUHdl(User o) {

        System.out.println("fun svUHdl");
        persist(o);
        return  o;
    }

      */
    private static <T> void setHttpGetHdl(String path, FunctionX<T, Object> fun) {
        //get raw fun(dto    name,type .to aop log

        Javalin app = appJvl.get();
        Handler handler = ctx -> {
            Object rzt = null;
            System.out.println("fun setHttpGetHdl(p="+path+",fun="+fun+")");
            setThrdHttpContext(ctx);
            Class lambdaMethodParamFirstType = fun.getLambdaMethodParamFirstType();
            System.out.println("fun setHttpGetHdl(p=" + path + ",fun=" + fun.getLambdaMethodName() + "<" + lambdaMethodParamFirstType.getName() + ">");
            Context ct1 = (Context) ctx;
            Map mp = ct1.queryParamMap();
            //auto tx ,commit n  roolback
            rzt = callInTransaction(em -> {
                T dto = (T) toDto(mp, lambdaMethodParamFirstType);
                valdt(dto);
                System.out.println("fun " + fun.getLambdaMethodName() + "(" + encodeJson(dto));
                Object apply = fun.apply(dto);
                System.out.println("endfun " + fun.getLambdaMethodName());
                return apply;
            });


            //  ctx.res.setCharacterEncoding("UTF-8");
            ctx.contentType("text/plain; charset=UTF-8");
            ctx.result("Hello, Javalin! rzt=" + rzt);
            System.out.println("endfun setHttpGetHdl");
        };

        app.get(path, handler);
    }
