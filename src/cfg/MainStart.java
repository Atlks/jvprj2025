package cfg;//package cfg;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import org.hibernate.SessionFactory;
////import org.springdoc.core.properties.SpringDocConfigProperties;
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
import util.misc.Flywayx;
import entityx.usr.NonDto;
import lombok.SneakyThrows;
import model.OpenBankingOBIE.Account;
import model.OpenBankingOBIE.AccountSubType;
import model.OpenBankingOBIE.AccountType;
import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import service.YLwltSvs.AddMoney2YLWltService;
import service.wlt.RdsFromWltService;
import util.auth.SecurityContextImp4jwt;
import util.misc.Flywayx;
import util.tx.findByIdExptn_CantFindData;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.List;

import static cfg.Containr.saveDirUsrs;
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
import static util.evtdrv.EvtUtil.iniEvtHdrCtnr;
import static util.ioc.SimpleContainer.registerInstance;
import static util.misc.util2026.sleep;
import static util.tx.HbntUtil.*;

////启用 MBean（Managed Bean） 的导出，即 将 Spring 管理的 Bean 注册到 JMX（Java Management Extensions） 中，使其可以被 JMX 监控和管理。
public class MainStart {


    /**
     *
     * @throws Exception
     */
    @SneakyThrows
    public static void handleRequest(NonDto nonDto) throws Exception {

        ConsoleInterceptor.init();// log
        System.out.println("fun main(), now= " + now());

        //--------ini saveurlFrm Cfg

        iniContnr4cfgfile();
        fxSql();
        new MainStart().sessionFactory();//ini sessFctr


        //ini contnr 4cfg,, svrs
        //ioc ini
        iniContnr();
        iniEvtHdrCtnr();

        //   evtlist4reg.add(new AgtHdl()::regEvtHdl);
        //  AgtHdl

        //================== 创建 HTTP 服务器，监听端口8080
        iniRestPathMap();


        //------ini sys acc
        iniSysAcc();


        startWebSrv();
        System.out.println(11);

        sleep(3000);
        System.out.println("--------------------\n\n main()");
        System.out.println("main() exe finish, " + now());
        AutoRestartApp.main(null);
    }

    private static void fxSql() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/prjdb", "root", "pppppp");
        System.out.println("连接成功：" + conn.getMetaData().getURL());
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            System.out.println("JDBC Driver: " + drivers.nextElement().getClass().getName());
        }
        System.out.println("Driver: " + java.sql.DriverManager.getDrivers());

        // 使用 BasicDataSource 来包装 Connection
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/prjdb");
        dataSource.setUsername("root");
        dataSource.setPassword("pppppp");


        // Get a connection from the pool
        try (Connection connection = dataSource.getConnection()) {
            System.out.println("Connection Successful: " + connection.getMetaData().getURL());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 创建并配置 Flyway
        Flywayx flyway = Flyway_configure()
                .dataSource(dataSource)  // 直接传递 Connection 对象

                .locations("filesystem:sql") // 指向 SQL 脚本目录
                // .baselineOnMigrate(true) // If you are starting fresh with Flyway
                .load();


        // .dataSource("jdbc:mysql://localhost:3306/prjdb", getUnameFrmDburl(saveDirUsrs), getPwdFrmDburl(saveDirUsrs)) // 替换为你的实际信息

        // 执行迁移
        flyway.migrate();

        System.out.println("✅ 数据库字段删除迁移完成！");
    }


    private static Flywayx Flyway_configure() {
        return new Flywayx();
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
        if(sessionFactory==null)
        {
            List<Class> li = List.of();
            iniContnr4cfgfile();
            SessionFactory sessionFactory2 = getSessionFactory(saveDirUsrs, li);
            sessionFactory=sessionFactory2;
            sessionFactory=sessionFactory2;
            return sessionFactory;
        }else
            return  sessionFactory;

    }

    public static void iniAccInsFdPool_IfNotExist(String uname1) {

        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        String accid =getAccId(AccountSubType.insFdPl.name(),sysusrName);
        try{
            var wlt=findByHerbinate(Account.class, accid, session);
        } catch (findByIdExptn_CantFindData e) {

            Account acc1=new Account(accid);
            // .. acc1.userId= uname1;
            //   acc1.accountId=
            acc1.accountOwner = sysusrName;
            //  acc1.uname
            acc1.accountType= AccountType.BUSINESS;
            acc1.accountSubType=AccountSubType.insFdPl;
            persistByHibernate(acc1, session);

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
        Containr.SecurityContext1=new SecurityContextImp4jwt();
        //   Containr.chooseEvtPblshr=  new ChooseEvtPublshr();
        registerInstance( "RdsFromWltService",()->{
            return    new RdsFromWltService();
        });

        registerInstance( "AddMoney2YLWltService",()->{
            return    new AddMoney2YLWltService();
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
