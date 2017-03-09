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

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
import com.sun.org.apache.xml.internal.security.transforms.Transform;
import com.sun.org.apache.xml.internal.security.transforms.TransformSpi;
import com.sun.org.apache.xml.internal.security.transforms.TransformationException;
import com.sun.org.apache.xml.internal.security.transforms.Transforms;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/**
 * Implements the <CODE>http://www.w3.org/2000/09/xmldsig#base64</CODE> decoding
 * transform.
 *
 * <p>The normative specification for base64 decoding transforms is
 * <A HREF="http://www.w3.org/TR/2001/CR-xmldsig-core-20010419/#ref-MIME">[MIME]</A>.
 * The base64 Transform element has no content. The input
 * is decoded by the algorithms. This transform is useful if an
 * application needs to sign the raw data associated with the encoded
 * content of an element. </p>
 *
 * <p>This transform requires an octet stream for input.
 * If an XPath node-set (or sufficiently functional alternative) is
 * given as input, then it is converted to an octet stream by
 * performing operations logically equivalent to 1) applying an XPath
 * transform with expression self::text(), then 2) taking the string-value
 * of the node-set. Thus, if an XML element is identified by a barename
 * XPointer in the Reference URI, and its content consists solely of base64
 * encoded character data, then this transform automatically strips away the
 * start and end tags of the identified element and any of its descendant
 * elements as well as any descendant comments and processing instructions.
 * The output of this transform is an octet stream.</p>
 *
 * <p>
 *  实现<CODE> http://www.w3.org/2000/09/xmldsig#base64 </CODE>解码转换。
 * 
 *  <p> base64解码变换的规范规范为<A HREF="http://www.w3.org/TR/2001/CR-xmldsig-core-20010419/#ref-MIME"> [MIME] </A >
 * 。
 *  base64 Transform元素没有内容。输入由算法解码。如果应用程序需要对与元素的编码内容相关联的原始数据进行签名,则此转换很有用。 </p>。
 * 
 * <p>此转换需要输入的八位字节流。
 * 如果给定XPath节点集(或足够功能的替代)作为输入,则通过执行逻辑上等效于以下的操作将其转换为八位字节流：1)应用具有表达式self :: text()的XPath变换,然后2)节点集的字符串值。
 * 因此,如果XML元素由引用URI中的裸名XPointer标识,并且其内容仅由base64编码字符数据组成,则此转换会自动删除标识元素及其任何后继元素的开始和结束标记,如以及任何后代评论和处理说明。
 * 此变换的输出是八位字节流。</p>。
 * 
 * @author Christian Geuer-Pollmann
 * @see com.sun.org.apache.xml.internal.security.utils.Base64
 */
public class TransformBase64Decode extends TransformSpi {

    /** Field implementedTransformURI */
    public static final String implementedTransformURI =
        Transforms.TRANSFORM_BASE64_DECODE;

    /**
     * Method engineGetURI
     *
     * @inheritDoc
     * <p>
     * 
     */
    protected String engineGetURI() {
        return TransformBase64Decode.implementedTransformURI;
    }

    /**
     * Method enginePerformTransform
     *
     * <p>
     *  方法engineGetURI
     * 
     *  @inheritDoc
     * 
     * 
     * @param input
     * @return {@link XMLSignatureInput} as the result of transformation
     * @inheritDoc
     * @throws CanonicalizationException
     * @throws IOException
     * @throws TransformationException
     */
    protected XMLSignatureInput enginePerformTransform(
        XMLSignatureInput input, Transform transformObject
    ) throws IOException, CanonicalizationException, TransformationException {
        return enginePerformTransform(input, null, transformObject);
    }

    protected XMLSignatureInput enginePerformTransform(
        XMLSignatureInput input, OutputStream os, Transform transformObject
    ) throws IOException, CanonicalizationException, TransformationException {
        try {
            if (input.isElement()) {
                Node el = input.getSubNode();
                if (input.getSubNode().getNodeType() == Node.TEXT_NODE) {
                    el = el.getParentNode();
                }
                StringBuilder sb = new StringBuilder();
                traverseElement((Element)el, sb);
                if (os == null) {
                    byte[] decodedBytes = Base64.decode(sb.toString());
                    return new XMLSignatureInput(decodedBytes);
                }
                Base64.decode(sb.toString(), os);
                XMLSignatureInput output = new XMLSignatureInput((byte[])null);
                output.setOutputStream(os);
                return output;
            }

            if (input.isOctetStream() || input.isNodeSet()) {
                if (os == null) {
                    byte[] base64Bytes = input.getBytes();
                    byte[] decodedBytes = Base64.decode(base64Bytes);
                    return new XMLSignatureInput(decodedBytes);
                }
                if (input.isByteArray() || input.isNodeSet()) {
                    Base64.decode(input.getBytes(), os);
                } else {
                    Base64.decode(new BufferedInputStream(input.getOctetStreamReal()), os);
                }
                XMLSignatureInput output = new XMLSignatureInput((byte[])null);
                output.setOutputStream(os);
                return output;
            }

            try {
                //Exceptional case there is current not text case testing this(Before it was a
                //a common case).
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, Boolean.TRUE);
                Document doc =
                    dbf.newDocumentBuilder().parse(input.getOctetStream());

                Element rootNode = doc.getDocumentElement();
                StringBuilder sb = new StringBuilder();
                traverseElement(rootNode, sb);
                byte[] decodedBytes = Base64.decode(sb.toString());
                return new XMLSignatureInput(decodedBytes);
            } catch (ParserConfigurationException e) {
                throw new TransformationException("c14n.Canonicalizer.Exception",e);
            } catch (SAXException e) {
                throw new TransformationException("SAX exception", e);
            }
        } catch (Base64DecodingException e) {
            throw new TransformationException("Base64Decoding", e);
        }
    }

    void traverseElement(org.w3c.dom.Element node, StringBuilder sb) {
        Node sibling = node.getFirstChild();
        while (sibling != null) {
            switch (sibling.getNodeType()) {
            case Node.ELEMENT_NODE:
                traverseElement((Element)sibling, sb);
                break;
            case Node.TEXT_NODE:
                sb.append(((Text)sibling).getData());
            }
            sibling = sibling.getNextSibling();
        }
    }
}
