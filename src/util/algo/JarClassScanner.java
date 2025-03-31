package util.algo;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static util.misc.util2026.printLn;
import static util.misc.util2026.scanAllClass;

public class JarClassScanner {

    public static void main(String[] args) {
        System.out.println(getCurrentJarPath());
    }

    public  static List<Class<?>> getClassesList()
    {
        List<Class<?>> classList = new ArrayList<>();
        //===================scan ini all
        Consumer<Class> csmr4log = clazz -> {

            try{
                if(clazz.getName().startsWith("com") || clazz.getName().startsWith("org"))
                    return;
                classList.add(clazz);

            } catch (Throwable e) {
                printLn("getClassesList失败: " + clazz.getName());
                printLn("getClassesList失败msg: " + e.getMessage());
//
            }



        };
        scanAllClass(csmr4log);//  all add class  ...  mdfyed class btr
        return  classList;

    }
    public static String getTargetPath() {
        String jarOrClsesPath= getCurrentJarPath();
      return   new File(jarOrClsesPath).getParent();
    }

    public static String getPrjPath() {
        String tgt= getTargetPath();
        return   new File(tgt).getParent();
    }


        /**
         * 获取当前 JAR 文件的路径  C:\apps\myapp.jar
         * if dir mode ,ret  C:\0prj\OptiBot\target\classes
         * 未打包成 JAR），则可能返回classes 目录：
         */
    public static String getCurrentJarPath() {
        try {
            CodeSource codeSource = JarClassScanner.class.getProtectionDomain().getCodeSource();
            if (codeSource != null && codeSource.getLocation() != null) {
                return new java.io.File(codeSource.getLocation().toURI()).getAbsolutePath();
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return "";
    }
}