
ts dart ktlin
Go（没有 null，但 interface{} 和指针可能是 nil）
Rust
java @notnull


d2024, [13/3/2025 下午 11:01]
null，并且具有空安全（null safety）特性的编程语言：

强空安全，默认变量不会是 null
这些语言默认所有变量都不能为空，除非显式标记为可空（如 ? 标记）。

Kotlin

默认变量不能是 null，需要显式声明可空类型 String? 才允许 null

d2024, [13/3/2025 下午 11:01]
Dart（Null Safety 版本）

自 Dart 2.12 起默认开启 Null Safety，变量默认不能为 null，需要 ? 允许可空值。

d2024, [13/3/2025 下午 11:02]
TypeScript（严格模式下）

在 strictNullChecks 选项开启的情况下，变量默认不能是 null，必须显式声明 string | null 才能允许 null。

d2024, [13/3/2025 下午 11:03]
Go（没有 null，但 interface{} 和指针可能是 nil）

d2024, [13/3/2025 下午 11:03]
Java（默认可能为 null，但可用 @NotNull 或 Optional

d2024, [13/3/2025 下午 11:03]
你想确保默认情况下变量不会是 null，推荐使用 Kotlin、Dart（Null Safety）、Rust、Swift 或 TypeScript（strict 模式）。