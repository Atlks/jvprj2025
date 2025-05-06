//package api.usr
//import MainApp
//import cfg.IniCfg.iniContnr
//import entityx.usr.SetWithdrawalPasswordDto
//import handler.usr.SetWthdrPwdHdr
//import org.junit.Assert.assertNotNull
//import org.junit.Test
//import util.tx.TransactMng
//import util.tx.TransactMng.openSessionBgnTransact
//
//class setWthpwdTest {
//
//
//    @Test
//    fun testSetWithdrawalPassword() {
//
//        //ini contnr 4cfg,, svrs
//        iniContnr()
//        openSessionBgnTransact()
//        // 准备测试数据
//        val reqdto = SetWithdrawalPasswordDto().apply {
//            // 这里根据你的 SetWithdrawalPasswordDto 的字段进行设置
//            pwd = "MySecurePassword123"
//            uname = "user123"
//        }
//
//        // 创建 Java 类实例
//        val service = SetWthdrPwdHdr()
//
//        // 调用 main 方法
//        val result: Any? = service.handleRequest(reqdto,null)
//
//
//        TransactMng.commitTsact()
//
//        // 验证返回结果，这里示例使用了简单断言
//        assertNotNull(result)
//        println(result)
//
//        // 你可以根据 main 返回值的实际类型进行判断
////        if (result is Response) {
////            assertEquals(200, result.status)
////        } else {
////            println("返回类型不是 Response，而是：${result?.javaClass?.name}")
////        }
//    }
//}