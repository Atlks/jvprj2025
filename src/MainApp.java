//import handler.agt.AgtHdl;
//import handler.rechg.RechargeCallbackHdr;

//import org.noear.solon.annotation.SolonMain;
import cfg.MainStart;
import it.sauronsoftware.cron4j.Scheduler;
import lombok.SneakyThrows;
import model.usr.UsrMapper;
import org.apache.commons.dbcp2.BasicDataSource;

import org.apache.ibatis.session.SqlSession;
import orgx.uti.context.ProcessContext;
import util.log.ConsoleInterceptor;

//import service.AddRchgOrdToWltService;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

// static cfg.AppConfig.sessionFactory;
//import static cfg.Containr.evtlist4reg;
import static cfg.CfgSvs.buildSessionFactory;
import static cfg.IniCfg.iniContnr4cfgfile;
import static cfg.MainStart.*;
import static cfg.WebSvr.*;
import static handler.statmt.StatmtService.geneStatmtTodateType;
import static handler.statmt.StatmtService.generStatmtCurMth;
import static java.time.LocalTime.now;
import static orgx.uti.context.ThreadContext.currSession;
import static util.algo.CallUtil.callTry;
import static util.algo.CallUtil.lmdIvk;
import static util.evtdrv.EvtUtil.iniEvtHdrCtnr;


import static util.orm.HbntExt.migrateSql;
import static util.tx.TransactMng.*;
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
//        List<Class> mapperClzs=new ArrayList<>();
//        mapperClzs.add(UsrMapper.class);
//        ProcessContext.sqlSessionFactory=buildSessionFactory(mapperClzs);
//        try (SqlSession session = ProcessContext.sqlSessionFactory.openSession()) {
//           // Object result = session.selectList("select 1");
//            // or execute insert/update/delete
//        }

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
            try{
                tmrTask();
            }catch(Exception e){
                e.printStackTrace();
            }



        });

        scheduler.start();

    }

    private static void tmrTask() {
        try{
            System.out.println("每小时的第一分执行 执行定时任务: " + System.currentTimeMillis());
            beginx();
            generStatmtCurMth();

            commitx();
        }catch (Exception e){
            rollbackTx();
            e.printStackTrace();
        }finally {
            closeCurrenSession();
        }

        try{
            System.out.println("每小时的第一分执行 执行定时任务: " + System.currentTimeMillis());
            beginx();
            geneStatmtTodateType();
            commitx();
        }catch (Exception e){
            rollbackTx();
            e.printStackTrace();
        }finally {
            closeCurrenSession();
        }



    }

    private static void closeCurrenSession() {
        try{
            currSession.get().flush();
            currSession.get().close();
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
