package service.auth;

import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.secury.*;

/**
 * sam安全授权模块   stoer in db
 * 包括俩个部分  keymng 和 安全审计日志接口
 */
public interface ISAM extends HttpAuthenticationMechanism,IKeyMngr,Alarm, IDPS, SWG, ASB, SASE, DLP {

    default AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) throws AuthenticationException {
        return AuthenticationStatus.SUCCESS;
    }

    /**
     *    oridat,,,pwd  fgrprint ,faceid
     * @param oriData
     * @return
     */
    String geneKey(String oriData);
    public void storeKey(String uid, String oriKeyData);

    CredentialValidationResult validate(Credential credential);
    public void addLogVldFail(String uid, Throwable e);
    public void addLogVldSucess(String uid);
}
