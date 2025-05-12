package util.model.common;

import util.misc.PageInfo;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

import static util.excptn.ExptUtil.curUrl;
import static util.excptn.ExptUtil.requestIdCur;

/**
 * dep ...
 *  extends  io.swagger.v3.oas.models.responses.ApiResponse
 * now shld use  APIGatewayResponse
 * from swg apirps props
 *  private String description = null;
 *      *     private Map<String, Header> headers = null;
 *      *     private Content content = null;
 */
@Data
@Deprecated
public class ApiResponse {

    /**
     * dep
     * @param data
     */

    //sucess ret
    //not for page
    public ApiResponse(Object data) {
        this.data = data;
        this.path=curUrl.get();
        this.requestId="reqid__" +requestIdCur.get();

    }

    public ApiResponse(Object data, PageInfo pageInfo) {
        this.data = data;
    }


//    public ApiResponse(Object e, String statusCodeStr,String err_message) {
//        this.data = e;
//        this.statusCode = 500;
//        this.statusCodeStr=statusCodeStr;
//        this.errcode=statusCodeStr;
//        this.message = err_message;
//    }

    public static ApiResponse createResponse(Object data) {
        ApiResponse rs = new ApiResponse(data);
//        if (data instanceof PageResult) {
//            PageResult pr = (PageResult) data;
//            rs.data = pr.records;
////            rs.pageInfo.setPage(pr.page);
////            rs.pageInfo.setPagesize(pr.pagesize);
////            rs.pageInfo.setTotalRows(pr.totalRecords);
////            rs.pageInfo.setTotalPages(pr.totalPages);
//        } else if (data instanceof List<?>) {
//            List li = (List) data;
//         //   rs.pageInfo.setTotalRows(li.size());
//            //page1  pagesize xx
//            //total page 1
//
//        }

        return rs;
    }

//    public static ApiResponse createErrResponse(Throwable ex) {
//        return new ApiResponse(ex,ex.getClass().getName(), ex.getMessage());
//    }
//
//    public static ApiResponse createErrResponseWzErrcode(ExceptionBase ex) {
//        return new ApiResponse(ex, ex.errcode,ex.getMessage());
//    }

    public String statusCodeStr;
    public String errcode;
    private int statusCode = 200;      // 状态码，如 200、400、500
    private Object message = "ok"; //statCodeStr  响应信息，如 "OK" 或 "Error"
    private Object data = "";        // 具体的数据对象

    private long timestamp = System.currentTimeMillis();    // 时间戳，前端可以用来计算请求时间
    private String path = "";       // 请求路径（可用于调试）
    private String requestId = "";  // 请求唯一 ID，便于追踪问题
    private Map<String, Object> debugInfo = new HashMap<>(); // 额外调试信息（可选）
    //public PageInfo pageInfo = new PageInfo();    // 分页信息（仅对列表查询有效）
}