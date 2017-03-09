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
 * Additional information items concerning the generation of the
 * <code>EncryptedData</code> or <code>EncryptedKey</code> can be placed in an
 * <code>EncryptionProperty</code> element (e.g., date/time stamp or the serial
 * number of cryptographic hardware used during encryption). The Target
 * attribute identifies the <code>EncryptedType</code> structure being
 * described. anyAttribute permits the inclusion of attributes from the XML
 * namespace to be included (i.e., <code>xml:space</code>,
 * <code>xml:lang</code>, and <code>xml:base</code>).
 * <p>
 * It is defined as follows:
 * <xmp>
 * <element name='EncryptionProperty' type='xenc:EncryptionPropertyType'/>
 * <complexType name='EncryptionPropertyType' mixed='true'>
 *     <choice maxOccurs='unbounded'>
 *         <any namespace='##other' processContents='lax'/>
 *     </choice>
 *     <attribute name='Target' type='anyURI' use='optional'/>
 *     <attribute name='Id' type='ID' use='optional'/>
 *     <anyAttribute namespace="http://www.w3.org/XML/1998/namespace"/>
 * </complexType>
 * </xmp>
 *
 * <p>
 *  关于生成<code> EncryptedData </code>或<code> EncryptedKey </code>的附加信息项可以放置在<code> EncryptionProperty </code>
 * 元素中(例如,日期/时间戳或序列号在加密期间使用的加密硬件)。
 *  Target属性标识正在描述的<code> EncryptedType </code>结构。
 *  anyAttribute允许包含来自要包含的XML命名空间的属性(即<code> xml：space </code>,<code> xml：lang </code>和<code> xml：base </code>
 * ) 。
 *  Target属性标识正在描述的<code> EncryptedType </code>结构。
 * <p>
 *  其定义如下：
 * <xmp>
 * <element name='EncryptionProperty' type='xenc:EncryptionPropertyType'/>
 * <complexType name='EncryptionPropertyType' mixed='true'>
 * <choice maxOccurs='unbounded'>
 * <any namespace='##other' processContents='lax'/>
 * </choice>
 * <attribute name='Target' type='anyURI' use='optional'/>
 * <attribute name='Id' type='ID' use='optional'/>
 * <anyAttribute namespace="http://www.w3.org/XML/1998/namespace"/>
 * </complexType>
 * </xmp>
 * 
 * 
 * @author Axl Mattheus
 */
public interface EncryptionProperty {

    /**
     * Returns the <code>EncryptedType</code> being described.
     *
     * <p>
     * 返回正在描述的<code> EncryptedType </code>。
     * 
     * 
     * @return the <code>EncryptedType</code> being described by this
     *   <code>EncryptionProperty</code>.
     */
    String getTarget();

    /**
     * Sets the target.
     *
     * <p>
     *  设置目标。
     * 
     * 
     * @param target
     */
    void setTarget(String target);

    /**
     * Returns the id of the <CODE>EncryptionProperty</CODE>.
     *
     * <p>
     *  返回<CODE> EncryptionProperty </CODE>的ID。
     * 
     * 
     * @return the id.
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
     * Returns the attribute's value in the <code>xml</code> namespace.
     *
     * <p>
     *  返回<code> xml </code>命名空间中属性的值。
     * 
     * 
     * @param attribute
     * @return the attribute's value.
     */
    String getAttribute(String attribute);

    /**
     * Set the attribute value.
     *
     * <p>
     *  设置属性值。
     * 
     * 
     * @param attribute the attribute's name.
     * @param value the attribute's value.
     */
    void setAttribute(String attribute, String value);

    /**
     * Returns the properties of the <CODE>EncryptionProperty</CODE>.
     *
     * <p>
     *  返回<CODE> EncryptionProperty </CODE>的属性。
     * 
     * 
     * @return an <code>Iterator</code> over all the additional encryption
     *   information contained in this class.
     */
    Iterator<Element> getEncryptionInformation();

    /**
     * Adds encryption information.
     *
     * <p>
     *  添加加密信息。
     * 
     * 
     * @param information the additional encryption information.
     */
    void addEncryptionInformation(Element information);

    /**
     * Removes encryption information.
     *
     * <p>
     *  删除加密信息。
     * 
     * @param information the information to remove.
     */
    void removeEncryptionInformation(Element information);
}
