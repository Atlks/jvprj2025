package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StrUtil {

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
