package util.algo;

public class NullUtil {
    public static boolean isBlank(Object str) {
        return str == null || str.toString().trim().isEmpty();
    }

    public static <T> T requireNonNullElse(T obj, T defaultValue) {
        return obj != null ? obj : defaultValue;
    }
}
