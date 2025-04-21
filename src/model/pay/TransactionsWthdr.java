package model.pay;
import jakarta.persistence.*;
import lombok.Data;
import model.constt.TransactionStatus;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import static service.CmsBiz.toBigDcmTwoDot;
import static com.alibaba.fastjson2.util.TypeUtils.toBigDecimal;
import static util.algo.GetUti.getUuid;

/**
 * //  ISO 20022  标准字段
 * 提现金额 - 订单号 会员账号 标签 VIP等级 上级代理 提现金额 到账金额 审核状态 审核人 提现时间 审核时间
 */
@Entity
@Data
@Table(name = "Transactions_Wthdr")
//WithdrawRequest
public class TransactionsWthdr {

    /** 创建时间（ISO8601格式）  2025-04-20T14:30:00Z */
    @CreationTimestamp
    public LocalDateTime bookingDate;

    // 交易金额有效入账的日期
    public Date valueDate;
    /** 提现流水号（端到端） */
    public String transactionId ="";


    //    到账金额
    /** 提现金额   InstdAmt 明确表示“指示支付金额”。 所以这里使用amt*/
    public BigDecimal amount;

    /** 币种，如：CNY、USD */
    public String currency="";


    /** 收款人姓名 */
    public String creditorName;

    /** 收款人账户 */
    public String creditorAccount;

    /** 提现备注 */
    public String remarks;



    public long timestamp;

    @Column(name = "uname", nullable = false)
    public String uname;

    @Id
    public String id=getUuid();
    public String reviewer;

    //审核状态
    public String status= String.valueOf(TransactionStatus.PENDING);;
    public long revwTime;
    public String tag;
    public  String vipLev;
    public String sprAgt;  //上级代理


  //  public BigDecimal rcv_amt;

    public BigDecimal getAmount() {
        return toBigDcmTwoDot(amount) ;
    }



    public long getTimestamp() {
        return timestamp;
    }


}
