





基本分层：Controller、Service、Repository（DAO）、Entity（Model）、Util
扩展分层：DTO/VO、Config、AOP/Interceptor、Cache、Messaging、Security


扩展的分层

    DTO / VO 层（数据传输层）
        DTO（Data Transfer Object）：用于 Service 和 Controller 之间的数据传输，避免直接暴露数据库实体。
        VO（View Object）：用于前端展示的数据格式。



Config 层（配置层）

    负责应用程序的各种配置，如数据库连接池、缓存、Spring 配置等。


Interceptor / AOP 层（拦截器、切面层）

    负责日志记录、权限控制、异常处理、请求参数校验等。


Cache 层（缓存层）

    负责 Redis、EhCache 之类的缓存管理，减少数据库查询压力。


Messaging / Event 层（消息队列层）

    处理事件驱动的架构，使用 Kafka、RabbitMQ 进行异步通信。


Security 层（安全层）

    负责用户认证（如 OAuth2、JWT）、权限管理（如 RBAC）