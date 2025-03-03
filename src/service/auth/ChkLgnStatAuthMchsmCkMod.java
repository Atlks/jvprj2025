package service.auth;

import api.usr.RuntimeExceptionUserNotExistEx;
import api.usr.encryptAesEx;
import biz.NeedLoginEx;
import com.sun.net.httpserver.HttpExchange;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.HashSet;
import java.util.Set;

import static api.usr.LoginHdr.Key4pwd4aeskey;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static util.AtProxy4api.httpExchangeCurThrd;
import static util.EncryUtil.decryptAesFromStrBase64;
import static util.Util2025.toExchgDt;
import static util.util2026.getCurrentMethodName;
import static util.util2026.getcookie;

public class ChkLgnStatAuthMchsmCkMod implements HttpAuthenticationMechanism, IdentityStore {
    /**
     * @param httpServletRequest
     * @param httpServletResponse
     * @param httpMessageContext
     * @return
     * @throws AuthenticationException
     */
    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, HttpMessageContext httpMessageContext) throws AuthenticationException {
        HttpExchange httpExchange = httpExchangeCurThrd.get();
        var uname = getcookie("uname", httpExchange);
        if (isBlank(uname)) {
            throw new AuthenticationException("SEND_FAILURE uname blank");
        }

        //    Authorization 头部中提取出 JWT Token
        UsernamePasswordCredential crdt = new UsernamePasswordCredential(uname, "");
        CredentialValidationResult rst = validate(crdt);
        if (rst.getStatus() == CredentialValidationResult.Status.VALID) {
            System.out.println("认证成功，用户：" + rst.getCallerPrincipal().getName());
            System.out.println("用户角色：" + rst.getCallerGroups());

            return AuthenticationStatus.SUCCESS;
        } else {
            // 未登录或认证失败
            System.out.println("认证失败");
            return AuthenticationStatus.SEND_FAILURE;
        }

    }

    /**

     * @return
     */
    @Override
    public CredentialValidationResult validate(Credential credential2) {

        UsernamePasswordCredential credential = (UsernamePasswordCredential) credential2;
        // 示例身份验证逻辑
        String caller = credential.getCaller();
        try {
            caller = decryptAesFromStrBase64(caller, Key4pwd4aeskey);
            if (caller == null) {  // 这里增加 `null` 检查
                return CredentialValidationResult.INVALID_RESULT;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new encryptAesEx("" + e.getMessage(), e);
        }

        if (caller.equals("")) {
            NeedLoginEx e = new NeedLoginEx("需要登录");
            e.fun = "BaseHdr." + getCurrentMethodName();
            e.funPrm = credential2;
            throw new RuntimeExNeedLoginEx("需要登录", e);
        }
        //todo qery db chk uname
        Set<String> roles = new HashSet<>();

        roles = new HashSet<>();
        roles.add("USER");
        return new CredentialValidationResult(caller, roles);

    }
}
