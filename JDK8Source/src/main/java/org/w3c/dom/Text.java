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
 *  (马萨诸塞理工学院,欧洲研究信息学和数学联合会,庆应大学)保留所有权利本作品根据W3C(r)软件许可证[1]分发,希望它有用,但没有任何保证;甚至没有对适销性或适用于特定用途的隐含保证
 * 
 *  [1] http：// wwww3org / Consortium / Legal / 2002 / copyright-software-20021231
 * 
 */

package org.w3c.dom;

/**
 * The <code>Text</code> interface inherits from <code>CharacterData</code>
 * and represents the textual content (termed <a href='http://www.w3.org/TR/2004/REC-xml-20040204#syntax'>character data</a> in XML) of an <code>Element</code> or <code>Attr</code>. If there is no
 * markup inside an element's content, the text is contained in a single
 * object implementing the <code>Text</code> interface that is the only
 * child of the element. If there is markup, it is parsed into the
 * information items (elements, comments, etc.) and <code>Text</code> nodes
 * that form the list of children of the element.
 * <p>When a document is first made available via the DOM, there is only one
 * <code>Text</code> node for each block of text. Users may create adjacent
 * <code>Text</code> nodes that represent the contents of a given element
 * without any intervening markup, but should be aware that there is no way
 * to represent the separations between these nodes in XML or HTML, so they
 * will not (in general) persist between DOM editing sessions. The
 * <code>Node.normalize()</code> method merges any such adjacent
 * <code>Text</code> objects into a single node for each block of text.
 * <p> No lexical check is done on the content of a <code>Text</code> node
 * and, depending on its position in the document, some characters must be
 * escaped during serialization using character references; e.g. the
 * characters "&lt;&amp;" if the textual content is part of an element or of
 * an attribute, the character sequence "]]&gt;" when part of an element,
 * the quotation mark character " or the apostrophe character ' when part of
 * an attribute.
 * <p>See also the <a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>Document Object Model (DOM) Level 3 Core Specification</a>.
 * <p>
 * <code> Text </code>接口继承自<code> CharacterData </code>并表示文本内容(称为<a href ='http：// wwww3org / TR / 2004 / REC-xml-20040204#syntax' >
 *  <code> Element </code>或<code> Attr </code>中的<>字符数据</a>)如果元素内容中没有标记,则文本包含在单个对象中,代码> Text </code>接口是元素
 * 的唯一子元素如果有标记,它将被解析为信息项(元素,注释等)和<code> Text </code>元素的子元素<p>当文档首次通过DOM可用时,每个文本块只有一个<code> Text </code>节
 * 点用户可以创建代表给定元素的内容的相邻的<code> Text </code>节点,而没有任何中间标记,但是应该意识到没有办法表示这些节点之间的XML或HTML分隔,因此他们将在DOM编辑会话之间不保持
 * (通常)<code> Nodenormalize()</code>方法将任何这样的相邻<code> Text </code>对象合并到每个文本块的单个节点<p>对<code> Text </code>节
 * 点的内容进行,并且根据其在文档中的位置,在序列化期间必须使用字符引用来转义一些字符;例如字符"&lt;"如果文本内容是元素或属性的一部分,则字符序列"]]>"当一个元素的一部分时,引号标记字符"或撇号字
 * 符"当属性<p>的一部分时也可以参见<a href ='http：// wwww3org / TR / 2004 / REC-DOM- Core-20040407'>文档对象模型(DOM)3级核心规范</a>
 * 。
 * 
 */
public interface Text extends CharacterData {
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
     * 将该节点分成两个节点,在指定的<code> offset </code>,保持树中的两个兄弟姐妹在分裂之后,这个节点将包含所有的内容到<code> offset </code>返回相同类型的节点,其包含
     * <code> offset </code>点之后及之后的所有内容。
     * 如果原始节点具有父节点,则将新节点插入为原始节点的下一个兄弟节点。 <code> offset </code>等于此节点的长度,新节点没有数据。
     * 
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
                          throws DOMException;

    /**
     * Returns whether this text node contains <a href='http://www.w3.org/TR/2004/REC-xml-infoset-20040204#infoitem.character'>
     * element content whitespace</a>, often abusively called "ignorable whitespace". The text node is
     * determined to contain whitespace in element content during the load
     * of the document or if validation occurs while using
     * <code>Document.normalizeDocument()</code>.
     * <p>
     * 返回此文本节点是否包含<a href='http://wwww3org/TR/2004/REC-xml-infoset-20040204#infoitemcharacter'>元素内容空格</a>,通常
     * 被称为"可忽略的空格"。
     * 文本节点在文档加载期间确定在元素内容中包含空格,或者如果使用<code> DocumentnormalizeDocument()</code>。
     * 
     * 
     * @since DOM Level 3
     */
    public boolean isElementContentWhitespace();

    /**
     * Returns all text of <code>Text</code> nodes logically-adjacent text
     * nodes to this node, concatenated in document order.
     * <br>For instance, in the example below <code>wholeText</code> on the
     * <code>Text</code> node that contains "bar" returns "barfoo", while on
     * the <code>Text</code> node that contains "foo" it returns "barfoo".
     *
     * <pre>
     *                     +-----+
     *                     | &lt;p&gt; |
     *                     +-----+
     *                       /\
     *                      /  \
     *               /-----\    +-------+
     *               | bar |    | &amp;ent; |
     *               \-----/    +-------+
     *                              |
     *                              |
     *                           /-----\
     *                           | foo |
     *                           \-----/
     * </pre>
     * <em>Figure: barTextNode.wholeText value is "barfoo"</em>
     *
     * <p>
     *  将<code> Text </code>节点的所有文本返回到此节点的逻辑上相邻的文本节点,以文档顺序连接<br>例如,在下面的示例中<code> textText </code>包含"bar"的节点返
     * 回"barfoo",而在包含"foo"的<code> Text </code>节点上返回"barfoo"。
     * 
     * <pre>
     * + ----- + | &lt; p&gt; | + ----- + / \\ / \\ / ----- \\ + ------- + | bar | | &amp; ent; | \\ ----- /
     *  + ------- + | | / ----- \\ | foo | \\ ----- /。
     * </pre>
     *  图：barTextNodewholeText值为"barfoo"</em>
     * 
     * 
     * @since DOM Level 3
     */
    public String getWholeText();

    /**
     * Replaces the text of the current node and all logically-adjacent text
     * nodes with the specified text. All logically-adjacent text nodes are
     * removed including the current node unless it was the recipient of the
     * replacement text.
     * <p>This method returns the node which received the replacement text.
     * The returned node is:</p>
     * <ul>
     * <li><code>null</code>, when the replacement text is
     * the empty string;
     * </li>
     * <li>the current node, except when the current node is
     * read-only;
     * </li>
     * <li> a new <code>Text</code> node of the same type (
     * <code>Text</code> or <code>CDATASection</code>) as the current node
     * inserted at the location of the replacement.
     * </li>
     * </ul>
     * <p>For instance, in the above example calling
     * <code>replaceWholeText</code> on the <code>Text</code> node that
     * contains "bar" with "yo" in argument results in the following:</p>
     *
     * <pre>
     *                     +-----+
     *                     | &lt;p&gt; |
     *                     +-----+
     *                        |
     *                        |
     *                     /-----\
     *                     | yo  |
     *                     \-----/
     * </pre>
     * <em>Figure: barTextNode.replaceWholeText("yo") modifies the
     * textual content of barTextNode with "yo"</em>
     *
     * <p>Where the nodes to be removed are read-only descendants of an
     * <code>EntityReference</code>, the <code>EntityReference</code> must
     * be removed instead of the read-only nodes. If any
     * <code>EntityReference</code> to be removed has descendants that are
     * not <code>EntityReference</code>, <code>Text</code>, or
     * <code>CDATASection</code> nodes, the <code>replaceWholeText</code>
     * method must fail before performing any modification of the document,
     * raising a <code>DOMException</code> with the code
     * <code>NO_MODIFICATION_ALLOWED_ERR</code>.</p>
     * <p>For instance, in the example below calling
     * <code>replaceWholeText</code> on the <code>Text</code> node that
     * contains "bar" fails, because the <code>EntityReference</code> node
     * "ent" contains an <code>Element</code> node which cannot be removed.</p>
     * <p>
     *  用指定的文本替换当前节点和所有逻辑上相邻的文本节点的文本除去包括当前节点在内的所有逻辑相邻的文本节点,除非它是替换文本的接收方<p>此方法返回接收到替换文本返回的节点是：</p>
     * <ul>
     *  <li> <code> null </code>,当替换文本是空字符串时;
     * </li>
     *  <li>当前节点,当前节点为只读时除外;
     * </li>
     * <li>作为在替换位置插入的当前节点的相同类型(<code> Text </code>或<code> CDATASection </code>)的新<
     * </li>
     * </ul>
     *  <p>例如,在上面的示例中,在参数中包含带有"yo"的"bar"的<code> Text </code>节点中调用<code> replaceWholeText </code>会产生以下结果：</p>
     * 。
     * 
     * 
     * @param content The content of the replacing <code>Text</code> node.
     * @return The <code>Text</code> node created with the specified content.
     * @exception DOMException
     *   NO_MODIFICATION_ALLOWED_ERR: Raised if one of the <code>Text</code>
     *   nodes being replaced is readonly.
     * @since DOM Level 3
     */
    public Text replaceWholeText(String content)
                                 throws DOMException;

}
