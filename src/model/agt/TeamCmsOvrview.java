package model.agt;


import lombok.Data;

import java.math.BigDecimal;

/**
 * 团队总充值概览 vo
 */
@Data
public class TeamCmsOvrview {

    // 团队总充值金额
    private BigDecimal totalRechargeAmount; // 总充值金额

    // 佣金比例
    private double commissionRate; // 佣金比例（百分比形式）

    // 佣金
    private BigDecimal commission; // 佣金金额

    // 构造函数
    public TeamCmsOvrview(BigDecimal totalRechargeAmount, double commissionRate, BigDecimal commission) {
        this.totalRechargeAmount = totalRechargeAmount;
        this.commissionRate = commissionRate;
        this.commission = commission;
    }



    // 输出类的属性信息
    @Override
    public String toString() {
        return "TeamCmsOvrview{" +
                "totalRechargeAmount=" + totalRechargeAmount +
                ", commissionRate=" + commissionRate +
                ", commission=" + commission +
                '}';
    }

}
