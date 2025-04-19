package ztoolsMy

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.net.HttpURLConnection
import java.net.URL


fun main() {

    var dw = "https://www.dw.com/zh/%E5%9C%A8%E7%BA%BF%E6%8A%A5%E5%AF%BC/s-9058"
    var Url_fri = "https://www.rfi.fr/cn/"
    var url_abc="https://www.abc.net.au/news/chinese"
    showNews(url_abc)
   showNews(dw)
    showNews(Url_fri)

}

private fun showNews(Url_fri: String) {
   println(Url_fri)
    var html = getHtml(Url_fri);
    var news: List<String> = getListString(html)
    for (newItem in news) {
        if (isDontCotainChnsChars(newItem))
            continue
        if (newItem.length < 10 || newItem.length>50)
            continue
        println(newItem)
    }
}


/**
 * 解析html，获取所有a标签，读取文本，
 */
fun getListString(html: String): List<String> {
    val doc: Document = Jsoup.parse(html)
    return doc.select("a")  // 获取所有 a 标签
        .map { it.text().trim() }  // 读取文本并去除前后空白
        .filter { it.isNotEmpty() }  // 过滤空字符串
}

/**
 * 如果不包含汉字，则true
 */
fun isDontCotainChnsChars(newItem: String): Boolean {
    return !newItem.contains(Regex("[\\u4e00-\\u9fa5]"))
}

fun getHtml(urlFri: String): String {
    val url = URL(urlFri)
    val connection = url.openConnection() as HttpURLConnection
    connection.requestMethod = "GET"
    connection.connectTimeout = 5000
    connection.readTimeout = 5000

    return connection.inputStream.bufferedReader().use { it.readText() }
}
