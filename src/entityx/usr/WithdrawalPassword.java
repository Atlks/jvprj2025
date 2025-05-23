package entityx.usr;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jnr.a64asm.Offset;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

/**
 * 提现密码
 */
@Entity
@Table(name = "withdrawal_passwords")
@Data
public class WithdrawalPassword {

    @Id


    @Column(nullable = false)
    private String uname;

    public  String pwd;
    @Column(nullable = false)
    private String encryptedPassword; // 加密后的提现密码

    private OffsetDateTime createdAt = OffsetDateTime.now();  // 设置时间

    private OffsetDateTime updatedAt = OffsetDateTime.now();  // 修改时间

    private Boolean isSet = false; // 是否已经设置提现密码

    private Integer failedAttempts = 0; // 连续错误次数

    private Boolean isLocked = false; // 是否锁定

    // 构造函数
    public WithdrawalPassword() {}

    // Getters & Setters


    public String getEncryptedPassword() { return encryptedPassword; }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }



    public Boolean getIsSet() { return isSet; }

    public void setIsSet(Boolean isSet) { this.isSet = isSet; }

    public Integer getFailedAttempts() { return failedAttempts; }

    public void setFailedAttempts(Integer failedAttempts) { this.failedAttempts = failedAttempts; }

    public Boolean getIsLocked() { return isLocked; }

    public void setIsLocked(Boolean isLocked) { this.isLocked = isLocked; }
}

