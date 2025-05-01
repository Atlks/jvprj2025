

obie stet标准对比呢
ChatGPT 说：
OBIE（Open Banking Implementation Entity）、Berlin Group（NextGenPSD2）和 STET（法国主导的 PSD2 API 标准）是欧洲三大主流开放银行标准。下面是 OBIE 与 STET 的详细对比分析：
OBIE（Open Banking Implementation Entity）、Berlin Group（NextGenPSD2）和 STET（法国主导的 PSD2 API 标准）是欧洲三大主流开放银行标准。下面是 OBIE 与 STET 的详细对比分析：


项目 / 标准	OBIE（英国）	STET（法国）
地域来源	英国	法国为主，多个欧洲国家采纳
法规基础	CMA（英国竞争与市场管理局）+ PSD2	PSD2（欧盟第二支付服务指令）
目标覆盖	强制性标准（主要针对英国 CMA9 银行）	推荐标准（法国及少量银行采用）
支持的服务	AISP、PISP、CBPII 全部支持	AISP、PISP，CBPII 支持有限
安全框架	OAuth 2.0 + FAPI（金融级安全）	OAuth 2.0，FAPI 支持不完整
API 风格	RESTful + JSON	RESTful + JSON
规范详细程度	高（字段、错误码、授权流程均细化）	中等偏上（流程规范清晰但字段定义留白多）
标准成熟度	高（广泛实施，官方工具链完善）	中（主要在法国市场，有实施但资源少）



🔍 技术对比
项目	OBIE	STET
授权方式	Redirect、Decoupled、App-to-App	Redirect、Decoupled
授权协议	OAuth 2.0 + OpenID Connect + FAPI	OAuth 2.0（OpenID Connect 可选）
客户端认证	MTLS（双向 TLS）+ 软件声明	MTLS（推荐）
错误码规范	有详细分类（标准错误结构）	错误结构较简单（错误码少，描述可变）
事件通知	支持基于 Webhook 的事件通知	支持但不统一
批量支付	支持	支持
国际化与多币种支持	针对英镑（GBP）	更偏向欧元区（EUR），多币种支持较弱



🧑‍💻 实施体验对比
方面	OBIE	STET
文档完整性	完善，有开发者门户和测试工具	文档中等，英文资源有限
开发支持	官方沙盒、SDK、兼容工具链	依赖银行个别提供沙盒
实施一致性	高，英国银行统一标准	中，法国银行较统一，其他国家差异大


需求类型	推荐使用标准
英国业务、合规驱动强	✅ OBIE
法国本地市场或对接 STET 支持银行	✅ STET
希望统一接入多个欧洲国家	❗ Berlin Group 更优
对错误码、安全、流程一致性要求很高	✅ OBIE