package service.auth;

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

import static util.AtProxy4api.httpExchangeCurThrd;
import static util.ExptUtil.appendEx2lastExs;
import static util.JwtUtil.*;
import static util.util2026.chkCantBeEmpty;
import static util.util2026.getCurrentMethodName;

public class chkLgnStatAuthMchsmJwtMod implements HttpAuthenticationMechanism, IdentityStore {
    /**
     * 登录验证HttpAuthenticationMechanism 接口
     * 步骤，拿到用户名密码frm http or dto，检测validate

     * @return
     * @throws AuthenticationException
     */
    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, HttpMessageContext httpMessageContext) throws AuthenticationException {


        try {
            var  uname = getUsernameFrmJwtToken(httpExchangeCurThrd.get());
            validate( new UsernamePasswordCredential(uname, "noNeed"));
            return AuthenticationStatus.SUCCESS;
        }catch (Throwable e) {
            e.printStackTrace();
            appendEx2lastExs(e);
            throw new   AuthenticationException(""+e.getMessage(),e);
        }


    }





    //    Authorization 头部中提取出 JWT Token


//        if (rst.getStatus() == CredentialValidationResult.Status.VALID) {
//        System.out.println("认证成功，用户：" + rst.getCallerPrincipal().getName());
//        System.out.println("用户角色：" + rst.getCallerGroups());
//
//        return AuthenticationStatus.SUCCESS;
//    } else {
//        // 未登录或认证失败
//        System.out.println("认证失败");
//        return AuthenticationStatus.SEND_FAILURE;
//    }
    /**
     * 用户名密码验证  IdentityStore接口
     * 步骤  findById , jude pwd eq
     * @return
     */
    @Override
    public CredentialValidationResult validate(Credential credential2) {


        try {
            UsernamePasswordCredential credential = (UsernamePasswordCredential) credential2;
            // 示例身份验证逻辑
            String caller = credential.getCaller();
            chkCantBeEmpty(caller);
            return new CredentialValidationResult(caller, java.util.Set.of("USER"));
        } catch (IsEmptyEx e) {
            throw new AuthenticationExceptionRuntimeException(e);
        }

    }


}
