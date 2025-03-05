package test

import org.bitcoinj.crypto.HDKeyDerivation
import org.bitcoinj.crypto.MnemonicCode
import org.bitcoinj.crypto.MnemonicException
import org.web3j.crypto.ECKeyPair
import org.web3j.crypto.Keys
import org.web3j.crypto.MnemonicUtils

import util.algo.EncodeUtil.*
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

import org.bitcoinj.crypto.*

/**
 * ffffff999999999s
 * 对应的addr sh  0x1704......c99e
 *
 * //mmnc2add.kt  v250220
 *======== fun indx
 *
 * // 更根据128位的种子  16bytes 生成12个助记词
 * fun generateMnemonic(byteArr128bit: ByteArray): String {
 *fun mmnc2addr(mnemonic: String):
 *
 *
 */
fun main() {
    //0x9eaa09b2505455486a80a95c4c8bcfc141f756d3
    var mmnc = " glare pave fatal catch cake large mad exit any hood expose neither"
//+
    //       "这个助剂词生成的eth地址是多少"

    //  println(mmnc2addr(mmnc))

   // testMykey2mmnc()

    scanAddrNumbAddr()

}

private fun scanAddrNumbAddr() {
    val basewd = String(Files.readAllBytes(Paths.get("C:\\Program Files\\tmp\\basewd.txt")), StandardCharsets.UTF_8)
    // val offst = 1
    val slt = String(Files.readAllBytes(Paths.get("C:\\Program Files\\tmp\\salt.txt")), StandardCharsets.UTF_8)
    for (offst in 1..0) { // 使用Kotlin的区间语法
        //  println(i)
        println("\n\n\n")
        var offset = offst
      //  offset = 999999999
        val baseNNNst = basewd + offset + slt

        if(offset==3)
            println("D435")
        println("ff8." + offset + "s")
     //   println(baseNNNst)
        var mmnc = geneMMncByMyseed(baseNNNst)
        val addr = mmnc2addr(mmnc)
     //   println(addr)
        if(isHead3isNumChkEthAdd(addr)  ) {
            println("add=$addr")
        }
    }
}

// 获取前三位 字符，如果全是数字，那么返回true
fun isHead3isNumChkEthAdd(str: String): Boolean {
    var strDeHd=str.substring(2);
    val allIsDgt = strDeHd.take(3).all { it.isDigit() }
    return allIsDgt
}

private fun testMykey2mmnc() {
    val basewd = String(Files.readAllBytes(Paths.get("C:\\Program Files\\tmp\\basewd.txt")), StandardCharsets.UTF_8)
    val offst = 999999999
    val slt = String(Files.readAllBytes(Paths.get("C:\\Program Files\\tmp\\salt.txt")), StandardCharsets.UTF_8)
    val baseNNNst = basewd + offst + slt
    var md5str = encodeMd5(baseNNNst)  //1a527d08b458c80f894d08156c2a97d3
    println("md5encodeHex:" + md5str)

    //  println(generateMnemonic(md5str))
    //32 byte
    // md5str=md5str.take(16)
    // 将十六进制密钥转换为字节数组
    val a128bitSeed = encodeMd5AsByteArr(baseNNNst)

    println(generateMnemonic(a128bitSeed))
}

fun geneMMNcForeach() {
    // 读取文件内容
    val basewd = String(Files.readAllBytes(Paths.get("C:\\Program Files\\tmp\\basewd.txt")), StandardCharsets.UTF_8)
    // val offst = 1
    val slt = String(Files.readAllBytes(Paths.get("C:\\Program Files\\tmp\\salt.txt")), StandardCharsets.UTF_8)
    for (offst in 1..2) { // 使用Kotlin的区间语法
        //  println(i)
        val baseNNNst = basewd + offst + slt
        println("ff8." + offst + "s")
        geneMMncByMyseed(baseNNNst)
    }
}

fun geneMMncByMyseed(baseNNNst: String): String {


//    println("\r\n\r\n\r\n---------------\r\n")
//
//
//    // 假设 i 是一个整数，替换为实际值
//    val i = 88 // 根据需要设置 i 的值
//    val baseNNNst = basewd + i + slt


    println("4verfy hexEncd=>" + encodeMd5Dbl(baseNNNst))
 //   println("ky md5encodeHex:" + encodeMd5(baseNNNst))
    val message = generateMnemonic(encodeMd5AsByteArr(baseNNNst))
//    println(message)
    return message;
}


/**
 * ff8.999999999s
 * 4verfy hexEncd=>bae7ed30447417668fbace4214530d15
 * md5:1a527d08b458c80f894d08156c2a97d3
 *
 * mmnc：bottom negative drama hamster milk amazing cement patient betray genuine planet pony
 *
 * ktln
 * md5str:1a527d08b458c80f894d08156c2a97d3
 * cousin answer erode human drink bring match mammal debris border army smile
 */
// 更根据128位的种子生成12个助记词
fun generateMnemonic(byteArr128bit: ByteArray): String {


// 确保传入的字节数组是128位的
    check(byteArr128bit.size == 16) { "Seed must be 128 bits (16 bytes) long." }

    try {
        // 使用MnemonicCode生成助记词
        val mnemonicCode = MnemonicCode.INSTANCE
        val mnemonic = mnemonicCode.toMnemonic(byteArr128bit)

        // 将助记词列表转换为以空格分隔的字符串
        return mnemonic.joinToString(" ")
    } catch (e: MnemonicException) {
        // 处理可能的异常
        throw RuntimeException("Failed to generate mnemonic", e)
    }


}

/**
 * fk999999999s
 * bottom negative drama hamster milk amazing cement patient betray genuine planet pony
 */
//fun generateMnemonic(a32Bytestr: String?): String {
//    // 将十六进制密钥转换为字节数组
//    val privateKeyBytes = Hex.decode(a32Bytestr)
//
//    // 使用前 16 字节作为熵（128 位熵）
//    val entropy = ByteArray(16)
//    System.arraycopy(
//        privateKeyBytes,
//        0,
//        entropy,
//        0,
//        min(privateKeyBytes.size.toDouble(), entropy.size.toDouble()).toInt()
//    )
//
//    // 生成助记词
//    var mnemonic: List<String?>? = null
//    try {
//        mnemonic = MnemonicCode.INSTANCE.toMnemonic(entropy)
//    } catch (e: MnemonicLengthException) {
//        throw java.lang.RuntimeException(e)
//    }
//    return java.lang.String.join(" ", mnemonic)
//}


fun mmnc2addr(mnemonic: String): String {

    // 生成熵（这一步通常不需要，因为助记词已经包含了熵）
    val entropy = MnemonicUtils.generateEntropy(mnemonic)


    // 生成 BIP-39 seed
    val seed = MnemonicCode.toSeed(mnemonic.split(" "), "")

    // 生成 HD 钱包根密钥（BIP-32）
    val masterKey = HDKeyDerivation.createMasterPrivateKey(seed)

    // 以太坊使用 BIP-44 路径: m/44'/60'/0'/0/0
    val path = listOf(
        ChildNumber(44, true),
        ChildNumber(60, true),
        ChildNumber(0, true),
        ChildNumber(0, false),
        ChildNumber(0, false)
    )
    val childKey = deriveKey(masterKey, path)

    // 获取私钥
    val privateKey = childKey.privKeyBytes

    // 生成 ECKeyPair
    val keyPair = ECKeyPair.create(privateKey)

    // 生成以太坊地址
    val ethAddress = "0x" + Keys.getAddress(keyPair)

   // println("Ethereum Address: $ethAddress")
    return ethAddress
}
// BIP-44 路径派生
// BIP-44 路径派生
fun deriveKey(rootKey: DeterministicKey, path: List<ChildNumber>): DeterministicKey {
    var key = rootKey
    for (child in path) {
        key = HDKeyDerivation.deriveChildKey(key, child)
    }
    return key
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
