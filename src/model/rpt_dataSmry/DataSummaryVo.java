package model.rpt_dataSmry;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据总览  top部分
 */
@Data
@NoArgsConstructor
public class DataSummaryVo {

    // 累计相关数据
    private BigDecimal totalRecharge= BigDecimal.valueOf(0);      // 累计充值

    private BigDecimal totalWithdraw= BigDecimal.valueOf(0);      // 累计提现


    private BigDecimal totalExchange= BigDecimal.valueOf(0);      // 累计兑换
    private BigDecimal totalProfit= BigDecimal.valueOf(0);        // 累计收益
    private BigDecimal totalCommission= BigDecimal.valueOf(0);    // 累计佣金


    private BigDecimal totalReward= BigDecimal.valueOf(0);    // 累计奖励



    private BigDecimal totalAdjustment= BigDecimal.valueOf(0);    // 累计调整

    // 月份列表（如：2025/05, 2025/06 ...）
    public List<MonthRechgStats> monthRechgStatsList=new ArrayList<>();

    // 当月相关
    private BigDecimal currentMonthRechargeAmount= BigDecimal.valueOf(0); // 当月充值金额
    private long currentMonthRechargeUsers=0;     // 当月充值人数

    // Getter & Setter 省略，可使用 Lombok 或 IDE 自动生成


    public static void main(String[] args) {

    }
}
