package model.opmng;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import model.OpenBankingOBIE.TransactionCode;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import util.excptn.AmtErr;

import java.math.BigDecimal;

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
@FieldNameConstants
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class InvestmentOpRecord {

    @Id
    public String id=getUuid();
    /**
     * 投资时间（时间戳或格式化字符串）
     */
    private long investmentTime=System.currentTimeMillis();

    public  long timestamp=System.currentTimeMillis();

    /**
     * 订单号（唯一标识）
     */
    private String orderNumber=getUuid();

    /**
     * 投资类型（盈利 ）
     *
     *     public TransactionCodes
     */
    @Enumerated(EnumType.STRING)
    public TransactionCode investmentType;

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



    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }



    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {

        this.amount = amount;
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new AmtErr("金额错误");
        }
    }

    public String getFundFlowDirection() {
        return fundFlowDirection;
    }

    public void setFundFlowDirection(String fundFlowDirection) {
        this.fundFlowDirection = fundFlowDirection;
    }
}
