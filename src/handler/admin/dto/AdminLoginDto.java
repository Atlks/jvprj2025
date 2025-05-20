package handler.admin.dto;

import lombok.Data;

@Data
public class AdminLoginDto {

    public  String username;
    public  String password;
    public String cptch="";
}
