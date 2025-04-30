package util.algo;

import org.springframework.beans.BeanUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class CopyUti {



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
    public  static  void  copyFile(String filepath, String saveDir){
        copyProp(new File(filepath),saveDir);
    }
    public static void copyFile(File file, String saveDir) {

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
        BeanUtils.copyProperties(from, target);
    }
}
