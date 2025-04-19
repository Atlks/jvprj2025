package ztoolsMy

import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.io.FileInputStream
import java.io.FileOutputStream

fun main() {
    repeat(100)
    {
        copy("c:/down/openvpn-connect-3.5.0.3818_signed.msi", "c:/newfldr")
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
    copyFileChannelChunked(fpath, newFilePath)

    return newFilePath // 返回新文件路径
}

/**
 * 这个是 Java 中最快的复制方式之一，会利用操作系统底层 DMA、零拷贝机制。
 * 性能更快
 * 高性能稳定复制大文件方案
 */
fun copyFileChannelChunked(srcPath: String, destPath: String) {
    println(" fun copyFile2(srcPath=" + srcPath + ",newfi=" + destPath)
    val maxChunk = 1024L * 1024L * 32L // 32MB
    FileInputStream(srcPath).channel.use { source ->
        FileOutputStream(destPath).channel.use { target ->
            var position = 0L
            val size = source.size()
            while (position < size) {
                val count = minOf(maxChunk, size - position)
                val transferred = source.transferTo(position, count, target)
                if (transferred <= 0) break
                position += transferred
            }
        }
    }
}
/**
 * 这个是 Java 中最快的复制方式之一，会利用操作系统底层 DMA、零拷贝机制。
 * 但是性能不如  copyFileChannelChunked
 */
fun copyFileFastByChannel(srcPath: String, destPath: String) {
    println(" fun copyFile2(fpath=" + srcPath + ",newfi=" + destPath)
    FileInputStream(srcPath).channel.use { source ->
        FileOutputStream(destPath).channel.use { target ->
            source.transferTo(0, source.size(), target)
        }
    }
}

//性能慢
fun copyFile2(fpath: String, newFilePath: String) {

    println(" fun copyFile2(fpath=" + fpath + ",newfi=" + newFilePath)
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