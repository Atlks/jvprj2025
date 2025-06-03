//package orgx.msc;
//
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.function.Function;
//import java.util.Map;
//
//public class USvs {
//    public static String add1(Integer o) {
//        System.out.println("add1");
//        return "sss";
//    }
//
//    public static String add2(String o) {
//        System.out.println("add2");
//        return o;
//    }
//
//    // 使用 Function<?, ?> 兼容不同类型的方法
//    public static Map<FunType, Function<?, ?>> funMap = new ConcurrentHashMap<>();
//
//    static {
//        funMap.put(FunType.USER_add, USvs::add1);
//     //   funMap.put(FunType.USER_add, USvs::add2);
//    }
//
//    public static void main(String[] args) {
//        // 调用 add1(Integer)
//        Function<Integer, String> func1 = (Function<Integer, String>) funMap.get(FunType.USER_add);
//        System.out.println(func1.apply(10)); // ✅ 执行 add1(10)
//
//        // 调用 add2(String)
//        Function<String, String> func2 = (Function<String, String>) funMap.get(FunType.USER_add);
//        System.out.println(func2.apply("Hello")); // ✅ 执行 add2("Hello")
//    }
//}
