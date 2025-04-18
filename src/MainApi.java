import handler.wlt.RechargeCallbackHdr;
import biz.Containr;
import cfg.AppConfig;
import org.noear.solon.annotation.SolonMain;
import org.springframework.context.annotation.ComponentScan;
import service.wlt.AddMoneyToWltService;
import util.algo.ChooseEvtPublshr;
import util.auth.SecurityContextImp4jwt;
import util.log.ConsoleInterceptor;
//import service.AddRchgOrdToWltService;

import java.io.FileNotFoundException;

import static cfg.MyCfg.iniContnr;
import static cfg.MyCfg.iniContnr4cfgfile;
import static cfg.WebSvr.*;
import static util.evtdrv.EvtUtil.iniEvtHdrCtnr;

import static util.proxy.SprUtil.getBeanFrmSpr;
import static util.tx.dbutil.setField;
//import static cfg.IocPicoCfg.iniIocContainr;
//  System.out.flush();  // 刷新输出缓冲区
//            System.err.flush();  // 刷新输出缓冲区
@SolonMain
@ComponentScan("apiUsr")
public class MainApi {

    /**
     * -Ddbcfg=/cfg/dbcfg.ini
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
    //    ovrtTEst=true;//todo cancel if test ok
        ConsoleInterceptor.init();// log
        start();

//        sleep(3000);
        System.out.println("--------------------\n\n main()");
        t1();
        AutoRestartApp.main(null);
    }

    private static void t1() {
        Object AddMoneyToWltService1 = getBeanFrmSpr(AddMoneyToWltService.class);
        System.out.println("AddMoneyToWltService is :"+ AddMoneyToWltService1.getClass());

//
        Object bean = getBeanFrmSpr(RechargeCallbackHdr.class);
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
    public static void start() throws Exception {
        //--------ini saveurlFrm Cfg

        new AppConfig().sessionFactory();//ini sessFctr
        //ini contnr 4cfg,, svrs
        iniContnr();
        iniEvtHdrCtnr();

        //================== 创建 HTTP 服务器，监听端口8080
        iniRestPathMap();
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
