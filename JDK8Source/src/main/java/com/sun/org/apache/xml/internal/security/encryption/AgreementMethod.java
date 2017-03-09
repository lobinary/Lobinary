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

import java.util.Iterator;
import com.sun.org.apache.xml.internal.security.keys.KeyInfo;
import org.w3c.dom.Element;

/**
 * A Key Agreement algorithm provides for the derivation of a shared secret key
 * based on a shared secret computed from certain types of compatible public
 * keys from both the sender and the recipient. Information from the originator
 * to determine the secret is indicated by an optional OriginatorKeyInfo
 * parameter child of an <code>AgreementMethod</code> element while that
 * associated with the recipient is indicated by an optional RecipientKeyInfo. A
 * shared key is derived from this shared secret by a method determined by the
 * Key Agreement algorithm.
 * <p>
 * <b>Note:</b> XML Encryption does not provide an on-line key agreement
 * negotiation protocol. The <code>AgreementMethod</code> element can be used by
 * the originator to identify the keys and computational procedure that were
 * used to obtain a shared encryption key. The method used to obtain or select
 * the keys or algorithm used for the agreement computation is beyond the scope
 * of this specification.
 * <p>
 * The <code>AgreementMethod</code> element appears as the content of a
 * <code>ds:KeyInfo</code> since, like other <code>ds:KeyInfo</code> children,
 * it yields a key. This <code>ds:KeyInfo</code> is in turn a child of an
 * <code>EncryptedData</code> or <code>EncryptedKey</code> element. The
 * Algorithm attribute and KeySize child of the <code>EncryptionMethod</code>
 * element under this <code>EncryptedData</code> or <code>EncryptedKey</code>
 * element are implicit parameters to the key agreement computation. In cases
 * where this <code>EncryptionMethod</code> algorithm <code>URI</code> is
 * insufficient to determine the key length, a KeySize MUST have been included.
 * In addition, the sender may place a KA-Nonce element under
 * <code>AgreementMethod</code> to assure that different keying material is
 * generated even for repeated agreements using the same sender and recipient
 * public keys.
 * <p>
 * If the agreed key is being used to wrap a key, then
 * <code>AgreementMethod</code> would appear inside a <code>ds:KeyInfo</code>
 * inside an <code>EncryptedKey</code> element.
 * <p>
 * The Schema for AgreementMethod is as follows:
 * <xmp>
 * <element name="AgreementMethod" type="xenc:AgreementMethodType"/>
 * <complexType name="AgreementMethodType" mixed="true">
 *     <sequence>
 *         <element name="KA-Nonce" minOccurs="0" type="base64Binary"/>
 *         <!-- <element ref="ds:DigestMethod" minOccurs="0"/> -->
 *         <any namespace="##other" minOccurs="0" maxOccurs="unbounded"/>
 *         <element name="OriginatorKeyInfo" minOccurs="0" type="ds:KeyInfoType"/>
 *         <element name="RecipientKeyInfo" minOccurs="0" type="ds:KeyInfoType"/>
 *     </sequence>
 *     <attribute name="Algorithm" type="anyURI" use="required"/>
 * </complexType>
 * </xmp>
 *
 * <p>
 *  密钥协商算法提供基于从来自发送方和接收方的某些类型的兼容公钥计算的共享秘密的共享秘密密钥的推导。
 * 来自发起者的用于确定秘密的信息由<code> AgreementMethod </code>元素的可选OriginatorKeyInfo参数子表示,而与接收者相关联的参数由可选的RecipientKey
 * Info指示。
 *  密钥协商算法提供基于从来自发送方和接收方的某些类型的兼容公钥计算的共享秘密的共享秘密密钥的推导。共享密钥通过由密钥协商算法确定的方法从该共享秘密中导出。
 * <p>
 * <b>注意：</b> XML加密不提供在线密钥协议协商协议。发起者可以使用<code> AgreementMethod </code>元素来标识用于获得共享加密密钥的密钥和计算过程。
 * 用于获得或选择用于协议计算的密钥或算法的方法超出本说明书的范围。
 * <p>
 *  <code> AgreementMethod </code>元素显示为<code> ds：KeyInfo </code>的内容,因为像其他<code> ds：KeyInfo </code>子代,它产生
 * 一个键。
 * 这个<code> ds：KeyInfo </code>又是<code> EncryptedData </code>或<code> EncryptedKey </code>元素的子代。
 * 此<code> EncryptedData </code>或<code> EncryptedKey </code>元素下的<code> EncryptionMethod </code>元素的Algori
 * thm属性和KeySize子元素是密钥协议计算的隐式参数。
 * 这个<code> ds：KeyInfo </code>又是<code> EncryptedData </code>或<code> EncryptedKey </code>元素的子代。
 * 在这种<code> EncryptionMethod </code>算法<code> URI </code>不足以确定密钥长度的情况下,必须包括KeySize。
 * 此外,发送方可以在<code> AgreementMethod </code>下放置KA-Nonce元素,以确保即使对于使用相同发送方和接收方公钥的重复协议也产生不同的密钥材料。
 * <p>
 *  如果使用约定的密钥来包装密钥,则<code> AgreementMethod </code>将出现在<code> EncryptedKey </code>元素内的<code> ds：KeyInfo </code>
 * 。
 * <p>
 *  AgreementMethod的Schema如下：
 * <xmp>
 * <element name="AgreementMethod" type="xenc:AgreementMethodType"/>
 * <complexType name="AgreementMethodType" mixed="true">
 * <sequence>
 * <element name="KA-Nonce" minOccurs="0" type="base64Binary"/>
 * <！ -  <element ref ="ds：DigestMethod"minOccurs ="0"/>  - >
 * <any namespace="##other" minOccurs="0" maxOccurs="unbounded"/>
 * <element name="OriginatorKeyInfo" minOccurs="0" type="ds:KeyInfoType"/>
 * <element name="RecipientKeyInfo" minOccurs="0" type="ds:KeyInfoType"/>
 * </sequence>
 * <attribute name="Algorithm" type="anyURI" use="required"/>
 * </complexType>
 * 
 * @author Axl Mattheus
 */
public interface AgreementMethod {

    /**
     * Returns a <code>byte</code> array.
     * <p>
     * </xmp>
     * 
     * 
     * @return a <code>byte</code> array.
     */
    byte[] getKANonce();

    /**
     * Sets the KANonce.jj
     * <p>
     *  返回一个<code> byte </code>数组。
     * 
     * 
     * @param kanonce
     */
    void setKANonce(byte[] kanonce);

    /**
     * Returns additional information regarding the <code>AgreementMethod</code>.
     * <p>
     *  设置KANonce.jj
     * 
     * 
     * @return additional information regarding the <code>AgreementMethod</code>.
     */
    Iterator<Element> getAgreementMethodInformation();

    /**
     * Adds additional <code>AgreementMethod</code> information.
     *
     * <p>
     *  返回有关<code> AgreementMethod </code>的其他信息。
     * 
     * 
     * @param info a <code>Element</code> that represents additional information
     * specified by
     *   <xmp>
     *     <any namespace="##other" minOccurs="0" maxOccurs="unbounded"/>
     *   </xmp>
     */
    void addAgreementMethodInformation(Element info);

    /**
     * Removes additional <code>AgreementMethod</code> information.
     *
     * <p>
     *  添加其他<code> AgreementMethod </code>信息。
     * 
     * 
     * @param info a <code>Element</code> that represents additional information
     * specified by
     *   <xmp>
     *     <any namespace="##other" minOccurs="0" maxOccurs="unbounded"/>
     *   </xmp>
     */
    void revoveAgreementMethodInformation(Element info);

    /**
     * Returns information relating to the originator's shared secret.
     *
     * <p>
     *  删除其他<code> AgreementMethod </code>信息。
     * 
     * 
     * @return information relating to the originator's shared secret.
     */
    KeyInfo getOriginatorKeyInfo();

    /**
     * Sets the information relating to the originator's shared secret.
     *
     * <p>
     *  返回与发起者的共享密钥相关的信息。
     * 
     * 
     * @param keyInfo information relating to the originator's shared secret.
     */
    void setOriginatorKeyInfo(KeyInfo keyInfo);

    /**
     * Returns information relating to the recipient's shared secret.
     *
     * <p>
     *  设置与发起方共享密钥相关的信息。
     * 
     * 
     * @return information relating to the recipient's shared secret.
     */
    KeyInfo getRecipientKeyInfo();

    /**
     * Sets the information relating to the recipient's shared secret.
     *
     * <p>
     *  返回与收件人的共享密钥相关的信息。
     * 
     * 
     * @param keyInfo information relating to the recipient's shared secret.
     */
    void setRecipientKeyInfo(KeyInfo keyInfo);

    /**
     * Returns the algorithm URI of this <code>CryptographicMethod</code>.
     *
     * <p>
     *  设置与收件人的共享密钥相关的信息。
     * 
     * 
     * @return the algorithm URI of this <code>CryptographicMethod</code>
     */
    String getAlgorithm();
}
