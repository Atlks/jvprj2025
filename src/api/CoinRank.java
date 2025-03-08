package api;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import api.bet.QryDto4bets;
import entityx.Coin;
import entityx.Non;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RestController;
import util.algo.Icall;

//加密货币排行榜   CryptoRank
// 虚拟货币实体类  coinRank
//class Coin {
//    public String name; // 名称，如 btc、eth
//    public double price; // 价格
//
//    public Coin(String name, double price) {
//        this.name = name;
//        this.price = price;
//    }
//}
@RestController

@PermitAll
@Path("/coinRank")
public class CoinRank  implements Icall<Non, Object> {
    private static final String API_URL = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=20&page=1";

    /**
     * getTop5CryptoPrices
     * @return
     * @throws IOException
     */
    public List<Coin> call(Non dto) throws IOException {
        List<Coin> coins = new ArrayList<>();
        String string = getStrFrmUrl(API_URL);
        System.out.println( string);

        JSONArray jsonArray = new JSONArray(string);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            String name = obj.getString("id");
            double price = obj.getDouble("current_price");
            coins.add(new Coin(name, price));
        }
        return coins;
    }



//    public static void main(String[] args) {
//        try {
//            List<Coin> topCoins = getTop5CryptoPrices();
//            for (Coin coin : topCoins) {
//                System.out.println(coin.name + ": $" + coin.price);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }



    @NotNull
    private static String getStrFrmUrl(String apiUrl) throws IOException {
        URL url = new URL(API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() != 200) {
            throw new IOException("Failed to fetch data: HTTP error code " + conn.getResponseCode());
        }

        Scanner scanner = new Scanner(conn.getInputStream());
        StringBuilder jsonStr = new StringBuilder();
        while (scanner.hasNext()) {
            jsonStr.append(scanner.nextLine());
        }
        scanner.close();
        conn.disconnect();

        String string = jsonStr.toString();
        return string;
    }
}
