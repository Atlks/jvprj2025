package model.cfg;


import java.math.BigDecimal;

/**
 * 会员vip等级配置  ，包含以下字段
 * VIP等级  1---9
 * VIP名称   VIP0===VIP9
 * 累计充值金额>=
 */
public class MbrVipCfg {


    /**
     * VIP等级（数值越大表示等级越高）
     */
    private int vipLevel;

    /**
     * VIP名称（例如：黄金会员、白金会员等）
     */
    private String vipName="";

    /**
     * 累计充值金额需达到的最低值
     */
    private BigDecimal minTotalDeposit= BigDecimal.valueOf(0);

    // 构造函数
    public MbrVipCfg() {
    }

    public MbrVipCfg(int vipLevel, String vipName, BigDecimal minTotalDeposit) {
        this.vipLevel = vipLevel;
        this.vipName = vipName;
        this.minTotalDeposit = minTotalDeposit;
    }

    // Getter & Setter
    public int getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(int vipLevel) {
        this.vipLevel = vipLevel;
    }

    public String getVipName() {
        return vipName;
    }

    public void setVipName(String vipName) {
        this.vipName = vipName;
    }

    public BigDecimal getMinTotalDeposit() {
        return minTotalDeposit;
    }

    public void setMinTotalDeposit(BigDecimal minTotalDeposit) {
        this.minTotalDeposit = minTotalDeposit;
    }

    @Override
    public String toString() {
        return "MbrVipCfg{" +
                "vipLevel=" + vipLevel +
                ", vipName='" + vipName + '\'' +
                ", minTotalDeposit=" + minTotalDeposit +
                '}';
    }
}
