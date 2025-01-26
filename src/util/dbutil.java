package util;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSON;

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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.alibaba.fastjson2.JSONArray;
//import org.apache.lucene.analysis.standard.StandardAnalyzer;
//import org.apache.lucene.document.Document;
//import org.apache.lucene.index.IndexWriter;
//import org.apache.lucene.index.IndexWriterConfig;
//import org.apache.lucene.index.Term;
//import org.apache.lucene.store.Directory;
//import org.apache.lucene.store.FSDirectory;

//import static util.Fltr.filterWithSpEL;
import static util.Fltr.fltr2501;
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
     * @throws Exception
     */
    public static void addObj(Object obj, String saveDir) throws Exception {
        System.out.println("fun addobj(");
        System.out.println("obj="+encodeJson(obj));
        System.out.println("saveDir="+saveDir);
        System.out.println(")");
        String collName = "";
        if (saveDir.endsWith(".db")) {
            addObjSqlt(obj, saveDir);
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
            addObjMysql(obj, saveDir);
        } else {
            //if (saveDir.startsWith("ini:"))
            //ini doc
            saveDir = saveDir.substring(0);
            System.out.println("savedir=" + saveDir);
            addObjIni(obj, saveDir);
        }


    }
    public static void updtObj(Object obj, String saveDir) throws Exception {
        System.out.println("fun updtObj(");
        System.out.println("obj="+encodeJson(obj));
        System.out.println("saveDir="+saveDir);
        System.out.println(")");
        String collName = "";
        if (saveDir.endsWith(".db")) {
            updtObjSqlt(obj, saveDir);
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
            addObjMysql(obj, saveDir);
        } else {
            //if (saveDir.startsWith("ini:"))
            //ini doc
            saveDir = saveDir.substring(0);
            System.out.println("savedir=" + saveDir);
            addObjIni(obj, saveDir);
        }


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
    private static String encodeIni(Object obj) {

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


    public static SortedMap<String, Object> getObj(String id, String saveDir) {

        if (saveDir.endsWith(".db")) {
            return getObjSqlt(id, saveDir);
        } else if (saveDir.startsWith("jdbc:mysql")) {
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


    public static List<SortedMap<String, Object>> qrySql(String sql, String jdbcUrl) throws Exception {

        System.out.println("sql=" + sql);
        System.out.println("jdbcUrl=" + jdbcUrl);
        if (jdbcUrl.startsWith("jdbc:sqlite"))
            Class.forName("org.sqlite.JDBC");
        if (jdbcUrl.startsWith("jdbc:mysql"))
            Class.forName("com.mysql.cj.jdbc.Driver");
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
            return toMapList(rs);
        } finally {
            // 关闭资源
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }

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

    private static SortedMap<String, Object> toMapFrmInicontext(String iniFileContent) {
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

    private static SortedMap<String, Object> getObjSqlt(String id, String jdbcurl) {
        var sql = "select * from tab1 where id='" + id + "'";
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
    private static void addObjSqlt(Object obj, String saveDir) throws Exception {

        Class.forName("org.sqlite.JDBC");
        mkdir2025(saveDir);
        //    String url = "jdbc:sqlite:" + saveDir + collName + ".db";
        Connection conn = DriverManager.getConnection(saveDir);
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS tab1 (id TEXT PRIMARY KEY)");

        foreachObjFieldsCreateColume(obj, stmt);

        String us = encodeJson(obj);

        String sql ="";
        String id = (String) getField2025(obj, "id", "");


            String cols = getCols(obj);
            String valss = getValsSqlFmt(obj);
            sql = "INSERT INTO tab1 (" + cols + ") VALUES (" + valss + ")";



        System.out.println(sql);
        stmt.execute(sql);

    }

    private static void updtObjSqlt(Object obj, String saveDir) throws Exception {

        Class.forName("org.sqlite.JDBC");
        mkdir2025(saveDir);
        //    String url = "jdbc:sqlite:" + saveDir + collName + ".db";
        Connection conn = DriverManager.getConnection(saveDir);
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS tab1 (id TEXT PRIMARY KEY)");

        foreachObjFieldsCreateColume(obj, stmt);

        String us = encodeJson(obj);

        String sql ="";
        String id = (String) getField2025(obj, "id", "");


        if( !id.equals("") )
        {
              sql=crtUpdtStmt(obj);
           // sql="update tab1 set "+setStmt+" where id='"+id+"'";
        }

        System.out.println(sql);
        stmt.execute(sql);

    }

    //根据obj字段值
    private static String crtUpdtStmt(Object obj) {

         if(obj instanceof  Map)
         {
             Map<String,Object> m= (Map<String, Object>) obj;
             return  crtUpdtStmt4Map(m);
         }
        return  crtUpdtStmt4obj(obj);
    }

    //根据对象obj的属性与值，生成sql语句 update语句
    private static String crtUpdtStmt4obj(Object obj) {
        // 假设 "id" 是更新条件的键（可以根据实际情况修改）
        // 假设 "id" 是更新条件的键（可以根据实际情况修改）
        StringBuilder sql = new StringBuilder("UPDATE tab1 SET ");

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
    public static String crtUpdtStmt4Map(Map<String, Object> m) {
        // 假设 "id" 是更新条件的键（可以根据实际情况修改）
        StringBuilder sql = new StringBuilder("UPDATE tab1 SET ");

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
        return value.toString();  // 对于非字符串，直接调用toString()
    }

    /**
     * @param obj
     * @return
     */
    private static String getValsSqlFmt(Object obj) {

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
        }


        if (obj instanceof Map) {
            Map<String, Object> map = (Map) obj;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
                valsList.add(toSqlValFormat(entry.getValue()));
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

    private static String getCols(Object obj) {

        List<String> colsList = new ArrayList<>();
        if (obj instanceof Record) {
            // 获取 record 的字段
            var components = obj.getClass().getRecordComponents();

            for (var component : components) {
                try {
                    Object value = component.getAccessor().invoke(obj);
                    String name = component.getName();
                    System.out.println(name + ": " + value);
                    colsList.add(name);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        if (obj instanceof Map) {
            Map<String, Object> map = (Map) obj;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
                colsList.add(entry.getKey());
            }
        }

        return String.join(",", colsList);
    }

    //循环对象属性，创建对应的字段
    private static void foreachObjFieldsCreateColume(Object obj, Statement stmt) throws SQLException {

        if (obj instanceof Record) {
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
                    String sql = "ALTER TABLE tab1 ADD COLUMN  " + name + "  " + getTypeSqlt(value) + " ";
                    System.out.println(sql);
                    stmt.execute(sql);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


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
                    System.out.println(name + ": " + value);
                    if(name.equals("amt"))
                        System.out.println("dbg");
                    String sql = "ALTER TABLE tab1 ADD COLUMN  " + name + "  " + getTypeSqlt(value) + " ";
                    System.out.println(sql);
                    stmt.execute(sql);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


    }

    private static String getTypeSqlt(Object value) {

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

    private static void addObjMysql(Object obj, String saveDir) throws Exception {

        Class.forName("org.sqlite.JDBC");
        mkdir2025(saveDir);
        //    String url = "jdbc:sqlite:" + saveDir + collName + ".db";
        Connection conn = DriverManager.getConnection(saveDir);
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS tab1 (k TEXT PRIMARY KEY, v TEXT)");
        String us = encodeJson(obj);
        String sql = "INSERT INTO tab1 (k, v) VALUES ('" + getField2025(obj, "id", "") + "', '" + us + "')";
        System.out.println(sql);
        stmt.execute(sql);

    }
}
