

1. 身份验证（Authentication）

 
   常用技术规范：

   JWT（JSON Web Token）✅


实现方式	Token 通常放在 HTTP Header：Authorization: Bearer


=实际流程：JWT  

① 用户登录账号密码
↓
② 服务端验证成功，生成 JWT（含用户ID、角色、过期时间等）
↓
③ 前端保存 JWT，后续请求带上 JWT
↓
④ 后端解析 JWT，取出 userId / role
↓
⑤ 后端根据 role 查询该用户是否有权限访问此 API
↓
⑥ 如果有权限 → 返回数据




[登录] ---> [验证账号密码] ---> [生成JWT Token] ---> [客户端保存Token]

↓                                 ↑
[访问API] --带上Token--> [解析JWT, 得到user+role] ---> [RBAC权限判断]
↓
[允许 or 拒绝]
