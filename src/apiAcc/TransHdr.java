package apiAcc;

import entityx.TransDto;
import entityx.Usr;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import service.YLwltSvs.AddMoney2YLWltService;
import service.wlt.RdsFromWltService;
import util.Icall;
import service.Trans2YLwltService;

import static cfg.AppConfig.sessionFactory;
import static com.alibaba.fastjson2.util.TypeUtils.toBigDecimal;
import static util.HbntUtil.findByHbnt;
import static util.SprUtil.injectAll4spr;

/**
 * 从本机钱包转账到盈利钱包
 * http://localhost:8889/AddOrdBetHdr?changeAmount=8
 */
//@Component
public class TransHdr implements Icall<TransDto, String> {
    // 实现 Serializable 接口
    public static final long serialVersionUID = 1L; // 推荐
    /**
     * 配合 SELECT ... FOR UPDATE 进行行级锁定：
     * 如果你的查询会影响后续的更新操作，并且不希望其他事务修改这些数据，你可以使用事务 + SELECT ... FOR UPDATE：
     * sql
     * Copy
     * Edit
     * START TRANSACTION;
     * SELECT balance FROM accounts WHERE user_id = 1 FOR UPDATE;
     * -- 此时，其他事务无法修改此行数据
     * UPDATE accounts SET balance = balance - 100 WHERE user_id = 1;
     * COMMIT;
     * 这种方式 常用于 银行转账、库存扣减 等需要保证数据一致性的操作。
     *

     * @throws Exception
     */
//    public static void main(String[] args) throws Exception {
//        //  System.out.println(com.mysql.cj.jdbc.Driver);
//        MyCfg.iniCfgFrmCfgfile();
//        LogBls lgbls = new LogBls();
//        lgbls.changeAmount = BigDecimal.valueOf(100.5);
//        lgbls.uname = "009";
//        //   transToYinliWlt(lgbls);
//    }



    @Autowired
    Trans2YLwltService Trans2YLwltService1;


    @Autowired
            @Qualifier("RdsFromWltService")
    RdsFromWltService RdsFromWltService1;


    @Autowired
    @Qualifier("AddMoney2YLWltService")
    AddMoney2YLWltService AddMoney2YLWltService1;

    public static ThreadLocal<Usr> curLockAcc=new ThreadLocal<>();
    @Transactional
    @Override
    public String call(TransDto lgblsDto) throws Exception {

        injectAll4spr(this);
        //blk login ed



        // 获取对象并加悲观锁

        //add blance
        String uname = lgblsDto.uname;
        Usr objU = findByHbnt(Usr.class, lgblsDto.uname, LockModeType.PESSIMISTIC_WRITE, sessionFactory.getCurrentSession());

        if (objU.id == null) {
            objU.id = uname;
            objU.uname = uname;
        }
        curLockAcc.set(objU);

        RdsFromWltService1.call(lgblsDto);
        AddMoney2YLWltService1.call(lgblsDto);

        Icall is=Trans2YLwltService1;
        ((Trans2YLwltService) is).handle(lgblsDto);

       return "ok";
    }


}
