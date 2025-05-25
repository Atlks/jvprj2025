package model.OpenBankingOBIE;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import model.obieErrCode.InvldAmtEx;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import util.annos.*;

import java.math.BigDecimal;
// java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * OpenBanking OBIE V3 的Accounts实体
 * 在项目中的名称 ：本金钱包  投资资金钱包
 * acc里面本来是不包含banlance的。。。因为一对多的关系，以及安全元素，不能不包含bals,只能在bals关联acc
 */
@Entity
@Table(name = "accounts")
@DynamicUpdate
@DynamicInsert
@Data
@NoArgsConstructor
@FieldNameConstants
@ToString(exclude = "bals") // Lombok
@JsonIgnoreProperties(ignoreUnknown = true) // <--- 忽略 JSON 中未在类中定义的字段
public class Account {

    public Account(String accountId, AccountType accountType, String accountSubType) {
        setAccountId(accountId);
        setAccountType(accountType);
        setAccountSubType(accountSubType);

    }

    // 使用hibernate ，查询account实体，查询所有InterimAvailableBalance>1的账户
    @ObieFld
    @Id
    public String accountId;// 用户ID


    @ObieFld
    @Enumerated(EnumType.STRING)
    public AccountType accountType = AccountType.PERSONAL;         // 账户类型

    @ObieFld
    @Column(length = 500)
    //  @Enumerated(EnumType.STRING)
    @NotNull
    @NotBlank
    public String accountSubType = AccountSubType.EMoney.name();         // 账户类型


    /**
     * account实体的bals字段，不能包含bals为了安全
     * transt 表示脱离hbnt管理，手动引用，不然容易循环引用
     * this bals ,only for some ui,for query balss,not all ui need show bals..
     * 只读提示 field
     */

    @Transient
    @org.hibernate.annotations.Immutable  // Hibernate扩展，只读提示
    @OneToManyTips(fetch = FetchType.LAZY)
//    @JsonManagedReference
    public List<Balance> bals = new ArrayList<>();
//avd bls
    /**
     * 🧾 拆解 InterimAvailableBalance 的含义：
     * Interim：临时的（即非结算时点）。
     * <p>
     * ：可用余额，即客户此刻能支配的金额（扣除了冻结/挂账等
     * iso 20022和obie都没有avlbbls fld...only itrAvBls
     */
    @DecimalMin(value = "0.00", inclusive = true, message = "余额不能为负数")
    public BigDecimal interim_Available_Balance = BigDecimal.valueOf(0); // 有效余额


    public void setInterim_Available_Balance(BigDecimal interim_Available_Balance) {
        if (interim_Available_Balance.compareTo(BigDecimal.ZERO) < 0)
            throw new InvldAmtEx("itrAvBls=" + interim_Available_Balance);
        this.interim_Available_Balance = interim_Available_Balance;
    }

    //冻结金额必须大于
    public void setFrozenAmountVld(BigDecimal frozenAmount) {
        if (frozenAmount.compareTo(BigDecimal.ZERO) <= 0)
            throw new InvldAmtEx("frzAmt=" + frozenAmount);
        this.frozenAmount = frozenAmount;
    }


    /**
     * frz amt is not starnd field
     */
    @DecimalMin(value = "0.00", inclusive = false, message = "冻结金额必须大于 0")
    @Column(nullable = false)
    public BigDecimal frozenAmount = BigDecimal.valueOf(0);    // 冻结金额


    @PostLoad
    private void initDefaults() {
        if (interim_Available_Balance == null) {
            interim_Available_Balance = BigDecimal.valueOf(0.0);
        }
        if (InterimBookedBalance == null) {
            InterimBookedBalance = BigDecimal.valueOf(0);
        }
    }

    //总余额,每日帐点后的余额，一般是Pm10以后，扎帐
//    @DecimalMin(value = "0.00", inclusive = true, message = "余额不能为负数")
//    public BigDecimal ClosingBookedBalance = BigDecimal.valueOf(0);
    ;

    // 总余额  tmp ttl bls
    @DecimalMin(value = "0.00", inclusive = true, message = "余额不能为负数")
    public BigDecimal InterimBookedBalance = BigDecimal.valueOf(0);  //totalBalance

    //余额不能为负数
    public void setInterimBookedBalance(BigDecimal interimBookedBalance) {
        if (interimBookedBalance.compareTo(BigDecimal.ZERO) < 0)
            throw new InvldAmtEx("interimBookedBalance=" + interimBookedBalance);
        this.InterimBookedBalance = interimBookedBalance;
    }

    // totalBalance=availableBalance+frozenAmount+penddingBalance
    @DecimalMin(value = "0.00", inclusive = false, message = "penddingBalance余额不能为负数")
    public BigDecimal penddingBalance = BigDecimal.valueOf(0);
    ; //InterimCleared bls

    //  private List<Transaction> transactions; // 交易记录
    @Enumerated(EnumType.STRING)
    @NotNull
    @NotBlank
    public AccountStatus status = AccountStatus.Enabled;       // 账户状态（例如：有效、冻结、关闭）


    //  public String status;
    @UpdateTimestamp
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    public OffsetDateTime statusUpdateDateTime;

    public String Description;
    @Blankable
    public String nickname;

    @NotBlank
    public String currency="usdt";            // 币种（如 CNY、USD）

    @CreationTimestamp
    @NotNull

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")

    public OffsetDateTime OpeningDate;


    //-----------option field
    //到期日
    @ObieFld
    public OffsetDateTime MaturityDate;
    public String name;
    public String accountIdentification;
    public String accountSecondaryIdentification;


    // -------------ext fld
    @NotBlank
    @CurrentUsername
    @ExtFld
    public String owner;
//    @Deprecated  //bcs db embd json need this
//    public String accountOwner="";

    public Account(String accountId) {
        this.accountId = accountId;

        // this.id=accountId;
    }
}