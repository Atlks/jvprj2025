package ztest;

import api.ylwlt.BizFun;
import cfg.Containr;
import cfg.MainStart;
import handler.statmt.CrudFun;
import model.OpenBankingOBIE.Account;
import model.OpenBankingOBIE.AccountSubType;
import model.OpenBankingOBIE.Balance;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import util.model.openbank.BalanceTypes;
import util.tx.findByIdExptn_CantFindData;

import java.math.BigDecimal;

import static cfg.IniCfg.iniContnr4cfgfile;
import static cfg.MainStart.iniContnr;
import static handler.acc.AccService.updateAccSetBlsZero;
import static handler.acc.AccService.updtAccSetBls;
import static handler.balance.BlsSvs.getBlsid;
import static util.acc.AccUti.getAccid;
import static util.algo.CallUtil.callTry;
import static util.evtdrv.EvtUtil.iniEvtHdrCtnr;
import static util.model.openbank.BalanceTypes.interimAvailable;
import static util.model.openbank.BalanceTypes.interimBooked;
import static util.orm.HbntExt.migrateSql;
import static util.tx.HbntUtil.*;
import static util.tx.TransactMng.beginx;
import static util.tx.TransactMng.commitx;

public class clrBls {

    public static void main(String[] args) throws Exception, findByIdExptn_CantFindData {
        globalSetup();
        setupBefEach();
        String uname="uuuu6666";
        String accid=getAccid(uname, AccountSubType.EMoney);
        accid=getAccid(uname, AccountSubType.GeneralInvestment);
      //  updateAccSetBlsZero(accid);
      //  updateAccSetBlsZero(getAccid(uname, AccountSubType.GeneralInvestment));

        updtAccSetBls(getAccid(uname, AccountSubType.agtCms), BigDecimal.valueOf(100));

        setupAftEach();

    }



    static void globalSetup() throws  Exception {
        System.out.println("所有测试类共用的初始化");
        Containr.testUnitMode=true;//for scan claz in from scrDir mode
        iniContnr4cfgfile();

        callTry(() -> migrateSql());
        new MainStart().sessionFactory();//ini sessFctr ..


        //ini contnr 4cfg,, svrs
        //ioc ini
        iniContnr();
        iniEvtHdrCtnr();
    }

   // @BeforeEach
    public static void setupBefEach() throws Exception {
        beginx();

    }
//
    // @AfterEach
    public static void setupAftEach() throws Exception {
        commitx();
    }
}
