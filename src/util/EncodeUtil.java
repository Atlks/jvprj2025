package util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncodeUtil {

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
