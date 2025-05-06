package handler.wlt;


import handler.ivstAcc.dto.QueryDto;
import lombok.NoArgsConstructor;
import model.OpenBankingOBIE.AccountSubType;
import model.OpenBankingOBIE.Account;
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
            Account account = findByHerbinate(Account.class,reqdto.uname,sessionFactory.getCurrentSession());
var accYl=findByHerbinate(Account.class, getAccId4ylwlt(reqdto.uname) ,sessionFactory.getCurrentSession());
    var acc_insFdpool=findByHerbinate (Account.class, AccountSubType.insFdPl.name() ,sessionFactory.getCurrentSession());
            BalanceOverview balanceOverview = new BalanceOverview();
            balanceOverview.accBalance =account.InterimAvailableBalance;
            balanceOverview.accInvst_balance =accYl.InterimAvailableBalance;
            balanceOverview.InsFdPool_balance=acc_insFdpool.InterimAvailableBalance;
            return balanceOverview;
        }

    }
