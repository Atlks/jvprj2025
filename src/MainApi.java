import api.wlt.RechargeCallbackHdr;
import org.noear.solon.annotation.SolonMain;
import org.springframework.context.annotation.ComponentScan;
import service.wlt.AddMoneyToWltService;
//import service.AddRchgOrdToWltService;

import static cfg.MyCfg.iniCfgFrmCfgfile;
import static cfg.WebSvr.start;
import static util.proxy.SprUtil.getBeanFrmSpr;
import static util.tx.dbutil.setField;
//import static cfg.IocPicoCfg.iniIocContainr;

@SolonMain
@ComponentScan("apiUsr")
public class MainApi {
    public static void main(String[] args) throws Exception {
    //    ovrtTEst=true;//todo cancel if test ok
        start();

//        sleep(3000);
        System.out.println("--------------------\n\n main()");
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
        AutoRestartApp.main(null);
    }

    private static void sleep(int i) throws InterruptedException {
         Thread.sleep(i);
    }

    public static void openMap4test() {

        //  drvMap.put("com.mysql.cj.jdbc.Driver","org.h2.Driver");

    }

    static {
        iniCfgFrmCfgfile();
        //   saveUrlOrdChrg
    }


}
