

面向注解编程（Annotation-Oriented Programming, AOP） 近年来越来越流行，尤其在 Java 生态中。注解的主要作用是声明式编程，即让开发者通过简单的注解来配置和控制代码逻辑，而不需要手写大量样板代码。


为什么面向注解编程会流行？

    简化代码，提高可读性：
        通过注解替代 XML 配置，避免繁琐的手写代码。
        让业务逻辑更加清晰，不需要关注底层实现。

    与 AOP（面向切面编程）结合：
        注解常用于 AOP，例如 @Transactional 可以在方法执行前后自动管理事务，而不需要手动编写事务管理逻辑。


5. 日志与监控

Spring Boot 的 @Slf4j（Lombok 提供）

✅ 自动化和减少样板代码：Lombok 的 @Data、Spring 的 @RestController 都让代码更简洁。