
🔹 1. 账户相关（Accounts）

获取地址的余额、交易、内部交易、代币持仓等。
功能	action	示例
获取地址 ETH 余额	balance	module=account&action=balance&address=...
获取地址的交易记录	txlist	module=account&action=txlist&address=...



2. 区块相关（Blocks）

按时间戳查找区块号、查询区块奖励。
功能	action	示例
根据时间戳查区块号	getblocknobytime	module=block&action=getblocknobytime&timestamp=...



🔹 3. 交易相关（Transactions）

获取交易状态和明细。
功能	action	示例
根据交易哈希查状态	getstatus	module=transaction&action=getstatus&txhash=...
根据交易哈希查receipt	gettxreceiptstatus	module=transaction&action=gettxreceiptstatus&txhash=...



🔹 4. 代币相关（Tokens）

获取代币信息、余额、持有人等。
功能	action	示例
查询代币总供应量	tokensupply	module=stats&action=tokensupply&contractaddress=...
查询代币余额	tokenbalance	module=account&action=tokenbalance&contractaddress=...&address=...



🔹 5. 合约相关（Contracts）

查源码、验证状态、ABI 等。
功能	action	示例
获取合约源码	getsourcecode	module=contract&action=getsourcecode&address=...
获取 ABI	getabi	module=contract&action=getabi&address=...
检查是否是合约地址	getsourcecode 是否非空即可判断



🔹 6. 统计与市场数据（Stats）
功能	action	示例
当前 ETH 价格	ethprice	module=stats&action=ethprice
当前 ETH 供应	ethsupply	module=stats&action=ethsupply
当前 gas price	gasoracle	module=gastracker&action=gasoracle



🔹 7. 代理 JSON-RPC（Proxy）

等同于以太坊节点的 RPC 调用，支持很多 eth_* 方法。
功能	方法	示例
获取区块	eth_getBlockByNumber	module=proxy&action=eth_getBlockByNumber&tag=0x...
获取交易详情	eth_getTransactionByHash	module=proxy&action=eth_getTransactionByHash&txhash=...
获取合约代码	eth_getCode	module=proxy&action=eth_getCode&address=...
获取余额	eth_getBalance	module=proxy&action=eth_getBalance&address=...
调用智能合约	eth_call	module=proxy&action=eth_call