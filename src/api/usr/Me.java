package api.usr;

import biz.BaseHdr;
import com.sun.net.httpserver.HttpHandler;
import entityx.Usr;
import jakarta.ws.rs.Path;
import util.algo.Icall;
import util.excptn.NotExistRow;

import static biz.Containr.sessionFactory;
import static util.auth.AuthUtil.getCurrentUser;
import static util.misc.util2026.setcookie;
import static util.tx.HbntUtil.findByHerbinate;


/**
 * user center
 */
@jakarta.annotation.security.RolesAllowed("user")
@Path("/users/me")
public class Me implements Icall<Usr, Object> {
    @Override
    public Object call(Usr dto) throws  Throwable {
        Usr objU = findByHerbinate(Usr.class, getCurrentUser(), sessionFactory.getCurrentSession());

        return objU;
    }
}
