

EventBus
可以使用 @Subscribe 注解的 priority 参数设置事件处理方法的优先级。
粘性事件： 可以使用 EventBus.getDefault().postSticky(event) 方法发布粘性事件。粘性事件会在订阅者注册后立即发送给订阅者。
取消事件传递： 在事件处理方法中，可以调用 EventBus.cancelEventDelivery(event) 方法取消事件的传递。
EventBus 的注意事项：

jdk
@objsrervs


spr
@evetnlisterner

