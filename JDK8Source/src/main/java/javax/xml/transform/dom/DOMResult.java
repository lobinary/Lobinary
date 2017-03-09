/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2005, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.transform.dom;

import javax.xml.transform.Result;
import org.w3c.dom.Node;

/**
 * <p>Acts as a holder for a transformation result tree in the form of a Document Object Model (DOM) tree.</p>
 *
 * <p>If no output DOM source is set, the transformation will create a Document node as the holder for the result of the transformation,
 * which may be retrieved with {@link #getNode()}.</p>
 *
 * <p>
 *  <p>作为文档对象模型(DOM)树形式的转换结果树的持有者。</p>
 * 
 *  <p>如果未设置输出DOM源,则转换将创建一个Document节点作为转换结果的holder,可以使用{@link #getNode()}检索。</p>
 * 
 * 
 * @author <a href="Jeff.Suttor@Sun.com">Jeff Suttor</a>
 */
public class DOMResult implements Result {

    /** <p>If {@link javax.xml.transform.TransformerFactory#getFeature}
     * returns <code>true</code> when passed this value as an argument,
     * the <code>Transformer</code> supports <code>Result</code> output of this type.</p>
     * <p>
     *  当传递此值作为参数时,返回<code> true </code>,<code> Transformer </code>支持此类型的<code> Result </code>输出。
     * 
     */
    public static final String FEATURE = "http://javax.xml.transform.dom.DOMResult/feature";

    /**
     * <p>Zero-argument default constructor.</p>
     *
     * <p><code>node</code>,
     * <code>siblingNode</code> and
     * <code>systemId</code>
     * will be set to <code>null</code>.</p>
     * <p>
     *  <p>零参数默认构造函数。</p>
     * 
     *  <p> <code> node </code>,<code> siblingNode </code>和<code> systemId </code>将设置为<code> null </code>
     * 
     */
    public DOMResult() {
        setNode(null);
        setNextSibling(null);
        setSystemId(null);
    }

    /**
     * <p>Use a DOM node to create a new output target.</p>
     *
     * <p>In practice, the node should be
     * a {@link org.w3c.dom.Document} node,
     * a {@link org.w3c.dom.DocumentFragment} node, or
     * a {@link org.w3c.dom.Element} node.
     * In other words, a node that accepts children.</p>
     *
     * <p><code>siblingNode</code> and
     * <code>systemId</code>
     * will be set to <code>null</code>.</p>
     *
     * <p>
     *  <p>使用DOM节点创建新的输出目标。</p>
     * 
     *  <p>实际上,节点应该是{@link org.w3c.dom.Document}节点,{@link org.w3c.dom.DocumentFragment}节点或{@link org.w3c.dom.Element }
     * 节点。
     * 换句话说,一个接受孩子的节点。</p>。
     * 
     *  <p> <code> siblingNode </code>和<code> systemId </code>将设置为<code> null </code>。</p>
     * 
     * 
     * @param node The DOM node that will contain the result tree.
     */
    public DOMResult(Node node) {
        setNode(node);
        setNextSibling(null);
        setSystemId(null);
    }

    /**
     * <p>Use a DOM node to create a new output target with the specified System ID.<p>
     *
     * <p>In practice, the node should be
     * a {@link org.w3c.dom.Document} node,
     * a {@link org.w3c.dom.DocumentFragment} node, or
     * a {@link org.w3c.dom.Element} node.
     * In other words, a node that accepts children.</p>
     *
     * <p><code>siblingNode</code> will be set to <code>null</code>.</p>
     *
     * <p>
     *  <p>使用DOM节点创建具有指定系统ID的新输出目标。<p>
     * 
     *  <p>实际上,节点应该是{@link org.w3c.dom.Document}节点,{@link org.w3c.dom.DocumentFragment}节点或{@link org.w3c.dom.Element }
     * 节点。
     * 换句话说,一个接受孩子的节点。</p>。
     * 
     *  <p> <code> siblingNode </code>将设置为<code> null </code>。</p>
     * 
     * 
     * @param node The DOM node that will contain the result tree.
     * @param systemId The system identifier which may be used in association with this node.
     */
    public DOMResult(Node node, String systemId) {
        setNode(node);
        setNextSibling(null);
        setSystemId(systemId);
    }

    /**
     * <p>Use a DOM node to create a new output target specifying the child node where the result nodes should be inserted before.</p>
     *
     * <p>In practice, <code>node</code> and <code>nextSibling</code> should be
     * a {@link org.w3c.dom.Document} node,
     * a {@link org.w3c.dom.DocumentFragment} node, or
     * a {@link org.w3c.dom.Element} node.
     * In other words, a node that accepts children.</p>
     *
     * <p>Use <code>nextSibling</code> to specify the child node
     * where the result nodes should be inserted before.
     * If <code>nextSibling</code> is not a sibling of <code>node</code>,
     * then an <code>IllegalArgumentException</code> is thrown.
     * If <code>node</code> is <code>null</code> and <code>nextSibling</code> is not <code>null</code>,
     * then an <code>IllegalArgumentException</code> is thrown.
     * If <code>nextSibling</code> is <code>null</code>,
     * then the behavior is the same as calling {@link #DOMResult(Node node)},
     * i.e. append the result nodes as the last child of the specified <code>node</code>.</p>
     *
     * <p><code>systemId</code> will be set to <code>null</code>.</p>
     *
     * <p>
     * <p>使用DOM节点创建新的输出目标,指定之前应插入结果节点的子节点。</p>
     * 
     *  <p>实际上,<code> node </code>和<code> nextSibling </code>应是{@link org.w3c.dom.Document}节点,{@link org.w3c.dom.DocumentFragment }
     * 节点或{@link org.w3c.dom.Element}节点。
     * 换句话说,一个接受孩子的节点。</p>。
     * 
     *  <p>使用<code> nextSibling </code>指定之前应插入结果节点的子节点。
     * 如果<code> nextSibling </code>不是<code> node </code>的兄弟,那么会抛出<code> IllegalArgumentException </code>。
     * 如果<code> node </code>是<code> null </code>和<code> nextSibling </code>不是<code> null </code>,那么会抛出<code>
     *  IllegalArgumentException </code> 。
     * 如果<code> nextSibling </code>不是<code> node </code>的兄弟,那么会抛出<code> IllegalArgumentException </code>。
     * 如果<code> nextSibling </code>是<code> null </code>,则该行为与调用{@link #DOMResult(Node node)}相同,即将结果节点追加为指定的最
     * 后一个子节点<code> node </code>。
     * 如果<code> nextSibling </code>不是<code> node </code>的兄弟,那么会抛出<code> IllegalArgumentException </code>。
     * </p>。
     * 
     *  <p> <code> systemId </code>将设置为<code> null </code>。</p>
     * 
     * 
     * @param node The DOM node that will contain the result tree.
     * @param nextSibling The child node where the result nodes should be inserted before.
     *
     * @throws IllegalArgumentException If <code>nextSibling</code> is not a sibling of <code>node</code> or
     *   <code>node</code> is <code>null</code> and <code>nextSibling</code>
     *   is not <code>null</code>.
     *
     * @since 1.5
     */
    public DOMResult(Node node, Node nextSibling) {

        // does the corrent parent/child relationship exist?
        if (nextSibling != null) {
            // cannot be a sibling of a null node
            if (node == null) {
                throw new IllegalArgumentException("Cannot create a DOMResult when the nextSibling is contained by the \"null\" node.");
            }

            // nextSibling contained by node?
            if ((node.compareDocumentPosition(nextSibling)&Node.DOCUMENT_POSITION_CONTAINED_BY)==0) {
                throw new IllegalArgumentException("Cannot create a DOMResult when the nextSibling is not contained by the node.");
            }
        }

        setNode(node);
        setNextSibling(nextSibling);
        setSystemId(null);
    }

    /**
     * <p>Use a DOM node to create a new output target specifying the child node where the result nodes should be inserted before and
     * the specified System ID.</p>
     *
     * <p>In practice, <code>node</code> and <code>nextSibling</code> should be
     * a {@link org.w3c.dom.Document} node,
     * a {@link org.w3c.dom.DocumentFragment} node, or a
     * {@link org.w3c.dom.Element} node.
     * In other words, a node that accepts children.</p>
     *
     * <p>Use <code>nextSibling</code> to specify the child node
     * where the result nodes should be inserted before.
     * If <code>nextSibling</code> is not a sibling of <code>node</code>,
     * then an <code>IllegalArgumentException</code> is thrown.
     * If <code>node</code> is <code>null</code> and <code>nextSibling</code> is not <code>null</code>,
     * then an <code>IllegalArgumentException</code> is thrown.
     * If <code>nextSibling</code> is <code>null</code>,
     * then the behavior is the same as calling {@link #DOMResult(Node node, String systemId)},
     * i.e. append the result nodes as the last child of the specified node and use the specified System ID.</p>
     *
     * <p>
     *  <p>使用DOM节点创建新的输出目标,指定之前应插入结果节点的子节点和指定的系统ID。</p>
     * 
     *  <p>实际上,<code> node </code>和<code> nextSibling </code>应是{@link org.w3c.dom.Document}节点,{@link org.w3c.dom.DocumentFragment }
     * 节点或{@link org.w3c.dom.Element}节点。
     * 换句话说,一个接受孩子的节点。</p>。
     * 
     * <p>使用<code> nextSibling </code>指定之前应插入结果节点的子节点。
     * 如果<code> nextSibling </code>不是<code> node </code>的兄弟,那么会抛出<code> IllegalArgumentException </code>。
     * 如果<code> node </code>是<code> null </code>和<code> nextSibling </code>不是<code> null </code>,那么会抛出<code>
     *  IllegalArgumentException </code> 。
     * 如果<code> nextSibling </code>不是<code> node </code>的兄弟,那么会抛出<code> IllegalArgumentException </code>。
     * 如果<code> nextSibling </code>是<code> null </code>,则该行为与调用{@link #DOMResult(Node node,String systemId)}
     * 相同,即将结果节点追加为最后一个子节点并使用指定的系统ID。
     * 如果<code> nextSibling </code>不是<code> node </code>的兄弟,那么会抛出<code> IllegalArgumentException </code>。
     * </p>。
     * 
     * 
     * @param node The DOM node that will contain the result tree.
     * @param nextSibling The child node where the result nodes should be inserted before.
     * @param systemId The system identifier which may be used in association with this node.
     *
     * @throws IllegalArgumentException If <code>nextSibling</code> is not a
     *   sibling of <code>node</code> or
     *   <code>node</code> is <code>null</code> and <code>nextSibling</code>
     *   is not <code>null</code>.
     *
     * @since 1.5
     */
    public DOMResult(Node node, Node nextSibling, String systemId) {

        // does the corrent parent/child relationship exist?
        if (nextSibling != null) {
            // cannot be a sibling of a null node
            if (node == null) {
                throw new IllegalArgumentException("Cannot create a DOMResult when the nextSibling is contained by the \"null\" node.");
            }

            // nextSibling contained by node?
            if ((node.compareDocumentPosition(nextSibling)&Node.DOCUMENT_POSITION_CONTAINED_BY)==0) {
                throw new IllegalArgumentException("Cannot create a DOMResult when the nextSibling is not contained by the node.");
            }
        }

        setNode(node);
        setNextSibling(nextSibling);
        setSystemId(systemId);
    }

    /**
     * <p>Set the node that will contain the result DOM tree.<p>
     *
     * <p>In practice, the node should be
     * a {@link org.w3c.dom.Document} node,
     * a {@link org.w3c.dom.DocumentFragment} node, or
     * a {@link org.w3c.dom.Element} node.
     * In other words, a node that accepts children.</p>
     *
     * <p>An <code>IllegalStateException</code> is thrown if
     * <code>nextSibling</code> is not <code>null</code> and
     * <code>node</code> is not a parent of <code>nextSibling</code>.
     * An <code>IllegalStateException</code> is thrown if <code>node</code> is <code>null</code> and
     * <code>nextSibling</code> is not <code>null</code>.</p>
     *
     * <p>
     *  <p>设置将包含结果DOM树的节点。<p>
     * 
     *  <p>实际上,节点应该是{@link org.w3c.dom.Document}节点,{@link org.w3c.dom.DocumentFragment}节点或{@link org.w3c.dom.Element }
     * 节点。
     * 换句话说,一个接受孩子的节点。</p>。
     * 
     *  <p>如果<code> nextSibling </code>不是<code> null </code>且<code>节点</code>不是<code>的父代,则抛出<code> IllegalSta
     * teException </code> nextSibling </code>。
     * 如果<code> node </code>是<code> null </code>且<code> nextSibling </code>不是<code> null </code>,则抛出<code> I
     * llegalStateException </code> / p>。
     * 
     * 
     * @param node The node to which the transformation will be appended.
     *
     * @throws IllegalStateException If <code>nextSibling</code> is not
     *   <code>null</code> and
     *   <code>nextSibling</code> is not a child of <code>node</code> or
     *   <code>node</code> is <code>null</code> and
     *   <code>nextSibling</code> is not <code>null</code>.
     */
    public void setNode(Node node) {
        // does the corrent parent/child relationship exist?
        if (nextSibling != null) {
            // cannot be a sibling of a null node
            if (node == null) {
                throw new IllegalStateException("Cannot create a DOMResult when the nextSibling is contained by the \"null\" node.");
            }

            // nextSibling contained by node?
            if ((node.compareDocumentPosition(nextSibling)&Node.DOCUMENT_POSITION_CONTAINED_BY)==0) {
                throw new IllegalArgumentException("Cannot create a DOMResult when the nextSibling is not contained by the node.");
            }
        }

        this.node = node;
    }

    /**
     * <p>Get the node that will contain the result DOM tree.</p>
     *
     * <p>If no node was set via
     * {@link #DOMResult(Node node)},
     * {@link #DOMResult(Node node, String systeId)},
     * {@link #DOMResult(Node node, Node nextSibling)},
     * {@link #DOMResult(Node node, Node nextSibling, String systemId)} or
     * {@link #setNode(Node node)},
     * then the node will be set by the transformation, and may be obtained from this method once the transformation is complete.
     * Calling this method before the transformation will return <code>null</code>.</p>
     *
     * <p>
     *  <p>获取将包含结果DOM树的节点。</p>
     * 
     * <p>如果没有通过{@link #DOMResult(Node node)},{@link #DOMResult(Node node,String systeId)},{@link #DOMResult(Node node,Node nextSibling)}
     * ,{@链接#DOMResult(节点节点,节点nextSibling,String systemId)}或{@link #setNode(节点节点)},则该节点将通过变换设置,并且一旦变换完成,可以从该
     * 方法获得。
     * 在转换之前调用此方法将返回<code> null </code>。</p>。
     * 
     * 
     * @return The node to which the transformation will be appended.
     */
    public Node getNode() {
        return node;
    }

    /**
     * <p>Set the child node before which the result nodes will be inserted.</p>
     *
     * <p>Use <code>nextSibling</code> to specify the child node
     * before which the result nodes should be inserted.
     * If <code>nextSibling</code> is not a descendant of <code>node</code>,
     * then an <code>IllegalArgumentException</code> is thrown.
     * If <code>node</code> is <code>null</code> and <code>nextSibling</code> is not <code>null</code>,
     * then an <code>IllegalStateException</code> is thrown.
     * If <code>nextSibling</code> is <code>null</code>,
     * then the behavior is the same as calling {@link #DOMResult(Node node)},
     * i.e. append the result nodes as the last child of the specified <code>node</code>.</p>
     *
     * <p>
     *  <p>设置将在其中插入结果节点的子节点。</p>
     * 
     *  <p>使用<code> nextSibling </code>指定应在其上插入结果节点的子节点。
     * 如果<code> nextSibling </code>不是<code> node </code>的后代,那么会抛出<code> IllegalArgumentException </code>。
     * 如果<code> node </code>是<code> null </code>且<code> nextSibling </code>不是<code> null </code>,则会抛出<code> 
     * IllegalStateException </code> 。
     * 如果<code> nextSibling </code>不是<code> node </code>的后代,那么会抛出<code> IllegalArgumentException </code>。
     * 如果<code> nextSibling </code>是<code> null </code>,则该行为与调用{@link #DOMResult(Node node)}相同,即将结果节点追加为指定的最
     * 后一个子节点<code> node </code>。
     * 如果<code> nextSibling </code>不是<code> node </code>的后代,那么会抛出<code> IllegalArgumentException </code>。
     * </p>。
     * 
     * 
     * @param nextSibling The child node before which the result nodes will be inserted.
     *
     * @throws IllegalArgumentException If <code>nextSibling</code> is not a
     *   descendant of <code>node</code>.
     * @throws IllegalStateException If <code>node</code> is <code>null</code>
     *   and <code>nextSibling</code> is not <code>null</code>.
     *
     * @since 1.5
     */
    public void setNextSibling(Node nextSibling) {

        // does the corrent parent/child relationship exist?
        if (nextSibling != null) {
            // cannot be a sibling of a null node
            if (node == null) {
                throw new IllegalStateException("Cannot create a DOMResult when the nextSibling is contained by the \"null\" node.");
            }

            // nextSibling contained by node?
            if ((node.compareDocumentPosition(nextSibling)&Node.DOCUMENT_POSITION_CONTAINED_BY)==0) {
                throw new IllegalArgumentException("Cannot create a DOMResult when the nextSibling is not contained by the node.");
            }
        }

        this.nextSibling = nextSibling;
    }

    /**
     * <p>Get the child node before which the result nodes will be inserted.</p>
     *
     * <p>If no node was set via
     * {@link #DOMResult(Node node, Node nextSibling)},
     * {@link #DOMResult(Node node, Node nextSibling, String systemId)} or
     * {@link #setNextSibling(Node nextSibling)},
     * then <code>null</code> will be returned.</p>
     *
     * <p>
     *  <p>获取要插入结果节点的子节点。</p>
     * 
     *  <p>如果没有通过{@link #DOMResult(Node node,Node nextSibling)},{@link #DOMResult(Node node,Node nextSibling,String systemId)}
     * 或{@link #setNextSibling(Node nextSibling)}设置节点,那么将返回<code> null </code>。
     * </p>。
     * 
     * 
     * @return The child node before which the result nodes will be inserted.
     *
     * @since 1.5
     */
    public Node getNextSibling() {
        return nextSibling;
    }

    /**
     * <p>Set the systemId that may be used in association with the node.</p>
     *
     * <p>
     * <p>设置可与节点关联使用的systemId。</p>
     * 
     * 
     * @param systemId The system identifier as a URI string.
     */
    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    /**
     * <p>Get the System Identifier.</p>
     *
     * <p>If no System ID was set via
     * {@link #DOMResult(Node node, String systemId)},
     * {@link #DOMResult(Node node, Node nextSibling, String systemId)} or
     * {@link #setSystemId(String systemId)},
     * then <code>null</code> will be returned.</p>
     *
     * <p>
     *  <p>获取系统标识符</p>
     * 
     *  <p>如果没有通过{@link #DOMResult(Node node,String systemId)},{@link #DOMResult(Node node,Node nextSibling,String systemId)}
     * 或{@link #setSystemId(String systemId) },那么将返回<code> null </code>。
     * </p>。
     * 
     * 
     * @return The system identifier.
     */
    public String getSystemId() {
        return systemId;
    }

    //////////////////////////////////////////////////////////////////////
    // Internal state.
    //////////////////////////////////////////////////////////////////////

    /**
     * <p>The node to which the transformation will be appended.</p>
     * <p>
     *  <p>要附加转换的节点。</p>
     * 
     */
    private Node node = null;

    /**
     * <p>The child node before which the result nodes will be inserted.</p>
     *
     * <p>
     *  <p>在其之前将插入结果节点的子节点。</p>
     * 
     * 
     * @since 1.5
     */
    private Node nextSibling = null;

    /**
     * <p>The System ID that may be used in association with the node.</p>
     * <p>
     *  <p>可与节点相关联使用的系统ID。</p>
     */
    private String systemId = null;
}
