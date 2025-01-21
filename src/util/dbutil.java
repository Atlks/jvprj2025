package util;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSON;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.*;

import com.alibaba.fastjson2.JSONArray;
import static util.Util2025.*;
import static util.util2026.getField2025;

public class dbutil {


    /**
     *
     * @param obj
     * @param collName   usrs
     * @param saveDir   /db2026/
     *             jdbc:sqlite:/db2026/usrs.db
     *             redis
     *             jdbc:mysql
     *
     * @throws Exception
     */
    public static void addObj(Object obj, String collName,String saveDir) throws Exception {

        if(saveDir.endsWith(".db"))
        {
            addObjSqlt(obj,collName,saveDir);
        }else if( saveDir.startsWith("jdbc:mysql"))
        {
            addObjMysql(obj,collName,saveDir);
        }else {
            addObjDocdb(obj,collName,saveDir);
        }


    }


    public static void getObj( String collName,String id,String saveDir) throws Exception {

        if(saveDir.endsWith(".db"))
        {
            getObjSqlt(id,collName,saveDir);
        }else if( saveDir.startsWith("jdbc:mysql"))
        {
          //  addObjMysql(obj,collName,saveDir);
        }else {
            getObjDocdb(id,collName,saveDir);
        }


    }

    /**
     * //遍历目录saveDir+collName，读取每一个文件，并解析为SortedMap<String, Objects>
     *
     * @param collName
     * @param saveDir
     * @return
     */
    public static List<SortedMap<String, Object>> getObjsDocdb(String collName, String saveDir) {

        mkdir2025(saveDir+collName);
        //encodeFilName todo

        String dir=saveDir+collName;

        //遍历目录dir，读取每一个文件，并解析为SortedMap<String, Objects>
        //最终返回一个List<SortedMap<String, Objects>>
        List<SortedMap<String, Object>> result = new ArrayList<>();

        try {
            // 遍历目录中的所有文件
            Files.list(Paths.get(dir))
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
            System.err.println("Error reading directory: " + dir + " - " + e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 将文件解析为 SortedMap<String, Object>
     *  文件内容是一个json对象
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


    public static JSONObject getObjDocdb(String id, String collName, String saveDir) {
        mkdir2025(saveDir+collName);
        //encodeFilName todo
        String fname =id+".json";
        String fnamePath=saveDir+collName+"/"+fname;
        if(new File(fnamePath).exists())
            return  JSONObject.parseObject(readTxtFrmFil(fnamePath));
     //   if(!new File(fnamePath).exists())
        else
            return    JSONObject.parseObject("{}");
    }

    private static void getObjSqlt(String id, String collName, String saveDir) {
    }

    private static void addObjDocdb(Object obj, String collName, String saveDir) {
        mkdir2025(saveDir+collName);
        String fname=getField2025(obj,"id","");
        //todo need fname encode
        fname=fname+".json";
        String fnamePath=saveDir+collName+"/"+fname;
        System.out.println("fnamePath="+fnamePath);
        writeFile2024(fnamePath,encodeJson(obj));

    }

    private static void addObjSqlt(Object obj, String collName, String saveDir) throws Exception {

        Class.forName("org.sqlite.JDBC");
        mkdir2025(saveDir);
        //    String url = "jdbc:sqlite:" + saveDir + collName + ".db";
        Connection conn = DriverManager.getConnection(saveDir);
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS tab1 (k TEXT PRIMARY KEY, v TEXT)");
        String us = encodeJson(obj);
        String sql = "INSERT INTO tab1 (k, v) VALUES ('" + getField2025(obj,"id","") + "', '" + us + "')";
        System.out.println(sql);
        stmt.execute(sql);

    }

    private static void addObjMysql(Object obj, String collName, String saveDir) throws Exception {

        Class.forName("org.sqlite.JDBC");
        mkdir2025(saveDir);
        //    String url = "jdbc:sqlite:" + saveDir + collName + ".db";
        Connection conn = DriverManager.getConnection(saveDir);
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS tab1 (k TEXT PRIMARY KEY, v TEXT)");
        String us = encodeJson(obj);
        String sql = "INSERT INTO tab1 (k, v) VALUES ('" + getField2025(obj,"id","") + "', '" + us + "')";
        System.out.println(sql);
        stmt.execute(sql);

    }
}
