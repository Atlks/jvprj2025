package wbdv;

import com.sun.net.httpserver.HttpExchange;
import io.milton.http.Cookie;
import io.milton.http.Response;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

public   class MiltonResponseAdapter implements Response {
    private final HttpExchange exchange;
    private int status = 200;
    private final Map<String, String> headers = new HashMap<>();

    public MiltonResponseAdapter(HttpExchange exchange) {
        this.exchange = exchange;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status.code;
    }

    @Override
    public void setEtag(String s) {

    }

    @Override
    public void setContentRangeHeader(long l, long l1, Long aLong) {

    }

    @Override
    public Status getStatus() {
        return Status.fromCode(status);
    }

    @Override
    public Map<String, String> getHeaders() {
        return Map.of();
    }

    @Override
    public Long getContentLength() {
        return 0L;
    }

    @Override
    public void setContentEncodingHeader(ContentEncoding contentEncoding) {

    }

    @Override
    public void setExpiresHeader(Date date) {

    }

    @Override
    public void setLockTokenHeader(String s) {

    }

    @Override
    public void setAuthenticateHeader(List<String> list) {

    }

    @Override
    public void setContentLengthHeader(Long total) {
        if (total != null) {
            exchange.getResponseHeaders().set("Content-Length", String.valueOf(total));
        }
    }

    @Override
    public void setContentTypeHeader(String s) {

    }

    @Override
    public String getContentTypeHeader() {
        return "";
    }

    @Override
    public Entity getEntity() {
        return null;
    }

    @Override
    public void setEntity(Entity entity) {

    }

    @Override
    public void setCacheControlMaxAgeHeader(Long aLong) {

    }

    @Override
    public void setCacheControlPrivateMaxAgeHeader(Long aLong) {

    }

    @Override
    public void setCacheControlNoCacheHeader() {

    }

    @Override
    public void setLastModifiedHeader(Date date) {

    }

    @Override
    public void setDavHeader(String s) {

    }

    @Override
    public OutputStream getOutputStream() {
        exchange.getResponseHeaders().putAll(headersToMultiMap());
        try {
            exchange.sendResponseHeaders(status, 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return exchange.getResponseBody();
    }

    @Override
    public void setLocationHeader(String s) {

    }

    @Override
    public void setVaryHeader(String s) {

    }

    @Override
    public void setDateHeader(Date date) {

    }

    @Override
    public String getAccessControlAllowOrigin() {
        return "";
    }

    @Override
    public void setAccessControlAllowOrigin(String s) {

    }

    @Override
    public String getAcceptRanges() {
        return "";
    }

    @Override
    public void setAcceptRanges(String s) {

    }

    @Override
    public void close() {

    }

    @Override
    public void sendError(Status status, String s) {

    }

    @Override
    public void sendRedirect(String s) {

    }

    @Override
    public void sendPermanentRedirect(String s) {

    }

    @Override
    public Cookie setCookie(Cookie cookie) {
        return null;
    }

    @Override
    public Cookie setCookie(String s, String s1) {
        return null;
    }

    @Override
    public void setNonStandardHeader(String code, String value) {
        headers.put(code, value);
    }

    @Override
    public String getNonStandardHeader(String s) {
        return "";
    }

    @Override
    public void setAllowHeader(List<String> list) {

    }

    //@Override
    public void setResponseHeader(Response.Header header, String value) {
        headers.put(header.code, value);
    }

    private Map<String, List<String>> headersToMultiMap() {
        Map<String, List<String>> multi = new HashMap<>();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            multi.put(entry.getKey(), List.of(entry.getValue()));
        }
        return multi;
    }
}
