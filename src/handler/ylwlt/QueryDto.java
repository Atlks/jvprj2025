package handler.ylwlt;


import util.annos.CurrentUsername;
import lombok.Data;
import lombok.NoArgsConstructor;
import util.entty.PageDto;

@Data
@NoArgsConstructor
public class QueryDto  extends PageDto {

    @CurrentUsername
    public  String uname="";
    public int page =1;
    public int pagesize =100;

    public QueryDto(String uname2) {
   this.uname=uname2;
    }
}
