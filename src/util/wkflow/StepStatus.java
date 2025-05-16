package util.wkflow;

public enum StepStatus {
    PENDING,       // 等待执行
    RUNNING,       // 正在执行
    SUCCESS,       // 执行成功
    FAILED,        // 执行失败
    SKIPPED        // 被跳过
}

