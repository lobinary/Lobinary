/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2010, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package javax.xml.ws.spi.http;

import javax.xml.ws.handler.MessageContext;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.security.Principal;

/**
 * This class encapsulates a HTTP request received and a
 * response to be generated in one exchange. It provides methods
 * for examining the request from the client, and for building and
 * sending the response.
 * <p>
 * A <code>HttpExchange</code> must be closed to free or reuse
 * underlying resources. The effect of failing to close an exchange
 * is undefined.
 *
 * <p>
 *  此类封装了在一个交换中收到的HTTP请求和要生成的响应。它提供了用于检查来自客户端的请求以及用于构建和发送响应的方法。
 * <p>
 *  必须关闭<code> HttpExchange </code>才能释放或重用基础资源。未能关闭交换的效果未定义。
 * 
 * 
 * @author Jitendra Kotamraju
 * @since JAX-WS 2.2
 */
public abstract class HttpExchange {

    /**
     * Standard property: cipher suite value when the request is received
     * over HTTPS
     * <p>Type: String
     * <p>
     *  标准属性：通过HTTPS接收请求时的密码套件值<p>类型：字符串
     * 
     */
    public static final String REQUEST_CIPHER_SUITE =
            "javax.xml.ws.spi.http.request.cipher.suite";

    /**
     * Standard property: bit size of the algorithm when the request is
     * received over HTTPS
     * <p>Type: Integer
     * <p>
     *  标准属性：通过HTTPS接收请求时算法的位大小<p>类型：整数
     * 
     */
    public static final String REQUEST_KEY_SIZE =
            "javax.xml.ws.spi.http.request.key.size";

    /**
     * Standard property: A SSL certificate, if any, associated with the request
     *
     * <p>Type: java.security.cert.X509Certificate[]
     * The order of this array is defined as being in ascending order of trust.
     * The first certificate in the chain is the one set by the client, the next
     * is the one used to authenticate the first, and so on.
     * <p>
     *  标准属性：与请求相关联的SSL证书(如果有)
     * 
     *  <p>类型：java.security.cert.X509Certificate []此数组的顺序定义为按信任的升序排列。
     * 链中的第一个证书是由客户端设置的证书,下一个是用于验证第一个证书的证书,依此类推。
     * 
     */
    public static final String REQUEST_X509CERTIFICATE =
            "javax.xml.ws.spi.http.request.cert.X509Certificate";

    /**
     * Returns an immutable Map containing the HTTP headers that were
     * included with this request. The keys in this Map will be the header
     * names, while the values will be a List of Strings containing each value
     * that was included (either for a header that was listed several times,
     * or one that accepts a comma-delimited list of values on a single line).
     * In either of these cases, the values for the header name will be
     * presented in the order that they were included in the request.
     * <p>
     * The keys in Map are case-insensitive.
     *
     * <p>
     *  返回包含此请求所包含的HTTP标头的不可变地图。此映射中的键将是标题名,而值将是包含所包含的每个值的字符串列表(对于列出多次的标题,或者接受逗号分隔的值的列表单线)。
     * 在这些情况下,头名称的值将按照它们包含在请求中的顺序显示。
     * <p>
     * Map中的键不区分大小写。
     * 
     * 
     * @return an immutable Map which can be used to access request headers
     */
    public abstract Map<String, List<String>> getRequestHeaders();

    /**
     * Returns the value of the specified request header. If the request
     * did not include a header of the specified name, this method returns
     * null. If there are multiple headers with the same name, this method
     * returns the first header in the request. The header name is
     * case-insensitive. This is a convienence method to get a header
     * (instead of using the {@link #getRequestHeaders}).
     *
     * <p>
     *  返回指定的请求头的值。如果请求未包含指定名称的标头,则此方法返回null。如果有多个具有相同名称的头,此方法将返回请求中的第一个头。头名称不区分大小写。
     * 这是一个convienence方法来获取头(而不是使用{@link #getRequestHeaders})。
     * 
     * 
     * @param name the name of the request header
     * @return returns the value of the requested header,
     *         or null if the request does not have a header of that name
     */
     public abstract String getRequestHeader(String name);

    /**
     * Returns a mutable Map into which the HTTP response headers can be stored
     * and which will be transmitted as part of this response. The keys in the
     * Map will be the header names, while the values must be a List of Strings
     * containing each value that should be included multiple times
     * (in the order that they should be included).
     * <p>
     * The keys in Map are case-insensitive.
     *
     * <p>
     *  返回一个可变的Map,其中可以存储HTTP响应头,并且将作为此响应的一部分传输。 Map中的键将是标题名,而值必须是包含应该多次包含的每个值的字符串列表(按照应包括的顺序)。
     * <p>
     *  Map中的键不区分大小写。
     * 
     * 
     * @return a mutable Map which can be used to set response headers.
     */
    public abstract Map<String, List<String>> getResponseHeaders();

    /**
     * Adds a response header with the given name and value. This method
     * allows a response header to have multiple values. This is a
     * convenience method to add a response header(instead of using the
     * {@link #getResponseHeaders()}).
     *
     * <p>
     *  添加具有给定名称和值的响应头。此方法允许响应头有多个值。这是一个方便的方法来添加响应头(而不是使用{@link #getResponseHeaders()})。
     * 
     * 
     * @param name the name of the header
     * @param value the additional header value. If it contains octet string,
     *        it should be encoded according to
     *        RFC 2047 (http://www.ietf.org/rfc/rfc2047.txt)
     *
     * @see #getResponseHeaders
     */
    public abstract void addResponseHeader(String name, String value);

    /**
     * Returns the part of the request's URI from the protocol
     * name up to the query string in the first line of the HTTP request.
     * Container doesn't decode this string.
     *
     * <p>
     *  将请求的URI的一部分从协议名称返回到HTTP请求第一行中的查询字符串。容器不解码此字符串。
     * 
     * 
     * @return the request URI
     */
    public abstract String getRequestURI();

    /**
     * Returns the context path of all the endpoints in an application.
     * This path is the portion of the request URI that indicates the
     * context of the request. The context path always comes first in a
     * request URI. The path starts with a "/" character but does not
     * end with a "/" character. If this method returns "", the request
     * is for default context. The container does not decode this string.
     *
     * <p>
     * Context path is used in computing the endpoint address. See
     * {@link HttpContext#getPath}
     *
     * <p>
     * 返回应用程序中所有端点的上下文路径。此路径是请求URI的指示请求上下文的部分。上下文路径总是在请求URI中排在第一位。路径以"/"字符开头,但不以"/"字符结尾。如果此方法返回"",请求是默认上下文。
     * 容器不解码此字符串。
     * 
     * <p>
     *  上下文路径用于计算端点地址。参见{@link HttpContext#getPath}
     * 
     * 
     * @return context path of all the endpoints in an application
     * @see HttpContext#getPath
     */
    public abstract String getContextPath();

    /**
     * Get the HTTP request method
     *
     * <p>
     *  获取HTTP请求方法
     * 
     * 
     * @return the request method
     */
    public abstract String getRequestMethod();

    /**
     * Returns a {@link HttpContext} for this exchange.
     * Container matches the request with the associated Endpoint's HttpContext
     *
     * <p>
     *  返回此交换的{@link HttpContext}。容器将请求与关联的端点的HttpContext进行匹配
     * 
     * 
     * @return the HttpContext for this exchange
     */
    public abstract HttpContext getHttpContext();

    /**
     * This must be called to end an exchange. Container takes care of
     * closing request and response streams. This must be called so that
     * the container can free or reuse underlying resources.
     *
     * <p>
     *  必须调用此函数才能结束交换。容器负责关闭请求和响应流。这必须被调用,以便容器可以释放或重用底层资源。
     * 
     * 
     * @throws IOException if any i/o error
     */
    public abstract void close() throws IOException;

    /**
     * Returns a stream from which the request body can be read.
     * Multiple calls to this method will return the same stream.
     *
     * <p>
     *  返回可从中读取请求正文的流。对此方法的多次调用将返回相同的流。
     * 
     * 
     * @return the stream from which the request body can be read.
     * @throws IOException if any i/o error during request processing
     */
    public abstract InputStream getRequestBody() throws IOException;

    /**
     * Returns a stream to which the response body must be
     * written. {@link #setStatus}) must be called prior to calling
     * this method. Multiple calls to this method (for the same exchange)
     * will return the same stream.
     *
     * <p>
     *  返回必须写入响应主体的流。 {@link #setStatus})必须在调用此方法之前调用。多次调用此方法(对于同一个交换)将返回相同的流。
     * 
     * 
     * @return the stream to which the response body is written
     * @throws IOException if any i/o error during response processing
     */
    public abstract OutputStream getResponseBody() throws IOException;

    /**
     * Sets the HTTP status code for the response.
     *
     * <p>
     * This method must be called prior to calling {@link #getResponseBody}.
     *
     * <p>
     *  设置响应的HTTP状态代码。
     * 
     * <p>
     *  在调用{@link #getResponseBody}之前,必须调用此方法。
     * 
     * 
     * @param status the response code to send
     * @see #getResponseBody
     */
    public abstract void setStatus(int status);

    /**
     * Returns the unresolved address of the remote entity invoking
     * this request.
     *
     * <p>
     *  返回调用此请求的远程实体的未解析地址。
     * 
     * 
     * @return the InetSocketAddress of the caller
     */
    public abstract InetSocketAddress getRemoteAddress();

    /**
     * Returns the unresolved local address on which the request was received.
     *
     * <p>
     *  返回接收到请求的未解析的本地地址。
     * 
     * 
     * @return the InetSocketAddress of the local interface
     */
    public abstract InetSocketAddress getLocalAddress();

    /**
     * Returns the protocol string from the request in the form
     * <i>protocol/majorVersion.minorVersion</i>. For example,
     * "HTTP/1.1"
     *
     * <p>
     * 以<i> protocol / majorVersion.minorVersion </i>的形式返回请求中的协议字符串。例如,"HTTP / 1.1"
     * 
     * 
     * @return the protocol string from the request
     */
    public abstract String getProtocol();

    /**
     * Returns the name of the scheme used to make this request,
     * for example: http, or https.
     *
     * <p>
     *  返回用于发出此请求的方案的名称,例如：http或https。
     * 
     * 
     * @return name of the scheme used to make this request
     */
    public abstract String getScheme();

    /**
     * Returns the extra path information that follows the web service
     * path but precedes the query string in the request URI and will start
     * with a "/" character.
     *
     * <p>
     * This can be used for {@link MessageContext#PATH_INFO}
     *
     * <p>
     *  返回跟随Web服务路径但在请求URI中的查询字符串之前的额外路径信息,并以"/"字符开头。
     * 
     * <p>
     *  这可以用于{@link MessageContext#PATH_INFO}
     * 
     * 
     * @return decoded extra path information of web service.
     *         It is the path that comes
     *         after the web service path but before the query string in the
     *         request URI
     *         <tt>null</tt> if there is no extra path in the request URI
     */
    public abstract String getPathInfo();

    /**
     * Returns the query string that is contained in the request URI
     * after the path.
     *
     * <p>
     * This can be used for {@link MessageContext#QUERY_STRING}
     *
     * <p>
     *  返回路径后的请求URI中包含的查询字符串。
     * 
     * <p>
     *  这可以用于{@link MessageContext#QUERY_STRING}
     * 
     * 
     * @return undecoded query string of request URI, or
     *         <tt>null</tt> if the request URI doesn't have one
     */
    public abstract String getQueryString();

    /**
     * Returns an attribute that is associated with this
     * <code>HttpExchange</code>. JAX-WS handlers and endpoints may then
     * access the attribute via {@link MessageContext}.
     * <p>
     * Servlet containers must expose {@link MessageContext#SERVLET_CONTEXT},
     * {@link MessageContext#SERVLET_REQUEST}, and
     * {@link MessageContext#SERVLET_RESPONSE}
     * as attributes.
     *
     * <p>If the request has been received by the container using HTTPS, the
     * following information must be exposed as attributes. These attributes
     * are {@link #REQUEST_CIPHER_SUITE}, and {@link #REQUEST_KEY_SIZE}.
     * If there is a SSL certificate associated with the request, it must
     * be exposed using {@link #REQUEST_X509CERTIFICATE}
     *
     * <p>
     *  返回与此<code> HttpExchange </code>关联的属性。然后JAX-WS处理程序和端点可以通过{@link MessageContext}访问该属性。
     * <p>
     *  Servlet容器必须作为属性公开{@link MessageContext#SERVLET_CONTEXT},{@link MessageContext#SERVLET_REQUEST}和{@link MessageContext#SERVLET_RESPONSE}
     * 。
     * 
     *  <p>如果容器使用HTTPS收到请求,则必须将以下信息作为属性公开。这些属性是{@link #REQUEST_CIPHER_SUITE}和{@link #REQUEST_KEY_SIZE}。
     * 如果有与请求相关联的SSL证书,则必须使用{@link#REQUEST_X509CERTIFICATE}。
     * 
     * 
     * @param name attribute name
     * @return the attribute value, or <tt>null</tt> if the attribute doesn't
     *         exist
     */
    public abstract Object getAttribute(String name);

    /**
     * Gives all the attribute names that are associated with
     * this <code>HttpExchange</code>.
     *
     * <p>
     *  提供与此<code> HttpExchange </code>相关联的所有属性名称。
     * 
     * 
     * @return set of all attribute names
     * @see #getAttribute(String)
     */
    public abstract Set<String> getAttributeNames();

    /**
     * Returns the {@link Principal} that represents the authenticated
     * user for this <code>HttpExchange</code>.
     *
     * <p>
     *  返回表示此<code> HttpExchange </code>的经过身份验证的用户的{@link Principal}。
     * 
     * 
     * @return Principal for an authenticated user, or
     *         <tt>null</tt> if not authenticated
     */
    public abstract Principal getUserPrincipal();

    /**
     * Indicates whether an authenticated user is included in the specified
     * logical "role".
     *
     * <p>
     * 指示经过身份验证的用户是否包含在指定的逻辑"角色"中。
     * 
     * @param role specifies the name of the role
     * @return <tt>true</tt> if the user making this request belongs to a
     *         given role
     */
    public abstract boolean isUserInRole(String role);

}
