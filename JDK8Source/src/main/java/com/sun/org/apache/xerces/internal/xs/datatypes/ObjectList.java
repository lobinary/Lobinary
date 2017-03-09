/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2004,2005 The Apache Software Foundation.
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
 *  版权所有2004,2005 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
package com.sun.org.apache.xerces.internal.xs.datatypes;

import java.util.List;

/**
 * <p>The <code>ObjectList</code> is an immutable ordered collection of
 * <code>Object</code>.</p>
 *
 * <p>
 *  <p> <code> ObjectList </code>是<code> Object </code>的不可变有序集合。</p>
 * 
 * 
 * @author Ankit Pasricha, IBM
 *
 * @version $Id: ObjectList.java,v 1.7 2010-11-01 04:40:31 joehw Exp $
 */
public interface ObjectList extends List {

    /**
     * The number of <code>Object</code>s in the list. The range of
     * valid child object indices is 0 to <code>length-1</code> inclusive.
     * <p>
     *  列表中<code> Object </code>的数量。有效子对象索引的范围是0到<code> length-1 </code>(包括)。
     * 
     */
    public int getLength();

    /**
     * Checks if the <code>Object</code> <code>item</code> is a
     * member of this list.
     * <p>
     *  检查<code> Object </code> <code>项</code>是否为此列表的成员。
     * 
     * 
     * @param item  <code>Object</code> whose presence in this list
     *   is to be tested.
     * @return  True if this list contains the <code>Object</code>
     *   <code>item</code>.
     */
    public boolean contains(Object item);

    /**
     * Returns the <code>index</code>th item in the collection or
     * <code>null</code> if <code>index</code> is greater than or equal to
     * the number of objects in the list. The index starts at 0.
     * <p>
     *  如果<code> index </code>大于或等于列表中的对象数,则返回集合中的<code> index </code>项或<code> null </code>。索引从0开始。
     * 
     * @param index  index into the collection.
     * @return  The <code>Object</code> at the <code>index</code>th
     *   position in the <code>ObjectList</code>, or <code>null</code> if
     *   the index specified is not valid - greater than or equal to the
     *   number of items in the list or less than zero.
     */
    public Object item(int index);

}
