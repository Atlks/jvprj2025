package test

import util.auth.JwtUtil

fun main() {
    println(JwtUtil.generateToken("007"))
}