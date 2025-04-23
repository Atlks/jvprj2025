//package model.constt;
//
//
///**
// *   OpenBanking 的TransactionStatus
// *
// *
// */
//public enum TransactionStatus {
//
//    /**
//     * Accepted - 交易组已被接受并成功处理
//     */
//    COMPLETED("COMPLETED", "Accepted"),
//
//    /**
//     * Rejected - 交易组被拒绝
//     */
//    Rejected("Rejected", "Rejected"),
//
//    /**
//     * Pending - 交易组仍在处理中
//     */
//    PENDING("PENDING", "Pending"),
//
//
//    /**
//     * Cancelled - 交易组已被取消
//     */
//    CANC("CANC", "Cancelled");
//
//      private final String code;
//    private final String description;
//
//    /**
//     * 构造函数
//     * @param code 状态代码
//     * @param description 状态描述
//     */
//    TransactionStatus(String code, String description) {
//        this.code = code;
//        this.description = description;
//    }
//
//    /**
//     * 获取状态代码
//     * @return 状态代码
//     */
//    public String getCode() {
//        return code;
//    }
//
//    /**
//     * 获取状态描述
//     * @return 状态描述
//     */
//    public String getDescription() {
//        return description;
//    }
//
//    /**
//     * 根据状态代码获取状态枚举
//     * @param code 状态代码
//     * @return 对应的状态枚举
//     */
//    public static TransactionStatus fromCode(String code) {
//        for (TransactionStatus status : values()) {
//            if (status.getCode().equals(code)) {
//                return status;
//            }
//        }
//        throw new IllegalArgumentException("Unknown code: " + code);
//    }
//}
