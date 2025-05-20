package util.model;

import lombok.Data;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 表示一次 FaaS 调用时的上下文信息
 */
@Data
public class Context {

    /** 当前函数名称 */
    private String functionName;

    /** 函数版本 */
    private String functionVersion;

    /** 分配的最大内存（MB） */
    private Integer memoryLimitInMB;

    /** 超时时间（秒） */
    private Integer timeout;

    /** 请求唯一 ID，用于链路追踪 */
    private String requestId;

    /** 调用时间戳 */
    private OffsetDateTime timestamp;

    /** 函数被调用的唯一 ARN（如 AWS） */
    private String invokedFunctionArn;

    /** 日志组名称 */
    private String logGroupName;

    /** 日志流标识 */
    private String logStreamName;

    /** 身份信息（如用户/角色等） */
    private Map<String, Object> identity;

    public  String currUsername="";

    /** 客户端上下文信息（如设备、应用版本等） */
    private Map<String, Object> clientContext;

    /** 链路追踪 ID */
    private String traceId;

    /** 云平台区域 */
    private String region;

    /** 环境变量快照 */
    private Map<String, String> environment;

    /** 自定义上下文数据 */
    private Map<String, Object> customContext;

    /** 事件来源类型（如 HTTP / CRON / PubSub） */
    private String eventSource="http";

    /** 身份验证令牌（如 JWT） */
    private String authToken="jwt";

    /** ⬇️ 数据库配置（不会直接持有连接，而是持有连接参数） */
    private DbConfig dbConfig;

    // Getters and setters...

    public static class DbConfig {
        private String url;
        private String username;
        private String password;
        private String driverClass;
    }
public  Map<String,Object> cfg=new HashMap<>();
    public Session session;
    public SessionFactory sessionFactory;

        // Getters & Setters (可使用 Lombok 简化)

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    // 其他 getters/setters 省略...

    @Override
    public String toString() {
        return "FaasContext{" +
                "functionName='" + functionName + '\'' +
                ", functionVersion='" + functionVersion + '\'' +
                ", memoryLimitInMB=" + memoryLimitInMB +
                ", requestId='" + requestId + '\'' +
                ", region='" + region + '\'' +
                ", eventSource='" + eventSource + '\'' +
                '}';
    }
}

