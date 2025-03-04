package util.auth;

import com.sun.net.httpserver.HttpExchange;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;

import static util.AtProxy4api.httpExchangeCurThrd;
import static util.JwtUtil.*;
import static util.util2026.chkCantBeEmpty;

//这里实际是存储 读取cookie的地方

/**
 * HttpAuthenticationMechanism 通常会调用 IdentityStore 来验证凭据。
 * 简单来说，HttpAuthenticationMechanism 像是一个“门卫”，负责接收用户的凭据，而 IdentityStore 像是一个“验证员”，负责检查这些凭据是否有效。
 */
@ApplicationScoped
public class ChkLgnStatAuthenticationMechanism implements HttpAuthenticationMechanism, IdentityStore {

    //@Inject
    //  private AuthenticationService authenticationService;
    // @Inject

//    @Qualifier("chkLoginStatIdentityStore")
//    public IdentityStore IdentityStore1;


    /**
     * 大部分情况下，你应该返回 SEND_FAILURE 处理认证失败，而 AuthenticationException 主要用于不可恢复的错误。   比如从存储读取验证数据出错
     *
     * @param request            contains the request the client has made
     * @param response           contains the response that will be send to the client
     * @param httpMessageContext context for interacting with the container
     * @return
     * @throws AuthenticationException
     */
    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) throws AuthenticationException {
        String uname = "";

        HttpExchange httpExchange = httpExchangeCurThrd.get();
        String mode = getAuthChkMode(httpExchange);
        if (mode.equals("cookie")) {
            return new ChkLgnStatAuthMchsmCkMod().validateRequest(null, null, null);
        }
        //def jwt  is empty or jwt
        return new chkLgnStatAuthMchsmJwtMod().validateRequest(null, null, null);


    }

    /**
     * 用户名密码验证  IdentityStore接口
     * 步骤  findById , jude pwd eq
     * @return
     */
    @Override
    public CredentialValidationResult validate(@NotNull Credential credential2) {


        try {
            UsernamePasswordCredential credential = (UsernamePasswordCredential) credential2;
            // 示例身份验证逻辑
            @NotNull
            String caller = credential.getCaller();
            chkCantBeEmpty("VarName.uname",caller);
            return new CredentialValidationResult(caller, java.util.Set.of("USER"));
        } catch (IsEmptyEx e) {
            throw new AuthenticationExceptionRuntimeException(e);
        }

    }

    //  System.out.println(jakarta.security.enterprise.authentication.mechanism.http.);
//        boolean authenticate = authenticationService.authenticate(request, response);
    //  return  AuthenticationStatus.NOT_DONE;
    //    throw  new AuthenticationException("需要登录");
}