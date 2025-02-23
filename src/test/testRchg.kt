package test


//import MyProxyExample.MyProxyExample
//import utilDep.AOPASM.customClassLoader


fun main() {



     //   Thread.sleep(7000) // 线程中延迟 3 秒
        println("\n\n\n===================start req")
        var rzt = sendGetRequest("http://localhost:8889/rechargeHdr?amt=888", "uname=007");
        println("responsetxt=$rzt")

   // Thread.sleep(5000) // 让主线程保持运行，防止程序退出
}