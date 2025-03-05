package util.tx;

import entityx.Usr;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static util.misc.Util2025.encodeJson;
//paging fun also here
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

        whereClause = whereClause
                .replaceAll("!=", " <> ");
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


    /**
     * 将 SQL ORDER BY 语句转换为 SpEL 语法
     * 支持 SQL 格式
     * ✅ ORDER BY age ASC, name DESC
     * ✅ ORDER BY created_at DESC, score ASC
     * ✅ ORDER BY name ASC
     */
    public static String convertSqlOrdbyToSpEL(String sqlOrderBy) {
        List<String> expressions = new ArrayList<>();
        Pattern pattern = Pattern.compile("([a-zA-Z_]+)\\s+(ASC|DESC)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(sqlOrderBy);

        while (matcher.find()) {
            String field = matcher.group(1).trim();
            String direction = matcher.group(2).trim().toLowerCase();
            expressions.add(field + " " + direction);
        }

        return "?[" + String.join(", ", expressions) + "]";
    }



    /**     // 定义过滤表达式，使用 SpEL 的集合操作
     //  String expression = "#list1.?[uname == 'unm2' && pwd == 'pp']";
     * 如果集合中的元素有可能为 null，SpEL 会抛出 NullPointerException。可以用
     * 如果集合中的元素有可能为 null，SpEL 会抛出 NullPointerException。可以用 null safe 语法：
     * ?[uname != null and uname == '008']
     * @param list1
     * @param expression  ?[uname != null and uname == '008']
     * @return
     */
    @SuppressWarnings("unchecked")
    static List<?> filterWithSpEL(List<?> list1, String expression) {
        System.out.println("fun fltSpel(lst["+list1.size()+"],spel="+expression);

        // 检查 list1 是否为 null 或空
        if (list1 == null || list1.isEmpty()) {
            return new ArrayList<>();
        }

       if(list1.size()==0) {
           return new ArrayList<>();
       }


        // 创建 SpEL 解析器
        ExpressionParser parser = new SpelExpressionParser();

        // 创建上下文并设置变量
      //  StandardEvaluationContext context = new StandardEvaluationContext();
      //  context.setVariable("list2", list1); // 将 list1 设置为变量


        // 创建上下文并将 list1 设置为根对象（而不是将其设置为变量）
        StandardEvaluationContext context = new StandardEvaluationContext(list1);
      //  context.setVariable("list1", list1); // 不再需要 setVariable("list2", list1)

//        在 SpEL 中，默认情况下，根对象是直接在上下文中可访问的，而无需显式设置变量。你可以通过将整个 list1 设置为上下文的根对象来简化表达式。
        // 解析并返回结果
        return (List<?>) parser.parseExpression(expression).getValue(context);
    }

    static  void main()
    {
//        List<Map<String,Object>> li=new ArrayList<>();
//        Map<String,Object> m=new HashMap<>();
//        m.put("uname","000");
//        li.add(m);
//
//
//        m = new HashMap<>();
//        m.put("uname", "008");
//        li.add(m);

        List<Usr> li=new ArrayList<>();
        Usr u=new Usr("008");
        li.add(u);
        //uname != null and
     // var lirzt=  filterWithSpEL(li,"#list1.?[#uname == '008']");
    //    List<?> filteredList = filterWithSpEL(li, "?[uname == '008']");
        // 调用过滤函数，尝试按条件过滤
        //  zhege stmt is ok   "#list1.?[uname == '008']");
     //   List<?> filteredList = filterWithSpEL(li, "#list2.?[uname == '008']");

        var spel="?[uname != null  and  uname ='008']";
     //   spel=   "?[uname!=null and uname == '008']";
        List<?> filteredList = filterWithSpEL(li,spel );

       // ?[uname != null  and  uname ='008']
        System.out.println( encodeJson(filteredList));
        /**
         * 访问 Map 键的方式： 在 SpEL 中，Map 元素可以通过 get 方法访问。你可以用如下的表达式来过滤：
         *
         * java
         * Copy
         * Edit
         * "?[ #uname == '008']"
         */
    }
}
