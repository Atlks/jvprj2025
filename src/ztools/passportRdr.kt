package ztools

import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*

/**
 * mrz fmt line2
 * passportNumber+issuingCountry+birthDate+sex+expiryDate
 *
 * 国籍（Nationality） - 第 29-31 位（28-30）
 * 例如 CHN 代表中国。
 *
 * 可选数据（Optional Data） - 第 32-42 位（31-41）
 * 可能用于存储额外信息，如身份证号码。
 *
 * 校验位（Check Digit） - 第 43 位（42）
 * 用于验证可选数据的正确性。
 *
 * 最终校验位（Final Check Digit） - 第 44 位（43）
 * 用于验证整个 MRZ 代码的完整性。
 */
data class MRZ(
    val passportNumber: String,
    val issuingCountry: String,
    val birthDate: String,
    val sex: Char,
    val expiryDate: String,

    val optionalData: String,
    val chkDgt: String,
    val fnlChkDgt: String
)

fun main() {
    var mrzLine1 = "POCHNLI<<SHIJIE<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"
    var mrz = "EB96090654CHN8804293M2801290MAOOMKMALNNMA962"
    mrz = mrzLine1 + "\n" + mrz;
    var key = calcKeyForReadChipinfo(mrz)
    println(key)
    // println(readMRzLine2(mrz))  ;
}

//根据mrz，计算密钥，返回密钥，字节数组
/**
 * mrz  :   line+\n+line2
 * @return 128bit  16byte key
 */
fun calcKeyForReadChipinfo(mrz: String): ByteArray {
    // 1. 解析MRZ字符串
    val lines = mrz.split("\n")
    if (lines.size < 2) {
        throw IllegalArgumentException("Invalid MRZ format")
    }

    val line1 = lines[0]
    val line2 = lines[1]

    // 假设MRZ格式如下（根据实际情况调整）：
    // P<国家代码护照号码<<出生日期<<有效期<<可选数据
    // 假设护照号码，出生日期，有效期位置如下，需要根据实际护照来确定位置。
    val passportNumber = line2.substring(0, 9).replace("<", "")
    val birthDateStr = line2.substring(13, 19)
    val expiryDateStr = line2.substring(21, 27)

    // 2. 格式化日期
    val birthDate = formatDate(birthDateStr)
    val expiryDate = formatDate(expiryDateStr)

    // 3. 构建种子字符串
    val seedString = passportNumber + birthDate + expiryDate

    // 4. 计算SHA-1哈希
    val sha1 = MessageDigest.getInstance("SHA-1")
    val seedBytes = seedString.toByteArray()
    val hash = sha1.digest(seedBytes)

    // 5. 取前16字节作为密钥
    return hash.copyOfRange(0, 16)
}

fun formatDate(dateStr: String): String {
    val inputFormat = SimpleDateFormat("yyMMdd", Locale.US)
    val outputFormat = SimpleDateFormat("yyyyMMdd", Locale.US)
    val date = inputFormat.parse(dateStr)
    return outputFormat.format(date)
}

//阅读护照第二行 ,解析
fun readMRzLine2(mrz: String): MRZ? {
    if (mrz.length != 44) {
        println("Invalid MRZ length")
        return null
    }

    val passportNumber = mrz.substring(0, 9).trim('<')
    val issuingCountry = mrz.substring(10, 13)
    val birthDate = mrz.substring(13, 19)
    val sex = mrz[20]
    val expiryDate = mrz.substring(21, 27)

    val optionalData = mrz.substring(28, 42).trim('<')
    val chkDgt = mrz.substring(42, 43);
    val fnlChkDgt = mrz.substring(43, 44);

    return MRZ(passportNumber, issuingCountry, birthDate, sex, expiryDate, optionalData, chkDgt, fnlChkDgt)


}