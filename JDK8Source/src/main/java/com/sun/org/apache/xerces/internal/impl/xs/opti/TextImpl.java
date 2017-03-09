/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2001-2004 The Apache Software Foundation.
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
 *  版权所有2001-2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.impl.xs.opti;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

/**
 * @xerces.internal
 *
 * <p>
 *  @ xerces.internal
 * 
 * 
 * @author Neil Graham, IBM
 */

public class TextImpl extends DefaultText {

    // Data
    String fData = null;
    SchemaDOM fSchemaDOM = null;
    int fRow;
    int fCol;

    public TextImpl(StringBuffer str, SchemaDOM sDOM, int row, int col) {
        fData = str.toString();
        fSchemaDOM = sDOM;
        fRow = row;
        fCol = col;
        rawname = prefix = localpart = uri = null;
        nodeType = Node.TEXT_NODE;
    }

    //
    // org.w3c.dom.Node methods
    //

    public Node getParentNode() {
        return fSchemaDOM.relations[fRow][0];
    }

    public Node getPreviousSibling() {
        if (fCol == 1) {
            return null;
        }
        return fSchemaDOM.relations[fRow][fCol-1];
    }


    public Node getNextSibling() {
        if (fCol == fSchemaDOM.relations[fRow].length-1) {
            return null;
        }
        return fSchemaDOM.relations[fRow][fCol+1];
    }

    // CharacterData methods

    /**
     * The character data of the node that implements this interface. The DOM
     * implementation may not put arbitrary limits on the amount of data
     * that may be stored in a <code>CharacterData</code> node. However,
     * implementation limits may mean that the entirety of a node's data may
     * not fit into a single <code>DOMString</code>. In such cases, the user
     * may call <code>substringData</code> to retrieve the data in
     * appropriately sized pieces.
     * <p>
     *  实现此接口的节点的字符数据。 DOM实现可以不对可以存储在<code> CharacterData </code>节点中的数据量施加任意限制。
     * 然而,实现限制可能意味着整个节点的数据可能不适合单个<code> DOMString </code>。
     * 在这种情况下,用户可以调用<code> substringData </code>来检索大小合适的数据。
     * 
     * 
     * @exception DOMException
     *   NO_MODIFICATION_ALLOWED_ERR: Raised when the node is readonly.
     * @exception DOMException
     *   DOMSTRING_SIZE_ERR: Raised when it would return more characters than
     *   fit in a <code>DOMString</code> variable on the implementation
     *   platform.
     */
    public String getData()
                            throws DOMException {
        return fData;
    }

    /**
     * The number of 16-bit units that are available through <code>data</code>
     * and the <code>substringData</code> method below. This may have the
     * value zero, i.e., <code>CharacterData</code> nodes may be empty.
     * <p>
     *  通过<code> data </code>和<code> substringData </code>方法可用的16位单位数。
     * 这可以具有值零,即,<code> CharacterData </code>节点可以是空的。
     * 
     */
    public int getLength() {
        if(fData == null) return 0;
        return fData.length();
    }

    /**
     * Extracts a range of data from the node.
     * <p>
     * 
     * @param offset Start offset of substring to extract.
     * @param count The number of 16-bit units to extract.
     * @return The specified substring. If the sum of <code>offset</code> and
     *   <code>count</code> exceeds the <code>length</code>, then all 16-bit
     *   units to the end of the data are returned.
     * @exception DOMException
     *   INDEX_SIZE_ERR: Raised if the specified <code>offset</code> is
     *   negative or greater than the number of 16-bit units in
     *   <code>data</code>, or if the specified <code>count</code> is
     *   negative.
     *   <br>DOMSTRING_SIZE_ERR: Raised if the specified range of text does
     *   not fit into a <code>DOMString</code>.
     */
    public String substringData(int offset,
                                int count)
                                throws DOMException {
        if(fData == null) return null;
        if(count < 0 || offset < 0 || offset > fData.length())
            throw new DOMException(DOMException.INDEX_SIZE_ERR, "parameter error");
        if(offset+count >= fData.length())
            return fData.substring(offset);
        return fData.substring(offset, offset+count);
    }

}
