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

/**
 * <p><code>XPathExpression</code> provides access to compiled XPath expressions.</p>
 *
 * <a name="XPathExpression-evaluation"/>
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
 *        If the expression contains a variable reference, its value will be found through the {@link XPathVariableResolver}.
 *        An {@link XPathExpressionException} is raised if the variable resolver is undefined or
 *        the resolver returns <code>null</code> for the variable.
 *        The value of a variable must be immutable through the course of any single evaluation.</p>
 *      </td>
 *    </tr>
 *    <tr>
 *      <td>functions</td>
 *      <td>
 *        If the expression contains a function reference, the function will be found through the {@link XPathFunctionResolver}.
 *        An {@link XPathExpressionException} is raised if the function resolver is undefined or
 *        the function resolver returns <code>null</code> for the function.</p>
 *      </td>
 *    </tr>
 *    <tr>
 *      <td>QNames</td>
 *      <td>
 *        QNames in the expression are resolved against the XPath namespace context.
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
 * <p>An XPath expression is not thread-safe and not reentrant.
 * In other words, it is the application's responsibility to make
 * sure that one {@link XPathExpression} object is not used from
 * more than one thread at any given time, and while the <code>evaluate</code>
 * method is invoked, applications may not recursively call
 * the <code>evaluate</code> method.
 * <p>
 *
 * <p>
 *  <p> <code> XPathExpression </code>提供对已编译XPath表达式的访问。</p>
 * 
 * <a name="XPathExpression-evaluation"/>
 * <table border="1" cellpadding="2">
 * <thead>
 * <tr>
 *  <th colspan ="2"> XPath表达式的评估。</th>
 * </tr>
 * </thead>
 * <tbody>
 * <tr>
 *  <td>上下文</td>
 * <td>
 *  如果在没有上下文项的情况下请求求值表达式,则将使用空文档节点作为上下文。为了评估XPath表达式,将DocumentFragment视为Document节点。
 * </td>
 * </tr>
 * <tr>
 *  <td>变量</td>
 * <td>
 *  如果表达式包含一个变量引用,它的值将通过{@link XPathVariableResolver}找到。
 * 如果变量解析器未定义或解析器为变量返回<code> null </code>,则会引发{@link XPathExpressionException}。在任何单个评估过程中,变量的值必须是不可变的。
 * </p>。
 * </td>
 * </tr>
 * <tr>
 *  <td>函数</td>
 * <td>
 *  如果表达式包含函数引用,则将通过{@link XPathFunctionResolver}找到该函数。
 * 如果函数解析器未定义或函数解析器为函数返回<code> null </code>,则会引发{@link XPathExpressionException}。</p>。
 * </td>
 * </tr>
 * <tr>
 *  <td> QNames </td>
 * <td>
 *  表达式中的QNames是针对XPath命名空间上下文解析的。
 * </td>
 * </tr>
 * <tr>
 *  <td> result </td>
 * <td>
 * 评估表达式的结果将转换为所需返回类型的实例。有效的返回类型在{@link XPathConstants}中定义。转换为返回类型遵循XPath转换规则。</p>
 * </td>
 * </tr>
 * </table>
 * 
 *  <p> XPath表达式不是线程安全的,不可重入。
 * 换句话说,应用程序有责任确保一个{@link XPathExpression}对象在任何给定时间都不会从多个线程中使用,并且在调用<code> evaluate </code>方法时,应用程序可能不递归
 * 调用<code> evaluate </code>方法。
 *  <p> XPath表达式不是线程安全的,不可重入。
 * <p>
 * 
 * @author  <a href="mailto:Norman.Walsh@Sun.com">Norman Walsh</a>
 * @author  <a href="mailto:Jeff.Suttor@Sun.com">Jeff Suttor</a>
 * @see <a href="http://www.w3.org/TR/xpath#section-Expressions">XML Path Language (XPath) Version 1.0, Expressions</a>
 * @since 1.5
 */
public interface XPathExpression {

    /**
     * <p>Evaluate the compiled XPath expression in the specified context and return the result as the specified type.</p>
     *
     * <p>See <a href="#XPathExpression-evaluation">Evaluation of XPath Expressions</a> for context item evaluation,
     * variable, function and QName resolution and return type conversion.</p>
     *
     * <p>If <code>returnType</code> is not one of the types defined in {@link XPathConstants},
     * then an <code>IllegalArgumentException</code> is thrown.</p>
     *
     * <p>If a <code>null</code> value is provided for
     * <code>item</code>, an empty document will be used for the
     * context.
     * If <code>returnType</code> is <code>null</code>, then a <code>NullPointerException</code> is thrown.</p>
     *
     * <p>
     * 
     * 
     * @param item The starting context (a node, for example).
     * @param returnType The desired return type.
     *
     * @return The <code>Object</code> that is the result of evaluating the expression and converting the result to
     *   <code>returnType</code>.
     *
     * @throws XPathExpressionException If the expression cannot be evaluated.
     * @throws IllegalArgumentException If <code>returnType</code> is not one of the types defined in {@link XPathConstants}.
     * @throws NullPointerException If  <code>returnType</code> is <code>null</code>.
     */
    public Object evaluate(Object item, QName returnType)
        throws XPathExpressionException;

    /**
     * <p>Evaluate the compiled XPath expression in the specified context and return the result as a <code>String</code>.</p>
     *
     * <p>This method calls {@link #evaluate(Object item, QName returnType)} with a <code>returnType</code> of
     * {@link XPathConstants#STRING}.</p>
     *
     * <p>See <a href="#XPathExpression-evaluation">Evaluation of XPath Expressions</a> for context item evaluation,
     * variable, function and QName resolution and return type conversion.</p>
     *
     * <p>If a <code>null</code> value is provided for
     * <code>item</code>, an empty document will be used for the
     * context.
     *
     * <p>
     *  <p>在指定的上下文中评估编译的XPath表达式,并返回指定类型的结果。</p>
     * 
     *  <p>请参阅<a href="#XPathExpression-evaluation">评估XPath表达式</a>,了解上下文项评估,变量,函数和QName解析以及返回类型转换。</p>
     * 
     *  <p>如果<code> returnType </code>不是{@link XPathConstants}中定义的类型之一,则会抛出<code> IllegalArgumentException </code>
     * 。
     * </p>。
     * 
     *  <p>如果为<code> item </code>提供了<code> null </code>值,那么空文档将用于上下文。
     * 如果<code> returnType </code>是<code> null </code>,那么会抛出<code> NullPointerException </code>。</p>。
     * 
     * 
     * @param item The starting context (a node, for example).
     *
     * @return The <code>String</code> that is the result of evaluating the expression and converting the result to a
     *   <code>String</code>.
     *
     * @throws XPathExpressionException If the expression cannot be evaluated.
     */
    public String evaluate(Object item)
        throws XPathExpressionException;

    /**
     * <p>Evaluate the compiled XPath expression in the context of the specified <code>InputSource</code> and return the result as the
     * specified type.</p>
     *
     * <p>This method builds a data model for the {@link InputSource} and calls
     * {@link #evaluate(Object item, QName returnType)} on the resulting document object.</p>
     *
     * <p>See <a href="#XPathExpression-evaluation">Evaluation of XPath Expressions</a> for context item evaluation,
     * variable, function and QName resolution and return type conversion.</p>
     *
     * <p>If <code>returnType</code> is not one of the types defined in {@link XPathConstants},
     * then an <code>IllegalArgumentException</code> is thrown.</p>
     *
     * <p>If <code>source</code> or <code>returnType</code> is <code>null</code>,
     * then a <code>NullPointerException</code> is thrown.</p>
     *
     * <p>
     *  <p>在指定的上下文中评估编译的XPath表达式,并将结果返回为<code> String </code>。</p>
     * 
     * <p>此方法使用{@link XPathConstants#STRING}的<code> returnType </code>调用{@link #evaluate(Object item,QName returnType)}
     * 。
     * </p>。
     * 
     *  <p>请参阅<a href="#XPathExpression-evaluation">评估XPath表达式</a>,了解上下文项评估,变量,函数和QName解析以及返回类型转换。</p>
     * 
     *  <p>如果为<code> item </code>提供了<code> null </code>值,那么空文档将用于上下文。
     * 
     * 
     * @param source The <code>InputSource</code> of the document to evaluate over.
     * @param returnType The desired return type.
     *
     * @return The <code>Object</code> that is the result of evaluating the expression and converting the result to
     *   <code>returnType</code>.
     *
     * @throws XPathExpressionException If the expression cannot be evaluated.
     * @throws IllegalArgumentException If <code>returnType</code> is not one of the types defined in {@link XPathConstants}.
     * @throws NullPointerException If  <code>source</code> or <code>returnType</code> is <code>null</code>.
     */
    public Object evaluate(InputSource source, QName returnType)
        throws XPathExpressionException;

    /**
     * <p>Evaluate the compiled XPath expression in the context of the specified <code>InputSource</code> and return the result as a
     * <code>String</code>.</p>
     *
     * <p>This method calls {@link #evaluate(InputSource source, QName returnType)} with a <code>returnType</code> of
     * {@link XPathConstants#STRING}.</p>
     *
     * <p>See <a href="#XPathExpression-evaluation">Evaluation of XPath Expressions</a> for context item evaluation,
     * variable, function and QName resolution and return type conversion.</p>
     *
     * <p>If <code>source</code> is <code>null</code>, then a <code>NullPointerException</code> is thrown.</p>
     *
     * <p>
     *  <p>在指定的<code> InputSource </code>上下文中评估编译的XPath表达式,并返回指定类型的结果。</p>
     * 
     *  <p>此方法在结果文档对象上构建{@link InputSource}的数据模型并调用{@link #evaluate(Object item,QName returnType)}。</p>
     * 
     *  <p>请参阅<a href="#XPathExpression-evaluation">评估XPath表达式</a>,了解上下文项评估,变量,函数和QName解析以及返回类型转换。</p>
     * 
     *  <p>如果<code> returnType </code>不是{@link XPathConstants}中定义的类型之一,则会抛出<code> IllegalArgumentException </code>
     * 。
     * </p>。
     * 
     *  <p>如果<code> source </code>或<code> returnType </code>为<code> null </code>,则会抛出<code> NullPointerExcep
     * tion </code>。
     * </p>。
     * 
     * @param source The <code>InputSource</code> of the document to evaluate over.
     *
     * @return The <code>String</code> that is the result of evaluating the expression and converting the result to a
     *   <code>String</code>.
     *
     * @throws XPathExpressionException If the expression cannot be evaluated.
     * @throws NullPointerException If  <code>source</code> is <code>null</code>.
     */
    public String evaluate(InputSource source)
        throws XPathExpressionException;
}
