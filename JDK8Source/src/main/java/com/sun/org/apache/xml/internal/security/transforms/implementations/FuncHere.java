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
package com.sun.org.apache.xml.internal.security.transforms.implementations;

import javax.xml.transform.TransformerException;

import com.sun.org.apache.xml.internal.dtm.DTM;
import com.sun.org.apache.xml.internal.security.utils.I18n;
import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import com.sun.org.apache.xpath.internal.NodeSetDTM;
import com.sun.org.apache.xpath.internal.XPathContext;
import com.sun.org.apache.xpath.internal.functions.Function;
import com.sun.org.apache.xpath.internal.objects.XNodeSet;
import com.sun.org.apache.xpath.internal.objects.XObject;
import com.sun.org.apache.xpath.internal.res.XPATHErrorResources;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * The 'here()' function returns a node-set containing the attribute or
 * processing instruction node or the parent element of the text node
 * that directly bears the XPath expression.  This expression results
 * in an error if the containing XPath expression does not appear in the
 * same XML document against which the XPath expression is being evaluated.
 *
 * Mainpart is stolen from FuncId.java
 *
 * This does crash under Xalan2.2.D7 and works under Xalan2.2.D9
 *
 * To get this baby to work, a special trick has to be used. The function needs
 * access to the Node where the XPath expression has been defined. This is done
 * by constructing a {@link FuncHere} which has this Node as 'owner'.
 *
 * <p>
 *  'here()'函数返回包含属性或处理指令节点或直接承载XPath表达式的文本节点的父元素的节点集。
 * 如果包含的XPath表达式未显示在要针对其评估XPath表达式的同一个XML文档中,则此表达式将导致错误。
 * 
 *  Mainpart从FuncId.java中被窃取
 * 
 *  这在Xalan2.2.D7下崩溃,并在Xalan2.2.D9下工作
 * 
 * 为了让这个宝宝工作,必须使用一个特殊的技巧。该函数需要访问已定义XPath表达式的节点。这是通过构造一个{@link FuncHere}来实现的,它的Node是"owner"。
 * 
 * 
 * @see "http://www.w3.org/Signature/Drafts/xmldsig-core/Overview.html#function-here"
 */
public class FuncHere extends Function {

    /**
     *
     * <p>
     */
    private static final long serialVersionUID = 1L;

    /**
     * The here function returns a node-set containing the attribute or
     * processing instruction node or the parent element of the text node
     * that directly bears the XPath expression.  This expression results
     * in an error if the containing XPath expression does not appear in the
     * same XML document against which the XPath expression is being evaluated.
     *
     * <p>
     * 
     * @param xctxt
     * @return the xobject
     * @throws javax.xml.transform.TransformerException
     */
    @Override
    public XObject execute(XPathContext xctxt)
        throws javax.xml.transform.TransformerException {

        Node xpathOwnerNode = (Node) xctxt.getOwnerObject();

        if (xpathOwnerNode == null) {
            return null;
        }

        int xpathOwnerNodeDTM = xctxt.getDTMHandleFromNode(xpathOwnerNode);

        int currentNode = xctxt.getCurrentNode();
        DTM dtm = xctxt.getDTM(currentNode);
        int docContext = dtm.getDocument();

        if (DTM.NULL == docContext) {
            error(xctxt, XPATHErrorResources.ER_CONTEXT_HAS_NO_OWNERDOC, null);
        }

        {
            // check whether currentNode and the node containing the XPath expression
            // are in the same document
            Document currentDoc =
                XMLUtils.getOwnerDocument(dtm.getNode(currentNode));
            Document xpathOwnerDoc = XMLUtils.getOwnerDocument(xpathOwnerNode);

            if (currentDoc != xpathOwnerDoc) {
                throw new TransformerException(I18n.translate("xpath.funcHere.documentsDiffer"));
            }
        }

        XNodeSet nodes = new XNodeSet(xctxt.getDTMManager());
        NodeSetDTM nodeSet = nodes.mutableNodeset();

        {
            int hereNode = DTM.NULL;

            switch (dtm.getNodeType(xpathOwnerNodeDTM)) {

            case Node.ATTRIBUTE_NODE :
            case Node.PROCESSING_INSTRUCTION_NODE : {
                // returns a node-set containing the attribute /  processing instruction node
                hereNode = xpathOwnerNodeDTM;

                nodeSet.addNode(hereNode);

                break;
            }
            case Node.TEXT_NODE : {
                // returns a node-set containing the parent element of the
                // text node that directly bears the XPath expression
                hereNode = dtm.getParent(xpathOwnerNodeDTM);

                nodeSet.addNode(hereNode);

                break;
            }
            default :
                break;
            }
        }

        /** $todo$ Do I have to do this detach() call? */
        nodeSet.detach();

        return nodes;
    }

    /**
     * No arguments to process, so this does nothing.
     * <p>
     *  here函数返回一个包含属性或处理指令节点或直接承载XPath表达式的文本节点的父元素的节点集。
     * 如果包含的XPath表达式未显示在要对其评估XPath表达式的同一个XML文档中,则此表达式将导致错误。
     * 
     * 
     * @param vars
     * @param globalsSize
     */
    @SuppressWarnings("rawtypes")
    public void fixupVariables(java.util.Vector vars, int globalsSize) {
        // do nothing
    }
}
