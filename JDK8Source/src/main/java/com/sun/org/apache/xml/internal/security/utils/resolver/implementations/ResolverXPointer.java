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
package com.sun.org.apache.xml.internal.security.utils.resolver.implementations;

import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolverContext;
import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolverException;
import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolverSpi;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Handles barename XPointer Reference URIs.
 * <BR />
 * To retain comments while selecting an element by an identifier ID,
 * use the following full XPointer: URI='#xpointer(id('ID'))'.
 * <BR />
 * To retain comments while selecting the entire document,
 * use the following full XPointer: URI='#xpointer(/)'.
 * This XPointer contains a simple XPath expression that includes
 * the root node, which the second to last step above replaces with all
 * nodes of the parse tree (all descendants, plus all attributes,
 * plus all namespaces nodes).
 *
 * <p>
 *  处理裸名XPointer引用URI。
 * <BR />
 *  要在通过标识符ID选择元素时保留注释,请使用以下完整XPointer：URI ='#xpointer(id('ID'))'。
 * <BR />
 *  要在选择整个文档时保留注释,请使用以下完整XPointer：URI ='#xpointer(/)'。
 * 这个XPointer包含一个简单的XPath表达式,其中包含根节点,上面第二到最后一步代替解析树的所有节点(所有后代,所有属性,以及所有命名空间节点)。
 * 
 * 
 * @author $Author: coheigea $
 */
public class ResolverXPointer extends ResourceResolverSpi {

    /** {@link org.apache.commons.logging} logging facility */
    private static java.util.logging.Logger log =
        java.util.logging.Logger.getLogger(ResolverXPointer.class.getName());

    private static final String XP = "#xpointer(id(";
    private static final int XP_LENGTH = XP.length();

    @Override
    public boolean engineIsThreadSafe() {
        return true;
    }

    /**
     * @inheritDoc
     * <p>
     *  @inheritDoc
     * 
     */
    @Override
    public XMLSignatureInput engineResolveURI(ResourceResolverContext context)
        throws ResourceResolverException {

        Node resultNode = null;
        Document doc = context.attr.getOwnerElement().getOwnerDocument();

        if (isXPointerSlash(context.uriToResolve)) {
            resultNode = doc;
        } else if (isXPointerId(context.uriToResolve)) {
            String id = getXPointerId(context.uriToResolve);
            resultNode = doc.getElementById(id);

            if (context.secureValidation) {
                Element start = context.attr.getOwnerDocument().getDocumentElement();
                if (!XMLUtils.protectAgainstWrappingAttack(start, id)) {
                    Object exArgs[] = { id };
                    throw new ResourceResolverException(
                        "signature.Verification.MultipleIDs", exArgs, context.attr, context.baseUri
                    );
                }
            }

            if (resultNode == null) {
                Object exArgs[] = { id };

                throw new ResourceResolverException(
                    "signature.Verification.MissingID", exArgs, context.attr, context.baseUri
                );
            }
        }

        XMLSignatureInput result = new XMLSignatureInput(resultNode);

        result.setMIMEType("text/xml");
        if (context.baseUri != null && context.baseUri.length() > 0) {
            result.setSourceURI(context.baseUri.concat(context.uriToResolve));
        } else {
            result.setSourceURI(context.uriToResolve);
        }

        return result;
    }

    /**
     * @inheritDoc
     * <p>
     *  @inheritDoc
     * 
     */
    public boolean engineCanResolveURI(ResourceResolverContext context) {
        if (context.uriToResolve == null) {
            return false;
        }
        if (isXPointerSlash(context.uriToResolve) || isXPointerId(context.uriToResolve)) {
            return true;
        }

        return false;
    }

    /**
     * Method isXPointerSlash
     *
     * <p>
     *  方法是XPointerSlash
     * 
     * 
     * @param uri
     * @return true if begins with xpointer
     */
    private static boolean isXPointerSlash(String uri) {
        if (uri.equals("#xpointer(/)")) {
            return true;
        }

        return false;
    }

    /**
     * Method isXPointerId
     *
     * <p>
     *  方法是XPointerId
     * 
     * 
     * @param uri
     * @return whether it has an xpointer id
     */
    private static boolean isXPointerId(String uri) {
        if (uri.startsWith(XP) && uri.endsWith("))")) {
            String idPlusDelim = uri.substring(XP_LENGTH, uri.length() - 2);

            int idLen = idPlusDelim.length() -1;
            if (((idPlusDelim.charAt(0) == '"') && (idPlusDelim.charAt(idLen) == '"'))
                || ((idPlusDelim.charAt(0) == '\'') && (idPlusDelim.charAt(idLen) == '\''))) {
                if (log.isLoggable(java.util.logging.Level.FINE)) {
                    log.log(java.util.logging.Level.FINE, "Id = " + idPlusDelim.substring(1, idLen));
                }
                return true;
            }
        }

        return false;
    }

    /**
     * Method getXPointerId
     *
     * <p>
     *  方法getXPointerId
     * 
     * @param uri
     * @return xpointerId to search.
     */
    private static String getXPointerId(String uri) {
        if (uri.startsWith(XP) && uri.endsWith("))")) {
            String idPlusDelim = uri.substring(XP_LENGTH,uri.length() - 2);

            int idLen = idPlusDelim.length() -1;
            if (((idPlusDelim.charAt(0) == '"') && (idPlusDelim.charAt(idLen) == '"'))
                || ((idPlusDelim.charAt(0) == '\'') && (idPlusDelim.charAt(idLen) == '\''))) {
                return idPlusDelim.substring(1, idLen);
            }
        }

        return null;
    }
}
