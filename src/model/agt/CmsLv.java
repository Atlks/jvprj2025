package model.agt;

/**
 * 实体类：佣金档位
 * 包含字段（档挡位，充值金额，佣金比例）
 *
 */
public class CmsLv
{

    /**
     * 档位编号（如：1，2，3...）
     */
    public Integer level=1;

    /**
     * 对应档位的最低充值金额（单位：元）
     */
    public Double rechargeAmount= (double) 5;

    /**
     * 佣金比例（如：5 表示 5%）
     */
    public Double commissionRate= 25.0;

    // Getter & Setter 方法

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Double getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(Double rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    public Double getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(Double commissionRate) {
        this.commissionRate = commissionRate;
    }
}
