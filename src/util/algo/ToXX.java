package util.algo;

import com.alibaba.fastjson2.JSON;
import com.sun.net.httpserver.HttpExchange;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static util.misc.Util2025.encodeJson;
import static util.tx.QueryParamParser.toDto;


public class ToXX {

    public static @jakarta.validation.constraints.NotNull <T> T toDtoFrmHttp(HttpExchange exchange, Class<T> usrClass) throws Exception {

        if(exchange.getClass()==usrClass)
            return (T) exchange;
        
        
        
            // 获取查询参数 ?name=John&age=30
        String query = exchange.getRequestURI().getQuery();
        if (query == null || query.isEmpty()) {
            return usrClass.getConstructor().newInstance();
        }

        // 解析查询参数到 Map
        Map<String, String> paramMap = parseQueryParams(exchange.getRequestURI());
        System.out.println("parmmap="+encodeJson(paramMap));

        return toDto( paramMap,usrClass);
    }

    public static <T> T toObjFrmQrystr(HttpExchange exchange, Class<?> class1) {
        Map<String, String> queryParams = parseQueryParams(exchange.getRequestURI());
        //    Class<?> class1 = OrdBet.class;
        T queryParamsDto= (T) toObjFrmMap(queryParams, class1);
        return  queryParamsDto;
    }

    public static <T> T toObjFrmPostBody(HttpExchange exchange, Class<?> class1) {
       
       
        // 读取请求体
        InputStream inputStream = exchange.getRequestBody();
        String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        Map<String, String> queryParams = parseQueryParams(exchange.getRequestURI());
        //    Class<?> class1 = OrdBet.class;
        T queryParamsDto= (T) toObjFrmMap(queryParams, class1);
        return  queryParamsDto;
    }



    // 解析查询参数的方法
    public static Map<String, String> getRequestParameters(URI uri) {
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
    /**
     * 将map转换为给定的实体类。。如果需要使用json，使用fastjson2
     * @param map
     * @param Class1
     * @return
     * @param <t>
     */
    public static <t> t toObjFrmMap(Map<String, String> map, Class<t> Class1) {

        // 将 Map 转换为 JSON 字符串
        String jsonString = JSON.toJSONString(map);

        // 将 JSON 字符串解析为目标对象
        return JSON.parseObject(jsonString, Class1);
    }
}
