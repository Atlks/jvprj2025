package orgx.uti.orm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import orgx.uti.enttMngrs.IniEnttMngr;

import orgx.uti.expressions.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class JPAQueryX<T> {
    private EntityManager enttMng;
    private Class<T> from;
    private String fldsStrFmt;

    private Object[] selectFldsExprsArr;

    public JPAQueryX(EntityManager enttMngr) {
        this.enttMng = enttMngr;
    }

    public JPAQueryX select(String s) {
        this.fldsStrFmt = s;
        return this;
    }

    //select(exprs
//    public JPAQueryX select(Expression express) {
//        this.selectFldsExprs = express;
//        return this;
//    }

    //select(exprs
    public JPAQueryX select(Object... flds) {
        this.selectFldsExprsArr=flds;
        return this;
    }
    public static CountExpress count(String fld) {
        return new CountExpress(fld);
    }

    /**
     * ret Tuple
     * @return
     */
    public Tuple getSingleResultTuple() {
        if (enttMng instanceof IniEnttMngr) {
            List list = ((IniEnttMngr) enttMng).getResultList(this.from);
            List<T> rztList = flt(list, wherePredicate);
            Tuple map = calcArrgt(rztList, selectFldsExprsArr);
            return map;
        }
        return null;
    }

    private Tuple calcArrgt(List<T> rztList, Object[] selectFldsExprsArr) {
        TupleImpl tuple = new TupleImpl();
        Tuple result= tuple;
        Map rztMap = new HashMap();
        for(Object o : selectFldsExprsArr) {
            if (o instanceof SumExpress) {
                String fld = ((SumExpress) o).fld.toString();
                Object val=calcArrgt(rztList, (Expression) o);
                rztMap.put("sum_"+fld, val);
                tuple.alias.add("sum_"+fld);
                tuple.row.add(val);

            }
            if (o instanceof CountExpress) {
                rztMap.put("cnt_",  rztList.size());
               // return rztList.size();
            }

        }
        return tuple;
    }

    /**
     * 这里只获取单值，jq里面可以获取one row数据，也可单列只，也可   sum，count 这样的tuple数据
     * @return
     */
    public Object getSingleResult() {
        if (enttMng instanceof IniEnttMngr) {
            List list = ((IniEnttMngr) enttMng).getResultList(this.from);
            List<T> rztList = flt(list, wherePredicate);
//            if(selectFldsExprs!=null) {
//                //colje value ,Object
//                return calcArrgt(rztList, selectFldsExprs);
//            }
            // tuple map val
            if(selectFldsExprsArr!=null) {
                Tuple map = calcArrgt(rztList, selectFldsExprsArr);
                return     ((TupleImpl)map).get(0);
            }

            //singel row
            Map m = (Map) rztList.get(0);
            return m.values().iterator().next(); // 获取唯一的值;

        }
        return null;
    }

    private Object calcArrgt(List<T> rztList, Expression selectFldsExprs) {
        if (selectFldsExprs instanceof SumExpress) {
            String fld = ((SumExpress) selectFldsExprs).fld.toString();
            BigDecimal val =calcSum(rztList, fld);
            return val;
        }
        if (selectFldsExprs instanceof CountExpress) {

            return rztList.size();
        }
        return null;
    }



    /**
     * 计算聚合，list中实体中fld字段的值
     *
     * @param rztList
     * @param fld
     * @return
     */
    private BigDecimal calcSum(List<T> rztList, String fld) {
        BigDecimal sum = BigDecimal.ZERO;

        if (rztList == null || rztList.isEmpty()) {
            return sum;
        }

        try {
            for (T item : rztList) {
                Field field = item.getClass().getDeclaredField(fld);
                field.setAccessible(true);
                Object value = field.get(item);

                if (value instanceof Number) {
                    sum = sum.add(new BigDecimal(value.toString()));
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace(); // 处理异常，可替换为日志记录
        }

        return sum;
    }

    public List fetch() {
        if (enttMng instanceof IniEnttMngr) {
            List list = ((IniEnttMngr) enttMng).getResultList(this.from);
            List<T> rztList = flt(list, wherePredicate);
//String selectFields=fldsStrFmt;

            return list;
        }

        return null;
    }

    private List flt(List list, Predicate WhrPredicate) {
        if (WhrPredicate instanceof AndPredicate) {

        }
        if (WhrPredicate instanceof OrPredicate) {

        }
        return list;
        //list.stream().filter(predicateList::contains).collect(Collectors.toList());
    }

    public JPAQueryX from(Class<T> userClass) {
        this.from = userClass;
        return this;
    }

    public Predicate wherePredicate;

    //List<Predicate> AddPredicateList =new ArrayList<>();
    public JPAQueryX where(Predicate a) {
        wherePredicate = a;
        return this;
    }

    public AndPredicate andPredicate = new AndPredicate();

    public JPAQueryX and(Predicate p) {

        // AndPredicate ap=new AndPredicate();
        if(wherePredicate instanceof AndPredicate) {
            AndPredicate andPredicate1 = (AndPredicate) wherePredicate;
            andPredicate1.predicateList.add(p);
        }else {
            AndPredicate andPredicate1 =new AndPredicate();
            andPredicate1.predicateList.add(wherePredicate);
            andPredicate1.predicateList.add(p);
            wherePredicate = andPredicate1;

        }

        return this;
    }

    public String toDsl() {
        return "from " + from.getSimpleName() + " where " + encodeJsonPretty(wherePredicate);
    }

    /**
     * 使用 Gson 进行序列化，并格式化 JSON 输出
     *
     * @param o 需要序列化的对象
     * @return 格式化后的 JSON 字符串
     */
    private static String encodeJsonPretty(Object o) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(o);
    }

    public static Predicate eq(String fld, Object v) {
        return new EqualPredicate(fld, v);
    }

    static Object fld(Function<String, Object> fun, String fld) {
        return null;
    }

    static ThreadLocal<Long> sumCalc = new ThreadLocal<Long>();

    public static SumExpress sum(String fld) {
        return new SumExpress(fld);
//        Long result = sumCalc.get();
//        Long newRzt=result+Long.parseLong(fldVal);
//        sumCalc.set(newRzt);
//        return  newRzt;
    }


}
