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

import java.io.OutputStream;

import com.sun.org.apache.xml.internal.security.signature.NodeFilter;
import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
import com.sun.org.apache.xml.internal.security.transforms.Transform;
import com.sun.org.apache.xml.internal.security.transforms.TransformSpi;
import com.sun.org.apache.xml.internal.security.transforms.TransformationException;
import com.sun.org.apache.xml.internal.security.transforms.Transforms;
import com.sun.org.apache.xml.internal.security.utils.Constants;
import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Implements the <CODE>http://www.w3.org/2000/09/xmldsig#enveloped-signature</CODE>
 * transform.
 *
 * <p>
 *  实施<CODE> http://www.w3.org/2000/09/xmldsig#enveloped-signature </CODE>转换。
 * 
 * 
 * @author Christian Geuer-Pollmann
 */
public class TransformEnvelopedSignature extends TransformSpi {

    /** Field implementedTransformURI */
    public static final String implementedTransformURI =
        Transforms.TRANSFORM_ENVELOPED_SIGNATURE;

    /**
     * Method engineGetURI
     *
     * @inheritDoc
     * <p>
     *  方法engineGetURI
     * 
     *  @inheritDoc
     * 
     */
    protected String engineGetURI() {
        return implementedTransformURI;
    }

    /**
     * @inheritDoc
     * <p>
     *  @inheritDoc
     * 
     */
    protected XMLSignatureInput enginePerformTransform(
        XMLSignatureInput input, OutputStream os, Transform transformObject
    ) throws TransformationException {
        /**
         * If the actual input is an octet stream, then the application MUST
         * convert the octet stream to an XPath node-set suitable for use by
         * Canonical XML with Comments. (A subsequent application of the
         * REQUIRED Canonical XML algorithm would strip away these comments.)
         *
         * ...
         *
         * The evaluation of this expression includes all of the document's nodes
         * (including comments) in the node-set representing the octet stream.
         * <p>
         *  如果实际输入是八位字节流,那么应用程序必须将八位字节流转换为适用于带有注释的规范XML使用的XPath节点集。 (随后应用所需的规范XML算法将剥离这些注释。)
         * 
         *  ... ...
         * 
         */

        Node signatureElement = transformObject.getElement();

        signatureElement = searchSignatureElement(signatureElement);
        input.setExcludeNode(signatureElement);
        input.addNodeFilter(new EnvelopedNodeFilter(signatureElement));
        return input;
    }

    /**
    /* <p>
    /*  该表达式的评估包括表示八位字节流的节点集中的所有文档的节点(包括注释)。
    /* 
    /* 
     * @param signatureElement
     * @return the node that is the signature
     * @throws TransformationException
     */
    private static Node searchSignatureElement(Node signatureElement)
        throws TransformationException {
        boolean found = false;

        while (true) {
            if (signatureElement == null
                || signatureElement.getNodeType() == Node.DOCUMENT_NODE) {
                break;
            }
            Element el = (Element) signatureElement;
            if (el.getNamespaceURI().equals(Constants.SignatureSpecNS)
                && el.getLocalName().equals(Constants._TAG_SIGNATURE)) {
                found = true;
                break;
            }

            signatureElement = signatureElement.getParentNode();
        }

        if (!found) {
            throw new TransformationException(
                "transform.envelopedSignatureTransformNotInSignatureElement");
        }
        return signatureElement;
    }

    static class EnvelopedNodeFilter implements NodeFilter {

        Node exclude;

        EnvelopedNodeFilter(Node n) {
            exclude = n;
        }

        public int isNodeIncludeDO(Node n, int level) {
            if (n == exclude) {
                return -1;
            }
            return 1;
        }

        /**
        /* <p>
        /* 
         * @see com.sun.org.apache.xml.internal.security.signature.NodeFilter#isNodeInclude(org.w3c.dom.Node)
         */
        public int isNodeInclude(Node n) {
            if (n == exclude || XMLUtils.isDescendantOrSelf(exclude, n)) {
                return -1;
            }
            return 1;
            //return !XMLUtils.isDescendantOrSelf(exclude,n);
        }
    }
}
