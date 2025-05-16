package util.wkflow;

public enum StepType {
    ACTION,        // 操作步骤
    CONDITION,     // 条件判断步骤
    WAIT,          // 等待外部事件
    PARALLEL,      // 并行步骤
    MANUAL,        // 人工操作
    SCRIPT,        // 脚本执行
    END            // 流程终止
}
