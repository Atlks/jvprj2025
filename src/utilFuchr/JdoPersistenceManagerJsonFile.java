package utilFuchr;

import apiUsr.Usr;
import com.alibaba.fastjson2.JSON;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

public class JdoPersistenceManagerJsonFile {
    private static final String DIRECTORY = "data"; // JSON 文件存储目录
    public static void main(String[] args) {
        // 创建一个 User 对象
        Usr user = new Usr();
        user.id="009";

        // 保存到 JSON 文件
        JdoPersistenceManagerJsonFile.save(user, user.id + ".json");

        // 读取 JSON 文件并转换回对象
        Usr loadedUser = JdoPersistenceManagerJsonFile.load("009.json", Usr.class);
        System.out.println("🔄 读取到的用户: " + loadedUser);
    }
    // 保存对象为 JSON 文件
    public static <T> void save(T obj, String filename) {
        try {
            // 确保目录存在
            File dir = new File(DIRECTORY);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 序列化为 JSON
            String json = JSON.toJSONString(obj);
            FileWriter writer = new FileWriter(DIRECTORY + "/" + filename);
            writer.write(json);
            writer.close();
            System.out.println("✅ 保存成功: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 读取 JSON 并反序列化
    public static <T> T load(String filename, Class<T> clazz) {
        try {
            File file = new File(DIRECTORY + "/" + filename);
            if (!file.exists()) {
                System.out.println("❌ 文件不存在: " + filename);
                return null;
            }

            FileReader reader = new FileReader(file);
            T obj = JSON.parseObject(reader, clazz);
            reader.close();
            return obj;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
