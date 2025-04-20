package core;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.BeanParam;
import util.ex.PwdErrEx;


/**
 * //   http://localhost:8889/login?uname=008&pwd=000
 */
@PermitAll
//@Path("/login")
public interface IloginV2<DTO> {


//      Object main(@NotNull @BeanParam DTO usr_dto) throws Exception, PwdErrEx
//
//    ;


      Object setLoginTicket(@NotNull DTO usr_dto) ;
}
