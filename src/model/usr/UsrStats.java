package model.usr;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

//成员统计
@Entity
@Table
@Data
public class UsrStats {

    public  String uname;

    public BigDecimal totalSubsDpsSum;//下级累计充值总额

    // 累计佣金
    private BigDecimal totalCommission = BigDecimal.ZERO;

    // 累计充值总额
    private BigDecimal totalDeposit = BigDecimal.ZERO;

    // 累计奖金总额
    private BigDecimal totalBonus = BigDecimal.ZERO;

    // 构造函数
    public UsrStats() {}

    public UsrStats(BigDecimal totalCommission, BigDecimal totalDeposit, BigDecimal totalBonus) {
        this.totalCommission = totalCommission;
        this.totalDeposit = totalDeposit;
        this.totalBonus = totalBonus;
    }

    // Getters and Setters
    public BigDecimal getTotalCommission() {
        return totalCommission;
    }

    public void setTotalCommission(BigDecimal totalCommission) {
        this.totalCommission = totalCommission;
    }

    public BigDecimal getTotalDeposit() {
        return totalDeposit;
    }

    public void setTotalDeposit(BigDecimal totalDeposit) {
        this.totalDeposit = totalDeposit;
    }

    public BigDecimal getTotalBonus() {
        return totalBonus;
    }

    public void setTotalBonus(BigDecimal totalBonus) {
        this.totalBonus = totalBonus;
    }

    @Override
    public String toString() {
        return "MemberStats{" +
                "totalCommission=" + totalCommission +
                ", totalDeposit=" + totalDeposit +
                ", totalBonus=" + totalBonus +
                '}';
    }
}
