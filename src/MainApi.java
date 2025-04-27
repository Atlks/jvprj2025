import handler.agt.AgtHdl;
//import handler.rechg.RechargeCallbackHdr;
import cfg.AppConfig;
//import org.noear.solon.annotation.SolonMain;
import lombok.SneakyThrows;
import model.OpenBankingOBIE.AccountSubType;
import model.OpenBankingOBIE.AccountType;
import model.OpenBankingOBIE.Accounts;
import org.hibernate.Session;
import org.springframework.context.annotation.ComponentScan;
import service.wlt.AddMoneyToWltService;
import util.log.ConsoleInterceptor;
import util.tx.findByIdExptn_CantFindData;
//import service.AddRchgOrdToWltService;

import java.io.FileNotFoundException;

import static Evt.RegEvt.evtlist4reg;
import static cfg.AppConfig.sessionFactory;
//import static cfg.Containr.evtlist4reg;
import static cfg.MyCfg.iniContnr;
import static cfg.MyCfg.iniContnr4cfgfile;
import static cfg.WebSvr.*;
import static util.evtdrv.EvtUtil.iniEvtHdrCtnr;

import static util.proxy.SprUtil.getBeanFrmSpr;
import static util.tx.HbntUtil.findByHerbinate;
import static util.tx.HbntUtil.persistByHibernate;
import static util.tx.dbutil.setField;
//import static cfg.IocPicoCfg.iniIocContainr;
//  System.out.flush();  // 刷新输出缓冲区
//            System.err.flush();  // 刷新输出缓冲区
//@SolonMain
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
        //cfg auth mode =jwt ,,,in apigateway




//        sleep(3000);
        System.out.println("--------------------\n\n main()");
        t1();
        AutoRestartApp.main(null);
    }

    private static void t1() {
        Object AddMoneyToWltService1 = getBeanFrmSpr(AddMoneyToWltService.class);
        System.out.println("AddMoneyToWltService is :"+ AddMoneyToWltService1.getClass());

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

        new AppConfig().sessionFactory();//ini sessFctr
        //ini contnr 4cfg,, svrs
        iniContnr();
        iniEvtHdrCtnr();

        evtlist4reg.add(new AgtHdl()::regEvtHdl);
      //  AgtHdl

        //================== 创建 HTTP 服务器，监听端口8080
        iniRestPathMap();

        iniAccInsFdPool_IfNotExist("");

        startWebSrv();
        System.out.println(11);
    }


    public static void iniAccInsFdPool_IfNotExist(String uname1) {

        Session session = sessionFactory.getCurrentSession();
       session.getTransaction().begin();
        try{
            var wlt=findByHerbinate(Accounts.class, AccountSubType.uke_ins_fd_pool.name(), session);
        } catch (findByIdExptn_CantFindData e) {

            Accounts acc1=new Accounts(AccountSubType.uke_ins_fd_pool.name());
            // .. acc1.userId= uname1;
         //   acc1.accountId=
            acc1.uname= String.valueOf(AccountSubType.uke_ins_fd_pool);
            acc1.accountType=AccountType.BUSINESS;
            acc1.accountSubType=AccountSubType.uke_ins_fd_pool;
            persistByHibernate(acc1, session);

        }
        session.getTransaction().commit();
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
