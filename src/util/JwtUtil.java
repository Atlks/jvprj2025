package util;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * 一个完整的 JWT 可能长这样：
 *
 * 复制
 * 编辑
 * eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
 * 其中：
 *
 * 第一部分是 Header，经过 Base64Url 编码。
 * 第二部分是 Payload，也经过 Base64Url 编码。
 * 第三部分是 Signature，由前两部分和密钥生成。
 */
public class JwtUtil {

    @Value("scrkey")
    // 密钥，通常应该从环境变量或配置文件中获取，避免硬编码
    private static final String SECRET_KEY = "mysecretkey";

    // JWT过期时间，单位毫秒
    private static final long EXPIRATION_TIME = 864_000_000L; // 10 days


    /**
     * JWT 常见字段解释
     * sub (Subject)：通常是用户的唯一标识符。
     * iat (Issued At)：JWT 的创建时间，用来表示该令牌生成的时间。
     * exp (Expiration Time)：JWT 过期时间，超过此时间后令牌失效。
     * aud (Audience)：令牌的受众，表示谁是这个 JWT 的目标用户。
     * iss (Issuer)：JWT 的签发者，通常为生成该令牌的应用或服务。
     * scope：定义该令牌的权限范围，常用于 OAuth2 授权中，表示允许的操作或访问的资源范围
     * @param username
     * @return
     */
    // 生成 JWT
    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setIssuer("ati")

                .setAudience("nmlUser")  //Audience，受众，表示这个 JWT 是为谁生成的。
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

//jwt 0.11.5 is ok
    //jwt版本  0.12.6 ，这个函数报错语法错误
    //Cannot resolve method 'parseClaimsJws' in 'JwtParserBuilder'
    // 解析 JWT，获取其中的 claims
//    public static Claims getClaims(String token) {
//
//        return Jwts.parser()
//                .setSigningKey(SECRET_KEY)
//                .parseClaimsJws(token)
//                .getBody();
//    }

    public static Claims getClaims(String token) {
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build();
        return jwtParser.parseClaimsJws(token).getBody();
    }




    // 获取 JWT 中的用户信息
    public static String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    // 验证 JWT 是否有效
    public static boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    // 验证 JWT 是否有效
    public static boolean validateToken(String token, String username) {
        return (username.equals(getUsername(token)) && !isTokenExpired(token));
    }
}
