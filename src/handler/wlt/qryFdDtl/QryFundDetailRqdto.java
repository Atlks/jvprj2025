package handler.wlt.qryFdDtl;

import annos.CurrentUsername;
import lombok.Data;
import util.entty.PageDto;

@Data
public class QryFundDetailRqdto extends PageDto {

    @CurrentUsername
    public String uname="";
    public  int day=7;
}
