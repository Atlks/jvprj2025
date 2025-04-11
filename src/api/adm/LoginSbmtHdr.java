package api.adm;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController


//组合了 @Controller 和 @ResponseBody，表示该类是 REST API 控制器，所有方法的返回值默认序列化为 JSON 或 XML。
@PermitAll
@Path("/adm/loginSbmt")
//   http://localhost:8889//adm/login
@NoArgsConstructor
@Data
public class LoginSbmtHdr {
}
