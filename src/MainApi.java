import apiAcc.RechargeHdr;
import apiAcc.QueryOrdChrgHdr;
import apiCms.QryTeamHdr;
import apiOrdBet.AddOrdBetHdr;
import apiOrdBet.QryOrdBetHdr;
import apiUsr.LoginHdr;
import apiUsr.QueryUsrHdr;
import apiUsr.RegHandler;
import apiUsr.UserCentrHdr;
import apis.*;
import cfg.IocPicoCfg;
import com.sun.net.httpserver.HttpServer;
import org.noear.solon.annotation.SolonMain;
import org.picocontainer.MutablePicoContainer;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;

import static apis.BaseHdr.iniCfgFrmCfgfile;
import static cfg.WebSvr.start;
import static util.SprUtil.getBeanFrmSpr;
//import static cfg.IocPicoCfg.iniIocContainr;

@SolonMain
@ComponentScan("apiUsr")
public class MainApi {
    public static void main(String[] args) throws IOException, SQLException {

        start();
    }

    public static void openMap4test() {

        //  drvMap.put("com.mysql.cj.jdbc.Driver","org.h2.Driver");

    }

    static {
        iniCfgFrmCfgfile();
        //   saveUrlOrdChrg
    }


}
