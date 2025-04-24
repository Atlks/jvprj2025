package model.OpenBankingOBIE;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.OpenBankingOBIE.AccountType;

import java.math.BigDecimal;
import java.util.Date;

/**
 * OpenBanking OBIE 的Accounts实体
 * 在项目中的名称 ：本金钱包
 */
@Entity
@Table
@Data
@NoArgsConstructor
public class Accounts {

    @Id
    public String accountId;// 用户ID


    @Enumerated(EnumType.STRING)
    public AccountType accountType= AccountType.nml;         // 账户类型
    public BigDecimal availableBalance= BigDecimal.valueOf(0); // 有效余额
    public BigDecimal frozenAmount= BigDecimal.valueOf(0);    // 冻结金额
    public BigDecimal totalBalance;    // 总余额
    public BigDecimal currentBalance;  //totalBalance

    // totalBalance=availableBalance+frozenAmount+penddingBalance
    public BigDecimal penddingBalance;

    //  private List<Transaction> transactions; // 交易记录
    @Enumerated(EnumType.STRING)
    public AccountStatus accountStatus=AccountStatus.Enabled;       // 账户状态（例如：有效、冻结、关闭）
    public Date accountCreationTime;   // 账户创建时间
    public Date lastUpdatedTime;       // 上次更新时间

    public String nickname;
    public String currency;            // 币种（如 CNY、USD）


    public String uname;

    public Accounts(String accountId) {
    this.accountId=accountId;
   // this.id=accountId;
    }
}