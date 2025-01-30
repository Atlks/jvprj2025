package util;

import com.alibaba.fastjson2.JSON;
import javax.jdo.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class JdoPersistenceManager {
    private static final PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("mysqlUnit");
    private static final String DATA_DIR = "data/";

    static {
        try {
            Files.createDirectories(Paths.get(DATA_DIR));
        } catch (IOException e) {
            throw new RuntimeException("创建 JSON 数据文件夹失败", e);
        }
    }

    private final boolean useDatabase;

    public JdoPersistenceManager(boolean useDatabase) {
        this.useDatabase = useDatabase;
    }

    public void makePersistent(Object obj) {
        if (useDatabase) {
            saveToDatabase(obj);
        } else {
            saveToJson(obj);
        }
    }

    private void saveToDatabase(Object obj) {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try {
            tx.begin();
            pm.makePersistent(obj);
            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
    }

    private void saveToJson(Object obj) {
        String filePath = DATA_DIR + obj.hashCode() + ".json";
        try (Writer writer = new FileWriter(filePath)) {
            writer.write(JSON.toJSONString(obj));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> List<T> getAll(Class<T> clazz) {
        return useDatabase ? getAllFromDatabase(clazz) : getAllFromJson(clazz);
    }

    private <T> List<T> getAllFromDatabase(Class<T> clazz) {
        PersistenceManager pm = pmf.getPersistenceManager();
        Query<T> query = pm.newQuery(clazz);
        return query.executeList();
    }

    private <T> List<T> getAllFromJson(Class<T> clazz) {
        List<T> result = new ArrayList<>();
        File folder = new File(DATA_DIR);
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            try {
                String content = Files.readString(file.toPath());
                result.add(JSON.parseObject(content, clazz));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}

