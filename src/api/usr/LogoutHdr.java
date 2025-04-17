package api.usr;

import jakarta.ws.rs.Path;
import util.algo.Icall;

import static util.misc.util2026.setcookie;
import static util.serverless.ApiGateway.httpExchangeCurThrd;

@jakarta.annotation.security.RolesAllowed("user")
@Path("/logout")
public class LogoutHdr implements Icall<Object, Object> {
    @Override
    public Object main(Object dto) throws Exception {
        setcookie("uname","",httpExchangeCurThrd.get());

        return "";
    }
}
