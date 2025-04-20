package model.wlt;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
@Entity
@Table
@Data
@NoArgsConstructor
public class Wallet {

    @Id
    public String userId;                // 用户ID
    public String accountType;         // 账户类型
    public BigDecimal availableBalance; // 有效余额
    public BigDecimal frozenAmount;    // 冻结金额
  //  public BigDecimal totalBalance;    // 总余额
    //  private List<Transaction> transactions; // 交易记录

    public String accountStatus;       // 账户状态（例如：有效、冻结、关闭）
    public Date accountCreationTime;   // 账户创建时间
    public Date lastUpdatedTime;       // 上次更新时间

    public String currency;            // 币种（如 CNY、USD）

    public BigDecimal balanceYinliwlt; // 有效余额

}