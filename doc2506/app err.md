

⚠️ 常见异常类型与处理建议
异常类型	原因示例	处理建议
客户端参数错误	缺少字段、格式错误	返回 400 Bad Request，附上清晰错误信息
身份认证失败	Token 过期或无效	返回 401 Unauthorized，建议重新登录
权限不足	用户未授权当前操作	返回 403 Forbidden，提示权限问题
数据不存在	查询的资源不存在	返回 404 Not Found
请求频率过高	接口被刷频或 DDoS	返回 429 Too Many Requests，附重试时间
服务内部错误	数据库崩溃、空指针等服务器异常	返回 500 Internal Server Error
服务不可用	依赖服务宕机、超时	返回 503 Service Unavailable，可附 Retry-After