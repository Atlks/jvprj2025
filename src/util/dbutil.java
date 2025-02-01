package util;

import apiAcc.OrdChrg;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import utilBiz.OrmUtilBiz;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static apiUsr.RegHandler.saveDirUsrs;
import static util.ColorLogger.*;
import static util.Fltr.fltr2501;
import static util.StrUtil.getPwdFromJdbcurl;
import static util.StrUtil.getUnameFromJdbcurl;
import static util.Util2025.*;
import static util.UtilEncode.encodeFilename;
import static util.util2026.*;

/**
 * *  saveDir  jdbc:ini
 * *                 /db2026/coll1    json doc
 * *                 lucene:
 * jdbc:sqltKV
 * redis:
 * <p>
 * *                 jdbc:sqlite:/db2026/usrs.db
 * *                mongodb:xxx
 * *                 jdbc:mysql
 */
public class dbutil {

    public static void main(String[] args) throws Exception {
        HashMap m = new HashMap();
        m.put("id", "id1");
        m.put("name", "nm1");
        //   addObj(m, "jdbc:ini:/db22/usrs");
        String sql = "select 1 as f";
        var url = "jdbc:sqlite:/dbx/db1.db";
        System.out.println(encodeJson(qrySql(sql, url)));
    }


    public static Object findObjs(String saveDir, Predicate<SortedMap<String, Object>> filter1) {

        //nullchk
        if (saveDir == null || saveDir.equals(""))
            return new ArrayList<>();


        List<SortedMap<String, Object>> result = findObjsAll(saveDir);

        if (result.isEmpty())
            return result;

        if (filter1 == null)
            return result;


        result = fltr2501(result, filter1);

        return result;

    }

    public static List<SortedMap<String, Object>> findObjsAll(String saveDir) {
        //null chk
        if (saveDir == null || saveDir.equals(""))
            return new ArrayList<>();

        if (saveDir.startsWith("jdbc:ini")) {
            saveDir = saveDir.substring(9);
            System.out.println("savedir=" + saveDir);
            //  return findObjsIni(saveDir, "");
        } else if (saveDir.startsWith("jdbc:lucene")) {
            saveDir = saveDir.substring(11);
            System.out.println("savedir=" + saveDir);
            return (List<SortedMap<String, Object>>) findObjsLucene(saveDir);
        } else if (saveDir.startsWith("jdbc:redis")) {
            saveDir = saveDir.substring(10);
            System.out.println("savedir=" + saveDir);
            // addObjRds(obj, collName, saveDir);
        } else if (saveDir.startsWith("json:")) {
            saveDir = saveDir.substring(6);
            System.out.println("savedir=" + saveDir);
            return findObjsJsDocdb(saveDir, "");
        } else {
            //ini doc
            return findObjsAllIni(saveDir);
        }

        return List.of();
    }

    private static Object findObjsLucene(String saveDir) {
        return null;
    }

    static List<SortedMap<String, Object>> findObjsAllIni(String saveDir) {

        //nullchk
        if (saveDir == null || saveDir.equals(""))
            return new ArrayList<>();


        List<SortedMap<String, Object>> result = getSortedMapsFrmINiFmt(saveDir);


        return result;


    }

    public static Object execQry2501(String saveUrl, HashMap<String, Function<Map<String, String>, Object>> mapFuns, Map<String, String> queryParams) {

        if (isSqldb(saveUrl)) {
            Function<Map<String, String>, Object> qryFun = mapFuns.get("sqldbFun");
            return qryFun.apply(queryParams);
        } else if (saveUrl.startsWith("lucene:")) {
            Function<Map<String, String>, Object> qryFun = mapFuns.get("luceneFun");
            return qryFun.apply(queryParams);
        } else {
            //json doc ,ini ,redis
            Function<Map<String, String>, Object> qryFun = mapFuns.get("arrFun");
            return qryFun.apply(queryParams);
        }
    }

    public static Object execQry(String saveUrl, HashMap<String, Function<Map<String, String>, Object>> map) {


        Map<String, String> queryParams = Map.of();
        if (isSqldb(saveUrl)) {
            Function<Map<String, String>, Object> qryFun = map.get("sqldbFun");
            return qryFun.apply(queryParams);
        } else if (saveUrl.startsWith("lucene:")) {
            Function<Map<String, String>, Object> qryFun = map.get("luceneFun");
            return qryFun.apply(queryParams);
        } else {
            //json doc ,ini ,redis
            Function<Map<String, String>, Object> qryFun = map.get("arrFun");
            return qryFun.apply(queryParams);
        }


//        if (isSqldb(saveUrlOrdBet))             {
//            return qryOrdBetSql(queryParams);
//        } else if (saveUrlOrdBet.startsWith("lucene:")) {
//            return qryuserLucene(queryParams);
//        } else {
//            //json doc ,ini ,redis
//            return qryOrdBetIni(queryParams);
//        }

    }


    /**
     * @param obj
     * @param saveDir jdbc:ini
     *                /db2026/    json doc
     *                lucene:
     *                jdbc:sqlite:/db2026/usrs.db
     *                redis:
     *                jdbc:mysql
     * @return
     * @throws Exception
     */
    public static Object addObj(Object obj, String saveDir, Class class1) throws Exception {
        System.out.println("\r\n\r\n");
        //   System.out.println("fun addobj(");
        String runEmoji = "▶️";
        printLn("▶️fun addobj(", BLUE);
        printLn("obj=" + encodeJson(obj), GREEN);
        printLn("saveDir=" + saveDir, GREEN);
        printLn("class1=" + class1, GREEN);
        System.out.println(")");
        String collName = "";
        String rzt = "";
        //chk empty null
        if (saveDir.equals(""))
            throw new RuntimeException("prm savadir/url=''");

        if (saveDir.endsWith(".db")) {
            String s = addObjSqlt(obj, saveDir);
            System.out.println("endfun addobj().rzt=" + s);
            return s;
        } else if (saveDir.startsWith("jdbc:h2")) {
            String s = addObjMysql(obj, saveDir);
            System.out.println("endfun addobj().rzt=" + s);
            return s;
        } else if (saveDir.startsWith("json:")) {
            saveDir = saveDir.substring(5);
            System.out.println("savedir=" + saveDir);
            addObjDocdb(obj, saveDir);


        } else if (saveDir.startsWith("jdbc:lucene")) {
            saveDir = saveDir.substring(11);
            System.out.println("savedir=" + saveDir);
            //addObjLucene(obj, collName, saveDir);
        } else if (saveDir.startsWith("jdbc:redis")) {
            saveDir = saveDir.substring(10);
            System.out.println("savedir=" + saveDir);
            addObjRds(obj, collName, saveDir);
        } else if (saveDir.startsWith("jdbc:mysql")) {
            String s = addObjMysql(obj, saveDir);
            System.out.println("endfun addobj().rzt=" + s);
            return s;
        } else {
            //if (saveDir.startsWith("ini:"))
            //ini doc
            saveDir = saveDir.substring(0);
            System.out.println("savedir=" + saveDir);
            addObjIni(obj, saveDir);
        }

        System.out.println("endfun addobj()");
        return "";
    }

    @Deprecated
    public static String addObj(Object obj, String saveDir) throws Exception {
        System.out.println("\r\n\r\n");
        System.out.println("fun addobj(");
        System.out.println("obj=" + encodeJson(obj));
        System.out.println("saveDir=" + saveDir);
        System.out.println(")");
        String collName = "";
        String rzt = "";
        if (saveDir.endsWith(".db")) {
            String s = addObjSqlt(obj, saveDir);
            System.out.println("endfun addobj().rzt=" + s);
            return s;
        } else if (saveDir.startsWith("jdbc:h2")) {
            String s = addObjMysql(obj, saveDir);
            System.out.println("endfun addobj().rzt=" + s);
            return s;
        } else if (saveDir.startsWith("json:")) {
            saveDir = saveDir.substring(5);
            System.out.println("savedir=" + saveDir);
            addObjDocdb(obj, saveDir);


        } else if (saveDir.startsWith("jdbc:lucene")) {
            saveDir = saveDir.substring(11);
            System.out.println("savedir=" + saveDir);
            //addObjLucene(obj, collName, saveDir);
        } else if (saveDir.startsWith("jdbc:redis")) {
            saveDir = saveDir.substring(10);
            System.out.println("savedir=" + saveDir);
            addObjRds(obj, collName, saveDir);
        } else if (saveDir.startsWith("jdbc:mysql")) {
            String s = addObjMysql(obj, saveDir);
            System.out.println("endfun addobj().rzt=" + s);
            return s;
        } else {
            //if (saveDir.startsWith("ini:"))
            //ini doc
            saveDir = saveDir.substring(0);
            System.out.println("savedir=" + saveDir);
            addObjIni(obj, saveDir);
        }

        System.out.println("endfun addobj()");
        return "";
    }

//    private static Object addObjHbnt(Object obj, String saveDir, Class class1) throws Exception {
//        printLn("️▶️fun addObjHbnt(", BLUE);
//        printLn("saveDir="+saveDir, GREEN);
//        printLn("class1="+class1, GREEN);
//        printLn("))", BLUE);
//        var jdbcUrl = saveDir.substring(5);
//
//        SessionFactory sessionFactory = getSessionFactory(class1, jdbcUrl);
//
//        // 获取 Session
//        Session session = sessionFactory.openSession();
//        session.beginTransaction();
//
//        // 示例操作
//      //  session.find()
//        session.merge(obj);
//
//        session.getTransaction().commit();
//        session.close();
//
//        // 关闭 SessionFactory
//        sessionFactory.close();
//
//        printLn("endfun addObjHbnt()", BLUE);
//        return obj;
//    }
//
//    private static SessionFactory getSessionFactory(Class class1, String jdbcUrl) throws SQLException {
//        var db = getDatabaseFileName4mysql(jdbcUrl);
//        crtDatabase(jdbcUrl, db);
//
//        // Hibernate 配置属性
//        Properties properties = new Properties();
//        properties.put(Environment.DRIVER, getDvr(jdbcUrl));
//        properties.put(Environment.URL, "" + jdbcUrl);
//        properties.put(Environment.USER, getUnameFromJdbcurl(jdbcUrl));
//        properties.put(Environment.PASS, getPwdFromJdbcurl(jdbcUrl));
//        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
//        //    hibernate.dialect.storage_engine
//        properties.put(Environment.SHOW_SQL, "true");
//        properties.put(Environment.FORMAT_SQL, "true");
//        properties.put(Environment.STORAGE_ENGINE, "innodb");
//        //HBM2DDL_CHARSET_NAME
//        properties.put(Environment.HBM2DDL_AUTO, "update"); // 自动建表
//        properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
//
//        // 创建 ServiceRegistry
//        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
//                .applySettings(properties)
//                .build();
//
//        // 添加实体类映射
//        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
//        metadataSources.addAnnotatedClass(class1); // 你的实体类
//
//        // 创建 SessionFactory
//        SessionFactory sessionFactory = metadataSources.buildMetadata().buildSessionFactory();
//        return sessionFactory;
//    }

//    private static String getPwdFromJdbcurl(String jdbcUrl) {
//        return getCredentialFromJdbcUrl(jdbcUrl, "password");
//    }
//
//    private static String getUnameFromJdbcurl(String jdbcUrl) {
//        return getCredentialFromJdbcUrl(jdbcUrl, "user");
//    }

    private static String getCredentialFromJdbcUrlUPhostMod(String jdbcUrl, String type) {
        // 使用正则表达式解析 "用户名:密码@host" 部分
        Pattern pattern = Pattern.compile("jdbc:mysql://([^:@]+):([^:@]+)@");
        Matcher matcher = pattern.matcher(jdbcUrl);

        if (matcher.find()) {
            if ("user".equals(type)) {
                return matcher.group(1); // 获取用户名
            } else if ("password".equals(type)) {
                return matcher.group(2); // 获取密码
            }
        }
        return null; // 如果匹配失败，返回 null
    }

    public static String getDvr(String jdbcUrl) {
        if (jdbcUrl.startsWith("jdbc:mysql"))
            return "com.mysql.cj.jdbc.Driver";
        return "sqlt";
    }

//    private static String addObjHbntCfgmd(Object obj, String saveDir, Class class1) {
//        var hbntcfg = saveDir.substring(5);
//        SessionFactory factory = new Configuration()
//                .configure(hbntcfg)
//                .addAnnotatedClass(class1)
//                .buildSessionFactory();
//
//        // 创建 Session
//        Session session = factory.getCurrentSession();
//
//
//        // 1. 保存数据

    /// /            OrdBet newUser = new OrdBet();
    /// /            newUser.uname="John Doe2";
    /// /            newUser.id=newUser.uname;
//        //  newUser.setEmail("john.doe@example.com");
//
//        // 开启事务
//        session.beginTransaction();
//
//        // 保存用户
//        session.save(obj);
//
//        // 提交事务
//        session.getTransaction().commit();
//        return "";
//    }
    public static Object updtObj(Object obj, String saveDir, Class class1) throws Exception {
        System.out.println("\r\n\r\n");
        System.out.println("fun updtObj(");
        System.out.println("obj=" + encodeJson(obj));
        System.out.println("saveDir=" + saveDir);
        System.out.println(")");
        String collName = "";
        var rzt = "";
        if (saveDir.endsWith(".db")) {

            rzt = updtObjSqlt(obj, saveDir);
            System.out.println("endfun updtObj().rzt=" + rzt);
            return rzt;

        } else if (saveDir.startsWith("hbnt:")) {
            //  return updtByHbnt(obj, saveDir, class1);
        } else if (saveDir.startsWith("json:")) {
            saveDir = saveDir.substring(5);
            System.out.println("savedir=" + saveDir);
            addObjDocdb(obj, saveDir);


        } else if (saveDir.startsWith("jdbc:lucene")) {
            saveDir = saveDir.substring(11);
            System.out.println("savedir=" + saveDir);
            //addObjLucene(obj, collName, saveDir);
        } else if (saveDir.startsWith("jdbc:redis")) {
            saveDir = saveDir.substring(10);
            System.out.println("savedir=" + saveDir);
            addObjRds(obj, collName, saveDir);
        } else if (saveDir.startsWith("jdbc:mysql")) {
            rzt = updtObjSqlt(obj, saveDir);
            System.out.println("endfun updtObj().rzt=" + rzt);
            return rzt;
        } else {
            //if (saveDir.startsWith("ini:"))
            //ini doc
            saveDir = saveDir.substring(0);
            System.out.println("savedir=" + saveDir);
            addObjIni(obj, saveDir);
        }

        System.out.println("endfun updtObj().rzt=" + rzt);
        return rzt;
    }

//    private static Object updtByHbnt(Object obj, String saveDir, Class class1) {
//        var jdbcUrl = saveDir.substring(5);
//
//        try (Session session = getSessionFactory(class1, jdbcUrl).openSession()) {
//            Transaction tx = session.beginTransaction();
//
//
//            session.merge(obj);  // 合并到持久化上下文
//            tx.commit();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

    /// /        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
    /// /            Transaction tx = session.beginTransaction();
    /// /
    /// /            User user = session.get(User.class, userId);  // 先获取对象
    /// /            if (user != null) {
    /// /                user.setName(newName);  // 修改字段
    /// /                session.update(user);   // 更新对象
    /// /            }
    /// /
    /// /            tx.commit();
    /// /        }
//
//        return obj;
//    }
    @Deprecated
    public static String updtObj(Object obj, String saveDir) throws Exception {
        System.out.println("\r\n\r\n");
        System.out.println("fun updtObj(");
        System.out.println("obj=" + encodeJson(obj));
        System.out.println("saveDir=" + saveDir);
        System.out.println(")");
        String collName = "";
        var rzt = "";
        if (saveDir.endsWith(".db")) {

            rzt = updtObjSqlt(obj, saveDir);
            System.out.println("endfun updtObj().rzt=" + rzt);
            return rzt;

        } else if (saveDir.startsWith("json:")) {
            saveDir = saveDir.substring(5);
            System.out.println("savedir=" + saveDir);
            addObjDocdb(obj, saveDir);


        } else if (saveDir.startsWith("jdbc:lucene")) {
            saveDir = saveDir.substring(11);
            System.out.println("savedir=" + saveDir);
            //addObjLucene(obj, collName, saveDir);
        } else if (saveDir.startsWith("jdbc:redis")) {
            saveDir = saveDir.substring(10);
            System.out.println("savedir=" + saveDir);
            addObjRds(obj, collName, saveDir);
        } else if (saveDir.startsWith("jdbc:mysql")) {
            rzt = updtObjSqlt(obj, saveDir);
            System.out.println("endfun updtObj().rzt=" + rzt);
            return rzt;
        } else {
            //if (saveDir.startsWith("ini:"))
            //ini doc
            saveDir = saveDir.substring(0);
            System.out.println("savedir=" + saveDir);
            addObjIni(obj, saveDir);
        }

        System.out.println("endfun updtObj().rzt=" + rzt);
        return rzt;
    }

    private static void addObjRds(Object obj, String collName, String saveDir) {
    }


    private static void addObjIni(Object obj, String saveDir) {
        mkdir2025(saveDir);
        String fname = (String) getField2025(obj, "id", "");
        //todo need fname encode
        fname = encodeFilename(fname) + ".ini";
        String fnamePath = saveDir + "/" + fname;
        System.out.println("fnamePath=" + fnamePath);
        writeFile2501(fnamePath, encodeIni(obj));

    }


    /**
     * 遍历Map属性，转化为ini格式，组成字符串
     *
     * @param map 需要转换为INI格式的Map
     * @return INI格式字符串
     */
    private static String encodeIniMap(Map<String, Object> map) {
        StringBuilder iniString = new StringBuilder();

        // 遍历Map中的所有键值对
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            // 处理值为 null 的情况
            if (value != null) {
                // 将键值对格式化为 key=value 并添加到INI字符串中
                iniString.append(key)
                        .append("=")
                        .append(value.toString())
                        .append(System.lineSeparator());
            }
        }

        return iniString.toString();
    }

    /**
     * 遍历对象属性，转化为ini格式，组成字符串
     *
     * @param obj
     * @return
     */
    static String encodeIni(Object obj) {

        if (obj instanceof Map) {
            return encodeIniMap((Map<String, Object>) obj);
        }
        StringBuilder iniString = new StringBuilder();

        // 获取对象的所有字段（属性）
        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {
            try {
                // 设置字段可访问，即使是private字段也能访问
                field.setAccessible(true);

                // 获取字段的名字和字段的值
                String fieldName = field.getName();
                Object fieldValue = field.get(obj);

                // 处理字段值为 null 的情况
                if (fieldValue != null) {
                    // 将字段名和值添加到INI字符串中
                    iniString.append(fieldName)
                            .append("=")
                            .append(fieldValue.toString())
                            .append(System.lineSeparator());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return iniString.toString();
    }

    /**
     * // 调用 getObjAsT 方法
     * ExampleClass obj = getObjAsT("123", "/path/to/saveDir", ExampleClass.class);
     * 将读取到的 SortedMap<String, Object> 转换为给定的class
     *
     * @param id
     * @param saveDir
     * @return
     */
    public static <T> T getObjById(String id, String saveDir, Class<T> cls) throws Exception {

        if (saveDir.endsWith(".db")) {
            SortedMap<String, Object> objSqlt = getObjSqlt(id, saveDir);
//            if(objSqlt.isEmpty())
//                return
            return toObj(objSqlt, cls);
        } else if (saveDir.startsWith("jdbc:mysql")) {
            //  return (T) getObjMysql(id, saveDir);
            SortedMap<String, Object> objSqlt = getMapMysql(id, saveDir);
            return toObj(objSqlt, cls);

        } else if (saveDir.startsWith("hbnt:")) {
            //  return findByHbnt(id, saveDir, cls);
        } else {
            return (T) getObjIni(id, saveDir);
        }


        return null;
    }

//    private static <T> T findByHbnt(String id, String saveDir, Class<T> class1) throws Exception {
//
//        var jdbcUrl = saveDir.substring(5);
//
//        SessionFactory sessionFactory = getSessionFactory(class1, jdbcUrl);
//
//        try (Session session = sessionFactory.openSession()) {
//            return session.find(class1, id);
//        }
//
//    }

    // Java 中的泛型在运行时是类型擦除的，因此无法直接使用 T.class，这会导致编译错误。为了修复并正确调用该方法，需要通过调用者显式传入 Class<T>，以便在运行时保留类型信息。
//    public static <T> T getObjAsT(String id, String saveDir) {
//
//        if (saveDir.endsWith(".db")) {
//            SortedMap<String, Object> objSqlt = getObjSqlt(id, saveDir);
//            //这里bug，如何修复
//            return  toConvert(objSqlt, T.class ) ;
//        } else if (saveDir.startsWith("jdbc:mysql")) {
//            return (T) getObjSqlt(id, saveDir);
//        } else {
//            return (T) getObjIni(id, saveDir);
//        }
//    }

    public static void setPrmts4sql(Map<String, Object> sqlprmMap, NativeQuery<?> nativeQuery) {
        for (Map.Entry<String, Object> entry : sqlprmMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (Objects.equals(key, "sql"))
                continue;
            System.out.println("setprm key=" + key + ",v=" + value);
            nativeQuery = nativeQuery
                    .setParameter(key, value);
        }

    }

    public static <T> List<T> getQrySql(String sql, Map<String, Object> sqlprmMap, Session session, Class<T> cls) {
        // 防止 SQL 注入  // 安全参数绑定
        NativeQuery<?> nativeQuery = session.createNativeQuery(sql, cls);
        setPrmts4sql(sqlprmMap, nativeQuery);

    //    nativeQuery.getResultCount();
        // 设置分页
//        nativeQuery.setFirstResult(getstartPosition(pageNumber, pageSize));
//        nativeQuery.setMaxResults(pageSize);
        //       .setParameter("age", 18);
        List<T> lst = (List<T>) nativeQuery.getResultList();
        return lst;
    }

    public static List<?> getQrySql(String sql, Map<String, Object> sqlprmMap, int pageNumber, int pageSize, Session session) {
        // 防止 SQL 注入  // 安全参数绑定
        NativeQuery<?> nativeQuery = session.createNativeQuery(sql, OrdChrg.class);
        setPrmts4sql(sqlprmMap, nativeQuery);

        nativeQuery.getResultCount();
        // 设置分页
        nativeQuery.setFirstResult(getstartPosition(pageNumber, pageSize));
        nativeQuery.setMaxResults(pageSize);
        //       .setParameter("age", 18);
        List<?> lst = nativeQuery.getResultList();
        return lst;
    }

    public static int getstartPosition(int pageNumber, int pageSize) {

        int startPosition = (pageNumber - 1) * pageSize; // 计算起始行
        return startPosition;
    }

    /**
     * 错误的 COUNT(*) 查询 👉 COUNT(*) 语句不能直接嵌套在 FROM (...)，如果 sql 本身包含 ORDER BY，可能会报错。
     *
     * @param sql
     * @param sqlprmMap
     * @param list1
     * @param pageSize
     * @return
     * @throws SQLException
     */
    public static PageResult<SortedMap<String, Object>> getPageResult(String sql, Map<String, Object> sqlprmMap, List list1, long pageSize) throws SQLException {
        String countSql = "SELECT COUNT(*) FROM (" + sql + ") t";
        System.out.println("countSql=" + countSql);
        org.hibernate.Session session = OrmUtilBiz.openSession(saveDirUsrs);

        NativeQuery nativeQuery = session.createNativeQuery(countSql);
        setPrmts4sql(sqlprmMap, nativeQuery);
        long totalRecords = ((Number) nativeQuery
//                .setParameter(1, username)
//                .setParameter(2, minAge)
                .getSingleResult()).longValue();

        long totalPages = (long) Math.ceil((double) totalRecords / pageSize);
        return new PageResult<>(list1, totalRecords, totalPages);
    }

    public static <T> PageResult<T> getPageResult(List<T> list1, int pageSize, int pageNumber) {
        long totalRecords = list1.size();
        long totalPages = (long) Math.ceil((double) totalRecords / pageSize);


        int fromIndex = getstartPosition(pageNumber, pageSize);
     if( fromIndex+1>list1.size())
     {
         //ret
         return new PageResult<>(new ArrayList<>(), totalRecords, totalPages);
     }

     //   List<T> listPageed=new ArrayList<>();

        //last page
        int endidx = fromIndex + pageSize;
       if(endidx>(list1.size()-1))
       {
           endidx=list1.size();
         //  List<T> listPageed = list1.subList(fromIndex, endidx);
         //  return new PageResult<>(listPageed, totalRecords, totalPages);
       }

        System.out.println( "sublist frmidx="+fromIndex+",endidx="+endidx);
        List<T> listPageed = list1.subList(fromIndex, endidx);
        return new PageResult<>(listPageed, totalRecords, totalPages);
    }

    public static List<SortedMap<String, Object>> getSortedMapsBypages(String sql, long pageSize, long pageNumber) throws Exception {
        var sql_limt = sql + " LIMIT " + pageSize + " OFFSET " + (pageNumber - 1) * pageSize;
        System.out.println(sql_limt);
        var list1 = qrySql(sql_limt, saveDirUsrs);
        return list1;
    }

    /**
     * 来将 Map 转换为对象
     *
     * @param sortedMap
     * @param cls
     * @param <T>
     * @return
     */
    static <T> T toObj(SortedMap<String, Object> sortedMap, Class<T> cls) throws Exception {
        // 将 Map 转换为 JSON 字符串，再反序列化为指定类型的对象
//        String jsonString = JSON.toJSONString(obj);
//        return JSON.parseObject(jsonString, cls);

        //    SortedMap<String, Object> sortedMap = new TreeMap<>();
        if (sortedMap == null || cls == null) {
            throw new IllegalArgumentException("toObj().sortedMap and cls cannot be null.");
        }
        // 使用反射创建目标对象实例
        T obj = cls.getDeclaredConstructor().newInstance();


        // 遍历 Map 并设置字段值
        for (Map.Entry<String, Object> entry : sortedMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            setField(obj, key, value);
        }

        return obj;


    }

    /**
     * 给对象属性设置值
     *
     * @param obj
     * @param key
     * @param value
     * @param <T>
     */
    private static <T> void setField(T obj, String key, Object value) {

        if (obj == null || key == null) {
            throw new IllegalArgumentException("fun  setField().Object, key, and value cannot be null.");
        }

        // 获取 obj 的所有字段
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field fld : fields) {
            if (fld.getName().equalsIgnoreCase(key)) { // 忽略大小写比较字段名
                try {
                    if (key.toLowerCase().equals("balance"))
                        System.out.println("d1025");
                    fld.setAccessible(true); // 确保字段可访问
                    fld.set(obj, value); // 设置字段值
                } catch (IllegalAccessException e) {
                    //  throw new RuntimeException("Failed to set field value: " + key, e);
                }
                return; // 找到匹配字段后可以直接返回
            }
        }

    }

    @Deprecated
    public static SortedMap<String, Object> getObj(String id, String saveDir) {

        if (saveDir.endsWith(".db")) {
            return getObjSqlt(id, saveDir);
        } else if (saveDir.startsWith("jdbc:mysql") || saveDir.startsWith("jdbc:h2")) {
            return getObjSqlt(id, saveDir);
        } else {
            return getObjIni(id, saveDir);
        }


    }

    public static SortedMap<String, Object> getObjIni(String id, String saveDir) {

        mkdir2025(saveDir);
        //encodeFilName todo
        String fname = id + ".ini";
        String fnamePath = saveDir + "/" + fname;
        if (new File(fnamePath).exists()) {
            String text = readTxtFrmFil(fnamePath);
            System.out.println("getobjDoc().txt=" + text);
            return toMapFrmInicontext(text);
        }

        //   if(!new File(fnamePath).exists())
        else
            return toMapFrmInicontext("");

    }


    /**
     * @param saveDir
     * @param qryExpression
     * @return
     */
    public static List<SortedMap<String, Object>> findObjsJsDocdb(String saveDir, String qryExpression) {

        //nullchk
        if (saveDir == null || saveDir.equals(""))
            return new ArrayList<>();


        List<SortedMap<String, Object>> result = getSortedMaps(saveDir);


        if (qryExpression == null || qryExpression.equals(""))
            return result;
        if (result.isEmpty())
            return result;

        //  result = filterWithSpEL(result, qryExpression);

        return result;
    }

    public static Object qrySqlAsValScalar(String sql, String jdbcUrl) {
        SortedMap<String, Object> stringObjectSortedMap = qrySqlAsMap(sql, jdbcUrl);
        if (stringObjectSortedMap.isEmpty())
            return "";
        return stringObjectSortedMap.get(0);
    }

    public static SortedMap<String, Object> qrySqlAsMap(String sql, Connection Connection1) {
        try {
            List<SortedMap<String, Object>> sortedMaps = qrySql(sql, Connection1);
            if (sortedMaps.size() == 0)
                return new TreeMap<>();
            return sortedMaps.get(0);
        } catch (Exception e) {
            throwEx(e);

        }
        return new TreeMap<>();
    }

    public static SortedMap<String, Object> qrySqlAsMap(String sql, String jdbcUrl) {
        try {
            List<SortedMap<String, Object>> sortedMaps = qrySql(sql, jdbcUrl);
            if (sortedMaps.size() == 0)
                return new TreeMap<>();
            return sortedMaps.get(0);
        } catch (Exception e) {
            throwEx(e);

        }
        return new TreeMap<>();
    }

    public static List<SortedMap<String, Object>> qrySql(String sql, Connection conn) throws Exception {
        System.out.println("\r\n");
        System.out.println("fun qrysql(");
        System.out.println("sql=" + sql);

        System.out.println("))");
//        if (jdbcUrl.startsWith("jdbc:sqlite"))
//            Class.forName("org.sqlite.JDBC");
//        else if (jdbcUrl.startsWith("jdbc:mysql"))
//            Class.forName(toTrueDvr("com.mysql.cj.jdbc.Driver"));
//
//        else if (jdbcUrl.startsWith("jdbc:h2"))
//            Class.forName("org.h2.Driver");
        //    mkdir2025(saveDir);
        //    String url = "jdbc:sqlite:" + saveDir + collName + ".db";
        // 建立连接
        //   Connection conn = DriverManager.getConnection(jdbcUrl);
        Statement stmt = null;
        ResultSet rs = null;

        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);

        // 转换 ResultSet 为 List<SortedMap<String, Object>>
        List<SortedMap<String, Object>> mapList = toMapList(rs);
        //System.out.println("");
        System.out.println("endfun qrysql().ret=[" + mapList.size() + "]," + encodeJson(get0(mapList)));
        return mapList;


    }

    public static List<SortedMap<String, Object>> qrySql(String sql, String jdbcUrl) throws Exception {
        System.out.println("\r\n");
        System.out.println("fun qrysql(");
        System.out.println("sql=" + sql);
        System.out.println("jdbcUrl=" + jdbcUrl);
        System.out.println("))");
        if (jdbcUrl.startsWith("jdbc:sqlite"))
            Class.forName("org.sqlite.JDBC");
        else if (jdbcUrl.startsWith("jdbc:mysql"))
            Class.forName(toTrueDvr("com.mysql.cj.jdbc.Driver"));

        else if (jdbcUrl.startsWith("jdbc:h2"))
            Class.forName("org.h2.Driver");
        //    mkdir2025(saveDir);
        //    String url = "jdbc:sqlite:" + saveDir + collName + ".db";
        // 建立连接
        Connection conn = DriverManager.getConnection(jdbcUrl);
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            // 转换 ResultSet 为 List<SortedMap<String, Object>>
            List<SortedMap<String, Object>> mapList = toMapList(rs);
            //System.out.println("");
            System.out.println("endfun qrysql().ret=[" + mapList.size() + "]," + encodeJson(get0(mapList)));
            return mapList;

        } catch (SQLSyntaxErrorException e) {
            if (e.getMessage().startsWith("Table") && e.getMessage().contains("doesn't exist")) {
                System.out.println("endfun qrysql().ret=[0]");
                return new ArrayList<>();
            }
            e.printStackTrace();
        } catch (Exception e) {
            if (e.getMessage().contains("no such table")) {
                System.out.println("endfun qrysql().ret=[0]");
                return new ArrayList<>();
            }
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
        System.out.println("endfun qrysql().ret=[0]");
        return List.of();
    }

    private static String toTrueDvr(String k) {

        String s1 = drvMap.get(k);
        if (s1 != null)
            return s1;
        return k;
    }

    public static Map<String, String> drvMap = new HashMap<>();

    private static Object get0(List<SortedMap<String, Object>> mapList) {
        if (mapList.isEmpty())
            return new TreeMap<>();
        return mapList.get(0);
    }
// // 转换 ResultSet 为 List<SortedMap<String, Object>>

    /**
     * MapListHandler 是 Apache Commons DbUtils 库中的一个处理器类，主要用于将 SQL 查询结果 (ResultSet) 转换为 List<Map<String, Object>> 的形式，其中每个 Map 代表结果集的一行，键为列名，值为列对应的值。
     * MapListHandler 的主要功能是简化 ResultSet 的处理，将其转换为更易于操作的 Java 集合结构。
     *
     * @param rs
     * @return
     * @throws Exception
     */
    private static List<SortedMap<String, Object>> toMapList(ResultSet rs) throws Exception {
        List<SortedMap<String, Object>> resultList = new ArrayList<>();
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        while (rs.next()) {
            SortedMap<String, Object> rowMap = new TreeMap<>();

            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                Object columnValue = rs.getObject(i);
                rowMap.put(columnName, columnValue);
            }

            resultList.add(rowMap);
        }

        return resultList;
    }

    private static List<SortedMap<String, Object>> getSortedMapsFrmINiFmt(String saveDir) {
        mkdir2025(saveDir);
        //encodeFilName todo


        //遍历目录dir，读取每一个文件，并解析为SortedMap<String, Objects>
        //最终返回一个List<SortedMap<String, Objects>>
        List<SortedMap<String, Object>> result = new ArrayList<>();

        try {
            // 遍历目录中的所有文件
            Files.list(Paths.get(saveDir))
                    .filter(Files::isRegularFile)
                    .forEach(filePath -> {
                        try {
                            // 解析文件内容为 SortedMap<String, Object>
                            SortedMap<String, Object> fileData = parseFileToSortedMapFrmIni(filePath);
                            result.add(fileData);
                        } catch (Exception e) {
                            System.err.println("Error processing file: " + filePath + " - " + e.getMessage());
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            System.err.println("Error reading directory: " + saveDir + " - " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    private static List<SortedMap<String, Object>> getSortedMaps(String saveDir) {
        mkdir2025(saveDir);
        //encodeFilName todo


        //遍历目录dir，读取每一个文件，并解析为SortedMap<String, Objects>
        //最终返回一个List<SortedMap<String, Objects>>
        List<SortedMap<String, Object>> result = new ArrayList<>();

        try {
            // 遍历目录中的所有文件
            Files.list(Paths.get(saveDir))
                    .filter(Files::isRegularFile)
                    .forEach(filePath -> {
                        try {
                            // 解析文件内容为 SortedMap<String, Object>
                            SortedMap<String, Object> fileData = parseFileToSortedMap(filePath);
                            result.add(fileData);
                        } catch (Exception e) {
                            System.err.println("Error processing file: " + filePath + " - " + e.getMessage());
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            System.err.println("Error reading directory: " + saveDir + " - " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * //遍历目录saveDir+collName，读取每一个文件，并解析为SortedMap<String, Objects>
     *
     * @param collName
     * @param saveDir
     * @return
     */
//    public static List<SortedMap<String, Object>> getObjsDocdb(String collName, String saveDir) {
//
//        mkdir2025(saveDir + collName);
//        //encodeFilName todo
//
//        String dir = saveDir + collName;
//
//        //遍历目录dir，读取每一个文件，并解析为SortedMap<String, Objects>
//        //最终返回一个List<SortedMap<String, Objects>>
//        List<SortedMap<String, Object>> result = new ArrayList<>();
//
//        try {
//            // 遍历目录中的所有文件
//            Files.list(Paths.get(dir))
//                    .filter(Files::isRegularFile)
//                    .forEach(filePath -> {
//                        try {
//                            // 解析文件内容为 SortedMap<String, Object>
//                            SortedMap<String, Object> fileData = parseFileToSortedMap(filePath);
//                            result.add(fileData);
//                        } catch (Exception e) {
//                            System.err.println("Error processing file: " + filePath + " - " + e.getMessage());
//                            e.printStackTrace();
//                        }
//                    });
//        } catch (IOException e) {
//            System.err.println("Error reading directory: " + dir + " - " + e.getMessage());
//            e.printStackTrace();
//        }
//
//        return result;
//    }


    /**
     * 将一个简单的ini文件，没有节，转换为SortedMap<String, Object>
     *
     * @param filePath ini文件路径
     * @return
     * @throws IOException
     */
    private static SortedMap<String, Object> parseFileToSortedMapFrmIni(Path filePath) throws IOException {
        // 读取文件内容为字符串
        String iniFileContent = Files.readString(filePath);


        return toMapFrmInicontext(iniFileContent);


    }

    static SortedMap<String, Object> toMapFrmInicontext(String iniFileContent) {
        // 创建SortedMap来存储键值对
        SortedMap<String, Object> map = new TreeMap<>();

        // 按行分割文件内容
        String[] lines = iniFileContent.split(System.lineSeparator());

        // 遍历每一行
        for (String line : lines) {
            // 忽略空行和注释行
            if (line.trim().isEmpty() || line.trim().startsWith(";") || line.trim().startsWith("#")) {
                continue;
            }

            // 分割键和值
            String[] parts = line.split("=", 2);
            if (parts.length == 2) {
                // 将键和值放入映射中
                String key = parts[0].trim();
                String value = parts[1].trim();
                map.put(key, value);
            }
        }

        return map;
    }

    /**
     * 将文件解析为 SortedMap<String, Object>
     * 文件内容是一个json对象
     *
     * @param filePath 文件路径
     * @return SortedMap<String, Object>
     */
    private static SortedMap<String, Object> parseFileToSortedMap(Path filePath) throws IOException {
        // 读取文件内容为字符串
        String jsonContent = Files.readString(filePath);

        // 使用 FastJSON2 解析 JSON 字符串为 JSONObject
        JSONObject jsonObject = JSON.parseObject(jsonContent);

        // 转换 JSONObject 为 SortedMap
        return convertJSONObjectToSortedMap(jsonObject);
    }

    /**
     * 将 JSONObject 转换为 SortedMap<String, Object>
     *
     * @param jsonObject FastJSON2 的 JSONObject
     * @return SortedMap<String, Object>
     */
    private static SortedMap<String, Object> convertJSONObjectToSortedMap(JSONObject jsonObject) {
        SortedMap<String, Object> sortedMap = new TreeMap<>();

        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof JSONObject) {
                sortedMap.put(key, convertJSONObjectToSortedMap((JSONObject) value));
            } else if (value instanceof JSONArray) {
                sortedMap.put(key, convertJSONArrayToList((JSONArray) value));
            } else {
                sortedMap.put(key, value);
            }
        }

        return sortedMap;
    }

    /**
     * 将 JSONArray 转换为 List<Object>
     *
     * @param jsonArray FastJSON2 的 JSONArray
     * @return List<Object>
     */
    private static List<Object> convertJSONArrayToList(JSONArray jsonArray) {
        List<Object> list = new ArrayList<>();
        for (Object item : jsonArray) {
            if (item instanceof JSONObject) {
                list.add(convertJSONObjectToSortedMap((JSONObject) item));
            } else if (item instanceof JSONArray) {
                list.add(convertJSONArrayToList((JSONArray) item));
            } else {
                list.add(item);
            }
        }
        return list;
    }

    public static void updateObjIni(JSONObject obj, String saveDir) {
        addObjIni(obj, saveDir);
//        mkdir2025(saveDir+collName);
//        String fname=getField2025(obj,"id","");
//        //todo need fname encode
//        fname=fname+".json";
//        String fnamePath=saveDir+collName+"/"+fname;
//        System.out.println("fnamePath="+fnamePath);
//        writeFile2024(fnamePath,encodeJson(obj));

    }

    /**
     * just wrt file same as addobj
     *
     * @param obj
     * @param collName
     * @param saveDir
     */
    public static void updateObjDocdb(JSONObject obj, String collName, String saveDir) {
        addObjDocdb(obj, saveDir);
//        mkdir2025(saveDir+collName);
//        String fname=getField2025(obj,"id","");
//        //todo need fname encode
//        fname=fname+".json";
//        String fnamePath=saveDir+collName+"/"+fname;
//        System.out.println("fnamePath="+fnamePath);
//        writeFile2024(fnamePath,encodeJson(obj));

    }

    /**
     * 大多数场景下，返回空的 JSONObject 是更好的选择。
     * 原因：它使调用方的逻辑更简单，减少了潜在的 NullPointerException，更符合现代 Java 设计规范。
     * <p>
     * 如果使用 Java 8+，也可以返回 Optional<JSONObject>，这样显式表达了结果可能不存在的情况
     *
     * @param id
     * @param saveDir /dbdir/coll1
     * @return
     */
    public static JSONObject getObjDocdb(String id, String saveDir) {
        mkdir2025(saveDir);
        //encodeFilName todo
        String fname = id + ".json";
        String fnamePath = saveDir + "/" + fname;
        if (new File(fnamePath).exists()) {
            String text = readTxtFrmFil(fnamePath);
            System.out.println("getobjDoc().txt=" + text);
            return JSONObject.parseObject(text);
        }

        //   if(!new File(fnamePath).exists())
        else
            return JSONObject.parseObject("{}");
    }

    private static SortedMap<String, Object> getMapMysql(String id, String jdbcurl) {
        var tbnm = getDatabaseFileName(jdbcurl);
        if (jdbcurl.startsWith("jdbc:h2"))
            tbnm = getDatabaseFileName4H2(jdbcurl);
        else if (jdbcurl.startsWith("jdbc:mysql"))
            tbnm = getDatabaseFileName4mysql(jdbcurl);
        var sql = "select * from " + tbnm + " where id='" + id + "'";
        try {
            var truedrv = toTrueDvr("com.mysql.cj.jdbc.Driver");
            jdbcurl = toTrueJdbcUrl(truedrv, jdbcurl);
            return qrySqlAsMap(sql, jdbcurl);
        } catch (Exception e) {
            String message = e.getMessage();
            if (message.contains("no such table: "))
                return new TreeMap<>();
            if (message.startsWith("Table") && message.contains("not found"))
                return new TreeMap<>();
            if (message.contains("Unknown database '"))
                return new TreeMap<>();
            throw new RuntimeException(e);
        }

    }

    public static String getDatabaseFileName4mysql(String jdbcurl) {


        // Extract the file path from the URL
        String filePath = jdbcurl.substring("jdbc:h2:file:".length());

        // Extract and return the file name
        int lastSlashIndex = filePath.lastIndexOf('/');
        int lastSlashIndexQueo = filePath.lastIndexOf('?');
        //    int lastSlashIndexDot = filePath.lastIndexOf('.');
        if (lastSlashIndex != -1 && lastSlashIndexQueo == -1) {
//filepath=c:/dbh2dir/usr.h2;MODE=MySQL
            String nm = filePath.substring(lastSlashIndex + 1);
            return nm;
        } else {
            String nm = filePath.substring(lastSlashIndex + 1, lastSlashIndexQueo);
            return nm;
        }

    }

    private static SortedMap<String, Object> getObjSqlt(String id, String jdbcurl) {
        var tbnm = getDatabaseFileName(jdbcurl);
        var sql = "select * from " + tbnm + " where id='" + id + "'";
        try {
            return qrySqlAsMap(sql, jdbcurl);
        } catch (Exception e) {
            if (e.getMessage().contains("no such table: tab1"))
                return new TreeMap<>();
            throw new RuntimeException(e);
        }

    }

    private static void addObjDocdb(Object obj, String saveDir) {
        mkdir2025(saveDir);
        String fname = (String) getField2025(obj, "id", "");
        //todo need fname encode
        fname = fname + ".json";
        String fnamePath = saveDir + "/" + fname;
        System.out.println("fnamePath=" + fnamePath);
        writeFile2501(fnamePath, encodeJson(obj));

    }

    public static String getDatabaseFileName(String databaseUrl) {
        if (databaseUrl == null) {
            throw new IllegalArgumentException("Invalid SQLite JDBC URL: " + databaseUrl);
        }

        if (databaseUrl.startsWith("jdbc:h2"))
            return getDatabaseFileName4H2(databaseUrl);
        if (databaseUrl.startsWith("jdbc:mysql"))
            return getDatabaseFileName4mysql(databaseUrl);

        // Extract the file path from the URL
        String filePath = databaseUrl.substring("jdbc:sqlite:".length());

        // Extract and return the file name
        int lastSlashIndex = filePath.lastIndexOf('/');
        if (lastSlashIndex != -1) {

            String nm = filePath.substring(lastSlashIndex + 1, filePath.length() - 3);
            return nm;
        }
        filePath = filePath.substring(0, filePath.length() - 3);
        return filePath; // Return the full path if no slash is present
    }

    /**
     * @param databaseUrl jdbc:h2:file:c:/dbh2dir/usr.h2;MODE=MySQL
     * @return
     */
    private static String getDatabaseFileName4H2(String databaseUrl) {


        // Extract the file path from the URL
        String filePath = databaseUrl.substring("jdbc:h2:file:".length());

        // Extract and return the file name
        int lastSlashIndex = filePath.lastIndexOf('/');
        int lastSlashIndexDot = filePath.lastIndexOf('.');
        if (lastSlashIndex != -1) {
//filepath=c:/dbh2dir/usr.h2;MODE=MySQL
            String nm = filePath.substring(lastSlashIndex + 1, lastSlashIndexDot);
            return nm;
        }
        filePath = filePath.substring(0, filePath.length() - 3);
        return filePath; // Return the full path if no slash is presen
    }

    private static String addObjSqlt(Object obj, String saveDir) throws Exception {

        Class.forName("org.sqlite.JDBC");
        mkdir2025(saveDir);
        //    String url = "jdbc:sqlite:" + saveDir + collName + ".db";
        Connection conn = DriverManager.getConnection(saveDir);
        Statement stmt = conn.createStatement();
        String tbnm = getDatabaseFileName(saveDir);
        String sql1 = "CREATE TABLE IF NOT EXISTS " + tbnm + " (id TEXT PRIMARY KEY)";
        stmt.execute(sql1);

        foreachObjFieldsCreateColume(obj, stmt, tbnm, saveDir);

        String us = encodeJson(obj);

        String sql = "";
        String id = (String) getField2025(obj, "id", "");


        String cols = getCols(obj);
        String valss = getValsSqlFmt(obj);
        sql = "INSERT INTO " + tbnm + "  (" + cols + ") VALUES (" + valss + ")";


        System.out.println(sql);
        int i = stmt.executeUpdate(sql);
        return "executeUpdateRzt=" + i;

    }

    private static String updtObjSqlt(Object obj, String saveDir) throws Exception {
        var urltrue = "";

        if (saveDir.startsWith("jdbc:mysql")) {
            String mysqlDvr = "com.mysql.cj.jdbc.Driver";
            var trueDvr = toTrueDvr(mysqlDvr);
            Class.forName(trueDvr);
            urltrue = toTrueJdbcUrl(trueDvr, saveDir);

        }

        if (saveDir.startsWith("jdbc:sqlite"))
            Class.forName("org.sqlite.JDBC");
        mkdir2025(saveDir);
        //    String url = "jdbc:sqlite:" + saveDir + collName + ".db";
        Connection conn = DriverManager.getConnection(urltrue);
        Statement stmt = conn.createStatement();
        //  stmt.execute("CREATE TABLE IF NOT EXISTS tab1 (id TEXT PRIMARY KEY)");
        var tbnm = getDatabaseFileName(urltrue);
        foreachObjFieldsCreateColume(obj, stmt, tbnm, urltrue);

        String us = encodeJson(obj);

        String sql = "";
        String id = (String) getField2025(obj, "id", "");


        sql = crtUpdtStmt(obj, tbnm);
        // sql="update tab1 set "+setStmt+" where id='"+id+"'";


        System.out.println(sql);
        //  stmt.execute(sql);
        int i = stmt.executeUpdate(sql);
        return "executeUpdateRzt=" + i;

    }

    //根据obj字段值
    static String crtUpdtStmt(Object obj, String tbnm) {

        if (obj instanceof Map) {
            Map<String, Object> m = (Map<String, Object>) obj;
            return crtUpdtStmt4Map(m, tbnm);
        }
        return crtUpdtStmt4obj(obj, tbnm);
    }

    //根据对象obj的属性与值，生成sql语句 update语句
    private static String crtUpdtStmt4obj(Object obj, String tbnm) {
        // 假设 "id" 是更新条件的键（可以根据实际情况修改）
        // 假设 "id" 是更新条件的键（可以根据实际情况修改）
        StringBuilder sql = new StringBuilder("UPDATE " + tbnm + " SET ");

        // 获取 obj 的所有字段
        Field[] fields = obj.getClass().getDeclaredFields();

        // 生成SET部分
        String setClause = Stream.of(fields)
                .filter(field -> !field.getName().equals("id"))  // 排除 id 字段
                .map(field -> {
                    field.setAccessible(true);  // 设置字段可访问
                    try {
                        Object value = field.get(obj);
                        return field.getName() + " = " + formatValue(value);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);  // 处理访问异常
                    }
                })
                .collect(Collectors.joining(", "));

        sql.append(setClause);

        // 获取id字段（假设id字段是用于WHERE条件）
        try {
            Field idField = obj.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            Object idValue = idField.get(obj);
            if (idValue != null) {
                sql.append(" WHERE id = ").append(formatValue(idValue));
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // 如果没有找到 id 字段，或者发生访问异常，则不附加 WHERE 子句
        }

        return sql.toString();

    }

    //根据map的kv，生成sql语句 update语句
    public static String crtUpdtStmt4Map(Map<String, Object> m, String tbnm) {
        // 假设 "id" 是更新条件的键（可以根据实际情况修改）
        StringBuilder sql = new StringBuilder("UPDATE " + tbnm + " SET ");

        // 获取所有的键值对
        Set<Map.Entry<String, Object>> entries = m.entrySet();

        // 生成SET部分
        String setClause = entries.stream()
                .filter(entry -> !"id".equals(entry.getKey())) // 排除 id 字段
                .map(entry -> entry.getKey() + " = " + formatValue(entry.getValue()))
                .collect(Collectors.joining(", "));

        sql.append(setClause);

        // 获取id字段（假设id字段是用于WHERE条件）
        Object idValue = m.get("id");
        if (idValue != null) {
            sql.append(" WHERE id = ").append(formatValue(idValue));
        }

        return sql.toString();
    }

    // 用于格式化值，如果是字符串，则加上单引号
    private static String formatValue(Object value) {
        if (value instanceof String) {
            return "'" + value + "'";
        }
        if (value == null)
            return "null";
        return value.toString();  // 对于非字符串，直接调用toString()
    }

    /**
     * @param obj
     * @return
     */
    static String getValsSqlFmt(Object obj) throws Exception {

        List<String> valsList = new ArrayList<>();


        if (obj instanceof Record) {
            // 获取 record 的字段
            var components = obj.getClass().getRecordComponents();

            for (var component : components) {
                try {
                    Object value = component.getAccessor().invoke(obj);
                    String name = component.getName();
                    System.out.println(name + ": " + value);
                    valsList.add(toSqlValFormat(value));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (obj instanceof Map) {
            Map<String, Object> map = (Map) obj;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
                valsList.add(toSqlValFormat(entry.getValue()));
            }
        } else {
            // Get the class of the object
            Class<?> clazz = obj.getClass();

            // Get all fields (including private ones)
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true); // Allow access to private fields
                String fieldName = field.getName();
                Object value = field.get(obj); // Get the value of the field for the given object
                //  System.out.println(fieldName + ": " + value);
                valsList.add(toSqlValFormat(value));
            }
        }

        String joinVals = String.join(",", valsList);
        return joinVals;

    }

    /**
     * 将每个值转化为sql的值模式。。如果是字符串，用单引号包起来
     *
     * @param joinVals 逗号分割的字符串
     * @return
     */
    private static String toSqlValFormat(Object joinVals) {
        if (joinVals instanceof String)
            return "'" + joinVals + "'";
        return String.valueOf(joinVals);
    }

    static String getCols(Object obj) throws Exception {


        List<String> colsList = new ArrayList<>();
        if (obj instanceof Map) {
            Map<String, Object> map = (Map) obj;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                //   System.out.println(entry.getKey() + ": " + entry.getValue());
                colsList.add(entry.getKey());
            }
        } else if (obj instanceof Record) {
            // 获取 record 的字段
            var components = obj.getClass().getRecordComponents();

            for (var component : components) {
                try {
                    Object value = component.getAccessor().invoke(obj);
                    String name = component.getName();
                    //  System.out.println(name + ": " + value);
                    colsList.add(name);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {


            // Get the class of the object
            Class<?> clazz = obj.getClass();

            // Get all fields (including private ones)
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true); // Allow access to private fields
                String fieldName = field.getName();
                Object value = field.get(obj); // Get the value of the field for the given object
                //   System.out.println(fieldName + ": " + value);
                colsList.add(fieldName);
            }
        }


        return String.join(",", colsList);
    }

    //  <property name="hibernate.dialect">org.hibernate.community.dialect.SQLiteDialect</property>
    //循环对象属性，创建对应的字段
    static void foreachObjFieldsCreateColume(Object obj, Statement stmt, String tbnm, String saveDir) throws Exception {

        if (obj instanceof Map) {
            Map<String, Object> map = (Map) obj;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                //  System.out.println(entry.getKey() + ": " + entry.getValue());
                //  System.out.println(entry.getKey() + ": " + entry.getValue());
                try {
                    Object value = entry.getValue();
                    String name = entry.getKey();
                    if (name.toLowerCase().equals("id"))
                        continue;
                    ;
                    //   System.out.println(name + ": " + value);
                    if (name.equals("amt"))
                        System.out.println("dbg");
                    String sql = "ALTER TABLE " + tbnm + " ADD COLUMN  " + name + "  " + getTypeSqlt(value) + " ";
                    //   System.out.println(sql);
                    stmt.execute(sql);


                } catch (Exception e) {
                    if (!e.getMessage().contains("duplicate column name"))
                        e.printStackTrace();
                }
            }
        } else if (obj instanceof Record) {
            // 获取 record 的字段
            var components = obj.getClass().getRecordComponents();

            for (var component : components) {
                try {
                    Object value = component.getAccessor().invoke(obj);
                    String name = component.getName();
                    if (name.toLowerCase().equals("id"))
                        continue;
                    ;
                    System.out.println(name + ": " + value);
                    String sql = "ALTER TABLE " + tbnm + " ADD COLUMN  " + name + "  " + getTypeSqlt(value) + " ";
                    System.out.println(sql);
                    stmt.execute(sql);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            // Get the class of the object
            Class<?> clazz = obj.getClass();

            // Get all fields (including private ones)
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                try {
                    field.setAccessible(true); // Allow access to private fields
                    String fieldName = field.getName();
                    Object value = field.get(obj); // Get the value of the field for the given object
                    //   System.out.println(fieldName + ": " + value);

                    var colType = getTypeSqlt(value);
                    ; //def sqlte type
                    if (saveDir.startsWith("jdbc:mysql") || saveDir.startsWith("jdbc:h2"))
                        colType = getTypeMysql(field.getType());

                    String fldNameDbfmt = "\"" + fieldName + "\"";//h2 fmt
                    if (saveDir.startsWith("jdbc:mysql"))
                        fldNameDbfmt = fieldName;
                    String sql = "ALTER TABLE " + tbnm + " ADD COLUMN  " + fldNameDbfmt + "  " + colType + " ";
                    // ALTER TABLE usr ADD COLUMN c1 int;
                    System.out.println(sql);
                    stmt.execute(sql);
                } catch (Exception e) {
                    if (e.getMessage().contains("duplicate column name")) {

                    } else if (e.getMessage().contains("Duplicate column name")) {

                    } else
                        e.printStackTrace();
                }

            }
        }


    }

    static String getTypeMysql(Class javatype) {
        if (javatype == String.class)
            return "VARCHAR(999)";

        if (javatype == int.class || javatype == Integer.class)
            return "INT";

        if (javatype == long.class || javatype == Long.class)
            return "BIGINT";

        if (javatype == BigDecimal.class)
            return "DECIMAL(20, 2)";

        // 默认返回 VARCHAR(999)
        return "VARCHAR(999)";
//        if (javatype instanceof String)
//            return "VARCHAR(999)";
//        if (javatype instanceof int)
//            return "INT";
//
//        if (javatype instanceof long)
//            return "BIGINT";
//        if (javatype instanceof BigDecimal)
//            return "DECIMAL(20, 2)";
//
//        return "VARCHAR(999)";
    }

    static String getTypeSqlt(Object value) {

        if (value instanceof String)
            return "text";
        if (value instanceof int)
            return "integer";

        if (value instanceof long)
            return "integer";
        if (value instanceof BigDecimal)
            return "real";

        return "text";

    }

    private static String addObjMysql(Object obj, String saveDir) throws Exception {
        System.out.println("\r\n");
        System.out.println("fun addObjMysql()");
        System.out.println("jdbcurl=" + saveDir);
        System.out.println(")");


        return saveDir;
    }

    public static void crtDatabase(String jdbcurl, String tbnm) throws SQLException {
        if (jdbcurl.startsWith("jdbc:mysql")) {
            var sqlCrtDb = "CREATE DATABASE IF NOT EXISTS " + tbnm + "";
            //  stmt.execute(sqlCrtDb);
            System.out.println(sqlCrtDb);

            int lastSlashIndex = jdbcurl.lastIndexOf('/');
            var url_noDB = jdbcurl.substring(0, lastSlashIndex);
            String url = url_noDB + "/mysql?user=" + getUnameFromJdbcurl(jdbcurl) + "&password=" + getPwdFromJdbcurl(jdbcurl);
            System.out.println("crtDB().url=" + url);
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            try {
                stmt.executeUpdate(sqlCrtDb);
            } catch (Exception e) {
                //database exists
                if (e.getMessage().contains("Can't create database")) {

                } else
                    e.printStackTrace();
            }

        }

    }

    //  //  SELECT * FROM USR WHERE  uname='009'
    private static String toTrueJdbcUrl(String trueDvr, String saveDir) {
        if (trueDvr.equals("org.h2.Driver")) {
            //and url is mysql url
            var tbnm = getDatabaseFileName4mysql(saveDir);
            var h2Tmplt = "jdbc:h2:file:c:/dbh2dir/@tbnm@.h2;MODE=MySQL;CASE_INSENSITIVE_IDENTIFIERS=TRUE";
            var trueurl = h2Tmplt.replaceAll("@tbnm@", tbnm);
            return trueurl;

        } else
            return saveDir;
    }
}
