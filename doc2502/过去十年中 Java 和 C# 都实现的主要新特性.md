

过去十年中 Java 和 C# 都实现的主要新特性



2. 异步编程
   Java:
   引入 CompletableFuture（Java 8）和异步流（java.util.concurrent.Flow，Java 9）。
   C#:
   早在 C# 5 就有 async/await，但进一步增强（如异步流 IAsyncEnumerable，C# 8）。
   差异:
   C# 的异步支持更成熟，async/await 的语言集成体验优于 Java。

3. 记录类型 / 数据类

4. 模式匹配
   Java:
   从 Java 14 开始支持模式匹配（instanceof 增强），Java 17 的 switch 语句引入模式匹配。
   C#:
   从 C# 7 开始，全面支持模式匹配，包括 is 表达式、switch 表达式和递归模式。

5. 字符串插值
   Java:
   Java 15 引入文本块（Text Blocks）简化多行字符串，但没有直接的字符串插值。
   C#:
   早在 C# 6 引入字符串插值（$"{variable}"），并在 C# 11 支持原始字符串文本。
   差异:
   C# 提供了更直观和灵活的字符串插值，而 Java 仍使用 String.format。



更新差异
以下是 Java 和 C# 在新特性方向上的显著差异：

1. 模块化 vs 默认接口方法
   Java:
   强调模块化（Java 9）来管理代码的依赖关系和封装。
   C#:
   更注重默认接口方法（C# 8），允许接口定义默认实现，从而减少对抽象类的依赖
3. 顶级程序（Top-Level Statements）

6. 原始字符串文本
   C#:
   C# 11 支持多行原始字符串文本，简化 JSON 或 SQL 等嵌入代码。
   Java:
   虽支持文本块，但不支持复杂插值。


总结
共同点:
Java 和 C# 在过去十年的演进中，都致力于：

简化开发者体验（如模式匹配、字符串插值、异步流）。
提升性能和扩展性（如模块化、内存优化）。
支持现代开发模式（如记录类型、接口默认方法）。
差异点:
C# 更注重开发者效率，例如 async/await、记录类型的高级支持、原始字符串文本。
Java 更关注生态系统的稳定性和兼容性，例如模块化系统和持续的性能优化。