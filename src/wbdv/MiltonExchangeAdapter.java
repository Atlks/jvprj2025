package wbdv;



import com.sun.net.httpserver.HttpExchange;
import io.milton.http.*;

import java.io.*;
import java.net.URI;
import java.util.*;

public class MiltonExchangeAdapter extends AbstractRequest {
    private final HttpExchange exchange;
    private final Map<String, String> headers;

    public MiltonExchangeAdapter(HttpExchange exchange) {
      //  super(new MiltonResponseAdapter(exchange));
        this.exchange = exchange;
        this.headers = new HashMap<>();
        for (Map.Entry<String, List<String>> e : exchange.getRequestHeaders().entrySet()) {
            if (!e.getValue().isEmpty()) {
                headers.put(e.getKey(), e.getValue().get(0));
            }
        }
    }

    @Override
    public Method getMethod() {
        try {
            return Method.valueOf(exchange.getRequestMethod());
        } catch (IllegalArgumentException e) {
            return null; // unsupported method
        }
    }

    @Override
    public Auth getAuthorization() {
        return null;
    }

    @Override
    public void setAuthorization(Auth auth) {

    }

    @Override
    public String getAbsoluteUrl() {
        URI uri = exchange.getRequestURI();
        String scheme = "http"; // https support skipped for now
        return scheme + "://" + exchange.getLocalAddress().getHostName() + ":" +
                exchange.getLocalAddress().getPort() + uri.toString();
    }

    @Override
    public String getHostHeader() {
        return exchange.getRequestHeaders().getFirst("Host");
    }

    //@Override
    public String getRequestHeader(String name) {
        return headers.get(name);
    }

  //  @Override
    public Iterator<String> getHeaders(String name) {
        List<String> values = exchange.getRequestHeaders().get(name);
        return values == null ? Collections.emptyIterator() : values.iterator();
    }

    @Override
    public InputStream getInputStream() {
        return exchange.getRequestBody();
    }

    @Override
    public void parseRequestParameters(Map<String, String> map, Map<String, FileItem> map1) throws RequestParseException {

    }

    @Override
    public String getRemoteAddr() {
        return exchange.getRemoteAddress().getAddress().getHostAddress();
    }

    @Override
    public Map<String, String> getHeaders() {
        return Map.of();
    }

    @Override
    public String getFromAddress() {
        return "";
    }

    @Override
    public String getRequestHeader(Header header) {
        return "";
    }

    @Override
    public String getRefererHeader() {
        return getRequestHeader("Referer");
    }

    @Override
    public String getUserAgentHeader() {
        return getRequestHeader("User-Agent");
    }

    @Override
    public Cookie getCookie(String s) {
        return null;
    }

    @Override
    public List<Cookie> getCookies() {
        return List.of();
    }
}
