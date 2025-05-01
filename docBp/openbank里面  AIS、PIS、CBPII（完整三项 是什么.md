openbank里面  AIS、PIS、CBPII（完整三项 是什么


ChatGPT 说：
在 Open Banking（开放银行） 的语境下，AIS、PIS 和 CBPII 是三类受监管的第三方提供者（TPP, Third Party Providers），它们基于 PSD2（欧盟《第二支付服务指令》）定义，用于访问银行账户信息或发起支付服务：

✅ 1. AIS（Account Information Service）账户信息服务提供者
全称：Account Information Service Provider（AISP）

功能：

访问用户银行账户的交易明细、余额信息

汇总多个银行账户信息

作用：

提供财务概况视图、预算规划、信用分析等服务

权限限制：

只读访问权限，不能发起支付

✅ 2. PIS（Payment Initiation Service）支付发起服务提供者
全称：Payment Initiation Service Provider（PISP）

功能：

代表用户从其银行账户发起支付指令（例如转账）

作用：

替代传统卡支付（例如直接从银行账户扣款付款）

常用于电商收银、账单支付等场景

权限限制：

不能查看完整账户信息（除非也注册为 AISP）

✅ 3. CBPII（Card-Based Payment Instrument Issuer）基于卡的支付工具发行方
全称：Card-Based Payment Instrument Issuer

功能：

核实某银行卡是否有足够余额以完成某项交易

不直接发起支付，仅做资金可用性查询

常见场景：

当用户使用某个卡通道发起支付，CBPII 向银行验证“余额足够”

权限限制：

不能访问交易明细，也不能发起支付

🔄 三者区别总结：
类别	功能	是否能读取账户交易	是否能发起支付	是否能查余额可用性
AISP	账户信息读取	✅ 是	❌ 否	✅ 是
PISP	支付发起	❌ 否	✅ 是	可能间接需要余额信息
CBPII	卡类支付资金验证	❌ 否	❌ 否	✅ 是（仅验证可用