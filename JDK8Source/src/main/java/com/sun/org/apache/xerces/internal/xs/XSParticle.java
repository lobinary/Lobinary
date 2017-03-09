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
 * This interface represents the Particle schema component.
 * <p>
 *  此接口表示粒子模式组件。
 * 
 */
public interface XSParticle extends XSObject {
    /**
     * [min occurs]: determines the minimum number of terms that can occur.
     * <p>
     *  [最小发生]：确定可能发生的最小数量的术语。
     * 
     */
    public int getMinOccurs();

    /**
     *  [max occurs]: determines the maximum number of terms that can occur.
     * To query for the value of unbounded use
     * <code>maxOccursUnbounded</code>. When the value of
     * <code>maxOccursUnbounded</code> is <code>true</code>, the value of
     * <code>maxOccurs</code> is unspecified.
     * <p>
     *  [max occur]：确定可能发生的最大数量的术语。要查询无限的值,请使用<code> maxOccursUnbounded </code>。
     * 当<code> maxOccursUnbounded </code>的值为<code> true </code>时,未指定<code> maxOccurs </code>的值。
     * 
     */
    public int getMaxOccurs();

    /**
     * [max occurs]: whether the maxOccurs value is unbounded.
     * <p>
     *  [max occur]：maxOccurs值是否无界。
     * 
     */
    public boolean getMaxOccursUnbounded();

    /**
     * [term]: one of a model group, a wildcard, or an element declaration.
     * <p>
     *  [term]：模型组,通配符或元素声明之一。
     * 
     */
    public XSTerm getTerm();

    /**
     * A sequence of [annotations] or an empty <code>XSObjectList</code>.
     * <p>
     *  一个[注释]序列或一个空的<code> XSObjectList </code>。
     */
    public XSObjectList getAnnotations();
}
