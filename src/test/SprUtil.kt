package test

import cfg.IocAtiiocCfg
import cfg.IocSpringCfg
import cfg.IocSpringCfg.context

class SprUtil {
    companion object {
        fun <T>  getBeanFrmSpr(clazz: Class<T>): T {
            return IocSpringCfg.context.getBean(clazz.getName()) as T
        }
    }

}

