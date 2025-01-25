package util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UtilEncode {

    /**
     * 编码为合法的文件名称，遇到特殊符号，使用urlencode编码
     * @param str
     * @return
     */

    public static String encodeFilename(String str) {
        // 定义文件名中需要替换的特殊字符
        String[] specialChars = {"/", "\\", ":", "*", "?", "\"", "<", ">", "|"};
        // 用于替换的特殊字符的URL编码
        String[] encodedChars = {"%2F", "%5C", "%3A", "%2A", "%3F", "%22", "%3C", "%3E", "%7C"};

        // 首先替换特殊字符
        for (int i = 0; i < specialChars.length; i++) {
            str = str.replace(specialChars[i], encodedChars[i]);
        }

        // 然后对整个字符串进行URL编码，以确保非ASCII字符也被正确编码
        try {
            str = URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // UTF-8编码应该总是可用，所以这里不处理异常
            e.printStackTrace();
        }

        // 返回编码后的字符串
        return str;
    }
}
