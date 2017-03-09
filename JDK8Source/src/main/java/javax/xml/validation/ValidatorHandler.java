/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2005, Oracle and/or its affiliates. All rights reserved.
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

import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.ContentHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

/**
 * Streaming validator that works on SAX stream.
 *
 * <p>
 * A {@link ValidatorHandler} object is not thread-safe and not reentrant.
 * In other words, it is the application's responsibility to make
 * sure that one {@link ValidatorHandler} object is not used from
 * more than one thread at any given time.
 *
 * <p>
 * {@link ValidatorHandler} checks if the SAX events follow
 * the set of constraints described in the associated {@link Schema},
 * and additionally it may modify the SAX events (for example
 * by adding default values, etc.)
 *
 * <p>
 * {@link ValidatorHandler} extends from {@link ContentHandler},
 * but it refines the underlying {@link ContentHandler} in
 * the following way:
 * <ol>
 *  <li>startElement/endElement events must receive non-null String
 *      for <code>uri</code>, <code>localName</code>, and <code>qname</code>,
 *      even though SAX allows some of them to be null.
 *      Similarly, the user-specified {@link ContentHandler} will receive non-null
 *      Strings for all three parameters.
 *
 *  <li>Applications must ensure that {@link ValidatorHandler}'s
 *      {@link ContentHandler#startPrefixMapping(String,String)} and
 *      {@link ContentHandler#endPrefixMapping(String)} are invoked
 *      properly. Similarly, the user-specified {@link ContentHandler}
 *      will receive startPrefixMapping/endPrefixMapping events.
 *      If the {@link ValidatorHandler} introduces additional namespace
 *      bindings, the user-specified {@link ContentHandler} will receive
 *      additional startPrefixMapping/endPrefixMapping events.
 *
 *  <li>{@link org.xml.sax.Attributes} for the
 *      {@link ContentHandler#startElement(String,String,String,Attributes)} method
 *      may or may not include xmlns* attributes.
 * </ol>
 *
 * <p>
 * A {@link ValidatorHandler} is automatically reset every time
 * the startDocument method is invoked.
 *
 * <h2>Recognized Properties and Features</h2>
 * <p>
 * This spec defines the following feature that must be recognized
 * by all {@link ValidatorHandler} implementations.
 *
 * <h3><code>http://xml.org/sax/features/namespace-prefixes</code></h3>
 * <p>
 * This feature controls how a {@link ValidatorHandler} introduces
 * namespace bindings that were not present in the original SAX event
 * stream.
 * When this feature is set to true, it must make
 * sure that the user's {@link ContentHandler} will see
 * the corresponding <code>xmlns*</code> attribute in
 * the {@link org.xml.sax.Attributes} object of the
 * {@link ContentHandler#startElement(String,String,String,Attributes)}
 * callback. Otherwise, <code>xmlns*</code> attributes must not be
 * added to {@link org.xml.sax.Attributes} that's passed to the
 * user-specified {@link ContentHandler}.
 * <p>
 * (Note that regardless of this switch, namespace bindings are
 * always notified to applications through
 * {@link ContentHandler#startPrefixMapping(String,String)} and
 * {@link ContentHandler#endPrefixMapping(String)} methods of the
 * {@link ContentHandler} specified by the user.)
 *
 * <p>
 * Note that this feature does <em>NOT</em> affect the way
 * a {@link ValidatorHandler} receives SAX events. It merely
 * changes the way it augments SAX events.
 *
 * <p>This feature is set to <code>false</code> by default.</p>
 *
 * <p>
 *  在SAX流上工作的流验证器。
 * 
 * <p>
 *  {@link ValidatorHandler}对象不是线程安全的,不可重入。换句话说,应用程序有责任确保在任何给定时间一个{@link ValidatorHandler}对象不被多个线程使用。
 * 
 * <p>
 *  {@link ValidatorHandler}检查SAX事件是否遵循相关{@link Schema}中描述的约束集,此外还可以修改SAX事件(例如通过添加默认值等)
 * 
 * <p>
 *  {@link ValidatorHandler}扩展自{@link ContentHandler},但它通过以下方式细化底层的{@link ContentHandler}：
 * <ol>
 *  <li> startElement / endElement事件必须接收<code> uri </code>,<code> localName </code>和<code> qname </code>
 * 的非空字符串,即使SAX允许其中一些为null。
 * 类似地,用户指定的{@link ContentHandler}将接收所有三个参数的非空字符串。
 * 
 * <li>应用程序必须确保正确调用{@link ValidatorHandler}的{@link ContentHandler#startPrefixMapping(String,String)}和{@link ContentHandler#endPrefixMapping(String)}
 * 。
 * 类似地,用户指定的{@link ContentHandler}将接收startPrefixMapping / endPrefixMapping事件。
 * 如果{@link ValidatorHandler}引入了附加的命名空间绑定,那么用户指定的{@link ContentHandler}将接收到额外的startPrefixMapping / endPr
 * efixMapping事件。
 * 类似地,用户指定的{@link ContentHandler}将接收startPrefixMapping / endPrefixMapping事件。
 * 
 *  <li> {@link ContentHandler#startElement(String,String,String,Attributes)}方法的{@ link org.xml.sax.Attributes}
 * 可能包含或可能不包含xmlns *属性。
 * </ol>
 * 
 * <p>
 *  每次调用startDocument方法时,{@link ValidatorHandler}都会自动重置。
 * 
 *  <h2>已识别的属性和功能</h2>
 * <p>
 *  此规范定义了必须由所有{@link ValidatorHandler}实现识别的以下功能。
 * 
 *  <h3> <code> http://xml.org/sax/features/namespace-prefixes </code> </h3>
 * <p>
 * 此功能控制{@link ValidatorHandler}如何引入原始SAX事件流中不存在的命名空间绑定。
 * 当此功能设置为true时,必须确保用户的{@link ContentHandler}在{@link org.xml.sax.Attributes}对象中看到相应的<code> xmlns * </code>
 *  {@link ContentHandler#startElement(String,String,String,Attributes)}回调。
 * 此功能控制{@link ValidatorHandler}如何引入原始SAX事件流中不存在的命名空间绑定。
 * 否则,不能将<code> xmlns * </code>属性添加到传递给用户指定的{@link ContentHandler}的{@link org.xml.sax.Attributes}。
 * <p>
 *  (注意,不管这个切换,命名空间绑定总是通过{@link ContentHandler#startPrefixMapping(String,String)}和{@link ContentHandler#endPrefixMapping(String)}
 * 方法通知{@link ContentHandler}用户。
 * )。
 * 
 * <p>
 *  请注意,此功能会<em>不会</em>影响{@link ValidatorHandler}接收SAX事件的方式。它只是改变了它增强SAX事件的方式。
 * 
 *  <p>此功能默认设置为<code> false </code>。</p>
 * 
 * 
 * @author  <a href="mailto:Kohsuke.Kawaguchi@Sun.com">Kohsuke Kawaguchi</a>
 * @since 1.5
 */
public abstract class ValidatorHandler implements ContentHandler {

    /**
     * <p>Constructor for derived classes.</p>
     *
     * <p>The constructor does nothing.</p>
     *
     * <p>Derived classes must create {@link ValidatorHandler} objects that have
     * <code>null</code> {@link ErrorHandler} and
     * <code>null</code> {@link LSResourceResolver}.</p>
     * <p>
     *  <p>派生类的构造方法。</p>
     * 
     *  <p>构造函数不执行任何操作。</p>
     * 
     *  <p>派生类必须创建具有<code> null </code> {@link ErrorHandler}和<code> null </code> {@link LSResourceResolver}的
     * {@link ValidatorHandler}对象。
     * </p>。
     * 
     */
    protected ValidatorHandler() {
    }

    /**
     * Sets the {@link ContentHandler} which receives
     * the augmented validation result.
     *
     * <p>
     * When a {@link ContentHandler} is specified, a
     * {@link ValidatorHandler} will work as a filter
     * and basically copy the incoming events to the
     * specified {@link ContentHandler}.
     *
     * <p>
     * In doing so, a {@link ValidatorHandler} may modify
     * the events, for example by adding defaulted attributes.
     *
     * <p>
     * A {@link ValidatorHandler} may buffer events to certain
     * extent, but to allow {@link ValidatorHandler} to be used
     * by a parser, the following requirement has to be met.
     *
     * <ol>
     *  <li>When
     *      {@link ContentHandler#startElement(String, String, String, Attributes)},
     *      {@link ContentHandler#endElement(String, String, String)},
     *      {@link ContentHandler#startDocument()}, or
     *      {@link ContentHandler#endDocument()}
     *      are invoked on a {@link ValidatorHandler},
     *      the same method on the user-specified {@link ContentHandler}
     *      must be invoked for the same event before the callback
     *      returns.
     *  <li>{@link ValidatorHandler} may not introduce new elements that
     *      were not present in the input.
     *
     *  <li>{@link ValidatorHandler} may not remove attributes that were
     *      present in the input.
     * </ol>
     *
     * <p>
     * When a callback method on the specified {@link ContentHandler}
     * throws an exception, the same exception object must be thrown
     * from the {@link ValidatorHandler}. The {@link ErrorHandler}
     * should not be notified of such an exception.
     *
     * <p>
     * This method can be called even during a middle of a validation.
     *
     * <p>
     *  设置接收增强验证结果的{@link ContentHandler}。
     * 
     * <p>
     * 当指定{@link ContentHandler}时,{@link ValidatorHandler}将用作过滤器,并基本上将传入的事件复制到指定的{@link ContentHandler}。
     * 
     * <p>
     *  在这种情况下,{@link ValidatorHandler}可以修改事件,例如通过添加默认属性。
     * 
     * <p>
     *  {@link ValidatorHandler}可以在一定程度上缓冲事件,但是为了允许解析器使用{@link ValidatorHandler},必须满足以下要求。
     * 
     * <ol>
     *  <li>当{@link ContentHandler#startElement(String,String,String,Attributes)},{@link ContentHandler#endElement(String,String,String)}
     * ,{@link ContentHandler#startDocument()}或{@link在{@link ValidatorHandler}上调用ContentHandler#endDocument(
     * )},在回调返回之前,必须为同一个事件调用用户指定的{@link ContentHandler}上的同一个方法。
     *  <li> {@ link ValidatorHandler}可能不会引入输入中不存在的新元素。
     * 
     *  <li> {@ link ValidatorHandler}不能删除输入中存在的属性。
     * </ol>
     * 
     * <p>
     *  当指定的{@link ContentHandler}上的回调方法抛出异常时,必须从{@link ValidatorHandler}抛出相同的异常对象。
     *  {@link ErrorHandler}不应该被通知这样的异常。
     * 
     * <p>
     *  即使在验证中间,也可以调用此方法。
     * 
     * 
     * @param receiver
     *      A {@link ContentHandler} or a null value.
     */
    public abstract void setContentHandler(ContentHandler receiver);

    /**
     * Gets the {@link ContentHandler} which receives the
     * augmented validation result.
     *
     * <p>
     *  获取接收增强验证结果的{@link ContentHandler}。
     * 
     * 
     * @return
     *      This method returns the object that was last set through
     *      the {@link #getContentHandler()} method, or null
     *      if that method has never been called since this {@link ValidatorHandler}
     *      has created.
     *
     * @see #setContentHandler(ContentHandler)
     */
    public abstract ContentHandler getContentHandler();

    /**
     * Sets the {@link ErrorHandler} to receive errors encountered
     * during the validation.
     *
     * <p>
     * Error handler can be used to customize the error handling process
     * during a validation. When an {@link ErrorHandler} is set,
     * errors found during the validation will be first sent
     * to the {@link ErrorHandler}.
     *
     * <p>
     * The error handler can abort further validation immediately
     * by throwing {@link org.xml.sax.SAXException} from the handler. Or for example
     * it can print an error to the screen and try to continue the
     * validation by returning normally from the {@link ErrorHandler}
     *
     * <p>
     * If any {@link Throwable} is thrown from an {@link ErrorHandler},
     * the same {@link Throwable} object will be thrown toward the
     * root of the call stack.
     *
     * <p>
     * {@link ValidatorHandler} is not allowed to
     * throw {@link org.xml.sax.SAXException} without first reporting it to
     * {@link ErrorHandler}.
     *
     * <p>
     * When the {@link ErrorHandler} is null, the implementation will
     * behave as if the following {@link ErrorHandler} is set:
     * <pre>
     * class DraconianErrorHandler implements {@link ErrorHandler} {
     *     public void fatalError( {@link org.xml.sax.SAXParseException} e ) throws {@link org.xml.sax.SAXException} {
     *         throw e;
     *     }
     *     public void error( {@link org.xml.sax.SAXParseException} e ) throws {@link org.xml.sax.SAXException} {
     *         throw e;
     *     }
     *     public void warning( {@link org.xml.sax.SAXParseException} e ) throws {@link org.xml.sax.SAXException} {
     *         // noop
     *     }
     * }
     * </pre>
     *
     * <p>
     * When a new {@link ValidatorHandler} object is created, initially
     * this field is set to null.
     *
     * <p>
     *  设置{@link ErrorHandler}以接收验证期间遇到的错误。
     * 
     * <p>
     * 错误处理程序可用于在验证期间自定义错误处理过程。当设置{@link ErrorHandler}时,验证期间发现的错误将首先发送到{@link ErrorHandler}。
     * 
     * <p>
     *  错误处理程序可以通过从处理程序抛出{@link org.xml.sax.SAXException}来立即中止进一步的验证。
     * 或者例如,它可以打印一个错误到屏幕,并尝试继续验证通常返回从{@link ErrorHandler}。
     * 
     * <p>
     *  如果从{@link ErrorHandler}抛出任何{@link Throwable},则相同的{@link Throwable}对象将被抛向调用堆栈的根。
     * 
     * <p>
     *  {@link ValidatorHandler}不允许抛出{@link org.xml.sax.SAXException},而不先将其报告给{@link ErrorHandler}。
     * 
     * <p>
     *  当{@link ErrorHandler}为null时,实现将如同设置以下{@link ErrorHandler}：
     * <pre>
     *  class DraconianErrorHandler implements {@link ErrorHandler} {public void fatalError({@link org.xml.sax.SAXParseException}
     *  e)throws {@link org.xml.sax.SAXException} {throw e; } public void error({@link org.xml.sax.SAXParseException}
     *  e)throws {@link org.xml.sax.SAXException} {throw e; } public void warning({@link org.xml.sax.SAXParseException}
     *  e)throws {@link org.xml.sax.SAXException} {// noop}}。
     * </pre>
     * 
     * <p>
     *  当创建一个新的{@link ValidatorHandler}对象时,该字段最初设置为null。
     * 
     * 
     * @param   errorHandler
     *      A new error handler to be set. This parameter can be null.
     */
    public abstract void setErrorHandler(ErrorHandler errorHandler);

    /**
     * Gets the current {@link ErrorHandler} set to this {@link ValidatorHandler}.
     *
     * <p>
     *  获取设置为此{@link ValidatorHandler}的当前{@link ErrorHandler}。
     * 
     * 
     * @return
     *      This method returns the object that was last set through
     *      the {@link #setErrorHandler(ErrorHandler)} method, or null
     *      if that method has never been called since this {@link ValidatorHandler}
     *      has created.
     *
     * @see #setErrorHandler(ErrorHandler)
     */
    public abstract ErrorHandler getErrorHandler();

    /**
     * Sets the {@link LSResourceResolver} to customize
     * resource resolution while in a validation episode.
     *
     * <p>
     * {@link ValidatorHandler} uses a {@link LSResourceResolver}
     * when it needs to locate external resources while a validation,
     * although exactly what constitutes "locating external resources" is
     * up to each schema language.
     *
     * <p>
     * When the {@link LSResourceResolver} is null, the implementation will
     * behave as if the following {@link LSResourceResolver} is set:
     * <pre>
     * class DumbLSResourceResolver implements {@link LSResourceResolver} {
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
     * then the {@link ValidatorHandler} will abort the parsing and
     * the caller of the <code>validate</code> method will receive
     * the same {@link RuntimeException}.
     *
     * <p>
     * When a new {@link ValidatorHandler} object is created, initially
     * this field is set to null.
     *
     * <p>
     * 设置{@link LSResourceResolver}以在验证剧集中自定义资源解析。
     * 
     * <p>
     *  当需要在验证时定位外部资源时,{@link ValidatorHandler}使用{@link LSResourceResolver},虽然"定位外部资源"是什么构成了每种模式语言。
     * 
     * <p>
     *  当{@link LSResourceResolver}为null时,实现将如同设置以下{@link LSResourceResolver}：
     * <pre>
     *  类DumbLSResourceResolver实现{@link LSResourceResolver} {public {@link org.w3c.dom.ls.LSInput} resolveRe
     * source(String publicId,String systemId,String baseURI){。
     * 
     *  return null; // always return null}}
     * </pre>
     * 
     * <p>
     *  如果{@link LSResourceResolver}抛出{@link RuntimeException}(或其派生类的实例),则{@link ValidatorHandler}将中止解析,并且<code>
     *  validate </code>方法的调用者将接收相同的{@link RuntimeException}。
     * 
     * <p>
     *  当创建一个新的{@link ValidatorHandler}对象时,该字段最初设置为null。
     * 
     * 
     * @param   resourceResolver
     *      A new resource resolver to be set. This parameter can be null.
     */
    public abstract void setResourceResolver(LSResourceResolver resourceResolver);

    /**
     * Gets the current {@link LSResourceResolver} set to this {@link ValidatorHandler}.
     *
     * <p>
     *  获取设置为此{@link ValidatorHandler}的当前{@link LSResourceResolver}。
     * 
     * 
     * @return
     *      This method returns the object that was last set through
     *      the {@link #setResourceResolver(LSResourceResolver)} method, or null
     *      if that method has never been called since this {@link ValidatorHandler}
     *      has created.
     *
     * @see #setErrorHandler(ErrorHandler)
     */
    public abstract LSResourceResolver getResourceResolver();

    /**
     * Obtains the {@link TypeInfoProvider} implementation of this
     * {@link ValidatorHandler}.
     *
     * <p>
     * The obtained {@link TypeInfoProvider} can be queried during a parse
     * to access the type information determined by the validator.
     *
     * <p>
     * Some schema languages do not define the notion of type,
     * for those languages, this method may not be supported.
     * However, to be compliant with this specification, implementations
     * for W3C XML Schema 1.0 must support this operation.
     *
     * <p>
     *  获取此{@link ValidatorHandler}的{@link TypeInfoProvider}实现。
     * 
     * <p>
     *  可以在解析期间查询获得的{@linkTypeInfoProvider}以访问由验证器确定的类型信息。
     * 
     * <p>
     * 一些模式语言不定义类型的概念,对于这些语言,可能不支持此方法。但是,为了符合本规范,W3C XML Schema 1.0的实现必须支持此操作。
     * 
     * 
     * @return
     *      null if the validator / schema language does not support
     *      the notion of {@link org.w3c.dom.TypeInfo}.
     *      Otherwise a non-null valid {@link TypeInfoProvider}.
     */
    public abstract TypeInfoProvider getTypeInfoProvider();


    /**
     * Look up the value of a feature flag.
     *
     * <p>The feature name is any fully-qualified URI.  It is
     * possible for a {@link ValidatorHandler} to recognize a feature name but
     * temporarily be unable to return its value.
     * Some feature values may be available only in specific
     * contexts, such as before, during, or after a validation.
     *
     * <p>Implementors are free (and encouraged) to invent their own features,
     * using names built on their own URIs.</p>
     *
     * <p>
     *  查找特征标志的值。
     * 
     *  <p>地图项名称是任何完全限定的URI。 {@link ValidatorHandler}可能会识别功能名称,但暂时无法返回其值。一些特征值可能仅在特定上下文中可用,例如在验证之前,期间或之后。
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
     *   {@link ValidatorHandler} recognizes the feature name but
     *   cannot determine its value at this time.
     * @throws NullPointerException When <code>name</code> is <code>null</code>.
     *
     * @see #setFeature(String, boolean)
     */
    public boolean getFeature(String name)
        throws SAXNotRecognizedException, SAXNotSupportedException {

        if (name == null) {
            throw new NullPointerException();
        }

        throw new SAXNotRecognizedException(name);
    }

    /**
     * <p>Set a feature for this <code>ValidatorHandler</code>.</p>
     *
     * <p>Feature can be used to control the way a
     * {@link ValidatorHandler} parses schemas. The feature name is
     * any fully-qualified URI. It is possible for a
     * {@link SchemaFactory} to
     * expose a feature value but to be unable to change the current
     * value. Some feature values may be immutable or mutable only in
     * specific contexts, such as before, during, or after a
     * validation.</p>
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
     *  <p>为此<code> ValidatorHandler </code>设置一项功能。</p>
     * 
     *  <p>功能可用于控制{@link ValidatorHandler}解析模式的方式。功能名称是任何完全限定的URI。
     *  {@link SchemaFactory}可能会暴露一个特征值,但无法更改当前值。某些特征值可能仅在特定上下文中是不可变的或可变的,例如在验证之前,期间或之后。</p>。
     * 
     *  <p>所有实现都需要支持{@link javax.xml.XMLConstants#FEATURE_SECURE_PROCESSING}功能。功能为：</p>
     * <ul>
     * <li>
     * <code> true </code>：实现将限制XML处理以符合实现限制。示例包括将消耗大量资源的扩展限制和XML模式构造。
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
     *   {@link ValidatorHandler} recognizes the feature name but
     *   cannot set the requested value.
     * @throws NullPointerException When <code>name</code> is <code>null</code>.
     *
     * @see #getFeature(String)
     */
    public void setFeature(String name, boolean value)
        throws SAXNotRecognizedException, SAXNotSupportedException {

        if (name == null) {
            throw new NullPointerException();
        }

        throw new SAXNotRecognizedException(name);
    }

    /**
     * Set the value of a property.
     *
     * <p>The property name is any fully-qualified URI.  It is
     * possible for a {@link ValidatorHandler} to recognize a property name but
     * to be unable to change the current value.
     * Some property values may be immutable or mutable only
     * in specific contexts, such as before, during, or after
     * a validation.</p>
     *
     * <p>{@link ValidatorHandler}s are not required to recognize setting
     * any specific property names.</p>
     *
     * <p>
     *  设置属性的值。
     * 
     *  <p>属性名称是任何完全限定的URI。 {@link ValidatorHandler}可以识别属性名称,但无法更改当前值。
     * 某些属性值可能仅在特定上下文中是不可变的或可变的,例如在验证之前,期间或之后。</p>。
     * 
     *  <p> {@ link ValidatorHandler}不需要识别设置任何特定的属性名称。</p>
     * 
     * 
     * @param name The property name, which is a non-null fully-qualified URI.
     * @param object The requested value for the property.
     *
     * @throws SAXNotRecognizedException If the property
     *   value can't be assigned or retrieved.
     * @throws SAXNotSupportedException When the
     *   {@link ValidatorHandler} recognizes the property name but
     *   cannot set the requested value.
     * @throws NullPointerException When <code>name</code> is <code>null</code>.
     */
    public void setProperty(String name, Object object)
        throws SAXNotRecognizedException, SAXNotSupportedException {

        if (name == null) {
            throw new NullPointerException();
        }

        throw new SAXNotRecognizedException(name);
    }

    /**
     * Look up the value of a property.
     *
     * <p>The property name is any fully-qualified URI.  It is
     * possible for a {@link ValidatorHandler} to recognize a property name but
     * temporarily be unable to return its value.
     * Some property values may be available only in specific
     * contexts, such as before, during, or after a validation.</p>
     *
     * <p>{@link ValidatorHandler}s are not required to recognize any specific
     * property names.</p>
     *
     * <p>Implementors are free (and encouraged) to invent their own properties,
     * using names built on their own URIs.</p>
     *
     * <p>
     *  查找属性的值。
     * 
     *  <p>属性名称是任何完全限定的URI。 {@link ValidatorHandler}可能会识别属性名称,但暂时无法返回其值。某些属性值只能在特定上下文中使用,例如在验证之前,期间或之后。
     * </p>。
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
     * @throws NullPointerException When <code>name</code> is <code>null</code>.
     *
     * @see #setProperty(String, Object)
     */
    public Object getProperty(String name)
        throws SAXNotRecognizedException, SAXNotSupportedException {

        if (name == null) {
            throw new NullPointerException();
        }

        throw new SAXNotRecognizedException(name);
    }
}
