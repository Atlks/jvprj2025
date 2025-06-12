package orgx;

import orgx.acc.AccSubType;
import orgx.acc.Account;
import orgx.mapper.AccountMapper;
import orgx.mapper.BalanceMapper;
import orgx.mapper.TransactionMapper;
import orgx.transaction.Balance;
import orgx.transaction.BalanceType;
import orgx.transaction.Transaction;

import static orgx.CfgSvs.getDataSourceMysql;
import static orgx.orm.MbtsUti.buildSessionFactory;
import static orgx.orm.MbtsUti.persist;
import static orgx.uti.context.ProcessContext.mpprMap;
import static orgx.uti.orm.TxMng4mbts.callInTransaction;

public class addAccBls {
    public static void main(String[] args) throws Exception {

        mpprMap.put(Transaction.class, TransactionMapper.class);
        mpprMap.put(Account.class, AccountMapper.class);
        mpprMap.put(Balance.class, BalanceMapper.class);
        buildSessionFactory(getDataSourceMysql(), new Class[]{TransactionMapper.class,AccountMapper.class,BalanceMapper.class});

        callInTransaction((sqlSess, funCtx) -> {


            String owner="uuu666";
            String accid = owner+"_" + AccSubType.Emoney.name();
            Account acc=new Account(accid, owner);

            persist(acc);
            Balance bls=new Balance();
            String balanceId = acc.getAccId() + "_" + BalanceType.INTERIM_AVAILABLE.name();
            bls.setBalanceId(balanceId);

            persist(bls);
            return bls;
        });
        //System.out.println(tx22);
    }
}
