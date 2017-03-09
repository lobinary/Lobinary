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
 * Describes a multi-value constraining facets: pattern and enumeration.
 * <p>
 *  描述多值约束方面：模式和枚举。
 * 
 */
public interface XSMultiValueFacet extends XSObject {
    /**
     * The name of the facet, i.e. <code>FACET_ENUMERATION</code> and
     * <code>FACET_PATTERN</code> (see <code>XSSimpleTypeDefinition</code>).
     * <p>
     *  构面的名称,即<code> FACET_ENUMERATION </code>和<code> FACET_PATTERN </code>(请参阅<code> XSSimpleTypeDefinitio
     * n </code>)。
     * 
     */
    public short getFacetKind();

    /**
     * Values of this facet.
     * <p>
     *  此构面的值。
     * 
     */
    public StringList getLexicalFacetValues();

    /**
     * A sequence of [annotations] or an empty <code>XSObjectList</code>.
     * <p>
     *  一个[注释]序列或一个空的<code> XSObjectList </code>。
     */
    public XSObjectList getAnnotations();

}
