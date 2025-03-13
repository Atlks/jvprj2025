package tools

import kotlinx.coroutines.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.io.IOException


/**
 * 如果你在协程代码里看到 withContext，它并不是一个注解，而是 kotlinx.coroutines 提供的一个挂起函数，用于切换协程的执行上下文（CoroutineContext）。
 */
suspend fun getChiangMaiWeatherFromWebsite(): String? = withContext(Dispatchers.IO) {
    val url = "https://www.google.com/search?q=chiang+mai+weather" // 使用 Google 搜索结果页面

    try {
        val document = Jsoup.connect(url).get()
        // 查找包含天气信息的元素，这部分代码需要根据目标网站的结构进行调整
    //  val weatherElement = document.select("div[id=wob_wc]").first()

        // 选择 class="R3Y3ec rr3bxd" 的 div
        val weatherElement: Element? = document.selectFirst("div.wob_dfc")
        if (weatherElement != null) {
            return@withContext weatherElement.text()
        } else {
            println("elmt is null..")
            return@withContext null
        }
    } catch (e: IOException) {
        e.printStackTrace()
        return@withContext null
    }
}

fun main() = runBlocking {
    val weather = getChiangMaiWeatherFromWebsite()
    if (weather != null) {
        println("Chiang Mai Weather: $weather")
    } else {
        println("Failed to get Chiang Mai weather.")
    }
}