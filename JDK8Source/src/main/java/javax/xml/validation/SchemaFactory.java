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

package javax.xml.validation;

import java.io.File;
import java.net.URL;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXParseException;

/**
 * Factory that creates {@link Schema} objects&#x2E; Entry-point to
 * the validation API.
 *
 * <p>
 * {@link SchemaFactory} is a schema compiler. It reads external
 * representations of schemas and prepares them for validation.
 *
 * <p>
 * The {@link SchemaFactory} class is not thread-safe. In other words,
 * it is the application's responsibility to ensure that at most
 * one thread is using a {@link SchemaFactory} object at any
 * given moment. Implementations are encouraged to mark methods
 * as <code>synchronized</code> to protect themselves from broken clients.
 *
 * <p>
 * {@link SchemaFactory} is not re-entrant. While one of the
 * <code>newSchema</code> methods is being invoked, applications
 * may not attempt to recursively invoke the <code>newSchema</code> method,
 * even from the same thread.
 *
 * <h2><a name="schemaLanguage"></a>Schema Language</h2>
 * <p>
 * This spec uses a namespace URI to designate a schema language.
 * The following table shows the values defined by this specification.
 * <p>
 * To be compliant with the spec, the implementation
 * is only required to support W3C XML Schema 1.0. However,
 * if it chooses to support other schema languages listed here,
 * it must conform to the relevant behaviors described in this spec.
 *
 * <p>
 * Schema languages not listed here are expected to
 * introduce their own URIs to represent themselves.
 * The {@link SchemaFactory} class is capable of locating other
 * implementations for other schema languages at run-time.
 *
 * <p>
 * Note that because the XML DTD is strongly tied to the parsing process
 * and has a significant effect on the parsing process, it is impossible
 * to define the DTD validation as a process independent from parsing.
 * For this reason, this specification does not define the semantics for
 * the XML DTD. This doesn't prohibit implementors from implementing it
 * in a way they see fit, but <em>users are warned that any DTD
 * validation implemented on this interface necessarily deviate from
 * the XML DTD semantics as defined in the XML 1.0</em>.
 *
 * <table border="1" cellpadding="2">
 *   <thead>
 *     <tr>
 *       <th>value</th>
 *       <th>language</th>
 *     </tr>
 *   </thead>
 *   <tbody>
 *     <tr>
 *       <td>{@link javax.xml.XMLConstants#W3C_XML_SCHEMA_NS_URI} ("<code>http://www.w3.org/2001/XMLSchema</code>")</td>
 *       <td><a href="http://www.w3.org/TR/xmlschema-1">W3C XML Schema 1.0</a></td>
 *     </tr>
 *     <tr>
 *       <td>{@link javax.xml.XMLConstants#RELAXNG_NS_URI} ("<code>http://relaxng.org/ns/structure/1.0</code>")</td>
 *       <td><a href="http://www.relaxng.org/">RELAX NG 1.0</a></td>
 *     </tr>
 *   </tbody>
 * </table>
 *
 * <p>
 *  创建{@link Schema}对象的工厂&#x2e;入口点到验证API。
 * 
 * <p>
 *  {@link SchemaFactory}是一个模式编译器。它读取模式的外部表示,并准备它们以进行验证。
 * 
 * <p>
 *  {@link SchemaFactory}类不是线程安全的。换句话说,应用程序有责任确保在任何给定时刻最多只有一个线程使用{@link SchemaFactory}对象。
 * 鼓励实现将方法标记为<code> synchronized </code>以保护自己免受损坏的客户端。
 * 
 * <p>
 *  {@link SchemaFactory}不是重入。
 * 虽然正在调用<code> newSchema </code>方法之一,但应用程序可能不会尝试递归调用<code> newSchema </code>方法,即使是从同一个线程。
 * 
 *  <h2> <a name="schemaLanguage"> </a>架构语言</h2>
 * <p>
 *  此规范使用名称空间URI来指定模式语言。下表显示了本规范定义的值。
 * <p>
 *  为了符合规范,实现只需要支持W3C XML Schema 1.0。但是,如果它选择支持此处列出的其他模式语言,它必须符合本规范中描述的相关行为。
 * 
 * <p>
 * 这里没有列出的模式语言应该引入自己的URI来表示自己。 {@link SchemaFactory}类能够在运行时为其他模式语言定位其他实现。
 * 
 * <p>
 *  注意,因为XML DTD与解析过程密切相关,并且对解析过程有重大影响,所以不可能将DTD验证定义为独立于解析的过程。因此,本规范没有定义XML DTD的语义。
 * 这不禁止实现者以它们认为合适的方式实现它,但是用户被警告,在该接口上实现的任何DTD验证必然偏离XML 1.0中定义的XML DTD语义。
 * 
 * <table border="1" cellpadding="2">
 * <thead>
 * <tr>
 *  <th>值</th> <th>语言</th>
 * </tr>
 * </thead>
 * <tbody>
 * <tr>
 *  <td> {@ link javax.xml.XMLConstants#W3C_XML_SCHEMA_NS_URI}("<code> http://www.w3.org/2001/XMLSchema 
 * </code>")</td> <td> <a href =" http://www.w3.org/TR/xmlschema-1">W3C XML Schema 1.0 </a> </td>。
 * </tr>
 * <tr>
 *  <td> {@ link javax.xml.XMLConstants#RELAXNG_NS_URI}("<code> http://relaxng.org/ns/structure/1.0 </code>
 * ")</td> <td> <a href =" http://www.relaxng.org/"> RELAX NG 1.0 </a> </td>。
 * </tr>
 * </tbody>
 * </table>
 * 
 * 
 * @author  <a href="mailto:Kohsuke.Kawaguchi@Sun.com">Kohsuke Kawaguchi</a>
 * @author  <a href="mailto:Neeraj.Bajaj@sun.com">Neeraj Bajaj</a>
 *
 * @since 1.5
 */
public abstract class SchemaFactory {

     private static SecuritySupport ss = new SecuritySupport();

    /**
     * <p>Constructor for derived classes.</p>
     *
     * <p>The constructor does nothing.</p>
     *
     * <p>Derived classes must create {@link SchemaFactory} objects that have
     * <code>null</code> {@link ErrorHandler} and
     * <code>null</code> {@link LSResourceResolver}.</p>
     * <p>
     *  <p>派生类的构造方法。</p>
     * 
     *  <p>构造函数不执行任何操作。</p>
     * 
     *  <p>派生类必须创建具有<code> null </code> {@link ErrorHandler}和<code> null </code> {@link LSResourceResolver}的
     * {@link SchemaFactory}对象。
     * </p>。
     * 
     */
    protected SchemaFactory() {
    }

    /**
     * <p>Lookup an implementation of the <code>SchemaFactory</code> that supports the specified
     * schema language and return it.</p>
     *
     * <p>To find a <code>SchemaFactory</code> object for a given schema language,
     * this method looks the following places in the following order
     * where "the class loader" refers to the context class loader:</p>
     * <ol>
     *  <li>
     *     If the system property
     *     <code>"javax.xml.validation.SchemaFactory:<i>schemaLanguage</i>"</code>
     *     is present (where <i>schemaLanguage</i> is the parameter
     *     to this method), then its value is read
     *     as a class name. The method will try to
     *     create a new instance of this class by using the class loader,
     *     and returns it if it is successfully created.
     *   </li>
     *   <li>
     *     <code>$java.home/lib/jaxp.properties</code> is read and
     *     the value associated with the key being the system property above
     *     is looked for. If present, the value is processed just like above.
     *   </li>
     *   <li>
     *   Use the service-provider loading facilities, defined by the
     *   {@link java.util.ServiceLoader} class, to attempt to locate and load an
     *   implementation of the service using the {@linkplain
     *   java.util.ServiceLoader#load(java.lang.Class) default loading mechanism}:
     *   the service-provider loading facility will use the {@linkplain
     *   java.lang.Thread#getContextClassLoader() current thread's context class loader}
     *   to attempt to load the service. If the context class
     *   loader is null, the {@linkplain
     *   ClassLoader#getSystemClassLoader() system class loader} will be used.
     *   <br>
     *   Each potential service provider is required to implement the method
     *        {@link #isSchemaLanguageSupported(String schemaLanguage)}.
     *   <br>
     *   The first service provider found that supports the specified schema
     *   language is returned.
     *   <br>
     *   In case of {@link java.util.ServiceConfigurationError} a
     *   {@link SchemaFactoryConfigurationError} will be thrown.
     *   </li>
     *   <li>
     *     Platform default <code>SchemaFactory</code> is located
     *     in a implementation specific way. There must be a platform default
     *     <code>SchemaFactory</code> for W3C XML Schema.
     *   </li>
     * </ol>
     *
     * <p>If everything fails, {@link IllegalArgumentException} will be thrown.</p>
     *
     * <p><strong>Tip for Trouble-shooting:</strong></p>
     * <p>See {@link java.util.Properties#load(java.io.InputStream)} for
     * exactly how a property file is parsed. In particular, colons ':'
     * need to be escaped in a property file, so make sure schema language
     * URIs are properly escaped in it. For example:</p>
     * <pre>
     * http\://www.w3.org/2001/XMLSchema=org.acme.foo.XSSchemaFactory
     * </pre>
     *
     * <p>
     * <p>查找支持指定模式语言并返回的<code> SchemaFactory </code>的实现</p>
     * 
     *  <p>要为给定模式语言找到<code> SchemaFactory </code>对象,此方法按以下顺序查找以下位置,其中"类加载器"引用上下文类加载器：</p>
     * <ol>
     * <li>
     *  如果系统属性<code>"javax.xml.validation.SchemaFactory：<i> schemaLanguage </i>"</code>存在(其中<i> schemaLangua
     * ge </i>是此方法的参数)它的值被读取为类名。
     * 该方法将尝试使用类加载器创建此类的新实例,并返回它,如果它成功创建。
     * </li>
     * <li>
     *  读取<code> $ java.home / lib / jaxp.properties </code>,并查找与作为上述系统属性的键相关联的值。如果存在,则像上面一样处理该值。
     * </li>
     * <li>
     *  使用由{@link java.util.ServiceLoader}类定义的服务提供程序加载工具,尝试使用{@linkplain java.util.ServiceLoader#load(java.lang。
     * )来定位和加载服务的实现。
     * 类)默认加载机制}：service-provider加载工具将使用{@linkplain java.lang.Thread#getContextClassLoader()当前线程的上下文类加载器}来尝试
     * 加载服务。
     * )来定位和加载服务的实现。如果上下文类加载器为null,将使用{@linkplain ClassLoader#getSystemClassLoader()系统类加载器}。
     * <br>
     * 每个潜在服务提供商都需要实施{@link #isSchemaLanguageSupported(String schemaLanguage)}方法。
     * <br>
     *  发现支持指定模式语言的第一个服务提供程序被返回。
     * <br>
     *  在{@link java.util.ServiceConfigurationError}的情况下,将抛出{@link SchemaFactoryConfigurationError}。
     * </li>
     * <li>
     *  平台默认<code> SchemaFactory </code>位于实现特定的方式。对于W3C XML Schema,必须有一个平台默认<code> SchemaFactory </code>。
     * </li>
     * </ol>
     * 
     *  <p>如果一切都失败,{@link IllegalArgumentException}将被抛出。</p>
     * 
     *  <p> <strong>疑难解答提示：</strong> </p> <p>请参阅{@link java.util.Properties#load(java.io.InputStream)}了解如何解析
     * 属性文件。
     * 特别是,冒号'：'需要在属性文件中转义,因此请确保模式语言URI在其中正确转义。例如：</p>。
     * <pre>
     *  http \：//www.w3.org/2001/XMLSchema=org.acme.foo.XSSchemaFactory
     * </pre>
     * 
     * 
     * @param schemaLanguage
     *      Specifies the schema language which the returned
     *      SchemaFactory will understand. See
     *      <a href="#schemaLanguage">the list of available
     *      schema languages</a> for the possible values.
     *
     * @return New instance of a <code>SchemaFactory</code>
     *
     * @throws IllegalArgumentException
     *      If no implementation of the schema language is available.
     * @throws NullPointerException
     *      If the <code>schemaLanguage</code> parameter is null.
     * @throws SchemaFactoryConfigurationError
     *      If a configuration error is encountered.
     *
     * @see #newInstance(String schemaLanguage, String factoryClassName, ClassLoader classLoader)
     */
    public static SchemaFactory newInstance(String schemaLanguage) {
        ClassLoader cl;
        cl = ss.getContextClassLoader();

        if (cl == null) {
            //cl = ClassLoader.getSystemClassLoader();
            //use the current class loader
            cl = SchemaFactory.class.getClassLoader();
        }

        SchemaFactory f = new SchemaFactoryFinder(cl).newFactory(schemaLanguage);
        if (f == null) {
            throw new IllegalArgumentException(
                    "No SchemaFactory"
                    + " that implements the schema language specified by: " + schemaLanguage
                    + " could be loaded");
        }
        return f;
    }

    /**
     * <p>Obtain a new instance of a <code>SchemaFactory</code> from class name. <code>SchemaFactory</code>
     * is returned if specified factory class name supports the specified schema language.
     * This function is useful when there are multiple providers in the classpath.
     * It gives more control to the application as it can specify which provider
     * should be loaded.</p>
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
     *  <p>从类名获取<code> SchemaFactory </code>的新实例。如果指定的工厂类名支持指定的模式语言,则返回<code> SchemaFactory </code>。
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
     * @param schemaLanguage Specifies the schema language which the returned
     *                          <code>SchemaFactory</code> will understand. See
     *                          <a href="#schemaLanguage">the list of available
     *                          schema languages</a> for the possible values.
     *
     * @param factoryClassName fully qualified factory class name that provides implementation of <code>javax.xml.validation.SchemaFactory</code>.
     *
     * @param classLoader <code>ClassLoader</code> used to load the factory class. If <code>null</code>
     *                     current <code>Thread</code>'s context classLoader is used to load the factory class.
     *
     * @return New instance of a <code>SchemaFactory</code>
     *
     * @throws IllegalArgumentException
     *                   if <code>factoryClassName</code> is <code>null</code>, or
     *                   the factory class cannot be loaded, instantiated or doesn't
     *                   support the schema language specified in <code>schemLanguage</code>
     *                   parameter.
     *
     * @throws NullPointerException
     *      If the <code>schemaLanguage</code> parameter is null.
     *
     * @see #newInstance(String schemaLanguage)
     *
     * @since 1.6
     */
    public static SchemaFactory newInstance(String schemaLanguage, String factoryClassName, ClassLoader classLoader){
        ClassLoader cl = classLoader;

        if (cl == null) {
            cl = ss.getContextClassLoader();
        }

        SchemaFactory f = new SchemaFactoryFinder(cl).createInstance(factoryClassName);
        if (f == null) {
            throw new IllegalArgumentException(
                    "Factory " + factoryClassName
                    + " could not be loaded to implement the schema language specified by: " + schemaLanguage);
        }
        //if this factory supports the given schemalanguage return this factory else thrown exception
        if(f.isSchemaLanguageSupported(schemaLanguage)){
            return f;
        }else{
            throw new IllegalArgumentException(
                    "Factory " + f.getClass().getName()
                    + " does not implement the schema language specified by: " + schemaLanguage);
        }

    }

    /**
     * <p>Is specified schema supported by this <code>SchemaFactory</code>?</p>
     *
     * <p>
     *  <p>此<code> SchemaFactory </code> </p>支持指定的模式
     * 
     * 
     * @param schemaLanguage Specifies the schema language which the returned <code>SchemaFactory</code> will understand.
     *    <code>schemaLanguage</code> must specify a <a href="#schemaLanguage">valid</a> schema language.
     *
     * @return <code>true</code> if <code>SchemaFactory</code> supports <code>schemaLanguage</code>, else <code>false</code>.
     *
     * @throws NullPointerException If <code>schemaLanguage</code> is <code>null</code>.
     * @throws IllegalArgumentException If <code>schemaLanguage.length() == 0</code>
     *   or <code>schemaLanguage</code> does not specify a <a href="#schemaLanguage">valid</a> schema language.
     */
    public abstract boolean isSchemaLanguageSupported(String schemaLanguage);

    /**
     * Look up the value of a feature flag.
     *
     * <p>The feature name is any fully-qualified URI.  It is
     * possible for a {@link SchemaFactory} to recognize a feature name but
     * temporarily be unable to return its value.
     *
     * <p>Implementors are free (and encouraged) to invent their own features,
     * using names built on their own URIs.</p>
     *
     * <p>
     *  查找特征标志的值。
     * 
     *  <p>地图项名称是任何完全限定的URI。 {@link SchemaFactory}可能识别功能名称,但暂时无法返回其值。
     * 
     *  <p>实施者可以自由地(并鼓励)使用自己的URI创建自己的特征。</p>
     * 
     * 
     * @param name The feature name, which is a non-null fully-qualified URI.
     *
     * @return The current value of the feature (true or false).
     *
     * @throws SAXNotRecognizedException If the feature
     *   value can't be assigned or retrieved.
     * @throws SAXNotSupportedException When the
     *   {@link SchemaFactory} recognizes the feature name but
     *   cannot determine its value at this time.
     * @throws NullPointerException If <code>name</code> is <code>null</code>.
     *
     * @see #setFeature(String, boolean)
     */
    public boolean getFeature(String name)
        throws SAXNotRecognizedException, SAXNotSupportedException {

        if (name == null) {
                throw new NullPointerException("the name parameter is null");
        }
        throw new SAXNotRecognizedException(name);
    }

    /**
     * <p>Set a feature for this <code>SchemaFactory</code>,
     * {@link Schema}s created by this factory, and by extension,
     * {@link Validator}s and {@link ValidatorHandler}s created by
     * those {@link Schema}s.
     * </p>
     *
     * <p>Implementors and developers should pay particular attention
     * to how the special {@link Schema} object returned by {@link
     * #newSchema()} is processed. In some cases, for example, when the
     * <code>SchemaFactory</code> and the class actually loading the
     * schema come from different implementations, it may not be possible
     * for <code>SchemaFactory</code> features to be inherited automatically.
     * Developers should
     * make sure that features, such as secure processing, are explicitly
     * set in both places.</p>
     *
     * <p>The feature name is any fully-qualified URI. It is
     * possible for a {@link SchemaFactory} to expose a feature value but
     * to be unable to change the current value.</p>
     *
     * <p>All implementations are required to support the {@link javax.xml.XMLConstants#FEATURE_SECURE_PROCESSING} feature.
     * When the feature is:</p>
     * <ul>
     *   <li>
     *     <code>true</code>: the implementation will limit XML processing to conform to implementation limits.
     *     Examples include enity expansion limits and XML Schema constructs that would consume large amounts of resources.
     *     If XML processing is limited for security reasons, it will be reported via a call to the registered
     *    {@link ErrorHandler#fatalError(SAXParseException exception)}.
     *     See {@link #setErrorHandler(ErrorHandler errorHandler)}.
     *   </li>
     *   <li>
     *     <code>false</code>: the implementation will processing XML according to the XML specifications without
     *     regard to possible implementation limits.
     *   </li>
     * </ul>
     *
     * <p>
     *  <p>为此工厂创建的<code> SchemaFactory </code>,{@link Schema}设置一个功能,以及由{@link ValidatorHandler}创建的{@link Validator}
     * 和{@link ValidatorHandler}链接模式。
     * </p>
     * 
     *  <p>实施者和开发人员应特别注意如何处理{@link #newSchema()}返回的{@link Schema}对象。
     * 在某些情况下,例如,当<code> SchemaFactory </code>和实际加载模式的类来自不同的实现时,可能不会自动继承<code> SchemaFactory </code>特性。
     * 开发人员应确保在两个地方显式设置安全处理等功能。</p>。
     * 
     * <p>地图项名称是任何完全限定的URI。 {@link SchemaFactory}可能会暴露一个特征值,但无法更改当前值。</p>
     * 
     *  <p>所有实现都需要支持{@link javax.xml.XMLConstants#FEATURE_SECURE_PROCESSING}功能。功能为：</p>
     * <ul>
     * <li>
     *  <code> true </code>：实现将限制XML处理以符合实现限制。示例包括将消耗大量资源的扩展限制和XML模式构造。
     * 如果XML处理由于安全原因而受到限制,则将通过调用注册的{@link ErrorHandler#fatalError(SAXParseException exception)}来报告。
     * 请参阅{@link #setErrorHandler(ErrorHandler errorHandler)}。
     * </li>
     * <li>
     *  <code> false </code>：实现将根据XML规范处理XML,而不考虑可能的实现限制。
     * </li>
     * </ul>
     * 
     * 
     * @param name The feature name, which is a non-null fully-qualified URI.
     * @param value The requested value of the feature (true or false).
     *
     * @throws SAXNotRecognizedException If the feature
     *   value can't be assigned or retrieved.
     * @throws SAXNotSupportedException When the
     *   {@link SchemaFactory} recognizes the feature name but
     *   cannot set the requested value.
     * @throws NullPointerException If <code>name</code> is <code>null</code>.
     *
     * @see #getFeature(String)
     */
    public void setFeature(String name, boolean value)
        throws SAXNotRecognizedException, SAXNotSupportedException {

        if (name == null) {
                throw new NullPointerException("the name parameter is null");
        }
        throw new SAXNotRecognizedException(name);
    }

    /**
     * Set the value of a property.
     *
     * <p>The property name is any fully-qualified URI.  It is
     * possible for a {@link SchemaFactory} to recognize a property name but
     * to be unable to change the current value.</p>
     *
     * <p>
     * All implementations that implement JAXP 1.5 or newer are required to
     * support the {@link javax.xml.XMLConstants#ACCESS_EXTERNAL_DTD} and
     * {@link javax.xml.XMLConstants#ACCESS_EXTERNAL_SCHEMA} properties.
     * </p>
     * <ul>
     *   <li>
     *      <p>Access to external DTDs in Schema files is restricted to the protocols
     *      specified by the {@link javax.xml.XMLConstants#ACCESS_EXTERNAL_DTD} property.
     *      If access is denied during the creation of new Schema due to the restriction
     *      of this property, {@link org.xml.sax.SAXException} will be thrown by the
     *      {@link #newSchema(Source)} or {@link #newSchema(File)}
     *      or {@link #newSchema(URL)} or  or {@link #newSchema(Source[])} method.</p>
     *
     *      <p>Access to external DTDs in xml source files is restricted to the protocols
     *      specified by the {@link javax.xml.XMLConstants#ACCESS_EXTERNAL_DTD} property.
     *      If access is denied during validation due to the restriction
     *      of this property, {@link org.xml.sax.SAXException} will be thrown by the
     *      {@link javax.xml.validation.Validator#validate(Source)} or
     *      {@link javax.xml.validation.Validator#validate(Source, Result)} method.</p>
     *
     *      <p>Access to external reference set by the schemaLocation attribute is
     *      restricted to the protocols specified by the
     *      {@link javax.xml.XMLConstants#ACCESS_EXTERNAL_SCHEMA} property.
     *      If access is denied during validation due to the restriction of this property,
     *      {@link org.xml.sax.SAXException} will be thrown by the
     *      {@link javax.xml.validation.Validator#validate(Source)} or
     *      {@link javax.xml.validation.Validator#validate(Source, Result)} method.</p>
     *
     *      <p>Access to external reference set by the Import
     *      and Include element is restricted to the protocols specified by the
     *      {@link javax.xml.XMLConstants#ACCESS_EXTERNAL_SCHEMA} property.
     *      If access is denied during the creation of new Schema due to the restriction
     *      of this property, {@link org.xml.sax.SAXException} will be thrown by the
     *      {@link #newSchema(Source)} or {@link #newSchema(File)}
     *      or {@link #newSchema(URL)} or {@link #newSchema(Source[])} method.</p>
     *   </li>
     * </ul>
     *
     * <p>
     *  设置属性的值。
     * 
     *  <p>属性名称是任何完全限定的URI。 {@link SchemaFactory}可以识别属性名称,但无法更改当前值。</p>
     * 
     * <p>
     *  实现JAXP 1.5或更高版本的所有实现都需要支持{@link javax.xml.XMLConstants#ACCESS_EXTERNAL_DTD}和{@link javax.xml.XMLConstants#ACCESS_EXTERNAL_SCHEMA}
     * 属性。
     * </p>
     * <ul>
     * <li>
     * <p>在模式文件中访问外部DTD仅限于{@link javax.xml.XMLConstants#ACCESS_EXTERNAL_DTD}属性指定的协议。
     * 如果由于此属性的限制,在创建新模式期间访问被拒绝,{@link org.xml.sax.SAXException}将由{@link #newSchema(Source)}或{@link #newSchema文件)}
     * 或{@link #newSchema(URL)}或或{@link #newSchema(Source [])}方法。
     * <p>在模式文件中访问外部DTD仅限于{@link javax.xml.XMLConstants#ACCESS_EXTERNAL_DTD}属性指定的协议。</p>。
     * 
     *  <p>在xml源文件中访问外部DTD仅限于{@link javax.xml.XMLConstants#ACCESS_EXTERNAL_DTD}属性指定的协议。
     * 如果由于此属性的限制而在验证期间拒绝访问,{@link org.xml.sax.SAXException}将由{@link javax.xml.validation.Validator#validate(Source)}
     * 或{@link javax.xml.validation.Validator#validate(Source,Result)}方法。
     *  <p>在xml源文件中访问外部DTD仅限于{@link javax.xml.XMLConstants#ACCESS_EXTERNAL_DTD}属性指定的协议。</p>。
     * 
     *  <p>由schemaLocation属性设置的对外部引用的访问仅限于{@link javax.xml.XMLConstants#ACCESS_EXTERNAL_SCHEMA}属性指定的协议。
     * 如果由于此属性的限制而在验证期间拒绝访问,{@link org.xml.sax.SAXException}将由{@link javax.xml.validation.Validator#validate(Source)}
     * 或{@link javax.xml.validation.Validator#validate(Source,Result)}方法。
     *  <p>由schemaLocation属性设置的对外部引用的访问仅限于{@link javax.xml.XMLConstants#ACCESS_EXTERNAL_SCHEMA}属性指定的协议。
     * </p>。
     * 
     * <p>由Import和Include元素设置的对外部引用的访问仅限于{@link javax.xml.XMLConstants#ACCESS_EXTERNAL_SCHEMA}属性指定的协议。
     * 如果由于此属性的限制,在创建新模式期间访问被拒绝,则{@link org.xml.sax.SAXException}将由{@link #newSchema(Source)}或{@link #newSchema文件)}
     * 或{@link #newSchema(URL)}或{@link #newSchema(Source [])}方法。
     * <p>由Import和Include元素设置的对外部引用的访问仅限于{@link javax.xml.XMLConstants#ACCESS_EXTERNAL_SCHEMA}属性指定的协议。</p>。
     * </li>
     * </ul>
     * 
     * 
     * @param name The property name, which is a non-null fully-qualified URI.
     * @param object The requested value for the property.
     *
     * @throws SAXNotRecognizedException If the property
     *   value can't be assigned or retrieved.
     * @throws SAXNotSupportedException When the
     *   {@link SchemaFactory} recognizes the property name but
     *   cannot set the requested value.
     * @throws NullPointerException If <code>name</code> is <code>null</code>.
     */
    public void setProperty(String name, Object object)
        throws SAXNotRecognizedException, SAXNotSupportedException {

        if (name == null) {
                throw new NullPointerException("the name parameter is null");
        }
        throw new SAXNotRecognizedException(name);
    }

    /**
     * Look up the value of a property.
     *
     * <p>The property name is any fully-qualified URI.  It is
     * possible for a {@link SchemaFactory} to recognize a property name but
     * temporarily be unable to return its value.</p>
     *
     * <p>{@link SchemaFactory}s are not required to recognize any specific
     * property names.</p>
     *
     * <p>Implementors are free (and encouraged) to invent their own properties,
     * using names built on their own URIs.</p>
     *
     * <p>
     *  查找属性的值。
     * 
     *  <p>属性名称是任何完全限定的URI。 {@link SchemaFactory}可能识别属性名称,但暂时无法返回其值。</p>
     * 
     *  <p> {@ link SchemaFactory}不需要识别任何特定的属性名称。</p>
     * 
     *  <p>实施者可以自由(鼓励)使用自己的URI创建自己的属性。</p>
     * 
     * 
     * @param name The property name, which is a non-null fully-qualified URI.
     *
     * @return The current value of the property.
     *
     * @throws SAXNotRecognizedException If the property
     *   value can't be assigned or retrieved.
     * @throws SAXNotSupportedException When the
     *   XMLReader recognizes the property name but
     *   cannot determine its value at this time.
     * @throws NullPointerException If <code>name</code> is <code>null</code>.
     *
     * @see #setProperty(String, Object)
     */
    public Object getProperty(String name)
        throws SAXNotRecognizedException, SAXNotSupportedException {

        if (name == null) {
                throw new NullPointerException("the name parameter is null");
        }
        throw new SAXNotRecognizedException(name);
    }

    /**
     * Sets the {@link ErrorHandler} to receive errors encountered
     * during the <code>newSchema</code> method invocation.
     *
     * <p>
     * Error handler can be used to customize the error handling process
     * during schema parsing. When an {@link ErrorHandler} is set,
     * errors found during the parsing of schemas will be first sent
     * to the {@link ErrorHandler}.
     *
     * <p>
     * The error handler can abort the parsing of a schema immediately
     * by throwing {@link SAXException} from the handler. Or for example
     * it can print an error to the screen and try to continue the
     * processing by returning normally from the {@link ErrorHandler}
     *
     * <p>
     * If any {@link Throwable} (or instances of its derived classes)
     * is thrown from an {@link ErrorHandler},
     * the caller of the <code>newSchema</code> method will be thrown
     * the same {@link Throwable} object.
     *
     * <p>
     * {@link SchemaFactory} is not allowed to
     * throw {@link SAXException} without first reporting it to
     * {@link ErrorHandler}.
     *
     * <p>
     * Applications can call this method even during a {@link Schema}
     * is being parsed.
     *
     * <p>
     * When the {@link ErrorHandler} is null, the implementation will
     * behave as if the following {@link ErrorHandler} is set:
     * <pre>
     * class DraconianErrorHandler implements {@link ErrorHandler} {
     *     public void fatalError( {@link org.xml.sax.SAXParseException} e ) throws {@link SAXException} {
     *         throw e;
     *     }
     *     public void error( {@link org.xml.sax.SAXParseException} e ) throws {@link SAXException} {
     *         throw e;
     *     }
     *     public void warning( {@link org.xml.sax.SAXParseException} e ) throws {@link SAXException} {
     *         // noop
     *     }
     * }
     * </pre>
     *
     * <p>
     * When a new {@link SchemaFactory} object is created, initially
     * this field is set to null. This field will <em>NOT</em> be
     * inherited to {@link Schema}s, {@link Validator}s, or
     * {@link ValidatorHandler}s that are created from this {@link SchemaFactory}.
     *
     * <p>
     *  设置{@link ErrorHandler}以接收在<code> newSchema </code>方法调用期间遇到的错误。
     * 
     * <p>
     *  错误处理程序可用于在模式解析期间自定义错误处理过程。当设置{@link ErrorHandler}时,在解析模式期间发现的错误将首先发送到{@link ErrorHandler}。
     * 
     * <p>
     *  错误处理程序可以通过从处理程序抛出{@link SAXException}来立即中止模式的解析。或者例如它可以打印一个错误到屏幕,并尝试继续处理通常从{@link ErrorHandler}
     * 
     * <p>
     * 如果从{@link ErrorHandler}抛出任何{@link Throwable}(或其派生类的实例),则<code> newSchema </code>方法的调用者将抛出相同的{@link Throwable}
     * 对象。
     * 
     * <p>
     *  {@link SchemaFactory}不允许抛出{@link SAXException},而不先将其报告给{@link ErrorHandler}。
     * 
     * <p>
     *  即使在{@link Schema}正在解析中,应用程式仍可呼叫此方法。
     * 
     * <p>
     *  当{@link ErrorHandler}为null时,实现将如同设置以下{@link ErrorHandler}：
     * <pre>
     *  类DraconianErrorHandler实现{@link ErrorHandler} {public void fatalError({@link org.xml.sax.SAXParseException}
     *  e)throws {@link SAXException} {throw e; } public void error({@link org.xml.sax.SAXParseException} e)
     * throws {@link SAXException} {throw e; } public void warning({@link org.xml.sax.SAXParseException} e)t
     * hrows {@link SAXException} {// noop}}。
     * </pre>
     * 
     * <p>
     *  创建新的{@link SchemaFactory}对象时,最初此字段设置为null。
     * 此字段将<em>不会</em>继承到从此{@link SchemaFactory}创建的{@link Schema},{@link Validator}或{@link ValidatorHandler}
     * 。
     *  创建新的{@link SchemaFactory}对象时,最初此字段设置为null。
     * 
     * 
     * @param errorHandler A new error handler to be set.
     *   This parameter can be <code>null</code>.
     */
    public abstract void setErrorHandler(ErrorHandler errorHandler);

    /**
     * Gets the current {@link ErrorHandler} set to this {@link SchemaFactory}.
     *
     * <p>
     *  获取设置为此{@link SchemaFactory}的当前{@link ErrorHandler}。
     * 
     * 
     * @return
     *      This method returns the object that was last set through
     *      the {@link #setErrorHandler(ErrorHandler)} method, or null
     *      if that method has never been called since this {@link SchemaFactory}
     *      has created.
     *
     * @see #setErrorHandler(ErrorHandler)
     */
    public abstract ErrorHandler getErrorHandler();

    /**
     * Sets the {@link LSResourceResolver} to customize
     * resource resolution when parsing schemas.
     *
     * <p>
     * {@link SchemaFactory} uses a {@link LSResourceResolver}
     * when it needs to locate external resources while parsing schemas,
     * although exactly what constitutes "locating external resources" is
     * up to each schema language. For example, for W3C XML Schema,
     * this includes files <code>&lt;include></code>d or <code>&lt;import></code>ed,
     * and DTD referenced from schema files, etc.
     *
     * <p>
     * Applications can call this method even during a {@link Schema}
     * is being parsed.
     *
     * <p>
     * When the {@link LSResourceResolver} is null, the implementation will
     * behave as if the following {@link LSResourceResolver} is set:
     * <pre>
     * class DumbDOMResourceResolver implements {@link LSResourceResolver} {
     *     public {@link org.w3c.dom.ls.LSInput} resolveResource(
     *         String publicId, String systemId, String baseURI) {
     *
     *         return null; // always return null
     *     }
     * }
     * </pre>
     *
     * <p>
     * If a {@link LSResourceResolver} throws a {@link RuntimeException}
     *  (or instances of its derived classes),
     * then the {@link SchemaFactory} will abort the parsing and
     * the caller of the <code>newSchema</code> method will receive
     * the same {@link RuntimeException}.
     *
     * <p>
     * When a new {@link SchemaFactory} object is created, initially
     * this field is set to null.  This field will <em>NOT</em> be
     * inherited to {@link Schema}s, {@link Validator}s, or
     * {@link ValidatorHandler}s that are created from this {@link SchemaFactory}.
     *
     * <p>
     *  设置{@link LSResourceResolver}以在解析模式时自定义资源解析。
     * 
     * <p>
     * 当需要在解析模式时定位外部资源时,{@link SchemaFactory}使用{@link LSResourceResolver},虽然"定位外部资源"是什么构成了每种模式语言。
     * 例如,对于W3C XML模式,这包括文件<code>&lt; include> </code> d或<code>&lt; import> </code> ed,以及从模式文件引用的DTD等。
     * 
     * <p>
     *  即使在{@link Schema}正在解析中,应用程式仍可呼叫此方法。
     * 
     * <p>
     *  当{@link LSResourceResolver}为null时,实现将如同设置以下{@link LSResourceResolver}：
     * <pre>
     *  class DumbDOMResourceResolver implements {@link LSResourceResolver} {public {@link org.w3c.dom.ls.LSInput}
     *  resolveResource(String publicId,String systemId,String baseURI){。
     * 
     *  return null; // always return null}}
     * </pre>
     * 
     * <p>
     *  如果{@link LSResourceResolver}抛出{@link RuntimeException}(或其派生类的实例),则{@link SchemaFactory}将中止解析,并且<code>
     *  newSchema </code>方法的调用者将接收相同的{@link RuntimeException}。
     * 
     * <p>
     *  创建新的{@link SchemaFactory}对象时,最初此字段设置为null。
     * 此字段将<em>不会</em>继承到从此{@link SchemaFactory}创建的{@link Schema},{@link Validator}或{@link ValidatorHandler}
     * 。
     *  创建新的{@link SchemaFactory}对象时,最初此字段设置为null。
     * 
     * 
     * @param   resourceResolver
     *      A new resource resolver to be set. This parameter can be null.
     */
    public abstract void setResourceResolver(LSResourceResolver resourceResolver);

    /**
     * Gets the current {@link LSResourceResolver} set to this {@link SchemaFactory}.
     *
     * <p>
     *  获取设置为此{@link SchemaFactory}的当前{@link LSResourceResolver}。
     * 
     * 
     * @return
     *      This method returns the object that was last set through
     *      the {@link #setResourceResolver(LSResourceResolver)} method, or null
     *      if that method has never been called since this {@link SchemaFactory}
     *      has created.
     *
     * @see #setErrorHandler(ErrorHandler)
     */
    public abstract LSResourceResolver getResourceResolver();

    /**
     * <p>Parses the specified source as a schema and returns it as a schema.</p>
     *
     * <p>This is a convenience method for {@link #newSchema(Source[] schemas)}.</p>
     *
     * <p>
     *  <p>将指定的源解析为模式,并将其作为模式返回。</p>
     * 
     * <p>这是{@link #newSchema(Source [] schemas)}的一个方便的方法。</p>
     * 
     * 
     * @param schema Source that represents a schema.
     *
     * @return New <code>Schema</code> from parsing <code>schema</code>.
     *
     * @throws SAXException If a SAX error occurs during parsing.
     * @throws NullPointerException if <code>schema</code> is null.
     */
    public Schema newSchema(Source schema) throws SAXException {
        return newSchema(new Source[]{schema});
    }

    /**
     * <p>Parses the specified <code>File</code> as a schema and returns it as a <code>Schema</code>.</p>
     *
     * <p>This is a convenience method for {@link #newSchema(Source schema)}.</p>
     *
     * <p>
     *  <p>将指定的<code> File </code>解析为架构,并将其作为<code> Schema </code>返回。</p>
     * 
     *  <p>这是{@link #newSchema(来源架构)}的简便方法。</p>
     * 
     * 
     * @param schema File that represents a schema.
     *
     * @return New <code>Schema</code> from parsing <code>schema</code>.
     *
     * @throws SAXException If a SAX error occurs during parsing.
     * @throws NullPointerException if <code>schema</code> is null.
     */
    public Schema newSchema(File schema) throws SAXException {
        return newSchema(new StreamSource(schema));
    }

    /**
     * <p>Parses the specified <code>URL</code> as a schema and returns it as a <code>Schema</code>.</p>
     *
     * <p>This is a convenience method for {@link #newSchema(Source schema)}.</p>
     *
     * <p>
     *  <p>将指定的<code> URL </code>解析为架构,并将其作为<code> Schema </code>返回。</p>
     * 
     *  <p>这是{@link #newSchema(来源架构)}的简便方法。</p>
     * 
     * 
     * @param schema <code>URL</code> that represents a schema.
     *
     * @return New <code>Schema</code> from parsing <code>schema</code>.
     *
     * @throws SAXException If a SAX error occurs during parsing.
     * @throws NullPointerException if <code>schema</code> is null.
     */
    public Schema newSchema(URL schema) throws SAXException {
        return newSchema(new StreamSource(schema.toExternalForm()));
    }

    /**
     * Parses the specified source(s) as a schema and returns it as a schema.
     *
     * <p>
     * The callee will read all the {@link Source}s and combine them into a
     * single schema. The exact semantics of the combination depends on the schema
     * language that this {@link SchemaFactory} object is created for.
     *
     * <p>
     * When an {@link ErrorHandler} is set, the callee will report all the errors
     * found in sources to the handler. If the handler throws an exception, it will
     * abort the schema compilation and the same exception will be thrown from
     * this method. Also, after an error is reported to a handler, the callee is allowed
     * to abort the further processing by throwing it. If an error handler is not set,
     * the callee will throw the first error it finds in the sources.
     *
     * <h2>W3C XML Schema 1.0</h2>
     * <p>
     * The resulting schema contains components from the specified sources.
     * The same result would be achieved if all these sources were
     * imported, using appropriate values for schemaLocation and namespace,
     * into a single schema document with a different targetNamespace
     * and no components of its own, if the import elements were given
     * in the same order as the sources.  Section 4.2.3 of the XML Schema
     * recommendation describes the options processors have in this
     * regard.  While a processor should be consistent in its treatment of
     * JAXP schema sources and XML Schema imports, the behaviour between
     * JAXP-compliant parsers may vary; in particular, parsers may choose
     * to ignore all but the first &lt;import> for a given namespace,
     * regardless of information provided in schemaLocation.
     *
     * <p>
     * If the parsed set of schemas includes error(s) as
     * specified in the section 5.1 of the XML Schema spec, then
     * the error must be reported to the {@link ErrorHandler}.
     *
     * <h2>RELAX NG</h2>
     *
     * <p>For RELAX NG, this method must throw {@link UnsupportedOperationException}
     * if <code>schemas.length!=1</code>.
     *
     *
     * <p>
     *  将指定的源解析为模式并将其作为模式返回。
     * 
     * <p>
     *  被调用者将读取所有{@link Source}并将它们组合成一个模式。组合的确切语义取决于为此{@link SchemaFactory}对象创建的模式语言。
     * 
     * <p>
     *  当设置了{@link ErrorHandler}时,被调用者将向处理程序报告源中找到的所有错误。如果处理程序抛出异常,它将中止模式编译,并且将从此方法抛出相同的异常。
     * 此外,在将错误报告给处理程序之后,允许被调用程序通过抛出而中止进一步的处理。如果没有设置错误处理程序,被调用程序将抛出它在源中找到的第一个错误。
     * 
     *  <h2> W3C XML Schema 1.0 </h2>
     * <p>
     * 生成的模式包含来自指定源的组件。
     * 如果使用适当的schemaLocation和命名空间值将所有这些源导入到具有不同targetNamespace的单一模式文档中,并且没有自己的组件,那么将实现相同的结果(如果导入元素以与源相同的顺序给出
     * ) 。
     * 生成的模式包含来自指定源的组件。 XML Schema建议的第4.2.3节描述了处理器在这方面的选项。
     * 虽然处理器在处理JAXP模式源和XML模式导入时应该保持一致,但是JAXP兼容解析器之间的行为可能不同;特别是,解析器可以选择忽略除给定命名空间的第一个&lt; import&gt;之外的所有,而不管s
     * chemaLocation中提供的信息。
     * 生成的模式包含来自指定源的组件。 XML Schema建议的第4.2.3节描述了处理器在这方面的选项。
     * 
     * <p>
     *  如果解析的模式集包括XML模式规范的第5.1节中指定的错误,则必须将错误报告给{@link ErrorHandler}。
     * 
     *  <h2> RELAX NG </h2>
     * 
     *  <p>对于RELAX NG,如果<code> schemas.length！= 1 </code>,此方法必须抛出{@link UnsupportedOperationException}。
     * 
     * 
     * @param schemas
     *      inputs to be parsed. {@link SchemaFactory} is required
     *      to recognize {@link javax.xml.transform.sax.SAXSource},
     *      {@link StreamSource},
     *      {@link javax.xml.transform.stax.StAXSource},
     *      and {@link javax.xml.transform.dom.DOMSource}.
     *      Input schemas must be XML documents or
     *      XML elements and must not be null. For backwards compatibility,
     *      the results of passing anything other than
     *      a document or element are implementation-dependent.
     *      Implementations must either recognize and process the input
     *      or thrown an IllegalArgumentException.
     *
     * @return
     *      Always return a non-null valid {@link Schema} object.
     *      Note that when an error has been reported, there is no
     *      guarantee that the returned {@link Schema} object is
     *      meaningful.
     *
     * @throws SAXException
     *      If an error is found during processing the specified inputs.
     *      When an {@link ErrorHandler} is set, errors are reported to
     *      there first. See {@link #setErrorHandler(ErrorHandler)}.
     * @throws NullPointerException
     *      If the <code>schemas</code> parameter itself is null or
     *      any item in the array is null.
     * @throws IllegalArgumentException
     *      If any item in the array is not recognized by this method.
     * @throws UnsupportedOperationException
     *      If the schema language doesn't support this operation.
     */
    public abstract Schema newSchema(Source[] schemas) throws SAXException;

    /**
     * Creates a special {@link Schema} object.
     *
     * <p>The exact semantics of the returned {@link Schema} object
     * depend on the schema language for which this {@link SchemaFactory}
     * is created.
     *
     * <p>Also, implementations are allowed to use implementation-specific
     * property/feature to alter the semantics of this method.</p>
     *
     * <p>Implementors and developers should pay particular attention
     * to how the features set on this {@link SchemaFactory} are
     * processed by this special {@link Schema}.
     * In some cases, for example, when the
     * {@link SchemaFactory} and the class actually loading the
     * schema come from different implementations, it may not be possible
     * for {@link SchemaFactory} features to be inherited automatically.
     * Developers should
     * make sure that features, such as secure processing, are explicitly
     * set in both places.</p>
     *
     * <h2>W3C XML Schema 1.0</h2>
     * <p>
     * For XML Schema, this method creates a {@link Schema} object that
     * performs validation by using location hints specified in documents.
     *
     * <p>
     * The returned {@link Schema} object assumes that if documents
     * refer to the same URL in the schema location hints,
     * they will always resolve to the same schema document. This
     * asusmption allows implementations to reuse parsed results of
     * schema documents so that multiple validations against the same
     * schema will run faster.
     *
     * <p>
     * Note that the use of schema location hints introduces a
     * vulnerability to denial-of-service attacks.
     *
     *
     * <h2>RELAX NG</h2>
     * <p>
     * RELAX NG does not support this operation.
     *
     * <p>
     * 
     * @return
     *      Always return non-null valid {@link Schema} object.
     *
     * @throws UnsupportedOperationException
     *      If this operation is not supported by the callee.
     * @throws SAXException
     *      If this operation is supported but failed for some reason.
     */
    public abstract Schema newSchema() throws SAXException;
}
