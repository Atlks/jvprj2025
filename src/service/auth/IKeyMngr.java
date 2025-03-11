package service.auth;

import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;

public interface IKeyMngr {


    String geneKey(String pwd, String salt);
    public void storeKey(String uid, String pwdOri);

    CredentialValidationResult validate(Credential credential);
}
