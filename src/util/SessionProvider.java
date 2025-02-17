package util;

import org.hibernate.Session;
import org.picocontainer.injectors.ProviderAdapter;
import utilBiz.OrmUtilBiz;

import java.sql.SQLException;

import static apis.BaseHdr.saveDirUsrs;

// 提供 Session 的工厂类
public class SessionProvider extends ProviderAdapter {
    public Session provide() throws SQLException {
        return OrmUtilBiz.openSession(saveDirUsrs); // 每次调用都新建 Session
    }
}