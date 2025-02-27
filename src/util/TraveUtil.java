package util;

//import service.AddRchgOrdToWltService;

import service.AddMoneyToWltService;
import service.Iservice;

import java.lang.reflect.Field;
import java.util.function.Consumer;

public class TraveUtil {

    //遍历对象属性，如果为null，则设置为PicoContainer.getComponent(属性类型.class)
    public static void traveProp(Object ins, Consumer<Field> csmr) {

        if (ins == null) return;

        Class<?> clazz = ins.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true); // 允许访问私有字段
            if (field.getType() == Iservice.class)
                System.out.println("d951");
            System.out.println("trav prop=" + field);

            csmr.accept(field);

        }
    }

}
