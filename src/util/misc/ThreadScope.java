package util.misc;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadScope implements Scope {
    private static final ThreadLocal<Map<String, Object>> threadLocal = ThreadLocal.withInitial(ConcurrentHashMap::new);

    @NotNull
    @Override
    public Object get(@NotNull String name, @NotNull ObjectFactory<?> objectFactory) {
        Map<String, Object> map = threadLocal.get();
        Object object = map.get(name);
        if (object == null) {
            object = objectFactory.getObject();
            map.put(name, object);
        }
        return object;
    }

    @Override
    public Object remove(@NotNull String name) {
        return threadLocal.get().remove(name);
    }

    @Override
    public void registerDestructionCallback(@NotNull String name, @NotNull Runnable callback) {
        // Optional: register destruction logic
    }

    @Override
    public Object resolveContextualObject(@NotNull String key) {
        return null;  // Optional: resolve context object
    }

    @Override
    public String getConversationId() {
        return Thread.currentThread().getName();  // Using thread name as the conversation ID
    }
}

