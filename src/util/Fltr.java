package util;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.List;import java.util.SortedMap;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class Fltr {
    // 定义类型别名
//  类型别名的功能，可以考虑将复杂类型封装到类中（参考之前的建议）。
 //   type MapList = List<SortedMap<String, Object>>;
    // 使用别名
//    public static MapList fltr2501a(MapList list, Predicate<SortedMap<String, Object>> whereFun) {
//        MapList result = new ArrayList<>();
//
//        for (SortedMap<String, Object> map : list) {
//            if (whereFun.test(map)) {
//                result.add(map);
//            }
//        }
//
//        return result;
//    }
    /**
     * //过滤数组，根据指定的条件whereFun
     *
     * @param list
     * @param whereFun
     * @return
     */
    public static List<SortedMap<String, Object>> fltr2501(
            List<SortedMap<String, Object>> list,
            Predicate<SortedMap<String, Object>> whereFun) {

        String methodName = new Object() {
        }.getClass().getEnclosingMethod().getName();
        printTimestamp(" fun " + methodName + "(arr,fltrFun)");
        dbgCls.printCallFunArgs(methodName, "someRows");
        List<SortedMap<String, Object>> rowsResult = new ArrayList<>();

        //== prm null safe chk
        if (whereFun == null || list == null)
            return rowsResult;


//foreach safe ext

        for (SortedMap<String, Object> row : list) {
            try {
                if (whereFun.test(row)) {
                    rowsResult.add(row);
                }
            } catch (Exception e) {
                print(e);
                logErr2024(e, "whereFun", "errlog", null);
            }
        }

        printRet(methodName, rowsResult.size());
        printTimestamp(" end fun " + methodName + "()");
        return rowsResult;
    }

    /**
     *
     * @param list1
     * @param expression  范例  uname == 'unm2' && pwd == 'pp'
      @param expression 范例： #this['uname'] == 'unm2' && #this['pwd'] == 'pp'
     * @return
     */
    public static List<SortedMap<String, Object>> filterWithSpEL(List<SortedMap<String, Object>> list1,String expression) {
        // 创建 SpEL 解析器
        ExpressionParser parser = new SpelExpressionParser();

        // 定义过滤条件：uname 为 "unm2" 且 pwd 为 "pp"
        //  String expression =;

        // 遍历并过滤集合
        return list1.stream()
                .filter(map -> {
                    StandardEvaluationContext context = new StandardEvaluationContext();
                    context.setRootObject(map); // 设置当前 Map 为上下文的根对象
                    try {
                        return Boolean.TRUE.equals(parser.parseExpression(expression).getValue(context, Boolean.class));
                    } catch (Exception e) {
                        System.err.println("Error evaluating expression: " + e.getMessage());
                        return false; // 如果表达式出错，则跳过该元素
                    }
                })
                .collect(Collectors.toList());
    }

    public static List<SortedMap<String, Object>> fltr2501(
            List<SortedMap<String, Object>> list,
            List<Predicate<SortedMap<String, Object>>> whereFuns) {
        List<SortedMap<String, Object>> rowsResult = new ArrayList<>();
        //== prm null safe chk
        if (whereFuns == null || list == null)
            return rowsResult;
//deduli
        HashSet<String> ids_deduli = new HashSet<>();

        for (SortedMap<String, Object> row : list) {
            String id = (String) row.get("id");
            if (ids_deduli.contains(id))
                continue;

            boolean allFltrPass = prdctTestAll(whereFuns, row);

            if (allFltrPass) {
                rowsResult.add(row);
                ids_deduli.add(id);
            }


        }
        return rowsResult;
    }

    private static boolean prdctTestAll(List<Predicate<SortedMap<String, Object>>> whereFuns, SortedMap<String, Object> row) {
        boolean allFltrPass = true;
        for (Predicate<SortedMap<String, Object>> prd : whereFuns) {
            try {
//todo if ex ,dflt is true
                if (!prd.test(row)) {
                    allFltrPass = false;

                }
            } catch (Exception e) {
                print(e);
                logErr2024(e, "whereFun", "errlog", null);
            }
        }
        return allFltrPass;

    }

    private static void printTimestamp(String message) {
        System.out.println(message + " [" + new Date() + "]");
    }

    private static void print(Object e) {
        System.err.println(e);
    }

    private static void logErr2024(Exception e, String function, String logType, Object context) {
        // Error logging implementation
        System.err.println("Error in function: " + function + " | LogType: " + logType);
        e.printStackTrace();
    }

    private static void printRet(String methodName, int resultCount) {
        System.out.println("Return from " + methodName + " with result count: " + resultCount);
    }

    static class dbgCls {
        public static void printCallFunArgs(String methodName, String... args) {
            System.out.println("Function called: " + methodName + " with args: " + Arrays.toString(args));
        }
    }

}

