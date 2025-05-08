package util.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static util.algo.ReadUti.ReadFileAsJsonObjOrJsonArray;
import static util.algo.ReadUti.wrtFile;
import static util.misc.Util2025.encodeJson;
import static util.oo.FileUti.delFile;

/**
 * 文件模式缓存,每个key保存为一个文件，文件名就是key
 *  缓存目录是 cacheDir
 */
public class CacheFil implements CacheItfs {
    // 声明一个缓存（key 是字符串，value

    private static final Cache<String, Object> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .maximumSize(1000)
            .build();

    @SuppressWarnings("unchecked")
    public static <T> T getOrLoad(String key, Supplier<T> supplier) {
        System.out.println("fun getOrLoad key="+key+" ");
        try {
            return (T) get(key, (Callable<Object>) supplier::get);
        } catch (Exception e) {
            return  supplier.get();
        }
    }

    private static Object get(String key, Callable<Object> get) throws Exception {
        String pathname = "cacheDir/" + key + ".json";

        try{
            long passTimeSecs = FileModifyTimeDuration(pathname);
            if(passTimeSecs>300) {
                delFile(pathname);
            }
        }catch (CantFindFileEx e) {
            System.out.println(e.getLocalizedMessage());
        }

        if(new File(pathname
        ).exists()) {

                return ReadFileAsJsonObjOrJsonArray(pathname);

        }else {
            //not exst ,put

                Object obj = get.call();
                String json = encodeJson(obj);
                wrtFile(pathname, json);
                return obj;

        }

    }


    /**
     * 获取文件修改时间，单位秒
     * @param pathname
     * @return
     * @throws IOException
     * @throws CantFindFileEx
     */
    private static long FileModifyTimeDuration(String pathname) throws IOException, CantFindFileEx {

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


    public static void put(String key, Object value) {
        try {
            String json = encodeJson(value);
            wrtFile("cacheDir/" + key + ".json", json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



//    public static void invalidate(String key) {
//        cache.invalidate(key);
//    }

    public static void clearAll() {
        cache.invalidateAll();
    }
}
