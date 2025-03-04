package util.log;


/**
 * 在 ANSI 颜色代码中，38;2;R;G;B 的含义是：
 *
 * 38 —— 代表 前景色（字体颜色）
 * 2 —— 代表 使用 24 位（TrueColor）RGB 颜色
 *
 *
 * 是的，可以使用 简短的 ANSI 颜色代码，例如 \u001B[33m（黄色），不过 ANSI 标准色 只有 8 种基本颜色 + 8 种高亮颜色，并不包含 橙色。
 */
public class ColorLogger {
    // ANSI 颜色代码
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";

    //亮红色 用于bizfun
    public static final String RED_bright = "\u001B[91m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String YELLOW_bright = "\u001B[93m";
    public static final String BLUE = "\u001B[34m";

    //橙色
    public static final String ORANGE = "\u001B[38;2;255;165;0m";
    //util.log.ColorLogger.colorStr
    public static String colorStr(String message, String color)
    {
return  color + message + RESET;
    }
    public static void printLn(String message, String color) {
        System.out.println(color + message + RESET);
    }

    public static void main(String[] args) {
        printLn("INFO: This is an info message.", GREEN);
        printLn("WARN: This is a warning message.", YELLOW);
        printLn("ERROR: This is an error message.", RED);
        printLn("ERROR: This is an error message.", BLUE);
    }
}
/**
 * public class AnsiColorTest {
 *     public static void main(String[] args) {
 *         System.out.println("\u001B[31mThis is Red\u001B[0m");
 *         System.out.println("\u001B[32mThis is Green\u001B[0m");
 *         System.out.println("\u001B[33mThis is Yellow (close to Orange)\u001B[0m");
 *         System.out.println("\u001B[34mThis is Blue\u001B[0m");
 *         System.out.println("\u001B[35mThis is Magenta\u001B[0m");
 *         System.out.println("\u001B[36mThis is Cyan\u001B[0m");
 *         System.out.println("\u001B[37mThis is White\u001B[0m");
 *         System.out.println("\u001B[90mThis is Bright Black (Gray)\u001B[0m");
 *         System.out.println("\u001B[91mThis is Bright Red\u001B[0m");
 *         System.out.println("\u001B[92mThis is Bright Green\u001B[0m");
 *         System.out.println("\u001B[93mThis is Bright Yellow\u001B[0m");
 *         System.out.println("\u001B[94mThis is Bright Blue\u001B[0m");
 *         System.out.println("\u001B[95mThis is Bright Magenta\u001B[0m");
 *         System.out.println("\u001B[96mThis is Bright Cyan\u001B[0m");
 *         System.out.println("\u001B[97mThis is Bright White\u001B[0m");
 *     }
 * }
 */