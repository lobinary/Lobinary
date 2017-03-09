/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2002-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  版权所有2002-2005 Apache软件基金会
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
/*
 * $Id: XPathEvaluatorImpl.java,v 1.2.4.1 2005/09/10 04:04:07 jeffsuttor Exp $
 * <p>
 *  $ Id：XPathEvaluatorImpl.java,v 1.2.4.1 2005/09/10 04:04:07 jeffsuttor Exp $
 * 
 */

package com.sun.org.apache.xpath.internal.domapi;

import javax.xml.transform.TransformerException;

import com.sun.org.apache.xml.internal.utils.PrefixResolver;
import com.sun.org.apache.xpath.internal.XPath;
import com.sun.org.apache.xpath.internal.res.XPATHErrorResources;
import com.sun.org.apache.xpath.internal.res.XPATHMessages;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.xpath.XPathEvaluator;
import org.w3c.dom.xpath.XPathException;
import org.w3c.dom.xpath.XPathExpression;
import org.w3c.dom.xpath.XPathNSResolver;

/**
 *
 * The class provides an implementation of XPathEvaluator according
 * to the DOM L3 XPath Specification, Working Group Note 26 February 2004.
 *
 * <p>See also the <a href='http://www.w3.org/TR/2004/NOTE-DOM-Level-3-XPath-20040226'>Document Object Model (DOM) Level 3 XPath Specification</a>.</p>
 *
 * </p>The evaluation of XPath expressions is provided by
 * <code>XPathEvaluator</code>, which will provide evaluation of XPath 1.0
 * expressions with no specialized extension functions or variables. It is
 * expected that the <code>XPathEvaluator</code> interface will be
 * implemented on the same object which implements the <code>Document</code>
 * interface in an implementation which supports the XPath DOM module.
 * <code>XPathEvaluator</code> implementations may be available from other
 * sources that may provide support for special extension functions or
 * variables which are not defined in this specification.</p>
 *
 * <p>
 *  该类根据DOM L3 XPath规范,工作组注2004年2月26日提供了XPathEvaluator的实现。
 * 
 *  <p>另请参阅<a href='http://www.w3.org/TR/2004/NOTE-DOM-Level-3-XPath-20040226'>文档对象模型(DOM)3级XPath规范< a>。
 * </p>。
 * 
 * </P>的XPath表达式的评价通过<代码> XPathEvaluator </代码>提供,这将提供的XPath 1.0表达式的评估没有专门扩展函数或变量。
 * 期望在支持XPath DOM模块的实现中,在实现<code> Document </code>接口的同一对象上实现<code> XPathEvaluator </code>接口。
 *  <code> XPathEvaluator </code>实现可以从可以为特殊扩展函数或未在本规范中定义的变量提供支持的其他源提供。</p>。
 * 
 * 
 * @see org.w3c.dom.xpath.XPathEvaluator
 *
 * @xsl.usage internal
 */
public final class XPathEvaluatorImpl implements XPathEvaluator {

        /**
         * This prefix resolver is created whenever null is passed to the
         * evaluate method.  Its purpose is to satisfy the DOM L3 XPath API
         * requirement that if a null prefix resolver is used, an exception
         * should only be thrown when an attempt is made to resolve a prefix.
         * <p>
         *  每当将null传递给evaluate方法时,都会创建此前缀解析器。其目的是满足DOM L3 XPath API要求,如果使用空前缀解析器,则只有在尝试解析前缀时才应抛出异常。
         * 
         */
        private class DummyPrefixResolver implements PrefixResolver {

                /**
                 * Constructor for DummyPrefixResolver.
                 * <p>
                 *  DummyPrefixResolver的构造函数。
                 * 
                 */
                DummyPrefixResolver() {}

                /**
                /* <p>
                /* 
                 * @exception DOMException
         *   NAMESPACE_ERR: Always throws this exceptionn
                 *
                 * @see com.sun.org.apache.xml.internal.utils.PrefixResolver#getNamespaceForPrefix(String, Node)
                 */
                public String getNamespaceForPrefix(String prefix, Node context) {
            String fmsg = XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_NULL_RESOLVER, null);
            throw new DOMException(DOMException.NAMESPACE_ERR, fmsg);   // Unable to resolve prefix with null prefix resolver.
                }

                /**
                /* <p>
                /* 
                 * @exception DOMException
         *   NAMESPACE_ERR: Always throws this exceptionn
         *
                 * @see com.sun.org.apache.xml.internal.utils.PrefixResolver#getNamespaceForPrefix(String)
                 */
                public String getNamespaceForPrefix(String prefix) {
                        return getNamespaceForPrefix(prefix,null);
                }

                /**
                /* <p>
                /* 
                 * @see com.sun.org.apache.xml.internal.utils.PrefixResolver#handlesNullPrefixes()
                 */
                public boolean handlesNullPrefixes() {
                        return false;
                }

                /**
                /* <p>
                /* 
                 * @see com.sun.org.apache.xml.internal.utils.PrefixResolver#getBaseIdentifier()
                 */
                public String getBaseIdentifier() {
                        return null;
                }

        }

    /**
     * The document to be searched to parallel the case where the XPathEvaluator
     * is obtained by casting a Document.
     * <p>
     *  要并行搜索的文档,其中XPathEvaluator是通过转换Document获得的。
     * 
     */
    private final Document m_doc;

    /**
     * Constructor for XPathEvaluatorImpl.
     *
     * <p>
     *  XPathEvaluatorImpl的构造函数。
     * 
     * 
     * @param doc The document to be searched, to parallel the case where''
     *            the XPathEvaluator is obtained by casting the document.
     */
    public XPathEvaluatorImpl(Document doc) {
        m_doc = doc;
    }

    /**
     * Constructor in the case that the XPath expression can be evaluated
     * without needing an XML document at all.
     *
     * <p>
     *  在可以评估XPath表达式而无需任何XML文档的情况下的构造方法。
     * 
     */
    public XPathEvaluatorImpl() {
            m_doc = null;
    }

        /**
     * Creates a parsed XPath expression with resolved namespaces. This is
     * useful when an expression will be reused in an application since it
     * makes it possible to compile the expression string into a more
     * efficient internal form and preresolve all namespace prefixes which
     * occur within the expression.
     *
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
     *
         * @see org.w3c.dom.xpath.XPathEvaluator#createExpression(String, XPathNSResolver)
         */
        public XPathExpression createExpression(
                String expression,
                XPathNSResolver resolver)
                throws XPathException, DOMException {

                try {

                        // If the resolver is null, create a dummy prefix resolver
                        XPath xpath =  new XPath(expression,null,
                             ((null == resolver) ? new DummyPrefixResolver() : ((PrefixResolver)resolver)),
                              XPath.SELECT);

            return new XPathExpressionImpl(xpath, m_doc);

                } catch (TransformerException e) {
                        // Need to pass back exception code DOMException.NAMESPACE_ERR also.
                        // Error found in DOM Level 3 XPath Test Suite.
                        if(e instanceof XPathStylesheetDOM3Exception)
                                throw new DOMException(DOMException.NAMESPACE_ERR,e.getMessageAndLocation());
                        else
                                throw new XPathException(XPathException.INVALID_EXPRESSION_ERR,e.getMessageAndLocation());

                }
        }

        /**
     * Adapts any DOM node to resolve namespaces so that an XPath expression
     * can be easily evaluated relative to the context of the node where it
     * appeared within the document. This adapter works like the DOM Level 3
     * method <code>lookupNamespaceURI</code> on nodes in resolving the
     * namespaceURI from a given prefix using the current information available
     * in the node's hierarchy at the time lookupNamespaceURI is called, also
     * correctly resolving the implicit xml prefix.
     *
     * <p>
     * 适应任何DOM节点以解析命名空间,以便可以轻松地相对于其在文档中出现的节点的上下文来评估XPath表达式。
     * 此适配器的工作方式类似DOM 3级方法<code> lookupNamespaceURI </code>在解析命名空间uri从给定的前缀使用在调用lookupNamespaceURI时节点的层次结构中可
     * 用的当前信息的节点上,也正确解析隐式xml前缀。
     * 适应任何DOM节点以解析命名空间,以便可以轻松地相对于其在文档中出现的节点的上下文来评估XPath表达式。
     * 
     * @param nodeResolver The node to be used as a context for namespace
     *   resolution.
     * @return <code>XPathNSResolver</code> which resolves namespaces with
     *   respect to the definitions in scope for a specified node.
     *
         * @see org.w3c.dom.xpath.XPathEvaluator#createNSResolver(Node)
         */
        public XPathNSResolver createNSResolver(Node nodeResolver) {

                return new XPathNSResolverImpl((nodeResolver.getNodeType() == Node.DOCUMENT_NODE)
                   ? ((Document) nodeResolver).getDocumentElement() : nodeResolver);
        }

        /**
     * Evaluates an XPath expression string and returns a result of the
     * specified type if possible.
     *
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
     *   result will be coerced to return the specified type relying on
     *   XPath type conversions and fail if the desired coercion is not
     *   possible. This must be one of the type codes of
     *   <code>XPathResult</code>.
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
     *   supported by this XPathEvaluator.
     *   <br>NOT_SUPPORTED_ERR: The Node is not a type permitted as an XPath
     *   context node.
         *
         * @see org.w3c.dom.xpath.XPathEvaluator#evaluate(String, Node, XPathNSResolver, short, XPathResult)
         */
        public Object evaluate(
                String expression,
                Node contextNode,
                XPathNSResolver resolver,
                short type,
                Object result)
                throws XPathException, DOMException {

                XPathExpression xpathExpression = createExpression(expression, resolver);

                return  xpathExpression.evaluate(contextNode, type, result);
        }

}
