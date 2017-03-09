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
import java.util.Hashtable;
import java.util.StringTokenizer;
import sun.security.util.SecurityConstants;

/**
 * Class {@code URL} represents a Uniform Resource
 * Locator, a pointer to a "resource" on the World
 * Wide Web. A resource can be something as simple as a file or a
 * directory, or it can be a reference to a more complicated object,
 * such as a query to a database or to a search engine. More
 * information on the types of URLs and their formats can be found at:
 * <a href=
 * "http://web.archive.org/web/20051219043731/http://archive.ncsa.uiuc.edu/SDG/Software/Mosaic/Demo/url-primer.html">
 * <i>Types of URL</i></a>
 * <p>
 * In general, a URL can be broken into several parts. Consider the
 * following example:
 * <blockquote><pre>
 *     http://www.example.com/docs/resource1.html
 * </pre></blockquote>
 * <p>
 * The URL above indicates that the protocol to use is
 * {@code http} (HyperText Transfer Protocol) and that the
 * information resides on a host machine named
 * {@code www.example.com}. The information on that host
 * machine is named {@code /docs/resource1.html}. The exact
 * meaning of this name on the host machine is both protocol
 * dependent and host dependent. The information normally resides in
 * a file, but it could be generated on the fly. This component of
 * the URL is called the <i>path</i> component.
 * <p>
 * A URL can optionally specify a "port", which is the
 * port number to which the TCP connection is made on the remote host
 * machine. If the port is not specified, the default port for
 * the protocol is used instead. For example, the default port for
 * {@code http} is {@code 80}. An alternative port could be
 * specified as:
 * <blockquote><pre>
 *     http://www.example.com:1080/docs/resource1.html
 * </pre></blockquote>
 * <p>
 * The syntax of {@code URL} is defined by  <a
 * href="http://www.ietf.org/rfc/rfc2396.txt"><i>RFC&nbsp;2396: Uniform
 * Resource Identifiers (URI): Generic Syntax</i></a>, amended by <a
 * href="http://www.ietf.org/rfc/rfc2732.txt"><i>RFC&nbsp;2732: Format for
 * Literal IPv6 Addresses in URLs</i></a>. The Literal IPv6 address format
 * also supports scope_ids. The syntax and usage of scope_ids is described
 * <a href="Inet6Address.html#scoped">here</a>.
 * <p>
 * A URL may have appended to it a "fragment", also known
 * as a "ref" or a "reference". The fragment is indicated by the sharp
 * sign character "#" followed by more characters. For example,
 * <blockquote><pre>
 *     http://java.sun.com/index.html#chapter1
 * </pre></blockquote>
 * <p>
 * This fragment is not technically part of the URL. Rather, it
 * indicates that after the specified resource is retrieved, the
 * application is specifically interested in that part of the
 * document that has the tag {@code chapter1} attached to it. The
 * meaning of a tag is resource specific.
 * <p>
 * An application can also specify a "relative URL",
 * which contains only enough information to reach the resource
 * relative to another URL. Relative URLs are frequently used within
 * HTML pages. For example, if the contents of the URL:
 * <blockquote><pre>
 *     http://java.sun.com/index.html
 * </pre></blockquote>
 * contained within it the relative URL:
 * <blockquote><pre>
 *     FAQ.html
 * </pre></blockquote>
 * it would be a shorthand for:
 * <blockquote><pre>
 *     http://java.sun.com/FAQ.html
 * </pre></blockquote>
 * <p>
 * The relative URL need not specify all the components of a URL. If
 * the protocol, host name, or port number is missing, the value is
 * inherited from the fully specified URL. The file component must be
 * specified. The optional fragment is not inherited.
 * <p>
 * The URL class does not itself encode or decode any URL components
 * according to the escaping mechanism defined in RFC2396. It is the
 * responsibility of the caller to encode any fields, which need to be
 * escaped prior to calling URL, and also to decode any escaped fields,
 * that are returned from URL. Furthermore, because URL has no knowledge
 * of URL escaping, it does not recognise equivalence between the encoded
 * or decoded form of the same URL. For example, the two URLs:<br>
 * <pre>    http://foo.com/hello world/ and http://foo.com/hello%20world</pre>
 * would be considered not equal to each other.
 * <p>
 * Note, the {@link java.net.URI} class does perform escaping of its
 * component fields in certain circumstances. The recommended way
 * to manage the encoding and decoding of URLs is to use {@link java.net.URI},
 * and to convert between these two classes using {@link #toURI()} and
 * {@link URI#toURL()}.
 * <p>
 * The {@link URLEncoder} and {@link URLDecoder} classes can also be
 * used, but only for HTML form encoding, which is not the same
 * as the encoding scheme defined in RFC2396.
 *
 * <p>
 *  类{@code URL}表示统一资源定位符,指向万维网上的"资源"的指针。资源可以是简单的文件或目录,或者它可以是对更复杂的对象的引用,例如对数据库或搜索引擎的查询。
 * 有关网址类型及其格式的详细信息,请访问：<a href =。
 * "http://web.archive.org/web/20051219043731/http://archive.ncsa.uiuc.edu/SDG/Software/Mosaic/Demo/url-primer.html">
 *  <i> URL的类型</i> </a>
 * <p>
 *  一般来说,一个URL可以分成几个部分。
 * 请考虑以下示例：<blockquote> <pre> http://www.example.com/docs/resource1.html </pre> </blockquote>。
 * <p>
 *  上面的URL表示要使用的协议是{@code http}(超文本传输​​协议),并且信息驻留在名为{@code www.example.com}的主机上。
 * 该主机上的信息被命名为{@code /docs/resource1.html}。此名称在主机上的确切含义是依赖于协议和依赖于主机。该信息通常驻留在文件中,但它可以即时生成。
 *  URL的这个组件称为<i>路径</i>组件。
 * <p>
 * URL可以可选地指定"端口",其是在远程主机上进行TCP连接的端口号。如果未指定端口,将使用协议的默认端口。例如,{@code http}的默认端口是{@code 80}。
 * 另一个端口可以指定为：<blockquote> <pre> http://www.example.com:1080/docs/resource1.html </pre> </blockquote>。
 * <p>
 *  {@code URL}的语法由<a href="http://www.ietf.org/rfc/rfc2396.txt"> <i> RFC 2396：统一资源标识符(URI)定义：通用语法< / i>
 *  </a>,由<a href="http://www.ietf.org/rfc/rfc2732.txt"> <i> RFC 2732：URL中的字面IPv6地址格式</i> </a>。
 * 字面IPv6地址格式还支持scope_ids。 <a href="Inet6Address.html#scoped">此处</a>介绍了scope_ids的语法和用法。
 * <p>
 *  URL可以附加有"片段",也称为"ref"或"引用"。片段由尖括号字符"#"指示,后跟更多字符。
 * 例如,<blockquote> <pre> http://java.sun.com/index.html#chapter1 </pre> </blockquote>。
 * <p>
 *  此片段在技术上不是URL的一部分。相反,它指示在检索到指定资源之后,应用程序特别感兴趣于具有标签{@code chapter1}的文档的那部分。标签的含义是资源特定的。
 * <p>
 * 应用程序还可以指定"相对URL",其中仅包含足够的信息以相对于另一个URL访问资源。相对URL经常在HTML页面中使用。
 * 例如,如果URL的内容：<blockquote> <pre> http://java.sun.com/index.html </pre> </blockquote>中包含相对URL：<blockquote>
 *  <pre> FAQ.html </pre> </blockquote>这将是以下缩写：<blockquote> <pre> http://java.sun.com/FAQ.html </pre> </blockquote>
 * 。
 * 应用程序还可以指定"相对URL",其中仅包含足够的信息以相对于另一个URL访问资源。相对URL经常在HTML页面中使用。
 * <p>
 *  相对URL不需要指定URL的所有组件。如果协议,主机名或端口号缺失,则该值从完全指定的URL继承。必须指定文件组件。可选片段不是继承的。
 * <p>
 *  URL类本身不根据RFC2396中定义的转义机制编码或解码任何URL组件。调用者负责对任何字段进行编码,这些字段需要在调用URL之前进行转义,并对从URL返回的任何转义字段进行解码。
 * 此外,因为URL不知道URL转义,所以它不识别同一URL的编码或解码形式之间的等同性。
 * 例如,两个网址：<br> <pre> http://foo.com/hello world /和http://foo.com/hello%20world </pre>将被视为彼此不相等。
 * <p>
 * 注意,在某些情况下,{@link java.net.URI}类会执行其组件字段的转义。
 * 建议您管理网址的编码和解码方式是使用{@link java.net.URI},并使用{@link #toURI()}和{@link URI#toURL()}在这两个类别之间进行转换。
 * <p>
 *  也可以使用{@link URLEncoder}和{@link URLDecoder}类,但仅适用于HTML表单编码,这与RFC2396中定义的编码方案不同。
 * 
 * 
 * @author  James Gosling
 * @since JDK1.0
 */
public final class URL implements java.io.Serializable {

    static final long serialVersionUID = -7627629688361524110L;

    /**
     * The property which specifies the package prefix list to be scanned
     * for protocol handlers.  The value of this property (if any) should
     * be a vertical bar delimited list of package names to search through
     * for a protocol handler to load.  The policy of this class is that
     * all protocol handlers will be in a class called <protocolname>.Handler,
     * and each package in the list is examined in turn for a matching
     * handler.  If none are found (or the property is not specified), the
     * default package prefix, sun.net.www.protocol, is used.  The search
     * proceeds from the first package in the list to the last and stops
     * when a match is found.
     * <p>
     *  指定要扫描的协议处理程序的包前缀列表的属性。此属性的值(如果有)应为要搜索的协议处理程序加载的包名称的垂直条形分隔列表。
     * 这个类的策略是所有协议处理程序将在一个名为<protocolname> .Handler的类中,并且列表中的每个包依次检查匹配处理程序。
     * 如果找不到(或未指定属性),则使用缺省包前缀sun.net.www.protocol。搜索从列表中的第一个包到最后一个,并在找到匹配时停止。
     * 
     */
    private static final String protocolPathProp = "java.protocol.handler.pkgs";

    /**
     * The protocol to use (ftp, http, nntp, ... etc.) .
     * <p>
     *  要使用的协议(ftp,http,nntp,...等)。
     * 
     * 
     * @serial
     */
    private String protocol;

    /**
     * The host name to connect to.
     * <p>
     *  要连接的主机名。
     * 
     * 
     * @serial
     */
    private String host;

    /**
     * The protocol port to connect to.
     * <p>
     *  要连接的协议端口。
     * 
     * 
     * @serial
     */
    private int port = -1;

    /**
     * The specified file name on that host. {@code file} is
     * defined as {@code path[?query]}
     * <p>
     *  该主机上指定的文件名。 {@code file}定义为{@code path [?query]}
     * 
     * 
     * @serial
     */
    private String file;

    /**
     * The query part of this URL.
     * <p>
     *  此URL的查询部分。
     * 
     */
    private transient String query;

    /**
     * The authority part of this URL.
     * <p>
     *  此网址的权限部分。
     * 
     * 
     * @serial
     */
    private String authority;

    /**
     * The path part of this URL.
     * <p>
     *  此URL的路径部分。
     * 
     */
    private transient String path;

    /**
     * The userinfo part of this URL.
     * <p>
     *  此URL的userinfo部分。
     * 
     */
    private transient String userInfo;

    /**
     * # reference.
     * <p>
     *  #引用。
     * 
     * 
     * @serial
     */
    private String ref;

    /**
     * The host's IP address, used in equals and hashCode.
     * Computed on demand. An uninitialized or unknown hostAddress is null.
     * <p>
     * 主机的IP地址,用于equals和hashCode。按需计算。未初始化或未知的hostAddress为null。
     * 
     */
    transient InetAddress hostAddress;

    /**
     * The URLStreamHandler for this URL.
     * <p>
     *  此URL的URLStreamHandler。
     * 
     */
    transient URLStreamHandler handler;

    /* Our hash code.
    /* <p>
    /* 
     * @serial
     */
    private int hashCode = -1;

    /**
     * Creates a {@code URL} object from the specified
     * {@code protocol}, {@code host}, {@code port}
     * number, and {@code file}.<p>
     *
     * {@code host} can be expressed as a host name or a literal
     * IP address. If IPv6 literal address is used, it should be
     * enclosed in square brackets ({@code '['} and {@code ']'}), as
     * specified by <a
     * href="http://www.ietf.org/rfc/rfc2732.txt">RFC&nbsp;2732</a>;
     * However, the literal IPv6 address format defined in <a
     * href="http://www.ietf.org/rfc/rfc2373.txt"><i>RFC&nbsp;2373: IP
     * Version 6 Addressing Architecture</i></a> is also accepted.<p>
     *
     * Specifying a {@code port} number of {@code -1}
     * indicates that the URL should use the default port for the
     * protocol.<p>
     *
     * If this is the first URL object being created with the specified
     * protocol, a <i>stream protocol handler</i> object, an instance of
     * class {@code URLStreamHandler}, is created for that protocol:
     * <ol>
     * <li>If the application has previously set up an instance of
     *     {@code URLStreamHandlerFactory} as the stream handler factory,
     *     then the {@code createURLStreamHandler} method of that instance
     *     is called with the protocol string as an argument to create the
     *     stream protocol handler.
     * <li>If no {@code URLStreamHandlerFactory} has yet been set up,
     *     or if the factory's {@code createURLStreamHandler} method
     *     returns {@code null}, then the constructor finds the
     *     value of the system property:
     *     <blockquote><pre>
     *         java.protocol.handler.pkgs
     *     </pre></blockquote>
     *     If the value of that system property is not {@code null},
     *     it is interpreted as a list of packages separated by a vertical
     *     slash character '{@code |}'. The constructor tries to load
     *     the class named:
     *     <blockquote><pre>
     *         &lt;<i>package</i>&gt;.&lt;<i>protocol</i>&gt;.Handler
     *     </pre></blockquote>
     *     where &lt;<i>package</i>&gt; is replaced by the name of the package
     *     and &lt;<i>protocol</i>&gt; is replaced by the name of the protocol.
     *     If this class does not exist, or if the class exists but it is not
     *     a subclass of {@code URLStreamHandler}, then the next package
     *     in the list is tried.
     * <li>If the previous step fails to find a protocol handler, then the
     *     constructor tries to load from a system default package.
     *     <blockquote><pre>
     *         &lt;<i>system default package</i>&gt;.&lt;<i>protocol</i>&gt;.Handler
     *     </pre></blockquote>
     *     If this class does not exist, or if the class exists but it is not a
     *     subclass of {@code URLStreamHandler}, then a
     *     {@code MalformedURLException} is thrown.
     * </ol>
     *
     * <p>Protocol handlers for the following protocols are guaranteed
     * to exist on the search path :-
     * <blockquote><pre>
     *     http, https, file, and jar
     * </pre></blockquote>
     * Protocol handlers for additional protocols may also be
     * available.
     *
     * <p>No validation of the inputs is performed by this constructor.
     *
     * <p>
     *  从指定的{@code协议},{@code主机},{@code端口}号码和{@code file}中创建{@code URL}对象。<p>
     * 
     *  {@code host}可以表示为主机名或文字IP地址。
     * 如果使用IPv6字面值地址,它应该用方括号({@code'['}和{@code']'}括起来,如<a href ="http://www.ietf.org/ rfc / rfc2732.txt"> RF
     * C&nbsp; 2732 </a>;但是,在<a href="http://www.ietf.org/rfc/rfc2373.txt"> <i> RFC 2373：IP版本6寻址体系结构</i>中定义的
     * 字面IPv6地址格式</a >也被接受。
     *  {@code host}可以表示为主机名或文字IP地址。<p>。
     * 
     *  指定{@code port} {@code -1}的数字表示该网址应使用协议的默认端口。<p>
     * 
     *  如果这是使用指定协议创建的第一个URL对象,则会为该协议创建一个流协议处理程序</i>对象,类{@code URLStreamHandler}的一个实例：
     * <ol>
     * <li>如果应用程序先前已将{@code URLStreamHandlerFactory}的实例设置为流处理程序工厂,则会使用协议字符串作为参数调用该实例的{@code createURLStreamHandler}
     * 方法来创建流协议处理程序。
     *  <li>如果尚未设置{@code URLStreamHandlerFactory},或者工厂的{@code createURLStreamHandler}方法返回{@code null},则构造函数会
     * 找到系统属性的值：<blockquote> <pre> java.protocol.handler.pkgs </pre> </blockquote>如果该系统属性的值不是{@code null},它将
     * 被解释为由垂直斜杠字符"{@code |}"分隔的包列表'。
     * 构造函数尝试加载名为<block> </i>> </i>&gt;的</b> </i>其中&lt; i&gt;包&lt; i&gt;由包的名称和&lt; i&gt;协议&lt; / i&gt;被协议的名称
     * 替换。
     * 如果这个类不存在,或者该类存在,但它不是{@code URLStreamHandler}的子类,那么将尝试列表中的下一个包。
     *  <li>如果上一步骤未能找到协议处理程序,则构造函数将尝试从系统默认程序包加载。
     *  <block> </>> </block> </block>如果此类不存在,则</b>或者如果类存在,但它不是{@code URLStreamHandler}的子类,则会抛出{@code MalformedURLException}
     * 。
     *  <li>如果上一步骤未能找到协议处理程序,则构造函数将尝试从系统默认程序包加载。
     * </ol>
     * 
     * <p>以下协议的协议处理程序保证存在于搜索路径上： -  <blockquote> <pre> http,https,file和jar </pre> </blockquote> 。
     * 
     *  <p>此构造函数不会验证输入。
     * 
     * 
     * @param      protocol   the name of the protocol to use.
     * @param      host       the name of the host.
     * @param      port       the port number on the host.
     * @param      file       the file on the host
     * @exception  MalformedURLException  if an unknown protocol is specified.
     * @see        java.lang.System#getProperty(java.lang.String)
     * @see        java.net.URL#setURLStreamHandlerFactory(
     *                  java.net.URLStreamHandlerFactory)
     * @see        java.net.URLStreamHandler
     * @see        java.net.URLStreamHandlerFactory#createURLStreamHandler(
     *                  java.lang.String)
     */
    public URL(String protocol, String host, int port, String file)
        throws MalformedURLException
    {
        this(protocol, host, port, file, null);
    }

    /**
     * Creates a URL from the specified {@code protocol}
     * name, {@code host} name, and {@code file} name. The
     * default port for the specified protocol is used.
     * <p>
     * This method is equivalent to calling the four-argument
     * constructor with the arguments being {@code protocol},
     * {@code host}, {@code -1}, and {@code file}.
     *
     * No validation of the inputs is performed by this constructor.
     *
     * <p>
     *  根据指定的{@code protocol}名称,{@code host}名称和{@code file}名称创建网址。使用指定协议的默认端口。
     * <p>
     *  此方法等效于调用参数为{@code protocol},{@code host},{@code -1}和{@code file}的四参数构造函数。
     * 
     *  此构造函数不执行输入验证。
     * 
     * 
     * @param      protocol   the name of the protocol to use.
     * @param      host       the name of the host.
     * @param      file       the file on the host.
     * @exception  MalformedURLException  if an unknown protocol is specified.
     * @see        java.net.URL#URL(java.lang.String, java.lang.String,
     *                  int, java.lang.String)
     */
    public URL(String protocol, String host, String file)
            throws MalformedURLException {
        this(protocol, host, -1, file);
    }

    /**
     * Creates a {@code URL} object from the specified
     * {@code protocol}, {@code host}, {@code port}
     * number, {@code file}, and {@code handler}. Specifying
     * a {@code port} number of {@code -1} indicates that
     * the URL should use the default port for the protocol. Specifying
     * a {@code handler} of {@code null} indicates that the URL
     * should use a default stream handler for the protocol, as outlined
     * for:
     *     java.net.URL#URL(java.lang.String, java.lang.String, int,
     *                      java.lang.String)
     *
     * <p>If the handler is not null and there is a security manager,
     * the security manager's {@code checkPermission}
     * method is called with a
     * {@code NetPermission("specifyStreamHandler")} permission.
     * This may result in a SecurityException.
     *
     * No validation of the inputs is performed by this constructor.
     *
     * <p>
     *  从指定的{@code protocol},{@code host},{@code port} number,{@code file}和{@code handler}中创建{@code URL}对象。
     * 指定{@code port} {@code -1}的数字表示该URL应使用协议的默认端口。
     * 指定{@code null}的{@code handler}表示该URL应使用协议的默认流处理程序,如下所示：java.net.URL#URL(java.lang.String,java.lang.St
     * ring ,int,java.lang.String)。
     * 指定{@code port} {@code -1}的数字表示该URL应使用协议的默认端口。
     * 
     *  <p>如果处理程序不为空并且有安全管理器,则会使用{@code NetPermission("specifyStreamHandler")}权限调用安全管理器的{@code checkPermission}
     * 方法。
     * 这可能导致SecurityException。
     * 
     *  此构造函数不执行输入验证。
     * 
     * 
     * @param      protocol   the name of the protocol to use.
     * @param      host       the name of the host.
     * @param      port       the port number on the host.
     * @param      file       the file on the host
     * @param      handler    the stream handler for the URL.
     * @exception  MalformedURLException  if an unknown protocol is specified.
     * @exception  SecurityException
     *        if a security manager exists and its
     *        {@code checkPermission} method doesn't allow
     *        specifying a stream handler explicitly.
     * @see        java.lang.System#getProperty(java.lang.String)
     * @see        java.net.URL#setURLStreamHandlerFactory(
     *                  java.net.URLStreamHandlerFactory)
     * @see        java.net.URLStreamHandler
     * @see        java.net.URLStreamHandlerFactory#createURLStreamHandler(
     *                  java.lang.String)
     * @see        SecurityManager#checkPermission
     * @see        java.net.NetPermission
     */
    public URL(String protocol, String host, int port, String file,
               URLStreamHandler handler) throws MalformedURLException {
        if (handler != null) {
            SecurityManager sm = System.getSecurityManager();
            if (sm != null) {
                // check for permission to specify a handler
                checkSpecifyHandler(sm);
            }
        }

        protocol = protocol.toLowerCase();
        this.protocol = protocol;
        if (host != null) {

            /**
             * if host is a literal IPv6 address,
             * we will make it conform to RFC 2732
             * <p>
             *  如果主机是字面IPv6地址,我们将使其符合RFC 2732
             * 
             */
            if (host.indexOf(':') >= 0 && !host.startsWith("[")) {
                host = "["+host+"]";
            }
            this.host = host;

            if (port < -1) {
                throw new MalformedURLException("Invalid port number :" +
                                                    port);
            }
            this.port = port;
            authority = (port == -1) ? host : host + ":" + port;
        }

        Parts parts = new Parts(file);
        path = parts.getPath();
        query = parts.getQuery();

        if (query != null) {
            this.file = path + "?" + query;
        } else {
            this.file = path;
        }
        ref = parts.getRef();

        // Note: we don't do validation of the URL here. Too risky to change
        // right now, but worth considering for future reference. -br
        if (handler == null &&
            (handler = getURLStreamHandler(protocol)) == null) {
            throw new MalformedURLException("unknown protocol: " + protocol);
        }
        this.handler = handler;
    }

    /**
     * Creates a {@code URL} object from the {@code String}
     * representation.
     * <p>
     * This constructor is equivalent to a call to the two-argument
     * constructor with a {@code null} first argument.
     *
     * <p>
     * 从{@code String}表示中创建{@code URL}对象。
     * <p>
     *  这个构造函数相当于调用带有{@code null}第一个参数的双参数构造函数。
     * 
     * 
     * @param      spec   the {@code String} to parse as a URL.
     * @exception  MalformedURLException  if no protocol is specified, or an
     *               unknown protocol is found, or {@code spec} is {@code null}.
     * @see        java.net.URL#URL(java.net.URL, java.lang.String)
     */
    public URL(String spec) throws MalformedURLException {
        this(null, spec);
    }

    /**
     * Creates a URL by parsing the given spec within a specified context.
     *
     * The new URL is created from the given context URL and the spec
     * argument as described in
     * RFC2396 &quot;Uniform Resource Identifiers : Generic * Syntax&quot; :
     * <blockquote><pre>
     *          &lt;scheme&gt;://&lt;authority&gt;&lt;path&gt;?&lt;query&gt;#&lt;fragment&gt;
     * </pre></blockquote>
     * The reference is parsed into the scheme, authority, path, query and
     * fragment parts. If the path component is empty and the scheme,
     * authority, and query components are undefined, then the new URL is a
     * reference to the current document. Otherwise, the fragment and query
     * parts present in the spec are used in the new URL.
     * <p>
     * If the scheme component is defined in the given spec and does not match
     * the scheme of the context, then the new URL is created as an absolute
     * URL based on the spec alone. Otherwise the scheme component is inherited
     * from the context URL.
     * <p>
     * If the authority component is present in the spec then the spec is
     * treated as absolute and the spec authority and path will replace the
     * context authority and path. If the authority component is absent in the
     * spec then the authority of the new URL will be inherited from the
     * context.
     * <p>
     * If the spec's path component begins with a slash character
     * &quot;/&quot; then the
     * path is treated as absolute and the spec path replaces the context path.
     * <p>
     * Otherwise, the path is treated as a relative path and is appended to the
     * context path, as described in RFC2396. Also, in this case,
     * the path is canonicalized through the removal of directory
     * changes made by occurrences of &quot;..&quot; and &quot;.&quot;.
     * <p>
     * For a more detailed description of URL parsing, refer to RFC2396.
     *
     * <p>
     *  通过解析指定上下文中给定的规范来创建URL。
     * 
     *  新URL是从给定上下文URL和spec参数创建的,如RFC2396"Uniform Resource Identifiers：Generic * Syntax"中所述。
     *  ：<blockquote> <pre>&lt; scheme&gt;：//&lt; authority&gt;&lt; path&gt;?&lt; query&gt;#& </pre> </blockquote>
     * 引用被解析为方案,权限,路径,查询和片段部分。
     *  新URL是从给定上下文URL和spec参数创建的,如RFC2396"Uniform Resource Identifiers：Generic * Syntax"中所述。
     * 如果路径组件为空,并且方案,权限和查询组件未定义,则新URL是对当前文档的引用。否则,规范中出现的片段和查询部分将在新的URL中使用。
     * <p>
     *  如果方案组件在给定规范中定义并且与上下文的方案不匹配,则新URL将基于规范单独创建为绝对URL。否则,方案组件从上下文URL继承。
     * <p>
     *  如果权限组件存在于规范中,则规范被视为绝对的,并且规范权限和路径将替换上下文权限和路径。如果规范组件在spec中不存在,则新URL的权限将从上下文继承。
     * <p>
     * 如果规范的路径分量以斜杠字符"/"开始,那么该路径被视为绝对路径,spec路径替换上下文路径。
     * <p>
     *  否则,该路径将被视为相对路径,并附加到上下文路径,如RFC2396中所述。此外,在这种情况下,通过删除由".."的出现所做的目录改变来规范路径。和"。"。
     * <p>
     *  有关URL解析的更详细的描述,请参阅RFC2396。
     * 
     * 
     * @param      context   the context in which to parse the specification.
     * @param      spec      the {@code String} to parse as a URL.
     * @exception  MalformedURLException  if no protocol is specified, or an
     *               unknown protocol is found, or {@code spec} is {@code null}.
     * @see        java.net.URL#URL(java.lang.String, java.lang.String,
     *                  int, java.lang.String)
     * @see        java.net.URLStreamHandler
     * @see        java.net.URLStreamHandler#parseURL(java.net.URL,
     *                  java.lang.String, int, int)
     */
    public URL(URL context, String spec) throws MalformedURLException {
        this(context, spec, null);
    }

    /**
     * Creates a URL by parsing the given spec with the specified handler
     * within a specified context. If the handler is null, the parsing
     * occurs as with the two argument constructor.
     *
     * <p>
     *  通过使用指定上下文中指定的处理程序解析给定规范来创建URL。如果处理程序为null,则解析与两参数构造函数一样发生。
     * 
     * 
     * @param      context   the context in which to parse the specification.
     * @param      spec      the {@code String} to parse as a URL.
     * @param      handler   the stream handler for the URL.
     * @exception  MalformedURLException  if no protocol is specified, or an
     *               unknown protocol is found, or {@code spec} is {@code null}.
     * @exception  SecurityException
     *        if a security manager exists and its
     *        {@code checkPermission} method doesn't allow
     *        specifying a stream handler.
     * @see        java.net.URL#URL(java.lang.String, java.lang.String,
     *                  int, java.lang.String)
     * @see        java.net.URLStreamHandler
     * @see        java.net.URLStreamHandler#parseURL(java.net.URL,
     *                  java.lang.String, int, int)
     */
    public URL(URL context, String spec, URLStreamHandler handler)
        throws MalformedURLException
    {
        String original = spec;
        int i, limit, c;
        int start = 0;
        String newProtocol = null;
        boolean aRef=false;
        boolean isRelative = false;

        // Check for permission to specify a handler
        if (handler != null) {
            SecurityManager sm = System.getSecurityManager();
            if (sm != null) {
                checkSpecifyHandler(sm);
            }
        }

        try {
            limit = spec.length();
            while ((limit > 0) && (spec.charAt(limit - 1) <= ' ')) {
                limit--;        //eliminate trailing whitespace
            }
            while ((start < limit) && (spec.charAt(start) <= ' ')) {
                start++;        // eliminate leading whitespace
            }

            if (spec.regionMatches(true, start, "url:", 0, 4)) {
                start += 4;
            }
            if (start < spec.length() && spec.charAt(start) == '#') {
                /* we're assuming this is a ref relative to the context URL.
                 * This means protocols cannot start w/ '#', but we must parse
                 * ref URL's like: "hello:there" w/ a ':' in them.
                 * <p>
                 *  这意味着协议不能启动w /'#',但我们必须解析ref URL的像："hello：there"w / a'：'。
                 * 
                 */
                aRef=true;
            }
            for (i = start ; !aRef && (i < limit) &&
                     ((c = spec.charAt(i)) != '/') ; i++) {
                if (c == ':') {

                    String s = spec.substring(start, i).toLowerCase();
                    if (isValidProtocol(s)) {
                        newProtocol = s;
                        start = i + 1;
                    }
                    break;
                }
            }

            // Only use our context if the protocols match.
            protocol = newProtocol;
            if ((context != null) && ((newProtocol == null) ||
                            newProtocol.equalsIgnoreCase(context.protocol))) {
                // inherit the protocol handler from the context
                // if not specified to the constructor
                if (handler == null) {
                    handler = context.handler;
                }

                // If the context is a hierarchical URL scheme and the spec
                // contains a matching scheme then maintain backwards
                // compatibility and treat it as if the spec didn't contain
                // the scheme; see 5.2.3 of RFC2396
                if (context.path != null && context.path.startsWith("/"))
                    newProtocol = null;

                if (newProtocol == null) {
                    protocol = context.protocol;
                    authority = context.authority;
                    userInfo = context.userInfo;
                    host = context.host;
                    port = context.port;
                    file = context.file;
                    path = context.path;
                    isRelative = true;
                }
            }

            if (protocol == null) {
                throw new MalformedURLException("no protocol: "+original);
            }

            // Get the protocol handler if not specified or the protocol
            // of the context could not be used
            if (handler == null &&
                (handler = getURLStreamHandler(protocol)) == null) {
                throw new MalformedURLException("unknown protocol: "+protocol);
            }

            this.handler = handler;

            i = spec.indexOf('#', start);
            if (i >= 0) {
                ref = spec.substring(i + 1, limit);
                limit = i;
            }

            /*
             * Handle special case inheritance of query and fragment
             * implied by RFC2396 section 5.2.2.
             * <p>
             *  处理RFC2396第5.2.2节中隐含的查询和片段的特殊情况继承。
             * 
             */
            if (isRelative && start == limit) {
                query = context.query;
                if (ref == null) {
                    ref = context.ref;
                }
            }

            handler.parseURL(this, spec, start, limit);

        } catch(MalformedURLException e) {
            throw e;
        } catch(Exception e) {
            MalformedURLException exception = new MalformedURLException(e.getMessage());
            exception.initCause(e);
            throw exception;
        }
    }

    /*
     * Returns true if specified string is a valid protocol name.
     * <p>
     *  如果指定的字符串是有效的协议名称,则返回true。
     * 
     */
    private boolean isValidProtocol(String protocol) {
        int len = protocol.length();
        if (len < 1)
            return false;
        char c = protocol.charAt(0);
        if (!Character.isLetter(c))
            return false;
        for (int i = 1; i < len; i++) {
            c = protocol.charAt(i);
            if (!Character.isLetterOrDigit(c) && c != '.' && c != '+' &&
                c != '-') {
                return false;
            }
        }
        return true;
    }

    /*
     * Checks for permission to specify a stream handler.
     * <p>
     *  检查是否有指定流处理程序的权限。
     * 
     */
    private void checkSpecifyHandler(SecurityManager sm) {
        sm.checkPermission(SecurityConstants.SPECIFY_HANDLER_PERMISSION);
    }

    /**
     * Sets the fields of the URL. This is not a public method so that
     * only URLStreamHandlers can modify URL fields. URLs are
     * otherwise constant.
     *
     * <p>
     *  设置URL的字段。这不是一个公共方法,因此只有URLStreamHandlers可以修改URL字段。 URL否则是不变的。
     * 
     * 
     * @param protocol the name of the protocol to use
     * @param host the name of the host
       @param port the port number on the host
     * @param file the file on the host
     * @param ref the internal reference in the URL
     */
    void set(String protocol, String host, int port,
             String file, String ref) {
        synchronized (this) {
            this.protocol = protocol;
            this.host = host;
            authority = port == -1 ? host : host + ":" + port;
            this.port = port;
            this.file = file;
            this.ref = ref;
            /* This is very important. We must recompute this after the
            /* <p>
            /* 
             * URL has been changed. */
            hashCode = -1;
            hostAddress = null;
            int q = file.lastIndexOf('?');
            if (q != -1) {
                query = file.substring(q+1);
                path = file.substring(0, q);
            } else
                path = file;
        }
    }

    /**
     * Sets the specified 8 fields of the URL. This is not a public method so
     * that only URLStreamHandlers can modify URL fields. URLs are otherwise
     * constant.
     *
     * <p>
     *  设置URL的指定的8个字段。这不是一个公共方法,因此只有URLStreamHandlers可以修改URL字段。 URL否则是不变的。
     * 
     * 
     * @param protocol the name of the protocol to use
     * @param host the name of the host
     * @param port the port number on the host
     * @param authority the authority part for the url
     * @param userInfo the username and password
     * @param path the file on the host
     * @param ref the internal reference in the URL
     * @param query the query part of this URL
     * @since 1.3
     */
    void set(String protocol, String host, int port,
             String authority, String userInfo, String path,
             String query, String ref) {
        synchronized (this) {
            this.protocol = protocol;
            this.host = host;
            this.port = port;
            this.file = query == null ? path : path + "?" + query;
            this.userInfo = userInfo;
            this.path = path;
            this.ref = ref;
            /* This is very important. We must recompute this after the
            /* <p>
            /* 
             * URL has been changed. */
            hashCode = -1;
            hostAddress = null;
            this.query = query;
            this.authority = authority;
        }
    }

    /**
     * Gets the query part of this {@code URL}.
     *
     * <p>
     *  获取此{@code URL}的查询部分。
     * 
     * 
     * @return  the query part of this {@code URL},
     * or <CODE>null</CODE> if one does not exist
     * @since 1.3
     */
    public String getQuery() {
        return query;
    }

    /**
     * Gets the path part of this {@code URL}.
     *
     * <p>
     *  获取此{@code URL}的路径部分。
     * 
     * 
     * @return  the path part of this {@code URL}, or an
     * empty string if one does not exist
     * @since 1.3
     */
    public String getPath() {
        return path;
    }

    /**
     * Gets the userInfo part of this {@code URL}.
     *
     * <p>
     *  获取此{@code URL}的userInfo部分。
     * 
     * 
     * @return  the userInfo part of this {@code URL}, or
     * <CODE>null</CODE> if one does not exist
     * @since 1.3
     */
    public String getUserInfo() {
        return userInfo;
    }

    /**
     * Gets the authority part of this {@code URL}.
     *
     * <p>
     *  获取此{@code URL}的权限部分。
     * 
     * 
     * @return  the authority part of this {@code URL}
     * @since 1.3
     */
    public String getAuthority() {
        return authority;
    }

    /**
     * Gets the port number of this {@code URL}.
     *
     * <p>
     *  获取此{@code URL}的端口号。
     * 
     * 
     * @return  the port number, or -1 if the port is not set
     */
    public int getPort() {
        return port;
    }

    /**
     * Gets the default port number of the protocol associated
     * with this {@code URL}. If the URL scheme or the URLStreamHandler
     * for the URL do not define a default port number,
     * then -1 is returned.
     *
     * <p>
     * 获取与此{@code URL}相关联的协议的默认端口号。如果URL方案或URL的URLStreamHandler未定义默认端口号,则返回-1。
     * 
     * 
     * @return  the port number
     * @since 1.4
     */
    public int getDefaultPort() {
        return handler.getDefaultPort();
    }

    /**
     * Gets the protocol name of this {@code URL}.
     *
     * <p>
     *  获取此{@code URL}的协议名称。
     * 
     * 
     * @return  the protocol of this {@code URL}.
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * Gets the host name of this {@code URL}, if applicable.
     * The format of the host conforms to RFC 2732, i.e. for a
     * literal IPv6 address, this method will return the IPv6 address
     * enclosed in square brackets ({@code '['} and {@code ']'}).
     *
     * <p>
     *  获取此{@code URL}的主机名(如果适用)。主机的格式符合RFC 2732,即对于文字IPv6地址,此方法将返回用方括号括起来的IPv6地址({@code'['}和{@code']'})。
     * 
     * 
     * @return  the host name of this {@code URL}.
     */
    public String getHost() {
        return host;
    }

    /**
     * Gets the file name of this {@code URL}.
     * The returned file portion will be
     * the same as <CODE>getPath()</CODE>, plus the concatenation of
     * the value of <CODE>getQuery()</CODE>, if any. If there is
     * no query portion, this method and <CODE>getPath()</CODE> will
     * return identical results.
     *
     * <p>
     *  获取此{@code URL}的文件名。返回的文件部分将与<CODE> getPath()</CODE>相同,加上<CODE> getQuery()</CODE>的值(如果有)的串联。
     * 如果没有查询部分,此方法和<CODE> getPath()</CODE>将返回相同的结果。
     * 
     * 
     * @return  the file name of this {@code URL},
     * or an empty string if one does not exist
     */
    public String getFile() {
        return file;
    }

    /**
     * Gets the anchor (also known as the "reference") of this
     * {@code URL}.
     *
     * <p>
     *  获取此{@code URL}的锚点(也称为"引用")。
     * 
     * 
     * @return  the anchor (also known as the "reference") of this
     *          {@code URL}, or <CODE>null</CODE> if one does not exist
     */
    public String getRef() {
        return ref;
    }

    /**
     * Compares this URL for equality with another object.<p>
     *
     * If the given object is not a URL then this method immediately returns
     * {@code false}.<p>
     *
     * Two URL objects are equal if they have the same protocol, reference
     * equivalent hosts, have the same port number on the host, and the same
     * file and fragment of the file.<p>
     *
     * Two hosts are considered equivalent if both host names can be resolved
     * into the same IP addresses; else if either host name can't be
     * resolved, the host names must be equal without regard to case; or both
     * host names equal to null.<p>
     *
     * Since hosts comparison requires name resolution, this operation is a
     * blocking operation. <p>
     *
     * Note: The defined behavior for {@code equals} is known to
     * be inconsistent with virtual hosting in HTTP.
     *
     * <p>
     *  将此网址与其他对象的相等性进行比较。<p>
     * 
     *  如果给定的对象不是一个URL,那么这个方法立即返回{@code false}。<p>
     * 
     *  如果两个URL对象具有相同的协议,引用等效主机,在主机上具有相同的端口号,以及文件的相同文件和片段,则两个URL对象是相等的。<p>
     * 
     *  如果两个主机名可以解析为相同的IP地址,则两个主机被认为是等效的;否则如果无法解析任一主机名,则主机名必须相等,不考虑大小写;或两个主机名都等于​​null。<p>
     * 
     *  由于主机比较需要名称解析,此操作是阻塞操作。 <p>
     * 
     * 注意：已知{@code equals}的定义行为与HTTP中的虚拟主机不一致。
     * 
     * 
     * @param   obj   the URL to compare against.
     * @return  {@code true} if the objects are the same;
     *          {@code false} otherwise.
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof URL))
            return false;
        URL u2 = (URL)obj;

        return handler.equals(this, u2);
    }

    /**
     * Creates an integer suitable for hash table indexing.<p>
     *
     * The hash code is based upon all the URL components relevant for URL
     * comparison. As such, this operation is a blocking operation.<p>
     *
     * <p>
     *  创建适合哈希表索引的整数。<p>
     * 
     *  哈希码基于与URL比较相关的所有URL组件。因此,此操作是阻止操作。<p>
     * 
     * 
     * @return  a hash code for this {@code URL}.
     */
    public synchronized int hashCode() {
        if (hashCode != -1)
            return hashCode;

        hashCode = handler.hashCode(this);
        return hashCode;
    }

    /**
     * Compares two URLs, excluding the fragment component.<p>
     *
     * Returns {@code true} if this {@code URL} and the
     * {@code other} argument are equal without taking the
     * fragment component into consideration.
     *
     * <p>
     *  比较两个网址,不包括片段组件。<p>
     * 
     *  如果此{@code URL}和{@code other}参数相等,而不考虑片段组件,则返回{@code true}。
     * 
     * 
     * @param   other   the {@code URL} to compare against.
     * @return  {@code true} if they reference the same remote object;
     *          {@code false} otherwise.
     */
    public boolean sameFile(URL other) {
        return handler.sameFile(this, other);
    }

    /**
     * Constructs a string representation of this {@code URL}. The
     * string is created by calling the {@code toExternalForm}
     * method of the stream protocol handler for this object.
     *
     * <p>
     *  构造此{@code URL}的字符串表示形式。该字符串是通过调用此对象的流协议处理程序的{@code toExternalForm}方法创建的。
     * 
     * 
     * @return  a string representation of this object.
     * @see     java.net.URL#URL(java.lang.String, java.lang.String, int,
     *                  java.lang.String)
     * @see     java.net.URLStreamHandler#toExternalForm(java.net.URL)
     */
    public String toString() {
        return toExternalForm();
    }

    /**
     * Constructs a string representation of this {@code URL}. The
     * string is created by calling the {@code toExternalForm}
     * method of the stream protocol handler for this object.
     *
     * <p>
     *  构造此{@code URL}的字符串表示形式。该字符串是通过调用此对象的流协议处理程序的{@code toExternalForm}方法创建的。
     * 
     * 
     * @return  a string representation of this object.
     * @see     java.net.URL#URL(java.lang.String, java.lang.String,
     *                  int, java.lang.String)
     * @see     java.net.URLStreamHandler#toExternalForm(java.net.URL)
     */
    public String toExternalForm() {
        return handler.toExternalForm(this);
    }

    /**
     * Returns a {@link java.net.URI} equivalent to this URL.
     * This method functions in the same way as {@code new URI (this.toString())}.
     * <p>Note, any URL instance that complies with RFC 2396 can be converted
     * to a URI. However, some URLs that are not strictly in compliance
     * can not be converted to a URI.
     *
     * <p>
     *  返回与此网址等效的{@link java.net.URI}。此方法的功能与{@code new URI(this.toString())}相同。
     *  <p>请注意,符合RFC 2396的任何网址实例都可以转换为URI。但是,某些不严格遵守的网址不能转换为URI。
     * 
     * 
     * @exception URISyntaxException if this URL is not formatted strictly according to
     *            to RFC2396 and cannot be converted to a URI.
     *
     * @return    a URI instance equivalent to this URL.
     * @since 1.5
     */
    public URI toURI() throws URISyntaxException {
        return new URI (toString());
    }

    /**
     * Returns a {@link java.net.URLConnection URLConnection} instance that
     * represents a connection to the remote object referred to by the
     * {@code URL}.
     *
     * <P>A new instance of {@linkplain java.net.URLConnection URLConnection} is
     * created every time when invoking the
     * {@linkplain java.net.URLStreamHandler#openConnection(URL)
     * URLStreamHandler.openConnection(URL)} method of the protocol handler for
     * this URL.</P>
     *
     * <P>It should be noted that a URLConnection instance does not establish
     * the actual network connection on creation. This will happen only when
     * calling {@linkplain java.net.URLConnection#connect() URLConnection.connect()}.</P>
     *
     * <P>If for the URL's protocol (such as HTTP or JAR), there
     * exists a public, specialized URLConnection subclass belonging
     * to one of the following packages or one of their subpackages:
     * java.lang, java.io, java.util, java.net, the connection
     * returned will be of that subclass. For example, for HTTP an
     * HttpURLConnection will be returned, and for JAR a
     * JarURLConnection will be returned.</P>
     *
     * <p>
     *  返回一个{@link java.net.URLConnection URLConnection}实例,该实例表示与{@code URL}引用的远程对象的连接。
     * 
     * <P>每次调用协议处理程序的{@linkplain java.net.URLStreamHandler#openConnection(URL)URLStreamHandler.openConnection(URL)}
     * 方法时,都会创建{@linkplain java.net.URLConnection URLConnection}的新实例此URL。
     * </P>。
     * 
     *  <p>应该注意,URLConnection实例在创建时不会建立实际的网络连接。
     * 这只会在调用{@linkplain java.net.URLConnection#connect()URLConnection.connect()}时发生。</P>。
     * 
     *  <P>如果对于URL的协议(例如HTTP或JAR),存在属于以下包之一或其子包之一的公共的,专用的URLConnection子类：java.lang,java.io,java.util,java .n
     * et,返回的连接将是那个子类。
     * 例如,对于HTTP,将返回HttpURLConnection,对于JAR,将返回JarURLConnection。</P>。
     * 
     * 
     * @return     a {@link java.net.URLConnection URLConnection} linking
     *             to the URL.
     * @exception  IOException  if an I/O exception occurs.
     * @see        java.net.URL#URL(java.lang.String, java.lang.String,
     *             int, java.lang.String)
     */
    public URLConnection openConnection() throws java.io.IOException {
        return handler.openConnection(this);
    }

    /**
     * Same as {@link #openConnection()}, except that the connection will be
     * made through the specified proxy; Protocol handlers that do not
     * support proxing will ignore the proxy parameter and make a
     * normal connection.
     *
     * Invoking this method preempts the system's default ProxySelector
     * settings.
     *
     * <p>
     *  与{@link #openConnection()}相同,但连接将通过指定的代理进行;不支持代理的协议处理程序将忽略代理参数并建立正常连接。
     * 
     *  调用此方法将抢占系统的默认ProxySelector设置。
     * 
     * 
     * @param      proxy the Proxy through which this connection
     *             will be made. If direct connection is desired,
     *             Proxy.NO_PROXY should be specified.
     * @return     a {@code URLConnection} to the URL.
     * @exception  IOException  if an I/O exception occurs.
     * @exception  SecurityException if a security manager is present
     *             and the caller doesn't have permission to connect
     *             to the proxy.
     * @exception  IllegalArgumentException will be thrown if proxy is null,
     *             or proxy has the wrong type
     * @exception  UnsupportedOperationException if the subclass that
     *             implements the protocol handler doesn't support
     *             this method.
     * @see        java.net.URL#URL(java.lang.String, java.lang.String,
     *             int, java.lang.String)
     * @see        java.net.URLConnection
     * @see        java.net.URLStreamHandler#openConnection(java.net.URL,
     *             java.net.Proxy)
     * @since      1.5
     */
    public URLConnection openConnection(Proxy proxy)
        throws java.io.IOException {
        if (proxy == null) {
            throw new IllegalArgumentException("proxy can not be null");
        }

        // Create a copy of Proxy as a security measure
        Proxy p = proxy == Proxy.NO_PROXY ? Proxy.NO_PROXY : sun.net.ApplicationProxy.create(proxy);
        SecurityManager sm = System.getSecurityManager();
        if (p.type() != Proxy.Type.DIRECT && sm != null) {
            InetSocketAddress epoint = (InetSocketAddress) p.address();
            if (epoint.isUnresolved())
                sm.checkConnect(epoint.getHostName(), epoint.getPort());
            else
                sm.checkConnect(epoint.getAddress().getHostAddress(),
                                epoint.getPort());
        }
        return handler.openConnection(this, p);
    }

    /**
     * Opens a connection to this {@code URL} and returns an
     * {@code InputStream} for reading from that connection. This
     * method is a shorthand for:
     * <blockquote><pre>
     *     openConnection().getInputStream()
     * </pre></blockquote>
     *
     * <p>
     *  打开与此{@code URL}的连接,并返回用于从该连接中读取的{@code InputStream}。这个方法是一个缩写：<blockquote> <pre> openConnection()。
     * getInputStream()</pre> </blockquote>。
     * 
     * 
     * @return     an input stream for reading from the URL connection.
     * @exception  IOException  if an I/O exception occurs.
     * @see        java.net.URL#openConnection()
     * @see        java.net.URLConnection#getInputStream()
     */
    public final InputStream openStream() throws java.io.IOException {
        return openConnection().getInputStream();
    }

    /**
     * Gets the contents of this URL. This method is a shorthand for:
     * <blockquote><pre>
     *     openConnection().getContent()
     * </pre></blockquote>
     *
     * <p>
     * 获取此网址的内容。这个方法是一个缩写：<blockquote> <pre> openConnection()。getContent()</pre> </blockquote>
     * 
     * 
     * @return     the contents of this URL.
     * @exception  IOException  if an I/O exception occurs.
     * @see        java.net.URLConnection#getContent()
     */
    public final Object getContent() throws java.io.IOException {
        return openConnection().getContent();
    }

    /**
     * Gets the contents of this URL. This method is a shorthand for:
     * <blockquote><pre>
     *     openConnection().getContent(Class[])
     * </pre></blockquote>
     *
     * <p>
     *  获取此网址的内容。这个方法是一个简写：<blockquote> <pre> openConnection()。getContent(Class [])</pre> </blockquote>
     * 
     * 
     * @param classes an array of Java types
     * @return     the content object of this URL that is the first match of
     *               the types specified in the classes array.
     *               null if none of the requested types are supported.
     * @exception  IOException  if an I/O exception occurs.
     * @see        java.net.URLConnection#getContent(Class[])
     * @since 1.3
     */
    public final Object getContent(Class[] classes)
    throws java.io.IOException {
        return openConnection().getContent(classes);
    }

    /**
     * The URLStreamHandler factory.
     * <p>
     *  URLStreamHandler工厂。
     * 
     */
    static URLStreamHandlerFactory factory;

    /**
     * Sets an application's {@code URLStreamHandlerFactory}.
     * This method can be called at most once in a given Java Virtual
     * Machine.
     *
     *<p> The {@code URLStreamHandlerFactory} instance is used to
     *construct a stream protocol handler from a protocol name.
     *
     * <p> If there is a security manager, this method first calls
     * the security manager's {@code checkSetFactory} method
     * to ensure the operation is allowed.
     * This could result in a SecurityException.
     *
     * <p>
     *  设置应用程序的{@code URLStreamHandlerFactory}。此方法在给定的Java虚拟机中最多只能调用一次。
     * 
     *  p> {@code URLStreamHandlerFactory}实例用于从协议名称构造流协议处理程序。
     * 
     *  <p>如果有安全管理员,此方法会先调用安全管理员的{@code checkSetFactory}方法,以确保允许操作。这可能导致SecurityException。
     * 
     * 
     * @param      fac   the desired factory.
     * @exception  Error  if the application has already set a factory.
     * @exception  SecurityException  if a security manager exists and its
     *             {@code checkSetFactory} method doesn't allow
     *             the operation.
     * @see        java.net.URL#URL(java.lang.String, java.lang.String,
     *             int, java.lang.String)
     * @see        java.net.URLStreamHandlerFactory
     * @see        SecurityManager#checkSetFactory
     */
    public static void setURLStreamHandlerFactory(URLStreamHandlerFactory fac) {
        synchronized (streamHandlerLock) {
            if (factory != null) {
                throw new Error("factory already defined");
            }
            SecurityManager security = System.getSecurityManager();
            if (security != null) {
                security.checkSetFactory();
            }
            handlers.clear();
            factory = fac;
        }
    }

    /**
     * A table of protocol handlers.
     * <p>
     *  协议处理程序表。
     * 
     */
    static Hashtable<String,URLStreamHandler> handlers = new Hashtable<>();
    private static Object streamHandlerLock = new Object();

    /**
     * Returns the Stream Handler.
     * <p>
     *  返回流处理程序。
     * 
     * 
     * @param protocol the protocol to use
     */
    static URLStreamHandler getURLStreamHandler(String protocol) {

        URLStreamHandler handler = handlers.get(protocol);
        if (handler == null) {

            boolean checkedWithFactory = false;

            // Use the factory (if any)
            if (factory != null) {
                handler = factory.createURLStreamHandler(protocol);
                checkedWithFactory = true;
            }

            // Try java protocol handler
            if (handler == null) {
                String packagePrefixList = null;

                packagePrefixList
                    = java.security.AccessController.doPrivileged(
                    new sun.security.action.GetPropertyAction(
                        protocolPathProp,""));
                if (packagePrefixList != "") {
                    packagePrefixList += "|";
                }

                // REMIND: decide whether to allow the "null" class prefix
                // or not.
                packagePrefixList += "sun.net.www.protocol";

                StringTokenizer packagePrefixIter =
                    new StringTokenizer(packagePrefixList, "|");

                while (handler == null &&
                       packagePrefixIter.hasMoreTokens()) {

                    String packagePrefix =
                      packagePrefixIter.nextToken().trim();
                    try {
                        String clsName = packagePrefix + "." + protocol +
                          ".Handler";
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
                            handler  =
                              (URLStreamHandler)cls.newInstance();
                        }
                    } catch (Exception e) {
                        // any number of exceptions can get thrown here
                    }
                }
            }

            synchronized (streamHandlerLock) {

                URLStreamHandler handler2 = null;

                // Check again with hashtable just in case another
                // thread created a handler since we last checked
                handler2 = handlers.get(protocol);

                if (handler2 != null) {
                    return handler2;
                }

                // Check with factory if another thread set a
                // factory since our last check
                if (!checkedWithFactory && factory != null) {
                    handler2 = factory.createURLStreamHandler(protocol);
                }

                if (handler2 != null) {
                    // The handler from the factory must be given more
                    // importance. Discard the default handler that
                    // this thread created.
                    handler = handler2;
                }

                // Insert this handler into the hashtable
                if (handler != null) {
                    handlers.put(protocol, handler);
                }

            }
        }

        return handler;

    }

    /**
     * WriteObject is called to save the state of the URL to an
     * ObjectOutputStream. The handler is not saved since it is
     * specific to this system.
     *
     * <p>
     *  WriteObject被调用来将URL的状态保存到ObjectOutputStream。处理程序不会被保存,因为它特定于此系统。
     * 
     * 
     * @serialData the default write object value. When read back in,
     * the reader must ensure that calling getURLStreamHandler with
     * the protocol variable returns a valid URLStreamHandler and
     * throw an IOException if it does not.
     */
    private synchronized void writeObject(java.io.ObjectOutputStream s)
        throws IOException
    {
        s.defaultWriteObject(); // write the fields
    }

    /**
     * readObject is called to restore the state of the URL from the
     * stream.  It reads the components of the URL and finds the local
     * stream handler.
     * <p>
     *  readObject被调用以从流中恢复URL的状态。它读取URL的组件并查找本地流处理程序。
     */
    private synchronized void readObject(java.io.ObjectInputStream s)
         throws IOException, ClassNotFoundException
    {
        s.defaultReadObject();  // read the fields
        if ((handler = getURLStreamHandler(protocol)) == null) {
            throw new IOException("unknown protocol: " + protocol);
        }

        // Construct authority part
        if (authority == null &&
            ((host != null && host.length() > 0) || port != -1)) {
            if (host == null)
                host = "";
            authority = (port == -1) ? host : host + ":" + port;

            // Handle hosts with userInfo in them
            int at = host.lastIndexOf('@');
            if (at != -1) {
                userInfo = host.substring(0, at);
                host = host.substring(at+1);
            }
        } else if (authority != null) {
            // Construct user info part
            int ind = authority.indexOf('@');
            if (ind != -1)
                userInfo = authority.substring(0, ind);
        }

        // Construct path and query part
        path = null;
        query = null;
        if (file != null) {
            // Fix: only do this if hierarchical?
            int q = file.lastIndexOf('?');
            if (q != -1) {
                query = file.substring(q+1);
                path = file.substring(0, q);
            } else
                path = file;
        }
    }
}

class Parts {
    String path, query, ref;

    Parts(String file) {
        int ind = file.indexOf('#');
        ref = ind < 0 ? null: file.substring(ind + 1);
        file = ind < 0 ? file: file.substring(0, ind);
        int q = file.lastIndexOf('?');
        if (q != -1) {
            query = file.substring(q+1);
            path = file.substring(0, q);
        } else {
            path = file;
        }
    }

    String getPath() {
        return path;
    }

    String getQuery() {
        return query;
    }

    String getRef() {
        return ref;
    }
}
