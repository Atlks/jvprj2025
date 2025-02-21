package exmp

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import entityx.CharBo
import net.sourceforge.pinyin4j.PinyinHelper
import util.Util2025.encodeJson
import java.io.File

fun main() {
      getSmpName4boy()
    val liRzt = mutableListOf<CharBo>() // 正确初始化可变列表

   // smpPySlkt(liRzt)
}

private fun smpPySlkt(liRzt: MutableList<CharBo>) {
    var li = getSmplPY()
    var charBos: List<CharBo> = toBeansFrmFile<CharBo>("stkcnt_namech.json")


    for (ch in charBos) {  // 遍历所有索引
        if (li.contains(ch.pinyinNoTone))
            liRzt.add(ch)

    }

    writeFile2025(encodeJson(liRzt), "smpPyCns.json")
}

//得到简单的拼音  abt45个 音节
fun getSmplPY(): MutableList<String> {

    // 简单声母列表
    val smplShenmu = "b m d n l g h z s w y ".toCharArray()
    //简单韵母表
    val smplYunmu = "a i o e u ".toCharArray()

    val li = mutableListOf<String>() // 正确初始化可变列表

    for (ch in smplShenmu) {
        for (ym in smplYunmu) {
            if (ch.toString().trim().isNotEmpty() && ym.toString().trim().isNotEmpty())
                li.add(ch.toString() + ym.toString()) // 拼接声母和韵母
        }
    }

    println(li.size) // 正确获取列表大小
    return li;
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
    calcProp4cnchar()
    var charBos: List<CharBo> = toBeansFrmFile<CharBo>("cnchs2500v2501.json")

    //val liRzt = mutableListOf<CharBo>() // 正确初始化可变列表
   //这里如何做fitler
    // CharBo的属性  canUseInName=true  boynameCanUse=true
    // isSmpPy=true stkcnt<10
    val liRzt = charBos.filter {
        it.canUseInName && it.boynameCanUse && it.isSmpPy && it.stkcnt < 15
    }

    for (curChar in liRzt) {
        print(curChar.ch)
    }
    writeFile2025(encodeJson(liRzt), "smpName4boyFltred.json")
}

private fun calcProp4cnchar() {
    val liRzt = mutableListOf<CharBo>() // 正确初始化可变列表
    var girlnameFile = "C:\\Users\\attil\\IdeaProjects\\jvprj2025\\res\\girlNameChar.md"
    var gilrNameCharsSet = toSetFrmFile(girlnameFile);
    var notforNameChsF = "C:\\Users\\attil\\IdeaProjects\\jvprj2025\\res\\notForName.md"

    var notForNameCharsSet = toSetFrmFile(notforNameChsF);

    var charBos: List<CharBo> = toBeansFrmFile<CharBo>("stkcnt.json")

    var liSmpPys = getSmplPY()
    for (curChar in charBos) {  // 遍历所有索引
        // val curChar: CharBo = charBos[offset]  // 使用索引访问
        if (curChar.ch.equals("芳"))
            println("dd")


        if (notForNameCharsSet.contains(curChar.ch)) {
            curChar.canUseInName = false
            curChar.isGilrOnlyNameCh = "不适合"
            curChar.boynameCanUse=false;
        } else {
            curChar.canUseInName = true
            //设置属性，只适合女孩的，不能使用在名字里面的字
            if (gilrNameCharsSet.contains(curChar.ch)) {
                curChar.isGilrOnlyNameCh = CharBo.enm_girlOnlyName
                curChar.boynameCanUse=false;
            } else
            {
                curChar.boynameCanUse=true;
                curChar.isGilrOnlyNameCh = CharBo.enm_boyname
            }

        }


        curChar.pinyin = getPinyin(curChar.ch)
        curChar.pinyinWzTone = getPinyin(curChar.ch)
        curChar.pinyinNoTone = trimRight(getPinyin(curChar.ch), 1)
        if (liSmpPys.contains(curChar.pinyinNoTone))
            curChar.isSmpPy = true;
        else
            curChar.isSmpPy = false;
        //liRzt.add(curChar)
    }

    // 写入 JSON 文件
    writeFile2025(encodeJson(charBos), "cnchs2500v2501.json")
}

//从右侧截取字符串,去除长度为len
fun trimRight(s: String?, len: Int): String {
    if (s.isNullOrEmpty() || len >= s.length) return "" // 处理空值或去除长度大于等于字符串长度的情况
    return s.substring(0, s.length - len) // 截取前 `s.length - len` 个字符
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


var charBos: List<CharBo> = toBeansFrmFile<CharBo>("stkcnt.json")

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
