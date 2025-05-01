package model.OpenBankingOBIE;

/**
 * obie v3 for pament
 */
public enum TransactionStatusCode {
    AcceptedCreditSettlementCompleted,  // 支付完成并已结算
    AcceptedSettlementInProcess,        // 支付接受，结算处理中
    AcceptedTechnicalValidation,        // 通过技术验证，等待授权
    Pending,                            // 等待用户处理或银行处理
    Rejected,                           // 拒绝
    Cancelled                           // 被取消
}
