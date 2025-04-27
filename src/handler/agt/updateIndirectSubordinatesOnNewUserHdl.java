package handler.agt;

import entityx.usr.Usr;
import jakarta.validation.constraints.NotNull;
import util.tx.findByIdExptn_CantFindData;

//regEvtHdl4refreshIndrctSubCnt
public class updateIndirectSubordinatesOnNewUserHdl {

public Object handleRequest(@NotNull Usr u) throws Exception, findByIdExptn_CantFindData {
      
      new AgtRegSubSttSvs().updateIndirectSubordinatesOnNewUser(u.id);
return 0;
    }



}
