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

/*
 * WARNING: because java doesn't support multi-inheritance some code is
 * duplicated. If you're changing this file you probably want to change
 * DeferredAttrNSImpl.java at the same time.
 * <p>
 *  警告：因为java不支持多继承,一些代码是重复的。如果你改变这个文件,你可能想改变DeferredAttrNSImpl.java在同一时间。
 * 
 */

package com.sun.org.apache.xerces.internal.dom;

/**
 * Attribute represents an XML-style attribute of an
 * Element. Typically, the allowable values are controlled by its
 * declaration in the Document Type Definition (DTD) governing this
 * kind of document.
 * <P>
 * If the attribute has not been explicitly assigned a value, but has
 * been declared in the DTD, it will exist and have that default. Only
 * if neither the document nor the DTD specifies a value will the
 * Attribute really be considered absent and have no value; in that
 * case, querying the attribute will return null.
 * <P>
 * Attributes may have multiple children that contain their data. (XML
 * allows attributes to contain entity references, and tokenized
 * attribute types such as NMTOKENS may have a child for each token.)
 * For convenience, the Attribute object's getValue() method returns
 * the string version of the attribute's value.
 * <P>
 * Attributes are not children of the Elements they belong to, in the
 * usual sense, and have no valid Parent reference. However, the spec
 * says they _do_ belong to a specific Element, and an INUSE exception
 * is to be thrown if the user attempts to explicitly share them
 * between elements.
 * <P>
 * Note that Elements do not permit attributes to appear to be shared
 * (see the INUSE exception), so this object's mutability is
 * officially not an issue.
 * <P>
 * DeferredAttrImpl inherits from AttrImpl which does not support
 * Namespaces. DeferredAttrNSImpl, which inherits from AttrNSImpl, does.
 * <p>
 *  属性表示元素的XML样式属性。通常,允许值由其在管理此类文档的文档类型定义(DTD)中的声明控​​制。
 * <P>
 *  如果属性没有显式分配一个值,但已在DTD中声明,它将存在并具有该缺省值。只有当文档和DTD都没有指定一个值时,属性才会被认为是空的并且没有值;在这种情况下,查询属性将返回null。
 * <P>
 * 属性可能有多个包含其数据的子级。 (XML允许属性包含实体引用,并且标记化的属性类型,如NMTOKENS可能为每个标记有一个子类。
 * )为方便起见,Attribute对象的getValue()方法返回属性值的字符串版本。
 * <P>
 *  属性不是它们属于的元素的子元素,通常意义上,并没有有效的父引用。但是,规范说他们_do_属于一个特定的元素,如果用户尝试在元素之间显式共享它们,则抛出INUSE异常。
 * <P>
 *  请注意,元素不允许属性显示为共享(参见INUSE异常),因此该对象的可变性在官方上不是问题。
 * 
 * @see DeferredAttrNSImpl
 *
 * @xerces.internal
 *
 * @author Andy Clark, IBM
 * @author Arnaud  Le Hors, IBM
 * @since  PR-DOM-Level-1-19980818.
 */
public final class DeferredAttrImpl
    extends AttrImpl
    implements DeferredNode {

    //
    // Constants
    //

    /** Serialization version. */
    static final long serialVersionUID = 6903232312469148636L;

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
     * <P>
     *  DeferredAttrImpl从不支持命名空间的AttrImpl继承。 DeferredAttrNSImpl,它继承自AttrNSImpl。
     * 
     */
    DeferredAttrImpl(DeferredDocumentImpl ownerDocument, int nodeIndex) {
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
            (DeferredDocumentImpl) ownerDocument();
        name = ownerDocument.getNodeName(fNodeIndex);
        int extra = ownerDocument.getNodeExtra(fNodeIndex);
        isSpecified((extra & SPECIFIED) != 0);
        isIdAttribute((extra & ID) != 0);

        int extraNode = ownerDocument.getLastChild(fNodeIndex);
        type = ownerDocument.getTypeInfo(extraNode);
    } // synchronizeData()

    /**
     * Synchronizes the node's children with the internal structure.
     * Fluffing the children at once solves a lot of work to keep
     * the two structures in sync. The problem gets worse when
     * editing the tree -- this makes it a lot easier.
     * <p>
     *  这是延迟构造函数。这里只给出fNodeIndex。所有其他数据,可以通过索引从ownerDocument请求。
     * 
     */
    protected void synchronizeChildren() {
        DeferredDocumentImpl ownerDocument =
            (DeferredDocumentImpl) ownerDocument();
        ownerDocument.synchronizeChildren(this, fNodeIndex);
    } // synchronizeChildren()

} // class DeferredAttrImpl
