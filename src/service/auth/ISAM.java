package service.auth;

import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;

/**
 * sam安全授权模块   stoer in db
 * 包括俩个部分  keymng 和 安全审计日志接口
 */
public interface ISAM extends IKeyMngr {



    String geneKey(String oriData);
    public void storeKey(String uid, String oriKeyData);

    CredentialValidationResult validate(Credential credential);
    public void addLogVldFail(String uid, Throwable e);
    public void addLogVldSucess(String uid);
}
