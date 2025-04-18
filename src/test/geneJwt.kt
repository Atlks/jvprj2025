package test

import util.auth.JwtUtil

fun main() {
    println(JwtUtil.newToken("007"))
}