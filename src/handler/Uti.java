package handler;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import util.annos.Paths;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;

import static util.oo.HttpUti.getParamMap;
import static util.serverless.ApiGateway.httpExchangeCurThrd;
@PermitAll
public class Uti {

    @PermitAll
    @Paths({"/oth/LookLog"})
    @Produces(MediaType.TEXT_PLAIN)
    @NoWarpApiRsps
    public static Object LookLog() throws Throwable {
          return  readFile("app.log");

    }

    private static String readFile(String file) throws IOException {

            return new String(Files.readAllBytes(java.nio.file.Paths.get(file)), StandardCharsets.UTF_8);

    }

    @PermitAll
    @Paths({"/oth/ClrLogFileHdl"})
    public static Object ClrLogFileHdl() throws Exception, ExecFailEx {
        Map m=getCurrHttpMap();
        delFil("app.log");
        return "ok";

    }

    private static Map getCurrHttpMap() throws Exception {
        return  getParamMap(httpExchangeCurThrd.get());
    }

    public static void delFil(String file) throws ExecFailEx {
        System.out.println("fun delfile(f="+file+")");
        if (file == null || file.isEmpty()) return;
        File f = new File(file);
        if (f.exists() && f.isFile()) {
            if (!f.delete()) {
                System.err.println("无法删除文件: " + file);
                throw new ExecFailEx("f="+file);
            }
        }
    }
}
