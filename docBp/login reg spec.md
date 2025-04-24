
哪里“规定”了注册登录 API 和字段？

来源	是否官方	推荐作用
OpenAPI	✅ 是	API 格式/字段结构
OWASP	✅ 是	安全字段和认证流程


/api/register

{
  "username": "jerry001",
  "email": "jerry@example.com",
  "password": "P@ssw0rd!"
}






/api/login
{
  "username": "jerry001",
  "password": "P@ssw0rd!"
}


Response（登录成功）   openid spec
json
复制
编辑
{
  "accessToken": "eyJhbGciOi...",
  "expiresIn": 3600
}