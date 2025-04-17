


Spring Cloud Function 是最推荐的做法。 要如何写
ChatGPT said:

使用 Spring Cloud Function 编写 Serverless 应用，能让你“一套代码跑多平台”，包括：

    ✅ 本地开发调试方便（像普通 Spring Boot 一样）

    ✅ 一键部署到 AWS Lambda / 阿里云函数计算 / Azure Functions 等

    ✅ 避免平台绑死，关注业务逻辑，不关心平台差异



核心思想

你只需要写：

@Bean
public Function<String, String> hello() {
return name -> "Hello, " + name;
}

Spring Cloud Function 会自动适配为 AWS Lambda / HTTP API / 消息中间件等调用入口。