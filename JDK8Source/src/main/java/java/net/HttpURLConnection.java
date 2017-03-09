/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.net;

import java.io.InputStream;
import java.io.IOException;
import java.security.Permission;
import java.util.Date;

/**
 * A URLConnection with support for HTTP-specific features. See
 * <A HREF="http://www.w3.org/pub/WWW/Protocols/"> the spec </A> for
 * details.
 * <p>
 *
 * Each HttpURLConnection instance is used to make a single request
 * but the underlying network connection to the HTTP server may be
 * transparently shared by other instances. Calling the close() methods
 * on the InputStream or OutputStream of an HttpURLConnection
 * after a request may free network resources associated with this
 * instance but has no effect on any shared persistent connection.
 * Calling the disconnect() method may close the underlying socket
 * if a persistent connection is otherwise idle at that time.
 *
 * <P>The HTTP protocol handler has a few settings that can be accessed through
 * System Properties. This covers
 * <a href="doc-files/net-properties.html#Proxies">Proxy settings</a> as well as
 * <a href="doc-files/net-properties.html#MiscHTTP"> various other settings</a>.
 * </P>
 * <p>
 * <b>Security permissions</b>
 * <p>
 * If a security manager is installed, and if a method is called which results in an
 * attempt to open a connection, the caller must possess either:-
 * <ul><li>a "connect" {@link SocketPermission} to the host/port combination of the
 * destination URL or</li>
 * <li>a {@link URLPermission} that permits this request.</li>
 * </ul><p>
 * If automatic redirection is enabled, and this request is redirected to another
 * destination, then the caller must also have permission to connect to the
 * redirected host/URL.
 *
 * <p>
 *  支持HTTP特定功能的URLConnection。有关详情,请参阅<A HREF="http://www.w3.org/pub/WWW/Protocols/">规范</A>。
 * <p>
 * 
 *  每个HttpURLConnection实例用于进行单个请求,但是与HTTP服务器的底层网络连接可以由其他实例透明地共享。
 * 在请求之后调用HttpURLConnection的InputStream或OutputStream上的close()方法可以释放与此实例相关联的网络资源,但对任何共享持久连接没有影响。
 * 如果持久连接在那时是空闲的,则调用disconnect()方法可以关闭底层套接字。
 * 
 *  <P> HTTP协议处理程序有一些可以通过系统属性访问的设置。
 * 这包括<a href="doc-files/net-properties.html#Proxies">代理设置</a>以及<a href="doc-files/net-properties.html#MiscHTTP">
 * 各种其他设置</a>。
 *  <P> HTTP协议处理程序有一些可以通过系统属性访问的设置。
 * </P>
 * <p>
 *  <b>安全权限</b>
 * <p>
 * 如果安装了安全管理器,并且调用了导致尝试打开连接的方法,则调用程序必须具有： -  <ul> <li>向主机/端口"连接"{@link SocketPermission} </li> </ul> <p>
 * 如果启用了自动重定向,并且此请求被重定向到另一个目的地,则可以使用目标网址或</li> <li> a {@link URLPermission}则呼叫者还必须具有连接到重定向的主机/ URL的权限。
 * 
 * 
 * @see     java.net.HttpURLConnection#disconnect()
 * @since JDK1.1
 */
abstract public class HttpURLConnection extends URLConnection {
    /* instance variables */

    /**
     * The HTTP method (GET,POST,PUT,etc.).
     * <p>
     *  HTTP方法(GET,POST,PUT等)。
     * 
     */
    protected String method = "GET";

    /**
     * The chunk-length when using chunked encoding streaming mode for output.
     * A value of {@code -1} means chunked encoding is disabled for output.
     * <p>
     *  使用分块编码流模式输出时的块长度。值为{@code -1}表示对于输出禁用分块编码。
     * 
     * 
     * @since 1.5
     */
    protected int chunkLength = -1;

    /**
     * The fixed content-length when using fixed-length streaming mode.
     * A value of {@code -1} means fixed-length streaming mode is disabled
     * for output.
     *
     * <P> <B>NOTE:</B> {@link #fixedContentLengthLong} is recommended instead
     * of this field, as it allows larger content lengths to be set.
     *
     * <p>
     *  固定内容长度时使用固定长度流模式。值为{@code -1}表示固定长度流模式禁用输出。
     * 
     *  <P> <B>注意：</B>建议使用{@link #fixedContentLengthLong},而不是此字段,因为它允许设置更大的内容长度。
     * 
     * 
     * @since 1.5
     */
    protected int fixedContentLength = -1;

    /**
     * The fixed content-length when using fixed-length streaming mode.
     * A value of {@code -1} means fixed-length streaming mode is disabled
     * for output.
     *
     * <p>
     *  固定内容长度时使用固定长度流模式。值为{@code -1}表示固定长度流模式禁用输出。
     * 
     * 
     * @since 1.7
     */
    protected long fixedContentLengthLong = -1;

    /**
     * Returns the key for the {@code n}<sup>th</sup> header field.
     * Some implementations may treat the {@code 0}<sup>th</sup>
     * header field as special, i.e. as the status line returned by the HTTP
     * server. In this case, {@link #getHeaderField(int) getHeaderField(0)} returns the status
     * line, but {@code getHeaderFieldKey(0)} returns null.
     *
     * <p>
     *  返回{@code n} <sup> th </sup>头字段的键。一些实现可以将{@code 0} <sup> th </sup>头字段视为特殊的,即作为HTTP服务器返回的状态行。
     * 在这种情况下,{@link #getHeaderField(int)getHeaderField(0)}返回状态行,但{@code getHeaderFieldKey(0)}返回null。
     * 
     * 
     * @param   n   an index, where {@code n >=0}.
     * @return  the key for the {@code n}<sup>th</sup> header field,
     *          or {@code null} if the key does not exist.
     */
    public String getHeaderFieldKey (int n) {
        return null;
    }

    /**
     * This method is used to enable streaming of a HTTP request body
     * without internal buffering, when the content length is known in
     * advance.
     * <p>
     * An exception will be thrown if the application
     * attempts to write more data than the indicated
     * content-length, or if the application closes the OutputStream
     * before writing the indicated amount.
     * <p>
     * When output streaming is enabled, authentication
     * and redirection cannot be handled automatically.
     * A HttpRetryException will be thrown when reading
     * the response if authentication or redirection are required.
     * This exception can be queried for the details of the error.
     * <p>
     * This method must be called before the URLConnection is connected.
     * <p>
     * <B>NOTE:</B> {@link #setFixedLengthStreamingMode(long)} is recommended
     * instead of this method as it allows larger content lengths to be set.
     *
     * <p>
     * 当内容长度预先已知时,此方法用于启用没有内部缓冲的HTTP请求主体的流式传输。
     * <p>
     *  如果应用程序尝试写入比指示的内容长度更多的数据,或者应用程序在写入指示的数量之前关闭OutputStream,则将抛出异常。
     * <p>
     *  启用输出流时,无法自动处理身份验证和重定向。如果需要认证或重定向,则在读取响应时将抛出HttpRetryException。可以查询此异常以获取错误的详细信息。
     * <p>
     *  在连接URLConnection之前,必须调用此方法。
     * <p>
     *  <B>注意：</B>建议使用{@link #setFixedLengthStreamingMode(long)},而不是使用此方法,因为它允许设置更大的内容长度。
     * 
     * 
     * @param   contentLength The number of bytes which will be written
     *          to the OutputStream.
     *
     * @throws  IllegalStateException if URLConnection is already connected
     *          or if a different streaming mode is already enabled.
     *
     * @throws  IllegalArgumentException if a content length less than
     *          zero is specified.
     *
     * @see     #setChunkedStreamingMode(int)
     * @since 1.5
     */
    public void setFixedLengthStreamingMode (int contentLength) {
        if (connected) {
            throw new IllegalStateException ("Already connected");
        }
        if (chunkLength != -1) {
            throw new IllegalStateException ("Chunked encoding streaming mode set");
        }
        if (contentLength < 0) {
            throw new IllegalArgumentException ("invalid content length");
        }
        fixedContentLength = contentLength;
    }

    /**
     * This method is used to enable streaming of a HTTP request body
     * without internal buffering, when the content length is known in
     * advance.
     *
     * <P> An exception will be thrown if the application attempts to write
     * more data than the indicated content-length, or if the application
     * closes the OutputStream before writing the indicated amount.
     *
     * <P> When output streaming is enabled, authentication and redirection
     * cannot be handled automatically. A {@linkplain HttpRetryException} will
     * be thrown when reading the response if authentication or redirection
     * are required. This exception can be queried for the details of the
     * error.
     *
     * <P> This method must be called before the URLConnection is connected.
     *
     * <P> The content length set by invoking this method takes precedence
     * over any value set by {@link #setFixedLengthStreamingMode(int)}.
     *
     * <p>
     *  当内容长度预先已知时,此方法用于启用没有内部缓冲的HTTP请求主体的流式传输。
     * 
     *  <P>如果应用程序尝试写入比指示的内容长度更多的数据,或者应用程序在写入指定的数量之前关闭OutputStream,则将抛出异常。
     * 
     *  <P>当启用输出流时,无法自动处理身份验证和重定向。如果需要验证或重定向,则在读取响应时将抛出{@linkplain HttpRetryException}。可以查询此异常以获取错误的详细信息。
     * 
     *  <P>在连接URLConnection之前必须调用此方法。
     * 
     * <P>通过调用此方法设置的内容长度优先于由{@link #setFixedLengthStreamingMode(int)}设置的任何值。
     * 
     * 
     * @param  contentLength
     *         The number of bytes which will be written to the OutputStream.
     *
     * @throws  IllegalStateException
     *          if URLConnection is already connected or if a different
     *          streaming mode is already enabled.
     *
     * @throws  IllegalArgumentException
     *          if a content length less than zero is specified.
     *
     * @since 1.7
     */
    public void setFixedLengthStreamingMode(long contentLength) {
        if (connected) {
            throw new IllegalStateException("Already connected");
        }
        if (chunkLength != -1) {
            throw new IllegalStateException(
                "Chunked encoding streaming mode set");
        }
        if (contentLength < 0) {
            throw new IllegalArgumentException("invalid content length");
        }
        fixedContentLengthLong = contentLength;
    }

    /* Default chunk size (including chunk header) if not specified;
     * we want to keep this in sync with the one defined in
     * sun.net.www.http.ChunkedOutputStream
     * <p>
     *  我们希望保持与sun.net.www.http.ChunkedOutputStream中定义的同步
     * 
     */
    private static final int DEFAULT_CHUNK_SIZE = 4096;

    /**
     * This method is used to enable streaming of a HTTP request body
     * without internal buffering, when the content length is <b>not</b>
     * known in advance. In this mode, chunked transfer encoding
     * is used to send the request body. Note, not all HTTP servers
     * support this mode.
     * <p>
     * When output streaming is enabled, authentication
     * and redirection cannot be handled automatically.
     * A HttpRetryException will be thrown when reading
     * the response if authentication or redirection are required.
     * This exception can be queried for the details of the error.
     * <p>
     * This method must be called before the URLConnection is connected.
     *
     * <p>
     *  当内容长度<b>不</b>预先知道时,此方法用于启用无内部缓冲的HTTP请求主体的流式传输。在这种模式下,分块传输编码用于发送请求体。注意,并非所有HTTP服务器都支持此模式。
     * <p>
     *  启用输出流时,无法自动处理身份验证和重定向。如果需要认证或重定向,则在读取响应时将抛出HttpRetryException。可以查询此异常以获取错误的详细信息。
     * <p>
     *  在连接URLConnection之前,必须调用此方法。
     * 
     * 
     * @param   chunklen The number of bytes to write in each chunk.
     *          If chunklen is less than or equal to zero, a default
     *          value will be used.
     *
     * @throws  IllegalStateException if URLConnection is already connected
     *          or if a different streaming mode is already enabled.
     *
     * @see     #setFixedLengthStreamingMode(int)
     * @since 1.5
     */
    public void setChunkedStreamingMode (int chunklen) {
        if (connected) {
            throw new IllegalStateException ("Can't set streaming mode: already connected");
        }
        if (fixedContentLength != -1 || fixedContentLengthLong != -1) {
            throw new IllegalStateException ("Fixed length streaming mode set");
        }
        chunkLength = chunklen <=0? DEFAULT_CHUNK_SIZE : chunklen;
    }

    /**
     * Returns the value for the {@code n}<sup>th</sup> header field.
     * Some implementations may treat the {@code 0}<sup>th</sup>
     * header field as special, i.e. as the status line returned by the HTTP
     * server.
     * <p>
     * This method can be used in conjunction with the
     * {@link #getHeaderFieldKey getHeaderFieldKey} method to iterate through all
     * the headers in the message.
     *
     * <p>
     *  返回{@code n} <sup> th </sup>头字段的值。一些实现可以将{@code 0} <sup> th </sup>头字段视为特殊的,即作为HTTP服务器返回的状态行。
     * <p>
     *  此方法可以与{@link #getHeaderFieldKey getHeaderFieldKey}方法结合使用,以遍历消息中的所有标头。
     * 
     * 
     * @param   n   an index, where {@code n>=0}.
     * @return  the value of the {@code n}<sup>th</sup> header field,
     *          or {@code null} if the value does not exist.
     * @see     java.net.HttpURLConnection#getHeaderFieldKey(int)
     */
    public String getHeaderField(int n) {
        return null;
    }

    /**
     * An {@code int} representing the three digit HTTP Status-Code.
     * <ul>
     * <li> 1xx: Informational
     * <li> 2xx: Success
     * <li> 3xx: Redirection
     * <li> 4xx: Client Error
     * <li> 5xx: Server Error
     * </ul>
     * <p>
     *  表示三位数HTTP状态代码的{@code int}。
     * <ul>
     *  <li> 1xx：信息<li> 2xx：成功<li> 3xx：重定向<li> 4xx：客户端错误<li> 5xx：服务器错误
     * </ul>
     */
    protected int responseCode = -1;

    /**
     * The HTTP response message.
     * <p>
     *  HTTP响应消息。
     * 
     */
    protected String responseMessage = null;

    /* static variables */

    /* do we automatically follow redirects? The default is true. */
    private static boolean followRedirects = true;

    /**
     * If {@code true}, the protocol will automatically follow redirects.
     * If {@code false}, the protocol will not automatically follow
     * redirects.
     * <p>
     * This field is set by the {@code setInstanceFollowRedirects}
     * method. Its value is returned by the {@code getInstanceFollowRedirects}
     * method.
     * <p>
     * Its default value is based on the value of the static followRedirects
     * at HttpURLConnection construction time.
     *
     * <p>
     * 如果{@code true},协议将自动跟随重定向。如果{@code false},协议不会自动跟随重定向。
     * <p>
     *  此字段由{@code setInstanceFollowRedirects}方法设置。它的值由{@code getInstanceFollowRedirects}方法返回。
     * <p>
     *  其默认值基于HttpURLConnection构建时的静态followRedirects的值。
     * 
     * 
     * @see     java.net.HttpURLConnection#setInstanceFollowRedirects(boolean)
     * @see     java.net.HttpURLConnection#getInstanceFollowRedirects()
     * @see     java.net.HttpURLConnection#setFollowRedirects(boolean)
     */
    protected boolean instanceFollowRedirects = followRedirects;

    /* valid HTTP methods */
    private static final String[] methods = {
        "GET", "POST", "HEAD", "OPTIONS", "PUT", "DELETE", "TRACE"
    };

    /**
     * Constructor for the HttpURLConnection.
     * <p>
     *  HttpURLConnection的构造函数。
     * 
     * 
     * @param u the URL
     */
    protected HttpURLConnection (URL u) {
        super(u);
    }

    /**
     * Sets whether HTTP redirects  (requests with response code 3xx) should
     * be automatically followed by this class.  True by default.  Applets
     * cannot change this variable.
     * <p>
     * If there is a security manager, this method first calls
     * the security manager's {@code checkSetFactory} method
     * to ensure the operation is allowed.
     * This could result in a SecurityException.
     *
     * <p>
     *  设置HTTP重定向(响应代码为3xx的请求)是否应该由此类自动跟随。默认为True。小程序不能更改此变量。
     * <p>
     *  如果有安全管理器,则此方法首先调用安全管理器的{@code checkSetFactory}方法,以确保允许操作。这可能导致SecurityException。
     * 
     * 
     * @param set a {@code boolean} indicating whether or not
     * to follow HTTP redirects.
     * @exception  SecurityException  if a security manager exists and its
     *             {@code checkSetFactory} method doesn't
     *             allow the operation.
     * @see        SecurityManager#checkSetFactory
     * @see #getFollowRedirects()
     */
    public static void setFollowRedirects(boolean set) {
        SecurityManager sec = System.getSecurityManager();
        if (sec != null) {
            // seems to be the best check here...
            sec.checkSetFactory();
        }
        followRedirects = set;
    }

    /**
     * Returns a {@code boolean} indicating
     * whether or not HTTP redirects (3xx) should
     * be automatically followed.
     *
     * <p>
     *  返回{@code boolean},指示是否应自动遵循HTTP重定向(3xx)。
     * 
     * 
     * @return {@code true} if HTTP redirects should
     * be automatically followed, {@code false} if not.
     * @see #setFollowRedirects(boolean)
     */
    public static boolean getFollowRedirects() {
        return followRedirects;
    }

    /**
     * Sets whether HTTP redirects (requests with response code 3xx) should
     * be automatically followed by this {@code HttpURLConnection}
     * instance.
     * <p>
     * The default value comes from followRedirects, which defaults to
     * true.
     *
     * <p>
     *  设置是否应该由此{@code HttpURLConnection}实例自动跟随HTTP重定向(具有响应代码3xx的请求)。
     * <p>
     *  默认值来自followRedirects,默认值为true。
     * 
     * 
     * @param followRedirects a {@code boolean} indicating
     * whether or not to follow HTTP redirects.
     *
     * @see    java.net.HttpURLConnection#instanceFollowRedirects
     * @see #getInstanceFollowRedirects
     * @since 1.3
     */
     public void setInstanceFollowRedirects(boolean followRedirects) {
        instanceFollowRedirects = followRedirects;
     }

     /**
     * Returns the value of this {@code HttpURLConnection}'s
     * {@code instanceFollowRedirects} field.
     *
     * <p>
     *  返回此{@code HttpURLConnection}的{@code instanceFollowRedirects}字段的值。
     * 
     * 
     * @return  the value of this {@code HttpURLConnection}'s
     *          {@code instanceFollowRedirects} field.
     * @see     java.net.HttpURLConnection#instanceFollowRedirects
     * @see #setInstanceFollowRedirects(boolean)
     * @since 1.3
     */
     public boolean getInstanceFollowRedirects() {
         return instanceFollowRedirects;
     }

    /**
     * Set the method for the URL request, one of:
     * <UL>
     *  <LI>GET
     *  <LI>POST
     *  <LI>HEAD
     *  <LI>OPTIONS
     *  <LI>PUT
     *  <LI>DELETE
     *  <LI>TRACE
     * </UL> are legal, subject to protocol restrictions.  The default
     * method is GET.
     *
     * <p>
     *  设置URL请求的方法,其中之一：
     * <UL>
     *  <LI> GET <LI> POST <LI> HEAD <LI> OPTIONS <LI> PUT <LI> DELETE <LI> TRACE </UL>默认方法是GET。
     * 
     * 
     * @param method the HTTP method
     * @exception ProtocolException if the method cannot be reset or if
     *              the requested method isn't valid for HTTP.
     * @exception SecurityException if a security manager is set and the
     *              method is "TRACE", but the "allowHttpTrace"
     *              NetPermission is not granted.
     * @see #getRequestMethod()
     */
    public void setRequestMethod(String method) throws ProtocolException {
        if (connected) {
            throw new ProtocolException("Can't reset method: already connected");
        }
        // This restriction will prevent people from using this class to
        // experiment w/ new HTTP methods using java.  But it should
        // be placed for security - the request String could be
        // arbitrarily long.

        for (int i = 0; i < methods.length; i++) {
            if (methods[i].equals(method)) {
                if (method.equals("TRACE")) {
                    SecurityManager s = System.getSecurityManager();
                    if (s != null) {
                        s.checkPermission(new NetPermission("allowHttpTrace"));
                    }
                }
                this.method = method;
                return;
            }
        }
        throw new ProtocolException("Invalid HTTP method: " + method);
    }

    /**
     * Get the request method.
     * <p>
     *  获取请求方法。
     * 
     * 
     * @return the HTTP request method
     * @see #setRequestMethod(java.lang.String)
     */
    public String getRequestMethod() {
        return method;
    }

    /**
     * Gets the status code from an HTTP response message.
     * For example, in the case of the following status lines:
     * <PRE>
     * HTTP/1.0 200 OK
     * HTTP/1.0 401 Unauthorized
     * </PRE>
     * It will return 200 and 401 respectively.
     * Returns -1 if no code can be discerned
     * from the response (i.e., the response is not valid HTTP).
     * <p>
     * 从HTTP响应消息获取状态代码。例如,在以下状态行的情况下：
     * <PRE>
     *  HTTP / 1.0 200 OK HTTP / 1.0 401未授权
     * </PRE>
     *  它将分别返回200和401。如果没有可以从响应中辨别出代码(即,响应无效的HTTP),则返回-1。
     * 
     * 
     * @throws IOException if an error occurred connecting to the server.
     * @return the HTTP Status-Code, or -1
     */
    public int getResponseCode() throws IOException {
        /*
         * We're got the response code already
         * <p>
         *  我们已经得到了响应代码
         * 
         */
        if (responseCode != -1) {
            return responseCode;
        }

        /*
         * Ensure that we have connected to the server. Record
         * exception as we need to re-throw it if there isn't
         * a status line.
         * <p>
         *  确保我们已连接到服务器。记录异常,因为我们需要重新抛出它,如果没有状态行。
         * 
         */
        Exception exc = null;
        try {
            getInputStream();
        } catch (Exception e) {
            exc = e;
        }

        /*
         * If we can't a status-line then re-throw any exception
         * that getInputStream threw.
         * <p>
         *  如果我们不能一个状态行然后重新抛出任何异常getInputStream抛出。
         * 
         */
        String statusLine = getHeaderField(0);
        if (statusLine == null) {
            if (exc != null) {
                if (exc instanceof RuntimeException)
                    throw (RuntimeException)exc;
                else
                    throw (IOException)exc;
            }
            return -1;
        }

        /*
         * Examine the status-line - should be formatted as per
         * section 6.1 of RFC 2616 :-
         *
         * Status-Line = HTTP-Version SP Status-Code SP Reason-Phrase
         *
         * If status line can't be parsed return -1.
         * <p>
         *  检查状态行 - 应按照RFC 2616第6.1节的格式： - 
         * 
         *  状态线= HTTP-Version SP状态码SP原因短语
         * 
         *  如果状态行不能被解析返回-1。
         * 
         */
        if (statusLine.startsWith("HTTP/1.")) {
            int codePos = statusLine.indexOf(' ');
            if (codePos > 0) {

                int phrasePos = statusLine.indexOf(' ', codePos+1);
                if (phrasePos > 0 && phrasePos < statusLine.length()) {
                    responseMessage = statusLine.substring(phrasePos+1);
                }

                // deviation from RFC 2616 - don't reject status line
                // if SP Reason-Phrase is not included.
                if (phrasePos < 0)
                    phrasePos = statusLine.length();

                try {
                    responseCode = Integer.parseInt
                            (statusLine.substring(codePos+1, phrasePos));
                    return responseCode;
                } catch (NumberFormatException e) { }
            }
        }
        return -1;
    }

    /**
     * Gets the HTTP response message, if any, returned along with the
     * response code from a server.  From responses like:
     * <PRE>
     * HTTP/1.0 200 OK
     * HTTP/1.0 404 Not Found
     * </PRE>
     * Extracts the Strings "OK" and "Not Found" respectively.
     * Returns null if none could be discerned from the responses
     * (the result was not valid HTTP).
     * <p>
     *  获取与服务器的响应代码一起返回的HTTP响应消息(如果有)。从以下响应：
     * <PRE>
     *  HTTP / 1.0 200 OK HTTP / 1.0 404未找到
     * </PRE>
     *  分别提取字符串"OK"和"Not Found"。如果没有可以从响应中辨别出来,则返回null(结果不是有效的HTTP)。
     * 
     * 
     * @throws IOException if an error occurred connecting to the server.
     * @return the HTTP response message, or {@code null}
     */
    public String getResponseMessage() throws IOException {
        getResponseCode();
        return responseMessage;
    }

    @SuppressWarnings("deprecation")
    public long getHeaderFieldDate(String name, long Default) {
        String dateString = getHeaderField(name);
        try {
            if (dateString.indexOf("GMT") == -1) {
                dateString = dateString+" GMT";
            }
            return Date.parse(dateString);
        } catch (Exception e) {
        }
        return Default;
    }


    /**
     * Indicates that other requests to the server
     * are unlikely in the near future. Calling disconnect()
     * should not imply that this HttpURLConnection
     * instance can be reused for other requests.
     * <p>
     *  表示在不久的将来对服务器的其他请求不太可能。调用disconnect()不应该暗示这个HttpURLConnection实例可以重用于其他请求。
     * 
     */
    public abstract void disconnect();

    /**
     * Indicates if the connection is going through a proxy.
     * <p>
     *  指示连接是否正在通过代理。
     * 
     * 
     * @return a boolean indicating if the connection is
     * using a proxy.
     */
    public abstract boolean usingProxy();

    /**
     * Returns a {@link SocketPermission} object representing the
     * permission necessary to connect to the destination host and port.
     *
     * <p>
     *  返回{@link SocketPermission}对象,表示连接到目标主机和端口所需的权限。
     * 
     * 
     * @exception IOException if an error occurs while computing
     *            the permission.
     *
     * @return a {@code SocketPermission} object representing the
     *         permission necessary to connect to the destination
     *         host and port.
     */
    public Permission getPermission() throws IOException {
        int port = url.getPort();
        port = port < 0 ? 80 : port;
        String host = url.getHost() + ":" + port;
        Permission permission = new SocketPermission(host, "connect");
        return permission;
    }

   /**
    * Returns the error stream if the connection failed
    * but the server sent useful data nonetheless. The
    * typical example is when an HTTP server responds
    * with a 404, which will cause a FileNotFoundException
    * to be thrown in connect, but the server sent an HTML
    * help page with suggestions as to what to do.
    *
    * <p>This method will not cause a connection to be initiated.  If
    * the connection was not connected, or if the server did not have
    * an error while connecting or if the server had an error but
    * no error data was sent, this method will return null. This is
    * the default.
    *
    * <p>
    * 如果连接失败但服务器发送有用数据,则返回错误流。
    * 典型的例子是HTTP服务器以404响应,这将导致在connect中抛出FileNotFoundException,但是服务器发送了一个HTML帮助页面,其中包含了做什么的建议。
    * 
    *  <p>此方法不会导致启动连接。如果连接未连接,或者服务器在连接时没有错误,或者如果服务器有错误但没有发送错误数据,则此方法将返回null。这是默认值。
    * 
    * 
    * @return an error stream if any, null if there have been no
    * errors, the connection is not connected or the server sent no
    * useful data.
    */
    public InputStream getErrorStream() {
        return null;
    }

    /**
     * The response codes for HTTP, as of version 1.1.
     * <p>
     *  HTTP版本为1.1的响应代码。
     * 
     */

    // REMIND: do we want all these??
    // Others not here that we do want??

    /* 2XX: generally "OK" */

    /**
     * HTTP Status-Code 200: OK.
     * <p>
     *  HTTP状态代码200：OK。
     * 
     */
    public static final int HTTP_OK = 200;

    /**
     * HTTP Status-Code 201: Created.
     * <p>
     *  HTTP状态代码201：已创建。
     * 
     */
    public static final int HTTP_CREATED = 201;

    /**
     * HTTP Status-Code 202: Accepted.
     * <p>
     *  HTTP状态代码202：接受。
     * 
     */
    public static final int HTTP_ACCEPTED = 202;

    /**
     * HTTP Status-Code 203: Non-Authoritative Information.
     * <p>
     *  HTTP状态代码203：非授权信息。
     * 
     */
    public static final int HTTP_NOT_AUTHORITATIVE = 203;

    /**
     * HTTP Status-Code 204: No Content.
     * <p>
     *  HTTP状态代码204：无内容。
     * 
     */
    public static final int HTTP_NO_CONTENT = 204;

    /**
     * HTTP Status-Code 205: Reset Content.
     * <p>
     *  HTTP状态代码205：重置内容。
     * 
     */
    public static final int HTTP_RESET = 205;

    /**
     * HTTP Status-Code 206: Partial Content.
     * <p>
     *  HTTP状态码206：部分内容。
     * 
     */
    public static final int HTTP_PARTIAL = 206;

    /* 3XX: relocation/redirect */

    /**
     * HTTP Status-Code 300: Multiple Choices.
     * <p>
     *  HTTP状态代码300：多个选择。
     * 
     */
    public static final int HTTP_MULT_CHOICE = 300;

    /**
     * HTTP Status-Code 301: Moved Permanently.
     * <p>
     *  HTTP状态代码301：永久移动。
     * 
     */
    public static final int HTTP_MOVED_PERM = 301;

    /**
     * HTTP Status-Code 302: Temporary Redirect.
     * <p>
     *  HTTP状态代码302：临时重定向。
     * 
     */
    public static final int HTTP_MOVED_TEMP = 302;

    /**
     * HTTP Status-Code 303: See Other.
     * <p>
     *  HTTP状态代码303：请参阅其他。
     * 
     */
    public static final int HTTP_SEE_OTHER = 303;

    /**
     * HTTP Status-Code 304: Not Modified.
     * <p>
     *  HTTP状态代码304：未修改。
     * 
     */
    public static final int HTTP_NOT_MODIFIED = 304;

    /**
     * HTTP Status-Code 305: Use Proxy.
     * <p>
     *  HTTP状态代码305：使用代理。
     * 
     */
    public static final int HTTP_USE_PROXY = 305;

    /* 4XX: client error */

    /**
     * HTTP Status-Code 400: Bad Request.
     * <p>
     *  HTTP状态代码400：错误请求。
     * 
     */
    public static final int HTTP_BAD_REQUEST = 400;

    /**
     * HTTP Status-Code 401: Unauthorized.
     * <p>
     *  HTTP状态代码401：未经授权。
     * 
     */
    public static final int HTTP_UNAUTHORIZED = 401;

    /**
     * HTTP Status-Code 402: Payment Required.
     * <p>
     *  HTTP状态 - 代码402：需要付款。
     * 
     */
    public static final int HTTP_PAYMENT_REQUIRED = 402;

    /**
     * HTTP Status-Code 403: Forbidden.
     * <p>
     *  HTTP状态 - 代码403：禁止。
     * 
     */
    public static final int HTTP_FORBIDDEN = 403;

    /**
     * HTTP Status-Code 404: Not Found.
     * <p>
     *  HTTP状态代码404：找不到。
     * 
     */
    public static final int HTTP_NOT_FOUND = 404;

    /**
     * HTTP Status-Code 405: Method Not Allowed.
     * <p>
     *  HTTP状态代码405：方法不允许。
     * 
     */
    public static final int HTTP_BAD_METHOD = 405;

    /**
     * HTTP Status-Code 406: Not Acceptable.
     * <p>
     *  HTTP状态代码406：不可接受。
     * 
     */
    public static final int HTTP_NOT_ACCEPTABLE = 406;

    /**
     * HTTP Status-Code 407: Proxy Authentication Required.
     * <p>
     *  HTTP状态 - 代码407：需要代理验证。
     * 
     */
    public static final int HTTP_PROXY_AUTH = 407;

    /**
     * HTTP Status-Code 408: Request Time-Out.
     * <p>
     *  HTTP状态代码408：请求超时。
     * 
     */
    public static final int HTTP_CLIENT_TIMEOUT = 408;

    /**
     * HTTP Status-Code 409: Conflict.
     * <p>
     *  HTTP状态 - 代码409：冲突。
     * 
     */
    public static final int HTTP_CONFLICT = 409;

    /**
     * HTTP Status-Code 410: Gone.
     * <p>
     * HTTP状态代码410：去。
     * 
     */
    public static final int HTTP_GONE = 410;

    /**
     * HTTP Status-Code 411: Length Required.
     * <p>
     *  HTTP状态码411：长度必需。
     * 
     */
    public static final int HTTP_LENGTH_REQUIRED = 411;

    /**
     * HTTP Status-Code 412: Precondition Failed.
     * <p>
     *  HTTP状态 - 代码412：前提条件失败。
     * 
     */
    public static final int HTTP_PRECON_FAILED = 412;

    /**
     * HTTP Status-Code 413: Request Entity Too Large.
     * <p>
     *  HTTP状态代码413：请求实体太大。
     * 
     */
    public static final int HTTP_ENTITY_TOO_LARGE = 413;

    /**
     * HTTP Status-Code 414: Request-URI Too Large.
     * <p>
     *  HTTP状态代码414：请求URI太大。
     * 
     */
    public static final int HTTP_REQ_TOO_LONG = 414;

    /**
     * HTTP Status-Code 415: Unsupported Media Type.
     * <p>
     *  HTTP状态代码415：不支持的介质类型。
     * 
     */
    public static final int HTTP_UNSUPPORTED_TYPE = 415;

    /* 5XX: server error */

    /**
     * HTTP Status-Code 500: Internal Server Error.
     * <p>
     *  HTTP状态代码500：内部服务器错误。
     * 
     * 
     * @deprecated   it is misplaced and shouldn't have existed.
     */
    @Deprecated
    public static final int HTTP_SERVER_ERROR = 500;

    /**
     * HTTP Status-Code 500: Internal Server Error.
     * <p>
     *  HTTP状态代码500：内部服务器错误。
     * 
     */
    public static final int HTTP_INTERNAL_ERROR = 500;

    /**
     * HTTP Status-Code 501: Not Implemented.
     * <p>
     *  HTTP状态代码501：未实现。
     * 
     */
    public static final int HTTP_NOT_IMPLEMENTED = 501;

    /**
     * HTTP Status-Code 502: Bad Gateway.
     * <p>
     *  HTTP状态 - 代码502：错误网关。
     * 
     */
    public static final int HTTP_BAD_GATEWAY = 502;

    /**
     * HTTP Status-Code 503: Service Unavailable.
     * <p>
     *  HTTP状态代码503：服务不可用。
     * 
     */
    public static final int HTTP_UNAVAILABLE = 503;

    /**
     * HTTP Status-Code 504: Gateway Timeout.
     * <p>
     *  HTTP状态 - 代码504：网关超时。
     * 
     */
    public static final int HTTP_GATEWAY_TIMEOUT = 504;

    /**
     * HTTP Status-Code 505: HTTP Version Not Supported.
     * <p>
     *  HTTP状态 - 代码505：不支持HTTP版本。
     */
    public static final int HTTP_VERSION = 505;

}
