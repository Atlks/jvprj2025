package toolsMy

import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption


fun main() {
    repeat(20)
    {
        copy("c:/down/openvpn-connect-3.5.0.3818_signed.msi","c:/newfldr")
    }
}


    fun getUuid(): Any? {
        return java.util.UUID.randomUUID().toString()
    }

/**
 * 如果目录不存在就新建
 */
    fun copy(fpath: String, dir: String): Any {
    // 创建目标目录（如果不存在）
    val targetDir = File(dir)
    if (!targetDir.exists()) {
        targetDir.mkdirs()
    }

    val newFilePath = File(targetDir, "${getUuid()}.zip").absolutePath
    copyFile2(fpath, newFilePath)

    return newFilePath // 返回新文件路径
    }

    fun copyFile2(fpath: String, newFilePath: String) {

       println(" fun copyFile2(fpath="+fpath+",newfi="+newFilePath)
        val sourceFile = File(fpath)
        val destFile = File(newFilePath)

        // 创建目标文件所在目录（如果不存在）
        destFile.parentFile?.mkdirs()

        // 使用 Java NIO 复制文件，覆盖已有文件
        Files.copy(
            sourceFile.toPath(),
            destFile.toPath(),
            StandardCopyOption.REPLACE_EXISTING
        )
    }