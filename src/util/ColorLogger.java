package util;

public class ColorLogger {
    // ANSI 颜色代码
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";

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
