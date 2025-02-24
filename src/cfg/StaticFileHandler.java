package cfg;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class StaticFileHandler implements HttpHandler {
    private final File rootDir;

    public StaticFileHandler(String staticDir) {
        this.rootDir = new File(staticDir);
        if (!rootDir.exists() || !rootDir.isDirectory()) {
            throw new IllegalArgumentException("静态文件目录不存在: " + staticDir);
        }
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestPath = exchange.getRequestURI().getPath().replaceFirst("^/static", "");
        File file = new File(rootDir, requestPath);

        if (!file.exists() || file.isDirectory()) {
            exchange.sendResponseHeaders(404, 0);
            exchange.getResponseBody().write("404 Not Found".getBytes());
            exchange.getResponseBody().close();
            return;
        }

        byte[] fileBytes = Files.readAllBytes(file.toPath());
        exchange.sendResponseHeaders(200, fileBytes.length);
        exchange.getResponseBody().write(fileBytes);
        exchange.getResponseBody().close();
    }
}
