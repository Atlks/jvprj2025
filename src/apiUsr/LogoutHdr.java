package apiUsr;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

import static util.util2026.setcookie;
import static util.util2026.wrtResp;

public class LogoutHdr  implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        setcookie("uname","",exchange);
        wrtResp(exchange, "ok");
    }
}
