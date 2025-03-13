package api.usr;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;

public class ConditionContextMockImp implements ConditionContext {
    /**
     * @return
     */
    @Override
    public BeanDefinitionRegistry getRegistry() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public ConfigurableListableBeanFactory getBeanFactory() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public Environment getEnvironment() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public ResourceLoader getResourceLoader() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public ClassLoader getClassLoader() {
        return null;
    }
}
