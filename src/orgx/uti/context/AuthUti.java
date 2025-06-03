package orgx.uti.context;

import jakarta.security.enterprise.CallerPrincipal;

import static orgx.uti.context.ThreadContext.principal;

public class AuthUti {


    public static void setCurrUname(String uname) {
        CallerPrincipal c = new CallerPrincipal(uname);


        principal.set(c);
    }

    // is user in role("admin"

    /**
     * REST API，建议 每次读取解析 JWT 进行权限验证
     *
     * @param role
     * @return
     */
    public static boolean isUserInRole(String role) {
        String[] rolesFrmJwt = new String[0];  //read thread contxt ,http context ,jwt token
        for (String roleFrmJwt : rolesFrmJwt) {
            if (roleFrmJwt.equals(role)) {
                return true;
            }
        }
        return false;

    }

    ;

    static String getCurrUname() {
        return principal.get().getName();
    }
}
