package util.misc;

import jakarta.validation.constraints.NotNull;

import java.lang.reflect.Method;

public class ReflectionUtils {

    public static @NotNull Class<?> getFirstParamClassFromMethod(  @NotNull Method mth) {
        Class<?>[] paramTypes = mth.getParameterTypes();
        return  paramTypes[0];
    }

    public static void main(String[] args) throws NoSuchMethodException {
        // 测试方法
        Method method = TestClass.class.getMethod("testMethod", String.class, int.class);
        Class<?> firstParamClass = getFirstParamClassFromMethod(method);

        System.out.println("第一个参数类型：" + (firstParamClass != null ? firstParamClass.getName() : "无参数"));
    }

    static class TestClass {
        public void testMethod(String arg1, int arg2) {
        }
    }
}
