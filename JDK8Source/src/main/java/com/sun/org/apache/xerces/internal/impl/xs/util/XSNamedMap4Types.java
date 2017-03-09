/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2002,2004 The Apache Software Foundation.
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
 *  版权所有2002,2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.impl.xs.util;

import com.sun.org.apache.xerces.internal.util.SymbolHash;
import com.sun.org.apache.xerces.internal.xs.XSObject;
import com.sun.org.apache.xerces.internal.xs.XSTypeDefinition;


/**
 * Containts the map between qnames and XSObject's.
 *
 * @xerces.internal
 *
 * <p>
 *  约束qnames和XSObject之间的映射。
 * 
 *  @ xerces.internal
 * 
 * 
 * @author Sandy Gao, IBM
 *
 * @version $Id: XSNamedMap4Types.java,v 1.7 2010-11-01 04:40:06 joehw Exp $
 */
public final class XSNamedMap4Types extends XSNamedMapImpl {

    // the type of component stored here: complex or simple type
    private final short fType;

    /**
     * Construct an XSNamedMap implementation for one namespace
     *
     * <p>
     *  为一个命名空间构造XSNamedMap实现
     * 
     * 
     * @param namespace the namespace to which the components belong
     * @param map       the map from local names to components
     * @param type      the type of components
     */
    public XSNamedMap4Types(String namespace, SymbolHash map, short type) {
        super(namespace, map);
        fType = type;
    }

    /**
     * Construct an XSNamedMap implementation for a list of namespaces
     *
     * <p>
     *  为命名空间列表构造XSNamedMap实现
     * 
     * 
     * @param namespaces the namespaces to which the components belong
     * @param maps       the maps from local names to components
     * @param num        the number of namespaces
     * @param type      the type of components
     */
    public XSNamedMap4Types(String[] namespaces, SymbolHash[] maps, int num, short type) {
        super(namespaces, maps, num);
        fType = type;
    }

    /**
     * The number of <code>XSObjects</code> in the <code>XSObjectList</code>. The
     * range of valid child node indices is 0 to <code>length-1</code>
     * inclusive.
     * <p>
     *  <code> XSObjectList </code>中的<code> XSObjects </code>的数量。有效子节点索引的范围是0到<code> length-1 </code>。
     * 
     */
    public synchronized int getLength() {
        if (fLength == -1) {
            // first get the number of components for all types
            int length = 0;
            for (int i = 0; i < fNSNum; i++) {
                length += fMaps[i].getLength();
            }
            // then copy all types to an temporary array
            int pos = 0;
            XSObject[] array = new XSObject[length];
            for (int i = 0; i < fNSNum; i++) {
                pos += fMaps[i].getValues(array, pos);
            }
            // then copy either simple or complex types to fArray,
            // depending on which kind is required
            fLength = 0;
            fArray = new XSObject[length];
            XSTypeDefinition type;
            for (int i = 0; i < length; i++) {
                type = (XSTypeDefinition)array[i];
                if (type.getTypeCategory() == fType) {
                    fArray[fLength++] = type;
                }
            }
        }
        return fLength;
    }

    /**
     * Retrieves an <code>XSObject</code> specified by local name and namespace
     * URI.
     * <p>
     *  检索由本地名称和命名空间URI指定的<code> XSObject </code>。
     * 
     * 
     * @param namespace The namespace URI of the <code>XSObject</code> to
     *   retrieve.
     * @param localName The local name of the <code>XSObject</code> to retrieve.
     * @return A <code>XSObject</code> (of any type) with the specified local
     *   name and namespace URI, or <code>null</code> if they do not
     *   identify any <code>XSObject</code> in this map.
     */
    public XSObject itemByName(String namespace, String localName) {
        for (int i = 0; i < fNSNum; i++) {
            if (isEqual(namespace, fNamespaces[i])) {
                XSTypeDefinition type = (XSTypeDefinition)fMaps[i].get(localName);
                // only return it if it matches the required type
                if (type != null && type.getTypeCategory() == fType) {
                    return type;
                }
                return null;
            }
        }
        return null;
    }

    /**
     * Returns the <code>index</code>th item in the map. The index starts at
     * 0. If <code>index</code> is greater than or equal to the number of
     * nodes in the list, this returns <code>null</code>.
     * <p>
     *  返回地图中的<code> index </code> th项。索引从0开始。如果<code> index </code>大于或等于列表中的节点数,则返回<code> null </code>。
     * 
     * @param index The position in the map from which the item is to be
     *   retrieved.
     * @return The <code>XSObject</code> at the <code>index</code>th position
     *   in the <code>XSNamedMap</code>, or <code>null</code> if that is
     *   not a valid index.
     */
    public synchronized XSObject item(int index) {
        if (fArray == null) {
            getLength();
        }
        if (index < 0 || index >= fLength) {
            return null;
        }
        return fArray[index];
    }

} // class XSNamedMapImpl
