package api.usr;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
}
