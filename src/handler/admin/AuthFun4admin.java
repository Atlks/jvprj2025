package handler.admin;

import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.algo.EncryUtil;
import util.auth.ChkLgnStatAuthenticationMechanism;
import util.misc.util2026;
import util.serverless.ApiGateway;

import static util.excptn.ExptUtil.appendEx2lastExs;

public class AuthFun4admin implements HttpAuthenticationMechanism {


    /**
     * @param httpServletRequest
     * @param httpServletResponse
     * @param httpMessageContext
     * @return
     * @throws AuthenticationException
     */
    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, HttpMessageContext httpMessageContext) throws AuthenticationException {
        try {

            var adm_wz_encry = util2026.getcookie("adm", ApiGateway.httpExchangeCurThrd.get());
            var adm = EncryUtil.decryptAesFromStrBase64(adm_wz_encry, EncryUtil.Key4pwd4aeskey);

            new ChkLgnStatAuthenticationMechanism().validate(new UsernamePasswordCredential(adm, "no_need"));
            return AuthenticationStatus.SUCCESS;

            //todo blk list ,usr blk list
            // token blk list

        } catch (Throwable e) {
            e.printStackTrace();
            appendEx2lastExs(e);
            throw new AuthenticationException("" + e.getMessage(), e);
        }
    }
}
