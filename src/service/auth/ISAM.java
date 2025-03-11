package service.auth;

import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import service.secury.*;

/**
 * sam安全授权模块   stoer in db
 * 包括俩个部分  keymng 和 安全审计日志接口
 */
public interface ISAM extends HttpAuthenticationMechanism,IKeyMngr,Alarm, IDPS, SWG, ASB, SASE, DLP {



    String geneKey(String oriData);
    public void storeKey(String uid, String oriKeyData);

    CredentialValidationResult validate(Credential credential);
    public void addLogVldFail(String uid, Throwable e);
    public void addLogVldSucess(String uid);
}
