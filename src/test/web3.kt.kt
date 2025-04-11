package test

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.time.Instant
import java.util.*

/**web3 函数
 * 得到当前最新的区块号码
 *  * 获取区块信息 根据区块号码
 */
fun main()
    {
        println(getTimestampSecond())
      //  getLastBlockNumber()

   println( getBlockHashByNumber(22242759,"VASRGU6XT768WSKI2VME6Z8ZK3GK5E3UDT"))
    }

    //生成 当前时间戳（秒）
    private fun getTimestampSecond(): Any {
        return Instant.now().epochSecond
    }


/**
 * 获取区块信息 根据区块号码
 */
fun getBlockHashByNumber(blockNumber: Long, apiKey: String): String? {
    val hexBlock = "0x" + blockNumber.toString(16)
    val urlStr = "https://api.etherscan.io/api" +
            "?module=proxy" +
            "&action=eth_getBlockByNumber" +
            "&tag=$hexBlock" +
            "&boolean=false" +
            "&apikey=$apiKey"
println(urlStr)
    try {
        val url = URL(urlStr)
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.connectTimeout = 25000
        connection.readTimeout = 25000

        val responseCode = connection.responseCode
        println("HTTP 响应码: $responseCode")
        val inputStream = if (responseCode in 200..299)
            connection.inputStream
        else
            connection.errorStream  // 如果失败也尝试读内容



        val reader = BufferedReader(InputStreamReader(inputStream))
        val response = StringBuilder()
        var line: String?

        while (reader.readLine().also { line = it } != null) {
            response.append(line)
        }

        reader.close()
        return response.toString()

    } catch (e: Exception) {
        println("Error fetching block data: ${e.message}")
    }

    return null
}

/**
 * 得到当前最新的区块
 * Response: {"status":"1","message":"OK","result":"22242759"}
 */
    fun getLastBlockNumber()
    {
   var tm=getTimestampSecond();
       // var url="https://api.etherscan.io/api?module=block&action=getblocknobytime&timestamp=$tm&closest=before&apikey=VASRGU6XT768WSKI2VME6Z8ZK3GK5E3UDT";


     //   val tm = getTimestampSecond()
        val apiKey = "VASRGU6XT768WSKI2VME6Z8ZK3GK5E3UDT"
        val urlStr = "https://api.etherscan.io/api?module=block&action=getblocknobytime&timestamp=$tm&closest=before&apikey=$apiKey"

        val url = URL(urlStr)
        with(url.openConnection() as HttpURLConnection) {
            requestMethod = "GET"

            val response = inputStream.bufferedReader().use(BufferedReader::readText)
            println("Response: $response")
        }

    }

