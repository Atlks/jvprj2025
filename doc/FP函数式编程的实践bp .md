


FP函数式编程的实践bp .md

# FP 在 REST 接口中的应用模式
Line 40: #     函数式编程（FP）中给 静态函数添加 AOP 日志
Line 53: # fp ioc
Line 75: # fp orm


-------------------------

# FP 在 REST 接口中的应用模式
✅ 1. 使用纯函数处理请求
每个 REST 接口可以是一个接收参数并返回响应数据的纯函数：

java
复制
编辑
public static Response getUser(Request req) {
String userId = req.getParam("id");
return Response.ok(UserService.findUser(userId));
}


✅ 2. 组合中间件：函数管道化
可以把请求处理链拆成多个函数组合：

java
复制
编辑



✅ 如果你是自己写的微框架或基于反射路由
你也可以这样定义映射关系：

java
复制
编辑
Map<String, Function<Request, Response>> routes = Map.of(
"/hello", MyController::hello
);


#     函数式编程（FP）中给 静态函数添加 AOP 日志

在函数式编程（FP）中给 静态函数添加 AOP 日志（在调用每个函数时记录日志）可以通过以下方式实现：

✅ 方法一：用高阶函数包装静态方法（最推荐的方式）
这是最典型的“函数式 AOP”，我们用一个装饰器（高阶函数）来给静态函数加日志。


🧠 总结
方案	适用于	能拦截静态方法	推荐程度
高阶函数装饰器	Java、函数式代码	✅ 支持	⭐⭐⭐⭐⭐


# fp ioc

那么fp如何实现ioc



函数式编程（FP）中的 IoC（控制反转）不像面向对象（OOP）那样依赖类和容器（如 Spring）。FP 的 IoC 更偏向于**显式依赖注入（Explicit Dependency Injection）和函数组合（Function Composition）**的方式来解耦组件、控制依赖注入和生命周期。




✅ 函数式风格的 IoC 核心思想：
概念	FP 的替代方式
控制反转（IoC）	函数参数中传入依赖
依赖注入容器	顶层函数组装依赖并传入各组件

接口与实现解耦	用函数签名 + 高阶函数


✅ FP IoC 的优势：
依赖显式化：构造时传入依赖，不依赖全局容器

# fp orm

fp如何orm



函数式编程（FP）与传统 ORM（对象关系映射）在哲学上存在差异，因为：

ORM 通常依赖 类的状态、懒加载、实体生命周期管理（如 Hibernate），这与 FP 强调的无副作用、不可变数据结构是冲突的。

✅ 函数式 ORM 的关键特征：
功能	FP 的替代思路
实体类	不可变的数据结构（如 record, DTO）
ORM 查询（JPQL）	纯函数 + SQL 构造（如 jOOQ，Exposed，Doobie）
DAO 生命周期	显式函数调用
延迟加载、脏检查	显式数据流，没有自动代理和魔法
✅ 示例 1：Java 函数式 ORM（使用 jOOQ）
jOOQ 是 Java 中支持函数式风格 SQL 构造和执行的工具，它比 Hibernate 更接近 FP：

✅ 示例 2：Kotlin + Exposed（类 FP）
Kotlin 的 Exposed 是一个类型安全 SQL DSL，支持函数式风格：
 