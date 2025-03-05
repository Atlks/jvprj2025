package util.web3;

import java.net.http.*;
import java.net.URI;
import java.util.*;


import org.json.*;

import static util.oo.StrUtil.toStr;
import static util.misc.Util2025.encodeJson;


public class StellarHelper {
    private static final String HORIZON_URL = "https://horizon.stellar.org/";

    public static List<Map<String, Object>> getXlmTransactionList(String xlmAddress) {
        List<Map<String, Object>> transactions = new ArrayList<>();
        try {
            String url = HORIZON_URL + "accounts/" + xlmAddress + "/transactions?limit=10&order=desc";
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            String body = response.body();
          //  System.out.println(body);
            JSONObject jsonResponse = new JSONObject(body);
            JSONArray records = getJSONArrayX("_embedded.records",jsonResponse);

            for (int i = 0; i < records.length(); i++) {
                JSONObject tx = records.getJSONObject(i);
                Map<String, Object> txMap = new HashMap<>();
                txMap.put("id", tx.getString("id"));
                //memo
                txMap.put("memo",  getStrFromJsonobj(tx,"memo"));
                txMap.put("hash", tx.getString("hash"));
                txMap.put("source_account", tx.getString("source_account"));
                txMap.put("created_at", tx.getString("created_at"));
                txMap.put("operation_count", tx.getInt("operation_count"));
                transactions.add(txMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactions;
    }

    private static Object getStrFromJsonobj(JSONObject tx, String memo) {
        if(tx.has(memo))
            return  toStr(tx.get(memo));
        return  "";
    }

    /**
     *  根据路径得到json数组
     * @param path  "_embedded.records"
     * @param jsonResponse
     * @return
     */
    private static JSONArray getJSONArrayX(String path, JSONObject jsonResponse) {
        if (jsonResponse == null || path == null || path.isEmpty()) {
            return new JSONArray(); // 返回空数组
        }

        String[] keys = path.split("\\."); // 按 "." 分割路径
        JSONObject currentObj = jsonResponse;

        for (int i = 0; i < keys.length; i++) {
            String key = keys[i];

            if (i == keys.length - 1) { // 最后一个 key
                JSONArray result = currentObj.optJSONArray(key);
                return result != null ? result : new JSONArray(); // 找不到就返回空数组
            } else {
                currentObj = currentObj.optJSONObject(key);
                if (currentObj == null) {
                    return new JSONArray(); // 如果中间层不存在，返回空数组
                }
            }
        }

        return new JSONArray();
    }

    // wlt1  to wlt28 xlm
    public static void main(String[] args) {
        String xlmAddress = "GCMO2UWRXH4QPYXVF56IP4ZOIYPL77UI6M6XBO3G4KKDM2VHUPLCLIC6"; // 这里填入 Stellar 地址
        List<Map<String, Object>> transactions = getXlmTransactionList(xlmAddress);
        System.out.println(encodeJson(transactions) );
    }
}
