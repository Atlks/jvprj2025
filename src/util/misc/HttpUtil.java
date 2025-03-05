package util.misc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.json.JSONObject;

public class HttpUtil {

    // 返回为gson的对像
    public static JsonObject getFromHttpgetAsGson(String url) throws Throwable {
        String jsonStr = getFromHttpgetAsStr(url);
        return JsonParser.parseString(jsonStr).getAsJsonObject();
    }

    // 发送http请求get模式，返回json转换为JSONObject
    // 发送 HTTP GET 请求，返回解析后的 JSONObject
    public static JSONObject getFromHttpget(@NotBlank String url) throws Throwable {


        return new JSONObject(getFromHttpgetAsStr(url));

    }

    private static @NotNull String getFromHttpgetAsStr(@NotBlank String url) throws Throwable {
        chkNotBlank(url);

        URL requestUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) requestUrl.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json"); // 设置请求头，告诉服务器返回 JSON

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            String responseTxt = getResponseTxt(conn.getErrorStream());
            throw new RuntimeException("HTTP GET 请求失败，状态码: " + responseCode + " respsTxt=" + responseTxt);
        }
        StringBuilder response = new StringBuilder();
        InputStream inputStream = conn.getInputStream();
        readStreamToResps(inputStream, response);
        conn.disconnect();

        // 将返回的字符串解析为 JSONObject
        String string = response.toString();
        return string;
    }

    private static void chkNotBlank(@NotBlank String url) throws StrIsBlankEx {

        if (isBlank(url))
            throw new StrIsBlankEx("var url=empty");

    }

    //
    private static boolean isBlank(@NotBlank String str) {
        return str == null || str.trim().isEmpty();

    }


    // 读取 HTTP 错误流并返回字符串
    private static @NotNull String getResponseTxt(@NotNull InputStream errorStream) {
        if (errorStream == null) {
            return "";
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(errorStream))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line).append("\n");
            }
            return response.toString().trim(); // 移除最后的换行符
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static void readStreamToResps(InputStream inputStream, StringBuilder response) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
    }
}
