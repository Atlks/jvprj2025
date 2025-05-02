//RegHandler.java

package handler.admin.ssr;

import model.admin.Admin;
//import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import lombok.NoArgsConstructor;

import org.springframework.stereotype.Controller;
import util.algo.Icall;
import util.algo.Tag;

import static ztest.htmlTppltl.rend;


/**
 * 注册 用户
 * // @param uname
 * // @param pwd
 */
  // 让 Spring 自动管理这个 Bean

//  http://localhost:8889/adm/addUi
@Controller
@Path("/adm/addUi")

@Tag(name = "用户管理", description = "用户相关操作")
@PermitAll
@NoArgsConstructor
// @Produces / @Consumes：指定返回和接收的数据格式（如 application/json）
public class AddAdminUiHdr implements Icall<Admin, Object> {
  /**
     * @param arg
     * @return
     * @throws Throwable
     */
    @Override
    public Object main(Admin arg) throws Throwable {
        org.thymeleaf.context.Context context = new org.thymeleaf.context.Context();
        // context.setVariable("users", users);
        String tmpleFileName = "addAdm";


        //  System.out.println( );
        return rend(tmpleFileName, context );
    }




}
