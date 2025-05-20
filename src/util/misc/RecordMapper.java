package util.misc;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.*;
import java.nio.charset.StandardCharsets;
import java.net.URLDecoder;

public class RecordMapper {
    public static <T> T mapToRecord(Class<T> recordClass, Map<String, String> data) {
        if (!recordClass.isRecord()) {
            throw new IllegalArgumentException("Provided class is not a record.");
        }

        try {
            RecordComponent[] components = recordClass.getRecordComponents();
            Object[] args = new Object[components.length];

            for (int i = 0; i < components.length; i++) {
                RecordComponent comp = components[i];
                String name = comp.getName();
                Class<?> type = comp.getType();
                String value = data.get(name);

                if (value == null) {
                    args[i] = null;
                    continue;
                }

                args[i] = convert(value, type);
            }

            Constructor<T> ctor = recordClass.getDeclaredConstructor(
                    Arrays.stream(components).map(RecordComponent::getType).toArray(Class[]::new)
            );

            return ctor.newInstance(args);
        } catch (Exception e) {
            throw new RuntimeException("Failed to map query to record", e);
        }
    }

    private static Object convert(String value, Class<?> targetType) {
        if (targetType == String.class) return value;
        if (targetType == int.class || targetType == Integer.class) return Integer.parseInt(value);
        if (targetType == long.class || targetType == Long.class) return Long.parseLong(value);
        if (targetType == boolean.class || targetType == Boolean.class) return Boolean.parseBoolean(value);
        if (targetType == double.class || targetType == Double.class) return Double.parseDouble(value);
        // 其他类型可以继续扩展
        throw new IllegalArgumentException("Unsupported field type: " + targetType);
    }

    public static Map<String, String> parseQuery(String query) {
        Map<String, String> result = new HashMap<>();
        if (query == null || query.isEmpty()) return result;

        for (String param : query.split("&")) {
            String[] parts = param.split("=", 2);
            String key = URLDecoder.decode(parts[0], StandardCharsets.UTF_8);
            String value = parts.length > 1 ? URLDecoder.decode(parts[1], StandardCharsets.UTF_8) : "";
            result.put(key, value);
        }
        return result;
    }
}
