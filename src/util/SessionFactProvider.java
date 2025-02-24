package util;

import cfg.MyCfg;
import org.hibernate.SessionFactory;
import org.picocontainer.injectors.ProviderAdapter;

import java.sql.SQLException;
import java.util.List;

import static biz.BaseHdr.saveDirUsrs;
import static util.HbntUtil.getSessionFactory;

public class SessionFactProvider extends ProviderAdapter {
    public SessionFactory provide() throws SQLException {
        List<Class> li = List.of();
        MyCfg.iniCfgFrmCfgfile();
        SessionFactory sessionFactory = getSessionFactory(saveDirUsrs, li);

        return sessionFactory;

    }
}
