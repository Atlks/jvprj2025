package util;
//import org.springframework.expression.ExpressionParser;
//import org.springframework.expression.spel.standard.SpelExpressionParser;
//import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.ArrayList;
import java.util.List;

import static util.dbutil.getstartPosition;

public class ArrUtil {

    public static <T> List<T> subList2025(List<T> list1, int pageNumber, int pageSize) {

        int fromIndex = getstartPosition(pageNumber, pageSize);
        if (fromIndex + 1 > list1.size()) {
            //ret
            return  new ArrayList<>();
            // return new PageResult<>(new ArrayList<>(), totalRecords, totalPages);
        }

        //   List<T> listPageed=new ArrayList<>();

        //last page

        int sizeList = list1.size();

        int endidx = getEndidx(pageSize, fromIndex, sizeList);

        System.out.println("sublist frmidx=" + fromIndex + ",endidx=" + endidx);
        List<T> listPageed =list1. subList(fromIndex, endidx);
        return listPageed;
    }

    static int getEndidx(int pageSize, int fromIndex, int sizeList) {
        int endidx = fromIndex + pageSize;
        if (endidx > (sizeList - 1)) {
            endidx = sizeList;
            //  List<T> listPageed = list1.subList(fromIndex, endidx);
            //  return new PageResult<>(listPageed, totalRecords, totalPages);
        }
        return endidx;
    }
    /**
     * 使用 SpEL 表达式对列表进行排序
     *
     * @param list       需要排序的集合
     * @param expression 比较表达式，例如：#this['age'] > #this['age']
     * @return 排序后的集合
     */
//    public static List<SortedMap<String, Object>> sortWithSpEL(List<SortedMap<String, Object>> list, String expression) {
//        ExpressionParser parser = new SpelExpressionParser();
//
//        // 使用 Comparator 比较表达式排序
//        list.sort((map1, map2) -> {
//            StandardEvaluationContext context1 = new StandardEvaluationContext();
//            StandardEvaluationContext context2 = new StandardEvaluationContext();
//
//            context1.setRootObject(map1);
//            context2.setRootObject(map2);
//
//            // 设置 map1 和 map2 为上下文变量
//         //   context.setVariable("map1", map1);
//         //   context.setVariable("map2", map2);
//
//            try {
//                // 将 map1 和 map2 传入表达式并计算结果
//                Boolean result = parser.parseExpression(expression).getValue(context1, Boolean.class);
//                int rtl = result != null && result ? -1 : 1;
//                System.out.println("sortWithSpEL().rtl="+rtl);
//                return rtl;
//            } catch (Exception e) {
//                System.err.println("Error evaluating expression: " + e.getMessage());
//                return 0; // 如果表达式解析失败，保持原顺序
//            }
//        });
//
//        return list;
//    }
//


}
