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
 * $Id: StepIterator.java,v 1.2.4.1 2005/09/06 10:26:47 pvedula Exp $
 * <p>
 *  $ Id：StepIterator.java,v 1.2.4.1 2005/09/06 10:26:47 pvedula Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.dom;

import com.sun.org.apache.xalan.internal.xsltc.runtime.BasisLibrary;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.dtm.ref.DTMAxisIteratorBase;

/**
 * A step iterator is used to evaluate expressions like "BOOK/TITLE".
 * A better name for this iterator would have been ParentIterator since
 * both "BOOK" and "TITLE" are steps in XPath lingo. Step iterators are
 * constructed from two other iterators which we are going to refer to
 * as "outer" and "inner". Every node from the outer iterator (the one
 * for BOOK in our example) is used to initialize the inner iterator.
 * After this initialization, every node from the inner iterator is
 * returned (in essence, implementing a "nested loop").
 * <p>
 *  步骤迭代器用于评估诸如"BOOK / TITLE"的表达式。这个迭代器的更好的名称将是ParentIterator,因为"BOOK"和"TITLE"是XPath lingo中的步骤。
 * 步迭代器是从另外两个迭代器构造的,我们将其称为"外部"和"内部"。来自外部迭代器(在我们的例子中为BOOK)的每个节点用于初始化内部迭代器。
 * 在这个初始化之后,来自内部迭代器的每个节点被返回(实质上,实现"嵌套循环")。
 * 
 * 
 * @author Jacek Ambroziak
 * @author Santiago Pericas-Geertsen
 * @author Erwin Bolwidt <ejb@klomp.org>
 * @author Morten Jorgensen
 */
public class StepIterator extends DTMAxisIteratorBase {

    /**
     * A reference to the "outer" iterator.
     * <p>
     *  引用"外部"迭代器。
     * 
     */
    protected DTMAxisIterator _source;

    /**
     * A reference to the "inner" iterator.
     * <p>
     *  引用"内部"迭代器。
     * 
     */
    protected DTMAxisIterator _iterator;

    /**
     * Temp variable to store a marked position.
     * <p>
     *  用于存储标记位置的Temp变量。
     */
    private int _pos = -1;

    public StepIterator(DTMAxisIterator source, DTMAxisIterator iterator) {
        _source = source;
        _iterator = iterator;
// System.out.println("SI source = " + source + " this = " + this);
// System.out.println("SI iterator = " + iterator + " this = " + this);
    }


    public void setRestartable(boolean isRestartable) {
        _isRestartable = isRestartable;
        _source.setRestartable(isRestartable);
        _iterator.setRestartable(true);         // must be restartable
    }

    public DTMAxisIterator cloneIterator() {
        _isRestartable = false;
        try {
            final StepIterator clone = (StepIterator) super.clone();
            clone._source = _source.cloneIterator();
            clone._iterator = _iterator.cloneIterator();
            clone._iterator.setRestartable(true);       // must be restartable
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
            // Set start node for left-hand iterator...
            _source.setStartNode(_startNode = node);

            // ... and get start node for right-hand iterator from left-hand,
            // with special case for //* path - see ParentLocationPath
            _iterator.setStartNode(_includeSelf ? _startNode : _source.next());
            return resetPosition();
        }
        return this;
    }

    public DTMAxisIterator reset() {
        _source.reset();
        // Special case for //* path - see ParentLocationPath
        _iterator.setStartNode(_includeSelf ? _startNode : _source.next());
        return resetPosition();
    }

    public int next() {
        for (int node;;) {
            // Try to get another node from the right-hand iterator
            if ((node = _iterator.next()) != END) {
                return returnNode(node);
            }
            // If not, get the next starting point from left-hand iterator...
            else if ((node = _source.next()) == END) {
                return END;
            }
            // ...and pass it on to the right-hand iterator
            else {
                _iterator.setStartNode(node);
            }
        }
    }

    public void setMark() {
        _source.setMark();
        _iterator.setMark();
        //_pos = _position;
    }

    public void gotoMark() {
        _source.gotoMark();
        _iterator.gotoMark();
        //_position = _pos;
    }
}
