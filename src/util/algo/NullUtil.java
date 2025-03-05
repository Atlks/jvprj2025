package util.algo;

public class NullUtil {
    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static <T> T requireNonNullElse(T obj, T defaultValue) {
        return obj != null ? obj : defaultValue;
    }
}
