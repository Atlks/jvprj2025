package handler.wlt;

import cfg.MainStart;
import model.OpenBankingOBIE.Account;
import model.OpenBankingOBIE.AccountSubType;
import org.hibernate.Session;
import util.acc.AccUti;
import util.tx.TransactMng;
import util.tx.findByIdExptn_CantFindData;

import java.math.BigDecimal;

import static cfg.Containr.sessionFactory;
// static handler.uti.TestUti.bftst;
import static cfg.MainStart.iniSysAcc;
import static handler.uti.TestUti.bftst;
import static util.acc.AccUti.getAccId;
import static util.tx.HbntUtil.findById;
import static util.tx.HbntUtil.mergex;

public class AddMoney2SysEmnyAccHdl {

    private void handleRequest(AddMoneyDto dto) throws findByIdExptn_CantFindData {
        Session session = sessionFactory.getCurrentSession();
        String accId = getAccId(AccountSubType.insFdPl.name(), AccUti.sysusrName);
        Account sysAccIvst= findById(Account.class, accId, session);

        sysAccIvst.setInterim_Available_Balance(sysAccIvst.getInterim_Available_Balance().add(dto.amt));

        sysAccIvst.setInterimBookedBalance(sysAccIvst.getInterimBookedBalance().add(dto.amt));
        mergex(sysAccIvst, session);
    }

    public static void main(String[] args) throws Throwable {

        new MainStart().sessionFactory(); // initialize
        iniSysAcc();

        bftst();
        AddMoneyDto dto=new AddMoneyDto();
        dto.amt= BigDecimal.valueOf(888888);
             setMny2sysEmnAcc(dto);


        TransactMng.commitx();

    }

    private static void setMny2sysEmnAcc(AddMoneyDto dto) throws findByIdExptn_CantFindData {

        Session session = sessionFactory.getCurrentSession();
        String accId = getAccId(AccountSubType.insFdPl.name(), AccUti.sysusrName);
        Account sysAccIvst= findById(Account.class, accId, session);

        sysAccIvst.setInterim_Available_Balance(dto.amt);

        sysAccIvst.setInterimBookedBalance( (dto.amt));
        mergex(sysAccIvst, session);
    }
}
