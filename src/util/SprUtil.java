package util;

import cfg.IocSpringCfg;

public class SprUtil {

    public static <T> T getBeanFrmSpr(Class<T> clazz) {
        return (T) IocSpringCfg.context.getBean(clazz.getName());
    }
}
