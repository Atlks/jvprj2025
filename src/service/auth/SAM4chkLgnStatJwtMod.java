package service.auth;

import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.jetbrains.annotations.Nullable;
import util.auth.ChkLgnStatAuthenticationMechanism;

import static util.proxy.AtProxy4api.httpExchangeCurThrd;
import static util.excptn.ExptUtil.appendEx2lastExs;
import static util.auth.JwtUtil.*;

public class SAM4chkLgnStatJwtMod implements ISAM, HttpAuthenticationMechanism {
    /**
     * 登录验证HttpAuthenticationMechanism 接口
     * 步骤，拿到用户名密码frm http or dto，检测validate
     *
     * @return
     * @throws AuthenticationException
     */
    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, HttpMessageContext httpMessageContext) throws AuthenticationException {


        try {
            @NotNull
            String uname = getUsernameFrmJwtToken(httpExchangeCurThrd.get());
            new ChkLgnStatAuthenticationMechanism().validate(new UsernamePasswordCredential(uname, "noNeed"));
            return AuthenticationStatus.SUCCESS;
        } catch (Throwable e) {
            e.printStackTrace();
            appendEx2lastExs(e);
            throw new AuthenticationException("" + e.getMessage(), e);
        }


    }

    /**
     * jwt
     * @param oriData
     * @return
     */
    @Override
    public String geneKey(String oriData) {
        return "";
    }

    /**jwt
     * @param uid
     * @param oriKeyData
     */
    @Override
    public void storeKey(String uid, String oriKeyData) {

    }

    /**
     * @param credential
     * @return
     */
    @Nullable
    @Override
    public CredentialValidationResult validate(Credential credential) {
        return null;
    }

    /**
     * @param uid
     * @param e
     */
    @Override
    public void addLogVldFail(String uid, Throwable e) {

    }

    /**
     * @param uid
     */
    @Override
    public void addLogVldSucess(String uid) {

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


}
