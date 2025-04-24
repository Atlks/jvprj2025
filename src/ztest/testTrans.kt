package ztest

import cfg.AppConfig
import cfg.Containr
import cfg.MyCfg
import entityx.usr.Usr
import entityx.wlt.TransDto
import handler.agt.AgtHdl
import handler.wlt.TransHdr
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import util.evtdrv.EvtUtil
import util.tx.TransactMng
import java.io.IOException
import java.math.BigDecimal
import java.util.function.Consumer

fun main() {


    //--------ini saveurlFrm Cfg
    AppConfig().sessionFactory() //ini sessFctr

    //ini contnr 4cfg,, svrs
    MyCfg.iniContnr()
    EvtUtil.iniEvtHdrCtnr()

    Containr.evtlist4reg.add(Consumer { u: Usr? -> AgtHdl().regEvtHdl(u) })


    var jwt="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2NjYiLCJpYXQiOjE3NDU0MTUwNzgsImlzcyI6ImF0aSIsInVwbiI6IjY2NiIsInByZWZlcnJlZF91c2VybmFtZSI6IjY2NiIsImVtYWlsIjoiYXRAdWtlLmNvbSIsInVuYW1lIjoiNjY2IiwiYXVkIjoiVVNFUiIsInJvbGUiOiJVU0VSIiwiZXhwIjoxNzU0MDU1MDc4LCJqdGkiOiJlMWIzODM4ZGQyOTk0ZWRlYjNmNTNjMzg1NzY2YzVkMSJ9.MEbTr22IB4IZdShdnUPFZoxr92StP8rBHR8Vd-hL65vU5DuXgcl-2-weO6cB5C6tB6KW14-F8zdRiqF1l9wtKQ"

    var url="http://localhost:8889/wlt/Trans?changeAmount=1"

   // println(getHttp(url,jwt))




    //   setcookie("uname", "007", exchange);//for test

    //============aop trans begn
    TransactMng.openSessionBgnTransact()


    val dto:TransDto = TransDto()  // 可以省略类型，Kotlin 自动推断类型
    dto.uname = "666"
    dto.changeAmount = BigDecimal(1);
    TransHdr().main(dto);

    TransactMng.commitTsact()
}


/**
 *
 */
fun getHttp(url: String, jwt: String): String {

    // 创建 OkHttpClient 实例
    val client = OkHttpClient()

    // 构造 HTTP GET 请求
    val request = Request.Builder()
        .url(url) // 设置 URL
        .addHeader("Authorization", "Bearer $jwt") // 添加 Authorization 头部，传递 JWT
        .build()

    // 执行请求并获取响应
    try {
        val response: Response = client.newCall(request).execute()

        // 检查响应是否成功
        if (response.isSuccessful) {
            // 返回响应的正文
            return response.body?.string() ?: "Empty Response"
        } else {
            // 如果响应失败，抛出异常
            throw IOException("Request failed with status code: ${response.code}")
        }
    } catch (e: IOException) {
        // 捕获并处理异常
        throw IOException("Error during HTTP request: ${e.message}", e)
    }


}
