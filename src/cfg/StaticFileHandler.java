package cfg;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import static util.algo.GetUti.getImageMimeType;
import static util.algo.IsXXX.isImp;

public class StaticFileHandler implements HttpHandler {
    private final File rootDir;
    private final String rmvUrlPathPrefix;

    public StaticFileHandler(String staticDir, String rmvUrlPathPrefix) {
        this.rmvUrlPathPrefix = rmvUrlPathPrefix;
        this.rootDir = new File(staticDir);
        if (!rootDir.exists() || !rootDir.isDirectory()) {
            System.out.println("静态文件目录不存在: " + staticDir);
            //throw new IllegalArgumentException("静态文件目录不存在: " + staticDir);
        }
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestPath = exchange.getRequestURI().getPath().replaceFirst("^" + rmvUrlPathPrefix, "");
        File file = new File(rootDir, requestPath);



        if (!file.exists() || file.isDirectory()) {
            exchange.sendResponseHeaders(404, 0);
            exchange.getResponseBody().write("404 Not Found".getBytes());
            exchange.getResponseBody().close();
            return;
        }

        byte[] fileBytes = Files.readAllBytes(file.toPath());
        if (isImp(file)) {
            String contentType = getImageMimeType(file);
            System.out.println("contentType="+contentType);
         //   exchange.set
            exchange.getResponseHeaders().set("Content-Type", contentType);
        } else if (isHtml(file)) {  //is json html,txt
            exchange.getResponseHeaders().set("Content-Type", "text/html");
        }
        else if (isJson(file)) {  //is json html,txt
            exchange.getResponseHeaders().set("Content-Type", "application/json");
        }
        else {
            exchange.getResponseHeaders().set("Content-Type", "application/octet-stream");
        }
        //  exchange.getResponseHeaders().set("Content-Type", "img/mpeg");
        // 发送 200 OK 状态，并写入文件内容
        exchange.sendResponseHeaders(200, fileBytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(fileBytes);
        }
    }

    private boolean isJson(File file) {
        if (file == null || !file.exists() || !file.isFile()) {
            return false;
        }
        String fileName = file.getName().toLowerCase();
        return fileName.endsWith(".json")  ; }

    private boolean isHtml(File file) {

        if (file == null || !file.exists() || !file.isFile()) {
            return false;
        }
        String fileName = file.getName().toLowerCase();
        return fileName.endsWith(".html") || fileName.endsWith(".htm") || fileName.endsWith(".txt");
    }


}
