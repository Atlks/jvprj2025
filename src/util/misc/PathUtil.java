package util.misc;

import java.io.File;
import java.net.URISyntaxException;

public class PathUtil {

    public static void main(String[] args) {
      //  System.out.println(com.p6spy.engine.spy.appender.SingleLineFormat.class);
     //   System.out.println(com.p6spy.engine.logging.format.MySQLBinaryFormat);
    }
    @org.jetbrains.annotations.NotNull
    public static String getDirTaget( ) throws URISyntaxException {
        // 获取 target/classes 目录
        File classesDir = new File(PathUtil.class.getProtectionDomain().getCodeSource().getLocation().toURI());

        // 获取 target 目录
        File targetDir = classesDir.getParentFile();

        // 获取与 classes 同级的 static 目录

//        File staticDir = new File(targetDir, dirWzClassesDirSameLev);

        String staticDir_absolutePath = targetDir.getAbsolutePath();
        System.out.println("target directory path: " + staticDir_absolutePath);
        return staticDir_absolutePath;
    }
}
