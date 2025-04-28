

事件驱动的对比，uke aws azure



----------nml evt drv

# reg evt

@EventListener(AftrCalcRchgAmtSumEvt.class)
public class CalcCmsHdl {


# reg pblsh

       Transactions tx=new Transactions();
        publishEvent(AftrCalcRchgAmtSumEvt.class,tx);


---------aws evt drv

AWS上常用的事件驱动组件

组件	用途
EventBridge （核心）	统一的事件总线系统，接收、路由、分发各种事件
API Gateway	HTTP请求触发事件（常用于前端）

AWS 上？
如果你在AWS，手动触发通常是：

aws events put-events 命令

或者用 SDK 调用 EventBridge.putEvent


绑定到 EventBridge 的规则”，是在哪儿写代码的？
创建EventBridge规则

找到【EventBridge】服务 ➔【Rules】

【Create rule】Event pattern】 ➔ 写匹配条件：


如果想写代码自动化创建（比如Java、或者命令行）
比如用 AWS SDK for Java ➔ 自动创建规则：

java
复制
编辑
// 1. 创建 EventBridge 规则
PutRuleRequest putRuleRequest = new PutRuleRequest()
.withName("on-calcCms")
.withEventPattern("{\"detail-type\": [\"CalcCmsEvent\"]}")
.withState(RuleState.ENABLED)
.withEventBusName("default");

PutRuleResult putRuleResult = eventBridgeClient.putRule(putRuleRequest);

// 2. 绑定 Lambda 作为目标
Target target = new Target()
.withArn(lambdaFunctionArn) // Lambda 函数 ARN
.withId("TargetLambda");

PutTargetsRequest putTargetsRequest = new PutTargetsRequest()
.withRule("on-calcCms")
.withTargets(target);

eventBridgeClient.putTargets(putTargetsRequest);

-------azure evt drv

Azure 用 Event Grid / Event Hub / Service Bus 作为事件中间人，➔ 触发 Azure Functions 或其他服务！

📖 整体流程

2. 中转平台（Event Broker）

Azure Event Grid ➔ 广播式（类似AWS EventBridge）

Azure Event Hub ➔ 高吞吐，专门收集日志流、传感器数据

Azure Service Bus ➔ 队列式（更像消息队列，可靠投递）

4. 触发器（Trigger Target）

Azure Functions（最典型 FaaS）

Logic Apps（无代码工作流）

Webhook（你自己的HTTP接口

azure用 EventGridTrigger 绑定事件
@EventGridTrigger(name = "event")


在 Azure Function 里「手动触发 calcCms 事件」=http POST 自己的事件到 EventGrid Topic，

