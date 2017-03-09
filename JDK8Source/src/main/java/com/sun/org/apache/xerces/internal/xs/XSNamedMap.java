/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2003,2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  版权所有2003,2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.xs;

import java.util.Map;

/**
 * Objects implementing the <code>XSNamedMap</code> interface are used to
 * represent immutable collections of XML Schema components that can be
 * accessed by name. Note that <code>XSNamedMap</code> does not inherit from
 * <code>XSObjectList</code>. The <code>XSObject</code>s in
 * <code>XSNamedMap</code>s are not maintained in any particular order.
 * <p>
 *  实现<code> XSNamedMap </code>接口的对象用于表示可以通过名称访问的XML模式组件的不可变集合。
 * 注意,<code> XSNamedMap </code>不会继承<code> XSObjectList </code>。
 *  <code> XSNamedMap </code>中的<code> XSObject </code>不以任何特定顺序维护。
 * 
 */
public interface XSNamedMap extends Map {
    /**
     * The number of <code>XSObjects</code> in the <code>XSObjectList</code>.
     * The range of valid child object indices is 0 to <code>length-1</code>
     * inclusive.
     * <p>
     *  <code> XSObjectList </code>中的<code> XSObjects </code>的数量。有效子对象索引的范围是0到<code> length-1 </code>(包括)。
     * 
     */
    public int getLength();

    /**
     *  Returns the <code>index</code>th item in the collection or
     * <code>null</code> if <code>index</code> is greater than or equal to
     * the number of objects in the list. The index starts at 0.
     * <p>
     *  如果<code> index </code>大于或等于列表中的对象数,则返回集合中的<code> index </code>项或<code> null </code>。索引从0开始。
     * 
     * 
     * @param index  index into the collection.
     * @return  The <code>XSObject</code> at the <code>index</code>th
     *   position in the <code>XSObjectList</code>, or <code>null</code> if
     *   the index specified is not valid.
     */
    public XSObject item(int index);

    /**
     * Retrieves an <code>XSObject</code> specified by local name and
     * namespace URI.
     * <br>Per XML Namespaces, applications must use the value <code>null</code> as the
     * <code>namespace</code> parameter for methods if they wish to specify
     * no namespace.
     * <p>
     * 检索由本地名称和命名空间URI指定的<code> XSObject </code>。
     *  <br>对于每个XML命名空间,如果方法希望指定无命名空间,应用程序必须使用<code> null </code>作为<code> namespace </code>参数。
     * 
     * @param namespace The namespace URI of the <code>XSObject</code> to
     *   retrieve, or <code>null</code> if the <code>XSObject</code> has no
     *   namespace.
     * @param localName The local name of the <code>XSObject</code> to
     *   retrieve.
     * @return A <code>XSObject</code> (of any type) with the specified local
     *   name and namespace URI, or <code>null</code> if they do not
     *   identify any object in this map.
     */
    public XSObject itemByName(String namespace,
                               String localName);

}
