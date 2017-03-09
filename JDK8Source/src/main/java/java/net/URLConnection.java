/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.Collections;
import java.util.Map;
import java.util.List;
import java.security.Permission;
import java.security.AccessController;
import sun.security.util.SecurityConstants;
import sun.net.www.MessageHeader;

/**
 * The abstract class {@code URLConnection} is the superclass
 * of all classes that represent a communications link between the
 * application and a URL. Instances of this class can be used both to
 * read from and to write to the resource referenced by the URL. In
 * general, creating a connection to a URL is a multistep process:
 *
 * <center><table border=2 summary="Describes the process of creating a connection to a URL: openConnection() and connect() over time.">
 * <tr><th>{@code openConnection()}</th>
 *     <th>{@code connect()}</th></tr>
 * <tr><td>Manipulate parameters that affect the connection to the remote
 *         resource.</td>
 *     <td>Interact with the resource; query header fields and
 *         contents.</td></tr>
 * </table>
 * ----------------------------&gt;
 * <br>time</center>
 *
 * <ol>
 * <li>The connection object is created by invoking the
 *     {@code openConnection} method on a URL.
 * <li>The setup parameters and general request properties are manipulated.
 * <li>The actual connection to the remote object is made, using the
 *    {@code connect} method.
 * <li>The remote object becomes available. The header fields and the contents
 *     of the remote object can be accessed.
 * </ol>
 * <p>
 * The setup parameters are modified using the following methods:
 * <ul>
 *   <li>{@code setAllowUserInteraction}
 *   <li>{@code setDoInput}
 *   <li>{@code setDoOutput}
 *   <li>{@code setIfModifiedSince}
 *   <li>{@code setUseCaches}
 * </ul>
 * <p>
 * and the general request properties are modified using the method:
 * <ul>
 *   <li>{@code setRequestProperty}
 * </ul>
 * <p>
 * Default values for the {@code AllowUserInteraction} and
 * {@code UseCaches} parameters can be set using the methods
 * {@code setDefaultAllowUserInteraction} and
 * {@code setDefaultUseCaches}.
 * <p>
 * Each of the above {@code set} methods has a corresponding
 * {@code get} method to retrieve the value of the parameter or
 * general request property. The specific parameters and general
 * request properties that are applicable are protocol specific.
 * <p>
 * The following methods are used to access the header fields and
 * the contents after the connection is made to the remote object:
 * <ul>
 *   <li>{@code getContent}
 *   <li>{@code getHeaderField}
 *   <li>{@code getInputStream}
 *   <li>{@code getOutputStream}
 * </ul>
 * <p>
 * Certain header fields are accessed frequently. The methods:
 * <ul>
 *   <li>{@code getContentEncoding}
 *   <li>{@code getContentLength}
 *   <li>{@code getContentType}
 *   <li>{@code getDate}
 *   <li>{@code getExpiration}
 *   <li>{@code getLastModifed}
 * </ul>
 * <p>
 * provide convenient access to these fields. The
 * {@code getContentType} method is used by the
 * {@code getContent} method to determine the type of the remote
 * object; subclasses may find it convenient to override the
 * {@code getContentType} method.
 * <p>
 * In the common case, all of the pre-connection parameters and
 * general request properties can be ignored: the pre-connection
 * parameters and request properties default to sensible values. For
 * most clients of this interface, there are only two interesting
 * methods: {@code getInputStream} and {@code getContent},
 * which are mirrored in the {@code URL} class by convenience methods.
 * <p>
 * More information on the request properties and header fields of
 * an {@code http} connection can be found at:
 * <blockquote><pre>
 * <a href="http://www.ietf.org/rfc/rfc2616.txt">http://www.ietf.org/rfc/rfc2616.txt</a>
 * </pre></blockquote>
 *
 * Invoking the {@code close()} methods on the {@code InputStream} or {@code OutputStream} of an
 * {@code URLConnection} after a request may free network resources associated with this
 * instance, unless particular protocol specifications specify different behaviours
 * for it.
 *
 * <p>
 *  抽象类{@code URLConnection}是表示应用程序和URL之间的通信链接的所有类的超类。此类的实例可用于从URL引用的资源读取和写入。一般来说,创建到URL的连接是一个多步过程：
 * 
 *  <center> <table border = 2 summary ="描述创建到URL的连接的过程：openConnection()和connect()。
 * "> <tr> <th> {@ code openConnection()} </th > <th> {@ code connect()} </th> </tr> <tr> <td>操作影响到远程资源连
 * 接的参数</td> <td>查询头字段和内容。
 *  <center> <table border = 2 summary ="描述创建到URL的连接的过程：openConnection()和connect()。</td> </tr>。
 * </table>
 *  ----------------------------&gt; <br>时间</center>
 * 
 * <ol>
 *  <li>连接对象是通过在URL上调用{@code openConnection}方法创建的。 <li>操作设置参数和常规请求属性。
 *  <li>使用{@code connect}方法建立与远程对象的实际连接。 <li>远程对象变为可用。可以访问头域和远程对象的内容。
 * </ol>
 * <p>
 *  使用以下方法修改设置参数：
 * <ul>
 *  <li> {@ code setAllowUserInteraction} <li> {@ code setDoInput} <li> {@ code setDoOutput} <li> {@ code setIfModifiedSince}
 *  <li> {@ code setUseCaches}。
 * </ul>
 * <p>
 * 并使用以下方法修改常规请求属性：
 * <ul>
 *  <li> {@ code setRequestProperty}
 * </ul>
 * <p>
 *  {@code AllowUserInteraction}和{@code UseCaches}参数的默认值可以使用方法{@code setDefaultAllowUserInteraction}和{@code setDefaultUseCaches}
 * 设置。
 * <p>
 *  上述每个{@code set}方法都有一个相应的{@code get}方法来检索参数或一般请求属性的值。可应用的特定参数和一般请求属性是协议特定的。
 * <p>
 *  在连接到远程对象后,使用以下方法访问头字段和内容：
 * <ul>
 *  <li> {@ code getContent} <li> {@ code getHeaderField} <li> {@ code getInputStream} <li> {@ code getOutputStream}
 * 。
 * </ul>
 * <p>
 *  某些头字段被频繁访问。方法：
 * <ul>
 *  <li> {@ code getContentLength} <li> {@ code getContentLength} <li> {@ code getContentType} <li> {@ code getDate}
 *  <li> {@ code getExpiration} <li> {@ code getLastModifed}。
 * </ul>
 * <p>
 *  提供方便地访问这些字段。
 *  {@code getContentType}方法由{@code getContent}方法用于确定远程对象的类型;子类可能会发现覆盖{@code getContentType}方法很方便。
 * <p>
 * 在通常情况下,所有的预连接参数和一般请求属性可以忽略：预连接参数和请求属性默认为合理值。
 * 对于这个接口的大多数客户端,只有两个有趣的方法：{@code getInputStream}和{@code getContent},它们通过方便的方法在{@code URL}类中镜像。
 * <p>
 *  有关{@code http}连接的请求属性和标头字段的详情,请访问：<blockquote> <pre> <a href="http://www.ietf.org/rfc/rfc2616.txt"> 
 * http://www.ietf.org/rfc/rfc2616.txt </a> </pre> </blockquote>。
 * 
 *  在请求之后在{@code InputStream}或{@code OutputStream}上调用{@code close()}方法可能会释放与此实例关联的网络资源,除非特定协议规范指定了不同的行为它
 * 。
 * 
 * 
 * @author  James Gosling
 * @see     java.net.URL#openConnection()
 * @see     java.net.URLConnection#connect()
 * @see     java.net.URLConnection#getContent()
 * @see     java.net.URLConnection#getContentEncoding()
 * @see     java.net.URLConnection#getContentLength()
 * @see     java.net.URLConnection#getContentType()
 * @see     java.net.URLConnection#getDate()
 * @see     java.net.URLConnection#getExpiration()
 * @see     java.net.URLConnection#getHeaderField(int)
 * @see     java.net.URLConnection#getHeaderField(java.lang.String)
 * @see     java.net.URLConnection#getInputStream()
 * @see     java.net.URLConnection#getLastModified()
 * @see     java.net.URLConnection#getOutputStream()
 * @see     java.net.URLConnection#setAllowUserInteraction(boolean)
 * @see     java.net.URLConnection#setDefaultUseCaches(boolean)
 * @see     java.net.URLConnection#setDoInput(boolean)
 * @see     java.net.URLConnection#setDoOutput(boolean)
 * @see     java.net.URLConnection#setIfModifiedSince(long)
 * @see     java.net.URLConnection#setRequestProperty(java.lang.String, java.lang.String)
 * @see     java.net.URLConnection#setUseCaches(boolean)
 * @since   JDK1.0
 */
public abstract class URLConnection {

   /**
     * The URL represents the remote object on the World Wide Web to
     * which this connection is opened.
     * <p>
     * The value of this field can be accessed by the
     * {@code getURL} method.
     * <p>
     * The default value of this variable is the value of the URL
     * argument in the {@code URLConnection} constructor.
     *
     * <p>
     *  URL表示打开此连接的万维网上的远程对象。
     * <p>
     *  此字段的值可以通过{@code getURL}方法访问。
     * <p>
     *  此变量的默认值是{@code URLConnection}构造函数中的URL参数的值。
     * 
     * 
     * @see     java.net.URLConnection#getURL()
     * @see     java.net.URLConnection#url
     */
    protected URL url;

   /**
     * This variable is set by the {@code setDoInput} method. Its
     * value is returned by the {@code getDoInput} method.
     * <p>
     * A URL connection can be used for input and/or output. Setting the
     * {@code doInput} flag to {@code true} indicates that
     * the application intends to read data from the URL connection.
     * <p>
     * The default value of this field is {@code true}.
     *
     * <p>
     *  此变量由{@code setDoInput}方法设置。它的值由{@code getDoInput}方法返回。
     * <p>
     *  URL连接可用于输入和/或输出。将{@code doInput}标志设置为{@code true}表示应用程序打算从URL连接读取数据。
     * <p>
     *  此字段的默认值为{@code true}。
     * 
     * 
     * @see     java.net.URLConnection#getDoInput()
     * @see     java.net.URLConnection#setDoInput(boolean)
     */
    protected boolean doInput = true;

   /**
     * This variable is set by the {@code setDoOutput} method. Its
     * value is returned by the {@code getDoOutput} method.
     * <p>
     * A URL connection can be used for input and/or output. Setting the
     * {@code doOutput} flag to {@code true} indicates
     * that the application intends to write data to the URL connection.
     * <p>
     * The default value of this field is {@code false}.
     *
     * <p>
     * 此变量由{@code setDoOutput}方法设置。其值由{@code getDoOutput}方法返回。
     * <p>
     *  URL连接可用于输入和/或输出。将{@code doOutput}标志设置为{@code true}表示应用程序打算将数据写入URL连接。
     * <p>
     *  此字段的默认值为{@code false}。
     * 
     * 
     * @see     java.net.URLConnection#getDoOutput()
     * @see     java.net.URLConnection#setDoOutput(boolean)
     */
    protected boolean doOutput = false;

    private static boolean defaultAllowUserInteraction = false;

   /**
     * If {@code true}, this {@code URL} is being examined in
     * a context in which it makes sense to allow user interactions such
     * as popping up an authentication dialog. If {@code false},
     * then no user interaction is allowed.
     * <p>
     * The value of this field can be set by the
     * {@code setAllowUserInteraction} method.
     * Its value is returned by the
     * {@code getAllowUserInteraction} method.
     * Its default value is the value of the argument in the last invocation
     * of the {@code setDefaultAllowUserInteraction} method.
     *
     * <p>
     *  如果{@code true}正在检查此{@code URL},则允许用户进行交互(例如弹出身份验证对话框)是有意义的。如果{@code false},则不允许用户互动。
     * <p>
     *  此字段的值可以通过{@code setAllowUserInteraction}方法设置。它的值由{@code getAllowUserInteraction}方法返回。
     * 其默认值是{@code setDefaultAllowUserInteraction}方法的最后一次调用中的参数的值。
     * 
     * 
     * @see     java.net.URLConnection#getAllowUserInteraction()
     * @see     java.net.URLConnection#setAllowUserInteraction(boolean)
     * @see     java.net.URLConnection#setDefaultAllowUserInteraction(boolean)
     */
    protected boolean allowUserInteraction = defaultAllowUserInteraction;

    private static boolean defaultUseCaches = true;

   /**
     * If {@code true}, the protocol is allowed to use caching
     * whenever it can. If {@code false}, the protocol must always
     * try to get a fresh copy of the object.
     * <p>
     * This field is set by the {@code setUseCaches} method. Its
     * value is returned by the {@code getUseCaches} method.
     * <p>
     * Its default value is the value given in the last invocation of the
     * {@code setDefaultUseCaches} method.
     *
     * <p>
     *  如果{@code true},协议允许使用缓存,只要它可以。如果{@code false},协议必须总是试图获得对象的新副本。
     * <p>
     *  此字段由{@code setUseCaches}方法设置。它的值由{@code getUseCaches}方法返回。
     * <p>
     *  它的默认值是在最后调用{@code setDefaultUseCaches}方法中给出的值。
     * 
     * 
     * @see     java.net.URLConnection#setUseCaches(boolean)
     * @see     java.net.URLConnection#getUseCaches()
     * @see     java.net.URLConnection#setDefaultUseCaches(boolean)
     */
    protected boolean useCaches = defaultUseCaches;

   /**
     * Some protocols support skipping the fetching of the object unless
     * the object has been modified more recently than a certain time.
     * <p>
     * A nonzero value gives a time as the number of milliseconds since
     * January 1, 1970, GMT. The object is fetched only if it has been
     * modified more recently than that time.
     * <p>
     * This variable is set by the {@code setIfModifiedSince}
     * method. Its value is returned by the
     * {@code getIfModifiedSince} method.
     * <p>
     * The default value of this field is {@code 0}, indicating
     * that the fetching must always occur.
     *
     * <p>
     *  一些协议支持跳过对对象的提取,除非对象比特定时间更新更新。
     * <p>
     * 非零值将时间表示为自1970年1月1日以来的GMT的毫秒数。仅当该对象最近被修改的时间超过该时间时才获取该对象。
     * <p>
     *  此变量由{@code setIfModifiedSince}方法设置。它的值由{@code getIfModifiedSince}方法返回。
     * <p>
     *  此字段的默认值为{@code 0},表示必须始终进行提取。
     * 
     * 
     * @see     java.net.URLConnection#getIfModifiedSince()
     * @see     java.net.URLConnection#setIfModifiedSince(long)
     */
    protected long ifModifiedSince = 0;

   /**
     * If {@code false}, this connection object has not created a
     * communications link to the specified URL. If {@code true},
     * the communications link has been established.
     * <p>
     *  如果{@code false},此连接对象尚未创建指向指定URL的通信链接。如果{@code true},则通信链路已建立。
     * 
     */
    protected boolean connected = false;

    /**
    /* <p>
    /* 
     * @since 1.5
     */
    private int connectTimeout;
    private int readTimeout;

    /**
    /* <p>
    /* 
     * @since 1.6
     */
    private MessageHeader requests;

   /**
   /* <p>
   /* 
    * @since   JDK1.1
    */
    private static FileNameMap fileNameMap;

    /**
    /* <p>
    /* 
     * @since 1.2.2
     */
    private static boolean fileNameMapLoaded = false;

    /**
     * Loads filename map (a mimetable) from a data file. It will
     * first try to load the user-specific table, defined
     * by &quot;content.types.user.table&quot; property. If that fails,
     * it tries to load the default built-in table.
     *
     * <p>
     *  从数据文件加载文件名映射(可模拟)。它将首先尝试加载由"content.types.user.table"定义的用户特定表,属性。如果失败,它将尝试加载默认的内置表。
     * 
     * 
     * @return the FileNameMap
     * @since 1.2
     * @see #setFileNameMap(java.net.FileNameMap)
     */
    public static synchronized FileNameMap getFileNameMap() {
        if ((fileNameMap == null) && !fileNameMapLoaded) {
            fileNameMap = sun.net.www.MimeTable.loadTable();
            fileNameMapLoaded = true;
        }

        return new FileNameMap() {
            private FileNameMap map = fileNameMap;
            public String getContentTypeFor(String fileName) {
                return map.getContentTypeFor(fileName);
            }
        };
    }

    /**
     * Sets the FileNameMap.
     * <p>
     * If there is a security manager, this method first calls
     * the security manager's {@code checkSetFactory} method
     * to ensure the operation is allowed.
     * This could result in a SecurityException.
     *
     * <p>
     *  设置FileNameMap。
     * <p>
     *  如果有安全管理器,则此方法首先调用安全管理器的{@code checkSetFactory}方法,以确保允许操作。这可能导致SecurityException。
     * 
     * 
     * @param map the FileNameMap to be set
     * @exception  SecurityException  if a security manager exists and its
     *             {@code checkSetFactory} method doesn't allow the operation.
     * @see        SecurityManager#checkSetFactory
     * @see #getFileNameMap()
     * @since 1.2
     */
    public static void setFileNameMap(FileNameMap map) {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) sm.checkSetFactory();
        fileNameMap = map;
    }

    /**
     * Opens a communications link to the resource referenced by this
     * URL, if such a connection has not already been established.
     * <p>
     * If the {@code connect} method is called when the connection
     * has already been opened (indicated by the {@code connected}
     * field having the value {@code true}), the call is ignored.
     * <p>
     * URLConnection objects go through two phases: first they are
     * created, then they are connected.  After being created, and
     * before being connected, various options can be specified
     * (e.g., doInput and UseCaches).  After connecting, it is an
     * error to try to set them.  Operations that depend on being
     * connected, like getContentLength, will implicitly perform the
     * connection, if necessary.
     *
     * <p>
     *  打开到此URL引用的资源的通信链接(如果尚未建立此类连接)。
     * <p>
     *  如果在连接已经打开(由具有值{@code true}的{@code connected}字段指示)时调用{@code connect}方法,则该调用将被忽略。
     * <p>
     * URLConnection对象经历两个阶段：首先它们被创建,然后它们被连接。创建后,在连接之前,可以指定各种选项(例如,doInput和UseCaches)。连接后,尝试设置它们是一个错误。
     * 取决于被连接的操作,如getContentLength,如果需要,将隐式执行连接。
     * 
     * 
     * @throws SocketTimeoutException if the timeout expires before
     *               the connection can be established
     * @exception  IOException  if an I/O error occurs while opening the
     *               connection.
     * @see java.net.URLConnection#connected
     * @see #getConnectTimeout()
     * @see #setConnectTimeout(int)
     */
    abstract public void connect() throws IOException;

    /**
     * Sets a specified timeout value, in milliseconds, to be used
     * when opening a communications link to the resource referenced
     * by this URLConnection.  If the timeout expires before the
     * connection can be established, a
     * java.net.SocketTimeoutException is raised. A timeout of zero is
     * interpreted as an infinite timeout.

     * <p> Some non-standard implementation of this method may ignore
     * the specified timeout. To see the connect timeout set, please
     * call getConnectTimeout().
     *
     * <p>
     *  设置在打开与此URLConnection引用的资源的通信链接时使用的指定超时值(以毫秒为单位)。
     * 如果超时在可以建立连接之前到期,则会引发java.net.SocketTimeoutException。超时为零被解释为无限超时。
     * 
     *  <p>此方法的一些非标准实现可能会忽略指定的超时。要查看连接超时设置,请调用getConnectTimeout()。
     * 
     * 
     * @param timeout an {@code int} that specifies the connect
     *               timeout value in milliseconds
     * @throws IllegalArgumentException if the timeout parameter is negative
     *
     * @see #getConnectTimeout()
     * @see #connect()
     * @since 1.5
     */
    public void setConnectTimeout(int timeout) {
        if (timeout < 0) {
            throw new IllegalArgumentException("timeout can not be negative");
        }
        connectTimeout = timeout;
    }

    /**
     * Returns setting for connect timeout.
     * <p>
     * 0 return implies that the option is disabled
     * (i.e., timeout of infinity).
     *
     * <p>
     *  返回连接超时的设置。
     * <p>
     *  0返回意味着该选项被禁用(即,无限超时)。
     * 
     * 
     * @return an {@code int} that indicates the connect timeout
     *         value in milliseconds
     * @see #setConnectTimeout(int)
     * @see #connect()
     * @since 1.5
     */
    public int getConnectTimeout() {
        return connectTimeout;
    }

    /**
     * Sets the read timeout to a specified timeout, in
     * milliseconds. A non-zero value specifies the timeout when
     * reading from Input stream when a connection is established to a
     * resource. If the timeout expires before there is data available
     * for read, a java.net.SocketTimeoutException is raised. A
     * timeout of zero is interpreted as an infinite timeout.
     *
     *<p> Some non-standard implementation of this method ignores the
     * specified timeout. To see the read timeout set, please call
     * getReadTimeout().
     *
     * <p>
     *  将读取超时设置为指定的超时,以毫秒为单位。非零值指定在建立与资源的连接时从输入流读取时的超时。
     * 如果超时在可用于读取的数据之前到期,则会引发java.net.SocketTimeoutException。超时为零被解释为无限超时。
     * 
     *  p>此方法的某些非标准实现忽略指定的超时。要查看读取超时设置,请调用getReadTimeout()。
     * 
     * 
     * @param timeout an {@code int} that specifies the timeout
     * value to be used in milliseconds
     * @throws IllegalArgumentException if the timeout parameter is negative
     *
     * @see #getReadTimeout()
     * @see InputStream#read()
     * @since 1.5
     */
    public void setReadTimeout(int timeout) {
        if (timeout < 0) {
            throw new IllegalArgumentException("timeout can not be negative");
        }
        readTimeout = timeout;
    }

    /**
     * Returns setting for read timeout. 0 return implies that the
     * option is disabled (i.e., timeout of infinity).
     *
     * <p>
     * 返回读取超时的设置。 0返回意味着该选项被禁用(即,无限超时)。
     * 
     * 
     * @return an {@code int} that indicates the read timeout
     *         value in milliseconds
     *
     * @see #setReadTimeout(int)
     * @see InputStream#read()
     * @since 1.5
     */
    public int getReadTimeout() {
        return readTimeout;
    }

    /**
     * Constructs a URL connection to the specified URL. A connection to
     * the object referenced by the URL is not created.
     *
     * <p>
     *  构造与指定URL的URL连接。不会创建与URL引用的对象的连接。
     * 
     * 
     * @param   url   the specified URL.
     */
    protected URLConnection(URL url) {
        this.url = url;
    }

    /**
     * Returns the value of this {@code URLConnection}'s {@code URL}
     * field.
     *
     * <p>
     *  返回此{@code URLConnection}的{@code URL}字段的值。
     * 
     * 
     * @return  the value of this {@code URLConnection}'s {@code URL}
     *          field.
     * @see     java.net.URLConnection#url
     */
    public URL getURL() {
        return url;
    }

    /**
     * Returns the value of the {@code content-length} header field.
     * <P>
     * <B>Note</B>: {@link #getContentLengthLong() getContentLengthLong()}
     * should be preferred over this method, since it returns a {@code long}
     * instead and is therefore more portable.</P>
     *
     * <p>
     *  返回{@code content-length}标头字段的值。
     * <P>
     *  注意</B>：{@link #getContentLengthLong()getContentLengthLong()}应该优先于此方法,因为它返回一个{@code long},因此更便于移植。
     * </P>。
     * 
     * 
     * @return  the content length of the resource that this connection's URL
     *          references, {@code -1} if the content length is not known,
     *          or if the content length is greater than Integer.MAX_VALUE.
     */
    public int getContentLength() {
        long l = getContentLengthLong();
        if (l > Integer.MAX_VALUE)
            return -1;
        return (int) l;
    }

    /**
     * Returns the value of the {@code content-length} header field as a
     * long.
     *
     * <p>
     *  将{@code content-length}标头字段的值返回为long。
     * 
     * 
     * @return  the content length of the resource that this connection's URL
     *          references, or {@code -1} if the content length is
     *          not known.
     * @since 7.0
     */
    public long getContentLengthLong() {
        return getHeaderFieldLong("content-length", -1);
    }

    /**
     * Returns the value of the {@code content-type} header field.
     *
     * <p>
     *  返回{@code content-type}标头字段的值。
     * 
     * 
     * @return  the content type of the resource that the URL references,
     *          or {@code null} if not known.
     * @see     java.net.URLConnection#getHeaderField(java.lang.String)
     */
    public String getContentType() {
        return getHeaderField("content-type");
    }

    /**
     * Returns the value of the {@code content-encoding} header field.
     *
     * <p>
     *  返回{@code content-encoding}标头字段的值。
     * 
     * 
     * @return  the content encoding of the resource that the URL references,
     *          or {@code null} if not known.
     * @see     java.net.URLConnection#getHeaderField(java.lang.String)
     */
    public String getContentEncoding() {
        return getHeaderField("content-encoding");
    }

    /**
     * Returns the value of the {@code expires} header field.
     *
     * <p>
     *  返回{@code expires}标头字段的值。
     * 
     * 
     * @return  the expiration date of the resource that this URL references,
     *          or 0 if not known. The value is the number of milliseconds since
     *          January 1, 1970 GMT.
     * @see     java.net.URLConnection#getHeaderField(java.lang.String)
     */
    public long getExpiration() {
        return getHeaderFieldDate("expires", 0);
    }

    /**
     * Returns the value of the {@code date} header field.
     *
     * <p>
     *  返回{@code date}标头字段的值。
     * 
     * 
     * @return  the sending date of the resource that the URL references,
     *          or {@code 0} if not known. The value returned is the
     *          number of milliseconds since January 1, 1970 GMT.
     * @see     java.net.URLConnection#getHeaderField(java.lang.String)
     */
    public long getDate() {
        return getHeaderFieldDate("date", 0);
    }

    /**
     * Returns the value of the {@code last-modified} header field.
     * The result is the number of milliseconds since January 1, 1970 GMT.
     *
     * <p>
     *  返回{@code last-modified}头字段的值。结果是自1970年1月1日以来的毫秒数。
     * 
     * 
     * @return  the date the resource referenced by this
     *          {@code URLConnection} was last modified, or 0 if not known.
     * @see     java.net.URLConnection#getHeaderField(java.lang.String)
     */
    public long getLastModified() {
        return getHeaderFieldDate("last-modified", 0);
    }

    /**
     * Returns the value of the named header field.
     * <p>
     * If called on a connection that sets the same header multiple times
     * with possibly different values, only the last value is returned.
     *
     *
     * <p>
     *  返回指定头字段的值。
     * <p>
     *  如果在一个连接上调用多次设置相同的头,并且可能具有不同的值,则只返回最后一个值。
     * 
     * 
     * @param   name   the name of a header field.
     * @return  the value of the named header field, or {@code null}
     *          if there is no such field in the header.
     */
    public String getHeaderField(String name) {
        return null;
    }

    /**
     * Returns an unmodifiable Map of the header fields.
     * The Map keys are Strings that represent the
     * response-header field names. Each Map value is an
     * unmodifiable List of Strings that represents
     * the corresponding field values.
     *
     * <p>
     *  返回标头字段的不可修改Map。映射键是表示响应头字段名称的字符串。每个Map值是不可修改的字符串列表,表示相应的字段值。
     * 
     * 
     * @return a Map of header fields
     * @since 1.4
     */
    public Map<String,List<String>> getHeaderFields() {
        return Collections.emptyMap();
    }

    /**
     * Returns the value of the named field parsed as a number.
     * <p>
     * This form of {@code getHeaderField} exists because some
     * connection types (e.g., {@code http-ng}) have pre-parsed
     * headers. Classes for that connection type can override this method
     * and short-circuit the parsing.
     *
     * <p>
     *  返回作为数字解析的命名字段的值。
     * <p>
     * {@code getHeaderField}的这种形式存在,因为某些连接类型(例如{@code http-ng})具有预解析的标头。该连接类型的类可以覆盖此方法并使解析短路。
     * 
     * 
     * @param   name      the name of the header field.
     * @param   Default   the default value.
     * @return  the value of the named field, parsed as an integer. The
     *          {@code Default} value is returned if the field is
     *          missing or malformed.
     */
    public int getHeaderFieldInt(String name, int Default) {
        String value = getHeaderField(name);
        try {
            return Integer.parseInt(value);
        } catch (Exception e) { }
        return Default;
    }

    /**
     * Returns the value of the named field parsed as a number.
     * <p>
     * This form of {@code getHeaderField} exists because some
     * connection types (e.g., {@code http-ng}) have pre-parsed
     * headers. Classes for that connection type can override this method
     * and short-circuit the parsing.
     *
     * <p>
     *  返回作为数字解析的命名字段的值。
     * <p>
     *  {@code getHeaderField}的这种形式存在,因为某些连接类型(例如{@code http-ng})具有预解析的标头。该连接类型的类可以覆盖此方法并使解析短路。
     * 
     * 
     * @param   name      the name of the header field.
     * @param   Default   the default value.
     * @return  the value of the named field, parsed as a long. The
     *          {@code Default} value is returned if the field is
     *          missing or malformed.
     * @since 7.0
     */
    public long getHeaderFieldLong(String name, long Default) {
        String value = getHeaderField(name);
        try {
            return Long.parseLong(value);
        } catch (Exception e) { }
        return Default;
    }

    /**
     * Returns the value of the named field parsed as date.
     * The result is the number of milliseconds since January 1, 1970 GMT
     * represented by the named field.
     * <p>
     * This form of {@code getHeaderField} exists because some
     * connection types (e.g., {@code http-ng}) have pre-parsed
     * headers. Classes for that connection type can override this method
     * and short-circuit the parsing.
     *
     * <p>
     *  返回被解析为date的命名字段的值。结果是自指定字段表示的1970年1月1日以来的毫秒数。
     * <p>
     *  {@code getHeaderField}的这种形式存在,因为某些连接类型(例如{@code http-ng})具有预解析的标头。该连接类型的类可以覆盖此方法并使解析短路。
     * 
     * 
     * @param   name     the name of the header field.
     * @param   Default   a default value.
     * @return  the value of the field, parsed as a date. The value of the
     *          {@code Default} argument is returned if the field is
     *          missing or malformed.
     */
    @SuppressWarnings("deprecation")
    public long getHeaderFieldDate(String name, long Default) {
        String value = getHeaderField(name);
        try {
            return Date.parse(value);
        } catch (Exception e) { }
        return Default;
    }

    /**
     * Returns the key for the {@code n}<sup>th</sup> header field.
     * It returns {@code null} if there are fewer than {@code n+1} fields.
     *
     * <p>
     *  返回{@code n} <sup> th </sup>头字段的键。如果少于{@code n + 1}个字段,则返回{@code null}。
     * 
     * 
     * @param   n   an index, where {@code n>=0}
     * @return  the key for the {@code n}<sup>th</sup> header field,
     *          or {@code null} if there are fewer than {@code n+1}
     *          fields.
     */
    public String getHeaderFieldKey(int n) {
        return null;
    }

    /**
     * Returns the value for the {@code n}<sup>th</sup> header field.
     * It returns {@code null} if there are fewer than
     * {@code n+1}fields.
     * <p>
     * This method can be used in conjunction with the
     * {@link #getHeaderFieldKey(int) getHeaderFieldKey} method to iterate through all
     * the headers in the message.
     *
     * <p>
     *  返回{@code n} <sup> th </sup>头字段的值。如果少于{@code n + 1}个字段,则返回{@code null}。
     * <p>
     *  此方法可以与{@link #getHeaderFieldKey(int)getHeaderFieldKey}方法结合使用,以遍历消息中的所有标头。
     * 
     * 
     * @param   n   an index, where {@code n>=0}
     * @return  the value of the {@code n}<sup>th</sup> header field
     *          or {@code null} if there are fewer than {@code n+1} fields
     * @see     java.net.URLConnection#getHeaderFieldKey(int)
     */
    public String getHeaderField(int n) {
        return null;
    }

    /**
     * Retrieves the contents of this URL connection.
     * <p>
     * This method first determines the content type of the object by
     * calling the {@code getContentType} method. If this is
     * the first time that the application has seen that specific content
     * type, a content handler for that content type is created:
     * <ol>
     * <li>If the application has set up a content handler factory instance
     *     using the {@code setContentHandlerFactory} method, the
     *     {@code createContentHandler} method of that instance is called
     *     with the content type as an argument; the result is a content
     *     handler for that content type.
     * <li>If no content handler factory has yet been set up, or if the
     *     factory's {@code createContentHandler} method returns
     *     {@code null}, then the application loads the class named:
     *     <blockquote><pre>
     *         sun.net.www.content.&lt;<i>contentType</i>&gt;
     *     </pre></blockquote>
     *     where &lt;<i>contentType</i>&gt; is formed by taking the
     *     content-type string, replacing all slash characters with a
     *     {@code period} ('.'), and all other non-alphanumeric characters
     *     with the underscore character '{@code _}'. The alphanumeric
     *     characters are specifically the 26 uppercase ASCII letters
     *     '{@code A}' through '{@code Z}', the 26 lowercase ASCII
     *     letters '{@code a}' through '{@code z}', and the 10 ASCII
     *     digits '{@code 0}' through '{@code 9}'. If the specified
     *     class does not exist, or is not a subclass of
     *     {@code ContentHandler}, then an
     *     {@code UnknownServiceException} is thrown.
     * </ol>
     *
     * <p>
     *  检索此URL连接的内容。
     * <p>
     * 此方法首先通过调用{@code getContentType}方法确定对象的内容类型。如果这是应用程序第一次看到特定内容类型,则会创建该内容类型的内容处理程序：
     * <ol>
     *  <li>如果应用程序使用{@code setContentHandlerFactory}方法设置了内容处理程序工厂实例,则会调用该实例的{@code createContentHandler}方法,并
     * 将内容类型作为参数;结果是该内容类型的内容处理程序。
     *  <li>如果尚未设置内容处理程序工厂,或者工厂的{@code createContentHandler}方法返回{@code null},那么应用程序将加载名为的类：<blockquote> <pre>
     *  sun.net.www .content。
     * &lt; <i> contentType </i>&gt; </pre> </blockquote>其中&lt; <i> contentType </i>&gt;通过采用内容类型字符串,用{@code period}
     * ('。
     * ')替换所有斜杠字符以及用下划线字符"{@code _}"替换所有其他非字母数字字符来形成。
     * 字母数字字符具体为26个大写ASCII字母'{@code A}'到'{@code Z}',26个小写ASCII字母'{@code a}'到'{@code z}', ASCII数字'{@code 0}'到
     * '{@code 9}'。
     * ')替换所有斜杠字符以及用下划线字符"{@code _}"替换所有其他非字母数字字符来形成。
     * 如果指定的类不存在,或者不是{@code ContentHandler}的子类,那么将抛出{@code UnknownServiceException}。
     * </ol>
     * 
     * 
     * @return     the object fetched. The {@code instanceof} operator
     *               should be used to determine the specific kind of object
     *               returned.
     * @exception  IOException              if an I/O error occurs while
     *               getting the content.
     * @exception  UnknownServiceException  if the protocol does not support
     *               the content type.
     * @see        java.net.ContentHandlerFactory#createContentHandler(java.lang.String)
     * @see        java.net.URLConnection#getContentType()
     * @see        java.net.URLConnection#setContentHandlerFactory(java.net.ContentHandlerFactory)
     */
    public Object getContent() throws IOException {
        // Must call getInputStream before GetHeaderField gets called
        // so that FileNotFoundException has a chance to be thrown up
        // from here without being caught.
        getInputStream();
        return getContentHandler().getContent(this);
    }

    /**
     * Retrieves the contents of this URL connection.
     *
     * <p>
     *  检索此URL连接的内容。
     * 
     * 
     * @param classes the {@code Class} array
     * indicating the requested types
     * @return     the object fetched that is the first match of the type
     *               specified in the classes array. null if none of
     *               the requested types are supported.
     *               The {@code instanceof} operator should be used to
     *               determine the specific kind of object returned.
     * @exception  IOException              if an I/O error occurs while
     *               getting the content.
     * @exception  UnknownServiceException  if the protocol does not support
     *               the content type.
     * @see        java.net.URLConnection#getContent()
     * @see        java.net.ContentHandlerFactory#createContentHandler(java.lang.String)
     * @see        java.net.URLConnection#getContent(java.lang.Class[])
     * @see        java.net.URLConnection#setContentHandlerFactory(java.net.ContentHandlerFactory)
     * @since 1.3
     */
    public Object getContent(Class[] classes) throws IOException {
        // Must call getInputStream before GetHeaderField gets called
        // so that FileNotFoundException has a chance to be thrown up
        // from here without being caught.
        getInputStream();
        return getContentHandler().getContent(this, classes);
    }

    /**
     * Returns a permission object representing the permission
     * necessary to make the connection represented by this
     * object. This method returns null if no permission is
     * required to make the connection. By default, this method
     * returns {@code java.security.AllPermission}. Subclasses
     * should override this method and return the permission
     * that best represents the permission required to make a
     * a connection to the URL. For example, a {@code URLConnection}
     * representing a {@code file:} URL would return a
     * {@code java.io.FilePermission} object.
     *
     * <p>The permission returned may dependent upon the state of the
     * connection. For example, the permission before connecting may be
     * different from that after connecting. For example, an HTTP
     * sever, say foo.com, may redirect the connection to a different
     * host, say bar.com. Before connecting the permission returned by
     * the connection will represent the permission needed to connect
     * to foo.com, while the permission returned after connecting will
     * be to bar.com.
     *
     * <p>Permissions are generally used for two purposes: to protect
     * caches of objects obtained through URLConnections, and to check
     * the right of a recipient to learn about a particular URL. In
     * the first case, the permission should be obtained
     * <em>after</em> the object has been obtained. For example, in an
     * HTTP connection, this will represent the permission to connect
     * to the host from which the data was ultimately fetched. In the
     * second case, the permission should be obtained and tested
     * <em>before</em> connecting.
     *
     * <p>
     * 返回一个权限对象,该对象表示进行此对象所表示的连接所需的权限。如果不需要任何权限来建立连接,此方法将返回null。
     * 默认情况下,此方法返回{@code java.security.AllPermission}。子类应该覆盖此方法,并返回最能代表与URL建立连接所需的权限的权限。
     * 例如,表示{@code file：} URL的{@code URLConnection}将返回一个{@code java.io.FilePermission}对象。
     * 
     *  <p>返回的权限可能取决于连接的状态。例如,连接前的权限可能与连接后的权限不同。例如,HTTP服务器(例如foo.com)可以将连接重定向到不同的主机(例如bar.com)。
     * 在连接之前,连接返回的权限将表示连接到foo.com所需的权限,连接后返回的权限将是bar.com。
     * 
     * <p>权限通常用于两个目的：保护通过URLConnections获取的对象的缓存,并检查收件人了解特定URL的权利。在第一种情况下,应在</em>获得对象之后<em>获得该权限。
     * 例如,在HTTP连接中,这将表示连接到最终从其获取数据的主机的权限。在第二种情况下,应该在连接之前获得并测试该许可。
     * 
     * 
     * @return the permission object representing the permission
     * necessary to make the connection represented by this
     * URLConnection.
     *
     * @exception IOException if the computation of the permission
     * requires network or file I/O and an exception occurs while
     * computing it.
     */
    public Permission getPermission() throws IOException {
        return SecurityConstants.ALL_PERMISSION;
    }

    /**
     * Returns an input stream that reads from this open connection.
     *
     * A SocketTimeoutException can be thrown when reading from the
     * returned input stream if the read timeout expires before data
     * is available for read.
     *
     * <p>
     *  返回从此打开的连接读取的输入流。
     * 
     *  如果读取超时在数据可用于读取之前读取,则从读取返回的输入流时可能会抛出SocketTimeoutException。
     * 
     * 
     * @return     an input stream that reads from this open connection.
     * @exception  IOException              if an I/O error occurs while
     *               creating the input stream.
     * @exception  UnknownServiceException  if the protocol does not support
     *               input.
     * @see #setReadTimeout(int)
     * @see #getReadTimeout()
     */
    public InputStream getInputStream() throws IOException {
        throw new UnknownServiceException("protocol doesn't support input");
    }

    /**
     * Returns an output stream that writes to this connection.
     *
     * <p>
     *  返回写入此连接的输出流。
     * 
     * 
     * @return     an output stream that writes to this connection.
     * @exception  IOException              if an I/O error occurs while
     *               creating the output stream.
     * @exception  UnknownServiceException  if the protocol does not support
     *               output.
     */
    public OutputStream getOutputStream() throws IOException {
        throw new UnknownServiceException("protocol doesn't support output");
    }

    /**
     * Returns a {@code String} representation of this URL connection.
     *
     * <p>
     *  返回此URL连接的{@code String}表示形式。
     * 
     * 
     * @return  a string representation of this {@code URLConnection}.
     */
    public String toString() {
        return this.getClass().getName() + ":" + url;
    }

    /**
     * Sets the value of the {@code doInput} field for this
     * {@code URLConnection} to the specified value.
     * <p>
     * A URL connection can be used for input and/or output.  Set the DoInput
     * flag to true if you intend to use the URL connection for input,
     * false if not.  The default is true.
     *
     * <p>
     *  将此{@code URLConnection}的{@code doInput}字段的值设置为指定的值。
     * <p>
     *  URL连接可用于输入和/或输出。如果您打算将URL连接用于输入,请将DoInput标志设置为true,否则为false。默认值为true。
     * 
     * 
     * @param   doinput   the new value.
     * @throws IllegalStateException if already connected
     * @see     java.net.URLConnection#doInput
     * @see #getDoInput()
     */
    public void setDoInput(boolean doinput) {
        if (connected)
            throw new IllegalStateException("Already connected");
        doInput = doinput;
    }

    /**
     * Returns the value of this {@code URLConnection}'s
     * {@code doInput} flag.
     *
     * <p>
     *  返回此{@code URLConnection}的{@code doInput}标志的值。
     * 
     * 
     * @return  the value of this {@code URLConnection}'s
     *          {@code doInput} flag.
     * @see     #setDoInput(boolean)
     */
    public boolean getDoInput() {
        return doInput;
    }

    /**
     * Sets the value of the {@code doOutput} field for this
     * {@code URLConnection} to the specified value.
     * <p>
     * A URL connection can be used for input and/or output.  Set the DoOutput
     * flag to true if you intend to use the URL connection for output,
     * false if not.  The default is false.
     *
     * <p>
     *  将此{@code URLConnection}的{@code doOutput}字段的值设置为指定的值。
     * <p>
     *  URL连接可用于输入和/或输出。如果您打算将URL连接用于输出,请将DoOutput标志设置为true,否则为false。默认值为false。
     * 
     * 
     * @param   dooutput   the new value.
     * @throws IllegalStateException if already connected
     * @see #getDoOutput()
     */
    public void setDoOutput(boolean dooutput) {
        if (connected)
            throw new IllegalStateException("Already connected");
        doOutput = dooutput;
    }

    /**
     * Returns the value of this {@code URLConnection}'s
     * {@code doOutput} flag.
     *
     * <p>
     * 返回此{@code URLConnection}的{@code doOutput}标志的值。
     * 
     * 
     * @return  the value of this {@code URLConnection}'s
     *          {@code doOutput} flag.
     * @see     #setDoOutput(boolean)
     */
    public boolean getDoOutput() {
        return doOutput;
    }

    /**
     * Set the value of the {@code allowUserInteraction} field of
     * this {@code URLConnection}.
     *
     * <p>
     *  设置此{@code URLConnection}的{@code allowUserInteraction}字段的值。
     * 
     * 
     * @param   allowuserinteraction   the new value.
     * @throws IllegalStateException if already connected
     * @see     #getAllowUserInteraction()
     */
    public void setAllowUserInteraction(boolean allowuserinteraction) {
        if (connected)
            throw new IllegalStateException("Already connected");
        allowUserInteraction = allowuserinteraction;
    }

    /**
     * Returns the value of the {@code allowUserInteraction} field for
     * this object.
     *
     * <p>
     *  返回此对象的{@code allowUserInteraction}字段的值。
     * 
     * 
     * @return  the value of the {@code allowUserInteraction} field for
     *          this object.
     * @see     #setAllowUserInteraction(boolean)
     */
    public boolean getAllowUserInteraction() {
        return allowUserInteraction;
    }

    /**
     * Sets the default value of the
     * {@code allowUserInteraction} field for all future
     * {@code URLConnection} objects to the specified value.
     *
     * <p>
     *  将{@code allowUserInteraction}字段的所有未来{@code URLConnection}对象的默认值设置为指定的值。
     * 
     * 
     * @param   defaultallowuserinteraction   the new value.
     * @see     #getDefaultAllowUserInteraction()
     */
    public static void setDefaultAllowUserInteraction(boolean defaultallowuserinteraction) {
        defaultAllowUserInteraction = defaultallowuserinteraction;
    }

    /**
     * Returns the default value of the {@code allowUserInteraction}
     * field.
     * <p>
     * Ths default is "sticky", being a part of the static state of all
     * URLConnections.  This flag applies to the next, and all following
     * URLConnections that are created.
     *
     * <p>
     *  返回{@code allowUserInteraction}字段的默认值。
     * <p>
     *  默认值是"sticky",它是所有URLConnections的静态状态的一部分。此标志适用于创建的下一个和所有后面的URLConnections。
     * 
     * 
     * @return  the default value of the {@code allowUserInteraction}
     *          field.
     * @see     #setDefaultAllowUserInteraction(boolean)
     */
    public static boolean getDefaultAllowUserInteraction() {
        return defaultAllowUserInteraction;
    }

    /**
     * Sets the value of the {@code useCaches} field of this
     * {@code URLConnection} to the specified value.
     * <p>
     * Some protocols do caching of documents.  Occasionally, it is important
     * to be able to "tunnel through" and ignore the caches (e.g., the
     * "reload" button in a browser).  If the UseCaches flag on a connection
     * is true, the connection is allowed to use whatever caches it can.
     *  If false, caches are to be ignored.
     *  The default value comes from DefaultUseCaches, which defaults to
     * true.
     *
     * <p>
     *  将此{@code URLConnection}的{@code useCaches}字段的值设置为指定的值。
     * <p>
     *  一些协议做文档的缓存。偶尔,重要的是能够"通过"并忽略缓存(例如,浏览器中的"重新加载"按钮)。如果连接上的UseCaches标志为true,则允许连接使用它可以使用的任何缓存。
     * 如果为false,则将忽略高速缓存。默认值来自DefaultUseCaches,默认值为true。
     * 
     * 
     * @param usecaches a {@code boolean} indicating whether
     * or not to allow caching
     * @throws IllegalStateException if already connected
     * @see #getUseCaches()
     */
    public void setUseCaches(boolean usecaches) {
        if (connected)
            throw new IllegalStateException("Already connected");
        useCaches = usecaches;
    }

    /**
     * Returns the value of this {@code URLConnection}'s
     * {@code useCaches} field.
     *
     * <p>
     *  返回此{@code URLConnection}的{@code useCaches}字段的值。
     * 
     * 
     * @return  the value of this {@code URLConnection}'s
     *          {@code useCaches} field.
     * @see #setUseCaches(boolean)
     */
    public boolean getUseCaches() {
        return useCaches;
    }

    /**
     * Sets the value of the {@code ifModifiedSince} field of
     * this {@code URLConnection} to the specified value.
     *
     * <p>
     *  将此{@code URLConnection}的{@code ifModifiedSince}字段的值设置为指定的值。
     * 
     * 
     * @param   ifmodifiedsince   the new value.
     * @throws IllegalStateException if already connected
     * @see     #getIfModifiedSince()
     */
    public void setIfModifiedSince(long ifmodifiedsince) {
        if (connected)
            throw new IllegalStateException("Already connected");
        ifModifiedSince = ifmodifiedsince;
    }

    /**
     * Returns the value of this object's {@code ifModifiedSince} field.
     *
     * <p>
     *  返回此对象的{@code ifModifiedSince}字段的值。
     * 
     * 
     * @return  the value of this object's {@code ifModifiedSince} field.
     * @see #setIfModifiedSince(long)
     */
    public long getIfModifiedSince() {
        return ifModifiedSince;
    }

   /**
     * Returns the default value of a {@code URLConnection}'s
     * {@code useCaches} flag.
     * <p>
     * Ths default is "sticky", being a part of the static state of all
     * URLConnections.  This flag applies to the next, and all following
     * URLConnections that are created.
     *
     * <p>
     *  返回{@code URLConnection}的{@code useCaches}标记的默认值。
     * <p>
     * 默认值是"sticky",它是所有URLConnections的静态状态的一部分。此标志适用于创建的下一个和所有后面的URLConnections。
     * 
     * 
     * @return  the default value of a {@code URLConnection}'s
     *          {@code useCaches} flag.
     * @see     #setDefaultUseCaches(boolean)
     */
    public boolean getDefaultUseCaches() {
        return defaultUseCaches;
    }

   /**
     * Sets the default value of the {@code useCaches} field to the
     * specified value.
     *
     * <p>
     *  将{@code useCaches}字段的默认值设置为指定的值。
     * 
     * 
     * @param   defaultusecaches   the new value.
     * @see     #getDefaultUseCaches()
     */
    public void setDefaultUseCaches(boolean defaultusecaches) {
        defaultUseCaches = defaultusecaches;
    }

    /**
     * Sets the general request property. If a property with the key already
     * exists, overwrite its value with the new value.
     *
     * <p> NOTE: HTTP requires all request properties which can
     * legally have multiple instances with the same key
     * to use a comma-separated list syntax which enables multiple
     * properties to be appended into a single property.
     *
     * <p>
     *  设置常规请求属性。如果具有键的属性已存在,则使用新值覆盖其值。
     * 
     *  <p>注意：HTTP要求所有可以合法使用同一个键的多个实例的请求属性,以使用逗号分隔的列表语法,这样可以将多个属性附加到单个属性中。
     * 
     * 
     * @param   key     the keyword by which the request is known
     *                  (e.g., "{@code Accept}").
     * @param   value   the value associated with it.
     * @throws IllegalStateException if already connected
     * @throws NullPointerException if key is <CODE>null</CODE>
     * @see #getRequestProperty(java.lang.String)
     */
    public void setRequestProperty(String key, String value) {
        if (connected)
            throw new IllegalStateException("Already connected");
        if (key == null)
            throw new NullPointerException ("key is null");

        if (requests == null)
            requests = new MessageHeader();

        requests.set(key, value);
    }

    /**
     * Adds a general request property specified by a
     * key-value pair.  This method will not overwrite
     * existing values associated with the same key.
     *
     * <p>
     *  添加键值对指定的常规请求属性。此方法不会覆盖与同一键相关联的现有值。
     * 
     * 
     * @param   key     the keyword by which the request is known
     *                  (e.g., "{@code Accept}").
     * @param   value  the value associated with it.
     * @throws IllegalStateException if already connected
     * @throws NullPointerException if key is null
     * @see #getRequestProperties()
     * @since 1.4
     */
    public void addRequestProperty(String key, String value) {
        if (connected)
            throw new IllegalStateException("Already connected");
        if (key == null)
            throw new NullPointerException ("key is null");

        if (requests == null)
            requests = new MessageHeader();

        requests.add(key, value);
    }


    /**
     * Returns the value of the named general request property for this
     * connection.
     *
     * <p>
     *  返回此连接的命名通用请求属性的值。
     * 
     * 
     * @param key the keyword by which the request is known (e.g., "Accept").
     * @return  the value of the named general request property for this
     *           connection. If key is null, then null is returned.
     * @throws IllegalStateException if already connected
     * @see #setRequestProperty(java.lang.String, java.lang.String)
     */
    public String getRequestProperty(String key) {
        if (connected)
            throw new IllegalStateException("Already connected");

        if (requests == null)
            return null;

        return requests.findValue(key);
    }

    /**
     * Returns an unmodifiable Map of general request
     * properties for this connection. The Map keys
     * are Strings that represent the request-header
     * field names. Each Map value is a unmodifiable List
     * of Strings that represents the corresponding
     * field values.
     *
     * <p>
     *  返回此连接的常规请求属性的不可修改Map。 Map键是表示请求头字段名的字符串。每个Map值是不可修改的字符串列表,表示相应的字段值。
     * 
     * 
     * @return  a Map of the general request properties for this connection.
     * @throws IllegalStateException if already connected
     * @since 1.4
     */
    public Map<String,List<String>> getRequestProperties() {
        if (connected)
            throw new IllegalStateException("Already connected");

        if (requests == null)
            return Collections.emptyMap();

        return requests.getHeaders(null);
    }

    /**
     * Sets the default value of a general request property. When a
     * {@code URLConnection} is created, it is initialized with
     * these properties.
     *
     * <p>
     *  设置常规请求属性的默认值。创建{@code URLConnection}时,将使用这些属性进行初始化。
     * 
     * 
     * @param   key     the keyword by which the request is known
     *                  (e.g., "{@code Accept}").
     * @param   value   the value associated with the key.
     *
     * @see java.net.URLConnection#setRequestProperty(java.lang.String,java.lang.String)
     *
     * @deprecated The instance specific setRequestProperty method
     * should be used after an appropriate instance of URLConnection
     * is obtained. Invoking this method will have no effect.
     *
     * @see #getDefaultRequestProperty(java.lang.String)
     */
    @Deprecated
    public static void setDefaultRequestProperty(String key, String value) {
    }

    /**
     * Returns the value of the default request property. Default request
     * properties are set for every connection.
     *
     * <p>
     *  返回默认请求属性的值。为每个连接设置默认请求属性。
     * 
     * 
     * @param key the keyword by which the request is known (e.g., "Accept").
     * @return  the value of the default request property
     * for the specified key.
     *
     * @see java.net.URLConnection#getRequestProperty(java.lang.String)
     *
     * @deprecated The instance specific getRequestProperty method
     * should be used after an appropriate instance of URLConnection
     * is obtained.
     *
     * @see #setDefaultRequestProperty(java.lang.String, java.lang.String)
     */
    @Deprecated
    public static String getDefaultRequestProperty(String key) {
        return null;
    }

    /**
     * The ContentHandler factory.
     * <p>
     *  ContentHandler工厂。
     * 
     */
    static ContentHandlerFactory factory;

    /**
     * Sets the {@code ContentHandlerFactory} of an
     * application. It can be called at most once by an application.
     * <p>
     * The {@code ContentHandlerFactory} instance is used to
     * construct a content handler from a content type
     * <p>
     * If there is a security manager, this method first calls
     * the security manager's {@code checkSetFactory} method
     * to ensure the operation is allowed.
     * This could result in a SecurityException.
     *
     * <p>
     *  设置应用程序的{@code ContentHandlerFactory}。它可以由应用程序最多调用一次。
     * <p>
     * {@code ContentHandlerFactory}实例用于从内容类型构造内容处理程序
     * <p>
     *  如果有安全管理器,则此方法首先调用安全管理器的{@code checkSetFactory}方法,以确保允许操作。这可能导致SecurityException。
     * 
     * 
     * @param      fac   the desired factory.
     * @exception  Error  if the factory has already been defined.
     * @exception  SecurityException  if a security manager exists and its
     *             {@code checkSetFactory} method doesn't allow the operation.
     * @see        java.net.ContentHandlerFactory
     * @see        java.net.URLConnection#getContent()
     * @see        SecurityManager#checkSetFactory
     */
    public static synchronized void setContentHandlerFactory(ContentHandlerFactory fac) {
        if (factory != null) {
            throw new Error("factory already defined");
        }
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkSetFactory();
        }
        factory = fac;
    }

    private static Hashtable<String, ContentHandler> handlers = new Hashtable<>();

    /**
     * Gets the Content Handler appropriate for this connection.
     * <p>
     *  获取适合此连接的内容处理程序。
     * 
     */
    synchronized ContentHandler getContentHandler()
        throws UnknownServiceException
    {
        String contentType = stripOffParameters(getContentType());
        ContentHandler handler = null;
        if (contentType == null)
            throw new UnknownServiceException("no content-type");
        try {
            handler = handlers.get(contentType);
            if (handler != null)
                return handler;
        } catch(Exception e) {
        }

        if (factory != null)
            handler = factory.createContentHandler(contentType);
        if (handler == null) {
            try {
                handler = lookupContentHandlerClassFor(contentType);
            } catch(Exception e) {
                e.printStackTrace();
                handler = UnknownContentHandler.INSTANCE;
            }
            handlers.put(contentType, handler);
        }
        return handler;
    }

    /*
     * Media types are in the format: type/subtype*(; parameter).
     * For looking up the content handler, we should ignore those
     * parameters.
     * <p>
     *  介质类型的格式为：type / subtype *(; parameter)。为了查找内容处理程序,我们应该忽略这些参数。
     * 
     */
    private String stripOffParameters(String contentType)
    {
        if (contentType == null)
            return null;
        int index = contentType.indexOf(';');

        if (index > 0)
            return contentType.substring(0, index);
        else
            return contentType;
    }

    private static final String contentClassPrefix = "sun.net.www.content";
    private static final String contentPathProp = "java.content.handler.pkgs";

    /**
     * Looks for a content handler in a user-defineable set of places.
     * By default it looks in sun.net.www.content, but users can define a
     * vertical-bar delimited set of class prefixes to search through in
     * addition by defining the java.content.handler.pkgs property.
     * The class name must be of the form:
     * <pre>
     *     {package-prefix}.{major}.{minor}
     * e.g.
     *     YoyoDyne.experimental.text.plain
     * </pre>
     * <p>
     *  在用户可定义的场所集合中查找内容处理程序。
     * 默认情况下,它在sun.net.www.content中查找,但用户可以通过定义java.content.handler.pkgs属性来定义一个垂直条分隔的类前缀集,以此进行搜索。
     * 类名必须是以下形式：。
     * <pre>
     *  {package-prefix}。{major}。{minor}例如YoyoDyne.experimental.text.plain
     * </pre>
     */
    private ContentHandler lookupContentHandlerClassFor(String contentType)
        throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        String contentHandlerClassName = typeToPackageName(contentType);

        String contentHandlerPkgPrefixes =getContentHandlerPkgPrefixes();

        StringTokenizer packagePrefixIter =
            new StringTokenizer(contentHandlerPkgPrefixes, "|");

        while (packagePrefixIter.hasMoreTokens()) {
            String packagePrefix = packagePrefixIter.nextToken().trim();

            try {
                String clsName = packagePrefix + "." + contentHandlerClassName;
                Class<?> cls = null;
                try {
                    cls = Class.forName(clsName);
                } catch (ClassNotFoundException e) {
                    ClassLoader cl = ClassLoader.getSystemClassLoader();
                    if (cl != null) {
                        cls = cl.loadClass(clsName);
                    }
                }
                if (cls != null) {
                    ContentHandler handler =
                        (ContentHandler)cls.newInstance();
                    return handler;
                }
            } catch(Exception e) {
            }
        }

        return UnknownContentHandler.INSTANCE;
    }

    /**
     * Utility function to map a MIME content type into an equivalent
     * pair of class name components.  For example: "text/html" would
     * be returned as "text.html"
     * <p>
     *  用于将MIME内容类型映射到等效名称组件的等效对中的实用程序功能。例如："text / html"将作为"text.html"返回,
     * 
     */
    private String typeToPackageName(String contentType) {
        // make sure we canonicalize the class name: all lower case
        contentType = contentType.toLowerCase();
        int len = contentType.length();
        char nm[] = new char[len];
        contentType.getChars(0, len, nm, 0);
        for (int i = 0; i < len; i++) {
            char c = nm[i];
            if (c == '/') {
                nm[i] = '.';
            } else if (!('A' <= c && c <= 'Z' ||
                       'a' <= c && c <= 'z' ||
                       '0' <= c && c <= '9')) {
                nm[i] = '_';
            }
        }
        return new String(nm);
    }


    /**
     * Returns a vertical bar separated list of package prefixes for potential
     * content handlers.  Tries to get the java.content.handler.pkgs property
     * to use as a set of package prefixes to search.  Whether or not
     * that property has been defined, the sun.net.www.content is always
     * the last one on the returned package list.
     * <p>
     *  返回潜在内容处理程序的垂直条分隔的包前缀列表。尝试获取java.content.handler.pkgs属性以用作要搜索的一组包前缀。
     * 无论该属性是否已定义,sun.net.www.content始终是返回的包列表上的最后一个。
     * 
     */
    private String getContentHandlerPkgPrefixes() {
        String packagePrefixList = AccessController.doPrivileged(
            new sun.security.action.GetPropertyAction(contentPathProp, ""));

        if (packagePrefixList != "") {
            packagePrefixList += "|";
        }

        return packagePrefixList + contentClassPrefix;
    }

    /**
     * Tries to determine the content type of an object, based
     * on the specified "file" component of a URL.
     * This is a convenience method that can be used by
     * subclasses that override the {@code getContentType} method.
     *
     * <p>
     * 尝试根据URL的指定"文件"组件确定对象的内容类型。这是一个方便的方法,可以由覆盖{@code getContentType}方法的子类使用。
     * 
     * 
     * @param   fname   a filename.
     * @return  a guess as to what the content type of the object is,
     *          based upon its file name.
     * @see     java.net.URLConnection#getContentType()
     */
    public static String guessContentTypeFromName(String fname) {
        return getFileNameMap().getContentTypeFor(fname);
    }

    /**
     * Tries to determine the type of an input stream based on the
     * characters at the beginning of the input stream. This method can
     * be used by subclasses that override the
     * {@code getContentType} method.
     * <p>
     * Ideally, this routine would not be needed. But many
     * {@code http} servers return the incorrect content type; in
     * addition, there are many nonstandard extensions. Direct inspection
     * of the bytes to determine the content type is often more accurate
     * than believing the content type claimed by the {@code http} server.
     *
     * <p>
     *  尝试根据输入流开头处的字符确定输入流的类型。此方法可以由覆盖{@code getContentType}方法的子类使用。
     * <p>
     *  理想情况下,不需要这个例程。但许多{@code http}服务器返回不正确的内容类型;此外,还有很多非标准扩展。
     * 直接检查字节以确定内容类型通常比相信由{@code http}服务器声明的内容类型更准确。
     * 
     * 
     * @param      is   an input stream that supports marks.
     * @return     a guess at the content type, or {@code null} if none
     *             can be determined.
     * @exception  IOException  if an I/O error occurs while reading the
     *               input stream.
     * @see        java.io.InputStream#mark(int)
     * @see        java.io.InputStream#markSupported()
     * @see        java.net.URLConnection#getContentType()
     */
    static public String guessContentTypeFromStream(InputStream is)
                        throws IOException {
        // If we can't read ahead safely, just give up on guessing
        if (!is.markSupported())
            return null;

        is.mark(16);
        int c1 = is.read();
        int c2 = is.read();
        int c3 = is.read();
        int c4 = is.read();
        int c5 = is.read();
        int c6 = is.read();
        int c7 = is.read();
        int c8 = is.read();
        int c9 = is.read();
        int c10 = is.read();
        int c11 = is.read();
        int c12 = is.read();
        int c13 = is.read();
        int c14 = is.read();
        int c15 = is.read();
        int c16 = is.read();
        is.reset();

        if (c1 == 0xCA && c2 == 0xFE && c3 == 0xBA && c4 == 0xBE) {
            return "application/java-vm";
        }

        if (c1 == 0xAC && c2 == 0xED) {
            // next two bytes are version number, currently 0x00 0x05
            return "application/x-java-serialized-object";
        }

        if (c1 == '<') {
            if (c2 == '!'
                || ((c2 == 'h' && (c3 == 't' && c4 == 'm' && c5 == 'l' ||
                                   c3 == 'e' && c4 == 'a' && c5 == 'd') ||
                (c2 == 'b' && c3 == 'o' && c4 == 'd' && c5 == 'y'))) ||
                ((c2 == 'H' && (c3 == 'T' && c4 == 'M' && c5 == 'L' ||
                                c3 == 'E' && c4 == 'A' && c5 == 'D') ||
                (c2 == 'B' && c3 == 'O' && c4 == 'D' && c5 == 'Y')))) {
                return "text/html";
            }

            if (c2 == '?' && c3 == 'x' && c4 == 'm' && c5 == 'l' && c6 == ' ') {
                return "application/xml";
            }
        }

        // big and little (identical) endian UTF-8 encodings, with BOM
        if (c1 == 0xef &&  c2 == 0xbb &&  c3 == 0xbf) {
            if (c4 == '<' &&  c5 == '?' &&  c6 == 'x') {
                return "application/xml";
            }
        }

        // big and little endian UTF-16 encodings, with byte order mark
        if (c1 == 0xfe && c2 == 0xff) {
            if (c3 == 0 && c4 == '<' && c5 == 0 && c6 == '?' &&
                c7 == 0 && c8 == 'x') {
                return "application/xml";
            }
        }

        if (c1 == 0xff && c2 == 0xfe) {
            if (c3 == '<' && c4 == 0 && c5 == '?' && c6 == 0 &&
                c7 == 'x' && c8 == 0) {
                return "application/xml";
            }
        }

        // big and little endian UTF-32 encodings, with BOM
        if (c1 == 0x00 &&  c2 == 0x00 &&  c3 == 0xfe &&  c4 == 0xff) {
            if (c5  == 0 && c6  == 0 && c7  == 0 && c8  == '<' &&
                c9  == 0 && c10 == 0 && c11 == 0 && c12 == '?' &&
                c13 == 0 && c14 == 0 && c15 == 0 && c16 == 'x') {
                return "application/xml";
            }
        }

        if (c1 == 0xff &&  c2 == 0xfe &&  c3 == 0x00 &&  c4 == 0x00) {
            if (c5  == '<' && c6  == 0 && c7  == 0 && c8  == 0 &&
                c9  == '?' && c10 == 0 && c11 == 0 && c12 == 0 &&
                c13 == 'x' && c14 == 0 && c15 == 0 && c16 == 0) {
                return "application/xml";
            }
        }

        if (c1 == 'G' && c2 == 'I' && c3 == 'F' && c4 == '8') {
            return "image/gif";
        }

        if (c1 == '#' && c2 == 'd' && c3 == 'e' && c4 == 'f') {
            return "image/x-bitmap";
        }

        if (c1 == '!' && c2 == ' ' && c3 == 'X' && c4 == 'P' &&
                        c5 == 'M' && c6 == '2') {
            return "image/x-pixmap";
        }

        if (c1 == 137 && c2 == 80 && c3 == 78 &&
                c4 == 71 && c5 == 13 && c6 == 10 &&
                c7 == 26 && c8 == 10) {
            return "image/png";
        }

        if (c1 == 0xFF && c2 == 0xD8 && c3 == 0xFF) {
            if (c4 == 0xE0) {
                return "image/jpeg";
            }

            /**
             * File format used by digital cameras to store images.
             * Exif Format can be read by any application supporting
             * JPEG. Exif Spec can be found at:
             * http://www.pima.net/standards/it10/PIMA15740/Exif_2-1.PDF
             * <p>
             *  数码相机用于存储图像的文件格式。 Exif格式可以由任何支持JPEG的应用程序读取。
             *  Exif Spec可以在以下网址找到：http://www.pima.net/standards/it10/PIMA15740/Exif_2-1.PDF。
             * 
             */
            if ((c4 == 0xE1) &&
                (c7 == 'E' && c8 == 'x' && c9 == 'i' && c10 =='f' &&
                 c11 == 0)) {
                return "image/jpeg";
            }

            if (c4 == 0xEE) {
                return "image/jpg";
            }
        }

        if (c1 == 0xD0 && c2 == 0xCF && c3 == 0x11 && c4 == 0xE0 &&
            c5 == 0xA1 && c6 == 0xB1 && c7 == 0x1A && c8 == 0xE1) {

            /* Above is signature of Microsoft Structured Storage.
             * Below this, could have tests for various SS entities.
             * For now, just test for FlashPix.
             * <p>
             *  在这之下,可以测试各种SS实体。现在,只是测试FlashPix。
             * 
             */
            if (checkfpx(is)) {
                return "image/vnd.fpx";
            }
        }

        if (c1 == 0x2E && c2 == 0x73 && c3 == 0x6E && c4 == 0x64) {
            return "audio/basic";  // .au format, big endian
        }

        if (c1 == 0x64 && c2 == 0x6E && c3 == 0x73 && c4 == 0x2E) {
            return "audio/basic";  // .au format, little endian
        }

        if (c1 == 'R' && c2 == 'I' && c3 == 'F' && c4 == 'F') {
            /* I don't know if this is official but evidence
             * suggests that .wav files start with "RIFF" - brown
             * <p>
             *  建议.wav文件以"RIFF"开头 - 棕色
             * 
             */
            return "audio/x-wav";
        }
        return null;
    }

    /**
     * Check for FlashPix image data in InputStream is.  Return true if
     * the stream has FlashPix data, false otherwise.  Before calling this
     * method, the stream should have already been checked to be sure it
     * contains Microsoft Structured Storage data.
     * <p>
     *  检查InputStream中的FlashPix图像数据。如果流具有FlashPix数据,则返回true,否则返回false。
     * 在调用此方法之前,应该已经检查流以确保它包含Microsoft结构化存储数据。
     * 
     */
    static private boolean checkfpx(InputStream is) throws IOException {

        /* Test for FlashPix image data in Microsoft Structured Storage format.
         * In general, should do this with calls to an SS implementation.
         * Lacking that, need to dig via offsets to get to the FlashPix
         * ClassID.  Details:
         *
         * Offset to Fpx ClsID from beginning of stream should be:
         *
         * FpxClsidOffset = rootEntryOffset + clsidOffset
         *
         * where: clsidOffset = 0x50.
         *        rootEntryOffset = headerSize + sectorSize*sectDirStart
         *                          + 128*rootEntryDirectory
         *
         *        where:  headerSize = 0x200 (always)
         *                sectorSize = 2 raised to power of uSectorShift,
         *                             which is found in the header at
         *                             offset 0x1E.
         *                sectDirStart = found in the header at offset 0x30.
         *                rootEntryDirectory = in general, should search for
         *                                     directory labelled as root.
         *                                     We will assume value of 0 (i.e.,
         *                                     rootEntry is in first directory)
         * <p>
         *  一般来说,应该通过调用SS实现。缺少这一点,需要通过偏移挖掘到FlashPix ClassID。细节：
         * 
         * 偏移到Fpx ClsID从流的开始应该是：
         * 
         *  FpxClsidOffset = rootEntryOffset + clsidOffset
         * 
         *  其中：clsidOffset = 0x50。
         *  rootEntryOffset = headerSize + sectorSize * sectDirStart + 128 * rootEntryDirectory。
         * 
         *  其中：headerSize = 0x200(always)sectorSize = 2上升到uSectorShift的幂,它在偏移0x1E的头中找到。
         *  sectDirStart =在偏移0x30的标头中找到。 rootEntryDirectory =一般来说,应该搜索标记为root的目录。我们将假设值为0(即,rootEntry在第一个目录中)。
         * 
         */

        // Mark the stream so we can reset it. 0x100 is enough for the first
        // few reads, but the mark will have to be reset and set again once
        // the offset to the root directory entry is computed. That offset
        // can be very large and isn't know until the stream has been read from
        is.mark(0x100);

        // Get the byte ordering located at 0x1E. 0xFE is Intel,
        // 0xFF is other
        long toSkip = (long)0x1C;
        long posn;

        if ((posn = skipForward(is, toSkip)) < toSkip) {
          is.reset();
          return false;
        }

        int c[] = new int[16];
        if (readBytes(c, 2, is) < 0) {
            is.reset();
            return false;
        }

        int byteOrder = c[0];

        posn+=2;
        int uSectorShift;
        if (readBytes(c, 2, is) < 0) {
            is.reset();
            return false;
        }

        if(byteOrder == 0xFE) {
            uSectorShift = c[0];
            uSectorShift += c[1] << 8;
        }
        else {
            uSectorShift = c[0] << 8;
            uSectorShift += c[1];
        }

        posn += 2;
        toSkip = (long)0x30 - posn;
        long skipped = 0;
        if ((skipped = skipForward(is, toSkip)) < toSkip) {
          is.reset();
          return false;
        }
        posn += skipped;

        if (readBytes(c, 4, is) < 0) {
            is.reset();
            return false;
        }

        int sectDirStart;
        if(byteOrder == 0xFE) {
            sectDirStart = c[0];
            sectDirStart += c[1] << 8;
            sectDirStart += c[2] << 16;
            sectDirStart += c[3] << 24;
        } else {
            sectDirStart =  c[0] << 24;
            sectDirStart += c[1] << 16;
            sectDirStart += c[2] << 8;
            sectDirStart += c[3];
        }
        posn += 4;
        is.reset(); // Reset back to the beginning

        toSkip = 0x200L + (long)(1<<uSectorShift)*sectDirStart + 0x50L;

        // Sanity check!
        if (toSkip < 0) {
            return false;
        }

        /*
         * How far can we skip? Is there any performance problem here?
         * This skip can be fairly long, at least 0x4c650 in at least
         * one case. Have to assume that the skip will fit in an int.
         * Leave room to read whole root dir
         * <p>
         *  我们可以跳多远?这里有什么性能问题吗?这个跳过可能相当长,至少在0x4c650至少一个case。不得不假设跳过将适合int。留下阅读整根目录的空间
         * 
         */
        is.mark((int)toSkip+0x30);

        if ((skipForward(is, toSkip)) < toSkip) {
            is.reset();
            return false;
        }

        /* should be at beginning of ClassID, which is as follows
         * (in Intel byte order):
         *    00 67 61 56 54 C1 CE 11 85 53 00 AA 00 A1 F9 5B
         *
         * This is stored from Windows as long,short,short,char[8]
         * so for byte order changes, the order only changes for
         * the first 8 bytes in the ClassID.
         *
         * Test against this, ignoring second byte (Intel) since
         * this could change depending on part of Fpx file we have.
         * <p>
         *  (以Intel字节顺序)：00 67 61 56 54 C1 CE 11 85 53 00 AA 00 A1 F9 5B
         * 
         *  这是从Windows存储为long,short,short,char [8],所以对于字节顺序更改,顺序只改变ClassID的前8个字节。
         * 
         *  测试这个,忽略第二个字节(英特尔),因为这可能会改变取决于Fpx文件的一部分。
         * 
         */

        if (readBytes(c, 16, is) < 0) {
            is.reset();
            return false;
        }

        // intel byte order
        if (byteOrder == 0xFE &&
            c[0] == 0x00 && c[2] == 0x61 && c[3] == 0x56 &&
            c[4] == 0x54 && c[5] == 0xC1 && c[6] == 0xCE &&
            c[7] == 0x11 && c[8] == 0x85 && c[9] == 0x53 &&
            c[10]== 0x00 && c[11]== 0xAA && c[12]== 0x00 &&
            c[13]== 0xA1 && c[14]== 0xF9 && c[15]== 0x5B) {
            is.reset();
            return true;
        }

        // non-intel byte order
        else if (c[3] == 0x00 && c[1] == 0x61 && c[0] == 0x56 &&
            c[5] == 0x54 && c[4] == 0xC1 && c[7] == 0xCE &&
            c[6] == 0x11 && c[8] == 0x85 && c[9] == 0x53 &&
            c[10]== 0x00 && c[11]== 0xAA && c[12]== 0x00 &&
            c[13]== 0xA1 && c[14]== 0xF9 && c[15]== 0x5B) {
            is.reset();
            return true;
        }
        is.reset();
        return false;
    }

    /**
     * Tries to read the specified number of bytes from the stream
     * Returns -1, If EOF is reached before len bytes are read, returns 0
     * otherwise
     * <p>
     */
    static private int readBytes(int c[], int len, InputStream is)
                throws IOException {

        byte buf[] = new byte[len];
        if (is.read(buf, 0, len) < len) {
            return -1;
        }

        // fill the passed in int array
        for (int i = 0; i < len; i++) {
             c[i] = buf[i] & 0xff;
        }
        return 0;
    }


    /**
     * Skips through the specified number of bytes from the stream
     * until either EOF is reached, or the specified
     * number of bytes have been skipped
     * <p>
     *  尝试从流中读取指定数量的字节返回-1,如果在读取len字节之前达到EOF,则返回0,否则返回0
     * 
     */
    static private long skipForward(InputStream is, long toSkip)
                throws IOException {

        long eachSkip = 0;
        long skipped = 0;

        while (skipped != toSkip) {
            eachSkip = is.skip(toSkip - skipped);

            // check if EOF is reached
            if (eachSkip <= 0) {
                if (is.read() == -1) {
                    return skipped ;
                } else {
                    skipped++;
                }
            }
            skipped += eachSkip;
        }
        return skipped;
    }

}


class UnknownContentHandler extends ContentHandler {
    static final ContentHandler INSTANCE = new UnknownContentHandler();

    public Object getContent(URLConnection uc) throws IOException {
        return uc.getInputStream();
    }
}
