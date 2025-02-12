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



    //拦截所有类的所有方法，（包括静态方法，动态方法）   不能拦截所有方法，  匹配所有方法（包括 Solon 框架内部的方法），可能导致 AOP 失效或行为异常。

    @Before("execution(* apiUsr..*(..))") // 只拦截 `apiUsr` 包下的所有方法
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


    //  @Before("execution(* *.*(..))")
    //   @Around("execution(* apiUsr.RegHandler.handle(..))") // 只拦截 RegHandler.handle()

//    @Before("execution(public static * com.example.MyClass.myStaticMethod(..))")
//    public void beforeStaticMethod(JoinPoint joinPoint) {
//        System.out.println("Before executing static method");
//    }


//
//    // 在方法执行之前
//    // 对com.xxx.api包下的所有类的静态方法进行拦截
//    @Around("execution(static * com.xxx.api.*.*(..))")
//    public void beforeMethod() {
//        System.out.println("Method is about to be executed.");
//    }
//
//    // 在方法执行后
//    @After
//    public void afterMethod() {
//        System.out.println("Method has been executed.");
//    }
//
//    // 在方法执行前后都执行
//    @Around
//    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
//        System.out.println("Before executing: " + joinPoint.getSignature());
//        Object result = joinPoint.proceed();
//        System.out.println("After executing: " + joinPoint.getSignature());
//        return result;
//    }
}
