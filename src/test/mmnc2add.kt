package test
import org.web3j.crypto.ECKeyPair
import org.web3j.crypto.Keys
import org.web3j.crypto.MnemonicUtils
import org.web3j.utils.Numeric
import util.EncodeUtil.encodeMd5
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths


fun main(){
    //0x9eaa09b2505455486a80a95c4c8bcfc141f756d3
    var mmnc=" glare pave fatal catch cake large mad exit any hood expose neither"
//+
    //       "这个助剂词生成的eth地址是多少"

  //  println(mmnc2addr(mmnc))
}

fun geneMMNcForeach(){
    // 读取文件内容
    val basewd = String(Files.readAllBytes(Paths.get("C:\\Program Files\\tmp\\basewd.txt")), StandardCharsets.UTF_8)
   // val offst = 1
    val slt = String(Files.readAllBytes(Paths.get("C:\\Program Files\\tmp\\salt.txt")), StandardCharsets.UTF_8)
    for(offst in 1..99) { // 使用Kotlin的区间语法
      //  println(i)
        val baseNNNst = basewd + i + slt
    }
}
fun geneMMncByMyseed(baseNNNst:String,offset:int){



//    println("\r\n\r\n\r\n---------------\r\n")
//
//
//    // 假设 i 是一个整数，替换为实际值
//    val i = 88 // 根据需要设置 i 的值
//    val baseNNNst = basewd + i + slt

    println("ff8." + offset + "s")
    println("4verfy hexEncd=>" + encodeMd5Dbl(baseNNNst))

    val seedx = encodeMd5(baseNNNst)
    println("ky:$seedx")
}

// 俩次md5
fun encodeMd5Dbl(str: String): Any? {

}

fun generateMnemonic(seed:String){

}

fun mmnc2addr(mnemonic: String): Any {

    // 生成熵（这一步通常不需要，因为助记词已经包含了熵）
    val entropy = MnemonicUtils.generateEntropy(mnemonic)


    // 从助记词生成seed,然后根据seed生成私钥，私钥
    val seed = MnemonicUtils.generateSeed(mnemonic, null) // 可以提供密码来增强安全性


    val keyPair = ECKeyPair.create(Numeric.toBigInt(seed))


    // 生成以太坊地址
    val ethAddress = Numeric.prependHexPrefix(Keys.getAddress(keyPair.publicKey))


    // 打印以太坊地址
    println("Ethereum Address: $ethAddress")
    return  ethAddress
}

/**
 * 助记词生成eth地址
mmnc：：助记词
 */
//fun mmnc2addr(mmnc: String): Any {
//    try {
////        if (!MnemonicUtils.validateMnemonic(mmnc)) {
////            return null; // 助记词无效
////        }
//        val seed = MnemonicUtils.generateSeed(mmnc, null)
//
//        val credentials = Credentials.create(WalletUtils.generateFullBip44KeyPair(seed).privateKey.toString(16))
//        return credentials.address
//    } catch (e: Exception) {
//        e.printStackTrace()
//        return null; // 生成地址失败
//    }
//}
