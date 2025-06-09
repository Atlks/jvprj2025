package handler.usr.dto;

import lombok.Data;
import util.annos.CurrentUsername;
@Data
public class ResetWithdrawPwdRequestDto {

@CurrentUsername
    @jakarta.validation.constraints.NotBlank(message = "用户名不能为空")
    public String uname;

    @jakarta.validation.constraints.NotBlank(message = "安全问题答案不能为空")
    public String answer;

    @jakarta.validation.constraints.NotBlank(message = "提现密码不能为空")
    @jakarta.validation.constraints.Size(min = 6, message = "提现密码不能少于6位")
    public String newWithdrawPwd;

    // Getters and Setters
    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getNewWithdrawPwd() {
        return newWithdrawPwd;
    }

    public void setNewWithdrawPwd(String newWithdrawPwd) {
        this.newWithdrawPwd = newWithdrawPwd;
    }

    @Override
    public String toString() {
        return "ResetWithdrawPwdRequestDto{" +
                "uname='" + uname + '\'' +
                ", answer='[PROTECTED]'" +
                ", newWithdrawPwd='[PROTECTED]'" +
                '}';
    }
}

