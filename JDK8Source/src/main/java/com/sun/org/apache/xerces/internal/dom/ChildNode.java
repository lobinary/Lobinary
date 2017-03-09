/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2000-2002,2004 The Apache Software Foundation.
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
 *  版权所有2000-2002,2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.dom;

import org.w3c.dom.Node;

/**
 * ChildNode inherits from NodeImpl and adds the capability of being a child by
 * having references to its previous and next siblings.
 *
 * @xerces.internal
 *
 * <p>
 *  ChildNode继承自NodeImpl,并通过引用其上一个和下一个兄弟姐妹来添加作为子代的能力。
 * 
 *  @ xerces.internal
 * 
 */
public abstract class ChildNode
    extends NodeImpl {

    //
    // Constants
    //

    /** Serialization version. */
    static final long serialVersionUID = -6112455738802414002L;

    transient StringBuffer fBufferStr = null;

    //
    // Data
    //

    /** Previous sibling. */
    protected ChildNode previousSibling;

    /** Next sibling. */
    protected ChildNode nextSibling;

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
     *  每个节点知道它属于什么文档。
     * 
     */
    protected ChildNode(CoreDocumentImpl ownerDocument) {
        super(ownerDocument);
    } // <init>(CoreDocumentImpl)

    /** Constructor for serialization. */
    public ChildNode() {}

    //
    // Node methods
    //

    /**
     * Returns a duplicate of a given node. You can consider this a
     * generic "copy constructor" for nodes. The newly returned object should
     * be completely independent of the source object's subtree, so changes
     * in one after the clone has been made will not affect the other.
     * <P>
     * Note: since we never have any children deep is meaningless here,
     * ParentNode overrides this behavior.
     * <p>
     *  返回给定节点的副本。你可以认为这是一个通用的"复制构造函数"的节点。新返回的对象应该完全独立于源对象的子树,因此在克隆之后的一个更改不会影响另一个。
     * <P>
     *  注意：因为我们从来没有任何孩子deep在这里没有意义,ParentNode重写这个行为。
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

        ChildNode newnode = (ChildNode) super.cloneNode(deep);

        // Need to break the association w/ original kids
        newnode.previousSibling = null;
        newnode.nextSibling     = null;
        newnode.isFirstChild(false);

        return newnode;

    } // cloneNode(boolean):Node

    /**
     * Returns the parent node of this node
     * <p>
     *  返回此节点的父节点
     * 
     */
    public Node getParentNode() {
        // if we have an owner, ownerNode is our parent, otherwise it's
        // our ownerDocument and we don't have a parent
        return isOwned() ? ownerNode : null;
    }

    /*
     * same as above but returns internal type
     * <p>
     *  同上,但返回内部类型
     * 
     */
    final NodeImpl parentNode() {
        // if we have an owner, ownerNode is our parent, otherwise it's
        // our ownerDocument and we don't have a parent
        return isOwned() ? ownerNode : null;
    }

    /** The next child of this node's parent, or null if none */
    public Node getNextSibling() {
        return nextSibling;
    }

    /** The previous child of this node's parent, or null if none */
    public Node getPreviousSibling() {
        // if we are the firstChild, previousSibling actually refers to our
        // parent's lastChild, but we hide that
        return isFirstChild() ? null : previousSibling;
    }

    /*
     * same as above but returns internal type
     * <p>
     * 与上面相同但返回内部类型
     */
    final ChildNode previousSibling() {
        // if we are the firstChild, previousSibling actually refers to our
        // parent's lastChild, but we hide that
        return isFirstChild() ? null : previousSibling;
    }

} // class ChildNode
