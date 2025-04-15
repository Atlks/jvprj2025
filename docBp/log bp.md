


🔹 1. 本地日志打印（简单）

在函数中使用标准输出：

System.out.println("User login success: userId=" + userId);
System.err.println("Login error: " + e.getMessage());

💡 在 AWS Lambda 中，这些输出会自动进入 CloudWatch Logs，无须配置。


trans console log to file log