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

import static util.ColorLogger.*;

public class OrmUtilBiz {

    public static Session openSession(String jdbcUrl) throws SQLException {
        String mthClr=colorStr("openSession",YELLOW_bright);
        System.out.println("▶\uFE0F fun "+mthClr+"(url="+colorStr(jdbcUrl,GREEN));
        if(jdbcUrl.equals(""))
            throw  new RuntimeException("openSession().jdbcurl is empty");
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

            Session session = HbntUtil.openSession(jdbcUrl, li);
            System.out.println("✅endfun openSession()");
            return session;
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
