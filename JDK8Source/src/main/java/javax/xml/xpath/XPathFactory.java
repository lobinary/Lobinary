/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.xpath;

/**
 * <p>An <code>XPathFactory</code> instance can be used to create
 * {@link javax.xml.xpath.XPath} objects.</p>
 *
 *<p>See {@link #newInstance(String uri)} for lookup mechanism.</p>
 *
 * <p>The {@link XPathFactory} class is not thread-safe. In other words,
 * it is the application's responsibility to ensure that at most
 * one thread is using a {@link XPathFactory} object at any
 * given moment. Implementations are encouraged to mark methods
 * as <code>synchronized</code> to protect themselves from broken clients.
 *
 * <p>{@link XPathFactory} is not re-entrant. While one of the
 * <code>newInstance</code> methods is being invoked, applications
 * may not attempt to recursively invoke a <code>newInstance</code> method,
 * even from the same thread.
 *
 * <p>
 *  <p> <code> XPathFactory </code>实例可用于创建{@link javax.xml.xpath.XPath}对象。</p>
 * 
 *  p>查看{@link #newInstance(String uri)}查找机制。</p>
 * 
 *  <p> {@link XPathFactory}类不是线程安全的。换句话说,应用程序有责任确保在任何给定时刻最多只有一个线程使用{@link XPathFactory}对象。
 * 鼓励实现将方法标记为<code> synchronized </code>以保护自己免受损坏的客户端。
 * 
 *  <p> {@ link XPathFactory}不是重新进入。
 * 虽然正在调用<code> newInstance </code>方法之一,但应用程序可能不会尝试递归调用<code> newInstance </code>方法,即使是从同一个线程。
 * 
 * 
 * @author  <a href="mailto:Norman.Walsh@Sun.com">Norman Walsh</a>
 * @author  <a href="mailto:Jeff.Suttor@Sun.com">Jeff Suttor</a>
 *
 * @since 1.5
 */
public abstract class XPathFactory {


    /**
     * <p>The default property name according to the JAXP spec.</p>
     * <p>
     *  <p>根据JAXP规范的默认属性名称</p>
     * 
     */
    public static final String DEFAULT_PROPERTY_NAME = "javax.xml.xpath.XPathFactory";

    /**
     * <p>Default Object Model URI.</p>
     * <p>
     *  <p>默认对象模型URI。</p>
     * 
     */
    public static final String DEFAULT_OBJECT_MODEL_URI = "http://java.sun.com/jaxp/xpath/dom";

    /**
     *<p> Take care of restrictions imposed by java security model </p>
     * <p>
     *  p>处理由java安全模型施加的限制</p>
     * 
     */
    private static SecuritySupport ss = new SecuritySupport() ;

    /**
     * <p>Protected constructor as {@link #newInstance()} or {@link #newInstance(String uri)}
     * or {@link #newInstance(String uri, String factoryClassName, ClassLoader classLoader)}
     * should be used to create a new instance of an <code>XPathFactory</code>.</p>
     * <p>
     *  <p>应使用受保护的构造函数{@link #newInstance()}或{@link #newInstance(String uri)}或{@link #newInstance(String uri,String factoryClassName,ClassLoader classLoader)}
     * 创建一个新实例<code> XPathFactory </code>。
     * </p>。
     * 
     */
    protected XPathFactory() {
    }

    /**
     * <p>Get a new <code>XPathFactory</code> instance using the default object model,
     * {@link #DEFAULT_OBJECT_MODEL_URI},
     * the W3C DOM.</p>
     *
     * <p>This method is functionally equivalent to:</p>
     * <pre>
     *   newInstance(DEFAULT_OBJECT_MODEL_URI)
     * </pre>
     *
     * <p>Since the implementation for the W3C DOM is always available, this method will never fail.</p>
     *
     * <p>
     *  <p>使用默认对象模型{@link #DEFAULT_OBJECT_MODEL_URI}(W3C DOM)获取一个新的<code> XPathFactory </code>实例。</p>
     * 
     *  <p>此方法在功能上等同于：</p>
     * <pre>
     *  newInstance(DEFAULT_OBJECT_MODEL_URI)
     * </pre>
     * 
     * <p>由于W3C DOM的实现始终可用,因此此方法永远不会失败。</p>
     * 
     * 
     * @return Instance of an <code>XPathFactory</code>.
     *
     * @throws RuntimeException When there is a failure in creating an
     *   <code>XPathFactory</code> for the default object model.
     */
    public static XPathFactory newInstance() {

        try {
                return newInstance(DEFAULT_OBJECT_MODEL_URI);
        } catch (XPathFactoryConfigurationException xpathFactoryConfigurationException) {
                throw new RuntimeException(
                        "XPathFactory#newInstance() failed to create an XPathFactory for the default object model: "
                        + DEFAULT_OBJECT_MODEL_URI
                        + " with the XPathFactoryConfigurationException: "
                        + xpathFactoryConfigurationException.toString()
                );
        }
    }

    /**
    * <p>Get a new <code>XPathFactory</code> instance using the specified object model.</p>
    *
    * <p>To find a <code>XPathFactory</code> object,
    * this method looks the following places in the following order where "the class loader" refers to the context class loader:</p>
    * <ol>
    *   <li>
    *     If the system property {@link #DEFAULT_PROPERTY_NAME} + ":uri" is present,
    *     where uri is the parameter to this method, then its value is read as a class name.
    *     The method will try to create a new instance of this class by using the class loader,
    *     and returns it if it is successfully created.
    *   </li>
    *   <li>
    *     ${java.home}/lib/jaxp.properties is read and the value associated with the key being the system property above is looked for.
    *     If present, the value is processed just like above.
    *   </li>
    *   <li>
    *     Use the service-provider loading facilities, defined by the
    *     {@link java.util.ServiceLoader} class, to attempt to locate and load an
    *     implementation of the service using the {@linkplain
    *     java.util.ServiceLoader#load(java.lang.Class) default loading mechanism}:
    *     the service-provider loading facility will use the {@linkplain
    *     java.lang.Thread#getContextClassLoader() current thread's context class loader}
    *     to attempt to load the service. If the context class
    *     loader is null, the {@linkplain
    *     ClassLoader#getSystemClassLoader() system class loader} will be used.
    *     <br>
    *     Each potential service provider is required to implement the method
    *     {@link #isObjectModelSupported(String objectModel)}.
    *     The first service provider found that supports the specified object
    *     model is returned.
    *     <br>
    *     In case of {@link java.util.ServiceConfigurationError} an
    *     {@link XPathFactoryConfigurationException} will be thrown.
    *   </li>
    *   <li>
    *     Platform default <code>XPathFactory</code> is located in a platform specific way.
    *     There must be a platform default XPathFactory for the W3C DOM, i.e. {@link #DEFAULT_OBJECT_MODEL_URI}.
    *   </li>
    * </ol>
    * <p>If everything fails, an <code>XPathFactoryConfigurationException</code> will be thrown.</p>
    *
    * <p>Tip for Trouble-shooting:</p>
    * <p>See {@link java.util.Properties#load(java.io.InputStream)} for exactly how a property file is parsed.
    * In particular, colons ':' need to be escaped in a property file, so make sure the URIs are properly escaped in it.
    * For example:</p>
    * <pre>
    *   http\://java.sun.com/jaxp/xpath/dom=org.acme.DomXPathFactory
    * </pre>
    *
    * <p>
    *  <p>使用指定的对象模型获取新的<code> XPathFactory </code>实例。</p>
    * 
    *  <p>要查找<code> XPathFactory </code>对象,此方法按以下顺序查找以下位置,其中"类加载器"引用上下文类加载器：</p>
    * <ol>
    * <li>
    *  如果系统属性{@link #DEFAULT_PROPERTY_NAME} +"：uri"存在,其中uri是此方法的参数,则将其值读取为类名称。
    * 该方法将尝试使用类加载器创建此类的新实例,并返回它,如果它成功创建。
    * </li>
    * <li>
    *  将读取$ {java.home} /lib/jaxp.properties,并查找与作为上述系统属性的键相关联的值。如果存在,则像上面一样处理该值。
    * </li>
    * <li>
    *  使用由{@link java.util.ServiceLoader}类定义的服务提供程序加载工具,尝试使用{@linkplain java.util.ServiceLoader#load(java.lang。
    * )来定位和加载服务的实现。
    * 类)默认加载机制}：service-provider加载工具将使用{@linkplain java.lang.Thread#getContextClassLoader()当前线程的上下文类加载器}来尝试
    * 加载服务。
    * )来定位和加载服务的实现。如果上下文类加载器为null,将使用{@linkplain ClassLoader#getSystemClassLoader()系统类加载器}。
    * <br>
    * 每个潜在的服务提供程序都需要实现{@link #isObjectModelSupported(String objectModel)}方法。发现支持指定对象模型的第一个服务提供程序返回。
    * <br>
    *  在{@link java.util.ServiceConfigurationError}的情况下,将抛出{@link XPathFactoryConfigurationException}。
    * </li>
    * <li>
    *  平台默认<code> XPathFactory </code>位于平台特定的方式。
    *  W3C DOM必须有一个平台默认XPathFactory,即{@link #DEFAULT_OBJECT_MODEL_URI}。
    * </li>
    * </ol>
    *  <p>如果一切失败,将抛出<code> XPathFactoryConfigurationException </code>。</p>
    * 
    *  <p>疑难解答提示：</p> <p>请参阅{@link java.util.Properties#load(java.io.InputStream)}了解如何解析属性文件。
    * 特别是,冒号'：'需要在属性文件中转义,因此请确保URI在其中正确转义。例如：</p>。
    * <pre>
    *  http \：//java.sun.com/jaxp/xpath/dom=org.acme.DomXPathFactory
    * </pre>
    * 
    * 
    * @param uri Identifies the underlying object model.
    *   The specification only defines the URI {@link #DEFAULT_OBJECT_MODEL_URI},
    *   <code>http://java.sun.com/jaxp/xpath/dom</code> for the W3C DOM,
    *   the org.w3c.dom package, and implementations are free to introduce other URIs for other object models.
    *
    * @return Instance of an <code>XPathFactory</code>.
    *
    * @throws XPathFactoryConfigurationException If the specified object model
    *      is unavailable, or if there is a configuration error.
    * @throws NullPointerException If <code>uri</code> is <code>null</code>.
    * @throws IllegalArgumentException If <code>uri</code> is <code>null</code>
    *   or <code>uri.length() == 0</code>.
    */
    public static XPathFactory newInstance(final String uri)
        throws XPathFactoryConfigurationException {

        if (uri == null) {
            throw new NullPointerException(
                    "XPathFactory#newInstance(String uri) cannot be called with uri == null");
        }

        if (uri.length() == 0) {
            throw new IllegalArgumentException(
                    "XPathFactory#newInstance(String uri) cannot be called with uri == \"\"");
        }

        ClassLoader classLoader = ss.getContextClassLoader();

        if (classLoader == null) {
            //use the current class loader
            classLoader = XPathFactory.class.getClassLoader();
        }

        XPathFactory xpathFactory = new XPathFactoryFinder(classLoader).newFactory(uri);

        if (xpathFactory == null) {
            throw new XPathFactoryConfigurationException(
                    "No XPathFactory implementation found for the object model: "
                    + uri);
        }

        return xpathFactory;
    }

    /**
     * <p>Obtain a new instance of a <code>XPathFactory</code> from a factory class name. <code>XPathFactory</code>
     * is returned if specified factory class supports the specified object model.
     * This function is useful when there are multiple providers in the classpath.
     * It gives more control to the application as it can specify which provider
     * should be loaded.</p>
     *
     *
     * <h2>Tip for Trouble-shooting</h2>
     * <p>Setting the <code>jaxp.debug</code> system property will cause
     * this method to print a lot of debug messages
     * to <code>System.err</code> about what it is doing and where it is looking at.</p>
     *
     * <p> If you have problems try:</p>
     * <pre>
     * java -Djaxp.debug=1 YourProgram ....
     * </pre>
     *
     * <p>
     *  <p>从工厂类名获取<code> XPathFactory </code>的新实例。如果指定的工厂类支持指定的对象模型,则返回<code> XPathFactory </code>。
     * 当类路径中有多个提供程序时,此函数很有用。它为应用程序提供了更多的控制,因为它可以指定应该加载哪个提供程序。</p>。
     * 
     * <h2>故障排除提示</h2> <p>设置<code> jaxp.debug </code>系统属性将导致此方法将大量调试消息打印到<code> System.err </code >关于它在做什么,它
     * 在看什么。
     * </p>。
     * 
     *  <p>如果您遇到问题,请尝试：</p>
     * <pre>
     *  java -Djaxp.debug = 1 YourProgram ....
     * </pre>
     * 
     * 
     * @param uri         Identifies the underlying object model. The specification only defines the URI
     *                    {@link #DEFAULT_OBJECT_MODEL_URI},<code>http://java.sun.com/jaxp/xpath/dom</code>
     *                    for the W3C DOM, the org.w3c.dom package, and implementations are free to introduce
     *                    other URIs for other object models.
     *
     * @param factoryClassName fully qualified factory class name that provides implementation of <code>javax.xml.xpath.XPathFactory</code>.
     *
     * @param classLoader <code>ClassLoader</code> used to load the factory class. If <code>null</code>
     *                     current <code>Thread</code>'s context classLoader is used to load the factory class.
     *
     *
     * @return New instance of a <code>XPathFactory</code>
     *
     * @throws XPathFactoryConfigurationException
     *                   if <code>factoryClassName</code> is <code>null</code>, or
     *                   the factory class cannot be loaded, instantiated
     *                   or the factory class does not support the object model specified
     *                   in the <code>uri</code> parameter.
     *
     * @throws NullPointerException If <code>uri</code> is <code>null</code>.
     * @throws IllegalArgumentException If <code>uri</code> is <code>null</code>
     *          or <code>uri.length() == 0</code>.
     *
     * @see #newInstance()
     * @see #newInstance(String uri)
     *
     * @since 1.6
     */
    public static XPathFactory newInstance(String uri, String factoryClassName, ClassLoader classLoader)
        throws XPathFactoryConfigurationException{
        ClassLoader cl = classLoader;

        if (uri == null) {
            throw new NullPointerException(
                    "XPathFactory#newInstance(String uri) cannot be called with uri == null");
        }

        if (uri.length() == 0) {
            throw new IllegalArgumentException(
                    "XPathFactory#newInstance(String uri) cannot be called with uri == \"\"");
        }

        if (cl == null) {
            cl = ss.getContextClassLoader();
        }

        XPathFactory f = new XPathFactoryFinder(cl).createInstance(factoryClassName);

        if (f == null) {
            throw new XPathFactoryConfigurationException(
                    "No XPathFactory implementation found for the object model: "
                    + uri);
        }
        //if this factory supports the given schemalanguage return this factory else thrown exception
        if (f.isObjectModelSupported(uri)) {
            return f;
        } else {
            throw new XPathFactoryConfigurationException("Factory "
                    + factoryClassName + " doesn't support given " + uri
                    + " object model");
        }

    }

    /**
     * <p>Is specified object model supported by this <code>XPathFactory</code>?</p>
     *
     * <p>
     *  <p>此<code> XPathFactory </code>?</p>支持指定的对象模型
     * 
     * 
     * @param objectModel Specifies the object model which the returned <code>XPathFactory</code> will understand.
     *
     * @return <code>true</code> if <code>XPathFactory</code> supports <code>objectModel</code>, else <code>false</code>.
     *
     * @throws NullPointerException If <code>objectModel</code> is <code>null</code>.
     * @throws IllegalArgumentException If <code>objectModel.length() == 0</code>.
     */
    public abstract boolean isObjectModelSupported(String objectModel);

    /**
     * <p>Set a feature for this <code>XPathFactory</code> and
     * <code>XPath</code>s created by this factory.</p>
     *
     * <p>
     * Feature names are fully qualified {@link java.net.URI}s.
     * Implementations may define their own features.
     * An {@link XPathFactoryConfigurationException} is thrown if this
     * <code>XPathFactory</code> or the <code>XPath</code>s
     * it creates cannot support the feature.
     * It is possible for an <code>XPathFactory</code> to expose a feature value
     * but be unable to change its state.
     * </p>
     *
     * <p>
     * All implementations are required to support the {@link javax.xml.XMLConstants#FEATURE_SECURE_PROCESSING} feature.
     * When the feature is <code>true</code>, any reference to  an external function is an error.
     * Under these conditions, the implementation must not call the {@link XPathFunctionResolver}
     * and must throw an {@link XPathFunctionException}.
     * </p>
     *
     * <p>
     *  <p>为此工厂创建的<code> XPathFactory </code>和<code> XPath </code>设置一个功能。</p>
     * 
     * <p>
     *  功能名称是完全限定的{@link java.net.URI}。实现可以定义它们自己的特征。
     * 如果此<code> XPathFactory </code>或其创建的<code> XPath </code>无法支持该功能,则会抛出{@link XPathFactoryConfigurationException}
     * 。
     *  功能名称是完全限定的{@link java.net.URI}。实现可以定义它们自己的特征。一个<code> XPathFactory </code>可以暴露一个特征值,但是无法改变它的状态。
     * </p>
     * 
     * <p>
     *  所有实现都需要支持{@link javax.xml.XMLConstants#FEATURE_SECURE_PROCESSING}功能。
     * 当功能是<code> true </code>时,对外部函数的任何引用都是错误。
     * 在这些条件下,实现不能调用{@link XPathFunctionResolver}并且必须抛出{@link XPathFunctionException}。
     * </p>
     * 
     * 
     * @param name Feature name.
     * @param value Is feature state <code>true</code> or <code>false</code>.
     *
     * @throws XPathFactoryConfigurationException if this <code>XPathFactory</code> or the <code>XPath</code>s
     *   it creates cannot support this feature.
     * @throws NullPointerException if <code>name</code> is <code>null</code>.
     */
    public abstract void setFeature(String name, boolean value)
            throws XPathFactoryConfigurationException;

    /**
     * <p>Get the state of the named feature.</p>
     *
     * <p>
     * Feature names are fully qualified {@link java.net.URI}s.
     * Implementations may define their own features.
     * An {@link XPathFactoryConfigurationException} is thrown if this
     * <code>XPathFactory</code> or the <code>XPath</code>s
     * it creates cannot support the feature.
     * It is possible for an <code>XPathFactory</code> to expose a feature value
     * but be unable to change its state.
     * </p>
     *
     * <p>
     *  <p>获取命名要素的状态。</p>
     * 
     * <p>
     * 功能名称是完全限定的{@link java.net.URI}。实现可以定义它们自己的特征。
     * 如果此<code> XPathFactory </code>或其创建的<code> XPath </code>无法支持该功能,则会抛出{@link XPathFactoryConfigurationException}
     * 。
     * 功能名称是完全限定的{@link java.net.URI}。实现可以定义它们自己的特征。一个<code> XPathFactory </code>可以暴露一个特征值,但是无法改变它的状态。
     * </p>
     * 
     * 
     * @param name Feature name.
     *
     * @return State of the named feature.
     *
     * @throws XPathFactoryConfigurationException if this
     *   <code>XPathFactory</code> or the <code>XPath</code>s
     *   it creates cannot support this feature.
     * @throws NullPointerException if <code>name</code> is <code>null</code>.
     */
    public abstract boolean getFeature(String name)
            throws XPathFactoryConfigurationException;

    /**
     * <p>Establish a default variable resolver.</p>
     *
     * <p>Any <code>XPath</code> objects constructed from this factory will use
     * the specified resolver by default.</p>
     *
     * <p>A <code>NullPointerException</code> is thrown if <code>resolver</code>
     * is <code>null</code>.</p>
     *
     * <p>
     *  <p>建立一个默认变量解析器。</p>
     * 
     *  <p>默认情况下,从此工厂构造的任何<code> XPath </code>对象将使用指定的解析器。</p>
     * 
     *  <p>如果<code> resolver </code>为<code> null </code>,则抛出<code> NullPointerException </code>。</p>
     * 
     * 
     * @param resolver Variable resolver.
     *
     * @throws NullPointerException If <code>resolver</code> is
     *   <code>null</code>.
     */
    public abstract void setXPathVariableResolver(XPathVariableResolver resolver);

    /**
     * <p>Establish a default function resolver.</p>
     *
     * <p>Any <code>XPath</code> objects constructed from this factory will
     * use the specified resolver by default.</p>
     *
     * <p>A <code>NullPointerException</code> is thrown if
     * <code>resolver</code> is <code>null</code>.</p>
     *
     * <p>
     *  <p>建立默认函数解析器。</p>
     * 
     *  <p>默认情况下,从此工厂构造的任何<code> XPath </code>对象将使用指定的解析器。</p>
     * 
     *  <p>如果<code> resolver </code>为<code> null </code>,则抛出<code> NullPointerException </code>。</p>
     * 
     * @param resolver XPath function resolver.
     *
     * @throws NullPointerException If <code>resolver</code> is
     *   <code>null</code>.
     */
    public abstract void setXPathFunctionResolver(XPathFunctionResolver resolver);

    /**
    * <p>Return a new <code>XPath</code> using the underlying object
    * model determined when the <code>XPathFactory</code> was instantiated.</p>
    *
    * <p>
    * 
    * 
    * @return New instance of an <code>XPath</code>.
    */
    public abstract XPath newXPath();
}
