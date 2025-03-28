import java.io.IOException;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MetalPriceFetcher {

    /**
     * 获取指定金属的价格。
     *
     * @param metalType 金属类型，目前支持 "黄金" 或 "白银"。
     * @return BigDecimal 类型的价格，如果获取失败则返回 null。
     */
    public static BigDecimal getMetalPrice(String metalType) {
        if (!metalType.equals("黄金") && !metalType.equals("白银")) {
            System.err.println("不支持的金属类型：" + metalType + "。目前只支持黄金和白银。");
            return null;
        }

        String url = "";
        String priceRegex = "";

        if (metalType.equals("黄金")) {
            // 这里需要替换成可靠的黄金价格数据来源 URL 和对应的价格正则表达式
            url = "https://cn.investing.com/commodities/gold";
            // 示例正则表达式，可能需要根据实际网页结构调整
            priceRegex = "<span id=\"last_last\" class=\".*?\">([\\d\\.]+)</span>";
        } else if (metalType.equals("白银")) {
            // 这里需要替换成可靠的白银价格数据来源 URL 和对应的价格正则表达式
            url = "https://cn.investing.com/commodities/silver";
            // 示例正则表达式，可能需要根据实际网页结构调整
            priceRegex = "<span id=\"last_last\" class=\".*?\">([\\d\\.]+)</span>";
        }

        try {
            String content = HttpUtil.fetchContent(url);
            if (content != null) {
                Pattern pattern = Pattern.compile(priceRegex);
                Matcher matcher = pattern.matcher(content);
                if (matcher.find()) {
                    String priceStr = matcher.group(1).replaceAll(",", ""); // 去除千位分隔符
                    return new BigDecimal(priceStr);
                } else {
                    System.err.println("未能从网页中找到 " + metalType + " 价格。请检查正则表达式。");
                    return null;
                }
            } else {
                System.err.println("未能获取 " + metalType + " 价格的网页内容。");
                return null;
            }
        } catch (IOException e) {
            System.err.println("获取 " + metalType + " 价格时发生 IO 异常：" + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        BigDecimal goldPrice = getMetalPrice("黄金");
        if (goldPrice != null) {
            System.out.println("当前黄金价格： " + goldPrice);
        }

        BigDecimal silverPrice = getMetalPrice("白银");
        if (silverPrice != null) {
            System.out.println("当前白银价格： " + silverPrice);
        }

        BigDecimal invalidPrice = getMetalPrice("铂金");
        if (invalidPrice != null) {
            System.out.println("铂金价格： " + invalidPrice);
        }
    }
}

// 辅助类，用于发送 HTTP 请求获取网页内容
class HttpUtil {
    public static String fetchContent(String url) throws IOException {
        java.net.URL u = new java.net.URL(url);
        java.net.HttpURLConnection connection = (java.net.HttpURLConnection) u.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        int responseCode = connection.getResponseCode();
        if (responseCode == java.net.HttpURLConnection.HTTP_OK) {
            try (java.io.InputStream inputStream = connection.getInputStream();
                 java.util.Scanner scanner = new java.util.Scanner(inputStream, java.nio.charset.StandardCharsets.UTF_8).useDelimiter("\\A")) {
                return scanner.hasNext() ? scanner.next() : "";
            }
        } else {
            System.err.println("HTTP 请求失败，响应代码: " + responseCode);
            return null;
        }
    }
}
