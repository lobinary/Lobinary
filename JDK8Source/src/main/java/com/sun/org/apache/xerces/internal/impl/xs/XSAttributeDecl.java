/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2001-2004 The Apache Software Foundation.
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
 *  版权所有2001-2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.impl.xs;

import com.sun.org.apache.xerces.internal.impl.dv.ValidatedInfo;
import com.sun.org.apache.xerces.internal.impl.dv.XSSimpleType;
import com.sun.org.apache.xerces.internal.impl.xs.util.XSObjectListImpl;
import com.sun.org.apache.xerces.internal.xni.QName;
import com.sun.org.apache.xerces.internal.xs.ShortList;
import com.sun.org.apache.xerces.internal.xs.XSAnnotation;
import com.sun.org.apache.xerces.internal.xs.XSAttributeDeclaration;
import com.sun.org.apache.xerces.internal.xs.XSComplexTypeDefinition;
import com.sun.org.apache.xerces.internal.xs.XSConstants;
import com.sun.org.apache.xerces.internal.xs.XSNamespaceItem;
import com.sun.org.apache.xerces.internal.xs.XSObjectList;
import com.sun.org.apache.xerces.internal.xs.XSSimpleTypeDefinition;

/**
 * The XML representation for an attribute declaration
 * schema component is an <attribute> element information item
 *
 * @xerces.internal
 *
 * <p>
 *  属性声明模式组件的XML表示是一个<attribute>元素信息项
 * 
 *  @ xerces.internal
 * 
 * 
 * @author Elena Litani, IBM
 * @author Sandy Gao, IBM
 * @version $Id: XSAttributeDecl.java,v 1.7 2010-11-01 04:39:55 joehw Exp $
 */
public class XSAttributeDecl implements XSAttributeDeclaration {

    // scopes
    public final static short     SCOPE_ABSENT        = 0;
    public final static short     SCOPE_GLOBAL        = 1;
    public final static short     SCOPE_LOCAL         = 2;

    // the name of the attribute
    String fName = null;
    // the target namespace of the attribute
    String fTargetNamespace = null;
    // the simple type of the attribute
    XSSimpleType fType = null;
    public QName fUnresolvedTypeName = null;
    // value constraint type: default, fixed or !specified
    short fConstraintType = XSConstants.VC_NONE;
    // scope
    short fScope = XSConstants.SCOPE_ABSENT;
    // enclosing complex type, when the scope is local
    XSComplexTypeDecl fEnclosingCT = null;
    // optional annotations
    XSObjectList fAnnotations = null;
    // value constraint value
    ValidatedInfo fDefault = null;
    // The namespace schema information item corresponding to the target namespace
    // of the attribute declaration, if it is globally declared; or null otherwise.
    private XSNamespaceItem fNamespaceItem = null;

    public void setValues(String name, String targetNamespace,
            XSSimpleType simpleType, short constraintType, short scope,
            ValidatedInfo valInfo, XSComplexTypeDecl enclosingCT,
            XSObjectList annotations) {
        fName = name;
        fTargetNamespace = targetNamespace;
        fType = simpleType;
        fConstraintType = constraintType;
        fScope = scope;
        fDefault = valInfo;
        fEnclosingCT = enclosingCT;
        fAnnotations = annotations;
    }

    public void reset(){
        fName = null;
        fTargetNamespace = null;
        fType = null;
        fUnresolvedTypeName = null;
        fConstraintType = XSConstants.VC_NONE;
        fScope = XSConstants.SCOPE_ABSENT;
        fDefault = null;
        fAnnotations = null;
    }

    /**
     * Get the type of the object, i.e ELEMENT_DECLARATION.
     * <p>
     *  获取对象的类型,即ELEMENT_DECLARATION。
     * 
     */
    public short getType() {
        return XSConstants.ATTRIBUTE_DECLARATION;
    }

    /**
     * The <code>name</code> of this <code>XSObject</code> depending on the
     * <code>XSObject</code> type.
     * <p>
     *  取决于<code> XSObject </code>类型的<code> XSObject </code>的<code> name </code>
     * 
     */
    public String getName() {
        return fName;
    }

    /**
     * The namespace URI of this node, or <code>null</code> if it is
     * unspecified.  defines how a namespace URI is attached to schema
     * components.
     * <p>
     *  此节点的名称空间URI,或<code> null </code>(如果未指定)。定义命名空间URI如何附加到模式组件。
     * 
     */
    public String getNamespace() {
        return fTargetNamespace;
    }

    /**
     * A simple type definition
     * <p>
     *  一个简单的类型定义
     * 
     */
    public XSSimpleTypeDefinition getTypeDefinition() {
        return fType;
    }

    /**
     * Optional. Either global or a complex type definition (
     * <code>ctDefinition</code>). This property is absent in the case of
     * declarations within attribute group definitions: their scope will be
     * determined when they are used in the construction of complex type
     * definitions.
     * <p>
     *  可选的。全局或复杂类型定义(<code> ctDefinition </code>)。在属性组定义中的声明的情况下,不存在此属性：它们的作用域将在构建复杂类型定义时使用。
     * 
     */
    public short getScope() {
        return fScope;
    }

    /**
     * Locally scoped declarations are available for use only within the
     * complex type definition identified by the <code>scope</code>
     * property.
     * <p>
     * 局部范围声明仅可用于由<code> scope </code>属性标识的复杂类型定义中。
     * 
     */
    public XSComplexTypeDefinition getEnclosingCTDefinition() {
        return fEnclosingCT;
    }

    /**
     * Value constraint: one of default, fixed.
     * <p>
     *  值约束：默认值之一,固定。
     * 
     */
    public short getConstraintType() {
        return fConstraintType;
    }

    /**
     * Value constraint: The actual value (with respect to the {type
     * definition}) Should we return Object instead of DOMString?
     * <p>
     *  值约束：实际值(相对于{类型定义})我们应该返回Object而不是DOMString吗?
     * 
     */
    public String getConstraintValue() {
        // REVISIT: SCAPI: what's the proper representation
        return getConstraintType() == XSConstants.VC_NONE ?
               null :
               fDefault.stringValue();
    }

    /**
     * Optional. Annotation.
     * <p>
     *  可选的。注解。
     * 
     */
    public XSAnnotation getAnnotation() {
        return (fAnnotations != null) ? (XSAnnotation) fAnnotations.item(0) : null;
    }

    /**
     * Optional. Annotations.
     * <p>
     *  可选的。注释。
     * 
     */
    public XSObjectList getAnnotations() {
        return (fAnnotations != null) ? fAnnotations : XSObjectListImpl.EMPTY_LIST;
    }

    public ValidatedInfo getValInfo() {
        return fDefault;
    }

    /**
    /* <p>
    /* 
     * @see org.apache.xerces.xs.XSObject#getNamespaceItem()
     */
    public XSNamespaceItem getNamespaceItem() {
        return fNamespaceItem;
    }

    void setNamespaceItem(XSNamespaceItem namespaceItem) {
        fNamespaceItem = namespaceItem;
    }

    public Object getActualVC() {
        return getConstraintType() == XSConstants.VC_NONE ?
               null :
               fDefault.actualValue;
    }

    public short getActualVCType() {
        return getConstraintType() == XSConstants.VC_NONE ?
               XSConstants.UNAVAILABLE_DT :
               fDefault.actualValueType;
    }

    public ShortList getItemValueTypes() {
        return getConstraintType() == XSConstants.VC_NONE ?
               null :
               fDefault.itemValueTypes;
    }

} // class XSAttributeDecl
