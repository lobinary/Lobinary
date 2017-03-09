/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 1999-2002,2004 The Apache Software Foundation.
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
 *  版权所有1999-2002,2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.impl.xs.models;

import com.sun.org.apache.xerces.internal.impl.dtd.models.CMNode;
import com.sun.org.apache.xerces.internal.impl.dtd.models.CMStateSet;

/**
 * Content model leaf node.
 *
 * @xerces.internal
 *
 * <p>
 *  内容模型叶节点。
 * 
 *  @ xerces.internal
 * 
 * 
 * @author Neil Graham, IBM
 */
public class XSCMLeaf
    extends CMNode {

    //
    // Data
    //

    /** This is the leaf: element decl or wildcard decl. */
    private Object fLeaf = null;

    /**
     * Identify the particle: for UPA checking
     * <p>
     *  识别粒子：用于UPA检查
     * 
     */
    private int fParticleId = -1;

    /**
     * Part of the algorithm to convert a regex directly to a DFA
     * numbers each leaf sequentially. If its -1, that means its an
     * epsilon node. Zero and greater are non-epsilon positions.
     * <p>
     *  将正则表达式直接转换为DFA的算法的一部分顺序地对每个叶进行编号。如果它的-1,这意味着它的一个epsilon节点。零和更大的是非ε位置。
     */
    private int fPosition = -1;

    //
    // Constructors
    //

    /** Constructs a content model leaf. */
    public XSCMLeaf(int type, Object leaf, int id, int position)  {
        super(type);

        // Store the element index and position
        fLeaf = leaf;
        fParticleId = id;
        fPosition = position;
    }

    //
    // Package methods
    //

    final Object getLeaf() {
        return fLeaf;
    }

    final int getParticleId() {
        return fParticleId;
    }

    final int getPosition() {
        return fPosition;
    }

    final void setPosition(int newPosition) {
        fPosition = newPosition;
    }

    //
    // CMNode methods
    //

    // package

    public boolean isNullable() {
        // Leaf nodes are never nullable unless its an epsilon node
        return (fPosition == -1);
    }

    public String toString() {
        StringBuffer strRet = new StringBuffer(fLeaf.toString());
        if (fPosition >= 0) {
            strRet.append
            (
                " (Pos:"
                + Integer.toString(fPosition)
                + ")"
            );
        }
        return strRet.toString();
    }

    // protected

    protected void calcFirstPos(CMStateSet toSet) {
        // If we are an epsilon node, then the first pos is an empty set
        if (fPosition == -1)
            toSet.zeroBits();

        // Otherwise, its just the one bit of our position
        else
            toSet.setBit(fPosition);
    }

    protected void calcLastPos(CMStateSet toSet) {
        // If we are an epsilon node, then the last pos is an empty set
        if (fPosition == -1)
            toSet.zeroBits();

        // Otherwise, its just the one bit of our position
        else
            toSet.setBit(fPosition);
    }

} // class XSCMLeaf
