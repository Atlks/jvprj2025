import apiAcc.CompleteChargeCallbackHdr;
import org.noear.solon.annotation.SolonMain;
import org.springframework.context.annotation.ComponentScan;
import service.WltService;

import static cfg.MyCfg.iniCfgFrmCfgfile;
import static cfg.WebSvr.start;
import static util.SprUtil.getBeanFrmSpr;
//import static cfg.IocPicoCfg.iniIocContainr;

@SolonMain
@ComponentScan("apiUsr")
public class MainApi {
    public static void main(String[] args) throws Exception {

        start();
        System.out.println("--------------------\n\n main()");
        System.out.println("wsbet:"+getBeanFrmSpr(WltService.class));
        System.out.println("CompleteChargeCallbackHdr:"+getBeanFrmSpr(CompleteChargeCallbackHdr.class));
        AutoRestartApp.main(null);
    }

    public static void openMap4test() {

        //  drvMap.put("com.mysql.cj.jdbc.Driver","org.h2.Driver");

    }

    static {
        iniCfgFrmCfgfile();
        //   saveUrlOrdChrg
    }


}
