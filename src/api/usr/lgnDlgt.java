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

    static void setVisaByCookie(RegDto usr_dto) {
        util2026.setcookie("unameHRZ", usr_dto.uname, AtProxy4api.httpExchangeCurThrd.get());
        util2026.setcookie("uname", EncryUtil.encryptAesToStrBase64(usr_dto.uname, EncryUtil.Key4pwd4aeskey), AtProxy4api.httpExchangeCurThrd.get());
    }

    void valid(RegDto usr_dto) {
      //  loginController.getSam().validate(new UsernamePasswordCredential(usr_dto.uname, usr_dto.pwd));
    }
}