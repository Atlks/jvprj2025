package model.rpt;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.YearMonth;

/**
 *
 *  月份充值统计实体类
 *  * 包含以下字段：
 *  * - 月份（如：2025-05）
 *  * - 充值总金额
 *  * - 充值人数
 * 月份 充值总金额  充值人数
 */
@Entity
@Table(name="month_rechg_sum")

public class RechgAmtSumNMbrs {

    /**
     * 月份（格式如：2025-05）
     */
    private YearMonth month;

    /**
     * 该月充值总金额
     */
    private BigDecimal totalRechargeAmount;

    /**
     * 该月充值人数
     */
    private int rechargeUserCount;






    public RechgAmtSumNMbrs() {}

    public RechgAmtSumNMbrs(YearMonth month, BigDecimal totalRechargeAmount, int rechargeUserCount) {
        this.month = month;
        this.totalRechargeAmount = totalRechargeAmount;
        this.rechargeUserCount = rechargeUserCount;
    }

    public YearMonth getMonth() {
        return month;
    }

    public void setMonth(YearMonth month) {
        this.month = month;
    }

    public BigDecimal getTotalRechargeAmount() {
        return totalRechargeAmount;
    }

    public void setTotalRechargeAmount(BigDecimal totalRechargeAmount) {
        this.totalRechargeAmount = totalRechargeAmount;
    }

    public int getRechargeUserCount() {
        return rechargeUserCount;
    }

    public void setRechargeUserCount(int rechargeUserCount) {
        this.rechargeUserCount = rechargeUserCount;
    }

    @Override
    public String toString() {
        return "RechgAmtSumNMbrs{" +
                "month=" + month +
                ", totalRechargeAmount=" + totalRechargeAmount +
                ", rechargeUserCount=" + rechargeUserCount +
                '}';
    }

}
