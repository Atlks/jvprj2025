package test;

import apis.RegHandler;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;

public class NitriteExmpl {
    public static void main(String[] args) {
        Nitrite db = Nitrite.builder()
                .filePath("myDatabase.db")
                .openOrCreate();

        // 创建一个集合（文档类型）
        ObjectRepository<RegHandler.User> userRepository = db.getRepository(RegHandler.User.class);

        db.close();
    }
}
