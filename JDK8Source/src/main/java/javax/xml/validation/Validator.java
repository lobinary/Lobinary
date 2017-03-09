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

import java.io.IOException;

import javax.xml.transform.Result;
import javax.xml.transform.Source;

import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

/**
 * <p>A processor that checks an XML document against {@link Schema}.</p>
 *
 * <p>
 * A validator object is not thread-safe and not reentrant.
 * In other words, it is the application's responsibility to make
 * sure that one {@link Validator} object is not used from
 * more than one thread at any given time, and while the <code>validate</code>
 * method is invoked, applications may not recursively call
 * the <code>validate</code> method.
 * <p>
 *
 *
 * <p>
 *  <p>一种处理器,用于根据{@link Schema}检查XML文档。</p>
 * 
 * <p>
 *  验证器对象不是线程安全的,不可重入。
 * 换句话说,应用程序有责任确保一个{@link Validator}对象在任何给定时间都不会从多个线程中使用,并且在调用<code> validate </code>方法时,应用程序可能不递归调用<code>
 *  validate </code>方法。
 *  验证器对象不是线程安全的,不可重入。
 * <p>
 * 
 * 
 * @author  <a href="mailto:Kohsuke.Kawaguchi@Sun.com">Kohsuke Kawaguchi</a>
 * @since 1.5
 */
public abstract class Validator {

    /**
     * Constructor for derived classes.
     *
     * <p>The constructor does nothing.</p>
     *
     * <p>Derived classes must create {@link Validator} objects that have
     * <code>null</code> {@link ErrorHandler} and
     * <code>null</code> {@link LSResourceResolver}.
     * </p>
     * <p>
     *  派生类的构造方法。
     * 
     *  <p>构造函数不执行任何操作。</p>
     * 
     *  <p>派生类必须创建具有<code> null </code> {@link ErrorHandler}和<code> null </code> {@link LSResourceResolver}的
     * {@link Validator}对象。
     * </p>
     */
    protected Validator() {
    }

        /**
         * <p>Reset this <code>Validator</code> to its original configuration.</p>
         *
         * <p><code>Validator</code> is reset to the same state as when it was created with
         * {@link Schema#newValidator()}.
         * <code>reset()</code> is designed to allow the reuse of existing <code>Validator</code>s
         * thus saving resources associated with the creation of new <code>Validator</code>s.</p>
         *
         * <p>The reset <code>Validator</code> is not guaranteed to have the same {@link LSResourceResolver} or {@link ErrorHandler}
         * <code>Object</code>s, e.g. {@link Object#equals(Object obj)}.  It is guaranteed to have a functionally equal
         * <code>LSResourceResolver</code> and <code>ErrorHandler</code>.</p>
         * <p>
         *  <p>将此<code>验证程序</code>重置为其原始配置。</p>
         * 
         *  <p> <code>验证程序</code>被重置为与使用{@link Schema#newValidator()}创建时相同的状态。
         *  <code> reset()</code>旨在允许重用现有的<code> Validator </code>,从而节省与创建新的<code> Validator </code>相关联的资源。
         * 
         *  <p>重置<code>验证程序</code>不保证具有相同的{@link LSResourceResolver}或{@link ErrorHandler} <code> Object </code>,
         * 例如{@link Object#equals(Object obj)}。
         * 它保证有一个功能相等的<code> LSResourceResolver </code>和<code> ErrorHandler </code>。</p>。
         * 
         */
        public abstract void reset();

    /**
     * Validates the specified input.
     *
     * <p>This is just a convenience method for
     * {@link #validate(Source source, Result result)}
     * with <code>result</code> of <code>null</code>.</p>
     *
     * <p>
     *  验证指定的输入。
     * 
     * <p>这只是一个{@ link #validate(源代码,结果结果)}的方便方法,<code> result </code>为<code> null </code>。</p>
     * 
     * 
     * @param source
     *      XML to be validated. Must be an XML document or
     *      XML element and must not be null. For backwards compatibility,
     *      the results of attempting to validate anything other than
     *      a document or element are implementation-dependent.
     *      Implementations must either recognize and process the input
     *      or throw an IllegalArgumentException.
     *
     * @throws IllegalArgumentException
     *      If the <code>Source</code>
     *      is an XML artifact that the implementation cannot
     *      validate (for example, a processing instruction).
     *
     * @throws SAXException
     *      If the {@link ErrorHandler} throws a {@link SAXException} or
     *      if a fatal error is found and the {@link ErrorHandler} returns
     *      normally.
     *
     * @throws IOException
     *      If the validator is processing a
     *      {@link javax.xml.transform.sax.SAXSource} and the
     *      underlying {@link org.xml.sax.XMLReader} throws an
     *      {@link IOException}.
     *
     *
     * @throws NullPointerException If <code>source</code> is
     *   <code>null</code>.
     *
     * @see #validate(Source source, Result result)
     */
    public void validate(Source source)
        throws SAXException, IOException {

        validate(source, null);
    }

    /**
     * <p>Validates the specified input and send the augmented validation
     * result to the specified output.</p>
     *
     * <p>This method places the following restrictions on the types of
     * the {@link Source}/{@link Result} accepted.</p>
     *
     * <table border=1>
     * <thead>
     *  <tr>
     *   <th colspan="5"><code>Source</code> / <code>Result</code> Accepted</th>
     *  </tr>
     *  <tr>
     *   <th></th>
     *   <th>{@link javax.xml.transform.stream.StreamSource}</th>
     *   <th>{@link javax.xml.transform.sax.SAXSource}</th>
     *   <th>{@link javax.xml.transform.dom.DOMSource}</th>
     *   <th>{@link javax.xml.transform.stax.StAXSource}</th>
     *  </tr>
     * </thead>
     * <tbody align="center">
     *  <tr>
     *   <td><code>null</code></td>
     *   <td>OK</td>
     *   <td>OK</td>
     *   <td>OK</td>
     *   <td>OK</td>
     *  </tr>
     *  <tr>
     *   <th>{@link javax.xml.transform.stream.StreamResult}</th>
     *   <td>OK</td>
     *   <td><code>IllegalArgumentException</code></td>
     *   <td><code>IllegalArgumentException</code></td>
     *   <td><code>IllegalArgumentException</code></td>
     *  </tr>
     *  <tr>
     *   <th>{@link javax.xml.transform.sax.SAXResult}</th>
     *   <td><code>IllegalArgumentException</code></td>
     *   <td>OK</td>
     *   <td><code>IllegalArgumentException</code></td>
     *   <td><code>IllegalArgumentException</code></td>
     *  </tr>
     *  <tr>
     *   <th>{@link javax.xml.transform.dom.DOMResult}</th>
     *   <td><code>IllegalArgumentException</code></td>
     *   <td><code>IllegalArgumentException</code></td>
     *   <td>OK</td>
     *   <td><code>IllegalArgumentException</code></td>
     *  </tr>
     *  <tr>
     *   <th>{@link javax.xml.transform.stax.StAXResult}</th>
     *   <td><code>IllegalArgumentException</code></td>
     *   <td><code>IllegalArgumentException</code></td>
     *   <td><code>IllegalArgumentException</code></td>
     *   <td>OK</td>
     *  </tr>
     * </tbody>
     * </table>
     *
     * <p>To validate one <code>Source</code> into another kind of
     * <code>Result</code>, use the identity transformer (see
     * {@link javax.xml.transform.TransformerFactory#newTransformer()}).</p>
     *
     * <p>Errors found during the validation is sent to the specified
     * {@link ErrorHandler}.</p>
     *
     * <p>If a document is valid, or if a document contains some errors
     * but none of them were fatal and the <code>ErrorHandler</code> didn't
     * throw any exception, then the method returns normally.</p>
     *
     * <p>
     *  <p>验证指定的输入,并将增强的验证结果发送到指定的输出。</p>
     * 
     *  <p>此方法对接受的{@link Source} / {@ link Result}的类型设置了以下限制。</p>
     * 
     * <table border=1>
     * <thead>
     * <tr>
     *  <th colspan ="5"> <code> Source </code> / <code>结果</code>接受</th>
     * </tr>
     * <tr>
     *  <th> </th> <th> {@ link javax.xml.transform.stream.StreamSource} </th> <th> {@ link javax.xml.transform.sax.SAXSource}
     *  </th> <th> {@link javax.xml.transform.dom.DOMSource} </th> <th> {@ link javax.xml.transform.stax.StAXSource}
     *  </th>。
     * </tr>
     * </thead>
     * <tbody align="center">
     * <tr>
     *  <td> <td> OK </td> <td> </td> </td> </td>
     * </tr>
     * <tr>
     *  <th> {@ link javax.xml.transform.stream.StreamResult} </th> <td> OK </td> <td> <code> IllegalArgumen
     * tException </code> </td> <td> <code> IllegalArgumentException </code> </td> <td> <code> IllegalArgume
     * ntException </code> </td>。
     * </tr>
     * <tr>
     *  <th> {@ link javax.xml.transform.sax.SAXResult} </th> <td> <code> IllegalArgumentException </code> </td>
     *  <td> OK </td> <td> <code> IllegalArgumentException </code> </td> <td> <code> IllegalArgumentExceptio
     * n </code> </td>。
     * </tr>
     * <tr>
     *  <th> {@ link javax.xml.transform.dom.DOMResult} </th> <td> <code> IllegalArgumentException </code> </td>
     *  <td> <code> IllegalArgumentException </code> </td> <td> OK </td> <td> <code> IllegalArgumentExceptio
     * n </code> </td>。
     * </tr>
     * <tr>
     * <th> {@ link javax.xml.transform.stax.StAXResult} </th> <td> <code> IllegalArgumentException </code> 
     * </td> <td> <code> IllegalArgumentException </code> </td> <td> <code> IllegalArgumentException </code>
     *  </td> <td>确定</td>。
     * </tr>
     * </tbody>
     * </table>
     * 
     *  <p>要将一个<code> Source </code>验证为另一种<code> Result </code>,请使用身份转换器(参见{@link javax.xml.transform.TransformerFactory#newTransformer()}
     * )。
     *  </p>。
     * 
     *  <p>验证期间发现的错误会发送到指定的{@link ErrorHandler}。</p>
     * 
     *  <p>如果文档有效,或者文档包含一些错误,但没有一个是致命错误,并且<code> ErrorHandler </code>没有抛出任何异常,则该方法将正常返回。</p>
     * 
     * 
     * @param source
     *      XML to be validated. Must be an XML document or
     *      XML element and must not be null. For backwards compatibility,
     *      the results of attempting to validate anything other than
     *      a document or element are implementation-dependent.
     *      Implementations must either recognize and process the input
     *      or throw an IllegalArgumentException.
     *
     * @param result
     *      The <code>Result</code> object that receives (possibly augmented)
     *      XML. This parameter can be null if the caller is not interested
     *      in it.
     *
     *      Note that when a <code>DOMResult</code> is used,
     *      a validator might just pass the same DOM node from
     *      <code>DOMSource</code> to <code>DOMResult</code>
     *      (in which case <code>source.getNode()==result.getNode()</code>),
     *      it might copy the entire DOM tree, or it might alter the
     *      node given by the source.
     *
     * @throws IllegalArgumentException
     *      If the <code>Result</code> type doesn't match the
     *      <code>Source</code> type of if the <code>Source</code>
     *      is an XML artifact that the implementation cannot
     *      validate (for example, a processing instruction).
     * @throws SAXException
     *      If the <code>ErrorHandler</code> throws a
     *      <code>SAXException</code> or
     *      if a fatal error is found and the <code>ErrorHandler</code> returns
     *      normally.
     * @throws IOException
     *      If the validator is processing a
     *      <code>SAXSource</code> and the
     *      underlying {@link org.xml.sax.XMLReader} throws an
     *      <code>IOException</code>.
     * @throws NullPointerException
     *      If the <code>source</code> parameter is <code>null</code>.
     *
     * @see #validate(Source source)
     */
    public abstract void validate(Source source, Result result)
        throws SAXException, IOException;

    /**
     * Sets the {@link ErrorHandler} to receive errors encountered
     * during the <code>validate</code> method invocation.
     *
     * <p>
     * Error handler can be used to customize the error handling process
     * during a validation. When an {@link ErrorHandler} is set,
     * errors found during the validation will be first sent
     * to the {@link ErrorHandler}.
     *
     * <p>
     * The error handler can abort further validation immediately
     * by throwing {@link SAXException} from the handler. Or for example
     * it can print an error to the screen and try to continue the
     * validation by returning normally from the {@link ErrorHandler}
     *
     * <p>
     * If any {@link Throwable} is thrown from an {@link ErrorHandler},
     * the caller of the <code>validate</code> method will be thrown
     * the same {@link Throwable} object.
     *
     * <p>
     * {@link Validator} is not allowed to
     * throw {@link SAXException} without first reporting it to
     * {@link ErrorHandler}.
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
     * When a new {@link Validator} object is created, initially
     * this field is set to null.
     *
     * <p>
     *  设置{@link ErrorHandler}以接收在<code> validate </code>方法调用期间遇到的错误。
     * 
     * <p>
     *  错误处理程序可用于在验证期间自定义错误处理过程。当设置{@link ErrorHandler}时,验证期间发现的错误将首先发送到{@link ErrorHandler}。
     * 
     * <p>
     *  错误处理程序可以通过从处理程序抛出{@link SAXException}来立即中止进一步的验证。
     * 或者例如,它可以打印一个错误到屏幕,并尝试继续验证通常返回从{@link ErrorHandler}。
     * 
     * <p>
     *  如果从{@link ErrorHandler}抛出任何{@link Throwable},则<code> validate </code>方法的调用者将抛出相同的{@link Throwable}对象
     * 。
     * 
     * <p>
     * {@link Validator}不允许抛出{@link SAXException},而不先将其报告给{@link ErrorHandler}。
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
     *  创建新的{@link Validator}对象时,最初此字段设置为null。
     * 
     * 
     * @param   errorHandler
     *      A new error handler to be set. This parameter can be null.
     */
    public abstract void setErrorHandler(ErrorHandler errorHandler);

    /**
     * Gets the current {@link ErrorHandler} set to this {@link Validator}.
     *
     * <p>
     *  获取设置为此{@link Validator}的当前{@link ErrorHandler}。
     * 
     * 
     * @return
     *      This method returns the object that was last set through
     *      the {@link #setErrorHandler(ErrorHandler)} method, or null
     *      if that method has never been called since this {@link Validator}
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
     * {@link Validator} uses a {@link LSResourceResolver}
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
     * then the {@link Validator} will abort the parsing and
     * the caller of the <code>validate</code> method will receive
     * the same {@link RuntimeException}.
     *
     * <p>
     * When a new {@link Validator} object is created, initially
     * this field is set to null.
     *
     * <p>
     *  设置{@link LSResourceResolver}以在验证剧集中自定义资源解析。
     * 
     * <p>
     *  {@link Validator}在验证时需要定位外部资源时使用{@link LSResourceResolver},虽然"定位外部资源"是什么构成了每种模式语言。
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
     * 如果{@link LSResourceResolver}抛出{@link RuntimeException}(或其派生类的实例),则{@link Validator}将中止解析,并且<code> val
     * idate </code>方法的调用者将接收相同的{@link RuntimeException}。
     * 
     * <p>
     *  创建新的{@link Validator}对象时,最初此字段设置为null。
     * 
     * 
     * @param   resourceResolver
     *      A new resource resolver to be set. This parameter can be null.
     */
    public abstract void setResourceResolver(LSResourceResolver resourceResolver);

    /**
     * Gets the current {@link LSResourceResolver} set to this {@link Validator}.
     *
     * <p>
     *  获取设置为此{@link Validator}的当前{@link LSResourceResolver}。
     * 
     * 
     * @return
     *      This method returns the object that was last set through
     *      the {@link #setResourceResolver(LSResourceResolver)} method, or null
     *      if that method has never been called since this {@link Validator}
     *      has created.
     *
     * @see #setErrorHandler(ErrorHandler)
     */
    public abstract LSResourceResolver getResourceResolver();



    /**
     * Look up the value of a feature flag.
     *
     * <p>The feature name is any fully-qualified URI.  It is
     * possible for a {@link Validator} to recognize a feature name but
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
     *  <p>地图项名称是任何完全限定的URI。 {@link Validator}可能会识别功能名称,但暂时无法返回其值。一些特征值可能仅在特定上下文中可用,例如在验证之前,期间或之后。
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
     *   {@link Validator} recognizes the feature name but
     *   cannot determine its value at this time.
     * @throws NullPointerException
     *   When the name parameter is null.
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
     * Set the value of a feature flag.
     *
     * <p>
     * Feature can be used to control the way a {@link Validator}
     * parses schemas, although {@link Validator}s are not required
     * to recognize any specific feature names.</p>
     *
     * <p>The feature name is any fully-qualified URI.  It is
     * possible for a {@link Validator} to expose a feature value but
     * to be unable to change the current value.
     * Some feature values may be immutable or mutable only
     * in specific contexts, such as before, during, or after
     * a validation.</p>
     *
     * <p>
     *  设置功能标志的值。
     * 
     * <p>
     *  功能可用于控制{@link Validator}解析模式的方式,尽管{@link Validator}不需要识别任何特定的功能名称。</p>
     * 
     *  <p>地图项名称是任何完全限定的URI。 {@link Validator}可能会显示一个特征值,但无法更改当前值。某些特征值可能仅在特定上下文中是不可变的或可变的,例如在验证之前,期间或之后。
     * </p>。
     * 
     * 
     * @param name The feature name, which is a non-null fully-qualified URI.
     * @param value The requested value of the feature (true or false).
     *
     * @throws SAXNotRecognizedException If the feature
     *   value can't be assigned or retrieved.
     * @throws SAXNotSupportedException When the
     *   {@link Validator} recognizes the feature name but
     *   cannot set the requested value.
     * @throws NullPointerException
     *   When the name parameter is null.
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
     * possible for a {@link Validator} to recognize a property name but
     * to be unable to change the current value.
     * Some property values may be immutable or mutable only
     * in specific contexts, such as before, during, or after
     * a validation.</p>
     *
     * <p>
     * All implementations that implement JAXP 1.5 or newer are required to
     * support the {@link javax.xml.XMLConstants#ACCESS_EXTERNAL_DTD} and
     * {@link javax.xml.XMLConstants#ACCESS_EXTERNAL_SCHEMA} properties.
     * </p>
     * <ul>
     *   <li>
     *      <p>Access to external DTDs in source or Schema file is restricted to
     *      the protocols specified by the {@link javax.xml.XMLConstants#ACCESS_EXTERNAL_DTD}
     *      property.  If access is denied during validation due to the restriction
     *      of this property, {@link org.xml.sax.SAXException} will be thrown by the
     *      {@link #validate(Source)} method.</p>
     *
     *      <p>Access to external reference set by the schemaLocation attribute is
     *      restricted to the protocols specified by the
     *      {@link javax.xml.XMLConstants#ACCESS_EXTERNAL_SCHEMA} property.
     *      If access is denied during validation due to the restriction of this property,
     *      {@link org.xml.sax.SAXException} will be thrown by the
     *      {@link #validate(Source)} method.</p>
     *   </li>
     * </ul>
     *
     * <p>
     *  设置属性的值。
     * 
     * <p>属性名称是任何完全限定的URI。 {@link Validator}可能会识别属性名称,但无法更改当前值。某些属性值可能仅在特定上下文中是不可变的或可变的,例如在验证之前,期间或之后。</p>
     * 
     * <p>
     *  实现JAXP 1.5或更高版本的所有实现都需要支持{@link javax.xml.XMLConstants#ACCESS_EXTERNAL_DTD}和{@link javax.xml.XMLConstants#ACCESS_EXTERNAL_SCHEMA}
     * 属性。
     * </p>
     * <ul>
     * <li>
     *  <p>在源或模式文件中访问外部DTD仅限于{@link javax.xml.XMLConstants#ACCESS_EXTERNAL_DTD}属性指定的协议。
     * 如果由于此属性的限制而在验证期间拒绝访问,{@link org.xml.sax.SAXException}将由{@link #validate(Source)}方法抛出。</p>。
     * 
     *  <p>由schemaLocation属性设置的对外部引用的访问仅限于{@link javax.xml.XMLConstants#ACCESS_EXTERNAL_SCHEMA}属性指定的协议。
     * 
     * @param name The property name, which is a non-null fully-qualified URI.
     * @param object The requested value for the property.
     *
     * @throws SAXNotRecognizedException If the property
     *   value can't be assigned or retrieved.
     * @throws SAXNotSupportedException When the
     *   {@link Validator} recognizes the property name but
     *   cannot set the requested value.
     * @throws NullPointerException
     *   When the name parameter is null.
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
     * possible for a {@link Validator} to recognize a property name but
     * temporarily be unable to return its value.
     * Some property values may be available only in specific
     * contexts, such as before, during, or after a validation.</p>
     *
     * <p>{@link Validator}s are not required to recognize any specific
     * property names.</p>
     *
     * <p>Implementors are free (and encouraged) to invent their own properties,
     * using names built on their own URIs.</p>
     *
     * <p>
     * 如果由于此属性的限制而在验证期间拒绝访问,{@link org.xml.sax.SAXException}将由{@link #validate(Source)}方法抛出。</p>。
     * </li>
     * </ul>
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
     * @throws NullPointerException
     *   When the name parameter is null.
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
}
