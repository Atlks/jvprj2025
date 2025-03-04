package util.auth;

import static biz.Containr.SecurityContext1;

public class AuthUtil {

    public  static  String getCurrentUser( ) {
        return SecurityContext1.getCallerPrincipal() != null ? SecurityContext1.getCallerPrincipal().getName() : "";
    }
}
