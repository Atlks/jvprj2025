package util.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static util.algo.ReadUti.ReadFileAsJsonObjOrJsonArray;
import static util.algo.ReadUti.wrtFile;
import static util.misc.Util2025.encodeJson;
import static util.oo.FileUti.fileModifyTimeDuration;
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
            long passTimeSecs = fileModifyTimeDuration(pathname);
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
