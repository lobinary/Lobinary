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

/**
 * This class represents a Document Type <em>declaraction</em> in
 * the document itself, <em>not</em> a Document Type Definition (DTD).
 * An XML document may (or may not) have such a reference.
 * <P>
 * DocumentType is an Extended DOM feature, used in XML documents but
 * not in HTML.
 * <P>
 * Note that Entities and Notations are no longer children of the
 * DocumentType, but are parentless nodes hung only in their
 * appropriate NamedNodeMaps.
 * <P>
 * This area is UNDERSPECIFIED IN REC-DOM-Level-1-19981001
 * Most notably, absolutely no provision was made for storing
 * and using Element and Attribute information. Nor was the linkage
 * between Entities and Entity References nailed down solidly.
 *
 * @xerces.internal
 *
 * <p>
 *  此类在文档本身中表示文档类型<em>声明</em>,<em>不是</em>文档类型定义(DTD)。 XML文档可以(或可能不)具有这样的引用。
 * <P>
 *  DocumentType是一种扩展DOM功能,用于XML文档,但不能用于HTML。
 * <P>
 *  请注意,实体和符号不再是DocumentType的子级,而是无父节点仅挂在其相应的NamedNodeMaps中。
 * <P>
 *  这个区域在REC-DOM-Level-1-19981001中是未知的。最值得注意的是,绝对没有为存储和使用元素和属性信息做出规定。实体和实体参考之间的联系也没有牢固地固定下来。
 * 
 * @since  PR-DOM-Level-1-19980818.
 */
public class DeferredDocumentTypeImpl
    extends DocumentTypeImpl
    implements DeferredNode {

    //
    // Constants
    //

    /** Serialization version. */
    static final long serialVersionUID = -2172579663227313509L;

    //
    // Data
    //

    /** Node index. */
    protected transient int fNodeIndex;

    //
    // Constructors
    //

    /**
     * This is the deferred constructor. Only the fNodeIndex is given here.
     * All other data, can be requested from the ownerDocument via the index.
     * <p>
     * 
     *  @ xerces.internal
     * 
     */
    DeferredDocumentTypeImpl(DeferredDocumentImpl ownerDocument, int nodeIndex) {
        super(ownerDocument, null);

        fNodeIndex = nodeIndex;
        needsSyncData(true);
        needsSyncChildren(true);

    } // <init>(DeferredDocumentImpl,int)

    //
    // DeferredNode methods
    //

    /** Returns the node index. */
    public int getNodeIndex() {
        return fNodeIndex;
    }

    //
    // Protected methods
    //

    /** Synchronizes the data (name and value) for fast nodes. */
    protected void synchronizeData() {

        // no need to sync in the future
        needsSyncData(false);

        // fluff data
        DeferredDocumentImpl ownerDocument =
            (DeferredDocumentImpl)this.ownerDocument;
        name = ownerDocument.getNodeName(fNodeIndex);

        // public and system ids
        publicID = ownerDocument.getNodeValue(fNodeIndex);
        systemID = ownerDocument.getNodeURI(fNodeIndex);
        int extraDataIndex = ownerDocument.getNodeExtra(fNodeIndex);
        internalSubset = ownerDocument.getNodeValue(extraDataIndex);
    } // synchronizeData()

    /** Synchronizes the entities, notations, and elements. */
    protected void synchronizeChildren() {

        // we don't want to generate any event for this so turn them off
        boolean orig = ownerDocument().getMutationEvents();
        ownerDocument().setMutationEvents(false);

        // no need to synchronize again
        needsSyncChildren(false);

        // create new node maps
        DeferredDocumentImpl ownerDocument =
            (DeferredDocumentImpl)this.ownerDocument;

        entities  = new NamedNodeMapImpl(this);
        notations = new NamedNodeMapImpl(this);
        elements  = new NamedNodeMapImpl(this);

        // fill node maps
        DeferredNode last = null;
        for (int index = ownerDocument.getLastChild(fNodeIndex);
            index != -1;
            index = ownerDocument.getPrevSibling(index)) {

            DeferredNode node = ownerDocument.getNodeObject(index);
            int type = node.getNodeType();
            switch (type) {

                // internal, external, and unparsed entities
                case Node.ENTITY_NODE: {
                    entities.setNamedItem(node);
                    break;
                }

                // notations
                case Node.NOTATION_NODE: {
                    notations.setNamedItem(node);
                    break;
                }

                // element definitions
                case NodeImpl.ELEMENT_DEFINITION_NODE: {
                    elements.setNamedItem(node);
                    break;
                }

                // elements
                case Node.ELEMENT_NODE: {
                    if (((DocumentImpl)getOwnerDocument()).allowGrammarAccess){
                        insertBefore(node, last);
                        last = node;
                        break;
                    }
                }

                // NOTE: Should never get here! -Ac
                default: {
                    System.out.println("DeferredDocumentTypeImpl" +
                                       "#synchronizeInfo: " +
                                       "node.getNodeType() = " +
                                       node.getNodeType() +
                                       ", class = " +
                                       node.getClass().getName());
                }
             }
        }

        // set mutation events flag back to its original value
        ownerDocument().setMutationEvents(orig);

        // set entities and notations read_only per DOM spec
        setReadOnly(true, false);

    } // synchronizeChildren()

} // class DeferredDocumentTypeImpl
