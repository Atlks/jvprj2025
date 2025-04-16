
=本地console打印。然后同时拦截输出到log文件即可

public class ConsoleInterceptor {
public static void init() {
try {
// 日志文件输出流
FileOutputStream fileOut = new FileOutputStream("app.log", true);
PrintStream customOut = new PrintStream(new MultiOutputStream(System.out, fileOut));
PrintStream customErr = new PrintStream(new MultiOutputStream(System.err, fileOut));

🔹 1. 本地日志打印（简单）

在函数中使用标准输出：

System.out.println("User login success: userId=" + userId);
System.err.println("Login error: " + e.getMessage());

=AWS Lambda 中，这些输出会自动进入 CloudWatch Logs，无须配置。


trans console log to file log



