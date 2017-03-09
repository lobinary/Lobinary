/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2004, 2012, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.soap;

/**
 * A representation of a node (element) in an XML document.
 * This interface extnends the standard DOM Node interface with methods for
 * getting and setting the value of a node, for
 * getting and setting the parent of a node, and for removing a node.
 * <p>
 *  XML文档中节点(元素)的表示形式。此接口将标准DOM节点接口与用于获取和设置节点的值,用于获取和设置节点的父级以及用于删除节点的方法延伸。
 * 
 */
public interface Node extends org.w3c.dom.Node {
    /**
     * Returns the value of this node if this is a <code>Text</code> node or the
     * value of the immediate child of this node otherwise.
     * If there is an immediate child of this <code>Node</code> that it is a
     * <code>Text</code> node then it's value will be returned. If there is
     * more than one <code>Text</code> node then the value of the first
     * <code>Text</code> Node will be returned.
     * Otherwise <code>null</code> is returned.
     *
     * <p>
     *  如果此节点是<code> Text </code>节点,则返回此节点的值,否则返回此节点的直接子节点的值。
     * 如果这个<code> Node </code>的一个直接子节点是一个<code> Text </code>节点,那么它的值将被返回。
     * 如果有多个<code> Text </code>节点,则将返回第一个<code> Text </code>节点的值。否则返回<code> null </code>。
     * 
     * 
     * @return a <code>String</code> with the text of this node if this is a
     *          <code>Text</code> node or the text contained by the first
     *          immediate child of this <code>Node</code> object that is a
     *          <code>Text</code> object if such a child exists;
     *          <code>null</code> otherwise.
     */
    public String getValue();

    /**
     * If this is a Text node then this method will set its value,
     * otherwise it sets the value of  the immediate (Text) child of this node.
     * The value of the immediate child of this node can be set only if, there is
     * one child node and that node is a <code>Text</code> node, or if
     * there are no children in which case a child <code>Text</code> node will be
     * created.
     *
     * <p>
     *  如果这是一个Text节点,那么此方法将设置其值,否则设置此节点的immediate(Text)子节点的值。
     * 只有当有一个子节点并且该节点是一个<code> Text </code>节点时,或者如果没有子节点,子节点<code> Text </code>节点将被创建。
     * 
     * 
     * @exception IllegalStateException if the node is not a <code>Text</code>
     *              node and either has more than one child node or has a child
     *              node that is not a <code>Text</code> node.
     *
     * @since SAAJ 1.2
     */
    public void setValue(String value);

    /**
     * Sets the parent of this <code>Node</code> object to the given
     * <code>SOAPElement</code> object.
     *
     * <p>
     *  将此<code> Node </code>对象的父代设置为给定的<code> SOAPElement </code>对象。
     * 
     * 
     * @param parent the <code>SOAPElement</code> object to be set as
     *       the parent of this <code>Node</code> object
     *
     * @exception SOAPException if there is a problem in setting the
     *                          parent to the given element
     * @see #getParentElement
     */
    public void setParentElement(SOAPElement parent) throws SOAPException;

    /**
     * Returns the parent element of this <code>Node</code> object.
     * This method can throw an <code>UnsupportedOperationException</code>
     * if the tree is not kept in memory.
     *
     * <p>
     *  返回此<code> Node </code>对象的父元素。如果树不保存在内存中,此方法可以抛出<code> UnsupportedOperationException </code>。
     * 
     * 
     * @return the <code>SOAPElement</code> object that is the parent of
     *         this <code>Node</code> object or <code>null</code> if this
     *         <code>Node</code> object is root
     *
     * @exception UnsupportedOperationException if the whole tree is not
     *            kept in memory
     * @see #setParentElement
     */
    public SOAPElement getParentElement();

    /**
     * Removes this <code>Node</code> object from the tree.
     * <p>
     *  从树中删除此<code> Node </code>对象。
     * 
     */
    public void detachNode();

    /**
     * Notifies the implementation that this <code>Node</code>
     * object is no longer being used by the application and that the
     * implementation is free to reuse this object for nodes that may
     * be created later.
     * <P>
     * Calling the method <code>recycleNode</code> implies that the method
     * <code>detachNode</code> has been called previously.
     * <p>
     * 通知实现这个<code> Node </code>对象不再被应用程序使用,并且实现可以为稍后可能创建的节点重用此对象。
     * <P>
     */
    public void recycleNode();

}
