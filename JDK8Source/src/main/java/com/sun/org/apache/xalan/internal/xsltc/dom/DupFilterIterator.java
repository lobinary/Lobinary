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
 * $Id: DupFilterIterator.java,v 1.2.4.1 2005/09/06 06:16:11 pvedula Exp $
 * <p>
 *  $ Id：DupFilterIterator.java,v 1.2.4.1 2005/09/06 06:16:11 pvedula Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.dom;

import com.sun.org.apache.xalan.internal.xsltc.runtime.BasisLibrary;
import com.sun.org.apache.xalan.internal.xsltc.util.IntegerArray;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.dtm.ref.DTMAxisIteratorBase;
import com.sun.org.apache.xml.internal.dtm.ref.DTMDefaultBase;

/**
 * Removes duplicates and sorts a source iterator. The nodes from the
 * source are collected in an array upon calling setStartNode(). This
 * array is later sorted and duplicates are ignored in next().
 * <p>
 *  删除重复项并对源迭代器排序。来自源的节点在调用setStartNode()时被收集在数组中。此数组以后排序,并且在next()中忽略重复。
 * 
 * 
 * @author G. Todd Miller
 */
public final class DupFilterIterator extends DTMAxisIteratorBase {

    /**
     * Reference to source iterator.
     * <p>
     *  引用源迭代器。
     * 
     */
    private DTMAxisIterator _source;

    /**
     * Array to cache all nodes from source.
     * <p>
     *  数组从源缓存所有节点。
     * 
     */
    private IntegerArray _nodes = new IntegerArray();

    /**
     * Index in _nodes array to current node.
     * <p>
     *  _nodes数组中的索引到当前节点。
     * 
     */
    private int _current = 0;

    /**
     * Cardinality of _nodes array.
     * <p>
     *  _nodes数组的基数。
     * 
     */
    private int _nodesSize = 0;

    /**
     * Last value returned by next().
     * <p>
     *  next()返回的最后一个值。
     * 
     */
    private int _lastNext = END;

    /**
     * Temporary variable to store _lastNext.
     * <p>
     *  用于存储_lastNext的临时变量。
     * 
     */
    private int _markedLastNext = END;

    public DupFilterIterator(DTMAxisIterator source) {
        _source = source;
// System.out.println("DFI source = " + source + " this = " + this);

        // Cache contents of id() or key() index right away. Necessary for
        // union expressions containing multiple calls to the same index, and
        // correct as well since start-node is irrelevant for id()/key() exrp.
        if (source instanceof KeyIndex) {
            setStartNode(DTMDefaultBase.ROOTNODE);
        }
    }

    /**
     * Set the start node for this iterator
     * <p>
     *  设置此迭代器的起始节点
     * 
     * @param node The start node
     * @return A reference to this node iterator
     */
    public DTMAxisIterator setStartNode(int node) {
        if (_isRestartable) {
            // KeyIndex iterators are always relative to the root node, so there
            // is never any point in re-reading the iterator (and we SHOULD NOT).
            boolean sourceIsKeyIndex = _source instanceof KeyIndex;

            if (sourceIsKeyIndex
                    && _startNode == DTMDefaultBase.ROOTNODE) {
                return this;
            }

            if (node != _startNode) {
                _source.setStartNode(_startNode = node);

                _nodes.clear();
                while ((node = _source.next()) != END) {
                    _nodes.add(node);
                }

                // Nodes produced by KeyIndex are known to be in document order.
                // Take advantage of it.
                if (!sourceIsKeyIndex) {
                    _nodes.sort();
                }
                _nodesSize = _nodes.cardinality();
                _current = 0;
                _lastNext = END;
                resetPosition();
            }
        }
        return this;
    }

    public int next() {
        while (_current < _nodesSize) {
            final int next = _nodes.at(_current++);
            if (next != _lastNext) {
                return returnNode(_lastNext = next);
            }
        }
        return END;
    }

    public DTMAxisIterator cloneIterator() {
        try {
            final DupFilterIterator clone =
                (DupFilterIterator) super.clone();
            clone._nodes = (IntegerArray) _nodes.clone();
            clone._source = _source.cloneIterator();
            clone._isRestartable = false;
            return clone.reset();
        }
        catch (CloneNotSupportedException e) {
            BasisLibrary.runTimeError(BasisLibrary.ITERATOR_CLONE_ERR,
                                      e.toString());
            return null;
        }
    }

    public void setRestartable(boolean isRestartable) {
        _isRestartable = isRestartable;
        _source.setRestartable(isRestartable);
    }

    public void setMark() {
        _markedNode = _current;
        _markedLastNext = _lastNext;    // Bugzilla 25924
    }

    public void gotoMark() {
        _current = _markedNode;
        _lastNext = _markedLastNext;    // Bugzilla 25924
    }

    public DTMAxisIterator reset() {
        _current = 0;
        _lastNext = END;
        return resetPosition();
    }
}
