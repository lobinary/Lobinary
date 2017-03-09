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

package org.w3c.dom.ranges;

import org.w3c.dom.Node;
import org.w3c.dom.DOMException;
import org.w3c.dom.DocumentFragment;

/**
 * <p>See also the <a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Traversal-Range-20001113'>Document Object Model (DOM) Level 2 Traversal and Range Specification</a>.
 * <p>
 *  <p>另请参阅<a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Taversal-Range-20001113'>文档对象模型(DOM)2级遍历和范
 * 围规范</a>。
 * 
 * 
 * @since DOM Level 2
 */
public interface Range {
    /**
     * Node within which the Range begins
     * <p>
     *  范围开始的节点
     * 
     * 
     * @exception DOMException
     *   INVALID_STATE_ERR: Raised if <code>detach()</code> has already been
     *   invoked on this object.
     */
    public Node getStartContainer()
                       throws DOMException;

    /**
     * Offset within the starting node of the Range.
     * <p>
     *  范围的起始节点内的偏移。
     * 
     * 
     * @exception DOMException
     *   INVALID_STATE_ERR: Raised if <code>detach()</code> has already been
     *   invoked on this object.
     */
    public int getStartOffset()
                       throws DOMException;

    /**
     * Node within which the Range ends
     * <p>
     *  范围结束的节点
     * 
     * 
     * @exception DOMException
     *   INVALID_STATE_ERR: Raised if <code>detach()</code> has already been
     *   invoked on this object.
     */
    public Node getEndContainer()
                       throws DOMException;

    /**
     * Offset within the ending node of the Range.
     * <p>
     *  范围的结束节点内的偏移。
     * 
     * 
     * @exception DOMException
     *   INVALID_STATE_ERR: Raised if <code>detach()</code> has already been
     *   invoked on this object.
     */
    public int getEndOffset()
                       throws DOMException;

    /**
     * TRUE if the Range is collapsed
     * <p>
     *  如果范围折叠,则为TRUE
     * 
     * 
     * @exception DOMException
     *   INVALID_STATE_ERR: Raised if <code>detach()</code> has already been
     *   invoked on this object.
     */
    public boolean getCollapsed()
                       throws DOMException;

    /**
     * The deepest common ancestor container of the Range's two
     * boundary-points.
     * <p>
     *  Range的两个边界点的最深的共同祖先容器。
     * 
     * 
     * @exception DOMException
     *   INVALID_STATE_ERR: Raised if <code>detach()</code> has already been
     *   invoked on this object.
     */
    public Node getCommonAncestorContainer()
                       throws DOMException;

    /**
     * Sets the attributes describing the start of the Range.
     * <p>
     *  设置描述范围开始的属性。
     * 
     * 
     * @param refNode The <code>refNode</code> value. This parameter must be
     *   different from <code>null</code>.
     * @param offset The <code>startOffset</code> value.
     * @exception RangeException
     *   INVALID_NODE_TYPE_ERR: Raised if <code>refNode</code> or an ancestor
     *   of <code>refNode</code> is an Entity, Notation, or DocumentType
     *   node.
     * @exception DOMException
     *   INDEX_SIZE_ERR: Raised if <code>offset</code> is negative or greater
     *   than the number of child units in <code>refNode</code>. Child units
     *   are 16-bit units if <code>refNode</code> is a type of CharacterData
     *   node (e.g., a Text or Comment node) or a ProcessingInstruction
     *   node. Child units are Nodes in all other cases.
     *   <br>INVALID_STATE_ERR: Raised if <code>detach()</code> has already
     *   been invoked on this object.
     *   <br>WRONG_DOCUMENT_ERR: Raised if <code>refNode</code> was created
     *   from a different document than the one that created this range.
     */
    public void setStart(Node refNode,
                         int offset)
                         throws RangeException, DOMException;

    /**
     * Sets the attributes describing the end of a Range.
     * <p>
     *  设置描述范围结束的属性。
     * 
     * 
     * @param refNode The <code>refNode</code> value. This parameter must be
     *   different from <code>null</code>.
     * @param offset The <code>endOffset</code> value.
     * @exception RangeException
     *   INVALID_NODE_TYPE_ERR: Raised if <code>refNode</code> or an ancestor
     *   of <code>refNode</code> is an Entity, Notation, or DocumentType
     *   node.
     * @exception DOMException
     *   INDEX_SIZE_ERR: Raised if <code>offset</code> is negative or greater
     *   than the number of child units in <code>refNode</code>. Child units
     *   are 16-bit units if <code>refNode</code> is a type of CharacterData
     *   node (e.g., a Text or Comment node) or a ProcessingInstruction
     *   node. Child units are Nodes in all other cases.
     *   <br>INVALID_STATE_ERR: Raised if <code>detach()</code> has already
     *   been invoked on this object.
     *   <br>WRONG_DOCUMENT_ERR: Raised if <code>refNode</code> was created
     *   from a different document than the one that created this range.
     */
    public void setEnd(Node refNode,
                       int offset)
                       throws RangeException, DOMException;

    /**
     * Sets the start position to be before a node
     * <p>
     *  将开始位置设置为节点之前
     * 
     * 
     * @param refNode Range starts before <code>refNode</code>
     * @exception RangeException
     *   INVALID_NODE_TYPE_ERR: Raised if the root container of
     *   <code>refNode</code> is not an Attr, Document, or DocumentFragment
     *   node or if <code>refNode</code> is a Document, DocumentFragment,
     *   Attr, Entity, or Notation node.
     * @exception DOMException
     *   INVALID_STATE_ERR: Raised if <code>detach()</code> has already been
     *   invoked on this object.
     *   <br>WRONG_DOCUMENT_ERR: Raised if <code>refNode</code> was created
     *   from a different document than the one that created this range.
     */
    public void setStartBefore(Node refNode)
                               throws RangeException, DOMException;

    /**
     * Sets the start position to be after a node
     * <p>
     *  将开始位置设置为节点之后
     * 
     * 
     * @param refNode Range starts after <code>refNode</code>
     * @exception RangeException
     *   INVALID_NODE_TYPE_ERR: Raised if the root container of
     *   <code>refNode</code> is not an Attr, Document, or DocumentFragment
     *   node or if <code>refNode</code> is a Document, DocumentFragment,
     *   Attr, Entity, or Notation node.
     * @exception DOMException
     *   INVALID_STATE_ERR: Raised if <code>detach()</code> has already been
     *   invoked on this object.
     *   <br>WRONG_DOCUMENT_ERR: Raised if <code>refNode</code> was created
     *   from a different document than the one that created this range.
     */
    public void setStartAfter(Node refNode)
                              throws RangeException, DOMException;

    /**
     * Sets the end position to be before a node.
     * <p>
     *  将结束位置设置为在节点之前。
     * 
     * 
     * @param refNode Range ends before <code>refNode</code>
     * @exception RangeException
     *   INVALID_NODE_TYPE_ERR: Raised if the root container of
     *   <code>refNode</code> is not an Attr, Document, or DocumentFragment
     *   node or if <code>refNode</code> is a Document, DocumentFragment,
     *   Attr, Entity, or Notation node.
     * @exception DOMException
     *   INVALID_STATE_ERR: Raised if <code>detach()</code> has already been
     *   invoked on this object.
     *   <br>WRONG_DOCUMENT_ERR: Raised if <code>refNode</code> was created
     *   from a different document than the one that created this range.
     */
    public void setEndBefore(Node refNode)
                             throws RangeException, DOMException;

    /**
     * Sets the end of a Range to be after a node
     * <p>
     *  将范围的结束设置为在节点之后
     * 
     * 
     * @param refNode Range ends after <code>refNode</code>.
     * @exception RangeException
     *   INVALID_NODE_TYPE_ERR: Raised if the root container of
     *   <code>refNode</code> is not an Attr, Document or DocumentFragment
     *   node or if <code>refNode</code> is a Document, DocumentFragment,
     *   Attr, Entity, or Notation node.
     * @exception DOMException
     *   INVALID_STATE_ERR: Raised if <code>detach()</code> has already been
     *   invoked on this object.
     *   <br>WRONG_DOCUMENT_ERR: Raised if <code>refNode</code> was created
     *   from a different document than the one that created this range.
     */
    public void setEndAfter(Node refNode)
                            throws RangeException, DOMException;

    /**
     * Collapse a Range onto one of its boundary-points
     * <p>
     *  将范围折叠到其边界点之一上
     * 
     * 
     * @param toStart If TRUE, collapses the Range onto its start; if FALSE,
     *   collapses it onto its end.
     * @exception DOMException
     *   INVALID_STATE_ERR: Raised if <code>detach()</code> has already been
     *   invoked on this object.
     */
    public void collapse(boolean toStart)
                         throws DOMException;

    /**
     * Select a node and its contents
     * <p>
     *  选择节点及其内容
     * 
     * 
     * @param refNode The node to select.
     * @exception RangeException
     *   INVALID_NODE_TYPE_ERR: Raised if an ancestor of <code>refNode</code>
     *   is an Entity, Notation or DocumentType node or if
     *   <code>refNode</code> is a Document, DocumentFragment, Attr, Entity,
     *   or Notation node.
     * @exception DOMException
     *   INVALID_STATE_ERR: Raised if <code>detach()</code> has already been
     *   invoked on this object.
     *   <br>WRONG_DOCUMENT_ERR: Raised if <code>refNode</code> was created
     *   from a different document than the one that created this range.
     */
    public void selectNode(Node refNode)
                           throws RangeException, DOMException;

    /**
     * Select the contents within a node
     * <p>
     *  选择节点中的内容
     * 
     * 
     * @param refNode Node to select from
     * @exception RangeException
     *   INVALID_NODE_TYPE_ERR: Raised if <code>refNode</code> or an ancestor
     *   of <code>refNode</code> is an Entity, Notation or DocumentType node.
     * @exception DOMException
     *   INVALID_STATE_ERR: Raised if <code>detach()</code> has already been
     *   invoked on this object.
     *   <br>WRONG_DOCUMENT_ERR: Raised if <code>refNode</code> was created
     *   from a different document than the one that created this range.
     */
    public void selectNodeContents(Node refNode)
                                   throws RangeException, DOMException;

    // CompareHow
    /**
     * Compare start boundary-point of <code>sourceRange</code> to start
     * boundary-point of Range on which <code>compareBoundaryPoints</code>
     * is invoked.
     * <p>
     * 比较<code> sourceRange </code>的起始边界点到调用<code> compareBoundaryPoints </code>的Range的起始边界点。
     * 
     */
    public static final short START_TO_START            = 0;
    /**
     * Compare start boundary-point of <code>sourceRange</code> to end
     * boundary-point of Range on which <code>compareBoundaryPoints</code>
     * is invoked.
     * <p>
     *  比较<code> sourceRange </code>的起始边界点到调用<code> compareBoundaryPoints </code>的Range的结束边界点。
     * 
     */
    public static final short START_TO_END              = 1;
    /**
     * Compare end boundary-point of <code>sourceRange</code> to end
     * boundary-point of Range on which <code>compareBoundaryPoints</code>
     * is invoked.
     * <p>
     *  比较<code> sourceRange </code>的结束边界点到调用<code> compareBoundaryPoints </code>的Range的结束边界点。
     * 
     */
    public static final short END_TO_END                = 2;
    /**
     * Compare end boundary-point of <code>sourceRange</code> to start
     * boundary-point of Range on which <code>compareBoundaryPoints</code>
     * is invoked.
     * <p>
     *  比较<code> sourceRange </code>的结束边界点到调用<code> compareBoundaryPoints </code>的Range的开始边界点。
     * 
     */
    public static final short END_TO_START              = 3;

    /**
     * Compare the boundary-points of two Ranges in a document.
     * <p>
     *  比较文档中两个范围的边界点。
     * 
     * 
     * @param how A code representing the type of comparison, as defined
     *   above.
     * @param sourceRange The <code>Range</code> on which this current
     *   <code>Range</code> is compared to.
     * @return  -1, 0 or 1 depending on whether the corresponding
     *   boundary-point of the Range is respectively before, equal to, or
     *   after the corresponding boundary-point of <code>sourceRange</code>.
     * @exception DOMException
     *   WRONG_DOCUMENT_ERR: Raised if the two Ranges are not in the same
     *   Document or DocumentFragment.
     *   <br>INVALID_STATE_ERR: Raised if <code>detach()</code> has already
     *   been invoked on this object.
     */
    public short compareBoundaryPoints(short how,
                                       Range sourceRange)
                                       throws DOMException;

    /**
     * Removes the contents of a Range from the containing document or
     * document fragment without returning a reference to the removed
     * content.
     * <p>
     *  从包含的文档或文档片段中删除范围的内容,而不返回对删除的内容的引用。
     * 
     * 
     * @exception DOMException
     *   NO_MODIFICATION_ALLOWED_ERR: Raised if any portion of the content of
     *   the Range is read-only or any of the nodes that contain any of the
     *   content of the Range are read-only.
     *   <br>INVALID_STATE_ERR: Raised if <code>detach()</code> has already
     *   been invoked on this object.
     */
    public void deleteContents()
                               throws DOMException;

    /**
     * Moves the contents of a Range from the containing document or document
     * fragment to a new DocumentFragment.
     * <p>
     *  将包含文档或文档片段的范围内容移动到新的DocumentFragment。
     * 
     * 
     * @return A DocumentFragment containing the extracted contents.
     * @exception DOMException
     *   NO_MODIFICATION_ALLOWED_ERR: Raised if any portion of the content of
     *   the Range is read-only or any of the nodes which contain any of the
     *   content of the Range are read-only.
     *   <br>HIERARCHY_REQUEST_ERR: Raised if a DocumentType node would be
     *   extracted into the new DocumentFragment.
     *   <br>INVALID_STATE_ERR: Raised if <code>detach()</code> has already
     *   been invoked on this object.
     */
    public DocumentFragment extractContents()
                                            throws DOMException;

    /**
     * Duplicates the contents of a Range
     * <p>
     *  复制范围的内容
     * 
     * 
     * @return A DocumentFragment that contains content equivalent to this
     *   Range.
     * @exception DOMException
     *   HIERARCHY_REQUEST_ERR: Raised if a DocumentType node would be
     *   extracted into the new DocumentFragment.
     *   <br>INVALID_STATE_ERR: Raised if <code>detach()</code> has already
     *   been invoked on this object.
     */
    public DocumentFragment cloneContents()
                                          throws DOMException;

    /**
     * Inserts a node into the Document or DocumentFragment at the start of
     * the Range. If the container is a Text node, this will be split at the
     * start of the Range (as if the Text node's splitText method was
     * performed at the insertion point) and the insertion will occur
     * between the two resulting Text nodes. Adjacent Text nodes will not be
     * automatically merged. If the node to be inserted is a
     * DocumentFragment node, the children will be inserted rather than the
     * DocumentFragment node itself.
     * <p>
     *  将一个节点插入到范围开始处的Document或DocumentFragment中。
     * 如果容器是Text节点,则将在Range的开始处拆分(如同Text节点的splitText方法在插入点处执行),并且插入将出现在两个生成的Text节点之间。相邻文本节点将不会自动合并。
     * 如果要插入的节点是DocumentFragment节点,则将插入子节点,而不是DocumentFragment节点本身。
     * 
     * 
     * @param newNode The node to insert at the start of the Range
     * @exception DOMException
     *   NO_MODIFICATION_ALLOWED_ERR: Raised if an ancestor container of the
     *   start of the Range is read-only.
     *   <br>WRONG_DOCUMENT_ERR: Raised if <code>newNode</code> and the
     *   container of the start of the Range were not created from the same
     *   document.
     *   <br>HIERARCHY_REQUEST_ERR: Raised if the container of the start of
     *   the Range is of a type that does not allow children of the type of
     *   <code>newNode</code> or if <code>newNode</code> is an ancestor of
     *   the container.
     *   <br>INVALID_STATE_ERR: Raised if <code>detach()</code> has already
     *   been invoked on this object.
     * @exception RangeException
     *   INVALID_NODE_TYPE_ERR: Raised if <code>newNode</code> is an Attr,
     *   Entity, Notation, or Document node.
     */
    public void insertNode(Node newNode)
                           throws DOMException, RangeException;

    /**
     * Reparents the contents of the Range to the given node and inserts the
     * node at the position of the start of the Range.
     * <p>
     * 将Range的内容重新排列到给定节点,并将该节点插入到Range的开始位置。
     * 
     * 
     * @param newParent The node to surround the contents with.
     * @exception DOMException
     *   NO_MODIFICATION_ALLOWED_ERR: Raised if an ancestor container of
     *   either boundary-point of the Range is read-only.
     *   <br>WRONG_DOCUMENT_ERR: Raised if <code> newParent</code> and the
     *   container of the start of the Range were not created from the same
     *   document.
     *   <br>HIERARCHY_REQUEST_ERR: Raised if the container of the start of
     *   the Range is of a type that does not allow children of the type of
     *   <code>newParent</code> or if <code>newParent</code> is an ancestor
     *   of the container or if <code>node</code> would end up with a child
     *   node of a type not allowed by the type of <code>node</code>.
     *   <br>INVALID_STATE_ERR: Raised if <code>detach()</code> has already
     *   been invoked on this object.
     * @exception RangeException
     *   BAD_BOUNDARYPOINTS_ERR: Raised if the Range partially selects a
     *   non-text node.
     *   <br>INVALID_NODE_TYPE_ERR: Raised if <code> node</code> is an Attr,
     *   Entity, DocumentType, Notation, Document, or DocumentFragment node.
     */
    public void surroundContents(Node newParent)
                                 throws DOMException, RangeException;

    /**
     * Produces a new Range whose boundary-points are equal to the
     * boundary-points of the Range.
     * <p>
     *  生成一个新的范围,其边界点等于范围的边界点。
     * 
     * 
     * @return The duplicated Range.
     * @exception DOMException
     *   INVALID_STATE_ERR: Raised if <code>detach()</code> has already been
     *   invoked on this object.
     */
    public Range cloneRange()
                            throws DOMException;

    /**
     * Returns the contents of a Range as a string. This string contains only
     * the data characters, not any markup.
     * <p>
     *  以字符串形式返回Range的内容。此字符串只包含数据字符,不包含任何标记。
     * 
     * 
     * @return The contents of the Range.
     * @exception DOMException
     *   INVALID_STATE_ERR: Raised if <code>detach()</code> has already been
     *   invoked on this object.
     */
    public String toString()
                           throws DOMException;

    /**
     * Called to indicate that the Range is no longer in use and that the
     * implementation may relinquish any resources associated with this
     * Range. Subsequent calls to any methods or attribute getters on this
     * Range will result in a <code>DOMException</code> being thrown with an
     * error code of <code>INVALID_STATE_ERR</code>.
     * <p>
     *  调用以指示范围不再使用,并且实现可以放弃与该范围相关联的任何资源。
     * 对此范围上的任何方法或属性获取器的后续调用将导致以<code> INVALID_STATE_ERR </code>的错误代码抛出<code> DOMException </code>。
     * 
     * @exception DOMException
     *   INVALID_STATE_ERR: Raised if <code>detach()</code> has already been
     *   invoked on this object.
     */
    public void detach()
                       throws DOMException;

}
