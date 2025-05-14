package model.oauthOpenid;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;


/**
 * reef  TokenResponse  openid,oauth
 * 哪里“规定”了注册登录 API 和字段？

来源	是否官方	推荐作用
OpenAPI	✅ 是	API 格式/字段结构
OWASP	✅ 是	安全字段和认证流程
 */
@Data
@NoArgsConstructor

@Accessors(chain = true)//	支持链式调用（set 返回 this）
public class LoginResponse {
    
    @NotNull
    public String accessToken;
    @NotNull
    public int expiresIn;

    public String token_type="Bearer";

}
