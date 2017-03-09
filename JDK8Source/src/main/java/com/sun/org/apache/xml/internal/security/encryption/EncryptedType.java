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

import com.sun.org.apache.xml.internal.security.keys.KeyInfo;

/**
 * EncryptedType is the abstract type from which <code>EncryptedData</code> and
 * <code>EncryptedKey</code> are derived. While these two latter element types
 * are very similar with respect to their content models, a syntactical
 * distinction is useful to processing.
 * <p>
 * Its schema definition is as follows:
 * <xmp>
 * <complexType name='EncryptedType' abstract='true'>
 *     <sequence>
 *         <element name='EncryptionMethod' type='xenc:EncryptionMethodType'
 *             minOccurs='0'/>
 *         <element ref='ds:KeyInfo' minOccurs='0'/>
 *         <element ref='xenc:CipherData'/>
 *         <element ref='xenc:EncryptionProperties' minOccurs='0'/>
 *     </sequence>
 *     <attribute name='Id' type='ID' use='optional'/>
 *     <attribute name='Type' type='anyURI' use='optional'/>
 *     <attribute name='MimeType' type='string' use='optional'/>
 *     <attribute name='Encoding' type='anyURI' use='optional'/>
 * </complexType>
 * </xmp>
 *
 * <p>
 *  EncryptedType是从中派生<code> EncryptedData </code>和<code> EncryptedKey </code>的抽象类型。
 * 虽然这两个后面的元素类型在它们的内容模型方面非常相似,但是语法上的区别对于处理是有用的。
 * <p>
 *  其模式定义如下：
 * <xmp>
 * <complexType name='EncryptedType' abstract='true'>
 * <sequence>
 *  <element name ='EncryptionMethod'type ='xenc：EncryptionMethodType'
 * minOccurs='0'/>
 * <element ref='ds:KeyInfo' minOccurs='0'/>
 * <element ref='xenc:CipherData'/>
 * <element ref='xenc:EncryptionProperties' minOccurs='0'/>
 * </sequence>
 * <attribute name='Id' type='ID' use='optional'/>
 * <attribute name='Type' type='anyURI' use='optional'/>
 * <attribute name='MimeType' type='string' use='optional'/>
 * <attribute name='Encoding' type='anyURI' use='optional'/>
 * </complexType>
 * </xmp>
 * 
 * 
 * @author Axl Mattheus
 */
public interface EncryptedType {

    /**
     * Returns a <code>String</code> providing for the standard method of
     * assigning an id to the element within the document context.
     *
     * <p>
     *  返回<code> String </code>,它提供了为文档上下文中的元素分配id的标准方法。
     * 
     * 
     * @return the id for the <code>EncryptedType</code>.
     */
    String getId();

    /**
     * Sets the id.
     *
     * <p>
     *  设置ID。
     * 
     * 
     * @param id
     */
    void setId(String id);

    /**
     * Returns an <code>URI</code> identifying type information about the
     * plaintext form of the encrypted content. While optional, this
     * specification takes advantage of it for mandatory processing described in
     * Processing Rules: Decryption (section 4.2). If the
     * <code>EncryptedData</code> element contains data of Type 'element' or
     * element 'content', and replaces that data in an XML document context, it
     * is strongly recommended the Type attribute be provided. Without this
     * information, the decryptor will be unable to automatically restore the
     * XML document to its original cleartext form.
     *
     * <p>
     * 返回标识加密内容的明文形式的类型信息的<code> URI </code>。虽然是可选的,本规范利用它用于处理规则：解密(第4.2节)中描述的强制处理。
     * 如果<code> EncryptedData </code>元素包含Type'element'或element'content'的数据,并且在XML文档上下文中替换该数据,强烈建议提供Type属性。
     * 没有此信息,解密器将无法自动将XML文档恢复为其原始明文形式。
     * 
     * 
     * @return the identifier for the type of information in plaintext form of
     *   encrypted content.
     */
    String getType();

    /**
     * Sets the type.
     *
     * <p>
     *  设置类型。
     * 
     * 
     * @param type an <code>URI</code> identifying type information about the
     *   plaintext form of the encrypted content.
     */
    void setType(String type);

    /**
     * Returns a <code>String</code> which describes the media type of the data
     * which has been encrypted. The value of this attribute has values defined
     * by [MIME]. For example, if the data that is encrypted is a base64 encoded
     * PNG, the transfer Encoding may be specified as
     * 'http://www.w3.org/2000/09/xmldsig#base64' and the MimeType as
     * 'image/png'.
     * <br>
     * This attribute is purely advisory; no validation of the MimeType
     * information is required and it does not indicate the encryption
     * application must do any additional processing. Note, this information may
     * not be necessary if it is already bound to the identifier in the Type
     * attribute. For example, the Element and Content types defined in this
     * specification are always UTF-8 encoded text.
     *
     * <p>
     *  返回<code> String </code>,它描述了已加密数据的媒体类型。此属性的值具有由[MIME]定义的值。
     * 例如,如果被加密的数据是base64编码的PNG,则传输Encoding可以被指定为"http://www.w3.org/2000/09/xmldsig#base64",MimeType被指定为"ima
     * ge / png" 。
     *  返回<code> String </code>,它描述了已加密数据的媒体类型。此属性的值具有由[MIME]定义的值。
     * <br>
     *  此属性纯粹是建议性的;不需要验证MimeType信息,并且它不指示加密应用程序必须进行任何额外的处理。注意,如果已经绑定到Type属性中的标识符,则此信息可能不是必需的。
     * 例如,本规范中定义的元素和内容类型始终是UTF-8编码的文本。
     * 
     * 
     * @return the media type of the data which was encrypted.
     */
    String getMimeType();

    /**
     * Sets the mime type.
     *
     * <p>
     *  设置MIME类型。
     * 
     * 
     * @param type a <code>String</code> which describes the media type of the
     *   data which has been encrypted.
     */
    void setMimeType(String type);

    /**
     * Return an <code>URI</code> representing the encoding of the
     * <code>EncryptedType</code>.
     *
     * <p>
     *  返回代表<code> EncryptedType </code>的编码的<code> URI </code>。
     * 
     * 
     * @return the encoding of this <code>EncryptedType</code>.
     */
    String getEncoding();

    /**
     * Sets the <code>URI</code> representing the encoding of the
     * <code>EncryptedType</code>.
     *
     * <p>
     * 设置代表<code> EncryptedType </code>的编码的<code> URI </code>。
     * 
     * 
     * @param encoding
     */
    void setEncoding(String encoding);

    /**
     * Returns an <code>EncryptionMethod</code> that describes the encryption
     * algorithm applied to the cipher data. If the element is absent, the
     * encryption algorithm must be known by the recipient or the decryption
     * will fail.
     *
     * <p>
     *  返回描述应用于加密数据的加密算法的<code> EncryptionMethod </code>。如果该元素不存在,则加密算法必须为接收者所知,否则解密将失败。
     * 
     * 
     * @return the method used to encrypt the cipher data.
     */
    EncryptionMethod getEncryptionMethod();

    /**
     * Sets the <code>EncryptionMethod</code> used to encrypt the cipher data.
     *
     * <p>
     *  设置用于加密密码数据的<code> EncryptionMethod </code>。
     * 
     * 
     * @param method the <code>EncryptionMethod</code>.
     */
    void setEncryptionMethod(EncryptionMethod method);

    /**
     * Returns the <code>ds:KeyInfo</code>, that carries information about the
     * key used to encrypt the data. Subsequent sections of this specification
     * define new elements that may appear as children of
     * <code>ds:KeyInfo</code>.
     *
     * <p>
     *  返回<code> ds：KeyInfo </code>,其中包含有关用于加密数据的密钥的信息。本规范的后续章节定义了可能出现为<code> ds：KeyInfo </code>的子元素。
     * 
     * 
     * @return information about the key that encrypted the cipher data.
     */
    KeyInfo getKeyInfo();

    /**
     * Sets the encryption key information.
     *
     * <p>
     *  设置加密密钥信息。
     * 
     * 
     * @param info the <code>ds:KeyInfo</code>, that carries information about
     *   the key used to encrypt the data.
     */
    void setKeyInfo(KeyInfo info);

    /**
     * Returns the <code>CipherReference</code> that contains the
     * <code>CipherValue</code> or <code>CipherReference</code> with the
     * encrypted data.
     *
     * <p>
     *  返回包含加密数据的<code> CipherValue </code>或<code> CipherReference </code>的<code> CipherReference </code>
     * 
     * 
     * @return the cipher data for the encrypted type.
     */
    CipherData getCipherData();

    /**
     * Returns additional information concerning the generation of the
     * <code>EncryptedType</code>.
     *
     * <p>
     *  返回有关生成<code> EncryptedType </code>的其他信息。
     * 
     * 
     * @return information relating to the generation of the
     *   <code>EncryptedType</code>.
     */
    EncryptionProperties getEncryptionProperties();

    /**
     * Sets the <code>EncryptionProperties</code> that supplies additional
     * information about the generation of the <code>EncryptedType</code>.
     *
     * <p>
     *  设置提供有关生成<code> EncryptedType </code>的附加信息的<code> EncryptionProperties </code>。
     * 
     * @param properties
     */
    void setEncryptionProperties(EncryptionProperties properties);
}

