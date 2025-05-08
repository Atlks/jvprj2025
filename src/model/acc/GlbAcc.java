package model.acc;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import util.model.TableNames;

import java.math.BigDecimal;

@Entity
// 方面的统计
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class GlbAcc {

    @Id
    public  String uname="sys";

   // public BigDecimal totalSubsDpsSum;//下级累计充值总额
   private BigDecimal insFdPoolBalance = BigDecimal.ZERO;
    private BigDecimal totalEmoneyBalance = BigDecimal.ZERO;
    private BigDecimal totalInvstProfit = BigDecimal.ZERO;
    private BigDecimal totalInvstLoss = BigDecimal.ZERO;



    //累计收益  累计划转  累计提现
    // 累计收益
    private BigDecimal totalProfit = BigDecimal.ZERO;

    // 累计划转
    private BigDecimal totalTransfer = BigDecimal.ZERO;


    // 累计充值总额
    private BigDecimal totalDeposit = BigDecimal.ZERO;

    // 累计提现
    private BigDecimal totalWithdraw = BigDecimal.ZERO;


    // 累计佣金
    private BigDecimal totalCommission = BigDecimal.ZERO;


    // 累计奖金总额
     private BigDecimal totalBonus = BigDecimal.ZERO;

    // 构造函数
    public GlbAcc() {}


    public BigDecimal getTotalDeposit() {
        return totalDeposit;
    }

    public void setTotalDeposit(BigDecimal totalDeposit) {
        this.totalDeposit = totalDeposit;
    }


}
