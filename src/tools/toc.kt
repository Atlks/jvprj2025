package tools

import java.io.File

/**
 * toc 生成 title
 * v250329
 * 增加了end index
 */
fun main() {

    var filePath = "C:\\Users\\attil\\IdeaProjects\\jvprj2025\\doc2503\\地理学 ，那些南海岛礁的故事.md" // 替换为你的文件路径
    filePath="C:\\0prj\\jvprj2025\\doc2503\\跨国家地理名词.md"
    filePath="C:\\0prj\\jvprj2025\\doc2503\\十大堡垒要塞top10.md"
    filePath="C:\\0prj\\jvprj2025\\doc2503\\地理 板块学.md"
    filePath="C:\\0prj\\jvprj2025\\doc2504\\东南亚避暑圣地.md"
    filePath="C:\\0prj\\jvprj2025\\doc2504\\适合普通人移民的方法大总结.md"
   // .md
    filePath="C:\\0prj\\jvprj2025\\doc2504\\prgrmlan future new fuchr 编程语言未来十大新特点2025-2035.md"

    filePath="C:\\0prj\\jvprj2025\\doc2504\\y imgrt为什么要移民.md"

    filePath="C:\\0prj\\jvprj2025\\doc2503\\fuchr job 未来工作.md"


    val searchStartWzChars = "= - . 。" // 替换为你要查找的字符串
    println("-----------TOC INDEX------------")
    extractTOC(filePath, searchStartWzChars)

    //v250329
    println("-----------end TOC INDEX------------")
}


/**
 * 读取文件内容，逐行检查并打印包含特定字符串的行。
 * @param filePath 文件路径
 * @param searchString 要查找的特定字符串
 */
fun extractTOC(filePath: String, searchString: String) {
    try {
        // 读取文件内容并按行分割
        val lines = File(filePath).readLines()

        lines.forEachIndexed { index, line ->
            if (isStartWith(line, searchString)) {
                val lineNum = index + 1
                println(line)
            }
        }
    } catch (e: Exception) {
        println("Error reading file: ${e.message}")
    }
}

/**
 * 判断是否包含任何一个关键词
 * @param line 行内容
 * @param searchKeywords 多个要搜索的关键词，用空格分割
 */
fun includesx(line: String, searchKeywords: String): Boolean {
    val keywords = searchKeywords.split(" ")
    // 检查是否有任意一个关键词存在于 line 中
    return keywords.any { line.contains(it) }
}
/**
 * 判断给定的字符串是否以指定的关键词开头
 *
 * @param line - 需要检查的字符串
 * @param searchKeywords - 以空格分隔的多个关键词
 * @returns 如果 `line` 以任意一个 `searchKeywords` 中的关键词开头，则返回 `true`，否则返回 `false`
 */
fun isStartWith(line: String, searchKeywords: String): Boolean {
    // 将关键词字符串按空格分割成数组
    val keywords = searchKeywords.split(" ")
    // 检查是否有任意一个关键词存在于 line 中
    return keywords.any { line.startsWith(it) }
}
