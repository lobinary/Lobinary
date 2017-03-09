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
package com.sun.org.apache.xml.internal.security.encryption;

import org.w3c.dom.Attr;

/**
 * <code>CipherReference</code> identifies a source which, when processed,
 * yields the encrypted octet sequence.
 * <p>
 * The actual value is obtained as follows. The <code>CipherReference URI</code>
 * contains an identifier that is dereferenced. Should the
 * Transforms, the data resulting from dereferencing the <code>URI</code> is
 * transformed as specified so as to yield the intended cipher value. For
 * example, if the value is base64 encoded within an XML document; the
 * transforms could specify an XPath expression followed by a base64 decoding so
 * as to extract the octets.
 * <p>
 * The syntax of the <code>URI</code> and Transforms is similar to that of
 * [XML-DSIG]. However, there is a difference between signature and encryption
 * processing. In [XML-DSIG] both generation and validation processing start
 * with the same source data and perform that transform in the same order. In
 * encryption, the decryptor has only the cipher data and the specified
 * transforms are enumerated for the decryptor, in the order necessary to obtain
 * the octets. Consequently, because it has different semantics Transforms is in
 * the &xenc; namespace.
 * <p>
 * The schema definition is as follows:
 * <xmp>
 * <element name='CipherReference' type='xenc:CipherReferenceType'/>
 * <complexType name='CipherReferenceType'>
 *     <sequence>
 *         <element name='Transforms' type='xenc:TransformsType' minOccurs='0'/>
 *     </sequence>
 *     <attribute name='URI' type='anyURI' use='required'/>
 * </complexType>
 * </xmp>
 *
 * <p>
 *  <code> CipherReference </code>标识在处理时产生加密的八位字节序列的源。
 * <p>
 *  实际值如下获得。 <code> CipherReference URI </code>包含被取消引用的标识符。
 * 如果转换,从解除引用<code> URI </code>产生的数据按指定进行转换,以便产生预期的密码值。
 * 例如,如果值在XML文档中进行base64编码;变换可以指定XPath表达式,随后是base64解码,以便提取八位字节。
 * <p>
 * <code> URI </code>和Transforms的语法类似于[XML-DSIG]的语法。然而,签名和加密处理之间存在差异。
 * 在[XML-DSIG]中,生成和验证处理都从相同的源数据开始,并以相同的顺序执行该转换。在加密中,解密器仅具有密码数据,并且以获得八位字节所必需的顺序枚举解密器的指定变换。
 * 因此,因为它有不同的语义转换是在&xenc;命名空间。
 * <p>
 *  模式定义如下：
 * <xmp>
 * <element name='CipherReference' type='xenc:CipherReferenceType'/>
 * <complexType name='CipherReferenceType'>
 * <sequence>
 * <element name='Transforms' type='xenc:TransformsType' minOccurs='0'/>
 * </sequence>
 * 
 * @author Axl Mattheus
 */
public interface CipherReference {
    /**
     * Returns an <code>URI</code> that contains an identifier that should be
     * dereferenced.
     * <p>
     * <attribute name='URI' type='anyURI' use='required'/>
     * </complexType>
     * </xmp>
     * 
     * 
     * @return an <code>URI</code> that contains an identifier that should be
     * dereferenced.
     */
    String getURI();

    /**
     * Gets the URI as an Attribute node.  Used to meld the CipherReference
     * with the XMLSignature ResourceResolvers
     * <p>
     *  返回包含应解除引用的标识符的<code> URI </code>。
     * 
     * 
     * @return the URI as an Attribute node
     */
    Attr getURIAsAttr();

    /**
     * Returns the <code>Transforms</code> that specifies how to transform the
     * <code>URI</code> to yield the appropriate cipher value.
     *
     * <p>
     *  获取URI作为属性节点。用于将CipherReference与XMLSignature ResourceResolvers混合
     * 
     * 
     * @return the transform that specifies how to transform the reference to
     *   yield the intended cipher value.
     */
    Transforms getTransforms();

    /**
     * Sets the <code>Transforms</code> that specifies how to transform the
     * <code>URI</code> to yield the appropriate cipher value.
     *
     * <p>
     *  返回指定如何转换<code> URI </code>以生成适当的密码值的<code> Transforms </code>。
     * 
     * 
     * @param transforms the set of <code>Transforms</code> that specifies how
     *   to transform the reference to yield the intended cipher value.
     */
    void setTransforms(Transforms transforms);
}

