package cfg;

import cfg.AuthenticationService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//这里实际是存储 读取cookie的地方
@ApplicationScoped
public class CookieAuthenticationMechanism implements HttpAuthenticationMechanism {

    @Inject
    private AuthenticationService authenticationService;

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) throws AuthenticationException {
        boolean authenticate = authenticationService.authenticate(request, response);
        if (authenticate) {
            return httpMessageContext.notifyContainerAboutLogin("user", null); // 成功登录
        }
        return httpMessageContext.doNothing(); // 未登录或认证失败
    }
}