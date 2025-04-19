package util.auth;

import static cfg.Containr.SecurityContext1;
import static util.serverless.RequestHandler.request_getHeaders;

public class AuthUtil {

    /**
     * jdk mode ,starnd
     * @return
     */
    public  static  String getCurrentUser( ) {
        return SecurityContext1.getCallerPrincipal() != null ? SecurityContext1.getCallerPrincipal().getName() : "";
    }


    /**
     * azure mode ,more easy btr than aws
     * @param k
     * @return
     */
    public static String request_getHeaders_get(String k) {
        return (String) request_getHeaders.get().get(k);
    }
}
