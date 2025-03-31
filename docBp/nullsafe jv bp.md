


总结
类型	语法	例子
静态方法引用	类名::静态方法	Math::abs
实例方法引用	实例::方法	obj::toString
特定类型的实例方法引用	类名::方法	String::toLowerCase
构造方法引用	类名::new	ArrayList::new

方法引用本质上是 Lambda 表达式的简化形式，在 Java 8 及以上版本广泛应用于 Stream API 和函数式接口中。


使用  call(obj::mthdName,prm)

可以null chk n aop log  ...