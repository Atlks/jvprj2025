package entityx.usr;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "jwt_blacklist")
public class JwtBlacklist {

    @Id
    public String token;       // 被拉黑的 JWT Token（可存哈希）

    public long expiryTime;    // Token 过期时间戳（用于定期清理）

    public long blacklistTime; // 被加入黑名单的时间戳（记录管理）

    public String reason;      // 可选：拉黑原因（如“用户登出”、“手动踢出”等）

    public JwtBlacklist() {}

    public JwtBlacklist(String token, long expiryTime, long blacklistTime, String reason) {
        this.token = token;
        this.expiryTime = expiryTime;
        this.blacklistTime = blacklistTime;
        this.reason = reason;
    }

    // Getters / Setters 省略，可用 Lombok 注解
}
