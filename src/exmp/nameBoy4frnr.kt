package exmp

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import entityx.CharBo
import net.sourceforge.pinyin4j.PinyinHelper
import util.Util2025.encodeJson
import java.io.File

fun main() {
    getSmpName4boy()
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

    var charBos:List<CharBo> = toBeansFrmFile<CharBo>("stkcnt.json")


    for (offset in charBos.indices) {  // 遍历所有索引
        val curChar: CharBo = charBos[offset]  // 使用索引访问
       if(curChar.ch.equals("芳"))
           println("dd")
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


var charBos:List<CharBo> = toBeansFrmFile<CharBo>("stkcnt.json")

//reified 让你在泛型函数中能保留和使用类型信息，解决了普通泛型函数中类型擦除的问题。reified 让你在泛型函数中能保留和使用类型信息，解决了普通泛型函数中类型擦除的问题。
//将文件读取转换位list，，文件是个json数组，里面每个jsonobject转化为class实体
inline fun <reified T> toBeansFrmFile(f: String): List<T> {
// 读取文件内容为字符串
    val jsonContent = File(f).readText()

    // 使用 Gson 来将 JSON 字符串转换为 List<T>
    // 使用 TypeToken 来传递泛型类型信息
    val gson = Gson()
    val type = object : TypeToken<List<T>>() {}.type  // 获取正确的类型
    return gson.fromJson(jsonContent, type)  // 使用 TypeToken 类型解析
}

//读取文件，将每个字符拆分，转化为字符数组，然后填充为set
fun toSetFrmFile(fileName: String): Set<String> {
// 读取文件内容并按行分割
    // 读取文件内容并按行分割
    val lines = File(fileName).readLines()

    // 使用 Set 来存储不重复的字符
    val charSet = mutableSetOf<String>()

    // 遍历每一行，将每行的字符拆分并添加到 Set 中
    for (line in lines) {
        for (ch in line) {
            charSet.add(ch.toString())  // 将字符转换为字符串并加入 Set
        }
    }

    return charSet
}
