package biz;

import apis.RegHandler;
import util.Fltr;

import static apis.RegHandler.reg;
import static biz.BaseBiz.saveDirUsrs;
import static util.ArrUtil.sortWithSpEL;
import static util.Util2025.encodeJson;

public class UserBiz {



    public static void main(String[] args) throws Exception, existUserEx {


        // 创建 User 对象
        RegHandler.User user = new RegHandler.User("u1", "u1", "", 1);
        reg( new RegHandler.User("u1", "u1", "",1));
        reg( new RegHandler.User("u2", "u2", "",2));
        reg( new RegHandler.User("u3", "u3", "",3));
        // var lst2 = getObjsDocdb("usrs", saveDir);


        // var rzt = flt1tmp(lst2);


        String expression = "uname == 'unm2' && pwd == 'pp'";
        expression = " #this['uname'] == 'unm2' && #this['pwd'] == 'pp'  ";
        expression = "";

        //   var rzt=filterWithSpEL(lst2, expression);

        var rzt = Fltr.filterWithSpEL(saveDirUsrs , expression);
        System.out.println("rztFlted=" + encodeJson(rzt));

        var rztSted = sortWithSpEL(rzt, "#map1['age'] < #map2['age']");
        System.out.println("rztSorted=" + encodeJson(rztSted));
        //   List<SortedMap<String, Object>>  rzt=   fltr2501(lst2,flt1);

        //   search("unm2")
        //   System.out.println(responseTxt);
        // 定义一个 Record
        //   record User(String username, int age) {}
    }


//    @SuppressWarnings("unchecked")
//    private static List<SortedMap<String, Object>> filterWithSpEL(List<SortedMap<String, Object>> list1) {
//        // 创建 SpEL 解析器
//        ExpressionParser parser = new SpelExpressionParser();
//
//        // 定义过滤表达式，使用 SpEL 的集合操作
//        String expression = "#list1.?[uname == 'unm2' && pwd == 'pp']";
//
//        // 创建上下文并设置变量
//        StandardEvaluationContext context = new StandardEvaluationContext();
//        context.setVariable("list1", list1); // 将 list1 设置为变量
//
//        // 解析并返回结果
//        return (List<SortedMap<String, Object>>) parser.parseExpression(expression).getValue(context);
//    }


//    public static String logOut() {
//        return "";
//    }

//    public static String updtPwd() {
//        return "";
//    }





}
