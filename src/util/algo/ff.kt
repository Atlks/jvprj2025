package util.algo

//package lib

//import android.util.log.Log
import org.json.JSONObject
import util.log.Log
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.io.StringWriter
import java.net.HttpURLConnection
import java.net.URL


//bot  .me/ntfycoll2025bot.
//grp  ntfy mir coll
val TOKEN = "8121736741:AAGKUS35nyeTodd7JXIb8u-sNQPN2sh6olw"
val CHAT_ID = "-1002209160657" // 你的群组 ID
val TELEGRAM_API_URL = "https://api.telegram.org/bot$TOKEN/sendMessage"

fun main() {
    sendTgTxtMsg("ttt");
}

fun sendMsgTg(messageWzFmt: String) {
    Thread {

        try {
            sendTgTxtMsg(messageWzFmt)
        } catch (e: Exception) {
            try {
                sendTgTxtMsg(messageWzFmt)
            } catch (e: Exception) {

                Log.e("TelegramBot", "发生异常: ${e.message}")
                //  e.printStackTrace()
                val stackTraceString = getStackTraceString(e)
                println(stackTraceString)
                wrtLib_failMsg(messageWzFmt)
                //end catch
            }
            //end catch

        }
    }.start() // 在子线程中执行网络请求，避免阻塞主线程
}

fun wrtLib_failMsg(messageWzFmt: String) {

}

/**
 * 调用telegrame bot api 发送文本消息
 */
fun sendTgTxtMsg(messageWzFmt: String) {

//        grpid=-1002209160657  //ntfy mir coll grp
//
//// 你的 Telegram 机器人 Token
//        const token =  "8121736741:AAGKUS35nye"
//
//        。。。


    val url = URL(TELEGRAM_API_URL)
    val conn = url.openConnection() as HttpURLConnection
    conn.requestMethod = "POST"
    conn.doOutput = true
    conn.setRequestProperty("Content-Type", "application/json")

    // 构造 JSON 请求体
    val json = JSONObject().apply {
        put("chat_id", CHAT_ID)
        put("text", messageWzFmt)
        put("parse_mode", "Markdown") // 可选: "HTML" 或 "Markdown"
    }

    // 发送请求
    val writer = OutputStreamWriter(conn.outputStream)
    writer.write(json.toString())
    writer.flush()
    writer.close()

    // 读取响应
    val responseCode = conn.responseCode
    if (responseCode == HttpURLConnection.HTTP_OK) {
        Log.d("TelegramBot", "消息发送成功")
    } else {
        throw RuntimeException("TelegramBot 发送失败，状态码: $responseCode")
        // throw runtimeExction("TelegramBot 发送失败，状态码: $responseCode")
        //  Log.e("TelegramBot", "发送失败，状态码: $responseCode")
    }
//            throw RuntimeException("这是一个示例异常")
    conn.disconnect()


}

fun getStackTraceString(throwable: Throwable): String {
    val stringWriter = StringWriter()
    val printWriter = PrintWriter(stringWriter)
    throwable.printStackTrace(printWriter)
    return stringWriter.toString()
}