package model.agt;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 实体类：代理信息
 */
@Entity
@Table
@Data
public class Agent {

    /**
     * 代理账号
     */
    @Id
    public String agentAccount;

    /**
     * 充值会员数
     */
    public int rechargeMemberCount = 0;

    /**
     * 注册会员数
     */
    public int registeredMemberCount = 0;

    public long   timestamp=System.currentTimeMillis();
    /**
     * 标签
     */
    public String tag = "";

    /**
     * 兑换会员数
     */
    public int exchangeMemberCount = 0;

    /**
     * 提现会员数
     */
    public int withdrawalMemberCount = 0;

    /**
     * 充值金额
     */
    public BigDecimal rechargeAmount = BigDecimal.valueOf(0);

    /**
     * 兑换金额
     */
    public BigDecimal exchangeAmount = BigDecimal.valueOf(0);

    /**
     * 提现金额
     */
    public BigDecimal withdrawalAmount = BigDecimal.valueOf(0);

    /**
     * 本级充值金额
     */
    public BigDecimal levelOneRechargeAmount = BigDecimal.valueOf(0);

    /**
     * 下级充值金额
     */
    public BigDecimal subLevelRechargeAmount = BigDecimal.valueOf(0);

    /**
     * 总充值金额（本级 + 下级）
     */
    public BigDecimal totalRechargeAmount = BigDecimal.valueOf(0);

    /**
     * 佣金比例（如：0.05 表示 5%）
     */
    public BigDecimal commissionRate = BigDecimal.valueOf(0);

    /**
     * 佣金金额
     */
    public BigDecimal commissionAmount = BigDecimal.valueOf(0);

    // Getters and Setters

    public String getAgentAccount() {
        return agentAccount;
    }

    public void setAgentAccount(String agentAccount) {
        this.agentAccount = agentAccount;
    }

    public int getRechargeMemberCount() {
        return rechargeMemberCount;
    }

    public void setRechargeMemberCount(int rechargeMemberCount) {
        this.rechargeMemberCount = rechargeMemberCount;
    }

    public int getRegisteredMemberCount() {
        return registeredMemberCount;
    }

    public void setRegisteredMemberCount(int registeredMemberCount) {
        this.registeredMemberCount = registeredMemberCount;
    }

}
