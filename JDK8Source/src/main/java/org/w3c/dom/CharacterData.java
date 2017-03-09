/***** Lobxxx Translate Finished ******/
/*
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

/*
 *
 *
 *
 *
 *
 * Copyright (c) 2004 World Wide Web Consortium,
 *
 * (Massachusetts Institute of Technology, European Research Consortium for
 * Informatics and Mathematics, Keio University). All Rights Reserved. This
 * work is distributed under the W3C(r) Software License [1] in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * [1] http://www.w3.org/Consortium/Legal/2002/copyright-software-20021231
 * <p>
 *  版权所有(c)2004万维网联盟,
 * 
 *  (马萨诸塞理工学院,欧洲研究联合会信息学和数学,庆应大学)。版权所有。这项工作根据W3C(r)软件许可证[1]分发,希望它有用,但没有任何保证;甚至没有对适销性或适用于特定用途的隐含保证。
 * 
 *  [1] http://www.w3.org/Consortium/Legal/2002/copyright-software-20021231
 * 
 */

package org.w3c.dom;

/**
 * The <code>CharacterData</code> interface extends Node with a set of
 * attributes and methods for accessing character data in the DOM. For
 * clarity this set is defined here rather than on each object that uses
 * these attributes and methods. No DOM objects correspond directly to
 * <code>CharacterData</code>, though <code>Text</code> and others do
 * inherit the interface from it. All <code>offsets</code> in this interface
 * start from <code>0</code>.
 * <p>As explained in the <code>DOMString</code> interface, text strings in
 * the DOM are represented in UTF-16, i.e. as a sequence of 16-bit units. In
 * the following, the term 16-bit units is used whenever necessary to
 * indicate that indexing on CharacterData is done in 16-bit units.
 * <p>See also the <a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>Document Object Model (DOM) Level 3 Core Specification</a>.
 * <p>
 *  <code> CharacterData </code>接口通过一组用于访问DOM中的字符数据的属性和方法来扩展Node。为了清楚起见,这里定义这个集合而不是使用这些属性和方法的每个对象。
 * 没有DOM对象直接对应于<code> CharacterData </code>,虽然<code> Text </code>,其他人继承它的接口。
 * 此接口中的所有<code>偏移量</code>从<code> 0 </code>开始。
 *  <p>如<code> DOMString </code>界面中所述,DOM中的文本字符串以UTF-16表示,即以16位为单位的序列。
 * 在下文中,当需要时使用术语16位单位来指示以16位为单位完成对字符数据的索引。
 *  <p>另请参阅<a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>文档对象模型(DOM)3级核心规范< a>。
 * 
 */
public interface CharacterData extends Node {
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
     *   DOMSTRING_SIZE_ERR: Raised when it would return more characters than
     *   fit in a <code>DOMString</code> variable on the implementation
     *   platform.
     */
    public String getData()
                            throws DOMException;
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
     */
    public void setData(String data)
                            throws DOMException;

    /**
     * The number of 16-bit units that are available through <code>data</code>
     * and the <code>substringData</code> method below. This may have the
     * value zero, i.e., <code>CharacterData</code> nodes may be empty.
     * <p>
     *  通过<code> data </code>和<code> substringData </code>方法可用的16位单位数。
     * 这可以具有值零,即,<code> CharacterData </code>节点可以是空的。
     * 
     */
    public int getLength();

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
                                throws DOMException;

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
                           throws DOMException;

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
                           throws DOMException;

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
                           throws DOMException;

    /**
     * Replace the characters starting at the specified 16-bit unit offset
     * with the specified string.
     * <p>
     * 用指定的字符串替换从指定的16位单位偏移开始的字符。
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
                            throws DOMException;

}
