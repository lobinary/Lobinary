/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.security.Permission;

/**
 * Represents permission to access a resource or set of resources defined by a
 * given url, and for a given set of user-settable request methods
 * and request headers. The <i>name</i> of the permission is the url string.
 * The <i>actions</i> string is a concatenation of the request methods and headers.
 * The range of method and header names is not restricted by this class.
 * <p><b>The url</b><p>
 * The url string has the following expected structure.
 * <pre>
 *     scheme : // authority [ / path ]
 * </pre>
 * <i>scheme</i> will typically be http or https, but is not restricted by this
 * class.
 * <i>authority</i> is specified as:
 * <pre>
 *     authority = [ userinfo @ ] hostrange [ : portrange ]
 *     portrange = portnumber | -portnumber | portnumber-[portnumber] | *
 *     hostrange = ([*.] dnsname) | IPv4address | IPv6address
 * </pre>
 * <i>dnsname</i> is a standard DNS host or domain name, ie. one or more labels
 * separated by ".". <i>IPv4address</i> is a standard literal IPv4 address and
 * <i>IPv6address</i> is as defined in <a href="http://www.ietf.org/rfc/rfc2732.txt">
 * RFC 2732</a>. Literal IPv6 addresses must however, be enclosed in '[]' characters.
 * The <i>dnsname</i> specification can be preceded by "*." which means
 * the name will match any hostname whose right-most domain labels are the same as
 * this name. For example, "*.oracle.com" matches "foo.bar.oracle.com"
 * <p>
 * <i>portrange</i> is used to specify a port number, or a bounded or unbounded range of ports
 * that this permission applies to. If portrange is absent or invalid, then a default
 * port number is assumed if the scheme is {@code http} (default 80) or {@code https}
 * (default 443). No default is assumed for other schemes. A wildcard may be specified
 * which means all ports.
 * <p>
 * <i>userinfo</i> is optional. A userinfo component if present, is ignored when
 * creating a URLPermission, and has no effect on any other methods defined by this class.
 * <p>
 * The <i>path</i> component comprises a sequence of path segments,
 * separated by '/' characters. <i>path</i> may also be empty. The path is specified
 * in a similar way to the path in {@link java.io.FilePermission}. There are
 * three different ways as the following examples show:
 * <table border>
 * <caption>URL Examples</caption>
 * <tr><th>Example url</th><th>Description</th></tr>
 * <tr><td style="white-space:nowrap;">http://www.oracle.com/a/b/c.html</td>
 *   <td>A url which identifies a specific (single) resource</td>
 * </tr>
 * <tr><td>http://www.oracle.com/a/b/*</td>
 *   <td>The '*' character refers to all resources in the same "directory" - in
 *       other words all resources with the same number of path components, and
 *       which only differ in the final path component, represented by the '*'.
 *   </td>
 * </tr>
 * <tr><td>http://www.oracle.com/a/b/-</td>
 *   <td>The '-' character refers to all resources recursively below the
 *       preceding path (eg. http://www.oracle.com/a/b/c/d/e.html matches this
 *       example).
 *   </td>
 * </tr>
 * </table>
 * <p>
 * The '*' and '-' may only be specified in the final segment of a path and must be
 * the only character in that segment. Any query or fragment components of the
 * url are ignored when constructing URLPermissions.
 * <p>
 * As a special case, urls of the form, "scheme:*" are accepted to
 * mean any url of the given scheme.
 * <p>
 * The <i>scheme</i> and <i>authority</i> components of the url string are handled
 * without regard to case. This means {@link #equals(Object)},
 * {@link #hashCode()} and {@link #implies(Permission)} are case insensitive with respect
 * to these components. If the <i>authority</i> contains a literal IP address,
 * then the address is normalized for comparison. The path component is case sensitive.
 * <p><b>The actions string</b><p>
 * The actions string of a URLPermission is a concatenation of the <i>method list</i>
 * and the <i>request headers list</i>. These are lists of the permitted request
 * methods and permitted request headers of the permission (respectively). The two lists
 * are separated by a colon ':' character and elements of each list are comma separated.
 * Some examples are:
 * <pre>
 *         "POST,GET,DELETE"
 *         "GET:X-Foo-Request,X-Bar-Request"
 *         "POST,GET:Header1,Header2"
 * </pre>
 * The first example specifies the methods: POST, GET and DELETE, but no request headers.
 * The second example specifies one request method and two headers. The third
 * example specifies two request methods, and two headers.
 * <p>
 * The colon separator need not be present if the request headers list is empty.
 * No white-space is permitted in the actions string. The action strings supplied to
 * the URLPermission constructors are case-insensitive and are normalized by converting
 * method names to upper-case and header names to the form defines in RFC2616 (lower case
 * with initial letter of each word capitalized). Either list can contain a wild-card '*'
 * character which signifies all request methods or headers respectively.
 * <p>
 * Note. Depending on the context of use, some request methods and headers may be permitted
 * at all times, and others may not be permitted at any time. For example, the
 * HTTP protocol handler might disallow certain headers such as Content-Length
 * from being set by application code, regardless of whether the security policy
 * in force, permits it.
 *
 * <p>
 *  表示访问由给定url定义的资源或资源集的权限,以及对于给定的一组用户可设置的请求方法和请求标头。权限的<i>名称</i>是url字符串。
 *  <i> actions </i>字符串是请求方法和标头的连接。方法的范围和标题名称不受此类限制。 <p> <b>网址</b> <p>网址字符串具有以下预期结构。
 * <pre>
 *  scheme：// authority [/ path]
 * </pre>
 *  <i>方案</i>通常为http或https,但不受此类限制。 <i>权限</i>指定为：
 * <pre>
 *  authority = [userinfo @] hostrange [：portrange] portrange = portnumber | -portnumber | portnumber- [
 * portnumber] | * hostrange =([*。
 * ] dnsname)| IPv4地址IPv6地址。
 * </pre>
 *  <i> dnsname </i>是标准DNS主机或域名,即。由"。"分隔的一个或多个标签。
 *  <i> IPv4地址</i>是标准的文字IPv4地址,<i> IPv6地址</i>与<a href="http://www.ietf.org/rfc/rfc2732.txt"> RFC 2732 </a>
 * 。
 *  <i> dnsname </i>是标准DNS主机或域名,即。由"。"分隔的一个或多个标签。但是,字面IPv6地址必须用"[]"字符括起来。 <i> dnsname </i>规范可以在前面加上"*"。
 * 这意味着该名称将匹配任何主机名,其最右边的域标签与此名称相同。例如,"* .oracle.com"匹配"foo.bar.oracle.com"。
 * <p>
 * <i> portrange </i>用于指定此权限适用于的端口号,或有界或无界端口范围。
 * 如果portrange不存在或无效,则如果方案是{@code http}(默认值80)或{@code https}(默认值443),则采用默认端口号。其他方案不假设为默认值。
 * 可以指定通配符,这意味着所有端口。
 * <p>
 *  <i> userinfo </i>是可选的。如果存在userinfo组件,则在创建URLPermission时将被忽略,并且对此类定义的任何其他方法没有影响。
 * <p>
 *  <i>路径</i>组件包括由'/'字符分隔的路径段序列。 <i>路径</i>也可能为空。路径以与{@link java.io.FilePermission}中的路径类似的方式指定。
 * 有三种不同的方法,如下面的例子所示：。
 * <table border>
 *  <caption>网址示例</caption> <tr> <th>示例网址</th> <th>描述</th> </tr> <tr> <td style ="white-space：nowrap;"> 
 * http ：//www.oracle.com/a/b/c.html </td> <td>标识特定(单一)资源的网址</td>。
 * </tr>
 *  <tr> <td> http://www.oracle.com/a/b/* </td> <td>"*"字符是指同一"目录"中的所有资源 - 换句话说,所有资源相同数量的路径分量,并且仅在最终路径分量不
 * 同,由'*'表示。
 * </td>
 * </tr>
 *  < - > <td> http://www.oracle.com/a/b/- </td> <td>" - "字符指的是在前面路径下方递归的所有资源(例如http：// www .oracle.com 
 * / a / b / c / d / e.html匹配此示例)。
 * </td>
 * </tr>
 * </table>
 * <p>
 * '*'和' - '只能在路径的最后一个段中指定,并且必须是该段中唯一的字符。构造URLPermissions时,将忽略URL的任何查询或片段组件。
 * <p>
 *  作为特殊情况,形式为"scheme：*"的网址被接受为指给定方案的任何网址。
 * <p>
 *  处理url字符串的<i> scheme </i>和<i> authority </i>组件不考虑大小写。
 * 这意味着{@link #equals(Object)},{@link #hashCode()}和{@link #implies(Permission)}对这些组件不区分大小写。
 * 如果<i>权限</i>包含文本IP地址,则将该地址标准化以进行比较。路径组件区分大小写。
 *  <p> <b>操作字符串</b> <p> URLPermission的操作字符串是<i>方法列表</i>和<i>请求标题列表</i>的连接。这些是允许的请求方法和允许的请求头的列表(分别)。
 * 这两个列表由冒号"："分隔,每个列表的元素以逗号分隔。一些例子是：。
 * <pre>
 *  "POST,GET,DELETE""GET：X-Foo-Request,X-Bar-Request""POST,GET：Header1,Header2"
 * </pre>
 *  第一个示例指定方法：POST,GET和DELETE,但没有请求标头。第二个示例指定一个请求方法和两个头。第三个示例指定两个请求方法和两个头。
 * <p>
 * 如果请求标头列表为空,则不需要出现冒号分隔符。操作字符串中不允许有空格。
 * 提供给URLPermission构造函数的操作字符串不区分大小写,并通过将方法名称转换为大写和标题名称,转换为RFC2616中定义的格式(小写,每个单词的大写字母为小写)进行规范化。
 * 
 * @since 1.8
 */
public final class URLPermission extends Permission {

    private static final long serialVersionUID = -2702463814894478682L;

    private transient String scheme;
    private transient String ssp;                 // scheme specific part
    private transient String path;
    private transient List<String> methods;
    private transient List<String> requestHeaders;
    private transient Authority authority;

    // serialized field
    private String actions;

    /**
     * Creates a new URLPermission from a url string and which permits the given
     * request methods and user-settable request headers.
     * The name of the permission is the url string it was created with. Only the scheme,
     * authority and path components of the url are used internally. Any fragment or query
     * components are ignored. The permissions action string is as specified above.
     *
     * <p>
     * 任一列表都可以包含通配符"*"字符,分别表示所有请求方法或标题。
     * <p>
     *  注意。根据使用的上下文,可以一直允许一些请求方法和报头,并且可以在任何时候不允许其他请求方法和报头。
     * 例如,HTTP协议处理程序可能不允许由应用程序代码设置某些头,例如Content-Length,而不管有效的安全策略是否允许。
     * 
     * 
     * @param url the url string
     *
     * @param actions the actions string
     *
     * @exception IllegalArgumentException if url is invalid or if actions contains white-space.
     */
    public URLPermission(String url, String actions) {
        super(url);
        init(actions);
    }

    private void init(String actions) {
        parseURI(getName());
        int colon = actions.indexOf(':');
        if (actions.lastIndexOf(':') != colon) {
            throw new IllegalArgumentException("invalid actions string");
        }

        String methods, headers;
        if (colon == -1) {
            methods = actions;
            headers = "";
        } else {
            methods = actions.substring(0, colon);
            headers = actions.substring(colon+1);
        }

        List<String> l = normalizeMethods(methods);
        Collections.sort(l);
        this.methods = Collections.unmodifiableList(l);

        l = normalizeHeaders(headers);
        Collections.sort(l);
        this.requestHeaders = Collections.unmodifiableList(l);

        this.actions = actions();
    }

    /**
     * Creates a URLPermission with the given url string and unrestricted
     * methods and request headers by invoking the two argument
     * constructor as follows: URLPermission(url, "*:*")
     *
     * <p>
     *  从URL字符串创建新的URLPermission,并允许给定的请求方法和用户可设置的请求头。权限的名称是用它创建的url字符串。只有内部使用url的scheme,authority和path组件。
     * 任何片段或查询组件都将被忽略。权限操作字符串如上所述。
     * 
     * 
     * @param url the url string
     *
     * @throws    IllegalArgumentException if url does not result in a valid {@link URI}
     */
    public URLPermission(String url) {
        this(url, "*:*");
    }

    /**
     * Returns the normalized method list and request
     * header list, in the form:
     * <pre>
     *      "method-names : header-names"
     * </pre>
     * <p>
     * where method-names is the list of methods separated by commas
     * and header-names is the list of permitted headers separated by commas.
     * There is no white space in the returned String. If header-names is empty
     * then the colon separator will not be present.
     * <p>
     *  通过调用以下两个参数构造函数,创建具有给定url字符串和非限制方法和请求头的URLPermission：URLPermission(url,"*：*")
     * 
     */
    public String getActions() {
        return actions;
    }

    /**
     * Checks if this URLPermission implies the given permission.
     * Specifically, the following checks are done as if in the
     * following sequence:
     * <ul>
     * <li>if 'p' is not an instance of URLPermission return false</li>
     * <li>if any of p's methods are not in this's method list, and if
     *     this's method list is not equal to "*", then return false.</li>
     * <li>if any of p's headers are not in this's request header list, and if
     *     this's request header list is not equal to "*", then return false.</li>
     * <li>if this's url scheme is not equal to p's url scheme return false</li>
     * <li>if the scheme specific part of this's url is '*' return true</li>
     * <li>if the set of hosts defined by p's url hostrange is not a subset of
     *     this's url hostrange then return false. For example, "*.foo.oracle.com"
     *     is a subset of "*.oracle.com". "foo.bar.oracle.com" is not
     *     a subset of "*.foo.oracle.com"</li>
     * <li>if the portrange defined by p's url is not a subset of the
     *     portrange defined by this's url then return false.
     * <li>if the path or paths specified by p's url are contained in the
     *     set of paths specified by this's url, then return true
     * <li>otherwise, return false</li>
     * </ul>
     * <p>Some examples of how paths are matched are shown below:
     * <table border>
     * <caption>Examples of Path Matching</caption>
     * <tr><th>this's path</th><th>p's path</th><th>match</th></tr>
     * <tr><td>/a/b</td><td>/a/b</td><td>yes</td></tr>
     * <tr><td>/a/b/*</td><td>/a/b/c</td><td>yes</td></tr>
     * <tr><td>/a/b/*</td><td>/a/b/c/d</td><td>no</td></tr>
     * <tr><td>/a/b/-</td><td>/a/b/c/d</td><td>yes</td></tr>
     * <tr><td>/a/b/-</td><td>/a/b/c/d/e</td><td>yes</td></tr>
     * <tr><td>/a/b/-</td><td>/a/b/c/*</td><td>yes</td></tr>
     * <tr><td>/a/b/*</td><td>/a/b/c/-</td><td>no</td></tr>
     * </table>
     * <p>
     *  返回标准化方法列表和请求标头列表,形式如下：
     * <pre>
     *  "method-names：header-names"
     * </pre>
     * <p>
     * 其中method-names是以逗号分隔的方法列表,头名称是允许的标头列表,以逗号分隔。在返回的字符串中没有空格。如果头名称为空,则冒号分隔符将不存在。
     * 
     */
    public boolean implies(Permission p) {
        if (! (p instanceof URLPermission)) {
            return false;
        }

        URLPermission that = (URLPermission)p;

        if (!this.methods.get(0).equals("*") &&
                Collections.indexOfSubList(this.methods, that.methods) == -1) {
            return false;
        }

        if (this.requestHeaders.isEmpty() && !that.requestHeaders.isEmpty()) {
            return false;
        }

        if (!this.requestHeaders.isEmpty() &&
            !this.requestHeaders.get(0).equals("*") &&
             Collections.indexOfSubList(this.requestHeaders,
                                        that.requestHeaders) == -1) {
            return false;
        }

        if (!this.scheme.equals(that.scheme)) {
            return false;
        }

        if (this.ssp.equals("*")) {
            return true;
        }

        if (!this.authority.implies(that.authority)) {
            return false;
        }

        if (this.path == null) {
            return that.path == null;
        }
        if (that.path == null) {
            return false;
        }

        if (this.path.endsWith("/-")) {
            String thisprefix = this.path.substring(0, this.path.length() - 1);
            return that.path.startsWith(thisprefix);
            }

        if (this.path.endsWith("/*")) {
            String thisprefix = this.path.substring(0, this.path.length() - 1);
            if (!that.path.startsWith(thisprefix)) {
                return false;
            }
            String thatsuffix = that.path.substring(thisprefix.length());
            // suffix must not contain '/' chars
            if (thatsuffix.indexOf('/') != -1) {
                return false;
            }
            if (thatsuffix.equals("-")) {
                return false;
            }
            return true;
        }
        return this.path.equals(that.path);
    }


    /**
     * Returns true if, this.getActions().equals(p.getActions())
     * and p's url equals this's url.  Returns false otherwise.
     * <p>
     *  检查此URLPermission是否暗示给定的权限。具体地,进行以下检查,如同以下列顺序：
     * <ul>
     *  <li>如果'p'不是URLPermission的实例,则返回false </li> <li>如果p的任何方法不在此方法列表中,并且此方法列表不等于"*" </li> <li>如果p的标头不在此请求标
     * 头列表中,并且此请求标头列表不等于"*",则返回false。
     * </li> <li>如果此网址方案不等于p的网址方案return false </li> <li>如果此网址的方案特定部分为"*",则返回true </li> <li>如果由p的url主机范围定义的主机
     * 集不是这个url hostrange的子集然后返回false。
     * 例如,"* .foo.oracle.com"是"* .oracle.com"的子集。
     *  "foo.bar.oracle.com"不是"* .foo.oracle.com"的子集</li> <li>如果p的网址定义的portrange不是由此网址定义的portrange的子集,则返回假。
     *  <li>如果p的网址指定的路径包含在由此网址指定的路径集中,则返回true <li>否则返回false </li>。
     * </ul>
     *  <p>路径匹配的一些示例如下所示：
     * <table border>
     * <caption>路径匹配示例</caption> <tr> <th>此路径</th> <th> p的路径</th> <th>匹配</th> </tr> <tr> <td> / a / b </td> 
     * <td> / a / b </td> <td>是</td> </tr> <tr> <td> / a / b / * </td> <td> / a / b / c </td> <td>是</td> </tr>
     *  <tr> <td> / a / b / * </td> <td> / td> <td> no </td> </tr> <tr> <td> / a / b /  -  </td> <td> / a / 
     * b / c / d </td> <td> </td> </td> </td> </td> / a / b / c / d / e </td> / tr> <tr> <td> / a / b /  -  
     * </td> <td> / a / b / c / * </td> <td>是</td> td> / a / b / * </td> <td> / a / b / c /  -  </td> <td> n
     */
    public boolean equals(Object p) {
        if (!(p instanceof URLPermission)) {
            return false;
        }
        URLPermission that = (URLPermission)p;
        if (!this.scheme.equals(that.scheme)) {
            return false;
        }
        if (!this.getActions().equals(that.getActions())) {
            return false;
        }
        if (!this.authority.equals(that.authority)) {
            return false;
        }
        if (this.path != null) {
            return this.path.equals(that.path);
        } else {
            return that.path == null;
        }
    }

    /**
     * Returns a hashcode calculated from the hashcode of the
     * actions String and the url string.
     * <p>
     * o </td> </tr>。
     * </table>
     */
    public int hashCode() {
        return getActions().hashCode()
            + scheme.hashCode()
            + authority.hashCode()
            + (path == null ? 0 : path.hashCode());
    }


    private List<String> normalizeMethods(String methods) {
        List<String> l = new ArrayList<>();
        StringBuilder b = new StringBuilder();
        for (int i=0; i<methods.length(); i++) {
            char c = methods.charAt(i);
            if (c == ',') {
                String s = b.toString();
                if (s.length() > 0)
                    l.add(s);
                b = new StringBuilder();
            } else if (c == ' ' || c == '\t') {
                throw new IllegalArgumentException("white space not allowed");
            } else {
                if (c >= 'a' && c <= 'z') {
                    c += 'A' - 'a';
                }
                b.append(c);
            }
        }
        String s = b.toString();
        if (s.length() > 0)
            l.add(s);
        return l;
    }

    private List<String> normalizeHeaders(String headers) {
        List<String> l = new ArrayList<>();
        StringBuilder b = new StringBuilder();
        boolean capitalizeNext = true;
        for (int i=0; i<headers.length(); i++) {
            char c = headers.charAt(i);
            if (c >= 'a' && c <= 'z') {
                if (capitalizeNext) {
                    c += 'A' - 'a';
                    capitalizeNext = false;
                }
                b.append(c);
            } else if (c == ' ' || c == '\t') {
                throw new IllegalArgumentException("white space not allowed");
            } else if (c == '-') {
                    capitalizeNext = true;
                b.append(c);
            } else if (c == ',') {
                String s = b.toString();
                if (s.length() > 0)
                    l.add(s);
                b = new StringBuilder();
                capitalizeNext = true;
            } else {
                capitalizeNext = false;
                b.append(c);
            }
        }
        String s = b.toString();
        if (s.length() > 0)
            l.add(s);
        return l;
    }

    private void parseURI(String url) {
        int len = url.length();
        int delim = url.indexOf(':');
        if (delim == -1 || delim + 1 == len) {
            throw new IllegalArgumentException("invalid URL string");
        }
        scheme = url.substring(0, delim).toLowerCase();
        this.ssp = url.substring(delim + 1);

        if (!ssp.startsWith("//")) {
            if (!ssp.equals("*")) {
                throw new IllegalArgumentException("invalid URL string");
            }
            this.authority = new Authority(scheme, "*");
            return;
        }
        String authpath = ssp.substring(2);

        delim = authpath.indexOf('/');
        String auth;
        if (delim == -1) {
            this.path = "";
            auth = authpath;
        } else {
            auth = authpath.substring(0, delim);
            this.path = authpath.substring(delim);
        }
        this.authority = new Authority(scheme, auth.toLowerCase());
    }

    private String actions() {
        StringBuilder b = new StringBuilder();
        for (String s : methods) {
            b.append(s);
        }
        b.append(":");
        for (String s : requestHeaders) {
            b.append(s);
        }
        return b.toString();
    }

    /**
     * restore the state of this object from stream
     * <p>
     *  String thisprefix = this.path.substring(0,this.path.length() -  1); if(！that.path.startsWith(thispre
     * fix)){return false; } String thatsuffix = that.path.substring(thisprefix.length()); //后缀不能包含'/'chars 
     * if(thatsuffix.indexOf('/')！= -1){return false; } if(thatsuffix.equals(" - ")){return false; } return 
     * true; } return this.path.equals(that.path); }}。
     * 
     *  / **返回true if,this.getActions()。equals(p.getActions()),p的url等于这个url。否则返回false。
     * 
     */
    private void readObject(ObjectInputStream s)
        throws IOException, ClassNotFoundException {
        ObjectInputStream.GetField fields = s.readFields();
        String actions = (String)fields.get("actions", null);

        init(actions);
    }

    static class Authority {
        HostPortrange p;

        Authority(String scheme, String authority) {
            int at = authority.indexOf('@');
            if (at == -1) {
                    p = new HostPortrange(scheme, authority);
            } else {
                    p = new HostPortrange(scheme, authority.substring(at+1));
            }
        }

        boolean implies(Authority other) {
            return impliesHostrange(other) && impliesPortrange(other);
        }

        private boolean impliesHostrange(Authority that) {
            String thishost = this.p.hostname();
            String thathost = that.p.hostname();

            if (p.wildcard() && thishost.equals("")) {
                // this "*" implies all others
                return true;
            }
            if (that.p.wildcard() && thathost.equals("")) {
                // that "*" can only be implied by this "*"
                return false;
            }
            if (thishost.equals(thathost)) {
                // covers all cases of literal IP addresses and fixed
                // domain names.
                return true;
            }
            if (this.p.wildcard()) {
                // this "*.foo.com" implies "bub.bar.foo.com"
                return thathost.endsWith(thishost);
            }
            return false;
        }

        private boolean impliesPortrange(Authority that) {
            int[] thisrange = this.p.portrange();
            int[] thatrange = that.p.portrange();
            if (thisrange[0] == -1) {
                /* port not specified non http/s URL */
                return true;
            }
            return thisrange[0] <= thatrange[0] &&
                        thisrange[1] >= thatrange[1];
        }

        boolean equals(Authority that) {
            return this.p.equals(that.p);
        }

        public int hashCode() {
            return p.hashCode();
        }
    }
}
