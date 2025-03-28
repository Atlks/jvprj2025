import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class ForexRateFetcher {
    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/USD"; // 可换成其他 API

    public static void main(String[] args) {
        System.out.println("USD to EUR: " + getExchangeRate("EUR"));
        System.out.println("USD to CNY: " + getExchangeRate("CNY"));
        System.out.println("USD to thb: " + getExchangeRate("THB"));
    }

    public static double getExchangeRate(String currencyCode) {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("HTTP error code: " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();
            conn.disconnect();

            JSONObject json = new JSONObject(response.toString());
            return json.getJSONObject("rates").getDouble(currencyCode);

        } catch (Exception e) {
            e.printStackTrace();
            return -1; // 获取失败时返回 -1
        }
    }
}

