package model.OpenBankingOBIE;

public enum AmountType {
    /**
     * 用户在支付指令中明确请求支付的金额。
     * OBIE字段名: InstructedAmount
     */
    INSTRUCTED,

    /**
     * 实际从付款人账户中扣除的金额（可能包含手续费）。
     */
    DEBITED,

    /**
     * 实际记入收款人账户的金额（可能被扣除手续费）。
     */
    CREDITED,

    /**
     * 银行或支付系统收取的手续费金额。
     */
    FEE,

    /**
     * 银行在支付过程中预估的将被扣除的金额（可能用于展示）。
     */
    ESTIMATED_DEBITED,

    /**
     * 实际清算（settlement）中处理的金额，常用于跨境或中介支付。
     */
    SETTLED,

    /**
     * 实际交换的货币金额（如外汇转换后的值）。
     */
    EXCHANGED
}
