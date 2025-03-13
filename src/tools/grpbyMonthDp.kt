package tools
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import exmp._writeFile2025
import org.apache.commons.lang3.math.NumberUtils.toDouble
import util.misc.Util2025.encodeJson
import java.io.File


//@Serializable
//data class RecordDaylyx(val date: String, val amount: Double, val category: String, val description: String)
//
//@Serializable
//data class CategorySummaryx(val category: String, val totalAmount: Double)


fun main() {



    val li = mutableListOf<RecordDaylyx>()
    var filePath = "C:\\Users\\attil\\IdeaProjects\\jvprj2025\\src\\tools\\2502mnyLg.md";
    //这里读取循环每一行
    // 逐行读取文件内容
    File(filePath).forEachLine { line ->
       // println(line) // 这里可以对每一行进行处理
        var lineFx = replaceDoublePlacespace(line);
        var fieldArr = lineFx.split(" ")
        // 确保数组有足够的元素
        if (fieldArr.size < 3) return@forEachLine

        //drop(n) 方法会丢弃列表的前 n 个元素，并返回剩余的部分。
        var dscrp =fieldArr.drop(3).joinToString(" ");
        println("prepr rcd")
        try{
            var amt=toDouble(fieldArr[1]);
             if (amt == 0.0) return@forEachLine // 正确跳过当前循环
            val record = RecordDaylyx(fieldArr[0], amt, fieldArr[2], dscrp)
            li.add(record)
        } catch (e: Exception) {
           // e.printStackTrace() // 打印异常，避免程序静默失败
        }
        //fieldArr[3] + fieldArr[4] + fieldArr[5] + fieldArr[6]


    }

    _writeFile2025(encodeJson(li), "2502mnyLg.json");
    
    grpbyx("2502mnyLg.json")
}
//需要对他分组汇总，，字段是  category ，totalAmount  ， ，读取json文件，计算
fun grpbyx(filePath: String) {
    val jsonText = File(filePath).readText()
    val records = Json.decodeFromString<List<RecordDaylyx>>(jsonText)

    val summary = records
        .groupBy { it.category }  // 按 category 进行分组
        .map { (category, list) ->
            CategorySummaryx(category, list.sumOf { it.amount }) // 计算总金额
        }


    println(Json.encodeToString(summary))
}

// 多次替换去除多余空格，不管多长空格值保留一个
fun replaceDoublePlacespace(line: String): String {
    return line.trim().replace("\\s+".toRegex(), " ")
}