
// t.kt  kotlin范例
package apiAcc

import java.util.*

fun main() {
    println(123)
}

fun getUuid(): String? {
// 生成一个随机的 UUID，并将其转换为字符串
    return UUID.randomUUID().toString()
}