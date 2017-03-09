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
 * This interface represents a complex or simple type definition.
 * <p>
 *  此接口表示复杂或简单的类型定义。
 * 
 */
public interface XSTypeDefinition extends XSObject {
    /**
     * The object describes a complex type.
     * <p>
     *  对象描述复杂类型。
     * 
     */
    public static final short COMPLEX_TYPE              = 15;
    /**
     * The object describes a simple type.
     * <p>
     *  对象描述一个简单类型。
     * 
     */
    public static final short SIMPLE_TYPE               = 16;
    /**
     * Return whether this type definition is a simple type or complex type.
     * <p>
     *  返回此类型定义是简单类型还是复杂类型。
     * 
     */
    public short getTypeCategory();

    /**
     * {base type definition}: either a simple type definition or a complex
     * type definition.
     * <p>
     *  {base type definition}：简单类型定义或复杂类型定义。
     * 
     */
    public XSTypeDefinition getBaseType();

    /**
     * {final}. For a complex type definition it is a subset of {extension,
     * restriction}. For a simple type definition it is a subset of
     * {extension, list, restriction, union}.
     * <p>
     *  {最后}。对于复杂类型定义,它是{extension,restriction}的子集。对于简单的类型定义,它是{extension,list,restriction,union}的子集。
     * 
     * 
     * @param restriction  Extension, restriction, list, union constants
     *   (defined in <code>XSConstants</code>).
     * @return True if <code>restriction</code> is in the final set,
     *   otherwise false.
     */
    public boolean isFinal(short restriction);

    /**
     * For complex types the returned value is a bit combination of the subset
     * of {<code>DERIVATION_EXTENSION, DERIVATION_RESTRICTION</code>}
     * corresponding to <code>final</code> set of this type or
     * <code>DERIVATION_NONE</code>. For simple types the returned value is
     * a bit combination of the subset of {
     * <code>DERIVATION_RESTRICTION, DERIVATION_EXTENSION, DERIVATION_UNION, DERIVATION_LIST</code>
     * } corresponding to <code>final</code> set of this type or
     * <code>DERIVATION_NONE</code>.
     * <p>
     * 对于复杂类型,返回值是对应于此类型的<code> final </code>集合或<code> DERIVATION_NONE </code>的{<code> DERIVATION_EXTENSION,DERIVATION_RESTRICTION </code>}
     * 子集的位组合。
     * 对于简单类型,返回值是对应于此类型的<code> final </code>集合的{<code> DERIVATION_RESTRICTION,DERIVATION_EXTENSION,DERIVATION_UNION,DERIVATION_LIST </code>}
     * 子集合的位组合,或<code> DERIVATION_NONE < / code>。
     * 
     */
    public short getFinal();

    /**
     *  Convenience attribute. A boolean that specifies if the type definition
     * is anonymous.
     * <p>
     *  便利属性。指定类型定义是否为匿名的布尔值。
     * 
     */
    public boolean getAnonymous();

    /**
     * Convenience method which checks if this type is derived from the given
     * <code>ancestorType</code>.
     * <p>
     *  检查这种类型是否是从给定的<code> ancestorType </code>派生的方便方法。
     * 
     * 
     * @param ancestorType  An ancestor type definition.
     * @param derivationMethod  A bit combination representing a subset of {
     *   <code>DERIVATION_RESTRICTION, DERIVATION_EXTENSION, DERIVATION_UNION, DERIVATION_LIST</code>
     *   }.
     * @return  True if this type is derived from <code>ancestorType</code>
     *   using only derivation methods from the <code>derivationMethod</code>
     *   .
     */
    public boolean derivedFromType(XSTypeDefinition ancestorType,
                                   short derivationMethod);

    /**
     * Convenience method which checks if this type is derived from the given
     * ancestor type.
     * <p>
     *  方便方法检查这种类型是从给定的祖先类型派生的。
     * 
     * @param namespace  An ancestor type namespace.
     * @param name  An ancestor type name.
     * @param derivationMethod  A bit combination representing a subset of {
     *   <code>DERIVATION_RESTRICTION, DERIVATION_EXTENSION, DERIVATION_UNION, DERIVATION_LIST</code>
     *   }.
     * @return  True if this type is derived from <code>ancestorType</code>
     *   using only derivation methods from the <code>derivationMethod</code>
     *   .
     */
    public boolean derivedFrom(String namespace,
                               String name,
                               short derivationMethod);

}
