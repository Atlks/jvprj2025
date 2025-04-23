package model.OpenBankingOBIE;


/**
 * transactionStatus 的典型取值（参考 OBIE v3.1+ 和 ISO 20022）
 *
 * 取值	含义（中文）	说明
 * Booked	已入账	交易已在账户中正式记账完成
 * Pending	待处理 / 待清算	交易正在处理中，尚未入账
 * Cancelled	已取消	交易被取消，不会发生资金变动
 * Rejected	被拒绝	银行拒绝处理该交易请求
 * Information	仅供信息参考，不表示真实交易	某些银行用于展示如预计付款、汇率信息等非真实交易
 */
public enum TransactionStatus {
    BOOKED,
    PENDING,
    CANCELLED,
    REJECTED,
    INFORMATION;

    public static TransactionStatus fromValue(String value) {
        return valueOf(value.toUpperCase());
    }
}

