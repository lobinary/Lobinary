/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * <p>
 *  根据一个或多个贡献者许可协议授予Apache软件基金会(ASF)。有关版权所有权的其他信息,请参阅随此作品分发的NOTICE文件。
 *  ASF根据Apache许可证2.0版("许可证")向您授予此文件;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本。
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
 * <p>
 *  版权所有(c)2005,2013,Oracle和/或其附属公司。版权所有。
 * 
 */
/*
 * $Id$
 * <p>
 *  $ Id $
 * 
 */
package com.sun.org.apache.xml.internal.security.signature.reference;

import java.util.Iterator;

import org.w3c.dom.Node;

/**
 * An abstract representation of a <code>ReferenceData</code> type containing a node-set.
 * <p>
 *  包含节点集的<code> ReferenceData </code>类型的抽象表示。
 * 
 */
public interface ReferenceNodeSetData extends ReferenceData {

    /**
     * Returns a read-only iterator over the nodes contained in this
     * <code>NodeSetData</code> in
     * <a href="http://www.w3.org/TR/1999/REC-xpath-19991116#dt-document-order">
     * document order</a>. Attempts to modify the returned iterator
     * via the <code>remove</code> method throw
     * <code>UnsupportedOperationException</code>.
     *
     * <p>
     *  在<code> NodeSetData </code>中包含的节点上返回只读迭代器
     * <a href="http://www.w3.org/TR/1999/REC-xpath-19991116#dt-document-order">
     * 
     * @return an <code>Iterator</code> over the nodes in this
     *    <code>NodeSetData</code> in document order
     */
    Iterator<Node> iterator();

}
