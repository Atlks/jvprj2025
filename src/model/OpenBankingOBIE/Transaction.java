package model.OpenBankingOBIE;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import static util.algo.GetUti.getUuid;

/**
 * OpenBanking OBIE 的Transactions实体  交易流水，也就是
 *   ,just use transPay   transWdth
 * also ue for rechg pay wdthr
 *
 * 每个交易在 OBIE 中都必须至少包含 TransactionCode、Amount、CreditDebitIndicator、TransactionDateTime、TransactionStatus 和 AccountNumber 等字段
 */
@Entity
@DynamicUpdate  // 仅更新被修改的字段
@DynamicInsert //如果还希望 INSERT 时也只插入非 null 的字段，可以搭配
@Table(name="Transactions",
        indexes = {
        @Index(name = "idx_transaction_code", columnList = "transactionCode"),
        @Index(name = "idx_uname", columnList = "accountOwner")
} )  //Transactions_Pay
@NoArgsConstructor
@Data
@FieldNameConstants
public class Transaction {

    @Id
    // 交易唯一标识
    public String transactionId;

    //OBIE Transaction 是否有 accountId？	❌ 没有，默认由 API URL 上下文提供
    public String accountId;

    public String   StatementReference;
    public String   transactionReference;


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


    /**
     * OBIE 规范中，TransactionReference 是正确的名称。
     *
     * ISO 20022 规范中，Reference 可能使用不同的名称，具体包括 EndToEndIdentification 和 TransactionIdentification，这两者都在不同的上下文中用于标识交易的唯一性
     * 可以看作一个外部使用的trxID
     *
     * 在某些场景下，TransactionReference 和 TransactionID 可能会是相同的值，只是命名不同。例如，某些系统可能会直接用 TransactionID 作为引用标识符，在这种情况下，两者可能没有实际的区别。然而，在其他系统或规范中，它们可能被视为不同的字段，每个字段用于不同的用途。
     *
     * 总结：
     * TransactionReference 更侧重于 外部引用 和 标识，用于跟踪和查询交易。
     *
     * TransactionID 更侧重于 系统内部的标识符，用于交易处理和管理。
     */
    public  String TransactionReference;
    // 商户名称（适用于消费交易）
    private String merchantName;

    // 交易的实际处理日期 this not starnd inobie
    private Date bookingDate;

    // 交易金额有效入账的日期  this not starnd inobie
    private Date valueDate;

    //采用 ISO 8601 标准的日期时间格式，即 YYYY-MM-DDThh:mm:ss+00:00。
    /**
     * BookingDateTime 是强制字段，所有 ASPSPs（Account Servicing Payment Service Providers）必须提供此字段，
     * 对应mysql字段 DATETIME LocalDateTime ⇒ MySQL DATETIME
     */
  public LocalDateTime bookingDateTime;
    public  LocalDateTime       ValueDateTime;

    @Column(name = "booking_timezone")
    private String bookingTimezone; // 比如 "Asia/Shanghai"

    // 付款方账户信息
    private String debtorAccount;

    // 收款方账户信息
    public String creditorAccount;

    // 交易状态（如：BOOKED、PENDING等）
    @Enumerated(EnumType.STRING)
    public TransactionStatus transactionStatus=TransactionStatus.PENDING;


  //obie没有表示，交易类型，iso 20022  有表示
  @Enumerated(EnumType.STRING)
    public TransactionCode transactionCode= TransactionCode.OTH;


  //TransactionCode/SubCode

    String ProprietaryBankTransactionCode;


    //after bls
    BigDecimal Balance;
    String BalanceCreditDebitIndicator;
    String BalanceType;
    String BalanceAmountCurrency;
    String SupplementaryData;

   // ---------本地自定义扩展字段
   public String refUniqId;
    public String id;

    private String paymentChannel="usdt";     // usdt/微信/支付宝等
    private String channelOrderNo;     // 第三方返回单号


    /**
     * OBIE 标准并不直接提供一个 transactionType 字段，但提供了：
     *
     * creditDebitIndicator（入账/出账）
     *
     * bankTransactionCode.code + subCode（标准化交易分类）
     *
     * （自然语言提示）
     *
     * 若你在构建平台，可以自定义一个 transactionType 字段来标注 "DEPOSIT"、"WITHDRAWAL"、"FEE"、"TRANSFER" 等类型。
     */

   // 记录有关交易的地址信息，例如：

   // 线下刷卡消费的商户地址
    String AddressLine;

    public BigDecimal ChargeAmount;

    String ChargeAmountCurrency;
    String CurrencyExchange;

    public String transactionInformation;
    // 交易类型：借记、贷记等  可选字段
    private String transactionType;

    @NotBlank
    public String accountOwner ="";

    //review time
    public long review_timestamp=0;

    public long   timestamp=System.currentTimeMillis();

    public Transaction(String txId, CreditDebitIndicator creditDebitIndicator, @NotNull(message = "金额不能为空") @Min(value = 1, message = "金额必须大于0") BigDecimal amount) {
    this.transactionId = txId;
    this.creditDebitIndicator = creditDebitIndicator;
    this.amount = amount;
    }

//    public Transactions( CreditDebitIndicator creditDebitIndicator, @NotNull(message = "提现金额不能为空") @Min(value = 1, message = "提现金额必须大于0") BigDecimal amount) {
//        this.transactionId =getUuid();
//        this.creditDebitIndicator = creditDebitIndicator;
//        this.amount = amount;
//    }

    @Deprecated
    public Transaction(String accountId, String uname, CreditDebitIndicator creditDebitIndicator, @NotNull(message = "金额不能为空") @Min(value = 1, message = "金额必须大于0") BigDecimal amount) {
        this.transactionId ="div_"+getUuid();
        this.creditDebitIndicator = creditDebitIndicator;
        this.amount = amount;
        this.accountOwner =uname;
        this.accountId=accountId;
    }


    public String getAccountOwner() {
        return accountOwner;
    }

    public String setAccountOwner() {
        return accountOwner;
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
