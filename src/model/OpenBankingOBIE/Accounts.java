package model.OpenBankingOBIE;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.OpenBankingOBIE.AccountType;

import java.math.BigDecimal;
import java.util.Date;

/**
 * OpenBanking的Accounts实体
 * 在项目中的名称 ：本金钱包
 */
@Entity
@Table
@Data
@NoArgsConstructor
public class Accounts {

    @Id
    public String accountId;                // 用户ID
    public AccountType accountType= AccountType.nml;         // 账户类型
    public BigDecimal availableBalance= BigDecimal.valueOf(0); // 有效余额
    public BigDecimal frozenAmount= BigDecimal.valueOf(0);    // 冻结金额
  //  public BigDecimal totalBalance;    // 总余额
    //  private List<Transaction> transactions; // 交易记录

    public String accountStatus="";       // 账户状态（例如：有效、冻结、关闭）
    public Date accountCreationTime;   // 账户创建时间
    public Date lastUpdatedTime;       // 上次更新时间

    public String nickname;
    public String currency;            // 币种（如 CNY、USD）

    public BigDecimal balanceYinliwlt=BigDecimal.valueOf(0); // 有效余额

    public String uname;

    public Accounts(String accountId) {
    this.accountId=accountId;
    }
}