package api.usr;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Path;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import util.algo.Icall;
import util.ex.existUserEx;
@RestController
@Path("/reg")
@Tag(name = "usr")
@Tag(name = "用户管理", description = "用户相关操作")
@annos.Parameter(name = "uname")
@annos.Parameter(name = "pwd")
@annos.Parameter(name = "invtr",description = "邀请人",required = false)
@PermitAll
/**
 * reg
 *
 * existUser,adduser,storekey (这里流程抽象)
 */
public interface IRegHandler extends Icall<RegDto, Object> {
    String SAM4regLgn = "SAM4regLgn";

    //如何标识swagger文档。。
//我的访问url类似  http://localhost:8889/reg?uname=008&pwd=000&invtr=007


    // 会自动把 ?name=John&age=30 转换成 UserQueryDTO 对象！
//    public void handle2(
//            @ModelAttribute
//            HttpExchange exchange) throws Exception, existUserEx {
//
//        //u dto
//        Usr u = toDto(exchange,Usr.class);
//
//
//    }
    @Path("/reg")
    @Tag(name = "usr")
    @Operation(summary = "注册用户的方法reg", description = "注册用户的方法dscrp。。。。")

    @Parameter(name = "uname", description = "用户名", required = true)
    @Parameter(name = "pwd", description = "密码", required = true)
    @Parameter(name = "uname", description = "邀请人", required = false)
    @PermitAll
    @Validated
    Object call(@BeanParam RegDto dtoReg) throws Throwable;

    //@Autowired
//    org.hibernate.Session session;
    boolean existUser(RegDto user) throws existUserEx;

 //   boolean existUser(String uname) throws Exception;
}
