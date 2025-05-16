package util.wkflow;




import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 表示一个流程定义或实例。
 */
@Data
public class ProcessWkfl {

    /** 流程唯一标识（可用于区分多个流程实例） */
    private String id;

    /** 流程名称（如：请假审批流程、订单处理流程） */
    private String name;

    /** 流程版本号（便于升级和兼容） */
    private String version;

    /** 流程状态（是否运行中、暂停、结束等） */
    private ProcessStatus status;

    /** 流程的描述信息 */
    private String description;

    /** 流程的开始时间 */
    private LocalDateTime startTime;

    /** 流程的结束时间（如已结束） */
    private LocalDateTime endTime;

    /** 流程启动者（可选） */
    private String initiator;

    /** 流程变量，全局上下文（供步骤读取） */
    private Map<String, Object> variables;

    /** 流程所属的业务类型或标签（如：finance, hr 等） */
    private String category;

    /** 流程的入口步骤 */
    private Step startStep;

    /** 流程中所有步骤（用于遍历/定义结构） */
    private List<Step> allSteps=new ArrayList<>();

    /** 流程扩展字段（预留） */
    private Map<String, Object> metadata;

    /** 构造函数自动生成唯一 ID */
    public ProcessWkfl() {
        this.id = UUID.randomUUID().toString();
        this.status = ProcessStatus.PENDING;
    }

    public void run() throws Throwable {
        for(Step step : allSteps)
        {
            step.run();
        }
    }

    // Getter 和 Setter 可使用 Lombok 简化
}
