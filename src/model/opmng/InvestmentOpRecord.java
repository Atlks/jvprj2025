package model.opmng;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static util.algo.GetUti.getUuid;

/**
 * 投资记录实体类
 *
 * 添加的时候，只需要投资类型 和amt，资金流转方向是自动计算的
 *
 */
@Entity
@Table(name = "invst_op_rcd")
@Data
public class InvestmentOpRecord {

    @Id
    public String id=getUuid();
    /**
     * 投资时间（时间戳或格式化字符串）
     */
    private LocalDateTime investmentTime;

    public  long timestamp=System.currentTimeMillis();

    /**
     * 订单号（唯一标识）
     */
    private String orderNumber=getUuid();

    /**
     * 投资类型（盈利 ）
     */
    public String investmentType;

    /**
     * 操作金额（例如：8888.00）
     */
    public BigDecimal amount;

    /**
     * 资金流转方向（例如：转入、转出、收益、亏损）
     *  2,000转入本金钱包；8,000转入保险资金池 
     */
    private String fundFlowDirection;

    // Getters and Setters

    public LocalDateTime getInvestmentTime() {
        return investmentTime;
    }

    public void setInvestmentTime(LocalDateTime investmentTime) {
        this.investmentTime = investmentTime;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getInvestmentType() {
        return investmentType;
    }

    public void setInvestmentType(String investmentType) {
        this.investmentType = investmentType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getFundFlowDirection() {
        return fundFlowDirection;
    }

    public void setFundFlowDirection(String fundFlowDirection) {
        this.fundFlowDirection = fundFlowDirection;
    }
}
