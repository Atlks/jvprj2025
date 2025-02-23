import org.noear.solon.annotation.SolonMain;
import org.springframework.context.annotation.ComponentScan;

import static biz.BaseHdr.iniCfgFrmCfgfile;
import static cfg.WebSvr.start;
//import static cfg.IocPicoCfg.iniIocContainr;

@SolonMain
@ComponentScan("apiUsr")
public class MainApi {
    public static void main(String[] args) throws Exception {

        start();
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
