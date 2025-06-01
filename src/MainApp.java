//import handler.agt.AgtHdl;
//import handler.rechg.RechargeCallbackHdr;

//import org.noear.solon.annotation.SolonMain;
import cfg.MainStart;
import it.sauronsoftware.cron4j.Scheduler;
import lombok.SneakyThrows;
import org.apache.commons.dbcp2.BasicDataSource;

import util.log.ConsoleInterceptor;

//import service.AddRchgOrdToWltService;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

// static cfg.AppConfig.sessionFactory;
//import static cfg.Containr.evtlist4reg;
import static cfg.IniCfg.iniContnr4cfgfile;
import static cfg.MainStart.*;
import static cfg.WebSvr.*;
import static handler.statmt.StatmtService.geneStatmtTodateType;
import static handler.statmt.StatmtService.generStatmtCurMth;
import static java.time.LocalTime.now;
import static util.algo.CallUtil.callTry;
import static util.algo.CallUtil.lmdIvk;
import static util.evtdrv.EvtUtil.iniEvtHdrCtnr;


import static util.orm.HbntExt.migrateSql;
import static util.tx.TransactMng.beginx;
import static util.tx.TransactMng.commitx;
import static util.tx.dbutil.setField;
//import static cfg.IocPicoCfg.iniIocContainr;
//  System.out.flush();  // 刷新输出缓冲区
//            System.err.flush();  // 刷新输出缓冲区
//@SolonMain
//Scan("apiUsr")
public class MainApp {



    /**
     * -Ddbcfg=/cfg/dbcfg.ini
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Throwable {
        System.setProperty("jdk.net.spi.nameservice.provider.1", "default");

        //    ovrtTEst=true;//todo cancel if test ok
        iniDbNcfg();
        startTmr();

        lmdIvk(MainStart.class,null);
        //cfg。 auth mode =jwt ,,,in apigateway




//

    }

    private static void startTmr() {
        tmrTask();
        Scheduler scheduler = new Scheduler();
        //每小时的第一分执行  it.sauronsoftware.cron4j.Scheduler;
        scheduler.schedule("1 * * * *", () ->
        {
            tmrTask();


        });

        scheduler.start();

    }

    private static void tmrTask() {
        try{
            System.out.println("每小时的第一分执行 执行定时任务: " + System.currentTimeMillis());
            beginx();
            generStatmtCurMth();
            geneStatmtTodateType();
            commitx();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private static void t1() {
//        Object AddMoneyToWltService1 = getBeanFrmSpr(AddMoneyToWltService.class);
      //  System.out.println("AddMoneyToWltService is :"+ AddMoneyToWltService1.getClass());

//
       //  Object bean = getBeanFrmSpr(RechargeCallbackHdr.class);
//        setField(bean,"addMoneyToWltService1", AddMoneyToWltService1);
//
//        System.out.println("bean.addMoneyToWltService1::"+getField(bean,"addMoneyToWltService1") );

//     //   bean.addMoneyToWltService1= (util.Iservice) AddMoneyToWltService1;
//      //  injectAll4spr(bean);
//        System.out.println("HttpHandler is:"+ bean);
    }





    public static void openMap4test() {

        //  drvMap.put("com.mysql.cj.jdbc.Driver","org.h2.Driver");

    }

    static {
        try {
            iniContnr4cfgfile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        //   saveUrlOrdChrg
    }


}
