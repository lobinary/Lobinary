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

/**
 * The <code>EncryptedKey</code> element is used to transport encryption keys
 * from the originator to a known recipient(s). It may be used as a stand-alone
 * XML document, be placed within an application document, or appear inside an
 * <code>EncryptedData</code> element as a child of a <code>ds:KeyInfo</code>
 * element. The key value is always encrypted to the recipient(s). When
 * <code>EncryptedKey</code> is decrypted the resulting octets are made
 * available to the <code>EncryptionMethod</code> algorithm without any
 * additional processing.
 * <p>
 * Its schema definition is as follows:
 * <xmp>
 * <element name='EncryptedKey' type='xenc:EncryptedKeyType'/>
 * <complexType name='EncryptedKeyType'>
 *     <complexContent>
 *         <extension base='xenc:EncryptedType'>
 *             <sequence>
 *                 <element ref='xenc:ReferenceList' minOccurs='0'/>
 *                 <element name='CarriedKeyName' type='string' minOccurs='0'/>
 *             </sequence>
 *             <attribute name='Recipient' type='string' use='optional'/>
 *         </extension>
 *     </complexContent>
 * </complexType>
 * </xmp>
 *
 * <p>
 *  <code> EncryptedKey </code>元素用于将加密密钥从始发者传输到已知接收者。
 * 它可以用作独立的XML文档,放置在应用程序文档中,或作为<code> ds：KeyInfo </code>元素的子代出现在<code> EncryptedData </code>元素中。
 * 密钥值始终加密到收件人。当<code> EncryptedKey </code>被解密时,所得到的八位字节可用于<code> EncryptionMethod </code>算法,无需任何额外的处理。
 * <p>
 *  其模式定义如下：
 * <xmp>
 * <element name='EncryptedKey' type='xenc:EncryptedKeyType'/>
 * <complexType name='EncryptedKeyType'>
 * <complexContent>
 * <extension base='xenc:EncryptedType'>
 * <sequence>
 * <element ref='xenc:ReferenceList' minOccurs='0'/>
 * <element name='CarriedKeyName' type='string' minOccurs='0'/>
 * </sequence>
 * <attribute name='Recipient' type='string' use='optional'/>
 * </extension>
 * </complexContent>
 * </complexType>
 * </xmp>
 * 
 * 
 * @author Axl Mattheus
 */
public interface EncryptedKey extends EncryptedType {

    /**
     * Returns a hint as to which recipient this encrypted key value is intended for.
     *
     * <p>
     * 返回此加密密钥值要用于哪个收件人的提示。
     * 
     * 
     * @return the recipient of the <code>EncryptedKey</code>.
     */
    String getRecipient();

    /**
     * Sets the recipient for this <code>EncryptedKey</code>.
     *
     * <p>
     *  设置此<code> EncryptedKey </code>的收件人。
     * 
     * 
     * @param recipient the recipient for this <code>EncryptedKey</code>.
     */
    void setRecipient(String recipient);

    /**
     * Returns pointers to data and keys encrypted using this key. The reference
     * list may contain multiple references to <code>EncryptedKey</code> and
     * <code>EncryptedData</code> elements. This is done using
     * <code>KeyReference</code> and <code>DataReference</code> elements
     * respectively.
     *
     * <p>
     *  返回使用此键加密的数据和密钥的指针。引用列表可以包含对<code> EncryptedKey </code>和<code> EncryptedData </code>元素的多个引用。
     * 这分别使用<code> KeyReference </code>和<code> DataReference </code>元素来完成。
     * 
     * 
     * @return an <code>Iterator</code> over all the <code>ReferenceList</code>s
     *   contained in this <code>EncryptedKey</code>.
     */
    ReferenceList getReferenceList();

    /**
     * Sets the <code>ReferenceList</code> to the <code>EncryptedKey</code>.
     *
     * <p>
     *  将<code> ReferenceList </code>设置为<code> EncryptedKey </code>。
     * 
     * 
     * @param list a list of pointers to data elements encrypted using this key.
     */
    void setReferenceList(ReferenceList list);

    /**
     * Returns a user readable name with the key value. This may then be used to
     * reference the key using the <code>ds:KeyName</code> element within
     * <code>ds:KeyInfo</code>. The same <code>CarriedKeyName</code> label,
     * unlike an ID type, may occur multiple times within a single document. The
     * value of the key is to be the same in all <code>EncryptedKey</code>
     * elements identified with the same <code>CarriedKeyName</code> label
     * within a single XML document.
     * <br>
     * <b>Note</b> that because whitespace is significant in the value of
     * the <code>ds:KeyName</code> element, whitespace is also significant in
     * the value of the <code>CarriedKeyName</code> element.
     *
     * <p>
     *  使用键值返回用户可读的名称。然后可以使用<code> ds：KeyInfo </code>中的<code> ds：KeyName </code>元素来引用密钥。
     * 与单个文档中的ID类型不同,相同的<code> CarriedKeyName </code>标签可能会多次出现。
     * 在单个XML文档中用相同的<code> CarriedKeyName </code>标签标识的所有<code> EncryptedKey </code>元素中,密钥的值相同。
     * <br>
     *  <b>注意</b>,因为空格在<code> ds：KeyName </code>元素的值中是重要的,空格在<code> CarriedKeyName </code>元素的值中也很重要。
     * 
     * @return over all the carried names contained in
     *   this <code>EncryptedKey</code>.
     */
    String getCarriedName();

    /**
     * Sets the carried name.
     *
     * <p>
     * 
     * 
     * @param name the carried name.
     */
    void setCarriedName(String name);
}

