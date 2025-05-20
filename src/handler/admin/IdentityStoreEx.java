package handler.admin;

import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.validation.ValidationException;

import java.util.EnumSet;
import java.util.Set;

public interface IdentityStoreEx {


    CredentialValidationResult validate(Credential credential) throws ValidationException;

}