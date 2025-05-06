

在 OBIE（开放银行）标准的 Balance 模型中，InterimCleared 是一种 余额类型（BalanceType），它表示：


是的，InterimCleared 可以理解为“在途资金”，但是“已经清算完成，正在等待记账”的那一类在途资金**。这是一个非常贴切的理解。

✅ 中间结算余额 —— 已清算但尚未完全结账的余额。

📘 更具体解释：
InterimCleared 是相对于其他余额（如 InterimBooked 和 Expected）来说的；

它表示：

交易已经清算（Cleared）（比如资金确实到账或扣款已完成）；

但可能还**没有完全入账（Booked）**或结算周期未到；

可用于一些银行处理“浮动期间”的余额展示。

🧾 OBIE 中 BalanceType 的常见类型对比：
BalanceType	含义	是否可用	是否结算
InterimAvailable	临时可用余额	✅ 可用于支付	❌ 可能含未清算交易
InterimBooked	中间账面余额	✅ 更正式	✅ 已入账（Booked）
InterimCleared	中间清算余额	✅ 可用于支付	✅ 已清算但不一定入账
ClosingBooked	账户期末余额	✅	✅
OpeningBooked	账户期初余额	✅	✅
Expected	预期余额	❌ 不一定准确	❌
ForwardAvailable	未来可用余额	❌	❌

🧠 理解场景举例：
假设今天你收到一笔转账：

它已经从对方银行到账；

银行已经清算；

但你的银行系统还没有正式“记账入账”（比如等日终批处理）；

这时：

InterimCleared 会更新；

InterimBooked 可能还不会变；

ClosingBooked 也不会变；

所以 InterimCleared 是比 Booked 更实时、比 Available 更真实的余额。



InterimCleared 是指那些已完成清算但尚未正式入账的中间余额，介于“到账”与“记账”之间，通常用于更精确反映账户当前真实的资金状态。



1. 银行处理是分阶段的，不是实时全账务系统
   银行交易通常经历以下阶段：

阶段	描述	对应余额
发起阶段	用户提交转账或消费，尚未处理	不计入任何余额
授权阶段	银行系统“锁定”金额（如POS刷卡）	InterimAvailable 减少
清算阶段	银行间完成资金清算	InterimCleared 增加
记账阶段	银行正式在账本中记录这笔交易	InterimBooked、ClosingBooked 增加

一个“实时余额”如果只反映当前账本（Booked），就不会体现正在发生但尚未入账的资金变化。


2. 不同业务场景需要不同精度
   场景	所需余额类型	原因
   用户刷卡付款	InterimAvailable	只要看“目前还能花多少钱”
   银行后台对账	InterimBooked 或 ClosingBooked	必须保证已入账，不能含浮动



3. 跨银行/跨时区交易延迟
   举例：

你周五晚收到一笔跨行转账；

银行清算系统可能周一早上才“记账”；

用户 APP 想显示这笔钱到了，但银行账本（Booked）里还没有；

此时就需要用 InterimCleared 反映“已清算但未记账”。




✅ 总结
余额类型	用途
InterimAvailable	显示“现在最多还能花多少钱”，即使有些未清算
InterimCleared	显示“已经清算但未入账”的金额，风控/预览场景常用
InterimBooked / ClosingBooked	显示“银行正式记账后”的余额，做对账、报表用
Expected / ForwardAvailable	做预测分析时使用（不一定准确）



你说得没错 —— 如果只考虑“能不能花”和“正式入账”两个维度，InterimAvailable 和 InterimBooked 的确可以满足绝大多数用户场景。

aa

🟡 比喻一下你就懂了：
InterimAvailable：钱包里你以为你有的钱（可能含信用额度）；

InterimCleared：你爸爸已经打钱给你，但银行柜台还没数完；

InterimBooked：银行柜台点完了，才打到你账户里。



你收到一笔跨国电汇：

阶段	状态	余额字段
电汇发起	银行收到消息，还未清算	无任何变动
银行收到清算通知	明确这笔钱会到，但还没入账	InterimCleared 增加
银行入账	账本更新	InterimBooked、Available 增加