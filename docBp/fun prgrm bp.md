

ChatGPT 说：
函数式接口（Functional Interface）是 Java 8 引入的一种接口类型，它是包含单一抽象方法的接口，通常用于支持 Lambda 表达式。函数式接口可以简化代码，特别是在处理回调、事件处理、集合操作等场景中。Java 通过这种接口来支持函数式编程风格，减少冗余代码，提高代码的可读性和可维护性。

函数式接口的特点：
单一抽象方法：

函数式接口必须只包含一个抽象方法。如果接口中有多个抽象方法，它就不是函数式接口。
但接口可以包含多个 default 或 static 方法，这些方法不会影响它是否是函数式接口。
@FunctionalInterface 注解：

Java 8 引入了 @FunctionalInterface 注解，它用来明确标示一个接口是函数式接口。
这个注解并不是必需的，但它帮助编译器检查该接口是否满足函数式接口的要求。如果一个接口被标注为 @FunctionalInterface，并且它有多个抽象方法，编译器会报错。
可以使用 Lambda 表达式或方法引用：

函数式接口的存在使得 Java 8 的 Lambda 表达式 和 方法引用 成为可能。
由于函数式接口只有一个抽象方法，Lambda 表达式可以自动与接口的单个方法签名匹配。


上面的代码中，MyFunctionalInterface 是一个函数式接口，它定义了一个抽象方法 myMethod() 和一个默认方法 defaultMethod()。然后，我们使用 Lambda 表达式 ( ) -> System.out.println("Executing myMethod") 实现了这个接口，简化了代码。


java.util.function 包提供了大量的内置函数式接口，这些接口常用于 Java 8 的 Stream API、Lambda 表达式和方法引用等。以下是 java.util.function 包中一些常见的函数式接口及其说明：

1. Predicate<T>
   描述：表示一个接受单个参数并返回 boolean 的函数。
   方法：
   boolean ztest(T t)
   用途：通常用于判断某个条件是否成立。

2. Function<T, R>
   描述：表示一个接受一个参数并返回一个结果的函数。
   方法：
   R apply(T t)
   用途：常用于将输入转换为输出。
   示例：
   java
   复制
   编辑
   Function<Integer, String> intToString = x -> String.valueOf(x);
   System.out.println(intToString.apply(123));  // "123"

3. Consumer<T>
   描述：表示一个接受单个参数并没有返回值的操作。
   方法：
   void accept(T t)
   用途：用于执行某些操作，而不关心返回值。

4. Supplier<T>
   描述：表示没有输入参数但返回一个结果的函数。
   方法：
   T get()
   用途：常用于生成数据或对象，特别是在懒加载时。

UnaryOperator<T>
描述：表示接受一个参数并返回同一类型的结果的函数。继承自 Function<T, T>。
方法：
T apply(T t)
用途：常用于对对象进行变换。


BinaryOperator<T>
描述：表示接受两个相同类型参数并返回同一类型结果的函数。继承自 BiFunction<T, T, T>。
方法：
T apply(T t1, T t2)
用途：常用于两个对象的合并或计算。



7. BiPredicate<T, U>
   描述：表示一个接受两个参数并返回 boolean 的函数。
   方法：
   boolean ztest(T t, U u)
   用途：常用于对两个条件的判断


8. BiFunction<T, U, R>
   描述：表示一个接受两个参数并返回一个结果的函数。
   方法：
   R apply(T t, U u)
   用途：常用于合并两个参数并返回结果。
   java.util.function 包提供了很多常用的函数式接口，能够简化 Java 代码，支持更简洁的函数式编程方式。通过这些接口，可以方便地进行函数式编程、集合处理、Lambda 表达式以及方法引用等操作。

在 JDK 9 及以上版本中，java.util.function 包新增了一些有用的函数式接口，以下是 JDK 9 及以上版本新增的函数式接口：

1. Flow.Publisher<T>
   描述：这是 java.util.concurrent.Flow 类的接口之一，属于响应式流的概念，表示发布者（Producer），能够向订阅者发送数据流。
   方法：
   void subscribe(Flow.Subscriber<? super T> subscriber)
   用途：用于在响应式流中向订阅者提供数据。


2. Flow.Subscriber<T>
   描述：用于表示订阅者（Consumer），接收由 Publisher 提供的数据流。
   方法：
   void onSubscribe(Flow.Subscription subscription)
   void onNext(T item)
   void onError(Throwable throwable)
   void onComplete()
   用途：用于接收响应式流中的数据。

4. Optional 类中的 ifPresentOrElse 方法 (JDK 9)
   描述：这是 Optional 类的一个方法，允许在值存在时执行一个动作，否则执行另一个动作。
   方法：
   void ifPresentOrElse(Consumer<? super T> action, Runnable emptyAction)
   用途：在值存在时执行一个操作，否则执行另一个操作（例如打印默认值等）。

Optional<String> opt = Optional.of("Hello");
opt.ifPresentOrElse(
value -> System.out.println("Found: " + value),
() -> System.out.println("Not found")
);



总结
在 JDK 9 及以上版本，增加了响应式流 (Flow 接口)，通过 Publisher、Subscriber 和 Subscription 等接口，可以支持异步和响应式编程。此外，还引入了一些实用的函数式接口方法，如 Optional 的 ifPresentOrElse，以及 Predicate 的 not 方法，这些增强使得函数式编程更加方便和强大。


Publisher：负责提供数据流。
Subscriber：负责接收数据流。
Subscription：管理发布者与订阅者之间的订阅关系，支持请求数据和取消订阅。
这种模式适用于需要异步、响应式编程的场景，特别是当你需要处理数据流（例如事件流、消息流等）时，Publisher 和 Subscriber 提供了一种标准化的方式来实现它们之间的交互。


=frmwk 提供的fun itfs


Spring 提供了许多基于函数式编程的接口，尤其是在 spring-core 和 spring-webflux 中。

常见函数式接口：
org.springframework.beans.factory.ObjectProvider<T>
用于延迟获取 Bean。
org.springframework.web.reactive.function.client.ExchangeFilterFunction
org.springframework.transaction.support.TransactionCallback<T>
用于事务管理：


2. Guava (com.google.common.base)
   Guava 是 Google 提供的 Java 扩展库，提供了大量函数式接口。

4. Reactor (Project Reactor)
   Reactor 是 Spring WebFlux 采用的响应式编程库，它的 reactor.core.publisher 包提供了大量函数式接口。

常见函数式接口：
reactor.function.Function3<T1, T2, T3, R>
接受 3 个参数的函数：