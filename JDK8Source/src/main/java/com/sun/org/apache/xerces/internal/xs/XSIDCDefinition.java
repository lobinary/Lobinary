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
 * This interface represents the Identity-constraint Definition schema
 * component.
 * <p>
 *  此接口表示Identity-constraint定义模式组件。
 * 
 */
public interface XSIDCDefinition extends XSObject {
    // Identity Constraints
    /**
     * See the definition of <code>key</code> in the identity-constraint
     * category.
     * <p>
     *  请参阅identity-constraint类别中<code> key </code>的定义。
     * 
     */
    public static final short IC_KEY                    = 1;
    /**
     * See the definition of <code>keyref</code> in the identity-constraint
     * category.
     * <p>
     *  请参阅identity-constraint类别中<code> keyref </code>的定义。
     * 
     */
    public static final short IC_KEYREF                 = 2;
    /**
     * See the definition of <code>unique</code> in the identity-constraint
     * category.
     * <p>
     *  请参阅identity-constraint类别中<code> unique </code>的定义。
     * 
     */
    public static final short IC_UNIQUE                 = 3;

    /**
     * [identity-constraint category]: one of key, keyref or unique.
     * <p>
     *  [identity-constraint category]：key,keyref或unique中的一个。
     * 
     */
    public short getCategory();

    /**
     * [selector]: a restricted XPath 1.0 expression.
     * <p>
     *  [selector]：受限的XPath 1.0表达式。
     * 
     */
    public String getSelectorStr();

    /**
     * [fields]: a non-empty list of restricted  XPath 1.0 expressions.
     * <p>
     *  [fields]：受限XPath 1.0表达式的非空列表。
     * 
     */
    public StringList getFieldStrs();

    /**
     * [referenced key]: required if [identity-constraint category] is keyref,
     * <code>null</code> otherwise. An identity-constraint definition with [
     * identity-constraint category] equal to key or unique.
     * <p>
     *  [referenced key]：如果[identity-constraint category]是keyref,则为required,否则为<code> null </code>。
     * 具有等于​​键或唯一的[identity-constraint category]的身份约束定义。
     * 
     */
    public XSIDCDefinition getRefKey();

    /**
     * A sequence of [annotations] or an empty  <code>XSObjectList</code>.
     * <p>
     */
    public XSObjectList getAnnotations();

}
