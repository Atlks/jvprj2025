package model.constt;


/**
 * ISO 20022 订单状态 <GrpHdr><Sts> 的标准化取值
 *
 * 参考状态流：
 * PENDING 或 WAITING：订单已创建，等待后台审核。
 *
 * ACCP（Accepted）：审核通过，订单已确认处理。
 *
 * RJCT（Rejected）：审核未通过，订单被拒绝。
 *
 * CANC（Cancelled）：订单被取消，可能是用户或系统主动取消。
 *
 * ERROR：审核过程中出现错误。
 *
 *
 */
public enum RechargeOrderStat {

    /**
     * Accepted - 交易组已被接受并成功处理
     */
    ACCP("ACCP", "Accepted"),

    /**
     * Rejected - 交易组被拒绝
     */
    RJCT("RJCT", "Rejected"),

    /**
     * Pending - 交易组仍在处理中
     */
    PNDG("PNDG", "Pending"),

    /**
     * Partially Processed - 交易组部分交易已被处理，部分未处理
     */
    PART("PART", "Partially Processed"),

    /**
     * Error - 交易组发生错误
     */
    ERROR("ERROR", "Error"),

    /**
     * Cancelled - 交易组已被取消
     */
    CANC("CANC", "Cancelled"),

    /**
     * Unsent - 交易组尚未发送
     */
    USND("USND", "Unsent"),

    /**
     * Available - 资金或数据已准备好
     */
    AVAI("AVAI", "Available"),

    /**
     * Waiting - 交易组处于等待状态
     */
    WAIT("WAIT", "Waiting");

    private final String code;
    private final String description;

    /**
     * 构造函数
     * @param code 状态代码
     * @param description 状态描述
     */
    RechargeOrderStat(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 获取状态代码
     * @return 状态代码
     */
    public String getCode() {
        return code;
    }

    /**
     * 获取状态描述
     * @return 状态描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 根据状态代码获取状态枚举
     * @param code 状态代码
     * @return 对应的状态枚举
     */
    public static RechargeOrderStat fromCode(String code) {
        for (RechargeOrderStat status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }
}
