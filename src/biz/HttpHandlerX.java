package biz;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public interface HttpHandlerX extends HttpHandler {
    public abstract void handlex(HttpExchange exchange) throws Exception;
}
