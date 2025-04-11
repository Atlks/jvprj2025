

这条 URL 是调用 Etherscan 的 Ethereum JSON-RPC Proxy API，用于根据区块号获取区块详细信息。
🔍 完整 URL 示例：

https://api.etherscan.io/api?module=proxy&action=eth_getBlockByNumber&tag=0x$HexNum&boolean=false&apikey=$apikey

✅ 各参数详细解释：
参数名	示例值	含义
module	proxy	表示使用 JSON-RPC Proxy API 模块，用于直接代理 Ethereum 节点的 JSON-RPC 接口。
action	eth_getBlockByNumber	表示要执行的动作是：根据区块号获取区块信息。相当于 JSON-RPC 中的 eth_getBlockByNumber。
tag	0x$HexNum	要查询的区块号，用 十六进制字符串 表示，前缀必须是 0x。例如：0x10FB78。
boolean	false	是否返回完整交易对象。
- true: 返回区块中每笔交易的完整对象。
- false: 仅返回交易哈希（较快）。
  apikey	你的APIKEY	你的 Etherscan API Key，用于身份验证和调用次数限制控制。


/**web3 函数
* 得到当前最新的区块号码
*  * 获取区块信息 根据区块号码

目前最新是web3.kt

php版本优点旧
phpprj/app/common

/LotteryHash28.php