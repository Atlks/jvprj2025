package api.usr;

import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import util.algo.EncryUtil;
import util.misc.util2026;
import util.proxy.AtProxy4api;

public class lgnDlgt {
    private final LoginController loginController;

    public lgnDlgt(LoginController loginController) {
        this.loginController = loginController;
    }



    void valid(RegDto usr_dto) {
      //  loginController.getSam().validate(new UsernamePasswordCredential(usr_dto.uname, usr_dto.pwd));
    }
}