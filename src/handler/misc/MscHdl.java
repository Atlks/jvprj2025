package handler.misc;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;

public class MscHdl {

    @PermitAll
    @Path("/api/ver")
    public String ver()
    {
        return  "519";
    }
}
