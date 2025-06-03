package orgx.orm;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        System.out.println("自动创建表...");
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        System.out.println("自动更新表结构...");
    }
}
