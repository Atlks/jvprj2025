package util;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSON;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.*;

import com.alibaba.fastjson2.JSONArray;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import static util.Fltr.filterWithSpEL;
import static util.Util2025.*;
import static util.luceneUtil.convertMapToDocument;
import static util.util2026.getField2025;

/**
 * *  saveDir  jdbc:ini
 * *                 /db2026/coll1    json doc
 * *                 lucene:
 * jdbc:sqltKV
 * redis:
 * <p>
 * *                 jdbc:sqlite:/db2026/usrs.db
 * *
 * *                 jdbc:mysql
 */
public class dbutil {

    public static void main(String[] args) throws Exception {
        HashMap m = new HashMap();
        m.put("id", "id1");
        m.put("name", "nm1");
        addObj(m, "jdbc:ini:/db22/usrs");
    }

    public static List<SortedMap<String, Object>> findObjs(String saveDir, String expression) {

        if (saveDir.startsWith("jdbc:ini")) {
            saveDir = saveDir.substring(9);
            System.out.println("savedir=" + saveDir);
            return findObjsIni(saveDir, "");
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
            return findObjsJsDocdb(saveDir, expression);
        } else {
            //json doc
            return findObjsIni(saveDir, expression);
        }

        return new ArrayList<>();
    }

    private static Object findObjsLucene(String saveDir) {
        return null;
    }

    static List<SortedMap<String, Object>> findObjsIni(String saveDir, String qryExpression) {

        //nullchk
        if (saveDir == null || saveDir.equals(""))
            return new ArrayList<>();


        List<SortedMap<String, Object>> result = getSortedMapsFrmINiFmt(saveDir);


        if (qryExpression == null || qryExpression.equals(""))
            return result;
        if (result.isEmpty())
            return result;

        result = filterWithSpEL(result, qryExpression);

        return result;
//        Map<String, String> m= parse_ini_fileNosec()
//        return List.of();
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
        String collName = "";
        if (saveDir.endsWith(".db")) {
            addObjSqlt(obj, collName, saveDir);
        } else if (saveDir.startsWith("json:")) {
            saveDir = saveDir.substring(5);
            System.out.println("savedir=" + saveDir);
            addObjDocdb(obj, saveDir);


        } else if (saveDir.startsWith("jdbc:lucene")) {
            saveDir = saveDir.substring(11);
            System.out.println("savedir=" + saveDir);
            addObjLucene(obj, collName, saveDir);
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

    private static void addObjLucene(Object obj, String collName, String saveDir) throws Exception {
        var map1 = (Map<String, Object>) obj;
        //  openIndexWriter
        // 创建分析器
        StandardAnalyzer analyzer = new StandardAnalyzer();

        // 创建目录
        Directory directory = FSDirectory.open(Paths.get(saveDir));

        // 创建索引写入器配置
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, config);

        // 创建文档并添加到索引
        Document doc1 = convertMapToDocument(map1);
        Term termId = new Term("id", map1.get("id").toString());
        indexWriter.updateDocument(termId, doc1);
        indexWriter.commit();

        // 提交并关闭索引写入器
        indexWriter.close();
    }

    private static void addObjIni(Object obj, String saveDir) {
        mkdir2025(saveDir);
        String fname = (String) getField2025(obj, "id", "");
        //todo need fname encode
        fname = fname + ".ini";
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


    public static SortedMap<String, Object> getObj(  String id, String saveDir)   {

        if (saveDir.endsWith(".db")) {
          return   getObjSqlt(id, saveDir);
        } else if (saveDir.startsWith("jdbc:mysql")) {
            return   getObjSqlt(id, saveDir);
        } else {
          return   getObjIni(id, saveDir);
        }


    }

    private static SortedMap<String, Object> getObjIni(String id, String saveDir) {

        mkdir2025(saveDir);
        //encodeFilName todo
        String fname = id + ".ini";
        String fnamePath = saveDir + "/" + fname;
        if (new File(fnamePath).exists()) {
            String text = readTxtFrmFil(fnamePath);
            System.out.println("getobjDoc().txt=" + text);
            return  toMapFrmInicontext(text);
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

        result = filterWithSpEL(result, qryExpression);

        return result;
    }


    public static Object qrySql(String sql, String saveDir) {
        return null;
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
    public static void updateObjIni(JSONObject obj,  String saveDir) {
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

    private static SortedMap<String, Object> getObjSqlt(String id, String collName) {
        return null;
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

    private static void addObjSqlt(Object obj, String collName, String saveDir) throws Exception {

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
