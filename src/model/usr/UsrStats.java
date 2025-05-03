package model.usr;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

//成员统计
@Entity
@Table
@Data
public class UsrStats {

    public  String uname;

    public BigDecimal totalSubsDpsSum;//下级累计充值总额

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
  //  private BigDecimal totalCommission = BigDecimal.ZERO;


    // 累计奖金总额
  //  private BigDecimal totalBonus = BigDecimal.ZERO;

    // 构造函数
    public UsrStats() {}


    public BigDecimal getTotalDeposit() {
        return totalDeposit;
    }

    public void setTotalDeposit(BigDecimal totalDeposit) {
        this.totalDeposit = totalDeposit;
    }


}
