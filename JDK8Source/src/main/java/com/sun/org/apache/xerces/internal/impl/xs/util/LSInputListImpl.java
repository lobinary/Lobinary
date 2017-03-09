/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  根据一个或多个贡献者许可协议授予Apache软件基金会(ASF)。有关版权所有权的其他信息,请参阅随此作品分发的NOTICE文件。
 *  ASF根据Apache许可证2.0版("许可证")将此文件授予您;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本。
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.impl.xs.util;

import java.lang.reflect.Array;
import java.util.AbstractList;

import com.sun.org.apache.xerces.internal.xs.LSInputList;
import org.w3c.dom.ls.LSInput;

/**
 * Contains a list of LSInputs.
 *
 * @xerces.internal
 *
 * <p>
 *  包含LSInputs的列表。
 * 
 *  @ xerces.internal
 * 
 * 
 * @author Michael Glavassevich, IBM
 *
 * @version $Id: LSInputListImpl.java,v 1.2 2010-10-26 23:01:04 joehw Exp $
 */
public final class LSInputListImpl extends AbstractList implements LSInputList {

    /**
     * An immutable empty list.
     * <p>
     *  一个不可变的空列表。
     * 
     */
    public static final LSInputListImpl EMPTY_LIST = new LSInputListImpl(new LSInput[0], 0);

    // The array to hold all data
    private final LSInput[] fArray;
    // Number of elements in this list
    private final int fLength;

    /**
     * Construct an LSInputList implementation
     *
     * <p>
     *  构造LSInputList实现
     * 
     * 
     * @param array     the data array
     * @param length    the number of elements
     */
    public LSInputListImpl(LSInput[] array, int length) {
        fArray = array;
        fLength = length;
    }

    /**
     * The number of <code>LSInput</code>s in the list. The range of valid
     * child object indices is 0 to <code>length-1</code> inclusive.
     * <p>
     *  列表中<code> LSInput </code>的数量。有效子对象索引的范围是0到<code> length-1 </code>(包括)。
     * 
     */
    public int getLength() {
        return fLength;
    }

    /**
     * Returns the <code>index</code>th item in the collection or
     * <code>null</code> if <code>index</code> is greater than or equal to
     * the number of objects in the list. The index starts at 0.
     * <p>
     *  如果<code> index </code>大于或等于列表中的对象数,则返回集合中的<code> index </code>项或<code> null </code>。索引从0开始。
     * 
     * 
     * @param index  index into the collection.
     * @return  The <code>LSInput</code> at the <code>index</code>th
     *   position in the <code>LSInputList</code>, or <code>null</code> if
     *   the index specified is not valid.
     */
    public LSInput item(int index) {
        if (index < 0 || index >= fLength) {
            return null;
        }
        return fArray[index];
    }

    /*
     * List methods
     * <p>
     *  列表方法
     */

    public Object get(int index) {
        if (index >= 0 && index < fLength) {
            return fArray[index];
        }
        throw new IndexOutOfBoundsException("Index: " + index);
    }

    public int size() {
        return getLength();
    }

    public Object[] toArray() {
        Object[] a = new Object[fLength];
        toArray0(a);
        return a;
    }

    public Object[] toArray(Object[] a) {
        if (a.length < fLength) {
            Class arrayClass = a.getClass();
            Class componentType = arrayClass.getComponentType();
            a = (Object[]) Array.newInstance(componentType, fLength);
        }
        toArray0(a);
        if (a.length > fLength) {
            a[fLength] = null;
        }
        return a;
    }

    private void toArray0(Object[] a) {
        if (fLength > 0) {
            System.arraycopy(fArray, 0, a, 0, fLength);
        }
    }

} // LSInputListImpl
