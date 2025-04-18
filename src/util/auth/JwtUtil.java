package util.auth;

import com.sun.net.httpserver.HttpExchange;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.constraints.NotBlank;
import model.auth.Role;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.trim;

/**
 * jwt visa spec
 * 一个完整的 JWT 可能长这样：
 * <p>
 * 复制
 * 编辑
 * eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
 * 其中：
 * <p>
 * 第一部分是 Header，经过 Base64Url 编码。
 * 第二部分是 Payload，也经过 Base64Url 编码。
 * 第三部分是 Signature，由前两部分和密钥生成。
 */
public class JwtUtil {

    public static void main(String[] args) {
        System.out.println(newToken("777"));
    }
    @Value("scrkey")
    // 密钥，通常应该从环境变量或配置文件中获取，避免硬编码
    private static final String SECRET_KEY = "mysecretkey";

    // JWT过期时间，单位毫秒  100day
    private static final long EXPIRATION_TIME = 100*24*3600*1000; // 10 days



    /**
     * JWT 常见字段解释
     * sub (Subject)：通常是用户的唯一标识符。
     * iat (Issued At)：JWT 的创建时间，用来表示该令牌生成的时间。
     * exp (Expiration Time)：JWT 过期时间，超过此时间后令牌失效。
     * aud (Audience)：令牌的受众，表示谁是这个 JWT 的目标用户。
     * iss (Issuer)：JWT 的签发者，通常为生成该令牌的应用或服务。
     * scope：定义该令牌的权限范围，常用于 OAuth2 授权中，表示允许的操作或访问的资源范围
     *
     * @param username
     * @return
     */
    // 生成 JWT   512bit 64byte
    public static @NotBlank String newToken(@NotBlank String username, Role role) {
        SecretKey key = Keys.hmacShaKeyFor(get64Bytes512bitKey(SECRET_KEY)); // 生成符合 HS512 规范的密钥
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setIssuer("ati")
                .setAudience(String.valueOf(role))  //Audience，受众，表示这个 JWT 是为谁生成的。
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .setId(getUuid())
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    /**
     * Deprecated
     * JWT 常见字段解释
     * sub (Subject)：通常是用户的唯一标识符。
     * iat (Issued At)：JWT 的创建时间，用来表示该令牌生成的时间。
     * exp (Expiration Time)：JWT 过期时间，超过此时间后令牌失效。
     * aud (Audience)：令牌的受众，表示谁是这个 JWT 的目标用户。
     * iss (Issuer)：JWT 的签发者，通常为生成该令牌的应用或服务。
     * scope：定义该令牌的权限范围，常用于 OAuth2 授权中，表示允许的操作或访问的资源范围
     *
     * @param username
     * @return
     */
    // 生成 JWT   512bit 64byte
    @Deprecated
    public static @NotBlank String newToken(@NotBlank String username) {
        SecretKey key = Keys.hmacShaKeyFor(get64Bytes512bitKey(SECRET_KEY)); // 生成符合 HS512 规范的密钥
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setIssuer("ati")
                .setAudience(String.valueOf(Role.USER))  //Audience，受众，表示这个 JWT 是为谁生成的。
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .setId(getUuid())
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    private static String getUuid() {
        return java.util.UUID.randomUUID().toString().replace("-", "");
    }

    //生成512位  64字节的密钥，，根据字符串做散列运算得到 64字节的hash值
    private static byte[] get64Bytes512bitKey(@NotBlank String secretKey) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512"); // 使用 SHA-512 进行哈希
            return digest.digest(secretKey.getBytes(StandardCharsets.UTF_8)); // 生成 64 字节密钥
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-512 algorithm not available", e);
        }
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

    public static Claims getClaims(@NotBlank String token) {
        byte[] keys = get64Bytes512bitKey(SECRET_KEY);
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(keys)
                .build();
        return jwtParser.parseClaimsJws(token).getBody();
    }

    //  从http请求头部提取    AuthChkMode  标头  dft jwt
    public static @NotBlank String getAuthChkMode(HttpExchange he) {
// 从请求头中获取 "AuthChkMode" 标头
        String authChkMode = he.getRequestHeaders().getFirst("AuthChkMode");
        if (authChkMode == null)
            return "jwt";
        return trim(authChkMode);

    }

    public static  @jakarta.validation.constraints.NotBlank String getTokenMust(HttpExchange he) throws CantGetTokenJwtEx, validateTokenExcptn {
// 从请求头中获取 Authorization 字段
        String authHeader = he.getRequestHeaders().getFirst("Authorization");
        if(isBlank(authHeader))
            throw new CantGetTokenJwtEx("Authorization token cant get");
        String token = authHeader.substring(7);
        if (isBlank(token))
            throw new CantGetTokenJwtEx("Authorization token cant get");
        validateToken(token);
        return token; // 去掉 "Bearer " 前缀

    }

    //   @Deprecated
    //  //    Authorization 头部中提取出 JWT Token
//    public static  String getToken(HttpExchange he){

    /// / 从请求头中获取 Authorization 字段
//        String authHeader = he.getRequestHeaders().getFirst("Authorization");
//
//        // 检查 Authorization 字段是否为空，并且它是否以 "Bearer " 开头
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            // 截取 "Bearer " 后的部分，即 JWT Token
//            return authHeader.substring(7); // 去掉 "Bearer " 前缀
//        }
//
//        // 如果没有 Authorization 头或者格式不正确，返回 null 或者你可以抛出异常
//        return "";
//    }


    // 获取 JWT 中的用户信息
    public static @NotBlank String getUsername(@NotBlank String token) {
        return getClaims(token).getSubject();
    }

    public static  @NotNull String getUsernameFrmJwtToken( @NotNull HttpExchange httpExchange) throws CantGetTokenJwtEx, unameIsEmptyExcptn, validateTokenExcptn {
        var token = getTokenMust(httpExchange);
        var uname = getUsername(token);
        if (isBlank(uname)) {
            throw new unameIsEmptyExcptn("");
        }
        return uname;
    }

    // 验证 JWT 是否有效
    public static boolean isTokenExpired(@NotBlank String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    // 验证 JWT 是否有效
    public static boolean validateToken(@NotBlank String token) throws validateTokenExcptn {
        if (!isValidStr(token))
            throw new validateTokenExcptn("");

//        if (token == null || token.isEmpty()) {
//            throw new InvalidTokenException("Token is invalid");
//        }
        // 1. 从 JWT 中提取用户名
        String username = getUsername(token);

        // 2. 验证 token 是否过期以及用户名是否有效
        if (username == null || username.equals("") || isTokenExpired(token)) {
            return false;  // 无效的用户名或令牌过期
        }

        // 3. 通过数据库验证用户是否有效
//        if (!isUserValid(username)) {
//            return false;  // 用户无效
//        }

        // 4. 所有验证通过，返回 true
        return true;
    }

    //是否有效字符串 ，不能为全部空白， 不能为null
    private static boolean isValidStr(@NotBlank String token) {
        // 检查字符串是否为 null 或者只包含空白字符
        return StringUtils.isNotBlank(token);
    }
}
