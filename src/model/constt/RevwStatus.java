package model.constt;

/**
 * public static final Map<String, String> STATUS_DESCRIPTIONS = Map.of(
 *     STATUS_VALID, "有效",
 *     STATUS_EXPIRED, "过期",
 *     STATUS_REVOKED, "已撤销",
 *     STATUS_PENDING, "待审批"
 * );
 */
public enum RevwStatus {
    待审核, 通过, 拒绝
}

//void setStatus(Status status) {
//    // 只能传 Status 枚举，错了编译都不通过
//}

