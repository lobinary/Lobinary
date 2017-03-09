/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * <p>
 *  根据一个或多个贡献者许可协议授予Apache软件基金会(ASF)。有关版权所有权的其他信息,请参阅随此作品分发的NOTICE文件。
 *  ASF根据Apache许可证2.0版("许可证")向您授予此文件;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本。
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
package com.sun.org.apache.xml.internal.security.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;

import com.sun.org.apache.xml.internal.security.transforms.implementations.FuncHere;
import com.sun.org.apache.xml.internal.utils.PrefixResolver;
import com.sun.org.apache.xml.internal.utils.PrefixResolverDefault;
import com.sun.org.apache.xpath.internal.Expression;
import com.sun.org.apache.xpath.internal.XPath;
import com.sun.org.apache.xpath.internal.XPathContext;
import com.sun.org.apache.xpath.internal.compiler.FunctionTable;
import com.sun.org.apache.xpath.internal.objects.XObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * An implementation of XPathAPI using Xalan. This supports the "here()" function defined in the digital
 * signature spec.
 * <p>
 *  使用Xalan的XPathAPI的实现。这支持在数字签名规范中定义的"here()"函数。
 * 
 */
public class XalanXPathAPI implements XPathAPI {

    private static java.util.logging.Logger log =
        java.util.logging.Logger.getLogger(XalanXPathAPI.class.getName());

    private String xpathStr = null;

    private XPath xpath = null;

    private static FunctionTable funcTable = null;

    private static boolean installed;

    private XPathContext context;

    static {
        fixupFunctionTable();
    }


    /**
     *  Use an XPath string to select a nodelist.
     *  XPath namespace prefixes are resolved from the namespaceNode.
     *
     * <p>
     *  使用XPath字符串选择一个节点列表。 XPath命名空间前缀从namespaceNode解析。
     * 
     * 
     *  @param contextNode The node to start searching from.
     *  @param xpathnode
     *  @param str
     *  @param namespaceNode The node from which prefixes in the XPath will be resolved to namespaces.
     *  @return A NodeIterator, should never be null.
     *
     * @throws TransformerException
     */
    public NodeList selectNodeList(
        Node contextNode, Node xpathnode, String str, Node namespaceNode
    ) throws TransformerException {

        // Execute the XPath, and have it return the result
        XObject list = eval(contextNode, xpathnode, str, namespaceNode);

        // Return a NodeList.
        return list.nodelist();
    }

    /**
     * Evaluate an XPath string and return true if the output is to be included or not.
     * <p>
     *  评估XPath字符串,如果要包括或不包括输出,则返回true。
     * 
     * 
     *  @param contextNode The node to start searching from.
     *  @param xpathnode The XPath node
     *  @param str The XPath expression
     *  @param namespaceNode The node from which prefixes in the XPath will be resolved to namespaces.
     */
    public boolean evaluate(Node contextNode, Node xpathnode, String str, Node namespaceNode)
        throws TransformerException {
        XObject object = eval(contextNode, xpathnode, str, namespaceNode);
        return object.bool();
    }

    /**
     * Clear any context information from this object
     * <p>
     *  清除此对象的任何上下文信息
     * 
     */
    public void clear() {
        xpathStr = null;
        xpath = null;
        context = null;
    }

    public synchronized static boolean isInstalled() {
        return installed;
    }

    private XObject eval(Node contextNode, Node xpathnode, String str, Node namespaceNode)
        throws TransformerException {
        if (context == null) {
            context = new XPathContext(xpathnode);
            context.setSecureProcessing(true);
        }

        // Create an object to resolve namespace prefixes.
        // XPath namespaces are resolved from the input context node's document element
        // if it is a root node, or else the current context node (for lack of a better
        // resolution space, given the simplicity of this sample code).
        Node resolverNode =
            (namespaceNode.getNodeType() == Node.DOCUMENT_NODE)
                ? ((Document) namespaceNode).getDocumentElement() : namespaceNode;
        PrefixResolverDefault prefixResolver = new PrefixResolverDefault(resolverNode);

        if (!str.equals(xpathStr)) {
            if (str.indexOf("here()") > 0) {
                context.reset();
            }
            xpath = createXPath(str, prefixResolver);
            xpathStr = str;
        }

        // Execute the XPath, and have it return the result
        int ctxtNode = context.getDTMHandleFromNode(contextNode);

        return xpath.execute(context, ctxtNode, prefixResolver);
    }

    private XPath createXPath(String str, PrefixResolver prefixResolver) throws TransformerException {
        XPath xpath = null;
        Class<?>[] classes = new Class<?>[]{String.class, SourceLocator.class, PrefixResolver.class, int.class,
                                      ErrorListener.class, FunctionTable.class};
        Object[] objects =
            new Object[]{str, null, prefixResolver, Integer.valueOf(XPath.SELECT), null, funcTable};
        try {
            Constructor<?> constructor = XPath.class.getConstructor(classes);
            xpath = (XPath) constructor.newInstance(objects);
        } catch (Exception ex) {
            if (log.isLoggable(java.util.logging.Level.FINE)) {
                log.log(java.util.logging.Level.FINE, ex.getMessage(), ex);
            }
        }
        if (xpath == null) {
            xpath = new XPath(str, null, prefixResolver, XPath.SELECT, null);
        }
        return xpath;
    }

    private synchronized static void fixupFunctionTable() {
        installed = false;
        if (log.isLoggable(java.util.logging.Level.FINE)) {
            log.log(java.util.logging.Level.FINE, "Registering Here function");
        }
        /**
         * Try to register our here() implementation as internal function.
         * <p>
         *  尝试注册我们的here()实现作为内部函数。
         */
        try {
            Class<?>[] args = {String.class, Expression.class};
            Method installFunction = FunctionTable.class.getMethod("installFunction", args);
            if ((installFunction.getModifiers() & Modifier.STATIC) != 0) {
                Object[] params = {"here", new FuncHere()};
                installFunction.invoke(null, params);
                installed = true;
            }
        } catch (Exception ex) {
            log.log(java.util.logging.Level.FINE, "Error installing function using the static installFunction method", ex);
        }
        if (!installed) {
            try {
                funcTable = new FunctionTable();
                Class<?>[] args = {String.class, Class.class};
                Method installFunction = FunctionTable.class.getMethod("installFunction", args);
                Object[] params = {"here", FuncHere.class};
                installFunction.invoke(funcTable, params);
                installed = true;
            } catch (Exception ex) {
                log.log(java.util.logging.Level.FINE, "Error installing function using the static installFunction method", ex);
            }
        }
        if (log.isLoggable(java.util.logging.Level.FINE)) {
            if (installed) {
                log.log(java.util.logging.Level.FINE, "Registered class " + FuncHere.class.getName()
                          + " for XPath function 'here()' function in internal table");
            } else {
                log.log(java.util.logging.Level.FINE, "Unable to register class " + FuncHere.class.getName()
                          + " for XPath function 'here()' function in internal table");
            }
        }
    }

}
