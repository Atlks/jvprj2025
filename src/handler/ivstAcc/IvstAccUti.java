package handler.ivstAcc;

import model.OpenBankingOBIE.Account;
import util.tx.findByIdExptn_CantFindData;

// static cfg.AppConfig.sessionFactory;
import static cfg.Containr.sessionFactory;
import static handler.wthdr.WthdReqHdl.iniYlwlt;
import static util.acc.AccUti.getAccId4ylwlt;
import static util.tx.HbntUtil.findByHerbinate;

public class IvstAccUti {

    public static void newIvstWltIfNotExist(String uname1) {

        try{
            var wlt=findByHerbinate(Account.class, getAccId4ylwlt(uname1) , sessionFactory.getCurrentSession());
        } catch (findByIdExptn_CantFindData e) {

            iniYlwlt(  uname1);
        }
    }
}
