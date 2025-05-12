package util.oo;

import com.sun.net.httpserver.HttpExchange;
import jakarta.validation.constraints.NotBlank;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.trim;
import static util.algo.ToXX.*;

public class HttpUti {

    public static Map<String, String>  toMapFrmPostBody(HttpExchange exchange) throws Exception {


        // 读取请求体
        InputStream inputStream = exchange.getRequestBody();
        String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        Map<String, String> queryParams = parseQryStr(body);
        //    Class<?> class1 = OrdBet.class;
        // T queryParamsDto= (T) toObjFrmMap(queryParams, class1);
        return  queryParams;
    }

    /**
     * 从 HTTP GET 请求中提取查询参数并构造成实例或参数 Map
     *
     * @param exchange HttpExchange 对象
     * @param usrClass 目标用户类（带无参构造）
     * @param <T> 泛型类型
     * @return 如果无参数，则返回新实例；否则返回参数 Map
     */
    public static  Map<String, String> toParamMapFromGetMthd(HttpExchange exchange)
            throws Exception {
        String query = exchange.getRequestURI().getQuery();

        // 如果没有参数，返回默认实例
        if (query == null || query.isEmpty()) {
            return  new HashMap();
        }

        // 有参数，解析为 Map<String, String>
        return parseQueryParams(exchange.getRequestURI());
    }
    public static ThreadLocal<HttpExchange> httpExchangeCurThrd = new ThreadLocal<>();

    // 解析查询参数的方法
    public static Map<String, String> parseQueryParams(URI uri) {
        Map<String, String> queryParams = new HashMap<>();
        String query = uri.getQuery(); // 获取查询字符串（例如 ?id=123）
        if (query != null) {
            String[] pairs = query.split("&"); // 按 & 分割多个参数
            for (String pair : pairs) {
                String[] keyValue = pair.split("=", 2); // 按 = 分割键值对
                if (keyValue.length == 2) {
                    queryParams.put(keyValue[0], keyValue[1]);
                } else {
                    queryParams.put(keyValue[0], ""); // 没有值时设为空字符串
                }
            }
        }
        return queryParams;
    }

    @NotNull
    public static Map<String, String> getParamMap(HttpExchange exchange) throws Exception {
        Map<String, String> paramMap = toParamMapFromGetMthd(exchange);

        if(exchange.getRequestMethod().equalsIgnoreCase("POST"))
            paramMap=toMapFrmPostBody(exchange);
        return paramMap;
    }
    //  从http请求头部提取    AuthChkMode  标头  dft jwt
    public static @NotBlank String getAuthChkMode(HttpExchange he) {
// 从请求头中获取 "AuthChkMode" 标头
        String authChkMode = he.getRequestHeaders().getFirst("AuthChkMode");
        if (authChkMode == null)
            return "jwt";
        return trim(authChkMode);

    }
}
