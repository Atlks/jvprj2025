package model.OpenBankingOBIE;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import util.model.openbank.BalanceTypes;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Data
@Table(name="balances")
@FieldNameConstants
@NoArgsConstructor
//@ToString(exclude = "account")
public class Balance {

    @Id
    public String id;

    @NotBlank
    // 使用@Column 显式指定数据库列名，这样就不会与 @JoinColumn 冲突
    @Column(name = "account_id")
    public String accountId;
    /**
     * balance实体的account对象
     */
//
     @org.hibernate.annotations.Immutable  // Hibernate扩展，只读提示
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "account_id", insertable = false, updatable = false)
//    @JsonBackReference
     @JdbcTypeCode(SqlTypes.JSON)
     @Column(columnDefinition = "varchar(2000)") // H2 实际只会当字符串存储
    public Account account;  //json acc



    private BigDecimal amount= BigDecimal.valueOf(0);
    public  void setAmount(BigDecimal amount) {
        if(amount.compareTo(BigDecimal.ZERO)<=0 )
            throw new InvldAmtEx("amount="+amount);
        this.amount = amount;
    }

    @PostLoad
    private void initDefaults() {
        if (amount == null) {
            amount = BigDecimal.valueOf(0.0);
        }

    }

    private String currency; //amt crcy
    //   Wallet.BalanceType
    //   private BalanceAmount balanceAmount;

    //   balanceType: 说明该余额的类型，例如“AVAILABLE”表示可用余额，“CURRENT”表示当前余额。

    //  dateTime: 余额记录的时间戳，格式为 ISO 8601。
   // @Enumerated(EnumType.STRING)
    private String type=BalanceTypes.interimAvailable.name();

    //必填项  发生的时间
    @CreationTimestamp
    private OffsetDateTime dateTime;


    /**
     * 所有账户类型（储蓄、信用卡、贷款等），因为有些账户的“余额”本身是负的（比如贷款、信用卡欠款），所以必须标明是 Credit 还是 Debit
     */
    @Enumerated(EnumType.STRING)
    private CreditDebitIndicator creditDebitIndicator=CreditDebitIndicator.CREDIT; // Credit or Debit

    public Balance(String blsId) {
      //  this.accountId=blsId;
        this.id=blsId;
    }
    // private CreditLine creditLine;  crdt card bls


    //text
    public  String owner="";  //uname accowner
    public  String accSubType=AccountSubType.EMoney.name();
}
