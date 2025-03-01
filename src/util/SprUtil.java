package util;

import cfg.IocSpringCfg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import service.wlt.AddMoneyToWltService;


import javax.inject.Inject;
import java.lang.reflect.Field;
import java.util.function.Consumer;

import static util.IocUtil.getBeanFrmBeanmap;


public class SprUtil {

//    public static <T> T getBeanFrmSpr(Class<T> clazz) {
//        return (T) IocSpringCfg.context.getBean(clazz.getName());
//    }

    public static Object getBeanFrmSprAsObj(Class<?> clazz) {
        return IocSpringCfg. context.getBean(clazz.getName());
    }

    public static <T> T getBeanFrmSpr(String beanRegname) {
        return  (T)IocSpringCfg.context.getBean(beanRegname);

        //  injectAll4spr(ins);
      //  return ins;
    }
    public static <T> T getBeanFrmSpr(Class<T> clazz) {
        T ins = (T) IocSpringCfg.context.getBean(clazz.getName());

     //  injectAll4spr(ins);
        return ins;
    }
    public static String getQualifierName(Field field) {

        //  Field field = clazz.getDeclaredField(fieldName);
        Qualifier qualifier = field.getAnnotation(Qualifier.class);
        return (qualifier != null) ? qualifier.value() : "";

    }


//    public static String getInjktName(Field field) {
//
//          //  Field field = clazz.getDeclaredField(fieldName);
//        Injkt qualifier = field.getAnnotation(Injkt.class);
//            return (qualifier != null) ? qualifier.value() : "";
//
//    }

    public static void injectAll4spr(Object ins) {
        Consumer<Field> csmr = field -> {
            try {
                var inject = field.getAnnotation(Inject.class);
                var Autowired1 = field.getAnnotation(Autowired.class);
                if( inject==null && Autowired1==null)
                    return;
                var qlfNm= getQualifierName(field);

                if(field.getType()==String.class)
                    return;
                if(field.getType()==Boolean.class)
                    return;
                if (field.get(ins) == null) { // 如果字段值为 null
                    if(qlfNm.equals(""))
                    {
                        if(field.getType()== AddMoneyToWltService.class)
                            System.out.println("d744");
                        Object component = getBeanFrmSpr(field.getType());

                            System.out.println("setprop as:" + component);

                            field.set(ins, component);
                    }else {
                        System.out.println("stat injkt,qlfNm="+qlfNm);
                        Object insFldObj = getBeanFrmBeanmap( qlfNm);

                        field.set(ins, insFldObj);
                        System.out.println("fld set (ins,fldObj) qlf="+qlfNm);
                    }


                }
            } catch (Exception e) {
                //    e.printStackTrace();
                throw new RuntimeException(field.getName(), e);
            }
        };
        TraveUtil. traveProp(ins, csmr);

    }




    /**
     * getBean() 执行流程示意图
     * <p>
     * getBean(beanName)
     * ├─> 1. 从 singletonObjects 缓存查找 (如果存在则直接返回)
     * ├─> 2. 检查是否正在创建 (防止循环依赖)
     * ├─> 3. 从 beanDefinitionMap 读取 BeanDefinition
     * ├─> 4. 解析依赖并实例化所需 Bean
     * ├─> 5. 实例化 bean (调用构造方法)
     * ├─> 6. 执行 BeanPostProcessor.postProcessBeforeInitialization()
     * ├─> 7. 调用 @PostConstruct 或 InitializingBean.afterPropertiesSet()
     * ├─> 8. 执行 BeanPostProcessor.postProcessAfterInitialization() (AOP 代理)
     * ├─> 9. 将单例 bean 放入 singletonObjects 缓存
     * └─> 10. 返回最终 bean
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBeanFrmSprV2(Class<T> clazz) {
        return (T) IocSpringCfg.context.getBean(clazz.getName());
    }
}
