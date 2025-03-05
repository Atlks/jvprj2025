package api.usr;

import biz.BaseHdr;
import com.sun.net.httpserver.HttpHandler;
import entityx.Usr;
import jakarta.ws.rs.Path;

import static util.misc.util2026.setcookie;

@jakarta.annotation.security.RolesAllowed("user")
@Path("/logout")
public class LogoutHdr extends BaseHdr<Usr, Usr> implements HttpHandler {
    @Override
    public Object handle3() throws Exception {
        setcookie("uname","",this.httpExchange);

        return "";
    }
}
