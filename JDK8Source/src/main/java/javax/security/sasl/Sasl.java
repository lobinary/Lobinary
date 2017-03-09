/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.security.sasl;

import javax.security.auth.callback.CallbackHandler;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;
import java.security.Provider;
import java.security.Security;

/**
 * A static class for creating SASL clients and servers.
 *<p>
 * This class defines the policy of how to locate, load, and instantiate
 * SASL clients and servers.
 *<p>
 * For example, an application or library gets a SASL client by doing
 * something like:
 *<blockquote><pre>
 * SaslClient sc = Sasl.createSaslClient(mechanisms,
 *     authorizationId, protocol, serverName, props, callbackHandler);
 *</pre></blockquote>
 * It can then proceed to use the instance to create an authentication connection.
 *<p>
 * Similarly, a server gets a SASL server by using code that looks as follows:
 *<blockquote><pre>
 * SaslServer ss = Sasl.createSaslServer(mechanism,
 *     protocol, serverName, props, callbackHandler);
 *</pre></blockquote>
 *
 * <p>
 *  用于创建SASL客户端和服务器的静态类。
 * p>
 *  此类定义如何查找,加载和实例化SASL客户端和服务器的策略。
 * p>
 *  例如,应用程序或库通过执行以下操作获取SASL客户机：blockquote> <pre> SaslClient sc = Sasl.createSaslClient(mechanisms,author
 * izationId,protocol,serverName,props,callbackHandler); / pre> </blockquote>然后可以继续使用实例来创建身份验证连接。
 * p>
 *  类似地,服务器通过使用如下代码获取SASL服务器：blockquote> <pre> SaslServer ss = Sasl.createSaslServer(mechanism,protocol,
 * serverName,props,callbackHandler); / pre> </blockquote>。
 * 
 * 
 * @since 1.5
 *
 * @author Rosanna Lee
 * @author Rob Weltman
 */
public class Sasl {
    // Cannot create one of these
    private Sasl() {
    }

    /**
     * The name of a property that specifies the quality-of-protection to use.
     * The property contains a comma-separated, ordered list
     * of quality-of-protection values that the
     * client or server is willing to support.  A qop value is one of
     * <ul>
     * <li>{@code "auth"} - authentication only</li>
     * <li>{@code "auth-int"} - authentication plus integrity protection</li>
     * <li>{@code "auth-conf"} - authentication plus integrity and confidentiality
     * protection</li>
     * </ul>
     *
     * The order of the list specifies the preference order of the client or
     * server. If this property is absent, the default qop is {@code "auth"}.
     * The value of this constant is {@code "javax.security.sasl.qop"}.
     * <p>
     *  指定要使用的保护质量的属性的名称。该属性包含客户端或服务器愿意支持的保护质量值的逗号分隔的有序列表。 qop值为以下之一
     * <ul>
     *  <li> {@ code"auth"}  - 仅验证</li> <li> {@ code"auth-int"}  - 验证加完整性保护</li> <li> {@ code"auth-conf"} - 
     * 身份验证加完整性和机密性保护</li>。
     * </ul>
     * 
     *  列表的顺序指定客户端或服务器的首选顺序。如果此属性不存在,则默认qop为{@code"auth"}。此常量的值为{@code"javax.security.sasl.qop"}。
     * 
     */
    public static final String QOP = "javax.security.sasl.qop";

    /**
     * The name of a property that specifies the cipher strength to use.
     * The property contains a comma-separated, ordered list
     * of cipher strength values that
     * the client or server is willing to support. A strength value is one of
     * <ul>
     * <li>{@code "low"}</li>
     * <li>{@code "medium"}</li>
     * <li>{@code "high"}</li>
     * </ul>
     * The order of the list specifies the preference order of the client or
     * server.  An implementation should allow configuration of the meaning
     * of these values.  An application may use the Java Cryptography
     * Extension (JCE) with JCE-aware mechanisms to control the selection of
     * cipher suites that match the strength values.
     * <BR>
     * If this property is absent, the default strength is
     * {@code "high,medium,low"}.
     * The value of this constant is {@code "javax.security.sasl.strength"}.
     * <p>
     * 指定要使用的密码强度的属性的名称。该属性包含客户端或服务器愿意支持的密码强度值的逗号分隔的有序列表。强度值为以下之一
     * <ul>
     *  <li> {@ code"low"} </li> <li> {@ code"medium"} </li>
     * </ul>
     *  列表的顺序指定客户端或服务器的首选顺序。实现应该允许配置这些值的含义。应用程序可以使用具有JCE感知机制的Java加密扩展(JCE)来控制与强度值匹配的密码套件的选择。
     * <BR>
     *  如果此属性不存在,默认强度为{@code"high,medium,low"}。此常量的值为{@code"javax.security.sasl.strength"}。
     * 
     */
    public static final String STRENGTH = "javax.security.sasl.strength";

    /**
     * The name of a property that specifies whether the
     * server must authenticate to the client. The property contains
     * {@code "true"} if the server must
     * authenticate the to client; {@code "false"} otherwise.
     * The default is {@code "false"}.
     * <br>The value of this constant is
     * {@code "javax.security.sasl.server.authentication"}.
     * <p>
     *  指定服务器是否必须向客户端进行身份验证的属性的名称。如果服务器必须对客户端进行身份验证,则该属性包含{@code"true"}; {@code"false"}。默认值为{@code"false"}。
     *  <br>此常数的值为{@code"javax.security.sasl.server.authentication"}。
     * 
     */
    public static final String SERVER_AUTH =
    "javax.security.sasl.server.authentication";

    /**
     * The name of a property that specifies the bound server name for
     * an unbound server. A server is created as an unbound server by setting
     * the {@code serverName} argument in {@link #createSaslServer} as null.
     * The property contains the bound host name after the authentication
     * exchange has completed. It is only available on the server side.
     * <br>The value of this constant is
     * {@code "javax.security.sasl.bound.server.name"}.
     * <p>
     * 指定未绑定服务器的绑定服务器名称的属性名称。通过将{@link #createSaslServer}中的{@code serverName}参数设置为null,可将服务器创建为未绑定的服务器。
     * 该属性在认证交换完成后包含绑定的主机名。它仅在服务器端可用。 <br>此常数的值为{@code"javax.security.sasl.bound.server.name"}。
     * 
     */
    public static final String BOUND_SERVER_NAME =
    "javax.security.sasl.bound.server.name";

    /**
     * The name of a property that specifies the maximum size of the receive
     * buffer in bytes of {@code SaslClient}/{@code SaslServer}.
     * The property contains the string representation of an integer.
     * <br>If this property is absent, the default size
     * is defined by the mechanism.
     * <br>The value of this constant is {@code "javax.security.sasl.maxbuffer"}.
     * <p>
     *  指定{@code SaslClient} / {@ code SaslServer}的接收缓冲区的最大大小(以字节为单位)的属性名称。该属性包含整数的字符串表示形式。
     *  <br>如果此属性不存在,则默认大小由机制定义。 <br>此常数的值为{@code"javax.security.sasl.maxbuffer"}。
     * 
     */
    public static final String MAX_BUFFER = "javax.security.sasl.maxbuffer";

    /**
     * The name of a property that specifies the maximum size of the raw send
     * buffer in bytes of {@code SaslClient}/{@code SaslServer}.
     * The property contains the string representation of an integer.
     * The value of this property is negotiated between the client and server
     * during the authentication exchange.
     * <br>The value of this constant is {@code "javax.security.sasl.rawsendsize"}.
     * <p>
     *  指定以{@code SaslClient} / {@ code SaslServer}为单位的原始发送缓冲区的最大大小的属性名称。该属性包含整数的字符串表示形式。
     * 在身份验证交换期间,在客户端和服务器之间协商此属性的值。 <br>此常数的值为{@code"javax.security.sasl.rawsendsize"}。
     * 
     */
    public static final String RAW_SEND_SIZE = "javax.security.sasl.rawsendsize";

    /**
     * The name of a property that specifies whether to reuse previously
     * authenticated session information. The property contains "true" if the
     * mechanism implementation may attempt to reuse previously authenticated
     * session information; it contains "false" if the implementation must
     * not reuse previously authenticated session information.  A setting of
     * "true" serves only as a hint: it does not necessarily entail actual
     * reuse because reuse might not be possible due to a number of reasons,
     * including, but not limited to, lack of mechanism support for reuse,
     * expiration of reusable information, and the peer's refusal to support
     * reuse.
     *
     * The property's default value is "false".  The value of this constant
     * is "javax.security.sasl.reuse".
     *
     * Note that all other parameters and properties required to create a
     * SASL client/server instance must be provided regardless of whether
     * this property has been supplied. That is, you cannot supply any less
     * information in anticipation of reuse.
     *
     * Mechanism implementations that support reuse might allow customization
     * of its implementation, for factors such as cache size, timeouts, and
     * criteria for reusability. Such customizations are
     * implementation-dependent.
     * <p>
     * 指定是否重用先前已验证的会话信息的属性名称。如果机制实现可以尝试重用先前认证的会话信息,则该属性包含"真";如果实现不能重用先前已验证的会话信息,则它包含"false"。
     *  "真"的设置仅用作提示：其不一定需要实际重用,因为重复可能由于多种原因而不可能,包括但不限于缺乏重用的机制支持,可重用信息的期满,以及对等体拒绝支持重用。
     * 
     *  属性的默认值为"false"。此常量的值为"javax.security.sasl.reuse"。
     * 
     *  请注意,无论是否提供了此属性,都必须提供创建SASL客户端/服务器实例所需的所有其他参数和属性。也就是说,您不能提供任何较少的信息预期重用。
     * 
     *  支持重用的机制实现可能允许定制其实现,例如高速缓存大小,超时和可重用性标准等因素。这样的定制是实现相关的。
     * 
     */
     public static final String REUSE = "javax.security.sasl.reuse";

    /**
     * The name of a property that specifies
     * whether mechanisms susceptible to simple plain passive attacks (e.g.,
     * "PLAIN") are not permitted. The property
     * contains {@code "true"} if such mechanisms are not permitted;
     * {@code "false"} if such mechanisms are permitted.
     * The default is {@code "false"}.
     * <br>The value of this constant is
     * {@code "javax.security.sasl.policy.noplaintext"}.
     * <p>
     * 指定是否不允许容易受到简单的简单被动攻击(例如,"PLAIN")的机制的属性名称。如果不允许这样的机制,则属性包含{@code"true"}; {@code"false"}如果允许这样的机制。
     * 默认值为{@code"false"}。 <br>此常数的值为{@code"javax.security.sasl.policy.noplaintext"}。
     * 
     */
    public static final String POLICY_NOPLAINTEXT =
    "javax.security.sasl.policy.noplaintext";

    /**
     * The name of a property that specifies whether
     * mechanisms susceptible to active (non-dictionary) attacks
     * are not permitted.
     * The property contains {@code "true"}
     * if mechanisms susceptible to active attacks
     * are not permitted; {@code "false"} if such mechanisms are permitted.
     * The default is {@code "false"}.
     * <br>The value of this constant is
     * {@code "javax.security.sasl.policy.noactive"}.
     * <p>
     *  指定是否不允许对活动(非字典)攻击敏感的机制的属性名称。如果不允许有主动攻击的机制,则该属性包含{@code"true"}; {@code"false"}如果允许这样的机制。
     * 默认值为{@code"false"}。 <br>此常数的值为{@code"javax.security.sasl.policy.noactive"}。
     * 
     */
    public static final String POLICY_NOACTIVE =
    "javax.security.sasl.policy.noactive";

    /**
     * The name of a property that specifies whether
     * mechanisms susceptible to passive dictionary attacks are not permitted.
     * The property contains {@code "true"}
     * if mechanisms susceptible to dictionary attacks are not permitted;
     * {@code "false"} if such mechanisms are permitted.
     * The default is {@code "false"}.
     *<br>
     * The value of this constant is
     * {@code "javax.security.sasl.policy.nodictionary"}.
     * <p>
     *  指定是否允许对被动字典攻击敏感的机制的属性名称。如果不允许对字典攻击敏感的机制,则该属性包含{@code"true"}; {@code"false"}如果允许这样的机制。
     * 默认值为{@code"false"}。
     * br>
     *  此常量的值为{@code"javax.security.sasl.policy.nodictionary"}。
     * 
     */
    public static final String POLICY_NODICTIONARY =
    "javax.security.sasl.policy.nodictionary";

    /**
     * The name of a property that specifies whether mechanisms that accept
     * anonymous login are not permitted. The property contains {@code "true"}
     * if mechanisms that accept anonymous login are not permitted;
     * {@code "false"}
     * if such mechanisms are permitted. The default is {@code "false"}.
     *<br>
     * The value of this constant is
     * {@code "javax.security.sasl.policy.noanonymous"}.
     * <p>
     *  指定是否不允许接受匿名登录的机制的属性名称。如果不允许接受匿名登录的机制,则该属性包含{@code"true"}; {@code"false"}如果允许这样的机制。
     * 默认值为{@code"false"}。
     * br>
     * 此常量的值为{@code"javax.security.sasl.policy.noanonymous"}。
     * 
     */
    public static final String POLICY_NOANONYMOUS =
    "javax.security.sasl.policy.noanonymous";

     /**
      * The name of a property that specifies whether mechanisms that implement
      * forward secrecy between sessions are required. Forward secrecy
      * means that breaking into one session will not automatically
      * provide information for breaking into future sessions.
      * The property
      * contains {@code "true"} if mechanisms that implement forward secrecy
      * between sessions are required; {@code "false"} if such mechanisms
      * are not required. The default is {@code "false"}.
      *<br>
      * The value of this constant is
      * {@code "javax.security.sasl.policy.forward"}.
      * <p>
      *  指定是否需要在会话之间实施前向保密的机制的属性名称。转发保密意味着分成一个会话将不会自动提供用于打入未来会话的信息。
      * 如果需要在会话之间实现前向保密的机制,则该属性包含{@code"true"}; {@code"false"}如果不需要这样的机制。默认值为{@code"false"}。
      * br>
      *  此常量的值为{@code"javax.security.sasl.policy.forward"}。
      * 
      */
    public static final String POLICY_FORWARD_SECRECY =
    "javax.security.sasl.policy.forward";

    /**
     * The name of a property that specifies whether
     * mechanisms that pass client credentials are required. The property
     * contains {@code "true"} if mechanisms that pass
     * client credentials are required; {@code "false"}
     * if such mechanisms are not required. The default is {@code "false"}.
     *<br>
     * The value of this constant is
     * {@code "javax.security.sasl.policy.credentials"}.
     * <p>
     *  指定是否需要传递客户端凭据的机制的属性名称。如果需要传递客户端凭据的机制,则该属性包含{@code"true"}; {@code"false"}如果不需要这样的机制。
     * 默认值为{@code"false"}。
     * br>
     *  此常量的值为{@code"javax.security.sasl.policy.credentials"}。
     * 
     */
    public static final String POLICY_PASS_CREDENTIALS =
    "javax.security.sasl.policy.credentials";

    /**
     * The name of a property that specifies the credentials to use.
     * The property contains a mechanism-specific Java credential object.
     * Mechanism implementations may examine the value of this property
     * to determine whether it is a class that they support.
     * The property may be used to supply credentials to a mechanism that
     * supports delegated authentication.
     *<br>
     * The value of this constant is
     * {@code "javax.security.sasl.credentials"}.
     * <p>
     *  指定要使用的凭据的属性的名称。该属性包含特定于机制的Java凭据对象。机制实现可以检查此属性的值,以确定它是否是它们支持的类。该属性可以用于向支持委托认证的机制提供凭证。
     * br>
     *  此常量的值为{@code"javax.security.sasl.credentials"}。
     * 
     */
    public static final String CREDENTIALS = "javax.security.sasl.credentials";

    /**
     * Creates a {@code SaslClient} using the parameters supplied.
     *
     * This method uses the
<a href="{@docRoot}/../technotes/guides/security/crypto/CryptoSpec.html#Provider">JCA Security Provider Framework</a>, described in the
     * "Java Cryptography Architecture API Specification &amp; Reference", for
     * locating and selecting a {@code SaslClient} implementation.
     *
     * First, it
     * obtains an ordered list of {@code SaslClientFactory} instances from
     * the registered security providers for the "SaslClientFactory" service
     * and the specified SASL mechanism(s). It then invokes
     * {@code createSaslClient()} on each factory instance on the list
     * until one produces a non-null {@code SaslClient} instance. It returns
     * the non-null {@code SaslClient} instance, or null if the search fails
     * to produce a non-null {@code SaslClient} instance.
     *<p>
     * A security provider for SaslClientFactory registers with the
     * JCA Security Provider Framework keys of the form <br>
     * {@code SaslClientFactory.}<em>{@code mechanism_name}</em>
     * <br>
     * and values that are class names of implementations of
     * {@code javax.security.sasl.SaslClientFactory}.
     *
     * For example, a provider that contains a factory class,
     * {@code com.wiz.sasl.digest.ClientFactory}, that supports the
     * "DIGEST-MD5" mechanism would register the following entry with the JCA:
     * {@code SaslClientFactory.DIGEST-MD5 com.wiz.sasl.digest.ClientFactory}
     *<p>
     * See the
     * "Java Cryptography Architecture API Specification &amp; Reference"
     * for information about how to install and configure security service
     *  providers.
     *
     * <p>
     *  使用提供的参数创建{@code SaslClient}。
     * 
     * 此方法使用<a href="{@docRoot}/../technotes/guides/security/crypto/CryptoSpec.html#Provider"> JCA安全提供程序框架</a>
     * ,如"Java加密架构API规范&amp; Reference",用于定位和选择{@code SaslClient}实现。
     * 
     *  首先,它从"SaslClientFactory"服务和指定的SASL机制的注册的安全提供程序获得{@code SaslClientFactory}实例的有序列表。
     * 然后它在列表上的每个工厂实例上调用{@code createSaslClient()},直到生成一个非空的{@code SaslClient}实例。
     * 它返回非空的{@code SaslClient}实例,如果搜索无法产生非空的{@code SaslClient}实例,则返回null。
     * p>
     *  SaslClientFactory的安全提供程序使用<br> {@code SaslClientFactory。
     * } <em> {@ code mechanism_name} </em>格式的JCA安全提供程序框架注册。
     * <br>
     *  和作为{@code javax.security.sasl.SaslClientFactory}的实现的类名的值。
     * 
     *  例如,包含支持"DIGEST-MD5"机制的工厂类{@code com.wiz.sasl.digest.ClientFactory}的提供程序将使用JCA注册以下条目：{@code SaslClientFactory.DIGEST- MD5 com.wiz.sasl.digest.ClientFactory}
     * 。
     * p>
     *  有关如何安装和配置安全服务提供程序的信息,请参阅"Java加密架构API规范和参考"。
     * 
     * 
     * @param mechanisms The non-null list of mechanism names to try. Each is the
     * IANA-registered name of a SASL mechanism. (e.g. "GSSAPI", "CRAM-MD5").
     * @param authorizationId The possibly null protocol-dependent
     * identification to be used for authorization.
     * If null or empty, the server derives an authorization
     * ID from the client's authentication credentials.
     * When the SASL authentication completes successfully,
     * the specified entity is granted access.
     *
     * @param protocol The non-null string name of the protocol for which
     * the authentication is being performed (e.g., "ldap").
     *
     * @param serverName The non-null fully-qualified host name of the server
     * to authenticate to.
     *
     * @param props The possibly null set of properties used to
     * select the SASL mechanism and to configure the authentication
     * exchange of the selected mechanism.
     * For example, if {@code props} contains the
     * {@code Sasl.POLICY_NOPLAINTEXT} property with the value
     * {@code "true"}, then the selected
     * SASL mechanism must not be susceptible to simple plain passive attacks.
     * In addition to the standard properties declared in this class,
     * other, possibly mechanism-specific, properties can be included.
     * Properties not relevant to the selected mechanism are ignored,
     * including any map entries with non-String keys.
     *
     * @param cbh The possibly null callback handler to used by the SASL
     * mechanisms to get further information from the application/library
     * to complete the authentication. For example, a SASL mechanism might
     * require the authentication ID, password and realm from the caller.
     * The authentication ID is requested by using a {@code NameCallback}.
     * The password is requested by using a {@code PasswordCallback}.
     * The realm is requested by using a {@code RealmChoiceCallback} if there is a list
     * of realms to choose from, and by using a {@code RealmCallback} if
     * the realm must be entered.
     *
     *@return A possibly null {@code SaslClient} created using the parameters
     * supplied. If null, cannot find a {@code SaslClientFactory}
     * that will produce one.
     *@exception SaslException If cannot create a {@code SaslClient} because
     * of an error.
     */
    public static SaslClient createSaslClient(
        String[] mechanisms,
        String authorizationId,
        String protocol,
        String serverName,
        Map<String,?> props,
        CallbackHandler cbh) throws SaslException {

        SaslClient mech = null;
        SaslClientFactory fac;
        String className;
        String mechName;

        for (int i = 0; i < mechanisms.length; i++) {
            if ((mechName=mechanisms[i]) == null) {
                throw new NullPointerException(
                    "Mechanism name cannot be null");
            } else if (mechName.length() == 0) {
                continue;
            }
            String mechFilter = "SaslClientFactory." + mechName;
            Provider[] provs = Security.getProviders(mechFilter);
            for (int j = 0; provs != null && j < provs.length; j++) {
                className = provs[j].getProperty(mechFilter);
                if (className == null) {
                    // Case is ignored
                    continue;
                }

                fac = (SaslClientFactory) loadFactory(provs[j], className);
                if (fac != null) {
                    mech = fac.createSaslClient(
                        new String[]{mechanisms[i]}, authorizationId,
                        protocol, serverName, props, cbh);
                    if (mech != null) {
                        return mech;
                    }
                }
            }
        }

        return null;
    }

    private static Object loadFactory(Provider p, String className)
        throws SaslException {
        try {
            /*
             * Load the implementation class with the same class loader
             * that was used to load the provider.
             * In order to get the class loader of a class, the
             * caller's class loader must be the same as or an ancestor of
             * the class loader being returned. Otherwise, the caller must
             * have "getClassLoader" permission, or a SecurityException
             * will be thrown.
             * <p>
             * 使用用于加载提供程序的相同类加载器加载实现类。为了获得类的类加载器,调用者的类加载器必须与返回的类加载器相同或者是它的祖先。
             * 否则,调用者必须具有"getClassLoader"权限,否则将抛出SecurityException。
             * 
             */
            ClassLoader cl = p.getClass().getClassLoader();
            Class<?> implClass;
            implClass = Class.forName(className, true, cl);
            return implClass.newInstance();
        } catch (ClassNotFoundException e) {
            throw new SaslException("Cannot load class " + className, e);
        } catch (InstantiationException e) {
            throw new SaslException("Cannot instantiate class " + className, e);
        } catch (IllegalAccessException e) {
            throw new SaslException("Cannot access class " + className, e);
        } catch (SecurityException e) {
            throw new SaslException("Cannot access class " + className, e);
        }
    }


    /**
     * Creates a {@code SaslServer} for the specified mechanism.
     *
     * This method uses the
<a href="{@docRoot}/../technotes/guides/security/crypto/CryptoSpec.html#Provider">JCA Security Provider Framework</a>,
     * described in the
     * "Java Cryptography Architecture API Specification &amp; Reference", for
     * locating and selecting a {@code SaslServer} implementation.
     *
     * First, it
     * obtains an ordered list of {@code SaslServerFactory} instances from
     * the registered security providers for the "SaslServerFactory" service
     * and the specified mechanism. It then invokes
     * {@code createSaslServer()} on each factory instance on the list
     * until one produces a non-null {@code SaslServer} instance. It returns
     * the non-null {@code SaslServer} instance, or null if the search fails
     * to produce a non-null {@code SaslServer} instance.
     *<p>
     * A security provider for SaslServerFactory registers with the
     * JCA Security Provider Framework keys of the form <br>
     * {@code SaslServerFactory.}<em>{@code mechanism_name}</em>
     * <br>
     * and values that are class names of implementations of
     * {@code javax.security.sasl.SaslServerFactory}.
     *
     * For example, a provider that contains a factory class,
     * {@code com.wiz.sasl.digest.ServerFactory}, that supports the
     * "DIGEST-MD5" mechanism would register the following entry with the JCA:
     * {@code SaslServerFactory.DIGEST-MD5  com.wiz.sasl.digest.ServerFactory}
     *<p>
     * See the
     * "Java Cryptography Architecture API Specification &amp; Reference"
     * for information about how to install and configure security
     * service providers.
     *
     * <p>
     *  为指定的机制创建{@code SaslServer}。
     * 
     *  此方法使用<a href="{@docRoot}/../technotes/guides/security/crypto/CryptoSpec.html#Provider"> JCA安全提供程序框架</a>
     * ,如"Java加密架构API规范&amp; Reference",用于定位和选择{@code SaslServer}实现。
     * 
     *  首先,它从"SaslServerFactory"服务和指定机制的注册安全提供程序获取{@code SaslServerFactory}实例的有序列表。
     * 然后它在列表上的每个工厂实例上调用{@code createSaslServer()},直到生成一个非空的{@code SaslServer}实例。
     * 它返回非空的{@code SaslServer}实例,如果搜索无法生成一个非空的{@code SaslServer}实例,则返回null。
     * p>
     *  SaslServerFactory的安全提供程序使用<br> {@code SaslServerFactory。
     * } <em> {@ code mechanism_name} </em>格式的JCA安全提供程序框架注册。
     * <br>
     *  和作为{@code javax.security.sasl.SaslServerFactory}的实现的类名的值。
     * 
     * @param mechanism The non-null mechanism name. It must be an
     * IANA-registered name of a SASL mechanism. (e.g. "GSSAPI", "CRAM-MD5").
     * @param protocol The non-null string name of the protocol for which
     * the authentication is being performed (e.g., "ldap").
     * @param serverName The fully qualified host name of the server, or null
     * if the server is not bound to any specific host name. If the mechanism
     * does not allow an unbound server, a {@code SaslException} will
     * be thrown.
     * @param props The possibly null set of properties used to
     * select the SASL mechanism and to configure the authentication
     * exchange of the selected mechanism.
     * For example, if {@code props} contains the
     * {@code Sasl.POLICY_NOPLAINTEXT} property with the value
     * {@code "true"}, then the selected
     * SASL mechanism must not be susceptible to simple plain passive attacks.
     * In addition to the standard properties declared in this class,
     * other, possibly mechanism-specific, properties can be included.
     * Properties not relevant to the selected mechanism are ignored,
     * including any map entries with non-String keys.
     *
     * @param cbh The possibly null callback handler to used by the SASL
     * mechanisms to get further information from the application/library
     * to complete the authentication. For example, a SASL mechanism might
     * require the authentication ID, password and realm from the caller.
     * The authentication ID is requested by using a {@code NameCallback}.
     * The password is requested by using a {@code PasswordCallback}.
     * The realm is requested by using a {@code RealmChoiceCallback} if there is a list
     * of realms to choose from, and by using a {@code RealmCallback} if
     * the realm must be entered.
     *
     *@return A possibly null {@code SaslServer} created using the parameters
     * supplied. If null, cannot find a {@code SaslServerFactory}
     * that will produce one.
     *@exception SaslException If cannot create a {@code SaslServer} because
     * of an error.
     **/
    public static SaslServer
        createSaslServer(String mechanism,
                    String protocol,
                    String serverName,
                    Map<String,?> props,
                    javax.security.auth.callback.CallbackHandler cbh)
        throws SaslException {

        SaslServer mech = null;
        SaslServerFactory fac;
        String className;

        if (mechanism == null) {
            throw new NullPointerException("Mechanism name cannot be null");
        } else if (mechanism.length() == 0) {
            return null;
        }

        String mechFilter = "SaslServerFactory." + mechanism;
        Provider[] provs = Security.getProviders(mechFilter);
        for (int j = 0; provs != null && j < provs.length; j++) {
            className = provs[j].getProperty(mechFilter);
            if (className == null) {
                throw new SaslException("Provider does not support " +
                    mechFilter);
            }
            fac = (SaslServerFactory) loadFactory(provs[j], className);
            if (fac != null) {
                mech = fac.createSaslServer(
                    mechanism, protocol, serverName, props, cbh);
                if (mech != null) {
                    return mech;
                }
            }
        }

        return null;
    }

    /**
     * Gets an enumeration of known factories for producing {@code SaslClient}.
     * This method uses the same algorithm for locating factories as
     * {@code createSaslClient()}.
     * <p>
     * 
     * 例如,包含支持"DIGEST-MD5"机制的工厂类{@code com.wiz.sasl.digest.ServerFactory}的提供程序将使用JCA注册以下条目：{@code SaslServerFactory.DIGEST- MD5 com.wiz.sasl.digest.ServerFactory}
     * 。
     * p>
     *  有关如何安装和配置安全服务提供程序的信息,请参阅"Java加密架构API规范和参考"。
     * 
     * 
     * @return A non-null enumeration of known factories for producing
     * {@code SaslClient}.
     * @see #createSaslClient
     */
    public static Enumeration<SaslClientFactory> getSaslClientFactories() {
        Set<Object> facs = getFactories("SaslClientFactory");
        final Iterator<Object> iter = facs.iterator();
        return new Enumeration<SaslClientFactory>() {
            public boolean hasMoreElements() {
                return iter.hasNext();
            }
            public SaslClientFactory nextElement() {
                return (SaslClientFactory)iter.next();
            }
        };
    }

    /**
     * Gets an enumeration of known factories for producing {@code SaslServer}.
     * This method uses the same algorithm for locating factories as
     * {@code createSaslServer()}.
     * <p>
     * 
     * @return A non-null enumeration of known factories for producing
     * {@code SaslServer}.
     * @see #createSaslServer
     */
    public static Enumeration<SaslServerFactory> getSaslServerFactories() {
        Set<Object> facs = getFactories("SaslServerFactory");
        final Iterator<Object> iter = facs.iterator();
        return new Enumeration<SaslServerFactory>() {
            public boolean hasMoreElements() {
                return iter.hasNext();
            }
            public SaslServerFactory nextElement() {
                return (SaslServerFactory)iter.next();
            }
        };
    }

    private static Set<Object> getFactories(String serviceName) {
        HashSet<Object> result = new HashSet<Object>();

        if ((serviceName == null) || (serviceName.length() == 0) ||
            (serviceName.endsWith("."))) {
            return result;
        }


        Provider[] providers = Security.getProviders();
        HashSet<String> classes = new HashSet<String>();
        Object fac;

        for (int i = 0; i < providers.length; i++) {
            classes.clear();

            // Check the keys for each provider.
            for (Enumeration<Object> e = providers[i].keys(); e.hasMoreElements(); ) {
                String currentKey = (String)e.nextElement();
                if (currentKey.startsWith(serviceName)) {
                    // We should skip the currentKey if it contains a
                    // whitespace. The reason is: such an entry in the
                    // provider property contains attributes for the
                    // implementation of an algorithm. We are only interested
                    // in entries which lead to the implementation
                    // classes.
                    if (currentKey.indexOf(" ") < 0) {
                        String className = providers[i].getProperty(currentKey);
                        if (!classes.contains(className)) {
                            classes.add(className);
                            try {
                                fac = loadFactory(providers[i], className);
                                if (fac != null) {
                                    result.add(fac);
                                }
                            }catch (Exception ignore) {
                            }
                        }
                    }
                }
            }
        }
        return Collections.unmodifiableSet(result);
    }
}
