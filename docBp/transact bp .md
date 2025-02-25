


tran act in   hdler   ....warp svs

3. @Transactional 和 异常处理

默认情况下，Spring 只会在遇到 RuntimeException 或 Error 异常时回滚事务。如果需要指定其他异常类型或添加自定义回滚逻辑，可以使用 rollbackFor 和 noRollbackFor 属性。