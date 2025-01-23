package biz;

import java.util.Map;

import static util.util2026.parse_ini_fileNosec;

public class BaseBiz {

    public static String saveDir = "";

    static {
        // 获取类加载器 /C:/Users/attil/IdeaProjects/jvprj2025/out/production/jvprj2025/
        String rootPath = UserBiz.class.getClassLoader().getResource("").getPath();
        Map cfg = parse_ini_fileNosec(rootPath + "../../../cfg/dbcfg.ini");
        saveDir = (String) cfg.get("savedir");
    }

}
