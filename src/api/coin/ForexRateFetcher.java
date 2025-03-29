package api.coin;

import entityx.Non;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RestController;
import util.algo.Icall;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * 外汇
 */
@RestController

@PermitAll
@Path("/coin/frnXchg")

public class ForexRateFetcher implements Icall<Non, Object> {
    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/USD"; // 可换成其他 API

    public static void main(String[] args) throws Exception {
        System.out.println(getExchangeRate());
    }
    public   Object main (Non dto) throws Exception {
        return  getExchangeRate();
//        System.out.println("USD to EUR: " + getExchangeRate("EUR"));
//        System.out.println("USD to CNY: " + getExchangeRate("CNY"));
//        System.out.println("USD to thb: " + getExchangeRate("THB"));
    }

    public static Object getExchangeRate() throws  Exception {

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
            return json;
           //         json.getJSONObject("rates").getDouble(currencyCode);

    }
}

