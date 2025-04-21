

| **字段含义**               | **OpenBanking 定义**                             | **ISO 20022 定义**                                |
|----------------------------|-------------------------------------------------|--------------------------------------------------|
| **账户标识符**             | AccountId                                       | Id                                               |
| **账户类型**               | AccountType                                     | Type                                             |
| **账户货币**               | Currency                                        | Currency                                         |
| **总余额**                 | Balance                                         | Balance                                          |
| **可用余额**               | AvailableBalance                                | AvailableBalance                                 |
| **信用额度**               | CreditLimit                                     | CreditLimit                                      |
| **账户持有人**             | AccountHolder                                   | AccountHolder                                    |
| **账户号码**               | AccountNumber                                   | AccountNumber                                    |
| **国际银行账号 (IBAN)**    | IBAN                                            | IBAN                                             |
| **银行标识符码 (BIC)**     | BIC                                             | BIC                                              |
| **银行名称**               | BankName                                        | BankName                                         |
| **账户状态**               | AccountStatus                                   | AccountStatus                                    |
| **透支额度**               | OverdraftLimit                                  | OverdraftLimit                                   |
| **账户开户日期**           | AccountOpeningDate                              | OpeningDate                                      |
| **冻结余额**               | —                                               | FrozenBalance                                    |





交易实体 的对应表，包括 字段含义、OpenBanking 定义 和 ISO 20022 定义，便于对比两者在交易数据表示上的差异。

交易对应表
markdown
复制
编辑
| **字段含义**               | **OpenBanking 定义**                             | **ISO 20022 定义**                                |
|----------------------------|-------------------------------------------------|--------------------------------------------------|
| **交易标识符**             | TransactionId                                   | TransactionIdentification                        |
| **交易类型**               | TransactionType                                 | TransactionType                                   |
| **交易金额**               | Amount                                          | Amount                                            |
| **货币类型**               | Currency                                        | Currency                                         |
| **交易日期**               | TransactionDate                                 | TransactionDate                                   |
| **交易时间**               | TransactionTime                                 | —                                                 |
| **付款方账户**             | DebtorAccount                                   | DebtorAccount                                     |
| **收款方账户**             | CreditorAccount                                 | CreditorAccount                                   |
| **付款方姓名**             | DebtorName                                      | DebtorName                                        |
| **收款方姓名**             | CreditorName                                    | CreditorName                                      |
| **交易状态**               | TransactionStatus                               | TransactionStatus                                 |
| **交易描述**               | TransactionDescription                          | —                                                 |
| **支付方式**               | PaymentMethod                                   | PaymentMethod                                     |
| **关联交易**               | RelatedTransactionId                            | RelatedTransactionIdentification                 |
| **费用**                   | Fees                                            | Charges                                           |
| **支付日期**               | PaymentDate                                     | RequestedExecutionDate                            |
| **支付状态**               | PaymentStatus                                   | PaymentStatus                                     |
| **交易参考号**             | ReferenceNumber                                  | EndToEndIdentification                           |
| **授权编号**               | AuthorizationCode                                | AuthorisationIdentification                       |
| **账户余额**               | AccountBalance                                  | —                                                 |
| **交易备注**               | —                                               | RemittanceInformation   