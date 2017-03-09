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

/**
 * Filters are objects that know how to "filter out" nodes. If a
 * <code>NodeIterator</code> or <code>TreeWalker</code> is given a
 * <code>NodeFilter</code>, it applies the filter before it returns the next
 * node. If the filter says to accept the node, the traversal logic returns
 * it; otherwise, traversal looks for the next node and pretends that the
 * node that was rejected was not there.
 * <p>The DOM does not provide any filters. <code>NodeFilter</code> is just an
 * interface that users can implement to provide their own filters.
 * <p><code>NodeFilters</code> do not need to know how to traverse from node
 * to node, nor do they need to know anything about the data structure that
 * is being traversed. This makes it very easy to write filters, since the
 * only thing they have to know how to do is evaluate a single node. One
 * filter may be used with a number of different kinds of traversals,
 * encouraging code reuse.
 * <p>See also the <a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Traversal-Range-20001113'>Document Object Model (DOM) Level 2 Traversal and Range Specification</a>.
 * <p>
 * 过滤器是知道如何"过滤"节点的对象。
 * 如果给一个<code> NodeIterator </code>或<code> TreeWalker </code>一个<code> NodeFilter </code>,它会在返回下一个节点之前应用过
 * 滤器。
 * 过滤器是知道如何"过滤"节点的对象。如果过滤器说要接受节点,则遍历逻辑返回它;否则,遍历寻找下一个节点并假定被拒绝的节点不存在。 <p> DOM不提供任何过滤器。
 *  <code> NodeFilter </code>只是一个接口,用户可以实现它提供自己的过滤器。
 *  <p> <code> NodeFilters </code>不需要知道如何从节点遍历,也不需要知道正在遍历的数据结构的任何内容。这使得编写过滤器非常容易,因为他们必须知道如何做的是评估单个节点。
 * 一个过滤器可以与多种不同类型的遍历一起使用,从而促进代码重用。
 *  <p>另请参阅<a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Taversal-Range-20001113'>文档对象模型(DOM)2级遍历和范
 * 围规范</a>。
 * 一个过滤器可以与多种不同类型的遍历一起使用,从而促进代码重用。
 * 
 * 
 * @since DOM Level 2
 */
public interface NodeFilter {
    // Constants returned by acceptNode
    /**
     * Accept the node. Navigation methods defined for
     * <code>NodeIterator</code> or <code>TreeWalker</code> will return this
     * node.
     * <p>
     *  接受节点。为<code> NodeIterator </code>或<code> TreeWalker </code>定义的导航方法将返回此节点。
     * 
     */
    public static final short FILTER_ACCEPT             = 1;
    /**
     * Reject the node. Navigation methods defined for
     * <code>NodeIterator</code> or <code>TreeWalker</code> will not return
     * this node. For <code>TreeWalker</code>, the children of this node
     * will also be rejected. <code>NodeIterators</code> treat this as a
     * synonym for <code>FILTER_SKIP</code>.
     * <p>
     *  拒绝节点。为<code> NodeIterator </code>或<code> TreeWalker </code>定义的导航方法不会返回此节点。
     * 对于<code> TreeWalker </code>,此节点的子节点也将被拒绝。
     *  <code> NodeIterators </code>将此视为<code> FILTER_SKIP </code>的同义词。
     * 
     */
    public static final short FILTER_REJECT             = 2;
    /**
     * Skip this single node. Navigation methods defined for
     * <code>NodeIterator</code> or <code>TreeWalker</code> will not return
     * this node. For both <code>NodeIterator</code> and
     * <code>TreeWalker</code>, the children of this node will still be
     * considered.
     * <p>
     * 跳过此单个节点。为<code> NodeIterator </code>或<code> TreeWalker </code>定义的导航方法不会返回此节点。
     * 对于<code> NodeIterator </code>和<code> TreeWalker </code>,这个节点的子节点仍然会被考虑。
     * 
     */
    public static final short FILTER_SKIP               = 3;

    // Constants for whatToShow
    /**
     * Show all <code>Nodes</code>.
     * <p>
     *  显示所有<code>节点</code>。
     * 
     */
    public static final int SHOW_ALL                  = 0xFFFFFFFF;
    /**
     * Show <code>Element</code> nodes.
     * <p>
     *  显示<code>元素</code>节点。
     * 
     */
    public static final int SHOW_ELEMENT              = 0x00000001;
    /**
     * Show <code>Attr</code> nodes. This is meaningful only when creating an
     * <code>NodeIterator</code> or <code>TreeWalker</code> with an
     * attribute node as its <code>root</code>; in this case, it means that
     * the attribute node will appear in the first position of the iteration
     * or traversal. Since attributes are never children of other nodes,
     * they do not appear when traversing over the document tree.
     * <p>
     *  显示<code> Attr </code>节点。
     * 这只有在创建一个属性节点为<code> root </code>的<code> NodeIterator </code>或<code> TreeWalker </code>时才有意义;在这种情况下,它意
     * 味着属性节点将出现在迭代或遍历的第一个位置。
     *  显示<code> Attr </code>节点。由于属性永远不是其他节点的子节点,因此它们在遍历文档树时不会出现。
     * 
     */
    public static final int SHOW_ATTRIBUTE            = 0x00000002;
    /**
     * Show <code>Text</code> nodes.
     * <p>
     *  显示<code>文本</code>节点。
     * 
     */
    public static final int SHOW_TEXT                 = 0x00000004;
    /**
     * Show <code>CDATASection</code> nodes.
     * <p>
     *  显示<code> CDATASection </code>节点。
     * 
     */
    public static final int SHOW_CDATA_SECTION        = 0x00000008;
    /**
     * Show <code>EntityReference</code> nodes.
     * <p>
     *  显示<code> EntityReference </code>节点。
     * 
     */
    public static final int SHOW_ENTITY_REFERENCE     = 0x00000010;
    /**
     * Show <code>Entity</code> nodes. This is meaningful only when creating
     * an <code>NodeIterator</code> or <code>TreeWalker</code> with an
     * <code>Entity</code> node as its <code>root</code>; in this case, it
     * means that the <code>Entity</code> node will appear in the first
     * position of the traversal. Since entities are not part of the
     * document tree, they do not appear when traversing over the document
     * tree.
     * <p>
     *  显示<code>实体</code>节点。
     * 这只有在创建一个<code> NodeIterator </code>或<code> TreeWalker </code>并且以<code> Entity </code>节点作为<code> root 
     * </code>时才有意义;在这种情况下,它意味着<code> Entity </code>节点将出现在遍历的第一个位置。
     *  显示<code>实体</code>节点。由于实体不是文档树的一部分,因此它们在遍历文档树时不会出现。
     * 
     */
    public static final int SHOW_ENTITY               = 0x00000020;
    /**
     * Show <code>ProcessingInstruction</code> nodes.
     * <p>
     *  显示<code> ProcessingInstruction </code>节点。
     * 
     */
    public static final int SHOW_PROCESSING_INSTRUCTION = 0x00000040;
    /**
     * Show <code>Comment</code> nodes.
     * <p>
     *  显示<code>注释</code>节点。
     * 
     */
    public static final int SHOW_COMMENT              = 0x00000080;
    /**
     * Show <code>Document</code> nodes.
     * <p>
     *  显示<code>文档</code>节点。
     * 
     */
    public static final int SHOW_DOCUMENT             = 0x00000100;
    /**
     * Show <code>DocumentType</code> nodes.
     * <p>
     *  显示<code> DocumentType </code>节点。
     * 
     */
    public static final int SHOW_DOCUMENT_TYPE        = 0x00000200;
    /**
     * Show <code>DocumentFragment</code> nodes.
     * <p>
     *  显示<code> DocumentFragment </code>节点。
     * 
     */
    public static final int SHOW_DOCUMENT_FRAGMENT    = 0x00000400;
    /**
     * Show <code>Notation</code> nodes. This is meaningful only when creating
     * an <code>NodeIterator</code> or <code>TreeWalker</code> with a
     * <code>Notation</code> node as its <code>root</code>; in this case, it
     * means that the <code>Notation</code> node will appear in the first
     * position of the traversal. Since notations are not part of the
     * document tree, they do not appear when traversing over the document
     * tree.
     * <p>
     * 显示<code>符号</code>节点。
     * 这只有在创建一个<code> NodeIterator </code>或<code> TreeWalker </code>并以<code> Notation </code>节点作为<code> root
     *  </code>时才有意义;在这种情况下,这意味着<code> Notation </code>节点将出现在遍历的第一个位置。
     * 显示<code>符号</code>节点。由于符号不是文档树的一部分,因此它们在遍历文档树时不会出现。
     * 
     */
    public static final int SHOW_NOTATION             = 0x00000800;

    /**
     * Test whether a specified node is visible in the logical view of a
     * <code>TreeWalker</code> or <code>NodeIterator</code>. This function
     * will be called by the implementation of <code>TreeWalker</code> and
     * <code>NodeIterator</code>; it is not normally called directly from
     * user code. (Though you could do so if you wanted to use the same
     * filter to guide your own application logic.)
     * <p>
     * 
     * @param n The node to check to see if it passes the filter or not.
     * @return A constant to determine whether the node is accepted,
     *   rejected, or skipped, as defined above.
     */
    public short acceptNode(Node n);

}
