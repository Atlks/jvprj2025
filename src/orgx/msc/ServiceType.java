package orgx.msc;


/**
 * 管理员使用admin svs type
 */
public enum ServiceType {
    USER_SERVICE,
    ACCOUNT_SERVICE,
    TRANSACTION_SERVICE,
    BALANCE_SERVICE,
    PAYMENT_SERVICE,
    BILLING_SERVICE,  //bill账单通常指 应付款项，包含商品、服务的费用 ✅ 用途：用户支付时看到的费用清单，如水电费、购物账单
    NOTIFICATION_SERVICE,
    AUTHENTICATION_SERVICE,
    SECURITY_SERVICE,

    /**
     * REPORT（财务报表）
     * ✅ 概念：报表更偏向 财务分析，通常是公司用来汇总数据
     * ✅ 用途：分析利润、现金流、财务健康状况 ✅ 内容：
     * 充值人数，充值金额 （好像也是交易明细的汇总啊
     */
    REPORTING_SERVICE,

    STATEMENT_SERVICE,
   // STATEMENT类似银行的月度对账单,主要关注 余额变化 交易明细汇总
    AUDIT_SERVICE,
    SETTLEMENT_SERVICE;
}
