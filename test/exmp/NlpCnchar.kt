/**
 * 计算汉字的复杂度
 * 一种是按照像素点数，一种是按照笔画数计算
 *
 * 笔画数计算更具简单些。。字体使用黑体，粗细一致，方便程序计算笔画
 *
 * 传统汉字有很多竖弯钩算一笔，这个太复杂规则，直接程序计算横竖为俩笔，更具简单
 */

package exmp


import test.readFile
import util.misc.Util2025.encodeJson
import java.awt.Font
import java.awt.Shape
import java.awt.font.GlyphVector
import java.awt.geom.Path2D
import java.awt.image.BufferedImage
import kotlin.math.roundToInt

fun main() {
    try {

        _calculateComplexity("一")

        _calculateComplexity("大")
        val character = "属"
        _calculateComplexity(character)

        getBihua("一")
        getBihua("大")
        getBihua("属")
        getBihua("数")

      //  getStkCntMid()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}




/**
 * 根据笔画数衡量汉字复杂度算法：
 * 先计算常用 2500 汉字的笔画数，排序后取中位数（第 1250 个汉字的笔画数）。
 * 若大于此汉字的笔画数，则为复杂汉字，否则为简单汉字。
 * 中位数大约就是9.。。为了方便，10画以内的汉字位简单汉字
 */
fun _getStkCntMid( ): Int? {
    val filePath = "C:\\Users\\attil\\IdeaProjects\\jvprj2025\\res\\2500cnChar.txt"

    // 读取文件内容
    val txt = readFile(filePath)
    val charArr = txt.toCharArray()

    // 存储每个汉字的笔画数
    val strokeMap = mutableMapOf<String, Int>()

    // 遍历汉字，计算笔画数
    for (offset in 1 until charArr.size) {
        val curChar = charArr[offset]
        strokeMap[curChar.toString()] = getStrokeCountWin(curChar) // 调用 Windows 获取笔画数
    }

    // 转换为可排序的列表
    // 按笔画数排序，转换为列表
    val sortedList = strokeMap.entries
        .sortedBy { it.value }
        .map { mapOf("ch" to it.key, "stkcnt" to it.value) }

    // 按笔画数排序
   // val sortedList = strokeList.sortedBy { it["stkcnt"] ?: 0 }

    // 写入 JSON 文件
    _writeFile2025(encodeJson(sortedList), "stkcnt.json")

    // 返回中位数笔画数
    // 获取中位数笔画数（防止索引越界）
    val medianIndex = sortedList.size / 2
    return (sortedList.getOrNull(medianIndex)?.get("stkcnt") ?: 0) as Int?
}


//fun getStkCntMid(char:String)
//{
//var f="C:\\Users\\attil\\IdeaProjects\\jvprj2025\\res\\2500cnChar.txt"
//    val mutableMap = mutableMapOf("一" to 1 )
//
//
//    var txt= readFile(f);
//    var charArr=txt.toCharArray();
//    for(offst 1...charArr.size)
//    {
//        var curChar=charArr[offst]
//        mutableMap[curChar] = getStrokeCountWin(curChar)
//         // 添加新元素
//    }
//
//    var list= listOf()
//    for ((key, value) in mutableMap) {
//        val charMap = mutableMapOf( )
//        charMap["ch"]=key
//        charMap["stkcnt"]=value
//      //  println("$key -> $value")
//        list.add(charMap)
//    }
//
//    //按照笔画数排序
//
//    writeFile2025(encodeJson(list),"stkcnt.json")
//
//    return list[1250]["stkcnt"]
//}

/**
 *计算汉字笔画数 ，，估算 复杂度路进度度量值除以8即可，四舍五入小数部分
 */
fun getStrokeCountWin(ch: Char): Int {
    return     getBihua(ch.toString())
}
/**
 *计算汉字笔画数 ，，估算 复杂度路进度度量值除以8即可，四舍五入小数部分
 */
fun getBihua(char: String, divNum: Int=8): Int {
    println("char="+char)
  //  val fzd = calcFzadu(char)
    val fzd = _calculateComplexity(char).toDouble() // 将 fzd 转为 Double 类型
    val value = (fzd / divNum).roundToInt()

    println("ret="+value)
    return value // 四舍五入并转为 Int 类型


}

//计算字形复杂度
private fun _calculateComplexity(character: String):Int {
    val font = Font("黑体", Font.PLAIN, 16) // 使用合适的字体和大小

    // 使用 BufferedImage 创建 Graphics2D 实例
    val image = BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)
    val graphics = image.createGraphics()

    // 使用 Graphics2D 创建 GlyphVector
    val glyphVector = font.createGlyphVector(graphics.fontRenderContext, character)

    // 计算复杂度
    val complexity = _calculateComplexity(glyphVector)
    println("Character: $character Complexity: $complexity")
    return  complexity
}

// 计算字形复杂度：通过路径段数来估算
fun _calculateComplexity(glyphVector: GlyphVector): Int {
    var complexity = 0

    for (i in 0 until glyphVector.numGlyphs) {
        val glyphOutline: Shape = glyphVector.getGlyphOutline(i) // 获取字形的轮廓
        if (glyphOutline is Path2D) {
            val path = glyphOutline as Path2D
            complexity += _getPathSegmentCount(path) // 计算路径段数
        }
    }

    return complexity
}


// 计算路径段数
fun _getPathSegmentCount(path: Path2D): Int {
    var segmentCount = 0
    val iterator = path.getPathIterator(null) // 获取路径迭代器

    // 遍历路径的所有段
    while (!iterator.isDone) {
        iterator.currentSegment(FloatArray(6)) // 获取当前路径段的坐标
        iterator.next() // 移动到下一个路径段
        segmentCount++ // 每次遇到一个路径段，计数加一
    }
    return segmentCount
}

// 计算字形复杂度：通过路径点数来估算
//fun calculateComplexity(glyphVector: GlyphVector): Int {
//    var complexity = 0
//
//    for (i in 0 until glyphVector.numGlyphs) {
//        val glyphOutline: Shape = glyphVector.getGlyphOutline(i) // 获取字形的轮廓
//        if (glyphOutline is Path2D) {
//            val path = glyphOutline as Path2D
//            complexity += getPathPointCount(path)
//        }
//    }
//
//    return complexity
//}
//
//// 计算路径上的点数
//fun getPathPointCount(path: Path2D): Int {
//    var count = 0
//    val pathIterator: PathIterator = path.getPathIterator(null)
//
//    while (!pathIterator.isDone) {
//        val type = pathIterator.currentSegment(FloatArray(6)) // 获取当前路径段的坐标
//        count++
//        pathIterator.next()
//    }
//    return count
//}
