import org.json.JSONObject;

public class GoldSilverPrice {

    public static double getGoldPrice() {
        try {
            String response = UnAuthorizedAPI.sendGetRequest("https://www.gold.org/goldprice/goldpriceperspectivelatest");
            JSONObject json = new JSONObject(response);
            return json.getDouble("spot_price");
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static double getSilverPrice() {
        try {
            String response = UnAuthorizedAPI.sendGetRequest("https://www.silver.org/prices/silver-spot-price");
            JSONObject json = new JSONObject(response);
            return json.getDouble("spot_price");
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void main(String[] args) {
        double goldPrice = getGoldPrice();
        double silverPrice = getSilverPrice();
        System.out.println("黄金价格: " + goldPrice + " 美元/盎司");
        System.out.println("白银价格: " + silverPrice + " 美元/盎司");
    }
}

class UnAuthorizedAPI {

    public static String sendGetRequest(String url) throws Exception {
        java.net.URL obj = new java.net.URL(url);
        java.net.HttpURLConnection con = (java.net.HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = con.getResponseCode();
        if (responseCode == 200) {
            return con.getResponseMessage();
        } else {
            throw new Exception("请求失败，状态码：" + responseCode);
        }
    }
}