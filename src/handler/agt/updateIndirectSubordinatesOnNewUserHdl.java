package handler.agt;

import static util.tx.HbntUtil.findByHerbinate;
import static util.tx.HbntUtil.mergeByHbnt;

import java.util.List;
import static cfg.AppConfig.sessionFactory;
import static util.tx.HbntUtil.findByHerbinate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import entityx.usr.Usr;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.constraints.NotNull;
import util.tx.findByIdExptn_CantFindData;
import entityx.usr.Usr;
import model.agt.Agent;

//regEvtHdl4refreshIndrctSubCnt
public class updateIndirectSubordinatesOnNewUserHdl {

public Object handleRequest(@NotNull Usr u) throws Exception, findByIdExptn_CantFindData {
      
      new AgtSvs().updateIndirectSubordinatesOnNewUser(u.id);
return 0;
    }



}
