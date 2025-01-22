package util;

public class UtilMysql {



    public static String getDbNameSqlt(String url) {

        // 检查 URL 是否以 jdbc:sqlite: 开头
        if (url.startsWith("jdbc:sqlite:")) {
            // 提取数据库名称
            String dbName = url.substring("jdbc:sqlite:".length());
            return dbName;
        }
        // 如果 URL 格式不正确，返回 null 或抛出异常
        return null;
    }



    public static String getDbName(String url) {

        // URL格式应为 jdbc:mysql://host:port/database
        if (url != null && url.startsWith("jdbc:mysql://")) {
            String[] parts = url.split("/");
            if (parts.length > 2) {
                return parts[parts.length - 1]; // 返回最后一个部分，即数据库名
            }
        }
        return null; // 或者抛出异常，指示URL格式不正确
    }
}
