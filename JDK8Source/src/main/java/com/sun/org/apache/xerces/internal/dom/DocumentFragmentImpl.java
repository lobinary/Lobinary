/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 1999-2004 The Apache Software Foundation.
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
 *  版权所有1999-2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.dom;

import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

/**
 * DocumentFragment is a "lightweight" or "minimal" Document
 * object. It is very common to want to be able to extract a portion
 * of a document's tree or to create a new fragment of a
 * document. Imagine implementing a user command like cut or
 * rearranging a document by moving fragments around. It is desirable
 * to have an object which can hold such fragments and it is quite
 * natural to use a Node for this purpose. While it is true that a
 * Document object could fulfil this role, a Document object can
 * potentially be a heavyweight object, depending on the underlying
 * implementation... and in DOM Level 1, nodes aren't allowed to cross
 * Document boundaries anyway. What is really needed for this is a
 * very lightweight object.  DocumentFragment is such an object.
 * <P>
 * Furthermore, various operations -- such as inserting nodes as
 * children of another Node -- may take DocumentFragment objects as
 * arguments; this results in all the child nodes of the
 * DocumentFragment being moved to the child list of this node.
 * <P>
 * The children of a DocumentFragment node are zero or more nodes
 * representing the tops of any sub-trees defining the structure of
 * the document.  DocumentFragment do not need to be well-formed XML
 * documents (although they do need to follow the rules imposed upon
 * well-formed XML parsed entities, which can have multiple top
 * nodes). For example, a DocumentFragment might have only one child
 * and that child node could be a Text node. Such a structure model
 * represents neither an HTML document nor a well-formed XML document.
 * <P>
 * When a DocumentFragment is inserted into a Document (or indeed any
 * other Node that may take children) the children of the
 * DocumentFragment and not the DocumentFragment itself are inserted
 * into the Node. This makes the DocumentFragment very useful when the
 * user wishes to create nodes that are siblings; the DocumentFragment
 * acts as the parent of these nodes so that the user can use the
 * standard methods from the Node interface, such as insertBefore()
 * and appendChild().
 *
 * @xerces.internal
 *
 * <p>
 *  DocumentFragment是一个"轻量级"或"最小"的Document对象。这是很常见的想要能够提取文档的树的一部分或创建文档的新片段。
 * 想象一下,通过移动碎片来实现像剪切或重新排列文档的用户命令。期望具有可以保存这样的片段的对象,并且为此目的使用节点是非常自然的。
 * 尽管Document对象可以满足这个角色,但是Document对象可能是一个重量级的对象,这取决于底层实现...在DOM级别1中,节点不允许跨越文档边界。这真正需要的是一个非常轻量级的对象。
 *  DocumentFragment是这样一个对象。
 * <P>
 * 此外,各种操作(例如,将节点作为另一个节点的子节点插入)可以将DocumentFragment对象作为参数;这会导致将DocumentFragment的所有子节点移动到此节点的子节点列表。
 * <P>
 *  DocumentFragment节点的子节点是零个或多个节点,代表定义文档结构的任何子树的顶部。
 *  DocumentFragment不需要是格式良好的XML文档(尽管他们需要遵循强加在格式良好的XML解析实体上的规则,这些实体可以有多个顶级节点)。
 * 例如,一个DocumentFragment可能只有一个子节点,该子节点可能是一个Text节点。这样的结构模型既不表示HTML文档也不表示格式良好的XML文档。
 * <P>
 *  当将DocumentFragment插入到Document(或任何其他可能接收子节点的节点)时,DocumentFragment的子项而不是DocumentFragment本身将插入到节点中。
 * 
 * @since  PR-DOM-Level-1-19980818.
 */
public class DocumentFragmentImpl
    extends ParentNode
    implements DocumentFragment {

    //
    // Constants
    //

    /** Serialization version. */
    static final long serialVersionUID = -7596449967279236746L;

    //
    // Constructors
    //

    /** Factory constructor. */
    public DocumentFragmentImpl(CoreDocumentImpl ownerDoc) {
        super(ownerDoc);
    }

    /** Constructor for serialization. */
    public DocumentFragmentImpl() {}

    //
    // Node methods
    //

    /**
     * A short integer indicating what type of node this is. The named
     * constants for this value are defined in the org.w3c.dom.Node interface.
     * <p>
     * 这使得DocumentFragment在用户希望创建兄弟节点时非常有用; DocumentFragment充当这些节点的父级,以便用户可以使用Node接口中的标准方法,例如insertBefore()和
     * appendChild()。
     *  当将DocumentFragment插入到Document(或任何其他可能接收子节点的节点)时,DocumentFragment的子项而不是DocumentFragment本身将插入到节点中。
     * 
     *  @ xerces.internal
     * 
     */
    public short getNodeType() {
        return Node.DOCUMENT_FRAGMENT_NODE;
    }

    /** Returns the node name. */
    public String getNodeName() {
        return "#document-fragment";
    }

    /**
     * Override default behavior to call normalize() on this Node's
     * children. It is up to implementors or Node to override normalize()
     * to take action.
     * <p>
     */
    public void normalize() {
        // No need to normalize if already normalized.
        if (isNormalized()) {
            return;
        }
        if (needsSyncChildren()) {
            synchronizeChildren();
        }
        ChildNode kid, next;

        for (kid = firstChild; kid != null; kid = next) {
            next = kid.nextSibling;

            // If kid is a text node, we need to check for one of two
            // conditions:
            //   1) There is an adjacent text node
            //   2) There is no adjacent text node, but kid is
            //      an empty text node.
            if ( kid.getNodeType() == Node.TEXT_NODE )
            {
                // If an adjacent text node, merge it with kid
                if ( next!=null && next.getNodeType() == Node.TEXT_NODE )
                {
                    ((Text)kid).appendData(next.getNodeValue());
                    removeChild( next );
                    next = kid; // Don't advance; there might be another.
                }
                else
                {
                    // If kid is empty, remove it
                    if ( kid.getNodeValue() == null || kid.getNodeValue().length() == 0 ) {
                        removeChild( kid );
                    }
                }
            }

            kid.normalize();
        }

        isNormalized(true);
    }

} // class DocumentFragmentImpl
