/***** Lobxxx Translate Finished ******/
/*
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

/*
 *
 *
 *
 *
 *
 * Copyright (c) 2002 World Wide Web Consortium,
 * (Massachusetts Institute of Technology, Institut National de
 * Recherche en Informatique et en Automatique, Keio University). All
 * Rights Reserved. This program is distributed under the W3C's Software
 * Intellectual Property License. This program is distributed in the
 * hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.
 * See W3C License http://www.w3.org/Consortium/Legal/ for more details.
 * <p>
 *  版权所有(c)2002万维网联盟,(马萨诸塞理工学院,庆应义藩大学信息自动化研究所)。版权所有。该程序根据W3C的软件知识产权许可证分发。
 * 这个程序是分发的,希望它将是有用的,但没有任何保证;甚至没有对适销性或适用于特定用途的隐含保证。有关详细信息,请参阅W3C许可证http://www.w3.org/Consortium/Legal/。
 * 
 */

package org.w3c.dom.xpath;


import org.w3c.dom.Node;
import org.w3c.dom.DOMException;

/**
 *  The evaluation of XPath expressions is provided by
 * <code>XPathEvaluator</code>. In a DOM implementation which supports the
 * XPath 3.0 feature, as described above, the <code>XPathEvaluator</code>
 * interface will be implemented on the same object which implements the
 * <code>Document</code> interface permitting it to be obtained by the usual
 * binding-specific method such as casting or by using the DOM Level 3
 * getInterface method. In this case the implementation obtained from the
 * Document supports the XPath DOM module and is compatible with the XPath
 * 1.0 specification.
 * <p>Evaluation of expressions with specialized extension functions or
 * variables may not work in all implementations and is, therefore, not
 * portable. <code>XPathEvaluator</code> implementations may be available
 * from other sources that could provide specific support for specialized
 * extension functions or variables as would be defined by other
 * specifications.
 * <p>See also the <a href='http://www.w3.org/2002/08/WD-DOM-Level-3-XPath-20020820'>Document Object Model (DOM) Level 3 XPath Specification</a>.
 * <p>
 * XPath表达式的评估由<code> XPathEvaluator </code>提供。
 * 在支持XPath 3.0特性的DOM实现中,如上所述,将在实现<code> Document </code>接口的同一对象上实现<code> XPathEvaluator </code>接口,允许它通过
 * 通常的绑定特定方法,如铸造或通过使用DOM Level 3 getInterface方法。
 * XPath表达式的评估由<code> XPathEvaluator </code>提供。在这种情况下,从Document获得的实现支持XPath DOM模块,并且与XPath 1.0规范兼容。
 *  <p>使用专用扩展函数或变量评估表达式在所有实现中可能不工作,因此不可移植。
 *  <code> XPathEvaluator </code>实现可以从其他源提供,这些源可以为其它规范定义的专用扩展函数或变量提供特定支持。
 *  <p>另请参见<a href='http://www.w3.org/2002/08/WD-DOM-Level-3-XPath-20020820'>文档对象模型(DOM)3级XPath规范< a>。
 * 
 */
public interface XPathEvaluator {
    /**
     * Creates a parsed XPath expression with resolved namespaces. This is
     * useful when an expression will be reused in an application since it
     * makes it possible to compile the expression string into a more
     * efficient internal form and preresolve all namespace prefixes which
     * occur within the expression.
     * <p>
     *  创建具有已解析命名空间的已分析XPath表达式。这在表达式将在应用程序中重用时很有用,因为它可以将表达式字符串编译为更有效的内部形式,并预先解析表达式中出现的所有命名空间前缀。
     * 
     * 
     * @param expression The XPath expression string to be parsed.
     * @param resolver The <code>resolver</code> permits translation of
     *   prefixes within the XPath expression into appropriate namespace URIs
     *   . If this is specified as <code>null</code>, any namespace prefix
     *   within the expression will result in <code>DOMException</code>
     *   being thrown with the code <code>NAMESPACE_ERR</code>.
     * @return The compiled form of the XPath expression.
     * @exception XPathException
     *   INVALID_EXPRESSION_ERR: Raised if the expression is not legal
     *   according to the rules of the <code>XPathEvaluator</code>i
     * @exception DOMException
     *   NAMESPACE_ERR: Raised if the expression contains namespace prefixes
     *   which cannot be resolved by the specified
     *   <code>XPathNSResolver</code>.
     */
    public XPathExpression createExpression(String expression,
                                            XPathNSResolver resolver)
                                            throws XPathException, DOMException;

    /**
     * Adapts any DOM node to resolve namespaces so that an XPath expression
     * can be easily evaluated relative to the context of the node where it
     * appeared within the document. This adapter works like the DOM Level 3
     * method <code>lookupNamespaceURI</code> on nodes in resolving the
     * namespaceURI from a given prefix using the current information
     * available in the node's hierarchy at the time lookupNamespaceURI is
     * called. also correctly resolving the implicit xml prefix.
     * <p>
     * 适应任何DOM节点以解析命名空间,以便可以轻松地相对于其在文档中出现的节点的上下文来评估XPath表达式。
     * 此适配器的工作方式类似于在解析来自给定前缀的namespaceURI的节点上的DOM 3级方法<code> lookupNamespaceURI </code>,使用在调用lookupNamespace
     * URI时节点层次结构中可用的当前信息。
     * 适应任何DOM节点以解析命名空间,以便可以轻松地相对于其在文档中出现的节点的上下文来评估XPath表达式。也正确解析了隐式xml前缀。
     * 
     * @param nodeResolver The node to be used as a context for namespace
     *   resolution.
     * @return <code>XPathNSResolver</code> which resolves namespaces with
     *   respect to the definitions in scope for a specified node.
     */
    public XPathNSResolver createNSResolver(Node nodeResolver);

    /**
     * Evaluates an XPath expression string and returns a result of the
     * specified type if possible.
     * <p>
     * 
     * 
     * @param expression The XPath expression string to be parsed and
     *   evaluated.
     * @param contextNode The <code>context</code> is context node for the
     *   evaluation of this XPath expression. If the XPathEvaluator was
     *   obtained by casting the <code>Document</code> then this must be
     *   owned by the same document and must be a <code>Document</code>,
     *   <code>Element</code>, <code>Attribute</code>, <code>Text</code>,
     *   <code>CDATASection</code>, <code>Comment</code>,
     *   <code>ProcessingInstruction</code>, or <code>XPathNamespace</code>
     *   node. If the context node is a <code>Text</code> or a
     *   <code>CDATASection</code>, then the context is interpreted as the
     *   whole logical text node as seen by XPath, unless the node is empty
     *   in which case it may not serve as the XPath context.
     * @param resolver The <code>resolver</code> permits translation of
     *   prefixes within the XPath expression into appropriate namespace URIs
     *   . If this is specified as <code>null</code>, any namespace prefix
     *   within the expression will result in <code>DOMException</code>
     *   being thrown with the code <code>NAMESPACE_ERR</code>.
     * @param type If a specific <code>type</code> is specified, then the
     *   result will be returned as the corresponding type.For XPath 1.0
     *   results, this must be one of the codes of the
     *   <code>XPathResult</code> interface.
     * @param result The <code>result</code> specifies a specific result
     *   object which may be reused and returned by this method. If this is
     *   specified as <code>null</code>or the implementation does not reuse
     *   the specified result, a new result object will be constructed and
     *   returned.For XPath 1.0 results, this object will be of type
     *   <code>XPathResult</code>.
     * @return The result of the evaluation of the XPath expression.For XPath
     *   1.0 results, this object will be of type <code>XPathResult</code>.
     * @exception XPathException
     *   INVALID_EXPRESSION_ERR: Raised if the expression is not legal
     *   according to the rules of the <code>XPathEvaluator</code>i
     *   <br>TYPE_ERR: Raised if the result cannot be converted to return the
     *   specified type.
     * @exception DOMException
     *   NAMESPACE_ERR: Raised if the expression contains namespace prefixes
     *   which cannot be resolved by the specified
     *   <code>XPathNSResolver</code>.
     *   <br>WRONG_DOCUMENT_ERR: The Node is from a document that is not
     *   supported by this <code>XPathEvaluator</code>.
     *   <br>NOT_SUPPORTED_ERR: The Node is not a type permitted as an XPath
     *   context node or the request type is not permitted by this
     *   <code>XPathEvaluator</code>.
     */
    public Object evaluate(String expression,
                           Node contextNode,
                           XPathNSResolver resolver,
                           short type,
                           Object result)
                           throws XPathException, DOMException;

}
