import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;

public class PreciousMetalsPriceFetcher {

    private static final String URL = "https://www.kitco.com/price/precious-metals";

    public static void main(String[] args) {
        fetchGoldAndSilverPrices();
    }

    public static void fetchGoldAndSilverPrices() {
        try {
            // 连接到目标URL并获取文档
            Document doc = Jsoup.connect(URL).get();

            // 获取黄金价格
            Element goldElement = doc.selectFirst("tr:contains(Gold) td:nth-of-type(2)");
            String goldPrice = (goldElement != null) ? goldElement.text() : "未找到黄金价格";

            // 获取白银价格
            Element silverElement = doc.selectFirst("tr:contains(Silver) td:nth-of-type(2)");
            String silverPrice = (silverElement != null) ? silverElement.text() : "未找到白银价格";

            // 输出结果
            System.out.println("黄金价格: " + goldPrice);
            System.out.println("白银价格: " + silverPrice);

        } catch (IOException e) {
            System.out.println("获取数据时发生错误: " + e.getMessage());
        }
    }
}
