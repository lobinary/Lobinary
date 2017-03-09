/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2011, Oracle and/or its affiliates. All rights reserved.
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

package javax.naming.ldap;

import java.util.Iterator;
import java.security.AccessController;
import java.security.PrivilegedAction;
import javax.naming.ConfigurationException;
import javax.naming.NamingException;
import com.sun.naming.internal.VersionHelper;
import java.util.ServiceLoader;
import java.util.ServiceConfigurationError;

/**
 * This class implements the LDAPv3 Extended Request for StartTLS as
 * defined in
 * <a href="http://www.ietf.org/rfc/rfc2830.txt">Lightweight Directory
 * Access Protocol (v3): Extension for Transport Layer Security</a>
 *
 * The object identifier for StartTLS is 1.3.6.1.4.1.1466.20037
 * and no extended request value is defined.
 *<p>
 * <tt>StartTlsRequest</tt>/<tt>StartTlsResponse</tt> are used to establish
 * a TLS connection over the existing LDAP connection associated with
 * the JNDI context on which <tt>extendedOperation()</tt> is invoked.
 * Typically, a JNDI program uses these classes as follows.
 * <blockquote><pre>
 * import javax.naming.ldap.*;
 *
 * // Open an LDAP association
 * LdapContext ctx = new InitialLdapContext();
 *
 * // Perform a StartTLS extended operation
 * StartTlsResponse tls =
 *     (StartTlsResponse) ctx.extendedOperation(new StartTlsRequest());
 *
 * // Open a TLS connection (over the existing LDAP association) and get details
 * // of the negotiated TLS session: cipher suite, peer certificate, etc.
 * SSLSession session = tls.negotiate();
 *
 * // ... use ctx to perform protected LDAP operations
 *
 * // Close the TLS connection (revert back to the underlying LDAP association)
 * tls.close();
 *
 * // ... use ctx to perform unprotected LDAP operations
 *
 * // Close the LDAP association
 * ctx.close;
 * </pre></blockquote>
 *
 * <p>
 *  此类实现了<a href="http://www.ietf.org/rfc/rfc2830.txt">轻量级目录访问协议(v3)中定义的LDAPv3扩展请求StartTLS：传输层安全性扩展</a >
 * 。
 * 
 *  StartTLS的对象标识符是1.3.6.1.4.1.1466.20037,并且没有定义扩展请求值。
 * p>
 *  <tt> StartTlsRequest </tt> / <tt> StartTlsResponse </tt>用于通过与调用<tt> extendedOperation()</tt>的JNDI上下文
 * 相关联的现有LDAP连接建立TLS连接。
 * 通常,JNDI程序如下使用这些类。 <blockquote> <pre> import javax.naming.ldap。*;。
 * 
 *  //打开LDAP关联LdapContext ctx = new InitialLdapContext();
 * 
 *  //执行StartTLS扩展操作StartTlsResponse tls =(StartTlsResponse)ctx.extendedOperation(new StartTlsRequest())
 * ;。
 * 
 *  //打开TLS连接(通过现有的LDAP关联)并获取协商的TLS会话的详细信息//加密套件,对等证书等SSLSession session = tls.negotiate();
 * 
 *  // ...使用ctx执行受保护的LDAP操作
 * 
 *  //关闭TLS连接(恢复到底层LDAP关联)tls.close();
 * 
 *  // ...使用ctx执行不受保护的LDAP操作
 * 
 *  //关闭LDAP关联ctx.close; </pre> </blockquote>
 * 
 * 
 * @since 1.4
 * @see StartTlsResponse
 * @author Vincent Ryan
 */
public class StartTlsRequest implements ExtendedRequest {

    // Constant

    /**
     * The StartTLS extended request's assigned object identifier
     * is 1.3.6.1.4.1.1466.20037.
     * <p>
     * StartTLS扩展请求的分配的对象标识符是1.3.6.1.4.1.1466.20037。
     * 
     */
    public static final String OID = "1.3.6.1.4.1.1466.20037";


    // Constructors

    /**
     * Constructs a StartTLS extended request.
     * <p>
     *  构造一个StartTLS扩展请求。
     * 
     */
    public StartTlsRequest() {
    }


    // ExtendedRequest methods

    /**
     * Retrieves the StartTLS request's object identifier string.
     *
     * <p>
     *  检索StartTLS请求的对象标识符字符串。
     * 
     * 
     * @return The object identifier string, "1.3.6.1.4.1.1466.20037".
     */
    public String getID() {
        return OID;
    }

    /**
     * Retrieves the StartTLS request's ASN.1 BER encoded value.
     * Since the request has no defined value, null is always
     * returned.
     *
     * <p>
     *  检索StartTLS请求的ASN.1 BER编码值。由于请求没有定义的值,因此总是返回null。
     * 
     * 
     * @return The null value.
     */
    public byte[] getEncodedValue() {
        return null;
    }

    /**
     * Creates an extended response object that corresponds to the
     * LDAP StartTLS extended request.
     * <p>
     * The result must be a concrete subclass of StartTlsResponse
     * and must have a public zero-argument constructor.
     * <p>
     * This method locates the implementation class by locating
     * configuration files that have the name:
     * <blockquote><tt>
     *     META-INF/services/javax.naming.ldap.StartTlsResponse
     * </tt></blockquote>
     * The configuration files and their corresponding implementation classes must
     * be accessible to the calling thread's context class loader.
     * <p>
     * Each configuration file should contain a list of fully-qualified class
     * names, one per line.  Space and tab characters surrounding each name, as
     * well as blank lines, are ignored.  The comment character is <tt>'#'</tt>
     * (<tt>0x23</tt>); on each line all characters following the first comment
     * character are ignored.  The file must be encoded in UTF-8.
     * <p>
     * This method will return an instance of the first implementation
     * class that it is able to load and instantiate successfully from
     * the list of class names collected from the configuration files.
     * This method uses the calling thread's context classloader to find the
     * configuration files and to load the implementation class.
     * <p>
     * If no class can be found in this way, this method will use
     * an implementation-specific way to locate an implementation.
     * If none is found, a NamingException is thrown.
     *
     * <p>
     *  创建与LDAP StartTLS扩展请求对应的扩展响应对象。
     * <p>
     *  结果必须是StartTlsResponse的具体子类,并且必须有一个公共的零参数构造函数。
     * <p>
     *  此方法通过定位具有名称的配置文件来定位实现类：<blockquote> <tt> META-INF / services / javax.naming.ldap.StartTlsResponse </tt>
     *  </blockquote>配置文件及其对应的实现类必须可以被调用线程的上下文类加载器访问。
     * <p>
     *  每个配置文件应包含一个完全限定类名的列表,每行一个。忽略每个名称周围的空格和制表符以及空行。
     * 注释字符为<tt>'#'</tt>(<tt> 0x23 </tt>);在每行上,忽略第一个注释字符后面的所有字符。该文件必须以UTF-8编码。
     * <p>
     * 
     * @param id         The object identifier of the extended response.
     *                   Its value must be "1.3.6.1.4.1.1466.20037" or null.
     *                   Both values are equivalent.
     * @param berValue   The possibly null ASN.1 BER encoded value of the
     *                   extended response. This is the raw BER bytes
     *                   including the tag and length of the response value.
     *                   It does not include the response OID.
     *                   Its value is ignored because a Start TLS response
     *                   is not expected to contain any response value.
     * @param offset     The starting position in berValue of the bytes to use.
     *                   Its value is ignored because a Start TLS response
     *                   is not expected to contain any response value.
     * @param length     The number of bytes in berValue to use.
     *                   Its value is ignored because a Start TLS response
     *                   is not expected to contain any response value.
     * @return           The StartTLS extended response object.
     * @exception        NamingException If a naming exception was encountered
     *                   while creating the StartTLS extended response object.
     */
    public ExtendedResponse createExtendedResponse(String id, byte[] berValue,
        int offset, int length) throws NamingException {

        // Confirm that the object identifier is correct
        if ((id != null) && (!id.equals(OID))) {
            throw new ConfigurationException(
                "Start TLS received the following response instead of " +
                OID + ": " + id);
        }

        StartTlsResponse resp = null;

        ServiceLoader<StartTlsResponse> sl = ServiceLoader.load(
                StartTlsResponse.class, getContextClassLoader());
        Iterator<StartTlsResponse> iter = sl.iterator();

        while (resp == null && privilegedHasNext(iter)) {
            resp = iter.next();
        }
        if (resp != null) {
            return resp;
        }
        try {
            VersionHelper helper = VersionHelper.getVersionHelper();
            Class<?> clas = helper.loadClass(
                "com.sun.jndi.ldap.ext.StartTlsResponseImpl");

            resp = (StartTlsResponse) clas.newInstance();

        } catch (IllegalAccessException e) {
            throw wrapException(e);

        } catch (InstantiationException e) {
            throw wrapException(e);

        } catch (ClassNotFoundException e) {
            throw wrapException(e);
        }

        return resp;
    }

    /*
     * Wrap an exception, thrown while attempting to load the StartTlsResponse
     * class, in a configuration exception.
     * <p>
     * 此方法将返回第一个实现类的实例,它可以从配置文件中收集的类名列表中成功加载和实例化。此方法使用调用线程的上下文类加载器来查找配置文件并加载实现类。
     * <p>
     *  如果没有以这种方式找到类,这个方法将使用一种实现特定的方式来定位一个实现。如果没有找到,则抛出NamingException。
     * 
     */
    private ConfigurationException wrapException(Exception e) {
        ConfigurationException ce = new ConfigurationException(
            "Cannot load implementation of javax.naming.ldap.StartTlsResponse");

        ce.setRootCause(e);
        return ce;
    }

    /*
     * Acquire the class loader associated with this thread.
     * <p>
     *  在尝试加载StartTlsResponse类时抛出异常,位于配置异常中。
     * 
     */
    private final ClassLoader getContextClassLoader() {
        return AccessController.doPrivileged(
            new PrivilegedAction<ClassLoader>() {
                public ClassLoader run() {
                    return Thread.currentThread().getContextClassLoader();
                }
            }
        );
    }

    private final static boolean privilegedHasNext(final Iterator<StartTlsResponse> iter) {
        Boolean answer = AccessController.doPrivileged(
            new PrivilegedAction<Boolean>() {
            public Boolean run() {
                return Boolean.valueOf(iter.hasNext());
            }
        });
        return answer.booleanValue();
    }

    private static final long serialVersionUID = 4441679576360753397L;
}
