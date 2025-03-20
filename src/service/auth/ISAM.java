package service.auth;

import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import service.secury.*;

import java.util.List;

/**
 * sam安全授权模块   stoer in db
 * 包括俩个部分  keymng 和 安全审计日志接口
 */
public interface ISAM extends HttpAuthenticationMechanism, IKeyMngr, IsamLogMng,Alarm, IDPS, SWG, ASB, SASE, DLP {

    default AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) throws AuthenticationException {
        throw  new RuntimeException("not implt");
    }





    ;

}
