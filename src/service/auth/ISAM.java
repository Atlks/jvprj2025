package service.auth;

import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;

public interface ISAM extends IdentityStore, IKeyMngr {


    String geneKey(String pwd, String salt);
    public void storeKey(String uid, String pwdOri);

    CredentialValidationResult validate(Credential credential);
    public void addLogVldFail(String uid, Throwable e);
    public void addLogVldSucess(String uid);
}
