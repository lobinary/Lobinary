/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2001, 2002,2004 The Apache Software Foundation.
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
 *  版权所有2001,2002,2004 Apache软件基金会。
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

import org.w3c.dom.DOMStringList;

/**
 * DOM Level 3
 *
 * This class implements the DOM Level 3 Core interface DOMStringList.
 *
 * @xerces.internal
 *
 * <p>
 *  DOM级别3
 * 
 *  此类实现DOM Level 3 Core接口DOMStringList。
 * 
 *  @ xerces.internal
 * 
 * 
 * @author Neil Delima, IBM
 */
public class DOMStringListImpl implements DOMStringList {

        //A collection of DOMString values
    private Vector fStrings;

    /**
     * Construct an empty list of DOMStringListImpl
     * <p>
     *  构造一个空的列表DOMStringListImpl
     * 
     */
    public DOMStringListImpl() {
        fStrings = new Vector();
    }

    /**
     * Construct an empty list of DOMStringListImpl
     * <p>
     *  构造一个空的列表DOMStringListImpl
     * 
     */
    public DOMStringListImpl(Vector params) {
        fStrings = params;
    }

        /**
        /* <p>
        /* 
         * @see org.w3c.dom.DOMStringList#item(int)
         */
        public String item(int index) {
        try {
            return (String) fStrings.elementAt(index);
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
        }

        /**
        /* <p>
        /* 
         * @see org.w3c.dom.DOMStringList#getLength()
         */
        public int getLength() {
                return fStrings.size();
        }

        /**
        /* <p>
        /* 
         * @see org.w3c.dom.DOMStringList#contains(String)
         */
        public boolean contains(String param) {
                return fStrings.contains(param) ;
        }

    /**
     * DOM Internal:
     * Add a <code>DOMString</code> to the list.
     *
     * <p>
     *  DOM内部：向列表中添加<code> DOMString </code>。
     * 
     * @param domString A string to add to the list
     */
    public void add(String param) {
        fStrings.add(param);
    }

}
