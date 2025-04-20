package model.rpt;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 用户财务统计信息实体类
 * 包含：累计充值、累计提现、累计兑换金额、累计佣金
 */
@Entity
@Table(name="rpt_Agt_Amt_Sum")
@Data
@NoArgsConstructor
public class AgtAmtSum {

    @Id
    public  String uname;
    /**
     * agt nxt lv mbr累计充值金额
     */
    private BigDecimal totalDeposit;

    /**
     * agt 累计提现金额
     */
    private BigDecimal totalWithdrawal;

    /**
     * agt 累计兑换金额
     */
    private BigDecimal totalExchange;

    /**
     *agt 累计佣金金额
     */
    private BigDecimal totalCommission;

    // Getters and Setters
    public BigDecimal getTotalDeposit() {
        return totalDeposit;
    }

    public void setTotalDeposit(BigDecimal totalDeposit) {
        this.totalDeposit = totalDeposit;
    }

    public BigDecimal getTotalWithdrawal() {
        return totalWithdrawal;
    }

    public void setTotalWithdrawal(BigDecimal totalWithdrawal) {
        this.totalWithdrawal = totalWithdrawal;
    }

    public BigDecimal getTotalExchange() {
        return totalExchange;
    }

    public void setTotalExchange(BigDecimal totalExchange) {
        this.totalExchange = totalExchange;
    }

    public BigDecimal getTotalCommission() {
        return totalCommission;
    }

    public void setTotalCommission(BigDecimal totalCommission) {
        this.totalCommission = totalCommission;
    }
}
