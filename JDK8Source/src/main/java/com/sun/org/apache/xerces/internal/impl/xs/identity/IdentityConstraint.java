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

package com.sun.org.apache.xerces.internal.impl.xs.identity;

import com.sun.org.apache.xerces.internal.xs.XSIDCDefinition;
import com.sun.org.apache.xerces.internal.xs.StringList;
import com.sun.org.apache.xerces.internal.xs.XSNamespaceItem;
import com.sun.org.apache.xerces.internal.xs.XSObjectList;
import com.sun.org.apache.xerces.internal.xs.XSConstants;
import com.sun.org.apache.xerces.internal.impl.xs.util.StringListImpl;
import com.sun.org.apache.xerces.internal.impl.xs.util.XSObjectListImpl;
import com.sun.org.apache.xerces.internal.impl.xs.XSAnnotationImpl;

/**
 * Base class of Schema identity constraint.
 *
 * @xerces.internal
 *
 * <p>
 *  模式标识约束的基类。
 * 
 *  @ xerces.internal
 * 
 * 
 * @author Andy Clark, IBM
 */
public abstract class IdentityConstraint implements XSIDCDefinition {

    //
    // Data
    //

    /** type */
    protected short type;

    /** target namespace */
    protected String fNamespace;

    /** Identity constraint name. */
    protected String fIdentityConstraintName;

    /** name of owning element */
    protected String fElementName;

    /** Selector. */
    protected Selector fSelector;

    /** Field count. */
    protected int fFieldCount;

    /** Fields. */
    protected Field[] fFields;

    // optional annotations
    protected XSAnnotationImpl [] fAnnotations = null;

    // number of annotations in this identity constraint
    protected int fNumAnnotations;

    //
    // Constructors
    //

    /** Default constructor. */
    protected IdentityConstraint(String namespace, String identityConstraintName, String elemName) {
        fNamespace = namespace;
        fIdentityConstraintName = identityConstraintName;
        fElementName = elemName;
    } // <init>(String,String)

    //
    // Public methods
    //

    /** Returns the identity constraint name. */
    public String getIdentityConstraintName() {
        return fIdentityConstraintName;
    } // getIdentityConstraintName():String

    /** Sets the selector. */
    public void setSelector(Selector selector) {
        fSelector = selector;
    } // setSelector(Selector)

    /** Returns the selector. */
    public Selector getSelector() {
        return fSelector;
    } // getSelector():Selector

    /** Adds a field. */
    public void addField(Field field) {
        if (fFields == null)
            fFields = new Field[4];
        else if (fFieldCount == fFields.length)
            fFields = resize(fFields, fFieldCount*2);
        fFields[fFieldCount++] = field;
    } // addField(Field)

    /** Returns the field count. */
    public int getFieldCount() {
        return fFieldCount;
    } // getFieldCount():int

    /** Returns the field at the specified index. */
    public Field getFieldAt(int index) {
        return fFields[index];
    } // getFieldAt(int):Field

    // get the name of the owning element
    public String getElementName () {
        return fElementName;
    } // getElementName(): String

    //
    // Object methods
    //

    /** Returns a string representation of this object. */
    public String toString() {
        String s = super.toString();
        int index1 = s.lastIndexOf('$');
        if (index1 != -1) {
            return s.substring(index1 + 1);
        }
        int index2 = s.lastIndexOf('.');
        if (index2 != -1) {
            return s.substring(index2 + 1);
        }
        return s;
    } // toString():String

    // equals:  returns true if and only if the String
    // representations of all members of both objects (except for
    // the elenemtName field) are equal.
    public boolean equals(IdentityConstraint id) {
        boolean areEqual = fIdentityConstraintName.equals(id.fIdentityConstraintName);
        if(!areEqual) return false;
        areEqual = fSelector.toString().equals(id.fSelector.toString());
        if(!areEqual) return false;
        areEqual = (fFieldCount == id.fFieldCount);
        if(!areEqual) return false;
        for(int i=0; i<fFieldCount; i++)
            if(!fFields[i].toString().equals(id.fFields[i].toString())) return false;
        return true;
    } // equals

    static final Field[] resize(Field[] oldArray, int newSize) {
        Field[] newArray = new Field[newSize];
        System.arraycopy(oldArray, 0, newArray, 0, oldArray.length);
        return newArray;
    }

    /**
     * Get the type of the object, i.e ELEMENT_DECLARATION.
     * <p>
     *  获取对象的类型,即ELEMENT_DECLARATION。
     * 
     */
    public short getType() {
        return XSConstants.IDENTITY_CONSTRAINT;
    }

    /**
     * The <code>name</code> of this <code>XSObject</code> depending on the
     * <code>XSObject</code> type.
     * <p>
     *  取决于<code> XSObject </code>类型的<code> XSObject </code>的<code> name </code>
     * 
     */
    public String getName() {
        return fIdentityConstraintName;
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
        return fNamespace;
    }

    /**
     * {identity-constraint category} One of key, keyref or unique.
     * <p>
     *  {identity-constraint category}键,keyref或唯一键之一。
     * 
     */
    public short getCategory() {
        return type;
    }

    /**
     * {selector} A restricted XPath ([XPath]) expression
     * <p>
     *  {selector}受限XPath([XPath])表达式
     * 
     */
    public String getSelectorStr() {
        return (fSelector != null) ? fSelector.toString() : null;
    }

    /**
     * {fields} A non-empty list of restricted XPath ([XPath]) expressions.
     * <p>
     *  {fields}受限XPath([XPath])表达式的非空列表。
     * 
     */
    public StringList getFieldStrs() {
        String[] strs = new String[fFieldCount];
        for (int i = 0; i < fFieldCount; i++)
            strs[i] = fFields[i].toString();
        return new StringListImpl(strs, fFieldCount);
    }

    /**
     * {referenced key} Required if {identity-constraint category} is keyref,
     * forbidden otherwise. An identity-constraint definition with
     * {identity-constraint category} equal to key or unique.
     * <p>
     *  {referenced key}如果{identity-constraint category}是keyref,则为必需,否则禁止。
     * 一个身份约束定义{identity-constraint category}等于key或unique。
     * 
     */
    public XSIDCDefinition getRefKey() {
        return null;
    }

    /**
     * Optional. Annotation.
     * <p>
     *  可选的。注解。
     * 
     */
    public XSObjectList getAnnotations() {
        return new XSObjectListImpl(fAnnotations, fNumAnnotations);
    }

        /**
        /* <p>
        /* 
         * @see com.sun.org.apache.xerces.internal.xs.XSObject#getNamespaceItem()
         */
        public XSNamespaceItem getNamespaceItem() {
        // REVISIT: implement
                return null;
        }

    public void addAnnotation(XSAnnotationImpl annotation) {
        if(annotation == null)
            return;
        if(fAnnotations == null) {
            fAnnotations = new XSAnnotationImpl[2];
        } else if(fNumAnnotations == fAnnotations.length) {
            XSAnnotationImpl[] newArray = new XSAnnotationImpl[fNumAnnotations << 1];
            System.arraycopy(fAnnotations, 0, newArray, 0, fNumAnnotations);
            fAnnotations = newArray;
        }
        fAnnotations[fNumAnnotations++] = annotation;
    }

} // class IdentityConstraint
