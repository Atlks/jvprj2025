package util;

import apis.BaseHdr;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.picocontainer.injectors.ProviderAdapter;
import utilBiz.OrmUtilBiz;

import java.sql.SQLException;
import java.util.List;

import static apis.BaseHdr.saveDirUsrs;
import static util.HbntUtil.getSessionFactory;

// 提供 Session 的工厂类
public class SessionProvider extends ProviderAdapter {
    public Session provide() throws SQLException {
        return OrmUtilBiz.openSession(saveDirUsrs); // 每次调用都新建 Session
    }
}

