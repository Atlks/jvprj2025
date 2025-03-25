
```mermaid  graph TD ：从上到下（Top to Down）的流程图。
graph TD
  A[开始] --> B(处理请求)
  B --> C{请求有效?}
  C -- 否 --> D[返回错误]
  C -- 是 --> E[执行操作]
  E --> F[返回成功]



flowchart TD
  A((注册流程)) --> B(收集参数)
  B --> C{用户名重复?}
  C -- 是 --> D[返回错误]
  C -- 否 --> E[添加用户]
  
  E --> F((返回成功))


### **示例**
```markdown
```dot
digraph G {
  A [label="开始"];
  B [label="用户输入"];
  C [label="处理数据"];
  D [label="返回结果"];
  A -> B -> C -> D;
}


1. PlantUML

PlantUML 是一个强大的 UML 和流程图绘制工具，支持复杂流程图、时序图、用例图等。可以通过 Markdown 渲染器（如 Typora、Obsidian、VS Code 插件）或在线工具使用。
示例

```plantuml
@startuml
start
:用户输入;
if (输入有效?) then (是)
  :处理数据;
  :返回结果;
else (否)
  :显示错误消息;
endif
stop
@enduml



如果需要 UML 或复杂流程建模，推荐 PlantUML 或 Graphviz。