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
import org.w3c.dom.Element;

/**
 * <code>EncryptionMethod</code> describes the encryption algorithm applied to
 * the cipher data. If the element is absent, the encryption algorithm must be
 * known by the recipient or the decryption will fail.
 * <p>
 * It is defined as follows:
 * <xmp>
 * <complexType name='EncryptionMethodType' mixed='true'>
 *     <sequence>
 *         <element name='KeySize' minOccurs='0' type='xenc:KeySizeType'/>
 *         <element name='OAEPparams' minOccurs='0' type='base64Binary'/>
 *         <any namespace='##other' minOccurs='0' maxOccurs='unbounded'/>
 *     </sequence>
 *     <attribute name='Algorithm' type='anyURI' use='required'/>
 * </complexType>
 * </xmp>
 *
 * <p>
 *  <code> EncryptionMethod </code>描述了应用于密码数据的加密算法。如果该元素不存在,则加密算法必须为接收者所知,否则解密将失败。
 * <p>
 *  其定义如下：
 * <xmp>
 * <complexType name='EncryptionMethodType' mixed='true'>
 * <sequence>
 * <element name='KeySize' minOccurs='0' type='xenc:KeySizeType'/>
 * <element name='OAEPparams' minOccurs='0' type='base64Binary'/>
 * <any namespace='##other' minOccurs='0' maxOccurs='unbounded'/>
 * </sequence>
 * <attribute name='Algorithm' type='anyURI' use='required'/>
 * </complexType>
 * </xmp>
 * 
 * 
 * @author Axl Mattheus
 */
public interface EncryptionMethod {
    /**
     * Returns the algorithm applied to the cipher data.
     *
     * <p>
     *  返回应用于加密数据的算法。
     * 
     * 
     * @return the encryption algorithm.
     */
    String getAlgorithm();

    /**
     * Returns the key size of the key of the algorithm applied to the cipher
     * data.
     *
     * <p>
     *  返回应用于密码数据的算法的密钥的密钥大小。
     * 
     * 
     * @return the key size.
     */
    int getKeySize();

    /**
     * Sets the size of the key of the algorithm applied to the cipher data.
     *
     * <p>
     *  设置应用于加密数据的算法的密钥大小。
     * 
     * 
     * @param size the key size.
     */
    void setKeySize(int size);

    /**
     * Returns the OAEP parameters of the algorithm applied applied to the
     * cipher data.
     *
     * <p>
     *  返回应用于加密数据的算法的OAEP参数。
     * 
     * 
     * @return the OAEP parameters.
     */
    byte[] getOAEPparams();

    /**
     * Sets the OAEP parameters.
     *
     * <p>
     *  设置OAEP参数。
     * 
     * 
     * @param parameters the OAEP parameters.
     */
    void setOAEPparams(byte[] parameters);

    /**
     * Set the Digest Algorithm to use
     * <p>
     *  设置要使用的摘要算法
     * 
     * 
     * @param digestAlgorithm the Digest Algorithm to use
     */
    void setDigestAlgorithm(String digestAlgorithm);

    /**
     * Get the Digest Algorithm to use
     * <p>
     *  获取Digest算法使用
     * 
     * 
     * @return the Digest Algorithm to use
     */
    String getDigestAlgorithm();

    /**
     * Set the MGF Algorithm to use
     * <p>
     * 设置要使用的MGF算法
     * 
     * 
     * @param mgfAlgorithm the MGF Algorithm to use
     */
    void setMGFAlgorithm(String mgfAlgorithm);

    /**
     * Get the MGF Algorithm to use
     * <p>
     *  获取要使用的MGF算法
     * 
     * 
     * @return the MGF Algorithm to use
     */
    String getMGFAlgorithm();

    /**
     * Returns an iterator over all the additional elements contained in the
     * <code>EncryptionMethod</code>.
     *
     * <p>
     *  返回包含在<code> EncryptionMethod </code>中的所有其他元素的迭代器。
     * 
     * 
     * @return an <code>Iterator</code> over all the additional information
     *   about the <code>EncryptionMethod</code>.
     */
    Iterator<Element> getEncryptionMethodInformation();

    /**
     * Adds encryption method information.
     *
     * <p>
     *  添加加密方法信息。
     * 
     * 
     * @param information additional encryption method information.
     */
    void addEncryptionMethodInformation(Element information);

    /**
     * Removes encryption method information.
     *
     * <p>
     *  删除加密方法信息。
     * 
     * @param information the information to remove from the
     *   <code>EncryptionMethod</code>.
     */
    void removeEncryptionMethodInformation(Element information);
}

