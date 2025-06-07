package handler.invstOp;

import api.ylwlt.BizFun;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.constraints.DecimalMin;
import jakarta.ws.rs.Path;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.OpenBankingOBIE.*;
import model.opmng.InvestmentOpRecord;

import org.hibernate.Session;
import org.hibernate.query.Query;
import util.sql.SqlBldr;
import util.acc.AccUti;

import static handler.acc.AccService.sumAmtFromAccWhereSubtypeEqEmoney;
import static util.sql.SqlBldr.*;

import util.tx.HbntUtil;
import util.tx.TransactMng;
import util.tx.findByIdExptn_CantFindData;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static cfg.Containr.sessionFactory;
import static handler.uti.TestUti.bftst;
import static java.time.LocalTime.now;

import static util.acc.AccUti.getAccId;
import static util.tx.HbntUtil.*;
import static util.tx.QueryParamParser.prtEx;


@NoArgsConstructor
@Data
public class AddInvstRcdHdl   {
    /**
     * @param dto

     * @return
     * @throws Throwable
     */
    @PermitAll
    @Path("/apiv1/admin/InvstRcd/AddInvstRcdHdl")

    public Object handleRequest(InvestmentOpRecord dto) throws Throwable {

        //yinli
        if(dto.investmentType.equals(TransactionCode.invstProfit.name()) )
        {

            Session session = sessionFactory.getCurrentSession();

            //add foreach per mem ,ivst acc
            BigDecimal amount2usr = dto.getAmount().multiply(BigDecimal.valueOf(0.2));
            addTx_typeProfit_IntoUsrs(amount2usr, session);


            //updt acc systm ivstAcc..
            String accId_sysInvstAcc = getAccId(AccountSubType.GeneralInvestment.name(), AccUti.sysusrName);
            Account sysAccIvst= findById(Account.class, accId_sysInvstAcc, session);
            BigDecimal amount2sysIvstAcc = dto.getAmount();
            sysAccIvst.setInterim_Available_Balance(sysAccIvst.getInterim_Available_Balance().add(amount2sysIvstAcc));

            sysAccIvst.setInterimBookedBalance(sysAccIvst.getInterimBookedBalance().add(amount2sysIvstAcc));
            mergex(sysAccIvst, session);


            //----add fdpool
            BigDecimal amount2sysFdpoolAcc = dto.getAmount().multiply(BigDecimal.valueOf(0.8));
            addMoney2fdpool(amount2sysFdpoolAcc);

            //

            //---add ord
            dto.setFundFlowDirection(amount2sysIvstAcc.toString()+"转入盈利钱包,"+amount2sysFdpoolAcc.toString()+"转入保险资金池");
            persist(dto, session);


            //add tx lg

        }

        if(dto.investmentType.equals(TransactionCode.invstLoss.name()) )
        {

            opInvstLost(dto);


            //add tx lg

        }

        return (dto);
    }

    @BizFun
    private void opInvstLost(InvestmentOpRecord dto) throws findByIdExptn_CantFindData {
        String accId = getAccId(AccountSubType.insFdPl.name(), AccUti.sysusrName);
        Account sysAccInsFdpl= findById(Account.class, accId);
        BigDecimal needSubBalFrmIfpAcc=calcSubAmtFrmInvsFdPl(dto.amount,sysAccInsFdpl.getInterim_Available_Balance());
        Session session = sessionFactory.getCurrentSession();
        BigDecimal interimAvailableBalance = sysAccInsFdpl.getInterim_Available_Balance();
        //-----sub Ins fdpoool
        Account insFdplAcc=   subAmtUpdtAccWhrFdpl(needSubBalFrmIfpAcc);


        //-----sub amt from Usr acc

        BigDecimal  deff= dto.amount.subtract(interimAvailableBalance);
        //if amtLost>insFdPlBls   ,,need usr pay some
        if(deff.compareTo(BigDecimal.ZERO)>0){
            //add foreach per mem ,ivst acc
            subAmt_typeLoss_IntoUsrs(deff);
        }


        //

        //---add ord amount2sysIvstAcc.toString()+

        dto.setFundFlowDirection("扣除本金钱包,"+ deff.toString()+",扣除保险资金池"+needSubBalFrmIfpAcc.toString());
        persist(dto, session);
    }

    private void subAmt_typeLoss_IntoUsrs(BigDecimal deff) throws findByIdExptn_CantFindData {
        Session session=sessionFactory.getCurrentSession();
        BigDecimal sysEmnyBls=sumAmtFromAccWhereSubtypeEqEmoney();
        System.out.println("sumAmt: "+sysEmnyBls);
        if(sysEmnyBls.compareTo(BigDecimal.ZERO)<=0)
            return;
        List<Account> accs= getAccountsWithBalanceGreaterThanOneAndSubtypeEmny(session);
        for(Account accEmng:accs){
            try{


                //-----------calc rate
                if(accEmng.getInterim_Available_Balance().compareTo(BigDecimal.ZERO)<=0)
                    continue;
                BigDecimal rate= accEmng.getInterim_Available_Balance().divide(sysEmnyBls,2, RoundingMode.HALF_UP);

//                BigDecimal interimAvailableBalance = accIvst.getInterim_Available_Balance();

                //-------updt acc systm ivstAcc
                String accId = getAccId(AccountSubType.GeneralInvestment.name(), accEmng.owner);

                Account accIvst= findById(Account.class, accId, session);
                BigDecimal mydiv=rate.multiply(deff);
                accIvst.setInterim_Available_Balance(accIvst.getInterim_Available_Balance().subtract(mydiv));

                mergex(accIvst, session);


                //add lgtx
                Transaction txr=new Transaction();
                txr.transactionId="invstLoss_"+now();
                txr.setStatus(TransactionStatus.BOOKED);
                txr.setTransactionCode(TransactionCode.invstLoss);
                //   txr.transactionCode= TransactionCode.invstProfit.name();
                txr.accountId=accId;
                txr.owner =accEmng.owner;
                txr.setAddressLine("");
                txr.creditDebitIndicator= CreditDebitIndicator.DEBIT;
                txr.setAmountVldChk(mydiv);
                persist(txr, session);
            } catch (Throwable e) {
                prtEx(e);
            }

        }
    }

//    private void addTx_typeLoss_IntoUsrs(InvestmentOpRecord dto, Session session) {
//    }

    private Account subAmtUpdtAccWhrFdpl(BigDecimal amt) throws findByIdExptn_CantFindData {
        String accId = getAccId(AccountSubType.insFdPl.name(), AccUti.sysusrName);
        Account sysAccInsFdpl= findById(Account.class, accId);
//
//        BigDecimal needSubAmt=calcSubAmtFrmInvsFdPl(dto.amount,sysAccInsFdpl.getInterim_Available_Balance());

            sysAccInsFdpl.setInterim_Available_Balance(sysAccInsFdpl.getInterim_Available_Balance().subtract(amt));



      //  sysAccInsFdpl.setInterimBookedBalance(sysAccInsFdpl.getInterimBookedBalance().add(amount2sysFdpoolAcc));
        mergex(sysAccInsFdpl);
        return sysAccInsFdpl;
    }

    private BigDecimal calcSubAmtFrmInvsFdPl(BigDecimal dto_amount, @DecimalMin(value = "0.00", inclusive = true, message = "余额不能为负数") BigDecimal interimAvailableBalance) {
        if(interimAvailableBalance.compareTo(dto_amount)>=0)
            return dto_amount;
        else
            return  interimAvailableBalance;
    }

    public static void main(String[] args) throws Throwable {
        bftst();
        InvestmentOpRecord dto=new InvestmentOpRecord();
       // dto.investmentType= TransactionCode.invstProfit;
        dto.setAmount(BigDecimal.valueOf(0)); ;

        new AddInvstRcdHdl().handleRequest(dto);


        TransactMng.commitx();

    }

    private void addMoney2fdpool(BigDecimal amount2sysFdpoolAcc) throws findByIdExptn_CantFindData {
        Session session = sessionFactory.getCurrentSession();
        String accId = getAccId(AccountSubType.insFdPl.name(), AccUti.sysusrName);
        Account sysAccInsFdpl= findById(Account.class, accId, session);

        sysAccInsFdpl.setInterim_Available_Balance(sysAccInsFdpl.getInterim_Available_Balance().add(amount2sysFdpoolAcc));

        sysAccInsFdpl.setInterimBookedBalance(sysAccInsFdpl.getInterimBookedBalance().add(amount2sysFdpoolAcc));
        mergex(sysAccInsFdpl, session);
    }

    public  static  void iniSysEmnyAccIfNotExst(Session session)   {
        try{
            String accId = getAccId(AccountSubType.EMoney.name(), AccUti.sysusrName);
            Account a= findById(Account.class, accId, session);

        }catch(findByIdExptn_CantFindData e)
        {
            String accId = getAccId(AccountSubType.EMoney.name(), AccUti.sysusrName);
            Account a= new Account(accId);
            a.accountType=AccountType.BUSINESS;
            a.setInterim_Available_Balance(BigDecimal.ZERO);
            a.setInterimBookedBalance(BigDecimal.ZERO);
          a.setOwner(AccUti.sysusrName);
            persist(a, session);
        }

    }
    public  static  void iniSysInvstAccIfNotExst(Session session)  {

        try{
            String accId = getAccId(AccountSubType.GeneralInvestment.name(), AccUti.sysusrName);
            Account a= findById(Account.class, accId, session);
        } catch (findByIdExptn_CantFindData e) {
            String accId = getAccId(AccountSubType.GeneralInvestment.name(), AccUti.sysusrName);
            Account a= new Account(accId);
            a.accountType=AccountType.BUSINESS;
            a.setInterim_Available_Balance(BigDecimal.ZERO);
            a.setOwner(AccUti.sysusrName);
            a.setInterimBookedBalance(BigDecimal.ZERO);
            persist(a, session);
        }


    }

    private void addTx_typeProfit_IntoUsrs(BigDecimal amt2usrs, Session session) throws findByIdExptn_CantFindData {
        BigDecimal sysEmnyBls=sumAmtFromAccWhereSubtypeEqEmoney();
        System.out.println("sumAmt: "+sysEmnyBls);
        if(sysEmnyBls.compareTo(BigDecimal.ZERO)<=0)
            return;
        List<Account> accs= getAccountsWithBalanceGreaterThanOneAndSubtypeEmny(session);
        for(Account accEmng:accs){
            try{


                //-----------calc rate
                if(accEmng.getInterim_Available_Balance().compareTo(BigDecimal.ZERO)<=0)
                    continue;
                BigDecimal rate= accEmng.getInterim_Available_Balance().divide(sysEmnyBls,20, RoundingMode.HALF_UP);

//                BigDecimal interimAvailableBalance = accIvst.getInterim_Available_Balance();

                //-------updt acc systm ivstAcc
                String accId = getAccId(AccountSubType.GeneralInvestment.name(), accEmng.owner);

                Account accIvst= findById(Account.class, accId, session);
                BigDecimal myProfit=rate.multiply(amt2usrs);
                accIvst.setInterim_Available_Balance(accIvst.getInterim_Available_Balance().add(myProfit));
                accIvst.setInterimBookedBalance(accIvst.getInterimBookedBalance().add(myProfit));
                mergex(accIvst, session);


                //add lgtx
                Transaction txr=new Transaction();
                txr.transactionId="invstPrft_"+now();
                txr.setStatus(TransactionStatus.BOOKED);
                txr.setTransactionCode(TransactionCode.invstProfit);
             //   txr.transactionCode= TransactionCode.invstProfit.name();
                txr.accountId=accId;
                txr.owner =accEmng.owner;
                txr.setAddressLine("");
                txr.creditDebitIndicator= CreditDebitIndicator.CREDIT;
               txr.setAmountVldChk(myProfit);
                persist(txr, session);
            } catch (Throwable e) {
                prtEx(e);
            }

        }
    }



    private BigDecimal getSysAccEmnyBls(Session session) throws findByIdExptn_CantFindData {
        String accId = getAccId(AccountSubType.EMoney.name(), AccUti.sysusrName);
        Account a= findById(Account.class, accId, session);
        return a.getInterim_Available_Balance();

    }


    public List<Account> getAccountsWithBalanceGreaterThanOneAndSubtypeEmny(Session session) {
        String sql = "SELECT * FROM accounts WHERE interim_available_balance > :amount and "
                +getFldName(Account.Fields.accountSubType)+"="+toSqlPrmAsStr(AccountSubType.EMoney.name())
           +" and "+   getFldName(Account.Fields.accountType)+"="+toSqlPrmAsStr(AccountType.PERSONAL.name())
                ;
        Query<Account> query = session.createNativeQuery(sql, Account.class);
        query.setParameter("amount", 1);
        return query.getResultList();
    }
}
