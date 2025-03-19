package tools


import java.net.URI
import java.time.LocalDate
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.random.Random
import com.jcraft.jsch.ChannelSftp
import com.jcraft.jsch.JSch
import com.jcraft.jsch.Session

import java.io.File
import java.time.format.DateTimeFormatter

fun main() {

    scheduleTask()
    Thread.sleep(Long.MAX_VALUE)  // 防止主线程退出
}

//每一小时执行任务定时器
fun scheduleTask() {
    val scheduler = Executors.newScheduledThreadPool(1)
    scheduler.scheduleAtFixedRate({
        println("执行任务: ${System.currentTimeMillis()}")
        myFunctionSyncfileTask()
    }, 0, 1, TimeUnit.HOURS)
}


// 执行任务，同步文件
fun myFunctionSyncfileTask() {
    var f = "hosts.txt"
    var hosts: List<HostInfo> = readFile2List(f);
    var radomHost: HostInfo = getRadomHost(hosts)
    var monthYYYYmm = getNowMonthOrLastMonth()
    var sourceDir = "/datadir/" + monthYYYYmm;
    // 获取远程主机源目录中的文件列表
    var filelist: List<Fileinfox> = getfilesFromRometeHostDir(radomHost, sourceDir)

    // 遍历文件列表，下载文件
    for (file in filelist) {
        var targetDir = sourceDir;
        // 执行下载操作
        download2(radomHost,file, targetDir);
    }
}

//通过ssh下载文件到本地目录   或ftp
fun download2(radomHost: HostInfo, file: Fileinfox, targetDir: String): Any {
    val jsch = JSch()
    val session: Session
    try {
        session = jsch.getSession(radomHost.uname, radomHost.host, radomHost.port)
        session.setPassword(radomHost.pwd)
        session.setConfig("StrictHostKeyChecking", "no")
        session.connect()

        val channel = session.openChannel("sftp") as ChannelSftp
        channel.connect()

        val remoteFilePath = file.path
        val localFilePath = "$targetDir/${file.name}"

        // 下载文件
        channel.get(remoteFilePath, localFilePath)
        channel.disconnect()
        session.disconnect()

        return "File downloaded successfully via SSH to $localFilePath"
    } catch (e: Exception) {
        e.printStackTrace()
        return "Error downloading file via SSH: ${e.message}"
    }
}

//获取文件列表从远程文件夹，使用ssh协议或ftp
fun getfilesFromRometeHostDir(radomHost: HostInfo, sourceDir: String): List<Fileinfox> {
    val fileList = mutableListOf<Fileinfox>()
    val jsch = JSch()
    val session: Session
    try {
        // 创建 SSH 会话
        session = jsch.getSession(radomHost.uname, radomHost.host, radomHost.port)
        session.setPassword(radomHost.pwd)
        session.setConfig("StrictHostKeyChecking", "no")
        session.connect()

        // 打开 SFTP 通道
        val channel = session.openChannel("sftp") as ChannelSftp
        channel.connect()

        // 列出远程目录中的文件
        val remoteFiles = channel.ls(sourceDir)

        // 遍历文件列表，创建 Fileinfox 对象
        for (file in remoteFiles) {
            val sftpFile = file as ChannelSftp.LsEntry
            val fileName = sftpFile.filename
            if (fileName != "." && fileName != "..") { // 排除当前目录和父目录
                val remoteFilePath = "$sourceDir/$fileName"
                fileList.add(Fileinfox(fileName, remoteFilePath,99))
            }
        }

        // 断开连接
        channel.disconnect()
        session.disconnect()

    } catch (e: Exception) {
        e.printStackTrace()
    }

    return fileList
}

//得到本月或上个月的月份   格式yyyymm 202402  ，就是2024年2月份
fun getNowMonthOrLastMonth(): Any {
    val now = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("yyyyMM")

    // 随机决定返回当前月或上个月
    return if (Random.nextBoolean()) {
        // 当前月份
        now.format(formatter)
    } else {
        // 上一个月份
        now.minusMonths(1).format(formatter)
    }
}


//随机选取一个hostinfo
fun getRadomHost(hosts: List<HostInfo>): HostInfo {
    require(hosts.isNotEmpty()) { "Host 列表不能为空" }
    return hosts[Random.nextInt(hosts.size)]
}

//读取文件，每行解析为一个hostinfo
//每行的格式是   ssh://localhost:3306/?user=root&password=pppppp
fun readFile2List(f: String): List<HostInfo> {
    return File(f).readLines().mapNotNull { line ->
        try {
            val uri = URI(line)
            val host = uri.host ?: return@mapNotNull null
            val port = if (uri.port != -1) uri.port else return@mapNotNull null
            val query = uri.query ?: return@mapNotNull null

            val params = query.split("&").associate {
                val kv = it.split("=")
                if (kv.size == 2) kv[0] to kv[1] else return@mapNotNull null
            }

            val uname = params["user"] ?: return@mapNotNull null
            val pwd = params["password"] ?: return@mapNotNull null

            HostInfo(host, port, uname, pwd)
        } catch (e: Exception) {
            null  // 解析失败的行会被跳过
        }
    }
}
