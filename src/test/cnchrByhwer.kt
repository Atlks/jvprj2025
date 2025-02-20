package test


import java.io.File

fun loadStrokeData(filePath: String): Map<Char, Int> {
    val strokeMap = mutableMapOf<Char, Int>()
    File(filePath).forEachLine { line ->
        if (line.startsWith("U+")) {
            val parts = line.split("\t")
            if (parts.size >= 2) {
                val codePoint = parts[0].removePrefix("U+").toInt(16)
                val strokeCount = parts[1].toIntOrNull()
                if (strokeCount != null) {
                    strokeMap[codePoint.toChar()] = strokeCount
                }
            }
        }
    }
    return strokeMap
}

fun main() {
    val strokeData = loadStrokeData("C:\\Users\\attil\\Downloads\\Unihan_IRGSources.txt")
    println(strokeData['汉']) // 5
    println(strokeData['字']) // 6
}

