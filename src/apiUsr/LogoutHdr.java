package apiUsr;

import biz.BaseHdr;
import com.sun.net.httpserver.HttpHandler;
import entityx.Usr;
import jakarta.ws.rs.Path;
import org.springframework.web.bind.annotation.ModelAttribute;

import static util.util2026.setcookie;
import static util.util2026.wrtResp;

@jakarta.annotation.security.RolesAllowed("user")
@Path("/logout")
public class LogoutHdr extends BaseHdr<Usr, Usr> implements HttpHandler {
    @Override
    public Object handle3() throws Exception {
        setcookie("uname","",this.httpExchange);

        return "";
    }
}
