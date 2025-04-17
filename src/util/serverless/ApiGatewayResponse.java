package util.serverless;

import entityx.ApiResponse;
import lombok.Data;
import util.excptn.ExceptionBase;

import java.util.HashMap;
import java.util.Map;

import static util.excptn.ExptUtil.curUrl;
import static util.excptn.ExptUtil.requestIdCur;

@Data
public class ApiGatewayResponse {

    public ApiGatewayResponse(Object data) {
        this.body = data;
        this.path=curUrl.get();
        this.requestId="reqid__" +requestIdCur.get();

    }
    public int statusCode=200;
    public String errcode="";   //ok
    public Map<String, String> headers=new HashMap<>();
    public Object body="";
    public String message_err="";
    public String message="";
    public long timestamp = System.currentTimeMillis();    // 时间戳，前端可以用来计算请求
    // 构建器模式 builder() 实现简化写法
    public String path = "";       // 请求路径（可用于调试）
    public String requestId = "";  // 请求唯一 ID，便于追踪问题
    public Map<String, Object> debugInfo = new HashMap<>(); // 额外调试信息（可选）
   // public Object data = "";        // 具体的数据对象
   public ApiGatewayResponse(Object e, String statusCodeStr,String err_message) {
        this.body = e;
        this.statusCode = 500;
      //  this.statusCodeStr=statusCodeStr;
        this.errcode=statusCodeStr;
        this.message = err_message;
    }

    public static ApiGatewayResponse createErrResponse(Throwable ex) {
        return new ApiGatewayResponse(ex,ex.getClass().getName(), ex.getMessage());
    }

    public static ApiGatewayResponse createErrResponseWzErrcode(ExceptionBase ex) {
        return new ApiGatewayResponse(ex, ex.errcode,ex.getMessage());
    }
}

