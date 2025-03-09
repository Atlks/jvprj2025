package biz;

import entityx.PageResult;
import lombok.Data;
import util.excptn.ExceptionBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Response {


    //sucess ret
    //not for page
    public Response(Object data) {
        this.data = data;

    }

    public Response(Object data, PageInfo pageInfo) {
        this.data = data;
    }


    public Response(Object e, Object err_message) {
        this.data = e;
        this.stat_code = 500;
        this.message = err_message;
    }

    public static Response createResponse(Object data) {
        Response rs = new Response(data);
        if (data instanceof PageResult) {
            PageResult pr = (PageResult) data;
            rs.data = pr.records;
            rs.pageInfo.setPage(pr.page);
            rs.pageInfo.setPagesize(pr.pagesize);
            rs.pageInfo.setTotalRows(pr.totalRecords);
            rs.pageInfo.setTotalPages(pr.totalPages);
        } else if (data instanceof List<?>) {
            List li = (List) data;
            rs.pageInfo.setTotalRows(li.size());
            //page1  pagesize xx
            //total page 1

        }

        return rs;
    }

    public static Response createErrResponse(Throwable ex) {
        return new Response(ex, ex.getMessage());
    }

    public static Response createErrResponseWzErrcode(ExceptionBase ex) {
        return new Response(ex, ex.errcode);
    }


    private int stat_code = 200;      // 状态码，如 200、400、500
    private Object message = "ok"; //statCodeStr  响应信息，如 "OK" 或 "Error"
    private Object data = "";        // 具体的数据对象

    private long timestamp = System.currentTimeMillis();    // 时间戳，前端可以用来计算请求时间
    private String path = "";       // 请求路径（可用于调试）
    private String requestId = "";  // 请求唯一 ID，便于追踪问题
    private Map<String, Object> debugInfo = new HashMap<>(); // 额外调试信息（可选）
    public PageInfo pageInfo = new PageInfo();    // 分页信息（仅对列表查询有效）
}