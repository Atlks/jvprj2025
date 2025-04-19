package model.constt;

/**
 * public static final Map<String, String> STATUS_DESCRIPTIONS = Map.of(
 *     STATUS_VALID, "有效",
 *     STATUS_EXPIRED, "过期",
 *     STATUS_REVOKED, "已撤销",
 *     STATUS_PENDING, "待审批"
 * );
 */
public enum EnableStatus {
    ENABLE, DISABLE,STATUS_REVOKED,STATUS_PENDING,STATUS_VALID
}

//void setStatus(Status status) {
//    // 只能传 Status 枚举，错了编译都不通过
//}

