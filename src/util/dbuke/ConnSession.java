package util.dbuke;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transaction;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static util.algo.CopyUti.copyFile;
import static util.algo.CopyUti.moveFile;
import static util.algo.GetUti.getId;
import static util.oo.FileUti.delFile;
import static util.oo.FileUti.mkdir;


/**
 * 文本数据库，存储和事务规范实现使用jpa 规范api接口
 * 一个集合就是一个文件夹，一个数据记录就是一个文件，文件名就是数据id
 * 文件内容是json序列化的对象实体
 * implt  trx mng api
 * also a conn sesson
 * implements EntityManager
 */
public class ConnSession {
    TrsctImp tx;  //事务实现
    public boolean autoCommit = true;//自动提交事务
    public String saveDir = "/0db"; // 数据库的根目录
    String saveDir1bkTx = "/0dbBk4tx";
    private ObjectMapper objectMapper = new ObjectMapper(); // 用于对象与JSON的转换

    public ConnSession(String dbSaveDir) {
        this.saveDir = dbSaveDir;
    }

    /**
     * 保存数据，数据必定包含字段id，使用反射读取，作为文件名
     *
     * @param obj
     * @return
     */
    public @NotNull Object merge(@NotNull Object obj) throws Exception {

        @NotBlank String collName = obj.getClass().getName();
        // 通过反射获取对象的 id 字段
        String id = getId(obj);

        // 获取集合对应的文件夹路径
        mkdir(saveDir+"/"+collName);

        // 文件路径（文件名是 id）
        String data_id = id + ".json";
        setTxRollback( data_id, collName );

        // 将对象序列化为 JSON 写入文件
        File file=new File(saveDir+"/"+collName+"/"+data_id);
        objectMapper.writeValue(file, obj);


        return obj; // 返回保存的数据对象
    }




    private void setTxRollback(String data_id, String collName) {

        String data_loc=collName+"/"+data_id;
        if (existDataInDB(data_loc)) {
             backOlddata(data_loc);
        }
        tx.setRollbackAct(() -> {
            delNewData(data_loc);

            if (existOlddataInTxbekArea(data_loc)) {
                restoreOlddata(data_loc);
            }
        });
    }

    private void delNewData(String data_loc) {

        String delf=saveDir+"/"+data_loc ;
        delFile(delf);

    }


    private boolean existDataInDB(String data_loc) {

        File file = new File(saveDir+"/"+ data_loc);
        return (file.exists());
    }

    private void backOlddata(String data_loc) {

        copyFile(saveDir+"/"+ data_loc, saveDir1bkTx);
    }

    private void restoreOlddata(String data_loc) {
        String collDir=saveDir1bkTx+"/"+data_loc;
        moveFile(new File(collDir), saveDir);
    }


    private boolean existOlddataInTxbekArea(String data_loc) {
        String collDir=saveDir1bkTx+"/"+data_loc;
        return new File(collDir ).exists();
    }


    /**
     * 事务启动
     *
     * @return
     */
    public Transaction beginTx() {

        this.autoCommit = false;
        TrsctImp ti = new TrsctImp();
        tx = ti;
        return ti;

    }


    public @NotNull Object find(@NotNull Class clz, @NotBlank String key) throws Exception {
        // 获取集合对应的文件夹路径
        File dir = new File(saveDir, clz.getName());
        if (!dir.exists()) {
            return null; // 如果集合文件夹不存在，返回 null
        }

        // 查找指定 id 的文件
        File file = new File(dir, key + ".json");
        if (!file.exists()) {
            return null; // 如果文件不存在，返回 null
        }

        // 反序列化 JSON 为对象
        Map<String, Object> result = objectMapper.readValue(file, Map.class);
        return result; // 返回读取到的对象（这里是 Map 类型）

    }


    /**
     * 获取指定集合中所有的数据
     *
     * @param collName 集合名
     * @return 返回集合中的所有数据列表
     */
    public <T> List<T> getList(String collName, Class<T> clz) {
        List<T> resultList = new ArrayList<>();
        try {
            // 获取集合对应的文件夹路径
            File dir = new File(saveDir, collName);
            if (!dir.exists()) {
                return resultList; // 如果集合文件夹不存在，返回空列表
            }

            // 获取该目录下的所有文件
            File[] files = dir.listFiles();
            if (files == null) {
                return resultList; // 如果没有文件，返回空列表
            }

            // 遍历每个文件，反序列化 JSON 为对象并加入结果列表
            for (File file : files) {
                if (file.getName().endsWith(".json")) { // 仅处理 .json 文件
                    T obj = objectMapper.readValue(file, clz); // 读取为 Object 类型，可以根据需要反序列化为具体类
                    resultList.add(obj);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultList; // 返回所有读取到的数据
    }


}
