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
 * The <code>Node</code> interface is the primary datatype for the entire
 * Document Object Model. It represents a single node in the document tree.
 * While all objects implementing the <code>Node</code> interface expose
 * methods for dealing with children, not all objects implementing the
 * <code>Node</code> interface may have children. For example,
 * <code>Text</code> nodes may not have children, and adding children to
 * such nodes results in a <code>DOMException</code> being raised.
 * <p>The attributes <code>nodeName</code>, <code>nodeValue</code> and
 * <code>attributes</code> are included as a mechanism to get at node
 * information without casting down to the specific derived interface. In
 * cases where there is no obvious mapping of these attributes for a
 * specific <code>nodeType</code> (e.g., <code>nodeValue</code> for an
 * <code>Element</code> or <code>attributes</code> for a <code>Comment</code>
 * ), this returns <code>null</code>. Note that the specialized interfaces
 * may contain additional and more convenient mechanisms to get and set the
 * relevant information.
 * <p>The values of <code>nodeName</code>,
 * <code>nodeValue</code>, and <code>attributes</code> vary according to the
 * node type as follows:
 * <table border='1' cellpadding='3'>
 * <tr>
 * <th>Interface</th>
 * <th>nodeName</th>
 * <th>nodeValue</th>
 * <th>attributes</th>
 * </tr>
 * <tr>
 * <td valign='top' rowspan='1' colspan='1'>
 * <code>Attr</code></td>
 * <td valign='top' rowspan='1' colspan='1'>same as <code>Attr.name</code></td>
 * <td valign='top' rowspan='1' colspan='1'>same as
 * <code>Attr.value</code></td>
 * <td valign='top' rowspan='1' colspan='1'><code>null</code></td>
 * </tr>
 * <tr>
 * <td valign='top' rowspan='1' colspan='1'><code>CDATASection</code></td>
 * <td valign='top' rowspan='1' colspan='1'>
 * <code>"#cdata-section"</code></td>
 * <td valign='top' rowspan='1' colspan='1'>same as <code>CharacterData.data</code>, the
 * content of the CDATA Section</td>
 * <td valign='top' rowspan='1' colspan='1'><code>null</code></td>
 * </tr>
 * <tr>
 * <td valign='top' rowspan='1' colspan='1'><code>Comment</code></td>
 * <td valign='top' rowspan='1' colspan='1'>
 * <code>"#comment"</code></td>
 * <td valign='top' rowspan='1' colspan='1'>same as <code>CharacterData.data</code>, the
 * content of the comment</td>
 * <td valign='top' rowspan='1' colspan='1'><code>null</code></td>
 * </tr>
 * <tr>
 * <td valign='top' rowspan='1' colspan='1'><code>Document</code></td>
 * <td valign='top' rowspan='1' colspan='1'>
 * <code>"#document"</code></td>
 * <td valign='top' rowspan='1' colspan='1'><code>null</code></td>
 * <td valign='top' rowspan='1' colspan='1'><code>null</code></td>
 * </tr>
 * <tr>
 * <td valign='top' rowspan='1' colspan='1'>
 * <code>DocumentFragment</code></td>
 * <td valign='top' rowspan='1' colspan='1'><code>"#document-fragment"</code></td>
 * <td valign='top' rowspan='1' colspan='1'>
 * <code>null</code></td>
 * <td valign='top' rowspan='1' colspan='1'><code>null</code></td>
 * </tr>
 * <tr>
 * <td valign='top' rowspan='1' colspan='1'><code>DocumentType</code></td>
 * <td valign='top' rowspan='1' colspan='1'>same as
 * <code>DocumentType.name</code></td>
 * <td valign='top' rowspan='1' colspan='1'><code>null</code></td>
 * <td valign='top' rowspan='1' colspan='1'><code>null</code></td>
 * </tr>
 * <tr>
 * <td valign='top' rowspan='1' colspan='1'>
 * <code>Element</code></td>
 * <td valign='top' rowspan='1' colspan='1'>same as <code>Element.tagName</code></td>
 * <td valign='top' rowspan='1' colspan='1'><code>null</code></td>
 * <td valign='top' rowspan='1' colspan='1'>
 * <code>NamedNodeMap</code></td>
 * </tr>
 * <tr>
 * <td valign='top' rowspan='1' colspan='1'><code>Entity</code></td>
 * <td valign='top' rowspan='1' colspan='1'>entity name</td>
 * <td valign='top' rowspan='1' colspan='1'><code>null</code></td>
 * <td valign='top' rowspan='1' colspan='1'>
 * <code>null</code></td>
 * </tr>
 * <tr>
 * <td valign='top' rowspan='1' colspan='1'><code>EntityReference</code></td>
 * <td valign='top' rowspan='1' colspan='1'>name of entity referenced</td>
 * <td valign='top' rowspan='1' colspan='1'>
 * <code>null</code></td>
 * <td valign='top' rowspan='1' colspan='1'><code>null</code></td>
 * </tr>
 * <tr>
 * <td valign='top' rowspan='1' colspan='1'><code>Notation</code></td>
 * <td valign='top' rowspan='1' colspan='1'>notation name</td>
 * <td valign='top' rowspan='1' colspan='1'>
 * <code>null</code></td>
 * <td valign='top' rowspan='1' colspan='1'><code>null</code></td>
 * </tr>
 * <tr>
 * <td valign='top' rowspan='1' colspan='1'><code>ProcessingInstruction</code></td>
 * <td valign='top' rowspan='1' colspan='1'>same
 * as <code>ProcessingInstruction.target</code></td>
 * <td valign='top' rowspan='1' colspan='1'>same as
 * <code>ProcessingInstruction.data</code></td>
 * <td valign='top' rowspan='1' colspan='1'><code>null</code></td>
 * </tr>
 * <tr>
 * <td valign='top' rowspan='1' colspan='1'><code>Text</code></td>
 * <td valign='top' rowspan='1' colspan='1'>
 * <code>"#text"</code></td>
 * <td valign='top' rowspan='1' colspan='1'>same as <code>CharacterData.data</code>, the content
 * of the text node</td>
 * <td valign='top' rowspan='1' colspan='1'><code>null</code></td>
 * </tr>
 * </table>
 * <p>See also the <a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>Document Object Model (DOM) Level 3 Core Specification</a>.
 * <p>
 * <code> Node </code>接口是整个文档对象模型的主数据类型它代表文档树中的单个节点虽然所有实现<code> Node </code>接口的对象都暴露了处理子节点的方法,并非所有实现<code>
 *  Node </code>接口的对象都有子代。
 * 例如,<code> Text </code>节点可能没有子节点,并且向这些节点添加子节会导致<code> DOMException </code >被引发<p>属性<code> nodeName </code>
 * ,<code> nodeValue </code>和<code>属性</code>被包括作为获取节点信息而不向下转换到特定派生接口在特定<code> nodeType </code>没有明显映射这些属性
 * 的情况下(例如,<code> NodeValue </code>代表<code> Element </code>或<code>属性</code>,则返回<code> null </code>注意,专用接
 * 口可以包含获取和设置相关信息的附加和更方便的机制<p> <code> nodeName </code>,<code> nodeValue </code>和<code>代码>根据节点类型而变化,如下所示：
 * 。
 * <table border='1' cellpadding='3'>
 * <tr>
 * <th>接口</th> <th> nodeName </th> <th> nodeValue </th> <th>属性</th>
 * </tr>
 * <tr>
 * <td valign='top' rowspan='1' colspan='1'>
 *  <code> Attr </code> </td> <td valign ='top'rowspan ='1'colspan ='1'>与<code> Attrname </code> </td> <td valign = 'rowspan ='1'colspan ='1'>
 * 与<code> Attrvalue </code>相同</td> <td valign ='top'rowspan ='1'colspan ='1'代码> </td>。
 * </tr>
 * <tr>
 *  <td valign ='top'rowspan ='1'colspan ='1'> <code> CDATASection </code> </td>
 * <td valign='top' rowspan='1' colspan='1'>
 *  <code>"#cdata-section"</code> </td> <td valign ='top'rowspan ='1'colspan ='1'>与<code> CharacterDatad
 * ata </code> CDATA节</td> <td valign ='top'rowspan ='1'colspan ='1'> <code> null </code> </td>。
 * </tr>
 * <tr>
 *  <td valign ='top'rowspan ='1'colspan ='1'> <code>注释</code> </td>
 * <td valign='top' rowspan='1' colspan='1'>
 * <code>"#comment"</code> </td> <td valign ='top'rowspan ='1'colspan ='1'>与<code> CharacterDatadata </code>
 *  / td> <td valign ='top'rowspan ='1'colspan ='1'> <code> null </code> </td>。
 * </tr>
 * <tr>
 *  <td valign ='top'rowspan ='1'colspan ='1'> <code>文档</code> </td>
 * <td valign='top' rowspan='1' colspan='1'>
 *  <code>"#document"</code> </td> <td valign ='top'rowspan ='1'colspan ='1'> <code> null </code> </td> 
 * <td valign = top'rowspan ='1'colspan ='1'> <code> null </code> </td>。
 * </tr>
 * <tr>
 * <td valign='top' rowspan='1' colspan='1'>
 *  <code> DocumentFragment </code> </td> <td valign ='top'rowspan ='1'colspan ='1'> <code>"#document-fr
 * agment"</code> </td>。
 * <td valign='top' rowspan='1' colspan='1'>
 *  <code> null </code> </td> <td valign ='top'rowspan ='1'colspan ='1'> <code> null </code> </td>
 * </tr>
 * <tr>
 * <td valign ='top'rowspan ='1'colspan ='1'> <code> DocumentType </code> </td> <td valign ='top'rowspan ='1'colspan ='1'>
 *  <code> DocumentTypename </code> </td> <td valign ='top'rowspan ='1'colspan ='1'> <code> null </code>
 *  </td> <td valign = ='1'colspan ='1'> <code> null </code> </td>。
 * </tr>
 * <tr>
 * <td valign='top' rowspan='1' colspan='1'>
 *  <code>元素</code> </td> <td valign ='top'rowspan ='1'colspan ='1'>与<code> ElementtagName </code> </td>
 *  <td valign = 'rowspan ='1'colspan ='1'> <code> null </code> </td>。
 * <td valign='top' rowspan='1' colspan='1'>
 *  <code> NamedNodeMap </code> </td>
 * </tr>
 * <tr>
 *  <td valign ='top'rowspan ='1'colspan ='1'> <code>实体</code> </td> <td valign ='top'rowspan ='1'colspan ='1' </td>
 *  <td valign ='top'rowspan ='1'colspan ='1'> <code> null </code> </td>。
 * <td valign='top' rowspan='1' colspan='1'>
 *  <code> null </code> </td>
 * </tr>
 * <tr>
 * <td valign ='top'rowspan ='1'colspan ='1'> <code> EntityReference </code> </td> <td valign ='top'rowspan ='1'colspan ='1'实体引用</td>
 * 。
 * <td valign='top' rowspan='1' colspan='1'>
 *  <code> null </code> </td> <td valign ='top'rowspan ='1'colspan ='1'> <code> null </code> </td>
 * </tr>
 * <tr>
 *  <td valign ='top'rowspan ='1'colspan ='1'> <code>符号</code> </td> <td valign ='top'rowspan ='1'colspan ='1'>
 * 符号名称</td>。
 * <td valign='top' rowspan='1' colspan='1'>
 *  <code> null </code> </td> <td valign ='top'rowspan ='1'colspan ='1'> <code> null </code> </td>
 * </tr>
 * <tr>
 *  <td valign ='top'rowspan ='1'colspan ='1'> <code> ProcessingInstruction </code> </td> <td valign ='top'rowspan ='1'colspan ='1'>
 *  <code> ProcessingInstructiontarget </code> </td> <td valign ='top'rowspan ='1'colspan ='1'>与<code> P
 * rocessingInstructiondata </code> </td> <td valign = 'rowspan ='1'colspan ='1'> <code> null </code> </td>
 * 。
 * </tr>
 * <tr>
 * <td valign ='top'rowspan ='1'colspan ='1'> <code>文本</code> </td>
 * <td valign='top' rowspan='1' colspan='1'>
 *  <code>"#text"</code> </td> <td valign ='top'rowspan ='1'colspan ='1'>与<code> CharacterDatadata </code>
 *  </td> <td valign ='top'rowspan ='1'colspan ='1'> <code> null </code> </td>。
 * </tr>
 * </table>
 *  <p>另请参阅<a href='http://wwww3org/TR/2004/REC-DOM-Level-3-Core-20040407'>文档对象模型(DOM)3级核心规范</a>
 * 
 */
public interface Node {
    // NodeType
    /**
     * The node is an <code>Element</code>.
     * <p>
     *  该节点是一个<code> Element </code>
     * 
     */
    public static final short ELEMENT_NODE              = 1;
    /**
     * The node is an <code>Attr</code>.
     * <p>
     *  该节点是一个<code> Attr </code>
     * 
     */
    public static final short ATTRIBUTE_NODE            = 2;
    /**
     * The node is a <code>Text</code> node.
     * <p>
     *  该节点是一个<code> Text </code>节点
     * 
     */
    public static final short TEXT_NODE                 = 3;
    /**
     * The node is a <code>CDATASection</code>.
     * <p>
     *  该节点是一个<code> CDATASection </code>
     * 
     */
    public static final short CDATA_SECTION_NODE        = 4;
    /**
     * The node is an <code>EntityReference</code>.
     * <p>
     *  该节点是一个<code> EntityReference </code>
     * 
     */
    public static final short ENTITY_REFERENCE_NODE     = 5;
    /**
     * The node is an <code>Entity</code>.
     * <p>
     *  该节点是一个<code> Entity </code>
     * 
     */
    public static final short ENTITY_NODE               = 6;
    /**
     * The node is a <code>ProcessingInstruction</code>.
     * <p>
     *  该节点是一个<code> ProcessingInstruction </code>
     * 
     */
    public static final short PROCESSING_INSTRUCTION_NODE = 7;
    /**
     * The node is a <code>Comment</code>.
     * <p>
     *  该节点是一个<code> Comment </code>
     * 
     */
    public static final short COMMENT_NODE              = 8;
    /**
     * The node is a <code>Document</code>.
     * <p>
     *  该节点是<code> Document </code>
     * 
     */
    public static final short DOCUMENT_NODE             = 9;
    /**
     * The node is a <code>DocumentType</code>.
     * <p>
     * 该节点是<code> DocumentType </code>
     * 
     */
    public static final short DOCUMENT_TYPE_NODE        = 10;
    /**
     * The node is a <code>DocumentFragment</code>.
     * <p>
     *  该节点是一个<code> DocumentFragment </code>
     * 
     */
    public static final short DOCUMENT_FRAGMENT_NODE    = 11;
    /**
     * The node is a <code>Notation</code>.
     * <p>
     *  该节点是<code>符号</code>
     * 
     */
    public static final short NOTATION_NODE             = 12;

    /**
     * The name of this node, depending on its type; see the table above.
     * <p>
     *  此节点的名称,具体取决于其类型;见上表
     * 
     */
    public String getNodeName();

    /**
     * The value of this node, depending on its type; see the table above.
     * When it is defined to be <code>null</code>, setting it has no effect,
     * including if the node is read-only.
     * <p>
     *  该节点的值,取决于其类型;见上表当它被定义为<code> null </code>时,设置它没有效果,包括如果节点是只读的
     * 
     * 
     * @exception DOMException
     *   DOMSTRING_SIZE_ERR: Raised when it would return more characters than
     *   fit in a <code>DOMString</code> variable on the implementation
     *   platform.
     */
    public String getNodeValue()
                              throws DOMException;
    /**
     * The value of this node, depending on its type; see the table above.
     * When it is defined to be <code>null</code>, setting it has no effect,
     * including if the node is read-only.
     * <p>
     *  该节点的值,取决于其类型;见上表当它被定义为<code> null </code>时,设置它没有效果,包括如果节点是只读的
     * 
     * 
     * @exception DOMException
     *   NO_MODIFICATION_ALLOWED_ERR: Raised when the node is readonly and if
     *   it is not defined to be <code>null</code>.
     */
    public void setNodeValue(String nodeValue)
                              throws DOMException;

    /**
     * A code representing the type of the underlying object, as defined above.
     * <p>
     *  表示基础对象类型的代码,如上定义
     * 
     */
    public short getNodeType();

    /**
     * The parent of this node. All nodes, except <code>Attr</code>,
     * <code>Document</code>, <code>DocumentFragment</code>,
     * <code>Entity</code>, and <code>Notation</code> may have a parent.
     * However, if a node has just been created and not yet added to the
     * tree, or if it has been removed from the tree, this is
     * <code>null</code>.
     * <p>
     * 该节点的父节点除了<code> Attr </code>,<code> Document </code>,<code> DocumentFragment </code>,<code> Entity </code>
     * 和<code> </code>可能有一个parent但是,如果一个节点刚刚创建并且还没有添加到树中,或者如果它已经从树中删除,这是<code> null </code>。
     * 
     */
    public Node getParentNode();

    /**
     * A <code>NodeList</code> that contains all children of this node. If
     * there are no children, this is a <code>NodeList</code> containing no
     * nodes.
     * <p>
     *  包含此节点的所有子节点的<code> NodeList </code>如果没有子节点,则这是一个不包含节点的<code> NodeList </code>
     * 
     */
    public NodeList getChildNodes();

    /**
     * The first child of this node. If there is no such node, this returns
     * <code>null</code>.
     * <p>
     *  此节点的第一个子节点如果没有这样的节点,则返回<code> null </code>
     * 
     */
    public Node getFirstChild();

    /**
     * The last child of this node. If there is no such node, this returns
     * <code>null</code>.
     * <p>
     *  这个节点的最后一个子节点如果没有这样的节点,则返回<code> null </code>
     * 
     */
    public Node getLastChild();

    /**
     * The node immediately preceding this node. If there is no such node,
     * this returns <code>null</code>.
     * <p>
     *  紧接在此节点之前的节点如果没有此类节点,则返回<code> null </code>
     * 
     */
    public Node getPreviousSibling();

    /**
     * The node immediately following this node. If there is no such node,
     * this returns <code>null</code>.
     * <p>
     * 紧随此节点之后的节点如果没有此类节点,则返回<code> null </code>
     * 
     */
    public Node getNextSibling();

    /**
     * A <code>NamedNodeMap</code> containing the attributes of this node (if
     * it is an <code>Element</code>) or <code>null</code> otherwise.
     * <p>
     *  包含此节点属性的<code> NamedNodeMap </code>(如果是<code> Element </code>)或<code> null </code>
     * 
     */
    public NamedNodeMap getAttributes();

    /**
     * The <code>Document</code> object associated with this node. This is
     * also the <code>Document</code> object used to create new nodes. When
     * this node is a <code>Document</code> or a <code>DocumentType</code>
     * which is not used with any <code>Document</code> yet, this is
     * <code>null</code>.
     *
     * <p>
     *  与此节点相关联的<code> Document </code>对象这也是用于创建新节点的<code> Document </code>对象当此节点是<code> Document </code>或<code>
     *  DocumentType </code>,它不与任何<code> Document </code>一起使用,这是<code> null </code>。
     * 
     * 
     * @since DOM Level 2
     */
    public Document getOwnerDocument();

    /**
     * Inserts the node <code>newChild</code> before the existing child node
     * <code>refChild</code>. If <code>refChild</code> is <code>null</code>,
     * insert <code>newChild</code> at the end of the list of children.
     * <br>If <code>newChild</code> is a <code>DocumentFragment</code> object,
     * all of its children are inserted, in the same order, before
     * <code>refChild</code>. If the <code>newChild</code> is already in the
     * tree, it is first removed.
     * <p ><b>Note:</b>  Inserting a node before itself is implementation
     * dependent.
     * <p>
     * 在现有子节点<code> refChild </code>之前插入节点<code> newChild </code>如果<code> refChild </code>是<code> null </code>
     * ,请插入<code> newChild <代码>在子代表列表的末尾<br>如果<code> newChild </code>是<code> DocumentFragment </code>对象,则所有子
     * 代都以相同的顺序插入< refChild </code>如果<code> newChild </code>已经在树中,则首先删除<p> <b>注意：</b>。
     * 
     * 
     * @param newChild The node to insert.
     * @param refChild The reference node, i.e., the node before which the
     *   new node must be inserted.
     * @return The node being inserted.
     * @exception DOMException
     *   HIERARCHY_REQUEST_ERR: Raised if this node is of a type that does not
     *   allow children of the type of the <code>newChild</code> node, or if
     *   the node to insert is one of this node's ancestors or this node
     *   itself, or if this node is of type <code>Document</code> and the
     *   DOM application attempts to insert a second
     *   <code>DocumentType</code> or <code>Element</code> node.
     *   <br>WRONG_DOCUMENT_ERR: Raised if <code>newChild</code> was created
     *   from a different document than the one that created this node.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this node is readonly or
     *   if the parent of the node being inserted is readonly.
     *   <br>NOT_FOUND_ERR: Raised if <code>refChild</code> is not a child of
     *   this node.
     *   <br>NOT_SUPPORTED_ERR: if this node is of type <code>Document</code>,
     *   this exception might be raised if the DOM implementation doesn't
     *   support the insertion of a <code>DocumentType</code> or
     *   <code>Element</code> node.
     *
     * @since DOM Level 3
     */
    public Node insertBefore(Node newChild,
                             Node refChild)
                             throws DOMException;

    /**
     * Replaces the child node <code>oldChild</code> with <code>newChild</code>
     *  in the list of children, and returns the <code>oldChild</code> node.
     * <br>If <code>newChild</code> is a <code>DocumentFragment</code> object,
     * <code>oldChild</code> is replaced by all of the
     * <code>DocumentFragment</code> children, which are inserted in the
     * same order. If the <code>newChild</code> is already in the tree, it
     * is first removed.
     * <p ><b>Note:</b>  Replacing a node with itself is implementation
     * dependent.
     * <p>
     * 在子节点列表中替换子节点<code> oldChild </code>与<code> newChild </code>,并返回<code> oldChild </code>节点<br>如果<code> 
     * newChild </code >是一个<code> DocumentFragment </code>对象,<code> oldChild </code>被所有<code> DocumentFragme
     * nt </code>子代替,它们以相同的顺序插入如果<code> newChild </code>已经在树中,因此首先删除<p> <b>注意：</b>将节点替换为自身是实现相关的。
     * 
     * 
     * @param newChild The new node to put in the child list.
     * @param oldChild The node being replaced in the list.
     * @return The node replaced.
     * @exception DOMException
     *   HIERARCHY_REQUEST_ERR: Raised if this node is of a type that does not
     *   allow children of the type of the <code>newChild</code> node, or if
     *   the node to put in is one of this node's ancestors or this node
     *   itself, or if this node is of type <code>Document</code> and the
     *   result of the replacement operation would add a second
     *   <code>DocumentType</code> or <code>Element</code> on the
     *   <code>Document</code> node.
     *   <br>WRONG_DOCUMENT_ERR: Raised if <code>newChild</code> was created
     *   from a different document than the one that created this node.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this node or the parent of
     *   the new node is readonly.
     *   <br>NOT_FOUND_ERR: Raised if <code>oldChild</code> is not a child of
     *   this node.
     *   <br>NOT_SUPPORTED_ERR: if this node is of type <code>Document</code>,
     *   this exception might be raised if the DOM implementation doesn't
     *   support the replacement of the <code>DocumentType</code> child or
     *   <code>Element</code> child.
     *
     * @since DOM Level 3
     */
    public Node replaceChild(Node newChild,
                             Node oldChild)
                             throws DOMException;

    /**
     * Removes the child node indicated by <code>oldChild</code> from the list
     * of children, and returns it.
     * <p>
     *  从子元素列表中删除由<code> oldChild </code>指示的子节点,并返回它
     * 
     * 
     * @param oldChild The node being removed.
     * @return The node removed.
     * @exception DOMException
     *   NO_MODIFICATION_ALLOWED_ERR: Raised if this node is readonly.
     *   <br>NOT_FOUND_ERR: Raised if <code>oldChild</code> is not a child of
     *   this node.
     *   <br>NOT_SUPPORTED_ERR: if this node is of type <code>Document</code>,
     *   this exception might be raised if the DOM implementation doesn't
     *   support the removal of the <code>DocumentType</code> child or the
     *   <code>Element</code> child.
     *
     * @since DOM Level 3
     */
    public Node removeChild(Node oldChild)
                            throws DOMException;

    /**
     * Adds the node <code>newChild</code> to the end of the list of children
     * of this node. If the <code>newChild</code> is already in the tree, it
     * is first removed.
     * <p>
     *  将节点<code> newChild </code>添加到此节点的子节点列表的末尾如果<code> newChild </code>已经在树中,
     * 
     * 
     * @param newChild The node to add.If it is a
     *   <code>DocumentFragment</code> object, the entire contents of the
     *   document fragment are moved into the child list of this node
     * @return The node added.
     * @exception DOMException
     *   HIERARCHY_REQUEST_ERR: Raised if this node is of a type that does not
     *   allow children of the type of the <code>newChild</code> node, or if
     *   the node to append is one of this node's ancestors or this node
     *   itself, or if this node is of type <code>Document</code> and the
     *   DOM application attempts to append a second
     *   <code>DocumentType</code> or <code>Element</code> node.
     *   <br>WRONG_DOCUMENT_ERR: Raised if <code>newChild</code> was created
     *   from a different document than the one that created this node.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this node is readonly or
     *   if the previous parent of the node being inserted is readonly.
     *   <br>NOT_SUPPORTED_ERR: if the <code>newChild</code> node is a child
     *   of the <code>Document</code> node, this exception might be raised
     *   if the DOM implementation doesn't support the removal of the
     *   <code>DocumentType</code> child or <code>Element</code> child.
     *
     * @since DOM Level 3
     */
    public Node appendChild(Node newChild)
                            throws DOMException;

    /**
     * Returns whether this node has any children.
     * <p>
     *  返回此节点是否有任何子节点
     * 
     * 
     * @return Returns <code>true</code> if this node has any children,
     *   <code>false</code> otherwise.
     */
    public boolean hasChildNodes();

    /**
     * Returns a duplicate of this node, i.e., serves as a generic copy
     * constructor for nodes. The duplicate node has no parent (
     * <code>parentNode</code> is <code>null</code>) and no user data. User
     * data associated to the imported node is not carried over. However, if
     * any <code>UserDataHandlers</code> has been specified along with the
     * associated data these handlers will be called with the appropriate
     * parameters before this method returns.
     * <br>Cloning an <code>Element</code> copies all attributes and their
     * values, including those generated by the XML processor to represent
     * defaulted attributes, but this method does not copy any children it
     * contains unless it is a deep clone. This includes text contained in
     * an the <code>Element</code> since the text is contained in a child
     * <code>Text</code> node. Cloning an <code>Attr</code> directly, as
     * opposed to be cloned as part of an <code>Element</code> cloning
     * operation, returns a specified attribute (<code>specified</code> is
     * <code>true</code>). Cloning an <code>Attr</code> always clones its
     * children, since they represent its value, no matter whether this is a
     * deep clone or not. Cloning an <code>EntityReference</code>
     * automatically constructs its subtree if a corresponding
     * <code>Entity</code> is available, no matter whether this is a deep
     * clone or not. Cloning any other type of node simply returns a copy of
     * this node.
     * <br>Note that cloning an immutable subtree results in a mutable copy,
     * but the children of an <code>EntityReference</code> clone are readonly
     * . In addition, clones of unspecified <code>Attr</code> nodes are
     * specified. And, cloning <code>Document</code>,
     * <code>DocumentType</code>, <code>Entity</code>, and
     * <code>Notation</code> nodes is implementation dependent.
     * <p>
     * 返回此节点的副本,即用作节点的通用副本构造函数副本节点没有父级(<code> parentNode </code>是<code> null </code>),没有用户数据与导入的节点不被继承但是,如果已
     * 经指定任何<code> UserDataHandlers </code>以及相关联的数据,则在此方法返回之前,将使用适当的参数调用这些处理程序<br>克隆<code> Element < / code>
     * 复制所有属性及其值,包括由XML处理器生成的表示默认属性的值,但此方法不复制其包含的任何子项,除非它是深层克隆。
     * 这包括<code> Element < / code>,因为文本包含在子<code> Text </code>节点中直接克隆<code> Attr </code>,而不是作为<code> Element
     *  </code>克隆操作的一部分进行克隆,返回指定的属性(<code>指定</code>为<code> true </code>)克隆一个<code> Attr </code>总是克隆它的孩子,因为它们
     * 代表它的值,无论这是一个深克隆还是克隆一个<code> EntityReference </code>自动构造它的子树如果相应的<code> Entity </code>可用,无论这是否是一个深层克隆克
     * 隆任何其他类型的节点只是返回此节点的副本<br>请注意,克隆一个不可变子树导致一个可变复制,但是<code> EntityReference </code>克隆的子节点是只读的。
     * 此外,指定未指定的<code> Attr </code>节点的克隆并且,克隆<code>文档</code>,<code> DocumentType </code>,<code> Entity </code>
     * 和<code>记法</code>节点是实现相关的。
     * 
     * 
     * @param deep If <code>true</code>, recursively clone the subtree under
     *   the specified node; if <code>false</code>, clone only the node
     *   itself (and its attributes, if it is an <code>Element</code>).
     * @return The duplicate node.
     */
    public Node cloneNode(boolean deep);

    /**
     *  Puts all <code>Text</code> nodes in the full depth of the sub-tree
     * underneath this <code>Node</code>, including attribute nodes, into a
     * "normal" form where only structure (e.g., elements, comments,
     * processing instructions, CDATA sections, and entity references)
     * separates <code>Text</code> nodes, i.e., there are neither adjacent
     * <code>Text</code> nodes nor empty <code>Text</code> nodes. This can
     * be used to ensure that the DOM view of a document is the same as if
     * it were saved and re-loaded, and is useful when operations (such as
     * XPointer [<a href='http://www.w3.org/TR/2003/REC-xptr-framework-20030325/'>XPointer</a>]
     *  lookups) that depend on a particular document tree structure are to
     * be used. If the parameter "normalize-characters" of the
     * <code>DOMConfiguration</code> object attached to the
     * <code>Node.ownerDocument</code> is <code>true</code>, this method
     * will also fully normalize the characters of the <code>Text</code>
     * nodes.
     * <p ><b>Note:</b> In cases where the document contains
     * <code>CDATASections</code>, the normalize operation alone may not be
     * sufficient, since XPointers do not differentiate between
     * <code>Text</code> nodes and <code>CDATASection</code> nodes.
     *
     * <p>
     * 将所有<code>节点</code>下的子树的所有<code> Text </code>节点包括在"正常"形式中,其中只有结构(例如,元素,注释,处理指令,CDATA段和实体引用)分离<code> Te
     * xt </code>节点,即,既没有相邻的<code> Text </code>节点,也没有空的<code> Text </code>用于确保文档的DOM视图与保存和重新加载相同,并且在操作(例如XPo
     * inter [<a href ='http：// wwww3org / TR / 2003 / REC -xptr-framework-20030325 /'> XPointer </a>]查找)将依赖
     * 于特定文档树结构。
     * 
     * 
     * @since DOM Level 3
     */
    public void normalize();

    /**
     *  Tests whether the DOM implementation implements a specific feature and
     * that feature is supported by this node, as specified in .
     * <p>
     * 测试DOM实现是否实现了特定功能,并且该功能受此节点支持,如在中指定
     * 
     * 
     * @param feature  The name of the feature to test.
     * @param version  This is the version number of the feature to test.
     * @return Returns <code>true</code> if the specified feature is
     *   supported on this node, <code>false</code> otherwise.
     *
     * @since DOM Level 2
     */
    public boolean isSupported(String feature,
                               String version);

    /**
     * The namespace URI of this node, or <code>null</code> if it is
     * unspecified (see ).
     * <br>This is not a computed value that is the result of a namespace
     * lookup based on an examination of the namespace declarations in
     * scope. It is merely the namespace URI given at creation time.
     * <br>For nodes of any type other than <code>ELEMENT_NODE</code> and
     * <code>ATTRIBUTE_NODE</code> and nodes created with a DOM Level 1
     * method, such as <code>Document.createElement()</code>, this is always
     * <code>null</code>.
     * <p ><b>Note:</b> Per the <em>Namespaces in XML</em> Specification [<a href='http://www.w3.org/TR/1999/REC-xml-names-19990114/'>XML Namespaces</a>]
     *  an attribute does not inherit its namespace from the element it is
     * attached to. If an attribute is not explicitly given a namespace, it
     * simply has no namespace.
     *
     * <p>
     * 此节点的命名空间URI,或<code> null </code>(如果未指定)<br>这不是一个计算值,它是基于对范围中的命名空间声明的检查的命名空间查找的结果它仅仅是在创建时提供的命名空间URI <br>
     * 对于除了<code> ELEMENT_NODE </code>和<code> ATTRIBUTE_NODE </code>之外的任何类型的节点和使用DOM Level 1方法创建的节点,代码> Docu
     * mentcreateElement()</code>,总是<code> null </code> <p> <b>注意：</b>根据XML中的命名空间</em> ='http：// wwww3org / 
     * TR / 1999 / REC-xml-names-19990114 /'> XML命名空间</a>]属性不从其附加的元素继承其命名空间如果未明确给出属性一个命名空间,它只是没有命名空间。
     * 
     * 
     * @since DOM Level 2
     */
    public String getNamespaceURI();

    /**
     * The namespace prefix of this node, or <code>null</code> if it is
     * unspecified. When it is defined to be <code>null</code>, setting it
     * has no effect, including if the node is read-only.
     * <br>Note that setting this attribute, when permitted, changes the
     * <code>nodeName</code> attribute, which holds the qualified name, as
     * well as the <code>tagName</code> and <code>name</code> attributes of
     * the <code>Element</code> and <code>Attr</code> interfaces, when
     * applicable.
     * <br>Setting the prefix to <code>null</code> makes it unspecified,
     * setting it to an empty string is implementation dependent.
     * <br>Note also that changing the prefix of an attribute that is known to
     * have a default value, does not make a new attribute with the default
     * value and the original prefix appear, since the
     * <code>namespaceURI</code> and <code>localName</code> do not change.
     * <br>For nodes of any type other than <code>ELEMENT_NODE</code> and
     * <code>ATTRIBUTE_NODE</code> and nodes created with a DOM Level 1
     * method, such as <code>createElement</code> from the
     * <code>Document</code> interface, this is always <code>null</code>.
     *
     * <p>
     * 此节点的命名空间前缀或<code> null </code>(如果未指定)当定义为<code> null </code>时,设置它无效,包括节点是只读<请注意,在允许的情况下设置此属性会更改保存有限定名称的<code>
     *  nodeName </code>属性以及<code> tagName </code>和<code> name </code>适用时,<code> Element </code>和<code> Attr
     *  </code>接口的属性<br>将前缀设置为<code> null </code>使其未指定,将其设置为空字符串实现依赖<br>请注意,更改已知具有默认值的属性的前缀不会使新属性带有默认值和原始前缀,
     * 因为<code> namespaceURI </code>和<code > localName </code>不要更改<br>对于除了<code> ELEMENT_NODE </code>和<code>
     *  ATTRIBUTE_NODE </code>之外的任何类型的节点以及使用DOM Level 1方法创建的节点,例如<code > createElement </code>从<code> Docume
     * nt </code>接口,这总是<code> null </code>。
     * 
     * 
     * @since DOM Level 2
     */
    public String getPrefix();
    /**
     * The namespace prefix of this node, or <code>null</code> if it is
     * unspecified. When it is defined to be <code>null</code>, setting it
     * has no effect, including if the node is read-only.
     * <br>Note that setting this attribute, when permitted, changes the
     * <code>nodeName</code> attribute, which holds the qualified name, as
     * well as the <code>tagName</code> and <code>name</code> attributes of
     * the <code>Element</code> and <code>Attr</code> interfaces, when
     * applicable.
     * <br>Setting the prefix to <code>null</code> makes it unspecified,
     * setting it to an empty string is implementation dependent.
     * <br>Note also that changing the prefix of an attribute that is known to
     * have a default value, does not make a new attribute with the default
     * value and the original prefix appear, since the
     * <code>namespaceURI</code> and <code>localName</code> do not change.
     * <br>For nodes of any type other than <code>ELEMENT_NODE</code> and
     * <code>ATTRIBUTE_NODE</code> and nodes created with a DOM Level 1
     * method, such as <code>createElement</code> from the
     * <code>Document</code> interface, this is always <code>null</code>.
     * <p>
     * 此节点的命名空间前缀或<code> null </code>(如果未指定)当定义为<code> null </code>时,设置它无效,包括节点是只读<请注意,在允许的情况下设置此属性会更改保存有限定名称的<code>
     *  nodeName </code>属性以及<code> tagName </code>和<code> name </code>适用时,<code> Element </code>和<code> Attr
     *  </code>接口的属性<br>将前缀设置为<code> null </code>使其未指定,将其设置为空字符串实现依赖<br>请注意,更改已知具有默认值的属性的前缀不会使新属性带有默认值和原始前缀,
     * 因为<code> namespaceURI </code>和<code > localName </code>不要更改<br>对于除了<code> ELEMENT_NODE </code>和<code>
     *  ATTRIBUTE_NODE </code>之外的任何类型的节点以及使用DOM Level 1方法创建的节点,例如<code > createElement </code>从<code> Docume
     * nt </code>接口,这总是<code> null </code>。
     * 
     * 
     * @exception DOMException
     *   INVALID_CHARACTER_ERR: Raised if the specified prefix contains an
     *   illegal character according to the XML version in use specified in
     *   the <code>Document.xmlVersion</code> attribute.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this node is readonly.
     *   <br>NAMESPACE_ERR: Raised if the specified <code>prefix</code> is
     *   malformed per the Namespaces in XML specification, if the
     *   <code>namespaceURI</code> of this node is <code>null</code>, if the
     *   specified prefix is "xml" and the <code>namespaceURI</code> of this
     *   node is different from "<a href='http://www.w3.org/XML/1998/namespace'>
     *   http://www.w3.org/XML/1998/namespace</a>", if this node is an attribute and the specified prefix is "xmlns" and
     *   the <code>namespaceURI</code> of this node is different from "<a href='http://www.w3.org/2000/xmlns/'>http://www.w3.org/2000/xmlns/</a>", or if this node is an attribute and the <code>qualifiedName</code> of
     *   this node is "xmlns" [<a href='http://www.w3.org/TR/1999/REC-xml-names-19990114/'>XML Namespaces</a>]
     *   .
     *
     * @since DOM Level 2
     */
    public void setPrefix(String prefix)
                               throws DOMException;

    /**
     * Returns the local part of the qualified name of this node.
     * <br>For nodes of any type other than <code>ELEMENT_NODE</code> and
     * <code>ATTRIBUTE_NODE</code> and nodes created with a DOM Level 1
     * method, such as <code>Document.createElement()</code>, this is always
     * <code>null</code>.
     *
     * <p>
     * 返回此节点的限定名称的局部部分<br>对于除了<code> ELEMENT_NODE </code>和<code> ATTRIBUTE_NODE </code>之外的任何类型的节点以及使用DOM Lev
     * el 1方法创建的节点,例如<code> DocumentcreateElement()</code>,这总是<code> null </code>。
     * 
     * 
     * @since DOM Level 2
     */
    public String getLocalName();

    /**
     * Returns whether this node (if it is an element) has any attributes.
     * <p>
     *  返回此节点(如果它是一个元素)是否有任何属性
     * 
     * 
     * @return Returns <code>true</code> if this node has any attributes,
     *   <code>false</code> otherwise.
     *
     * @since DOM Level 2
     */
    public boolean hasAttributes();

    /**
     * The absolute base URI of this node or <code>null</code> if the
     * implementation wasn't able to obtain an absolute URI. This value is
     * computed as described in . However, when the <code>Document</code>
     * supports the feature "HTML" [<a href='http://www.w3.org/TR/2003/REC-DOM-Level-2-HTML-20030109'>DOM Level 2 HTML</a>]
     * , the base URI is computed using first the value of the href
     * attribute of the HTML BASE element if any, and the value of the
     * <code>documentURI</code> attribute from the <code>Document</code>
     * interface otherwise.
     *
     * <p>
     * 该节点的绝对基本URI或<code> null </code>(如果实现无法获取绝对URI)此值的计算方法如下所述。
     * 但是,当<code> Document </code> "HTML"[<a href='http://wwww3org/TR/2003/REC-DOM-Level-2-HTML-20030109'> D
     * OM Level 2 HTML </a>],基本URI使用第一个HTML BASE元素的href属性的值(如果有)和来自<code> Document </code>接口的<code> document
     * URI </code>属性的值。
     * 该节点的绝对基本URI或<code> null </code>(如果实现无法获取绝对URI)此值的计算方法如下所述。
     * 
     * 
     * @since DOM Level 3
     */
    public String getBaseURI();

    // DocumentPosition
    /**
     * The two nodes are disconnected. Order between disconnected nodes is
     * always implementation-specific.
     * <p>
     *  两个节点断开断开节点之间的顺序始终是实现特定的
     * 
     */
    public static final short DOCUMENT_POSITION_DISCONNECTED = 0x01;
    /**
     * The second node precedes the reference node.
     * <p>
     *  第二节点在参考节点之前
     * 
     */
    public static final short DOCUMENT_POSITION_PRECEDING = 0x02;
    /**
     * The node follows the reference node.
     * <p>
     *  节点跟随参考节点
     * 
     */
    public static final short DOCUMENT_POSITION_FOLLOWING = 0x04;
    /**
     * The node contains the reference node. A node which contains is always
     * preceding, too.
     * <p>
     *  节点包含引用节点包含的节点也始终在前面
     * 
     */
    public static final short DOCUMENT_POSITION_CONTAINS = 0x08;
    /**
     * The node is contained by the reference node. A node which is contained
     * is always following, too.
     * <p>
     * 节点包含在引用节点中包含的节点也总是跟随
     * 
     */
    public static final short DOCUMENT_POSITION_CONTAINED_BY = 0x10;
    /**
     * The determination of preceding versus following is
     * implementation-specific.
     * <p>
     *  前面和后面的确定是实现特定的
     * 
     */
    public static final short DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC = 0x20;

    /**
     * Compares the reference node, i.e. the node on which this method is
     * being called, with a node, i.e. the one passed as a parameter, with
     * regard to their position in the document and according to the
     * document order.
     * <p>
     *  将参考节点(即,其上正调用该方法的节点)与作为参数传递的节点相关于它们在文档中的位置并且根据文档顺序
     * 
     * 
     * @param other The node to compare against the reference node.
     * @return Returns how the node is positioned relatively to the reference
     *   node.
     * @exception DOMException
     *   NOT_SUPPORTED_ERR: when the compared nodes are from different DOM
     *   implementations that do not coordinate to return consistent
     *   implementation-specific results.
     *
     * @since DOM Level 3
     */
    public short compareDocumentPosition(Node other)
                                         throws DOMException;

    /**
     * This attribute returns the text content of this node and its
     * descendants. When it is defined to be <code>null</code>, setting it
     * has no effect. On setting, any possible children this node may have
     * are removed and, if it the new string is not empty or
     * <code>null</code>, replaced by a single <code>Text</code> node
     * containing the string this attribute is set to.
     * <br> On getting, no serialization is performed, the returned string
     * does not contain any markup. No whitespace normalization is performed
     * and the returned string does not contain the white spaces in element
     * content (see the attribute
     * <code>Text.isElementContentWhitespace</code>). Similarly, on setting,
     * no parsing is performed either, the input string is taken as pure
     * textual content.
     * <br>The string returned is made of the text content of this node
     * depending on its type, as defined below:
     * <table border='1' cellpadding='3'>
     * <tr>
     * <th>Node type</th>
     * <th>Content</th>
     * </tr>
     * <tr>
     * <td valign='top' rowspan='1' colspan='1'>
     * ELEMENT_NODE, ATTRIBUTE_NODE, ENTITY_NODE, ENTITY_REFERENCE_NODE,
     * DOCUMENT_FRAGMENT_NODE</td>
     * <td valign='top' rowspan='1' colspan='1'>concatenation of the <code>textContent</code>
     * attribute value of every child node, excluding COMMENT_NODE and
     * PROCESSING_INSTRUCTION_NODE nodes. This is the empty string if the
     * node has no children.</td>
     * </tr>
     * <tr>
     * <td valign='top' rowspan='1' colspan='1'>TEXT_NODE, CDATA_SECTION_NODE, COMMENT_NODE,
     * PROCESSING_INSTRUCTION_NODE</td>
     * <td valign='top' rowspan='1' colspan='1'><code>nodeValue</code></td>
     * </tr>
     * <tr>
     * <td valign='top' rowspan='1' colspan='1'>DOCUMENT_NODE,
     * DOCUMENT_TYPE_NODE, NOTATION_NODE</td>
     * <td valign='top' rowspan='1' colspan='1'><em>null</em></td>
     * </tr>
     * </table>
     * <p>
     * 此属性返回此节点及其后代的文本内容当定义为<code> null </code>时,设置它没有效果设置时,此节点可能具有的任何可能的子项将被删除,如果新的字符串不为空或<code> null </code>
     * ,替换为单个包含字符串的<code> Text </code>节点此属性设置为<br>在获取时,不执行序列化,返回的字符串不包含任何标记不执行空白规范化,返回的字符串不包含元素内容中的空格(请参阅属性<code>
     *  TextisElementContentWhitespace </code>)同样,设置时也不执行解析,输入字符串作为纯文本内容<br>返回的字符串取决于此节点的文本内容,具体取决于其类型,如下所述：
     * 。
     * <table border='1' cellpadding='3'>
     * <tr>
     * <th>节点类型</th> <th>内容</th>
     * </tr>
     * <tr>
     * <td valign='top' rowspan='1' colspan='1'>
     *  EOPMENT </code>属性值的每个子节点的<code> textContent </code>属性值的连接,但不包括子节点的<code> textContent </code>属性值的字符串E
     * LEMENT_NODE,ATTRIBUTE_NODE,ENTITY_NODE,ENTITY_REFERENCE_NODE,DOCUMENT_FRAGMENT_NODE </td> <td valign ='top'rowspan ='1'colspan ='1' COMMENT_NODE和PROCESSING_INSTRUCTION_NODE节点如果节点没有子节点,则为空字符串</td>
     * 。
     * </tr>
     * <tr>
     *  <td valign ='top'rowspan ='1'colspan ='1'> TEXT_NODE,CDATA_SECTION_NODE,COMMENT_NODE,PROCESSING_INST
     * RUCTION_NODE </td> <td valign ='top'rowspan ='1'colspan ='1'> <code> nodeValue </code> </td>。
     * </tr>
     * <tr>
     *  <td valign ='top'rowspan ='1'colspan ='1'> DOCUMENT_NODE,DOCUMENT_TYPE_NODE,NOTATION_NODE </td> <td valign ='top'rowspan ='1'colspan ='1'>
     *  < / em> </td>。
     * </tr>
     * </table>
     * 
     * @exception DOMException
     *   DOMSTRING_SIZE_ERR: Raised when it would return more characters than
     *   fit in a <code>DOMString</code> variable on the implementation
     *   platform.
     *
     * @since DOM Level 3
     */
    public String getTextContent()
                                         throws DOMException;
    /**
     * This attribute returns the text content of this node and its
     * descendants. When it is defined to be <code>null</code>, setting it
     * has no effect. On setting, any possible children this node may have
     * are removed and, if it the new string is not empty or
     * <code>null</code>, replaced by a single <code>Text</code> node
     * containing the string this attribute is set to.
     * <br> On getting, no serialization is performed, the returned string
     * does not contain any markup. No whitespace normalization is performed
     * and the returned string does not contain the white spaces in element
     * content (see the attribute
     * <code>Text.isElementContentWhitespace</code>). Similarly, on setting,
     * no parsing is performed either, the input string is taken as pure
     * textual content.
     * <br>The string returned is made of the text content of this node
     * depending on its type, as defined below:
     * <table border='1' cellpadding='3'>
     * <tr>
     * <th>Node type</th>
     * <th>Content</th>
     * </tr>
     * <tr>
     * <td valign='top' rowspan='1' colspan='1'>
     * ELEMENT_NODE, ATTRIBUTE_NODE, ENTITY_NODE, ENTITY_REFERENCE_NODE,
     * DOCUMENT_FRAGMENT_NODE</td>
     * <td valign='top' rowspan='1' colspan='1'>concatenation of the <code>textContent</code>
     * attribute value of every child node, excluding COMMENT_NODE and
     * PROCESSING_INSTRUCTION_NODE nodes. This is the empty string if the
     * node has no children.</td>
     * </tr>
     * <tr>
     * <td valign='top' rowspan='1' colspan='1'>TEXT_NODE, CDATA_SECTION_NODE, COMMENT_NODE,
     * PROCESSING_INSTRUCTION_NODE</td>
     * <td valign='top' rowspan='1' colspan='1'><code>nodeValue</code></td>
     * </tr>
     * <tr>
     * <td valign='top' rowspan='1' colspan='1'>DOCUMENT_NODE,
     * DOCUMENT_TYPE_NODE, NOTATION_NODE</td>
     * <td valign='top' rowspan='1' colspan='1'><em>null</em></td>
     * </tr>
     * </table>
     * <p>
     * 此属性返回此节点及其后代的文本内容当定义为<code> null </code>时,设置它没有效果设置时,此节点可能具有的任何可能的子项将被删除,如果新的字符串不为空或<code> null </code>
     * ,替换为单个包含字符串的<code> Text </code>节点此属性设置为<br>在获取时,不执行序列化,返回的字符串不包含任何标记不执行空白规范化,返回的字符串不包含元素内容中的空格(请参阅属性<code>
     *  TextisElementContentWhitespace </code>)同样,设置时也不执行解析,输入字符串作为纯文本内容<br>返回的字符串取决于此节点的文本内容,具体取决于其类型,如下所述：
     * 。
     * <table border='1' cellpadding='3'>
     * <tr>
     * <th>节点类型</th> <th>内容</th>
     * </tr>
     * <tr>
     * <td valign='top' rowspan='1' colspan='1'>
     *  EOPMENT </code>属性值的每个子节点的<code> textContent </code>属性值的连接,但不包括子节点的<code> textContent </code>属性值的字符串E
     * LEMENT_NODE,ATTRIBUTE_NODE,ENTITY_NODE,ENTITY_REFERENCE_NODE,DOCUMENT_FRAGMENT_NODE </td> <td valign ='top'rowspan ='1'colspan ='1' COMMENT_NODE和PROCESSING_INSTRUCTION_NODE节点如果节点没有子节点,则为空字符串</td>
     * 。
     * </tr>
     * <tr>
     *  <td valign ='top'rowspan ='1'colspan ='1'> TEXT_NODE,CDATA_SECTION_NODE,COMMENT_NODE,PROCESSING_INST
     * RUCTION_NODE </td> <td valign ='top'rowspan ='1'colspan ='1'> <code> nodeValue </code> </td>。
     * </tr>
     * <tr>
     *  <td valign ='top'rowspan ='1'colspan ='1'> DOCUMENT_NODE,DOCUMENT_TYPE_NODE,NOTATION_NODE </td> <td valign ='top'rowspan ='1'colspan ='1'>
     *  < / em> </td>。
     * </tr>
     * </table>
     * 
     * @exception DOMException
     *   NO_MODIFICATION_ALLOWED_ERR: Raised when the node is readonly.
     *
     * @since DOM Level 3
     */
    public void setTextContent(String textContent)
                                         throws DOMException;

    /**
     * Returns whether this node is the same node as the given one.
     * <br>This method provides a way to determine whether two
     * <code>Node</code> references returned by the implementation reference
     * the same object. When two <code>Node</code> references are references
     * to the same object, even if through a proxy, the references may be
     * used completely interchangeably, such that all attributes have the
     * same values and calling the same DOM method on either reference
     * always has exactly the same effect.
     * <p>
     * 返回此节点是否与给定的节点是相同的节点<br>此方法提供了一种方法来确定实现引用的两个<code> Node </code>引用是否引用相同的对象当两个<code> Node </code >引用是对同
     * 一对象的引用,即使通过代理,引用可以完全可互换地使用,使得所有属性具有相同的值,并且对任一引用调用相同的DOM方法总是具有完全相同的效果。
     * 
     * 
     * @param other The node to test against.
     * @return Returns <code>true</code> if the nodes are the same,
     *   <code>false</code> otherwise.
     *
     * @since DOM Level 3
     */
    public boolean isSameNode(Node other);

    /**
     * Look up the prefix associated to the given namespace URI, starting from
     * this node. The default namespace declarations are ignored by this
     * method.
     * <br>See  for details on the algorithm used by this method.
     * <p>
     *  查找与给定命名空间URI相关联的前缀(从此节点开始)此方法忽略默认命名空间声明<br>有关此方法使用的算法的详细信息,请参阅
     * 
     * 
     * @param namespaceURI The namespace URI to look for.
     * @return Returns an associated namespace prefix if found or
     *   <code>null</code> if none is found. If more than one prefix are
     *   associated to the namespace prefix, the returned namespace prefix
     *   is implementation dependent.
     *
     * @since DOM Level 3
     */
    public String lookupPrefix(String namespaceURI);

    /**
     *  This method checks if the specified <code>namespaceURI</code> is the
     * default namespace or not.
     * <p>
     *  此方法检查指定的<code> namespaceURI </code>是否是默认命名空间
     * 
     * 
     * @param namespaceURI The namespace URI to look for.
     * @return Returns <code>true</code> if the specified
     *   <code>namespaceURI</code> is the default namespace,
     *   <code>false</code> otherwise.
     *
     * @since DOM Level 3
     */
    public boolean isDefaultNamespace(String namespaceURI);

    /**
     * Look up the namespace URI associated to the given prefix, starting from
     * this node.
     * <br>See  for details on the algorithm used by this method.
     * <p>
     * 查找与给定前缀关联的命名空间URI,从此节点开始<br>有关此方法使用的算法的详细信息,请参阅
     * 
     * 
     * @param prefix The prefix to look for. If this parameter is
     *   <code>null</code>, the method will return the default namespace URI
     *   if any.
     * @return Returns the associated namespace URI or <code>null</code> if
     *   none is found.
     *
     * @since DOM Level 3
     */
    public String lookupNamespaceURI(String prefix);

    /**
     * Tests whether two nodes are equal.
     * <br>This method tests for equality of nodes, not sameness (i.e.,
     * whether the two nodes are references to the same object) which can be
     * tested with <code>Node.isSameNode()</code>. All nodes that are the
     * same will also be equal, though the reverse may not be true.
     * <br>Two nodes are equal if and only if the following conditions are
     * satisfied:
     * <ul>
     * <li>The two nodes are of the same type.
     * </li>
     * <li>The following string
     * attributes are equal: <code>nodeName</code>, <code>localName</code>,
     * <code>namespaceURI</code>, <code>prefix</code>, <code>nodeValue</code>
     * . This is: they are both <code>null</code>, or they have the same
     * length and are character for character identical.
     * </li>
     * <li>The
     * <code>attributes</code> <code>NamedNodeMaps</code> are equal. This
     * is: they are both <code>null</code>, or they have the same length and
     * for each node that exists in one map there is a node that exists in
     * the other map and is equal, although not necessarily at the same
     * index.
     * </li>
     * <li>The <code>childNodes</code> <code>NodeLists</code> are equal.
     * This is: they are both <code>null</code>, or they have the same
     * length and contain equal nodes at the same index. Note that
     * normalization can affect equality; to avoid this, nodes should be
     * normalized before being compared.
     * </li>
     * </ul>
     * <br>For two <code>DocumentType</code> nodes to be equal, the following
     * conditions must also be satisfied:
     * <ul>
     * <li>The following string attributes
     * are equal: <code>publicId</code>, <code>systemId</code>,
     * <code>internalSubset</code>.
     * </li>
     * <li>The <code>entities</code>
     * <code>NamedNodeMaps</code> are equal.
     * </li>
     * <li>The <code>notations</code>
     * <code>NamedNodeMaps</code> are equal.
     * </li>
     * </ul>
     * <br>On the other hand, the following do not affect equality: the
     * <code>ownerDocument</code>, <code>baseURI</code>, and
     * <code>parentNode</code> attributes, the <code>specified</code>
     * attribute for <code>Attr</code> nodes, the <code>schemaTypeInfo</code>
     *  attribute for <code>Attr</code> and <code>Element</code> nodes, the
     * <code>Text.isElementContentWhitespace</code> attribute for
     * <code>Text</code> nodes, as well as any user data or event listeners
     * registered on the nodes.
     * <p ><b>Note:</b>  As a general rule, anything not mentioned in the
     * description above is not significant in consideration of equality
     * checking. Note that future versions of this specification may take
     * into account more attributes and implementations conform to this
     * specification are expected to be updated accordingly.
     * <p>
     *  测试两个节点是否相等<br>此方法测试节点的相等性,而不是测试与<code> NodeisSameNode()</code>可测试的同一性(即,两个节点是否是对同一对象的引用)</code>尽管相反可
     * 能不是真实的。
     * <br>如果且仅当满足以下条件时,两个节点是相等的：。
     * <ul>
     *  <li>两个节点的类型相同
     * </li>
     * <li>以下字符串属性相等：<code> nodeName </code>,<code> localName </code>,<code> namespaceURI </code>,<code>前缀</code>
     * ,<code> nodeValue </code>这是：他们都是<code> null </code>,或他们有相同的长度和字符的字符相同。
     * </li>
     *  <li> <code>属性</code> <code> NamedNodeMaps </code>相等这是：它们都是<code> null </code>,或者它们具有相同的长度,一个映射存在存在于另
     * 一映射中并且相等的节点,尽管不一定在相同的索引。
     * </li>
     * <li> <code> childNodes </code> <code> NodeLists </code>相等这是：它们都是<code> null </code>,或者它们具有相同的长度, inde
     * x注意,归一化可以影响等式;为了避免这种情况,应该在比较之前对节点进行归一化。
     * </li>
     * </ul>
     *  <br>要使两个<code> DocumentType </code>节点相等,还必须满足以下条件：
     * <ul>
     *  <li>以下字符串属性相等：<code> publicId </code>,<code> systemId </code>,<code> internalSubset </code>
     * </li>
     *  <li> <code> entities </code> <code> NamedNodeMaps </code>是相等的
     * 
     * @param arg The node to compare equality with.
     * @return Returns <code>true</code> if the nodes are equal,
     *   <code>false</code> otherwise.
     *
     * @since DOM Level 3
     */
    public boolean isEqualNode(Node arg);

    /**
     *  This method returns a specialized object which implements the
     * specialized APIs of the specified feature and version, as specified
     * in . The specialized object may also be obtained by using
     * binding-specific casting methods but is not necessarily expected to,
     * as discussed in . This method also allow the implementation to
     * provide specialized objects which do not support the <code>Node</code>
     *  interface.
     * <p>
     * </li>
     *  <li> <code>符号</code> <code> NamedNodeMaps </code>是相等的
     * </li>
     * </ul>
     * <br>另一方面,以下不影响平等：<code> ownerDocument </code>,<code> baseURI </code>和<code> parentNode </code>属性, <code>
     *  Attr </code>节点的</code>属性,<code> Attr </code>和<code> Element </code>节点的<code> schemaTypeInfo </code>属
     * 性, <p> <b>注册</b>注释的任何用户数据或事件侦听器的</code> TextisElementContentWhitespace </code>属性注意：</b>作为一般规则,在考虑等式检查
     * 时并不重要。
     * 注意,本说明书的未来版本可以考虑更多的属性,并且符合本规范的实现方式预期将被相应地更新。
     * 
     * 
     * @param feature  The name of the feature requested. Note that any plus
     *   sign "+" prepended to the name of the feature will be ignored since
     *   it is not significant in the context of this method.
     * @param version  This is the version number of the feature to test.
     * @return  Returns an object which implements the specialized APIs of
     *   the specified feature and version, if any, or <code>null</code> if
     *   there is no object which implements interfaces associated with that
     *   feature. If the <code>DOMObject</code> returned by this method
     *   implements the <code>Node</code> interface, it must delegate to the
     *   primary core <code>Node</code> and not return results inconsistent
     *   with the primary core <code>Node</code> such as attributes,
     *   childNodes, etc.
     *
     * @since DOM Level 3
     */
    public Object getFeature(String feature,
                             String version);

    /**
     * Associate an object to a key on this node. The object can later be
     * retrieved from this node by calling <code>getUserData</code> with the
     * same key.
     * <p>
     * 
     * @param key The key to associate the object to.
     * @param data The object to associate to the given key, or
     *   <code>null</code> to remove any existing association to that key.
     * @param handler The handler to associate to that key, or
     *   <code>null</code>.
     * @return Returns the <code>DOMUserData</code> previously associated to
     *   the given key on this node, or <code>null</code> if there was none.
     *
     * @since DOM Level 3
     */
    public Object setUserData(String key,
                              Object data,
                              UserDataHandler handler);

    /**
     * Retrieves the object associated to a key on a this node. The object
     * must first have been set to this node by calling
     * <code>setUserData</code> with the same key.
     * <p>
     * 这个方法返回一个专门的对象,实现指定的特性和版本的专门的API,如在指定的专门的对象也可以通过使用特定于绑定的转换方法获得,但不一定期望,如在此方法中所讨论的也允许实现提供不支持<code> Node 
     * </code>接口的专用对象。
     * 
     * 
     * @param key The key the object is associated to.
     * @return Returns the <code>DOMUserData</code> associated to the given
     *   key on this node, or <code>null</code> if there was none.
     *
     * @since DOM Level 3
     */
    public Object getUserData(String key);

}
