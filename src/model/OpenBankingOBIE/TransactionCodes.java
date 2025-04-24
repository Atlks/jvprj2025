package model.OpenBankingOBIE;



//交易类型，iso 20022  有表示
public enum TransactionCodes {

    /**
     * COM - Commission (佣金)
     */
    COM("Commission"),

    /**
     * BON - Bonus / Reward (奖励、奖金)
     */
    BON("Bonus / Reward"),

    /**
     * INT - Interest (利息)
     */
    INT("Interest"),

    /**
     * DIV - Dividend (股息、分红  收益)
     */
    DIV("Dividend"),

    /**
     * SAL - Salary / Payroll (工资)
     */
    SAL("Salary / Payroll"),

    /**
     * FEE - Fees (费用)
     */
    FEE("Fees"),

    /**
     * CHG - Charges (服务费、杂费)
     */
    CHG("Charges"),

    /**
     * LON - Loan (贷款相关)
     */
    LON("Loan"),

    /**
     * TAX - Tax (税收)
     */
    TAX("Tax"),

    /**
     * TRF - Transfer (转账)
     */
    TRF("Transfer"),

    /**
     * GOV - Government Payment (政府转账)
     */
    GOV("Government Payment"),

    /**
     * OTH - Other (其他)
     */
    OTH("Other");

    private final String description;

    TransactionCodes(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    /**
     * 根据字符串 code 获取对应枚举，默认返回 OTH（其他）
     */
    public static TransactionCodes fromCode(String code) {
        for (TransactionCodes tc : values()) {
            if (tc.name().equalsIgnoreCase(code)) {
                return tc;
            }
        }
        return OTH;
    }

}
