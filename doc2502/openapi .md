OpenAPI 规范（以前称为 Swagger）描述了 API 的各个方面。 OpenAPI 规范描述 API 的终结点、参数和响应。 OpenAPI 规范以 YAML 或 JSON 编写，由工具用来生成文档、测试用例和客户端库。 通过具有 OpenAPI 规范，API 生成器可以确保其 API 准确描述、更易于访问且更易于跨各种应用程序和服务集成。

下面是应考虑为 API 使用 OpenAPI 规范的原因：

    以标准化方式记录 API。 采用一致且可读格式的文档 API 规范。


模拟 API。 根据 API 规范创建模拟服务器，这有助于在尚未实现实际 API 的早期阶段进行开发。
改进协作。 提供不同的团队（前端、后端、QA 等）清楚地了解 API 的功能和限制，并帮助新团队成员快速加快速度。
简化测试和验证。 自动验证 API 请求和针对规范的响应，从而更轻松地识别差异。


..OpenAPI的历史

OpenAPI 的起源可以追溯到 2009 年，当时 Wordnik 公司的工程师 Tony Tam 创建了一个规范（当时称为 Swagger），用于描述 Wordnik 的在线字典 JSON API。

在接下来的几年里，Tony 对 Swagger 进行了几次迭代。然而，Swagger 2.0 规范的采用率增加，并触发了解析规范的工具的创建。

2015年，SmartBear 收购了 Swagger。SmartBear 是当前拥有 Swagger的公司。Swagger 规范被重命名为“OpenAPI”，以反映新的 OpenAPI 倡议。这就是为什么“Swagger”与“OpenAPI”标准混淆的原因。

当时，一群公司认识到，行业需要一种供应商中立和标准化的方式来描述API。行业需要为行业提供“最佳实践”并监督 OpenAPI 的更新。

这些公司在 Linux 基金会下建立了 OpenAPI 倡议，作为一个治理程序，维护 OpenAPI标准并提供实际指导。成立 OpenAPI 倡议的创始公司包括 CapitalOne、PayPal、SmartBear、IBM、3Scale、Google、Apigee、Intuit、Microsoft 和 Restlet。从那之后，参与该倡议的公司数量已大幅增长。

技术指导委员会现在管理 OpenAPI，并根据社区反馈继续发布新版本。

=online editr

https://editor.swagger.io/