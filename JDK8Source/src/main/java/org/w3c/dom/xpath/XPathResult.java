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
 * Copyright (c) 2002 World Wide Web Consortium,
 * (Massachusetts Institute of Technology, Institut National de
 * Recherche en Informatique et en Automatique, Keio University). All
 * Rights Reserved. This program is distributed under the W3C's Software
 * Intellectual Property License. This program is distributed in the
 * hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.
 * See W3C License http://www.w3.org/Consortium/Legal/ for more details.
 * <p>
 *  版权所有(c)2002万维网联盟,(马萨诸塞理工学院,庆应义藩大学信息自动化研究所)。版权所有。该程序根据W3C的软件知识产权许可证分发。
 * 这个程序是分发的,希望它将是有用的,但没有任何保证;甚至没有对适销性或适用于特定用途的隐含保证。有关详细信息,请参阅W3C许可证http://www.w3.org/Consortium/Legal/。
 * 
 */

package org.w3c.dom.xpath;


import org.w3c.dom.Node;
import org.w3c.dom.DOMException;

/**
 * The <code>XPathResult</code> interface represents the result of the
 * evaluation of an XPath 1.0 expression within the context of a particular
 * node. Since evaluation of an XPath expression can result in various
 * result types, this object makes it possible to discover and manipulate
 * the type and value of the result.
 * <p>See also the <a href='http://www.w3.org/2002/08/WD-DOM-Level-3-XPath-20020820'>Document Object Model (DOM) Level 3 XPath Specification</a>.
 * <p>
 *  <code> XPathResult </code>接口表示在特定节点的上下文中评估XPath 1.0表达式的结果。
 * 由于XPath表达式的求值可以导致各种结果类型,因此该对象可以发现和操作结果的类型和值。
 *  <p>另请参见<a href='http://www.w3.org/2002/08/WD-DOM-Level-3-XPath-20020820'>文档对象模型(DOM)3级XPath规范< a>。
 * 
 */
public interface XPathResult {
    // XPathResultType
    /**
     * This code does not represent a specific type. An evaluation of an XPath
     * expression will never produce this type. If this type is requested,
     * then the evaluation returns whatever type naturally results from
     * evaluation of the expression.
     * <br>If the natural result is a node set when <code>ANY_TYPE</code> was
     * requested, then <code>UNORDERED_NODE_ITERATOR_TYPE</code> is always
     * the resulting type. Any other representation of a node set must be
     * explicitly requested.
     * <p>
     * 此代码不表示特定类型。 XPath表达式的评估将永远不会生成此类型。如果请求这种类型,则评估返回由表达式的评估自然产生的任何类型。
     *  <br>如果自然结果是在请求<code> ANY_TYPE </code>时设置的节点,则<code> UNORDERED_NODE_ITERATOR_TYPE </code>始终是生成的类型。
     * 必须明确请求节点集的任何其他表示。
     * 
     */
    public static final short ANY_TYPE                  = 0;
    /**
     * The result is a number as defined by . Document modification does not
     * invalidate the number, but may mean that reevaluation would not yield
     * the same number.
     * <p>
     *  结果是一个由定义的数字。文档修改不会使数字无效,但可能意味着重新评估将不会产生相同的数字。
     * 
     */
    public static final short NUMBER_TYPE               = 1;
    /**
     * The result is a string as defined by . Document modification does not
     * invalidate the string, but may mean that the string no longer
     * corresponds to the current document.
     * <p>
     *  结果是由定义的字符串。文档修改不会使字符串无效,但可能意味着字符串不再对应于当前文档。
     * 
     */
    public static final short STRING_TYPE               = 2;
    /**
     * The result is a boolean as defined by . Document modification does not
     * invalidate the boolean, but may mean that reevaluation would not
     * yield the same boolean.
     * <p>
     *  结果是一个布尔值。文档修改不会使布尔值无效,但可能意味着重新评估不会产生相同的布尔值。
     * 
     */
    public static final short BOOLEAN_TYPE              = 3;
    /**
     * The result is a node set as defined by  that will be accessed
     * iteratively, which may not produce nodes in a particular order.
     * Document modification invalidates the iteration.
     * <br>This is the default type returned if the result is a node set and
     * <code>ANY_TYPE</code> is requested.
     * <p>
     *  结果是如定义的节点集将被迭代地访问,其可能不以特定顺序产生节点。文档修改会使迭代无效。 <br>如果结果是节点集并且请求了<code> ANY_TYPE </code>,则这是返回的默认类型。
     * 
     */
    public static final short UNORDERED_NODE_ITERATOR_TYPE = 4;
    /**
     * The result is a node set as defined by  that will be accessed
     * iteratively, which will produce document-ordered nodes. Document
     * modification invalidates the iteration.
     * <p>
     *  结果是如定义的节点集将被迭代地访问,这将产生文档有序节点。文档修改会使迭代无效。
     * 
     */
    public static final short ORDERED_NODE_ITERATOR_TYPE = 5;
    /**
     * The result is a node set as defined by  that will be accessed as a
     * snapshot list of nodes that may not be in a particular order.
     * Document modification does not invalidate the snapshot but may mean
     * that reevaluation would not yield the same snapshot and nodes in the
     * snapshot may have been altered, moved, or removed from the document.
     * <p>
     * 结果是如定义的节点集将被作为可能不是以特定顺序的节点的快照列表来访问。文档修改不会使快照失效,但可能意味着重新评估将不会产生相同的快照,并且快照中的节点可能已被更改,移动或从文档中删除。
     * 
     */
    public static final short UNORDERED_NODE_SNAPSHOT_TYPE = 6;
    /**
     * The result is a node set as defined by  that will be accessed as a
     * snapshot list of nodes that will be in original document order.
     * Document modification does not invalidate the snapshot but may mean
     * that reevaluation would not yield the same snapshot and nodes in the
     * snapshot may have been altered, moved, or removed from the document.
     * <p>
     *  结果是如定义的节点集将被作为将以原始文档顺序的节点的快照列表来访问。文档修改不会使快照失效,但可能意味着重新评估将不会产生相同的快照,并且快照中的节点可能已被更改,移动或从文档中删除。
     * 
     */
    public static final short ORDERED_NODE_SNAPSHOT_TYPE = 7;
    /**
     * The result is a node set as defined by  and will be accessed as a
     * single node, which may be <code>null</code>if the node set is empty.
     * Document modification does not invalidate the node, but may mean that
     * the result node no longer corresponds to the current document. This
     * is a convenience that permits optimization since the implementation
     * can stop once any node in the in the resulting set has been found.
     * <br>If there are more than one node in the actual result, the single
     * node returned might not be the first in document order.
     * <p>
     *  结果是如由节点集定义的节点集并且将被作为单个节点访问,如果节点集为空,则可以是<code> null </code>。文档修改不会使节点无效,但可能意味着结果节点不再对应于当前文档。
     * 这是允许优化的方便,因为一旦找到结果集合中的任何节点,实现就可以停止。 <br>如果实际结果中有多个节点,则返回的单个节点可能不是文档顺序中的第一个。
     * 
     */
    public static final short ANY_UNORDERED_NODE_TYPE   = 8;
    /**
     * The result is a node set as defined by  and will be accessed as a
     * single node, which may be <code>null</code> if the node set is empty.
     * Document modification does not invalidate the node, but may mean that
     * the result node no longer corresponds to the current document. This
     * is a convenience that permits optimization since the implementation
     * can stop once the first node in document order of the resulting set
     * has been found.
     * <br>If there are more than one node in the actual result, the single
     * node returned will be the first in document order.
     * <p>
     * 结果是如由节点集定义的节点集并且将被作为单个节点访问,如果节点集为空,则可以是<code> null </code>。文档修改不会使节点无效,但可能意味着结果节点不再对应于当前文档。
     * 这是允许优化的方便,因为一旦找到了结果集合的文档顺序中的第一节点,则该实现可以停止。 <br>如果实际结果中有多个节点,返回的单个节点将是文档顺序中的第一个。
     * 
     */
    public static final short FIRST_ORDERED_NODE_TYPE   = 9;

    /**
     * A code representing the type of this result, as defined by the type
     * constants.
     * <p>
     *  代表此结果类型的代码,由类型常量定义。
     * 
     */
    public short getResultType();

    /**
     * The value of this number result. If the native double type of the DOM
     * binding does not directly support the exact IEEE 754 result of the
     * XPath expression, then it is up to the definition of the binding
     * binding to specify how the XPath number is converted to the native
     * binding number.
     * <p>
     *  此数字的值结果。如果DOM绑定的本机双重类型不直接支持XPath表达式的精确IEEE 754结果,则根据绑定绑定的定义来指定如何将XPath数转换为本机绑定数。
     * 
     * 
     * @exception XPathException
     *   TYPE_ERR: raised if <code>resultType</code> is not
     *   <code>NUMBER_TYPE</code>.
     */
    public double getNumberValue()
                             throws XPathException;

    /**
     * The value of this string result.
     * <p>
     *  此字符串的值result。
     * 
     * 
     * @exception XPathException
     *   TYPE_ERR: raised if <code>resultType</code> is not
     *   <code>STRING_TYPE</code>.
     */
    public String getStringValue()
                             throws XPathException;

    /**
     * The value of this boolean result.
     * <p>
     *  此布尔结果的值。
     * 
     * 
     * @exception XPathException
     *   TYPE_ERR: raised if <code>resultType</code> is not
     *   <code>BOOLEAN_TYPE</code>.
     */
    public boolean getBooleanValue()
                             throws XPathException;

    /**
     * The value of this single node result, which may be <code>null</code>.
     * <p>
     *  这个单节点结果的值,可以是<code> null </code>。
     * 
     * 
     * @exception XPathException
     *   TYPE_ERR: raised if <code>resultType</code> is not
     *   <code>ANY_UNORDERED_NODE_TYPE</code> or
     *   <code>FIRST_ORDERED_NODE_TYPE</code>.
     */
    public Node getSingleNodeValue()
                             throws XPathException;

    /**
     * Signifies that the iterator has become invalid. True if
     * <code>resultType</code> is <code>UNORDERED_NODE_ITERATOR_TYPE</code>
     * or <code>ORDERED_NODE_ITERATOR_TYPE</code> and the document has been
     * modified since this result was returned.
     * <p>
     *  表示迭代器已无效。
     * 如果<code> resultType </code>是<code> UNORDERED_NODE_ITERATOR_TYPE </code>或<code> ORDERED_NODE_ITERATOR_
     * TYPE </code>,并且文档在返回此结果后已修改,则为True。
     *  表示迭代器已无效。
     * 
     */
    public boolean getInvalidIteratorState();

    /**
     * The number of nodes in the result snapshot. Valid values for
     * snapshotItem indices are <code>0</code> to
     * <code>snapshotLength-1</code> inclusive.
     * <p>
     *  结果快照中的节点数。 snapshotItem索引的有效值为<code> 0 </code>至<code> snapshotLength-1 </code>(含)。
     * 
     * 
     * @exception XPathException
     *   TYPE_ERR: raised if <code>resultType</code> is not
     *   <code>UNORDERED_NODE_SNAPSHOT_TYPE</code> or
     *   <code>ORDERED_NODE_SNAPSHOT_TYPE</code>.
     */
    public int getSnapshotLength()
                             throws XPathException;

    /**
     * Iterates and returns the next node from the node set or
     * <code>null</code>if there are no more nodes.
     * <p>
     * 如果没有更多节点,则迭代并返回节点集合中的下一个节点或<code> null </code>。
     * 
     * 
     * @return Returns the next node.
     * @exception XPathException
     *   TYPE_ERR: raised if <code>resultType</code> is not
     *   <code>UNORDERED_NODE_ITERATOR_TYPE</code> or
     *   <code>ORDERED_NODE_ITERATOR_TYPE</code>.
     * @exception DOMException
     *   INVALID_STATE_ERR: The document has been mutated since the result was
     *   returned.
     */
    public Node iterateNext()
                            throws XPathException, DOMException;

    /**
     * Returns the <code>index</code>th item in the snapshot collection. If
     * <code>index</code> is greater than or equal to the number of nodes in
     * the list, this method returns <code>null</code>. Unlike the iterator
     * result, the snapshot does not become invalid, but may not correspond
     * to the current document if it is mutated.
     * <p>
     *  返回快照集合中的<code> index </code> th项。如果<code> index </code>大于或等于列表中的节点数,则此方法返回<code> null </code>。
     * 与迭代器结果不同,快照不会变得无效,但如果变异,则可能不对应当前文档。
     * 
     * @param index Index into the snapshot collection.
     * @return The node at the <code>index</code>th position in the
     *   <code>NodeList</code>, or <code>null</code> if that is not a valid
     *   index.
     * @exception XPathException
     *   TYPE_ERR: raised if <code>resultType</code> is not
     *   <code>UNORDERED_NODE_SNAPSHOT_TYPE</code> or
     *   <code>ORDERED_NODE_SNAPSHOT_TYPE</code>.
     */
    public Node snapshotItem(int index)
                             throws XPathException;

}
