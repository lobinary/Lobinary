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
 * Copyright (c) 2000 World Wide Web Consortium,
 * (Massachusetts Institute of Technology, Institut National de
 * Recherche en Informatique et en Automatique, Keio University). All
 * Rights Reserved. This program is distributed under the W3C's Software
 * Intellectual Property License. This program is distributed in the
 * hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.
 * See W3C License http://www.w3.org/Consortium/Legal/ for more details.
 * <p>
 *  版权所有(c)2000万维网联盟,(马萨诸塞理工学院,庆应义藩大学信息自动化研究所)。版权所有。该程序根据W3C的软件知识产权许可证分发。
 * 这个程序是分发的,希望它将是有用的,但没有任何保证;甚至没有对适销性或适用于特定用途的隐含保证。有关详细信息,请参阅W3C许可证http://www.w3.org/Consortium/Legal/。
 * 
 */

package org.w3c.dom.traversal;

import org.w3c.dom.Node;
import org.w3c.dom.DOMException;

/**
 * <code>NodeIterators</code> are used to step through a set of nodes, e.g.
 * the set of nodes in a <code>NodeList</code>, the document subtree
 * governed by a particular <code>Node</code>, the results of a query, or
 * any other set of nodes. The set of nodes to be iterated is determined by
 * the implementation of the <code>NodeIterator</code>. DOM Level 2
 * specifies a single <code>NodeIterator</code> implementation for
 * document-order traversal of a document subtree. Instances of these
 * <code>NodeIterators</code> are created by calling
 * <code>DocumentTraversal</code><code>.createNodeIterator()</code>.
 * <p>See also the <a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Traversal-Range-20001113'>Document Object Model (DOM) Level 2 Traversal and Range Specification</a>.
 * <p>
 *  <code> NodeIterators </code>用于遍历一组节点,例如<code> NodeList </code>中的节点集合,由特定<code> Node </code>管理的文档子树,查
 * 询的结果或任何其他节点集合。
 * 要迭代的节点集由<code> NodeIterator </code>的实现确定。 DOM级别2为文档子树的文档顺序遍历指定了单个<code> NodeIterator </code>实现。
 * 通过调用<code> DocumentTraversal </code> <code> .createNodeIterator()</code>创建这些<code> NodeIterators </code>
 * 的实例。
 * 要迭代的节点集由<code> NodeIterator </code>的实现确定。 DOM级别2为文档子树的文档顺序遍历指定了单个<code> NodeIterator </code>实现。
 *  <p>另请参阅<a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Taversal-Range-20001113'>文档对象模型(DOM)2级遍历和范
 * 围规范</a>。
 * 要迭代的节点集由<code> NodeIterator </code>的实现确定。 DOM级别2为文档子树的文档顺序遍历指定了单个<code> NodeIterator </code>实现。
 * 
 * 
 * @since DOM Level 2
 */
public interface NodeIterator {
    /**
     * The root node of the <code>NodeIterator</code>, as specified when it
     * was created.
     * <p>
     *  <code> NodeIterator </code>的根节点,在创建时指定。
     * 
     */
    public Node getRoot();

    /**
     * This attribute determines which node types are presented via the
     * <code>NodeIterator</code>. The available set of constants is defined
     * in the <code>NodeFilter</code> interface.  Nodes not accepted by
     * <code>whatToShow</code> will be skipped, but their children may still
     * be considered. Note that this skip takes precedence over the filter,
     * if any.
     * <p>
     * 此属性确定通过<code> NodeIterator </code>呈现哪些节点类型。可用的常量集在<code> NodeFilter </code>接口中定义。
     * 不会被<code> whatToShow </code>接受的节点将被跳过,但是他们的孩子仍然可以被考虑。请注意,此跳过优先于过滤器(如果有)。
     * 
     */
    public int getWhatToShow();

    /**
     * The <code>NodeFilter</code> used to screen nodes.
     * <p>
     *  用于屏幕节点的<code> NodeFilter </code>。
     * 
     */
    public NodeFilter getFilter();

    /**
     *  The value of this flag determines whether the children of entity
     * reference nodes are visible to the <code>NodeIterator</code>. If
     * false, these children  and their descendants will be rejected. Note
     * that this rejection takes precedence over <code>whatToShow</code> and
     * the filter. Also note that this is currently the only situation where
     * <code>NodeIterators</code> may reject a complete subtree rather than
     * skipping individual nodes.
     * <br>
     * <br> To produce a view of the document that has entity references
     * expanded and does not expose the entity reference node itself, use
     * the <code>whatToShow</code> flags to hide the entity reference node
     * and set <code>expandEntityReferences</code> to true when creating the
     * <code>NodeIterator</code>. To produce a view of the document that has
     * entity reference nodes but no entity expansion, use the
     * <code>whatToShow</code> flags to show the entity reference node and
     * set <code>expandEntityReferences</code> to false.
     * <p>
     *  此标志的值确定实体引用节点的子项是否对<code> NodeIterator </code>可见。如果为假,这些孩子及其后代将被拒绝。
     * 请注意,此拒绝优先于<code> whatToShow </code>和过滤器。
     * 还要注意,这是当前唯一的情况,其中<code> NodeIterators </code>可以拒绝一个完整的子树,而不跳过单个节点。
     * <br>
     *  <br>要生成具有实体引用扩展并且不公开实体引用节点本身的文档的视图,请使用<code> whatToShow </code>标志来隐藏实体引用节点,并将<code> expandEntityRefe
     * rences <代码>为true时创建<code> NodeIterator </code>。
     * 要生成具有实体引用节点但没有实体扩展的文档的视图,请使用<code> whatToShow </code>标志来显示实体引用节点,并将<code> expandEntityReferences </code>
     * 设置为false。
     * 
     */
    public boolean getExpandEntityReferences();

    /**
     * Returns the next node in the set and advances the position of the
     * <code>NodeIterator</code> in the set. After a
     * <code>NodeIterator</code> is created, the first call to
     * <code>nextNode()</code> returns the first node in the set.
     * <p>
     * 返回集合中的下一个节点,并推进集合中<code> NodeIterator </code>的位置。
     * 创建<code> NodeIterator </code>之后,第一次调用<code> nextNode()</code>返回集合中的第一个节点。
     * 
     * 
     * @return The next <code>Node</code> in the set being iterated over, or
     *   <code>null</code> if there are no more members in that set.
     * @exception DOMException
     *   INVALID_STATE_ERR: Raised if this method is called after the
     *   <code>detach</code> method was invoked.
     */
    public Node nextNode()
                         throws DOMException;

    /**
     * Returns the previous node in the set and moves the position of the
     * <code>NodeIterator</code> backwards in the set.
     * <p>
     *  返回集合中的上一个节点,并在集合中向后移动<code> NodeIterator </code>的位置。
     * 
     * 
     * @return The previous <code>Node</code> in the set being iterated over,
     *   or <code>null</code> if there are no more members in that set.
     * @exception DOMException
     *   INVALID_STATE_ERR: Raised if this method is called after the
     *   <code>detach</code> method was invoked.
     */
    public Node previousNode()
                             throws DOMException;

    /**
     * Detaches the <code>NodeIterator</code> from the set which it iterated
     * over, releasing any computational resources and placing the
     * <code>NodeIterator</code> in the INVALID state. After
     * <code>detach</code> has been invoked, calls to <code>nextNode</code>
     * or <code>previousNode</code> will raise the exception
     * INVALID_STATE_ERR.
     * <p>
     *  从迭代的集合中分离<code> NodeIterator </code>,释放任何计算资源并将<code> NodeIterator </code>置于INVALID状态。
     * 调用<code> detach </code>后,调用<code> nextNode </code>或<code> previousNode </code>会引发异常INVALID_STATE_ERR。
     */
    public void detach();

}
