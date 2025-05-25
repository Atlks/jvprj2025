package handler.admin.dto;

import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.validation.ValidationException;

public interface IdentityStoreEx {


    CredentialValidationResult validate(Credential credential) throws ValidationException;

}