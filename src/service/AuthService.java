package service;

import biz.BaseHdr;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.net.URI;
import java.util.Set;

import static util.Util2025.encodeJsonObj;
import static util.Util2025.toExchgDt;
import static util.util2026.getcookie;

public class AuthService {
    public static final Set<String> NO_AUTH_PATHS = Set.of("/reg", "/login");

    /**
     * 判断是否需要登录的url    。。格式为  /reg
     *
     * @param requestURI
     * @return * @return `true` 需要登录，`false` 不需要登录
     */
    public static boolean needLoginAuth(URI requestURI) {
        System.out.println("fun nededLogAuth(uri="+requestURI.getPath());
       String path = requestURI.getPath(); // 只取路径部分，不包括查询参数
        boolean b = !NO_AUTH_PATHS.contains(path);
        System.out.println("endfun needLoginAuth().ret="+b);
        return b;
    }

    public  static boolean isLogined(HttpExchange exchange) throws IOException {
        System.out.println("fun isLogined(httpExch="+encodeJsonObj(toExchgDt(exchange)));
        String uname = getcookie("uname", exchange);
        boolean b = !uname.equals("");
        System.out.println("endfun isLogined().ret="+b);
        return b;
        //   return  true;
    }

    public static  boolean isNotLogined(HttpExchange exchange) throws IOException {
        System.out.println("fun isNotLogined(httpExch="+encodeJsonObj(toExchgDt(exchange)));
        String uname = getcookie("uname", exchange);

        boolean b = uname.equals("");
        System.out.println("endfun isLogined().ret="+b);
        return uname.equals("");
        //   return  true;
    }
}
