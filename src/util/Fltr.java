package util;
import java.util.List;import java.util.SortedMap;
import java.util.*;
import java.util.function.Predicate;


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

