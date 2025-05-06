//import handler.agt.AgtHdl;
//import handler.rechg.RechargeCallbackHdr;

//import org.noear.solon.annotation.SolonMain;
import cfg.MainStart;
import lombok.SneakyThrows;
import org.apache.commons.dbcp2.BasicDataSource;

import util.log.ConsoleInterceptor;
import util.misc.Flywayx;
//import service.AddRchgOrdToWltService;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

// static cfg.AppConfig.sessionFactory;
//import static cfg.Containr.evtlist4reg;
import static cfg.MainStart.iniContnr;
import static cfg.IniCfg.iniContnr4cfgfile;
import static cfg.MainStart.iniSysAcc;
import static cfg.WebSvr.*;
import static java.time.LocalTime.now;
import static util.evtdrv.EvtUtil.iniEvtHdrCtnr;


import static util.tx.dbutil.setField;
//import static cfg.IocPicoCfg.iniIocContainr;
//  System.out.flush();  // 刷新输出缓冲区
//            System.err.flush();  // 刷新输出缓冲区
//@SolonMain
//Scan("apiUsr")
public class MainApp {



    /**
     * -Ddbcfg=/cfg/dbcfg.ini
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {


        //    ovrtTEst=true;//todo cancel if test ok
        ConsoleInterceptor.init();// log
        System.out.println("fun main(), now= " + now());


        start();
        //cfg auth mode =jwt ,,,in apigateway




//        sleep(3000);
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


    //  url格式是  jdbc:mysql://localhost:3306/prjdb?user=root&password=pppppp
    private static String getPwdFrmDburl(String url) {
        int pwdIndex = url.indexOf("password=");
        if (pwdIndex == -1) return null;
        int endIndex = url.indexOf('&', pwdIndex);
        return endIndex == -1
                ? url.substring(pwdIndex + 9)
                : url.substring(pwdIndex + 9, endIndex);
    }

    private static String getUnameFrmDburl(String url) {
        int userIndex = url.indexOf("user=");
        if (userIndex == -1) return null;
        int endIndex = url.indexOf('&', userIndex);
        return endIndex == -1
                ? url.substring(userIndex + 5)
                : url.substring(userIndex + 5, endIndex);
    }

    private static void t1() {
//        Object AddMoneyToWltService1 = getBeanFrmSpr(AddMoneyToWltService.class);
      //  System.out.println("AddMoneyToWltService is :"+ AddMoneyToWltService1.getClass());

//
       //  Object bean = getBeanFrmSpr(RechargeCallbackHdr.class);
//        setField(bean,"addMoneyToWltService1", AddMoneyToWltService1);
//
//        System.out.println("bean.addMoneyToWltService1::"+getField(bean,"addMoneyToWltService1") );

//     //   bean.addMoneyToWltService1= (util.Iservice) AddMoneyToWltService1;
//      //  injectAll4spr(bean);
//        System.out.println("HttpHandler is:"+ bean);
    }

    /**
     *
     * @throws Exception
     */
    @SneakyThrows
    public static void start() throws Exception {
        //--------ini saveurlFrm Cfg

        iniContnr4cfgfile();
        fxSql();
         new MainStart().sessionFactory();//ini sessFctr
        //ini contnr 4cfg,, svrs
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
    }







    public static void openMap4test() {

        //  drvMap.put("com.mysql.cj.jdbc.Driver","org.h2.Driver");

    }

    static {
        try {
            iniContnr4cfgfile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        //   saveUrlOrdChrg
    }


}
