

DB_CLOSE_DELAY=-1 只适用于内存数据库 (mem:) 或临时连接。

若你使用的是文件数据库如：jdbc:h2:/datax/mydb，该参数影响不大 —— 因为数据已持久化到磁盘。


上没问题，file: 是文件模式，数据库数据会持久化到 /datax/prjdb.mv.db 这个文件里，DB_CLOSE_DELAY=-1 对文件模式没啥特别影响，也不会导致数据丢失。