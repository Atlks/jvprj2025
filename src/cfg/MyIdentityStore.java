package cfg;



//import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;

import java.util.HashSet;
import java.util.Set;

//@ApplicationScoped
public class MyIdentityStore implements IdentityStore {


    public CredentialValidationResult validate(UsernamePasswordCredential credential) {
        // 示例身份验证逻辑
        if ("user".equals(credential.getCaller()) && "password123".equals(credential.getPasswordAsString())) {
            Set<String> roles = new HashSet<>();
            roles.add("USER");
            return new CredentialValidationResult("user", roles);
        }
        return CredentialValidationResult.INVALID_RESULT;
    }
}

