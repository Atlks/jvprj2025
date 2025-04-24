package model.usr;

import lombok.Data;

@Data
public class PasswordResetBySecurityQuestion {
    private String username;
    private String answer;
    private String newPassword;
}
