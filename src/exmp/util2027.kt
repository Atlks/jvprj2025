package exmp

import java.io.File

fun _writeFile2025(encodeJson: String?, file: String) {
    try {
        if (encodeJson != null) {
            File(file).writeText(encodeJson, Charsets.UTF_8)
        }
        println("File written successfully: $file")
    } catch (e: Exception) {
        e.printStackTrace()
    }
}