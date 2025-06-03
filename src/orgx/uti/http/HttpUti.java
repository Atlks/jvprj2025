package orgx.uti.http;

import com.sun.net.httpserver.HttpExchange;
import io.javalin.http.Context;
import io.javalin.http.HandlerType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.core.MediaType;
import orgx.uti.Uti;
import orgx.uti.context.ProcessContext;
import orgx.uti.orm.FunctionX;

import java.io.*;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static orgx.uti.Uti.*;
import static orgx.uti.Uti.encodeJson;
import static orgx.uti.context.ThreadContext.*;
// static orgx.uti.http.HttpUti.hasPermission;


public class HttpUti {






    public static void printStackTrace(Exception e) {
        System.out.println("---catch by httpgetHdl");
        System.out.flush(); // 确保立即输出到控制台
        e.printStackTrace();

        System.err.flush();
        flushBySlp10ms();
        System.out.println("---end catch by httpgetHdl");
        System.out.flush(); // 确保立即输出到控制台
    }

    private static void flushBySlp10ms() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException ex) {
          //  throw new RuntimeException(ex);
        }
    }

    public static <T> Object callFun(FunctionX<T, Object> mOrFun,
                                 Class<?> dtoClz, String fname, HttpExchange httpExchange) throws Throwable {
        return  _call(mOrFun,dtoClz,fname,httpExchange);

    }
    public static Object callMthd(Method mOrFun,
                              Class<?> dtoClz, String fname, HttpExchange httpExchange4toDto) throws Throwable {
        return  _call(mOrFun,dtoClz,fname,httpExchange4toDto);

    }
    public static Object toDto(HttpExchange httpExchange, Class<?> dtoClz) throws Exception {
        Map<String, List<String>> mp = getParamMap(httpExchange);
        Object dto = Uti. toDto(mp, dtoClz);
        return dto;
    }
    /**
     * inner mthod
     * @param mOrFun
     * @param dtoClz
     * @param fname
     * @param httpExchange
     * @return
     * @throws IOException
     */
    public static Object _call(Object mOrFun,
                               Class<?> dtoClz, String fname, HttpExchange httpExchange) throws Throwable {
      //  HttpExchange exchange = ThreadContext.currHttpExchange.get();
        Map<String, List<String>> mp = getParamMap(httpExchange);
        //auto tx ,commit n  roolback

            Object dto = Uti.toDto(mp, dtoClz);
            valdt(dto);
            System.out.println("fun " + fname + "(" + encodeJson(dto));
            Object rzt = callFunOrMthd(mOrFun, dto, dtoClz);
            System.out.println("endfun " + fname);
            return rzt;
      //  });
     //   return rzt;
    }

    public static void setThrdHttpContext(Context ctx) {
        HttpHeader value = extractHeaders(ctx);
        System.out.println(encodeJson(value));
        currHttpHeader.set(value);
        currJvlnContext.set(ctx);
    }


//    private static <T> Object setHdlCore(String path, FunctionX<T, Object> fun, Context ctx) {
//
//        return o;
//    }



    public static HttpHeader extractHeaders(Context javalinContext) {
        return new HttpHeader(
                javalinContext.method(),
                javalinContext.url(),
                javalinContext.header("Host"),
                javalinContext.header("User-Agent"),
                javalinContext.header("Content-Type"),
                javalinContext.header("Accept"),
                javalinContext.header("Authorization"),
                javalinContext.header("Cache-Control"),
                javalinContext.header("Connection"),
                javalinContext.header("Referer"),
                toHttpHeadMultiValueMode(javalinContext.headerMap())   // 获取所有自定义头部信息
        );
    }

    /**
     * 转换为标准的http head
     * @param javalinContextheaderMap
     * @return
     */
    private static Map<String, List<String>> toHttpHeadMultiValueMode(Map<String, String> javalinContextheaderMap) {
        Map<String, List<String>> httpHeaders = new HashMap<>();

        for (Map.Entry<String, String> entry : javalinContextheaderMap.entrySet()) {
            httpHeaders.put(entry.getKey(), Collections.singletonList(entry.getValue())); // 转换为 List<String>
        }

        return httpHeaders;
    }


    /**
     * write
     *
     * @param rzt
     * @param httpExchange
     * @throws IOException
     */
    public static void write(Object rzt, HttpExchange httpExchange) throws IOException {

        // HttpExchange ctx= currHttpExchange.get();
        String cttType2 = String.valueOf(httpExchange.getResponseHeaders().get("Content-Type"));

//        if (cttType2.contains(MediaType.APPLICATION_JSON)) {
//
//
//        }
        if (cttType2.contains(MediaType.TEXT_PLAIN)) {

            writeTxt(rzt.toString(),httpExchange);
        }else
        {

            writeJson((rzt),httpExchange);
           // wrtJson(encodeJson(rzt));
        }
    }
    public static void setThrdHttpContext(HttpExchange exchange) throws IOException {
        HttpHeader value = extractHeaders4sdkweb(exchange);
        System.out.println("setThrdHttpContext::"+encodeJson(value));
        currHttpHeader.set(value);
        currHttpExchange.set(exchange);
        currHttpParamMap.set(getParamMap(exchange) );
     //   Map<String, List<String>> mp = getParamMap(ctx);
        String cttType = MediaType.APPLICATION_JSON;
        setContentType(cttType + "; charset=UTF-8");
    }

    public static Map<String, List<String>> getParamMap(HttpExchange exchange) throws IOException {
        Map<String, List<String>> paramMap = new HashMap<>();

        // 解析 GET 请求的 URL 查询参数
        String query = exchange.getRequestURI().getQuery();
        if (query != null) {
            parseQueryParams(query, paramMap);
        }

        // 解析 POST 请求的请求体参数
        if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            StringBuilder requestBody = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                requestBody.append(line);
            }
            parseQueryParams(requestBody.toString(), paramMap);
        }

        return paramMap;
    }

    // 解析查询参数的方法
    private static void parseQueryParams(String query, Map<String, List<String>> paramMap) throws UnsupportedEncodingException {
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=", 2);
            if (keyValue.length == 2) {
                String key = URLDecoder.decode(keyValue[0], "UTF-8");
                String value = URLDecoder.decode(keyValue[1], "UTF-8");
                paramMap.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
            }
        }
    }

    private static HttpHeader extractHeaders4sdkweb(HttpExchange exchange) {
        return new HttpHeader(
                toJavalinHandlerType(exchange.getRequestMethod()) ,  // 获取 HTTP 方法
                exchange.getRequestURI().toString(),  // 获取 URL
                getHeader(exchange, "Host"),
                getHeader(exchange, "User-Agent"),
                getHeader(exchange, "Content-Type"),
                getHeader(exchange, "Accept"),
                getHeader(exchange, "Authorization"),
                getHeader(exchange, "Cache-Control"),
                getHeader(exchange, "Connection"),
                getHeader(exchange, "Referer"),
                exchange.getRequestHeaders() // 获取所有请求头
        );
    }
    public static void sendErrorResponse(HttpExchange exchange, int errCode, String errmsg) {
        try {
            String contentType = MediaType.APPLICATION_JSON + "; charset=UTF-8";
            setContentType(contentType,exchange);
            byte[] response = errmsg.getBytes("utf8");
            exchange.sendResponseHeaders(errCode, response.length);
            exchange.getResponseBody().write(response);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            exchange.close();
        }
    }

    public static HandlerType toJavalinHandlerType(String requestMethod) {
        try {
            return HandlerType.valueOf(requestMethod.toUpperCase()); // 转换为 HandlerType
        } catch (IllegalArgumentException e) {
            System.err.println("无效的请求方法: " + requestMethod);
            return HandlerType.GET; // 默认返回 GET
        }
    }

    // 获取指定请求头的方法
    public static String getHeader(HttpExchange exchange, String headerName) {
        List<String> headers = exchange.getRequestHeaders().get(headerName);
        return (headers != null && !headers.isEmpty()) ? headers.get(0) : "";
    }

    private static String decode(String value) {
        try {
            return URLDecoder.decode(value, "UTF-8"); // 解析 URL 编码参数
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("解码失败", e);
        }
    }

    private static String[] appendValue(String[] array, String value) {
        String[] newArray = new String[array.length + 1];
        System.arraycopy(array, 0, newArray, 0, array.length);
        newArray[array.length] = value;
        return newArray;
    }
    public static Map<String, List<String>> parseQueryParams(HttpExchange exchange) {
        Map<String, List<String>> queryParams = new HashMap<>();
        String query = exchange.getRequestURI().getQuery();

        if (query == null || query.isEmpty()) {
            return queryParams; // 空查询参数
        }

        for (String param : query.split("&")) {
            String[] keyValue = param.split("=");
            if (keyValue.length == 2) {
                String key = keyValue[0];
                String value = keyValue[1];

                queryParams.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
            }
        }
        return queryParams;
    }

    public static void writeTxt(String s,@NotNull HttpExchange httpExchange) throws IOException {

        setContentType(MediaType.TEXT_PLAIN + "; charset=UTF-8");
        write((s),httpExchange);
    }

    public static void writeJson(Object s,@NotNull HttpExchange httpExchange) throws IOException {

        setContentType(MediaType.APPLICATION_JSON + "; charset=UTF-8");
        write(encodeJson(s),httpExchange);
    }
    public static void write(String s, @NotNull HttpExchange httpExchange) throws IOException {

        if (httpExchange != null) {
            byte[] response = s.getBytes(StandardCharsets.UTF_8);
            httpExchange.sendResponseHeaders(200, response.length);
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(response);
            }
        }
    }

//    /**
//     * 输出数据
//     * @param s
//     */
//    public static void wrtJson(String s) throws IOException {
//        HttpExchange ctx=   currHttpExchange.get();
//        if (ctx != null) {
//            byte[] response = s.getBytes(StandardCharsets.UTF_8);
//            ctx.sendResponseHeaders(200, response.length);
//            try (OutputStream os = ctx.getResponseBody()) {
//                os.write(response);
//            }
//        }
//    }
    public static void setContentType(String s,HttpExchange ctx) {
      //  HttpExchange ctx=   currHttpExchange.get();
        if (ctx != null) {
            ctx.getResponseHeaders().set("Content-Type", s);
        }
    }
    public static void setContentType(@NotBlank String s) {

        if(ProcessContext.appJvl!=null) {
            Context ctx=currJvlnContext.get();
            if (ctx != null) {
                ctx.header("Content-Type", s);
            }
        }

        if(ProcessContext.httpServer!=null) {
            HttpExchange ctx=   currHttpExchange.get();
            if (ctx != null) {
                ctx.getResponseHeaders().set("Content-Type", s);
            }
        }


    }


    /**
     *
     // 获取 `user`，如果不存在则返回 "unknown"
     String user = QueryParamParser.getQueryParam(paramMap, "user").orElse("unknown");
     System.out.println("用户：" + user); // 输出: 用户：admin
     可以用 orElseThrow() 在关键参数缺失时抛异常
     * @param paramMap
     * @param key
     * @return
     */
//    public static Optional<String> getKeyFrmMp(Map<String, List<String>> paramMap, String key) {
//        /**
//         * 如果 paramMap.get(key) 是 null，则返回 Optional.empty()，避免 NullPointerException。
//         * 如果 list 为空，则返回 Optional.empty()。
//         */
//        return Optional.ofNullable(paramMap.get(key))
//                .flatMap(list -> list.stream().findFirst());
//    }
}
