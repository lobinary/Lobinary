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

import org.w3c.dom.Text;
import org.w3c.dom.DOMException;

/*
/* <p>
/* 
 * @author Neil Graham, IBM
 */
/**
 * The <code>Text</code> interface inherits from <code>CharacterData</code>
 * and represents the textual content (termed character data in XML) of an
 * <code>Element</code> or <code>Attr</code>. If there is no markup inside
 * an element's content, the text is contained in a single object
 * implementing the <code>Text</code> interface that is the only child of
 * the element. If there is markup, it is parsed into the information items
 * (elements, comments, etc.) and <code>Text</code> nodes that form the list
 * of children of the element.
 * <p>When a document is first made available via the DOM, there is only one
 * <code>Text</code> node for each block of text. Users may create adjacent
 * <code>Text</code> nodes that represent the contents of a given element
 * without any intervening markup, but should be aware that there is no way
 * to represent the separations between these nodes in XML or HTML, so they
 * will not (in general) persist between DOM editing sessions. The
 * <code>normalize()</code> method on <code>Node</code> merges any such
 * adjacent <code>Text</code> objects into a single node for each block of
 * text.
 * <p>See also the <a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Core-20001113'>Document Object Model (DOM) Level 2 Core Specification</a>.
 *
 * This is an empty implementation.
 *
 * @xerces.internal
 * <p>
 * <code> Text </code>接口继承自<code> CharacterData </code>并表示<code> Element </code>或<code> Attr </code的文本内容(称为XML中的字符数据) >
 * 。
 * 如果元素内容中没有标记,则文本包含在实现<code> Text </code>接口的单个​​对象中,该接口是元素的唯一子代。
 * 如果有标记,它将被解析为形成元素的子元素列表的信息项(元素,注释等)和<code> Text </code>节点。
 *  <p>当文档首次通过DOM可用时,每个文本块只有一个<code> Text </code>节点。
 * 用户可以创建代表给定元素的内容的相邻的<code> Text </code>节点,而没有任何中间标记,但是应该意识到没有办法表示这些节点之间的XML或HTML分隔,因此他们将而不是(通常)在DOM编辑会
 * 话之间持续。
 *  <p>当文档首次通过DOM可用时,每个文本块只有一个<code> Text </code>节点。
 *  <code> Node </code>上的<code> normalize()</code>方法将任何这种相邻的<code> Text </code>对象合并成每个文本块的单个节点。
 *  <p>另请参阅<a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Core-20001113'>文档对象模型(DOM)2级核心规范< a>。
 * 
 *  这是一个空的实现。
 * 
 *  @ xerces.internal
 * 
 */
public class DefaultText extends NodeImpl implements Text {

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
     * 实现此接口的节点的字符数据。 DOM实现可以不对可以存储在<code> CharacterData </code>节点中的数据量施加任意限制。
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
        return null;
    }

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
    public void setData(String data)
                            throws DOMException {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Method not supported");
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
        return 0;
    }

    /**
     * Extracts a range of data from the node.
     * <p>
     *  从节点提取一系列数据。
     * 
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
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Method not supported");
    }

    /**
     * Append the string to the end of the character data of the node. Upon
     * success, <code>data</code> provides access to the concatenation of
     * <code>data</code> and the <code>DOMString</code> specified.
     * <p>
     *  将字符串附加到节点的字符数据的结尾。
     * 成功时,<code> data </code>提供对<code> data </code>和指定的<code> DOMString </code>的连接的访问​​。
     * 
     * 
     * @param arg The <code>DOMString</code> to append.
     * @exception DOMException
     *   NO_MODIFICATION_ALLOWED_ERR: Raised if this node is readonly.
     */
    public void appendData(String arg)
                           throws DOMException {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Method not supported");
    }

    /**
     * Insert a string at the specified 16-bit unit offset.
     * <p>
     *  以指定的16位单位偏移插入一个字符串。
     * 
     * 
     * @param offset The character offset at which to insert.
     * @param arg The <code>DOMString</code> to insert.
     * @exception DOMException
     *   INDEX_SIZE_ERR: Raised if the specified <code>offset</code> is
     *   negative or greater than the number of 16-bit units in
     *   <code>data</code>.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this node is readonly.
     */
    public void insertData(int offset,
                           String arg)
                           throws DOMException {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Method not supported");
    }

    /**
     * Remove a range of 16-bit units from the node. Upon success,
     * <code>data</code> and <code>length</code> reflect the change.
     * <p>
     *  从节点中删除一个16位单位的范围。成功后,<code> data </code>和<code> length </code>反映更改。
     * 
     * 
     * @param offset The offset from which to start removing.
     * @param count The number of 16-bit units to delete. If the sum of
     *   <code>offset</code> and <code>count</code> exceeds
     *   <code>length</code> then all 16-bit units from <code>offset</code>
     *   to the end of the data are deleted.
     * @exception DOMException
     *   INDEX_SIZE_ERR: Raised if the specified <code>offset</code> is
     *   negative or greater than the number of 16-bit units in
     *   <code>data</code>, or if the specified <code>count</code> is
     *   negative.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this node is readonly.
     */
    public void deleteData(int offset,
                           int count)
                           throws DOMException {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Method not supported");
    }

    /**
     * Replace the characters starting at the specified 16-bit unit offset
     * with the specified string.
     * <p>
     * 用指定的字符串替换从指定的16位单位偏移开始的字符。
     * 
     * 
     * @param offset The offset from which to start replacing.
     * @param count The number of 16-bit units to replace. If the sum of
     *   <code>offset</code> and <code>count</code> exceeds
     *   <code>length</code>, then all 16-bit units to the end of the data
     *   are replaced; (i.e., the effect is the same as a <code>remove</code>
     *    method call with the same range, followed by an <code>append</code>
     *    method invocation).
     * @param arg The <code>DOMString</code> with which the range must be
     *   replaced.
     * @exception DOMException
     *   INDEX_SIZE_ERR: Raised if the specified <code>offset</code> is
     *   negative or greater than the number of 16-bit units in
     *   <code>data</code>, or if the specified <code>count</code> is
     *   negative.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this node is readonly.
     */
    public void replaceData(int offset,
                            int count,
                            String arg)
                            throws DOMException {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Method not supported");
    }

    // Text node methods
    /**
     * Breaks this node into two nodes at the specified <code>offset</code>,
     * keeping both in the tree as siblings. After being split, this node
     * will contain all the content up to the <code>offset</code> point. A
     * new node of the same type, which contains all the content at and
     * after the <code>offset</code> point, is returned. If the original
     * node had a parent node, the new node is inserted as the next sibling
     * of the original node. When the <code>offset</code> is equal to the
     * length of this node, the new node has no data.
     * <p>
     *  将此节点拆分为指定<code> offset </code>处的两个节点,从而将树中的两个节点保留为兄弟节点。在分裂之后,该节点将包含直到<code> offset </code>点的所有内容。
     * 返回相同类型的新节点,其包含<code> offset </code>点之后及之后的所有内容。如果原始节点具有父节点,则将新节点插入为原始节点的下一个兄弟节点。
     * 
     * @param offset The 16-bit unit offset at which to split, starting from
     *   <code>0</code>.
     * @return The new node, of the same type as this node.
     * @exception DOMException
     *   INDEX_SIZE_ERR: Raised if the specified offset is negative or greater
     *   than the number of 16-bit units in <code>data</code>.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this node is readonly.
     */
    public Text splitText(int offset)
                          throws DOMException {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Method not supported");
    }

    /** DOM Level 3 CR */
    public boolean isElementContentWhitespace(){
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Method not supported");
    }

    public String getWholeText(){
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Method not supported");
    }

    public Text replaceWholeText(String content) throws DOMException {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Method not supported");
    }
}
