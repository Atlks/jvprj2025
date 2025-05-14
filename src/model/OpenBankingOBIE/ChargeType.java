package model.OpenBankingOBIE;

/**
 * 在 OBIE（Open Banking Implementation Entity）v3 中，ChargeType 表示费用的种类。虽然 OBIE 没有列出固定枚举值（它支持银行自定义扩展），但根据 UK OBIE 文档和金融业通用实践，常见的 ChargeType 枚举值如下：
 */
public enum ChargeType {
    SERVICE_FEE,              // 服务费，例如月账单管理费
    PAYMENT_FEE,              // 支付手续费
    INTERNATIONAL_TRANSFER,   // 国际汇款费用
    FX_FEE,                   // 外汇转换费
  //  ATM_FEE,                  // ATM 提现费
    OVERDRAFT_FEE,            // 透支费用
    LATE_PAYMENT_FEE,         // 逾期付款费
    CANCELLATION_FEE,         // 撤销、取消交易手续费
    RETURNED_PAYMENT_FEE,     // 退回付款费
  //  CARD_REPLACEMENT_FEE,     // 卡片补办费
    MAINTENANCE_FEE,          // 账户维护费
    TRANSFER_FEE,             // 普通转账手续费
   // CASH_ADVANCE_FEE,         // 信用卡取现手续费
    BALANCE_ENQUIRY_FEE,      // 查询余额费
    DISPUTE_FEE,              // 争议处理费
    OTHER                     // 其他费用
}

