package handler.uti;

import cfg.MainStart;
import util.evtdrv.EvtUtil;
import util.tx.TransactMng;

import static cfg.MainStart.iniContnr;

public class TestUti {

    public static void bftst() throws Exception {
        //--------initialize session factory from AppConfig
        new MainStart().sessionFactory(); // initialize sessionFactory

        // initialize container for config and servers
        iniContnr();
        EvtUtil.iniEvtHdrCtnr();

        // Register event handler (commented in Kotlin)
        // Containr.evtlist4reg.add(u -> new AgtHdl().regEvtHdl(u));

        //============begin AOP-style transaction
        TransactMng.openSessionBgnTransact();
    }

}
