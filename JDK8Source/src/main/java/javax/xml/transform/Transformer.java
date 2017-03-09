/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2005, Oracle and/or its affiliates. All rights reserved.
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

import java.util.Properties;

/**
 * An instance of this abstract class can transform a
 * source tree into a result tree.
 *
 * <p>An instance of this class can be obtained with the
 * {@link TransformerFactory#newTransformer TransformerFactory.newTransformer}
 * method. This instance may then be used to process XML from a
 * variety of sources and write the transformation output to a
 * variety of sinks.</p>
 *
 * <p>An object of this class may not be used in multiple threads
 * running concurrently.  Different Transformers may be used
 * concurrently by different threads.</p>
 *
 * <p>A <code>Transformer</code> may be used multiple times.  Parameters and
 * output properties are preserved across transformations.</p>
 *
 * <p>
 *  此抽象类的实例可以将源树转换为结果树。
 * 
 *  <p>此类的实例可以使用{@link TransformerFactory#newTransformer TransformerFactory.newTransformer}方法获取。
 * 然后,可以使用该实例处理来自各种源的XML,并将变换输出写入各种汇。</p>。
 * 
 *  <p>此类的对象可能不会同时在多个线程中使用。不同的变压器可以由不同的线程同时使用。</p>
 * 
 *  <p> <code> Transformer </code>可能会多次使用。参数和输出属性在转换中保留。</p>
 * 
 * 
 * @author <a href="Jeff.Suttor@Sun.com">Jeff Suttor</a>
 */
public abstract class Transformer {

    /**
     * Default constructor is protected on purpose.
     * <p>
     *  默认构造函数是有目的的保护。
     * 
     */
    protected Transformer() { }

        /**
         * <p>Reset this <code>Transformer</code> to its original configuration.</p>
         *
         * <p><code>Transformer</code> is reset to the same state as when it was created with
         * {@link TransformerFactory#newTransformer()},
         * {@link TransformerFactory#newTransformer(Source source)} or
         * {@link Templates#newTransformer()}.
         * <code>reset()</code> is designed to allow the reuse of existing <code>Transformer</code>s
         * thus saving resources associated with the creation of new <code>Transformer</code>s.</p>
         *
         * <p>The reset <code>Transformer</code> is not guaranteed to have the same {@link URIResolver}
         * or {@link ErrorListener} <code>Object</code>s, e.g. {@link Object#equals(Object obj)}.
         * It is guaranteed to have a functionally equal <code>URIResolver</code>
         * and <code>ErrorListener</code>.</p>
     *
     * <p>
     *  <p>将此<code> Transformer </code>重置为原始配置。</p>
     * 
     *  <p> <code> Transformer </code>重置为与使用{@link TransformerFactory#newTransformer()},{@link TransformerFactory#newTransformer(Source source)}
     * 或{@link Templates# newTransformer()}。
     *  <code> reset()</code>旨在允许重用现有的<code> Transformer </code>,从而节省与创建新<code> Transformer </code>相关联的资源。
     * 
     * <p>重置<code> Transformer </code>不保证具有相同的{@link URIResolver}或{@link ErrorListener} <code> Object </code>
     * ,例如{@link Object#equals(Object obj)}。
     * 它保证有一个功能相等的<code> URIResolver </code>和<code> ErrorListener </code>。</p>。
     * 
     * 
     * @throws UnsupportedOperationException When implementation does not
     *   override this method.
         *
         * @since 1.5
         */
        public void reset() {

                // implementors should override this method
                throw new UnsupportedOperationException(
                        "This Transformer, \"" + this.getClass().getName() + "\", does not support the reset functionality."
                        + "  Specification \"" + this.getClass().getPackage().getSpecificationTitle() + "\""
                        + " version \"" + this.getClass().getPackage().getSpecificationVersion() + "\""
                        );
        }

    /**
     * <p>Transform the XML <code>Source</code> to a <code>Result</code>.
     * Specific transformation behavior is determined by the settings of the
     * <code>TransformerFactory</code> in effect when the
     * <code>Transformer</code> was instantiated and any modifications made to
     * the <code>Transformer</code> instance.</p>
     *
     * <p>An empty <code>Source</code> is represented as an empty document
     * as constructed by {@link javax.xml.parsers.DocumentBuilder#newDocument()}.
     * The result of transforming an empty <code>Source</code> depends on
     * the transformation behavior; it is not always an empty
     * <code>Result</code>.</p>
     *
     * <p>
     *  <p>将XML <code> Source </code>转换为<code> Result </code>。
     * 当<code> Transformer </code>被实例化并对<code> Transformer </code>实例进行任何修改时,具体的变换行为由<code> TransformerFactor
     * y </code> p>。
     *  <p>将XML <code> Source </code>转换为<code> Result </code>。
     * 
     *  <p>一个空的<code> Source </code>表示为一个空文档,由{@link javax.xml.parsers.DocumentBuilder#newDocument()}构造。
     * 变换空的<code> Source </code>的结果取决于变换行为;它不总是空的<code> Result </code>。</p>。
     * 
     * 
     * @param xmlSource The XML input to transform.
     * @param outputTarget The <code>Result</code> of transforming the
     *   <code>xmlSource</code>.
     *
     * @throws TransformerException If an unrecoverable error occurs
     *   during the course of the transformation.
     */
    public abstract void transform(Source xmlSource, Result outputTarget)
        throws TransformerException;

    /**
     * Add a parameter for the transformation.
     *
     * <p>Pass a qualified name as a two-part string, the namespace URI
     * enclosed in curly braces ({}), followed by the local name. If the
     * name has a null URL, the String only contain the local name. An
     * application can safely check for a non-null URI by testing to see if the
     * first character of the name is a '{' character.</p>
     * <p>For example, if a URI and local name were obtained from an element
     * defined with &lt;xyz:foo
     * xmlns:xyz="http://xyz.foo.com/yada/baz.html"/&gt;,
     * then the qualified name would be "{http://xyz.foo.com/yada/baz.html}foo".
     * Note that no prefix is used.</p>
     *
     * <p>
     *  为转换添加参数。
     * 
     *  <p>将限定名称作为两部分字符串传递,以大括号({})括起的命名空间URI,后跟本地名称。如果名称具有空URL,则String仅包含本地名称。
     * 应用程序可以通过测试查看名称的第一个字符是否为"{'字符。</p> <p>来安全地检查非空URI。
     * 例如,如果从元素获取URI和本地名称用&lt; xyz：foo xmlns：xyz ="http://xyz.foo.com/yada/baz.html"/&gt;定义,那么限定名称将是"{http://xyz.foo.com/ yada / baz.html}
     *  foo"。
     * 应用程序可以通过测试查看名称的第一个字符是否为"{'字符。</p> <p>来安全地检查非空URI。请注意,不使用前缀。</p>。
     * 
     * 
     * @param name The name of the parameter, which may begin with a
     * namespace URI in curly braces ({}).
     * @param value The value object.  This can be any valid Java object. It is
     * up to the processor to provide the proper object coersion or to simply
     * pass the object on for use in an extension.
     *
     * @throws NullPointerException If value is null.
     */
     public abstract void setParameter(String name, Object value);

    /**
     * Get a parameter that was explicitly set with setParameter.
     *
     * <p>This method does not return a default parameter value, which
     * cannot be determined until the node context is evaluated during
     * the transformation process.
     *
     * <p>
     * 获取使用setParameter显式设置的参数。
     * 
     *  <p>此方法不返回默认参数值,只有在转换过程中评估节点上下文时,才能确定此参数值。
     * 
     * 
     * @param name of <code>Object</code> to get
     *
     * @return A parameter that has been set with setParameter.
     */
    public abstract Object getParameter(String name);

    /**
     * <p>Set a list of parameters.</p>
     *
     * <p>Note that the list of parameters is specified as a
     * <code>Properties</code> <code>Object</code> which limits the parameter
     * values to <code>String</code>s.  Multiple calls to
     * {@link #setParameter(String name, Object value)} should be used when the
     * desired values are non-<code>String</code> <code>Object</code>s.
     * The parameter names should conform as specified in
     * {@link #setParameter(String name, Object value)}.
     * An <code>IllegalArgumentException</code> is thrown if any names do not
     * conform.</p>
     *
     * <p>New parameters in the list are added to any existing parameters.
     * If the name of a new parameter is equal to the name of an existing
     * parameter as determined by {@link java.lang.Object#equals(Object obj)},
     *  the existing parameter is set to the new value.</p>
     *
     * <p>
     *  <p>设置参数列表。</p>
     * 
     *  <p>请注意,参数列表被指定为<code> Properties </code> <code> Object </code>,将参数值限制为<code> String </code>。
     * 当所需值为非<code> String </code> <code> Object </code>时,应使用对{@link #setParameter(String name,Object value)}
     * 的多次调用。
     *  <p>请注意,参数列表被指定为<code> Properties </code> <code> Object </code>,将参数值限制为<code> String </code>。
     * 参数名称应符合{@link #setParameter(String name,Object value)}中的指定。
     * 如果任何名称不符合,则抛出<code> IllegalArgumentException </code>。</p>。
     * 
     *  <p>列表中的新参数将添加到任何现有参数。如果新参数的名称等于由{@link java.lang.Object#equals(Object obj)}确定的现有参数的名称,则现有参数将设置为新值。
     * </p>。
     * 
     * 
     * @param params Parameters to set.
     *
     * @throws IllegalArgumentException If any parameter names do not conform
     *   to the naming rules.
     */

    /**
     * Clear all parameters set with setParameter.
     * <p>
     *  清除使用setParameter设置的所有参数。
     * 
     */
    public abstract void clearParameters();

    /**
     * Set an object that will be used to resolve URIs used in
     * document().
     *
     * <p>If the resolver argument is null, the URIResolver value will
     * be cleared and the transformer will no longer have a resolver.</p>
     *
     * <p>
     *  设置将用于解析document()中使用的URI的对象。
     * 
     *  <p>如果resolver参数为null,URIResolver值将被清除,变压器将不再有解析器。</p>
     * 
     * 
     * @param resolver An object that implements the URIResolver interface,
     * or null.
     */
    public abstract void setURIResolver(URIResolver resolver);

    /**
     * Get an object that will be used to resolve URIs used in
     * document().
     *
     * <p>
     *  获取将用于解析在document()中使用的URI的对象。
     * 
     * 
     * @return An object that implements the URIResolver interface,
     * or null.
     */
    public abstract URIResolver getURIResolver();

    /**
     * Set the output properties for the transformation.  These
     * properties will override properties set in the Templates
     * with xsl:output.
     *
     * <p>If argument to this function is null, any properties
     * previously set are removed, and the value will revert to the value
     * defined in the templates object.</p>
     *
     * <p>Pass a qualified property key name as a two-part string, the namespace
     * URI enclosed in curly braces ({}), followed by the local name. If the
     * name has a null URL, the String only contain the local name. An
     * application can safely check for a non-null URI by testing to see if the
     * first character of the name is a '{' character.</p>
     * <p>For example, if a URI and local name were obtained from an element
     * defined with &lt;xyz:foo
     * xmlns:xyz="http://xyz.foo.com/yada/baz.html"/&gt;,
     * then the qualified name would be "{http://xyz.foo.com/yada/baz.html}foo".
     * Note that no prefix is used.</p>
     * An <code>IllegalArgumentException</code> is thrown  if any of the
     * argument keys are not recognized and are not namespace qualified.
     *
     * <p>
     *  设置转换的输出属性。这些属性将覆盖使用xsl：output模板中设置的属性。
     * 
     * <p>如果此函数的参数为​​null,则先前设置的任何属性都将被删除,并且该值将还原为templates对象中定义的值。</p>
     * 
     *  <p>将限定属性键值名称作为两部分字符串传递,以大括号({})括起的命名空间URI,后跟本地名称。如果名称具有空URL,则String仅包含本地名称。
     * 应用程序可以通过测试查看名称的第一个字符是否为"{'字符。</p> <p>来安全地检查非空URI。
     * 例如,如果从元素获取URI和本地名称用&lt; xyz：foo xmlns：xyz ="http://xyz.foo.com/yada/baz.html"/&gt;定义,那么限定名称将是"{http://xyz.foo.com/ yada / baz.html}
     *  foo"。
     * 应用程序可以通过测试查看名称的第一个字符是否为"{'字符。</p> <p>来安全地检查非空URI。请注意,不使用任何前缀。
     * </p>如果任何参数键未被识别并且未命名空间限定,则抛出<code> IllegalArgumentException </code>。
     * 
     * 
     * @param oformat A set of output properties that will be
     *   used to override any of the same properties in affect
     *   for the transformation.
     *
     * @throws IllegalArgumentException When keys are not recognized and
     *   are not namespace qualified.
     *
     * @see javax.xml.transform.OutputKeys
     * @see java.util.Properties
     *
     */
    public abstract void setOutputProperties(Properties oformat);

    /**
     * <p>Get a copy of the output properties for the transformation.</p>
     *
     * <p>The properties returned should contain properties set by the user,
     * and properties set by the stylesheet, and these properties
     * are "defaulted" by default properties specified by
     * <a href="http://www.w3.org/TR/xslt#output">section 16 of the
     * XSL Transformations (XSLT) W3C Recommendation</a>.  The properties that
     * were specifically set by the user or the stylesheet should be in the base
     * Properties list, while the XSLT default properties that were not
     * specifically set should be the default Properties list.  Thus,
     * getOutputProperties().getProperty(String key) will obtain any
     * property in that was set by {@link #setOutputProperty},
     * {@link #setOutputProperties}, in the stylesheet, <em>or</em> the default
     * properties, while
     * getOutputProperties().get(String key) will only retrieve properties
     * that were explicitly set by {@link #setOutputProperty},
     * {@link #setOutputProperties}, or in the stylesheet.</p>
     *
     * <p>Note that mutation of the Properties object returned will not
     * effect the properties that the transformer contains.</p>
     *
     * <p>If any of the argument keys are not recognized and are not
     * namespace qualified, the property will be ignored and not returned.
     * In other words the behaviour is not orthogonal with
     * {@link #setOutputProperties setOutputProperties}.</p>
     *
     * <p>
     *  <p>获取转换的输出属性的副本。</p>
     * 
     * <p>返回的属性应包含用户设置的属性和样式表设置的属性,并且这些属性由<a href ="http://www.w3.org/TR/默认属性"默认" xslt#output"> XSL Transfor
     * mations(XSLT)W3C Recommendation </a>的第16节。
     * 由用户或样式表专门设置的属性应在基本属性列表中,而未特别设置的XSLT默认属性应为默认属性列表。因此,getOutputProperties()。
     * getProperty(String key)将获取由{@link #setOutputProperty},{@link #setOutputProperties},样式表中的<em>或</em>默认属
     * 性设置的任何属性,而getOutputProperties()。
     * 由用户或样式表专门设置的属性应在基本属性列表中,而未特别设置的XSLT默认属性应为默认属性列表。因此,getOutputProperties()。
     * get(String key)只会检索由{@link #setOutputProperty},{@link #setOutputProperties}或样式表显式设置的属性。</p>。
     * 
     *  <p>请注意,返回的Properties对象的变异不会影响变压器包含的属性。</p>
     * 
     *  <p>如果任何参数键未被识别,并且不是命名空间限定的,则该属性将被忽略并且不返回。
     * 换句话说,行为不与{@link #setOutputProperties setOutputProperties}正交。</p>。
     * 
     * 
     * @return A copy of the set of output properties in effect for
     *   the next transformation.
     *
     * @see javax.xml.transform.OutputKeys
     * @see java.util.Properties
     * @see <a href="http://www.w3.org/TR/xslt#output">
     *   XSL Transformations (XSLT) Version 1.0</a>
     */
    public abstract Properties getOutputProperties();

    /**
     * Set an output property that will be in effect for the
     * transformation.
     *
     * <p>Pass a qualified property name as a two-part string, the namespace URI
     * enclosed in curly braces ({}), followed by the local name. If the
     * name has a null URL, the String only contain the local name. An
     * application can safely check for a non-null URI by testing to see if the
     * first character of the name is a '{' character.</p>
     * <p>For example, if a URI and local name were obtained from an element
     * defined with &lt;xyz:foo
     * xmlns:xyz="http://xyz.foo.com/yada/baz.html"/&gt;,
     * then the qualified name would be "{http://xyz.foo.com/yada/baz.html}foo".
     * Note that no prefix is used.</p>
     *
     * <p>The Properties object that was passed to {@link #setOutputProperties}
     * won't be effected by calling this method.</p>
     *
     * <p>
     *  设置将对转换有效的输出属性。
     * 
     * <p>将限定属性名称作为两部分字符串传递,以大括号({})括起的命名空间URI,后跟本地名称。如果名称具有空URL,则String仅包含本地名称。
     * 应用程序可以通过测试查看名称的第一个字符是否为"{'字符。</p> <p>来安全地检查非空URI。
     * 例如,如果从元素获取URI和本地名称用&lt; xyz：foo xmlns：xyz ="http://xyz.foo.com/yada/baz.html"/&gt;定义,那么限定名称将是"{http://xyz.foo.com/ yada / baz.html}
     *  foo"。
     * 应用程序可以通过测试查看名称的第一个字符是否为"{'字符。</p> <p>来安全地检查非空URI。请注意,不使用前缀。</p>。
     * 
     *  <p>传递给{@link #setOutputProperties}的Properties对象不会通过调用此方法实现。</p>
     * 
     * 
     * @param name A non-null String that specifies an output
     * property name, which may be namespace qualified.
     * @param value The non-null string value of the output property.
     *
     * @throws IllegalArgumentException If the property is not supported, and is
     * not qualified with a namespace.
     *
     * @see javax.xml.transform.OutputKeys
     */
    public abstract void setOutputProperty(String name, String value)
        throws IllegalArgumentException;

    /**
     * <p>Get an output property that is in effect for the transformer.</p>
     *
     * <p>If a property has been set using {@link #setOutputProperty},
     * that value will be returned. Otherwise, if a property is explicitly
     * specified in the stylesheet, that value will be returned. If
     * the value of the property has been defaulted, that is, if no
     * value has been set explicitly either with {@link #setOutputProperty} or
     * in the stylesheet, the result may vary depending on
     * implementation and input stylesheet.</p>
     *
     * <p>
     *  <p>获取对变压器有效的输出属性。</p>
     * 
     *  <p>如果使用{@link #setOutputProperty}设置了属性,则会返回该值。否则,如果在样式表中显式指定了属性,则将返回该值。
     * 如果属性的值已默认,即如果没有使用{@link #setOutputProperty}或样式表中显式设置的值,则结果可能会因实现和输入样式表而异。</p>。
     * 
     * 
     * @param name A non-null String that specifies an output
     * property name, which may be namespace qualified.
     *
     * @return The string value of the output property, or null
     * if no property was found.
     *
     * @throws IllegalArgumentException If the property is not supported.
     *
     * @see javax.xml.transform.OutputKeys
     */
    public abstract String getOutputProperty(String name)
        throws IllegalArgumentException;

    /**
     * Set the error event listener in effect for the transformation.
     *
     * <p>
     *  设置对转换有效的错误事件侦听器。
     * 
     * 
     * @param listener The new error listener.
     *
     * @throws IllegalArgumentException if listener is null.
     */
    public abstract void setErrorListener(ErrorListener listener)
        throws IllegalArgumentException;

    /**
     * Get the error event handler in effect for the transformation.
     * Implementations must provide a default error listener.
     *
     * <p>
     *  获取对转换有效的错误事件处理程序。实现必须提供默认错误侦听器。
     * 
     * @return The current error handler, which should never be null.
     */
    public abstract ErrorListener getErrorListener();
}
