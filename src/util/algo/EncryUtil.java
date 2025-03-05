package util.algo;

import util.ex.encryptAesEx;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

import static api.usr.LoginHdr.Key4pwd4aeskey;


public class EncryUtil {


    // DES 加密函数
//    public static byte[] encryptDES(String data, String key) throws Exception {
//        Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
//        cipher.init(Cipher.ENCRYPT_MODE, generateDESKey(key));
//        return cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
//    }
//    // DES 加密后转 Base64
//    public static String encryptDESToStrBase64(String data, String key) throws Exception {
//        byte[] encryptedBytes = encryptDES(data, key);
//        return Base64.getEncoder().encodeToString(encryptedBytes);
//    }

    //使用aes加密，加密后使用base64编码输出
    public static String encryptAesToStrBase64(String data, String key) {
        // 确保密钥的长度是 16 字节
        if (key.length() != 16) {
            throw new IllegalArgumentException("AES 密钥必须是 16 字节长");
        }

        // 创建密钥
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");

        try{
            // 创建 Cipher 实例并初始化
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            // 执行加密操作
            byte[] encryptedData = cipher.doFinal(data.getBytes());

            // 返回 Base64 编码的加密结果
            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception e) {
            throw new encryptAesEx("" + e.getMessage(), e);
        }

    }

    //使用aes解密密，
    public static String decryptAesFromStrBase64(String s, String key) throws Exception {
        // 确保密钥的长度是 16 字节
        if (key.length() != 16) {
            throw new IllegalArgumentException("AES 密钥必须是 16 字节长");
        }
        // 创建密钥
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");

        // 创建 Cipher 实例并初始化
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        // Base64 解码
        byte[] decodedData = Base64.getDecoder().decode(s);

        // 执行解密操作
        byte[] decryptedData = cipher.doFinal(decodedData);

        // 返回解密后的字符串
        return new String(decryptedData);

    }


    // public static String Key_a1235678 = "a1235678";

    public static void main(String[] args) throws Exception {
        String x = encryptAesToStrBase64("007", Key4pwd4aeskey);
        System.out.println(x);
        //  System.out.println("ecurl="+encodeUrl(x));
        //   System.out.println( "deurl="+(EncodeUtil.decodeUrl(encodeUrl(x))));
        System.out.println(decryptAesFromStrBase64(x, Key4pwd4aeskey));

    }

//    // DES 解密
//    public static String decryptDES(String encryptedData, String key) throws Exception {
//        Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
//        cipher.init(Cipher.DECRYPT_MODE, generateDESKey(key));
//        byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
//        return new String(decryptedData, StandardCharsets.UTF_8);
//    }

    private static final String DES_ALGORITHM = "DES";

    // 生成 DES 密钥
    // 生成固定的 8 字节 DES 密钥
    private static SecretKey generateDESKey(String key) {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        byte[] keyMaterial = Arrays.copyOf(keyBytes, 8); // 截取前 8 字节
        return new SecretKeySpec(keyMaterial, "DES");
    }
}
