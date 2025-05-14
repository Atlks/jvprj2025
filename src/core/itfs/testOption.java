package core.itfs;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static util.misc.Util2025.encodeJson;

public class testOption {


    //测试发送option请求
    //url 是 http://localhost:8889/wlt/findBalanceOverviewHdl
    public static void main(String[] args) {

        String targetUrl = "http://localhost:8889/wlt/findBalanceOverviewHdl";

        try {
            // 创建 URL 对象
            URL url = new URL(targetUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // 设置请求方法为 OPTIONS
            conn.setRequestMethod("OPTIONS");

            // 设置请求头（可选）
            conn.setRequestProperty("Accept", "*/*");

            // 发起请求
            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);


            System.out.println(encodeJson(conn.getHeaderFields()));
            // 输出允许的方法（如果服务器响应中带有 Allow）
            String allowHeader = conn.getHeaderField("Allow");
            if (allowHeader != null) {
                System.out.println("Allow Header: " + allowHeader);
            } else {
                System.out.println("No Allow header in response.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
