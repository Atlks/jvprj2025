package util.auth;

import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static util.serverless.ApiGateway.httpExchangeCurThrd;
import static util.excptn.ExptUtil.appendEx2lastExs;
import static util.misc.util2026.getcookie;

public class ChkLgnStatAuthMchsmCkMod implements HttpAuthenticationMechanism {
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

            var uname = getcookie("uname", httpExchangeCurThrd.get());
            new ChkLgnStatAuthenticationMechanism().  validate(new UsernamePasswordCredential(uname, "no_need"));
            return AuthenticationStatus.SUCCESS;

        } catch (Throwable e) {
            e.printStackTrace();
            appendEx2lastExs(e);
            throw new AuthenticationException("" + e.getMessage(), e);
        }


    }


}
