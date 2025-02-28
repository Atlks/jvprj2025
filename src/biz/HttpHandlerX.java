package biz;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public interface  HttpHandlerX<T>  {
    public abstract Object handlex(T dto) throws Exception;
}
