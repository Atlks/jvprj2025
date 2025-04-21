



openbanking里面transcation的状态有哪些


ChatGPT 说：
在 OpenBanking 标准中，交易（Transaction）的“状态”字段（如 status 或 statusCode）取决于具体使用的 API 和版本，以及使用的标准（如 UK Open Banking、Berlin Group、OBIE 等）。不过，以下是一些常见的 Transaction Status（交易状态） 值，供参考：

一般常见的交易状态（Transaction Status）有：

状态代码	说明
Booked	已记账（交易已完成，并在账户中体现）
Pending	待处理（银行已接收但尚未处理或结算）
Cancelled	已取消（交易被用户或银行取消）
Rejected	被拒绝（由于某种原因，交易未被接受）
Information	信息状态（例如仅供查看，不一定是实际交易）