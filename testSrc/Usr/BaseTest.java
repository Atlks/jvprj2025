package Usr;

import cfg.Containr;
import cfg.MainStart;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.io.FileNotFoundException;

import static cfg.IniCfg.iniContnr4cfgfile;
import static cfg.MainStart.iniContnr;
import static util.algo.CallUtil.callTry;
import static util.evtdrv.EvtUtil.iniEvtHdrCtnr;
import static util.tx.TransactMng.commitTsact;
import static util.tx.TransactMng.openSessionBgnTransact;

public abstract class BaseTest {
    @BeforeAll
    static void globalSetup() throws  Exception {
        System.out.println("所有测试类共用的初始化");
        Containr.testUnitMode=true;
        iniContnr4cfgfile();

        callTry(MainStart::fxSql);
        new MainStart().sessionFactory();//ini sessFctr ..


        //ini contnr 4cfg,, svrs
        //ioc ini
        iniContnr();
        iniEvtHdrCtnr();
    }


    @BeforeEach
    public void setupBefEach() throws Exception {
        openSessionBgnTransact();

    }

    @AfterEach
    public void setupAftEach() throws Exception {
        commitTsact();
    }

}
