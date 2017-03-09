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
 * <code>CipherData</code> provides encrypted data. It must either contain the
 * encrypted octet sequence as base64 encoded text of the
 * <code>CipherValue</code> element, or provide a reference to an external
 * location containing the encrypted octet sequence via the
 * <code>CipherReference</code> element.
 * <p>
 * The schema definition is as follows:
 * <xmp>
 * <element name='CipherData' type='xenc:CipherDataType'/>
 * <complexType name='CipherDataType'>
 *     <choice>
 *         <element name='CipherValue' type='base64Binary'/>
 *         <element ref='xenc:CipherReference'/>
 *     </choice>
 * </complexType>
 * </xmp>
 *
 * <p>
 *  <code> CipherData </code>提供加密数据。
 * 它必须包含加密的八位字节序列作为<code> CipherValue </code>元素的base64编码文本,或者通过<code> CipherReference </code>元素提供包含加密的八位
 * 字节序列的外部位置的引用。
 *  <code> CipherData </code>提供加密数据。
 * <p>
 *  模式定义如下：
 * <xmp>
 * <element name='CipherData' type='xenc:CipherDataType'/>
 * <complexType name='CipherDataType'>
 * <choice>
 * <element name='CipherValue' type='base64Binary'/>
 * <element ref='xenc:CipherReference'/>
 * </choice>
 * </complexType>
 * </xmp>
 * 
 * 
 * @author Axl Mattheus
 */
public interface CipherData {

    /** VALUE_TYPE ASN */
    int VALUE_TYPE = 0x00000001;

    /** REFERENCE_TYPE ASN */
    int REFERENCE_TYPE = 0x00000002;

    /**
     * Returns the type of encrypted data contained in the
     * <code>CipherData</code>.
     *
     * <p>
     * 
     * @return <code>VALUE_TYPE</code> if the encrypted data is contained as
     *   <code>CipherValue</code> or <code>REFERENCE_TYPE</code> if the
     *   encrypted data is contained as <code>CipherReference</code>.
     */
    int getDataType();

    /**
     * Returns the cipher value as a base64 encoded <code>byte</code> array.
     *
     * <p>
     *  返回<code> CipherData </code>中包含的加密数据的类型。
     * 
     * 
     * @return the <code>CipherData</code>'s value.
     */
    CipherValue getCipherValue();

    /**
     * Sets the <code>CipherData</code>'s value.
     *
     * <p>
     *  以base64编码的<code> byte </code>数组返回密码值。
     * 
     * 
     * @param value the value of the <code>CipherData</code>.
     * @throws XMLEncryptionException
     */
    void setCipherValue(CipherValue value) throws XMLEncryptionException;

    /**
     * Returns a reference to an external location containing the encrypted
     * octet sequence (<code>byte</code> array).
     *
     * <p>
     *  设置<code> CipherData </code>的值。
     * 
     * 
     * @return the reference to an external location containing the encrypted
     * octet sequence.
     */
    CipherReference getCipherReference();

    /**
     * Sets the <code>CipherData</code>'s reference.
     *
     * <p>
     * 返回对包含加密的八位字节序列(<code> byte </code> array)的外部位置的引用。
     * 
     * 
     * @param reference an external location containing the encrypted octet sequence.
     * @throws XMLEncryptionException
     */
    void setCipherReference(CipherReference reference) throws XMLEncryptionException;
}

