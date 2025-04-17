package dto;

import annos.CurrentUsername;
import lombok.Data;

@Data
public class ChangePasswordReqDto {

    @CurrentUsername
    public String uname;

    // 当前密码
    public String oldpwd;

    // 新密码
    public String newpwd
            ;

    // 确认新密码（可选）
   // private String confirmPassword;

    // Getter 和 Setter 方法
    public String getOldPassword() {
        return oldpwd;
    }

    public void setOldPassword(String oldPassword) {
        this.oldpwd = oldPassword;
    }



}

