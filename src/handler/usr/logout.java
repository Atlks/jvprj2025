package handler.usr;

import entityx.usr.NonDto;
import jakarta.annotation.security.PermitAll;
import util.annos.Paths;

@PermitAll
@Paths({"/logout"})
public class logout {
    public Object handleRequest(NonDto arg) throws Throwable {
return  "ok";
    }
}