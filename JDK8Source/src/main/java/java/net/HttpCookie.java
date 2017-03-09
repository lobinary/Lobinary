/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.util.List;
import java.util.StringTokenizer;
import java.util.NoSuchElementException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * An HttpCookie object represents an HTTP cookie, which carries state
 * information between server and user agent. Cookie is widely adopted
 * to create stateful sessions.
 *
 * <p> There are 3 HTTP cookie specifications:
 * <blockquote>
 *   Netscape draft<br>
 *   RFC 2109 - <a href="http://www.ietf.org/rfc/rfc2109.txt">
 * <i>http://www.ietf.org/rfc/rfc2109.txt</i></a><br>
 *   RFC 2965 - <a href="http://www.ietf.org/rfc/rfc2965.txt">
 * <i>http://www.ietf.org/rfc/rfc2965.txt</i></a>
 * </blockquote>
 *
 * <p> HttpCookie class can accept all these 3 forms of syntax.
 *
 * <p>
 *  HttpCookie对象表示HTTP cookie,它在服务器和用户代理之间传送状态信息。 Cookie被广泛应用于创建有状态会话。
 * 
 *  <p>有3个HTTP Cookie规范：
 * <blockquote>
 *  Netscape草稿<br> RFC 2109  -  <a href="http://www.ietf.org/rfc/rfc2109.txt"> <i> http://www.ietf.org/r
 * fc/rfc2109.txt </span> i> </a> <br> RFC 2965  -  <a href="http://www.ietf.org/rfc/rfc2965.txt"> <i> h
 * ttp://www.ietf.org/rfc/rfc2965 .txt </i> </a>。
 * </blockquote>
 * 
 *  <p> HttpCookie类可以接受所有这三种形式的语法。
 * 
 * 
 * @author Edward Wang
 * @since 1.6
 */
public final class HttpCookie implements Cloneable {
    // ---------------- Fields --------------

    // The value of the cookie itself.
    private final String name;  // NAME= ... "$Name" style is reserved
    private String value;       // value of NAME

    // Attributes encoded in the header's cookie fields.
    private String comment;     // Comment=VALUE ... describes cookie's use
    private String commentURL;  // CommentURL="http URL" ... describes cookie's use
    private boolean toDiscard;  // Discard ... discard cookie unconditionally
    private String domain;      // Domain=VALUE ... domain that sees cookie
    private long maxAge = MAX_AGE_UNSPECIFIED;  // Max-Age=VALUE ... cookies auto-expire
    private String path;        // Path=VALUE ... URLs that see the cookie
    private String portlist;    // Port[="portlist"] ... the port cookie may be returned to
    private boolean secure;     // Secure ... e.g. use SSL
    private boolean httpOnly;   // HttpOnly ... i.e. not accessible to scripts
    private int version = 1;    // Version=1 ... RFC 2965 style

    // The original header this cookie was consructed from, if it was
    // constructed by parsing a header, otherwise null.
    private final String header;

    // Hold the creation time (in seconds) of the http cookie for later
    // expiration calculation
    private final long whenCreated;

    // Since the positive and zero max-age have their meanings,
    // this value serves as a hint as 'not specify max-age'
    private final static long MAX_AGE_UNSPECIFIED = -1;

    // date formats used by Netscape's cookie draft
    // as well as formats seen on various sites
    private final static String[] COOKIE_DATE_FORMATS = {
        "EEE',' dd-MMM-yyyy HH:mm:ss 'GMT'",
        "EEE',' dd MMM yyyy HH:mm:ss 'GMT'",
        "EEE MMM dd yyyy HH:mm:ss 'GMT'Z",
        "EEE',' dd-MMM-yy HH:mm:ss 'GMT'",
        "EEE',' dd MMM yy HH:mm:ss 'GMT'",
        "EEE MMM dd yy HH:mm:ss 'GMT'Z"
    };

    // constant strings represent set-cookie header token
    private final static String SET_COOKIE = "set-cookie:";
    private final static String SET_COOKIE2 = "set-cookie2:";

    // ---------------- Ctors --------------

    /**
     * Constructs a cookie with a specified name and value.
     *
     * <p> The name must conform to RFC 2965. That means it can contain
     * only ASCII alphanumeric characters and cannot contain commas,
     * semicolons, or white space or begin with a $ character. The cookie's
     * name cannot be changed after creation.
     *
     * <p> The value can be anything the server chooses to send. Its
     * value is probably of interest only to the server. The cookie's
     * value can be changed after creation with the
     * {@code setValue} method.
     *
     * <p> By default, cookies are created according to the RFC 2965
     * cookie specification. The version can be changed with the
     * {@code setVersion} method.
     *
     *
     * <p>
     *  构造具有指定名称和值的cookie。
     * 
     *  <p>名称必须符合RFC 2965.这意味着它只能包含ASCII字母数字字符,并且不能包含逗号,分号或空格或以$字符开头。 Cookie的名称在创建后无法更改。
     * 
     *  <p>值可以是服务器选择发送的任何值。它的值可能只对服务器感兴趣。 Cookie的值可以在使用{@code setValue}方法创建后更改。
     * 
     *  <p>默认情况下,Cookie是根据RFC 2965 cookie规范创建的。版本可以使用{@code setVersion}方法更改。
     * 
     * 
     * @param  name
     *         a {@code String} specifying the name of the cookie
     *
     * @param  value
     *         a {@code String} specifying the value of the cookie
     *
     * @throws  IllegalArgumentException
     *          if the cookie name contains illegal characters
     * @throws  NullPointerException
     *          if {@code name} is {@code null}
     *
     * @see #setValue
     * @see #setVersion
     */
    public HttpCookie(String name, String value) {
        this(name, value, null /*header*/);
    }

    private HttpCookie(String name, String value, String header) {
        name = name.trim();
        if (name.length() == 0 || !isToken(name) || name.charAt(0) == '$') {
            throw new IllegalArgumentException("Illegal cookie name");
        }

        this.name = name;
        this.value = value;
        toDiscard = false;
        secure = false;

        whenCreated = System.currentTimeMillis();
        portlist = null;
        this.header = header;
    }

    /**
     * Constructs cookies from set-cookie or set-cookie2 header string.
     * RFC 2965 section 3.2.2 set-cookie2 syntax indicates that one header line
     * may contain more than one cookie definitions, so this is a static
     * utility method instead of another constructor.
     *
     * <p>
     *  }}
     * 
     * private HttpCookie(String name,String value,String header){name = name.trim(); if(name.length()== 0 ||！isToken(name)|| name.charAt(0)=='$'){throw new IllegalArgumentException("Illegal cookie name"); }
     * }。
     * 
     *  this.name = name; this.value = value; toDiscard = false; secure = false;
     * 
     *  whenCreated = System.currentTimeMillis(); portlist = null; this.header = header; }}
     * 
     *  / **构造来自set-cookie或set-cookie2头字符串的cookie。
     *  RFC 2965第3.2.2节set-cookie2语法表示一个标题行可能包含多个cookie定义,因此这是一个静态实用程序方法,而不是另一个构造函数。
     * 
     * 
     * @param  header
     *         a {@code String} specifying the set-cookie header. The header
     *         should start with "set-cookie", or "set-cookie2" token; or it
     *         should have no leading token at all.
     *
     * @return  a List of cookie parsed from header line string
     *
     * @throws  IllegalArgumentException
     *          if header string violates the cookie specification's syntax or
     *          the cookie name contains illegal characters.
     * @throws  NullPointerException
     *          if the header string is {@code null}
     */
    public static List<HttpCookie> parse(String header) {
        return parse(header, false);
    }

    // Private version of parse() that will store the original header used to
    // create the cookie, in the cookie itself. This can be useful for filtering
    // Set-Cookie[2] headers, using the internal parsing logic defined in this
    // class.
    private static List<HttpCookie> parse(String header, boolean retainHeader) {

        int version = guessCookieVersion(header);

        // if header start with set-cookie or set-cookie2, strip it off
        if (startsWithIgnoreCase(header, SET_COOKIE2)) {
            header = header.substring(SET_COOKIE2.length());
        } else if (startsWithIgnoreCase(header, SET_COOKIE)) {
            header = header.substring(SET_COOKIE.length());
        }

        List<HttpCookie> cookies = new java.util.ArrayList<>();
        // The Netscape cookie may have a comma in its expires attribute, while
        // the comma is the delimiter in rfc 2965/2109 cookie header string.
        // so the parse logic is slightly different
        if (version == 0) {
            // Netscape draft cookie
            HttpCookie cookie = parseInternal(header, retainHeader);
            cookie.setVersion(0);
            cookies.add(cookie);
        } else {
            // rfc2965/2109 cookie
            // if header string contains more than one cookie,
            // it'll separate them with comma
            List<String> cookieStrings = splitMultiCookies(header);
            for (String cookieStr : cookieStrings) {
                HttpCookie cookie = parseInternal(cookieStr, retainHeader);
                cookie.setVersion(1);
                cookies.add(cookie);
            }
        }

        return cookies;
    }

    // ---------------- Public operations --------------

    /**
     * Reports whether this HTTP cookie has expired or not.
     *
     * <p>
     *  报告此HTTP Cookie是否已过期。
     * 
     * 
     * @return  {@code true} to indicate this HTTP cookie has expired;
     *          otherwise, {@code false}
     */
    public boolean hasExpired() {
        if (maxAge == 0) return true;

        // if not specify max-age, this cookie should be
        // discarded when user agent is to be closed, but
        // it is not expired.
        if (maxAge == MAX_AGE_UNSPECIFIED) return false;

        long deltaSecond = (System.currentTimeMillis() - whenCreated) / 1000;
        if (deltaSecond > maxAge)
            return true;
        else
            return false;
    }

    /**
     * Specifies a comment that describes a cookie's purpose.
     * The comment is useful if the browser presents the cookie
     * to the user. Comments are not supported by Netscape Version 0 cookies.
     *
     * <p>
     *  指定描述Cookie的用途的注释。如果浏览器向用户呈现Cookie,则注释很有用。 Netscape版本0 Cookie不支持注释。
     * 
     * 
     * @param  purpose
     *         a {@code String} specifying the comment to display to the user
     *
     * @see  #getComment
     */
    public void setComment(String purpose) {
        comment = purpose;
    }

    /**
     * Returns the comment describing the purpose of this cookie, or
     * {@code null} if the cookie has no comment.
     *
     * <p>
     *  返回描述此Cookie的用途的注释,如果Cookie没有注释,则返回{@code null}。
     * 
     * 
     * @return  a {@code String} containing the comment, or {@code null} if none
     *
     * @see  #setComment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Specifies a comment URL that describes a cookie's purpose.
     * The comment URL is useful if the browser presents the cookie
     * to the user. Comment URL is RFC 2965 only.
     *
     * <p>
     *  指定描述Cookie的用途的注释URL。如果浏览器向用户提供Cookie,则注释URL很有用。注释URL仅限RFC 2965。
     * 
     * 
     * @param  purpose
     *         a {@code String} specifying the comment URL to display to the user
     *
     * @see  #getCommentURL
     */
    public void setCommentURL(String purpose) {
        commentURL = purpose;
    }

    /**
     * Returns the comment URL describing the purpose of this cookie, or
     * {@code null} if the cookie has no comment URL.
     *
     * <p>
     *  返回描述此Cookie的用途的注释网址,如果Cookie没有注释网址,则返回{@code null}。
     * 
     * 
     * @return  a {@code String} containing the comment URL, or {@code null}
     *          if none
     *
     * @see  #setCommentURL
     */
    public String getCommentURL() {
        return commentURL;
    }

    /**
     * Specify whether user agent should discard the cookie unconditionally.
     * This is RFC 2965 only attribute.
     *
     * <p>
     *  指定用户代理是否应无条件地丢弃Cookie。这是仅RFC 2965属性。
     * 
     * 
     * @param  discard
     *         {@code true} indicates to discard cookie unconditionally
     *
     * @see  #getDiscard
     */
    public void setDiscard(boolean discard) {
        toDiscard = discard;
    }

    /**
     * Returns the discard attribute of the cookie
     *
     * <p>
     *  返回cookie的discard属性
     * 
     * 
     * @return  a {@code boolean} to represent this cookie's discard attribute
     *
     * @see  #setDiscard
     */
    public boolean getDiscard() {
        return toDiscard;
    }

    /**
     * Specify the portlist of the cookie, which restricts the port(s)
     * to which a cookie may be sent back in a Cookie header.
     *
     * <p>
     * 指定cookie的端口列表,该端口列表限制Cookie在Cookie头中发送回的端口。
     * 
     * 
     * @param  ports
     *         a {@code String} specify the port list, which is comma separated
     *         series of digits
     *
     * @see  #getPortlist
     */
    public void setPortlist(String ports) {
        portlist = ports;
    }

    /**
     * Returns the port list attribute of the cookie
     *
     * <p>
     *  返回cookie的端口列表属性
     * 
     * 
     * @return  a {@code String} contains the port list or {@code null} if none
     *
     * @see  #setPortlist
     */
    public String getPortlist() {
        return portlist;
    }

    /**
     * Specifies the domain within which this cookie should be presented.
     *
     * <p> The form of the domain name is specified by RFC 2965. A domain
     * name begins with a dot ({@code .foo.com}) and means that
     * the cookie is visible to servers in a specified Domain Name System
     * (DNS) zone (for example, {@code www.foo.com}, but not
     * {@code a.b.foo.com}). By default, cookies are only returned
     * to the server that sent them.
     *
     * <p>
     *  指定应在其中显示此Cookie的域。
     * 
     *  <p>域名的格式由RFC 2965指定。域名以点({@code .foo.com})开头,表示Cookie对指定的域名系统(DNS)中的服务器可见。
     * 区域(例如{@code www.foo.com},但不是{@code abfoo.com})。默认情况下,Cookie只会返回给发送它们的服务器。
     * 
     * 
     * @param  pattern
     *         a {@code String} containing the domain name within which this
     *         cookie is visible; form is according to RFC 2965
     *
     * @see  #getDomain
     */
    public void setDomain(String pattern) {
        if (pattern != null)
            domain = pattern.toLowerCase();
        else
            domain = pattern;
    }

    /**
     * Returns the domain name set for this cookie. The form of the domain name
     * is set by RFC 2965.
     *
     * <p>
     *  返回为此Cookie设置的域名。域名的格式由RFC 2965设置。
     * 
     * 
     * @return  a {@code String} containing the domain name
     *
     * @see  #setDomain
     */
    public String getDomain() {
        return domain;
    }

    /**
     * Sets the maximum age of the cookie in seconds.
     *
     * <p> A positive value indicates that the cookie will expire
     * after that many seconds have passed. Note that the value is
     * the <i>maximum</i> age when the cookie will expire, not the cookie's
     * current age.
     *
     * <p> A negative value means that the cookie is not stored persistently
     * and will be deleted when the Web browser exits. A zero value causes the
     * cookie to be deleted.
     *
     * <p>
     *  设置Cookie的最长使用时间(以秒为单位)。
     * 
     *  <p>正值表示Cookie将在过去许多秒后到期。请注意,该值是Cookie到期时的<i>最大</i>年龄,而不是Cookie的当前年龄。
     * 
     *  <p>负值表示Cookie不会持久存储,并且会在Web浏览器退出时被删除。零值导致cookie被删除。
     * 
     * 
     * @param  expiry
     *         an integer specifying the maximum age of the cookie in seconds;
     *         if zero, the cookie should be discarded immediately; otherwise,
     *         the cookie's max age is unspecified.
     *
     * @see  #getMaxAge
     */
    public void setMaxAge(long expiry) {
        maxAge = expiry;
    }

    /**
     * Returns the maximum age of the cookie, specified in seconds. By default,
     * {@code -1} indicating the cookie will persist until browser shutdown.
     *
     * <p>
     *  返回Cookie的最大期限,以秒为单位。默认情况下,指示Cookie的{@code -1}将持续到浏览器关闭。
     * 
     * 
     * @return  an integer specifying the maximum age of the cookie in seconds
     *
     * @see  #setMaxAge
     */
    public long getMaxAge() {
        return maxAge;
    }

    /**
     * Specifies a path for the cookie to which the client should return
     * the cookie.
     *
     * <p> The cookie is visible to all the pages in the directory
     * you specify, and all the pages in that directory's subdirectories.
     * A cookie's path must include the servlet that set the cookie,
     * for example, <i>/catalog</i>, which makes the cookie
     * visible to all directories on the server under <i>/catalog</i>.
     *
     * <p> Consult RFC 2965 (available on the Internet) for more
     * information on setting path names for cookies.
     *
     * <p>
     *  指定客户端应向其返回Cookie的Cookie的路径。
     * 
     * <p>该Cookie对您指定的目录中的所有页面以及该目录的子目录中的所有页面都可见。
     *  Cookie的路径必须包含设置Cookie的servlet,例如<i> / catalog </i>,这使cookie可以通过<i> / catalog </i>下的服务器上的所有目录查看。
     * 
     *  <p>有关设置Cookie的路径名的详细信息,请参阅RFC 2965(可从Internet获得)。
     * 
     * 
     * @param  uri
     *         a {@code String} specifying a path
     *
     * @see  #getPath
     */
    public void setPath(String uri) {
        path = uri;
    }

    /**
     * Returns the path on the server to which the browser returns this cookie.
     * The cookie is visible to all subpaths on the server.
     *
     * <p>
     *  返回浏览器返回此Cookie的服务器上的路径。该cookie对服务器上的所有子路径都可见。
     * 
     * 
     * @return  a {@code String} specifying a path that contains a servlet name,
     *          for example, <i>/catalog</i>
     *
     * @see  #setPath
     */
    public String getPath() {
        return path;
    }

    /**
     * Indicates whether the cookie should only be sent using a secure protocol,
     * such as HTTPS or SSL.
     *
     * <p> The default value is {@code false}.
     *
     * <p>
     *  指示是否只应使用安全协议(例如HTTPS或SSL)发送Cookie。
     * 
     *  <p>默认值为{@code false}。
     * 
     * 
     * @param  flag
     *         If {@code true}, the cookie can only be sent over a secure
     *         protocol like HTTPS. If {@code false}, it can be sent over
     *         any protocol.
     *
     * @see  #getSecure
     */
    public void setSecure(boolean flag) {
        secure = flag;
    }

    /**
     * Returns {@code true} if sending this cookie should be restricted to a
     * secure protocol, or {@code false} if the it can be sent using any
     * protocol.
     *
     * <p>
     *  如果发送此Cookie应限制为安全协议,则返回{@code true};如果可以使用任何协议发送,则返回{@code false}。
     * 
     * 
     * @return  {@code false} if the cookie can be sent over any standard
     *          protocol; otherwise, {@code true}
     *
     * @see  #setSecure
     */
    public boolean getSecure() {
        return secure;
    }

    /**
     * Returns the name of the cookie. The name cannot be changed after
     * creation.
     *
     * <p>
     *  返回Cookie的名称。创建后不能更改名称。
     * 
     * 
     * @return  a {@code String} specifying the cookie's name
     */
    public String getName() {
        return name;
    }

    /**
     * Assigns a new value to a cookie after the cookie is created.
     * If you use a binary value, you may want to use BASE64 encoding.
     *
     * <p> With Version 0 cookies, values should not contain white space,
     * brackets, parentheses, equals signs, commas, double quotes, slashes,
     * question marks, at signs, colons, and semicolons. Empty values may not
     * behave the same way on all browsers.
     *
     * <p>
     *  创建cookie后,为cookie分配一个新值。如果使用二进制值,则可能需要使用BASE64编码。
     * 
     *  <p>对于版本0 Cookie,值不应包含空格,括号,括号,等号,逗号,双引号,斜杠,问号,符号,冒号和分号。空值在所有浏览器上的行为可能不同。
     * 
     * 
     * @param  newValue
     *         a {@code String} specifying the new value
     *
     * @see  #getValue
     */
    public void setValue(String newValue) {
        value = newValue;
    }

    /**
     * Returns the value of the cookie.
     *
     * <p>
     *  返回Cookie的值。
     * 
     * 
     * @return  a {@code String} containing the cookie's present value
     *
     * @see  #setValue
     */
    public String getValue() {
        return value;
    }

    /**
     * Returns the version of the protocol this cookie complies with. Version 1
     * complies with RFC 2965/2109, and version 0 complies with the original
     * cookie specification drafted by Netscape. Cookies provided by a browser
     * use and identify the browser's cookie version.
     *
     * <p>
     * 返回此Cookie符合的协议版本。版本1符合RFC 2965/2109,版本0符合Netscape起草的原始cookie规范。浏览器提供的Cookie使用并标识浏览器的Cookie版本。
     * 
     * 
     * @return  0 if the cookie complies with the original Netscape
     *          specification; 1 if the cookie complies with RFC 2965/2109
     *
     * @see  #setVersion
     */
    public int getVersion() {
        return version;
    }

    /**
     * Sets the version of the cookie protocol this cookie complies
     * with. Version 0 complies with the original Netscape cookie
     * specification. Version 1 complies with RFC 2965/2109.
     *
     * <p>
     *  设置此cookie符合的cookie协议的版本。版本0符合原始的Netscape cookie规范。版本1符合RFC 2965/2109。
     * 
     * 
     * @param  v
     *         0 if the cookie should comply with the original Netscape
     *         specification; 1 if the cookie should comply with RFC 2965/2109
     *
     * @throws  IllegalArgumentException
     *          if {@code v} is neither 0 nor 1
     *
     * @see  #getVersion
     */
    public void setVersion(int v) {
        if (v != 0 && v != 1) {
            throw new IllegalArgumentException("cookie version should be 0 or 1");
        }

        version = v;
    }

    /**
     * Returns {@code true} if this cookie contains the <i>HttpOnly</i>
     * attribute. This means that the cookie should not be accessible to
     * scripting engines, like javascript.
     *
     * <p>
     *  如果此Cookie包含<i> HttpOnly </i>属性,则返回{@code true}。这意味着cookie不应该被脚本引擎访问,比如javascript。
     * 
     * 
     * @return  {@code true} if this cookie should be considered HTTPOnly
     *
     * @see  #setHttpOnly(boolean)
     */
    public boolean isHttpOnly() {
        return httpOnly;
    }

    /**
     * Indicates whether the cookie should be considered HTTP Only. If set to
     * {@code true} it means the cookie should not be accessible to scripting
     * engines like javascript.
     *
     * <p>
     *  指示Cookie是否应被视为仅HTTP。如果设置为{@code true},这意味着cookie不应该像javascript这样的脚本引擎可以访问。
     * 
     * 
     * @param  httpOnly
     *         if {@code true} make the cookie HTTP only, i.e. only visible as
     *         part of an HTTP request.
     *
     * @see  #isHttpOnly()
     */
    public void setHttpOnly(boolean httpOnly) {
        this.httpOnly = httpOnly;
    }

    /**
     * The utility method to check whether a host name is in a domain or not.
     *
     * <p> This concept is described in the cookie specification.
     * To understand the concept, some terminologies need to be defined first:
     * <blockquote>
     * effective host name = hostname if host name contains dot<br>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;or = hostname.local if not
     * </blockquote>
     * <p>Host A's name domain-matches host B's if:
     * <blockquote><ul>
     *   <li>their host name strings string-compare equal; or</li>
     *   <li>A is a HDN string and has the form NB, where N is a non-empty
     *   name string, B has the form .B', and B' is a HDN string.  (So,
     *   x.y.com domain-matches .Y.com but not Y.com.)</li>
     * </ul></blockquote>
     *
     * <p>A host isn't in a domain (RFC 2965 sec. 3.3.2) if:
     * <blockquote><ul>
     *   <li>The value for the Domain attribute contains no embedded dots,
     *   and the value is not .local.</li>
     *   <li>The effective host name that derives from the request-host does
     *   not domain-match the Domain attribute.</li>
     *   <li>The request-host is a HDN (not IP address) and has the form HD,
     *   where D is the value of the Domain attribute, and H is a string
     *   that contains one or more dots.</li>
     * </ul></blockquote>
     *
     * <p>Examples:
     * <blockquote><ul>
     *   <li>A Set-Cookie2 from request-host y.x.foo.com for Domain=.foo.com
     *   would be rejected, because H is y.x and contains a dot.</li>
     *   <li>A Set-Cookie2 from request-host x.foo.com for Domain=.foo.com
     *   would be accepted.</li>
     *   <li>A Set-Cookie2 with Domain=.com or Domain=.com., will always be
     *   rejected, because there is no embedded dot.</li>
     *   <li>A Set-Cookie2 from request-host example for Domain=.local will
     *   be accepted, because the effective host name for the request-
     *   host is example.local, and example.local domain-matches .local.</li>
     * </ul></blockquote>
     *
     * <p>
     *  用于检查主机名是否位于域中的实用程序方法。
     * 
     *  <p>这个概念在cookie规范中描述。为了理解这个概念,需要首先定义一些术语：
     * <blockquote>
     *  有效主机名=主机名(如果主机名包含点)<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp
     * ;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; or = hostname.local如果没有。
     * </blockquote>
     *  <p>主机A的名称与主机B的域匹配,如果：<blockquote> <ul> <li>主机名字符串string-compare equal;或</li> <li> A是一个HDN字符串,其格式为NB,
     * 其中N是非空的名称字符串,B的格式为.B',B'是HDN字符串。
     *  (所以,x.y.com域名匹配.Y.com但不是Y.com。)</li> </ul> </blockquote>。
     * 
     * <p>主机不在域中(RFC 2965 sec。3.3.2)if：<blockquote> <ul> <li> Domain属性的值不包含嵌入的点,值不是.local。
     *  </li> <li>从请求主机派生的有效主机名不与域属性进行域匹配。</li> <li>请求主机是HDN(不是IP地址) HD,其中D是Domain属性的值,H是包含一个或多个点的字符串。
     * </li> </ul> </blockquote>。
     * 
     *  <p>示例：<blockquote> <ul> <li>对于Domain = .foo.com,来自请求主机yxfoo.com的Set-Cookie2将被拒绝,因为H是yx且包含点。
     * </li> <li>接受来自请求 - 主机x.foo.com的Domain = .foo.com的Set-Cookie2。
     * </li> <li>设置为Cookie = .com或Domain = .com的Set-Cookie2, </li> <li>将接受来自Domain = .local的请求主机示例的Set-Cooki
     * e2,因为请求主机的有效主机名是示例。
     * </li> <li>接受来自请求 - 主机x.foo.com的Domain = .foo.com的Set-Cookie2。
     *  local,and example.local domain-matches .local。</li> </ul> </blockquote>。
     * 
     * 
     * @param  domain
     *         the domain name to check host name with
     *
     * @param  host
     *         the host name in question
     *
     * @return  {@code true} if they domain-matches; {@code false} if not
     */
    public static boolean domainMatches(String domain, String host) {
        if (domain == null || host == null)
            return false;

        // if there's no embedded dot in domain and domain is not .local
        boolean isLocalDomain = ".local".equalsIgnoreCase(domain);
        int embeddedDotInDomain = domain.indexOf('.');
        if (embeddedDotInDomain == 0)
            embeddedDotInDomain = domain.indexOf('.', 1);
        if (!isLocalDomain
            && (embeddedDotInDomain == -1 ||
                embeddedDotInDomain == domain.length() - 1))
            return false;

        // if the host name contains no dot and the domain name
        // is .local or host.local
        int firstDotInHost = host.indexOf('.');
        if (firstDotInHost == -1 &&
            (isLocalDomain ||
             domain.equalsIgnoreCase(host + ".local"))) {
            return true;
        }

        int domainLength = domain.length();
        int lengthDiff = host.length() - domainLength;
        if (lengthDiff == 0) {
            // if the host name and the domain name are just string-compare euqal
            return host.equalsIgnoreCase(domain);
        }
        else if (lengthDiff > 0) {
            // need to check H & D component
            String H = host.substring(0, lengthDiff);
            String D = host.substring(lengthDiff);

            return (H.indexOf('.') == -1 && D.equalsIgnoreCase(domain));
        }
        else if (lengthDiff == -1) {
            // if domain is actually .host
            return (domain.charAt(0) == '.' &&
                        host.equalsIgnoreCase(domain.substring(1)));
        }

        return false;
    }

    /**
     * Constructs a cookie header string representation of this cookie,
     * which is in the format defined by corresponding cookie specification,
     * but without the leading "Cookie:" token.
     *
     * <p>
     *  构造此Cookie的Cookie标头字符串表示形式,其格式由相应的Cookie规范定义,但没有前导的"Cookie："标记。
     * 
     * 
     * @return  a string form of the cookie. The string has the defined format
     */
    @Override
    public String toString() {
        if (getVersion() > 0) {
            return toRFC2965HeaderString();
        } else {
            return toNetscapeHeaderString();
        }
    }

    /**
     * Test the equality of two HTTP cookies.
     *
     * <p> The result is {@code true} only if two cookies come from same domain
     * (case-insensitive), have same name (case-insensitive), and have same path
     * (case-sensitive).
     *
     * <p>
     *  测试两个HTTP Cookie的相等性。
     * 
     *  <p>结果是{@code true}仅当两个Cookie来自同一域(不区分大小写),具有相同的名称(不区分大小写),并且具有相同的路径(区分大小写)。
     * 
     * 
     * @return  {@code true} if two HTTP cookies equal to each other;
     *          otherwise, {@code false}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof HttpCookie))
            return false;
        HttpCookie other = (HttpCookie)obj;

        // One http cookie equals to another cookie (RFC 2965 sec. 3.3.3) if:
        //   1. they come from same domain (case-insensitive),
        //   2. have same name (case-insensitive),
        //   3. and have same path (case-sensitive).
        return equalsIgnoreCase(getName(), other.getName()) &&
               equalsIgnoreCase(getDomain(), other.getDomain()) &&
               Objects.equals(getPath(), other.getPath());
    }

    /**
     * Returns the hash code of this HTTP cookie. The result is the sum of
     * hash code value of three significant components of this cookie: name,
     * domain, and path. That is, the hash code is the value of the expression:
     * <blockquote>
     * getName().toLowerCase().hashCode()<br>
     * + getDomain().toLowerCase().hashCode()<br>
     * + getPath().hashCode()
     * </blockquote>
     *
     * <p>
     * 返回此HTTP Cookie的哈希码。结果是此cookie的三个重要组件的散列码值的总和：名称,域和路径。也就是说,哈希码是表达式的值：
     * <blockquote>
     *  getName()。toLowerCase()。hashCode()<br> + getDomain()。toLowerCase()。hashCode()<br> + getPath()。
     * hashCode。
     * </blockquote>
     * 
     * 
     * @return  this HTTP cookie's hash code
     */
    @Override
    public int hashCode() {
        int h1 = name.toLowerCase().hashCode();
        int h2 = (domain!=null) ? domain.toLowerCase().hashCode() : 0;
        int h3 = (path!=null) ? path.hashCode() : 0;

        return h1 + h2 + h3;
    }

    /**
     * Create and return a copy of this object.
     *
     * <p>
     *  创建并返回此对象的副本。
     * 
     * 
     * @return  a clone of this HTTP cookie
     */
    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // ---------------- Private operations --------------

    // Note -- disabled for now to allow full Netscape compatibility
    // from RFC 2068, token special case characters
    //
    // private static final String tspecials = "()<>@,;:\\\"/[]?={} \t";
    private static final String tspecials = ",; ";  // deliberately includes space

    /*
     * Tests a string and returns true if the string counts as a token.
     *
     * <p>
     *  测试字符串,如果字符串计数为令牌,则返回true。
     * 
     * 
     * @param  value
     *         the {@code String} to be tested
     *
     * @return  {@code true} if the {@code String} is a token;
     *          {@code false} if it is not
     */
    private static boolean isToken(String value) {
        int len = value.length();

        for (int i = 0; i < len; i++) {
            char c = value.charAt(i);

            if (c < 0x20 || c >= 0x7f || tspecials.indexOf(c) != -1)
                return false;
        }
        return true;
    }

    /*
     * Parse header string to cookie object.
     *
     * <p>
     *  将头字符串解析为cookie对象。
     * 
     * 
     * @param  header
     *         header string; should contain only one NAME=VALUE pair
     *
     * @return  an HttpCookie being extracted
     *
     * @throws  IllegalArgumentException
     *          if header string violates the cookie specification
     */
    private static HttpCookie parseInternal(String header,
                                            boolean retainHeader)
    {
        HttpCookie cookie = null;
        String namevaluePair = null;

        StringTokenizer tokenizer = new StringTokenizer(header, ";");

        // there should always have at least on name-value pair;
        // it's cookie's name
        try {
            namevaluePair = tokenizer.nextToken();
            int index = namevaluePair.indexOf('=');
            if (index != -1) {
                String name = namevaluePair.substring(0, index).trim();
                String value = namevaluePair.substring(index + 1).trim();
                if (retainHeader)
                    cookie = new HttpCookie(name,
                                            stripOffSurroundingQuote(value),
                                            header);
                else
                    cookie = new HttpCookie(name,
                                            stripOffSurroundingQuote(value));
            } else {
                // no "=" in name-value pair; it's an error
                throw new IllegalArgumentException("Invalid cookie name-value pair");
            }
        } catch (NoSuchElementException ignored) {
            throw new IllegalArgumentException("Empty cookie header string");
        }

        // remaining name-value pairs are cookie's attributes
        while (tokenizer.hasMoreTokens()) {
            namevaluePair = tokenizer.nextToken();
            int index = namevaluePair.indexOf('=');
            String name, value;
            if (index != -1) {
                name = namevaluePair.substring(0, index).trim();
                value = namevaluePair.substring(index + 1).trim();
            } else {
                name = namevaluePair.trim();
                value = null;
            }

            // assign attribute to cookie
            assignAttribute(cookie, name, value);
        }

        return cookie;
    }

    /*
     * assign cookie attribute value to attribute name;
     * use a map to simulate method dispatch
     * <p>
     *  将cookie属性值分配给属性名称;使用地图来模拟方法分派
     * 
     */
    static interface CookieAttributeAssignor {
            public void assign(HttpCookie cookie,
                               String attrName,
                               String attrValue);
    }
    static final java.util.Map<String, CookieAttributeAssignor> assignors =
            new java.util.HashMap<>();
    static {
        assignors.put("comment", new CookieAttributeAssignor() {
                public void assign(HttpCookie cookie,
                                   String attrName,
                                   String attrValue) {
                    if (cookie.getComment() == null)
                        cookie.setComment(attrValue);
                }
            });
        assignors.put("commenturl", new CookieAttributeAssignor() {
                public void assign(HttpCookie cookie,
                                   String attrName,
                                   String attrValue) {
                    if (cookie.getCommentURL() == null)
                        cookie.setCommentURL(attrValue);
                }
            });
        assignors.put("discard", new CookieAttributeAssignor() {
                public void assign(HttpCookie cookie,
                                   String attrName,
                                   String attrValue) {
                    cookie.setDiscard(true);
                }
            });
        assignors.put("domain", new CookieAttributeAssignor(){
                public void assign(HttpCookie cookie,
                                   String attrName,
                                   String attrValue) {
                    if (cookie.getDomain() == null)
                        cookie.setDomain(attrValue);
                }
            });
        assignors.put("max-age", new CookieAttributeAssignor(){
                public void assign(HttpCookie cookie,
                                   String attrName,
                                   String attrValue) {
                    try {
                        long maxage = Long.parseLong(attrValue);
                        if (cookie.getMaxAge() == MAX_AGE_UNSPECIFIED)
                            cookie.setMaxAge(maxage);
                    } catch (NumberFormatException ignored) {
                        throw new IllegalArgumentException(
                                "Illegal cookie max-age attribute");
                    }
                }
            });
        assignors.put("path", new CookieAttributeAssignor(){
                public void assign(HttpCookie cookie,
                                   String attrName,
                                   String attrValue) {
                    if (cookie.getPath() == null)
                        cookie.setPath(attrValue);
                }
            });
        assignors.put("port", new CookieAttributeAssignor(){
                public void assign(HttpCookie cookie,
                                   String attrName,
                                   String attrValue) {
                    if (cookie.getPortlist() == null)
                        cookie.setPortlist(attrValue == null ? "" : attrValue);
                }
            });
        assignors.put("secure", new CookieAttributeAssignor(){
                public void assign(HttpCookie cookie,
                                   String attrName,
                                   String attrValue) {
                    cookie.setSecure(true);
                }
            });
        assignors.put("httponly", new CookieAttributeAssignor(){
                public void assign(HttpCookie cookie,
                                   String attrName,
                                   String attrValue) {
                    cookie.setHttpOnly(true);
                }
            });
        assignors.put("version", new CookieAttributeAssignor(){
                public void assign(HttpCookie cookie,
                                   String attrName,
                                   String attrValue) {
                    try {
                        int version = Integer.parseInt(attrValue);
                        cookie.setVersion(version);
                    } catch (NumberFormatException ignored) {
                        // Just ignore bogus version, it will default to 0 or 1
                    }
                }
            });
        assignors.put("expires", new CookieAttributeAssignor(){ // Netscape only
                public void assign(HttpCookie cookie,
                                   String attrName,
                                   String attrValue) {
                    if (cookie.getMaxAge() == MAX_AGE_UNSPECIFIED) {
                        cookie.setMaxAge(cookie.expiryDate2DeltaSeconds(attrValue));
                    }
                }
            });
    }
    private static void assignAttribute(HttpCookie cookie,
                                        String attrName,
                                        String attrValue)
    {
        // strip off the surrounding "-sign if there's any
        attrValue = stripOffSurroundingQuote(attrValue);

        CookieAttributeAssignor assignor = assignors.get(attrName.toLowerCase());
        if (assignor != null) {
            assignor.assign(cookie, attrName, attrValue);
        } else {
            // Ignore the attribute as per RFC 2965
        }
    }

    static {
        sun.misc.SharedSecrets.setJavaNetHttpCookieAccess(
            new sun.misc.JavaNetHttpCookieAccess() {
                public List<HttpCookie> parse(String header) {
                    return HttpCookie.parse(header, true);
                }

                public String header(HttpCookie cookie) {
                    return cookie.header;
                }
            }
        );
    }

    /*
     * Returns the original header this cookie was consructed from, if it was
     * constructed by parsing a header, otherwise null.
     * <p>
     *  如果是通过解析头构造的,则返回这个cookie所绑定的原始头,否则返回null。
     * 
     */
    private String header() {
        return header;
    }

    /*
     * Constructs a string representation of this cookie. The string format is
     * as Netscape spec, but without leading "Cookie:" token.
     * <p>
     *  构造此cookie的字符串表示形式。字符串格式是Netscape规范,但没有前导"Cookie："令牌。
     * 
     */
    private String toNetscapeHeaderString() {
        return getName() + "=" + getValue();
    }

    /*
     * Constructs a string representation of this cookie. The string format is
     * as RFC 2965/2109, but without leading "Cookie:" token.
     * <p>
     *  构造此cookie的字符串表示形式。字符串格式为RFC 2965/2109,但没有前导"Cookie："令牌。
     * 
     */
    private String toRFC2965HeaderString() {
        StringBuilder sb = new StringBuilder();

        sb.append(getName()).append("=\"").append(getValue()).append('"');
        if (getPath() != null)
            sb.append(";$Path=\"").append(getPath()).append('"');
        if (getDomain() != null)
            sb.append(";$Domain=\"").append(getDomain()).append('"');
        if (getPortlist() != null)
            sb.append(";$Port=\"").append(getPortlist()).append('"');

        return sb.toString();
    }

    static final TimeZone GMT = TimeZone.getTimeZone("GMT");

    /*
    /* <p>
    /* 
     * @param  dateString
     *         a date string in one of the formats defined in Netscape cookie spec
     *
     * @return  delta seconds between this cookie's creation time and the time
     *          specified by dateString
     */
    private long expiryDate2DeltaSeconds(String dateString) {
        Calendar cal = new GregorianCalendar(GMT);
        for (int i = 0; i < COOKIE_DATE_FORMATS.length; i++) {
            SimpleDateFormat df = new SimpleDateFormat(COOKIE_DATE_FORMATS[i],
                                                       Locale.US);
            cal.set(1970, 0, 1, 0, 0, 0);
            df.setTimeZone(GMT);
            df.setLenient(false);
            df.set2DigitYearStart(cal.getTime());
            try {
                cal.setTime(df.parse(dateString));
                if (!COOKIE_DATE_FORMATS[i].contains("yyyy")) {
                    // 2-digit years following the standard set
                    // out it rfc 6265
                    int year = cal.get(Calendar.YEAR);
                    year %= 100;
                    if (year < 70) {
                        year += 2000;
                    } else {
                        year += 1900;
                    }
                    cal.set(Calendar.YEAR, year);
                }
                return (cal.getTimeInMillis() - whenCreated) / 1000;
            } catch (Exception e) {
                // Ignore, try the next date format
            }
        }
        return 0;
    }

    /*
     * try to guess the cookie version through set-cookie header string
     * <p>
     *  尝试通过set-cookie头字符串猜测cookie版本
     * 
     */
    private static int guessCookieVersion(String header) {
        int version = 0;

        header = header.toLowerCase();
        if (header.indexOf("expires=") != -1) {
            // only netscape cookie using 'expires'
            version = 0;
        } else if (header.indexOf("version=") != -1) {
            // version is mandatory for rfc 2965/2109 cookie
            version = 1;
        } else if (header.indexOf("max-age") != -1) {
            // rfc 2965/2109 use 'max-age'
            version = 1;
        } else if (startsWithIgnoreCase(header, SET_COOKIE2)) {
            // only rfc 2965 cookie starts with 'set-cookie2'
            version = 1;
        }

        return version;
    }

    private static String stripOffSurroundingQuote(String str) {
        if (str != null && str.length() > 2 &&
            str.charAt(0) == '"' && str.charAt(str.length() - 1) == '"') {
            return str.substring(1, str.length() - 1);
        }
        if (str != null && str.length() > 2 &&
            str.charAt(0) == '\'' && str.charAt(str.length() - 1) == '\'') {
            return str.substring(1, str.length() - 1);
        }
        return str;
    }

    private static boolean equalsIgnoreCase(String s, String t) {
        if (s == t) return true;
        if ((s != null) && (t != null)) {
            return s.equalsIgnoreCase(t);
        }
        return false;
    }

    private static boolean startsWithIgnoreCase(String s, String start) {
        if (s == null || start == null) return false;

        if (s.length() >= start.length() &&
                start.equalsIgnoreCase(s.substring(0, start.length()))) {
            return true;
        }

        return false;
    }

    /*
     * Split cookie header string according to rfc 2965:
     *   1) split where it is a comma;
     *   2) but not the comma surrounding by double-quotes, which is the comma
     *      inside port list or embeded URIs.
     *
     * <p>
     *  根据rfc 2965拆分cookie头字符串：1)split它是逗号; 2),而不是用双引号括起来的逗号,这是端口列表或嵌入的URI中的逗号。
     * 
     * @param  header
     *         the cookie header string to split
     *
     * @return  list of strings; never null
     */
    private static List<String> splitMultiCookies(String header) {
        List<String> cookies = new java.util.ArrayList<String>();
        int quoteCount = 0;
        int p, q;

        for (p = 0, q = 0; p < header.length(); p++) {
            char c = header.charAt(p);
            if (c == '"') quoteCount++;
            if (c == ',' && (quoteCount % 2 == 0)) {
                // it is comma and not surrounding by double-quotes
                cookies.add(header.substring(q, p));
                q = p + 1;
            }
        }

        cookies.add(header.substring(q));

        return cookies;
    }
}
