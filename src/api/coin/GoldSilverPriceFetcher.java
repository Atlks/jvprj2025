package api.coin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
// kotlinx.serialization.descriptors.StructureKind;
import org.json.JSONObject;


import static util.misc.Util2025.encodeJson;
import static util.misc.Util2025.encodeJsonByGson;

/**
 * 贵金属
 */


@PermitAll
@Path("/coin/goldSilver")
public class GoldSilverPriceFetcher {

    private static final String API_URL = "https://api.gold-api.com/price/";


    /**
     * 金属货币 金银铜铝爬
     * @param metal
     * @return
     */
    public static JSONObject getGoldSilverPrice(String metal) {
        try {
            String url2 = API_URL + "" + metal;
            System.out.println(url2);
            URL url = new URL(url2);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            conn.disconnect();

            String responseString = response.toString();
            System.out.println(responseString);
            JSONObject jsonObject = new JSONObject(responseString);
            return jsonObject;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {

        List<Object> li=new ArrayList<>();
        JSONObject prices_gld = getGoldSilverPrice("XAG");
        li.add(prices_gld);
        li.add(  getGoldSilverPrice("XAU"));
        li.add(   getGoldSilverPrice("HG"));
        System.out.println(encodeJsonByGson(li));

    }
}
