package utilDep;

import com.google.gson.Gson;
import javassist.*;

public class StaticMethodAOP {
    private static final Gson gson = new Gson();

    public static void enhanceClass(String className) throws Exception {
        ClassPool pool = ClassPool.getDefault();
//        pool.appendClassPath(new LoaderClassPath(Thread.currentThread().getContextClassLoader()));
        pool.insertClassPath("out/classes");
        pool.insertClassPath("target/classes");
        pool.insertClassPath("./out/production/your_pro");
        //C:\Users\attil\IdeaProjects\jvprj2025\target\classes\apiUsr\RegHandler.class
        // 如果是 Maven 项目
        pool.insertClassPath("bin");  // 如果是普通 Java 项目
     //   CtClass ctClass = pool.get("api.usr.RegHandler");

        CtClass ctClass = pool.get(className);

        // 获取所有方法
        for (CtMethod method : ctClass.getDeclaredMethods()) {
            enhanceMethod(ctClass, method);
            // 只处理静态方法
//            if ((method.getModifiers() & Modifier.STATIC) != 0) {
//
//            }
        }

        // 重新加载修改后的类
        ctClass.toClass();
    }

    private static void enhanceMethod(CtClass ctClass, CtMethod method) throws CannotCompileException {
        String methodName = method.getName();

        // 生成新的方法体
        String code = "{"
                + "    long startTime = System.nanoTime();"
                + "    Object result = null;"
                + "    try {"
                + "        System.out.println(\"[AOP] 调用静态方法: " + methodName + "\");"
                + "        System.out.println(\"[AOP] 参数: \" + com.google.gson.Gson().toJson($args));"
                + "        result = ($r) " + methodName + "($$);"
                + "        return result;"
                + "    } finally {"
                + "        long elapsedTime = System.nanoTime() - startTime;"
                + "        System.out.println(\"[AOP] 方法: " + methodName + " 执行完成，返回值: \" + com.google.gson.Gson().toJson(result));"
                + "        System.out.println(\"[AOP] 执行时间: \" + (elapsedTime / 1_000_000.0) + \" ms\");"
                + "    }"
                + "}";

        // 替换原始方法
        method.setBody(code);
    }
}
