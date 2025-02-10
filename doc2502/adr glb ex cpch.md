



Thread.setDefaultUncaughtExceptionHandler	全局	捕获所有未处理的异常
CoroutineExceptionHandler	仅限于 CoroutineScope.launch	只处理 launch 的异常，不会影响 async
如果你的应用是 多线程 + 协程混用，建议 两者都实现，以确保最大程度捕获异常并进行处理