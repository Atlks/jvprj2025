package handler.admin;

import entityx.usr.NonDto;
import handler.admin.dto.AdminLoginDto;
import jakarta.annotation.security.PermitAll;
import util.annos.Paths;
@PermitAll
@Paths({"/admin/adm/logout"})
public class logout {
    public Object handleRequest(NonDto arg) throws Throwable {
return  "ok";
    }
}