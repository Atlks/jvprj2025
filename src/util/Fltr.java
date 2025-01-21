package util;

import java.util.*;
import java.util.function.Predicate;

public class Fltr {

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

    public static List<Object> fltr2501(
            List<Object> list,
            List<Predicate<Object>> whereFuns) {
        List<Object> rowsResult = new ArrayList<>();
        //== prm null safe chk
        if (whereFuns == null || list == null)
            return rowsResult;

        for (Object row : list) {
            for (Predicate<Object> prd : whereFuns) {
                try {
                    if (prd.test(row)) {
                        rowsResult.add(row);
                    }
                } catch (Exception e) {
                    print(e);
                    logErr2024(e, "whereFun", "errlog", null);
                }
            }

        }
        return rowsResult;
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
