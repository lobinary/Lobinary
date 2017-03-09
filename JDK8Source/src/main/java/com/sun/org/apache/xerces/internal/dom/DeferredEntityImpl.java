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


/**
 * Entity nodes hold the reference data for an XML Entity -- either
 * parsed or unparsed. The nodeName (inherited from Node) will contain
 * the name (if any) of the Entity. Its data will be contained in the
 * Entity's children, in exactly the structure which an
 * EntityReference to this name will present within the document's
 * body.
 * <P>
 * Note that this object models the actual entity, _not_ the entity
 * declaration or the entity reference.
 * <P>
 * An XML processor may choose to completely expand entities before
 * the structure model is passed to the DOM; in this case, there will
 * be no EntityReferences in the DOM tree.
 * <P>
 * Quoting the 10/01 DOM Proposal,
 * <BLOCKQUOTE>
 * "The DOM Level 1 does not support editing Entity nodes; if a user
 * wants to make changes to the contents of an Entity, every related
 * EntityReference node has to be replaced in the structure model by
 * a clone of the Entity's contents, and then the desired changes
 * must be made to each of those clones instead. All the
 * descendants of an Entity node are readonly."
 * </BLOCKQUOTE>
 * I'm interpreting this as: It is the parser's responsibilty to call
 * the non-DOM operation setReadOnly(true,true) after it constructs
 * the Entity. Since the DOM explicitly decided not to deal with this,
 * _any_ answer will involve a non-DOM operation, and this is the
 * simplest solution.
 *
 * @xerces.internal
 *
 * <p>
 *  实体节点保存XML实体的参考数据 - 已解析或未解析。 nodeName(继承自Node)将包含实体的名称(如果有)。
 * 它的数据将包含在Entity的孩子中,在这个结构中,这个名字的EntityReference将出现在文档的正文中。
 * <P>
 *  请注意,此对象建模实际实体,_not_实体声明或实体引用。
 * <P>
 *  XML处理器可以选择在将结构模型传递给DOM之前完全展开实体;在这种情况下,在DOM树中将没有EntityReferences。
 * <P>
 *  引用10/01 DOM提案,
 * <BLOCKQUOTE>
 * "DOM级别1不支持编辑实体节点;如果用户想要更改实体的内容,则必须在结构模型中将每个相关的EntityReference节点替换为实体内容的克隆,然后将所需的必须对每个克隆进行更改。
 * 实体节点的所有后代都是只读的。
 * </BLOCKQUOTE>
 * 
 * @since  PR-DOM-Level-1-19980818.
 */
public class DeferredEntityImpl
    extends EntityImpl
    implements DeferredNode {

    //
    // Constants
    //

    /** Serialization version. */
    static final long serialVersionUID = 4760180431078941638L;

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
     *  我将其解释为：解析器的责任是在它构造实体之后调用非DOM操作setReadOnly(true,true)。
     * 由于DOM明确决定不处理这一点,_any_ answer将涉及非DOM操作,这是最简单的解决方案。
     * 
     *  @ xerces.internal
     * 
     */
    DeferredEntityImpl(DeferredDocumentImpl ownerDocument, int nodeIndex) {
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

    /**
     * Synchronize the entity data. This is special because of the way
     * that the "fast" version stores the information.
     * <p>
     *  这是延迟构造函数。这里只给出fNodeIndex。所有其他数据,可以通过索引从ownerDocument请求。
     * 
     */
    protected void synchronizeData() {

        // no need to sychronize again
        needsSyncData(false);

        // get the node data
        DeferredDocumentImpl ownerDocument =
            (DeferredDocumentImpl)this.ownerDocument;
        name = ownerDocument.getNodeName(fNodeIndex);

        // get the entity data
        publicId    = ownerDocument.getNodeValue(fNodeIndex);
        systemId    = ownerDocument.getNodeURI(fNodeIndex);
        int extraDataIndex = ownerDocument.getNodeExtra(fNodeIndex);
        ownerDocument.getNodeType(extraDataIndex);

        notationName = ownerDocument.getNodeName(extraDataIndex);

        // encoding and version DOM L3
        version     = ownerDocument.getNodeValue(extraDataIndex);
        encoding    = ownerDocument.getNodeURI(extraDataIndex);

        // baseURI, actualEncoding DOM L3
        int extraIndex2 = ownerDocument.getNodeExtra(extraDataIndex);
        baseURI = ownerDocument.getNodeName(extraIndex2);
        inputEncoding = ownerDocument.getNodeValue(extraIndex2);

    } // synchronizeData()

    /** Synchronize the children. */
    protected void synchronizeChildren() {

        // no need to synchronize again
        needsSyncChildren(false);

        isReadOnly(false);
        DeferredDocumentImpl ownerDocument =
            (DeferredDocumentImpl) ownerDocument();
        ownerDocument.synchronizeChildren(this, fNodeIndex);
        setReadOnly(true, true);

    } // synchronizeChildren()

} // class DeferredEntityImpl
