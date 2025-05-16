package util.wkflow;

import lombok.Data;
import util.algo.RunnableThrowing;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 表示流程引擎中的一个步骤节点。
 */
@Data
public class NmlStep<T> extends Step {

    /** 步骤唯一标识 */
    private String id;

    /** 步骤名称（例如：审批、提交、审核等） */
    private String name;

    /** 步骤类型，如操作、判断、等待等 */
    private StepType type;

    /** 步骤描述，便于文档化或界面显示 */
    private String description;

    /** 输入参数，可以从流程上下文传入 */
    private Map<String, Object> inputDto;

    /** 输出结果，供后续步骤使用 */
    private Map<String, Object> outputDto;
    public List<RunnableThrowing> taskList = new ArrayList<>();




    @Override
    public   void  run( ) throws Throwable {
        for (RunnableThrowing task  : taskList) {

            {
                task .run();
                break;
            }

        }
    }


    /** 步骤超时时间，超过时间可标记为失败或等待 */
    private Duration timeout;

    /** 出现异常时的处理策略（重试次数、延时等） */
    private RetryPolicy retryPolicy;

    /** 步骤当前执行状态 */
    private StepStatus status;

    /** 附加元数据，支持自定义扩展 */
    private Map<String, Object> metadata;
}
