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
 * This interface represents the Wildcard schema component.
 * <p>
 *  此接口表示通配符模式组件。
 * 
 */
public interface XSWildcard extends XSTerm {
    // Namespace Constraint
    /**
     * Namespace Constraint: any namespace is allowed.
     * <p>
     *  命名空间约束：允许任何命名空间。
     * 
     */
    public static final short NSCONSTRAINT_ANY          = 1;
    /**
     * Namespace Constraint: namespaces in the list are not allowed.
     * <p>
     *  命名空间约束：不允许列表中的命名空间。
     * 
     */
    public static final short NSCONSTRAINT_NOT          = 2;
    /**
     * Namespace Constraint: namespaces in the list are allowed.
     * <p>
     *  命名空间约束：允许列表中的命名空间。
     * 
     */
    public static final short NSCONSTRAINT_LIST         = 3;

    // Process contents
    /**
     * There must be a top-level declaration for the item available, or the
     * item must have an xsi:type, and the item must be valid as appropriate.
     * <p>
     *  必须有可用项目的顶级声明,或者项目必须具有xsi：类型,并且项目必须适当有效。
     * 
     */
    public static final short PC_STRICT                 = 1;
    /**
     * No constraints at all: the item must simply be well-formed XML.
     * <p>
     *  没有约束：项目必须简单地是良好形式的XML。
     * 
     */
    public static final short PC_SKIP                   = 2;
    /**
     * If the item, or any items among its [children] is an element
     * information item, has a uniquely determined declaration available, it
     * must be valid with respect to that definition, that is, validate
     * where you can and do not worry when you cannot.
     * <p>
     *  如果项目或其[children]中的任何项目是元素信息项目,具有唯一确定的声明可用,则它必须对该定义有效,即验证您在哪里可以和不必担心,当您不能。
     * 
     */
    public static final short PC_LAX                    = 3;

    /**
     * Namespace constraint: A constraint type: any, not, list.
     * <p>
     *  命名空间约束：约束类型：any,not,list。
     * 
     */
    public short getConstraintType();

    /**
     * Namespace constraint: For <code>constraintType</code>
     * <code>NSCONSTRAINT_LIST</code>, the list contains allowed namespaces.
     * For <code>constraintType</code> <code>NSCONSTRAINT_NOT</code>, the
     * list contains disallowed namespaces. For <code>constraintType</code>
     * <code>NSCONSTRAINT_ANY</code>, the <code>StringList</code> is empty.
     * <p>
     * 命名空间约束：对于<code> constraintType </code> <code> NSCONSTRAINT_LIST </code>,列表包含允许的命名空间。
     * 对于<code> constraintType </code> <code> NSCONSTRAINT_NOT </code>,列表包含不允许的命名空间。
     * 对于<code> constraintType </code> <code> NSCONSTRAINT_ANY </code>,<code> StringList </code>为空。
     * 
     */
    public StringList getNsConstraintList();

    /**
     * [process contents]: one of skip, lax or strict. Valid constants values
     * are: <code>PC_LAX</code>, <code>PC_SKIP</code> and
     * <code>PC_STRICT</code>.
     * <p>
     *  [进程内容]：skip,lax或strict之一。
     * 有效的常数值为：<code> PC_LAX </code>,<code> PC_SKIP </code>和<code> PC_STRICT </code>。
     * 
     */
    public short getProcessContents();

    /**
     * An annotation if it exists, otherwise <code>null</code>. If not null
     * then the first [annotation] from the sequence of annotations.
     * <p>
     *  注释(如果存在),否则<code> null </code>。如果不是null,那么第一个[注释]来自注释序列。
     * 
     */
    public XSAnnotation getAnnotation();

    /**
     * A sequence of [annotations] or an empty <code>XSObjectList</code>.
     * <p>
     *  一个[注释]序列或一个空的<code> XSObjectList </code>。
     */
    public XSObjectList getAnnotations();
}
