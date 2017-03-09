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
import com.sun.org.apache.xerces.internal.impl.xs.identity.IdentityConstraint;
import com.sun.org.apache.xerces.internal.impl.xs.util.XSNamedMapImpl;
import com.sun.org.apache.xerces.internal.impl.xs.util.XSObjectListImpl;
import com.sun.org.apache.xerces.internal.xni.QName;
import com.sun.org.apache.xerces.internal.xs.ShortList;
import com.sun.org.apache.xerces.internal.xs.XSAnnotation;
import com.sun.org.apache.xerces.internal.xs.XSComplexTypeDefinition;
import com.sun.org.apache.xerces.internal.xs.XSConstants;
import com.sun.org.apache.xerces.internal.xs.XSElementDeclaration;
import com.sun.org.apache.xerces.internal.xs.XSNamedMap;
import com.sun.org.apache.xerces.internal.xs.XSNamespaceItem;
import com.sun.org.apache.xerces.internal.xs.XSObjectList;
import com.sun.org.apache.xerces.internal.xs.XSTypeDefinition;

/**
 * The XML representation for an element declaration
 * schema component is an <element> element information item
 *
 * @xerces.internal
 *
 * <p>
 *  元素声明模式组件的XML表示是一个<element>元素信息项
 * 
 *  @ xerces.internal
 * 
 * 
 * @author Elena Litani, IBM
 * @author Sandy Gao, IBM
 * @version $Id: XSElementDecl.java,v 1.7 2010-11-01 04:39:55 joehw Exp $
 */
public class XSElementDecl implements XSElementDeclaration {

    // scopes
    public final static short     SCOPE_ABSENT        = 0;
    public final static short     SCOPE_GLOBAL        = 1;
    public final static short     SCOPE_LOCAL         = 2;

    // name of the element
    public String fName = null;
    // target namespace of the element
    public String fTargetNamespace = null;
    // type of the element
    public XSTypeDefinition fType = null;
    public QName fUnresolvedTypeName = null;
    // misc flag of the element: nillable/abstract/fixed
    short fMiscFlags = 0;
    public short fScope = XSConstants.SCOPE_ABSENT;
    // enclosing complex type, when the scope is local
    XSComplexTypeDecl fEnclosingCT = null;
    // block set (disallowed substitutions) of the element
    public short fBlock = XSConstants.DERIVATION_NONE;
    // final set (substitution group exclusions) of the element
    public short fFinal = XSConstants.DERIVATION_NONE;
    // optional annotation
    public XSObjectList fAnnotations = null;
    // value constraint value
    public ValidatedInfo fDefault = null;
    // the substitution group affiliation of the element
    public XSElementDecl fSubGroup = null;
    // identity constraints
    static final int INITIAL_SIZE = 2;
    int fIDCPos = 0;
    IdentityConstraint[] fIDConstraints = new IdentityConstraint[INITIAL_SIZE];
    // The namespace schema information item corresponding to the target namespace
    // of the element declaration, if it is globally declared; or null otherwise.
    private XSNamespaceItem fNamespaceItem = null;

    private static final short CONSTRAINT_MASK = 3;
    private static final short NILLABLE        = 4;
    private static final short ABSTRACT        = 8;

    // methods to get/set misc flag
    public void setConstraintType(short constraintType) {
        // first clear the bits
        fMiscFlags ^= (fMiscFlags & CONSTRAINT_MASK);
        // then set the proper one
        fMiscFlags |= (constraintType & CONSTRAINT_MASK);
    }
    public void setIsNillable() {
        fMiscFlags |= NILLABLE;
    }
    public void setIsAbstract() {
        fMiscFlags |= ABSTRACT;
    }
    public void setIsGlobal() {
        fScope = SCOPE_GLOBAL;
    }
    public void setIsLocal(XSComplexTypeDecl enclosingCT) {
        fScope = SCOPE_LOCAL;
        fEnclosingCT = enclosingCT;
    }

    public void addIDConstraint(IdentityConstraint idc) {
        if (fIDCPos == fIDConstraints.length) {
            fIDConstraints = resize(fIDConstraints, fIDCPos*2);
        }
        fIDConstraints[fIDCPos++] = idc;
    }

    public IdentityConstraint[] getIDConstraints() {
        if (fIDCPos == 0) {
            return null;
        }
        if (fIDCPos < fIDConstraints.length) {
            fIDConstraints = resize(fIDConstraints, fIDCPos);
        }
        return fIDConstraints;
    }

    static final IdentityConstraint[] resize(IdentityConstraint[] oldArray, int newSize) {
        IdentityConstraint[] newArray = new IdentityConstraint[newSize];
        System.arraycopy(oldArray, 0, newArray, 0, Math.min(oldArray.length, newSize));
        return newArray;
    }

    /**
     * get the string description of this element
     * <p>
     *  获取此元素的字符串描述
     * 
     */
    private String fDescription = null;
    public String toString() {
        if (fDescription == null) {
            if (fTargetNamespace != null) {
                StringBuffer buffer = new StringBuffer(
                    fTargetNamespace.length() +
                    ((fName != null) ? fName.length() : 4) + 3);
                buffer.append('"');
                buffer.append(fTargetNamespace);
                buffer.append('"');
                buffer.append(':');
                buffer.append(fName);
                fDescription = buffer.toString();
            }
            else {
                fDescription = fName;
            }
        }
        return fDescription;
    }

    /**
     * get the hash code
     * <p>
     *  获取哈希码
     * 
     */
    public int hashCode() {
        int code = fName.hashCode();
        if (fTargetNamespace != null)
            code = (code<<16)+fTargetNamespace.hashCode();
        return code;
    }

    /**
     * whether two decls are the same
     * <p>
     *  是否两个decls相同
     * 
     */
    public boolean equals(Object o) {
        return o == this;
    }

    /**
      * Reset current element declaration
      * <p>
      *  重置当前元素声明
      * 
      */
    public void reset(){
        fScope = XSConstants.SCOPE_ABSENT;
        fName = null;
        fTargetNamespace = null;
        fType = null;
        fUnresolvedTypeName = null;
        fMiscFlags = 0;
        fBlock = XSConstants.DERIVATION_NONE;
        fFinal = XSConstants.DERIVATION_NONE;
        fDefault = null;
        fAnnotations = null;
        fSubGroup = null;
        // reset identity constraints
        for (int i=0;i<fIDCPos;i++) {
            fIDConstraints[i] = null;
        }

        fIDCPos = 0;
    }

    /**
     * Get the type of the object, i.e ELEMENT_DECLARATION.
     * <p>
     *  获取对象的类型,即ELEMENT_DECLARATION。
     * 
     */
    public short getType() {
        return XSConstants.ELEMENT_DECLARATION;
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
     * Either a simple type definition or a complex type definition.
     * <p>
     *  可以是简单类型定义或复杂类型定义。
     * 
     */
    public XSTypeDefinition getTypeDefinition() {
        return fType;
    }

    /**
     * Optional. Either global or a complex type definition (
     * <code>ctDefinition</code>). This property is absent in the case of
     * declarations within named model groups: their scope will be
     * determined when they are used in the construction of complex type
     * definitions.
     * <p>
     * 可选的。全局或复杂类型定义(<code> ctDefinition </code>)。在命名模型组中的声明的情况下,此属性不存在：当它们用于构造复杂类型定义时,它们的范围将被确定。
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
     *  局部范围声明仅可用于由<code> scope </code>属性标识的复杂类型定义中。
     * 
     */
    public XSComplexTypeDefinition getEnclosingCTDefinition() {
        return fEnclosingCT;
    }

    /**
     * A value constraint: one of default, fixed.
     * <p>
     *  值约束：默认值之一,固定。
     * 
     */
    public short getConstraintType() {
        return (short)(fMiscFlags & CONSTRAINT_MASK);
    }

    /**
     * A value constraint: The actual value (with respect to the {type
     * definition})
     * <p>
     *  值约束：实际值(相对于{类型定义})
     * 
     */
    public String getConstraintValue() {
        // REVISIT: SCAPI: what's the proper representation
        return getConstraintType() == XSConstants.VC_NONE ?
               null :
               fDefault.stringValue();
    }

    /**
     * If {nillable} is true, then an element may also be valid if it carries
     * the namespace qualified attribute with [local name] nil from
     * namespace http://www.w3.org/2001/XMLSchema-instance and value true
     * (see xsi:nil (2.6.2)) even if it has no text or element content
     * despite a {content type} which would otherwise require content.
     * <p>
     *  如果{nillable}为true,那么如果元素携带来自命名空间http://www.w3.org/2001/XMLSchema-instance中的[local name] nil的命名空间限定属性
     * ,值为true(见xsi ：nil(2.6.2)),即使它没有文本或元素内容,尽管否则需要内容的{content type}。
     * 
     */
    public boolean getNillable() {
        return ((fMiscFlags & NILLABLE) != 0);
    }

    /**
     * {identity-constraint definitions} A set of constraint definitions.
     * <p>
     *  {identity-constraint definitions}一组约束定义。
     * 
     */
    public XSNamedMap getIdentityConstraints() {
        return new XSNamedMapImpl(fIDConstraints, fIDCPos);
    }

    /**
     * {substitution group affiliation} Optional. A top-level element
     * definition.
     * <p>
     *  {substitute group affiliation}可选。顶级元素定义。
     * 
     */
    public XSElementDeclaration getSubstitutionGroupAffiliation() {
        return fSubGroup;
    }

    /**
     * Convenience method. Check if <code>exclusion</code> is a substitution
     * group exclusion for this element declaration.
     * <p>
     *  方便的方法。检查<code>排除</code>是否为此元素声明的替代组排除。
     * 
     * 
     * @param exclusion Extension, restriction or none. Represents final
     *   set for the element.
     * @return True if <code>exclusion</code> is a part of the substitution
     *   group exclusion subset.
     */
    public boolean isSubstitutionGroupExclusion(short exclusion) {
        return (fFinal & exclusion) != 0;
    }

    /**
     * Specifies if this declaration can be nominated as
     * the {substitution group affiliation} of other
     * element declarations having the same {type definition}
     * or types derived therefrom.
     *
     * <p>
     *  指定此声明是否可以被指定为具有相同{类型定义}或其派生类型的其他元素声明的{替换组附属}。
     * 
     * 
     * @return A bit flag representing {extension, restriction} or NONE.
     */
    public short getSubstitutionGroupExclusions() {
        return fFinal;
    }

    /**
     * Convenience method. Check if <code>disallowed</code> is a disallowed
     * substitution for this element declaration.
     * <p>
     *  方便的方法。检查<code>不允许</code>是否不允许替换此元素声明。
     * 
     * 
     * @param disallowed Substitution, extension, restriction or none.
     *   Represents a block set for the element.
     * @return True if <code>disallowed</code> is a part of the substitution
     *   group exclusion subset.
     */
    public boolean isDisallowedSubstitution(short disallowed) {
        return (fBlock & disallowed) != 0;
    }

    /**
     * The supplied values for {disallowed substitutions}
     *
     * <p>
     *  提供的{disallowed substitution}
     * 
     * 
     * @return A bit flag representing {substitution, extension, restriction} or NONE.
     */
    public short getDisallowedSubstitutions() {
        return fBlock;
    }

    /**
     * {abstract} A boolean.
     * <p>
     *  {abstract}布尔值。
     * 
     */
    public boolean getAbstract() {
        return ((fMiscFlags & ABSTRACT) != 0);
    }

    /**
     * Optional. Annotation.
     * <p>
     * 可选的。注解。
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

} // class XSElementDecl
