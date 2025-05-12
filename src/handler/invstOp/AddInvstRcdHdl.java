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
import util.tx.TransactMng;
import util.tx.findByIdExptn_CantFindData;

import java.math.BigDecimal;
import java.util.List;

import static cfg.Containr.sessionFactory;
import static handler.uti.TestUti.bftst;
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
        if(dto.investmentType== TransactionCode.invstProfit)
        {

            Session session = sessionFactory.getCurrentSession();

            //add foreach per mem ,ivst acc
            addDiv2perUsr(dto, session);


            //updt acc systm ivstAcc
            String accId = getAccId(AccountSubType.GeneralInvestment.name(), AccUti.sysusrName);
            Account sysAccIvst=findByHerbinate(Account.class, accId, session);
            BigDecimal amount2sysIvstAcc = dto.getAmount().multiply(BigDecimal.valueOf(0.2));
            sysAccIvst.setInterim_Available_Balance(sysAccIvst.getInterim_Available_Balance().add(amount2sysIvstAcc));

            sysAccIvst.setInterimBookedBalance(sysAccIvst.getInterimBookedBalance().add(amount2sysIvstAcc));
            mergeByHbnt(sysAccIvst, session);


            //----add fdpool
            BigDecimal amount2sysFdpoolAcc = dto.getAmount().multiply(BigDecimal.valueOf(0.8));
            addMoney2fdpool(amount2sysFdpoolAcc);

            //

            //---add ord
            dto.setFundFlowDirection(amount2sysIvstAcc.toString()+"转入盈利钱包,"+amount2sysFdpoolAcc.toString()+"转入保险资金池");
            persistByHibernate(dto, session);


            //add tx lg

        }

        return (dto);
    }

    public static void main(String[] args) throws Throwable {
        bftst();
        InvestmentOpRecord dto=new InvestmentOpRecord();
        dto.investmentType= TransactionCode.invstProfit;
        dto.setAmount(BigDecimal.valueOf(0)); ;

        new AddInvstRcdHdl().handleRequest(dto);


        TransactMng.commitTsact();

    }

    private void addMoney2fdpool(BigDecimal amount2sysFdpoolAcc) throws findByIdExptn_CantFindData {
        Session session = sessionFactory.getCurrentSession();
        String accId = getAccId(AccountSubType.insFdPl.name(), AccUti.sysusrName);
        Account sysAccIvst=findByHerbinate(Account.class, accId, session);

        sysAccIvst.setInterim_Available_Balance(sysAccIvst.getInterim_Available_Balance().add(amount2sysFdpoolAcc));

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
            a.setInterim_Available_Balance(BigDecimal.ZERO);
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
            a.setInterim_Available_Balance(BigDecimal.ZERO);
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
                BigDecimal rate=accIvst.getInterim_Available_Balance().divide(sysEmnyBls);
                BigDecimal mydiv=rate.multiply(param.amount);
                accIvst.setInterim_Available_Balance(accIvst.getInterim_Available_Balance().add(mydiv));
                accIvst.setInterimBookedBalance(accIvst.getInterimBookedBalance().add(mydiv));
                mergeByHbnt(accIvst, session);


                //add lgtx
                Transaction txr=new Transaction();
                txr.transactionId="div_"+now();
                txr.transactionCode= TransactionCode.invstProfit.name();
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
        return a.getInterim_Available_Balance();

    }


    public List<Account> getAccountsWithBalanceGreaterThanOne(Session session) {
        String sql = "SELECT * FROM account WHERE interim_available_balance > :amount";
        Query<Account> query = session.createNativeQuery(sql, Account.class);
        query.setParameter("amount", 1);
        return query.getResultList();
    }
}
