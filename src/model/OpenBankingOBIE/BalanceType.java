public enum BalanceType {

    /**
     * AVAILABLE: 可用余额
     * 这是指账户中可以立即使用的资金，减去了任何未结算的交易或冻结金额。
     * 适用于用户可以实际使用的余额，如进行支付、转账等。
     */
    AVAILABLE("AVAILABLE"),

    /**
     * CURRENT: 当前余额  current
     * 这是指账户中的总余额，包括所有已结算的交易。
     * 它可能包含一些不一定是可用的资金（例如，冻结的资金可能被包含在内）。
     */
    CURRENT("CURRENT"),

    /**
     * FROZEN: 冻结余额
     * 账户中因某些原因被冻结的资金，这些资金无法被用户使用。
     * 通常发生在法律要求、账户争议或欺诈调查等情况下。
     */
    FROZEN("FROZEN"),

    /**
     * PENDING: 待处理余额
     * 账户中的部分资金正在处理中，通常是指一些未清算的交易或支付。
     * 这些金额尚未完全确定，一旦交易完成，可能会变成可用余额或其他类型。
     */
    PENDING("PENDING"),

    /**
     * CLEARED: 已清算余额
     * 这是指账户中的资金已经经过清算，所有交易都已经完成，余额现在是可用的。
     * 适用于已经过所有必要处理的余额。
     */
    CLEARED("CLEARED"),

    /**
     * OVERDRAFT: 透支余额
     * 账户允许超过其实际余额的额度，通常是银行为账户提供的透支额度。
     * 用户可以使用超出账户余额的资金，但通常会产生额外费用或利息。
     */
    OVERDRAFT("OVERDRAFT"),

    /**
     * INACTIVE: 非活跃余额
     * 账户中由于账户长时间不活跃或其他原因而不能使用的资金。
     * 这些资金可能由于长期不活动被暂时锁定或被冻结。
     */
    INACTIVE("INACTIVE"),

    /**
     * RESERVED: 预留余额
     * 账户中预留用于特定用途的资金，这些金额尚不可用，通常是为了保证某些交易的完成。
     * 例如，酒店预订或租车时，商家可能会冻结一部分余额作为担保金。
     */
    RESERVED("RESERVED"),

    /**
     * LOCKED: 锁定余额
     * 账户余额因某些特定条件或事件而被完全锁定，无法使用。
     * 例如，账户可能因涉嫌欺诈行为而被锁定，导致余额无法动用。
     */
    LOCKED("LOCKED");

    // 枚举字段，表示余额类型
    private final String value;

    // 构造器，用于设置每个枚举值的对应字符串
    BalanceType(String value) {
        this.value = value;
    }

    // 获取枚举值的字符串表示
    public String getValue() {
        return value;
    }

    // 根据字符串值获取对应的枚举
    public static BalanceType fromValue(String value) {
        for (BalanceType balanceType : BalanceType.values()) {
            if (balanceType.getValue().equalsIgnoreCase(value)) {
                return balanceType;
            }
        }
        throw new IllegalArgumentException("Unknown balance type: " + value);
    }
}
