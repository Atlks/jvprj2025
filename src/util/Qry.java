package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Qry {


    /**
     * 将 JPQL 转换为 SpEL 语法
     * @param jpql  JPQL 语句，例如：SELECT e FROM Entity e WHERE e.age > :age AND e.name = :name
     * @return SpEL 语法，例如：?[age > #age and name == #name]
     */
    public static String convertJPQLToSpEL(String jpql) {
        // 去掉 "SELECT e FROM Entity e WHERE"
        String whereClause = jpql.replaceAll("(?i)^SELECT\\s+\\w+\\s+FROM\\s+\\w+\\s+WHERE\\s+", "");

        // 将 `:param` 替换为 `#param`
        String spelExpression = whereClause.replaceAll(":([a-zA-Z0-9_]+)", "#$1");

        // 添加 SpEL 过滤前缀 `?[...]`
        return "?[" + spelExpression + "]";
    }

    /**
     * 将 sQL 转换为 SpEL 语法
     * @param sql
     * @return
     */
    /**
     * 将 SQL WHERE 语句转换为 SpEL 语法
     *
     * @param sql SQL 语句 (包含 WHERE 条件)
     * @return SpEL 语法
     */
    public static String convertSqlToSpEL(String sql) {
        // 1️⃣ 提取 WHERE 条件
        String whereClause = sql.replaceAll("(?i)^SELECT\\s+\\*\\s+FROM\\s+\\w+\\s+WHERE\\s+", "").trim();

        // 2️⃣ 替换运算符
        whereClause = whereClause
                .replaceAll("(?i)\\bAND\\b", " and ")   // AND -> and
                .replaceAll("(?i)\\bOR\\b", " or ")     // OR -> or
                .replaceAll("=", "==")                 // = -> ==
                .replaceAll("<>", "!=")                // <> -> !=
                .replaceAll("(?i)\\bBETWEEN\\b (\\d+) AND (\\d+)", ">= $1 and <= $2") // BETWEEN X AND Y
                .replaceAll("(?i)\\bIN\\s*\\(([^)]+)\\)", " in {$1}"); // IN (a, b, c) -> in {a, b, c}

        // 3️⃣ 处理 LIKE 语句
        whereClause = processLikeConditions(whereClause);

        // 4️⃣ 返回 SpEL 格式
        return "?[" + whereClause + "]";
    }

    /**
     * 处理 LIKE 语句
     * SQL LIKE '%X%' 转换成 SpEL matches('.*X.*')
     */
    private static String processLikeConditions(String whereClause) {
        Pattern likePattern = Pattern.compile("(?i)(\\w+)\\s+LIKE\\s+'([^']*)'");
        Matcher matcher = likePattern.matcher(whereClause);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            String field = matcher.group(1);
            String value = matcher.group(2)
                    .replace("%", ".*")   // % -> .*
                    .replace("_", ".");   // _ -> .
            matcher.appendReplacement(sb, field + " matches '"
                    + value + "'");
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
