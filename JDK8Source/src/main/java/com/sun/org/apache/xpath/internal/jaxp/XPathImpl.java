/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 1999-2004 The Apache Software Foundation.
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
 *  版权所有1999-2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
// $Id: XPathImpl.java,v 1.2 2005/08/16 22:41:08 jeffsuttor Exp $

package com.sun.org.apache.xpath.internal.jaxp;

import javax.xml.namespace.QName;
import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFunctionResolver;
import javax.xml.xpath.XPathVariableResolver;
import javax.xml.xpath.XPathExpression;

import com.sun.org.apache.xml.internal.dtm.DTM;
import com.sun.org.apache.xpath.internal.*;
import com.sun.org.apache.xpath.internal.objects.XObject;
import com.sun.org.apache.xpath.internal.res.XPATHErrorResources;
import com.sun.org.apache.xalan.internal.res.XSLMessages;
import com.sun.org.apache.xalan.internal.utils.FactoryImpl;
import com.sun.org.apache.xalan.internal.utils.FeatureManager;

import org.w3c.dom.Node;
import org.w3c.dom.Document;
import org.w3c.dom.traversal.NodeIterator;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;

import java.io.IOException;

/**
 * The XPathImpl class provides implementation for the methods defined  in
 * javax.xml.xpath.XPath interface. This provide simple access to the results
 * of an XPath expression.
 *
 *
 * <p>
 *  XPathImpl类为javax.xml.xpath.XPath接口中定义的方法提供实现。这提供对XPath表达式的结果的简单访问。
 * 
 * 
 * @version $Revision: 1.10 $
 * @author  Ramesh Mandava
 */
public class XPathImpl implements javax.xml.xpath.XPath {

    // Private variables
    private XPathVariableResolver variableResolver;
    private XPathFunctionResolver functionResolver;
    private XPathVariableResolver origVariableResolver;
    private XPathFunctionResolver origFunctionResolver;
    private NamespaceContext namespaceContext=null;
    private JAXPPrefixResolver prefixResolver;
    // By default Extension Functions are allowed in XPath Expressions. If
    // Secure Processing Feature is set on XPathFactory then the invocation of
    // extensions function need to throw XPathFunctionException
    private boolean featureSecureProcessing = false;
    private boolean useServiceMechanism = true;
    private final FeatureManager featureManager;

    XPathImpl( XPathVariableResolver vr, XPathFunctionResolver fr ) {
        this(vr, fr, false, true, new FeatureManager());
    }

    XPathImpl( XPathVariableResolver vr, XPathFunctionResolver fr,
            boolean featureSecureProcessing, boolean useServiceMechanism,
            FeatureManager featureManager) {
        this.origVariableResolver = this.variableResolver = vr;
        this.origFunctionResolver = this.functionResolver = fr;
        this.featureSecureProcessing = featureSecureProcessing;
        this.useServiceMechanism = useServiceMechanism;
        this.featureManager = featureManager;
    }

    /**
     * <p>Establishes a variable resolver.</p>
     *
     * <p>
     *  <p>建立一个变量解析器。</p>
     * 
     * 
     * @param resolver Variable Resolver
     */
    public void setXPathVariableResolver(XPathVariableResolver resolver) {
        if ( resolver == null ) {
            String fmsg = XSLMessages.createXPATHMessage(
                    XPATHErrorResources.ER_ARG_CANNOT_BE_NULL,
                    new Object[] {"XPathVariableResolver"} );
            throw new NullPointerException( fmsg );
        }
        this.variableResolver = resolver;
    }

    /**
     * <p>Returns the current variable resolver.</p>
     *
     * <p>
     *  <p>返回当前变量解析器。</p>
     * 
     * 
     * @return Current variable resolver
     */
    public XPathVariableResolver getXPathVariableResolver() {
        return variableResolver;
    }

    /**
     * <p>Establishes a function resolver.</p>
     *
     * <p>
     *  <p>建立函数解析器。</p>
     * 
     * 
     * @param resolver XPath function resolver
     */
    public void setXPathFunctionResolver(XPathFunctionResolver resolver) {
        if ( resolver == null ) {
            String fmsg = XSLMessages.createXPATHMessage(
                    XPATHErrorResources.ER_ARG_CANNOT_BE_NULL,
                    new Object[] {"XPathFunctionResolver"} );
            throw new NullPointerException( fmsg );
        }
        this.functionResolver = resolver;
    }

    /**
     * <p>Returns the current function resolver.</p>
     *
     * <p>
     *  <p>返回当前函数解析器。</p>
     * 
     * 
     * @return Current function resolver
     */
    public XPathFunctionResolver getXPathFunctionResolver() {
        return functionResolver;
    }

    /**
     * <p>Establishes a namespace context.</p>
     *
     * <p>
     *  <p>建立命名空间上下文。</p>
     * 
     * 
     * @param nsContext Namespace context to use
     */
    public void setNamespaceContext(NamespaceContext nsContext) {
        if ( nsContext == null ) {
            String fmsg = XSLMessages.createXPATHMessage(
                    XPATHErrorResources.ER_ARG_CANNOT_BE_NULL,
                    new Object[] {"NamespaceContext"} );
            throw new NullPointerException( fmsg );
        }
        this.namespaceContext = nsContext;
        this.prefixResolver = new JAXPPrefixResolver ( nsContext );
    }

    /**
     * <p>Returns the current namespace context.</p>
     *
     * <p>
     *  <p>返回当前的命名空间上下文。</p>
     * 
     * 
     * @return Current Namespace context
     */
    public NamespaceContext getNamespaceContext() {
        return namespaceContext;
    }

    private static Document d = null;

    private DocumentBuilder getParser() {
        try {
            // we'd really like to cache those DocumentBuilders, but we can't because:
            // 1. thread safety. parsers are not thread-safe, so at least
            //    we need one instance per a thread.
            // 2. parsers are non-reentrant, so now we are looking at having a
            // pool of parsers.
            // 3. then the class loading issue. The look-up procedure of
            //    DocumentBuilderFactory.newInstance() depends on context class loader
            //    and system properties, which may change during the execution of JVM.
            //
            // so we really have to create a fresh DocumentBuilder every time we need one
            // - KK
            DocumentBuilderFactory dbf = FactoryImpl.getDOMFactory(useServiceMechanism);
            dbf.setNamespaceAware( true );
            dbf.setValidating( false );
            return dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            // this should never happen with a well-behaving JAXP implementation.
            throw new Error(e);
        }
    }


    private XObject eval(String expression, Object contextItem)
        throws javax.xml.transform.TransformerException {
        com.sun.org.apache.xpath.internal.XPath xpath = new com.sun.org.apache.xpath.internal.XPath( expression,
            null, prefixResolver, com.sun.org.apache.xpath.internal.XPath.SELECT );
        com.sun.org.apache.xpath.internal.XPathContext xpathSupport = null;
        if ( functionResolver != null ) {
            JAXPExtensionsProvider jep = new JAXPExtensionsProvider(
                    functionResolver, featureSecureProcessing, featureManager );
            xpathSupport = new com.sun.org.apache.xpath.internal.XPathContext( jep );
        } else {
            xpathSupport = new com.sun.org.apache.xpath.internal.XPathContext();
        }

        XObject xobj = null;

        xpathSupport.setVarStack(new JAXPVariableStack(variableResolver));

        // If item is null, then we will create a a Dummy contextNode
        if ( contextItem instanceof Node ) {
            xobj = xpath.execute (xpathSupport, (Node)contextItem,
                    prefixResolver );
        } else {
            xobj = xpath.execute ( xpathSupport, DTM.NULL, prefixResolver );
        }

        return xobj;
    }

    /**
     * <p>Evaluate an <code>XPath</code> expression in the specified context and return the result as the specified type.</p>
     *
     * <p>See "Evaluation of XPath Expressions" section of JAXP 1.3 spec
     * for context item evaluation,
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
     *  <p>在指定的上下文中评估<code> XPath </code>表达式,并返回指定类型的结果。</p>
     * 
     *  <p>有关上下文项评估,变量,函数和<code> QName </code>解析和返回类型转换的JAXP 1.3规范的"XPath表达式评估"部分。</p>
     * 
     * <p>如果<code> returnType </code>不是{@link XPathConstants}({@link XPathConstants#NUMBER NUMBER},{@link XPathConstants#STRING STRING}
     * ,{@link XPathConstants#BOOLEAN BOOLEAN},{@link XPathConstants#NODE NODE}或{@link XPathConstants#NODESET NODESET}
     * ),则会抛出<code> IllegalArgumentException </code>。
     * </p>。
     * 
     *  <p>如果为<code> item </code>提供了<code> null </code>值,那么空文档将用于上下文。
     * 如果<code> expression </code>或<code> returnType </code>是<code> null </code>,那么会抛出<code> NullPointerExce
     * ption </code>。
     *  <p>如果为<code> item </code>提供了<code> null </code>值,那么空文档将用于上下文。</p>。
     * 
     * 
     * @param expression The XPath expression.
     * @param item The starting context (node or node list, for example).
     * @param returnType The desired return type.
     *
     * @return Result of evaluating an XPath expression as an <code>Object</code> of <code>returnType</code>.
     *
     * @throws XPathExpressionException If <code>expression</code> cannot be evaluated.
     * @throws IllegalArgumentException If <code>returnType</code> is not one of the types defined in {@link XPathConstants}.
     * @throws NullPointerException If <code>expression</code> or <code>returnType</code> is <code>null</code>.
     */
    public Object evaluate(String expression, Object item, QName returnType)
            throws XPathExpressionException {
        if ( expression == null ) {
            String fmsg = XSLMessages.createXPATHMessage(
                    XPATHErrorResources.ER_ARG_CANNOT_BE_NULL,
                    new Object[] {"XPath expression"} );
            throw new NullPointerException ( fmsg );
        }
        if ( returnType == null ) {
            String fmsg = XSLMessages.createXPATHMessage(
                    XPATHErrorResources.ER_ARG_CANNOT_BE_NULL,
                    new Object[] {"returnType"} );
            throw new NullPointerException ( fmsg );
        }
        // Checking if requested returnType is supported. returnType need to
        // be defined in XPathConstants
        if ( !isSupported ( returnType ) ) {
            String fmsg = XSLMessages.createXPATHMessage(
                    XPATHErrorResources.ER_UNSUPPORTED_RETURN_TYPE,
                    new Object[] { returnType.toString() } );
            throw new IllegalArgumentException ( fmsg );
        }

        try {

            XObject resultObject = eval( expression, item );
            return getResultAsType( resultObject, returnType );
        } catch ( java.lang.NullPointerException npe ) {
            // If VariableResolver returns null Or if we get
            // NullPointerException at this stage for some other reason
            // then we have to reurn XPathException
            throw new XPathExpressionException ( npe );
        } catch ( javax.xml.transform.TransformerException te ) {
            Throwable nestedException = te.getException();
            if ( nestedException instanceof javax.xml.xpath.XPathFunctionException ) {
                throw (javax.xml.xpath.XPathFunctionException)nestedException;
            } else {
                // For any other exceptions we need to throw
                // XPathExpressionException ( as per spec )
                throw new XPathExpressionException ( te );
            }
        }

    }

    private boolean isSupported( QName returnType ) {
        if ( ( returnType.equals( XPathConstants.STRING ) ) ||
             ( returnType.equals( XPathConstants.NUMBER ) ) ||
             ( returnType.equals( XPathConstants.BOOLEAN ) ) ||
             ( returnType.equals( XPathConstants.NODE ) ) ||
             ( returnType.equals( XPathConstants.NODESET ) )  ) {

            return true;
        }
        return false;
     }

    private Object getResultAsType( XObject resultObject, QName returnType )
        throws javax.xml.transform.TransformerException {
        // XPathConstants.STRING
        if ( returnType.equals( XPathConstants.STRING ) ) {
            return resultObject.str();
        }
        // XPathConstants.NUMBER
        if ( returnType.equals( XPathConstants.NUMBER ) ) {
            return new Double ( resultObject.num());
        }
        // XPathConstants.BOOLEAN
        if ( returnType.equals( XPathConstants.BOOLEAN ) ) {
            return new Boolean( resultObject.bool());
        }
        // XPathConstants.NODESET ---ORdered, UNOrdered???
        if ( returnType.equals( XPathConstants.NODESET ) ) {
            return resultObject.nodelist();
        }
        // XPathConstants.NODE
        if ( returnType.equals( XPathConstants.NODE ) ) {
            NodeIterator ni = resultObject.nodeset();
            //Return the first node, or null
            return ni.nextNode();
        }
        String fmsg = XSLMessages.createXPATHMessage(
                XPATHErrorResources.ER_UNSUPPORTED_RETURN_TYPE,
                new Object[] { returnType.toString()});
        throw new IllegalArgumentException( fmsg );
    }



    /**
     * <p>Evaluate an XPath expression in the specified context and return the result as a <code>String</code>.</p>
     *
     * <p>This method calls {@link #evaluate(String expression, Object item, QName returnType)} with a <code>returnType</code> of
     * {@link XPathConstants#STRING}.</p>
     *
     * <p>See "Evaluation of XPath Expressions" of JAXP 1.3 spec
     * for context item evaluation,
     * variable, function and QName resolution and return type conversion.</p>
     *
     * <p>If a <code>null</code> value is provided for
     * <code>item</code>, an empty document will be used for the
     * context.
     * If <code>expression</code> is <code>null</code>, then a <code>NullPointerException</code> is thrown.</p>
     *
     * <p>
     *  <p>在指定的上下文中评估XPath表达式,并将结果作为<code> String </code>返回。</p>
     * 
     *  <p>此方法使用{@link XPathConstants#STRING}的<code> returnType </code>调用{@link #evaluate(String expression,Object item,QName returnType)}
     * 。
     * </p>。
     * 
     *  <p>有关上下文项评估,变量,函数和QName解析以及返回类型转换的JAXP 1.3规范的"XPath表达式评估"。</p>
     * 
     *  <p>如果为<code> item </code>提供了<code> null </code>值,那么空文档将用于上下文。
     * 如果<code> expression </code>是<code> null </code>,那么会抛出<code> NullPointerException </code>。</p>。
     * 
     * 
     * @param expression The XPath expression.
     * @param item The starting context (node or node list, for example).
     *
     * @return The <code>String</code> that is the result of evaluating the expression and
     *   converting the result to a <code>String</code>.
     *
     * @throws XPathExpressionException If <code>expression</code> cannot be evaluated.
     * @throws NullPointerException If <code>expression</code> is <code>null</code>.
     */
    public String evaluate(String expression, Object item)
        throws XPathExpressionException {
        return (String)this.evaluate( expression, item, XPathConstants.STRING );
    }

    /**
     * <p>Compile an XPath expression for later evaluation.</p>
     *
     * <p>If <code>expression</code> contains any {@link XPathFunction}s,
     * they must be available via the {@link XPathFunctionResolver}.
     * An {@link XPathExpressionException} will be thrown if the <code>XPathFunction</code>
     * cannot be resovled with the <code>XPathFunctionResolver</code>.</p>
     *
     * <p>If <code>expression</code> is <code>null</code>, a <code>NullPointerException</code> is thrown.</p>
     *
     * <p>
     *  <p>编译XPath表达式以供以后评估。</p>
     * 
     * <p>如果<code> expression </code>包含任何{@link XPathFunction},它们必须通过{@link XPathFunctionResolver}可用。
     * 如果<code> XPathFunction </code>无法使用<code> XPathFunctionResolver </code>重新移动,则会抛出{@link XPathExpressionException}
     * 。
     * <p>如果<code> expression </code>包含任何{@link XPathFunction},它们必须通过{@link XPathFunctionResolver}可用。</p>。
     * 
     *  <p>如果<code> expression </code>是<code> null </code>,则会抛出<code> NullPointerException </code>。</p>
     * 
     * 
     * @param expression The XPath expression.
     *
     * @return Compiled XPath expression.

     * @throws XPathExpressionException If <code>expression</code> cannot be compiled.
     * @throws NullPointerException If <code>expression</code> is <code>null</code>.
     */
    public XPathExpression compile(String expression)
        throws XPathExpressionException {
        if ( expression == null ) {
            String fmsg = XSLMessages.createXPATHMessage(
                    XPATHErrorResources.ER_ARG_CANNOT_BE_NULL,
                    new Object[] {"XPath expression"} );
            throw new NullPointerException ( fmsg );
        }
        try {
            com.sun.org.apache.xpath.internal.XPath xpath = new XPath (expression, null,
                    prefixResolver, com.sun.org.apache.xpath.internal.XPath.SELECT );
            // Can have errorListener
            XPathExpressionImpl ximpl = new XPathExpressionImpl (xpath,
                    prefixResolver, functionResolver, variableResolver,
                    featureSecureProcessing, useServiceMechanism, featureManager );
            return ximpl;
        } catch ( javax.xml.transform.TransformerException te ) {
            throw new XPathExpressionException ( te ) ;
        }
    }


    /**
     * <p>Evaluate an XPath expression in the context of the specified <code>InputSource</code>
     * and return the result as the specified type.</p>
     *
     * <p>This method builds a data model for the {@link InputSource} and calls
     * {@link #evaluate(String expression, Object item, QName returnType)} on the resulting document object.</p>
     *
     * <p>See "Evaluation of XPath Expressions" section of JAXP 1.3 spec
     * for context item evaluation,
     * variable, function and QName resolution and return type conversion.</p>
     *
     * <p>If <code>returnType</code> is not one of the types defined in {@link XPathConstants},
     * then an <code>IllegalArgumentException</code> is thrown.</p>
     *
     * <p>If <code>expression</code>, <code>source</code> or <code>returnType</code> is <code>null</code>,
     * then a <code>NullPointerException</code> is thrown.</p>
     *
     * <p>
     *  <p>在指定的<code> InputSource </code>上下文中评估XPath表达式,并返回指定类型的结果。</p>
     * 
     *  <p>此方法会在结果文档对象上构建{@link InputSource}的数据模型并调用{@link #evaluate(String expression,Object item,QName returnType)}
     * 。
     * </p>。
     * 
     *  <p>有关上下文项评估,变量,函数和QName解析以及返回类型转换的JAXP 1.3规范的"XPath表达式评估"部分。</p>
     * 
     *  <p>如果<code> returnType </code>不是{@link XPathConstants}中定义的类型之一,则会抛出<code> IllegalArgumentException </code>
     * 。
     * </p>。
     * 
     *  <p>如果<code> expression </code>,<code> source </code>或<code> returnType </code>是<code> null </code>,那
     * 么<code> NullPointerException </code>被抛出。
     * </p>。
     * 
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
    public Object evaluate(String expression, InputSource source,
            QName returnType) throws XPathExpressionException {
        // Checking validity of different parameters
        if( source== null ) {
            String fmsg = XSLMessages.createXPATHMessage(
                    XPATHErrorResources.ER_ARG_CANNOT_BE_NULL,
                    new Object[] {"source"} );
            throw new NullPointerException ( fmsg );
        }
        if ( expression == null ) {
            String fmsg = XSLMessages.createXPATHMessage(
                    XPATHErrorResources.ER_ARG_CANNOT_BE_NULL,
                    new Object[] {"XPath expression"} );
            throw new NullPointerException ( fmsg );
        }
        if ( returnType == null ) {
            String fmsg = XSLMessages.createXPATHMessage(
                    XPATHErrorResources.ER_ARG_CANNOT_BE_NULL,
                    new Object[] {"returnType"} );
            throw new NullPointerException ( fmsg );
        }

        //Checking if requested returnType is supported.
        //returnType need to be defined in XPathConstants
        if ( !isSupported ( returnType ) ) {
            String fmsg = XSLMessages.createXPATHMessage(
                    XPATHErrorResources.ER_UNSUPPORTED_RETURN_TYPE,
                    new Object[] { returnType.toString() } );
            throw new IllegalArgumentException ( fmsg );
        }

        try {

            Document document = getParser().parse( source );

            XObject resultObject = eval( expression, document );
            return getResultAsType( resultObject, returnType );
        } catch ( SAXException e ) {
            throw new XPathExpressionException ( e );
        } catch( IOException e ) {
            throw new XPathExpressionException ( e );
        } catch ( javax.xml.transform.TransformerException te ) {
            Throwable nestedException = te.getException();
            if ( nestedException instanceof javax.xml.xpath.XPathFunctionException ) {
                throw (javax.xml.xpath.XPathFunctionException)nestedException;
            } else {
                throw new XPathExpressionException ( te );
            }
        }

    }




    /**
     * <p>Evaluate an XPath expression in the context of the specified <code>InputSource</code>
     * and return the result as a <code>String</code>.</p>
     *
     * <p>This method calls {@link #evaluate(String expression, InputSource source, QName returnType)} with a
     * <code>returnType</code> of {@link XPathConstants#STRING}.</p>
     *
     * <p>See "Evaluation of XPath Expressions" section of JAXP 1.3 spec
     * for context item evaluation,
     * variable, function and QName resolution and return type conversion.</p>
     *
     * <p>If <code>expression</code> or <code>source</code> is <code>null</code>,
     * then a <code>NullPointerException</code> is thrown.</p>
     *
     * <p>
     *  <p>在指定的<code> InputSource </code>的上下文中评估XPath表达式,并将结果作为<code> String </code>返回。</p>
     * 
     *  <p>此方法使用{@link XPathConstants#STRING}的<code> returnType </code>调用{@link #evaluate(String expression,InputSource source,QName returnType)}
     * 。
     * </p>。
     * 
     * <p>有关上下文项评估,变量,函数和QName解析以及返回类型转换的JAXP 1.3规范的"XPath表达式评估"部分。</p>
     * 
     *  <p>如果<code> expression </code>或<code> source </code>是<code> null </code>,那么会抛出<code> NullPointerExce
     * ption </code>。
     * </p>。
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
        throws XPathExpressionException {
        return (String)this.evaluate( expression, source, XPathConstants.STRING );
    }

    /**
     * <p>Reset this <code>XPath</code> to its original configuration.</p>
     *
     * <p><code>XPath</code> is reset to the same state as when it was created with
     * {@link XPathFactory#newXPath()}.
     * <code>reset()</code> is designed to allow the reuse of existing <code>XPath</code>s
     * thus saving resources associated with the creation of new <code>XPath</code>s.</p>
     *
     * <p>The reset <code>XPath</code> is not guaranteed to have the same
     * {@link XPathFunctionResolver}, {@link XPathVariableResolver}
     * or {@link NamespaceContext} <code>Object</code>s, e.g. {@link Object#equals(Object obj)}.
     * It is guaranteed to have a functionally equal <code>XPathFunctionResolver</code>,
     * <code>XPathVariableResolver</code>
     * and <code>NamespaceContext</code>.</p>
     * <p>
     */
    public void reset() {
        this.variableResolver = this.origVariableResolver;
        this.functionResolver = this.origFunctionResolver;
        this.namespaceContext = null;
    }

}
