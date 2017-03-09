/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2000-2002,2004,2005 The Apache Software Foundation.
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
 *  版权所有2000-2002,2004,2005 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.impl.xs;

import com.sun.org.apache.xerces.internal.xs.ShortList;
import com.sun.org.apache.xerces.internal.xs.StringList;
import com.sun.org.apache.xerces.internal.xs.XSElementDeclaration;
import com.sun.org.apache.xerces.internal.xs.XSModel;
import com.sun.org.apache.xerces.internal.xs.XSNotationDeclaration;
import com.sun.org.apache.xerces.internal.xs.XSSimpleTypeDefinition;
import com.sun.org.apache.xerces.internal.xs.XSTypeDefinition;
import com.sun.org.apache.xerces.internal.impl.xs.util.StringListImpl;
import com.sun.org.apache.xerces.internal.xs.ElementPSVI;
import com.sun.org.apache.xerces.internal.xs.XSConstants;

/**
 * Element PSV infoset augmentations implementation.
 * The following information will be available at the startElement call:
 * name, namespace, type, notation, validation context
 *
 * The following information will be available at the endElement call:
 * nil, specified, normalized value, member type, validity, error codes,
 * default
 *
 * @xerces.internal
 *
 * <p>
 *  元素PSV信息集增强实现。以下信息将在startElement调用中可用：名称,命名空间,类型,符号,验证上下文
 * 
 *  以下信息将在endElement调用时可用：nil,指定,规范化值,成员类型,有效性,错误代码,默认
 * 
 *  @ xerces.internal
 * 
 * 
 * @author Elena Litani IBM
 */
public class ElementPSVImpl implements ElementPSVI {

    /** element declaration */
    protected XSElementDeclaration fDeclaration = null;

    /** type of element, could be xsi:type */
    protected XSTypeDefinition fTypeDecl = null;

    /** true if clause 3.2 of Element Locally Valid (Element) (3.3.4)
      * is satisfied, otherwise false
      * <p>
      *  否则为假
      * 
      */
    protected boolean fNil = false;

    /** true if the element value was provided by the schema; false otherwise.
    /* <p>
     */
    protected boolean fSpecified = false;

    /** schema normalized value property */
    protected String fNormalizedValue = null;

    /** schema actual value */
    protected Object fActualValue = null;

    /** schema actual value type */
    protected short fActualValueType = XSConstants.UNAVAILABLE_DT;

    /** actual value types if the value is a list */
    protected ShortList fItemValueTypes = null;

    /** http://www.w3.org/TR/xmlschema-1/#e-notation*/
    protected XSNotationDeclaration fNotation = null;

    /** member type definition against which element was validated */
    protected XSSimpleTypeDefinition fMemberType = null;

    /** validation attempted: none, partial, full */
    protected short fValidationAttempted = ElementPSVI.VALIDATION_NONE;

    /** validity: valid, invalid, unknown */
    protected short fValidity = ElementPSVI.VALIDITY_NOTKNOWN;

    /** error codes */
    protected String[] fErrorCodes = null;

    /** validation context: could be QName or XPath expression*/
    protected String fValidationContext = null;

    /** deferred XSModel **/
    protected SchemaGrammar[] fGrammars = null;

    /** the schema information property */
    protected XSModel fSchemaInformation = null;

    //
    // ElementPSVI methods
    //

    /**
     * [schema default]
     *
     * <p>
     *  [schema default]
     * 
     * 
     * @return The canonical lexical representation of the declaration's {value constraint} value.
     * @see <a href="http://www.w3.org/TR/xmlschema-1/#e-schema_default>XML Schema Part 1: Structures [schema default]</a>
     */
    public String getSchemaDefault() {
        return fDeclaration == null ? null : fDeclaration.getConstraintValue();
    }

    /**
     * [schema normalized value]
     *
     *
     * <p>
     *  [模式归一化值]
     * 
     * 
     * @see <a href="http://www.w3.org/TR/xmlschema-1/#e-schema_normalized_value>XML Schema Part 1: Structures [schema normalized value]</a>
     * @return the normalized value of this item after validation
     */
    public String getSchemaNormalizedValue() {
        return fNormalizedValue;
    }

    /**
     * [schema specified]
     * <p>
     *  [schema specified]
     * 
     * 
     * @see <a href="http://www.w3.org/TR/xmlschema-1/#e-schema_specified">XML Schema Part 1: Structures [schema specified]</a>
     * @return true - value was specified in schema, false - value comes from the infoset
     */
    public boolean getIsSchemaSpecified() {
        return fSpecified;
    }

    /**
     * Determines the extent to which the document has been validated
     *
     * <p>
     *  确定文档已验证的范围
     * 
     * 
     * @return return the [validation attempted] property. The possible values are
     *         NO_VALIDATION, PARTIAL_VALIDATION and FULL_VALIDATION
     */
    public short getValidationAttempted() {
        return fValidationAttempted;
    }

    /**
     * Determine the validity of the node with respect
     * to the validation being attempted
     *
     * <p>
     *  确定节点相对于正在尝试的验证的有效性
     * 
     * 
     * @return return the [validity] property. Possible values are:
     *         UNKNOWN_VALIDITY, INVALID_VALIDITY, VALID_VALIDITY
     */
    public short getValidity() {
        return fValidity;
    }

    /**
     * A list of error codes generated from validation attempts.
     * Need to find all the possible subclause reports that need reporting
     *
     * <p>
     *  从验证尝试生成的错误代码的列表。需要查找所有需要报告的可能子条款报告
     * 
     * 
     * @return Array of error codes
     */
    public StringList getErrorCodes() {
        if (fErrorCodes == null)
            return null;
        return new StringListImpl(fErrorCodes, fErrorCodes.length);
    }


    // This is the only information we can provide in a pipeline.
    public String getValidationContext() {
        return fValidationContext;
    }

    /**
     * [nil]
     * <p>
     *  [零]
     * 
     * 
     * @see <a href="http://www.w3.org/TR/xmlschema-1/#e-nil>XML Schema Part 1: Structures [nil]</a>
     * @return true if clause 3.2 of Element Locally Valid (Element) (3.3.4) above is satisfied, otherwise false
     */
    public boolean getNil() {
        return fNil;
    }

    /**
     * [notation]
     * <p>
     *  [符号]
     * 
     * 
     * @see <a href="http://www.w3.org/TR/xmlschema-1/#e-notation>XML Schema Part 1: Structures [notation]</a>
     * @return The notation declaration.
     */
    public XSNotationDeclaration getNotation() {
        return fNotation;
    }

    /**
     * An item isomorphic to the type definition used to validate this element.
     *
     * <p>
     * 与用于验证此元素的类型定义同构的项。
     * 
     * 
     * @return  a type declaration
     */
    public XSTypeDefinition getTypeDefinition() {
        return fTypeDecl;
    }

    /**
     * If and only if that type definition is a simple type definition
     * with {variety} union, or a complex type definition whose {content type}
     * is a simple thype definition with {variety} union, then an item isomorphic
     * to that member of the union's {member type definitions} which actually
     * validated the element item's normalized value.
     *
     * <p>
     *  如果且仅当该类型定义是一个具有{variety} union的简单类型定义,或者一个复杂类型定义,其{content type}是一个具有{variety} union的简单thype定义,那么一个项
     * 目同构于该成员的{成员类型定义},它实际上验证了元素项的归一化值。
     * 
     * 
     * @return  a simple type declaration
     */
    public XSSimpleTypeDefinition getMemberTypeDefinition() {
        return fMemberType;
    }

    /**
     * An item isomorphic to the element declaration used to validate
     * this element.
     *
     * <p>
     *  与用于验证此元素的元素声明同构的项。
     * 
     * 
     * @return  an element declaration
     */
    public XSElementDeclaration getElementDeclaration() {
        return fDeclaration;
    }

    /**
     * [schema information]
     * <p>
     *  [模式信息]
     * 
     * 
     * @see <a href="http://www.w3.org/TR/xmlschema-1/#e-schema_information">XML Schema Part 1: Structures [schema information]</a>
     * @return The schema information property if it's the validation root,
     *         null otherwise.
     */
    public synchronized XSModel getSchemaInformation() {
        if (fSchemaInformation == null && fGrammars != null) {
            fSchemaInformation = new XSModelImpl(fGrammars);
        }
        return fSchemaInformation;
    }

    /* (non-Javadoc)
    /* <p>
    /* 
     * @see com.sun.org.apache.xerces.internal.xs.ItemPSVI#getActualNormalizedValue()
     */
    public Object getActualNormalizedValue() {
        return this.fActualValue;
    }

    /* (non-Javadoc)
    /* <p>
    /* 
     * @see com.sun.org.apache.xerces.internal.xs.ItemPSVI#getActualNormalizedValueType()
     */
    public short getActualNormalizedValueType() {
        return this.fActualValueType;
    }

    /* (non-Javadoc)
    /* <p>
    /* 
     * @see com.sun.org.apache.xerces.internal.xs.ItemPSVI#getItemValueTypes()
     */
    public ShortList getItemValueTypes() {
        return this.fItemValueTypes;
    }

    /**
     * Reset() should be called in validator startElement(..) method.
     * <p>
     *  Reset()应该在校验器startElement(..)方法中调用。
     */
    public void reset() {
        fDeclaration = null;
        fTypeDecl = null;
        fNil = false;
        fSpecified = false;
        fNotation = null;
        fMemberType = null;
        fValidationAttempted = ElementPSVI.VALIDATION_NONE;
        fValidity = ElementPSVI.VALIDITY_NOTKNOWN;
        fErrorCodes = null;
        fValidationContext = null;
        fNormalizedValue = null;
        fActualValue = null;
        fActualValueType = XSConstants.UNAVAILABLE_DT;
        fItemValueTypes = null;
    }

}
