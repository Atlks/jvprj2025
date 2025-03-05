
🛠 Java 实现
Java 没有 call_user_func()，但可以用 反射 (Reflection) 或 Lambda 实现类似的功能：

PHP (call_user_func())	Java 对应方式	适用场景
call_user_func("hello", "John")	Method.invoke()	反射调用（适用于 字符串方法名）
call_user_func([$obj, "method"], "John")	BiConsumer<MyClass, String>	调用 对象方法
call_user_func(["MyClass", "staticMethod"], "arg1")	Consumer<String> or Method.invoke()	调用 静态方法
call_user_func($closure, "John")	Consumer<String> (Lambda)	调用 匿名函数 / 闭包
🔥 推荐：

如果方法名是字符串（动态调用）➡ 用反射 Method.invoke()
如果是 Lambda / 方法引用 ➡ 用 Consumer<T>，效率更高
这样，你可以在 Java 里实现 PHP call_user_func() 的功能 🎯