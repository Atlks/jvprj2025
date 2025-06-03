package util.model.openbank;

/**
 * 表示 OBIE 中定义的余额类型
 */
public enum BalanceTypes {
    /** 临时已清算余额（常用，实际到账） 也即是常见的总余额
     *
     * 如果你是做账务核对、审计对账，应该用 interimBooked。*/
    interimBooked,

    /** 临时可用余额（可能包含未清算交易） 可能包含部分pendding状态的余额
     * interimAvailable 是最贴近用户银行 App 所显示的“可用余额”（Available Balance）。
     *
     * 如果你是做支付、风控、资金校验，建议你使用 interimAvailable 作为主参考；
     *
     * 如果你是做账务核对、审计对账，应该用 interimBooked。
     *
     * */
    interimAvailable,


    /**
     * InterimCleared中间结算余额 —— 已清算但尚未完全结账的余额
     * 交易已经清算（Cleared）（比如资金确实到账或扣款已完成）；
     *
     * 但可能还**没有完全入账（Booked）**或结算周期未到；
     *
     * 可用于一些银行处理“浮动期间”的余额展示。
     */
    InterimCleared,

    /** 开始时的已清算余额（用于对账起点） */
  //  OpeningBooked,

    /** 结束时的已清算余额（用于对账终点） */
  //  ClosingBooked,

    /** 未来可用余额（含预测入账） */
    ForwardAvailable,

    /** 预计余额（未确定，但银行认为将到账） */
    Expected,

    /** 已授权但尚未清算的交易总额（如挂账） */
   // Authorised,

    /** 信息目的余额（可能用于展示或报表） */
    Information,




    /** 💰 累计充值金额（单位 ETH 或等值） */
        totalDeposited,

    //累计划转资金
    TOTAL_TRANSFERRED,

    //累计提现
    totalWthdr,



    //累计投资
    TOTAL_INVESTMENT,

    //累计投资收益
    TOTAL_INVESTMENT_income,

    //累计佣金 代理充值佣金（推广人数和充值佣金
    TOTAL_COMMISSIONS,
    ttl_cms_devSubsCnt,
    ttl_cms_subsRchgAmtSum,

    //累计得到奖励  (好像是发展人数得到的佣金
    totalReward,


    frz;

    /**
     * 从字符串安全解析（忽略大小写）
     */
    public static BalanceTypes fromString(String str) {
        for (BalanceTypes type : BalanceTypes.values()) {
            if (type.name().equalsIgnoreCase(str)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown balance type: " + str);
    }
}
