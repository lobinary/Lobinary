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
 * $Id: MatchingIterator.java,v 1.2.4.1 2005/09/06 09:22:07 pvedula Exp $
 * <p>
 *  $ Id：MatchingIterator.java,v 1.2.4.1 2005/09/06 09:22:07 pvedula Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.dom;

import com.sun.org.apache.xalan.internal.xsltc.runtime.BasisLibrary;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.dtm.ref.DTMAxisIteratorBase;

/**
 * This is a special kind of iterator that takes a source iterator and a
 * node N. If initialized with a node M (the parent of N) it computes the
 * position of N amongst the children of M. This position can be obtained
 * by calling getPosition().
 * It is an iterator even though next() will never be called. It is used to
 * match patterns with a single predicate like:
 *
 *    BOOK[position() = last()]
 *
 * In this example, the source iterator will return elements of type BOOK,
 * a call to position() will return the position of N. Notice that because
 * of the way the pattern matching is implemented, N will always be a node
 * in the source since (i) it is a BOOK or the test sequence would not be
 * considered and (ii) the source iterator is initialized with M which is
 * the parent of N. Also, and still in this example, a call to last() will
 * return the number of elements in the source (i.e. the number of BOOKs).
 * <p>
 *  这是一种特殊类型的迭代器,它接受源迭代器和节点N.如果用节点M(N的父节点)初始化,它计算M在M的子节点之间的位置。这个位置可以通过调用getPosition )。
 * 它是一个迭代器,即使next()将永远不会被调用。它用于使用单个谓词匹配模式,如：。
 * 
 *  BOOK [position()= last()]
 * 
 * 在这个例子中,源迭代器将返回类型BOOK的元素,调用position()将返回N的位置。
 * 注意,由于实现模式匹配的方式,N将始终是源中的一个节点,因为( i)它是一个BOOK或者测试序列将不被考虑,并且(ii)源迭代器用作为N的父的M初始化。
 * 而且,在该示例中,对last()的调用将返回数字的元素(即BOOK的数量)。
 * 
 * @author Jacek Ambroziak
 * @author Santiago Pericas-Geertsen
 */
public final class MatchingIterator extends DTMAxisIteratorBase {

    /**
     * A reference to a source iterator.
     * <p>
     * 
     */
    private DTMAxisIterator _source;

    /**
     * The node to match.
     * <p>
     *  对源迭代器的引用。
     * 
     */
    private final int _match;

    public MatchingIterator(int match, DTMAxisIterator source) {
        _source = source;
        _match = match;
    }


    public void setRestartable(boolean isRestartable) {
        _isRestartable = isRestartable;
        _source.setRestartable(isRestartable);
    }

    public DTMAxisIterator cloneIterator() {

        try {
            final MatchingIterator clone = (MatchingIterator) super.clone();
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

    public DTMAxisIterator setStartNode(int node) {
        if (_isRestartable) {
            // iterator is not a clone
            _source.setStartNode(node);

            // Calculate the position of the node in the set
            _position = 1;
            while ((node = _source.next()) != END && node != _match) {
                _position++;
            }
        }
        return this;
    }

    public DTMAxisIterator reset() {
        _source.reset();
        return resetPosition();
    }

    public int next() {
        return _source.next();
    }

    public int getLast() {
        if (_last == -1) {
            _last = _source.getLast();
        }
        return _last;
    }

    public int getPosition() {
        return _position;
    }

    public void setMark() {
        _source.setMark();
    }

    public void gotoMark() {
        _source.gotoMark();
    }
}
