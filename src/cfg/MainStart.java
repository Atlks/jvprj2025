package cfg;//package cfg;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import org.hibernate.SessionFactory;
/// /import org.springdoc.core.properties.SpringDocConfigProperties;
// import util.evtdrv.ApplicationEventPublisherImplt;
//
//import java.io.FileNotFoundException;
//import java.sql.SQLException;
//import java.util.List;
//
//import static cfg.BaseHdr.saveDirUsrs;
//import static util.tx.HbntUtil.getSessionFactory;
//
//@EnableAsync  // 启用异步功能
//@Configuration
////@EnableAspectJAutoProxy(proxyTargetClass = false)  // 禁用类加载时的字节码增强
//Scan("")  // 指定扫描包 all pkg if empty  ComponentScan 注解的参数为空字符串，这意味着它不会扫描任何包。Spring 无法找到
////@EnableMBeanExport
//@EnableMBeanExport

import handler.mainx.AutoRestartApp;
import util.log.ConsoleInterceptor;
import entityx.usr.NonDto;
import lombok.SneakyThrows;
import model.OpenBankingOBIE.Account;
import model.OpenBankingOBIE.AccountSubType;
import model.OpenBankingOBIE.AccountType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import service.YLwltSvs.AccSvs4invstAcc;
import service.wlt.AccSvs;
import util.auth.SecurityContextImp4jwt;
import util.tx.findByIdExptn_CantFindData;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

import static cfg.Containr.jdbcUrl;
import static cfg.Containr.sessionFactory;
// static cfg.MyCfg.iniContnr4cfgfile;
import static cfg.IniCfg.iniContnr4cfgfile;
import static cfg.WebSvr.iniRestPathMap;
import static cfg.WebSvr.startWebSrv;
import static handler.invstOp.AddInvstRcdHdl.iniSysEmnyAccIfNotExst;
import static handler.invstOp.AddInvstRcdHdl.iniSysInvstAccIfNotExst;
import static java.time.LocalTime.now;

import static util.acc.AccUti.getAccId;
import static util.acc.AccUti.sysusrName;
import static util.algo.CallUtil.callTry;
import static util.evtdrv.EvtUtil.iniEvtHdrCtnr;
import static util.ioc.SimpleContainer.registerInstance;
import static util.misc.RestUti.iniRestPathMthMap;
import static util.misc.util2026.sleep;
import static util.model.WindowSnapshot.restoreWinPrcs;
import static util.orm.CrtTblUti.scanClzCrtTable;
import static util.orm.HbntExt.migrateSql;
import static util.tx.HbntUtil.*;
import static util.tx.dbutil.crtDatabase;
import static util.tx.dbutil.getDatabaseFileName4mysql;

////启用 MBean（Managed Bean） 的导出，即 将 Spring 管理的 Bean 注册到 JMX（Java Management Extensions） 中，使其可以被 JMX 监控和管理。
public class MainStart {


    /**
     *
     * @throws Exception
     */
    @SneakyThrows
    public static void handleRequest(NonDto nonDto) throws Exception {

        //-DwbsvrDisable=true
        String wbsvrDisable = System.getProperty("wbsvrDisable", "false");
        if (wbsvrDisable.equals("true")) {
            restoreWinPrcs();
            return;
        }

        ConsoleInterceptor.init();// log
        System.out.println("fun main(), now= " + now());

        //--------ini saveurlFrm Cfg

        iniDbNcfg();


        //ini contnr 4cfg,, svrs
        //ioc ini
        iniContnr();
        iniEvtHdrCtnr();

        //   evtlist4reg.add(new AgtHdl()::regEvtHdl);
        //  AgtHdl

        //================== 创建 HTTP 服务器，监听端口8080
        iniRestPathMap();
        iniRestPathMthMap();

        //------ini sys acc
        iniSysAcc();


        if (wbsvrDisable.equals("false"))
            startWebSrv();


        System.out.println(11);

        sleep(3000);
        System.out.println("--------------------\n\n main()");
        System.out.println("main() exe finish, " + now());
        AutoRestartApp.main(null);
    }

    public static void iniDbNcfg() throws FileNotFoundException, SQLException {
        iniContnr4cfgfile();
        if (jdbcUrl.startsWith("jdbc:mysql")) {
            var db = getDatabaseFileName4mysql(jdbcUrl);
            crtDatabase(jdbcUrl, db);
        }
        //h2 not need crt db
        callTry(() -> migrateSql());

        //aft sess factry,crt table again by my slf
        callTry(() -> scanClzCrtTable());
       //  System.exit(0);
        new MainStart().sessionFactory();//ini sessFctr ..
    }


    //  public static SessionFactory sessionFactory;

//    public void sessionFactory() {
//
//    }

//
//    @Bean
//    public LoadTimeWeaver loadTimeWeaver() {
//        return new SimpleLoadTimeWeaver();
//    }

    //    @Bean
    public SessionFactory sessionFactory() throws SQLException, FileNotFoundException {
        if (sessionFactory == null) {
            List<Class> li = List.of();
            iniContnr4cfgfile();
            SessionFactory sessionFactory2 = getSessionFactory(jdbcUrl, li);
            sessionFactory = sessionFactory2;
            sessionFactory = sessionFactory2;
            return sessionFactory;
        } else
            return sessionFactory;

    }

    public static void iniAccInsFdPool_IfNotExist(String uname1) {

        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        String accid = getAccId(AccountSubType.insFdPl.name(), sysusrName);
        try {
            var wlt = findById(Account.class, accid, session);
        } catch (findByIdExptn_CantFindData e) {

            Account acc1 = new Account(accid);
            // .. acc1.userId= uname1;
            //   acc1.accountId=
            acc1.owner = sysusrName;
            //  acc1.uname
            acc1.accountType = AccountType.BUSINESS;
            acc1.accountSubType = AccountSubType.insFdPl.name();
            persist(acc1, session);

        }
        session.getTransaction().commit();
    }


    public static void iniSysAcc() {
        iniAccInsFdPool_IfNotExist("");
        Session session = sessionFactory.getCurrentSession();
        session.getTransaction().begin();
        iniSysEmnyAccIfNotExst(session);
        iniSysInvstAccIfNotExst(session);
        session.getTransaction().commit();
    }


    public static void iniContnr() throws Exception {
        //@NonNull
        iniContnr4cfgfile();
        //  new AppConfig().sessionFactory();//ini sessFctr
        //---------------ini contarin
        //   cfg.IocSpringCfg.iniIocContainr4spr();
        Containr.SecurityContext1 = new SecurityContextImp4jwt();
        //   Containr.chooseEvtPblshr=  new ChooseEvtPublshr();
        registerInstance("RdsFromWltService", () -> {
            return new AccSvs();
        });

        registerInstance("AddMoney2YLWltService", () -> {
            return new AccSvs4invstAcc();
        });


    }
//
//
//    @Bean
//    public ApplicationEventPublisher applicationEventPublisher() {
//        return new ApplicationEventPublisherImplt();
//    }
//
////    @Bean
////    public SpringDocConfigProperties springDocConfigProperties() {
////        SpringDocConfigProperties config = new SpringDocConfigProperties();
////      //  config.setApiDocsPath("/v3/api-docs");
////     //   config.setSwaggerUiPath("/swagger-ui.html");
////
////
////        return config;
////    }
//
//    //手动注入class
////    @Bean
////    public QueryUsrHdr queryUsrHdr( SessionFactory sessionFactory) {
////        // 确保 sessionFactory 被正确注入
////        return new QueryUsrHdr(sessionFactory);
////    }
//
//    @Bean
//    public CacheManager cacheManager() {
//        return new ConcurrentMapCacheManager("defaultCache");
//    }
//
//    @Bean
//    public ObjectMapper objectMapper() {
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new JavaTimeModule()); // 让 Jackson 支持 LocalDate
//        return mapper;
//    }
}
