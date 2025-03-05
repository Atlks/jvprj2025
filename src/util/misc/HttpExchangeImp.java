package util.misc;

import com.sun.net.httpserver.*;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static util.misc.util2026.iniHttpExchg;


public class HttpExchangeImp extends HttpExchange {
    private URI url;
    private Map<String, String> reqHdr;
    private Headers rspsHdrs;
    private OutputStream OutputStream1;

    public HttpExchangeImp(String url, String cookiestr, String respOutStream_file) throws FileNotFoundException {

        setRequestURI(url);

        Map<String, String> reqhdr = new HashMap<>();
        reqhdr.put("Cookie", cookiestr);


        iniHttpExchg(this, reqhdr, respOutStream_file);

    }

    public HttpExchangeImp() {

    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public Headers getRequestHeaders() {
        return  toHeaders(this.reqHdr);
    }

    private com. sun. net. httpserver.Headers toHeaders(Map<String, String> reqHdr) {
        Headers headers = new Headers();

        // 遍历 Map，将每个键值对添加到 Headers
        for (Map.Entry<String, String> entry : reqHdr.entrySet()) {
            headers.add(entry.getKey(), entry.getValue());
        }

        return headers;

    }

    /**
     * Returns a mutable {@link Headers} into which the HTTP response headers
     * can be stored and which will be transmitted as part of this response.
     *
     * <p> The keys in the {@code Headers} are the header names, while the
     * values must be a {@link List} of {@linkplain String Strings}
     * containing each value that should be included multiple times (in the
     * order that they should be included).
     *
     * <p> The keys in {@code Headers} are case-insensitive.
     *
     * @return a writable {@code Headers} which can be used to set response
     * headers.
     */
    @Override
    public Headers getResponseHeaders() {
        return this.rspsHdrs;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public URI getRequestURI() {
        return  ( this.url);
    }


    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public String getRequestMethod() {
        return "";
    }

    /**
     * Returns the {@link HttpContext} for this exchange.
     *
     * @return the {@code HttpContext}
     */
    @Override
    public HttpContext getHttpContext() {
        return null;
    }

    /**
     * Ends this exchange by doing the following in sequence:
     * <ol>
     *      <li> close the request {@link InputStream}, if not already closed.
     *      <li> close the response {@link OutputStream}, if not already closed.
     * </ol>
     */
    @Override
    public void close() {

    }

    /**
     * Returns a stream from which the request body can be read.
     * Multiple calls to this method will return the same stream.
     * It is recommended that applications should consume (read) all of the data
     * from this stream before closing it. If a stream is closed before all data
     * has been read, then the {@link InputStream#close()} call will read
     * and discard remaining data (up to an implementation specific number of
     * bytes).
     *
     * @return the stream from which the request body can be read
     */
    @Override
    public InputStream getRequestBody() {
        return null;
    }

    /**
     * Returns a stream to which the response body must be
     * written. {@link #sendResponseHeaders(int, long)}) must be called prior to
     * calling this method. Multiple calls to this method (for the same exchange)
     * will return the same stream. In order to correctly terminate each exchange,
     * the output stream must be closed, even if no response body is being sent.
     *
     * <p> Closing this stream implicitly closes the {@link InputStream}
     * returned from {@link #getRequestBody()} (if it is not already closed).
     *
     * <p> If the call to {@link #sendResponseHeaders(int, long)} specified a
     * fixed response body length, then the exact number of bytes specified in
     * that call must be written to this stream. If too many bytes are written,
     * then the write method of {@link OutputStream} will throw an {@code IOException}.
     * If too few bytes are written then the stream
     * {@link OutputStream#close()} will throw an {@code IOException}.
     * In both cases, the exchange is aborted and the underlying TCP connection
     * closed.
     *
     * @return the stream to which the response body is written
     */
    @Override
    public OutputStream getResponseBody() {
        return this.OutputStream1;
    }

    /**
     * Starts sending the response back to the client using the current set of
     * response headers and the numeric response code as specified in this
     * method. The response body length is also specified as follows. If the
     * response length parameter is greater than {@code zero}, this specifies an
     * exact number of bytes to send and the application must send that exact
     * amount of data. If the response length parameter is {@code zero}, then
     * chunked transfer encoding is used and an arbitrary amount of data may be
     * sent. The application terminates the response body by closing the
     * {@link OutputStream}.
     * If response length has the value {@code -1} then no response body is
     * being sent.
     *
     * <p> If the content-length response header has not already been set then
     * this is set to the appropriate value depending on the response length
     * parameter.
     *
     * <p> This method must be called prior to calling {@link #getResponseBody()}.
     *
     * @param rCode          the response code to send
     * @param responseLength if {@literal > 0}, specifies a fixed response body
     *                       length and that exact number of bytes must be written
     *                       to the stream acquired from {@link #getResponseCode()}
     *                       If {@literal == 0}, then chunked encoding is used,
     *                       and an arbitrary number of bytes may be written.
     *                       If {@literal <= -1}, then no response body length is
     *                       specified and no response body may be written.
     * @throws IOException if the response headers have already been sent or an I/O error occurs
     * @implNote This implementation allows the caller to instruct the
     * server to force a connection close after the exchange terminates, by
     * supplying a {@code Connection: close} header to the {@linkplain
     * #getResponseHeaders() response headers} before {@code sendResponseHeaders}
     * is called.
     * @see HttpExchange#getResponseBody()
     */
    @Override
    public void sendResponseHeaders(int rCode, long responseLength) throws IOException {

    }

    /**
     * Returns the address of the remote entity invoking this request.
     *
     * @return the {@link InetSocketAddress} of the caller
     */
    @Override
    public InetSocketAddress getRemoteAddress() {
        return null;
    }

    /**
     * Returns the response code, if it has already been set.
     *
     * @return the response code, if available. {@code -1} if not available yet.
     */
    @Override
    public int getResponseCode() {
        return 0;
    }

    /**
     * Returns the local address on which the request was received.
     *
     * @return the {@link InetSocketAddress} of the local interface
     */
    @Override
    public InetSocketAddress getLocalAddress() {
        return null;
    }

    /**
     * Returns the protocol string from the request in the form
     * <i>protocol/majorVersion.minorVersion</i>. For example,
     * "{@code HTTP/1.1}".
     *
     * @return the protocol string from the request
     */
    @Override
    public String getProtocol() {
        return "";
    }

    /**
     * {@link Filter} modules may store arbitrary objects with {@code HttpExchange}
     * instances as an out-of-band communication mechanism. Other filters
     * or the exchange handler may then access these objects.
     *
     * <p> Each {@code Filter} class will document the attributes which they make
     * available.
     *
     * @param name the name of the attribute to retrieve
     * @return the attribute object, or {@code null} if it does not exist
     * @throws NullPointerException if name is {@code null}
     */
    @Override
    public Object getAttribute(String name) {
        return null;
    }

    /**
     * {@link Filter} modules may store arbitrary objects with {@code HttpExchange}
     * instances as an out-of-band communication mechanism. Other filters
     * or the exchange handler may then access these objects.
     *
     * <p> Each {@code Filter} class will document the attributes which they make
     * available.
     *
     * @param name  the name to associate with the attribute value
     * @param value the object to store as the attribute value. {@code null}
     *              value is permitted.
     * @throws NullPointerException if name is {@code null}
     */
    @Override
    public void setAttribute(String name, Object value) {

    }

    /**
     * Used by {@linkplain Filter Filters} to wrap either
     * (or both) of this exchange's {@link InputStream} and
     * {@link OutputStream}, with the given filtered streams so that
     * subsequent calls to {@link #getRequestBody()} will return the given
     * {@code InputStream}, and calls to {@link #getResponseBody()} will return
     * the given {@code OutputStream}. The streams provided to this call must wrap
     * the original streams, and may be (but are not required to be) sub-classes
     * of {@link FilterInputStream} and {@link FilterOutputStream}.
     *
     * @param i the filtered input stream to set as this object's
     *          {@code Inputstream}, or {@code null} if no change
     * @param o the filtered output stream to set as this object's
     *          {@code Outputstream}, or {@code null} if no change
     */
    @Override
    public void setStreams(InputStream i, OutputStream o) {

    }

    /**
     * If an authenticator is set on the {@link HttpContext} that owns this exchange,
     * then this method will return the {@link HttpPrincipal} that represents
     * the authenticated user for this {@code HttpExchange}.
     *
     * @return the {@code HttpPrincipal}, or {@code null} if no authenticator is set
     */
    @Override
    public HttpPrincipal getPrincipal() {
        return null;
    }

    public void setRequestURI(String url) {
   this.url=toURI2025(url);
    }

    private URI toURI2025(String url) {
        try {
            // 将字符串转换为URI对象
            return new URI(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null; // 如果转换失败，返回null
        }
    }

    public void setRequestHeaders(Map<String, String> reqhdr) {
     this.reqHdr=reqhdr;
    }

    public void setResponseHeaders(Headers headers) {
       this.rspsHdrs=headers;
    }

    public void setResponseBody(OutputStream OutputStream1) {
       this.OutputStream1=OutputStream1;
    }
}
