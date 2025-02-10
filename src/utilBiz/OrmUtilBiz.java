package utilBiz;

import apiAcc.LogBls;
import apiAcc.OrdChrg;
import apiCms.LogCms;
import apiOrdBet.OrdBet;
import apiUsr.Usr;
import apiWltYinli.LogBlsLogYLwlt;
import org.hibernate.Session;
import org.hibernate.boot.MetadataSources;
import util.HbntUtil;
import util.SessionImpIni;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrmUtilBiz {

    public static Session openSession(String jdbcUrl) throws SQLException {
        System.out.println("openSession(url="+jdbcUrl);
        if(jdbcUrl.startsWith("jdbc:"))
        {
            // 添加实体类映射
            List<Class> li=new ArrayList<>();
            li.add(Usr.class);
            li.add(LogBls.class);
            li.add(LogBlsLogYLwlt.class);
            li.add(OrdChrg.class);
            li.add(Usr.class);
            li.add(OrdBet.class);
            li.add(LogCms.class);
            return    HbntUtil.openSession(jdbcUrl,li);
        }
        //def ini impt
        return  new SessionImpIni(jdbcUrl);


//        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
//        metadataSources.addAnnotatedClass(Usr.class); // 你的实体类
//        metadataSources.addAnnotatedClass(LogBls.class);
//        metadataSources.addAnnotatedClass(LogBlsLogYLwlt.class); //
//        metadataSources.addAnnotatedClass(OrdChrg.class); /
    }

    private static Session newSessionIni(String jdbcUrl) {
            return  new SessionImpIni(jdbcUrl);
    }

}
