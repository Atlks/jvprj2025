

出局了jdk自带的，还有spring blokbok的anno
还有other frmwk


. Jakarta EE / Java EE（JAX-RS、JPA、CDI）
3. Hibernate Validator（Java Bean 校验）
4. 4. Jackson（JSON 处理）
5. 7. JUnit / TestNG（单元测试框架）

MapStruct（Java Bean 转换框架）
ChatGPT 说：
MapStruct 适用场景
MapStruct 主要用于 Java Bean 之间的转换，特别适用于 DTO（数据传输对象）与实体对象之间的转换，大幅减少手写转换代码，提高开发效率。


. 常见应用场景
（1）DTO 与 Entity 转换
在 Web 应用或微服务中，通常有 Entity（持久化层对象）和 DTO（数据传输对象）：

Entity：用于数据库操作，包含完整字段
DTO：用于传输数据，可能只有部分字段


3）字段名不同的映射
如果 DTO 和 Entity 的字段名不同，可以手动指定映射：

java
复制
编辑
@Mapper
public interface ProductMapper {
@Mapping(source = "productName", target = "name")
ProductDTO toDto(Product product);
}


动态映射需求（字段不固定，使用 Jackson / Gson 更合适）

=log 类注解
=tx 类注解
=ex 类注解
=远程调用类注解  重试 超时啦配置
= 安全控制： 类注解   @inject注入sam
=远程调用：

在远程方法调用前后进行数据序列化、反序列化、网络传输等操作。
实现远程方法调用超时、重试等策略。
简化分布式系统开发。

@Retry:
Resilience4j的注解，用于声明重试策略。
@TimeLimiter:
Resilience4j的注解，用于设置超时时间。
断路器模式
org.springframework.retry.annotation.CircuitBreaker 如何使用
@CircuitBreaker(maxAttempts = 3, openTimeout = 5000, resetTimeout = 10000)

@CircuitBreaker(name = "myService", fallbackMethod = "fallback")
public String remoteCall() {
// 远程调用逻辑
return "remote call success";
}

    public String fallback(Throwable t) {
        return "fallback";
    }