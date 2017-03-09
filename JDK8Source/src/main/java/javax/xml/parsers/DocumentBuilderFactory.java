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

package javax.xml.parsers;

import javax.xml.validation.Schema;

/**
 * Defines a factory API that enables applications to obtain a
 * parser that produces DOM object trees from XML documents.
 *
 * <p>
 *  定义工厂API,使应用程序能够获取从XML文档生成DOM对象树的解析器。
 * 
 * 
 * @author <a href="mailto:Jeff.Suttor@Sun.com">Jeff Suttor</a>
 * @author <a href="mailto:Neeraj.Bajaj@sun.com">Neeraj Bajaj</a>
 *
 * @version $Revision: 1.9 $, $Date: 2010/05/25 16:19:44 $

 */

public abstract class DocumentBuilderFactory {

    private boolean validating = false;
    private boolean namespaceAware = false;
    private boolean whitespace = false;
    private boolean expandEntityRef = true;
    private boolean ignoreComments = false;
    private boolean coalescing = false;

    /**
     * <p>Protected constructor to prevent instantiation.
     * Use {@link #newInstance()}.</p>
     * <p>
     *  <p>受保护的构造函数以防止实例化。使用{@link #newInstance()}。</p>
     * 
     */
    protected DocumentBuilderFactory () {
    }

    /**
     * Obtain a new instance of a
     * <code>DocumentBuilderFactory</code>. This static method creates
     * a new factory instance.
     * This method uses the following ordered lookup procedure to determine
     * the <code>DocumentBuilderFactory</code> implementation class to
     * load:
     * <ul>
     * <li>
     * Use the <code>javax.xml.parsers.DocumentBuilderFactory</code> system
     * property.
     * </li>
     * <li>
     * Use the properties file "lib/jaxp.properties" in the JRE directory.
     * This configuration file is in standard <code>java.util.Properties
     * </code> format and contains the fully qualified name of the
     * implementation class with the key being the system property defined
     * above.
     *
     * The jaxp.properties file is read only once by the JAXP implementation
     * and it's values are then cached for future use.  If the file does not exist
     * when the first attempt is made to read from it, no further attempts are
     * made to check for its existence.  It is not possible to change the value
     * of any property in jaxp.properties after it has been read for the first time.
     * </li>
     * <li>
     * Uses the service-provider loading facilities, defined by the
     * {@link java.util.ServiceLoader} class, to attempt to locate and load an
     * implementation of the service using the {@linkplain
     * java.util.ServiceLoader#load(java.lang.Class) default loading mechanism}:
     * the service-provider loading facility will use the {@linkplain
     * java.lang.Thread#getContextClassLoader() current thread's context class loader}
     * to attempt to load the service. If the context class
     * loader is null, the {@linkplain
     * ClassLoader#getSystemClassLoader() system class loader} will be used.
     * </li>
     * <li>
     * Otherwise, the system-default implementation is returned.
     * </li>
     * </ul>
     *
     * Once an application has obtained a reference to a
     * <code>DocumentBuilderFactory</code> it can use the factory to
     * configure and obtain parser instances.
     *
     *
     * <h2>Tip for Trouble-shooting</h2>
     * <p>Setting the <code>jaxp.debug</code> system property will cause
     * this method to print a lot of debug messages
     * to <code>System.err</code> about what it is doing and where it is looking at.</p>
     *
     * <p> If you have problems loading {@link DocumentBuilder}s, try:</p>
     * <pre>
     * java -Djaxp.debug=1 YourProgram ....
     * </pre>
     *
     * <p>
     *  获取<code> DocumentBuilderFactory </code>的新实例。这个静态方法创建一个新的工厂实例。
     * 此方法使用以下有序查找过程来确定要加载的<code> DocumentBuilderFactory </code>实现类：。
     * <ul>
     * <li>
     *  使用<code> javax.xml.parsers.DocumentBuilderFactory </code>系统属性。
     * </li>
     * <li>
     *  使用JRE目录中的属性文件"lib / jaxp.properties"。
     * 此配置文件采用标准的<code> java.util.Properties </code>格式,并包含实现类的完全限定名,键是上面定义的系统属性。
     * 
     *  jaxp.properties文件由JAXP实现只读一次,然后将其值缓存以供将来使用。如果文件在第一次尝试读取文件时不存在,则不会进一步尝试检查其是否存在。
     * 在首次读取jaxp.properties后,无法更改任何属性的值。
     * </li>
     * <li>
     * 使用由{@link java.util.ServiceLoader}类定义的服务提供程序加载工具,尝试使用{@linkplain java.util.ServiceLoader#load(java.lang。
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
     *  一旦应用程序获得了对<code> DocumentBuilderFactory </code>的引用,它就可以使用工厂来配置和获取解析器实例。
     * 
     *  <h2>故障排除提示</h2> <p>设置<code> jaxp.debug </code>系统属性将导致此方法将大量调试消息打印到<code> System.err </code >关于它在做什么,
     * 它在看什么。
     * </p>。
     * 
     *  <p>如果您在加载{@link DocumentBuilder}时遇到问题,请尝试：</p>
     * <pre>
     *  java -Djaxp.debug = 1 YourProgram ....
     * </pre>
     * 
     * 
     * @return New instance of a <code>DocumentBuilderFactory</code>
     *
     * @throws FactoryConfigurationError in case of {@linkplain
     * java.util.ServiceConfigurationError service configuration error} or if
     * the implementation is not available or cannot be instantiated.
     */
    public static DocumentBuilderFactory newInstance() {
        return FactoryFinder.find(
                /* The default property name according to the JAXP spec */
                DocumentBuilderFactory.class, // "javax.xml.parsers.DocumentBuilderFactory"
                /* The fallback implementation class name */
                "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");
    }

    /**
     * <p>Obtain a new instance of a <code>DocumentBuilderFactory</code> from class name.
     * This function is useful when there are multiple providers in the classpath.
     * It gives more control to the application as it can specify which provider
     * should be loaded.</p>
     *
     * <p>Once an application has obtained a reference to a <code>DocumentBuilderFactory</code>
     * it can use the factory to configure and obtain parser instances.</p>
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
     *  <p>从类名获取<code> DocumentBuilderFactory </code>的新实例。当类路径中有多个提供程序时,此函数很有用。
     * 它为应用程序提供了更多的控制,因为它可以指定应该加载哪个提供程序。</p>。
     * 
     * <p>一旦应用程序获得对<code> DocumentBuilderFactory </code>的引用,它就可以使用工厂来配置和获取解析器实例。</p>
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
     * @param factoryClassName fully qualified factory class name that provides implementation of <code>javax.xml.parsers.DocumentBuilderFactory</code>.
     *
     * @param classLoader <code>ClassLoader</code> used to load the factory class. If <code>null</code>
     *                     current <code>Thread</code>'s context classLoader is used to load the factory class.
     *
     * @return New instance of a <code>DocumentBuilderFactory</code>
     *
     * @throws FactoryConfigurationError if <code>factoryClassName</code> is <code>null</code>, or
     *                                   the factory class cannot be loaded, instantiated.
     *
     * @see #newInstance()
     *
     * @since 1.6
     */
    public static DocumentBuilderFactory newInstance(String factoryClassName, ClassLoader classLoader){
            //do not fallback if given classloader can't find the class, throw exception
            return FactoryFinder.newInstance(DocumentBuilderFactory.class,
                        factoryClassName, classLoader, false);
    }

    /**
     * Creates a new instance of a {@link javax.xml.parsers.DocumentBuilder}
     * using the currently configured parameters.
     *
     * <p>
     *  使用当前配置的参数创建{@link javax.xml.parsers.DocumentBuilder}的新实例。
     * 
     * 
     * @return A new instance of a DocumentBuilder.
     *
     * @throws ParserConfigurationException if a DocumentBuilder
     *   cannot be created which satisfies the configuration requested.
     */

    public abstract DocumentBuilder newDocumentBuilder()
        throws ParserConfigurationException;


    /**
     * Specifies that the parser produced by this code will
     * provide support for XML namespaces. By default the value of this is set
     * to <code>false</code>
     *
     * <p>
     *  指定由此代码生成的解析器将为XML命名空间提供支持。默认情况下,此值设置为<code> false </code>
     * 
     * 
     * @param awareness true if the parser produced will provide support
     *                  for XML namespaces; false otherwise.
     */

    public void setNamespaceAware(boolean awareness) {
        this.namespaceAware = awareness;
    }

    /**
     * Specifies that the parser produced by this code will
     * validate documents as they are parsed. By default the value of this
     * is set to <code>false</code>.
     *
     * <p>
     * Note that "the validation" here means
     * <a href="http://www.w3.org/TR/REC-xml#proc-types">a validating
     * parser</a> as defined in the XML recommendation.
     * In other words, it essentially just controls the DTD validation.
     * (except the legacy two properties defined in JAXP 1.2.)
     * </p>
     *
     * <p>
     * To use modern schema languages such as W3C XML Schema or
     * RELAX NG instead of DTD, you can configure your parser to be
     * a non-validating parser by leaving the {@link #setValidating(boolean)}
     * method <code>false</code>, then use the {@link #setSchema(Schema)}
     * method to associate a schema to a parser.
     * </p>
     *
     * <p>
     *  指定由此代码生成的解析器将在解析文档时验证文档。默认情况下,此值设置为<code> false </code>。
     * 
     * <p>
     *  请注意,这里的"验证"是指XML建议中定义的<a href="http://www.w3.org/TR/REC-xml#proc-types">验证解析器</a>。
     * 换句话说,它本质上只是控制DTD验证。 (除了在JAXP 1.2中定义的遗留两个属性)。
     * </p>
     * 
     * <p>
     *  要使用现代模式语言(如W3C XML Schema或RELAX NG而不是DTD),可以将解析器配置为非验证解析器,方法是保留{@link #setValidating(boolean)}方法<code>
     *  false </code> ,然后使用{@link #setSchema(Schema)}方法将模式关联到解析器。
     * </p>
     * 
     * 
     * @param validating true if the parser produced will validate documents
     *                   as they are parsed; false otherwise.
     */

    public void setValidating(boolean validating) {
        this.validating = validating;
    }

    /**
     * Specifies that the parsers created by this  factory must eliminate
     * whitespace in element content (sometimes known loosely as
     * 'ignorable whitespace') when parsing XML documents (see XML Rec
     * 2.10). Note that only whitespace which is directly contained within
     * element content that has an element only content model (see XML
     * Rec 3.2.1) will be eliminated. Due to reliance on the content model
     * this setting requires the parser to be in validating mode. By default
     * the value of this is set to <code>false</code>.
     *
     * <p>
     * 指定由此工厂创建的解析器必须在解析XML文档时删除元素内容中的空格(有时称为"可忽略的空格")(请参阅XML Rec 2.10)。
     * 注意,只有直接包含在具有仅元素内容模型的元素内容(见XML Rec 3.2.1)中的空白将被消除。由于依赖于内容模型,此设置要求解析器处于验证模式。
     * 默认情况下,此值设置为<code> false </code>。
     * 
     * 
     * @param whitespace true if the parser created must eliminate whitespace
     *                   in the element content when parsing XML documents;
     *                   false otherwise.
     */

    public void setIgnoringElementContentWhitespace(boolean whitespace) {
        this.whitespace = whitespace;
    }

    /**
     * Specifies that the parser produced by this code will
     * expand entity reference nodes. By default the value of this is set to
     * <code>true</code>
     *
     * <p>
     *  指定由此代码生成的解析器将展开实体引用节点。默认情况下,此值设置为<code> true </code>
     * 
     * 
     * @param expandEntityRef true if the parser produced will expand entity
     *                        reference nodes; false otherwise.
     */

    public void setExpandEntityReferences(boolean expandEntityRef) {
        this.expandEntityRef = expandEntityRef;
    }

    /**
     * <p>Specifies that the parser produced by this code will
     * ignore comments. By default the value of this is set to <code>false
     * </code>.</p>
     *
     * <p>
     *  <p>指定由此代码生成的解析器将忽略注释。默认情况下,此值设置为<code> false </code>。</p>
     * 
     * 
     * @param ignoreComments <code>boolean</code> value to ignore comments during processing
     */

    public void setIgnoringComments(boolean ignoreComments) {
        this.ignoreComments = ignoreComments;
    }

    /**
     * Specifies that the parser produced by this code will
     * convert CDATA nodes to Text nodes and append it to the
     * adjacent (if any) text node. By default the value of this is set to
     * <code>false</code>
     *
     * <p>
     *  指定由此代码生成的解析器将CDATA节点转换为Text节点,并将其附加到相邻(如果有)文本节点。默认情况下,此值设置为<code> false </code>
     * 
     * 
     * @param coalescing  true if the parser produced will convert CDATA nodes
     *                    to Text nodes and append it to the adjacent (if any)
     *                    text node; false otherwise.
     */

    public void setCoalescing(boolean coalescing) {
        this.coalescing = coalescing;
    }

    /**
     * Indicates whether or not the factory is configured to produce
     * parsers which are namespace aware.
     *
     * <p>
     *  指示工厂是否配置为生成可识别命名空间的解析器。
     * 
     * 
     * @return  true if the factory is configured to produce parsers which
     *          are namespace aware; false otherwise.
     */

    public boolean isNamespaceAware() {
        return namespaceAware;
    }

    /**
     * Indicates whether or not the factory is configured to produce
     * parsers which validate the XML content during parse.
     *
     * <p>
     *  指示工厂是否配置为生成在解析期间验证XML内容的解析器。
     * 
     * 
     * @return  true if the factory is configured to produce parsers
     *          which validate the XML content during parse; false otherwise.
     */

    public boolean isValidating() {
        return validating;
    }

    /**
     * Indicates whether or not the factory is configured to produce
     * parsers which ignore ignorable whitespace in element content.
     *
     * <p>
     *  指示工厂是否配置为生成忽略元素内容中的可忽略空格的解析器。
     * 
     * 
     * @return  true if the factory is configured to produce parsers
     *          which ignore ignorable whitespace in element content;
     *          false otherwise.
     */

    public boolean isIgnoringElementContentWhitespace() {
        return whitespace;
    }

    /**
     * Indicates whether or not the factory is configured to produce
     * parsers which expand entity reference nodes.
     *
     * <p>
     *  指示工厂是否配置为生成扩展实体引用节点的解析器。
     * 
     * 
     * @return  true if the factory is configured to produce parsers
     *          which expand entity reference nodes; false otherwise.
     */

    public boolean isExpandEntityReferences() {
        return expandEntityRef;
    }

    /**
     * Indicates whether or not the factory is configured to produce
     * parsers which ignores comments.
     *
     * <p>
     * 指示工厂是否配置为生成忽略注释的解析器。
     * 
     * 
     * @return  true if the factory is configured to produce parsers
     *          which ignores comments; false otherwise.
     */

    public boolean isIgnoringComments() {
        return ignoreComments;
    }

    /**
     * Indicates whether or not the factory is configured to produce
     * parsers which converts CDATA nodes to Text nodes and appends it to
     * the adjacent (if any) Text node.
     *
     * <p>
     *  指示工厂是否配置为生成将CDATA节点转换为Text节点的解析器,并将其附加到相邻(如果有)Text节点。
     * 
     * 
     * @return  true if the factory is configured to produce parsers
     *          which converts CDATA nodes to Text nodes and appends it to
     *          the adjacent (if any) Text node; false otherwise.
     */

    public boolean isCoalescing() {
        return coalescing;
    }

    /**
     * Allows the user to set specific attributes on the underlying
     * implementation.
     * <p>
     * All implementations that implement JAXP 1.5 or newer are required to
     * support the {@link javax.xml.XMLConstants#ACCESS_EXTERNAL_DTD} and
     * {@link javax.xml.XMLConstants#ACCESS_EXTERNAL_SCHEMA} properties.
     * </p>
     * <ul>
     *   <li>
     *      <p>
     *      Setting the {@link javax.xml.XMLConstants#ACCESS_EXTERNAL_DTD} property
     *      restricts the access to external DTDs, external Entity References to the
     *      protocols specified by the property.
     *      If access is denied during parsing due to the restriction of this property,
     *      {@link org.xml.sax.SAXException} will be thrown by the parse methods defined by
     *      {@link javax.xml.parsers.DocumentBuilder}.
     *      </p>
     *      <p>
     *      Setting the {@link javax.xml.XMLConstants#ACCESS_EXTERNAL_SCHEMA} property
     *      restricts the access to external Schema set by the schemaLocation attribute to
     *      the protocols specified by the property.  If access is denied during parsing
     *      due to the restriction of this property, {@link org.xml.sax.SAXException}
     *      will be thrown by the parse methods defined by
     *      {@link javax.xml.parsers.DocumentBuilder}.
     *      </p>
     *   </li>
     * </ul>
     *
     * <p>
     *  允许用户在底层实现上设置特定属性。
     * <p>
     *  实现JAXP 1.5或更高版本的所有实现都需要支持{@link javax.xml.XMLConstants#ACCESS_EXTERNAL_DTD}和{@link javax.xml.XMLConstants#ACCESS_EXTERNAL_SCHEMA}
     * 属性。
     * </p>
     * <ul>
     * <li>
     * <p>
     *  设置{@link javax.xml.XMLConstants#ACCESS_EXTERNAL_DTD}属性会限制对外部DTD的访问,对属性指定的协议的外部实体引用。
     * 如果由于此属性的限制而在解析期间拒绝访问,{@link org.xml.sax.SAXException}将由{@link javax.xml.parsers.DocumentBuilder}定义的解析
     * 方法抛出。
     *  设置{@link javax.xml.XMLConstants#ACCESS_EXTERNAL_DTD}属性会限制对外部DTD的访问,对属性指定的协议的外部实体引用。
     * </p>
     * <p>
     *  设置{@link javax.xml.XMLConstants#ACCESS_EXTERNAL_SCHEMA}属性会将对schemaLocation属性设置的外部模式的访问限制为属性指定的协议。
     * 如果由于此属性的限制而在解析期间拒绝访问,{@link org.xml.sax.SAXException}将由{@link javax.xml.parsers.DocumentBuilder}定义的解析
     * 方法抛出。
     *  设置{@link javax.xml.XMLConstants#ACCESS_EXTERNAL_SCHEMA}属性会将对schemaLocation属性设置的外部模式的访问限制为属性指定的协议。
     * </p>
     * </li>
     * </ul>
     * 
     * 
     * @param name The name of the attribute.
     * @param value The value of the attribute.
     *
     * @throws IllegalArgumentException thrown if the underlying
     *   implementation doesn't recognize the attribute.
     */
    public abstract void setAttribute(String name, Object value)
                throws IllegalArgumentException;

    /**
     * Allows the user to retrieve specific attributes on the underlying
     * implementation.
     *
     * <p>
     *  允许用户检索基础实现上的特定属性。
     * 
     * 
     * @param name The name of the attribute.
     *
     * @return value The value of the attribute.
     *
     * @throws IllegalArgumentException thrown if the underlying
     *   implementation doesn't recognize the attribute.
     */
    public abstract Object getAttribute(String name)
                throws IllegalArgumentException;

    /**
     * <p>Set a feature for this <code>DocumentBuilderFactory</code> and <code>DocumentBuilder</code>s created by this factory.</p>
     *
     * <p>
     * Feature names are fully qualified {@link java.net.URI}s.
     * Implementations may define their own features.
     * A {@link ParserConfigurationException} is thrown if this <code>DocumentBuilderFactory</code> or the
     * <code>DocumentBuilder</code>s it creates cannot support the feature.
     * It is possible for a <code>DocumentBuilderFactory</code> to expose a feature value but be unable to change its state.
     * </p>
     *
     * <p>
     * All implementations are required to support the {@link javax.xml.XMLConstants#FEATURE_SECURE_PROCESSING} feature.
     * When the feature is:</p>
     * <ul>
     *   <li>
     *     <code>true</code>: the implementation will limit XML processing to conform to implementation limits.
     *     Examples include enity expansion limits and XML Schema constructs that would consume large amounts of resources.
     *     If XML processing is limited for security reasons, it will be reported via a call to the registered
     *    {@link org.xml.sax.ErrorHandler#fatalError(SAXParseException exception)}.
     *     See {@link  DocumentBuilder#setErrorHandler(org.xml.sax.ErrorHandler errorHandler)}.
     *   </li>
     *   <li>
     *     <code>false</code>: the implementation will processing XML according to the XML specifications without
     *     regard to possible implementation limits.
     *   </li>
     * </ul>
     *
     * <p>
     * <p>为此工厂创建的<code> DocumentBuilderFactory </code>和<code> DocumentBuilder </code>设置一个功能。</p>
     * 
     * <p>
     *  功能名称是完全限定的{@link java.net.URI}。实现可以定义它们自己的特征。
     * 如果<code> DocumentBuilderFactory </code>或其创建的<code> DocumentBuilder </code>无法支持该功能,则会抛出{@link ParserConfigurationException}
     * 。
     *  功能名称是完全限定的{@link java.net.URI}。实现可以定义它们自己的特征。
     * 一个<code> DocumentBuilderFactory </code>可以暴露一个特征值,但是无法改变它的状态。
     * </p>
     * 
     * <p>
     *  所有实现都需要支持{@link javax.xml.XMLConstants#FEATURE_SECURE_PROCESSING}功能。功能为：</p>
     * <ul>
     * <li>
     *  <code> true </code>：实现将限制XML处理以符合实现限制。示例包括将消耗大量资源的扩展限制和XML模式构造。
     * 如果XML处理由于安全原因而受到限制,则将通过调用注册的{@link org.xml.sax.ErrorHandler#fatalError(SAXParseException exception)}来
     * 报告。
     *  <code> true </code>：实现将限制XML处理以符合实现限制。示例包括将消耗大量资源的扩展限制和XML模式构造。
     * 请参阅{@link DocumentBuilder#setErrorHandler(org.xml.sax.ErrorHandler errorHandler)}。
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
     * @throws ParserConfigurationException if this <code>DocumentBuilderFactory</code> or the <code>DocumentBuilder</code>s
     *   it creates cannot support this feature.
     * @throws NullPointerException If the <code>name</code> parameter is null.
     */
    public abstract void setFeature(String name, boolean value)
            throws ParserConfigurationException;

    /**
     * <p>Get the state of the named feature.</p>
     *
     * <p>
     * Feature names are fully qualified {@link java.net.URI}s.
     * Implementations may define their own features.
     * An {@link ParserConfigurationException} is thrown if this <code>DocumentBuilderFactory</code> or the
     * <code>DocumentBuilder</code>s it creates cannot support the feature.
     * It is possible for an <code>DocumentBuilderFactory</code> to expose a feature value but be unable to change its state.
     * </p>
     *
     * <p>
     *  <p>获取命名要素的状态。</p>
     * 
     * <p>
     * 功能名称是完全限定的{@link java.net.URI}。实现可以定义它们自己的特征。
     * 如果<code> DocumentBuilderFactory </code>或其创建的<code> DocumentBuilder </code>无法支持该功能,则会抛出{@link ParserConfigurationException}
     * 。
     * 功能名称是完全限定的{@link java.net.URI}。实现可以定义它们自己的特征。
     * 一个<code> DocumentBuilderFactory </code>可以暴露一个特征值,但是不能改变它的状态。
     * </p>
     * 
     * 
     * @param name Feature name.
     *
     * @return State of the named feature.
     *
     * @throws ParserConfigurationException if this <code>DocumentBuilderFactory</code>
     *   or the <code>DocumentBuilder</code>s it creates cannot support this feature.
     */
    public abstract boolean getFeature(String name)
            throws ParserConfigurationException;


    /**
     * Gets the {@link Schema} object specified through
     * the {@link #setSchema(Schema schema)} method.
     *
     * <p>
     *  获取通过{@link #setSchema(Schema schema)}方法指定的{@link Schema}对象。
     * 
     * 
     * @return
     *      the {@link Schema} object that was last set through
     *      the {@link #setSchema(Schema)} method, or null
     *      if the method was not invoked since a {@link DocumentBuilderFactory}
     *      is created.
     *
     * @throws UnsupportedOperationException When implementation does not
     *   override this method.
     *
     * @since 1.5
     */
    public Schema getSchema() {
        throw new UnsupportedOperationException(
            "This parser does not support specification \""
            + this.getClass().getPackage().getSpecificationTitle()
            + "\" version \""
            + this.getClass().getPackage().getSpecificationVersion()
            + "\""
            );

    }

    /**
     * <p>Set the {@link Schema} to be used by parsers created
     * from this factory.
     *
     * <p>
     * When a {@link Schema} is non-null, a parser will use a validator
     * created from it to validate documents before it passes information
     * down to the application.
     *
     * <p>When errors are found by the validator, the parser is responsible
     * to report them to the user-specified {@link org.xml.sax.ErrorHandler}
     * (or if the error handler is not set, ignore them or throw them), just
     * like any other errors found by the parser itself.
     * In other words, if the user-specified {@link org.xml.sax.ErrorHandler}
     * is set, it must receive those errors, and if not, they must be
     * treated according to the implementation specific
     * default error handling rules.
     *
     * <p>
     * A validator may modify the outcome of a parse (for example by
     * adding default values that were missing in documents), and a parser
     * is responsible to make sure that the application will receive
     * modified DOM trees.
     *
     * <p>
     * Initialy, null is set as the {@link Schema}.
     *
     * <p>
     * This processing will take effect even if
     * the {@link #isValidating()} method returns <code>false</code>.
     *
     * <p>It is an error to use
     * the <code>http://java.sun.com/xml/jaxp/properties/schemaSource</code>
     * property and/or the <code>http://java.sun.com/xml/jaxp/properties/schemaLanguage</code>
     * property in conjunction with a {@link Schema} object.
     * Such configuration will cause a {@link ParserConfigurationException}
     * exception when the {@link #newDocumentBuilder()} is invoked.</p>
     *
     *
     * <h4>Note for implmentors</h4>
     *
     * <p>
     * A parser must be able to work with any {@link Schema}
     * implementation. However, parsers and schemas are allowed
     * to use implementation-specific custom mechanisms
     * as long as they yield the result described in the specification.
     * </p>
     *
     * <p>
     *  <p>设置要从此工厂创建的解析器使用的{@link Schema}。
     * 
     * <p>
     *  当{@link Schema}非空时,解析器将使用从其创建的验证器来验证文档,然后将信息传递到应用程序。
     * 
     *  <p>当验证器发现错误时,解析器负责将它们报告给用户指定的{@link org.xml.sax.ErrorHandler}(或者如果未设置错误处理程序,忽略它们或抛出它们) ,就像解析器本身发现的任何
     * 其他错误一样。
     * 换句话说,如果设置了用户指定的{@link org.xml.sax.ErrorHandler},它必须接收这些错误,如果没有,则必须根据实现特定的默认错误处理规则来对待它们。
     * 
     * <p>
     *  验证器可以修改解析的结果(例如通过添加在文档中缺失的默认值),并且解析器负责确保应用程序将接收修改的DOM树。
     * 
     * <p>
     *  Initialy,null设置为{@link Schema}。
     * 
     * <p>
     * 即使{@link #isValidating()}方法返回<code> false </code>,此处理也会生效。
     * 
     *  <p>使用<code> http://java.sun.com/xml/jaxp/properties/schemaSource </code>属性和/或<code> http：//java.sun是
     * 一个错误。
     *  com / xml / jaxp / properties / schemaLanguage </code>属性与{@link Schema}对象结合使用。
     * 
     * @param schema <code>Schema</code> to use or <code>null</code>
     *   to remove a schema.
     *
     * @throws UnsupportedOperationException When implementation does not
     *   override this method.
     *
     * @since 1.5
     */
    public void setSchema(Schema schema) {
        throw new UnsupportedOperationException(
            "This parser does not support specification \""
            + this.getClass().getPackage().getSpecificationTitle()
            + "\" version \""
            + this.getClass().getPackage().getSpecificationVersion()
            + "\""
            );
    }



    /**
     * <p>Set state of XInclude processing.</p>
     *
     * <p>If XInclude markup is found in the document instance, should it be
     * processed as specified in <a href="http://www.w3.org/TR/xinclude/">
     * XML Inclusions (XInclude) Version 1.0</a>.</p>
     *
     * <p>XInclude processing defaults to <code>false</code>.</p>
     *
     * <p>
     * 当调用{@link #newDocumentBuilder()}时,此类配置将导致{@link ParserConfigurationException}异常。</p>。
     * 
     *  <h4>注意事项注意事项</h4>
     * 
     * <p>
     *  解析器必须能够使用任何{@link Schema}实现。然而,解析器和模式允许使用实现特定的自定义机制,只要它们产生规范中描述的结果。
     * </p>
     * 
     * 
     * @param state Set XInclude processing to <code>true</code> or
     *   <code>false</code>
     *
     * @throws UnsupportedOperationException When implementation does not
     *   override this method.
     *
     * @since 1.5
     */
    public void setXIncludeAware(final boolean state) {
        if (state) {
            throw new UnsupportedOperationException(" setXIncludeAware " +
                "is not supported on this JAXP" +
                " implementation or earlier: " + this.getClass());
        }
    }

    /**
     * <p>Get state of XInclude processing.</p>
     *
     * <p>
     *  <p>设置XInclude处理的状态。</p>
     * 
     *  <p>如果在文档实例中找到XInclude标记,则应按<a href="http://www.w3.org/TR/xinclude/"> XML Inclusions(XInclude)版本1.0中的
     * 指定处理。
     *  a>。</p>。
     * 
     * 
     * @return current state of XInclude processing
     *
     * @throws UnsupportedOperationException When implementation does not
     *   override this method.
     *
     * @since 1.5
     */
    public boolean isXIncludeAware() {
        throw new UnsupportedOperationException(
            "This parser does not support specification \""
            + this.getClass().getPackage().getSpecificationTitle()
            + "\" version \""
            + this.getClass().getPackage().getSpecificationVersion()
            + "\""
            );
    }
}
