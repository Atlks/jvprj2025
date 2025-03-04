
=ide null chk

5. 代码检查工具
   使用静态代码分析工具，如：

IntelliJ IDEA（可检测潜在 null 变量）

=string use notblank  
@NotBlank 既包含对 null 的检查，也包含对空字符串（""）和仅包含空白字符（如 " "）的
=use check exc

only sucuceee process. other is all ex throw 

=all vaf not null,,defalt not null

=nullsafe lan     obj?.mth()

=@null   allMethod   parm n retval

=Option retobj

=defalt val ,

✅ 1.2 使用 Objects.requireNonNull()
在方法参数、返回值或关键变量初始化时，显式检查 null：

✅ 2.2 使用 Map.getOrDefault()
对于 Map，如果键不存在，get() 方法可能返回 null，使用 getOrDefault() 避免 NullPointerException：

✅ 2.3 Stream API 处理可能为 null 的集合
在 Java 8+，使用 Optional.ofNullable() 或 Stream.ofNullable() 处理可能为 null 的集合：


✅ 3.1 统一 null 检查方法
可以封装 null 安全检查工具类：

java
复制
编辑
public class NullUtil {


✅ 4.1 让 null 触发异常
如果某个变量 null 会导致逻辑错误，尽早抛出异常，而不是让它在后续代码中崩溃：

java
复制
编辑
if (user == null) {
throw new IllegalStateException("用户不能为空");
}

✅ 4.2 使用 try-catch 捕获 NullPointerException（不推荐）


总结
方法	适用场景	推荐程度
@NotNull / @NonNull	参数和返回值检查	⭐⭐⭐⭐⭐
Objects.requireNonNull()	关键变量初始化	⭐⭐⭐⭐⭐
Optional<T>	可能返回 null 的方法	⭐⭐⭐⭐⭐
getOrDefault()	Map 查找	⭐⭐⭐⭐
Collections.emptyList()	避免 null 赋值	⭐⭐⭐⭐
try-catch 捕获 NullPointerException	第三方库调用	⭐⭐（不推荐）
最佳实践：
✅ 尽量避免 null（使用默认值、Optional、初始化）
✅ 在方法入口检查 null（@NotNull / Objects.requireNonNull()）
✅ 静态分析工具（IntelliJ、SpotBugs）

这样可以大大减少 NullPointerException 发生的概率，提高代码健壮性。