package api.usr;

import handler.usr.LoginHdlr;
import handler.usr.dto.RegDto;

public class lgnDlgt {
    private final LoginHdlr loginSvs;

    public lgnDlgt(LoginHdlr loginSvs) {
        this.loginSvs = loginSvs;
    }



    void valid(RegDto usr_dto) {
      //  loginController.getSam().validate(new UsernamePasswordCredential(usr_dto.uname, usr_dto.pwd));
    }
}