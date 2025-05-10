//package model.rpt;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//import lombok.Data;
//
//import java.math.BigDecimal;
//import java.time.YearMonth;
//
//@Entity
//@Table(name="month_rechg_sum")
//@Data
//public class RechgAmtSumNMbrs {
//
//
//
//    private BigDecimal totalRechargeAmount;
//
//    /**
//     * 该月充值人数
//     */
//    private int rechargeUserCount;
//
//
//
//
//
//
//    public RechgAmtSumNMbrs() {}
//
//    public RechgAmtSumNMbrs(YearMonth month, BigDecimal totalRechargeAmount, int rechargeUserCount) {
//        this.yr_month = month.toString();
//        this.totalRechargeAmount = totalRechargeAmount;
//        this.rechargeUserCount = rechargeUserCount;
//    }
//
//
//
//    public BigDecimal getTotalRechargeAmount() {
//        return totalRechargeAmount;
//    }
//
//    public void setTotalRechargeAmount(BigDecimal totalRechargeAmount) {
//        this.totalRechargeAmount = totalRechargeAmount;
//    }
//
//    public int getRechargeUserCount() {
//        return rechargeUserCount;
//    }
//
//    public void setRechargeUserCount(int rechargeUserCount) {
//        this.rechargeUserCount = rechargeUserCount;
//    }
//
//    @Override
//    public String toString() {
//        return "RechgAmtSumNMbrs{" +
//                "month=" + yr_month +
//                ", totalRechargeAmount=" + totalRechargeAmount +
//                ", rechargeUserCount=" + rechargeUserCount +
//                '}';
//    }
//
//}
