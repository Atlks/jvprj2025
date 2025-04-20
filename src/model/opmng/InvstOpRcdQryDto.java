package model.opmng;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
/**
 * 投资记录查询条件dto
 *
 * 投资时间（区间）       投资类型       订单号       操作金额(区间）
 *
 *  * 用于传递前端查询条件：投资时间、投资类型、订单号、操作金额等
 */
@Data
public class InvstOpRcdQryDto {


    /**
     * 投资时间 - 起始
     */
    private LocalDateTime startInvestTime;

    /**
     * 投资时间 - 结束
     */
    private LocalDateTime endInvestTime;

    /**
     * 投资类型，例如
     */
    private String investmentType;

    /**
     * 订单号，支持精确或模糊查询
     */
    private String orderNumber;

    /**
     * 操作金额 - 最小值
     */
    private BigDecimal minAmount;

    /**
     * 操作金额 - 最大值
     */
    private BigDecimal maxAmount;

}
