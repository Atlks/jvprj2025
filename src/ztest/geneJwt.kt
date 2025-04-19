package ztest

import util.auth.JwtUtil

fun main() {
    println(JwtUtil.newToken("007"))
}