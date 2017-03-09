/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  版权所有2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.dom;

import java.util.Vector;
import org.w3c.dom.DOMImplementationList;
import org.w3c.dom.DOMImplementation;

/**
 * <p>This class implements the DOM Level 3 Core interface DOMImplementationList.</p>
 *
 * @xerces.internal
 *
 * <p>
 *  <p>此类实现DOM Level 3 Core接口DOMImplementationList。</p>
 * 
 *  @ xerces.internal
 * 
 * 
 * @author Neil Delima, IBM
 * @since DOM Level 3 Core
 */
public class DOMImplementationListImpl implements DOMImplementationList {

    //A collection of DOMImplementations
    private Vector fImplementations;

    /**
     * Construct an empty list of DOMImplementations
     * <p>
     *  构造一个空的DOMImplementations列表
     * 
     */
    public DOMImplementationListImpl() {
        fImplementations = new Vector();
    }

    /**
     * Construct an empty list of DOMImplementations
     * <p>
     *  构造一个空的DOMImplementations列表
     * 
     */
    public DOMImplementationListImpl(Vector params) {
        fImplementations = params;
    }

    /**
     * Returns the indexth item in the collection.
     *
     * <p>
     *  返回集合中的indexth项。
     * 
     * 
     * @param index The index of the DOMImplemetation from the list to return.
     */
    public DOMImplementation item(int index) {
        try {
            return (DOMImplementation) fImplementations.elementAt(index);
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * Returns the number of DOMImplementations in the list.
     *
     * <p>
     *  返回列表中DOMImplementations的数量。
     * 
     * @return An integer indicating the number of DOMImplementations.
     */
    public int getLength() {
        return fImplementations.size();
    }
}
