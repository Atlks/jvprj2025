package handler.acc;

import api.ylwlt.BizFun;
import entityx.wlt.TransDto;
import handler.statmt.CrudFun;
import handler.wlt.DepositDto;
import jakarta.validation.constraints.NotNull;
import model.OpenBankingOBIE.*;
import model.obieErrCode.FieldInvalidEx;
import org.hibernate.Session;
import util.Oosql.SlctQry;
import util.ex.BalanceNotEnghou;
import util.model.CrudRzt;
import util.model.openbank.BalanceTypes;
import util.model.openbank.BankAccountService;
import util.sql.SqlBldr;
import util.tx.HbntUtil;
import util.tx.findByIdExptn_CantFindData;

import java.math.BigDecimal;
import java.util.List;

import static cfg.Containr.sessionFactory;
import static com.alibaba.fastjson2.util.TypeUtils.toBigDecimal;

import static handler.acc.AccDao.*;
import static handler.balance.BlsSvs.*;
import static handler.trx.TransactnService.insertTxSetAmtIdctrBooked_txcod;
import static handler.wlt.TransfHdr.curLockAcc;
import static util.Oosql.SlctQry.newSelectQuery;
import static util.acc.AccUti.getAccId;
import static util.acc.AccUti.sysusrName;
import static util.algo.GetUti.getTableName;
import static util.algo.GetUti.getUuid;
import static util.log.ColorLogger.RED_bright;
import static util.log.ColorLogger.colorStr;
import static util.model.openbank.BalanceTypes.interimAvailable;
import static util.model.openbank.BalanceTypes.interimBooked;
import static util.sql.SqlBldr.sum;
import static util.tx.HbntUtil.*;
import static util.tx.HbntUtil.persist;

public class AccService implements BankAccountService {


    /**
     * 增加余额到代理佣金账户
     * @param owner
     * @param addAmt
     * @return
     * @throws findByIdExptn_CantFindData
     */
    @BizFun
public static  Account addBls2AgtcmsAcc(String owner,BigDecimal addAmt) throws findByIdExptn_CantFindData {
    String accid=getAccId(AccountSubType.agtCms,owner);
    Account acc=findById(Account.class,accid);
    acc.setInterim_Available_Balance(acc.getInterim_Available_Balance().add(addAmt));
    acc.setInterimBookedBalance(acc.getInterimBookedBalance().add(addAmt));
    mergex(acc);

        Balance avlbBls= addAmt2BalWhrAccNType(addAmt,acc, interimAvailable);
        avlbBls.account=null;
        Balance BkBls= addAmt2BalWhrAccNType(addAmt,acc, interimBooked);
        BkBls.account=null;
        acc.bals.add(avlbBls);
        acc.bals.add(BkBls);
        return acc;

}

    public static void updtAccSetFztbls_avlbls(Account acc1, BigDecimal newFrzAmt, BigDecimal newAvlBls) {
        acc1.setFrozenAmountVld(newFrzAmt);
        acc1.setInterim_Available_Balance(newAvlBls);
        mergex(acc1);
    }

@BizFun("admin dcrs bal")
    public static Object adjustBalanceDecrease(DecBalDto dto) throws findByIdExptn_CantFindData, FieldInvalidEx {
        Account acc=findById(Account.class,dto.accid);
        Transaction tx2 = new Transaction();
        tx2.setAmountVldChk(dto.amt);
        tx2.setCreditDebitIndicator(CreditDebitIndicator.DEBIT);
        tx2.setTransactionCode(TransactionCode.adjst_dbt);
    subAmt2accWzlog(acc,tx2);
        return 0;
    }

    public static void subAmt2accWzlog(Account acc1, Transaction tx) throws findByIdExptn_CantFindData {
        //-----------sub acc

        String txt = "\r\n\n\n=============⚡⚡bizfun  " + colorStr("sub acc", RED_bright);
        System.out.println(txt);
        BigDecimal adjAmt = tx.getAmount();
        subAmt2acc(acc1, adjAmt);


        //sub bls   addAmt2BalWhrAccNType
        txt = "\r\n\n\n=============⚡⚡bizfun  " + colorStr("sub bls", RED_bright);
        System.out.println(txt);
        Balance blsAvlb = subAmt2BalWhrAcc_type(adjAmt, acc1, interimAvailable);
        subAmt2BalWhrAcc_type(adjAmt, acc1, BalanceTypes.interimBooked);


        //add tx
        txt = "\r\n\n\n=============⚡⚡bizfun  " + colorStr("add txt", RED_bright);
        System.out.println(txt);
        tx.creditDebitIndicator = CreditDebitIndicator.DEBIT;


        insertTxSetAmtIdctrBooked_txcod(tx, acc1, blsAvlb);
    }

//    @Deprecated
//    private static void insertTx(Account acc1, Transaction tx) {
//        tx.setTransactionId(getUuid());
//        tx.owner = acc1.owner;
//        tx.accountId = acc1.accountId;
//        //   tx.setAmountVldChk(toBigDecimal(adjAmt));
//        tx.status = TransactionStatus.BOOKED;
//        tx.setBalanceAmount(acc1.getInterim_Available_Balance());
//        tx.setBalanceType(interimAvailable);
//        tx.setBalanceCreditDebitIndicator(CreditDebitIndicator.CREDIT.name());
//
//        persist(tx);
//    }


    public static void unfrzAmt2accWzlog(Account acc1, Transaction tx) throws findByIdExptn_CantFindData {
        //-----------unfrz acc
        BigDecimal adjAmt = tx.getAmount();

        updtAccSetFztbls_avlbls(acc1, acc1.frozenAmount.subtract(adjAmt), acc1.getInterim_Available_Balance().add(adjAmt));


        //sub bls
        Balance blsAvb=  addAmt2BalWhrAccNType(adjAmt, acc1, interimAvailable);
        subAmt2BalWhrAcc_type(adjAmt, acc1, BalanceTypes.frz);


        //add tx


        tx.setBalanceCreditDebitIndicator(CreditDebitIndicator.CREDIT.name());

        insertTxSetAmtIdctrBooked_txcod(tx,acc1,blsAvb);
        persist(tx);
    }
    @BizFun
    public static void updateAccSetBlsZero(String accid) throws findByIdExptn_CantFindData {
        BigDecimal amt = BigDecimal.valueOf(0);
        updtAccSetBls(accid, amt);
    }
@BizFun
public static void updtAccSetBls(String accid, BigDecimal amt) throws findByIdExptn_CantFindData {
        Account acc=findByHerbinateLockForUpdtV2(Account.class, accid);

        acc.setInterim_Available_Balance(amt);
        acc.setInterimBookedBalance(amt);
        mergex(acc);

        //  String blsid=getBlsid(accid, BalanceTypes.interimBooked);
    iniBal(acc,interimAvailable);  iniBal(acc,interimBooked);

        updateBlsSetAmt(getBlsid(accid, BalanceTypes.interimBooked), amt);
        updateBlsSetAmt(getBlsid(accid, interimAvailable), amt);
    }


    public static void frzAmt2accWzlog(Account acc1, Transaction tx) throws findByIdExptn_CantFindData {
        //-----------frz acc
        BigDecimal adjAmt = tx.getAmount();
        BigDecimal newFrzAmt = acc1.frozenAmount.add(adjAmt);
        BigDecimal newAvlBls = acc1.getInterim_Available_Balance().subtract(adjAmt);

        updtAccSetFztbls_avlbls(acc1, newFrzAmt, newAvlBls);


        //sub bls
       Balance blsAvb= subAmt2BalWhrAcc_type(adjAmt, acc1, interimAvailable);
        addAmt2BalWhrAccNType(adjAmt, acc1, BalanceTypes.frz);


        //add tx

        tx.creditDebitIndicator = CreditDebitIndicator.DEBIT;
        insertTxSetAmtIdctrBooked_txcod(tx,acc1,blsAvb);

        persist(tx);
    }

    /**
     * 增加余额的服务，包括流水日志
     * @param dto
     */
    @BizFun("通用增加余额服务")
    //@MethodInfo("存款服务")
    public static Object deposit(DepositDto dto) throws findByIdExptn_CantFindData, FieldInvalidEx {
        Account acc=findById(Account.class,dto.accid);
        Transaction tx2 = new Transaction();
        tx2.setCreditDebitIndicator(CreditDebitIndicator.CREDIT);
        tx2.setAmountVldChk(dto.amt);
        tx2.setTransactionCode(dto.type);
        icrBalByAccTx(acc,tx2);
        return 0;
    }
    /**
     * 增加余额的服务，包括流水日志
     *
     * @param acc1
     * @throws findByIdExptn_CantFindData
     */
    public static void icrBalByAccTx(Account acc1, Transaction tx) throws findByIdExptn_CantFindData {
        String txt;
        System.out.println("fun addAmt2accWzLog");
        //-----------add acc
        txt = "\r\n\n\n=============⚡⚡bizfun  " + colorStr("add acc", RED_bright);
        System.out.println(txt);
        BigDecimal adjAmt = tx.amount;
        addAmt2acc(acc1, adjAmt);

        //------add bls
        txt = "\r\n\n\n=============⚡⚡bizfun  " + colorStr("add bls", RED_bright);
        System.out.println(txt);
        System.out.println("blk::add two bls");
       // BalanceTypes blsType = BalanceTypes.interimAvailable;
        String accountId = acc1.accountId;
        Balance blsAvlb = addAmt2BalWhrAccNType(adjAmt, acc1, interimAvailable);
        addAmt2BalWhrAccNType(adjAmt, acc1, BalanceTypes.interimBooked);
        System.out.println("endblk::add two bls");

        //-----addtx
        txt = "\r\n\n\n=============⚡⚡bizfun  " + colorStr("add tx", RED_bright);
        System.out.println(txt);
        System.out.println("fun addtx");


        tx.creditDebitIndicator = CreditDebitIndicator.CREDIT;
        //alreay setAmt creditDebitIndicator txcode
        insertTxSetAmtIdctrBooked_txcod(tx, acc1, blsAvlb);
        System.out.println("endfun addtx");
        System.out.println("endfun addAmt2accWzLog");
    }






    /**
     * 减去钱包余额服务
     *
     * @return
     * @throws Exception
     */
    @BizFun
    @NotNull
    public static @NotNull Object subBalFrmAcc_whereTypeWlt(@NotNull TransDto TransDto88,  Transaction txx) throws Exception, findByIdExptn_CantFindData {


        System.out.println("\n\n\n===========减去钱包余额");

        //  放在一起一快存储，解决了十五问题事务。。。
        Account acc = curLockAcc.get();
        if (acc == null)
            acc = TransDto88.lockAccObj;

        BigDecimal nowAmt = acc.getInterim_Available_Balance();
        if (TransDto88.getAmount().compareTo(nowAmt) > 0) {
            throw new BalanceNotEnghou("余额不足" + "nowAmtBls=" + nowAmt);

        }

        BigDecimal amt = TransDto88.getAmount();
        BigDecimal newBls = nowAmt.subtract(toBigDecimal(amt));
        acc.setInterim_Available_Balance(newBls); ;

        acc.InterimBookedBalance = acc.InterimBookedBalance.subtract(amt);
        // acc.ClosingBookedBalance =acc.InterimBookedBalance;

        mergex(acc, sessionFactory.getCurrentSession());

        //-----------add tx lg

        txx.transactionId = "rdsFromWlt" + getUuid();
        txx.creditDebitIndicator = CreditDebitIndicator.DEBIT;
        txx.accountId = acc.accountId;

        txx.owner = acc.owner;
        txx.amount = TransDto88.getAmount();
        txx.refUniqId = String.valueOf(System.currentTimeMillis());
        txx.status = TransactionStatus.BOOKED;
        persist(txx, sessionFactory.getCurrentSession());

        String accountId = acc.accountId;

        //todo need updt bls
      //  subAmtUpdtBls(accountId,interimAvailable,amt);
       // subAmtUpdtBls(accountId,interimBooked,amt);



        //------------add balanceLog
//        LogBls logBalance = new LogBls();
//        logBalance.id = "LogBalance" + getFilenameFrmLocalTimeString();
//        logBalance.uname = acc.accountId;
//
//        logBalance.changeAmount = TransDto88.getChangeAmount();
//        logBalance.amtBefore = toBigDcmTwoDot(nowAmt);
//        logBalance.newBalance = toBigDcmTwoDot(newBls);
//        logBalance.refUniqId= String.valueOf(System.currentTimeMillis());
//        logBalance.changeMode = "减去";
//        System.out.println(" add balanceLog ");
//        //  addObj(logBalance,saveUrlLogBalance);
//        persistByHibernate(logBalance, sessionFactory.getCurrentSession());
        return acc;
    }


    //资金池余额
    public static BigDecimal getInsFdPlBal() {
        String accid = getAccId(String.valueOf(AccountSubType.insFdPl), sysusrName);
        Account acc = null;
        try {
            acc = findById(Account.class, accid, sessionFactory.getCurrentSession());
            return acc.getInterim_Available_Balance();
        } catch (findByIdExptn_CantFindData e) {
            return BigDecimal.valueOf(0);
        }

    }

    @BizFun
    public static BigDecimal sumAmtFromAccWhereSubtypeEqEmoney() throws findByIdExptn_CantFindData {
        Session session=sessionFactory.getCurrentSession();
        String sql= new SqlBldr()
                .select(sum(Account.Fields.interim_Available_Balance))
                .from(Account.class)
                .where(Account.Fields.accountSubType,"=",AccountSubType.EMoney.name())
                .and(Account.Fields.accountType,"=",AccountType.PERSONAL.name()).getSql();
        BigDecimal sum= HbntUtil.getSingleResult(sql,BigDecimal.class);
        return sum;
    }

    //总余额统计 本金钱包
//    public static BigDecimal sumAllEmnyAccBal() {
//        SlctQry query = newSelectQuery(getTableName(Account.class));
//        query.select("sum(" + Account.Fields.interim_Available_Balance + ")");
//        query.addConditions(Account.Fields.accountSubType , "=" ,(AccountSubType.EMoney.name()));
//        // query.addConditions("timestamp>"+ beforeTmstmp(reqdto.day));
//        //    query.addOrderBy("timestamp desc");
//        String sql = query.getSQL();  // ✅ 直接拿到 SQL 字符串
//        System.out.println(sql);
//        try {
//            return (BigDecimal) getSingleResult(sql, sessionFactory.getCurrentSession());
//        } catch (findByIdExptn_CantFindData e) {
//            return BigDecimal.valueOf(0);
//        }
//    }

    @Override
    public CrudRzt openAccount(Account account) {
        return null;
    }

    @Override
    public CrudRzt closeAccount(String accountId) {
        return null;
    }

    @Override
    public Account getAccountById(String accountId) {
        return null;
    }

    @Override
    public CrudRzt deposit(String accountId, BigDecimal amount) {
        return null;
    }

    @Override
    public CrudRzt withdraw(String accountId, BigDecimal amount) {
        return null;
    }

    @Override
    public CrudRzt transfer(String fromAccountId, String toAccountId, BigDecimal amount) {
        return null;
    }

    @Override
    public CrudRzt freezeAccount(String accountId) {
        return null;
    }

    @Override
    public CrudRzt unfreezeAccount(String accountId) {
        return null;
    }

    @Override
    public List<Transaction> listTransactions(String accountId, int page, int pageSize) {
        return List.of();
    }

    @Override
    public CrudRzt changePaymentPassword(String accountId, String oldPwd, String newPwd) {
        return null;
    }

    @Override
    public boolean verifyPaymentPassword(String accountId, String inputPwd) {
        return false;
    }
}
