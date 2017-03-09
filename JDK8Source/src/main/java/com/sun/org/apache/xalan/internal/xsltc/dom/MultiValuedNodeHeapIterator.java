/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2001-2006 The Apache Software Foundation.
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
 *  版权所有2001-2006 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
/*
 * $Id: UnionIterator.java 337874 2004-02-16 23:06:53Z minchau $
 * <p>
 *  $ Id：UnionIterator.java 337874 2004-02-16 23：06：53Z minchau $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.dom;

import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.runtime.BasisLibrary;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.dtm.ref.DTMAxisIteratorBase;

/**
 * <p><code>MultiValuedNodeHeapIterator</code> takes a set of multi-valued
 * heap nodes and produces a merged NodeSet in document order with duplicates
 * removed.</p>
 * <p>Each multi-valued heap node (which might be a
 * {@link org.apache.xml.dtm.DTMAxisIterator}, but that's  not necessary)
 * generates DTM node handles in document order.  The class
 * maintains the multi-valued heap nodes in a heap, not surprisingly, sorted by
 * the next DTM node handle available form the heap node.</p>
 * <p>After a DTM node is pulled from the heap node that's at the top of the
 * heap, the heap node is advanced to the next DTM node handle it makes
 * available, and the heap nature of the heap is restored to ensure the next
 * DTM node handle pulled is next in document order overall.
 *
 * <p>
 * <p> <code> MultiValuedNodeHeapIterator </code>采用一组多值堆节点,并按文档顺序生成合并的节点集,其中重复项已删除。
 * </p> <p>每个多值堆节点{@link org.apache.xml.dtm.DTMAxisIterator},但这不是必需的)按文档顺序生成DTM节点句柄。
 * 该类在堆中维护多值堆节点,毫不奇怪,按照从堆节点可用的下一个DTM节点句柄进行排序。
 * </p> <p>在从节点顶部的堆节点,堆节点被提前到下一个可用的DTM节点句柄,并且堆的堆本质被恢复,以确保下一个DTM节点句柄被拉到下一个文档顺序。
 * 
 * 
 * @author Jacek Ambroziak
 * @author Santiago Pericas-Geertsen
 */
public abstract class MultiValuedNodeHeapIterator extends DTMAxisIteratorBase {
    /** wrapper for NodeIterators to support iterator
        comparison on the value of their next() method
    /* <p>
    /*  比较他们的next()方法的值
    /* 
    */

    /**
     * An abstract representation of a set of nodes that will be retrieved in
     * document order.
     * <p>
     *  将按文档顺序检索的一组节点的抽象表示。
     * 
     */
    public abstract class HeapNode implements Cloneable {
        protected int _node, _markedNode;
        protected boolean _isStartSet = false;

        /**
         * Advance to the next node represented by this {@link HeapNode}
         *
         * <p>
         *  前进到由此{@link HeapNode}表示的下一个节点
         * 
         * 
         * @return the next DTM node.
         */
        public abstract int step();


        /**
         * Creates a deep copy of this {@link HeapNode}.  The clone is not
         * reset from the current position of the original.
         *
         * <p>
         *  创建此{@link HeapNode}的深层副本。克隆不从原始的当前位置重置。
         * 
         * 
         * @return the cloned heap node
         */
        public HeapNode cloneHeapNode() {
            HeapNode clone;

            try {
                clone = (HeapNode) super.clone();
            } catch (CloneNotSupportedException e) {
                BasisLibrary.runTimeError(BasisLibrary.ITERATOR_CLONE_ERR,
                                          e.toString());
                return null;
            }

            clone._node = _node;
            clone._markedNode = _node;

            return clone;
        }

        /**
         * Remembers the current node for the next call to {@link #gotoMark()}.
         * <p>
         *  记住下一次调用{@link #gotoMark()}的当前节点。
         * 
         */
        public void setMark() {
            _markedNode = _node;
        }

        /**
         * Restores the current node remembered by {@link #setMark()}.
         * <p>
         *  恢复{@link #setMark()}记住的当前节点。
         * 
         */
        public void gotoMark() {
            _node = _markedNode;
        }

        /**
         * Performs a comparison of the two heap nodes
         *
         * <p>
         *  执行两个堆节点的比较
         * 
         * 
         * @param heapNode the heap node against which to compare
         * @return <code>true</code> if and only if the current node for this
         *         heap node is before the current node of the argument heap
         *         node in document order.
         */
        public abstract boolean isLessThan(HeapNode heapNode);

        /**
         * Sets context with respect to which this heap node is evaluated.
         *
         * <p>
         *  设置对此堆节点进行求值的上下文。
         * 
         * 
         * @param node The new context node
         * @return a {@link HeapNode} which may or may not be the same as
         *         this <code>HeapNode</code>.
         */
        public abstract HeapNode setStartNode(int node);

        /**
         * Reset the heap node back to its beginning.
         *
         * <p>
         *  将堆节点重置回其开始。
         * 
         * 
         * @return a {@link HeapNode} which may or may not be the same as
         *         this <code>HeapNode</code>.
         */
        public abstract HeapNode reset();
    } // end of HeapNode

    private static final int InitSize = 8;

    private int        _heapSize = 0;
    private int        _size = InitSize;
    private HeapNode[] _heap = new HeapNode[InitSize];
    private int        _free = 0;

    // Last node returned by this MultiValuedNodeHeapIterator to the caller of
    // next; used to prune duplicates
    private int _returnedLast;

    // cached returned last for use in gotoMark
    private int _cachedReturnedLast = END;

    // cached heap size for use in gotoMark
    private int _cachedHeapSize;


    public DTMAxisIterator cloneIterator() {
        _isRestartable = false;
        final HeapNode[] heapCopy = new HeapNode[_heap.length];
        try {
            MultiValuedNodeHeapIterator clone =
                    (MultiValuedNodeHeapIterator)super.clone();

            for (int i = 0; i < _free; i++) {
                heapCopy[i] = _heap[i].cloneHeapNode();
            }
            clone.setRestartable(false);
            clone._heap = heapCopy;
            return clone.reset();
        }
        catch (CloneNotSupportedException e) {
            BasisLibrary.runTimeError(BasisLibrary.ITERATOR_CLONE_ERR,
                                      e.toString());
            return null;
        }
    }

    protected void addHeapNode(HeapNode node) {
        if (_free == _size) {
            HeapNode[] newArray = new HeapNode[_size *= 2];
            System.arraycopy(_heap, 0, newArray, 0, _free);
            _heap = newArray;
        }
        _heapSize++;
        _heap[_free++] = node;
    }

    public int next() {
        while (_heapSize > 0) {
            final int smallest = _heap[0]._node;
            if (smallest == END) { // iterator _heap[0] is done
                if (_heapSize > 1) {
                    // Swap first and last (iterator must be restartable)
                    final HeapNode temp = _heap[0];
                    _heap[0] = _heap[--_heapSize];
                    _heap[_heapSize] = temp;
                }
                else {
                    return END;
                }
            }
            else if (smallest == _returnedLast) {       // duplicate
                _heap[0].step(); // value consumed
            }
            else {
                _heap[0].step(); // value consumed
                heapify(0);
                return returnNode(_returnedLast = smallest);
            }
            // fallthrough if not returned above
            heapify(0);
        }
        return END;
    }

    public DTMAxisIterator setStartNode(int node) {
        if (_isRestartable) {
            _startNode = node;
            for (int i = 0; i < _free; i++) {
                if(!_heap[i]._isStartSet){
                   _heap[i].setStartNode(node);
                   _heap[i].step();     // to get the first node
                   _heap[i]._isStartSet = true;
                }
            }
            // build heap
            for (int i = (_heapSize = _free)/2; i >= 0; i--) {
                heapify(i);
            }
            _returnedLast = END;
            return resetPosition();
        }
        return this;
    }

    protected void init() {
        for (int i =0; i < _free; i++) {
            _heap[i] = null;
        }

        _heapSize = 0;
        _free = 0;
    }

    /* Build a heap in document order. put the smallest node on the top.
     * "smallest node" means the node before other nodes in document order
     * <p>
     *  "最小节点"是指文档顺序中其他节点之前的节点
     */
    private void heapify(int i) {
        for (int r, l, smallest;;) {
            r = (i + 1) << 1; l = r - 1;
            smallest = l < _heapSize
                && _heap[l].isLessThan(_heap[i]) ? l : i;
            if (r < _heapSize && _heap[r].isLessThan(_heap[smallest])) {
                smallest = r;
            }
            if (smallest != i) {
                final HeapNode temp = _heap[smallest];
                _heap[smallest] = _heap[i];
                _heap[i] = temp;
                i = smallest;
            } else {
                break;
            }
        }
    }

    public void setMark() {
        for (int i = 0; i < _free; i++) {
            _heap[i].setMark();
        }
        _cachedReturnedLast = _returnedLast;
        _cachedHeapSize = _heapSize;
    }

    public void gotoMark() {
        for (int i = 0; i < _free; i++) {
            _heap[i].gotoMark();
        }
        // rebuild heap after call last() function. fix for bug 20913
        for (int i = (_heapSize = _cachedHeapSize)/2; i >= 0; i--) {
            heapify(i);
        }
        _returnedLast = _cachedReturnedLast;
    }

    public DTMAxisIterator reset() {
        for (int i = 0; i < _free; i++) {
            _heap[i].reset();
            _heap[i].step();
        }

        // build heap
        for (int i = (_heapSize = _free)/2; i >= 0; i--) {
            heapify(i);
        }

        _returnedLast = END;
        return resetPosition();
    }

}
