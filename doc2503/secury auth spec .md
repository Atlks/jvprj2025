


相关标准和规范：
=ICAO Doc 9303

ICAO Doc 9303：机读旅行证件标准，详细规定了MRZ的格式和BAC机制。
ISO/IEC 14443：非接触式智能卡标准，定义了与芯片通信的协议。
ICAO LDS：逻辑数据结构标准，定义了芯片中数据的存储格式
=jwt
=JSR 375：Security API

=JSR 250：Common Annotations   @RolesAllowed。

JSR 250 定义了一组通用的注解，@RolesAllowed。
其中，@RolesAllowed 注解用于指定允许访问特定方法或类的角色。
它提供了一种简单的方式，用于在 Java EE 应用程序中实现基于角色的访问控制。


=SAM secury access mode  


 
审计与日志：

安全模块需要记录操作日志，以便追踪安全事件。
日志内容包括但不限于用户活动、系统错误、访问控制失败等。

 
密钥管理规范：

强制执行严格的密钥生命周期管理，包括生成、存储、使用、传输和销毁密钥的过程。
密钥的存储通常要求加密，并使用硬件安全模块（HSM）来存储。
身份验证与访问控制：

要求模块支持多因素认证（如生物识别、密码、硬件令牌等）。
必须能实施强访问控制，确保只有授权用户或应用程序能够访问或操作敏感信息

 
SAM（Security Access Module）安全模块是为了保证系统安全而设计的模块，通常用于存储加密密钥、进行身份验证、提供数据加密等功能。在不同的领域和环境中，SAM安全模块的规范也有所不同，但一般来说，常见的SAM安全模块规范包括以下几方面：
