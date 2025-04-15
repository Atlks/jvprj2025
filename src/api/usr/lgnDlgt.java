package api.usr;

public class lgnDlgt {
    private final LoginController loginController;

    public lgnDlgt(LoginController loginController) {
        this.loginController = loginController;
    }



    void valid(RegDto usr_dto) {
      //  loginController.getSam().validate(new UsernamePasswordCredential(usr_dto.uname, usr_dto.pwd));
    }
}