/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2002, 2013, Oracle and/or its affiliates. All rights reserved.
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


package javax.management.remote;


import com.sun.jmx.remote.util.ClassLogger;
import com.sun.jmx.remote.util.EnvHelp;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.BitSet;
import java.util.StringTokenizer;

/**
 * <p>The address of a JMX API connector server.  Instances of this class
 * are immutable.</p>
 *
 * <p>The address is an <em>Abstract Service URL</em> for SLP, as
 * defined in RFC 2609 and amended by RFC 3111.  It must look like
 * this:</p>
 *
 * <blockquote>
 *
 * <code>service:jmx:<em>protocol</em>:<em>sap</em></code>
 *
 * </blockquote>
 *
 * <p>Here, <code><em>protocol</em></code> is the transport
 * protocol to be used to connect to the connector server.  It is
 * a string of one or more ASCII characters, each of which is a
 * letter, a digit, or one of the characters <code>+</code> or
 * <code>-</code>.  The first character must be a letter.
 * Uppercase letters are converted into lowercase ones.</p>
 *
 * <p><code><em>sap</em></code> is the address at which the connector
 * server is found.  This address uses a subset of the syntax defined
 * by RFC 2609 for IP-based protocols.  It is a subset because the
 * <code>user@host</code> syntax is not supported.</p>
 *
 * <p>The other syntaxes defined by RFC 2609 are not currently
 * supported by this class.</p>
 *
 * <p>The supported syntax is:</p>
 *
 * <blockquote>
 *
 * <code>//<em>[host[</em>:<em>port]][url-path]</em></code>
 *
 * </blockquote>
 *
 * <p>Square brackets <code>[]</code> indicate optional parts of
 * the address.  Not all protocols will recognize all optional
 * parts.</p>
 *
 * <p>The <code><em>host</em></code> is a host name, an IPv4 numeric
 * host address, or an IPv6 numeric address enclosed in square
 * brackets.</p>
 *
 * <p>The <code><em>port</em></code> is a decimal port number.  0
 * means a default or anonymous port, depending on the protocol.</p>
 *
 * <p>The <code><em>host</em></code> and <code><em>port</em></code>
 * can be omitted.  The <code><em>port</em></code> cannot be supplied
 * without a <code><em>host</em></code>.</p>
 *
 * <p>The <code><em>url-path</em></code>, if any, begins with a slash
 * (<code>/</code>) or a semicolon (<code>;</code>) and continues to
 * the end of the address.  It can contain attributes using the
 * semicolon syntax specified in RFC 2609.  Those attributes are not
 * parsed by this class and incorrect attribute syntax is not
 * detected.</p>
 *
 * <p>Although it is legal according to RFC 2609 to have a
 * <code><em>url-path</em></code> that begins with a semicolon, not
 * all implementations of SLP allow it, so it is recommended to avoid
 * that syntax.</p>
 *
 * <p>Case is not significant in the initial
 * <code>service:jmx:<em>protocol</em></code> string or in the host
 * part of the address.  Depending on the protocol, case can be
 * significant in the <code><em>url-path</em></code>.</p>
 *
 * <p>
 *  <p> JMX API连接器服务器的地址。这个类的实例是不可变的。</p>
 * 
 *  <p>地址是SLP的<em>抽象服务URL </em>,如RFC 2609中定义,并由RFC 3111修改。它必须如下所示：</p>
 * 
 * <blockquote>
 * 
 *  <code> service：jmx：<em> protocol </em>：<em> sap </em> </code>
 * 
 * </blockquote>
 * 
 *  <p>在此,<code> <em> protocol </em> </code>是用于连接到连接器服务器的传输协议。
 * 它是一个或多个ASCII字符的字符串,每个字符都是字母,数字或字符<code> + </code>或<code>  -  </code>之一。第一个字符必须是字母。大写字母会转换为小写字母。</p>。
 * 
 *  <p> <code> <em> sap </em> </code>是找到连接器服务器的地址。此地址使用RFC 2609定义的基于IP协议的语法子集。
 * 它是一个子集,因为不支持<code> user @ host </code>语法。</p>。
 * 
 *  <p>此类别目前不支持RFC 2609定义的其他语法。</p>
 * 
 *  <p>支持的语法为：</p>
 * 
 * <blockquote>
 * 
 *  <code> // <em> [host [</em>：<em> port]] [url-path] </
 * 
 * </blockquote>
 * 
 *  <p>方括号<code> [] </code>表示地址的可选部分。并非所有协议都会识别所有可选部分。</p>
 * 
 *  <p> <code> <em> host </em> </code>是以方括号括起来的主机名称,IPv4数字主机地址或IPv6数字地址。</p>
 * 
 * <p> <code> <em> port </em> </code>是一个十进制端口号。 0表示默认或匿名端口,具体取决于协议。</p>
 * 
 *  <p> <code> <em> host </em> </code>和<code> <em> port </em> </code>可以省略。
 * 如果没有<code> <em>主机</em> </code>,则无法提供<code> <em> port </em> </。
 * 
 *  <p> <code> <em> url-path </em> </code>(如果有)以斜杠(<code> / </code>)或分号(<code>; </code >)并继续到地址的结尾。
 * 它可以包含使用RFC 2609中指定的分号语法的属性。这些属性不会被此类解析,并且不会检测到错误的属性语法。</p>。
 * 
 *  <p>虽然根据RFC 2609,有一个以分号开头的<code> <em> url-path </em> </code>是合法的,但并非所有SLP的实现都允许它,因此建议避免使用该语法。</p>
 * 
 *  <p>在初始<code>服务：jmx：<em> protocol </em> </code>字符串或地址的主机部分中,情况并不重要。
 * 根据协议,在<code> <em> url-path </em> </code>中,情况可能很重要。</p>。
 * 
 * 
 * @see <a
 * href="http://www.ietf.org/rfc/rfc2609.txt">RFC 2609,
 * "Service Templates and <code>Service:</code> Schemes"</a>
 * @see <a
 * href="http://www.ietf.org/rfc/rfc3111.txt">RFC 3111,
 * "Service Location Protocol Modifications for IPv6"</a>
 *
 * @since 1.5
 */
public class JMXServiceURL implements Serializable {

    private static final long serialVersionUID = 8173364409860779292L;

    /**
     * <p>Constructs a <code>JMXServiceURL</code> by parsing a Service URL
     * string.</p>
     *
     * <p>
     *  <p>通过解析服务网址字符串构造<code> JMXServiceURL </code>。</p>
     * 
     * 
     * @param serviceURL the URL string to be parsed.
     *
     * @exception NullPointerException if <code>serviceURL</code> is
     * null.
     *
     * @exception MalformedURLException if <code>serviceURL</code>
     * does not conform to the syntax for an Abstract Service URL or
     * if it is not a valid name for a JMX Remote API service.  A
     * <code>JMXServiceURL</code> must begin with the string
     * <code>"service:jmx:"</code> (case-insensitive).  It must not
     * contain any characters that are not printable ASCII characters.
     */
    public JMXServiceURL(String serviceURL) throws MalformedURLException {
        final int serviceURLLength = serviceURL.length();

        /* Check that there are no non-ASCII characters in the URL,
        /* <p>
        /* 
           following RFC 2609.  */
        for (int i = 0; i < serviceURLLength; i++) {
            char c = serviceURL.charAt(i);
            if (c < 32 || c >= 127) {
                throw new MalformedURLException("Service URL contains " +
                                                "non-ASCII character 0x" +
                                                Integer.toHexString(c));
            }
        }

        // Parse the required prefix
        final String requiredPrefix = "service:jmx:";
        final int requiredPrefixLength = requiredPrefix.length();
        if (!serviceURL.regionMatches(true, // ignore case
                                      0,    // serviceURL offset
                                      requiredPrefix,
                                      0,    // requiredPrefix offset
                                      requiredPrefixLength)) {
            throw new MalformedURLException("Service URL must start with " +
                                            requiredPrefix);
        }

        // Parse the protocol name
        final int protoStart = requiredPrefixLength;
        final int protoEnd = indexOf(serviceURL, ':', protoStart);
        this.protocol =
            serviceURL.substring(protoStart, protoEnd).toLowerCase();

        if (!serviceURL.regionMatches(protoEnd, "://", 0, 3)) {
            throw new MalformedURLException("Missing \"://\" after " +
                                            "protocol name");
        }

        // Parse the host name
        final int hostStart = protoEnd + 3;
        final int hostEnd;
        if (hostStart < serviceURLLength
            && serviceURL.charAt(hostStart) == '[') {
            hostEnd = serviceURL.indexOf(']', hostStart) + 1;
            if (hostEnd == 0)
                throw new MalformedURLException("Bad host name: [ without ]");
            this.host = serviceURL.substring(hostStart + 1, hostEnd - 1);
            if (!isNumericIPv6Address(this.host)) {
                throw new MalformedURLException("Address inside [...] must " +
                                                "be numeric IPv6 address");
            }
        } else {
            hostEnd =
                indexOfFirstNotInSet(serviceURL, hostNameBitSet, hostStart);
            this.host = serviceURL.substring(hostStart, hostEnd);
        }

        // Parse the port number
        final int portEnd;
        if (hostEnd < serviceURLLength && serviceURL.charAt(hostEnd) == ':') {
            if (this.host.length() == 0) {
                throw new MalformedURLException("Cannot give port number " +
                                                "without host name");
            }
            final int portStart = hostEnd + 1;
            portEnd =
                indexOfFirstNotInSet(serviceURL, numericBitSet, portStart);
            final String portString = serviceURL.substring(portStart, portEnd);
            try {
                this.port = Integer.parseInt(portString);
            } catch (NumberFormatException e) {
                throw new MalformedURLException("Bad port number: \"" +
                                                portString + "\": " + e);
            }
        } else {
            portEnd = hostEnd;
            this.port = 0;
        }

        // Parse the URL path
        final int urlPathStart = portEnd;
        if (urlPathStart < serviceURLLength)
            this.urlPath = serviceURL.substring(urlPathStart);
        else
            this.urlPath = "";

        validate();
    }

    /**
     * <p>Constructs a <code>JMXServiceURL</code> with the given protocol,
     * host, and port.  This constructor is equivalent to
     * {@link #JMXServiceURL(String, String, int, String)
     * JMXServiceURL(protocol, host, port, null)}.</p>
     *
     * <p>
     *  <p>使用给定的协议,主机和端口构造一个<code> JMXServiceURL </code>。
     * 这个构造函数相当于{@link #JMXServiceURL(String,String,int,String)JMXServiceURL(protocol,host,port,null)}。
     * </p>。
     * 
     * 
     * @param protocol the protocol part of the URL.  If null, defaults
     * to <code>jmxmp</code>.
     *
     * @param host the host part of the URL.  If null, defaults to the
     * local host name, as determined by
     * <code>InetAddress.getLocalHost().getHostName()</code>.  If it
     * is a numeric IPv6 address, it can optionally be enclosed in
     * square brackets <code>[]</code>.
     *
     * @param port the port part of the URL.
     *
     * @exception MalformedURLException if one of the parts is
     * syntactically incorrect, or if <code>host</code> is null and it
     * is not possible to find the local host name, or if
     * <code>port</code> is negative.
     */
    public JMXServiceURL(String protocol, String host, int port)
            throws MalformedURLException {
        this(protocol, host, port, null);
    }

    /**
     * <p>Constructs a <code>JMXServiceURL</code> with the given parts.
     *
     * <p>
     *  <p>使用给定的部分构造一个<code> JMXServiceURL </code>。
     * 
     * 
     * @param protocol the protocol part of the URL.  If null, defaults
     * to <code>jmxmp</code>.
     *
     * @param host the host part of the URL.  If null, defaults to the
     * local host name, as determined by
     * <code>InetAddress.getLocalHost().getHostName()</code>.  If it
     * is a numeric IPv6 address, it can optionally be enclosed in
     * square brackets <code>[]</code>.
     *
     * @param port the port part of the URL.
     *
     * @param urlPath the URL path part of the URL.  If null, defaults to
     * the empty string.
     *
     * @exception MalformedURLException if one of the parts is
     * syntactically incorrect, or if <code>host</code> is null and it
     * is not possible to find the local host name, or if
     * <code>port</code> is negative.
     */
    public JMXServiceURL(String protocol, String host, int port,
                         String urlPath)
            throws MalformedURLException {
        if (protocol == null)
            protocol = "jmxmp";

        if (host == null) {
            InetAddress local;
            try {
                local = InetAddress.getLocalHost();
            } catch (UnknownHostException e) {
                throw new MalformedURLException("Local host name unknown: " +
                                                e);
            }

            host = local.getHostName();

            /* We might have a hostname that violates DNS naming
               rules, for example that contains an `_'.  While we
               could be strict and throw an exception, this is rather
               user-hostile.  Instead we use its numerical IP address.
               We can only reasonably do this for the host==null case.
               If we're given an explicit host name that is illegal we
            /* <p>
            /* 规则,例如包含一个`_'。虽然我们可以是严格的,并抛出异常,这是相当用户敌对。相反,我们使用其数字IP地址。我们只能合理地为host == null的情况做这个。
            /* 如果我们给一个明确的主机名是非法的,我们。
            /* 
            /* 
               have to reject it.  (Bug 5057532.)  */
            try {
                validateHost(host, port);
            } catch (MalformedURLException e) {
                if (logger.fineOn()) {
                    logger.fine("JMXServiceURL",
                                "Replacing illegal local host name " +
                                host + " with numeric IP address " +
                                "(see RFC 1034)", e);
                }
                host = local.getHostAddress();
                /* Use the numeric address, which could be either IPv4
                /* <p>
                /* 
                   or IPv6.  validateHost will accept either.  */
            }
        }

        if (host.startsWith("[")) {
            if (!host.endsWith("]")) {
                throw new MalformedURLException("Host starts with [ but " +
                                                "does not end with ]");
            }
            host = host.substring(1, host.length() - 1);
            if (!isNumericIPv6Address(host)) {
                throw new MalformedURLException("Address inside [...] must " +
                                                "be numeric IPv6 address");
            }
            if (host.startsWith("["))
                throw new MalformedURLException("More than one [[...]]");
        }

        this.protocol = protocol.toLowerCase();
        this.host = host;
        this.port = port;

        if (urlPath == null)
            urlPath = "";
        this.urlPath = urlPath;

        validate();
    }

    private static final String INVALID_INSTANCE_MSG =
            "Trying to deserialize an invalid instance of JMXServiceURL";
    private void readObject(ObjectInputStream  inputStream) throws IOException, ClassNotFoundException {
        ObjectInputStream.GetField gf = inputStream.readFields();
        String h = (String)gf.get("host", null);
        int p = (int)gf.get("port", -1);
        String proto = (String)gf.get("protocol", null);
        String url = (String)gf.get("urlPath", null);

        if (proto == null || url == null || h == null) {
            StringBuilder sb = new StringBuilder(INVALID_INSTANCE_MSG).append('[');
            boolean empty = true;
            if (proto == null) {
                sb.append("protocol=null");
                empty = false;
            }
            if (h == null) {
                sb.append(empty ? "" : ",").append("host=null");
                empty = false;
            }
            if (url == null) {
                sb.append(empty ? "" : ",").append("urlPath=null");
            }
            sb.append(']');
            throw new InvalidObjectException(sb.toString());
        }

        if (h.contains("[") || h.contains("]")) {
            throw new InvalidObjectException("Invalid host name: " + h);
        }

        try {
            validate(proto, h, p, url);
            this.protocol = proto;
            this.host = h;
            this.port = p;
            this.urlPath = url;
        } catch (MalformedURLException e) {
            throw new InvalidObjectException(INVALID_INSTANCE_MSG + ": " +
                                             e.getMessage());
        }

    }

    private void validate(String proto, String h, int p, String url)
        throws MalformedURLException {
        // Check protocol
        final int protoEnd = indexOfFirstNotInSet(proto, protocolBitSet, 0);
        if (protoEnd == 0 || protoEnd < proto.length()
            || !alphaBitSet.get(proto.charAt(0))) {
            throw new MalformedURLException("Missing or invalid protocol " +
                                            "name: \"" + proto + "\"");
        }

        // Check host
        validateHost(h, p);

        // Check port
        if (p < 0)
            throw new MalformedURLException("Bad port: " + p);

        // Check URL path
        if (url.length() > 0) {
            if (!url.startsWith("/") && !url.startsWith(";"))
                throw new MalformedURLException("Bad URL path: " + url);
        }
    }

    private void validate() throws MalformedURLException {
        validate(this.protocol, this.host, this.port, this.urlPath);
    }

    private static void validateHost(String h, int port)
            throws MalformedURLException {

        if (h.length() == 0) {
            if (port != 0) {
                throw new MalformedURLException("Cannot give port number " +
                                                "without host name");
            }
            return;
        }

        if (isNumericIPv6Address(h)) {
            /* We assume J2SE >= 1.4 here.  Otherwise you can't
               use the address anyway.  We can't call
               InetAddress.getByName without checking for a
               numeric IPv6 address, because we mustn't try to do
               a DNS lookup in case the address is not actually
            /* <p>
            /*  请使用地址。我们不能在不检查数字IPv6地址的情况下调用InetAddress.getByName,因为我们不能尝试进行DNS查找,以防地址不实际
            /* 
            /* 
               numeric.  */
            try {
                InetAddress.getByName(h);
            } catch (Exception e) {
                /* We should really catch UnknownHostException
                   here, but a bug in JDK 1.4 causes it to throw
                   ArrayIndexOutOfBoundsException, e.g. if the
                /* <p>
                /*  这里,但JDK 1.4中的错误导致它抛出ArrayIndexOutOfBoundsException,例如。如果
                /* 
                /* 
                   string is ":".  */
                MalformedURLException bad =
                    new MalformedURLException("Bad IPv6 address: " + h);
                EnvHelp.initCause(bad, e);
                throw bad;
            }
        } else {
            /* Tiny state machine to check valid host name.  This
               checks the hostname grammar from RFC 1034 (DNS),
               page 11.  A hostname is a dot-separated list of one
               or more labels, where each label consists of
               letters, numbers, or hyphens.  A label cannot begin
               or end with a hyphen.  Empty hostnames are not
               allowed.  Note that numeric IPv4 addresses are a
               special case of this grammar.

               The state is entirely captured by the last
               character seen, with a virtual `.' preceding the
               name.  We represent any alphanumeric character by
               `a'.

               We need a special hack to check, as required by the
               RFC 2609 (SLP) grammar, that the last component of
               the hostname begins with a letter.  Respecting the
               intent of the RFC, we only do this if there is more
               than one component.  If your local hostname begins
            /* <p>
            /*  检查RFC 1034(DNS)(第11页)中的主机名语法。主机名是一个或多个标签的点分隔列表,其中每个标签由字母,数字或连字符组成。标签不能以连字符开头或结尾。不允许使用空主机名。
            /* 请注意,数字IPv4地址是此语法的特殊情况。
            /* 
            /*  状态完全被看到的最后一个字符捕获,有一个虚拟的。前面的名称。我们用`a'表示任何字母数字字符。
            /* 
            /*  我们需要一个特殊的黑客来检查,如RFC 2609(SLP)语法所要求的,主机名的最后一个组件以字母开头。尊重RFC的意图,我们只有在有多个组件的情况下才这样做。如果您的本地主机名开始
            /* 
            /* 
               with a digit, we don't reject it.  */
            final int hostLen = h.length();
            char lastc = '.';
            boolean sawDot = false;
            char componentStart = 0;

            loop:
            for (int i = 0; i < hostLen; i++) {
                char c = h.charAt(i);
                boolean isAlphaNumeric = alphaNumericBitSet.get(c);
                if (lastc == '.')
                    componentStart = c;
                if (isAlphaNumeric)
                    lastc = 'a';
                else if (c == '-') {
                    if (lastc == '.')
                        break; // will throw exception
                    lastc = '-';
                } else if (c == '.') {
                    sawDot = true;
                    if (lastc != 'a')
                        break; // will throw exception
                    lastc = '.';
                } else {
                    lastc = '.'; // will throw exception
                    break;
                }
            }

            try {
                if (lastc != 'a')
                    throw randomException;
                if (sawDot && !alphaBitSet.get(componentStart)) {
                    /* Must be a numeric IPv4 address.  In addition to
                       the explicitly-thrown exceptions, we can get
                       NoSuchElementException from the calls to
                       tok.nextToken and NumberFormatException from
                       the call to Integer.parseInt.  Using exceptions
                       for control flow this way is a bit evil but it
                    /* <p>
                    /* 显式抛出的异常,我们可以从调用tok.nextToken和NumberFormatException从调用Integer.parseInt获得NoSuchElementException。
                    /* 使用控制流的异常这种方式有点邪恶,但它。
                    /* 
                    /* 
                       does simplify things enormously.  */
                    StringTokenizer tok = new StringTokenizer(h, ".", true);
                    for (int i = 0; i < 4; i++) {
                        String ns = tok.nextToken();
                        int n = Integer.parseInt(ns);
                        if (n < 0 || n > 255)
                            throw randomException;
                        if (i < 3 && !tok.nextToken().equals("."))
                            throw randomException;
                    }
                    if (tok.hasMoreTokens())
                        throw randomException;
                }
            } catch (Exception e) {
                throw new MalformedURLException("Bad host: \"" + h + "\"");
            }
        }
    }

    private static final Exception randomException = new Exception();


    /**
     * <p>The protocol part of the Service URL.
     *
     * <p>
     *  <p>服务网址的协议部分。
     * 
     * 
     * @return the protocol part of the Service URL.  This is never null.
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * <p>The host part of the Service URL.  If the Service URL was
     * constructed with the constructor that takes a URL string
     * parameter, the result is the substring specifying the host in
     * that URL.  If the Service URL was constructed with a
     * constructor that takes a separate host parameter, the result is
     * the string that was specified.  If that string was null, the
     * result is
     * <code>InetAddress.getLocalHost().getHostName()</code>.</p>
     *
     * <p>In either case, if the host was specified using the
     * <code>[...]</code> syntax for numeric IPv6 addresses, the
     * square brackets are not included in the return value here.</p>
     *
     * <p>
     *  <p>服务网址的主机部分。如果服务URL是使用带有URL字符串参数的构造函数构造的,则结果是指定该URL中的主机的子字符串。
     * 如果服务URL是使用带有单独的主机参数的构造函数构造的,则结果是指定的字符串。如果该字符串为null,结果是<code> InetAddress.getLocalHost()。
     * getHostName()</code>。</p>。
     * 
     *  <p>在任何一种情况下,如果使用<code> [...] </code>语法为数字IPv6地址指定了主机,则方括号不包括在此处的返回值中。</p>
     * 
     * 
     * @return the host part of the Service URL.  This is never null.
     */
    public String getHost() {
        return host;
    }

    /**
     * <p>The port of the Service URL.  If no port was
     * specified, the returned value is 0.</p>
     *
     * <p>
     *  <p>服务网址的端口。如果未指定端口,则返回的值为0。</p>
     * 
     * 
     * @return the port of the Service URL, or 0 if none.
     */
    public int getPort() {
        return port;
    }

    /**
     * <p>The URL Path part of the Service URL.  This is an empty
     * string, or a string beginning with a slash (<code>/</code>), or
     * a string beginning with a semicolon (<code>;</code>).
     *
     * <p>
     *  <p>服务网址的网址路径部分。这是一个空字符串,或以斜杠(<code> / </code>)开头的字符串或以分号(<code>; </code>)开头的字符串。
     * 
     * 
     * @return the URL Path part of the Service URL.  This is never
     * null.
     */
    public String getURLPath() {
        return urlPath;
    }

    /**
     * <p>The string representation of this Service URL.  If the value
     * returned by this method is supplied to the
     * <code>JMXServiceURL</code> constructor, the resultant object is
     * equal to this one.</p>
     *
     * <p>The <code><em>host</em></code> part of the returned string
     * is the value returned by {@link #getHost()}.  If that value
     * specifies a numeric IPv6 address, it is surrounded by square
     * brackets <code>[]</code>.</p>
     *
     * <p>The <code><em>port</em></code> part of the returned string
     * is the value returned by {@link #getPort()} in its shortest
     * decimal form.  If the value is zero, it is omitted.</p>
     *
     * <p>
     *  <p>此服务网址的字符串表示形式。如果此方法返回的值提供给<code> JMXServiceURL </code>构造函数,则生成的对象等于此对象。</p>
     * 
     * <p>返回的字符串的<code> <em> host </em> </code>部分是{@link #getHost()}返回的值。
     * 如果该值指定一个数字IPv6地址,它将被方括号<code> [] </code>包围。</p>。
     * 
     *  <p>返回的字符串的<code> <em> port </em> </code>部分是{@link #getPort()}返回的最小十进制形式的值。如果值为零,则省略。</p>
     * 
     * 
     * @return the string representation of this Service URL.
     */
    public String toString() {
        /* We don't bother synchronizing the access to toString.  At worst,
        /* <p>
        /* 
           n threads will independently compute and store the same value.  */
        if (toString != null)
            return toString;
        StringBuilder buf = new StringBuilder("service:jmx:");
        buf.append(getProtocol()).append("://");
        final String getHost = getHost();
        if (isNumericIPv6Address(getHost))
            buf.append('[').append(getHost).append(']');
        else
            buf.append(getHost);
        final int getPort = getPort();
        if (getPort != 0)
            buf.append(':').append(getPort);
        buf.append(getURLPath());
        toString = buf.toString();
        return toString;
    }

    /**
     * <p>Indicates whether some other object is equal to this one.
     * This method returns true if and only if <code>obj</code> is an
     * instance of <code>JMXServiceURL</code> whose {@link
     * #getProtocol()}, {@link #getHost()}, {@link #getPort()}, and
     * {@link #getURLPath()} methods return the same values as for
     * this object.  The values for {@link #getProtocol()} and {@link
     * #getHost()} can differ in case without affecting equality.
     *
     * <p>
     *  <p>指示一些其他对象是否等于此对象。
     * 当且仅当<code> obj </code>是{@link #getProtocol()},{@link #getHost()},{@link #getPort()}和{@link #getURLPath()}
     * 方法返回与此对象相同的值。
     *  <p>指示一些其他对象是否等于此对象。 {@link #getProtocol()}和{@link #getHost()}的值在不影响平等的情况下可能不同。
     * 
     * 
     * @param obj the reference object with which to compare.
     *
     * @return <code>true</code> if this object is the same as the
     * <code>obj</code> argument; <code>false</code> otherwise.
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof JMXServiceURL))
            return false;
        JMXServiceURL u = (JMXServiceURL) obj;
        return
            (u.getProtocol().equalsIgnoreCase(getProtocol()) &&
             u.getHost().equalsIgnoreCase(getHost()) &&
             u.getPort() == getPort() &&
             u.getURLPath().equals(getURLPath()));
    }

    public int hashCode() {
        return toString().hashCode();
    }

    /* True if this string, assumed to be a valid argument to
     * InetAddress.getByName, is a numeric IPv6 address.
     * <p>
     *  InetAddress.getByName,是一个数字IPv6地址。
     * 
     */
    private static boolean isNumericIPv6Address(String s) {
        // address contains colon if and only if it's a numeric IPv6 address
        return (s.indexOf(':') >= 0);
    }

    // like String.indexOf but returns string length not -1 if not present
    private static int indexOf(String s, char c, int fromIndex) {
        int index = s.indexOf(c, fromIndex);
        if (index < 0)
            return s.length();
        else
            return index;
    }

    private static int indexOfFirstNotInSet(String s, BitSet set,
                                            int fromIndex) {
        final int slen = s.length();
        int i = fromIndex;
        while (true) {
            if (i >= slen)
                break;
            char c = s.charAt(i);
            if (c >= 128)
                break; // not ASCII
            if (!set.get(c))
                break;
            i++;
        }
        return i;
    }

    private final static BitSet alphaBitSet = new BitSet(128);
    private final static BitSet numericBitSet = new BitSet(128);
    private final static BitSet alphaNumericBitSet = new BitSet(128);
    private final static BitSet protocolBitSet = new BitSet(128);
    private final static BitSet hostNameBitSet = new BitSet(128);
    static {
        /* J2SE 1.4 adds lots of handy methods to BitSet that would
           allow us to simplify here, e.g. by not writing loops, but
        /* <p>
        /*  让我们在这里简化,例如通过不写循环,但
        /* 
        /* 
           we want to work on J2SE 1.3 too.  */

        for (char c = '0'; c <= '9'; c++)
            numericBitSet.set(c);

        for (char c = 'A'; c <= 'Z'; c++)
            alphaBitSet.set(c);
        for (char c = 'a'; c <= 'z'; c++)
            alphaBitSet.set(c);

        alphaNumericBitSet.or(alphaBitSet);
        alphaNumericBitSet.or(numericBitSet);

        protocolBitSet.or(alphaNumericBitSet);
        protocolBitSet.set('+');
        protocolBitSet.set('-');

        hostNameBitSet.or(alphaNumericBitSet);
        hostNameBitSet.set('-');
        hostNameBitSet.set('.');
    }

    /**
     * The value returned by {@link #getProtocol()}.
     * <p>
     *  由{@link #getProtocol()}返回的值。
     * 
     */
    private String protocol;

    /**
     * The value returned by {@link #getHost()}.
     * <p>
     *  由{@link #getHost()}返回的值。
     * 
     */
    private String host;

    /**
     * The value returned by {@link #getPort()}.
     * <p>
     *  由{@link #getPort()}返回的值。
     * 
     */
    private int port;

    /**
     * The value returned by {@link #getURLPath()}.
     * <p>
     *  由{@link #getURLPath()}返回的值。
     * 
     */
    private String urlPath;

    /**
     * Cached result of {@link #toString()}.
     * <p>
     *  {@link #toString()}的缓存结果。
     */
    private transient String toString;

    private static final ClassLogger logger =
        new ClassLogger("javax.management.remote.misc", "JMXServiceURL");
}
