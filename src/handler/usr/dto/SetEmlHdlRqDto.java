package handler.usr.dto;

import lombok.Data;
import util.annos.CurrentUsername;

@Data
public class SetEmlHdlRqDto {

    @CurrentUsername
    public String uname;
    public  String email;
}
