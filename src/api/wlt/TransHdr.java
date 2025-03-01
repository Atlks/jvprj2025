package api.wlt;

import annos.CookieParam;
import annos.Parameter;
import annos.注入;
import biz.Operation;
import entityx.TransDto;
import entityx.Usr;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;

import jakarta.ws.rs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;
import util.Icall;
import service.Trans2YLwltService;

import static cfg.AppConfig.sessionFactory;
import static com.alibaba.fastjson2.util.TypeUtils.toBigDecimal;
import static util.HbntUtil.findByHbnt;
import static util.SprUtil.injectAll4spr;

/**
 * 从本机钱包转账到盈利钱包
 * http://localhost:8889/Trans?changeAmount=8
 */
@RestController   // 默认返回 JSON，不需要额外加 @ResponseBody。
@Tag(name = "wlt 钱包")
@Path("/Trans")
@Operation(summary = "转账操作", example = "/Trans?changeAmount=8")
@Parameter(name = "uname", description = "用户名（in cookie）", required = true)
@Parameter(name = "changeAmount", description = "转账金额", required = true)
@CookieParam(name = "uname",description = "用户名",decryKey="a1235678")
@Component
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


    //@Autowired
    Trans2YLwltService Trans2YLwltService1;

    @注入
    @Autowired
    @Qualifier("RdsFromWltService")
    public Icall RdsFromWltService1;

    @注入
    @Autowired
    @Qualifier("AddMoney2YLWltService")
    public Icall AddMoney2YLWltService1;

    public static ThreadLocal<Usr> curLockAcc = new ThreadLocal<>();

    @Transactional
    @Override
    public String call(@ModelAttribute  TransDto lgblsDto) throws Exception {

        injectAll4spr(this);
        //blk login ed


        // 获取对象并加悲观锁

        //add blance   bcs uname frm cookie
      //  lgblsDto.uname=decryptDES( lgblsDto.uname,Key_a1235678);

        String uname = lgblsDto.uname;
        Usr objU = findByHbnt(Usr.class, lgblsDto.uname, LockModeType.PESSIMISTIC_WRITE, sessionFactory.getCurrentSession());

        if (objU.id == null) {
            objU.id = uname;
            objU.uname = uname;
        }
        curLockAcc.set(objU);

        RdsFromWltService1.call(lgblsDto);
        AddMoney2YLWltService1.call(lgblsDto);

//        Icall is = Trans2YLwltService1;
//        ((Trans2YLwltService) is).handle(lgblsDto);

        return "ok";
    }


}
