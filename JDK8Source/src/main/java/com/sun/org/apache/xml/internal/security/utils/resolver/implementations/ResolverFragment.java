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
 * This resolver is used for resolving same-document URIs like URI="" of URI="#id".
 *
 * <p>
 *  此解析器用于解析相同文档URI,如URI ="#id"的URI =""。
 * 
 * 
 * @author $Author: coheigea $
 * @see <A HREF="http://www.w3.org/TR/xmldsig-core/#sec-ReferenceProcessingModel">The Reference processing model in the XML Signature spec</A>
 * @see <A HREF="http://www.w3.org/TR/xmldsig-core/#sec-Same-Document">Same-Document URI-References in the XML Signature spec</A>
 * @see <A HREF="http://www.ietf.org/rfc/rfc2396.txt">Section 4.2 of RFC 2396</A>
 */
public class ResolverFragment extends ResourceResolverSpi {

    /** {@link org.apache.commons.logging} logging facility */
    private static java.util.logging.Logger log =
        java.util.logging.Logger.getLogger(ResolverFragment.class.getName());

    @Override
    public boolean engineIsThreadSafe() {
        return true;
    }

    /**
     * Method engineResolve
     *
     * @inheritDoc
     * <p>
     *  方法engineResolve
     * 
     *  @inheritDoc
     * 
     * 
     * @param uri
     * @param baseURI
     */
    public XMLSignatureInput engineResolveURI(ResourceResolverContext context)
        throws ResourceResolverException {

        Document doc = context.attr.getOwnerElement().getOwnerDocument();

        Node selectedElem = null;
        if (context.uriToResolve.equals("")) {
            /*
             * Identifies the node-set (minus any comment nodes) of the XML
             * resource containing the signature
             * <p>
             *  标识包含签名的XML资源的节点集(减去任何注释节点)
             * 
             */
            if (log.isLoggable(java.util.logging.Level.FINE)) {
                log.log(java.util.logging.Level.FINE, "ResolverFragment with empty URI (means complete document)");
            }
            selectedElem = doc;
        } else {
            /*
             * URI="#chapter1"
             * Identifies a node-set containing the element with ID attribute
             * value 'chapter1' of the XML resource containing the signature.
             * XML Signature (and its applications) modify this node-set to
             * include the element plus all descendants including namespaces and
             * attributes -- but not comments.
             * <p>
             *  URI ="#chapter1"标识包含具有签名的XML资源的ID属性值"chapter1"的元素的节点集。
             *  XML签名(及其应用程序)修改此节点集以包括元素以及所有后代,包括命名空间和属性 - 但不包括注释。
             * 
             */
            String id = context.uriToResolve.substring(1);

            selectedElem = doc.getElementById(id);
            if (selectedElem == null) {
                Object exArgs[] = { id };
                throw new ResourceResolverException(
                    "signature.Verification.MissingID", exArgs, context.attr, context.baseUri
                );
            }
            if (context.secureValidation) {
                Element start = context.attr.getOwnerDocument().getDocumentElement();
                if (!XMLUtils.protectAgainstWrappingAttack(start, id)) {
                    Object exArgs[] = { id };
                    throw new ResourceResolverException(
                        "signature.Verification.MultipleIDs", exArgs, context.attr, context.baseUri
                    );
                }
            }
            if (log.isLoggable(java.util.logging.Level.FINE)) {
                log.log(java.util.logging.Level.FINE,
                    "Try to catch an Element with ID " + id + " and Element was " + selectedElem
                );
            }
        }

        XMLSignatureInput result = new XMLSignatureInput(selectedElem);
        result.setExcludeComments(true);

        result.setMIMEType("text/xml");
        if (context.baseUri != null && context.baseUri.length() > 0) {
            result.setSourceURI(context.baseUri.concat(context.uriToResolve));
        } else {
            result.setSourceURI(context.uriToResolve);
        }
        return result;
    }

    /**
     * Method engineCanResolve
     * @inheritDoc
     * <p>
     * 
     * @param uri
     * @param baseURI
     */
    public boolean engineCanResolveURI(ResourceResolverContext context) {
        if (context.uriToResolve == null) {
            if (log.isLoggable(java.util.logging.Level.FINE)) {
                log.log(java.util.logging.Level.FINE, "Quick fail for null uri");
            }
            return false;
        }

        if (context.uriToResolve.equals("") ||
            ((context.uriToResolve.charAt(0) == '#') && !context.uriToResolve.startsWith("#xpointer("))
        ) {
            if (log.isLoggable(java.util.logging.Level.FINE)) {
                log.log(java.util.logging.Level.FINE, "State I can resolve reference: \"" + context.uriToResolve + "\"");
            }
            return true;
        }
        if (log.isLoggable(java.util.logging.Level.FINE)) {
            log.log(java.util.logging.Level.FINE, "Do not seem to be able to resolve reference: \"" + context.uriToResolve + "\"");
        }
        return false;
    }

}
