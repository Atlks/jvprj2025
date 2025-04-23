package model.OpenBankingOBIE;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;

/**
 * OpenBanking OBIE 的Transactions实体  交易流水，也就是
 *   ,just use transPay   transWdth
 * also ue for rechg pay wdthr
 */
@Entity
@DynamicUpdate  // 仅更新被修改的字段
@DynamicInsert //如果还希望 INSERT 时也只插入非 null 的字段，可以搭配
@Table( )  //Transactions_Pay

@Data
public class Transactions {

    @Id
    // 交易唯一标识
    public String transactionId;

    //OBIE Transaction 是否有 accountId？	❌ 没有，默认由 API URL 上下文提供
    public String accountId;


    /**
     * OBIE 标准并不直接提供一个 transactionType 字段，但提供了：
     *
     * creditDebitIndicator（入账/出账）
     *
     * bankTransactionCode.code + subCode（标准化交易分类）
     *
     * transactionInformation（自然语言提示）
     *
     * 若你在构建平台，可以自定义一个 transactionType 字段来标注 "DEPOSIT"、"WITHDRAWAL"、"FEE"、"TRANSFER" 等类型。
     */
    // 交易类型：借记、贷记等
    private String transactionType;


    /**
     * creditDebitIndicator 的合法取值（仅两个）
     *
     * 值	含义	说明
     * Credit	入账（Incoming）	表示这笔钱是进到账户里的，比如：充值、工资、退款等
     * Debit	出账（Outgoing）	表示这笔钱是从账户转出的，比如：消费、提现、支付等
     */
    @Enumerated(EnumType.STRING)
    public CreditDebitIndicator  creditDebitIndicator;


    // 交易金额
    public BigDecimal amount;

    // 商户名称（适用于消费交易）
    private String merchantName;

    // 交易的实际处理日期
    private Date bookingDate;

    // 交易金额有效入账的日期
    private Date valueDate;

    // 付款方账户信息
    private String debtorAccount;

    // 收款方账户信息
    public String creditorAccount;

    // 交易状态（如：BOOKED、PENDING等）
    public TransactionStatus transactionStatus=TransactionStatus.PENDING;



   // ---------本地自定义扩展字段
    public String id;

    private String paymentChannel="usdt";     // usdt/微信/支付宝等
    private String channelOrderNo;     // 第三方返回单号




    @NotBlank
    public String uname="";

    //review time
    public long review_timestamp=0;

    public long   timestamp=System.currentTimeMillis();



    public String getUname() {
        return uname;
    }

    public String setUname() {
        return uname;
    }






    //审核人

    public String parentProxy="";
    public String vipLev="";

    //截图附件
    public String img="";

    @CreationTimestamp   //20240505112233
    public long crtTimeStmpLocalfmt=System.currentTimeMillis();

    @UpdateTimestamp
    public long updtTmstmpLocalfmt=System.currentTimeMillis();

   // public long   timestamp=System.currentTimeMillis();


    // Getter 和 Setter 方法
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }





    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Date getValueDate() {
        return valueDate;
    }

    public void setValueDate(Date valueDate) {
        this.valueDate = valueDate;
    }







}
