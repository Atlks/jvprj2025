package orgx.msc;

import orgx.u.User;

import static orgx.uti.Uti.valdt;

public class vldT {

    public static void main(String[] args) {
        User user = new User();
        user.setName("2");
        valdt(user);
    }


}
