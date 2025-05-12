package model.OpenBankingOBIE;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import util.annos.CurrentUsername;

import java.math.BigDecimal;
import java.time.LocalDate;
// java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;

/**
 * OpenBanking OBIE 的Accounts实体
 * 在项目中的名称 ：本金钱包
 */
@Entity
@Table
@Data
@NoArgsConstructor
@FieldNameConstants

public class Account {

    // 使用hibernate ，查询account实体，查询所有InterimAvailableBalance>1的账户

    @Id
    public String accountId;// 用户ID


    @Enumerated(EnumType.STRING)
    public AccountType accountType = AccountType.PERSONAL;         // 账户类型


    @Column(length = 500)
    //  @Enumerated(EnumType.STRING)
    public String accountSubType = AccountSubType.EMoney.name();         // 账户类型


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

    public void setFrozenAmountVld(BigDecimal frozenAmount) {
        if(frozenAmount.compareTo(BigDecimal.ZERO)<=0 )
            throw new InVldAmtEx("frzAmt="+frozenAmount);
        this.frozenAmount = frozenAmount;
    }

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
    public BigDecimal ClosingBookedBalance;

    // 总余额  tmp ttl bls
    public BigDecimal InterimBookedBalance = BigDecimal.valueOf(0);  //totalBalance

    // totalBalance=availableBalance+frozenAmount+penddingBalance
    public BigDecimal penddingBalance; //InterimCleared bls

    //  private List<Transaction> transactions; // 交易记录
    @Enumerated(EnumType.STRING)
    public AccountStatus status = AccountStatus.Enabled;       // 账户状态（例如：有效、冻结、关闭）


    //  public String status;
    public OffsetDateTime statusUpdateDateTime;

    public String Description;
    public String nickname;
    public String currency;            // 币种（如 CNY、USD）
    public LocalDate OpeningDate;
    public String name;
    public String accountIdentification;
    public String accountSecondaryIdentification;


    // -------------ext fld
    @NotBlank
    @CurrentUsername
    public String accountOwner;

    public Account(String accountId) {
        this.accountId = accountId;

        // this.id=accountId;
    }
}