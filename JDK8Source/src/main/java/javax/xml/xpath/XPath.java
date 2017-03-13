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

package javax.xml.xpath;

import org.xml.sax.InputSource;
import javax.xml.namespace.QName;
import javax.xml.namespace.NamespaceContext;

/**
 * <p><code>XPath</code> provides access to the XPath evaluation environment and expressions.</p>
 *
 * <a name="XPath-evaluation"/>
 * <table border="1" cellpadding="2">
 *   <thead>
 *     <tr>
 *       <th colspan="2">Evaluation of XPath Expressions.</th>
 *     </tr>
 *   </thead>
 *   <tbody>
 *     <tr>
 *       <td>context</td>
 *       <td>
 *         If a request is made to evaluate the expression in the absence
 * of a context item, an empty document node will be used for the context.
 * For the purposes of evaluating XPath expressions, a DocumentFragment
 * is treated like a Document node.
 *      </td>
 *    </tr>
 *    <tr>
 *      <td>variables</td>
 *      <td>
 *        If the expression contains a variable reference, its value will be found through the {@link XPathVariableResolver}
 *        set with {@link #setXPathVariableResolver(XPathVariableResolver resolver)}.
 *        An {@link XPathExpressionException} is raised if the variable resolver is undefined or
 *        the resolver returns <code>null</code> for the variable.
 *        The value of a variable must be immutable through the course of any single evaluation.</p>
 *      </td>
 *    </tr>
 *    <tr>
 *      <td>functions</td>
 *      <td>
 *        If the expression contains a function reference, the function will be found through the {@link XPathFunctionResolver}
 *        set with {@link #setXPathFunctionResolver(XPathFunctionResolver resolver)}.
 *        An {@link XPathExpressionException} is raised if the function resolver is undefined or
 *        the function resolver returns <code>null</code> for the function.</p>
 *      </td>
 *    </tr>
 *    <tr>
 *      <td>QNames</td>
 *      <td>
 *        QNames in the expression are resolved against the XPath namespace context
 *        set with {@link #setNamespaceContext(NamespaceContext nsContext)}.
 *      </td>
 *    </tr>
 *    <tr>
 *      <td>result</td>
 *      <td>
 *        This result of evaluating an expression is converted to an instance of the desired return type.
 *        Valid return types are defined in {@link XPathConstants}.
 *        Conversion to the return type follows XPath conversion rules.</p>
 *      </td>
 *    </tr>
 * </table>
 *
 * <p>An XPath object is not thread-safe and not reentrant.
 * In other words, it is the application's responsibility to make
 * sure that one {@link XPath} object is not used from
 * more than one thread at any given time, and while the <code>evaluate</code>
 * method is invoked, applications may not recursively call
 * the <code>evaluate</code> method.
 * <p>
 *
 * <p>
 *  <p> <code> XPath </code>提供对XPath评估环境和表达式的访问</p>
 * 
 * <a name="XPath-evaluation"/>
 * <table border="1" cellpadding="2">
 * <thead>
 * <tr>
 *  <th colspan ="2"> XPath表达式的评估</th>
 * </tr>
 * </thead>
 * <tbody>
 * <tr>
 *  <td>上下文</td>
 * <td>
 *  如果在没有上下文项的情况下请求求值表达式,则将使用空文档节点作为上下文。为了评估XPath表达式,将DocumentFragment视为Document节点
 * </td>
 * </tr>
 * <tr>
 *  <td>变量</td>
 * <td>
 * 如果表达式包含一个变量引用,它的值将通过{@link XPathVariableResolver}设置与{@link #setXPathVariableResolver(XPathVariableResolver解析器)}
 * 找到一个{@link XPathExpressionException}如果变量解析器未定义或解析器返回<code> null </code>变量的值在任何单个求值过程中必须是不可变的</p>。
 * </td>
 * </tr>
 * <tr>
 *  <td>函数</td>
 * <td>
 *  如果表达式包含函数引用,则通过{@link XPathFunctionResolver}集合使用{@link #setXPathFunctionResolver(XPathFunctionResolver resolver)}
 * 找到该函数。
 * 如果函数解析器未定义或函数解析器,则引发{@link XPathExpressionException}为函数</p>返回<code> null </code>。
 * </td>
 * </tr>
 * <tr>
 * <td> QNames </td>
 * <td>
 *  表达式中的QNames使用{@link #setNamespaceContext(NamespaceContext nsContext)}设置的XPath命名空间上下文进行解析
 * </td>
 * </tr>
 * <tr>
 *  <td> result </td>
 * <td>
 *  评估表达式的结果将转换为所需返回类型的实例。有效的返回类型在{@link XPathConstants}中定义。转换为返回类型遵循XPath转换规则</p>
 * </td>
 * </tr>
 * </table>
 * 
 *  <p> XPath对象不是线程安全的,不是可重入的换句话说,应用程序有责任确保在任何给定时间不会从多个线程使用一个{@link XPath}对象,而<code> evaluate </code>方法被
 * 调用,应用程序可能不会递归调用<code> evaluate </code>方法。
 * <p>
 * 
 * 
 * @author  <a href="Norman.Walsh@Sun.com">Norman Walsh</a>
 * @author  <a href="Jeff.Suttor@Sun.com">Jeff Suttor</a>
 * @see <a href="http://www.w3.org/TR/xpath">XML Path Language (XPath) Version 1.0</a>
 * @since 1.5
 */
public interface XPath {

        /**
         * <p>Reset this <code>XPath</code> to its original configuration.</p>
         *
         * <p><code>XPath</code> is reset to the same state as when it was created with
         * {@link XPathFactory#newXPath()}.
         * <code>reset()</code> is designed to allow the reuse of existing <code>XPath</code>s
         * thus saving resources associated with the creation of new <code>XPath</code>s.</p>
         *
         * <p>The reset <code>XPath</code> is not guaranteed to have the same {@link XPathFunctionResolver}, {@link XPathVariableResolver}
         * or {@link NamespaceContext} <code>Object</code>s, e.g. {@link Object#equals(Object obj)}.
         * It is guaranteed to have a functionally equal <code>XPathFunctionResolver</code>, <code>XPathVariableResolver</code>
         * and <code>NamespaceContext</code>.</p>
         * <p>
         * <p>将此<code> XPath </code>重置为其原始配置</p>
         * 
         *  <p> <code> XPath </code>重置为与使用{@link XPathFactory#newXPath()}创建时相同的状态<code> reset()</code>旨在允许重用现有<code>
         *  XPath </code>,从而节省与创建新<code> XPath </code>相关联的资源</p>。
         * 
         *  <p>重置<code> XPath </code>不保证具有相同的{@link XPathFunctionResolver},{@link XPathVariableResolver}或{@link NamespaceContext}
         *  <code> Object </code>,例如{@链接对象#等于(Object obj)}保证有一个功能相等的<code> XPathFunctionResolver </code>,<code> 
         * XPathVariableResolver </code>和<code> NamespaceContext </code>。
         * 
         */
        public void reset();

    /**
     * <p>Establish a variable resolver.</p>
     *
     * <p>A <code>NullPointerException</code> is thrown if <code>resolver</code> is <code>null</code>.</p>
     *
     * <p>
     *  <p>建立变量解析器</p>
     * 
     * <p>如果<code> resolver </code>为<code> null </code> </p>,则抛出<code> NullPointerException </code>
     * 
     * 
     * @param resolver Variable resolver.
     *
     *  @throws NullPointerException If <code>resolver</code> is <code>null</code>.
     */
    public void setXPathVariableResolver(XPathVariableResolver resolver);

    /**
       * <p>Return the current variable resolver.</p>
       *
       * <p><code>null</code> is returned in no variable resolver is in effect.</p>
       *
       * <p>
       *  <p>返回当前变量解析器</p>
       * 
       *  <p> <code> null </code>在无变量解析器时返回有效</p>
       * 
       * 
       * @return Current variable resolver.
       */
    public XPathVariableResolver getXPathVariableResolver();

    /**
       * <p>Establish a function resolver.</p>
       *
       * <p>A <code>NullPointerException</code> is thrown if <code>resolver</code> is <code>null</code>.</p>
       *
       * <p>
       *  <p>建立函数解析器</p>
       * 
       *  <p>如果<code> resolver </code>为<code> null </code> </p>,则抛出<code> NullPointerException </code>
       * 
       * 
       * @param resolver XPath function resolver.
       *
       * @throws NullPointerException If <code>resolver</code> is <code>null</code>.
       */
    public void setXPathFunctionResolver(XPathFunctionResolver resolver);

    /**
       * <p>Return the current function resolver.</p>
       *
       * <p><code>null</code> is returned in no function resolver is in effect.</p>
       *
       * <p>
       *  <p>返回当前函数解析器</p>
       * 
       *  <p> <code> null </code>在没有函数解析器生效</p>时返回
       * 
       * 
       * @return Current function resolver.
       */
    public XPathFunctionResolver getXPathFunctionResolver();

    /**
       * <p>Establish a namespace context.</p>
       *
       * <p>A <code>NullPointerException</code> is thrown if <code>nsContext</code> is <code>null</code>.</p>
       *
       * <p>
       *  <p>建立命名空间上下文</p>
       * 
       *  <p>如果<code> nsContext </code>为<code> null </code> </p>,则抛出<code> NullPointerException </code>
       * 
       * 
       * @param nsContext Namespace context to use.
       *
       * @throws NullPointerException If <code>nsContext</code> is <code>null</code>.
       */
    public void setNamespaceContext(NamespaceContext nsContext);

    /**
       * <p>Return the current namespace context.</p>
       *
       * <p><code>null</code> is returned in no namespace context is in effect.</p>
       *
       * <p>
       *  <p>返回当前命名空间上下文</p>
       * 
       *  <p> <code> null </code>在无命名空间上下文中返回有效</p>
       * 
       * 
       * @return Current Namespace context.
       */
    public NamespaceContext getNamespaceContext();

    /**
       * <p>Compile an XPath expression for later evaluation.</p>
       *
       * <p>If <code>expression</code> contains any {@link XPathFunction}s,
       * they must be available via the {@link XPathFunctionResolver}.
       * An {@link XPathExpressionException} will be thrown if the
       * <code>XPathFunction</code>
       * cannot be resovled with the <code>XPathFunctionResolver</code>.</p>
       *
       * <p>If <code>expression</code> contains any variables, the
       * {@link XPathVariableResolver} in effect
       * <strong>at compile time</strong> will be used to resolve them.</p>
       *
       * <p>If <code>expression</code> is <code>null</code>, a <code>NullPointerException</code> is thrown.</p>
       *
       * <p>
       * <p>编译XPath表达式以供以后评估</p>
       * 
       *  <p>如果<code> expression </code>包含任何{@link XPathFunction},它们必须通过{@link XPathFunctionResolver}可用。
       * 如果<code> XPathFunction </code >无法使用<code> XPathFunctionResolver </code> </p>重新移动。
       * 
       *  <p>如果<code> expression </code>包含任何变量,则会使用在编译时<strong> </strong>生效的{@link XPathVariableResolver}来解析它们
       * </p>。
       * 
       *  <p>如果<code>表达式</code>是<code> null </code>,则抛出<code> NullPointerException </code> </p>
       * 
       * 
       * @param expression The XPath expression.
       *
       * @return Compiled XPath expression.

       * @throws XPathExpressionException If <code>expression</code> cannot be compiled.
       * @throws NullPointerException If <code>expression</code> is <code>null</code>.
       */
    public XPathExpression compile(String expression)
        throws XPathExpressionException;

    /**
     * <p>Evaluate an <code>XPath</code> expression in the specified context and return the result as the specified type.</p>
     *
     * <p>See <a href="#XPath-evaluation">Evaluation of XPath Expressions</a> for context item evaluation,
     * variable, function and <code>QName</code> resolution and return type conversion.</p>
     *
     * <p>If <code>returnType</code> is not one of the types defined in {@link XPathConstants} (
     * {@link XPathConstants#NUMBER NUMBER},
     * {@link XPathConstants#STRING STRING},
     * {@link XPathConstants#BOOLEAN BOOLEAN},
     * {@link XPathConstants#NODE NODE} or
     * {@link XPathConstants#NODESET NODESET})
     * then an <code>IllegalArgumentException</code> is thrown.</p>
     *
     * <p>If a <code>null</code> value is provided for
     * <code>item</code>, an empty document will be used for the
     * context.
     * If <code>expression</code> or <code>returnType</code> is <code>null</code>, then a
     * <code>NullPointerException</code> is thrown.</p>
     *
     * <p>
     *  <p>在指定的上下文中评估<code> XPath </code>表达式,并返回指定类型的结果</p>
     * 
     * <p>查看<a href=\"#XPath-evaluation\">评估XPath表达式</a>的上下文项评估,变量,函数和<code> QName </code>解析和返回类型转换</p>
     * 
     *  <p>如果<code> returnType </code>不是{@link XPathConstants}({@link XPathConstants#NUMBER NUMBER},{@link XPathConstants#STRING STRING}
     * )中定义的类型之一,{@link XPathConstants#BOOLEAN BOOLEAN},{@link XPathConstants#NODE NODE}或{@link XPathConstants#NODESET NODESET}
     * ),则抛出<code> IllegalArgumentException </code> </p>。
     * 
     *  <p>如果为<code> item </code>提供<code> null </code>值,则会为上下文使用空文档如果<code> expression </code>或<code> / code
     * >是<code> null </code>,那么会抛出<code> NullPointerException </code> </p>。
     * 
     * 
     * @param expression The XPath expression.
     * @param item The starting context (a node, for example).
     * @param returnType The desired return type.
     *
     * @return Result of evaluating an XPath expression as an <code>Object</code> of <code>returnType</code>.
     *
     * @throws XPathExpressionException If <code>expression</code> cannot be evaluated.
     * @throws IllegalArgumentException If <code>returnType</code> is not one of the types defined in {@link XPathConstants}.
     * @throws NullPointerException If <code>expression</code> or <code>returnType</code> is <code>null</code>.
     */
    public Object evaluate(String expression, Object item, QName returnType)
        throws XPathExpressionException;

    /**
     * <p>Evaluate an XPath expression in the specified context and return the result as a <code>String</code>.</p>
     *
     * <p>This method calls {@link #evaluate(String expression, Object item, QName returnType)} with a <code>returnType</code> of
     * {@link XPathConstants#STRING}.</p>
     *
     * <p>See <a href="#XPath-evaluation">Evaluation of XPath Expressions</a> for context item evaluation,
     * variable, function and QName resolution and return type conversion.</p>
     *
     * <p>If a <code>null</code> value is provided for
     * <code>item</code>, an empty document will be used for the
     * context.
     * If <code>expression</code> is <code>null</code>, then a <code>NullPointerException</code> is thrown.</p>
     *
     * <p>
     * <p>在指定的上下文中评估XPath表达式,并将结果返回为<code> String </code> </p>
     * 
     *  <p>此方法使用{@link XPathConstants#STRING}的<code> returnType </code>调用{@link #evaluate(String expression,Object item,QName returnType)}
     *  </p>。
     * 
     *  <p>请参阅<a href=\"#XPath-evaluation\">评估XPath表达式</a>,了解上下文项评估,变量,函数和QName解析和返回类型转换</p>
     * 
     *  <p>如果为<code> item </code>提供了<code> null </code>值,则空文档将用于上下文如果<code> expression </code>是<code> null < / code>
     * ,那么会抛出<code> NullPointerException </code> </p>。
     * 
     * 
     * @param expression The XPath expression.
     * @param item The starting context (a node, for example).
     *
     * @return The <code>String</code> that is the result of evaluating the expression and
     *   converting the result to a <code>String</code>.
     *
     * @throws XPathExpressionException If <code>expression</code> cannot be evaluated.
     * @throws NullPointerException If <code>expression</code> is <code>null</code>.
     */
    public String evaluate(String expression, Object item)
        throws XPathExpressionException;

    /**
     * <p>Evaluate an XPath expression in the context of the specified <code>InputSource</code>
     * and return the result as the specified type.</p>
     *
     * <p>This method builds a data model for the {@link InputSource} and calls
     * {@link #evaluate(String expression, Object item, QName returnType)} on the resulting document object.</p>
     *
     * <p>See <a href="#XPath-evaluation">Evaluation of XPath Expressions</a> for context item evaluation,
     * variable, function and QName resolution and return type conversion.</p>
     *
     * <p>If <code>returnType</code> is not one of the types defined in {@link XPathConstants},
     * then an <code>IllegalArgumentException</code> is thrown.</p>
     *
     * <p>If <code>expression</code>, <code>source</code> or <code>returnType</code> is <code>null</code>,
     * then a <code>NullPointerException</code> is thrown.</p>
     *
     * <p>
     * <p>在指定的<code> InputSource </code>的上下文中评估XPath表达式,并返回指定类型的结果</p>
     * 
     *  <p>此方法在结果文档对象上构建{@link InputSource}的数据模型并调用{@link #evaluate(String expression,Object item,QName returnType)}
     * 。
     * </p>。
     * 
     *  <p>请参阅<a href=\"#XPath-evaluation\">评估XPath表达式</a>,了解上下文项评估,变量,函数和QName解析和返回类型转换</p>
     * 
     *  <p>如果<code> returnType </code>不是{@link XPathConstants}中定义的类型之一,则会抛出<code> IllegalArgumentException </code>
     *  </p>。
     * 
     *  <p>如果<code> expression </code>,<code> source </code>或<code> returnType </code>是<code> null </code>,那
     * 么<code> NullPointerException </code>被抛出</p>。
     * 
     * @param expression The XPath expression.
     * @param source The input source of the document to evaluate over.
     * @param returnType The desired return type.
     *
     * @return The <code>Object</code> that encapsulates the result of evaluating the expression.
     *
     * @throws XPathExpressionException If expression cannot be evaluated.
     * @throws IllegalArgumentException If <code>returnType</code> is not one of the types defined in {@link XPathConstants}.
     * @throws NullPointerException If <code>expression</code>, <code>source</code> or <code>returnType</code>
     *   is <code>null</code>.
     */
    public Object evaluate(
        String expression,
        InputSource source,
        QName returnType)
        throws XPathExpressionException;

    /**
     * <p>Evaluate an XPath expression in the context of the specified <code>InputSource</code>
     * and return the result as a <code>String</code>.</p>
     *
     * <p>This method calls {@link #evaluate(String expression, InputSource source, QName returnType)} with a
     * <code>returnType</code> of {@link XPathConstants#STRING}.</p>
     *
     * <p>See <a href="#XPath-evaluation">Evaluation of XPath Expressions</a> for context item evaluation,
     * variable, function and QName resolution and return type conversion.</p>
     *
     * <p>If <code>expression</code> or <code>source</code> is <code>null</code>,
     * then a <code>NullPointerException</code> is thrown.</p>
     *
     * <p>
     * 
     * 
     * @param expression The XPath expression.
     * @param source The <code>InputSource</code> of the document to evaluate over.
     *
     * @return The <code>String</code> that is the result of evaluating the expression and
     *   converting the result to a <code>String</code>.
     *
     * @throws XPathExpressionException If expression cannot be evaluated.
     * @throws NullPointerException If <code>expression</code> or <code>source</code> is <code>null</code>.
     */
    public String evaluate(String expression, InputSource source)
        throws XPathExpressionException;
}
