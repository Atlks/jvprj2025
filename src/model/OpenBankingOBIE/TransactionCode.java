package model.OpenBankingOBIE;

/**
 * ✅ OBIE 的 Transaction Codes 源自 ISO 20022
 * OBIE 使用以下两个字段来描述交易类型：
 *
 * BankTransactionCode==trx type
 *
 * ProprietaryBankTransactionCode
 *iso20022 Transaction Codes
 * Domain	含义	示例
 * adjst
 * PMNT	Payments（支付）	PMNT-ICDT-STDO（标准直接借记）
 * CARD	Card Transactions（银行卡）	CARD-CCRD-PURC（信用卡消费）
 * CASH	Cash Transactions（现金）	CASH-DEPT-ATMW（ATM取现）
 * INTR	Internal Transfers（内部转账）	INTR-FUND-INTR（账户间转账）
 * CHRG	Charges（费用）	CHRG-OTHR-MNTH（月费）
 * INTP	Interest（利息）	INTP-CRED-INTC（利息收入）
 * GOVT	Government（税费等）	GOVT-TAXS-PYMT（税款支付）
 * SECU	Securities（证券类）	SECU-BUY-EXEQ（证券买入执行）
 * SCVE	表示 Service（服务类交易）
 */
//BankTransactionCode
//交易类型，iso 20022  有表示
public enum TransactionCode {
    adjst_crdt,
            // Credit Transfer - 充值、存款、转账入账
            adjst_dbt,  // Direct Debit - 支出、扣款


    adjst_loss, //亏损

    Payment_wthdr, //提现


    payment_rechg,  //充值

    InternalTransfers_exchg,  //划转兑换
    invstProfit,  //收益  income  earning


    invstLoss,//  投资损失
    Service_Cms_rechgCms,  //佣金（下级充值时，代理会有佣金
    Service_Cms_devlpSubsCntCms,// 另外一种佣金，看看你是奖励（多一个下级注册人数，就给代理一个佣金，按照注册数量给的这个）

    /**
     * OTH - Other (其他)
     */
    OTH,
    adjst_frz,
    adjst_unfrz

    ;


   // public static String adjst_add;

    /**
     * 根据字符串 code 获取对应枚举，默认返回 OTH（其他）
     */
    public static TransactionCode fromCode(String code) {
        for (TransactionCode tc : values()) {
            if (tc.name().equalsIgnoreCase(code)) {
                return tc;
            }
        }
        return OTH;
    }

}
