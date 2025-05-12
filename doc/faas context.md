

faas一般上下文有哪些，列个markdown表格


ChatGPT 说：
在 FaaS（Function as a Service）环境中，每次函数调用通常会带有一个上下文对象（context），用于提供与此次调用相关的元信息、环境变量、身份验证、资源限制等内容。

以下是一个常见的 FaaS 上下文字段汇总表（以 AWS Lambda、Azure Functions、Google Cloud Functions 等为参考）：

✅ FaaS 通用上下文字段一览
字段名	说明	示例
functionName	当前函数名称	"myFunction"
functionVersion	函数版本	"1"
memoryLimitInMB	为函数分配的最大内存（以 MB 为单位）	128
timeout	函数最大执行时间（以秒为单位）	30
requestId	当前请求唯一 ID，用于追踪日志等	"a1b2c3d4"
timestamp	调用发生时间戳	"2025-05-11T09:30:00Z"
invokedFunctionArn	被调用函数的 ARN（适用于 AWS）	"arn:aws:lambda:..."
logGroupName	记录日志的组名（平台相关）	"/aws/lambda/myFunction"
logStreamName	日志流标识	"2025/05/11/[$LATEST]abcdef123456"
identity	用户/服务调用者身份信息（如 IAM 用户或 OIDC 令牌）	{"user":"alice","roles":["admin"]}
clientContext	客户端传来的上下文信息（如移动 SDK、前端应用）	{"device":"iPhone","appVersion":"1.2"}
traceId	跟踪 ID（用于链路追踪/分布式系统观测）	"00-abcdef123456-..."
region	所在区域（云服务区域）	"us-west-2"
environment	环境变量（platform/env config）	{"MODE":"prod","DB":"xyz"}
customContext	用户自定义上下文数据（部分平台允许绑定自定义）	{"sessionId":"xyz-abc-123"}
eventSource	调用源（HTTP / S3 / Timer / Queue / PubSub 等）	"HTTP" / "CRON"
authToken	身份验证令牌（如 JWT/OAuth2）	"eyJhbGciOi..."