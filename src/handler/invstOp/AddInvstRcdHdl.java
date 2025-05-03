package handler.invstOp;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.OpenBankingOBIE.*;
import model.opmng.InvestmentOpRecord;

import org.hibernate.Session;
import org.hibernate.query.Query;
import util.acc.AccUti;
import util.tx.findByIdExptn_CantFindData;

import java.math.BigDecimal;
import java.util.List;

import static cfg.Containr.sessionFactory;
import static java.time.LocalTime.now;
import static util.acc.AccUti.getAccId;
import static util.tx.HbntUtil.*;


@PermitAll
@Path("/admin/InvstRcd/AddInvstRcdHdl")

@NoArgsConstructor
@Data
public class AddInvstRcdHdl   {
    /**
     * @param dto

     * @return
     * @throws Throwable
     */

    public Object handleRequest(InvestmentOpRecord dto) throws Throwable {

        //yinli
        if(dto.investmentType== TransactionCodes.DIV)
        {
            //---add ord
            Session session = sessionFactory.getCurrentSession();
            persistByHibernate(dto, session);

            //add foreach per mem ,ivst acc
            addDiv2perUsr(dto, session);


            //updt acc systm ivstAcc
            String accId = getAccId(AccountSubType.GeneralInvestment.name(), AccUti.sysusrName);
            Account sysAccIvst=findByHerbinate(Account.class, accId, session);
            BigDecimal amount2sysIvstAcc = dto.getAmount().multiply(BigDecimal.valueOf(0.2));
            sysAccIvst.setInterimAvailableBalance(sysAccIvst.getInterimAvailableBalance().add(amount2sysIvstAcc));

            sysAccIvst.setInterimBookedBalance(sysAccIvst.getInterimBookedBalance().add(amount2sysIvstAcc));
            mergeByHbnt(sysAccIvst, session);


            //----add fdpool
            BigDecimal amount2sysFdpoolAcc = dto.getAmount().multiply(BigDecimal.valueOf(0.8));
            addMoney2fdpool(amount2sysFdpoolAcc);

            //

            //add tx lg

        }

        return (dto);
    }

    private void addMoney2fdpool(BigDecimal amount2sysFdpoolAcc) throws findByIdExptn_CantFindData {
        Session session = sessionFactory.getCurrentSession();
        String accId = getAccId(AccountSubType.uke_ins_fd_pool.name(), AccUti.sysusrName);
        Account sysAccIvst=findByHerbinate(Account.class, accId, session);

        sysAccIvst.setInterimAvailableBalance(sysAccIvst.getInterimAvailableBalance().add(amount2sysFdpoolAcc));

        sysAccIvst.setInterimBookedBalance(sysAccIvst.getInterimBookedBalance().add(amount2sysFdpoolAcc));
        mergeByHbnt(sysAccIvst, session);
    }

    public  static  void iniSysEmnyAccIfNotExst(Session session)   {
        try{
            String accId = getAccId(AccountSubType.EMoney.name(), AccUti.sysusrName);
            Account a=findByHerbinate(Account.class, accId, session);

        }catch(findByIdExptn_CantFindData e)
        {
            String accId = getAccId(AccountSubType.EMoney.name(), AccUti.sysusrName);
            Account a= new Account(accId);
            a.accountType=AccountType.BUSINESS;
            a.setInterimAvailableBalance(BigDecimal.ZERO);
            a.setInterimBookedBalance(BigDecimal.ZERO);
            persistByHibernate(a, session);
        }

    }
    public  static  void iniSysInvstAccIfNotExst(Session session)  {

        try{
            String accId = getAccId(AccountSubType.GeneralInvestment.name(), AccUti.sysusrName);
            Account a=findByHerbinate(Account.class, accId, session);
        } catch (findByIdExptn_CantFindData e) {
            String accId = getAccId(AccountSubType.GeneralInvestment.name(), AccUti.sysusrName);
            Account a= new Account(accId);
            a.accountType=AccountType.BUSINESS;
            a.setInterimAvailableBalance(BigDecimal.ZERO);
            a.setInterimBookedBalance(BigDecimal.ZERO);
            persistByHibernate(a, session);
        }


    }

    private void addDiv2perUsr(InvestmentOpRecord param, Session session) throws findByIdExptn_CantFindData {
        BigDecimal sysEmnyBls=getSysAccEmnyBls(session);
        if(sysEmnyBls.compareTo(BigDecimal.ZERO)<=0)
            return;
        List<Account> accs= getAccountsWithBalanceGreaterThanOne(session);
        for(Account acc:accs){
            try{
                //updt acc systm ivstAcc
                String accId = getAccId(AccountSubType.GeneralInvestment.name(), acc.accountOwner);

                Account accIvst=findByHerbinate(Account.class, accId, session);
                BigDecimal rate=accIvst.getInterimAvailableBalance().divide(sysEmnyBls);
                BigDecimal mydiv=rate.multiply(param.amount);
                accIvst.setInterimAvailableBalance(accIvst.getInterimAvailableBalance().add(mydiv));
                accIvst.setInterimBookedBalance(accIvst.getInterimBookedBalance().add(mydiv));
                mergeByHbnt(accIvst, session);


                //add lgtx
                Transaction txr=new Transaction();
                txr.transactionId="div_"+now();
                txr.transactionCode=TransactionCodes.DIV;
                txr.accountId=accId;
                txr.accountOwner=acc.accountOwner;
                txr.creditDebitIndicator= CreditDebitIndicator.CREDIT;
                txr.amount=mydiv;
                persistByHibernate(txr, session);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private BigDecimal getSysAccEmnyBls(Session session) throws findByIdExptn_CantFindData {
        String accId = getAccId(AccountSubType.EMoney.name(), AccUti.sysusrName);
        Account a=findByHerbinate(Account.class, accId, session);
        return a.getInterimAvailableBalance();

    }


    public List<Account> getAccountsWithBalanceGreaterThanOne(Session session) {
        String sql = "SELECT * FROM account WHERE interim_available_balance > :amount";
        Query<Account> query = session.createNativeQuery(sql, Account.class);
        query.setParameter("amount", 1);
        return query.getResultList();
    }
}
