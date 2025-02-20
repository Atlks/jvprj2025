package exmp

import com.google.gson.Gson
import entityx.CharBo
import util.Util2025.encodeJson
import java.io.File

fun main() {

}

/*
得到简单的名字规范
汉字取自2500个简单常见汉字表
俩个字组成
音节简单
要有辅音开头，不可单韵母模式，有些国家单元音难以发音
声母响亮去除清辅音
去除含义不好的汉字，只保留褒义字和中性字
适合男孩的汉字，去除只适合女孩的汉字
笔画数简单，不要太复杂，防止签名麻烦

*/
fun getSmpName4boy() {
    var girlnameFile = "C:\\Users\\attil\\IdeaProjects\\jvprj2025\\res\\girlNameChar.md"
    var gilrNameCharsSet = toSetFrmFile(girlnameFile);
    var notforNameChsF = "C:\\Users\\attil\\IdeaProjects\\jvprj2025\\res\\notForName.md"

    var notForNameCharsSet = toSetFrmFile(notforNameChsF);

    var charBos = toBeanFrmFile<CharBo>("stkcnt.json")
    for (offset in 1 until charBos.size) {
        val curChar = charBos[offset]
        if(gilrNameCharsSet.contains(curChar.ch))
            curChar.isGilrOnlyNameCh=CharBo.enm_girlOnlyName
        if(notForNameCharsSet.contains(curChar.ch))
            curChar.CanUseInName=false

        curChar.pinyin=getPinyin(curChar.ch)

    }

    // 写入 JSON 文件
    writeFile2025(encodeJson(charBos), "stkcnt_namech.json")


}

//获取拼音
fun getPinyin(ch: String?): String? {
    if (ch.isNullOrEmpty()) return null

    // 使用 Pinyin4j 获取拼音
    val pinyinArray = PinyinHelper.toHanyuPinyinStringArray(ch[0])

    // 如果是汉字，返回拼音，否则返回原字符
    return if (pinyinArray != null) {
        pinyinArray[0].toLowerCase()  // 返回第一个拼音并转换为小写
    } else {
        ch  // 如果不是汉字，直接返回原字符
    }
}

//reified 让你在泛型函数中能保留和使用类型信息，解决了普通泛型函数中类型擦除的问题。reified 让你在泛型函数中能保留和使用类型信息，解决了普通泛型函数中类型擦除的问题。
//将文件读取转换位list，，文件是个json数组，里面每个jsonobject转化为class实体
inline fun <reified T> toBeanFrmFile(f: String): List<T> {
// 读取文件内容为字符串
    val jsonContent = File(f).readText()

    // 使用 Gson 来将 JSON 字符串转换为 List<T>
    val gson = Gson()
    return gson.fromJson(jsonContent, Array<T>::class.java).toList()
}

//读取文件，转化为字符数组，然后填充为set
fun toSetFrmFile(fileName: String): Set<String> {
// 读取文件内容并按行分割
    val lines = File(fileName).readLines()

    // 使用 Set 来存储不重复的字符串（每行转化为字符串）
    return lines.toSet()
}
