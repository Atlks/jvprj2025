package test;

import entityx.LogBls;
import entityx.ReChgOrd;
import entityx.Usr;
import entityx.LogBlsLogYLwlt;
import utilFuchr.JdoPstsMngrHbnt;
import utilFuchr.JdoPstsMngrIni;

import javax.jdo.PersistenceManager;
import java.util.ArrayList;
import java.util.List;

import static apiUsr.RegHandler.saveDirUsrs;
import static cfg.MyCfg.iniCfgFrmCfgfile;

public class jdoTest {

    public static void main(String[] args) {


        List<Class> li=new ArrayList<>();
        li.add(Usr.class);
        li.add(LogBls.class);
        li.add(LogBlsLogYLwlt.class);
        li.add(ReChgOrd.class);
        li.add(Usr.class);
     //   PersistenceManager pm=newPersistenceManager("/0db");
        iniCfgFrmCfgfile();
        PersistenceManager pm=newPersistenceManager4hbnt(saveDirUsrs,li);
        // 开始事务
        pm.currentTransaction().begin();
        Usr u=new Usr();
        u.uname="777";
        u.id=u.uname;
        pm.makePersistent(u);
        // 提交事务
        pm.currentTransaction().commit();
    }
    private static PersistenceManager newPersistenceManager4hbnt(String url,List<Class> li) {

        return  new JdoPstsMngrHbnt(url,li);

    }
    private static PersistenceManager newPersistenceManager(String url) {
          if(url.startsWith("jdbc:mysql")){
              //  return new JdoPstsMngrHbnt(url, li);
          }
          return  new JdoPstsMngrIni(url);

    }
}
