package tools;

//import io.swagger.v3.oas.models.responses.ApiResponse;
import entityx.ApiResponse;
import org.springframework.http.ResponseEntity;

import static util.misc.Util2025.encodeJson;

/**
 * {
 * 	"body":{
 * 		"data":"ok......",
 * 		"message":"Success",
 * 		"statusCode":200
 * 	    },
 * 	"headers":{
 *
 *    },
 * 	"statusCode":"OK",
 * 	"statusCodeValue":200
 * }
 */
public class ResponseEntityT {
    public static void main(String[] args) {
        var data="ok......";
        // 创建自定义的 JSON 响应对象
        ApiResponse apiResponse = new ApiResponse("Success", data, 200);

        // 返回响应体并设置响应码 200 OK
        ResponseEntity<ApiResponse> ok = ResponseEntity.ok(apiResponse);


        System.out.println(encodeJson(ok));
    }
}
