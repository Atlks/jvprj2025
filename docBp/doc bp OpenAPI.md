OpenAPI 是描述 HTTP API 的标准方式。




Smartbear于 2015年 捐赠了 OpenAPI，成立了 OpenAPI（OAI），这是一个由代表一些主要技术利益的约 40 名参与成员组成的行业联盟。

..JAX-RS 转换到oopenapi  easy

===// 设置静态资源目录 (例如: D:/myweb/static)
String staticDir = "C:\\Users\\attil\\IdeaProjects\\jvprj2025\\static";
server.createContext("/static", new StaticFileHandler(staticDir));


=doc.yaml      


openapi: 3.0.0
info:
title: 用户 API
version: 1.0.0
paths:

/reg:
get:
summary: "注册用户的方法reg"
description: "注册用户的方法dscrp。。。。"
operationId: "registerUser"
parameters:
- name: uname
in: query
description: "用户名"



=doc.htm


swagger-ui-bundle.js


=online editr

https://editor.swagger.io/