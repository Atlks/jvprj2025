
更健壮

接 return null; 可能更简单，但更容易出 bug。


❌ 但完全去掉 null 也有缺点

    某些业务逻辑仍然需要表示 "无值"
        数据库查询、API 响应、用户输入等情况经常需要表示**"数据不存在"**。
        Rust 采用 Option<T>，Kotlin 采用 ? 可空类型，但都增加了额外的封装成本


📌 结论

✔ 现代编程语言更倾向于“非空默认”，并用更安全的方法替代 null。
✔ 但完全去掉 null 可能增加代码复杂度，必须用 Option<T> 或 Optional<T> 代替。
✔ 最好的做法是"让 null 变得显式"，只在必要时使用，并让编译器检查可能的 null 访问。


✅ 方式 3：启用严格的 null 检查

TypeScript、Java、C# 等语言允许你启用严格的 null 检查，这样代码更安全：

    TypeScript：启用 strictNullChecks
    Java：用 @NotNull / @Nullable 让 IDE 检查 null
    C#：启用 #nullable enable

✅ 方式 4：使用 assert 或 fail-fast 机制

如果 null 是意外情况，你可以用断言（assert）或者 fail-fast 方式，在出问题时立刻终止程序：

可空类型（Kotlin、Swift、Dart）	Kotlin, Swift, Dart	编译期强制检查	默认变量非空，避免 null