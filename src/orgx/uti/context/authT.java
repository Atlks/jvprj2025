package orgx.uti.context;

// com.google.protobuf.AbstractProtobufList;
import jakarta.security.enterprise.CallerPrincipal;
import jakarta.servlet.http.HttpServletRequest;

import static orgx.uti.context.AuthUti.getCurrUname;
import static orgx.uti.context.AuthUti.setCurrUname;
import static orgx.uti.context.ThreadContext.principal;

public class authT {

    public static void main(String[] args) {
        String uname = "888";
        setCurrUname(uname);

        System.out.println(getCurrUname());


        HttpServletRequest request = null;
        boolean isAdmin = request.isUserInRole("ADMIN");
        System.out.println("当前用户是否是管理员: " + isAdmin);
    }




}
