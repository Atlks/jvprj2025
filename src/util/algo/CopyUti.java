package util.algo;

// org.springframework.beans.BeanUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class CopyUti {


    public static  void moveFile(String source, String target) throws IOException { Path sourcePath = Paths.get(source);
        Path targetPath = Paths.get(target);

        // 检查源文件是否存在
        if (!Files.exists(sourcePath)) {
            System.out.println("源文件不存在: " + source);
            return;
        }

        try {
            // 创建目标目录（如果不存在）
            Files.createDirectories(targetPath.getParent());

            // 移动文件（如果目标已存在，则覆盖）
            Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("文件移动成功: " + target);
        } catch (IOException e) {
            System.out.println("文件移动失败！");
            throw  e;
        }

    }
    public static  void moveFile(File file, String saveDir) {
        if (file == null || !file.exists()) {
            System.out.println("源文件不存在！");
            return;
        }

        // 创建目标目录（如果不存在）
        File dir = new File(saveDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 构造源路径和目标路径
        Path sourcePath = file.toPath();
        Path targetPath = Paths.get(saveDir, file.getName());

        try {
            // 移动文件，若目标已存在则覆盖
            Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("文件移动成功: " + targetPath);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("文件移动失败！");
        }

    }

    /**
     *
     * @param source
     * @param target
     */
    public  static  void  copyFile(String source, String target) throws Exception {
        Path sourcePath = Paths.get(source);
        Path targetPath = Paths.get(target);

        // 检查源文件是否存在
        if (!Files.exists(sourcePath)) {
            System.out.println("源文件不存在：" + source);
            return;
        }

        // 确保目标目录存在
        try {
            Files.createDirectories(targetPath.getParent());
        } catch (IOException e) {
            System.out.println("无法创建目标目录：" + targetPath.getParent());
             throw e;
        }

        try {
            // 执行复制，存在目标则覆盖
            Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("复制成功：" + targetPath);
        } catch (IOException e) {
            System.out.println("复制失败！");
             throw e;
        }
    }
    public  static  void  copyFileToDir(String filepath, String saveDir){
        copyFileToDir(new File(filepath),saveDir);
    }
    public static void copyFileToDir(File file, String saveDir) {

        if (file == null || !file.exists()) {
            System.out.println("源文件不存在！");
            return;
        }

        // 创建目标目录（如果不存在）
        File dir = new File(saveDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 构建目标路径
        Path sourcePath = file.toPath();
        Path targetPath = Paths.get(saveDir, file.getName());

        try {
            // 执行复制操作，存在则覆盖
            Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("文件复制成功: " + targetPath);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("文件复制失败！");
        }
    }

    /**
     * 复制属性
     * 将 from 对象的同名属性复制到 target 对象中
     *
     * @param from   来源对象
     * @param target 目标对象
     */
    public static void copyProp(Object from, Object target) {
        if (from == null || target == null) {
            throw new IllegalArgumentException("Source and target must not be null");
        }
        copyProperties(from, target);
    }

    private static void copyProperties(Object from, Object target) {

        if (from == null || target == null) return;

        Class<?> fromClass = from.getClass();
        Class<?> targetClass = target.getClass();

        Field[] fromFields = fromClass.getDeclaredFields();

        for (Field fromField : fromFields) {
            try {
                fromField.setAccessible(true);
                String name = fromField.getName();
                Object value = fromField.get(from);

                Field targetField = null;
                try {
                    targetField = targetClass.getDeclaredField(name);
                } catch (NoSuchFieldException e) {
                    continue; // skip if target doesn't have the field
                }

                if (targetField.getType().equals(fromField.getType())) {
                    targetField.setAccessible(true);
                    targetField.set(target, value);
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace(); // or handle logging
            }
        }
    }
}
