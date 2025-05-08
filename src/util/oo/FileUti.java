package util.oo;

import util.cache.CantFindFileEx;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Duration;
import java.time.Instant;

public class FileUti {


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
     * @param i
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
