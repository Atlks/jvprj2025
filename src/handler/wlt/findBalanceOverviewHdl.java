package handler.wlt;


import handler.ivstAcc.dto.QueryDto;
import lombok.NoArgsConstructor;
import model.OpenBankingOBIE.AccountSubType;
import model.OpenBankingOBIE.Account;
import model.wlt.BalanceOverview;
import util.tx.findByIdExptn_CantFindData;

import static cfg.Containr.sessionFactory;
import static cfg.MainStart.iniAccInsFdPool_IfNotExist;
import static handler.acc.IniAcc.iniTwoWlt;
import static util.acc.AccUti.*;
import static util.tx.HbntUtil.findByHerbinate;

@NoArgsConstructor
public class findBalanceOverviewHdl {

        /**
         *  aws fmt btr than azure,
         * 还是需要返回apires的，因为可能需要包含errcode
         * 但是好像可以返回err with errcode just ok...
         * //todo dft return obj is warp in apigtwyRes
         * //all ret json
         * name is        obj  run(dto) is ok...
         * @param reqdto
         * @return Object apigateWayWarp obj
         */
        public Object handleRequest(QueryDto reqdto) throws findByIdExptn_CantFindData {
            iniTwoWlt(reqdto.uname);
            iniAccInsFdPool_IfNotExist(null);
            String accid=getAccid(AccountSubType.EMoney.name(), reqdto.uname);
            Account account = findByHerbinate(Account.class,accid,sessionFactory.getCurrentSession());
var accYl=findByHerbinate(Account.class, getAccId4ylwlt(reqdto.uname) ,sessionFactory.getCurrentSession());
    var acc_insFdpool=findByHerbinate (Account.class,  getAccId(AccountSubType.insFdPl.name(), sysusrName),sessionFactory.getCurrentSession());
            BalanceOverview balanceOverview = new BalanceOverview();
            balanceOverview.accBalance =account.interim_Available_Balance;
            balanceOverview.accInvst_balance =accYl.interim_Available_Balance;
            balanceOverview.InsFdPool_balance=acc_insFdpool.interim_Available_Balance;
            return balanceOverview;
        }

    }
