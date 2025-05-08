

pfm enhs spec

提升性能方法
参照hdwr硬件模式和window

pfm

将硬件，WINDOWS还有软件开发中的性能提升方法对应起来
~
# 开源节流模式
。。并行架构 多通道 多核
。。读写分离
..分库分表

#  节流模式
。。直连模式 dma跳过中间环节
。。更高性能存储文件模型
..index model，多维索引，联合索引，强行指定索引
..分区


#----更好的匹配模式
# 更好的调度算法  智能队列

# cache模式 多级缓存（L1/L2/L3）：

# 异步并行 事件驱动
乱序执行（Out-of-Order Execution）：提高指令执行效率，减少等待。事件驱动，异步


---------------
# mysql pfm enhs
# 具体的cache
。。hbnt lev2 cache...jcache ,guava cache
..mysql  ,buffer pool ,let idex n data cache in memry



ref

mysql pfm
cache java






