package ztools;

import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GoogleCustomSearch {

    public static void main(String[] args) throws Exception {
        String apiKey = "";
        String cx = "a670d72e2b66944dd"; //自定义搜索引擎的 ID。
        String keyword = "下一站天后 youtube";
        String encodedKeyword = java.net.URLEncoder.encode(keyword, "UTF-8");

        String apiUrl = "https://www.googleapis.com/customsearch/v1?q=" + encodedKeyword + "&key=" + apiKey + "&cx=" + cx;

        // 发起 HTTP 请求
        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }

        // 解析 JSON 响应
        JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
        JsonArray items = jsonResponse.getAsJsonArray("items");

        for (int i = 0; i < items.size(); i++) {
            JsonObject item = items.get(i).getAsJsonObject();
            String title = item.get("title").getAsString();
            String link = item.get("link").getAsString();
            System.out.println("Title: " + title);
            System.out.println("Link: " + link);
            System.out.println();
        }
    }
}
