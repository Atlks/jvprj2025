//package util.jpaqry;
//
//import jakarta.persistence.criteria.*;
//import model.usr.Usr;
//
//import java.time.OffsetDateTime;
//import java.util.function.Consumer;
//
//import static util.algo.GetUti.getTablename;
//import static util.algo.ToXX.toSnake;
//
//public class Jpa2sqlUti {
//
//    public static void main(String[] args) {
//        //select * from user where a=1 and b=2
//        CriteriaBuilder cb = new CriteriaBuilderImplt();
//        CriteriaQuery<?> query = cb.createQuery(Usr.class);
//
//        query.select(new SelectionAll());
//
//        // 构建 WHERE 条件
//        Predicate conditionA = cb.equal(rootget("a"), 1);
//        Predicate conditionB =  cb.equal(rootget("b"), "bbtxt");
//        Predicate conditionC = cb.equal(rootget("c"), 3);
//        // 构造谓词条件：a = 1 AND b = 2
//        Predicate predicate = cb.and(conditionA, conditionB, conditionC
//        );
//        query.where(predicate);
//        //  QueryWrapper queryWrapper =
//        String sql = convert(query);
//        System.out.println(sql);
//    }
//
//    private static Expression<?> rootget(String a) {
//        return new FieldExpress(a);
//    }
//
//    public static <T> String convert(CriteriaQuery<T> criteriaQuery) {
//        //  QueryWrapper<T> queryWrapper = new QueryWrapper<>();
//        // 获取 WHERE 条件
//        StringBuilder sb = new StringBuilder();
//        CriteriaQueryImp cqi=(CriteriaQueryImp)criteriaQuery;
//        String table=getTablename(cqi.fromClz);
//        sb.append("select * from "+table+" where 1=1 ");
//        Predicate predicate = criteriaQuery.getRestriction();
//        if (predicate != null) {
//            extractPredicate(predicate, sb);
//        }
//        return sb.toString();
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
//    private static <T> void extractPredicate(Predicate predicate, StringBuilder strBld) {
//        if (predicate.getOperator().equals(Predicate.BooleanOperator.AND)) {
//
//
//            Consumer<Expression<Boolean>> expressionConsumer = expression -> {
//                if (expression instanceof EqualExprs) {
//                    //  System.out.println(expression);
//                    EqualExprs eq = (EqualExprs) expression;
//                    strBld.append(" and " + toSnake(eq.left) + "=" + toSqlVal(eq.rightVal));
//                    return;
//                }
//                if (expression instanceof Predicate) {
//                    extractPredicate((Predicate) expression, strBld);
//                } else {
//                    System.out.println(expression);
//                    // queryWrapper.eq(root.get(expression.toString()), expression);
//                }
//            };
//
//            predicate.getExpressions().forEach(expressionConsumer);
//        }
//    }
//
//    private static String toSqlVal(Object rightVal) {
//        if (rightVal instanceof String) {
//            return "'" + rightVal + "'";
//        } else if (rightVal instanceof OffsetDateTime) {
//            return "'" + ((OffsetDateTime) rightVal).toInstant().toString() + "'";
//        } else {
//            return rightVal.toString();
//        }
//    }
//
//
//}
