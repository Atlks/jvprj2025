package entityx.secury;

import java.time.LocalDateTime;


/**
 * ISO/IEC 27001 / 27002	信息安全管理规范，定义了用户凭证的存储与管理方法	提及密码字段安全要求
 */
public class PasswordCredentialIso27001 {

    /** 唯一标识符，例如用户ID或凭证ID */
    private String credentialId;

    /** 密码哈希（已加盐处理） */
    private String passwordHash;

    /** 哈希算法名称（如 bcrypt, argon2id, pbkdf2） */
    private String hashAlgorithm;

    /** 盐值（通常为Base64编码） */
    private String salt;

    /** 哈希的迭代次数或成本因子 */
    private int iterationCount;

    /** 上次密码修改时间 */
    private LocalDateTime passwordLastChanged;

    /** 密码到期时间（可选） */
    private LocalDateTime passwordExpiresAt;

    /** 是否要求强制重设密码 */
    private boolean passwordResetRequired;

    /** 登录失败次数 */
    private int failedLoginAttempts;

    /** 帐号是否被锁定 */
    private boolean isLocked;

    /** 创建时间 */
    private LocalDateTime createdAt;

    /** 最后更新时间 */
    private LocalDateTime updatedAt;

    // Getter 和 Setter 方法省略，可使用 Lombok 简化

    // 也可添加 equals(), hashCode(), toString() 等方法
}
