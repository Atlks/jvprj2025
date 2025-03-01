package util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

import static util.EncodeUtil.encodeUrl;


public class EncryUtil {


    // DES 加密函数
    public static byte[] encryptDES(String data, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, generateDESKey(key));
        return cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
    }
    // DES 加密后转 Base64
    public static String encryptDESToStrBase64(String data, String key) throws Exception {
        byte[] encryptedBytes = encryptDES(data, key);
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String Key_a1235678 = "a1235678";

    public static void main(String[] args) throws Exception {
        String x = encryptDESToStrBase64("007", Key_a1235678);
        System.out.println(x);
        System.out.println("ecurl="+encodeUrl(x));
        System.out.println( "deurl="+(EncodeUtil.decodeUrl(encodeUrl(x))));
        System.out.println(decryptDES(x,Key_a1235678));

    }

    // DES 解密
    public static String decryptDES(String encryptedData, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, generateDESKey(key));
        byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedData, StandardCharsets.UTF_8);
    }

    private static final String DES_ALGORITHM = "DES";

    // 生成 DES 密钥
    // 生成固定的 8 字节 DES 密钥
    private static SecretKey generateDESKey(String key) {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        byte[] keyMaterial = Arrays.copyOf(keyBytes, 8); // 截取前 8 字节
        return new SecretKeySpec(keyMaterial, "DES");
    }
}
