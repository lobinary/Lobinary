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

package javax.xml.transform;

/**
 * <p>A TransformerFactory instance can be used to create
 * {@link javax.xml.transform.Transformer} and
 * {@link javax.xml.transform.Templates} objects.</p>
 *
 * <p>The system property that determines which Factory implementation
 * to create is named <code>"javax.xml.transform.TransformerFactory"</code>.
 * This property names a concrete subclass of the
 * <code>TransformerFactory</code> abstract class. If the property is not
 * defined, a platform default is be used.</p>
 *
 * <p>
 *  <p> TransformerFactory实例可用于创建{@link javax.xml.transform.Transformer}和{@link javax.xml.transform.Templates}
 * 对象。
 * </p>。
 * 
 *  <p>确定要创建的Factory实现的系统属性名为<code>"javax.xml.transform.TransformerFactory"</code>。
 * 此属性命名<code> TransformerFactory </code>抽象类的一个具体子类。如果未定义属性,则使用平台默认值。</p>。
 * 
 * 
 * @author <a href="mailto:Jeff.Suttor@Sun.com">Jeff Suttor</a>
 * @author <a href="mailto:Neeraj.Bajaj@sun.com">Neeraj Bajaj</a>
 *
 * @since 1.5
 */
public abstract class TransformerFactory {

    /**
     * Default constructor is protected on purpose.
     * <p>
     *  默认构造函数是有目的的保护。
     * 
     */
    protected TransformerFactory() { }



    /**
     * <p>Obtain a new instance of a <code>TransformerFactory</code>.
     * This static method creates a new factory instance.</p>
     * <p>This method uses the following ordered lookup procedure to determine
     * the <code>TransformerFactory</code> implementation class to
     * load:</p>
     * <ul>
     * <li>
     * Use the <code>javax.xml.transform.TransformerFactory</code> system
     * property.
     * </li>
     * <li>
     * Use the properties file "lib/jaxp.properties" in the JRE directory.
     * This configuration file is in standard <code>java.util.Properties
     * </code> format and contains the fully qualified name of the
     * implementation class with the key being the system property defined
     * above.
     * <br>
     * The jaxp.properties file is read only once by the JAXP implementation
     * and it's values are then cached for future use.  If the file does not exist
     * when the first attempt is made to read from it, no further attempts are
     * made to check for its existence.  It is not possible to change the value
     * of any property in jaxp.properties after it has been read for the first time.
     * </li>
     * <li>
     *   Use the service-provider loading facilities, defined by the
     *   {@link java.util.ServiceLoader} class, to attempt to locate and load an
     *   implementation of the service using the {@linkplain
     *   java.util.ServiceLoader#load(java.lang.Class) default loading mechanism}:
     *   the service-provider loading facility will use the {@linkplain
     *   java.lang.Thread#getContextClassLoader() current thread's context class loader}
     *   to attempt to load the service. If the context class
     *   loader is null, the {@linkplain
     *   ClassLoader#getSystemClassLoader() system class loader} will be used.
     * </li>
     * <li>
     *   Otherwise, the system-default implementation is returned.
     * </li>
     * </ul>
     *
     * <p>Once an application has obtained a reference to a <code>
     * TransformerFactory</code> it can use the factory to configure
     * and obtain transformer instances.</p>
     *
     * <p>
     *  <p>获取一个<code> TransformerFactory </code>的新实例。
     *  </p> <p>此方法使用以下有序查找过程来确定要加载的<code> TransformerFactory </code>实现类：</p>。
     * <ul>
     * <li>
     *  使用<code> javax.xml.transform.TransformerFactory </code>系统属性。
     * </li>
     * <li>
     *  使用JRE目录中的属性文件"lib / jaxp.properties"。
     * 此配置文件采用标准的<code> java.util.Properties </code>格式,并包含实现类的完全限定名,键是上面定义的系统属性。
     * <br>
     * jaxp.properties文件由JAXP实现只读一次,然后将其值缓存以供将来使用。如果文件在第一次尝试读取文件时不存在,则不会进一步尝试检查其是否存在。
     * 在首次读取jaxp.properties后,无法更改任何属性的值。
     * </li>
     * <li>
     *  使用由{@link java.util.ServiceLoader}类定义的服务提供程序加载工具,尝试使用{@linkplain java.util.ServiceLoader#load(java.lang。
     * )来定位和加载服务的实现。
     * 类)默认加载机制}：service-provider加载工具将使用{@linkplain java.lang.Thread#getContextClassLoader()当前线程的上下文类加载器}来尝试
     * 加载服务。
     * )来定位和加载服务的实现。如果上下文类加载器为null,将使用{@linkplain ClassLoader#getSystemClassLoader()系统类加载器}。
     * </li>
     * <li>
     *  否则,返回系统默认实现。
     * </li>
     * </ul>
     * 
     *  <p>一旦应用程序获得对<code> TransformerFactory </code>的引用,它就可以使用工厂来配置和获取变换器实例。</p>
     * 
     * 
     * @return new TransformerFactory instance, never null.
     *
     * @throws TransformerFactoryConfigurationError Thrown in case of {@linkplain
     * java.util.ServiceConfigurationError service configuration error} or if
     * the implementation is not available or cannot be instantiated.
     */
    public static TransformerFactory newInstance()
        throws TransformerFactoryConfigurationError {

        return FactoryFinder.find(
            /* The default property name according to the JAXP spec */
            TransformerFactory.class,
            /* The fallback implementation class name, XSLTC */
            "com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl");
    }

    /**
     * <p>Obtain a new instance of a <code>TransformerFactory</code> from factory class name.
     * This function is useful when there are multiple providers in the classpath.
     * It gives more control to the application as it can specify which provider
     * should be loaded.</p>
     *
     * <p>Once an application has obtained a reference to a <code>
     * TransformerFactory</code> it can use the factory to configure
     * and obtain transformer instances.</p>
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
     *  <p>从工厂类名获取一个<code> TransformerFactory </code>的新实例。当类路径中有多个提供程序时,此函数很有用。
     * 它为应用程序提供了更多的控制,因为它可以指定应该加载哪个提供程序。</p>。
     * 
     * <p>一旦应用程序获得对<code> TransformerFactory </code>的引用,它就可以使用工厂来配置和获取变换器实例。</p>
     * 
     *  <h2>故障排除提示</h2> <p>设置<code> jaxp.debug </code>系统属性将导致此方法将大量调试消息打印到<code> System.err </code >关于它在做什么,
     * 它在看什么。
     * </p>。
     * 
     *  <p>如果您遇到问题,请尝试：</p>
     * <pre>
     *  java -Djaxp.debug = 1 YourProgram ....
     * </pre>
     * 
     * 
     * @param factoryClassName fully qualified factory class name that provides implementation of <code>javax.xml.transform.TransformerFactory</code>.
     *
     * @param classLoader <code>ClassLoader</code> used to load the factory class. If <code>null</code>
     *                     current <code>Thread</code>'s context classLoader is used to load the factory class.
     *
     * @return new TransformerFactory instance, never null.
     *
     * @throws TransformerFactoryConfigurationError
     *                    if <code>factoryClassName</code> is <code>null</code>, or
     *                   the factory class cannot be loaded, instantiated.
     *
     * @see #newInstance()
     *
     * @since 1.6
     */
    public static TransformerFactory newInstance(String factoryClassName, ClassLoader classLoader)
        throws TransformerFactoryConfigurationError{

        //do not fallback if given classloader can't find the class, throw exception
        return  FactoryFinder.newInstance(TransformerFactory.class,
                    factoryClassName, classLoader, false, false);
    }
    /**
     * <p>Process the <code>Source</code> into a <code>Transformer</code>
     * <code>Object</code>.  The <code>Source</code> is an XSLT document that
     * conforms to <a href="http://www.w3.org/TR/xslt">
     * XSL Transformations (XSLT) Version 1.0</a>.  Care must
     * be taken not to use this <code>Transformer</code> in multiple
     * <code>Thread</code>s running concurrently.
     * Different <code>TransformerFactories</code> can be used concurrently by
     * different <code>Thread</code>s.</p>
     *
     * <p>
     *  <p>将<code> Source </code>处理为<code> Transformer </code> <code> Object </code>。
     *  <code> Source </code>是一个符合<a href="http://www.w3.org/TR/xslt"> XSL转换(XSLT)1.0版</a>的XSLT文档。
     * 必须注意不要在并行运行的多个<code> Thread </code>中使用此<code> Transformer </code>。
     * 不同的<code> TransformerFactories </code>可以由不同的<code> Thread </code>同时使用。</p>。
     * 
     * 
     * @param source <code>Source </code> of XSLT document used to create
     *   <code>Transformer</code>.
     *   Examples of XML <code>Source</code>s include
     *   {@link javax.xml.transform.dom.DOMSource DOMSource},
     *   {@link javax.xml.transform.sax.SAXSource SAXSource}, and
     *   {@link javax.xml.transform.stream.StreamSource StreamSource}.
     *
     * @return A <code>Transformer</code> object that may be used to perform
     *   a transformation in a single <code>Thread</code>, never
     *   <code>null</code>.
     *
     * @throws TransformerConfigurationException Thrown if there are errors when
     *    parsing the <code>Source</code> or it is not possible to create a
     *   <code>Transformer</code> instance.
     *
     * @see <a href="http://www.w3.org/TR/xslt">
     *   XSL Transformations (XSLT) Version 1.0</a>
     */
    public abstract Transformer newTransformer(Source source)
        throws TransformerConfigurationException;

    /**
     * <p>Create a new <code>Transformer</code> that performs a copy
     * of the <code>Source</code> to the <code>Result</code>.
     * i.e. the "<em>identity transform</em>".</p>
     *
     * <p>
     *  <p>创建一个新的<code> Transformer </code>,用于执行<code> Source </code>到<code> Result </code>的副本。
     * 即"身份变换</em>"。</p>。
     * 
     * 
     * @return A Transformer object that may be used to perform a transformation
     * in a single thread, never null.
     *
     * @throws TransformerConfigurationException When it is not
     *   possible to create a <code>Transformer</code> instance.
     */
    public abstract Transformer newTransformer()
        throws TransformerConfigurationException;

    /**
     * Process the Source into a Templates object, which is a
     * a compiled representation of the source. This Templates object
     * may then be used concurrently across multiple threads.  Creating
     * a Templates object allows the TransformerFactory to do detailed
     * performance optimization of transformation instructions, without
     * penalizing runtime transformation.
     *
     * <p>
     *  将源处理为模板对象,它是源的编译表示。然后,可以跨多个线程并发使用此模板对象。创建模板对象允许TransformerFactory对转换指令进行详细的性能优化,而不会惩罚运行时转换。
     * 
     * 
     * @param source An object that holds a URL, input stream, etc.
     *
     * @return A Templates object capable of being used for transformation
     *   purposes, never <code>null</code>.
     *
     * @throws TransformerConfigurationException When parsing to
     *   construct the Templates object fails.
     */
    public abstract Templates newTemplates(Source source)
        throws TransformerConfigurationException;

    /**
     * <p>Get the stylesheet specification(s) associated with the
     * XML <code>Source</code> document via the
     * <a href="http://www.w3.org/TR/xml-stylesheet/">
     * xml-stylesheet processing instruction</a> that match the given criteria.
     * Note that it is possible to return several stylesheets, in which case
     * they are applied as if they were a list of imports or cascades in a
     * single stylesheet.</p>
     *
     * <p>
     * <p>通过获取与XML <code> Source </code>文档相关联的样式表规范
     * <a href="http://www.w3.org/TR/xml-stylesheet/">
     *  xml样式表处理指令</a>匹配给定的标准。注意,可以返回多个样式表,在这种情况下,它们被应用,就像它们是单个样式表中的导入或级联列表一样。</p>
     * 
     * 
     * @param source The XML source document.
     * @param media The media attribute to be matched.  May be null, in which
     *      case the prefered templates will be used (i.e. alternate = no).
     * @param title The value of the title attribute to match.  May be null.
     * @param charset The value of the charset attribute to match.  May be null.
     *
     * @return A <code>Source</code> <code>Object</code> suitable for passing
     *   to the <code>TransformerFactory</code>.
     *
     * @throws TransformerConfigurationException An <code>Exception</code>
     *   is thrown if an error occurings during parsing of the
     *   <code>source</code>.
     *
     * @see <a href="http://www.w3.org/TR/xml-stylesheet/">
     *   Associating Style Sheets with XML documents Version 1.0</a>
     */
    public abstract Source getAssociatedStylesheet(
        Source source,
        String media,
        String title,
        String charset)
        throws TransformerConfigurationException;

    /**
     * Set an object that is used by default during the transformation
     * to resolve URIs used in document(), xsl:import, or xsl:include.
     *
     * <p>
     *  设置转换期间默认使用的对象,以解析在document(),xsl：import或xsl：include中使用的URI。
     * 
     * 
     * @param resolver An object that implements the URIResolver interface,
     * or null.
     */
    public abstract void setURIResolver(URIResolver resolver);

    /**
     * Get the object that is used by default during the transformation
     * to resolve URIs used in document(), xsl:import, or xsl:include.
     *
     * <p>
     *  获取在转换期间默认使用的对象,以解析在document(),xsl：import或xsl：include中使用的URI。
     * 
     * 
     * @return The URIResolver that was set with setURIResolver.
     */
    public abstract URIResolver getURIResolver();

    //======= CONFIGURATION METHODS =======

        /**
         * <p>Set a feature for this <code>TransformerFactory</code> and <code>Transformer</code>s
         * or <code>Template</code>s created by this factory.</p>
         *
         * <p>
         * Feature names are fully qualified {@link java.net.URI}s.
         * Implementations may define their own features.
         * An {@link TransformerConfigurationException} is thrown if this <code>TransformerFactory</code> or the
         * <code>Transformer</code>s or <code>Template</code>s it creates cannot support the feature.
         * It is possible for an <code>TransformerFactory</code> to expose a feature value but be unable to change its state.
         * </p>
         *
         * <p>All implementations are required to support the {@link javax.xml.XMLConstants#FEATURE_SECURE_PROCESSING} feature.
         * When the feature is:</p>
         * <ul>
         *   <li>
         *     <code>true</code>: the implementation will limit XML processing to conform to implementation limits
         *     and behave in a secure fashion as defined by the implementation.
         *     Examples include resolving user defined style sheets and functions.
         *     If XML processing is limited for security reasons, it will be reported via a call to the registered
         *     {@link ErrorListener#fatalError(TransformerException exception)}.
         *     See {@link  #setErrorListener(ErrorListener listener)}.
         *   </li>
         *   <li>
         *     <code>false</code>: the implementation will processing XML according to the XML specifications without
         *     regard to possible implementation limits.
         *   </li>
         * </ul>
         *
         * <p>
         *  <p>为此工厂创建的<code> TransformerFactory </code>和<code> Transformer </code>或<code>模板</code>设置功能。<​​/ p>
         * 
         * <p>
         *  功能名称是完全限定的{@link java.net.URI}。实现可以定义它们自己的特征。
         * 如果此创建的<code> TransformerFactory </code>或<code> Transformer </code>或<code> Template </code>无法支持该功能,则会抛
         * 出{@link TransformerConfigurationException}。
         *  功能名称是完全限定的{@link java.net.URI}。实现可以定义它们自己的特征。
         * 一个<code> TransformerFactory </code>可以暴露一个特征值,但是不能改变它的状态。
         * </p>
         * 
         *  <p>所有实现都需要支持{@link javax.xml.XMLConstants#FEATURE_SECURE_PROCESSING}功能。功能为：</p>
         * <ul>
         * <li>
         * <code> true </code>：实现将限制XML处理以符合实现限制,并以实现所定义的安全方式运行。示例包括解析用户定义的样式表和函数。
         * 如果XML处理由于安全原因而受到限制,则将通过调用注册的{@link ErrorListener#fatalError(TransformerException exception)}来报告。
         * 请参阅{@link #setErrorListener(ErrorListener listener)}。
         * </li>
         * <li>
         *  <code> false </code>：实现将根据XML规范处理XML,而不考虑可能的实现限制。
         * </li>
         * </ul>
         * 
         * 
         * @param name Feature name.
         * @param value Is feature state <code>true</code> or <code>false</code>.
         *
         * @throws TransformerConfigurationException if this <code>TransformerFactory</code>
         *   or the <code>Transformer</code>s or <code>Template</code>s it creates cannot support this feature.
     * @throws NullPointerException If the <code>name</code> parameter is null.
         */
        public abstract void setFeature(String name, boolean value)
                throws TransformerConfigurationException;

    /**
     * Look up the value of a feature.
     *
         * <p>
         * Feature names are fully qualified {@link java.net.URI}s.
         * Implementations may define their own features.
         * <code>false</code> is returned if this <code>TransformerFactory</code> or the
         * <code>Transformer</code>s or <code>Template</code>s it creates cannot support the feature.
         * It is possible for an <code>TransformerFactory</code> to expose a feature value but be unable to change its state.
         * </p>
         *
         * <p>
         *  查找要素的值。
         * 
         * <p>
         *  功能名称是完全限定的{@link java.net.URI}。实现可以定义它们自己的特征。
         * 如果此创建的<code> TransformerFactory </code>或<code> Transformer </code>或<code> Template </code>不能支持此功能,则会返
         * 回<code> false </code>。
         *  功能名称是完全限定的{@link java.net.URI}。实现可以定义它们自己的特征。
         * 一个<code> TransformerFactory </code>可以暴露一个特征值,但是不能改变它的状态。
         * </p>
         * 
         * 
         * @param name Feature name.
         *
     * @return The current state of the feature, <code>true</code> or <code>false</code>.
     *
     * @throws NullPointerException If the <code>name</code> parameter is null.
     */
    public abstract boolean getFeature(String name);

    /**
     * Allows the user to set specific attributes on the underlying
     * implementation.  An attribute in this context is defined to
     * be an option that the implementation provides.
     * An <code>IllegalArgumentException</code> is thrown if the underlying
     * implementation doesn't recognize the attribute.
     * <p>
     * All implementations that implement JAXP 1.5 or newer are required to
     * support the {@link javax.xml.XMLConstants#ACCESS_EXTERNAL_DTD}  and
     * {@link javax.xml.XMLConstants#ACCESS_EXTERNAL_STYLESHEET} properties.
     * </p>
     * <ul>
     *   <li>
     *      <p>
     *      Access to external DTDs in the source file is restricted to the protocols
     *      specified by the {@link javax.xml.XMLConstants#ACCESS_EXTERNAL_DTD} property.
     *      If access is denied during transformation due to the restriction of this property,
     *      {@link javax.xml.transform.TransformerException} will be thrown by
     *      {@link javax.xml.transform.Transformer#transform(Source, Result)}.
     *      </p>
     *      <p>
     *      Access to external DTDs in the stylesheet is restricted to the protocols
     *      specified by the {@link javax.xml.XMLConstants#ACCESS_EXTERNAL_DTD} property.
     *      If access is denied during the creation of a new transformer due to the
     *      restriction of this property,
     *      {@link javax.xml.transform.TransformerConfigurationException} will be thrown
     *      by the {@link #newTransformer(Source)} method.
     *      </p>
     *      <p>
     *      Access to external reference set by the stylesheet processing instruction,
     *      Import and Include element is restricted to the protocols specified by the
     *      {@link javax.xml.XMLConstants#ACCESS_EXTERNAL_STYLESHEET} property.
     *      If access is denied during the creation of a new transformer due to the
     *      restriction of this property,
     *      {@link javax.xml.transform.TransformerConfigurationException} will be thrown
     *      by the {@link #newTransformer(Source)} method.
     *      </p>
     *      <p>
     *      Access to external document through XSLT document function is restricted
     *      to the protocols specified by the property. If access is denied during
     *      the transformation due to the restriction of this property,
     *      {@link javax.xml.transform.TransformerException} will be thrown by the
     *      {@link javax.xml.transform.Transformer#transform(Source, Result)} method.
     *      </p>
     *   </li>
     * </ul>
     *
     * <p>
     *  允许用户在底层实现上设置特定属性。此上下文中的属性被定义为实现提供的选项。如果底层实现不能识别属性,则抛出<code> IllegalArgumentException </code>。
     * <p>
     * 实现JAXP 1.5或更高版本的所有实现都需要支持{@link javax.xml.XMLConstants#ACCESS_EXTERNAL_DTD}和{@link javax.xml.XMLConstants#ACCESS_EXTERNAL_STYLESHEET}
     * 属性。
     * </p>
     * <ul>
     * <li>
     * <p>
     *  对源文件中的外部DTD的访问仅限于{@link javax.xml.XMLConstants#ACCESS_EXTERNAL_DTD}属性指定的协议。
     * 如果由于此属性的限制而在转换期间拒绝访问,{@link javax.xml.transform.TransformerException}将由{@link javax.xml.transform.Transformer#transform(Source,Result)}
     * 抛出。
     *  对源文件中的外部DTD的访问仅限于{@link javax.xml.XMLConstants#ACCESS_EXTERNAL_DTD}属性指定的协议。
     * </p>
     * <p>
     *  访问样式表中的外部DTD仅限于{@link javax.xml.XMLConstants#ACCESS_EXTERNAL_DTD}属性指定的协议。
     * 如果由于此属性的限制而在创建新变换器期间拒绝访问,{@link javax.xml.transform.TransformerConfigurationException}将由{@link #newTransformer(Source)}
     * 方法抛出。
     *  访问样式表中的外部DTD仅限于{@link javax.xml.XMLConstants#ACCESS_EXTERNAL_DTD}属性指定的协议。
     * </p>
     * <p>
     *  访问由样式表处理指令设置的外部引用,Import和Include元素仅限于{@link javax.xml.XMLConstants#ACCESS_EXTERNAL_STYLESHEET}属性指定的协
     * 议。
     * 
     * @param name The name of the attribute.
     * @param value The value of the attribute.
     *
     * @throws IllegalArgumentException When implementation does not
     *   recognize the attribute.
     */
    public abstract void setAttribute(String name, Object value);

    /**
     * Allows the user to retrieve specific attributes on the underlying
     * implementation.
     * An <code>IllegalArgumentException</code> is thrown if the underlying
     * implementation doesn't recognize the attribute.
     *
     * <p>
     * 如果由于此属性的限制而在创建新变换器期间拒绝访问,{@link javax.xml.transform.TransformerConfigurationException}将由{@link #newTransformer(Source)}
     * 方法抛出。
     * </p>
     * <p>
     * 通过XSLT文档功能访问外部文档仅限于属性指定的协议。
     * 如果由于此属性的限制而在转换期间拒绝访问,{@link javax.xml.transform.TransformerException}将由{@link javax.xml.transform.Transformer#transform(Source,Result)}
     * 方法抛出。
     * 通过XSLT文档功能访问外部文档仅限于属性指定的协议。
     * </p>
     * </li>
     * </ul>
     * 
     * @param name The name of the attribute.
     *
     * @return value The value of the attribute.
     *
     * @throws IllegalArgumentException When implementation does not
     *   recognize the attribute.
     */
    public abstract Object getAttribute(String name);

    /**
     * Set the error event listener for the TransformerFactory, which
     * is used for the processing of transformation instructions,
     * and not for the transformation itself.
     * An <code>IllegalArgumentException</code> is thrown if the
     * <code>ErrorListener</code> listener is <code>null</code>.
     *
     * <p>
     * 
     * 
     * @param listener The new error listener.
     *
     * @throws IllegalArgumentException When <code>listener</code> is
     *   <code>null</code>
     */
    public abstract void setErrorListener(ErrorListener listener);

    /**
     * Get the error event handler for the TransformerFactory.
     *
     * <p>
     *  允许用户检索基础实现上的特定属性。如果底层实现不能识别属性,则抛出<code> IllegalArgumentException </code>。
     * 
     * 
     * @return The current error handler, which should never be null.
     */
    public abstract ErrorListener getErrorListener();

}
