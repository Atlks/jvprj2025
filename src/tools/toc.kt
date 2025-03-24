package tools

import java.io.File


fun main() {

    var filePath = "C:\\Users\\attil\\IdeaProjects\\jvprj2025\\doc2503\\地理学 ，那些南海岛礁的故事.md" // 替换为你的文件路径
    val searchStartWzChars = "= - ." // 替换为你要查找的字符串
    println("-----------TOC INDEX------------")
    extractTOC(filePath, searchStartWzChars)
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

fun isStartWith(line: String, searchKeywords: String): Boolean {
    val keywords = searchKeywords.split(" ")
    // 检查是否有任意一个关键词存在于 line 中
    return keywords.any { line.startsWith(it) }
}
