package util.oo;

import java.io.File;

public class FileUti {

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
