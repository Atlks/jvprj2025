


在 ISO 20022 中，BankTransactionCode 是用来标准化交易类型的代码体系，用于精确描述一笔银行交易的业务语义。这个代码由两个部分组成：

🧩 结构：
plaintext
复制
编辑
BankTransactionCode = Domain + Family + SubFamily
Domain Code（4 位）：交易的高层类别，例如付款、转账、现金、证券等。

Family/SubFamily Code：细分交易类型。

这些组合形成了标准化代码，如 PMNT-ICDT-STDO 表示标准直接借记付款（SEPA direct debit）。

🗂️ 常见 BankTransactionCode 域和示例
Domain	含义	示例
adjst
PMNT	Payments（支付）	PMNT-ICDT-STDO（标准直接借记）
CARD	Card Transactions（银行卡）	CARD-CCRD-PURC（信用卡消费）
CASH	Cash Transactions（现金）	CASH-DEPT-ATMW（ATM取现）
INTR	Internal Transfers（内部转账）	INTR-FUND-INTR（账户间转账）
CHRG	Charges（费用）	CHRG-OTHR-MNTH（月费）
INTP	Interest（利息）	INTP-CRED-INTC（利息收入）
GOVT	Government（税费等）	GOVT-TAXS-PYMT（税款支付）
SECU	Securities（证券类）	SECU-BUY-EXEQ（证券买入执行）
SCVE	表示 Service（服务类交易）


从外部充值到 银行账户，交易记录的 BankTransactionCode   是什么

payment-rechg  (Received Credit Transfer_  External Standard Credit Transfer)
划转    InternalTransfers-exchg
充值  
投资收益  Investment-receip
下级充值之佣金  Service - Commission - rechgCms
推广人数奖金  Service-Commission-devlpSubsCntCms