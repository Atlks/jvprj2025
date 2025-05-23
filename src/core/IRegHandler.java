package core;

import model.usr.Usr;
import handler.usr.dto.RegDto;
import util.model.common.ApiResponse;
//import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.BeanParam;

import util.algo.Icall;
import util.ex.existUserEx;

import static cfg.Containr.sam4regLgn;
import static util.misc.Util2025.encodeJson;
import static util.proxy.AopUtil.ivk4log;
/**
 * reg
 * http://localhost:8889/reg?uname=008&pwd=000&invtr=007
 *
 *
 *
 */

//@Path("/reg")
//@Tag(name = "usr")
//@Tag(name = "用户管理", description = "用户相关操作")
//@Parameter(name = "uname")
//@Parameter(name = "pwd")
//@Parameter(name = "invtr", description = "邀请人", required = false)
//@Parameter(name = "email",required = false)
@PermitAll
// implements RequestHandler<Map<String,Object>, ApiGatewayResponse> {
public interface IRegHandler extends Icall<RegDto, Object>  {
    String SAM4regLgn = "SAM4regLgn";


    //handleRequest(Map<String, Object> input, Context context)
    default Object main(@BeanParam RegDto dtoReg) throws Throwable {
        System.out.println("IRegHandler.main(" + encodeJson(dtoReg));
        ivk4log("chkExistUser", () -> {
            return chkExistUser(dtoReg);
        });
        //add u
        addU(dtoReg);
      //  storekey(dtoReg);
        sam4regLgn.storeKey(dtoReg.uname, dtoReg.pwd);
        return new ApiResponse(dtoReg);
    }

    //
//    org.hibernate.Session session;
    boolean chkExistUser(RegDto user) throws existUserEx;

    public Usr addU(RegDto dtoReg);

}
