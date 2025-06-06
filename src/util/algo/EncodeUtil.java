package util.algo;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncodeUtil {



    /**
     *
     * @param uname
     * @return
     */
    public static String encodeSqlAsLikeMatchParam(String uname) {
        return   "'% "+encodeSqlPrm(uname)+" %'";

    }

    public static  String toStr4sqlprm(String name) {
        return   encodeSqlPrmAsStr(name);
    }

    /**
     * safe string val
     * @param uname
     * @return
     */
    public static String encodeSqlPrm(String uname) {
        return  uname.replaceAll("'","''");
    }

    public static String encodeSqlPrmAsStr(String uname) {
        return   "'"+encodeSqlPrm(uname)+"'";
    }
    //serialize
    public static String encodeParamSql(String uname) {
        return  uname.replaceAll("'","''");
    }

    public static String htmlspecialchars(String input) {
        if (input == null) return "";
        return input.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#039;");
    }

    public static String decodeUrl(String val) {
        if (val == null || val.isEmpty()) {
            return "";
        }
        try {
            return URLDecoder.decode(val, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 encoding not supported", e);
        }
    }

    public static String encodeUrl(String val) {
        if (val == null || val.isEmpty()) {
            return "";
        }
        try {
            return URLEncoder.encode(val, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 encoding not supported", e);
        }
    }
    public static byte[] encodeMd5AsByteArr(String s) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(s.getBytes());

            return  digest; // 直接返回digest字节数组
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not available", e);
        }
    }
    public static String encodeMd5(String s) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(s.getBytes());
            BigInteger number = new BigInteger(1, digest);
            return String.format("%032x", number);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not available", e);
        }
    }
    public static String encodeMd5Dbl(String s) {
   return   encodeMd5(encodeMd5(s));
    }


//    // 俩次md5
//    fun encodeMd5Dbl(str: String): Any? {
//        encodeMd5(encodeMd5(str))
//    }
}
