package service.auth;


//import jakarta.enterprise.context.ApplicationScoped;

import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;

import java.util.HashSet;
import java.util.Set;

import static api.usr.LoginHdr.Key4pwd4aeskey;
import static util.EncryUtil.decryptAesFromStrBase64;


//@ApplicationScoped
public class chkLoginStatIdentityStore implements IdentityStore {


    public CredentialValidationResult validate(UsernamePasswordCredential credential)  {
        // 示例身份验证逻辑
        String caller = credential.getCaller();
        try {
            caller = decryptAesFromStrBase64(caller, Key4pwd4aeskey);
            if (caller == null) {  // 这里增加 `null` 检查
                return CredentialValidationResult.INVALID_RESULT;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return CredentialValidationResult.INVALID_RESULT;
//            ParseEx ex = new ParseEx("token session解析错误");
//            ex.fun = this.getClass().getName() + "." + getCurrentMethodName();
//            ex.funPrm = toExchgDt((HttpExchange) httpExchangeCurThrd.get());
//            throw ex;
        }

        if (caller.equals("")) {
            return CredentialValidationResult.INVALID_RESULT;
//            NeedLoginEx e = new NeedLoginEx("需要登录");
//
//            e.fun = "BaseHdr." + getCurrentMethodName();
//            e.funPrm = toExchgDt((HttpExchange) httpExchangeCurThrd.get());
//            throw e;
        }
        //todo qery db chk uname
        Set<String> roles = new HashSet<>();
        if (caller.length() > 0)
        //&& "password123".equals(credential.getPasswordAsString())) {
        {
            roles = new HashSet<>();
            roles.add("USER");
            return new CredentialValidationResult(caller, roles);
        } else
            return CredentialValidationResult.INVALID_RESULT;


    }
}

