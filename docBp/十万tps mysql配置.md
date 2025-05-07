


📊 总结：10W TPS 单机配置建议
硬件配置：
CPU：多核，频率 2.5GHz 以上，16 核或更高

内存：128GB 以上（MySQL Buffer Pool 70%-80%）

存储：高性能 SSD，优先选择 NVMe SSD（4KB 随机写入 100K+ IOPS）

MySQL 配置：
innodb_buffer_pool_size：内存的 70%~80%

innodb_log_buffer_size：16MB 或更大

innodb_flush_log_at_trx_commit：设置为 2

max_connections：500~1000 连接

thread_cache_size：合理配置，避免频繁创建线程

操作系统：
禁用 swap，调整文件句柄数等

如果硬件资源达到上限，还需要考虑 集群化，

读写分离 分区