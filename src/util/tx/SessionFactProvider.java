//package util.tx;
//
//import cfg.MyCfg;
//import org.hibernate.SessionFactory;
//import org.picocontainer.injectors.ProviderAdapter;
//
//import java.io.FileNotFoundException;
//import java.sql.SQLException;
//import java.util.List;
//
//import static cfg.BaseHdr.saveDirUsrs;
//import static util.tx.HbntUtil.getSessionFactory;
//
//public class SessionFactProvider extends ProviderAdapter {
//    public SessionFactory provide() throws SQLException, FileNotFoundException {
//        List<Class> li = List.of();
//        MyCfg.iniContnr4cfgfile();
//        SessionFactory sessionFactory = getSessionFactory(saveDirUsrs, li);
//
//        return sessionFactory;
//
//    }
//}
