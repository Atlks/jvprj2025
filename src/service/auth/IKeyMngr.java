package service.auth;

import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;

/**
 * 密钥管理器
 */
public interface IKeyMngr extends IdentityStore{


    String geneKey(String oriData);
    public void storeKey(String uid, String pwdOri);

    CredentialValidationResult validate(Credential credential);
}
