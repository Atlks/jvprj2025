
从java9到java23，有哪些重要的新特性

总结
从 Java 9 到 Java 23，Java 在模块化系统、虚拟线程、模式匹配、垃圾回收和函数式编程方面不断发展，使其更现代、高效和易用。特别是虚拟线程 (Loom) 的引入，将进一步巩固 Java 在高并发领域的优势。

Java 19 (2022)
虚拟线程 (Project Loom, 预览):

更轻量的线程实现，适合高并发。
java
Copy
Edit
Thread.startVirtualThread(() -> { ... });
Structured Concurrency (预览):

更易管理并发任务。



Java 18 (2022)
Simple Web Server:

一个内置的简单 HTTP 文件服务器。


Java 16 (2021)
Records 正式发布:

简化数据类定义。
支持 instanceof 模式匹配:

避免显式强制类型转换。
java
Copy
Edit
if (obj instanceof String s) {
System.out.println(s.toUpperCase());
}Java 16 (2021)
Records 正式发布:

简化数据类定义。
支持 instanceof 模式匹配:

避免显式强制类型转换。
java
Copy
Edit
if (obj instanceof String s) {
System.out.println(s.toUpperCase());
}

Java 14 (2020)
Records (预览):

用于声明不可变数据类。
java
Copy
Edit
record Point(int x, int y) {}

Java 10 (2018)
局部变量类型推断 (var):

使用 var 代替显式类型声明。
java
Copy
Edit
var list = new ArrayList<String>();



Java 11 (2018 - LTS)
本地变量支持 Lambda 表达式 (var):

Lambda 参数中也可以使用 var。
java
Copy
Edit
(var x, var y) -> x + y;



从 Java 9 到 Java 23，各个版本引入了大量新特性，提升了开发效率、性能、安全性以及语言表达能力。以下是各版本的重要新特性概览：

Java 9 (2017)
模块化系统 (Project Jigsaw):

引入模块化系统 (java.module)，允许更好地组织代码和控制依赖。
增加 module-info.java 文件定义模块。
优化 JDK，支持更小的运行时环境。
JShell (交互式编程工具):

提供一个交互式 REPL 环境，适合快速测试代码。
改进的流 API:

新增方法：takeWhile, dropWhile, ofNullable。
提供更灵活的流操作。
多版本 JAR 文件:

支持为不同版本的 Java 定制 JAR 文件中的代码。
增强的垃圾回收 (GC):

引入 G1 GC 作为默认垃圾回收器。
Java 10 (2018)
局部变量类型推断 (var):

使用 var 代替显式类型声明。
java
Copy
Edit
var list = new ArrayList<String>();
应用容器感知:

JVM 能感知容器中的内存/CPU 限制，改进运行时性能。
性能优化:

并行完整垃圾回收。
改进了类数据共享（CDS）。
Java 11 (2018 - LTS)
本地变量支持 Lambda 表达式 (var):

Lambda 参数中也可以使用 var。
java
Copy
Edit
(var x, var y) -> x + y;
HttpClient API:

新的 java.net.http 包提供异步和同步的 HTTP/2 支持。
文本块处理:

改进了字符串 API，如 String.lines(), isBlank()。
运行时性能改进:

移除部分遗留功能（如 Java EE 和 CORBA 模块）。
无需 javac 执行:

直接运行 Java 源文件：
bash
Copy
Edit
java HelloWorld.java
Java 12 (2019)
Switch 表达式 (预览):

简化 switch，支持返回值。
java
Copy
Edit
int result = switch (day) {
case MONDAY, FRIDAY -> 6;
default -> 0;
};
G1 GC 改进:

更高效的内存回收机制。
Compact Number Formatting:

简化数值格式化。
java
Copy
Edit
NumberFormat formatter = NumberFormat.getCompactNumberInstance();
Java 13 (2019)
文本块 (Text Blocks, 预览):

支持多行字符串。
java
Copy
Edit
String text = """
Hello,
World!
""";