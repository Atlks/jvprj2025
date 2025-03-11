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
public interface ISAM extends HttpAuthenticationMechanism, IKeyMngr, Alarm, IDPS, SWG, ASB, SASE, DLP {

    default AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) throws AuthenticationException {
        return AuthenticationStatus.SUCCESS;
    }

    /**
     * oridat,,,pwd  fgrprint ,faceid
     *
     * @param oriData
     * @return
     */
    @NotBlank
    String geneKey(@NotNull String oriData);

    public void storeKey(@NotBlank String uid, @NotBlank String oriKeyData);

    CredentialValidationResult validate(@NotNull Credential credential);

    public void addLogVldFail(@NotBlank String uid, @NotNull Throwable e);

    public void addLogVldSucess(@NotBlank String uid);

    default @NotNull List<Object> listLog() {
        return List.of();
    }

    ;

}
