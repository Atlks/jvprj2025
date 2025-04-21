package entityx.secury;

import java.time.LocalDateTime;

/**
 * 基于 OWASP 最佳实践
 */
public class PasswordCredential {

    /** 唯一凭据 ID（如用户 ID） */
    private String credentialId;

    /** 加密后的密码哈希（推荐使用 bcrypt/argon2） */
    private String passwordHash;

    /** 随机生成的 Salt（Base64 编码，若哈希算法未内嵌） */
    private String salt;

    /** 哈希算法（如 bcrypt, argon2id, pbkdf2） */
    private String hashAlgorithm;

    /** 算法的成本参数（如迭代次数、内存成本等） */
    private String hashParameters;

    /** 密码创建时间（用于判断过期） */
    private LocalDateTime createdAt;

    /** 上次修改密码时间 */
    private LocalDateTime lastChangedAt;

    /** 是否要求用户在下次登录时强制修改密码 */
    private boolean passwordResetRequired;

    /** 登录失败次数（防爆破） */
    private int failedLoginAttempts;

    /** 账户是否锁定（因多次失败登录） */
    private boolean isLocked;

    /** 账户锁定时间（可选） */
    private LocalDateTime lockedAt;

    /** 账户最后一次成功登录时间（用于安全审计） */
    private LocalDateTime lastSuccessfulLogin;

    /** 最后一次失败登录时间 */
    private LocalDateTime lastFailedLogin;

    // Getter & Setter 略，可使用 Lombok 简化

}

