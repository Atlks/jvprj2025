package util;

import com.alibaba.fastjson2.JSON;
import com.sun.net.httpserver.HttpExchange;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;



public class ToXX {
    public static <T> T toObjFrmQrystr(HttpExchange exchange, Class<?> class1) {
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
