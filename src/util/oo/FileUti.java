package util.oo;

import util.cache.CantFindFileEx;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static cfg.Containr.testUnitMode;

public class FileUti {

    public static String readTxtFrmFile(File f) {
        System.out.println("fun rdTxt(f="+f.toPath());
        try {
            return new String(java.nio.file.Files.readAllBytes(f.toPath()), java.nio.charset.StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static boolean exist(String fname, String dir) {
        System.out.println("fun exist(f="+fname+",dir="+dir);
        File targetDir = new File(dir);
        if (!targetDir.exists()) {
            targetDir.mkdirs(); // 确保目录存在
        }


        File targetFile = new File(dir, fname);
        boolean exists = targetFile.exists();
        System.out.println("endfun exist(),ret="+exists+"");
        return exists;
    }

    public static void copyFile2Dir(File f, String dir) {
        File targetDir = new File(dir);
        if (!targetDir.exists()) {
            targetDir.mkdirs(); // 确保目录存在
        }

        File targetFile = new File(targetDir, f.getName());

        try (InputStream in = new FileInputStream(f);
             OutputStream out = new FileOutputStream(targetFile)) {

            byte[] buffer = new byte[8192];
            int len;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (IOException e) {
            throw new RuntimeException("复制文件失败: " + f.getName(), e);
        }
    }

    public static File[] getFilesInJarDir(String pathInJar) {
        List<File> filesList = new ArrayList<>();
        try {
            //  jarpath=/C:/0prj/jvprj2025/target/classes/
            String jarPath = FileUti.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            if(testUnitMode)
                jarPath=new File(jarPath).getParent()+"/classes/";
            if(new File(jarPath).isFile() && jarPath.endsWith(".jar"))
            {
                procssByJarfile(pathInJar, jarPath, filesList);
            }
            else{
                processByDirClzs(pathInJar, jarPath, filesList);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return filesList.toArray(new File[0]);
    }

    private static void processByDirClzs(String pathInDir, String classPath, List<File> filesList) {
        String pathname = classPath + pathInDir;
        // 处理目录中的文件
        File dir = new File(classPath, pathInDir);
        if (dir.isDirectory()) {
            File[] files = dir.listFiles((dir1, name) -> !name.endsWith(".class"));
            if (files != null) {
                for (File f : files) {
                    filesList.add(f);
                }
            }
        }
    }

    private static void procssByJarfile(String pathInJar, String jarPath, List<File> result) throws IOException {
        try (JarFile jarFile = new JarFile(jarPath)) {
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
//                if(entry.getPath！=pathInJar)
//                    continue;
                String name = entry.getName();
                //entry.getName() 是这个文件的路径，比如：
                System.out.println("entry getname="+name);
                //filt pathInJar
                if(!name.startsWith(pathInJar))
                    continue;
                if (name.startsWith(pathInJar) && !entry.isDirectory()) {
                    // 将 JAR 中的资源提取到临时文件（可选）
                    InputStream is = jarFile.getInputStream(entry);
                    File temp = newFile("jarDir2025", "" + new File(name).getName());
                    temp.deleteOnExit();
                    try (FileOutputStream fos = new FileOutputStream(temp)) {
                        is.transferTo(fos);
                    }
                    result.add(temp);
                }
            }
        }
    }

    private static File newFile(String jarDir2025, String filename) {
        if (jarDir2025 == null || filename == null) {
            throw new IllegalArgumentException("路径和文件名不能为空");
        }
        return new File(jarDir2025, filename);
    }


    /**
     * 获取文件修改时间，单位秒
     * @param pathname
     * @return
     * @throws IOException
     * @throws CantFindFileEx
     */
    public static long fileModifyTimeDuration(String pathname) throws IOException, CantFindFileEx {

        Path path = Paths.get(pathname);
        if (!Files.exists(path)) {
            throw  new CantFindFileEx(pathname+" not exst");
        }

        BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
        Instant createTime = attrs.lastModifiedTime().toInstant();
        Instant now = Instant.now();
        Duration duration = Duration.between(createTime, now);

        return duration.toSeconds() ;

    }



    /**
     * 读取文件创建时间，如果与当前时间相差超过5分钟，返回true
     *
     * @param pathname

     * @return
     */
    private static long FileCreateTimeGrtThan(String pathname) throws IOException, CantFindFileEx {

        Path path = Paths.get(pathname);
        if (!Files.exists(path)) {
            throw  new CantFindFileEx(pathname+" not exst");
        }

        BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
        Instant createTime = attrs.creationTime().toInstant();
        Instant now = Instant.now();
        Duration duration = Duration.between(createTime, now);

        return duration.toMinutes() ;

    }


    public static void delFile(String delfile) {
        new File(delfile).delete();
    }

    @org.jetbrains.annotations.NotNull
    public static File mkdir(String dir) {
        File saveDirColl = new File(dir);
        if (!saveDirColl.exists()) {
            saveDirColl.mkdirs(); // 创建文件夹（如果不存在）
        }
        return saveDirColl;
    }
}
