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

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * NON-DOM CLASS: Describe one of the Elements (and its associated
 * Attributes) defined in this Document Type.
 * <p>
 * I've included this in Level 1 purely as an anchor point for default
 * attributes. In Level 2 it should enable the ChildRule support.
 *
 * @xerces.internal
 *
 * <p>
 *  非DOM类：描述在此文档类型中定义的一个元素(及其关联的属性)。
 * <p>
 *  我把它包含在等级1中作为默认属性的锚点。在级别2它应该启用ChildRule支持。
 * 
 *  @ xerces.internal
 * 
 */
public class ElementDefinitionImpl
    extends ParentNode {

    //
    // Constants
    //

    /** Serialization version. */
    static final long serialVersionUID = -8373890672670022714L;

    //
    // Data
    //

    /** Element definition name. */
    protected String name;

    /** Default attributes. */
    protected NamedNodeMapImpl attributes;

    //
    // Constructors
    //

    /** Factory constructor. */
    public ElementDefinitionImpl(CoreDocumentImpl ownerDocument, String name) {
        super(ownerDocument);
        this.name = name;
        attributes = new NamedNodeMapImpl(ownerDocument);
    }

    //
    // Node methods
    //

    /**
     * A short integer indicating what type of node this is. The named
     * constants for this value are defined in the org.w3c.dom.Node interface.
     * <p>
     *  指示这是什么类型的节点的短整数。此值的命名常量在org.w3c.dom.Node接口中定义。
     * 
     */
    public short getNodeType() {
        return NodeImpl.ELEMENT_DEFINITION_NODE;
    }

    /**
     * Returns the element definition name
     * <p>
     *  返回元素定义名称
     * 
     */
    public String getNodeName() {
        if (needsSyncData()) {
            synchronizeData();
        }
        return name;
    }

    /**
     * Replicate this object.
     * <p>
     *  复制此对象。
     * 
     */
    public Node cloneNode(boolean deep) {

        ElementDefinitionImpl newnode =
            (ElementDefinitionImpl) super.cloneNode(deep);
        // NamedNodeMap must be explicitly replicated to avoid sharing
        newnode.attributes = attributes.cloneMap(newnode);
        return newnode;

    } // cloneNode(boolean):Node

    /**
     * Query the attributes defined on this Element.
     * <p>
     * In the base implementation this Map simply contains Attribute objects
     * representing the defaults. In a more serious implementation, it would
     * contain AttributeDefinitionImpl objects for all declared Attributes,
     * indicating which are Default, DefaultFixed, Implicit and/or Required.
     *
     * <p>
     *  查询在此元素上定义的属性。
     * <p>
     * 在基本实现中,这个Map只包含表示默认值的Attribute对象。
     * 
     * @return org.w3c.dom.NamedNodeMap containing org.w3c.dom.Attribute
     */
    public NamedNodeMap getAttributes() {

        if (needsSyncChildren()) {
            synchronizeChildren();
        }
        return attributes;

    } // getAttributes():NamedNodeMap

} // class ElementDefinitionImpl
