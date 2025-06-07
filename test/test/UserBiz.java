package test;

import util.ex.existUserEx;

//import static api.usr.RegHandler.reg;

//import static util.oo.ArrUtil.sortWithSpEL;
import static util.misc.Util2025.encodeJson;

public class UserBiz {



    public static void main(String[] args) throws Exception, existUserEx {


        // 创建 User 对象
   //     RegHandler.User user = new RegHandler.User("u1", "u1", "", 1,"");
//        reg( new RegHandler.User("u1", "u1", "",1));
//        reg( new RegHandler.User("u2", "u2", "",2));
//        reg( new RegHandler.User("u3", "u3", "",3));
        // var lst2 = getObjsDocdb("usrs", saveDir);


        // var rzt = flt1tmp(lst2);


        String expression = "uname == 'unm2' && pwd == 'pp'";
        expression = " #this['uname'] == 'unm2' && #this['pwd'] == 'pp'  ";
        expression = "";

        //   var rzt=filterWithSpEL(lst2, expression);

        var rzt =0;// Fltr.filterWithSpEL(saveDirUsrs , expression);
        System.out.println("rztFlted=" + encodeJson(rzt));

        var rztSted =0;// sortWithSpEL(rzt, "#map1['age'] < #map2['age']");
        System.out.println("rztSorted=" + encodeJson(rztSted));
        //   List<SortedMap<String, Object>>  rzt=   fltr2501(lst2,flt1);

        //   search("unm2")
        //   System.out.println(responseTxt);
        // 定义一个 Record
        //   record User(String username, int age) {}
    }





//    public static String logOut() {
//        return "";
//    }

//    public static String updtPwd() {
//        return "";
//    }





}
