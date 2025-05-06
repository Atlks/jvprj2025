

openbank obie是什么时候开始的
ChatGPT 说：
Open Banking OBIE（Open Banking Implementation Entity）是英国开放银行标准的实施机构，其正式运作始于：

📅 2016年9月
这是由英国金融行为监管局（FCA）推动，并由英国竞争与市场管理局（CMA）下令设立的。目标是推动银行业竞争，提升用户对数据的掌控权。

发展里程碑简要：
时间	事件
2016年9月	英国CMA发布指令成立 OBIE
2017年初	发布首个开放银行标准草案
2018年1月13日	英国9家大银行（CMA9）强制开放API（配合PSD2）
2018~2021年	OBIE 不断扩展标准，包括支付、账户信息、交易历史、余额等
2021年	OBIE 与英国Open Finance、智能数据计划逐渐接轨

OBIE 的标准体系叫做 Open Banking Standard（OBS），包含：

API 规范（账户、交易、支付等）

安全认证框架（OAuth2 + JWT + Mutual TLS）

数据模型（如 Accounts、Transactions、Balances 等）



🗂 OBIE 标准版本时间线与变化概览
版本	发布时间	关键变化/亮点
v1.0	2017年3月	初版标准草案，仅支持基本账户信息与支付发起。
未广泛采纳，仅供测试与反馈。
v2.0	2017年7月	完善账户访问（AISP）与支付发起（PISP）API。
定义 OAuth2 授权流程雏形。
v3.0	2017年10月	更稳定的账户信息和支付结构。正式引入 Consent 模型。
v3.1	2018年1月13日	🔥关键版本：CMA9 银行强制实施。
包含：Accounts、Balances、Transactions、Standing Orders、Direct Debits、Scheduled Payments 等。
v3.1.1	2018年3月	细节补充（如数据排序、过滤参数），修复一些兼容性问题。
v3.1.2	2018年9月	引入错误码标准，增强错误处理一致性。
v3.1.3	2019年4月	改进 Payment 流程，如多次支付（FutureDated）。
v3.1.4	2019年11月	添加 Statements 模块。引入对 Offers, Parties, Product 的支持。
v3.1.5	2020年7月	更严格的数据建模，增强一致性。API返回结构更精简。
v3.1.6	2020年10月	支持 File Payments，支持大宗支付（批量发薪）。
v3.1.7	2021年4月	更好支持外部机构（如保险、投资平台）的集成需求。
v3.1.8	2022年初	最后的官方版本之一，修复漏洞，增强稳定性。之后标准逐步交由 JROC 和 Smart Data 制定机构接管。