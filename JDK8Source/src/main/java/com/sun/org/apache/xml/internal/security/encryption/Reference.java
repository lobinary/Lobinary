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
 * A wrapper for a pointer from a key value of an <code>EncryptedKey</code> to
 * items encrypted by that key value (<code>EncryptedData</code> or
 * <code>EncryptedKey</code> elements).
 * <p>
 * It is defined as follows:
 * <xmp>
 * <complexType name='ReferenceType'>
 *     <sequence>
 *         <any namespace='##other' minOccurs='0' maxOccurs='unbounded'/>
 *     </sequence>
 *     <attribute name='URI' type='anyURI' use='required'/>
 * </complexType>
 * </xmp>
 *
 * <p>
 *  从<code> EncryptedKey </code>的键值到由该键值加密的项(<code> EncryptedData </code>或<code> EncryptedKey </code>元素)
 * 的指针的包装器。
 * <p>
 *  其定义如下：
 * <xmp>
 * <complexType name='ReferenceType'>
 * <sequence>
 * <any namespace='##other' minOccurs='0' maxOccurs='unbounded'/>
 * </sequence>
 * <attribute name='URI' type='anyURI' use='required'/>
 * </complexType>
 * </xmp>
 * 
 * 
 * @author Axl Mattheus
 * @see ReferenceList
 */
public interface Reference {
    /**
     * Returns the <code>Element</code> tag name for this <code>Reference</code>.
     *
     * <p>
     *  返回此<code>引用</code>的<code>元素</code>标记名称。
     * 
     * 
     * @return the tag name of this <code>Reference</code>.
     */
    String getType();

    /**
     * Returns a <code>URI</code> that points to an <code>Element</code> that
     * were encrypted using the key defined in the enclosing
     * <code>EncryptedKey</code> element.
     *
     * <p>
     *  返回指向使用包含的<code> EncryptedKey </code>元素中定义的键加密的<code> URI </code>,指向<code> Element </code>
     * 
     * 
     * @return an Uniform Resource Identifier that qualifies an
     *   <code>EncryptedType</code>.
     */
    String getURI();

    /**
     * Sets a <code>URI</code> that points to an <code>Element</code> that
     * were encrypted using the key defined in the enclosing
     * <code>EncryptedKey</code> element.
     *
     * <p>
     *  设置指向使用在封装的<code> EncryptedKey </code>元素中定义的密钥加密的<code> Element </code>的<code> URI </code>
     * 
     * 
     * @param uri the Uniform Resource Identifier that qualifies an
     *   <code>EncryptedType</code>.
     */
    void setURI(String uri);

    /**
     * Returns an <code>Iterator</code> over all the child elements contained in
     * this <code>Reference</code> that will aid the recipient in retrieving the
     * <code>EncryptedKey</code> and/or <code>EncryptedData</code> elements.
     * These could include information such as XPath transforms, decompression
     * transforms, or information on how to retrieve the elements from a
     * document storage facility.
     *
     * <p>
     * 对<code>引用</code>中包含的所有子元素返回<code>迭代器</code>,这将有助于接收方检索<code> EncryptedKey </code>和/ / code>元素。
     * 这些可以包括诸如XPath变换,解压缩变换或关于如何从文档存储设备检索元素的信息。
     * 
     * 
     * @return child elements.
     */
    Iterator<Element> getElementRetrievalInformation();

    /**
     * Adds retrieval information.
     *
     * <p>
     *  添加检索信息。
     * 
     * 
     * @param info
     */
    void addElementRetrievalInformation(Element info);

    /**
     * Removes the specified retrieval information.
     *
     * <p>
     *  删除指定的检索信息。
     * 
     * @param info
     */
    void removeElementRetrievalInformation(Element info);
}
