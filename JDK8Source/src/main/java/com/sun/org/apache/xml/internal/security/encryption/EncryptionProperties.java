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

/**
 * <code>EncryptionProperties</code> can hold additional information concerning
 * the generation of the <code>EncryptedData</code> or
 * <code>EncryptedKey</code>. This information is wraped int an
 * <code>EncryptionProperty</code> element. Examples of additional information
 * is e.g., a date/time stamp or the serial number of cryptographic hardware
 * used during encryption).
 * <p>
 * It is defined as follows:
 * <xmp>
 * <element name='EncryptionProperties' type='xenc:EncryptionPropertiesType'/>
 * <complexType name='EncryptionPropertiesType'>
 *     <sequence>
 *         <element ref='xenc:EncryptionProperty' maxOccurs='unbounded'/>
 *     </sequence>
 *     <attribute name='Id' type='ID' use='optional'/>
 * </complexType>
 * </xmp>
 *
 * <p>
 *  <code> EncryptionProperties </code>可以保存有关生成<code> EncryptedData </code>或<code> EncryptedKey </code>的
 * 附加信息。
 * 此信息包含在<code> EncryptionProperty </code>元素中。附加信息的示例是例如日期/时间戳或在加密期间使用的加密硬件的序列号)。
 * <p>
 *  其定义如下：
 * <xmp>
 * <element name='EncryptionProperties' type='xenc:EncryptionPropertiesType'/>
 * <complexType name='EncryptionPropertiesType'>
 * <sequence>
 * <element ref='xenc:EncryptionProperty' maxOccurs='unbounded'/>
 * </sequence>
 * <attribute name='Id' type='ID' use='optional'/>
 * </complexType>
 * </xmp>
 * 
 * 
 * @author Axl Mattheus
 */
public interface EncryptionProperties {

    /**
     * Returns the <code>EncryptionProperties</code>' id.
     *
     * <p>
     * 
     * @return the id.
     */
    String getId();

    /**
     * Sets the id.
     *
     * <p>
     *  返回<code> EncryptionProperties </code>'id。
     * 
     * 
     * @param id the id.
     */
    void setId(String id);

    /**
     * Returns an <code>Iterator</code> over all the
     * <code>EncryptionPropterty</code> elements contained in this
     * <code>EncryptionProperties</code>.
     *
     * <p>
     *  设置ID。
     * 
     * 
     * @return an <code>Iterator</code> over all the encryption properties.
     */
    Iterator<EncryptionProperty> getEncryptionProperties();

    /**
     * Adds an <code>EncryptionProperty</code>.
     *
     * <p>
     *  在<code> EncryptionProperties </code>中包含的所有<code> EncryptionPropterty </code>元素上返回<code>迭代器</code>。
     * 
     * 
     * @param property
     */
    void addEncryptionProperty(EncryptionProperty property);

    /**
     * Removes the specified <code>EncryptionProperty</code>.
     *
     * <p>
     * 添加<code> EncryptionProperty </code>。
     * 
     * 
     * @param property
     */
    void removeEncryptionProperty(EncryptionProperty property);
}

