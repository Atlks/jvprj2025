//import handler.agt.AgtHdl;
//import handler.rechg.RechargeCallbackHdr;

//import org.noear.solon.annotation.SolonMain;
import cfg.MainStart;
import lombok.SneakyThrows;
import model.OpenBankingOBIE.AccountSubType;
import model.OpenBankingOBIE.AccountType;
import model.OpenBankingOBIE.Account;
import org.hibernate.Session;

import util.log.ConsoleInterceptor;
import util.tx.findByIdExptn_CantFindData;
//import service.AddRchgOrdToWltService;

import java.io.FileNotFoundException;

// static cfg.AppConfig.sessionFactory;
//import static cfg.Containr.evtlist4reg;
import static cfg.Containr.sessionFactory;
import static cfg.MainStart.iniContnr;
import static cfg.IniCfg.iniContnr4cfgfile;
import static cfg.MainStart.iniSysAcc;
import static cfg.WebSvr.*;
import static handler.invstOp.AddInvstRcdHdl.iniSysEmnyAccIfNotExst;
import static handler.invstOp.AddInvstRcdHdl.iniSysInvstAccIfNotExst;
import static java.time.LocalTime.now;
import static util.acc.AccUti.getAccId;
import static util.acc.AccUti.sysusrName;
import static util.evtdrv.EvtUtil.iniEvtHdrCtnr;


import static util.tx.HbntUtil.findByHerbinate;
import static util.tx.HbntUtil.persistByHibernate;
import static util.tx.dbutil.setField;
//import static cfg.IocPicoCfg.iniIocContainr;
//  System.out.flush();  // 刷新输出缓冲区
//            System.err.flush();  // 刷新输出缓冲区
//@SolonMain
//Scan("apiUsr")
public class MainApi {



    /**
     * -Ddbcfg=/cfg/dbcfg.ini
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {


        //    ovrtTEst=true;//todo cancel if test ok
        ConsoleInterceptor.init();// log
        System.out.println("fun main(), now= " + now());


        start();
        //cfg auth mode =jwt ,,,in apigateway




//        sleep(3000);
        System.out.println("--------------------\n\n main()");
        System.out.println("main() exe finish, " + now());
        AutoRestartApp.main(null);

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

    /**
     *
     * @throws Exception
     */
    @SneakyThrows
    public static void start() throws Exception {
        //--------ini saveurlFrm Cfg

         new MainStart().sessionFactory();//ini sessFctr
        //ini contnr 4cfg,, svrs
        iniContnr();
        iniEvtHdrCtnr();

     //   evtlist4reg.add(new AgtHdl()::regEvtHdl);
      //  AgtHdl

        //================== 创建 HTTP 服务器，监听端口8080
        iniRestPathMap();


        //------ini sys acc
        iniSysAcc();


        startWebSrv();
        System.out.println(11);
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
