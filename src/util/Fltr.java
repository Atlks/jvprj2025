package util;
import java.util.*;
import java.util.function.Predicate;
public class Fltr {

    /**
     *   //过滤数组，根据指定的条件whereFun
     * @param rows
     * @param whereFun
     * @return
     */
    public static List<SortedMap<String, String>> fltr(
            List<SortedMap<String, String>> rows,
            Predicate<SortedMap<String, String>> whereFun) {

        String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
        printTimestamp(" fun " + methodName + "(arr,fltrFun)");
        dbgCls.printCallFunArgs(methodName, "someRows");

        List<SortedMap<String, String>> rowsResult = new ArrayList<>();

        for (SortedMap<String, String> row : rows) {
            try {
                if (whereFun == null || whereFun.test(row)) {
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
}
