package model.OpenBankingOBIE;

/**
 * 枚举值	含义说明
 * AccountClosure	账户关闭对账单。提供账户正式关闭前的最终对账单，通常作为账户结算的最后记录。
 * AccountOpening	账户开户对账单。用于账户开立时生成的对账单，一般包含初始余额等信息。
 * Annual	年度对账单。每年生成一次的总结性对账单，涵盖年度交易汇总。
 * Interim	临时对账单。在正式周期对账单之前生成的临时性记录，对特定期间的数据进行快照。
 * RegularPeriodic	定期周期性对账单。如按月、按季度生成的标准周期对账单，是最常见的类型。
 */
public enum StatementType {
    AccountClosure,
    AccountOpening,
            Annual,
    Interim,
            RegularPeriodic,
    xMonthly,xWeekly, xTodate;

    public static StatementType safeValueOf(String name) {
        try {
            return StatementType.valueOf(name);
        } catch (Exception e) {
            return null; // 或默认值
        }
    }
}
