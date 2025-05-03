package handler.invstOp;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.OpenBankingOBIE.Account;
import model.OpenBankingOBIE.AccountSubType;
import model.OpenBankingOBIE.TransactionCodes;
import model.opmng.InvestmentOpRecord;
import model.role.CustomRole;

import org.hibernate.Session;
import org.hibernate.query.Query;
import util.acc.AccUti;
import util.serverless.ApiGatewayResponse;
import util.serverless.RequestHandler;
import util.tx.findByIdExptn_CantFindData;

import java.math.BigDecimal;
import java.util.List;

import static cfg.Containr.sessionFactory;
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
            sysAccIvst.setInterimAvailableBalance(sysAccIvst.getInterimAvailableBalance().add(dto.getAmount()));

            sysAccIvst.setInterimBookedBalance(sysAccIvst.getInterimBookedBalance().add(dto.getAmount()));
            mergeByHbnt(sysAccIvst, session);

            //

            //add tx lg

        }

        return (dto);
    }

    private void addDiv2perUsr(InvestmentOpRecord param, Session session) throws findByIdExptn_CantFindData {
        BigDecimal sysEmnyBls=getSysAccEmnyBls(session);
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
