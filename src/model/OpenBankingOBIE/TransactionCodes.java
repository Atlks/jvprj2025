package model.OpenBankingOBIE;

/**
 * ✅ OBIE 的 Transaction Codes 源自 ISO 20022
 * OBIE 使用以下两个字段来描述交易类型：
 *
 * BankTransactionCode==trx type
 *
 * ProprietaryBankTransactionCode
 */
//BankTransactionCode
//交易类型，iso 20022  有表示
public enum TransactionCodes {
    ADJT,//	Adjustment	调账
    CRED,  // Credit Transfer - 充值、存款、转账入账
    DEBT,  // Direct Debit - 支出、扣款
    rchg,  //充值
    exchOut,//兑换支出
    loss, //亏损
    exchIn,
    adjstIn,adjstOut,
    wthdr,
    Profit,  //收益  income earning
    /**
     * COM - Commission (佣金)
     */
    COM,
    REFD,  // 退款
    /**
     * BON - Bonus / Reward (奖励、奖金)
     */
    BON,

    /**
     * INT - Interest (利息)
     */
   // INT,

    /**
     * DIV - Dividend (股息、分红  收益  股息)
     * 股息是收益的一种，但不是精确代表收益
     */
    DIV,

    /**
     * SAL - Salary / Payroll (工资)
     */
  //  SAL,

    /**
     * FEE - Fees (费用)
     */
    FEE,

    /**
     * CHG - Charges (服务费、杂费)
     */
    CHG,

    /**
     * LON - Loan (贷款相关)
     */
  //  LON,

    /**
     * TAX - Tax (税收)
     */
  //  TAX,

    /**
     * TRF - Transfer (转账)
     */
    TRF,

    /**
     * GOV - Government Payment (政府转账)
     */
  //  GOV,

    /**
     * OTH - Other (其他)
     */
    OTH,
    frz,
    unfrz

    ;



    

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
