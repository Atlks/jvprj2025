//package ztest;
//
//import com.microsoft.playwright.*;
//
//import java.io.FileWriter;
//import java.io.IOException;
//import java.nio.file.Paths;
//
///**
// * 效果演示
// * 启动 Chromium 浏览器
// *
// * 打开 Ethereum 的 CoinMarketCap 页面
// *
// * 自动点击“蜡烛图”图表 Tab
// */
//public class getCandlePic {
//
//    public static void main(String[] args) {
//        try (Playwright playwright = Playwright.create()) {
//            Browser browser = playwright.chromium().launch(
//                    new BrowserType.LaunchOptions().setHeadless(false)
//            );
//            Page page = browser.newPage();
//
//            // 打开页面
//            page.navigate("https://coinmarketcap.com/currencies/ethereum/");
//
//            // 等待元素加载完成
//            page.waitForSelector("li[data-index='tab-Candle']");
//
//            // 点击目标 tab（蜡烛图）
//            page.click("li[data-index='tab-Candle']");
//
//
//            // 等待 SVG 图表加载（最多 15 秒）
//            page.waitForSelector("svg.highcharts-root", new Page.WaitForSelectorOptions().setTimeout(15000));
//
//            // 等 SVG 的子元素真正渲染完成 ,是这个原因
//            page.waitForSelector("svg.highcharts-root g");
//            //page.waitForSelector("svg.highcharts-root g", new Page.WaitForSelectorOptions().setTimeout(10000));
//
//
//
//            // 提取 SVG 的 outerHTML
//           // String svgHtml = page.locator("svg.highcharts-root").first().evaluate("node => node.outerHTML").toString();
//
//         //    提取 SVG 的 outerHTML 正确方式
//            JSHandle handle = page.locator("svg.highcharts-root").first().evaluateHandle("node => node.outerHTML");
//            String svgHtml = handle.jsonValue().toString();
//
//            // 将所有 SVG 元素的样式内联化
////            page.evaluate("() => {" +
////                    "const svg = document.querySelector('svg.highcharts-root');" +
////                    "const styles = window.getComputedStyle(svg);" +
////                    "svg.setAttribute('style', styles.cssText);" +
////                    "Array.from(svg.querySelectorAll('*')).forEach(el => {" +
////                    "  const cs = window.getComputedStyle(el);" +
////                    "  el.setAttribute('style', cs.cssText);" +
////                    "});" +
////                    "}");
////
////            // 获取内联后的 SVG
////            String svgHtml = page.locator("svg.highcharts-root").first().evaluate("node => node.outerHTML").toString();
//
//
//            // 包一层 <html><body>，写入到文件
//            String fullHtml = "<html><body>" + svgHtml + "</body></html>";
//
//            try (FileWriter writer = new FileWriter("eth.htm")) {
//                writer.write(fullHtml);
//                System.out.println("SVG 图已保存为 eth.htm");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//
//            //截图
//            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("eth.png")));
//
//            // 停留几秒钟看效果
//            page.waitForTimeout(50000);
//        }
//    }
//}
//
