
不能拦截handle方法。。看下说明原因


package apis;

import org.aspectj.lang.JoinPoint;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.noear.solon.annotation.Component;

import java.util.Arrays;

import static java.io.IO.println;

@Aspect
@Component
public class MyAspect {

    @Before("execution(* *.*(..))")
    public  void before(JoinPoint joinPoint ) {
        println("Before method: ${joinPoint.signature.name}");
    }
    //execution(<访问修饰符> <返回类型> <类名>.<方法名>(<参数类型>))
    // 对所有类的静态方法进行拦截
    @Around("execution(* *.*(..))")
    public Object logMethodParameters(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取方法名
        String methodName = joinPoint.getSignature().getName();

        // 获取方法参数
        Object[] methodArguments = joinPoint.getArgs();

        // 记录方法参数
        System.out.println("Executing method: " + methodName);
        System.out.println("Method parameters: " + Arrays.toString(methodArguments));

        // 执行方法并返回结果
        return joinPoint.proceed();
    }




//test .kt
package test;

import api.usr.RegHandler
import apis.BaseHdr
import apis.MyAspect
import com.sun.tools.javac.tree.TreeInfo.args
import org.noear.solon.Solon
import util.HttpExchangeImp



//@Test
fun main() {
//  Solon.start(RegHandler::class.java)
//  Solon.start(RegHandler::class.java)

    Solon.start(MyAspect::class.java, arrayOf())
    Solon.start(RegHandler::class.java, arrayOf())
    BaseHdr.iniCfgFrmCfgfile()
    //   addObj(ord,saveUrlOrdBet,OrdBet.class);
    val he = HttpExchangeImp("http://localhost:8889/reg?uname=qq&pwd=ppp", "uname=0091", "output2025.txt")
    RegHandler().handle(he)

}




package apiUsr;

import apis.BaseHdr;
import util.ex.existUserEx;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.noear.solon.annotation.Component;
import util.excptn.OrmUtilBiz;

import java.io.IOException;


import static apis.BaseHdr.iniCfgFrmCfgfile;
import static util.EncodeUtil.encodeMd5;


import static util.tx.HbntUtil.openSession;
import static util.Util2025.encodeJson;
import static util.Util2025.encodeJsonObj;
import static util.dbutil.*;
import static util.util2026.*;

@Component
//  http://localhost:8889/reg?uname=008&pwd=000&invtr=007
// 自定义的请求处理器
public class RegHandler extends BaseHdr implements HttpHandler {


    /**
     * @param exchange
     * @throws Exception
     */
    @Override
    public void handle(HttpExchange exchange) throws 