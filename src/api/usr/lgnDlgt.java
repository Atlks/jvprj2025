package api.usr;

public class lgnDlgt {
    private final LoginSvs loginSvs;

    public lgnDlgt(LoginSvs loginSvs) {
        this.loginSvs = loginSvs;
    }



    void valid(RegDto usr_dto) {
      //  loginController.getSam().validate(new UsernamePasswordCredential(usr_dto.uname, usr_dto.pwd));
    }
}