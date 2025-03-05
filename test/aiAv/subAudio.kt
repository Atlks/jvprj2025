package aiAv
import util.misc.Util2025.encodeJson
import java.io.File
import java.io.IOException
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip

fun main() {

    var wav="C:\\Windows\\SysWOW64\\output.wav";
     subAudio(wav,4.5,800,"alarmPre.wav")
    var almWav="C:\\Users\\attil\\IdeaProjects\\jvprj2025\\alarmPre.wav";
    joinAudio(almWav,"C:\\cfg\\搭尬.wav","didiDaga.wav")

    playStallWarning("didiDaga.wav");
}


fun playStallWarning(wav: String) {
    try {
       // val wav = "stall_warning.wav"
        val file = File(wav) // WAV 文件路径
        val audioInputStream = AudioSystem.getAudioInputStream(file)
        val clip: Clip = AudioSystem.getClip()
        clip.open(audioInputStream)
        clip.loop(Clip.LOOP_CONTINUOUSLY) // 无限循环
        clip.start() // 开始播放

        // 等待播放完成，防止主线程退出
        Thread.sleep(Long.MAX_VALUE)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
/**
 * 使用ffmpeg 连接俩个wav
 */


/**
 * 使用 FFmpeg 连接两个 WAV 文件
 *
 * @param almWav 第一个 WAV 文件
 * @param wav2 第二个 WAV 文件
 * @param output 合并后的输出 WAV 文件
 */
fun joinAudio(almWav: String, wav2: String, output: String) {
    val listFile = File("file_list.txt")

    // 创建文件列表
    listFile.writeText("file '$almWav'\nfile '$wav2'\n")

    val command = listOf(
        "ffmpeg", "-y",
//        "-f", "concat",
//        "-safe", "0",
//        "-i", listFile.absolutePath,
//        "-c", "copy",


        "-i", almWav,
        "-i", wav2,
        "-filter_complex", "[0:0][1:0]concat=n=2:v=0:a=1[out]",
        "-map", "[out]",
        output
    )
    println(encodeJson(command))
    try {
        val process = ProcessBuilder(command)
            .redirectErrorStream(true)
            .redirectOutput(File("ffmpeg_log.txt")) // 记录日志
            .start()

        val exitCode = process.waitFor()
        if (exitCode == 0) {
            println("WAV 文件合并成功，输出文件：$output")
        } else {
            println("WAV 合并失败，请检查 FFmpeg 日志")
        }
    } catch (e: IOException) {
        println("执行 FFmpeg 失败: ${e.message}")
    } catch (e: InterruptedException) {
        println("FFmpeg 进程被中断: ${e.message}")
    } finally {
     //   listFile.delete() // 清理临时文件
    }
}

/**
 * 使用ffmpeg 截取wav文件
 * startPostion 开始截取的时间，单位秒
 * durationLength 截取长度，单位毫秒
 *
 */

//fun subAudio(wav: String, startPostion: Int, durationLength: Int, outputPath: String) {
//
//}



/**
 * 使用 FFmpeg 截取 WAV 文件
 *
 * @param wav 输入的 WAV 文件路径
 * @param startPosition 开始截取的时间，单位秒
 * @param durationLength 截取长度，单位毫秒
 * @param outputPath 输出的 WAV 文件路径
 */
fun subAudio(wav: String, startPosition: Double, durationLength: Int, outputPath: String) {
    val durationSeconds = durationLength / 1000.0  // 毫秒转秒

    val command = listOf(
        "ffmpeg", "-y",
        "-i", wav,
        "-ss", startPosition.toString(),   // 设置起始时间（秒）
        "-t", durationSeconds.toString(),  // 设置截取时长（秒）
        "-acodec", "pcm_s16le",            // 保持 WAV 格式
        "-ar", "44100",                    // 采样率（可根据需要调整）
        "-ac", "2",                         // 声道数
        outputPath
    )

    println(encodeJson(command))
    try {
        val process = ProcessBuilder(command)
            .redirectErrorStream(true)
            .redirectOutput(File("ffmpeg_log.txt")) // 记录日志，方便调试
            .start()

        val exitCode = process.waitFor()
        if (exitCode == 0) {
            println("WAV 截取成功，输出文件：$outputPath")
        } else {
            println("WAV 截取失败，请检查 FFmpeg 日志")
        }
    } catch (e: IOException) {
        println("执行 FFmpeg 失败: ${e.message}")
    } catch (e: InterruptedException) {
        println("FFmpeg 进程被中断: ${e.message}")
    }
}
