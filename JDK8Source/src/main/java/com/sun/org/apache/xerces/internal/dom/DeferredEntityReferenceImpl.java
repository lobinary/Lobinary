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
 * EntityReference models the XML &entityname; syntax, when used for
 * entities defined by the DOM. Entities hardcoded into XML, such as
 * character entities, should instead have been translated into text
 * by the code which generated the DOM tree.
 * <P>
 * An XML processor has the alternative of fully expanding Entities
 * into the normal document tree. If it does so, no EntityReference nodes
 * will appear.
 * <P>
 * Similarly, non-validating XML processors are not required to read
 * or process entity declarations made in the external subset or
 * declared in external parameter entities. Hence, some applications
 * may not make the replacement value available for Parsed Entities
 * of these types.
 * <P>
 * EntityReference behaves as a read-only node, and the children of
 * the EntityReference (which reflect those of the Entity, and should
 * also be read-only) give its replacement value, if any. They are
 * supposed to automagically stay in synch if the DocumentType is
 * updated with new values for the Entity.
 * <P>
 * The defined behavior makes efficient storage difficult for the DOM
 * implementor. We can't just look aside to the Entity's definition
 * in the DocumentType since those nodes have the wrong parent (unless
 * we can come up with a clever "imaginary parent" mechanism). We
 * must at least appear to clone those children... which raises the
 * issue of keeping the reference synchronized with its parent.
 * This leads me back to the "cached image of centrally defined data"
 * solution, much as I dislike it.
 * <P>
 * For now I have decided, since REC-DOM-Level-1-19980818 doesn't
 * cover this in much detail, that synchronization doesn't have to be
 * considered while the user is deep in the tree. That is, if you're
 * looking within one of the EntityReferennce's children and the Entity
 * changes, you won't be informed; instead, you will continue to access
 * the same object -- which may or may not still be part of the tree.
 * This is the same behavior that obtains elsewhere in the DOM if the
 * subtree you're looking at is deleted from its parent, so it's
 * acceptable here. (If it really bothers folks, we could set things
 * up so deleted subtrees are walked and marked invalid, but that's
 * not part of the DOM's defined behavior.)
 * <P>
 * As a result, only the EntityReference itself has to be aware of
 * changes in the Entity. And it can take advantage of the same
 * structure-change-monitoring code I implemented to support
 * DeepNodeList.
 *
 * @xerces.internal
 *
 * <p>
 *  EntityReference建模XML&entityname;语法,当用于由DOM定义的实体时。硬编码到XML中的实体(例如字符实体)应该由生成DOM树的代码翻译为文本。
 * <P>
 *  XML处理器具有将实体完全扩展到正常文档树中的替代。如果这样做,则不会出现EntityReference节点。
 * <P>
 *  类似地,不需要非验证XML处理器来读取或处理在外部子集中做出的或在外部参数实体中声明的实体声明。因此,一些应用可能不会使替换值可用于这些类型的解析实体。
 * <P>
 * EntityReference行为作为只读节点,EntityReference的子节点(它们反映实体的子节点,并且也应该是只读的)给出它的替换值(如果有的话)。
 * 如果DocumentType使用实体的新值更新,它们应该自动保持同步。
 * <P>
 *  定义的行为使得DOM实现者难以进行高效存储。我们不能只看看Entity在DocumentType中的定义,因为那些节点有错误的父类(除非我们能够想出一个聪明的"想象的父"机制)。
 * 我们必须至少似乎克隆这些孩子...这引起了保持引用与其父母同步的问题。这导致我回到"集中定义数据的缓存映像"解决方案,就像我不喜欢它。
 * <P>
 *  现在我决定,因为REC-DOM-Level-1-19980818没有详细地涵盖这一点,当用户在树中深处时,不必考虑同步。
 * 也就是说,如果你在一个EntityReferennce的孩子和Entity的改变,你不会被告知;相反,你将继续访问相同的对象 - 这可能或不会仍然是树的一部分。
 * 
 * @since  PR-DOM-Level-1-19980818.
 */
public class DeferredEntityReferenceImpl
    extends EntityReferenceImpl
    implements DeferredNode {

    //
    // Constants
    //

    /** Serialization version. */
    static final long serialVersionUID = 390319091370032223L;

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
     * 如果您正在查看的子树从其父级中删除,这是在DOM中其他位置获得的相同行为,因此这里可以接受。
     *  (如果它真的打扰了人,我们可以设置的东西,所以删除的子树被走路和标记为无效,但这不是DOM的定义行为的一部分。)。
     * <P>
     * 因此,只有EntityReference本身必须知道实体中的更改。它可以利用我实现的相同的结构变化监视代码来支持DeepNodeList。
     * 
     *  @ xerces.internal
     * 
     */
    DeferredEntityReferenceImpl(DeferredDocumentImpl ownerDocument,
                                int nodeIndex) {
        super(ownerDocument, null);

        fNodeIndex = nodeIndex;
        needsSyncData(true);

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
     */
    protected void synchronizeData() {

        // no need to sychronize again
        needsSyncData(false);

        // get the node data
        DeferredDocumentImpl ownerDocument =
            (DeferredDocumentImpl)this.ownerDocument;
        name = ownerDocument.getNodeName(fNodeIndex);
        baseURI = ownerDocument.getNodeValue(fNodeIndex);

    } // synchronizeData()

    /** Synchronize the children. */
    protected void synchronizeChildren() {

        // no need to synchronize again
        needsSyncChildren(false);

        // get children
        isReadOnly(false);
        DeferredDocumentImpl ownerDocument =
            (DeferredDocumentImpl) ownerDocument();
        ownerDocument.synchronizeChildren(this, fNodeIndex);
        setReadOnly(true, true);

    } // synchronizeChildren()

} // class DeferredEntityReferenceImpl
