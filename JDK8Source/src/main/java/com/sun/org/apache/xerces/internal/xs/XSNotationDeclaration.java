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
 *  This interface represents the Notation Declaration schema component.
 * <p>
 *  此接口表示符号声明模式组件。
 * 
 */
public interface XSNotationDeclaration extends XSObject {
    /**
     *  The URI reference representing the system identifier for the notation
     * declaration, if present, <code>null</code> otherwise.
     * <p>
     *  表示符号声明的系统标识符的URI引用(如果存在),否则为<code> null </code>。
     * 
     */
    public String getSystemId();

    /**
     *  The string representing the public identifier for this notation
     * declaration, if present; <code>null</code> otherwise.
     * <p>
     *  表示此符号声明的公共标识符的字符串(如果存在); <code> null </code>。
     * 
     */
    public String getPublicId();

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
