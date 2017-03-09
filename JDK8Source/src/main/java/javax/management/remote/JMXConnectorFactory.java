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

import com.sun.jmx.mbeanserver.Util;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.StringTokenizer;
import java.security.AccessController;
import java.security.PrivilegedAction;

import com.sun.jmx.remote.util.ClassLogger;
import com.sun.jmx.remote.util.EnvHelp;
import sun.reflect.misc.ReflectUtil;


/**
 * <p>Factory to create JMX API connector clients.  There
 * are no instances of this class.</p>
 *
 * <p>Connections are usually made using the {@link
 * #connect(JMXServiceURL) connect} method of this class.  More
 * advanced applications can separate the creation of the connector
 * client, using {@link #newJMXConnector(JMXServiceURL, Map)
 * newJMXConnector} and the establishment of the connection itself, using
 * {@link JMXConnector#connect(Map)}.</p>
 *
 * <p>Each client is created by an instance of {@link
 * JMXConnectorProvider}.  This instance is found as follows.  Suppose
 * the given {@link JMXServiceURL} looks like
 * <code>"service:jmx:<em>protocol</em>:<em>remainder</em>"</code>.
 * Then the factory will attempt to find the appropriate {@link
 * JMXConnectorProvider} for <code><em>protocol</em></code>.  Each
 * occurrence of the character <code>+</code> or <code>-</code> in
 * <code><em>protocol</em></code> is replaced by <code>.</code> or
 * <code>_</code>, respectively.</p>
 *
 * <p>A <em>provider package list</em> is searched for as follows:</p>
 *
 * <ol>
 *
 * <li>If the <code>environment</code> parameter to {@link
 * #newJMXConnector(JMXServiceURL, Map) newJMXConnector} contains the
 * key <code>jmx.remote.protocol.provider.pkgs</code> then the
 * associated value is the provider package list.
 *
 * <li>Otherwise, if the system property
 * <code>jmx.remote.protocol.provider.pkgs</code> exists, then its value
 * is the provider package list.
 *
 * <li>Otherwise, there is no provider package list.
 *
 * </ol>
 *
 * <p>The provider package list is a string that is interpreted as a
 * list of non-empty Java package names separated by vertical bars
 * (<code>|</code>).  If the string is empty, then so is the provider
 * package list.  If the provider package list is not a String, or if
 * it contains an element that is an empty string, a {@link
 * JMXProviderException} is thrown.</p>
 *
 * <p>If the provider package list exists and is not empty, then for
 * each element <code><em>pkg</em></code> of the list, the factory
 * will attempt to load the class
 *
 * <blockquote>
 * <code><em>pkg</em>.<em>protocol</em>.ClientProvider</code>
 * </blockquote>

 * <p>If the <code>environment</code> parameter to {@link
 * #newJMXConnector(JMXServiceURL, Map) newJMXConnector} contains the
 * key <code>jmx.remote.protocol.provider.class.loader</code> then the
 * associated value is the class loader to use to load the provider.
 * If the associated value is not an instance of {@link
 * java.lang.ClassLoader}, an {@link
 * java.lang.IllegalArgumentException} is thrown.</p>
 *
 * <p>If the <code>jmx.remote.protocol.provider.class.loader</code>
 * key is not present in the <code>environment</code> parameter, the
 * calling thread's context class loader is used.</p>
 *
 * <p>If the attempt to load this class produces a {@link
 * ClassNotFoundException}, the search for a handler continues with
 * the next element of the list.</p>
 *
 * <p>Otherwise, a problem with the provider found is signalled by a
 * {@link JMXProviderException} whose {@link
 * JMXProviderException#getCause() <em>cause</em>} indicates the underlying
 * exception, as follows:</p>
 *
 * <ul>
 *
 * <li>if the attempt to load the class produces an exception other
 * than <code>ClassNotFoundException</code>, that is the
 * <em>cause</em>;
 *
 * <li>if {@link Class#newInstance()} for the class produces an
 * exception, that is the <em>cause</em>.
 *
 * </ul>
 *
 * <p>If no provider is found by the above steps, including the
 * default case where there is no provider package list, then the
 * implementation will use its own provider for
 * <code><em>protocol</em></code>, or it will throw a
 * <code>MalformedURLException</code> if there is none.  An
 * implementation may choose to find providers by other means.  For
 * example, it may support the <a
 * href="{@docRoot}/../technotes/guides/jar/jar.html#Service Provider">
 * JAR conventions for service providers</a>, where the service
 * interface is <code>JMXConnectorProvider</code>.</p>
 *
 * <p>Every implementation must support the RMI connector protocol with
 * the default RMI transport, specified with string <code>rmi</code>.
 * An implementation may optionally support the RMI connector protocol
 * with the RMI/IIOP transport, specified with the string
 * <code>iiop</code>.</p>
 *
 * <p>Once a provider is found, the result of the
 * <code>newJMXConnector</code> method is the result of calling {@link
 * JMXConnectorProvider#newJMXConnector(JMXServiceURL,Map) newJMXConnector}
 * on the provider.</p>
 *
 * <p>The <code>Map</code> parameter passed to the
 * <code>JMXConnectorProvider</code> is a new read-only
 * <code>Map</code> that contains all the entries that were in the
 * <code>environment</code> parameter to {@link
 * #newJMXConnector(JMXServiceURL,Map)
 * JMXConnectorFactory.newJMXConnector}, if there was one.
 * Additionally, if the
 * <code>jmx.remote.protocol.provider.class.loader</code> key is not
 * present in the <code>environment</code> parameter, it is added to
 * the new read-only <code>Map</code>.  The associated value is the
 * calling thread's context class loader.</p>
 *
 * <p>
 *  <p>工厂创建JMX API连接器客户端。没有此类的实例。</p>
 * 
 *  <p>连接通常使用此类的{@link #connect(JMXServiceURL)connect}方法。
 * 更高级的应用程序可以使用{@link #newJMXConnector(JMXServiceURL,Map)newJMXConnector}和连接本身的建立来分离连接器客户端的创建,使用{@link JMXConnector#connect(Map)}
 * 。
 *  <p>连接通常使用此类的{@link #connect(JMXServiceURL)connect}方法。</p>。
 * 
 *  <p>每个客户端由{@link JMXConnectorProvider}的实例创建。此实例如下所示。
 * 假设给定的{@link JMXServiceURL}看起来像<code>"service：jmx：<em> protocol </em>：<em> remaining </em>"</code>。
 * 然后,工厂将尝试为<code> <em>协议</em> </code>找到适当的{@link JMXConnectorProvider}。
 *  <code> <em> protocol </em> </code>中的每个字符<code> + </code>或<code>  -  </code>会被替换为<code>。
 * </code> <code> _ </code>。</p>。
 * 
 *  <p>如下搜索<em>提供程序包列表</em>：</p>
 * 
 * <ol>
 * 
 *  <li>如果{@link #newJMXConnector(JMXServiceURL,Map)newJMXConnector}的<code> environment </code>参数包含关键字<code>
 *  jmx.remote.protocol.provider.pkgs </code>是提供程序包列表。
 * 
 * <li>否则,如果系统属性<code> jmx.remote.protocol.provider.pkgs </code>存在,则其值为提供程序包列表。
 * 
 *  <li>否则,没有提供程序包列表。
 * 
 * </ol>
 * 
 *  <p>提供程序包列表是一个字符串,解释为用竖线(<code> | </code>)分隔的非空Java包名称的列表。如果字符串为空,那么提供程序包列表也是如此。
 * 如果提供程序包列表不是字符串,或者它包含一个为空字符串的元素,则会抛出{@link JMXProviderException}。</p>。
 * 
 *  <p>如果提供程序包列表存在且不为空,则对于列表中的每个元素<code> <em> pkg </em> </code>,工厂将尝试加载类
 * 
 * <blockquote>
 *  <code> <em> pkg </em>。<em> protocol </em> .ClientProvider </code>
 * </blockquote>
 * 
 *  <p>如果{@link #newJMXConnector(JMXServiceURL,Map)newJMXConnector}的<code> environment </code>参数包含密钥<code>
 *  jmx.remote.protocol.provider.class.loader </code>关联值是用于加载提供程序的类加载器。
 * 如果相关联的值不是{@link java.lang.ClassLoader}的实例,则会抛出{@link java.lang.IllegalArgumentException}。</p>。
 * 
 *  <p>如果<code> jmx.remote.protocol.provider.class.loader </code>键不存在于<code> environment </code>参数中,则使用调
 * 用线程的上下文类加载器。
 *  p>。
 * 
 * <p>如果尝试加载此类会生成{@link ClassNotFoundException},则会继续搜索处理程序,并显示列表的下一个元素。</p>
 * 
 *  <p>否则,由{@link JMXProviderException} {@link JMXProviderException#getCause()<em> cause </em>}指示基本异常的{@link JMXProviderException}
 * 指示提供程序发现问题,如下所示：</p>。
 * 
 * <ul>
 * 
 *  <li>如果尝试加载类会产生除<code> ClassNotFoundException </code>之外的异常,即<em> cause </em>;
 * 
 * 
 * @since 1.5
 */
public class JMXConnectorFactory {

    /**
     * <p>Name of the attribute that specifies the default class
     * loader. This class loader is used to deserialize return values and
     * exceptions from remote <code>MBeanServerConnection</code>
     * calls.  The value associated with this attribute is an instance
     * of {@link ClassLoader}.</p>
     * <p>
     *  <li>如果类的{@link Class#newInstance()}产生异常,即<em> cause </em>。
     * 
     * </ul>
     * 
     *  <p>如果没有通过上述步骤找到提供者,包括没有提供者包列表的默认情况,那么实现将使用自己的提供者<code> <em> protocol </em> </code>或者如果没有,它会抛出一个<code>
     *  MalformedURLException </code>。
     * 实现可以选择通过其他方式找到提供商。例如,它可以支持<a。
     * href="{@docRoot}/../technotes/guides/jar/jar.html#Service Provider">
     *  服务提供者的JAR约定</a>,其中服务接口是<code> JMXConnectorProvider </code>。</p>
     * 
     *  <p>每个实现都必须使用默认的RMI传输支持RMI连接器协议,使用字符串<code> rmi </code>指定。
     * 实现可以可选地支持RMI连接器协议与用字符串<code> iiop </code>指定的RMI / IIOP传输。</p>。
     * 
     * <p>一旦找到提供者,<code> newJMXConnector </code>方法的结果是在提供者上调用{@link JMXConnectorProvider#newJMXConnector(JMXServiceURL,Map)newJMXConnector}
     * 的结果。
     * </p>。
     * 
     *  <p>传递给<code> JMXConnectorProvider </code>的<code> Map </code>参数是一个新的只读<code> Map </code>,其中包含<code>环境
     * </code>参数到{@link #newJMXConnector(JMXServiceURL,Map)JMXConnectorFactory.newJMXConnector},如果有的话。
     * 此外,如果<code> jmx.remote.protocol.provider.class.loader </code>键不存在于<code> environment </code>参数中,它将被添加
     * 到新的只读<code>地图</code>。
     * 相关的值是调用线程的上下文类加载器。</p>。
     * 
     */
    public static final String DEFAULT_CLASS_LOADER =
        "jmx.remote.default.class.loader";

    /**
     * <p>Name of the attribute that specifies the provider packages
     * that are consulted when looking for the handler for a protocol.
     * The value associated with this attribute is a string with
     * package names separated by vertical bars (<code>|</code>).</p>
     * <p>
     *  <p>指定默认类装入器的属性的名称。这个类加载器用于反序列化来自远程<code> MBeanServerConnection </code>调用的返回值和异常。
     * 与此属性关联的值是{@link ClassLoader}的实例。</p>。
     * 
     */
    public static final String PROTOCOL_PROVIDER_PACKAGES =
        "jmx.remote.protocol.provider.pkgs";

    /**
     * <p>Name of the attribute that specifies the class
     * loader for loading protocol providers.
     * The value associated with this attribute is an instance
     * of {@link ClassLoader}.</p>
     * <p>
     *  <p>指定在查找协议的处理程序时查询的提供程序包的属性的名称。与此属性关联的值是一个字符串,其中包名称由竖线(<code> | </code>)分隔。</p>
     * 
     */
    public static final String PROTOCOL_PROVIDER_CLASS_LOADER =
        "jmx.remote.protocol.provider.class.loader";

    private static final String PROTOCOL_PROVIDER_DEFAULT_PACKAGE =
        "com.sun.jmx.remote.protocol";

    private static final ClassLogger logger =
        new ClassLogger("javax.management.remote.misc", "JMXConnectorFactory");

    /** There are no instances of this class.  */
    private JMXConnectorFactory() {
    }

    /**
     * <p>Creates a connection to the connector server at the given
     * address.</p>
     *
     * <p>This method is equivalent to {@link
     * #connect(JMXServiceURL,Map) connect(serviceURL, null)}.</p>
     *
     * <p>
     *  <p>指定用于加载协议提供程序的类加载器的属性的名称。与此属性关联的值是{@link ClassLoader}的实例。</p>
     * 
     * 
     * @param serviceURL the address of the connector server to
     * connect to.
     *
     * @return a <code>JMXConnector</code> whose {@link
     * JMXConnector#connect connect} method has been called.
     *
     * @exception NullPointerException if <code>serviceURL</code> is null.
     *
     * @exception IOException if the connector client or the
     * connection cannot be made because of a communication problem.
     *
     * @exception SecurityException if the connection cannot be made
     * for security reasons.
     */
    public static JMXConnector connect(JMXServiceURL serviceURL)
            throws IOException {
        return connect(serviceURL, null);
    }

    /**
     * <p>Creates a connection to the connector server at the given
     * address.</p>
     *
     * <p>This method is equivalent to:</p>
     *
     * <pre>
     * JMXConnector conn = JMXConnectorFactory.newJMXConnector(serviceURL,
     *                                                         environment);
     * conn.connect(environment);
     * </pre>
     *
     * <p>
     * <p>在指定地址创建与连接器服务器的连接。</p>
     * 
     *  <p>此方法相当于{@link #connect(JMXServiceURL,Map)connect(serviceURL,null)}。</p>
     * 
     * 
     * @param serviceURL the address of the connector server to connect to.
     *
     * @param environment a set of attributes to determine how the
     * connection is made.  This parameter can be null.  Keys in this
     * map must be Strings.  The appropriate type of each associated
     * value depends on the attribute.  The contents of
     * <code>environment</code> are not changed by this call.
     *
     * @return a <code>JMXConnector</code> representing the newly-made
     * connection.  Each successful call to this method produces a
     * different object.
     *
     * @exception NullPointerException if <code>serviceURL</code> is null.
     *
     * @exception IOException if the connector client or the
     * connection cannot be made because of a communication problem.
     *
     * @exception SecurityException if the connection cannot be made
     * for security reasons.
     */
    public static JMXConnector connect(JMXServiceURL serviceURL,
                                       Map<String,?> environment)
            throws IOException {
        if (serviceURL == null)
            throw new NullPointerException("Null JMXServiceURL");
        JMXConnector conn = newJMXConnector(serviceURL, environment);
        conn.connect(environment);
        return conn;
    }

    private static <K,V> Map<K,V> newHashMap() {
        return new HashMap<K,V>();
    }

    private static <K> Map<K,Object> newHashMap(Map<K,?> map) {
        return new HashMap<K,Object>(map);
    }

    /**
     * <p>Creates a connector client for the connector server at the
     * given address.  The resultant client is not connected until its
     * {@link JMXConnector#connect(Map) connect} method is called.</p>
     *
     * <p>
     *  <p>在指定地址创建与连接器服务器的连接。</p>
     * 
     *  <p>此方法等效于：</p>
     * 
     * <pre>
     *  JMXConnector conn = JMXConnectorFactory.newJMXConnector(serviceURL,environment); conn.connect(enviro
     * 
     * @param serviceURL the address of the connector server to connect to.
     *
     * @param environment a set of attributes to determine how the
     * connection is made.  This parameter can be null.  Keys in this
     * map must be Strings.  The appropriate type of each associated
     * value depends on the attribute.  The contents of
     * <code>environment</code> are not changed by this call.
     *
     * @return a <code>JMXConnector</code> representing the new
     * connector client.  Each successful call to this method produces
     * a different object.
     *
     * @exception NullPointerException if <code>serviceURL</code> is null.
     *
     * @exception IOException if the connector client cannot be made
     * because of a communication problem.
     *
     * @exception MalformedURLException if there is no provider for the
     * protocol in <code>serviceURL</code>.
     *
     * @exception JMXProviderException if there is a provider for the
     * protocol in <code>serviceURL</code> but it cannot be used for
     * some reason.
     */
    public static JMXConnector newJMXConnector(JMXServiceURL serviceURL,
                                               Map<String,?> environment)
            throws IOException {

        final Map<String,Object> envcopy;
        if (environment == null)
            envcopy = newHashMap();
        else {
            EnvHelp.checkAttributes(environment);
            envcopy = newHashMap(environment);
        }

        final ClassLoader loader = resolveClassLoader(envcopy);
        final Class<JMXConnectorProvider> targetInterface =
                JMXConnectorProvider.class;
        final String protocol = serviceURL.getProtocol();
        final String providerClassName = "ClientProvider";
        final JMXServiceURL providerURL = serviceURL;

        JMXConnectorProvider provider = getProvider(providerURL, envcopy,
                                               providerClassName,
                                               targetInterface,
                                               loader);

        IOException exception = null;
        if (provider == null) {
            // Loader is null when context class loader is set to null
            // and no loader has been provided in map.
            // com.sun.jmx.remote.util.Service class extracted from j2se
            // provider search algorithm doesn't handle well null classloader.
            if (loader != null) {
                try {
                    JMXConnector connection =
                        getConnectorAsService(loader, providerURL, envcopy);
                    if (connection != null)
                        return connection;
                } catch (JMXProviderException e) {
                    throw e;
                } catch (IOException e) {
                    exception = e;
                }
            }
            provider = getProvider(protocol, PROTOCOL_PROVIDER_DEFAULT_PACKAGE,
                            JMXConnectorFactory.class.getClassLoader(),
                            providerClassName, targetInterface);
        }

        if (provider == null) {
            MalformedURLException e =
                new MalformedURLException("Unsupported protocol: " + protocol);
            if (exception == null) {
                throw e;
            } else {
                throw EnvHelp.initCause(e, exception);
            }
        }

        final Map<String,Object> fixedenv =
                Collections.unmodifiableMap(envcopy);

        return provider.newJMXConnector(serviceURL, fixedenv);
    }

    private static String resolvePkgs(Map<String, ?> env)
            throws JMXProviderException {

        Object pkgsObject = null;

        if (env != null)
            pkgsObject = env.get(PROTOCOL_PROVIDER_PACKAGES);

        if (pkgsObject == null)
            pkgsObject =
                AccessController.doPrivileged(new PrivilegedAction<String>() {
                    public String run() {
                        return System.getProperty(PROTOCOL_PROVIDER_PACKAGES);
                    }
                });

        if (pkgsObject == null)
            return null;

        if (!(pkgsObject instanceof String)) {
            final String msg = "Value of " + PROTOCOL_PROVIDER_PACKAGES +
                " parameter is not a String: " +
                pkgsObject.getClass().getName();
            throw new JMXProviderException(msg);
        }

        final String pkgs = (String) pkgsObject;
        if (pkgs.trim().equals(""))
            return null;

        // pkgs may not contain an empty element
        if (pkgs.startsWith("|") || pkgs.endsWith("|") ||
            pkgs.indexOf("||") >= 0) {
            final String msg = "Value of " + PROTOCOL_PROVIDER_PACKAGES +
                " contains an empty element: " + pkgs;
            throw new JMXProviderException(msg);
        }

        return pkgs;
    }

    static <T> T getProvider(JMXServiceURL serviceURL,
                             final Map<String, Object> environment,
                             String providerClassName,
                             Class<T> targetInterface,
                             final ClassLoader loader)
            throws IOException {

        final String protocol = serviceURL.getProtocol();

        final String pkgs = resolvePkgs(environment);

        T instance = null;

        if (pkgs != null) {
            instance =
                getProvider(protocol, pkgs, loader, providerClassName,
                            targetInterface);

            if (instance != null) {
                boolean needsWrap = (loader != instance.getClass().getClassLoader());
                environment.put(PROTOCOL_PROVIDER_CLASS_LOADER, needsWrap ? wrap(loader) : loader);
            }
        }

        return instance;
    }

    static <T> Iterator<T> getProviderIterator(final Class<T> providerClass,
                                               final ClassLoader loader) {
       ServiceLoader<T> serviceLoader =
                ServiceLoader.load(providerClass, loader);
       return serviceLoader.iterator();
    }

    private static ClassLoader wrap(final ClassLoader parent) {
        return parent != null ? AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() {
            @Override
            public ClassLoader run() {
                return new ClassLoader(parent) {
                    @Override
                    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
                        ReflectUtil.checkPackageAccess(name);
                        return super.loadClass(name, resolve);
                    }
                };
            }
        }) : null;
    }

    private static JMXConnector getConnectorAsService(ClassLoader loader,
                                                      JMXServiceURL url,
                                                      Map<String, ?> map)
        throws IOException {

        Iterator<JMXConnectorProvider> providers =
                getProviderIterator(JMXConnectorProvider.class, loader);
        JMXConnector connection;
        IOException exception = null;
        while (providers.hasNext()) {
            JMXConnectorProvider provider = providers.next();
            try {
                connection = provider.newJMXConnector(url, map);
                return connection;
            } catch (JMXProviderException e) {
                throw e;
            } catch (Exception e) {
                if (logger.traceOn())
                    logger.trace("getConnectorAsService",
                                 "URL[" + url +
                                 "] Service provider exception: " + e);
                if (!(e instanceof MalformedURLException)) {
                    if (exception == null) {
                        if (e instanceof IOException) {
                            exception = (IOException) e;
                        } else {
                            exception = EnvHelp.initCause(
                                new IOException(e.getMessage()), e);
                        }
                    }
                }
                continue;
            }
        }
        if (exception == null)
            return null;
        else
            throw exception;
    }

    static <T> T getProvider(String protocol,
                              String pkgs,
                              ClassLoader loader,
                              String providerClassName,
                              Class<T> targetInterface)
            throws IOException {

        StringTokenizer tokenizer = new StringTokenizer(pkgs, "|");

        while (tokenizer.hasMoreTokens()) {
            String pkg = tokenizer.nextToken();
            String className = (pkg + "." + protocol2package(protocol) +
                                "." + providerClassName);
            Class<?> providerClass;
            try {
                providerClass = Class.forName(className, true, loader);
            } catch (ClassNotFoundException e) {
                //Add trace.
                continue;
            }

            if (!targetInterface.isAssignableFrom(providerClass)) {
                final String msg =
                    "Provider class does not implement " +
                    targetInterface.getName() + ": " +
                    providerClass.getName();
                throw new JMXProviderException(msg);
            }

            // We have just proved that this cast is correct
            Class<? extends T> providerClassT = Util.cast(providerClass);
            try {
                return providerClassT.newInstance();
            } catch (Exception e) {
                final String msg =
                    "Exception when instantiating provider [" + className +
                    "]";
                throw new JMXProviderException(msg, e);
            }
        }

        return null;
    }

    static ClassLoader resolveClassLoader(Map<String, ?> environment) {
        ClassLoader loader = null;

        if (environment != null) {
            try {
                loader = (ClassLoader)
                    environment.get(PROTOCOL_PROVIDER_CLASS_LOADER);
            } catch (ClassCastException e) {
                final String msg =
                    "The ClassLoader supplied in the environment map using " +
                    "the " + PROTOCOL_PROVIDER_CLASS_LOADER +
                    " attribute is not an instance of java.lang.ClassLoader";
                throw new IllegalArgumentException(msg);
            }
        }

        if (loader == null) {
            loader = Thread.currentThread().getContextClassLoader();
        }

        return loader;
    }

    private static String protocol2package(String protocol) {
        return protocol.replace('+', '.').replace('-', '_');
    }
}
