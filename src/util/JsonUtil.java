package util;

import com.fasterxml.jackson.databind.ObjectMapper;

// JSON 序列化
public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // 对象 → JSON 字符串
    public static String serializeToJson(Object obj) throws Exception {
        return objectMapper.writeValueAsString(obj);
    }

    // JSON 字符串 → 对象
    public static <T> T deserializeFromJson(String json, Class<T> clazz) throws Exception {
        return objectMapper.readValue(json, clazz);
    }

    public static void main(String[] args) throws Exception {
//        Passport passport = new Passport("THA", "A1234567");
//
//        // JSON 序列化
//        String json = serializeToJson(passport);
//        System.out.println("JSON 序列化：" + json);
//
//        // JSON 反序列化
//        Passport deserializedPassport = deserializeFromJson(json, Passport.class);
//        System.out.println("JSON 反序列化：" + deserializedPassport);
    }
}

