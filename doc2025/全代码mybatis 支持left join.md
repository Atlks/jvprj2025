


望 全 Java 代码模式 仍然包含 LEFT JOIN，你需要使用 LambdaQueryWrapper 或者 MyBatis 自定义 SQL。在 QueryWrapper 中 不支持 JOIN，所以需要使用 SQL 语句 进行 JOIN 操作。

🔹 方式 1：使用 @Select 自定义 SQL
如果你仍然希望 在 Mapper 代码里 使用 LEFT JOIN




🔹 方式 2：使用 LambdaQueryWrapper（不支持 JOIN，只能查询当前表）
如果你的 LEFT JOIN 不是必须，可以使用 LambdaQueryWrapper 进行 动态条件查询：