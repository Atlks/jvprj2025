


2. 动态代理
   特点

   在运行时动态生成代理类，不需要手动编写代理类。
   代理类在程序运行时动态绑定到目标对象，提高代码复用性。
   主要有两种方式：
   JDK 动态代理（基于 java.lang.reflect.Proxy，只能代理接口）
   CGLIB / ByteBuddy 动态代理（基于子类继承，可以代理普通类）


3. 植入模式属于哪种代理？
   植入模式（Weaving）

AOP 采用 “植入”（Weaving） 方式将增强逻辑注入到目标方法中。主要有三种模式：

    编译期植入（Compile-Time Weaving）：类似静态代理，在编译时生成代理类，例如 AspectJ 的 ajc 编译器。
    类加载期植入（Load-Time Weaving）：在类加载时修改字节码，例如 java.lang.instrument API、ASM、Javassist。
    运行期植入（Runtime Weaving）：类似动态代理，在运行时修改字节码，例如 Spring AOP（基于 JDK 动态代理 / CGLIB）。

植入方式	代理类型	示例
编译期植入	静态代理	AspectJ（ajc 编译器）
类加载期植入	动态代理	java.lang.instrument、Javassist、ASM
运行期植入	动态代理	Spring AOP（JDK / CGLIB）