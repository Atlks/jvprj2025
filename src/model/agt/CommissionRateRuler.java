package model.agt;

import lombok.Data;

/**
 * 提成比例规则实体类
 * 表示在某个金额区间内适用的提成比例
 */
@Data

public class CommissionRateRuler {

     /**
      * 最小金额（ 含）
      * 适用于该规则的起始金额
      */
     private double minAmount;

     /**
      * 最大金额（含）
      * 适用于该规则的结束金额
      */
     private double maxAmount;

     /**
      * 提成比例（百分比，单位 %）
      */
     private double commissionRate;

     // 构造方法
     public void CommissionRateRuler() {}

     public void CommissionRateRuler(double minAmount, double maxAmount, double commissionRate) {
          this.minAmount = minAmount;
          this.maxAmount = maxAmount;
          this.commissionRate = commissionRate;
     }

     // Getter 和 Setter
     public double getMinAmount() {
          return minAmount;
     }

     public void setMinAmount(double minAmount) {
          this.minAmount = minAmount;
     }

     public double getMaxAmount() {
          return maxAmount;
     }

     public void setMaxAmount(double maxAmount) {
          this.maxAmount = maxAmount;
     }

     public double getCommissionRate() {
          return commissionRate;
     }

     public void setCommissionRate(double commissionRate) {
          this.commissionRate = commissionRate;
     }

     @Override
     public String toString() {
          return "CommissionRateRuler{" +
                  "minAmount=" + minAmount +
                  ", maxAmount=" + maxAmount +
                  ", commissionRate=" + commissionRate +
                  '}';
     }
}
