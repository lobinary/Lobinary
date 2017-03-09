/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2003,2004 The Apache Software Foundation.
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
 *  版权所有2003,2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.xs;

/**
 * This interface represents the Complex Type Definition schema component.
 * <p>
 *  此接口表示复杂类型定义模式组件。
 * 
 */
public interface XSComplexTypeDefinition extends XSTypeDefinition {
    // Content Model Types
    /**
     * Represents an empty content type. A content type with the distinguished
     * value empty validates elements with no character or element
     * information item children.
     * <p>
     *  表示空的内容类型。具有区分值empty的内容类型将验证没有字符或元素信息项子项的元素。
     * 
     */
    public static final short CONTENTTYPE_EMPTY         = 0;
    /**
     * Represents a simple content type. A content type which is simple
     * validates elements with character-only children.
     * <p>
     *  表示简单的内容类型。简单的内容类型验证具有仅有字符的子元素的元素。
     * 
     */
    public static final short CONTENTTYPE_SIMPLE        = 1;
    /**
     * Represents an element-only content type. An element-only content type
     * validates elements with children that conform to the supplied content
     * model.
     * <p>
     *  表示仅元素的内容类型。仅元素内容类型验证符合所提供的内容模型的具有子元素的元素。
     * 
     */
    public static final short CONTENTTYPE_ELEMENT       = 2;
    /**
     * Represents a mixed content type.
     * <p>
     *  表示混合内容类型。
     * 
     */
    public static final short CONTENTTYPE_MIXED         = 3;

    /**
     * [derivation method]: either <code>DERIVATION_EXTENSION</code>,
     * <code>DERIVATION_RESTRICTION</code>, or <code>DERIVATION_NONE</code>
     * (see <code>XSConstants</code>).
     * <p>
     *  [派生方法]：<code> DERIVATION_EXTENSION </code>,<code> DERIVATION_RESTRICTION </code>或<code> DERIVATION_N
     * ONE </code>(请参阅<code> XSConstants </code>)。
     * 
     */
    public short getDerivationMethod();

    /**
     * [abstract]: a boolean. Complex types for which <code>abstract</code> is
     * true must not be used as the type definition for the validation of
     * element information items.
     * <p>
     * [abstract]：一个布尔值。不能将<code> abstract </code>为真的复杂类型用作元素信息项验证的类型定义。
     * 
     */
    public boolean getAbstract();

    /**
     *  A set of attribute uses if it exists, otherwise an empty
     * <code>XSObjectList</code>.
     * <p>
     *  一组属性使用(如果存在),否则为一个空的<code> XSObjectList </code>。
     * 
     */
    public XSObjectList getAttributeUses();

    /**
     * An attribute wildcard if it exists, otherwise <code>null</code>.
     * <p>
     *  属性通配符(如果存在),否则为<code> null </code>。
     * 
     */
    public XSWildcard getAttributeWildcard();

    /**
     * [content type]: one of empty (<code>CONTENTTYPE_EMPTY</code>), a simple
     * type definition (<code>CONTENTTYPE_SIMPLE</code>), mixed (
     * <code>CONTENTTYPE_MIXED</code>), or element-only (
     * <code>CONTENTTYPE_ELEMENT</code>).
     * <p>
     *  [content type]：一个空格(<code> CONTENTTYPE_EMPTY </code>),一个简单的类型定义(<code> CONTENTTYPE_SIMPLE </code>),混
     * 合型(<code> CONTENTTYPE_MIXED </code> (<code> CONTENTTYPE_ELEMENT </code>)。
     * 
     */
    public short getContentType();

    /**
     * A simple type definition corresponding to a simple content model,
     * otherwise <code>null</code>.
     * <p>
     *  一个简单的类型定义对应一个简单的内容模型,否则<code> null </code>。
     * 
     */
    public XSSimpleTypeDefinition getSimpleType();

    /**
     * A particle for a mixed or element-only content model, otherwise
     * <code>null</code>.
     * <p>
     *  用于混合或仅元素内容模型的粒子,否则<code> null </code>。
     * 
     */
    public XSParticle getParticle();

    /**
     * [prohibited substitutions]: a subset of {extension, restriction}
     * <p>
     *  [禁止替代]：{extension,restriction}的一个子集
     * 
     * 
     * @param restriction  Extension or restriction constants (see
     *   <code>XSConstants</code>).
     * @return True if <code>restriction</code> is a prohibited substitution,
     *   otherwise false.
     */
    public boolean isProhibitedSubstitution(short restriction);

    /**
     *  [prohibited substitutions]: A subset of {extension, restriction} or
     * <code>DERIVATION_NONE</code> represented as a bit flag (see
     * <code>XSConstants</code>).
     * <p>
     *  [禁止替换]：表示为位标志的{extension,restriction}或<code> DERIVATION_NONE </code>的子集(请参阅<code> XSConstants </code>
     * )。
     * 
     */
    public short getProhibitedSubstitutions();

    /**
     * A sequence of [annotations] or an empty <code>XSObjectList</code>.
     * <p>
     */
    public XSObjectList getAnnotations();

}
