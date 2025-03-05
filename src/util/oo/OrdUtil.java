package util.oo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OrdUtil {


    /**
     * 去除sql排序部分，只保留前面部分语句
     * @param sql
     * @return
     */
    public static String delOrderby(String sql) {
        // 正则表达式用于匹配 ORDER BY 部分及其后内容
        String regex = "(?i)\\s*order by\\s+.*";  // 忽略大小写，匹配 ORDER BY 后的部分
        return sql.replaceAll(regex, "").trim();  // 使用空字符串替换 ORDER BY
    }
    /**
     * 根据sql的排序语法来继续list排序
     * @param lst
     * @param sqlOrder ，，，sql 的排序部分语句，，范例   order by timestamp desc
     * @return
     */
    public static <T> List<T> orderBySqlOrderMode(List<T> lst, String sqlOrder) {
        if(sqlOrder.trim().equals(""))
            return  lst;
       // 解析 SQL 排序语法，提取字段和排序方式
        String[] parts = sqlOrder.trim().toLowerCase().split("order by")[1].trim().split(",");
        List<Comparator<T>> comparators = new ArrayList<>();

        for (String part : parts) {
            String[] orderParts = part.trim().split(" ");
            String fieldName = orderParts[0].trim();
            String order = orderParts.length > 1 ? orderParts[1].trim() : "asc"; // 默认升序

            // 创建比较器来按字段排序
            Comparator<T> comparator = createComparator(fieldName, order);
            comparators.add(comparator);
        }

        // 使用多个比较器链式排序
        Comparator<T> combinedComparator = comparators.stream()
                .reduce((comparator1, comparator2) -> comparator1.thenComparing(comparator2))
                .orElse((Comparator<T>) Comparator.naturalOrder().reversed());

        // 排序并返回结果
        return lst.stream().sorted(combinedComparator).collect(Collectors.toList());
    }


    /**
     * 根据字段名和排序方式创建一个比较器
     * @param fieldName 排序字段名
     * @param order 排序方式（asc 或 desc）
     * @return Comparator
     */
    private static <T> Comparator<T> createComparator(String fieldName, String order) {
        return (o1, o2) -> {
            try {
                // 获取字段值
                Field field = o1.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                Comparable val1 = (Comparable) field.get(o1);
                Comparable val2 = (Comparable) field.get(o2);

                // 比较并根据排序方式反转结果
                int comparisonResult = val1.compareTo(val2);
                return "desc".equalsIgnoreCase(order) ? -comparisonResult : comparisonResult;
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("排序字段无效: " + fieldName, e);
            }
        };
    }
}
