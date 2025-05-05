package util.misc;

import lombok.experimental.Accessors;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.tx.dbutil;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static cfg.Containr.sessionFactory;
import static util.algo.CallUtil.callTry;
import static util.tx.HbntUtil.executeUpdate;


@Accessors(chain = true)
public class Flywayx {
    private Connection conn;
    private String loc;

    public Flywayx dataSource(DataSource dataSource) throws SQLException {
        this.conn = dataSource.getConnection();
        return this;
    }

    public Flywayx locations(String locations) {
        this.loc = locations;
        return this;
    }

    public Flywayx load() {


        return this;
    }

    private String readTxtFrmFile(File f) {
        System.out.println("fun rdTxt(f="+f.toPath());
        try {
            return new String(java.nio.file.Files.readAllBytes(f.toPath()), java.nio.charset.StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    private File[] getFilesInJarDir(String pathInJar) {
        List<File> filesList = new ArrayList<>();
        try {
            //  jarpath=/C:/0prj/jvprj2025/target/classes/
            String jarPath = getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
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
                String name = entry.getName();
                if (name.startsWith(pathInJar) && !entry.isDirectory()) {
                    // 将 JAR 中的资源提取到临时文件（可选）
                    InputStream is = jarFile.getInputStream(entry);
                    File temp = File.createTempFile("jarfile_", "_" + new File(name).getName());
                    temp.deleteOnExit();
                    try (FileOutputStream fos = new FileOutputStream(temp)) {
                        is.transferTo(fos);
                    }
                    result.add(temp);
                }
            }
        }
    }

    public void migrate() {

        String path = this.loc.substring(11);
        File[] files = getFilesInJarDir(path);
        for (File f : files) {
            String txt = readTxtFrmFile(f);
            String[] stmt = txt.split(";");
            for (String sql : stmt) {
                System.out.println(sql);
               callTry(()->{
                 dbutil.  executeUpdate(sql, conn);

               });

            }
        }
    }


}
