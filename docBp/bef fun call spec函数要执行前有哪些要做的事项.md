
bef fun call spec函数要执行前有哪些要做的事项.md


# db conn,,cfg ,tx
# prm valid ,to dto
# auth

# 必备的数据库 配置 事务
注入数据库链接
读取配置注入
开启事务
# 参数校验（类型、格式、必填等）

✅ 上下文准备类


注入请求上下文（Request Context），如 IP、UA、Token 等

事务准备（如需事务控制，准备好事务上下文）

语言/区域信息注入（多语言支持）

时间戳注入（例如记录请求进入时间



# ✅ 安全性类 权限
权限检查，注入当前用户名
防重复提交检查（幂等性 Token）

防止 CSRF/XSS/SQL 注入准备

对敏感操作进行日志记录准备（审计日志）

权限细粒度检查（角色、资源级别）



✅ 性能/并发类
缓存读取或准备（如权限缓存、配置缓存）

锁控制（如防止并发执行某些函数）



✅ 监控/追踪类  日志
链路追踪 ID（TraceId/SpanId）注入
开启日志拦截

性能监控计时器启动

埋点准备（用于接口耗时、错误率监控）

✅ 其他可选：
环境初始化检查（服务是否可用、依赖资源状态）

降级/熔断判断（某些函数是否已被暂停）

灰度/AB 测试注入标记