



# biz spec

...usr/role
passport visa jwt oauth openid
Java EE Security API）  jsr250
@RolesAllowed	指定允许访问的角色

..fns acc trx   iso20022 openbank obieV3
..game  thrd api,open game spec
..im  tg 
..ebiz
。。games  spec。。ltry baijiala
..blog
。。vod sys
。。pay sys
。。o2o sys


# dev spec

..dev spec
conn cfg model...

..rest   jax rs  ，apigateway ，req dto respdto
anno  @paths   to  hdl.clsz.fun(dto):apigatwayRespons

..ioc aop
..store   jpa  orm
hbnt not mbts...
oosql to qry ,insert orm
auto crt db,dlt column,use Flywayx
。。java spec jsr
。。valid
..doc spec.. openapi javadoc
..test     @Before / @BeforeEach	在每个测试前执行
test code jst same pkg



# non fun spec

。。rdable spec

..secury spec ,web secury
aes (不要des）
✅ 二、安全机制
1. 身份认证
   多因素认证（MFA）；
2. 密码学签名（RSA
号码随机数要从eth的区块链hash去取计算

..fns sys scry spec    转账、支付等核心业务对数据一致性与安全性要求极高，通
trx,强事务 jta   强一致性（ACID事务）
悲观锁, 不要乐观锁
--------
3pc （不要2pc,tcc,saga )
若涉及多个系统（例如用户账户+资金清算），会使用：

XA协议（数据库层两阶段提交）；
-------------
,隔离级别序列化+队列防止死锁
，幂等,
双重锁机制   数据库层锁+应用层
Write-Ahead Logging（WAL）机制  交易表）驱动
数据校验与修复 双写校验


。。perfm spec
db pefm enhance。。。  
落盘改为每秒 模式
分区表
读写分离 集群模式

..分布式技术

分库分表，分布式事务，分布式锁redis
队列
分布式缓存 redis
分布式文件系统 ssh ftp http

