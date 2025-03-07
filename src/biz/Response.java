package biz;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Response {


    //sucess ret
    public Response(Object data) {
        this.data = data;
    }

    public Response(Object data,PageInfo pageInfo) {
        this.data = data;
    }


    public Response(Object e,Object err_message) {
        this.data = e;
        this.stat_code=500;
        this.message=err_message;
    }

    private int stat_code=200;      // 状态码，如 200、400、500
    private Object message="ok"; // 响应信息，如 "OK" 或 "Error"
    private Object data="";        // 具体的数据对象

    private long timestamp= System.currentTimeMillis();    // 时间戳，前端可以用来计算请求时间
    private String path="";       // 请求路径（可用于调试）
    private String requestId="";  // 请求唯一 ID，便于追踪问题
    private Map<String, Object> debugInfo=new HashMap<>(); // 额外调试信息（可选）
    private PageInfo pageInfo=new PageInfo();    // 分页信息（仅对列表查询有效）
}