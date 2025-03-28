import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class GoldSilverPrice2 {

    public static double getGoldPrice() throws IOException {
        URL url = new URL("https://www.gold.org/goldprice/goldpriceperspectivelatest");
        Scanner scanner = new Scanner(url.openStream());
        String line;
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            if (line.contains("Spot Price")) {
                String[] words = line.split(" ");
                return Double.parseDouble(words[2]);
            }
        }
        return 0;
    }

    public static double getSilverPrice() throws IOException {
        URL url = new URL("https://www.silver.org/prices/silver-spot-price");
        Scanner scanner = new Scanner(url.openStream());
        String line;
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            if (line.contains("Spot Price")) {
                String[] words = line.split(" ");
                return Double.parseDouble(words[2]);
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        try {
            double goldPrice = getGoldPrice();
            double silverPrice = getSilverPrice();
            System.out.println("黄金价格: " + goldPrice + " 美元/盎司");
            System.out.println("白银价格: " + silverPrice + " 美元/盎司");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}