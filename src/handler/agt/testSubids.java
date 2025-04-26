package handler.agt;
import static cfg.MyCfg.iniContnr;
import static cfg.AppConfig.sessionFactory;
import static cfg.MyCfg.iniContnr;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import static util.tx.TransactMng.openSessionBgnTransact;

import cfg.AppConfig;

public class testSubids {

    public static void main(String[] args) throws Throwable, Throwable {
        beforTest();

        System.out.println(new GetSubIds().findAllSubordinateIds("666"));
    }

    private static void beforTest() throws Throwable, SQLException {
      
        
           new AppConfig().sessionFactory();//ini sessFctr
        //ini contnr 4cfg,, svrs
        iniContnr();
        openSessionBgnTransact();
      //  iniEvtHdrCtnr();

     //   evtlist4reg.add(new AgtHdl()::regEvtHdl);
    
    }

}
