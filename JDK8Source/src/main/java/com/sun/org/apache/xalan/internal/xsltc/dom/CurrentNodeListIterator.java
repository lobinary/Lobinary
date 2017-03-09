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
 * $Id: CurrentNodeListIterator.java,v 1.2.4.1 2005/09/06 06:04:45 pvedula Exp $
 * <p>
 *  $ Id：CurrentNodeListIterator.java,v 1.2.4.1 2005/09/06 06:04:45 pvedula Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.dom;

import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xalan.internal.xsltc.runtime.BasisLibrary;
import com.sun.org.apache.xalan.internal.xsltc.util.IntegerArray;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.dtm.ref.DTMAxisIteratorBase;

/**
 * Iterators of this kind use a CurrentNodeListFilter to filter a subset of
 * nodes from a source iterator. For each node from the source, the boolean
 * method CurrentNodeListFilter.test() is called.
 *
 * All nodes from the source are read into an array upon calling setStartNode()
 * (this is needed to determine the value of last, a parameter to
 * CurrentNodeListFilter.test()). The method getLast() returns the last element
 * after applying the filter.
 * <p>
 *  这种类型的迭代器使用CurrentNodeListFilter来过滤来自源迭代器的节点子集。对于源中的每个节点,调用布尔方法CurrentNodeListFilter.test()。
 * 
 *  来自源的所有节点在调用setStartNode()时读取到数组中(这需要确定last的值,即CurrentNodeListFilter.test()的参数)。
 * 方法getLast()在应用过滤器后返回最后一个元素。
 * 
 * 
 * @author Jacek Ambroziak
 * @author Santiago Pericas-Geertsen
 * @author Morten Jorgensen
 */

public final class CurrentNodeListIterator extends DTMAxisIteratorBase {
    /**
     * A flag indicating if nodes are returned in document order.
     * <p>
     *  指示节点是否按文档顺序返回的标志。
     * 
     */
    private boolean _docOrder;

    /**
     * The source for this iterator.
     * <p>
     *  此迭代器的源。
     * 
     */
    private DTMAxisIterator _source;

    /**
     * A reference to a filter object.
     * <p>
     *  对过滤器对象的引用。
     * 
     */
    private final CurrentNodeListFilter _filter;

    /**
     * An integer array to store nodes from source iterator.
     * <p>
     *  用于存储源迭代器的节点的整数数组。
     * 
     */
    private IntegerArray _nodes = new IntegerArray();

    /**
     * Index in _nodes of the next node to filter.
     * <p>
     *  要过滤的下一个节点的_node中的索引。
     * 
     */
    private int _currentIndex;

    /**
     * The current node in the stylesheet at the time of evaluation.
     * <p>
     * 评估时样式表中的当前节点。
     * 
     */
    private final int _currentNode;

    /**
     * A reference to the translet.
     * <p>
     *  参考translet。
     */
    private AbstractTranslet _translet;

    public CurrentNodeListIterator(DTMAxisIterator source,
                                   CurrentNodeListFilter filter,
                                   int currentNode,
                                   AbstractTranslet translet)
    {
        this(source, !source.isReverse(), filter, currentNode, translet);
    }

    public CurrentNodeListIterator(DTMAxisIterator source, boolean docOrder,
                                   CurrentNodeListFilter filter,
                                   int currentNode,
                                   AbstractTranslet translet)
    {
        _source = source;
        _filter = filter;
        _translet = translet;
        _docOrder = docOrder;
        _currentNode = currentNode;
    }

    public DTMAxisIterator forceNaturalOrder() {
        _docOrder = true;
        return this;
    }

    public void setRestartable(boolean isRestartable) {
        _isRestartable = isRestartable;
        _source.setRestartable(isRestartable);
    }

    public boolean isReverse() {
        return !_docOrder;
    }

    public DTMAxisIterator cloneIterator() {
        try {
            final CurrentNodeListIterator clone =
                (CurrentNodeListIterator) super.clone();
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

    public DTMAxisIterator reset() {
        _currentIndex = 0;
        return resetPosition();
    }

    public int next() {
        final int last = _nodes.cardinality();
        final int currentNode = _currentNode;
        final AbstractTranslet translet = _translet;

        for (int index = _currentIndex; index < last; ) {
            final int position = _docOrder ? index + 1 : last - index;
            final int node = _nodes.at(index++);        // note increment

            if (_filter.test(node, position, last, currentNode, translet,
                             this)) {
                _currentIndex = index;
                return returnNode(node);
            }
        }
        return END;
    }

    public DTMAxisIterator setStartNode(int node) {
        if (_isRestartable) {
            _source.setStartNode(_startNode = node);

            _nodes.clear();
            while ((node = _source.next()) != END) {
                _nodes.add(node);
            }
            _currentIndex = 0;
            resetPosition();
        }
        return this;
    }

    public int getLast() {
        if (_last == -1) {
            _last = computePositionOfLast();
        }
        return _last;
    }

    public void setMark() {
        _markedNode = _currentIndex;
    }

    public void gotoMark() {
        _currentIndex = _markedNode;
    }

    private int computePositionOfLast() {
        final int last = _nodes.cardinality();
        final int currNode = _currentNode;
        final AbstractTranslet translet = _translet;

        int lastPosition = _position;
        for (int index = _currentIndex; index < last; ) {
            final int position = _docOrder ? index + 1 : last - index;
            int nodeIndex = _nodes.at(index++);         // note increment

            if (_filter.test(nodeIndex, position, last, currNode, translet,
                             this)) {
                lastPosition++;
            }
        }
        return lastPosition;
    }
}
