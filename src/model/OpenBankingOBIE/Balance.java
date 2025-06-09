package model.OpenBankingOBIE;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import model.obieErrCode.InvldAmtEx;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import util.annos.ObieFld;
import util.model.openbank.BalanceTypes;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static util.oo.TimeUti.nowOffsetDateTime;


/**openbank obieV3.1   bal
 * obieV3规范的 金融模型余额定义
 * can rlt acc....
 * json store acc info
 * @author att
 * @version v250515
 */
@Entity
@DynamicUpdate
@DynamicInsert
@Data
@Table(name = "balances")
@FieldNameConstants
//@EnableJpaRepositories
@NoArgsConstructor
//@ToString(exclude = "account")
public class Balance {
    @ObieFld
    @Embedded
    public  Amountx amountObj;
    @NotBlank
    private String type = BalanceTypes.interimAvailable.name();

    public Balance(Account acc, BalanceTypes interimAvailable){
        setId(getBlsid(acc.accountId,interimAvailable));
        setAccountId(acc.accountId);
        setType(interimAvailable.name());
        setOwner(acc.getOwner());
        setAccountId(acc.accountId);
        setAccSnapshot(acc);
    }


    @org.jetbrains.annotations.NotNull
    public   String getBlsid(String accountId, BalanceTypes interimAvailable) {
        return accountId + "_" +
                interimAvailable;
    }

    @Id
    public String id;


    /**
     *  // 使用@Column 显式指定数据库列名，这样就不会与 @JoinColumn 冲突
     *  cant conflkt with auto gene accid field...
     *  this for rlt acc obj ...many to one
     */
    @NotBlank
    @Column(name = "account_id")
    public String accountId;


@Transient
@JsonBackReference
    /**
     * balance实体的account对象
     */
    //@org.hibernate.annotations.Immutable  // Hibernate扩展，只读提示
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "account_id", insertable = false, updatable = false)
    // @JsonBackReference
    public Account account;  //json acc



    @NotNull

    private BigDecimal amount = BigDecimal.valueOf(0);

    public void setAmountCantBeZero(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0)
            throw new InvldAmtEx("amount=" + amount+",blsid="+this.id);
        this.amount = amount;
    }
    public void setAmount(BigDecimal amount) {

        this.amount = amount;
    }

    @PostLoad
    private void initDefaults() {
        if (amount == null) {
            amount = BigDecimal.valueOf(0.0);
        }

    }

    @NotBlank
    private String currency = "usdt"; //amt crcy


    /**
     *  //   Wallet.BalanceType
     *     //   private BalanceAmount balanceAmount;
     *
     *     //   balanceType: 说明该余额的类型，例如“AVAILABLE”表示可用余额，“CURRENT”表示当前余额。
     *
     *     //  dateTime: 余额记录的时间戳，格式为 ISO 8601。
     *     // @Enumerated(EnumType.STRING)
     */



    //必填项  发生的时间
    @CreationTimestamp
    @NotNull
    private OffsetDateTime dateTime=nowOffsetDateTime();




    /**
     * 所有账户类型（储蓄、信用卡、贷款等），因为有些账户的“余额”本身是负的（比如贷款、信用卡欠款），所以必须标明是 Credit 还是 Debit
     */
    @Enumerated(EnumType.STRING)
    private CreditDebitIndicator creditDebitIndicator = CreditDebitIndicator.CREDIT; // Credit or Debit

    public Balance(String blsId) {
        //  this.accountId=blsId;
        this.id = blsId;
    }
    // private CreditLine creditLine;  crdt card bls


    //-----------uke ext
    @NotBlank
    public String owner = "";  //uname accowner
    @NotBlank
    public String accSubType = AccountSubType.EMoney.name();

    /**
     * acc store ,json fmt
     * //H2 实际只会当字符串存储H2 实际只会当字符串存储
     * 不能把同一个字段同时映射为外键关系又当成JSON列，Hibernate 会混乱。
     * 一旦你使用了 columnDefinition 明确指定列类型，那么 length 属性不会生效。
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @JsonBackReference("dfltRefs")
    @Column(columnDefinition = "varchar(2000)",length = 2200)
    public Account accSnapshot;


    @ObieFld
    @Embedded
    public  CreditLine creditLine;
}
