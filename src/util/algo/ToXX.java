package util.algo;

import com.alibaba.fastjson2.JSON;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static util.algo.EncodeUtil.decodeUrl;
import static util.misc.Util2025.encodeJson;
import static util.tx.QueryParamParser.toDto;


public class ToXX {


    public static String toSnake(String Camel_input) {
        if (Camel_input == null || Camel_input.isEmpty()) return Camel_input;
        return Camel_input.replaceAll("([a-z])([A-Z])", "$1_$2")
                .replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2")
                .toLowerCase();
    }

    public static @jakarta.validation.constraints.NotNull <T> T toDtoFrmHttp(HttpExchange exchange, Class<T> usrClass) throws Exception {

        if(exchange.getClass()==usrClass)
            return (T) exchange;
        
        
        
       

        // 解析查询参数到 Map
        Map<String, String> paramMap = toParamMapFromGetMthd(exchange);
     
        if(exchange.getRequestMethod().equalsIgnoreCase("POST"))
           paramMap=toMapFrmPostBody(exchange);
        System.out.println("parmmap="+encodeJson(paramMap));

        return toDto( paramMap,usrClass);
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

    public static <T> T toObjFrmQrystr(HttpExchange exchange, Class<?> class1) {
        Map<String, String> queryParams = parseQueryParams(exchange.getRequestURI());
        //    Class<?> class1 = OrdBet.class;
        T queryParamsDto= (T) toObjFrmMap(queryParams, class1);
        return  queryParamsDto;
    }


    public static Map<String, String>  toMapFrmPostBody(HttpExchange exchange) throws Exception {
       
       
        // 读取请求体
        InputStream inputStream = exchange.getRequestBody();
        String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        Map<String, String> queryParams = parseQryStr(body);
        //    Class<?> class1 = OrdBet.class;
       // T queryParamsDto= (T) toObjFrmMap(queryParams, class1);
        return  queryParams;
    }


    public static <T> T toObjFrmPostBody(HttpExchange exchange, Class<?> class1) throws Exception {
       
       
        // 读取请求体
        InputStream inputStream = exchange.getRequestBody();
        String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        Map<String, String> queryParams = parseQryStr(body);
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
     /**
      * 
      * @param qrystr   a=1&b=2
      * @return
      */
     public static Map<String, String> parseQryStr(String qrystr) {
        Map<String, String> queryParams = new HashMap<>();
        String query = qrystr; // 获取查询字符串（例如 ?id=123）
        if (query != null) {
            String[] pairs = query.split("&"); // 按 & 分割多个参数
            for (String pair : pairs) {
                String[] keyValue = pair.split("=", 2); // 按 = 分割键值对
                if (keyValue.length == 2) {
                    String value = keyValue[1];
                    value=decodeUrl(value);
                    queryParams.put(keyValue[0], value);
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
