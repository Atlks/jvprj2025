package util.oo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StrUtil {

        public static String ucfirst(String str) {
            if (str == null || str.isEmpty()) {
                return str; // 处理空字符串或 null
            }
            return str.substring(0, 1).toUpperCase() + str.substring(1);
        }

        public static String lowerFirstChar(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        char firstChar = str.charAt(0);
        if (Character.isUpperCase(firstChar)) {
            return Character.toLowerCase(firstChar) + str.substring(1);
        }
        return str;
    }
    public static String toStr(Object invtr) {
        if(invtr==null)
            return  "";
        return  invtr.toString();
    }
    public static String getPwdFromJdbcurl(String jdbcUrl) {
        return getParameterFromJdbcUrl(jdbcUrl, "password");
    }

    public  static String getUnameFromJdbcurl(String jdbcUrl) {
        return getParameterFromJdbcUrl(jdbcUrl, "user");
    }

    private static String getParameterFromJdbcUrl(String jdbcUrl, String paramName) {
        String regex = "[?&]" + paramName + "=([^&]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(jdbcUrl);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return ""; // 如果没有找到，返回 null
    }
}
