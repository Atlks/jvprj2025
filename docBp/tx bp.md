
关于事务 的注解有哪些，，有哪个注解可以指示忽略事务，不要提交
ChatGPT said:

在 Java 中使用事务时，通常依赖于 Spring Framework 的注解来声明事务行为。以下是一些常用的事务相关注解：
🔹 常用事务注解
1. @Transactional

这是最核心的事务注解，由 Spring 提供，表示方法或类需要事务支持。

@Transactional(Transactional.TxType.NOT_SUPPORTED)  //忽略事务

jakarta.transaction  也有事务


jakarta.transaction 的事务注解
ChatGPT said:

jakarta.transaction（以前是 javax.transaction）是 Java EE / Jakarta EE 提供的标准事务 API。它的注解适用于 Jakarta EE 环境，比如在 JPA、EJB 或某些支持 Jakarta 的 Spring 配置中也可用。
🔹 jakarta.transaction 的核心事务注解
1. @Transactional（jakarta.transaction.Transactional）

这是 Jakarta 提供的标准事务注解，作用类似于 Spring 的 @Transactional。


🔸 Transactional.TxType 类型

这是 Jakarta 的事务传播方式设置（和 Spring 的传播行为类似，但更简洁）：
TxType	说明
REQUIRED	默认值，有事务就加入，没有就创建
REQUIRES_NEW	始终新建事务，挂起当前事务
MANDATORY	必须在事务中运行，否则抛异常
NEVER	必须在非事务环境中运行，有事务就抛异常 ❗
SUPPORTS	有事务就加入，没有也照常执行
NOT_SUPPORTED	挂起当前事务，非事务方式运行 ✅（常用于忽略事务）



✅ 想要“忽略事务”或“非事务执行”的写法：

import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;

@Transactional(value = TxType.NOT_SUPPORTED)
public void nonTransactionalMethod() {
// 不参与事务，不提交也不会回滚
}



package jakarta.transaction;

import jakarta.enterprise.util.Nonbinding;
import jakarta.interceptor.InterceptorBinding;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Inherited
@InterceptorBinding
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Transactional {
TxType value() default Transactional.TxType.REQUIRED;

    @Nonbinding
    Class[] rollbackOn() default {};

    @Nonbinding
    Class[] dontRollbackOn() default {};

    public static enum TxType {
        REQUIRED,
        REQUIRES_NEW,
        MANDATORY,
        SUPPORTS,
        NOT_SUPPORTED,
        NEVER;