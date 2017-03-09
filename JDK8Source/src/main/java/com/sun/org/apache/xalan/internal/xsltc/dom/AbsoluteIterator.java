/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2001-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  版权所有2001-2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
/*
 * $Id: AbsoluteIterator.java,v 1.2.4.1 2005/09/06 05:46:46 pvedula Exp $
 * <p>
 *  $ Id：AbsoluteIterator.java,v 1.2.4.1 2005/09/06 05:46:46 pvedula Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.dom;

import com.sun.org.apache.xalan.internal.xsltc.runtime.BasisLibrary;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.dtm.ref.DTMAxisIteratorBase;
import com.sun.org.apache.xml.internal.dtm.ref.DTMDefaultBase;

/**
 * Absolute iterators ignore the node that is passed to setStartNode().
 * Instead, they always start from the root node. The node passed to
 * setStartNode() is not totally useless, though. It is needed to obtain the
 * DOM mask, i.e. the index into the MultiDOM table that corresponds to the
 * DOM "owning" the node.
 *
 * The DOM mask is cached, so successive calls to setStartNode() passing
 * nodes from other DOMs will have no effect (i.e. this iterator cannot
 * migrate between DOMs).
 * <p>
 *  绝对迭代器忽略传递给setStartNode()的节点。相反,它们总是从根节点开始。传递给setStartNode()的节点并不是完全无用的。
 * 需要获得DOM掩码,即对应于"拥有"节点的DOM的MultiDOM表中的索引。
 * 
 *  DOM掩码被缓存,因此对来自其他DOM的节点传递的setStartNode()的连续调用将没有效果(即此迭代器不能在DOM之间迁移)。
 * 
 * @author Jacek Ambroziak
 * @author Santiago Pericas-Geertsen
 */
public final class AbsoluteIterator extends DTMAxisIteratorBase {

    /**
     * Source for this iterator.
     * <p>
     * 
     */
    private DTMAxisIterator _source;

    public AbsoluteIterator(DTMAxisIterator source) {
        _source = source;
// System.out.println("AI source = " + source + " this = " + this);
    }

    public void setRestartable(boolean isRestartable) {
        _isRestartable = isRestartable;
        _source.setRestartable(isRestartable);
    }

    public DTMAxisIterator setStartNode(int node) {
        _startNode = DTMDefaultBase.ROOTNODE;
        if (_isRestartable) {
            _source.setStartNode(_startNode);
            resetPosition();
        }
        return this;
    }

    public int next() {
        return returnNode(_source.next());
    }

    public DTMAxisIterator cloneIterator() {
        try {
            final AbsoluteIterator clone = (AbsoluteIterator) super.clone();
            clone._source = _source.cloneIterator();    // resets source
            clone.resetPosition();
            clone._isRestartable = false;
            return clone;
        }
        catch (CloneNotSupportedException e) {
            BasisLibrary.runTimeError(BasisLibrary.ITERATOR_CLONE_ERR,
                                      e.toString());
            return null;
        }
    }

    public DTMAxisIterator reset() {
        _source.reset();
        return resetPosition();
    }

    public void setMark() {
        _source.setMark();
    }

    public void gotoMark() {
        _source.gotoMark();
    }
}
