package entityx.usr;


import jakarta.persistence.Id;
import lombok.Data;
import util.annos.CurrentUsername;

import java.time.LocalDateTime;

/**
 * 提现密码
 */

@Data
public class SetWithdrawalPasswordDto {

    @Id


    @CurrentUsername
    public String uname;

    public  String pwd;


    private LocalDateTime createdAt = LocalDateTime.now();  // 设置时间

    private LocalDateTime updatedAt = LocalDateTime.now();  // 修改时间

    private Boolean isSet = false; // 是否已经设置提现密码

    private Integer failedAttempts = 0; // 连续错误次数

    private Boolean isLocked = false; // 是否锁定

    // 构造函数
    public SetWithdrawalPasswordDto() {}

    // Getters & Setters




    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Boolean getIsSet() { return isSet; }

    public void setIsSet(Boolean isSet) { this.isSet = isSet; }

    public Integer getFailedAttempts() { return failedAttempts; }

    public void setFailedAttempts(Integer failedAttempts) { this.failedAttempts = failedAttempts; }

    public Boolean getIsLocked() { return isLocked; }

    public void setIsLocked(Boolean isLocked) { this.isLocked = isLocked; }
}

