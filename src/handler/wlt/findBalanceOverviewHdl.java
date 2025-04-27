package handler.wlt;


import handler.ylwlt.dto.QueryDto;
import lombok.NoArgsConstructor;
import model.OpenBankingOBIE.AccountSubType;
import model.OpenBankingOBIE.AccountType;
import model.OpenBankingOBIE.Accounts;
import model.wlt.BalanceOverview;
import util.tx.findByIdExptn_CantFindData;

import static cfg.Containr.sessionFactory;
import static util.acc.AccUti.getAccId4ylwlt;
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
            Accounts account = findByHerbinate(Accounts.class,reqdto.uname,sessionFactory.getCurrentSession());
var accYl=findByHerbinate(Accounts.class, getAccId4ylwlt(reqdto.uname) ,sessionFactory.getCurrentSession());
    var acc_insFdpool=findByHerbinate (Accounts.class, AccountSubType.uke_ins_fd_pool.name() ,sessionFactory.getCurrentSession());
            BalanceOverview balanceOverview = new BalanceOverview();
            balanceOverview.balance=account.availableBalance;
            balanceOverview.accYlwlt_balance=accYl.availableBalance;
            balanceOverview.InsFdPool_balance=acc_insFdpool.availableBalance;
            return balanceOverview;
        }

    }
