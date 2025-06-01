//package util.jpaqry;
//
//import jakarta.persistence.criteria.CriteriaBuilder;
//import jakarta.persistence.criteria.CriteriaQuery;
//import jakarta.persistence.criteria.Expression;
//import jakarta.persistence.criteria.Predicate;
//import model.usr.Usr;
//
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.List;
//
//public class Jpa2strmQry {
//
//    public static void main(String[] args) {
//        //select * from user where a=1 and b=2
//        CriteriaBuilder cb = new CriteriaBuilderImplt();
//        CriteriaQuery<?> query = cb.createQuery(Usr.class);
//
//        query.select(new SelectionAll());
//
//        // 构建 WHERE 条件
//        Predicate conditionA = cb.equal(rootget("id"), "0");
//        Predicate conditionB = cb.equal(rootget("uname"), "nm0");
//        Predicate conditionC = cb.equal(rootget("uname"), "nm2");
//        // 构造谓词条件：a = 1 AND b = 2
//        Predicate predicate = cb.and(conditionA, conditionB, conditionC
//        );
//        query.where(predicate);
//        //  QueryWrapper queryWrapper =
//
//        List<Usr> li = getUsrs();
//        List rzt = flt(query, li);
//        System.out.println(rzt);
//    }
//
//    private static List<Usr> getUsrs() {
//        List<Usr> li = new ArrayList<>();
//        for (int i = 0; i < 7; i++) {
//            Usr usr = new Usr();
//            usr.id = String.valueOf(i);
//            usr.uname = "nm" + i;
//            li.add(usr);
//
//        }
//        return li;
//    }
//
//    private static Expression<?> rootget(String a) {
//        return new FieldExpress(a);
//    }
//
//    public static <T> List flt(CriteriaQuery<T> criteriaQuery, List<?> collx) {
//
//        Predicate predicate = criteriaQuery.getRestriction();
//        if (predicate.getOperator().equals(Predicate.BooleanOperator.AND)) {
//
//            List rzt = new ArrayList();
//
//
//            for (Object row : collx) {
//                if (isMatchRulerAndMode(row, predicate, collx))
//                    rzt.add(row);
//                //expressions.forEach(expressionConsumer);
//            }
//            return rzt;
//
//        }
//        return new ArrayList();
//
//    }
////    public static <T> QueryWrapper<T> convert(CriteriaQuery<T> criteriaQuery) {
////        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
////        // 获取 WHERE 条件
////        Predicate predicate = criteriaQuery.getRestriction();
////        if (predicate != null) {
////            extractPredicate(predicate, queryWrapper);
////        }
////        return queryWrapper;
////    }
//
//
//    private static boolean isMatchRulerAndMode(Object row, Predicate predicate, List<?> collx) {
//        List<Expression<Boolean>> expressions = predicate.getExpressions();
//        for (Expression<Boolean> expression : expressions) {
//            if (expression instanceof EqualExprs) {
//                //  System.out.println(expression);
//                EqualExprs eq = (EqualExprs) expression;
//                Object v = getFldVal(row, eq.left);
//                 if(eq.rightVal instanceof String) {
//                     if (!v.toString().equals(eq.rightVal) )
//                         return false;
//                 }
//
//            }
//
//        }
//        return true;
//
//    }
//
//    private static Object getFldVal(Object obj, String field) {
//        if (obj == null || field == null || field.isEmpty()) {
//            return null;
//        }
//
//        try {
//            Class<?> clazz = obj.getClass();
//            Field f = null;
//
//            // 向上查找父类中的字段
//            while (clazz != null) {
//                try {
//                    f = clazz.getDeclaredField(field);
//                    break;
//                } catch (NoSuchFieldException e) {
//                    clazz = clazz.getSuperclass(); // 继续向上找
//                }
//            }
//
//            if (f != null) {
//                f.setAccessible(true);
//                return f.get(obj);
//            } else {
//                throw new NoSuchFieldException("Field '" + field + "' not found in class hierarchy.");
//            }
//
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to get field value: " + e.getMessage(), e);
//        }
//    }
//
//
//}
