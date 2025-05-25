package handler.admin.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import util.annos.CurrentUsername;
@Data
public class restePwdDto {


    @NotBlank(message = "用户不能为空")
    public String uname="";



    // 新密码
    @NotBlank(message = "新密码不能为空")
    public String newpwd="";
}
