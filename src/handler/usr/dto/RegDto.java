package handler.usr.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegDto {


    @NotBlank

    @Valid
    public String uname = "";
    @NotBlank
    public String pwd = "";
    public String invtr = "";
    public String email = "";
    public String cptch;
    public String refcode="";
}
