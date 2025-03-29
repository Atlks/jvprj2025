package api.coin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entityx.ApiResponse;
import entityx.Coin;
import entityx.Non;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Path;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RestController;
import util.algo.Icall;

import static util.algo.GetUti.getStrFrmUrl;
/**
 * 加密货币排行榜   CryptoRank
 */
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
    public Object main(Non dto) throws IOException {

        String API_URL = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=20&page=1";
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
        return  new ApiResponse(coins) ;
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




}
