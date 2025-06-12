package orgx;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.session.SqlSession;
import org.jetbrains.annotations.NotNull;
import orgx.acc.AccSubType;
import orgx.acc.Account;
import orgx.mapper.AccountMapper;
import orgx.mapper.BalanceMapper;
import orgx.obieErr.AlreadyProcessed;
import orgx.payment.QryTxResponse;
import orgx.payment.Stat;
import orgx.payment.TxDto;
import orgx.transaction.*;
import orgx.mapper.TransactionMapper;


import java.math.BigDecimal;
import java.util.UUID;


import static orgx.CfgSvs.getDataSourceMysql;
import static orgx.orm.MbtsUti.*;
import static orgx.uti.context.ProcessContext.mpprMap;
import static orgx.uti.context.ThreadContext.sqlSessionThreadLocal;
import static orgx.uti.orm.TxMng4mbts.callInTransaction;

public class App {

    public static void main(String[] args) throws Exception {
        mpprMap.put(Transaction.class, TransactionMapper.class);
        mpprMap.put(Account.class, AccountMapper.class);
        mpprMap.put(Balance.class, BalanceMapper.class);
        mpprMap.put(Transaction.class, TransactionMapper.class);
        buildSessionFactory(getDataSourceMysql(), new Class[]{TransactionMapper.class, BalanceMapper.class, AccountMapper.class});

        Transaction tx22 = callInTransaction((sqlSess, funCtx) -> {


             // Transaction tx = payments();
              paymentsCallbackConfirm(new TxDto("db3d1b51-1032-4fc4-ab85-603b521c8866","ok"));
            return null;
        });
        System.out.println(tx22);


    }

    @NotNull
    private static Transaction payments() throws ClassNotFoundException {
        BigDecimal amount = BigDecimal.valueOf(666);

        Transaction tx = new Transaction();

        tx.setOwner("uuu666");
        tx.setAmount(amount);
        String accountId = tx.getOwner() + "_" + AccSubType.Emoney.name();
        tx.setAccountId(accountId);
        tx.setTransactionId(UUID.randomUUID().toString());
        tx.setCreditDebitIndicator(CreditDebitIndicator.Creidt);
        tx.setTransactionType(TxCode.topup);
        tx.setCreditorAccount(new Account(accountId,tx.getOwner()));
        tx.setStatus(TxStat.Pending);


//            if(tx.getAmount().compareTo(BigDecimal.valueOf(0)) < 0) {
//                throw  new RuntimeException("invalid amount");
//            }
        String blsid = accountId + "_" + BalanceType.INTERIM_AVAILABLE.name();
        Balance ori = selectById(Balance.class, blsid);
        BigDecimal nowBls = ori.getAmount();
        BigDecimal newAmt = nowBls.add(amount);

        Balance balanceEmbd = new Balance();
        balanceEmbd.setAmount(newAmt);
        balanceEmbd.setAccountId(tx.getAccountId());
        balanceEmbd.setTypeAsEnum(BalanceType.INTERIM_AVAILABLE);
        balanceEmbd.setCreditDebitIndicatorByChk(CreditDebitIndicator.Creidt);
        tx.setBalance(balanceEmbd);

        persist(tx);
        return tx;
    }


    /**
     * 注意幂等和状态机处理,悲观锁增加
     * @param dto
     * @return
     */
    private static Transaction paymentsCallbackConfirm(TxDto dto) {
//        LambdaQueryWrapper<Transaction> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.

        //幂等
//        if (paymentRepository.existsByIdempotencyKey(idempotencyKey)) {
//            throw new RuntimeException("重复的请求，已处理过！");
//        }

        QryTxResponse rzt = qryTxStatFrmThrd(dto);
        if(!dto.stat.equals(Stat.ok.name()))
            return null;
        //状态机
        if (rzt.getStat().equals(TxStat.Booked.name())) {
            // Transaction tx = selectById(Transaction.class, dto.transactionId);
            Transaction tx = selectOne4TxlockForUpdt(dto.transactionId);

            if (tx.getStatus().equals(TxStat.Booked.name()))
                throw new AlreadyProcessed(tx.getTransactionId());
            tx.setStatus(TxStat.Booked);
            merge(tx);

            String blsid = tx.getAccountId() + "_" + BalanceType.INTERIM_AVAILABLE;
            Balance balance = selectOne4BlslockForUpdt(  blsid);
            balance.setAmount(balance.getAmount().add(
                    rzt.getAmt()));
            balance.setTypeAsEnum(BalanceType.INTERIM_AVAILABLE);
            balance.setAccountId(tx.getAccountId());
            balance.setCreditDebitIndicatorByChk(CreditDebitIndicator.Creidt);
            merge(balance);
            return tx;
        }
        return null;


    }

    private static Balance selectOne4BlslockForUpdt(String blsid) {

        LambdaQueryWrapper<Balance> qry = new LambdaQueryWrapper<Balance>()
                .eq(Balance::getBalanceId, blsid)
                .last("FOR UPDATE"); // 加悲观锁

        return  selectOne4lockForUpdt(qry, BalanceMapper.class);
    }



    private static  Transaction selectOne4TxlockForUpdt( String transactionId) {

        LambdaQueryWrapper<Transaction> qry = new LambdaQueryWrapper<Transaction>()
                .eq(Transaction::getTransactionId, transactionId)
                .last("FOR UPDATE"); // 加悲观锁
        return  selectOne4lockForUpdt(qry,TransactionMapper.class);
    }
    //        SqlSession sqlSess = sqlSessionThreadLocal.get();
//        TransactionMapper tm = sqlSess.getMapper(TransactionMapper.class);


    private static QryTxResponse qryTxStatFrmThrd(TxDto dto) {
        return new QryTxResponse();
    }


}
