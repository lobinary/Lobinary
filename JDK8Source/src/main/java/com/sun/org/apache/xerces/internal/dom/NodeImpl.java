/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
 /*
 * Copyright 1999-2002,2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  版权所有1999-2002,2004 Apache软件基金会
 * 
 *  根据Apache许可证第20版("许可证")授权;您不得使用此文件,除非符合许可证您可以在获取许可证的副本
 * 
 *  http：// wwwapacheorg / licenses / LICENSE-20
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件将按"原样"基础分发,无任何明示或暗示的保证或条件。请参阅许可证管理权限和限制许可证
 * 
 */

package com.sun.org.apache.xerces.internal.dom;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Hashtable;

import org.w3c.dom.UserDataHandler;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;

/**
 * NodeImpl provides the basic structure of a DOM tree. It is never used
 * directly, but instead is subclassed to add type and data
 * information, and additional methods, appropriate to each node of
 * the tree. Only its subclasses should be instantiated -- and those,
 * with the exception of Document itself, only through a specific
 * Document's factory methods.
 * <P>
 * The Node interface provides shared behaviors such as siblings and
 * children, both for consistancy and so that the most common tree
 * operations may be performed without constantly having to downcast
 * to specific node types. When there is no obvious mapping for one of
 * these queries, it will respond with null.
 * Note that the default behavior is that children are forbidden. To
 * permit them, the subclass ParentNode overrides several methods.
 * <P>
 * NodeImpl also implements NodeList, so it can return itself in
 * response to the getChildNodes() query. This eliminiates the need
 * for a separate ChildNodeList object. Note that this is an
 * IMPLEMENTATION DETAIL; applications should _never_ assume that
 * this identity exists.
 * <P>
 * All nodes in a single document must originate
 * in that document. (Note that this is much tighter than "must be
 * same implementation") Nodes are all aware of their ownerDocument,
 * and attempts to mismatch will throw WRONG_DOCUMENT_ERR.
 * <P>
 * However, to save memory not all nodes always have a direct reference
 * to their ownerDocument. When a node is owned by another node it relies
 * on its owner to store its ownerDocument. Parent nodes always store it
 * though, so there is never more than one level of indirection.
 * And when a node doesn't have an owner, ownerNode refers to its
 * ownerDocument.
 * <p>
 * This class doesn't directly support mutation events, however, it still
 * implements the EventTarget interface and forward all related calls to the
 * document so that the document class do so.
 *
 * @xerces.internal
 *
 * <p>
 * NodeImpl提供了一个DOM树的基本结构它从不直接使用,而是被子类化以添加类型和数据信息,以及适用于树的每个节点的附加方法只有它的子类应该被实例化 - 异常Document本身,只能通过具体的Doc
 * ument的工厂方法。
 * <P>
 *  Node接口提供共享行为,例如兄弟和孩子,这两者都是为了一致性,并且可以执行最常见的树操作而不必不断地向下转换到特定节点类型。
 * 当对于这些查询之一没有明显的映射时,它将响应with null注意,默认行为是禁止子代。为了允许它们,子类ParentNode覆盖几个方法。
 * <P>
 * NodeImpl也实现了NodeList,所以它可以响应getChildNodes()查询返回自己。这消除了对一个单独的ChildNodeList对象的需要。
 * 注意这是一个IMPLEMENTATION DETAIL;应用程序应该_never_假定此标识存在。
 * <P>
 *  单个文档中的所有节点都必须源自该文档(请注意,这比"必须是相同的实现"要严格得多)节点都知道它们的ownerDocument,并且尝试不匹配会抛出WRONG_DOCUMENT_ERR
 * <P>
 * 但是,为了节省内存,并非​​所有节点都直接引用它们的ownerDocument当一个节点由另一个节点拥有时,它依赖其所有者来存储它的ownerDocument父节点总是存储它,所以永远不会有多个间接级别
 * 当节点没有所有者时,ownerNode引用其ownerDocument。
 * <p>
 *  这个类不直接支持变异事件,但是它仍然实现了EventTarget接口并将所有相关的调用转发给文档,以便文档类这样做
 * 
 *  @xercesinternal
 * 
 * 
 * @author Arnaud  Le Hors, IBM
 * @author Joe Kesselman, IBM
 * @since  PR-DOM-Level-1-19980818.
 */
public abstract class NodeImpl
    implements Node, NodeList, EventTarget, Cloneable, Serializable{

    //
    // Constants
    //


    // TreePosition Constants.
    // Taken from DOM L3 Node interface.
    /**
     * The node precedes the reference node.
     * <p>
     *  节点在参考节点之前
     * 
     */
    public static final short TREE_POSITION_PRECEDING   = 0x01;
    /**
     * The node follows the reference node.
     * <p>
     *  节点跟随参考节点
     * 
     */
    public static final short TREE_POSITION_FOLLOWING   = 0x02;
    /**
     * The node is an ancestor of the reference node.
     * <p>
     *  节点是参考节点的祖先
     * 
     */
    public static final short TREE_POSITION_ANCESTOR    = 0x04;
    /**
     * The node is a descendant of the reference node.
     * <p>
     *  节点是参考节点的后代
     * 
     */
    public static final short TREE_POSITION_DESCENDANT  = 0x08;
    /**
     * The two nodes have an equivalent position. This is the case of two
     * attributes that have the same <code>ownerElement</code>, and two
     * nodes that are the same.
     * <p>
     * 两个节点具有等效位置这是具有相同<code> ownerElement </code>的两个属性的情况,以及两个相同的节点
     * 
     */
    public static final short TREE_POSITION_EQUIVALENT  = 0x10;
    /**
     * The two nodes are the same. Two nodes that are the same have an
     * equivalent position, though the reverse may not be true.
     * <p>
     *  两个节点是相同的两个相同的节点具有等效位置,尽管相反可能不是真的
     * 
     */
    public static final short TREE_POSITION_SAME_NODE   = 0x20;
    /**
     * The two nodes are disconnected, they do not have any common ancestor.
     * This is the case of two nodes that are not in the same document.
     * <p>
     *  两个节点断开连接,它们没有任何共同的祖先。这是不在同一文档中的两个节点的情况
     * 
     */
    public static final short TREE_POSITION_DISCONNECTED = 0x00;


    // DocumentPosition
    public static final short DOCUMENT_POSITION_DISCONNECTED = 0x01;
    public static final short DOCUMENT_POSITION_PRECEDING = 0x02;
    public static final short DOCUMENT_POSITION_FOLLOWING = 0x04;
    public static final short DOCUMENT_POSITION_CONTAINS = 0x08;
    public static final short DOCUMENT_POSITION_IS_CONTAINED = 0x10;
    public static final short DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC = 0x20;

    /** Serialization version. */
    static final long serialVersionUID = -6316591992167219696L;

    // public

    /** Element definition node type. */
    public static final short ELEMENT_DEFINITION_NODE = 21;

    //
    // Data
    //

    // links

    protected NodeImpl ownerNode; // typically the parent but not always!

    // data

    protected short flags;

    protected final static short READONLY     = 0x1<<0;
    protected final static short SYNCDATA     = 0x1<<1;
    protected final static short SYNCCHILDREN = 0x1<<2;
    protected final static short OWNED        = 0x1<<3;
    protected final static short FIRSTCHILD   = 0x1<<4;
    protected final static short SPECIFIED    = 0x1<<5;
    protected final static short IGNORABLEWS  = 0x1<<6;
    protected final static short HASSTRING    = 0x1<<7;
    protected final static short NORMALIZED = 0x1<<8;
    protected final static short ID           = 0x1<<9;

    //
    // Constructors
    //

    /**
     * No public constructor; only subclasses of Node should be
     * instantiated, and those normally via a Document's factory methods
     * <p>
     * Every Node knows what Document it belongs to.
     * <p>
     *  没有公共构造函数;只有Node的子类应该被实例化,并且那些通常通过Document的工厂方法
     * <p>
     *  每个节点知道它属于什么文档
     * 
     */
    protected NodeImpl(CoreDocumentImpl ownerDocument) {
        // as long as we do not have any owner, ownerNode is our ownerDocument
        ownerNode = ownerDocument;
    } // <init>(CoreDocumentImpl)

    /** Constructor for serialization. */
    public NodeImpl() {}

    //
    // Node methods
    //

    /**
     * A short integer indicating what type of node this is. The named
     * constants for this value are defined in the org.w3c.dom.Node interface.
     * <p>
     *  指示此节点类型的短整数此值的命名常量在orgw3cdomNode接口中定义
     * 
     */
    public abstract short getNodeType();

    /**
     * the name of this node.
     * <p>
     *  此节点的名称
     * 
     */
    public abstract String getNodeName();

    /**
     * Returns the node value.
     * <p>
     *  返回节点值
     * 
     * 
     * @throws DOMException(DOMSTRING_SIZE_ERR)
     */
    public String getNodeValue()
        throws DOMException {
        return null;            // overridden in some subclasses
    }

    /**
     * Sets the node value.
     * <p>
     *  设置节点值
     * 
     * 
     * @throws DOMException(NO_MODIFICATION_ALLOWED_ERR)
     */
    public void setNodeValue(String x)
        throws DOMException {
        // Default behavior is to do nothing, overridden in some subclasses
    }

    /**
     * Adds a child node to the end of the list of children for this node.
     * Convenience shorthand for insertBefore(newChild,null).
     * <p>
     * 将子节点添加到此节点的子节点列表的末尾insertBefore的方便速记(newChild,null)
     * 
     * 
     * @see #insertBefore(Node, Node)
     * <P>
     * By default we do not accept any children, ParentNode overrides this.
     * @see ParentNode
     *
     * @return newChild, in its new state (relocated, or emptied in the case of
     * DocumentNode.)
     *
     * @throws DOMException(HIERARCHY_REQUEST_ERR) if newChild is of a
     * type that shouldn't be a child of this node.
     *
     * @throws DOMException(WRONG_DOCUMENT_ERR) if newChild has a
     * different owner document than we do.
     *
     * @throws DOMException(NO_MODIFICATION_ALLOWED_ERR) if this node is
     * read-only.
     */
    public Node appendChild(Node newChild) throws DOMException {
        return insertBefore(newChild, null);
    }

    /**
     * Returns a duplicate of a given node. You can consider this a
     * generic "copy constructor" for nodes. The newly returned object should
     * be completely independent of the source object's subtree, so changes
     * in one after the clone has been made will not affect the other.
     * <P>
     * Note: since we never have any children deep is meaningless here,
     * ParentNode overrides this behavior.
     * <p>
     *  返回给定节点的副本您可以将此视为节点的通用"复制构造函数"新返回的对象应完全独立于源对象的子树,因此在克隆后进行的更改不会影响其他对象
     * <P>
     *  注意：因为我们从来没有任何孩子deep在这里没有意义,ParentNode重写这个行为
     * 
     * 
     * @see ParentNode
     *
     * <p>
     * Example: Cloning a Text node will copy both the node and the text it
     * contains.
     * <p>
     * Example: Cloning something that has children -- Element or Attr, for
     * example -- will _not_ clone those children unless a "deep clone"
     * has been requested. A shallow clone of an Attr node will yield an
     * empty Attr of the same name.
     * <p>
     * NOTE: Clones will always be read/write, even if the node being cloned
     * is read-only, to permit applications using only the DOM API to obtain
     * editable copies of locked portions of the tree.
     */
    public Node cloneNode(boolean deep) {

        if (needsSyncData()) {
            synchronizeData();
        }

        NodeImpl newnode;
        try {
            newnode = (NodeImpl)clone();
        }
        catch (CloneNotSupportedException e) {
            // if we get here we have an error in our program we may as well
            // be vocal about it, so that people can take appropriate action.
            throw new RuntimeException("**Internal Error**" + e);
        }

        // Need to break the association w/ original kids
        newnode.ownerNode      = ownerDocument();
        newnode.isOwned(false);

        // By default we make all clones readwrite,
        // this is overriden in readonly subclasses
        newnode.isReadOnly(false);

        ownerDocument().callUserDataHandlers(this, newnode,
                                             UserDataHandler.NODE_CLONED);

        return newnode;

    } // cloneNode(boolean):Node

    /**
     * Find the Document that this Node belongs to (the document in
     * whose context the Node was created). The Node may or may not
     * currently be part of that Document's actual contents.
     * <p>
     *  查找此节点所属的文档(在其上下文中创建节点的文档)节点可能或可能不当前是该文档的实际内容的一部分
     * 
     */
    public Document getOwnerDocument() {
        // if we have an owner simply forward the request
        // otherwise ownerNode is our ownerDocument
        if (isOwned()) {
            return ownerNode.ownerDocument();
        } else {
            return (Document) ownerNode;
        }
    }

    /**
     * same as above but returns internal type and this one is not overridden
     * by CoreDocumentImpl to return null
     * <p>
     *  与上述相同,但返回内部类型,并且这一个不被CoreDocumentImpl覆盖以返回null
     * 
     */
    CoreDocumentImpl ownerDocument() {
        // if we have an owner simply forward the request
        // otherwise ownerNode is our ownerDocument
        if (isOwned()) {
            return ownerNode.ownerDocument();
        } else {
            return (CoreDocumentImpl) ownerNode;
        }
    }

    /**
     * NON-DOM
     * set the ownerDocument of this node
     * <p>
     * NON-DOM设置此节点的ownerDocument
     * 
     */
    void setOwnerDocument(CoreDocumentImpl doc) {
        if (needsSyncData()) {
            synchronizeData();
        }
        // if we have an owner we rely on it to have it right
        // otherwise ownerNode is our ownerDocument
        if (!isOwned()) {
            ownerNode = doc;
        }
    }

    /**
     * Returns the node number
     * <p>
     *  返回节点编号
     * 
     */
    protected int getNodeNumber() {
        int nodeNumber;
        CoreDocumentImpl cd = (CoreDocumentImpl)(this.getOwnerDocument());
        nodeNumber = cd.getNodeNumber(this);
        return nodeNumber;
    }

    /**
     * Obtain the DOM-tree parent of this node, or null if it is not
     * currently active in the DOM tree (perhaps because it has just been
     * created or removed). Note that Document, DocumentFragment, and
     * Attribute will never have parents.
     * <p>
     *  获取此节点的DOM树父,如果它在DOM树中不是活动的(可能是因为它刚刚被创建或删除),则为null注意,Document,DocumentFragment和Attribute永远不会有父
     * 
     */
    public Node getParentNode() {
        return null;            // overriden by ChildNode
    }

    /*
     * same as above but returns internal type
     * <p>
     *  同上,但返回内部类型
     * 
     */
    NodeImpl parentNode() {
        return null;
    }

    /** The next child of this node's parent, or null if none */
    public Node getNextSibling() {
        return null;            // default behavior, overriden in ChildNode
    }

    /** The previous child of this node's parent, or null if none */
    public Node getPreviousSibling() {
        return null;            // default behavior, overriden in ChildNode
    }

    ChildNode previousSibling() {
        return null;            // default behavior, overriden in ChildNode
    }

    /**
     * Return the collection of attributes associated with this node,
     * or null if none. At this writing, Element is the only type of node
     * which will ever have attributes.
     *
     * <p>
     *  返回与此节点相关联的属性集合,如果没有则返回null在此写入时,Element是唯一具有属性的节点类型
     * 
     * 
     * @see ElementImpl
     */
    public NamedNodeMap getAttributes() {
        return null; // overridden in ElementImpl
    }

    /**
     *  Returns whether this node (if it is an element) has any attributes.
     * <p>
     *  返回此节点(如果它是一个元素)是否有任何属性
     * 
     * 
     * @return <code>true</code> if this node has any attributes,
     *   <code>false</code> otherwise.
     * @since DOM Level 2
     * @see ElementImpl
     */
    public boolean hasAttributes() {
        return false;           // overridden in ElementImpl
    }

    /**
     * Test whether this node has any children. Convenience shorthand
     * for (Node.getFirstChild()!=null)
     * <P>
     * By default we do not have any children, ParentNode overrides this.
     * <p>
     *  测试这个节点是否有任何子节点方便快捷(NodegetFirstChild()！= null)
     * <P>
     *  默认情况下,我们没有任何孩子,ParentNode覆盖这个
     * 
     * 
     * @see ParentNode
     */
    public boolean hasChildNodes() {
        return false;
    }

    /**
     * Obtain a NodeList enumerating all children of this node. If there
     * are none, an (initially) empty NodeList is returned.
     * <p>
     * NodeLists are "live"; as children are added/removed the NodeList
     * will immediately reflect those changes. Also, the NodeList refers
     * to the actual nodes, so changes to those nodes made via the DOM tree
     * will be reflected in the NodeList and vice versa.
     * <p>
     * In this implementation, Nodes implement the NodeList interface and
     * provide their own getChildNodes() support. Other DOMs may solve this
     * differently.
     * <p>
     * 获取枚举此节点的所有子节点的NodeList如果没有,则返回一个(最初)空的NodeList
     * <p>
     *  NodeList是"live";当添加/删除子节点时,NodeList将立即反映这些更改。
     * 此外,NodeList引用实际节点,因此通过DOM树进行的那些节点的更改将反映在NodeList中,反之亦然。
     * <p>
     *  在这个实现中,Nodes实现NodeList接口并提供自己的getChildNodes()支持其他DOM可以不同的解决方案
     * 
     */
    public NodeList getChildNodes() {
        return this;
    }

    /** The first child of this Node, or null if none.
     * <P>
     * By default we do not have any children, ParentNode overrides this.
     * <p>
     * <P>
     *  默认情况下,我们没有任何孩子,ParentNode覆盖这个
     * 
     * 
     * @see ParentNode
     */
    public Node getFirstChild() {
        return null;
    }

    /** The first child of this Node, or null if none.
     * <P>
     * By default we do not have any children, ParentNode overrides this.
     * <p>
     * <P>
     *  默认情况下,我们没有任何孩子,ParentNode覆盖这个
     * 
     * 
     * @see ParentNode
     */
    public Node getLastChild() {
        return null;
    }

    /**
     * Move one or more node(s) to our list of children. Note that this
     * implicitly removes them from their previous parent.
     * <P>
     * By default we do not accept any children, ParentNode overrides this.
     * <p>
     *  将一个或多个节点移动到我们的孩子列表请注意,这会隐式地从他们以前的父级中删除它们
     * <P>
     * 默认情况下,我们不接受任何孩子,ParentNode覆盖这个
     * 
     * 
     * @see ParentNode
     *
     * @param newChild The Node to be moved to our subtree. As a
     * convenience feature, inserting a DocumentNode will instead insert
     * all its children.
     *
     * @param refChild Current child which newChild should be placed
     * immediately before. If refChild is null, the insertion occurs
     * after all existing Nodes, like appendChild().
     *
     * @return newChild, in its new state (relocated, or emptied in the case of
     * DocumentNode.)
     *
     * @throws DOMException(HIERARCHY_REQUEST_ERR) if newChild is of a
     * type that shouldn't be a child of this node, or if newChild is an
     * ancestor of this node.
     *
     * @throws DOMException(WRONG_DOCUMENT_ERR) if newChild has a
     * different owner document than we do.
     *
     * @throws DOMException(NOT_FOUND_ERR) if refChild is not a child of
     * this node.
     *
     * @throws DOMException(NO_MODIFICATION_ALLOWED_ERR) if this node is
     * read-only.
     */
    public Node insertBefore(Node newChild, Node refChild)
        throws DOMException {
        throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR,
              DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN,
                 "HIERARCHY_REQUEST_ERR", null));
    }

    /**
     * Remove a child from this Node. The removed child's subtree
     * remains intact so it may be re-inserted elsewhere.
     * <P>
     * By default we do not have any children, ParentNode overrides this.
     * <p>
     *  从此节点删除子项已删除的子项的子树保持不变,因此可以重新插入其他位置
     * <P>
     *  默认情况下,我们没有任何孩子,ParentNode覆盖这个
     * 
     * 
     * @see ParentNode
     *
     * @return oldChild, in its new state (removed).
     *
     * @throws DOMException(NOT_FOUND_ERR) if oldChild is not a child of
     * this node.
     *
     * @throws DOMException(NO_MODIFICATION_ALLOWED_ERR) if this node is
     * read-only.
     */
    public Node removeChild(Node oldChild)
                throws DOMException {
        throw new DOMException(DOMException.NOT_FOUND_ERR,
              DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN,
                 "NOT_FOUND_ERR", null));
    }

    /**
     * Make newChild occupy the location that oldChild used to
     * have. Note that newChild will first be removed from its previous
     * parent, if any. Equivalent to inserting newChild before oldChild,
     * then removing oldChild.
     * <P>
     * By default we do not have any children, ParentNode overrides this.
     * <p>
     *  使newChild占据oldChild曾经存在的位置请注意,newChild将首先从其上一个父对象中删除(如果有的话)相当于在oldChild之前插入newChild,然后删除oldChild
     * <P>
     *  默认情况下,我们没有任何孩子,ParentNode覆盖这个
     * 
     * 
     * @see ParentNode
     *
     * @return oldChild, in its new state (removed).
     *
     * @throws DOMException(HIERARCHY_REQUEST_ERR) if newChild is of a
     * type that shouldn't be a child of this node, or if newChild is
     * one of our ancestors.
     *
     * @throws DOMException(WRONG_DOCUMENT_ERR) if newChild has a
     * different owner document than we do.
     *
     * @throws DOMException(NOT_FOUND_ERR) if oldChild is not a child of
     * this node.
     *
     * @throws DOMException(NO_MODIFICATION_ALLOWED_ERR) if this node is
     * read-only.
     */
    public Node replaceChild(Node newChild, Node oldChild)
        throws DOMException {
        throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR,
              DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN,
                 "HIERARCHY_REQUEST_ERR", null));
    }

    //
    // NodeList methods
    //

    /**
     * NodeList method: Count the immediate children of this node
     * <P>
     * By default we do not have any children, ParentNode overrides this.
     * <p>
     *  NodeList方法：计算此节点的直接子节点
     * <P>
     *  默认情况下,我们没有任何孩子,ParentNode覆盖这个
     * 
     * 
     * @see ParentNode
     *
     * @return int
     */
    public int getLength() {
        return 0;
    }

    /**
     * NodeList method: Return the Nth immediate child of this node, or
     * null if the index is out of bounds.
     * <P>
     * By default we do not have any children, ParentNode overrides this.
     * <p>
     *  NodeList方法：返回此节点的第N个直接子节点,如果索引超出边界则返回null
     * <P>
     * 默认情况下,我们没有任何孩子,ParentNode覆盖这个
     * 
     * 
     * @see ParentNode
     *
     * @return org.w3c.dom.Node
     * @param Index int
     */
    public Node item(int index) {
        return null;
    }

    //
    // DOM2: methods, getters, setters
    //

    /**
     * Puts all <code>Text</code> nodes in the full depth of the sub-tree
     * underneath this <code>Node</code>, including attribute nodes, into a
     * "normal" form where only markup (e.g., tags, comments, processing
     * instructions, CDATA sections, and entity references) separates
     * <code>Text</code> nodes, i.e., there are no adjacent <code>Text</code>
     * nodes.  This can be used to ensure that the DOM view of a document is
     * the same as if it were saved and re-loaded, and is useful when
     * operations (such as XPointer lookups) that depend on a particular
     * document tree structure are to be used.In cases where the document
     * contains <code>CDATASections</code>, the normalize operation alone may
     * not be sufficient, since XPointers do not differentiate between
     * <code>Text</code> nodes and <code>CDATASection</code> nodes.
     * <p>
     * Note that this implementation simply calls normalize() on this Node's
     * children. It is up to implementors or Node to override normalize()
     * to take action.
     * <p>
     * 将所有<code>节点</code>下的子树的所有<code> Text </code>节点包括在"正常"形式中,其中只有标记,处理指令,CDATA段和实体引用)分离<code> Text </code>
     * 节点,即没有相邻的<code> Text </code>节点。
     * 这可以用于确保文档的DOM视图与保存和重新加载时相同,并且在要使用依赖于特定文档树结构的操作(例如XPointer查找)时非常有用在文档包含<code> CDATASections </code>的情况
     * 下,归一化操作可能不够,因为XPointer不区分<code> Text </code>节点和<code> CDATASection </code>节点。
     * <p>
     * 注意,这个实现只是在这个Node的孩子上调用normalize()。这是由实现者或Node来覆盖normalize()来执行
     * 
     */
    public void normalize() {
        /* by default we do not have any children,
        /* <p>
        /* 
           ParentNode overrides this behavior */
    }

    /**
     * Introduced in DOM Level 2. <p>
     * Tests whether the DOM implementation implements a specific feature and
     * that feature is supported by this node.
     * <p>
     *  在DOM级别2中引入<p>测试DOM实现是否实现特定功能,并且此节点支持该功能
     * 
     * 
     * @param feature The package name of the feature to test. This is the same
     * name as what can be passed to the method hasFeature on
     * DOMImplementation.
     * @param version This is the version number of the package name to
     * test. In Level 2, version 1, this is the string "2.0". If the version is
     * not specified, supporting any version of the feature will cause the
     * method to return true.
     * @return boolean Returns true if this node defines a subtree within which
     * the specified feature is supported, false otherwise.
     * @since WD-DOM-Level-2-19990923
     */
    public boolean isSupported(String feature, String version)
    {
        return ownerDocument().getImplementation().hasFeature(feature,
                                                              version);
    }

    /**
     * Introduced in DOM Level 2. <p>
     *
     * The namespace URI of this node, or null if it is unspecified. When this
     * node is of any type other than ELEMENT_NODE and ATTRIBUTE_NODE, this is
     * always null and setting it has no effect. <p>
     *
     * This is not a computed value that is the result of a namespace lookup
     * based on an examination of the namespace declarations in scope. It is
     * merely the namespace URI given at creation time.<p>
     *
     * For nodes created with a DOM Level 1 method, such as createElement
     * from the Document interface, this is null.
     * <p>
     *  在DOM级别2中引入<p>
     * 
     *  此节点的命名空间URI,如果未指定则为null如果此节点是除ELEMENT_NODE和ATTRIBUTE_NODE之外的任何类型,则始终为空,并且将其设置为无效<p>
     * 
     *  这不是一个计算值,它是基于对范围中的命名空间声明的检查的命名空间查找的结果它只是在创建时提供的命名空间URI <p>
     * 
     * 对于使用DOM Level 1方法创建的节点(如Document接口中的createElement),此值为null
     * 
     * 
     * @since WD-DOM-Level-2-19990923
     * @see AttrNSImpl
     * @see ElementNSImpl
     */
    public String getNamespaceURI()
    {
        return null;
    }

    /**
     * Introduced in DOM Level 2. <p>
     *
     * The namespace prefix of this node, or null if it is unspecified. When
     * this node is of any type other than ELEMENT_NODE and ATTRIBUTE_NODE this
     * is always null and setting it has no effect.<p>
     *
     * For nodes created with a DOM Level 1 method, such as createElement
     * from the Document interface, this is null. <p>
     *
     * <p>
     *  在DOM级别2中引入<p>
     * 
     *  此节点的命名空间前缀,如果未指定则为null如果此节点是除ELEMENT_NODE和ATTRIBUTE_NODE之外的任何类型,则此节点始终为空,并且将其设置为无效<p>
     * 
     *  对于使用DOM Level 1方法创建的节点(例如Document接口中的createElement),则为null <p>
     * 
     * 
     * @since WD-DOM-Level-2-19990923
     * @see AttrNSImpl
     * @see ElementNSImpl
     */
    public String getPrefix()
    {
        return null;
    }

    /**
     *  Introduced in DOM Level 2. <p>
     *
     *  The namespace prefix of this node, or null if it is unspecified. When
     *  this node is of any type other than ELEMENT_NODE and ATTRIBUTE_NODE
     *  this is always null and setting it has no effect.<p>
     *
     *  For nodes created with a DOM Level 1 method, such as createElement from
     *  the Document interface, this is null.<p>
     *
     *  Note that setting this attribute changes the nodeName attribute, which
     *  holds the qualified name, as well as the tagName and name attributes of
     *  the Element and Attr interfaces, when applicable.<p>
     *
     * <p>
     *  在DOM级别2中引入<p>
     * 
     *  此节点的命名空间前缀,如果未指定则为null如果此节点是除ELEMENT_NODE和ATTRIBUTE_NODE之外的任何类型,则此节点始终为空,并且将其设置为无效<p>
     * 
     *  对于使用DOM Level 1方法创建的节点(例如Document接口中的createElement),则为null <p>
     * 
     * 请注意,设置此属性会更改nodeName属性,该属性包含限定名称,以及Element和Attr接口的tagName和name属性(如果适用)<p>
     * 
     * 
     * @throws INVALID_CHARACTER_ERR Raised if the specified
     *  prefix contains an invalid character.
     *
     * @since WD-DOM-Level-2-19990923
     * @see AttrNSImpl
     * @see ElementNSImpl
     */
    public void setPrefix(String prefix)
        throws DOMException
    {
        throw new DOMException(DOMException.NAMESPACE_ERR,
              DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN,
                 "NAMESPACE_ERR", null));
    }

    /**
     * Introduced in DOM Level 2. <p>
     *
     * Returns the local part of the qualified name of this node.
     * For nodes created with a DOM Level 1 method, such as createElement
     * from the Document interface, and for nodes of any type other than
     * ELEMENT_NODE and ATTRIBUTE_NODE this is the same as the nodeName
     * attribute.
     * <p>
     *  在DOM级别2中引入<p>
     * 
     *  返回此节点的限定名称的局部部分对于使用DOM 1级方法创建的节点(例如Document接口中的createElement),对于除ELEMENT_NODE和ATTRIBUTE_NODE之外的任何类型的
     * 节点,这与nodeName属性相同。
     * 
     * 
     * @since WD-DOM-Level-2-19990923
     * @see AttrNSImpl
     * @see ElementNSImpl
     */
    public String             getLocalName()
    {
        return null;
    }

    //
    // EventTarget support
    //

    public void addEventListener(String type, EventListener listener,
                                 boolean useCapture) {
        // simply forward to Document
        ownerDocument().addEventListener(this, type, listener, useCapture);
    }

    public void removeEventListener(String type, EventListener listener,
                                    boolean useCapture) {
        // simply forward to Document
        ownerDocument().removeEventListener(this, type, listener, useCapture);
    }

    public boolean dispatchEvent(Event event) {
        // simply forward to Document
        return ownerDocument().dispatchEvent(this, event);
    }

    //
    // Public DOM Level 3 methods
    //

    /**
     * The absolute base URI of this node or <code>null</code> if undefined.
     * This value is computed according to . However, when the
     * <code>Document</code> supports the feature "HTML" , the base URI is
     * computed using first the value of the href attribute of the HTML BASE
     * element if any, and the value of the <code>documentURI</code>
     * attribute from the <code>Document</code> interface otherwise.
     * <br> When the node is an <code>Element</code>, a <code>Document</code>
     * or a a <code>ProcessingInstruction</code>, this attribute represents
     * the properties [base URI] defined in . When the node is a
     * <code>Notation</code>, an <code>Entity</code>, or an
     * <code>EntityReference</code>, this attribute represents the
     * properties [declaration base URI] in the . How will this be affected
     * by resolution of relative namespace URIs issue?It's not.Should this
     * only be on Document, Element, ProcessingInstruction, Entity, and
     * Notation nodes, according to the infoset? If not, what is it equal to
     * on other nodes? Null? An empty string? I think it should be the
     * parent's.No.Should this be read-only and computed or and actual
     * read-write attribute?Read-only and computed (F2F 19 Jun 2000 and
     * teleconference 30 May 2001).If the base HTML element is not yet
     * attached to a document, does the insert change the Document.baseURI?
     * Yes. (F2F 26 Sep 2001)
     * <p>
     * 该节点的绝对基本URI或<code> null </code>如果未定义此值是根据以下方式计算的：然而,当<code> Document </code>支持特征"HTML"时,基本URI使用first 
     * HTML BASE元素的href属性的值(如果有)和来自<code> Document </code>接口的<code> documentURI </code>属性的值<br>当节点为<code >元素
     * </code>,<code> Document </code>或aa <code> ProcessingInstruction </code>,此属性表示当节点是<code>记号</code时定义的属性[base URI] >
     * ,<code> Entity </code>或<code> EntityReference </code>,此属性表示属性[声明基URI]相对命名空间URI问题的解决会如何影响?根据信息集,它不是只有在
     * 文档,元素,ProcessingInstruction,实体和符号节点上?如果不是,在其他节点上它是什么?空值?空字符串?我认为它应该是父的soshould这是只读和计算或实际的读写属性?只读和计算(F
     * 2F 2000年6月19日和电话会议2001年5月30日)如果基本HTML元素尚未附加一个文档,插入更改DocumentbaseURI?是(F2F 26 Sep 2001)。
     * 
     * 
     * @since DOM Level 3
     */
    public String getBaseURI() {
        return null;
    }

    /**
     * Compares a node with this node with regard to their position in the
     * tree and according to the document order. This order can be extended
     * by module that define additional types of nodes.
     * <p>
     * 将节点与此节点在树中的位置和根据文档顺序进行比较此顺序可以通过定义其他类型的节点的模块进行扩展
     * 
     * 
     * @param other The node to compare against this node.
     * @return Returns how the given node is positioned relatively to this
     *   node.
     * @since DOM Level 3
     * @deprecated
     */
    public short compareTreePosition(Node other) {
        // Questions of clarification for this method - to be answered by the
        // DOM WG.   Current assumptions listed - LM
        //
        // 1. How do ENTITY nodes compare?
        //    Current assumption: TREE_POSITION_DISCONNECTED, as ENTITY nodes
        //    aren't really 'in the tree'
        //
        // 2. How do NOTATION nodes compare?
        //    Current assumption: TREE_POSITION_DISCONNECTED, as NOTATION nodes
        //    aren't really 'in the tree'
        //
        // 3. Are TREE_POSITION_ANCESTOR and TREE_POSITION_DESCENDANT
        //    only relevant for nodes that are "part of the document tree"?
        //     <outer>
        //         <inner  myattr="true"/>
        //     </outer>
        //    Is the element node "outer" considered an ancestor of "myattr"?
        //    Current assumption: No.
        //
        // 4. How do children of ATTRIBUTE nodes compare (with eachother, or
        //    with children of other attribute nodes with the same element)
        //    Current assumption: Children of ATTRIBUTE nodes are treated as if
        //    they they are the attribute node itself, unless the 2 nodes
        //    are both children of the same attribute.
        //
        // 5. How does an ENTITY_REFERENCE node compare with it's children?
        //    Given the DOM, it should precede its children as an ancestor.
        //    Given "document order",  does it represent the same position?
        //    Current assumption: An ENTITY_REFERENCE node is an ancestor of its
        //    children.
        //
        // 6. How do children of a DocumentFragment compare?
        //    Current assumption: If both nodes are part of the same document
        //    fragment, there are compared as if they were part of a document.


        // If the nodes are the same...
        if (this==other)
          return (TREE_POSITION_SAME_NODE | TREE_POSITION_EQUIVALENT);

        // If either node is of type ENTITY or NOTATION, compare as disconnected
        short thisType = this.getNodeType();
        short otherType = other.getNodeType();

        // If either node is of type ENTITY or NOTATION, compare as disconnected
        if (thisType == Node.ENTITY_NODE ||
            thisType == Node.NOTATION_NODE ||
            otherType == Node.ENTITY_NODE ||
            otherType == Node.NOTATION_NODE ) {
          return TREE_POSITION_DISCONNECTED;
        }

        // Find the ancestor of each node, and the distance each node is from
        // its ancestor.
        // During this traversal, look for ancestor/descendent relationships
        // between the 2 nodes in question.
        // We do this now, so that we get this info correct for attribute nodes
        // and their children.

        Node node;
        Node thisAncestor = this;
        Node otherAncestor = other;
        int thisDepth=0;
        int otherDepth=0;
        for (node=this; node != null; node = node.getParentNode()) {
            thisDepth +=1;
            if (node == other)
              // The other node is an ancestor of this one.
              return (TREE_POSITION_ANCESTOR | TREE_POSITION_PRECEDING);
            thisAncestor = node;
        }

        for (node=other; node!=null; node=node.getParentNode()) {
            otherDepth +=1;
            if (node == this)
              // The other node is a descendent of the reference node.
              return (TREE_POSITION_DESCENDANT | TREE_POSITION_FOLLOWING);
            otherAncestor = node;
        }


        Node thisNode = this;
        Node otherNode = other;

        int thisAncestorType = thisAncestor.getNodeType();
        int otherAncestorType = otherAncestor.getNodeType();

        // if the ancestor is an attribute, get owning element.
        // we are now interested in the owner to determine position.

        if (thisAncestorType == Node.ATTRIBUTE_NODE)  {
           thisNode = ((AttrImpl)thisAncestor).getOwnerElement();
        }
        if (otherAncestorType == Node.ATTRIBUTE_NODE) {
           otherNode = ((AttrImpl)otherAncestor).getOwnerElement();
        }

        // Before proceeding, we should check if both ancestor nodes turned
        // out to be attributes for the same element
        if (thisAncestorType == Node.ATTRIBUTE_NODE &&
            otherAncestorType == Node.ATTRIBUTE_NODE &&
            thisNode==otherNode)
            return TREE_POSITION_EQUIVALENT;

        // Now, find the ancestor of the owning element, if the original
        // ancestor was an attribute

        // Note:  the following 2 loops are quite close to the ones above.
        // May want to common them up.  LM.
        if (thisAncestorType == Node.ATTRIBUTE_NODE) {
            thisDepth=0;
            for (node=thisNode; node != null; node=node.getParentNode()) {
                thisDepth +=1;
                if (node == otherNode)
                  // The other node is an ancestor of the owning element
                  {
                  return TREE_POSITION_PRECEDING;
                  }
                thisAncestor = node;
            }
        }

        // Now, find the ancestor of the owning element, if the original
        // ancestor was an attribute
        if (otherAncestorType == Node.ATTRIBUTE_NODE) {
            otherDepth=0;
            for (node=otherNode; node != null; node=node.getParentNode()) {
                otherDepth +=1;
                if (node == thisNode)
                  // The other node is a descendent of the reference
                  // node's element
                  return TREE_POSITION_FOLLOWING;
                otherAncestor = node;
            }
        }

        // thisAncestor and otherAncestor must be the same at this point,
        // otherwise, we are not in the same tree or document fragment
        if (thisAncestor != otherAncestor)
          return TREE_POSITION_DISCONNECTED;


        // Go up the parent chain of the deeper node, until we find a node
        // with the same depth as the shallower node

        if (thisDepth > otherDepth) {
          for (int i=0; i<thisDepth - otherDepth; i++)
            thisNode = thisNode.getParentNode();
          // Check if the node we have reached is in fact "otherNode". This can
          // happen in the case of attributes.  In this case, otherNode
          // "precedes" this.
          if (thisNode == otherNode)
            return TREE_POSITION_PRECEDING;
        }

        else {
          for (int i=0; i<otherDepth - thisDepth; i++)
            otherNode = otherNode.getParentNode();
          // Check if the node we have reached is in fact "thisNode".  This can
          // happen in the case of attributes.  In this case, otherNode
          // "follows" this.
          if (otherNode == thisNode)
            return TREE_POSITION_FOLLOWING;
        }

        // We now have nodes at the same depth in the tree.  Find a common
        // ancestor.
        Node thisNodeP, otherNodeP;
        for (thisNodeP=thisNode.getParentNode(),
                  otherNodeP=otherNode.getParentNode();
             thisNodeP!=otherNodeP;) {
             thisNode = thisNodeP;
             otherNode = otherNodeP;
             thisNodeP = thisNodeP.getParentNode();
             otherNodeP = otherNodeP.getParentNode();
        }

        // At this point, thisNode and otherNode are direct children of
        // the common ancestor.
        // See whether thisNode or otherNode is the leftmost

        for (Node current=thisNodeP.getFirstChild();
                  current!=null;
                  current=current.getNextSibling()) {
               if (current==otherNode) {
                 return TREE_POSITION_PRECEDING;
               }
               else if (current==thisNode) {
                 return TREE_POSITION_FOLLOWING;
               }
        }
        // REVISIT:  shouldn't get here.   Should probably throw an
        // exception
        return 0;

    }
    /**
     * Compares a node with this node with regard to their position in the
     * document.
     * <p>
     *  将节点与此节点在文档中的位置进行比较
     * 
     * 
     * @param other The node to compare against this node.
     * @return Returns how the given node is positioned relatively to this
     *   node.
     * @since DOM Level 3
     */
    public short compareDocumentPosition(Node other) throws DOMException {

        // If the nodes are the same, no flags should be set
        if (this==other)
          return 0;

        // check if other is from a different implementation
        try {
            NodeImpl node = (NodeImpl) other;
        } catch (ClassCastException e) {
            // other comes from a different implementation
            String msg = DOMMessageFormatter.formatMessage(
               DOMMessageFormatter.DOM_DOMAIN, "NOT_SUPPORTED_ERR", null);
            throw new DOMException(DOMException.NOT_SUPPORTED_ERR, msg);
        }

        Document thisOwnerDoc, otherOwnerDoc;
        // get the respective Document owners.
        if (this.getNodeType() == Node.DOCUMENT_NODE)
          thisOwnerDoc = (Document)this;
        else
          thisOwnerDoc = this.getOwnerDocument();
        if (other.getNodeType() == Node.DOCUMENT_NODE)
          otherOwnerDoc = (Document)other;
        else
          otherOwnerDoc = other.getOwnerDocument();

        // If from different documents, we know they are disconnected.
        // and have an implementation dependent order
        if (thisOwnerDoc != otherOwnerDoc &&
            thisOwnerDoc !=null &&
            otherOwnerDoc !=null)
 {
          int otherDocNum = ((CoreDocumentImpl)otherOwnerDoc).getNodeNumber();
          int thisDocNum = ((CoreDocumentImpl)thisOwnerDoc).getNodeNumber();
          if (otherDocNum > thisDocNum)
            return DOCUMENT_POSITION_DISCONNECTED |
                   DOCUMENT_POSITION_FOLLOWING |
                   DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC;
          else
            return DOCUMENT_POSITION_DISCONNECTED |
                   DOCUMENT_POSITION_PRECEDING |
                   DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC;

        }

        // Find the ancestor of each node, and the distance each node is from
        // its ancestor.
        // During this traversal, look for ancestor/descendent relationships
        // between the 2 nodes in question.
        // We do this now, so that we get this info correct for attribute nodes
        // and their children.

        Node node;
        Node thisAncestor = this;
        Node otherAncestor = other;

        int thisDepth=0;
        int otherDepth=0;
        for (node=this; node != null; node = node.getParentNode()) {
            thisDepth +=1;
            if (node == other)
              // The other node is an ancestor of this one.
              return (DOCUMENT_POSITION_CONTAINS |
                      DOCUMENT_POSITION_PRECEDING);
            thisAncestor = node;
        }

        for (node=other; node!=null; node=node.getParentNode()) {
            otherDepth +=1;
            if (node == this)
              // The other node is a descendent of the reference node.
              return (DOCUMENT_POSITION_IS_CONTAINED |
                      DOCUMENT_POSITION_FOLLOWING);
            otherAncestor = node;
        }



        int thisAncestorType = thisAncestor.getNodeType();
        int otherAncestorType = otherAncestor.getNodeType();
        Node thisNode = this;
        Node otherNode = other;

        // Special casing for ENTITY, NOTATION, DOCTYPE and ATTRIBUTES
        // LM:  should rewrite this.
        switch (thisAncestorType) {
          case Node.NOTATION_NODE:
          case Node.ENTITY_NODE: {
            DocumentType container = thisOwnerDoc.getDoctype();
            if (container == otherAncestor) return
                   (DOCUMENT_POSITION_CONTAINS | DOCUMENT_POSITION_PRECEDING);
            switch (otherAncestorType) {
              case Node.NOTATION_NODE:
              case Node.ENTITY_NODE:  {
                if (thisAncestorType != otherAncestorType)
                 // the nodes are of different types
                 return ((thisAncestorType>otherAncestorType) ?
                    DOCUMENT_POSITION_PRECEDING:DOCUMENT_POSITION_FOLLOWING);
                else {
                 // the nodes are of the same type.  Find order.
                 if (thisAncestorType == Node.NOTATION_NODE)

                     if (((NamedNodeMapImpl)container.getNotations()).precedes(otherAncestor,thisAncestor))
                       return (DOCUMENT_POSITION_PRECEDING |
                               DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC);
                     else
                       return (DOCUMENT_POSITION_FOLLOWING |
                               DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC);
                 else
                     if (((NamedNodeMapImpl)container.getEntities()).precedes(otherAncestor,thisAncestor))
                       return (DOCUMENT_POSITION_PRECEDING |
                               DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC);
                     else
                       return (DOCUMENT_POSITION_FOLLOWING |
                               DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC);
                }
              }
            }
            thisNode = thisAncestor = thisOwnerDoc;
            break;
          }
          case Node.DOCUMENT_TYPE_NODE: {
            if (otherNode == thisOwnerDoc)
              return (DOCUMENT_POSITION_PRECEDING |
                      DOCUMENT_POSITION_CONTAINS);
            else if (thisOwnerDoc!=null && thisOwnerDoc==otherOwnerDoc)
              return (DOCUMENT_POSITION_FOLLOWING);
            break;
          }
          case Node.ATTRIBUTE_NODE: {
            thisNode = ((AttrImpl)thisAncestor).getOwnerElement();
            if (otherAncestorType==Node.ATTRIBUTE_NODE) {
              otherNode = ((AttrImpl)otherAncestor).getOwnerElement();
              if (otherNode == thisNode) {
                if (((NamedNodeMapImpl)thisNode.getAttributes()).precedes(other,this))
                  return (DOCUMENT_POSITION_PRECEDING |
                          DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC);
                else
                  return (DOCUMENT_POSITION_FOLLOWING |
                          DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC);
              }
            }

            // Now, find the ancestor of the element
            thisDepth=0;
            for (node=thisNode; node != null; node=node.getParentNode()) {
                thisDepth +=1;
                if (node == otherNode)
                  {
                  // The other node is an ancestor of the owning element
                  return (DOCUMENT_POSITION_CONTAINS |
                          DOCUMENT_POSITION_PRECEDING);
                  }
                thisAncestor = node;
            }
          }
        }
        switch (otherAncestorType) {
          case Node.NOTATION_NODE:
          case Node.ENTITY_NODE: {
          DocumentType container = thisOwnerDoc.getDoctype();
            if (container == this) return (DOCUMENT_POSITION_IS_CONTAINED |
                                          DOCUMENT_POSITION_FOLLOWING);
            otherNode = otherAncestor = thisOwnerDoc;
            break;
          }
          case Node.DOCUMENT_TYPE_NODE: {
            if (thisNode == otherOwnerDoc)
              return (DOCUMENT_POSITION_FOLLOWING |
                      DOCUMENT_POSITION_IS_CONTAINED);
            else if (otherOwnerDoc!=null && thisOwnerDoc==otherOwnerDoc)
              return (DOCUMENT_POSITION_PRECEDING);
            break;
          }
          case Node.ATTRIBUTE_NODE: {
            otherDepth=0;
            otherNode = ((AttrImpl)otherAncestor).getOwnerElement();
            for (node=otherNode; node != null; node=node.getParentNode()) {
                otherDepth +=1;
                if (node == thisNode)
                  // The other node is a descendent of the reference
                  // node's element
                  return DOCUMENT_POSITION_FOLLOWING |
                         DOCUMENT_POSITION_IS_CONTAINED;
                otherAncestor = node;
            }

          }
        }

        // thisAncestor and otherAncestor must be the same at this point,
        // otherwise, the original nodes are disconnected
        if (thisAncestor != otherAncestor) {
          int thisAncestorNum, otherAncestorNum;
          thisAncestorNum = ((NodeImpl)thisAncestor).getNodeNumber();
          otherAncestorNum = ((NodeImpl)otherAncestor).getNodeNumber();

          if (thisAncestorNum > otherAncestorNum)
            return DOCUMENT_POSITION_DISCONNECTED |
                   DOCUMENT_POSITION_FOLLOWING |
                   DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC;
          else
            return DOCUMENT_POSITION_DISCONNECTED |
                   DOCUMENT_POSITION_PRECEDING |
                   DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC;
        }


        // Go up the parent chain of the deeper node, until we find a node
        // with the same depth as the shallower node

        if (thisDepth > otherDepth) {
          for (int i=0; i<thisDepth - otherDepth; i++)
            thisNode = thisNode.getParentNode();
          // Check if the node we have reached is in fact "otherNode". This can
          // happen in the case of attributes.  In this case, otherNode
          // "precedes" this.
          if (thisNode == otherNode)
{
            return DOCUMENT_POSITION_PRECEDING;
          }
        }

        else {
          for (int i=0; i<otherDepth - thisDepth; i++)
            otherNode = otherNode.getParentNode();
          // Check if the node we have reached is in fact "thisNode".  This can
          // happen in the case of attributes.  In this case, otherNode
          // "follows" this.
          if (otherNode == thisNode)
            return DOCUMENT_POSITION_FOLLOWING;
        }

        // We now have nodes at the same depth in the tree.  Find a common
        // ancestor.
        Node thisNodeP, otherNodeP;
        for (thisNodeP=thisNode.getParentNode(),
                  otherNodeP=otherNode.getParentNode();
             thisNodeP!=otherNodeP;) {
             thisNode = thisNodeP;
             otherNode = otherNodeP;
             thisNodeP = thisNodeP.getParentNode();
             otherNodeP = otherNodeP.getParentNode();
        }

        // At this point, thisNode and otherNode are direct children of
        // the common ancestor.
        // See whether thisNode or otherNode is the leftmost

        for (Node current=thisNodeP.getFirstChild();
                  current!=null;
                  current=current.getNextSibling()) {
               if (current==otherNode) {
                 return DOCUMENT_POSITION_PRECEDING;
               }
               else if (current==thisNode) {
                 return DOCUMENT_POSITION_FOLLOWING;
               }
        }
        // REVISIT:  shouldn't get here.   Should probably throw an
        // exception
        return 0;

    }

    /**
     * This attribute returns the text content of this node and its
     * descendants. When it is defined to be null, setting it has no effect.
     * When set, any possible children this node may have are removed and
     * replaced by a single <code>Text</code> node containing the string
     * this attribute is set to. On getting, no serialization is performed,
     * the returned string does not contain any markup. No whitespace
     * normalization is performed, the returned string does not contain the
     * element content whitespaces . Similarly, on setting, no parsing is
     * performed either, the input string is taken as pure textual content.
     * <br>The string returned is made of the text content of this node
     * depending on its type, as defined below:
     * <table border='1'>
     * <tr>
     * <th>Node type</th>
     * <th>Content</th>
     * </tr>

    /**
     * This attribute returns the text content of this node and its
     * descendants. When it is defined to be null, setting it has no effect.
     * When set, any possible children this node may have are removed and
     * replaced by a single <code>Text</code> node containing the string
     * this attribute is set to. On getting, no serialization is performed,
     * the returned string does not contain any markup. No whitespace
     * normalization is performed, the returned string does not contain the
     * element content whitespaces . Similarly, on setting, no parsing is
     * performed either, the input string is taken as pure textual content.
     * <br>The string returned is made of the text content of this node
     * depending on its type, as defined below:
     * <table border='1'>
     * <tr>
     * <th>Node type</th>
     * <th>Content</th>
     * </tr>
     * <tr>
     * <td valign='top' rowspan='1' colspan='1'>
     * ELEMENT_NODE, ENTITY_NODE, ENTITY_REFERENCE_NODE,
     * DOCUMENT_FRAGMENT_NODE</td>
     * <td valign='top' rowspan='1' colspan='1'>concatenation of the <code>textContent</code>
     * attribute value of every child node, excluding COMMENT_NODE and
     * PROCESSING_INSTRUCTION_NODE nodes</td>
     * </tr>
     * <tr>
     * <td valign='top' rowspan='1' colspan='1'>ATTRIBUTE_NODE, TEXT_NODE,
     * CDATA_SECTION_NODE, COMMENT_NODE, PROCESSING_INSTRUCTION_NODE</td>
     * <td valign='top' rowspan='1' colspan='1'>
     * <code>nodeValue</code></td>
     * </tr>
     * <tr>
     * <td valign='top' rowspan='1' colspan='1'>DOCUMENT_NODE, DOCUMENT_TYPE_NODE, NOTATION_NODE</td>
     * <td valign='top' rowspan='1' colspan='1'>
     * null</td>
     * </tr>
     * </table>
     * <p>
     * 此属性返回此节点及其后代的文本内容当定义为null时,设置它无效果设置时,此节点可能具有的任何可能的子项都将被单个<code> Text </code>包含字符串的节点此属性设置为On获取,不执行序列化
     * ,返回的字符串不包含任何标记不执行空白标准化,返回的字符串不包含元素内容whitespaces类似地,在设置时,不执行解析则输入字符串将被视为纯文本内容<br>返回的字符串取决于此节点的文本内容,具体取
     * 决于其类型,如下所述：。
     * <table border='1'>
     * <tr>
     *  <th>节点类型</th> <th>内容</th>
     * </tr>
     * 
     * / **此属​​性返回此节点及其后代的文本内容当定义为null时,设置它无效果设置时,此节点可能具有的任何可能的子项都将被删除并替换为单个<code> Text < / code>包含字符串的节点此属性
     * 设置为On获取,不执行序列化,返回的字符串不包含任何标记不执行空白标准化,返回的字符串不包含元素内容whitespaces同样,在设置时,无执行解析时,输入字符串将作为纯文本内容<br>返回的字符串取决
     * 于此节点的文本内容,具体取决于其类型,如下所述：。
     * <table border='1'>
     * <tr>
     *  <th>节点类型</th> <th>内容</th>
     * </tr>
     * <tr>
     * <td valign='top' rowspan='1' colspan='1'>
     * 每个子节点的<code> textContent </code>属性值的连接,不包括COMMENT_NODE和("COMMENT_NODE")。
     *  PROCESSING_INSTRUCTION_NODE个节点</td>。
     * </tr>
     * <tr>
     *  <td valign ='top'rowspan ='1'colspan ='1'> ATTRIBUTE_NODE,TEXT_NODE,CDATA_SECTION_NODE,COMMENT_NODE,
     * PROCESSING_INSTRUCTION_NODE </td>。
     * <td valign='top' rowspan='1' colspan='1'>
     *  <code> nodeValue </code> </td>
     * </tr>
     * <tr>
     *  <td valign ='top'rowspan ='1'colspan ='1'> DOCUMENT_NODE,DOCUMENT_TYPE_NODE,NOTATION_NODE </td>
     * <td valign='top' rowspan='1' colspan='1'>
     *  null </td>
     * </tr>
     * </table>
     * 
     * @exception DOMException
     *   NO_MODIFICATION_ALLOWED_ERR: Raised when the node is readonly.
     * @exception DOMException
     *   DOMSTRING_SIZE_ERR: Raised when it would return more characters than
     *   fit in a <code>DOMString</code> variable on the implementation
     *   platform.
     * @since DOM Level 3
     */
    public String getTextContent() throws DOMException {
        return getNodeValue();  // overriden in some subclasses
    }

    // internal method taking a StringBuffer in parameter
    void getTextContent(StringBuffer buf) throws DOMException {
        String content = getNodeValue();
        if (content != null) {
            buf.append(content);
        }
    }

    /**
     * This attribute returns the text content of this node and its
     * descendants. When it is defined to be null, setting it has no effect.
     * When set, any possible children this node may have are removed and
     * replaced by a single <code>Text</code> node containing the string
     * this attribute is set to. On getting, no serialization is performed,
     * the returned string does not contain any markup. No whitespace
     * normalization is performed, the returned string does not contain the
     * element content whitespaces . Similarly, on setting, no parsing is
     * performed either, the input string is taken as pure textual content.
     * <br>The string returned is made of the text content of this node
     * depending on its type, as defined below:
     * <table border='1'>
     * <tr>
     * <th>Node type</th>
     * <th>Content</th>
     * </tr>
     * <tr>
     * <td valign='top' rowspan='1' colspan='1'>
     * ELEMENT_NODE, ENTITY_NODE, ENTITY_REFERENCE_NODE,
     * DOCUMENT_FRAGMENT_NODE</td>
     * <td valign='top' rowspan='1' colspan='1'>concatenation of the <code>textContent</code>
     * attribute value of every child node, excluding COMMENT_NODE and
     * PROCESSING_INSTRUCTION_NODE nodes</td>
     * </tr>
     * <tr>
     * <td valign='top' rowspan='1' colspan='1'>ATTRIBUTE_NODE, TEXT_NODE,
     * CDATA_SECTION_NODE, COMMENT_NODE, PROCESSING_INSTRUCTION_NODE</td>
     * <td valign='top' rowspan='1' colspan='1'>
     * <code>nodeValue</code></td>
     * </tr>
     * <tr>
     * <td valign='top' rowspan='1' colspan='1'>DOCUMENT_NODE, DOCUMENT_TYPE_NODE, NOTATION_NODE</td>
     * <td valign='top' rowspan='1' colspan='1'>
     * null</td>
     * </tr>
     * </table>
     * <p>
     * 此属性返回此节点及其后代的文本内容当定义为null时,设置它无效果设置时,此节点可能具有的任何可能的子项都将被单个<code> Text </code>包含字符串的节点此属性设置为On获取,不执行序列化
     * ,返回的字符串不包含任何标记不执行空白标准化,返回的字符串不包含元素内容whitespaces类似地,在设置时,不执行解析则输入字符串将被视为纯文本内容<br>返回的字符串取决于此节点的文本内容,具体取
     * 决于其类型,如下所述：。
     * <table border='1'>
     * <tr>
     *  <th>节点类型</th> <th>内容</th>
     * </tr>
     * <tr>
     * <td valign='top' rowspan='1' colspan='1'>
     * 每个子节点的<code> textContent </code>属性值的连接,不包括COMMENT_NODE和("COMMENT_NODE")。
     *  PROCESSING_INSTRUCTION_NODE个节点</td>。
     * </tr>
     * <tr>
     *  <td valign ='top'rowspan ='1'colspan ='1'> ATTRIBUTE_NODE,TEXT_NODE,CDATA_SECTION_NODE,COMMENT_NODE,
     * PROCESSING_INSTRUCTION_NODE </td>。
     * <td valign='top' rowspan='1' colspan='1'>
     *  <code> nodeValue </code> </td>
     * </tr>
     * <tr>
     *  <td valign ='top'rowspan ='1'colspan ='1'> DOCUMENT_NODE,DOCUMENT_TYPE_NODE,NOTATION_NODE </td>
     * <td valign='top' rowspan='1' colspan='1'>
     *  null </td>
     * </tr>
     * </table>
     * 
     * @exception DOMException
     *   NO_MODIFICATION_ALLOWED_ERR: Raised when the node is readonly.
     * @exception DOMException
     *   DOMSTRING_SIZE_ERR: Raised when it would return more characters than
     *   fit in a <code>DOMString</code> variable on the implementation
     *   platform.
     * @since DOM Level 3
     */
    public void setTextContent(String textContent)
        throws DOMException {
        setNodeValue(textContent);
    }

    /**
     * Returns whether this node is the same node as the given one.
     * <br>This method provides a way to determine whether two
     * <code>Node</code> references returned by the implementation reference
     * the same object. When two <code>Node</code> references are references
     * to the same object, even if through a proxy, the references may be
     * used completely interchangably, such that all attributes have the
     * same values and calling the same DOM method on either reference
     * always has exactly the same effect.
     * <p>
     * 返回此节点是否与给定的节点相同的节点<br>此方法提供了一种方法来确定实现引用的两个<code> Node </code>引用是否引用相同的对象当两个<code> Node </code >引用是对同一
     * 对象的引用,即使通过代理,引用可以完全互换使用,使得所有属性具有相同的值,并且对任一引用调用相同的DOM方法总是具有完全相同的效果。
     * 
     * 
     * @param other The node to test against.
     * @return Returns <code>true</code> if the nodes are the same,
     *   <code>false</code> otherwise.
     * @since DOM Level 3
     */
    public boolean isSameNode(Node other) {
        // we do not use any wrapper so the answer is obvious
        return this == other;
    }




    /**
     *  DOM Level 3: Experimental
     *  This method checks if the specified <code>namespaceURI</code> is the
     *  default namespace or not.
     * <p>
     *  DOM Level 3：Experimental此方法检查指定的<code> namespaceURI </code>是否是默认命名空间
     * 
     * 
     *  @param namespaceURI The namespace URI to look for.
     *  @return  <code>true</code> if the specified <code>namespaceURI</code>
     *   is the default namespace, <code>false</code> otherwise.
     * @since DOM Level 3
     */
    public boolean isDefaultNamespace(String namespaceURI){
        // REVISIT: remove casts when DOM L3 becomes REC.
        short type = this.getNodeType();
        switch (type) {
        case Node.ELEMENT_NODE: {
            String namespace = this.getNamespaceURI();
            String prefix = this.getPrefix();

            // REVISIT: is it possible that prefix is empty string?
            if (prefix == null || prefix.length() == 0) {
                if (namespaceURI == null) {
                    return (namespace == namespaceURI);
                }
                return namespaceURI.equals(namespace);
            }
            if (this.hasAttributes()) {
                ElementImpl elem = (ElementImpl)this;
                NodeImpl attr = (NodeImpl)elem.getAttributeNodeNS("http://www.w3.org/2000/xmlns/", "xmlns");
                if (attr != null) {
                    String value = attr.getNodeValue();
                    if (namespaceURI == null) {
                        return (namespace == value);
                    }
                    return namespaceURI.equals(value);
                }
            }

            NodeImpl ancestor = (NodeImpl)getElementAncestor(this);
            if (ancestor != null) {
                return ancestor.isDefaultNamespace(namespaceURI);
            }
            return false;
        }
        case Node.DOCUMENT_NODE:{
                return((NodeImpl)((Document)this).getDocumentElement()).isDefaultNamespace(namespaceURI);
            }

        case Node.ENTITY_NODE :
        case Node.NOTATION_NODE:
        case Node.DOCUMENT_FRAGMENT_NODE:
        case Node.DOCUMENT_TYPE_NODE:
            // type is unknown
            return false;
        case Node.ATTRIBUTE_NODE:{
                if (this.ownerNode.getNodeType() == Node.ELEMENT_NODE) {
                    return ownerNode.isDefaultNamespace(namespaceURI);

                }
                return false;
            }
        default:{
                NodeImpl ancestor = (NodeImpl)getElementAncestor(this);
                if (ancestor != null) {
                    return ancestor.isDefaultNamespace(namespaceURI);
                }
                return false;
            }

        }


    }


    /**
     *
     * DOM Level 3 - Experimental:
     * Look up the prefix associated to the given namespace URI, starting from this node.
     *
     * <p>
     *  DOM Level 3  - 实验性：查找与给定命名空间URI相关联的前缀,从此节点开始
     * 
     * 
     * @param namespaceURI
     * @return the prefix for the namespace
     */
    public String lookupPrefix(String namespaceURI){

        // REVISIT: When Namespaces 1.1 comes out this may not be true
        // Prefix can't be bound to null namespace
        if (namespaceURI == null) {
            return null;
        }

        short type = this.getNodeType();

        switch (type) {
        case Node.ELEMENT_NODE: {

                String namespace = this.getNamespaceURI(); // to flip out children
                return lookupNamespacePrefix(namespaceURI, (ElementImpl)this);
            }
        case Node.DOCUMENT_NODE:{
                return((NodeImpl)((Document)this).getDocumentElement()).lookupPrefix(namespaceURI);
            }

        case Node.ENTITY_NODE :
        case Node.NOTATION_NODE:
        case Node.DOCUMENT_FRAGMENT_NODE:
        case Node.DOCUMENT_TYPE_NODE:
            // type is unknown
            return null;
        case Node.ATTRIBUTE_NODE:{
                if (this.ownerNode.getNodeType() == Node.ELEMENT_NODE) {
                    return ownerNode.lookupPrefix(namespaceURI);

                }
                return null;
            }
        default:{
                NodeImpl ancestor = (NodeImpl)getElementAncestor(this);
                if (ancestor != null) {
                    return ancestor.lookupPrefix(namespaceURI);
                }
                return null;
            }

        }
    }
    /**
     * DOM Level 3 - Experimental:
     * Look up the namespace URI associated to the given prefix, starting from this node.
     * Use lookupNamespaceURI(null) to lookup the default namespace
     *
     * <p>
     * DOM级别3  - 实验：查找与给定前缀关联的命名空间URI,从此节点开始使用lookupNamespaceURI(null)查找默认命名空间
     * 
     * 
     * @param namespaceURI
     * @return th URI for the namespace
     * @since DOM Level 3
     */
    public String lookupNamespaceURI(String specifiedPrefix) {
        short type = this.getNodeType();
        switch (type) {
        case Node.ELEMENT_NODE : {

                String namespace = this.getNamespaceURI();
                String prefix = this.getPrefix();
                if (namespace !=null) {
                    // REVISIT: is it possible that prefix is empty string?
                    if (specifiedPrefix== null && prefix==specifiedPrefix) {
                        // looking for default namespace
                        return namespace;
                    } else if (prefix != null && prefix.equals(specifiedPrefix)) {
                        // non default namespace
                        return namespace;
                    }
                }
                if (this.hasAttributes()) {
                    NamedNodeMap map = this.getAttributes();
                    int length = map.getLength();
                    for (int i=0;i<length;i++) {
                        Node attr = map.item(i);
                        String attrPrefix = attr.getPrefix();
                        String value = attr.getNodeValue();
                        namespace = attr.getNamespaceURI();
                        if (namespace !=null && namespace.equals("http://www.w3.org/2000/xmlns/")) {
                            // at this point we are dealing with DOM Level 2 nodes only
                            if (specifiedPrefix == null &&
                                attr.getNodeName().equals("xmlns")) {
                                // default namespace
                                return value;
                            } else if (attrPrefix !=null &&
                                       attrPrefix.equals("xmlns") &&
                                       attr.getLocalName().equals(specifiedPrefix)) {
                                // non default namespace
                                return value;
                            }
                        }
                    }
                }
                NodeImpl ancestor = (NodeImpl)getElementAncestor(this);
                if (ancestor != null) {
                    return ancestor.lookupNamespaceURI(specifiedPrefix);
                }

                return null;


            }
        case Node.DOCUMENT_NODE : {
                return((NodeImpl)((Document)this).getDocumentElement()).lookupNamespaceURI(specifiedPrefix);
            }
        case Node.ENTITY_NODE :
        case Node.NOTATION_NODE:
        case Node.DOCUMENT_FRAGMENT_NODE:
        case Node.DOCUMENT_TYPE_NODE:
            // type is unknown
            return null;
        case Node.ATTRIBUTE_NODE:{
                if (this.ownerNode.getNodeType() == Node.ELEMENT_NODE) {
                    return ownerNode.lookupNamespaceURI(specifiedPrefix);

                }
                return null;
            }
        default:{
                NodeImpl ancestor = (NodeImpl)getElementAncestor(this);
                if (ancestor != null) {
                    return ancestor.lookupNamespaceURI(specifiedPrefix);
                }
                return null;
            }

        }
    }


    Node getElementAncestor (Node currentNode){
        Node parent = currentNode.getParentNode();
        if (parent != null) {
            short type = parent.getNodeType();
            if (type == Node.ELEMENT_NODE) {
                return parent;
            }
            return getElementAncestor(parent);
        }
        return null;
    }

    String lookupNamespacePrefix(String namespaceURI, ElementImpl el){
        String namespace = this.getNamespaceURI();
        // REVISIT: if no prefix is available is it null or empty string, or
        //          could be both?
        String prefix = this.getPrefix();

        if (namespace!=null && namespace.equals(namespaceURI)) {
            if (prefix != null) {
                String foundNamespace =  el.lookupNamespaceURI(prefix);
                if (foundNamespace !=null && foundNamespace.equals(namespaceURI)) {
                    return prefix;
                }

            }
        }
        if (this.hasAttributes()) {
            NamedNodeMap map = this.getAttributes();
            int length = map.getLength();
            for (int i=0;i<length;i++) {
                Node attr = map.item(i);
                String attrPrefix = attr.getPrefix();
                String value = attr.getNodeValue();
                namespace = attr.getNamespaceURI();
                if (namespace !=null && namespace.equals("http://www.w3.org/2000/xmlns/")) {
                    // DOM Level 2 nodes
                    if (((attr.getNodeName().equals("xmlns")) ||
                         (attrPrefix !=null && attrPrefix.equals("xmlns")) &&
                         value.equals(namespaceURI))) {

                        String localname= attr.getLocalName();
                        String foundNamespace = el.lookupNamespaceURI(localname);
                        if (foundNamespace !=null && foundNamespace.equals(namespaceURI)) {
                            return localname;
                        }
                    }


                }
            }
        }
        NodeImpl ancestor = (NodeImpl)getElementAncestor(this);

        if (ancestor != null) {
            return ancestor.lookupNamespacePrefix(namespaceURI, el);
        }
        return null;
    }

    /**
     * Tests whether two nodes are equal.
     * <br>This method tests for equality of nodes, not sameness (i.e.,
     * whether the two nodes are references to the same object) which can be
     * tested with <code>Node.isSameNode</code>. All nodes that are the same
     * will also be equal, though the reverse may not be true.
     * <br>Two nodes are equal if and only if the following conditions are
     * satisfied: The two nodes are of the same type.The following string
     * attributes are equal: <code>nodeName</code>, <code>localName</code>,
     * <code>namespaceURI</code>, <code>prefix</code>, <code>nodeValue</code>
     * , <code>baseURI</code>. This is: they are both <code>null</code>, or
     * they have the same length and are character for character identical.
     * The <code>attributes</code> <code>NamedNodeMaps</code> are equal.
     * This is: they are both <code>null</code>, or they have the same
     * length and for each node that exists in one map there is a node that
     * exists in the other map and is equal, although not necessarily at the
     * same index.The <code>childNodes</code> <code>NodeLists</code> are
     * equal. This is: they are both <code>null</code>, or they have the
     * same length and contain equal nodes at the same index. This is true
     * for <code>Attr</code> nodes as for any other type of node. Note that
     * normalization can affect equality; to avoid this, nodes should be
     * normalized before being compared.
     * <br>For two <code>DocumentType</code> nodes to be equal, the following
     * conditions must also be satisfied: The following string attributes
     * are equal: <code>publicId</code>, <code>systemId</code>,
     * <code>internalSubset</code>.The <code>entities</code>
     * <code>NamedNodeMaps</code> are equal.The <code>notations</code>
     * <code>NamedNodeMaps</code> are equal.
     * <br>On the other hand, the following do not affect equality: the
     * <code>ownerDocument</code> attribute, the <code>specified</code>
     * attribute for <code>Attr</code> nodes, the
     * <code>isWhitespaceInElementContent</code> attribute for
     * <code>Text</code> nodes, as well as any user data or event listeners
     * registered on the nodes.
     * <p>
     * 测试两个节点是否相等<br>此方法测试节点的相等性,而不是同一性(即,两个节点是否是对同一对象的引用),可以使用<code> NodeisSameNode </code>测试所有节点相同也将是相等的,虽
     * 然相反可能不是真实的<br>当且仅当满足以下条件时,两个节点是相等的：两个节点是相同类型以下字符串属性是相等的：<code> nodeName < / code>,<code> localName </code>
     * ,<code> namespaceURI </code>,<code>前缀</code>,<code> nodeValue </code> ：它们都是<code> null </code>,或者它们具有
     * 相同的长度,并且是字符相同的字符</code> </code> </code> <NamedNodeMaps </code>这是：它们都是<code> null </code>,或者它们具有相同的长度,
     * 并且对于存在于一个映射中的每个节点,存在存在于另一个映射中并且相等的节点,但是不一定是相同的index </code> </Node> </Node> <Node> <Node> </code>是相等的
     * 。
     * 它们都是<code> null </code>,或者它们具有相同的长度,对于<code> Attr </code>节点,与任何其他类型的节点一样。
     * 注意,规范化可以影响等式;为避免这种情况,应在比较之前对节点进行归一化<br>为使两个<code> DocumentType </code>节点相等,还必须满足以下条件：以下字符串属性相等：<code>
     *  publicId < code>,<code> systemId </code>,<code> internalSubset </code><code> entities </code> <code>
     *  NamedNodeMaps </code>是相等的<code>符号</code> <code> NamedNodeMaps </code>是相等的<br>另一方面,平等：<code> ownerDoc
     * ument </code>属性,<code> Attr </code>节点的<code>指定</code>属性,<code> isWhitespaceInElementContent </code> /
     *  code>节点,以及在节点上注册的任何用户数据或事件侦听器。
     * 它们都是<code> null </code>,或者它们具有相同的长度,对于<code> Attr </code>节点,与任何其他类型的节点一样。
     * 
     * 
     * @param arg The node to compare equality with.
     * @param deep If <code>true</code>, recursively compare the subtrees; if
     *   <code>false</code>, compare only the nodes themselves (and its
     *   attributes, if it is an <code>Element</code>).
     * @return If the nodes, and possibly subtrees are equal,
     *   <code>true</code> otherwise <code>false</code>.
     * @since DOM Level 3
     */
    public boolean isEqualNode(Node arg) {
        if (arg == this) {
            return true;
        }
        if (arg.getNodeType() != getNodeType()) {
            return false;
        }
        // in theory nodeName can't be null but better be careful
        // who knows what other implementations may be doing?...
        if (getNodeName() == null) {
            if (arg.getNodeName() != null) {
                return false;
            }
        }
        else if (!getNodeName().equals(arg.getNodeName())) {
            return false;
        }

        if (getLocalName() == null) {
            if (arg.getLocalName() != null) {
                return false;
            }
        }
        else if (!getLocalName().equals(arg.getLocalName())) {
            return false;
        }

        if (getNamespaceURI() == null) {
            if (arg.getNamespaceURI() != null) {
                return false;
            }
        }
        else if (!getNamespaceURI().equals(arg.getNamespaceURI())) {
            return false;
        }

        if (getPrefix() == null) {
            if (arg.getPrefix() != null) {
                return false;
            }
        }
        else if (!getPrefix().equals(arg.getPrefix())) {
            return false;
        }

        if (getNodeValue() == null) {
            if (arg.getNodeValue() != null) {
                return false;
            }
        }
        else if (!getNodeValue().equals(arg.getNodeValue())) {
            return false;
        }


        return true;
    }

    /**
    /* <p>
    /* 
     * @since DOM Level 3
     */
    public Object getFeature(String feature, String version) {
        // we don't have any alternate node, either this node does the job
        // or we don't have anything that does
        return isSupported(feature, version) ? this : null;
    }

    /**
     * Associate an object to a key on this node. The object can later be
     * retrieved from this node by calling <code>getUserData</code> with the
     * same key.
     * <p>
     * 将对象与此节点上的键相关联稍后可以通过使用相同的键调用<code> getUserData </code>从此节点检索对象
     * 
     * 
     * @param key The key to associate the object to.
     * @param data The object to associate to the given key, or
     *   <code>null</code> to remove any existing association to that key.
     * @param handler The handler to associate to that key, or
     *   <code>null</code>.
     * @return Returns the <code>DOMObject</code> previously associated to
     *   the given key on this node, or <code>null</code> if there was none.
     * @since DOM Level 3
     */
    public Object setUserData(String key,
                              Object data,
                              UserDataHandler handler) {
        return ownerDocument().setUserData(this, key, data, handler);
    }

    /**
     * Retrieves the object associated to a key on a this node. The object
     * must first have been set to this node by calling
     * <code>setUserData</code> with the same key.
     * <p>
     *  检索与此节点上的键相关联的对象必须首先通过使用相同的键调用<code> setUserData </code>将该对象设置为此节点
     * 
     * 
     * @param key The key the object is associated to.
     * @return Returns the <code>DOMObject</code> associated to the given key
     *   on this node, or <code>null</code> if there was none.
     * @since DOM Level 3
     */
    public Object getUserData(String key) {
        return ownerDocument().getUserData(this, key);
    }

        protected Hashtable getUserDataRecord(){
        return ownerDocument().getUserDataRecord(this);
        }

    //
    // Public methods
    //

    /**
     * NON-DOM: PR-DOM-Level-1-19980818 mentions readonly nodes in conjunction
     * with Entities, but provides no API to support this.
     * <P>
     * Most DOM users should not touch this method. Its anticpated use
     * is during construction of EntityRefernces, where it will be used to
     * lock the contents replicated from Entity so they can't be casually
     * altered. It _could_ be published as a DOM extension, if desired.
     * <P>
     * Note: since we never have any children deep is meaningless here,
     * ParentNode overrides this behavior.
     * <p>
     *  NON-DOM：PR-DOM-Level-1-19980818提到只读节点与实体,但没有提供API来支持这个
     * <P>
     *  大多数DOM用户不应该触摸这个方法它的反对使用是在构造EntityRefernces期间,它将用于锁定从实体复制的内容,所以他们不能随便改变它_could_发布为DOM扩展,如果需要
     * <P>
     * 注意：因为我们从来没有任何孩子deep在这里没有意义,ParentNode重写这个行为
     * 
     * 
     * @see ParentNode
     *
     * @param readOnly True or false as desired.
     * @param deep If true, children are also toggled. Note that this will
     *  not change the state of an EntityReference or its children,
     *  which are always read-only.
     */
    public void setReadOnly(boolean readOnly, boolean deep) {

        if (needsSyncData()) {
            synchronizeData();
        }
        isReadOnly(readOnly);

    } // setReadOnly(boolean,boolean)

    /**
     * NON-DOM: Returns true if this node is read-only. This is a
     * shallow check.
     * <p>
     *  NON-DOM：如果此节点为只读,则返回true这是一个浅检查
     * 
     */
    public boolean getReadOnly() {

        if (needsSyncData()) {
            synchronizeData();
        }
        return isReadOnly();

    } // getReadOnly():boolean

    /**
     * NON-DOM: As an alternative to subclassing the DOM, this implementation
     * has been extended with the ability to attach an object to each node.
     * (If you need multiple objects, you can attach a collection such as a
     * vector or hashtable, then attach your application information to that.)
     * <p><b>Important Note:</b> You are responsible for removing references
     * to your data on nodes that are no longer used. Failure to do so will
     * prevent the nodes, your data is attached to, to be garbage collected
     * until the whole document is.
     *
     * <p>
     *  NON-DOM：作为子类化DOM的替代方法,这个实现已经扩展,能够将对象附加到每个节点(如果你需要多个对象,你可以附加一个集合,如矢量或哈希表,然后附加你的应用程序信息)<p> <b>重要说明：</b>
     * 您负责删除对不再使用的节点上的数据的引用如果不这样做,将阻止您的数据附加到的节点垃圾收集,直到整个文件。
     * 
     * 
     * @param data the object to store or null to remove any existing reference
     */
    public void setUserData(Object data) {
        ownerDocument().setUserData(this, data);
    }

    /**
     * NON-DOM:
     * Returns the user data associated to this node.
     * <p>
     *  NON-DOM：返回与此节点关联的用户数据
     * 
     */
    public Object getUserData() {
        return ownerDocument().getUserData(this);
    }

    //
    // Protected methods
    //

    /**
     * Denotes that this node has changed.
     * <p>
     *  表示此节点已更改
     * 
     */
    protected void changed() {
        // we do not actually store this information on every node, we only
        // have a global indicator on the Document. Doing otherwise cost us too
        // much for little gain.
        ownerDocument().changed();
    }

    /**
     * Returns the number of changes to this node.
     * <p>
     * 返回此节点的更改数
     * 
     */
    protected int changes() {
        // we do not actually store this information on every node, we only
        // have a global indicator on the Document. Doing otherwise cost us too
        // much for little gain.
        return ownerDocument().changes();
    }

    /**
     * Override this method in subclass to hook in efficient
     * internal data structure.
     * <p>
     *  在子类中覆盖此方法以挂钩有效的内部数据结构
     * 
     */
    protected void synchronizeData() {
        // By default just change the flag to avoid calling this method again
        needsSyncData(false);
    }

    /**
     * For non-child nodes, the node which "points" to this node.
     * For example, the owning element for an attribute
     * <p>
     *  对于非子节点,"指向"此节点的节点例如,属性的拥有元素
     * 
     */
    protected Node getContainer() {
       return null;
    }


    /*
     * Flags setters and getters
     * <p>
     *  标志setters和getters
     */

    final boolean isReadOnly() {
        return (flags & READONLY) != 0;
    }

    final void isReadOnly(boolean value) {
        flags = (short) (value ? flags | READONLY : flags & ~READONLY);
    }

    final boolean needsSyncData() {
        return (flags & SYNCDATA) != 0;
    }

    final void needsSyncData(boolean value) {
        flags = (short) (value ? flags | SYNCDATA : flags & ~SYNCDATA);
    }

    final boolean needsSyncChildren() {
        return (flags & SYNCCHILDREN) != 0;
    }

    public final void needsSyncChildren(boolean value) {
        flags = (short) (value ? flags | SYNCCHILDREN : flags & ~SYNCCHILDREN);
    }

    final boolean isOwned() {
        return (flags & OWNED) != 0;
    }

    final void isOwned(boolean value) {
        flags = (short) (value ? flags | OWNED : flags & ~OWNED);
    }

    final boolean isFirstChild() {
        return (flags & FIRSTCHILD) != 0;
    }

    final void isFirstChild(boolean value) {
        flags = (short) (value ? flags | FIRSTCHILD : flags & ~FIRSTCHILD);
    }

    final boolean isSpecified() {
        return (flags & SPECIFIED) != 0;
    }

    final void isSpecified(boolean value) {
        flags = (short) (value ? flags | SPECIFIED : flags & ~SPECIFIED);
    }

    // inconsistent name to avoid clash with public method on TextImpl
    final boolean internalIsIgnorableWhitespace() {
        return (flags & IGNORABLEWS) != 0;
    }

    final void isIgnorableWhitespace(boolean value) {
        flags = (short) (value ? flags | IGNORABLEWS : flags & ~IGNORABLEWS);
    }

    final boolean hasStringValue() {
        return (flags & HASSTRING) != 0;
    }

    final void hasStringValue(boolean value) {
        flags = (short) (value ? flags | HASSTRING : flags & ~HASSTRING);
    }

    final boolean isNormalized() {
        return (flags & NORMALIZED) != 0;
    }

    final void isNormalized(boolean value) {
        // See if flag should propagate to parent.
        if (!value && isNormalized() && ownerNode != null) {
            ownerNode.isNormalized(false);
        }
        flags = (short) (value ?  flags | NORMALIZED : flags & ~NORMALIZED);
    }

    final boolean isIdAttribute() {
        return (flags & ID) != 0;
    }

    final void isIdAttribute(boolean value) {
        flags = (short) (value ? flags | ID : flags & ~ID);
    }

    //
    // Object methods
    //

    /** NON-DOM method for debugging convenience. */
    public String toString() {
        return "["+getNodeName()+": "+getNodeValue()+"]";
    }

    //
    // Serialization methods
    //

    /** Serialize object. */
    private void writeObject(ObjectOutputStream out) throws IOException {

        // synchronize data
        if (needsSyncData()) {
            synchronizeData();
        }
        // write object
        out.defaultWriteObject();

    } // writeObject(ObjectOutputStream)

} // class NodeImpl
