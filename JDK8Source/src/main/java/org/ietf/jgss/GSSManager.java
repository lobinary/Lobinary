/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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

package org.ietf.jgss;

import java.security.Provider;

/**
 * This class serves as a factory for other important
 * GSS-API classes and also provides information about the mechanisms that
 * are supported. It can create instances of classes
 * implementing the following three GSS-API interfaces: {@link
 * GSSName GSSName}, {@link GSSCredential GSSCredential}, and {@link
 * GSSContext GSSContext}. It also has methods to query for the list
 * of available mechanisms and the nametypes that each mechanism
 * supports.<p>
 *
 * An instance of the default <code>GSSManager</code> subclass
 * may be obtained through the static method {@link #getInstance()
 * getInstance}, but applications are free to instantiate other subclasses
 * of <code>GSSManager</code>. The default <code>GSSManager</code> instance
 * will support the Kerberos v5 GSS-API mechanism in addition to any
 * others. This mechanism is identified by the Oid "1.2.840.113554.1.2.2"
 * and is defined in RFC 1964.<p>
 *
 * A subclass extending the <code>GSSManager</code> abstract class may be
 * implemented  as a modular provider based layer that utilizes some well
 * known  service provider specification. The <code>GSSManager</code> API
 * allows the application to set provider preferences on
 * such an implementation. These methods also allow the implementation to
 * throw a well-defined exception in case provider based configuration is
 * not supported. Applications that expect to be portable should be aware
 * of this and recover cleanly by catching the exception.<p>
 *
 * It is envisioned that there will be three most common ways in which
 * providers will be used:<p>
 * <ol>
 * <li> The application does not care about what provider is used (the
 * default case).
 * <li> The application wants a particular provider to be used
 * preferentially, either for a particular mechanism or all the
 * time, irrespective of mechanism.
 * <li> The application wants to use the locally configured providers
 * as far as possible but if support is missing for one or more
 * mechanisms then it wants to fall back on its own provider.
 *</ol><p>
 *
 * The <code>GSSManager</code> class has two methods that enable these modes of
 * usage:  {@link #addProviderAtFront(Provider, Oid) addProviderAtFront} and
 * {@link #addProviderAtEnd(Provider, Oid) addProviderAtEnd}. These methods
 * have the effect of creating an ordered list of <i>&lt;provider,
 * oid&gt;</i> pairs  where each pair indicates a preference of provider
 * for a given oid.<p>
 *
 * It is important to note that there are certain interactions
 * between the different GSS-API objects that are created by a
 * GSSManager, where the provider that is used for a particular mechanism
 * might need to be consistent across all objects. For instance, if a
 * GSSCredential contains elements from a provider <i>p</i> for a mechanism
 * <i>m</i>, it should generally be passed in to a GSSContext that will use
 * provider <i>p</i> for the mechanism <i>m</i>. A simple rule of thumb
 * that will maximize portability is that objects created from different
 * GSSManager's should not be mixed, and if possible, a different
 * GSSManager instance should be created if the application wants to invoke
 * the <code>addProviderAtFront</code> method on a GSSManager that has
 * already created an object.<p>
 *
 *  Here is some sample code showing how the GSSManager might be used: <p>
 * <pre>
 *     GSSManager manager = GSSManager.getInstance();
 *
 *     Oid krb5Mechanism = new Oid("1.2.840.113554.1.2.2");
 *     Oid krb5PrincipalNameType = new Oid("1.2.840.113554.1.2.2.1");
 *
 *     // Identify who the client wishes to be
 *     GSSName userName = manager.createName("duke", GSSName.NT_USER_NAME);
 *
 *     // Identify the name of the server. This uses a Kerberos specific
 *     // name format.
 *     GSSName serverName = manager.createName("nfs/foo.sun.com",
 *                                             krb5PrincipalNameType);
 *
 *     // Acquire credentials for the user
 *     GSSCredential userCreds = manager.createCredential(userName,
 *                                             GSSCredential.DEFAULT_LIFETIME,
 *                                             krb5Mechanism,
 *                                             GSSCredential.INITIATE_ONLY);
 *
 *     // Instantiate and initialize a security context that will be
 *     // established with the server
 *     GSSContext context = manager.createContext(serverName,
 *                                                krb5Mechanism,
 *                                                userCreds,
 *                                                GSSContext.DEFAULT_LIFETIME);
 * </pre><p>
 *
 * The server side might use the following variation of this source:<p>
 *
 * <pre>
 *     // Acquire credentials for the server
 *     GSSCredential serverCreds = manager.createCredential(serverName,
 *                                             GSSCredential.DEFAULT_LIFETIME,
 *                                             krb5Mechanism,
 *                                             GSSCredential.ACCEPT_ONLY);
 *
 *     // Instantiate and initialize a security context that will
 *     // wait for an establishment request token from the client
 *     GSSContext context = manager.createContext(serverCreds);
 * </pre>
 *
 * <p>
 *  此类用作其他重要GSS-API类的工厂,并提供有关支持的机制的信息。
 * 它可以创建实现以下三个GSS-API接口的类的实例：{@link GSSName GSSName},{@link GSSCredential GSSCredential}和{@link GSSContext GSSContext}
 * 。
 *  此类用作其他重要GSS-API类的工厂,并提供有关支持的机制的信息。它还具有查询可用机制列表和每个机制支持的命名类型的方法。
 * 
 *  可以通过静态方法{@link #getInstance()getInstance}获得默认的<code> GSSManager </code>子类的实例,但应用程序可以自由实例化<code> GSSM
 * anager </code>的其他子类。
 * 默认的<code> GSSManager </code>实例将支持Kerberos v5 GSS-API机制以及任何其他机制。
 * 该机制由Oid"1.2.840.113554.1.2.2"标识,并且在RFC 1964中定义。<p>。
 * 
 * 扩展<code> GSSManager </code>抽象类的子类可以实现为利用一些公知的服务提供者规范的基于模块化提供者的层。
 *  <code> GSSManager </code> API允许应用程序在此类实现上设置提供程序首选项。这些方法还允许实现抛出明确定义的异常,以防基于提供程序的配置不被支持。
 * 期望可移植的应用程序应该意识到这一点,并通过捕获异常来完全恢复。<p>。
 * 
 *  设想提供商将使用三种最常见的方式：<p>
 * <ol>
 *  <li>应用程序不关心使用的提供程序(默认情况)。 <li>应用程序要求优先使用特定提供程序,无论是针对特定机制还是所有时间,无论机制如何。
 *  <li>应用程序希望尽可能使用本地配置的提供程序,但如果一个或多个机制缺少支持,则它希望退回到其自己的提供程序。 / ol> <p>。
 * 
 *  <code> GSSManager </code>类有两种方法启用这些使用模式：{@link #addProviderAtFront(Provider,Oid)addProviderAtFront}和
 * {@link #addProviderAtEnd(Provider,Oid)addProviderAtEnd}。
 * 这些方法具有创建<i>&lt; provider,oid&gt; </i>对的有序列表的效果,其中每对表示给定oid的提供者的偏好。<p>。
 * 
 * 请务必注意,GSSManager创建的不同GSS-API对象之间存在某些交互,其中用于特定机制的提供程序可能需要在所有对象之间保持一致。
 * 例如,如果GSSCredential包含来自机制m的提供者p i的元素,则它通常应被传递到将使用提供者<p> p的GSSContext中, i>对于机制</i>。
 * 一个简单的经验法则将最大限度地提高可移植性是从不同的GSSManager创建的对象不应该混合,如果可能,应该创建一个不同的GSSManager实例,如果应用程序要调用<code> addProvider
 * AtFront </code>已创建对象的GSSManager。
 * 例如,如果GSSCredential包含来自机制m的提供者p i的元素,则它通常应被传递到将使用提供者<p> p的GSSContext中, i>对于机制</i>。<p>。
 * 
 *  下面是一些示例代码,显示如何使用GSSManager：<p>
 * <pre>
 *  GSSManager manager = GSSManager.getInstance();
 * 
 *  Oid krb5机制= new Oid("1.2.840.113554.1.2.2"); Oid krb5PrincipalNameType = new Oid("1.2.840.113554.1.2
 * .2.1");。
 * 
 *  //标识客户端希望是谁GSSName userName = manager.createName("duke",GSSName.NT_USER_NAME);
 * 
 *  //标识服务器的名称。这使用Kerberos特定的//名称格式。
 *  GSSName serverName = manager.createName("nfs / foo.sun.com",krb5PrincipalNameType);。
 * 
 *  //获取用户的凭据GSSCredential userCreds = manager.createCredential(userName,GSSCredential.DEFAULT_LIFETIME,
 * krb5Mechanism,GSSCredential.INITIATE_ONLY);。
 * 
 * //实例化和初始化将与服务器建立的安全上下文GSSContext context = manager.createContext(serverName,krb5Mechanism,userCreds,G
 * SSContext.DEFAULT_LIFETIME); </pre> <p>。
 * 
 *  服务器端可能使用此源的以下变体：<p>
 * 
 * <pre>
 *  //获取服务器的凭据GSSCredential serverCreds = manager.createCredential(serverName,GSSCredential.DEFAULT_LIFE
 * TIME,krb5Mechanism,GSSCredential.ACCEPT_ONLY);。
 * 
 *  //实例化和初始化一个安全上下文,它将等待来自客户端的建立请求令牌GSSContext context = manager.createContext(serverCreds);
 * </pre>
 * 
 * 
 * @author Mayank Upadhyay
 * @see GSSName
 * @see GSSCredential
 * @see GSSContext
 * @since 1.4
 */
public abstract class GSSManager {

    /**
     * Returns the default GSSManager implementation.
     *
     * <p>
     *  返回默认的GSSManager实现。
     * 
     * 
     * @return a GSSManager implementation
     */
    public static GSSManager getInstance() {
        return new sun.security.jgss.GSSManagerImpl();
    }

    /**
     * Returns a list of mechanisms that are available to GSS-API callers
     * through this GSSManager. The default GSSManager obtained from the
     * {@link #getInstance() getInstance()} method includes the Oid
     * "1.2.840.113554.1.2.2" in its list. This Oid identifies the Kerberos
     * v5 GSS-API mechanism that is defined in RFC 1964.
     *
     * <p>
     *  返回GSS-API调用者通过此GSSManager可用的机制列表。
     * 从{@link #getInstance()getInstance()}方法获得的默认GSSManager在其列表中包括Oid"1.2.840.113554.1.2.2"。
     * 此Oid标识在RFC 1964中定义的Kerberos v5 GSS-API机制。
     * 
     * 
     * @return an array of Oid objects corresponding to the mechanisms that
     * are available. A <code>null</code> value is returned when no
     * mechanism are available (an example of this would be when mechanism
     * are dynamically configured, and currently no mechanisms are
     * installed).
     */
    public  abstract Oid[] getMechs();

    /**
     * Returns then name types supported by the indicated mechanism.<p>
     *
     * The default GSSManager instance includes support for the Kerberos v5
     * mechanism. When this mechanism ("1.2.840.113554.1.2.2") is indicated,
     * the returned list will contain at least the following nametypes:
     * {@link GSSName#NT_HOSTBASED_SERVICE GSSName.NT_HOSTBASED_SERVICE},
     * {@link GSSName#NT_EXPORT_NAME GSSName.NT_EXPORT_NAME}, and the
     * Kerberos v5 specific Oid "1.2.840.113554.1.2.2.1". The namespace for
     * the Oid "1.2.840.113554.1.2.2.1" is defined in RFC 1964.
     *
     * <p>
     *  返回指定机制支持的名称类型。<p>
     * 
     * 默认的GSSManager实例包括对Kerberos v5机制的支持。
     * 当指示此机制("1.2.840.113554.1.2.2")时,返回的列表将至少包含以下命名类型：{@link GSSName#NT_HOSTBASED_SERVICE GSSName.NT_HOSTBASED_SERVICE}
     * ,{@link GSSName#NT_EXPORT_NAME GSSName.NT_EXPORT_NAME}和Kerberos v5特定Oid"1.2.840.113554.1.2.2.1"。
     * 默认的GSSManager实例包括对Kerberos v5机制的支持。 Oid"1.2.840.113554.1.2.2.1"的命名空间在RFC 1964中定义。
     * 
     * 
     * @return an array of Oid objects corresponding to the name types that
     * the mechanism supports.
     * @param mech the Oid of the mechanism to query
     *
     * @see #getMechsForName(Oid)
     *
     * @throws GSSException containing the following
     * major error codes:
     *    {@link GSSException#BAD_MECH GSSException.BAD_MECH}
     *    {@link GSSException#FAILURE GSSException.FAILURE}
     */
    public abstract  Oid[] getNamesForMech(Oid mech)
        throws GSSException;

    /**
     * Returns a list of mechanisms that support the indicated name type.<p>
     *
     * The Kerberos v5 mechanism ("1.2.840.113554.1.2.2") will always be
     * returned in this list when the indicated nametype is one of
     * {@link GSSName#NT_HOSTBASED_SERVICE GSSName.NT_HOSTBASED_SERVICE},
     * {@link GSSName#NT_EXPORT_NAME GSSName.NT_EXPORT_NAME}, or
     * "1.2.840.113554.1.2.2.1".
     *
     * <p>
     *  返回支持指定名称类型的机制列表。<p>
     * 
     *  当指定的类型是{@link GSSName#NT_HOSTBASED_SERVICE GSSName.NT_HOSTBASED_SERVICE},{@link GSSName#NT_EXPORT_NAME GSSName.NT_EXPORT_NAME}
     * 之一时,将始终在此列表中返回Kerberos v5机制("1.2.840.113554.1.2.2" ,或"1.2.840.113554.1.2.2.1"。
     * 
     * 
     * @return an array of Oid objects corresponding to the mechanisms that
     * support the specified name type.  <code>null</code> is returned when no
     * mechanisms are found to support the specified name type.
     * @param nameType the Oid of the name type to look for
     *
     * @see #getNamesForMech(Oid)
     */
    public abstract  Oid[] getMechsForName(Oid nameType);

    /**
     * Factory method to convert a string name from the
     * specified namespace to a GSSName object. In general, the
     * <code>GSSName</code> object created  will contain multiple
     * representations of the name, one for each mechanism that is
     * supported; two examples that are exceptions to this are when
     * the namespace type parameter indicates NT_EXPORT_NAME or when the
     * GSS-API implementation is not multi-mechanism. It is
     * not recommended to use this method with a NT_EXPORT_NAME type because
     * representing a previously exported name consisting of arbitrary bytes
     * as a String might cause problems with character encoding schemes. In
     * such cases it is recommended that the bytes be passed in directly to
     * the overloaded form of this method {@link #createName(byte[],
     * Oid) createName}.
     *
     * <p>
     * 工厂方法将字符串名称从指定的命名空间转换为GSSName对象。
     * 一般来说,创建的<code> GSSName </code>对象将包含名称的多个表示,每个机制支持一个;两个示例是例外,当命名空间类型参数指示NT_EXPORT_NAME或当GSS-API实现不是多机制
     * 时。
     * 工厂方法将字符串名称从指定的命名空间转换为GSSName对象。不建议对NT_EXPORT_NAME类型使用此方法,因为将以前导出的由任意字节组成的名称表示为字符串可能会导致字符编码方案出现问题。
     * 在这种情况下,建议将字节直接传递给此方法的重载形式{@link #createName(byte [],Oid)createName}。
     * 
     * 
     * @param nameStr the string representing a printable form of the name to
     * create.
     * @param nameType the Oid specifying the namespace of the printable name
     * supplied. <code>null</code> can be used to specify
     * that a mechanism specific default printable syntax should
     * be assumed by each mechanism that examines nameStr.
     * It is not advisable to use the nametype NT_EXPORT_NAME with this
     * method.
     * @return a GSSName representing the indicated principal
     *
     * @see GSSName
     * @see GSSName#NT_EXPORT_NAME
     *
     * @throws GSSException containing the following
     * major error codes:
     *    {@link GSSException#BAD_NAMETYPE GSSException.BAD_NAMETYPE},
     *    {@link GSSException#BAD_NAME GSSException.BAD_NAME},
     *    {@link GSSException#BAD_MECH GSSException.BAD_MECH},
     *    {@link GSSException#FAILURE GSSException.FAILURE}
     */
    public abstract GSSName createName(String nameStr, Oid nameType)
        throws GSSException;

    /**
     * Factory method to convert a byte array containing a
     * name from the specified namespace to a GSSName object. In general,
     * the <code>GSSName</code> object created  will contain multiple
     * representations of the name, one for each mechanism that is
     * supported; two examples that are exceptions to this are when the
     * namespace type parameter indicates NT_EXPORT_NAME or when the
     * GSS-API implementation is not multi-mechanism. The bytes that are
     * passed in are interpreted by each underlying mechanism according to
     * some encoding scheme of its choice for the given nametype.
     *
     * <p>
     *  工厂方法将包含名称的字节数组从指定的命名空间转换为GSSName对象。
     * 一般来说,创建的<code> GSSName </code>对象将包含名称的多个表示,每个机制支持一个;两个示例是例外,当命名空间类型参数指示NT_EXPORT_NAME或当GSS-API实现不是多机制
     * 时。
     *  工厂方法将包含名称的字节数组从指定的命名空间转换为GSSName对象。传入的字节由每个底层机制根据其为给定的类型选择的某种编码方案来解释。
     * 
     * 
     * @param name the byte array containing the name to create
     * @param nameType the Oid specifying the namespace of the name supplied
     * in the byte array. <code>null</code> can be used to specify that a
     * mechanism specific default syntax should be assumed by each mechanism
     * that examines the byte array.
     * @return a GSSName representing the indicated principal
     *
     * @see GSSName
     * @see GSSName#NT_EXPORT_NAME
     *
     * @throws GSSException containing the following
     * major error codes:
     *    {@link GSSException#BAD_NAMETYPE GSSException.BAD_NAMETYPE},
     *    {@link GSSException#BAD_NAME GSSException.BAD_NAME},
     *    {@link GSSException#BAD_MECH GSSException.BAD_MECH},
     *    {@link GSSException#FAILURE GSSException.FAILURE}
     */
    public abstract GSSName createName(byte name[], Oid nameType)
        throws GSSException;

    /**
     *  Factory method to convert a string name from the
     * specified namespace to a GSSName object and canonicalize it at the
     * same time for a mechanism. In other words, this method is
     * a utility that does the equivalent of two steps: the {@link
     * #createName(String, Oid) createName} and then also the {@link
     * GSSName#canonicalize(Oid) GSSName.canonicalize}.
     *
     * <p>
     * 工厂方法,用于将字符串名称从指定的命名空间转换为GSSName对象,并为机制同时进行规范化。
     * 换句话说,这个方法是一个相当于两个步骤的实用程序：{@link #createName(String,Oid)createName},然后是{@link GSSName#canonicalize(Oid)GSSName.canonicalize}
     * 。
     * 工厂方法,用于将字符串名称从指定的命名空间转换为GSSName对象,并为机制同时进行规范化。
     * 
     * 
     * @param nameStr the string representing a printable form of the name to
     * create.
     * @param nameType the Oid specifying the namespace of the printable name
     * supplied. <code>null</code> can be used to specify
     * that a mechanism specific default printable syntax should
     * be assumed by each mechanism that examines nameStr.
     * It is not advisable to use the nametype NT_EXPORT_NAME with this
     * method.
     * @param mech Oid specifying the mechanism for which the name should be
     * canonicalized
     * @return a GSSName representing the indicated principal
     *
     * @see GSSName#canonicalize(Oid)
     * @see GSSName#NT_EXPORT_NAME
     *
     * @throws GSSException containing the following
     * major error codes:
     *    {@link GSSException#BAD_NAMETYPE GSSException.BAD_NAMETYPE},
     *    {@link GSSException#BAD_NAME GSSException.BAD_NAME},
     *    {@link GSSException#BAD_MECH GSSException.BAD_MECH},
     *    {@link GSSException#FAILURE GSSException.FAILURE}
     */
    public abstract GSSName createName(String nameStr, Oid nameType,
                                       Oid mech) throws GSSException;

    /**
     *  Factory method to convert a byte array containing a
     * name from the specified namespace to a GSSName object and canonicalize
     * it at the same time for a mechanism. In other words, this method is a
     * utility that does the equivalent of two steps: the {@link
     * #createName(byte[], Oid) createName} and then also {@link
     * GSSName#canonicalize(Oid) GSSName.canonicalize}.
     *
     * <p>
     *  工厂方法,用于将包含名称的字节数组从指定的命名空间转换为GSSName对象,并为机制同时进行规范化。
     * 换句话说,这个方法是一个等效于两个步骤的实用程序：{@link #createName(byte [],Oid)createName},然后也是{@link GSSName#canonicalize(Oid)GSSName.canonicalize}
     * 。
     *  工厂方法,用于将包含名称的字节数组从指定的命名空间转换为GSSName对象,并为机制同时进行规范化。
     * 
     * 
     * @param name the byte array containing the name to create
     * @param nameType the Oid specifying the namespace of the name supplied
     * in the byte array. <code>null</code> can be used to specify that a
     * mechanism specific default syntax should be assumed by each mechanism
     * that examines the byte array.
     * @param mech Oid specifying the mechanism for which the name should be
     * canonicalized
     * @return a GSSName representing the indicated principal
     *
     * @see GSSName#canonicalize(Oid)
     * @see GSSName#NT_EXPORT_NAME
     *
     * @throws GSSException containing the following
     * major error codes:
     *    {@link GSSException#BAD_NAMETYPE GSSException.BAD_NAMETYPE},
     *    {@link GSSException#BAD_NAME GSSException.BAD_NAME},
     *    {@link GSSException#BAD_MECH GSSException.BAD_MECH},
     *    {@link GSSException#FAILURE GSSException.FAILURE}
     */
    public abstract GSSName createName(byte name[], Oid nameType, Oid mech)
        throws GSSException;

    /**
     * Factory method for acquiring default credentials.  This will cause
     * the GSS-API to use system specific defaults for the set of mechanisms,
     * name, and lifetime.<p>
     *
     * GSS-API mechanism providers must impose a local access-control
     * policy on callers to prevent unauthorized callers from acquiring
     * credentials to which they are not entitled. The kinds of permissions
     * needed by different mechanism providers will be documented on a
     * per-mechanism basis. A failed permission check might cause a {@link
     * java.lang.SecurityException SecurityException} to be thrown from
     * this method.
     *
     * <p>
     *  用于获取默认凭据的工厂方法。这将导致GSS-API对机制,名称和生命周期的集合使用系统特定的默认值。<p>
     * 
     *  GSS-API机制提供者必须对呼叫者施加本地访问控制策略,以防止未经授权的呼叫者获取他们没有被授权的证书。不同机制提供者所需的权限类型将在每个机制的基础上进行记录。
     * 权限检查失败可能会导致从此方法抛出{@link java.lang.SecurityException SecurityException}。
     * 
     * 
     * @param usage The intended usage for this credential object. The value
     * of this parameter must be one of:
     * {@link GSSCredential#INITIATE_AND_ACCEPT
     * GSSCredential.INITIATE_AND_ACCEPT},
     * {@link GSSCredential#ACCEPT_ONLY GSSCredential.ACCEPT_ONLY}, and
     * {@link GSSCredential#INITIATE_ONLY GSSCredential.INITIATE_ONLY}.
     * @return a GSSCredential of the requested type.
     *
     * @see GSSCredential
     *
     * @throws GSSException containing the following
     * major error codes:
     *    {@link GSSException#BAD_MECH GSSException.BAD_MECH},
     *    {@link GSSException#BAD_NAMETYPE GSSException.BAD_NAMETYPE},
     *    {@link GSSException#BAD_NAME GSSException.BAD_NAME},
     *    {@link GSSException#CREDENTIALS_EXPIRED
     *                                   GSSException.CREDENTIALS_EXPIRED},
     *    {@link GSSException#NO_CRED GSSException.NO_CRED},
     *    {@link GSSException#FAILURE GSSException.FAILURE}
     */
    public abstract GSSCredential createCredential (int usage)
        throws GSSException;

    /**
     * Factory method for acquiring a single mechanism credential.<p>
     *
     * GSS-API mechanism providers must impose a local access-control
     * policy on callers to prevent unauthorized callers from acquiring
     * credentials to which they are not entitled. The kinds of permissions
     * needed by different mechanism providers will be documented on a
     * per-mechanism basis. A failed permission check might cause a {@link
     * java.lang.SecurityException SecurityException} to be thrown from
     * this method. <p>
     *
     * Non-default values for lifetime cannot always be honored by the
     * underlying mechanisms, thus applications should be prepared to call
     * {@link GSSCredential#getRemainingLifetime() getRemainingLifetime}
     * on the returned credential.<p>
     *
     * <p>
     *  用于获取单个机制凭据的工厂方法
     * 
     * GSS-API机制提供者必须对呼叫者施加本地访问控制策略,以防止未经授权的呼叫者获取他们没有被授权的证书。不同机制提供者所需的权限类型将在每个机制的基础上进行记录。
     * 权限检查失败可能会导致从此方法抛出{@link java.lang.SecurityException SecurityException}。 <p>。
     * 
     *  生命周期的非默认值不能总是被底层机制支持,因此应用程序应该准备在返回的凭证上调用{@link GSSCredential#getRemainingLifetime()getRemainingLifetime}
     * 。
     * <p>。
     * 
     * 
     * @param name the name of the principal for whom this credential is to be
     * acquired.  Use <code>null</code> to specify the default principal.
     * @param lifetime The number of seconds that credentials should remain
     * valid.  Use {@link GSSCredential#INDEFINITE_LIFETIME
     * GSSCredential.INDEFINITE_LIFETIME} to request that the credentials
     * have the maximum permitted lifetime.  Use {@link
     * GSSCredential#DEFAULT_LIFETIME GSSCredential.DEFAULT_LIFETIME} to
     * request default credential lifetime.
     * @param mech the Oid of the desired mechanism.  Use <code>(Oid) null
     * </code> to request the default mechanism.
     * @param usage The intended usage for this credential object. The value
     * of this parameter must be one of:
     * {@link GSSCredential#INITIATE_AND_ACCEPT
     * GSSCredential.INITIATE_AND_ACCEPT},
     * {@link GSSCredential#ACCEPT_ONLY GSSCredential.ACCEPT_ONLY}, and
     * {@link GSSCredential#INITIATE_ONLY GSSCredential.INITIATE_ONLY}.
     * @return a GSSCredential of the requested type.
     *
     * @see GSSCredential
     *
     * @throws GSSException containing the following
     * major error codes:
     *    {@link GSSException#BAD_MECH GSSException.BAD_MECH},
     *    {@link GSSException#BAD_NAMETYPE GSSException.BAD_NAMETYPE},
     *    {@link GSSException#BAD_NAME GSSException.BAD_NAME},
     *    {@link GSSException#CREDENTIALS_EXPIRED
     *                                   GSSException.CREDENTIALS_EXPIRED},
     *    {@link GSSException#NO_CRED GSSException.NO_CRED},
     *    {@link GSSException#FAILURE GSSException.FAILURE}
     */
    public abstract GSSCredential createCredential (GSSName name,
                                  int lifetime, Oid mech, int usage)
        throws GSSException;

    /**
     * Factory method for acquiring credentials over a set of
     * mechanisms. This method attempts to acquire credentials for
     * each of the mechanisms specified in the array called mechs.  To
     * determine the list of mechanisms for which the acquisition of
     * credentials succeeded, the caller should use the {@link
     * GSSCredential#getMechs() GSSCredential.getMechs} method.<p>
     *
     * GSS-API mechanism providers must impose a local access-control
     * policy on callers to prevent unauthorized callers from acquiring
     * credentials to which they are not entitled. The kinds of permissions
     * needed by different mechanism providers will be documented on a
     * per-mechanism basis. A failed permission check might cause a {@link
     * java.lang.SecurityException SecurityException} to be thrown from
     * this method.<p>
     *
     * Non-default values for lifetime cannot always be honored by the
     * underlying mechanisms, thus applications should be prepared to call
     * {@link GSSCredential#getRemainingLifetime() getRemainingLifetime}
     * on the returned credential.<p>
     *
     * <p>
     *  用于通过一组机制获取凭证的工厂方法。此方法尝试获取名为mechs的数组中指定的每个机制的凭据。
     * 要确定获取凭据成功的机制列表,调用方应使用{@link GSSCredential#getMechs()GSSCredential.getMechs}方法。<p>。
     * 
     *  GSS-API机制提供者必须对呼叫者施加本地访问控制策略,以防止未经授权的呼叫者获取他们没有被授权的证书。不同机制提供者所需的权限类型将在每个机制的基础上进行记录。
     * 权限检查失败可能会导致从此方法抛出{@link java.lang.SecurityException SecurityException}。<p>。
     * 
     * 生命周期的非默认值不能总是被底层机制支持,因此应用程序应该准备在返回的凭证上调用{@link GSSCredential#getRemainingLifetime()getRemainingLifetime}
     * 。
     * <p>。
     * 
     * 
     * @param name the name of the principal for whom this credential is to
     * be acquired.  Use <code>null</code> to specify the default
     * principal.
     * @param lifetime The number of seconds that credentials should remain
     * valid.  Use {@link GSSCredential#INDEFINITE_LIFETIME
     * GSSCredential.INDEFINITE_LIFETIME} to request that the credentials
     * have the maximum permitted lifetime.  Use {@link
     * GSSCredential#DEFAULT_LIFETIME GSSCredential.DEFAULT_LIFETIME} to
     * request default credential lifetime.
     * @param mechs an array of Oid's indicating the mechanisms over which
     * the credential is to be acquired.  Use <code>(Oid[]) null</code> for
     * requesting a system specific default set of mechanisms.
     * @param usage The intended usage for this credential object. The value
     * of this parameter must be one of:
     * {@link GSSCredential#INITIATE_AND_ACCEPT
     * GSSCredential.INITIATE_AND_ACCEPT},
     * {@link GSSCredential#ACCEPT_ONLY GSSCredential.ACCEPT_ONLY}, and
     * {@link GSSCredential#INITIATE_ONLY GSSCredential.INITIATE_ONLY}.
     * @return a GSSCredential of the requested type.
     *
     * @see GSSCredential
     *
     * @throws GSSException containing the following
     * major error codes:
     *    {@link GSSException#BAD_MECH GSSException.BAD_MECH},
     *    {@link GSSException#BAD_NAMETYPE GSSException.BAD_NAMETYPE},
     *    {@link GSSException#BAD_NAME GSSException.BAD_NAME},
     *    {@link GSSException#CREDENTIALS_EXPIRED
     *                                   GSSException.CREDENTIALS_EXPIRED},
     *    {@link GSSException#NO_CRED GSSException.NO_CRED},
     *    {@link GSSException#FAILURE GSSException.FAILURE}
     */
    public abstract GSSCredential createCredential(GSSName name,
                                      int lifetime, Oid mechs[], int usage)
        throws GSSException;

    /**
     * Factory method for creating a context on the initiator's
     * side.
     *
     * Some mechanism providers might require that the caller be granted
     * permission to initiate a security context. A failed permission check
     * might cause a {@link java.lang.SecurityException SecurityException}
     * to be thrown from this method.<p>
     *
     * Non-default values for lifetime cannot always be honored by the
     * underlying mechanism, thus applications should be prepared to call
     * {@link GSSContext#getLifetime() getLifetime} on the returned
     * context.<p>
     *
     * <p>
     *  在启动器端创建上下文的工厂方法。
     * 
     *  一些机制提供程序可能需要授予调用者启动安全上下文的权限。权限检查失败可能会导致从此方法抛出{@link java.lang.SecurityException SecurityException}。
     * <p>。
     * 
     *  生命周期的非默认值不能总是被底层机制支持,因此应用程序应该准备在返回的上下文上调用{@link GSSContext#getLifetime()getLifetime}。<p>
     * 
     * 
     * @param peer the name of the target peer.
     * @param mech the Oid of the desired mechanism.  Use <code>null</code>
     * to request the default mechanism.
     * @param myCred the credentials of the initiator.  Use
     * <code>null</code> to act as the default initiator principal.
     * @param lifetime the lifetime, in seconds, requested for the
     * context. Use {@link GSSContext#INDEFINITE_LIFETIME
     * GSSContext.INDEFINITE_LIFETIME} to request that the context have the
     * maximum permitted lifetime. Use {@link GSSContext#DEFAULT_LIFETIME
     * GSSContext.DEFAULT_LIFETIME} to request a default lifetime for the
     * context.
     * @return an unestablished GSSContext
     *
     * @see GSSContext
     *
     * @throws GSSException containing the following
     * major error codes:
     *    {@link GSSException#NO_CRED GSSException.NO_CRED}
     *    {@link GSSException#CREDENTIALS_EXPIRED
     *                      GSSException.CREDENTIALS_EXPIRED}
     *    {@link GSSException#BAD_NAMETYPE GSSException.BAD_NAMETYPE}
     *    {@link GSSException#BAD_MECH GSSException.BAD_MECH}
     *    {@link GSSException#FAILURE GSSException.FAILURE}
     */
    public abstract GSSContext createContext(GSSName peer, Oid mech,
                                        GSSCredential myCred, int lifetime)
        throws GSSException;

   /**
    * Factory method for creating a context on the acceptor' side.  The
    * context's properties will be determined from the input token supplied
    * to the accept method.
    *
    * Some mechanism providers might require that the caller be granted
    * permission to accept a security context. A failed permission check
    * might cause a {@link java.lang.SecurityException SecurityException}
    * to be thrown from this method.
    *
    * <p>
    *  用于在受体侧创建上下文的工厂方法。上下文的属性将根据提供给accept方法的输入令牌确定。
    * 
    *  一些机制提供程序可能需要授予调用者接受安全上下文的权限。权限检查失败可能会导致从此方法抛出{@link java.lang.SecurityException SecurityException}。
    * 
    * 
    * @param myCred the credentials for the acceptor.  Use
    * <code>null</code> to act as a default acceptor principal.
    * @return an unestablished GSSContext
    *
    * @see GSSContext
    *
    * @throws GSSException containing the following
    * major error codes:
    *    {@link GSSException#NO_CRED GSSException.NO_CRED}
    *    {@link GSSException#CREDENTIALS_EXPIRED
    *                        GSSException.CREDENTIALS_EXPIRED}
    *    {@link GSSException#BAD_MECH GSSException.BAD_MECH}
    *    {@link GSSException#FAILURE GSSException.FAILURE}
    */
    public abstract GSSContext createContext(GSSCredential myCred)
        throws GSSException;

    /**
     * Factory method for creating a previously exported context.  The
     * context properties will be determined from the input token and
     * cannot be modified through the set methods.<p>
     *
     * Implementations are not required to support the inter-process
     * transfer of security contexts.  Before exporting a context, calling
     * the {@link GSSContext#isTransferable() GSSContext.isTransferable}
     * will indicate if the context is transferable. Calling this method in
     * an implementation that does not support it will result in a
     * <code>GSSException</code> with the error
     * code {@link GSSException#UNAVAILABLE GSSException.UNAVAILABLE}.
     *
     * Some mechanism providers might require that the caller be granted
     * permission to initiate or accept a security context. A failed
     * permission check might cause a {@link java.lang.SecurityException
     * SecurityException} to be thrown from this method.
     *
     * <p>
     *  用于创建先前导出的上下文的工厂方法。上下文属性将根据输入令牌确定,不能通过set方法修改。<p>
     * 
     * 不需要实现来支持安全上下文的进程间传输。
     * 在导出上下文之前,调用{@link GSSContext#isTransferable()GSSContext.isTransferable}将指示上下文是否可转移。
     * 在不支持此方法的实现中调用此方法将导致带有错误代码{@link GSSException#UNAVAILABLE GSSException.UNAVAILABLE}的<code> GSSExceptio
     * n </code>。
     * 在导出上下文之前,调用{@link GSSContext#isTransferable()GSSContext.isTransferable}将指示上下文是否可转移。
     * 
     *  一些机制提供程序可能需要授予调用者启动或接受安全上下文的权限。
     * 权限检查失败可能会导致从此方法抛出{@link java.lang.SecurityException SecurityException}。
     * 
     * 
     * @param interProcessToken the token previously emitted from the
     * export method.
     * @return the previously established GSSContext
     *
     * @see GSSContext
     *
     * @throws GSSException containing the following
     * major error codes:
     *    {@link GSSException#NO_CONTEXT GSSException.NO_CONTEXT},
     *    {@link GSSException#DEFECTIVE_TOKEN GSSException.DEFECTIVE_TOKEN},
     *    {@link GSSException#UNAVAILABLE GSSException.UNAVAILABLE},
     *    {@link GSSException#UNAUTHORIZED GSSException.UNAUTHORIZED},
     *    {@link GSSException#FAILURE GSSException.FAILURE}
     */
    public abstract GSSContext createContext(byte [] interProcessToken)
        throws GSSException;

    /**
     * This method is used to indicate to the GSSManager that the
     * application would like a particular provider to be used ahead of all
     * others when support is desired for the given mechanism. When a value
     * of null is used instead of an <code>Oid</code> for the mechanism,
     * the GSSManager must use the indicated provider ahead of all others
     * no matter what the mechanism is. Only when the indicated provider
     * does not support the needed mechanism should the GSSManager move on
     * to a different provider.<p>
     *
     * Calling this method repeatedly preserves the older settings but
     * lowers them in preference thus forming an ordered list of provider
     * and <code>Oid</code> pairs that grows at the top.<p>
     *
     * Calling addProviderAtFront with a null <code>Oid</code> will remove
     * all previous preferences that were set for this provider in the
     * GSSManager instance. Calling addProviderAtFront with a non-null
     * <code>Oid</code> will remove any previous preference that was set
     * using this mechanism and this provider together.<p>
     *
     * If the GSSManager implementation does not support an SPI with a
     * pluggable provider architecture it should throw a GSSException with
     * the status code GSSException.UNAVAILABLE to indicate that the
     * operation is unavailable.<p>
     *
     * Suppose an application desired that the provider A always be checked
     * first when any mechanism is needed, it would call:<p>
     * <pre>
     *         GSSManager mgr = GSSManager.getInstance();
     *         // mgr may at this point have its own pre-configured list
     *         // of provider preferences. The following will prepend to
     *         // any such list:
     *
     *         mgr.addProviderAtFront(A, null);
     * </pre>
     * Now if it also desired that the mechanism of Oid m1 always be
     * obtained from the provider B before the previously set A was checked,
     * it would call:<p>
     * <pre>
     *         mgr.addProviderAtFront(B, m1);
     * </pre>
     * The GSSManager would then first check with B if m1 was needed. In
     * case B did not provide support for m1, the GSSManager would continue
     * on to check with A.  If any mechanism m2 is needed where m2 is
     * different from m1 then the GSSManager would skip B and check with A
     * directly.<p>
     *
     * Suppose at a later time the following call is made to the same
     * GSSManager instance:<p>
     * <pre>
     *         mgr.addProviderAtFront(B, null)
     * </pre>
     * then the previous setting with the pair (B, m1) is subsumed by this
     * and should be removed. Effectively the list of preferences now
     * becomes {(B, null), (A, null),
     *         ... //followed by the pre-configured list.<p>
     *
     * Please note, however, that the following call:
     * <pre>
     *         mgr.addProviderAtFront(A, m3)
     * </pre>
     * does not subsume the previous setting of (A, null) and the list will
     * effectively become {(A, m3), (B, null), (A, null), ...}
     *
     * <p>
     *  此方法用于向GSSManager指示当希望获得给定机制的支持时,应用程序想要在所有其他提供者之前使用特定提供者。
     * 当对于该机制使用null值而不是<code> Oid </code>时,GSSManager必须使用指定的提供者,而不管其他机制是什么。
     * 只有当指定的提供程序不支持所需的机制时,GSSManager才会移动到其他提供程序。<p>。
     * 
     *  调用此方法会重复保留旧设置,但会优先降低它们,从而形成一个提供程序的有序列表,并在顶部生成<code> Oid </code>对。
     * 
     * 使用null <code> Oid </code>调用addProviderAtFront将删除在GSSManager实例中为此提供程序设置的所有先前首选项。
     * 使用非空<code> Oid </code>调用addProviderAtFront将删除使用此机制和此提供程序一起设置的任何先前的首选项。<p>。
     * 
     *  如果GSSManager实现不支持具有可插入提供程序体系结构的SPI,它应该抛出一个带有状态码GSSException.UNAVAILABLE的GSSException,以指示操作不可用。<p>
     * 
     *  假设应用程序希望在需要任何机制时始终首先检查提供者A,则它将调用：<p>
     * <pre>
     *  GSSManager mgr = GSSManager.getInstance(); // mgr可以在这一点上具有它自己的预先配置的提供商偏好的列表//。以下将添加到//任何此类列表：
     * 
     *  mgr.addProviderAtFront(A,null);
     * </pre>
     *  现在如果还希望在检查先前设置的A之前始终从提供者B获得Oid m1的机制,则它将调用：
     * <pre>
     *  mgr.addProviderAtFront(B,m1);
     * </pre>
     *  如果需要m1,GSSManager首先检查B。在B不提供对m1的支持的情况下,GSSManager将继续与A检查。
     * 如果需要任何机制m2,其中m2不同于m1,则GSSManager将跳过B并直接与A检查。
     * 
     *  假设在稍后的时间,对同一个GSSManager实例进行以下调用：<p>
     * <pre>
     *  mgr.addProviderAtFront(B,null)
     * </pre>
     * 那么具有对(B,m1)的先前设置被归入这个并且应该被移除。有效地,偏好列表现在变为{(B,null),(A,null),... //后面是预配置的列表。<p>
     * 
     *  但请注意,以下电话：
     * <pre>
     *  mgr.addProviderAtFront(A,m3)
     * </pre>
     * 
     * @param p the provider instance that should be used whenever support
     * is needed for mech.
     * @param mech the mechanism for which the provider is being set
     *
     * @throws GSSException containing the following
     * major error codes:
     *    {@link GSSException#UNAVAILABLE GSSException.UNAVAILABLE},
     *    {@link GSSException#FAILURE GSSException.FAILURE}
     */
    public abstract void addProviderAtFront(Provider p, Oid mech)
        throws GSSException;

    /**
     * This method is used to indicate to the GSSManager that the
     * application would like a particular provider to be used if no other
     * provider can be found that supports the given mechanism. When a value
     * of null is used instead of an Oid for the mechanism, the GSSManager
     * must use the indicated provider for any mechanism.<p>
     *
     * Calling this method repeatedly preserves the older settings but
     * raises them above newer ones in preference thus forming an ordered
     * list of providers and Oid pairs that grows at the bottom. Thus the
     * older provider settings will be utilized first before this one is.<p>
     *
     * If there are any previously existing preferences that conflict with
     * the preference being set here, then the GSSManager should ignore this
     * request.<p>
     *
     * If the GSSManager implementation does not support an SPI with a
     * pluggable provider architecture it should throw a GSSException with
     * the status code GSSException.UNAVAILABLE to indicate that the
     * operation is unavailable.<p>
     *
     * Suppose an application desired that when a mechanism of Oid m1 is
     * needed the system default providers always be checked first, and only
     * when they do not support m1 should a provider A be checked. It would
     * then make the call:<p>
     * <pre>
     *         GSSManager mgr = GSSManager.getInstance();
     *         mgr.addProviderAtEnd(A, m1);
     * </pre>
     * Now, if it also desired that for all mechanisms the provider B be
     * checked after all configured providers have been checked, it would
     * then call:<p>
     * <pre>
     *         mgr.addProviderAtEnd(B, null);
     * </pre>
     * Effectively the list of preferences now becomes {..., (A, m1), (B,
     * null)}.<p>
     *
     * Suppose at a later time the following call is made to the same
     * GSSManager instance:<p>
     * <pre>
     *         mgr.addProviderAtEnd(B, m2)
     * </pre>
     * then the previous setting with the pair (B, null) subsumes this and
     * therefore this request should be ignored. The same would happen if a
     * request is made for the already existing pairs of (A, m1) or (B,
     * null).<p>
     *
     * Please note, however, that the following call:<p>
     * <pre>
     *         mgr.addProviderAtEnd(A, null)
     * </pre>
     * is not subsumed by the previous setting of (A, m1) and the list will
     * effectively become {..., (A, m1), (B, null), (A, null)}
     *
     * <p>
     *  不包含(A,null)的先前设置,并且列表将有效地变成{(A,m3),(B,null),(A,null)
     * 
     * 
     * @param p the provider instance that should be used whenever support
     * is needed for mech.
     * @param mech the mechanism for which the provider is being set
     *
     * @throws GSSException containing the following
     * major error codes:
     *    {@link GSSException#UNAVAILABLE GSSException.UNAVAILABLE},
     *    {@link GSSException#FAILURE GSSException.FAILURE}
     */
    public abstract void addProviderAtEnd(Provider p, Oid mech)
        throws GSSException;
}
