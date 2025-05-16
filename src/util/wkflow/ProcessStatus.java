package util.wkflow;

public enum ProcessStatus {
    PENDING,     // 待执行
    RUNNING,     // 正在执行
    SUSPENDED,   // 已暂停
    COMPLETED,   // 已完成
    FAILED,      // 执行失败
    TERMINATED   // 被中止
}
