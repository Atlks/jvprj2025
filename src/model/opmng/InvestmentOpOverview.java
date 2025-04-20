package model.opmng;

import java.math.BigDecimal;

/**
 * 后台--用户投资资金概览
 */
public class InvestmentOpOverview {

    /**
     * 本金钱包总额（例如：888,888.00） 所有用户加起来
     */
    private BigDecimal principalWalletSum;

    /**
     * 保险资金池金额（例如：8,000.00） 只有一个，所有用户公用
     */
    private BigDecimal insuranceFundPool;

    /**
     * 累计投资盈利金额（例如：888,888.00）
     */
    private BigDecimal totalInvestmentProfit;

    /**
     * 累计投资亏损金额（例如：444.00）
     */
    private BigDecimal totalInvestmentLoss;

    // Getters and Setters

    public BigDecimal getPrincipalWalletSum() {
        return principalWalletSum;
    }

    public void setPrincipalWalletSum(BigDecimal principalWalletSum) {
        this.principalWalletSum = principalWalletSum;
    }

    public BigDecimal getInsuranceFundPool() {
        return insuranceFundPool;
    }

    public void setInsuranceFundPool(BigDecimal insuranceFundPool) {
        this.insuranceFundPool = insuranceFundPool;
    }

    public BigDecimal getTotalInvestmentProfit() {
        return totalInvestmentProfit;
    }

    public void setTotalInvestmentProfit(BigDecimal totalInvestmentProfit) {
        this.totalInvestmentProfit = totalInvestmentProfit;
    }

    public BigDecimal getTotalInvestmentLoss() {
        return totalInvestmentLoss;
    }

    public void setTotalInvestmentLoss(BigDecimal totalInvestmentLoss) {
        this.totalInvestmentLoss = totalInvestmentLoss;
    }
}
