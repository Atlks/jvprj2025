package orgx.rest;

import com.sun.net.httpserver.Authenticator;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;

import orgx.uti.context.ProcessContext;

import java.lang.annotation.Annotation;
import java.nio.file.AccessDeniedException;
import java.rmi.AccessException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static orgx.uti.Uti.*;
// static orgx.uti.http.HttpUti.hasPermission;

public class JwtAuthenticator extends Authenticator {


    public Result authenticateX(HttpExchange exchange)  throws Exception{
        chkAuth(  exchange);
        return new Success(new HttpPrincipal("user", "realm"));
    };




    private void chkAuth(HttpExchange exchange) throws AccessDeniedException, AccessException {

        System.out.println("fun chkAuth");
        //auth chk,
        String path = exchange.getRequestURI().getPath();
        // ProcessContext.authMap.get(path);
        Annotation[] rolesNeed = getVFrmMpArr(ProcessContext.authMap, path, EmptyAnnotations());

        String token = "";

        chkHasPermission(path, rolesNeed, token);
        System.out.println("endfun chkAuth");
    }

    public static void chkHasPermission(String path, Annotation[] rolesNeed, String token) throws AccessDeniedException, AccessException {


        List<Annotation> list = Arrays.asList(rolesNeed);
        if (list.contains(PermitAll.class))
            return;
        List<String> myrols = getMyrolesFromJwt(token);
        for (Annotation annotation : rolesNeed) {
            if (annotation.annotationType().equals(PermitAll.class)) {
                break;
            }
            if (annotation.annotationType().equals(RolesAllowed.class)) {
                String[] needRoles = ((RolesAllowed) annotation).value();

                chkHasPermission(needRoles, myrols);
                //all acc
            }

        }
    }

    private static List<String> getMyrolesFromJwt(String token) {
        List<String> li = new ArrayList<>();
        li.add("troorist");
        return li;
    }

    private static void chkHasPermission(String[] needRoles, List<String> myrols) throws AccessDeniedException, AccessException {
        for (String needRole : needRoles) {
            for (String myrole : myrols) {
                if (myrole.equals(needRole)) {
                    return;
                }
            }

        }

        //NotAuthorizedException
        throw new AccessException("need roles=" + encodeJson(needRoles) + ",myroles=" + myrols);

    }


    @Override
    public Result authenticate(HttpExchange exchange) {
        System.out.println("fun authenticate()");
        String authHeader = exchange.getRequestHeaders().getFirst("Authorization");
        if (authHeader != null && authHeader.equals("Bearer my-secret-token")) {
            //parser jwt  ,,set thread context
            //认证域（realm）：
            //✅ getUsername() → 获取用户名 ✅ getRealm() → 获取认证域 ✅ getName() → 返回 realm:username 格式的身份信息
            return new Success(new HttpPrincipal("user", "realm"));
        }




        return new Failure(403); // 认证失败
    }
}

