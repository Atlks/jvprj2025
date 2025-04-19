package ztoolsMy

import entityx.Coin
import org.json.JSONArray
import util.algo.GetUti
import util.misc.Util2025.encodeJson

fun main( )  {
    val API_URL =
        "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=10&page=1"
    val coins: MutableList<Coin> = ArrayList()
    val string = GetUti.getStrFrmUrl(API_URL)
  //  println(string)

    val jsonArray = JSONArray(string)
    for (i in 0..<jsonArray.length()) {
        val obj = jsonArray.getJSONObject(i)
        val name = obj.getString("id")
        val price = obj.getDouble("current_price")
        coins.add(Coin(name, price))
    }
      println( encodeJson(coins) )
 //   return ApiResponse(coins)
} //    public static void main(String[] args) {
//        try {
//            List<Coin> topCoins = getTop5CryptoPrices();
//            for (Coin coin : topCoins) {
//                System.out.println(coin.name + ": $" + coin.price);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }