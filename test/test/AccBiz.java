package test;

import java.util.List;
import java.util.SortedMap;

import static util.tx.dbutil.addObj;
import static util.tx.dbutil.findObjsJsDocdb;

public class AccBiz {


    public static String addAccLog(Object acclog) throws Exception {




        addObj(acclog,   "/db2026/acc");
        //  addObj(user, "u","jdbc:sqlite:/db2026/usrs.db");
        return "ok";


    }

    public static List<SortedMap<String, Object>> listAccLog(Object acclog) throws Exception {



        var lst2 = findObjsJsDocdb("acc", "/db2026/");

        return lst2;


    }
}
