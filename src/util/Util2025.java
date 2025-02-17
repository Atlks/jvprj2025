package util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.sun.net.httpserver.HttpExchange;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;


public class Util2025 {

    public static void main(String[] args) {
        System.out.println(replacex("aabbcc", "bb", null));
        System.out.println(toTimestmp("2024-12-01T05:29:29.567Z"));
    }

    /**
     *  * 全面增加了空安全 检测 与自动转换机制
     * @param resultInXml
     * @param y
     * @return
     */
    public static boolean isEqualsx(String resultInXml, String y) {
        if( resultInXml==null || y==null)
            return  false;
        return y.equals(resultInXml);
    }
    /**
     * 全面增加了空安全 检测 与自动转换机制
     * @param url
     * @param ori
     * @param rplsStr
     * @return
     */
    public static String replacex(String url, String ori, String rplsStr) {

        if (url == null)
            return "";
        if (rplsStr == null || ori == null)
            return url;

        return url.replace(ori, rplsStr);
    }

    public static boolean enable_consoleEcho = true;

    public static String encodeJson4grep(Object obj) {
        if (obj == null)
            return "{}";
        return JSON.toJSONString(obj);
    }
    public static String encodeJson4dbgShowVal(boolean obj) {

        if(obj==true)
            return  "true";
        else
            return "false";
         //   return obj ;  // 直接返回 "true" 或 "false"

    }
    public static String encodeJson4dbgShowVal(Object obj) {
        if (obj instanceof Boolean) {
            return obj.toString();  // 直接返回 "true" 或 "false"
        }

        return    com.alibaba.fastjson2.JSON.toJSONString(obj, JSONWriter.Feature.PrettyFormat);
    }

    public static String encodeJsonV2(Object obj) {
        try{
            if (obj == null)
                return "{}";
            //   return com.alibaba.fastjson2.JSON.toJSONString(obj);
            // 如果是数组或列表
            if (obj instanceof Object[]) {
                List<Object> list = new ArrayList<>();
                for (Object obj1 : (Object[]) obj) {
                    if (obj1 instanceof HttpExchange) {
                        list.add(toExchgDt((HttpExchange) obj1));
                    }else
                        list.add( obj1);
                }
                return JSON.toJSONString(list, JSONWriter.Feature.PrettyFormat);
            } else if (obj instanceof Collection<?>) {
                List<Object> list = new ArrayList<>();
                for (Object obj1 : (Collection<?>) obj) {
                    if (obj1 instanceof HttpExchange) {
                        list.add(toExchgDt((HttpExchange) obj1));
                    }
                }
                return JSON.toJSONString(list, JSONWriter.Feature.PrettyFormat);
            }

            if(obj instanceof HttpExchange){
                HttpExchange HttpExchange1= (HttpExchange) obj;
                Map<String, Object> exchangeData=toExchgDt(HttpExchange1);
                return    com.alibaba.fastjson2.JSON.toJSONString(exchangeData, JSONWriter.Feature.PrettyFormat);
            }
            return    com.alibaba.fastjson2.JSON.toJSONString(obj, JSONWriter.Feature.PrettyFormat);

        } catch (Exception e) {
            e.printStackTrace();
            return "{}";
        }

    }

    private static Map<String, Object> toExchgDt(HttpExchange exchange) throws IOException {
        Map<String, Object> exchangeData = new HashMap<>();

        // 获取请求方法和 URI
        exchangeData.put("method", exchange.getRequestMethod());
        exchangeData.put("uri", exchange.getRequestURI().toString());

        // 获取请求头
        Map<String, String> headers = new HashMap<>();
        exchange.getRequestHeaders().forEach((key, values) -> headers.put(key, String.join(", ", values)));
        exchangeData.put("headers", headers);

        // 读取请求体
        exchangeData.put("body", readRequestBody(exchange));
    return exchangeData;
    }

    private static String readRequestBody(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        if (is == null) {
            return "";
        }

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int bytesRead;
        while ((bytesRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, bytesRead);
        }
        return buffer.toString("UTF-8");
    }


    @Deprecated
    public static String encodeJson(Object obj) {
      try{
          if (obj == null)
              return "{}";
      //   return com.alibaba.fastjson2.JSON.toJSONString(obj);
          return    com.alibaba.fastjson2.JSON.toJSONString(obj, JSONWriter.Feature.PrettyFormat);

      } catch (Exception e) {
          return "{}";
      }

    }

    public static String encodeJsonObj(Object obj) {
        try{
            if (obj == null)
                return "{}";
            return JSON.toJSONString(obj);
        } catch (Exception e) {
            return "{}";
        }

    }

    public static String encodeJsonArray(Object obj) {
        try{
            if (obj == null)
                return "[]";
            return JSON.toJSONString(obj);
        } catch (Exception e) {
            return "[]";
        }

    }

//    public static String sendGetWzAuthV2(String url String, String username, String password) {
//        System.out.println("fun sendGetWzAuth(");
//        System.out.println(urlString);
//        System.out.println("username=" + username);
//        System.out.println("password=" + password);
//        System.out.println(")");
//        try {
//            if(urlString.startsWith("https://")) {
//                setNotChkSSL();
//            }
//
//            URL url = new URL(urlString);
//            HttpURLConnection    connection = (HttpURLConnection) url.openConnection();
//         //   connection.setRequestProperty("User-Agent", "YourCustomUserAgent");
//            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36");
//
//            connection.setRequestMethod("GET");
//
//
//            setAuthHttpHead(username, password, connection);
//
//
//            int responseCode = 0;
//
//            responseCode = connection.getResponseCode();
//
//            if (responseCode == HttpsURLConnection.HTTP_OK) { // 200
//                return getStringFrmConnHttp(connection);
//
//            } else {
//                return "GET request failed: " + responseCode;
//            }
//
//        } catch (IOException | KeyManagementException | NoSuchAlgorithmException e) {
//            //throw new RuntimeException(e);
//            e.printStackTrace();
//            return  "catch ex,GET request failed: " + e.getMessage();
//        }
//    }


    /**
     * 将特定格式的日期时间字符串转换为Date对象，并返回格式化后的字符串。
     *
     * @param inputDateTime 输入的日期时间字符串
     * @return 格式化后的日期时间字符串（格式：YYYY-MM-DDTHH:mm:ss.SSSZ）
     */
    public static String getFormattedDateTimeUtcZerofmt(String inputDateTime) {
        // 输入日期时间的格式
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 输出日期时间的格式
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        outputFormat.setTimeZone(TimeZone.getTimeZone("UTC")); // 设置时区为UTC

        try {
            // 将输入的字符串转换为Date对象
            Date date = inputFormat.parse(inputDateTime);
            // 将Date对象格式化为指定的输出格式
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "Error parsing date";
        }
    }

    /**
     * 格式化时间使用utc+8时区
     *
     * @param inputDateTime
     * @return
     */
    public static String getTimeUtc8(Date inputDateTime) {
        // 输入日期时间的格式
        //  SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 输出日期时间的格式
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        // 设置时区为UTC+8
        outputFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));

        // 将输入的字符串转换为Date对象

        // 将Date对象格式化为指定的输出格式
        return outputFormat.format(inputDateTime);
    }

    public static String getFormattedDateTimeUtcZerofmt(Date inputDateTime) {
        // 输入日期时间的格式
        //  SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 输出日期时间的格式
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        outputFormat.setTimeZone(TimeZone.getTimeZone("UTC")); // 设置时区为UTC

        // 将输入的字符串转换为Date对象

        // 将Date对象格式化为指定的输出格式
        return outputFormat.format(inputDateTime);
    }

    public static String getStringFrmConnHttp(HttpURLConnection connection) throws IOException {
        BufferedReader in = null;

        in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        StringBuilder response = new StringBuilder();
        String inputLine;
        while (true) {
            if (!((inputLine = in.readLine()) != null)) break;

            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    private static void setAuthHttpHead(String username, String password, HttpURLConnection connection) {
        // 创建 Basic Auth Header
        if (username != null && password != null) {
            String auth = username + ":" + password;
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
            String authHeaderValue = "Basic " + encodedAuth;

            connection.setRequestProperty("Authorization", authHeaderValue);

        }
        //end auth
    }
    public static String taglog="";
    public static String sendGetWzAuth(String urlString, String username, String password) {

        printlnx(taglog + "fun sendGetWzAuth(");
        printlnx(taglog + urlString);
        printlnx(taglog + "username=" + username);
        printlnx(taglog + "password=" + password);
        printlnx(taglog + ")");
        try {
            if (urlString.startsWith("https://")) {
                setNotChkSSL();
            }

            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36");

            connection.setRequestMethod("GET");


            setAuthHttpHead(username, password, connection);


            int responseCode = 0;

            responseCode = connection.getResponseCode();

            int HTTP429_TOO_MANY_REQUESTS = 429;
            if (responseCode == HttpsURLConnection.HTTP_OK) { // 200
                String resptxt = getStrFrmConnHttp(connection);
                printlnx(taglog + "endfun sendGetWzAuth()#ret=" + resptxt);
                return resptxt;

            } else if (responseCode == HTTP429_TOO_MANY_REQUESTS) {
                process429(connection);
                return "GET request failed: " + responseCode;
            } else {
                return "GET request failed: " + responseCode;
            }

        } catch (IOException e) {
            //throw new RuntimeException(e);
            e.printStackTrace();
            return "ctex,GET request failed: " + e.getMessage();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }

    //    /**
//     * 获 取时间戳，秒级别。。。
//     *
//     * @param startedAt 这是utc0时区的时间串，格式  2024-12-01T05:29:29.567Z
//     * @return
//     */
//    private static Long toTimestmp(String startedAt) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//
//        try {
//            Date date = dateFormat.parse(startedAt);
//            return date.getTime() / 1000; // 转换为秒级时间戳
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return null; // 或者根据需要返回一个默认值或抛出异常
//        }
//    }
    private static int process429(HttpURLConnection connection) {

        // 读取Retry-After头
        String retryAfterHeaderValue = connection.getHeaderField("Retry-After");
        if (retryAfterHeaderValue != null) {
            printlnx("http resp.Retry-After=" + retryAfterHeaderValue);
            try {
                // 尝试将Retry-After值转换为整数秒
                return Integer.parseInt(retryAfterHeaderValue);
            } catch (NumberFormatException e) {
                // 如果Retry-After不是一个整数，它可能是一个HTTP日期
                // 这里你可以选择如何处理日期，例如使用java.util.Date解析它
                // 为了简单起见，这里我们只打印出来
                System.out.println("Retry-After header value is a date: " + retryAfterHeaderValue);
            }
        }
        return 0;
    }


    public static Date getAfterHours(int hours) {

        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();

        // 将当前时间减去2小时

        LocalDateTime twoHoursAgo = now.plusHours(hours);

        // 转换为系统默认时区的 ZonedDateTime
        ZonedDateTime zonedDateTime = twoHoursAgo.atZone(ZoneId.systemDefault());

        // 转换为 java.util.Date
        return Date.from(zonedDateTime.toInstant());
    }


    /**
     * 获取当前时间往前推两个小时的时间。
     *
     * @return Date对象，表示两个小时前的时间。
     */
    public static Date getAgoHours(int hours) {
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();

        // 将当前时间减去2小时

        LocalDateTime twoHoursAgo = now.minusHours(hours);

        // 转换为系统默认时区的 ZonedDateTime
        ZonedDateTime zonedDateTime = twoHoursAgo.atZone(ZoneId.systemDefault());

        // 转换为 java.util.Date
        return Date.from(zonedDateTime.toInstant());
    }

    public static Long toLong2024(String crt) {
        return Long.parseLong(crt);
    }


    /**
     * 获取时间戳，ms级别。。。
     *
     * @param startedAt 这是utc0时区的时间串，格式  2024-12-01T05:29:29.567Z
     * @return
     */
    public static Long toTimestmpFrmUtczMs(String startedAt) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            Date date = dateFormat.parse(startedAt);
            return date.getTime(); // 转换为秒级时间戳
        } catch (ParseException e) {
            e.printStackTrace();
            return 0L; // 或者根据需要返回一个默认值或抛出异常
        }
    }

    /**
     * 获取时间戳，秒级别。。。
     *
     * @param startedAt 这是utc0时区的时间串，格式  2024-12-01T05:29:29.567Z
     * @return
     */
    public static Long toTimestmp(String startedAt) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            Date date = dateFormat.parse(startedAt);
            return date.getTime() / 1000; // 转换为秒级时间戳
        } catch (ParseException e) {
            e.printStackTrace();
            return 0L; // 或者根据需要返回一个默认值或抛出异常
        }
    }


    public static String writeFile2501(String filePath, String value) {
        System.out.println("filePath="+filePath);
        File file = mkdir(filePath);

        // 检查文件是否存在，若不存在则创建文件
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            // 使用 BufferedWriter 写入文件
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
                writer.write(value);
                writer.newLine(); // 添加换行符
            }

            // System.out.println("Data written to file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ok!";

    }

    /**
     * dep bcs append
     * @param filePath
     * @param value
     * @return
     */
    @Deprecated
    public static String writeFile2024(String filePath, String value) {
        System.out.println("filePath="+filePath);
        File file = mkdir(filePath);

        // 检查文件是否存在，若不存在则创建文件
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            // 使用 BufferedWriter 写入文件
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                writer.write(value);
                writer.newLine(); // 添加换行符
            }

            // System.out.println("Data written to file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ok!";

    }


    public static File mkdir2025(String filePath) {
        // 创建文件对象
        File file = new File(filePath);

        // 检查文件的父目录是否存在

        if (file != null && !file.exists()) {
            // 创建目录
            file.mkdirs();
        }
        return file;
    }

    public static File mkdir(String filePath) {
        // 创建文件对象
        File file = new File(filePath);

        // 检查文件的父目录是否存在
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            // 创建目录
            parentDir.mkdirs();
        }
        return file;
    }

    public static void sleepx(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public static String readTxtFrmFil(String cfgfile) {
        StringBuilder content = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(cfgfile))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content.toString();

    }

    public static String getAuthHttpHead(String username, String password) {
        // 创建 Basic Auth Header
        if (username != null && password != null) {
            String auth = username + ":" + password;
            System.out.println("auth http head bef base64=" + auth);
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
            String authHeaderValue = "Basic " + encodedAuth;
            return authHeaderValue;
            //  connection.setRequestProperty("Authorization", authHeaderValue);

        }
        //end auth
        return "";
    }



    public static boolean isStr2024(Object s1) {

        return s1 instanceof String;
    }


    public static JSONObject decodeJson(String config) {
        // 检查输入是否为空，避免解析空字符串
        if (config == null || config.isEmpty()) {
            return new JSONObject(); // 返回空的 JSONObject
        }

        // 使用 fastjson 解析 JSON 字符串
        return JSON.parseObject(config);
    }

    public static Integer ret2025(String taglog, int retVal, String fname) {
        printlnx(taglog + " endfun " + fname + "().ret  " + retVal);
        //   printlnx(taglog+" endfun " + fname + "()");
        return retVal;
    }

    public static Integer ret2025(int retVal, String fname) {
        printlnx(fname + "().ret  " + retVal);
        printlnx("endfun " + fname + "()");
        return retVal;
    }

    public static boolean enable_loginfoFun = true;

    public static void printlnx(Object s1) {

        if (s1 == null)
            s1 = "$null";


        // if str
        String s = s1.toString();

        //if not str
        if (!isStr2024(s1))
            s = encodeJsonPretty2(s1);

        if (enable_consoleEcho)
            System.out.println(s);
        if (enable_loginfoFun)
            loginfo(s);
    }

    private static void loginfo(String s) {
        writeFile2024("xx2025.log",s);

    }

    public static void setNotChkSSL() throws NoSuchAlgorithmException, KeyManagementException {
        // 创建不验证证书的信任管理器
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                }
        };

        // 设置 SSL 上下文

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }


    public static String sendPost4game(String urlString, String jsonInputString) {


        System.setProperty("https.protocols", "TLSv1.2,TLSv1.3");
        printlnx("fun sendPost4game(((");
        printlnx(urlString);
        printlnx(jsonInputString);
        printlnx(")))");
        StringBuilder response = new StringBuilder();

        try {
            // 创建一个不进行证书验证的 TrustManager
             setNotChkSSL();

            // 打开 URL 连接
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent", "firefox");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            // 发送请求数据
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // 读取响应
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                readRespOutputFrmInputstream(connection.getInputStream(), response);
            } else {
                response.append("Request failed with response code: ").append(responseCode);
                response.append("\n");
                readRespOutputFrmInputstream(connection.getErrorStream(), response);
            }

        } catch (Exception e) {
            response.append("Exception occurred: ").append(e.getMessage());
            // printEx(e);
        }

        String rzt = response.toString();
        printlnx("endfun sendPost4game#ret:" + rzt);
        return rzt;
    }


    public static void readRespOutput(HttpURLConnection connection, StringBuilder response) throws IOException {

        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line.trim());
            }
        }
    }

    public static String getUUid4game() {
        return UUID.randomUUID().toString();
    }

// 处理数组的 JSON 字符串，将每个元素换行显示

    public static String encodeJsonPretty2(Object obj) {
        // 使用 Fastjson 将对象转换为格式化的 JSON 字符串
        // return JSON.toJSONString(obj, SerializerFeature.PrettyFormat);

        // 检查对象是否为数组
        if (obj.getClass().isArray()) {
            // 将数组转换为 JSON 字符串
            String jsonArrayString = JSON.toJSONString(obj);
            // jsonArrayString=jsonArrayString.replaceAll("\\,","\n,\n");
            jsonArrayString = jsonArrayString.replace(",", ",\n");

            // 将 JSON 数组字符串中的每个元素换行显示

            String r = jsonArrayString.replaceAll("(?<=\\})\\s*(?=\\[)", "\n").replaceAll("\\{", "\n{").replaceAll("\\}", "}\n");
            return r;

        } else {
            // 处理单个对象
            return JSON.toJSONString(obj );
        }

    }


    public static void readRespOutputFrmInputstream(InputStream errorStream, StringBuilder response) throws IOException {

        try (BufferedReader in = new BufferedReader(new InputStreamReader(errorStream, "utf-8"))) {
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line.trim());
            }
        }
    }

    //getStrFrmConnHttp
    private static String getStrFrmConnHttp(HttpURLConnection connection) throws IOException {
        BufferedReader in = null;

        in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        StringBuilder response = new StringBuilder();
        String inputLine;
        while (true) {
            if (!((inputLine = in.readLine()) != null)) break;

            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }
}
