import api.wlt.RechargeCallbackHdr;
import biz.Containr;
import jakarta.security.enterprise.identitystore.IdentityStore;
import org.noear.solon.annotation.SolonMain;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import service.wlt.AddMoneyToWltService;
import util.algo.ChooseEvtPublshr;
import util.auth.SecurityContextImp4jwt;
//import service.AddRchgOrdToWltService;

import static Interfs.IRegHandler.SAM4regLgn;
import static cfg.MyCfg.iniCfgFrmCfgfile;
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
    public static void main(String[] args) throws Exception {
    //    ovrtTEst=true;//todo cancel if test ok

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

        iniContnr();
        iniEvtHdrCtnr();

        //================== 创建 HTTP 服务器，监听端口8080
        iniRestPathMap();
        startWebSrv();
    }

    /**
     * for ban
     * @throws Exception
     */
    public static void iniContnr() throws Exception {
        //@NonNull
        iniCfgFrmCfgfile();
        //---------------ini contarin
        cfg.IocSpringCfg.iniIocContainr4spr();
        Containr.SecurityContext1=new SecurityContextImp4jwt();
        Containr.chooseEvtPblshr=  new ChooseEvtPublshr();

    }



    public static void openMap4test() {

        //  drvMap.put("com.mysql.cj.jdbc.Driver","org.h2.Driver");

    }

    static {
        iniCfgFrmCfgfile();
        //   saveUrlOrdChrg
    }


}
