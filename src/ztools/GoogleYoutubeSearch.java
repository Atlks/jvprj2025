package ztools;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

public class GoogleYoutubeSearch {

    public static void main(String[] args) throws Exception {
        String keyword = "youtube 下一站天后";
        String encodedKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8.toString());
        String searchUrl = "https://www.google.com/search?q=" + encodedKeyword;

        // 发起请求，伪装为浏览器
        Document doc = Jsoup.connect(searchUrl)
                //.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
               // .get();
        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:124.0) Gecko/20100101 Firefox/124.0")
                .get();
        System.out.println(doc.outerHtml());

        // 提取所有包含youtube的链接
        Elements links = doc.select("a[href]");
        Set<String> youtubeLinks = new HashSet<>();

        for (Element link : links) {
            String href = link.attr("href");
            if (href.contains("youtube.com/watch")) {
                // Google搜索的链接格式是 "/url?q=https://youtube.com/xxx&sa=..."
                int start = href.indexOf("/url?q=");
                if (start != -1) {
                    int end = href.indexOf("&", start);
                    String fullUrl = href.substring(start + 7, end != -1 ? end : href.length());
                    youtubeLinks.add(fullUrl);
                }
            }
        }

        // 打印链接
        youtubeLinks.forEach(System.out::println);
    }
}
