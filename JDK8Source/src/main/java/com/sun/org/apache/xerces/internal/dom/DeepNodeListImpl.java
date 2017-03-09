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
 *  版权所有1999-2002,2004 Apache软件基金会。
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
import org.w3c.dom.NodeList;

import java.util.Vector;

/**
 * This class implements the DOM's NodeList behavior for
 * Element.getElementsByTagName()
 * <P>
 * The DOM describes NodeList as follows:
 * <P>
 * 1) It may represent EITHER nodes scattered through a subtree (when
 * returned by Element.getElementsByTagName), or just the immediate
 * children (when returned by Node.getChildNodes). The latter is easy,
 * but the former (which this class addresses) is more challenging.
 * <P>
 * 2) Its behavior is "live" -- that is, it always reflects the
 * current state of the document tree. To put it another way, the
 * NodeLists obtained before and after a series of insertions and
 * deletions are effectively identical (as far as the user is
 * concerned, the former has been dynamically updated as the changes
 * have been made).
 * <P>
 * 3) Its API accesses individual nodes via an integer index, with the
 * listed nodes numbered sequentially in the order that they were
 * found during a preorder depth-first left-to-right search of the tree.
 * (Of course in the case of getChildNodes, depth is not involved.) As
 * nodes are inserted or deleted in the tree, and hence the NodeList,
 * the numbering of nodes that follow them in the NodeList will
 * change.
 * <P>
 * It is rather painful to support the latter two in the
 * getElementsByTagName case. The current solution is for Nodes to
 * maintain a change count (eventually that may be a Digest instead),
 * which the NodeList tracks and uses to invalidate itself.
 * <P>
 * Unfortunately, this does _not_ respond efficiently in the case that
 * the dynamic behavior was supposed to address: scanning a tree while
 * it is being extended. That requires knowing which subtrees have
 * changed, which can become an arbitrarily complex problem.
 * <P>
 * We save some work by filling the vector only as we access the
 * item()s... but I suspect the same users who demanded index-based
 * access will also start by doing a getLength() to control their loop,
 * blowing this optimization out of the water.
 * <P>
 * NOTE: Level 2 of the DOM will probably _not_ use NodeList for its
 * extended search mechanisms, partly for the reasons just discussed.
 *
 * @xerces.internal
 *
 * <p>
 *  这个类实现了DOM的NodeList行为Element.getElementsByTagName()
 * <P>
 *  DOM描述NodeList如下：
 * <P>
 *  1)它可以表示散布在子树(当由Element.getElementsByTagName返回时)的EITHER节点,或者只是直接子节点(当由Node.getChildNodes返回时)。
 * 后者是容易的,但前者(这个类解决)是更具挑战性。
 * <P>
 *  2)它的行为是"活的" - 也就是说,它总是反映文档树的当前状态。换句话说,在一系列插入和删除之前和之后获得的NodeLists实际上是相同的(就用户而言,前者已经作出改变而被动态更新)。
 * <P>
 * 3)其API通过整数索引访问单个节点,其中列出的节点按照它们在树的预订深度优先从左到右搜索期间被找到的顺序按顺序编号。 (当然在getChildNodes的情况下,不涉及深度)。
 * 由于节点在树中被插入或删除,因此节点列表,在节点列表中跟随它们的节点的编号将改变。
 * <P>
 *  在getElementsByTagName的情况下支持后两个是相当痛苦的。当前的解决方案是节点维护一个更改计数(最终可能是一个摘要),NodeList跟踪并使用它使自己无效。
 * 
 * @since  PR-DOM-Level-1-19980818.
 */
public class DeepNodeListImpl
    implements NodeList {

    //
    // Data
    //

    protected NodeImpl rootNode; // Where the search started
    protected String tagName;   // Or "*" to mean all-tags-acceptable
    protected int changes=0;
    protected Vector nodes;

    protected String nsName;
    protected boolean enableNS = false;

    //
    // Constructors
    //

    /** Constructor. */
    public DeepNodeListImpl(NodeImpl rootNode, String tagName) {
        this.rootNode = rootNode;
        this.tagName  = tagName;
        nodes = new Vector();
    }

    /** Constructor for Namespace support. */
    public DeepNodeListImpl(NodeImpl rootNode,
                            String nsName, String tagName) {
        this(rootNode, tagName);
        this.nsName = (nsName != null && !nsName.equals("")) ? nsName : null;
        enableNS = true;
    }

    //
    // NodeList methods
    //

    /** Returns the length of the node list. */
    public int getLength() {
        // Preload all matching elements. (Stops when we run out of subtree!)
        item(java.lang.Integer.MAX_VALUE);
        return nodes.size();
    }

    /** Returns the node at the specified index. */
    public Node item(int index) {
        Node thisNode;

        // Tree changed. Do it all from scratch!
        if(rootNode.changes() != changes) {
            nodes   = new Vector();
            changes = rootNode.changes();
        }

        // In the cache
        if (index < nodes.size())
            return (Node)nodes.elementAt(index);

        // Not yet seen
        else {

            // Pick up where we left off (Which may be the beginning)
                if (nodes.size() == 0)
                    thisNode = rootNode;
                else
                    thisNode=(NodeImpl)(nodes.lastElement());

                // Add nodes up to the one we're looking for
                while(thisNode != null && index >= nodes.size()) {
                        thisNode=nextMatchingElementAfter(thisNode);
                        if (thisNode != null)
                            nodes.addElement(thisNode);
                    }

            // Either what we want, or null (not avail.)
                    return thisNode;
            }

    } // item(int):Node

    //
    // Protected methods (might be overridden by an extending DOM)
    //

    /**
     * Iterative tree-walker. When you have a Parent link, there's often no
     * need to resort to recursion. NOTE THAT only Element nodes are matched
     * since we're specifically supporting getElementsByTagName().
     * <p>
     * <P>
     *  不幸的是,这不会在动态行为应该解决的情况下有效地响应：在扩展时扫描树。这需要知道哪些子树已经改变,这可能成为一个任意复杂的问题。
     * <P>
     *  我们保存一些工作通过填充矢量只有当我们访问的item()s ...但我怀疑同样的用户要求基于索引访问也将开始通过做一个getLength()来控制他们的循环,吹出这个优化的水。
     * <P>
     *  注意：DOM的第2级可能不会使用NodeList作为其扩展搜索机制,部分原因是刚才讨论的原因。
     */
    protected Node nextMatchingElementAfter(Node current) {

            Node next;
            while (current != null) {
                    // Look down to first child.
                    if (current.hasChildNodes()) {
                            current = (current.getFirstChild());
                    }

                    // Look right to sibling (but not from root!)
                    else if (current != rootNode && null != (next = current.getNextSibling())) {
                                current = next;
                        }

                        // Look up and right (but not past root!)
                        else {
                                next = null;
                                for (; current != rootNode; // Stop when we return to starting point
                                        current = current.getParentNode()) {

                                        next = current.getNextSibling();
                                        if (next != null)
                                                break;
                                }
                                current = next;
                        }

                        // Have we found an Element with the right tagName?
                        // ("*" matches anything.)
                    if (current != rootNode
                        && current != null
                        && current.getNodeType() ==  Node.ELEMENT_NODE) {
                        if (!enableNS) {
                            if (tagName.equals("*") ||
                                ((ElementImpl) current).getTagName().equals(tagName))
                            {
                                return current;
                            }
                        } else {
                            // DOM2: Namespace logic.
                            if (tagName.equals("*")) {
                                if (nsName != null && nsName.equals("*")) {
                                    return current;
                                } else {
                                    ElementImpl el = (ElementImpl) current;
                                    if ((nsName == null
                                         && el.getNamespaceURI() == null)
                                        || (nsName != null
                                            && nsName.equals(el.getNamespaceURI())))
                                    {
                                        return current;
                                    }
                                }
                            } else {
                                ElementImpl el = (ElementImpl) current;
                                if (el.getLocalName() != null
                                    && el.getLocalName().equals(tagName)) {
                                    if (nsName != null && nsName.equals("*")) {
                                        return current;
                                    } else {
                                        if ((nsName == null
                                             && el.getNamespaceURI() == null)
                                            || (nsName != null &&
                                                nsName.equals(el.getNamespaceURI())))
                                        {
                                            return current;
                                        }
                                    }
                                }
                            }
                        }
                    }

                // Otherwise continue walking the tree
            }

            // Fell out of tree-walk; no more instances found
            return null;

    } // nextMatchingElementAfter(int):Node

} // class DeepNodeListImpl
