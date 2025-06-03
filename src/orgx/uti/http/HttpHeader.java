package orgx.uti.http;

import io.javalin.http.HandlerType;
import lombok.Data;

import java.util.List;
import java.util.Map;
@Data
public class HttpHeader {
    private HandlerType method;
    private String url;
    private String host;
    private String userAgent;
    private String contentType;
    private String accept;
    private String authorization;
    private String cacheControl;
    private String connection;
    private String referer;
    /**
     * 在 HttpExchange.getRequestHeaders() 方法中，返回的请求头数据格式是 Map<String, List<String>>，这是因为：
     *
     * ✅ HTTP 头部允许多个值 → 有些 HTTP 头部可以有多个值，例如：
     *
     * http
     * Accept: text/html
     * Accept: application/json
     * 在这种情况下，Accept 头部的值会存储为 List<String>。
     *
     * ✅ 保证顺序一致 → List<String> 确保请求头的多个值按照接收顺序存储，不会被覆盖。
     *
     * ✅ 标准 HTTP 头部格式 → 一些 HTTP 头部规范（如 Set-Cookie）要求多个值，因此需要使用 List<String> 来支持它们。
     */
    private Map<String, List<String>> allHeaders; // 存储其他不常见的头部

    public HttpHeader(HandlerType method, String url, String host, String userAgent, String contentType,
                      String accept, String authorization, String cacheControl, String connection,
                      String referer, Map<String, List<String>> allxHeaders) {
        this.method = method;
        this.url = url;
        this.host = host;

        this.contentType = contentType;
        this.accept = accept;
        this.authorization = authorization;
        this.cacheControl = cacheControl;
        this.connection = connection;
        this.referer = referer;
        this.userAgent = userAgent;
        this.allHeaders = allxHeaders;
    }


    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getHost() { return host; }
    public void setHost(String host) { this.host = host; }

    public String getUserAgent() { return userAgent; }
    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }

    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }

    public String getAccept() { return accept; }
    public void setAccept(String accept) { this.accept = accept; }

    public String getAuthorization() { return authorization; }
    public void setAuthorization(String authorization) { this.authorization = authorization; }

    public String getCacheControl() { return cacheControl; }
    public void setCacheControl(String cacheControl) { this.cacheControl = cacheControl; }

    public String getConnection() { return connection; }
    public void setConnection(String connection) { this.connection = connection; }

    public String getReferer() { return referer; }
    public void setReferer(String referer) { this.referer = referer; }


    @Override
    public String toString() {
        return "HttpHeader{" +
                "method='" + method + '\'' +
                ", url='" + url + '\'' +
                ", host='" + host + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", contentType='" + contentType + '\'' +
                ", accept='" + accept + '\'' +
                ", authorization='" + authorization + '\'' +
                ", cacheControl='" + cacheControl + '\'' +
                ", connection='" + connection + '\'' +
                ", referer='" + referer + '\'' +
                ", customHeaders=" + allHeaders +
                '}';
    }
}
