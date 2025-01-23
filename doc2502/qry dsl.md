


查询参数的传递
URL querystring 是一种轻量级的方式，可以用于传递查询条件，例如筛选条件、分页信息、排序规则等。

2. 用 URL 模拟 DSL
   如果想实现更复杂的查询，可以设计一种结构化的 querystring 格式，类似于 JSON 或其他 DSL 的简化形式。

示例：嵌套查询

text
Copy
Edit
https://api.example.com/search?query={"redis":{"key":"example"},"sql":{"status":"active"},"

4. 适合的场景
   使用 URL querystring 作为查询工具适用于以下场景：

简单查询： 查询条件较少，且无需复杂的逻辑。
前端友好： 方便通过浏览器地址栏共享查询链接。
缓存支持： URL querystring 查询可以方便地利用 HTTP 缓存机制。

= sql

或者使用sql作为主查询，然后解析，为其他的查询模式

= 翻译过程

根据 url qry str,,
人工翻译为  sql ，linql ,lucene dsl

== 集合查询语言  el dsl


3. JUEL (Java Unified Expression Language)
   JUEL 是 Java EE 的 EL 实现，可以在 Java 集合中运行表达式，尤其是在需要动态定义查询逻辑的场景


2. SpEL (Spring Expression Language)
   SpEL 是 Spring 框架的表达式语言，功能强大，支持复杂的集合查询操作。


4. MVEL (MVFLEX Expression Language)
   MVEL 是一个轻量级的表达式语言，适合动态查询集合。

5. Apache Commons JEXL
   JEXL (Java Expression Language) 是 Apache Commons 提供的一个表达式语言实现。

特点：

轻量级且简单易用。
支持动态表达式和变量操作。

6. Groovy Shell
   Groovy 是一种动态语言，直接支持集合查询操作，可以作为 Java 的补充。


spel和groovy最简洁净。。。。其他juel jexl mvel冗长。。
按照语法简单程度对这些动态查询工具进行排列（从简单到复杂）：

1. JUEL (Java Unified Expression Language)
2. SpEL (Spring Expression Language)
3. 总结：按照语法简单程度排序
   JUEL （最简单，语法直观且符合 EL 标准）
   SpEL （语法略复杂，但提供了丰富的操作符）
   JEXL （轻量级且简单）
   MVEL （语法清晰，适合动态场景）
   Groovy Shell （动态脚本，语法简洁但依赖较大）



3. OGNL (Object-Graph Navigation Language)
   OGNL 是一种强大的表达式语言，常用于 Struts 框架中，支持对对象图进行导航和操作。

特点：

功能强大，支持集合查询。
比 JUEL 和 SpEL 更复杂，语法稍微难懂。