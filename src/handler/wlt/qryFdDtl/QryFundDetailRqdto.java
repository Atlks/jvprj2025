package handler.wlt.qryFdDtl;

import util.annos.CurrentUsername;
import lombok.Data;
import util.entty.PageDto;

@Data
public class QryFundDetailRqdto extends PageDto {

    @CurrentUsername
    public String uname="";
    public  int day=7;
}
