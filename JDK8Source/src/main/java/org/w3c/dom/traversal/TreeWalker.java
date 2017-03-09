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
 * <code>TreeWalker</code> objects are used to navigate a document tree or
 * subtree using the view of the document defined by their
 * <code>whatToShow</code> flags and filter (if any). Any function which
 * performs navigation using a <code>TreeWalker</code> will automatically
 * support any view defined by a <code>TreeWalker</code>.
 * <p>Omitting nodes from the logical view of a subtree can result in a
 * structure that is substantially different from the same subtree in the
 * complete, unfiltered document. Nodes that are siblings in the
 * <code>TreeWalker</code> view may be children of different, widely
 * separated nodes in the original view. For instance, consider a
 * <code>NodeFilter</code> that skips all nodes except for Text nodes and
 * the root node of a document. In the logical view that results, all text
 * nodes will be siblings and appear as direct children of the root node, no
 * matter how deeply nested the structure of the original document.
 * <p>See also the <a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Traversal-Range-20001113'>Document Object Model (DOM) Level 2 Traversal and Range Specification</a>.
 * <p>
 * <code> TreeWalker </code>对象用于使用由其<code> whatToShow </code>标志和过滤器(如果有)定义的文档的视图来浏览文档树或子树。
 * 使用<code> TreeWalker </code>执行导航的任何函数将自动支持由<code> TreeWalker </code>定义的任何视图。
 *  <p>从子树的逻辑视图省略节点可能导致与完整的未过滤文档中的相同子树明显不同的结构。
 * 作为<code> TreeWalker </code>视图中的兄弟节点的节点可能是原始视图中不同的,广泛分离的节点的子节点。
 * 例如,考虑一个<code> NodeFilter </code>跳过除文本节点和文档的根节点之外的所有节点。
 * 在逻辑视图中,所有文本节点都将是兄弟节点,并显示为根节点的直接子节点,无论原始文档的结构有多深度嵌套。
 *  <p>另请参阅<a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Taversal-Range-20001113'>文档对象模型(DOM)2级遍历和范
 * 围规范</a>。
 * 在逻辑视图中,所有文本节点都将是兄弟节点,并显示为根节点的直接子节点,无论原始文档的结构有多深度嵌套。
 * 
 * 
 * @since DOM Level 2
 */
public interface TreeWalker {
    /**
     * The <code>root</code> node of the <code>TreeWalker</code>, as specified
     * when it was created.
     * <p>
     *  创建时指定的<code> TreeWalker </code>的<code> root </code>节点。
     * 
     */
    public Node getRoot();

    /**
     * This attribute determines which node types are presented via the
     * <code>TreeWalker</code>. The available set of constants is defined in
     * the <code>NodeFilter</code> interface.  Nodes not accepted by
     * <code>whatToShow</code> will be skipped, but their children may still
     * be considered. Note that this skip takes precedence over the filter,
     * if any.
     * <p>
     * 此属性确定通过<code> TreeWalker </code>显示哪些节点类型。可用的常量集在<code> NodeFilter </code>接口中定义。
     * 不会被<code> whatToShow </code>接受的节点将被跳过,但是他们的孩子仍然可以被考虑。请注意,此跳过优先于过滤器(如果有)。
     * 
     */
    public int getWhatToShow();

    /**
     * The filter used to screen nodes.
     * <p>
     *  用于筛选节点的过滤器。
     * 
     */
    public NodeFilter getFilter();

    /**
     * The value of this flag determines whether the children of entity
     * reference nodes are visible to the <code>TreeWalker</code>. If false,
     * these children  and their descendants will be rejected. Note that
     * this rejection takes precedence over <code>whatToShow</code> and the
     * filter, if any.
     * <br> To produce a view of the document that has entity references
     * expanded and does not expose the entity reference node itself, use
     * the <code>whatToShow</code> flags to hide the entity reference node
     * and set <code>expandEntityReferences</code> to true when creating the
     * <code>TreeWalker</code>. To produce a view of the document that has
     * entity reference nodes but no entity expansion, use the
     * <code>whatToShow</code> flags to show the entity reference node and
     * set <code>expandEntityReferences</code> to false.
     * <p>
     *  此标志的值确定实体引用节点的子项是否对<code> TreeWalker </code>可见。如果为假,这些孩子及其后代将被拒绝。
     * 请注意,此拒绝优先于<code> whatToShow </code>和过滤器(如果有)。
     *  <br>要生成具有实体引用扩展并且不公开实体引用节点本身的文档的视图,请使用<code> whatToShow </code>标志来隐藏实体引用节点,并将<code> expandEntityRefe
     * rences <代码>为true时创建<code> TreeWalker </code>。
     * 请注意,此拒绝优先于<code> whatToShow </code>和过滤器(如果有)。
     * 要生成具有实体引用节点但没有实体扩展的文档的视图,请使用<code> whatToShow </code>标志来显示实体引用节点,并将<code> expandEntityReferences </code>
     * 设置为false。
     * 请注意,此拒绝优先于<code> whatToShow </code>和过滤器(如果有)。
     * 
     */
    public boolean getExpandEntityReferences();

    /**
     * The node at which the <code>TreeWalker</code> is currently positioned.
     * <br>Alterations to the DOM tree may cause the current node to no longer
     * be accepted by the <code>TreeWalker</code>'s associated filter.
     * <code>currentNode</code> may also be explicitly set to any node,
     * whether or not it is within the subtree specified by the
     * <code>root</code> node or would be accepted by the filter and
     * <code>whatToShow</code> flags. Further traversal occurs relative to
     * <code>currentNode</code> even if it is not part of the current view,
     * by applying the filters in the requested direction; if no traversal
     * is possible, <code>currentNode</code> is not changed.
     * <p>
     * <code> TreeWalker </code>当前所在的节点。 <br>更改DOM树可能会导致当前节点不再被<code> TreeWalker </code>的关联过滤器接受。
     *  <code> currentNode </code>也可以被明确地设置到任何节点,无论它是否在由<code> root </code>节点指定的子树中或者将被过滤器和<code> whatToShow
     *  < / code>标志。
     * <code> TreeWalker </code>当前所在的节点。 <br>更改DOM树可能会导致当前节点不再被<code> TreeWalker </code>的关联过滤器接受。
     * 通过在请求的方向上应用过滤器,相对于<code> currentNode </code>进行进一步遍历,即使它不是当前视图的一部分;如果不可能进行遍历,则<code> currentNode </code>
     * 不会改变。
     * <code> TreeWalker </code>当前所在的节点。 <br>更改DOM树可能会导致当前节点不再被<code> TreeWalker </code>的关联过滤器接受。
     * 
     */
    public Node getCurrentNode();
    /**
     * The node at which the <code>TreeWalker</code> is currently positioned.
     * <br>Alterations to the DOM tree may cause the current node to no longer
     * be accepted by the <code>TreeWalker</code>'s associated filter.
     * <code>currentNode</code> may also be explicitly set to any node,
     * whether or not it is within the subtree specified by the
     * <code>root</code> node or would be accepted by the filter and
     * <code>whatToShow</code> flags. Further traversal occurs relative to
     * <code>currentNode</code> even if it is not part of the current view,
     * by applying the filters in the requested direction; if no traversal
     * is possible, <code>currentNode</code> is not changed.
     * <p>
     *  <code> TreeWalker </code>当前所在的节点。 <br>更改DOM树可能会导致当前节点不再被<code> TreeWalker </code>的关联过滤器接受。
     *  <code> currentNode </code>也可以被明确地设置到任何节点,无论它是否在由<code> root </code>节点指定的子树中或者将被过滤器和<code> whatToShow
     *  < / code>标志。
     *  <code> TreeWalker </code>当前所在的节点。 <br>更改DOM树可能会导致当前节点不再被<code> TreeWalker </code>的关联过滤器接受。
     * 通过在请求的方向上应用过滤器,相对于<code> currentNode </code>进行进一步遍历,即使它不是当前视图的一部分;如果不可能进行遍历,则<code> currentNode </code>
     * 不会改变。
     *  <code> TreeWalker </code>当前所在的节点。 <br>更改DOM树可能会导致当前节点不再被<code> TreeWalker </code>的关联过滤器接受。
     * 
     * 
     * @exception DOMException
     *   NOT_SUPPORTED_ERR: Raised if an attempt is made to set
     *   <code>currentNode</code> to <code>null</code>.
     */
    public void setCurrentNode(Node currentNode)
                         throws DOMException;

    /**
     * Moves to and returns the closest visible ancestor node of the current
     * node. If the search for <code>parentNode</code> attempts to step
     * upward from the <code>TreeWalker</code>'s <code>root</code> node, or
     * if it fails to find a visible ancestor node, this method retains the
     * current position and returns <code>null</code>.
     * <p>
     * 移动到并返回当前节点最近的可见祖代节点。
     * 如果搜索<code> parentNode </code>试图从<code> TreeWalker </code>的<code> root </code>节点向上跳,或者如果找不到可见的祖先节点,方法保
     * 留当前位置并返回<code> null </code>。
     * 移动到并返回当前节点最近的可见祖代节点。
     * 
     * 
     * @return The new parent node, or <code>null</code> if the current node
     *   has no parent  in the <code>TreeWalker</code>'s logical view.
     */
    public Node parentNode();

    /**
     * Moves the <code>TreeWalker</code> to the first visible child of the
     * current node, and returns the new node. If the current node has no
     * visible children, returns <code>null</code>, and retains the current
     * node.
     * <p>
     *  将<code> TreeWalker </code>移动到当前节点的第一个可见子节点,并返回新节点。如果当前节点没有可见的子节点,则返回<code> null </code>,并保留当前节点。
     * 
     * 
     * @return The new node, or <code>null</code> if the current node has no
     *   visible children  in the <code>TreeWalker</code>'s logical view.
     */
    public Node firstChild();

    /**
     * Moves the <code>TreeWalker</code> to the last visible child of the
     * current node, and returns the new node. If the current node has no
     * visible children, returns <code>null</code>, and retains the current
     * node.
     * <p>
     *  将<code> TreeWalker </code>移动到当前节点的最后一个可见子节点,并返回新节点。如果当前节点没有可见的子节点,则返回<code> null </code>,并保留当前节点。
     * 
     * 
     * @return The new node, or <code>null</code> if the current node has no
     *   children  in the <code>TreeWalker</code>'s logical view.
     */
    public Node lastChild();

    /**
     * Moves the <code>TreeWalker</code> to the previous sibling of the
     * current node, and returns the new node. If the current node has no
     * visible previous sibling, returns <code>null</code>, and retains the
     * current node.
     * <p>
     *  将<code> TreeWalker </code>移动到当前节点的上一个兄弟节点,并返回新节点。如果当前节点没有可见的前一个同级,返回<code> null </code>,并保留当前节点。
     * 
     * 
     * @return The new node, or <code>null</code> if the current node has no
     *   previous sibling.  in the <code>TreeWalker</code>'s logical view.
     */
    public Node previousSibling();

    /**
     * Moves the <code>TreeWalker</code> to the next sibling of the current
     * node, and returns the new node. If the current node has no visible
     * next sibling, returns <code>null</code>, and retains the current node.
     * <p>
     *  将<code> TreeWalker </code>移动到当前节点的下一个兄弟节点,并返回新节点。如果当前节点没有可见的下一个兄弟节点,则返回<code> null </code>,并保留当前节点。
     * 
     * 
     * @return The new node, or <code>null</code> if the current node has no
     *   next sibling.  in the <code>TreeWalker</code>'s logical view.
     */
    public Node nextSibling();

    /**
     * Moves the <code>TreeWalker</code> to the previous visible node in
     * document order relative to the current node, and returns the new
     * node. If the current node has no previous node,  or if the search for
     * <code>previousNode</code> attempts to step upward from the
     * <code>TreeWalker</code>'s <code>root</code> node,  returns
     * <code>null</code>, and retains the current node.
     * <p>
     * 以相对于当前节点的文档顺序将<code> TreeWalker </code>移动到上一个可见节点,并返回新节点。
     * 如果当前节点没有上一个节点,或者如果搜索<code> previousNode </code>试图从<code> TreeWalker </code>的<code> root </code>节点向上移动
     * , code> null </code>,并保留当前节点。
     * 以相对于当前节点的文档顺序将<code> TreeWalker </code>移动到上一个可见节点,并返回新节点。
     * 
     * 
     * @return The new node, or <code>null</code> if the current node has no
     *   previous node  in the <code>TreeWalker</code>'s logical view.
     */
    public Node previousNode();

    /**
     * Moves the <code>TreeWalker</code> to the next visible node in document
     * order relative to the current node, and returns the new node. If the
     * current node has no next node, or if the search for nextNode attempts
     * to step upward from the <code>TreeWalker</code>'s <code>root</code>
     * node, returns <code>null</code>, and retains the current node.
     * <p>
     *  将<code> TreeWalker </code>移动到相对于当前节点的文档顺序中的下一个可见节点,并返回新节点。
     * 如果当前节点没有下一个节点,或者如果搜索nextNode试图从<code> TreeWalker </code>的<code> root </code>节点向上移动,则返回<code> null </code>
     *  ,并保留当前节点。
     * 
     * @return The new node, or <code>null</code> if the current node has no
     *   next node  in the <code>TreeWalker</code>'s logical view.
     */
    public Node nextNode();

}
