package model.OpenBankingOBIE;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.*;
import org.hibernate.type.SqlTypes;
import util.annos.CurrentUsername;
import util.model.openbank.BalanceTypes;

import java.math.BigDecimal;

import java.time.OffsetDateTime;

/**金融系统规范  交易流水
 * OpenBanking OBIE 的Transactions实体  交易流水，也就是
 * ,just use transPay   transWdth
 * also ue for rechg pay wdthr
 * <p>
 * 每个交易在 OBIE 中都必须至少包含 TransactionCode、Amount、CreditDebitIndicator、TransactionDateTime、TransactionStatus 和 AccountNumber 等字段
 */
@Entity
@DynamicUpdate  // 仅更新被修改的字段
@DynamicInsert //如果还希望 INSERT 时也只插入非 null 的字段，可以搭配
@Table(name = "Transactions",
        indexes = {
                @Index(name = "idx_transaction_code", columnList = "transactionCode"),
                @Index(name = "idx_uname", columnList = "accountOwner")
        })  //Transactions_Pay
@NoArgsConstructor
@Data
@FieldNameConstants
public class Transaction {

    @Id
    // 交易唯一标识
    public String transactionId;

    //OBIE Transaction 是否有 accountId？	❌ 没有，默认由 API URL 上下文提供
    @NotNull
    @NotBlank  @ExtFld
    @Column(name = "account_id")
    public String accountId;


    /**
     * OBIE 规范中，TransactionReference 是正确的名称。
     * <p>
     * ISO 20022 规范中，Reference 可能使用不同的名称，具体包括 EndToEndIdentification 和 TransactionIdentification，这两者都在不同的上下文中用于标识交易的唯一性
     * 可以看作一个外部使用的trxID
     * <p>
     * 在某些场景下，TransactionReference 和 TransactionID 可能会是相同的值，只是命名不同。例如，某些系统可能会直接用 TransactionID 作为引用标识符，在这种情况下，两者可能没有实际的区别。然而，在其他系统或规范中，它们可能被视为不同的字段，每个字段用于不同的用途。
     * <p>
     * 总结：
     * TransactionReference 更侧重于 外部引用 和 标识，用于跟踪和查询交易。
     * <p>
     * TransactionID 更侧重于 系统内部的标识符，用于交易处理和管理。
     */
    @ObieFld
    public String transactionReference;




    /**
     * creditDebitIndicator 的合法取值（仅两个）
     * <p>
     * 值	含义	说明
     * Credit	入账（Incoming）	表示这笔钱是进到账户里的，比如：充值、工资、退款等
     * Debit	出账（Outgoing）	表示这笔钱是从账户转出的，比如：消费、提现、支付等
     */
    @Enumerated(EnumType.STRING)
    @NotNull
    @NotBlank
    public CreditDebitIndicator creditDebitIndicator = CreditDebitIndicator.CREDIT;


    // 交易金额
    /**
     * 在 OBIE（Open Banking Implementation Entity）规范中，交易流水的 amount 字段 本身不允许为负数。是否是支出或收入，由另一个字段 creditDebitIndicator 决定。
     */
    @DecimalMin(value = "0.00", inclusive = true, message = "余额不能为负数")
    @ObieFld
    public BigDecimal amount = BigDecimal.ZERO;

    /**
     * 不允许为负数
     *
     * @param amount
     */
    public void setAmountVldChk(@NotNull BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0)
            throw new AmtCantLessThan0Excptn(amount.toString());
        this.amount = amount;
    }


    /**
     * 这笔交易产生的费用或手续费（比如：手续费 2 GBP）
     * 银行或第三方收的服务费。
     */
    @ObieFld
    @DecimalMin(value = "0.00", inclusive = true, message = "chgamt不能为负数")
    public BigDecimal ChargeAmount = BigDecimal.valueOf(0);

    public void setChargeAmount(BigDecimal chargeAmount1) {
        if (chargeAmount1 == null || chargeAmount1.compareTo(BigDecimal.ZERO) < 0)
            throw new AmtCantLessThan0Excptn(chargeAmount1.toString());
        ChargeAmount = chargeAmount1;
    }




    /**
     * // 交易状态（如：BOOKED、PENDING等）
     */
    @ObieFld

    @Enumerated(EnumType.STRING)
    public TransactionStatus status = TransactionStatus.PENDING;


    //采用 ISO 8601 标准的日期时间格式，即 YYYY-MM-DDThh:mm:ss+00:00。
    /**
     * BookingDateTime 是强制字段，所有 ASPSPs（Account Servicing Payment Service Providers）必须提供此字段，
     * 对应mysql字段 DATETIME LocalDateTime ⇒ MySQL DATETIME
     * <p>
     * BookingDateTime 是银行系统完成处理、交易已正式反映到账户的时间，一般不可变更，是审计和报表的依据
     */
    // 允许为 null 的字段  def iscan be null
    @Nullable
    @ObieFld
    public OffsetDateTime bookingDateTime;

    @ObieFld
    public String transactionInformation = "";









    //obie有表示，交易类型，iso 20022  有表示
    //  @Enumerated(EnumType.STRING)
    @ObieFld("bank transactionCode")
    @NotBlank
    public String transactionCode = TransactionCode.OTH.name();

    public void setTransactionCode(@NotNull TransactionCode transactionCode) {
        this.transactionType = transactionCode.name();
        this.transactionCode=transactionCode.name();
    }




    //------------bls fld
    //after bls
    /**
     * 账户余额快照（如银行账单中常见的“交易后余额”）
     */
    @ObieFld()
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "varchar(2000)")
    @Alas("balanceAfterTx")
    public Balance balance;

    @org.hibernate.annotations.Immutable
    @ManyToOne
    @JoinColumn(name = "account_id", insertable = false, updatable = false)
    private Account account;

    /**
     * Transaction.Balance：通常是交易后的可用余额（Available Balance）
     * 每笔交易后的余额（通常为“可用余额”）
     */
    @NotNull
    @Alas("balanceAmtAfterTx")
    public BigDecimal balanceAmount = BigDecimal.ZERO;
    public String balanceCreditDebitIndicator = CreditDebitIndicator.CREDIT.name();

    public void setCreditDebitIndicator(@NotNull CreditDebitIndicator creditDebitIndicator) {
        this.setBalanceCreditDebitIndicator(creditDebitIndicator.name());
    }

    public String balanceType = BalanceTypes.interimBooked.name();
    public  void setBalanceType(BalanceTypes type){
        this.balanceType=type.name();
    }

    public String BalanceAmountCurrency = "usdt";
    //end bls





    //-------obie has ,but not use in sys


//    @ObieFld
//    public String SupplementaryData;

//    @ObieFld("bkTxCd.subcode")
//    public String SubCodeType = "";
//    //TransactionCode/SubCode
//    String ChargeAmountCurrency = "usdt";
//    // 商户名称（适用于消费交易）
//    private String merchantName;
    /**
     * 表示交易金额实际影响账户可用余额或开始计息的时间（值日）	记账在 5 月 12 日，但金额在 5 月 11 日就可用了（提前记值）或 5 月 14 日才可用（延后记值）
     * ValueDateTime 是资金对客户实际生效的日期，决定利息起算或资金实际可用的时间，银行可根据内部规则设定（如延后到账）。
     * 可选字段，经常为 null；  对应资金延迟到账或记息规则时才会设置。
     */
    // @Nullable @ObieFld
    // public OffsetDateTime  ValueDateTime;

//    @ObieFld
//    public String StatementReference;


    // ---------本地自定义扩展字段

    @ExtFld
    @UpdateTimestamp
    public OffsetDateTime updtDateTime;
    @ExtFld
    @CreationTimestamp
    @Column(updatable = false)
    public OffsetDateTime crtDateTime;
    @ObieFld("")
    public String ProprietaryBankTransactionCode;

    // 付款方账户信息  ext fld
    @ObieFld("dbtorAgent")
    private String debtorAccount;

    // 收款方账户信息
    @ObieFld("CreditorAgent")  @Alas("CreditorAgent")
    public String creditorAccount;
    @ExtFld
    public String refUniqId;

    @ExtFld @Alas("txid")
    public String id;
//    @ExtFld
//    private String paymentChannel = "usdt";     // usdt/微信/支付宝等
//    @ExtFld
//    private String channelOrderNo;     // 第三方返回单号





    /**
     *    // 记录有关交易的地址信息，例如：
     *
     *     // 线下刷卡消费的商户地址
     */
//
//            @ObieFld
//    String AddressLine="ol";

//    @ObieFld
//    String CurrencyExchange;


    @ExtFld
    String ChargeDetails = "";


    /**
     *  // 交易类型：借记、贷记等  可选字段,trscod alias 需要同步
     * OBIE 标准并不直接提供一个 transactionType 字段，但提供了：
     * <p>
     * creditDebitIndicator（入账/出账）
     * <p>
     * bankTransactionCode.code + subCode（标准化交易分类）
     * <p>
     * （自然语言提示）
     * <p>
     * 若你在构建平台，可以自定义一个 transactionType 字段来标注 "DEPOSIT"、"WITHDRAWAL"、"FEE"、"TRANSFER" 等类型。
     */
    @NotNull
    @NotBlank
    @Alas("tx code")
    @ExtFld
    private String transactionType = TransactionCode.OTH.name();

    @NotBlank
    @CurrentUsername
    @ExtFld
    public String owner = "";

    //review time
    @Nullable  @ExtFld
    public OffsetDateTime review_timestamp;

    @CreationTimestamp
    @ExtFld
    public OffsetDateTime timestamp;

    public Transaction(String txId, String accid, String accOwnr, CreditDebitIndicator creditDebitIndicator, @NotNull(message = "金额不能为空") @Min(value = 1, message = "金额必须大于0") BigDecimal amount) {
        this.transactionId = txId;
        this.setTransactionId(txId);
        this.setCreditDebitIndicator(creditDebitIndicator);
        this.owner = accOwnr;
        this.accountId = accid;
        this.setAmountVldChk(amount);
    }

//    public Transactions( CreditDebitIndicator creditDebitIndicator, @NotNull(message = "提现金额不能为空") @Min(value = 1, message = "提现金额必须大于0") BigDecimal amount) {
//        this.transactionId =getUuid();
//        this.creditDebitIndicator = creditDebitIndicator;
//        this.amount = amount;
//    }

//    @Deprecated
//    public Transaction(String accountId, String uname, CreditDebitIndicator creditDebitIndicator, @NotNull(message = "金额不能为空") @Min(value = 1, message = "金额必须大于0") BigDecimal amount) {
//        this.transactionId = "div_" + getUuid();
//        this.creditDebitIndicator = creditDebitIndicator;
//        this.amount = amount;
//        this.accountOwner = uname;
//        this.accountId = accountId;
//    }


    public String getOwner() {
        return owner;
    }

    public String setOwner() {
        return owner;
    }


    //审核人
    @ExtFld
    public String parentProxy = "";
    public String vipLev = "";

    //截图附件
    @ExtFld
    public String img = "";


    // Getter 和 Setter 方法
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
        this.id = transactionId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionCode transactionType) {
        this.transactionType=transactionType.name();
        this.transactionType = transactionType.name();
    }


}
