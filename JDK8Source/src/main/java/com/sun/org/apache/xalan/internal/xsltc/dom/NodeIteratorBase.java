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
 * $Id: NodeIteratorBase.java,v 1.2.4.1 2005/09/06 09:37:02 pvedula Exp $
 * <p>
 *  $ Id：NodeIteratorBase.java,v 1.2.4.1 2005/09/06 09:37:02 pvedula Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.dom;

import com.sun.org.apache.xalan.internal.xsltc.NodeIterator;
import com.sun.org.apache.xalan.internal.xsltc.runtime.BasisLibrary;

/**
/* <p>
/* 
 * @author Jacek Ambroziak
 * @author Santiago Pericas-Geertsen
 * @author Morten Jorgensen
 */
public abstract class NodeIteratorBase implements NodeIterator {

    /**
     * Cached computed value of last().
     * <p>
     *  缓存的last()的计算值。
     * 
     */
    protected int _last = -1;

    /**
     * Value of position() in this iterator. Incremented in
     * returnNode().
     * <p>
     *  position()在此迭代器中的值。在returnNode()中递增。
     * 
     */
    protected int _position = 0;

    /**
     * Store node in call to setMark().
     * <p>
     *  在调用setMark()中存储节点。
     * 
     */
    protected int _markedNode;

    /**
     * Store node in call to setStartNode().
     * <p>
     *  在调用setStartNode()中存储节点。
     * 
     */
    protected int _startNode = NodeIterator.END;

    /**
     * Flag indicating if "self" should be returned.
     * <p>
     *  指示是否应返回"self"的标志。
     * 
     */
    protected boolean _includeSelf = false;

    /**
     * Flag indicating if iterator can be restarted.
     * <p>
     *  指示是否可以重新启动迭代器的标志。
     * 
     */
    protected boolean _isRestartable = true;

    /**
     * Setter for _isRestartable flag.
     * <p>
     *  Setter for _isRestartable标志。
     * 
     */
    public void setRestartable(boolean isRestartable) {
        _isRestartable = isRestartable;
    }

    /**
     * Initialize iterator using a node. If iterator is not
     * restartable, then do nothing. If node is equal to END then
     * subsequent calls to next() must return END.
     * <p>
     *  使用节点初始化迭代器。如果iterator不可重新启动,则什么也不做。如果node等于END,则对next()的后续调用必须返回END。
     * 
     */
    abstract public NodeIterator setStartNode(int node);

    /**
     * Reset this iterator using state from last call to
     * setStartNode().
     * <p>
     *  使用上一次调用的状态重置此迭代器到setStartNode()。
     * 
     */
    public NodeIterator reset() {
        final boolean temp = _isRestartable;
        _isRestartable = true;
        // Must adjust _startNode if self is included
        setStartNode(_includeSelf ? _startNode + 1 : _startNode);
        _isRestartable = temp;
        return this;
    }

    /**
     * Setter for _includeSelf flag.
     * <p>
     *  setter for _includeSelf标志。
     * 
     */
    public NodeIterator includeSelf() {
        _includeSelf = true;
        return this;
    }

    /**
     * Default implementation of getLast(). Stores current position
     * and current node, resets the iterator, counts all nodes and
     * restores iterator to original state.
     * <p>
     * 默认实现getLast()。存储当前位置和当前节点,重置迭代器,计数所有节点并将迭代器恢复到原始状态。
     * 
     */
    public int getLast() {
        if (_last == -1) {
            final int temp = _position;
            setMark();
            reset();
            do {
                _last++;
            } while (next() != END);
            gotoMark();
            _position = temp;
        }
        return _last;
    }

    /**
     * Returns the position() in this iterator.
     * <p>
     *  返回此迭代器中的position()。
     * 
     */
    public int getPosition() {
        return _position == 0 ? 1 : _position;
    }

    /**
     * Indicates if position in this iterator is computed in reverse
     * document order. Note that nodes are always returned in document
     * order.
     * <p>
     *  指示此迭代器中的位置是否按照反向文档顺序计算。请注意,节点总是按文档顺序返回。
     * 
     */
    public boolean isReverse() {
        return false;
    }

    /**
     * Clones and resets this iterator. Note that the cloned iterator is
     * not restartable. This is because cloning is needed for variable
     * references, and the context node of the original variable
     * declaration must be preserved.
     * <p>
     *  克隆并重置此迭代器。请注意,克隆的迭代器不可重新启动。这是因为变量引用需要克隆,并且必须保留原始变量声明的上下文节点。
     * 
     */
    public NodeIterator cloneIterator() {
        try {
            final NodeIteratorBase clone = (NodeIteratorBase)super.clone();
            clone._isRestartable = false;
            return clone.reset();
        }
        catch (CloneNotSupportedException e) {
            BasisLibrary.runTimeError(BasisLibrary.ITERATOR_CLONE_ERR,
                                      e.toString());
            return null;
        }
    }

    /**
     * Utility method that increments position and returns its
     * argument.
     * <p>
     *  实用方法,用于递增位置并返回其参数。
     * 
     */
    protected final int returnNode(final int node) {
        _position++;
        return node;
    }

    /**
     * Reset the position in this iterator.
     * <p>
     *  重置此迭代器中的位置。
     */
    protected final NodeIterator resetPosition() {
        _position = 0;
        return this;
    }
}
