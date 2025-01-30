package utilBiz;

import apiAcc.LogBls;
import apiAcc.OrdChrg;
import apiUsr.Usr;
import apiWltYinli.LogBlsLogYLwlt;
import org.hibernate.Session;
import org.hibernate.boot.MetadataSources;
import util.HbntUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrmUtilBiz {

    public static Session openSession(String jdbcUrl) throws SQLException {
        // 添加实体类映射
        List<Class> li=new ArrayList<>();
        li.add(Usr.class);
        li.add(LogBls.class);
        li.add(LogBlsLogYLwlt.class);
        li.add(OrdChrg.class);
        li.add(Usr.class);
     return    HbntUtil.openSession(jdbcUrl,li);

//        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
//        metadataSources.addAnnotatedClass(Usr.class); // 你的实体类
//        metadataSources.addAnnotatedClass(LogBls.class);
//        metadataSources.addAnnotatedClass(LogBlsLogYLwlt.class); //
//        metadataSources.addAnnotatedClass(OrdChrg.class); /
    }

}
