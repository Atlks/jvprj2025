import apiAcc.CompleteChargeCallbackHdr;
import com.sun.net.httpserver.HttpHandler;
import org.noear.solon.annotation.SolonMain;
import org.springframework.context.annotation.ComponentScan;
import service.AddMoneyToWltService;
import service.AddMoneyToYLWltService;
//import service.AddRchgOrdToWltService;

import static cfg.MyCfg.iniCfgFrmCfgfile;
import static cfg.WebSvr.start;
import static util.SprUtil.getBeanFrmSpr;
import static util.SprUtil.injectAll4spr;
import static util.dbutil.setField;
//import static cfg.IocPicoCfg.iniIocContainr;

@SolonMain
@ComponentScan("apiUsr")
public class MainApi {
    public static void main(String[] args) throws Exception {

        start();

//        sleep(3000);
//        System.out.println("--------------------\n\n main()");
//        Object AddMoneyToWltService1 = getBeanFrmSpr(AddMoneyToWltService.class);
//        System.out.println("AddMoneyToWltService is :"+ AddMoneyToWltService1.getClass());
//
//       // CompleteChargeCallbackHdr bean = IocSpringCfg.context.getBean(CompleteChargeCallbackHdr.class);
//        //NoSuchBeanDefinitionException: No qualifying bean of type 'apiAcc.CompleteChargeCallbackHdr
//
//
//        Object bean = getBeanFrmSpr(CompleteChargeCallbackHdr.class);
//        setField(bean,"addMoneyToYLWltService1", getBeanFrmSpr(AddMoneyToYLWltService.class));
//     //   bean.addMoneyToWltService1= (service.Iservice) AddMoneyToWltService1;
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
