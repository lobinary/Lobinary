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
 */

/*
 * $Id: ArrayNodeListIterator.java,v 1.0 2009-11-25 04:34:24 joehw Exp $
 * <p>
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
package com.sun.org.apache.xalan.internal.xsltc.dom;

import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;

public class ArrayNodeListIterator implements DTMAxisIterator  {

    private int _pos = 0;

    private int _mark = 0;

    private int _nodes[];

    private static final int[] EMPTY = { };

    public ArrayNodeListIterator(int[] nodes) {
        _nodes = nodes;
    }

    public int next() {
        return _pos < _nodes.length ? _nodes[_pos++] : END;
    }

    public DTMAxisIterator reset() {
        _pos = 0;
        return this;
    }

    public int getLast() {
        return _nodes.length;
    }

    public int getPosition() {
        return _pos;
    }

    public void setMark() {
        _mark = _pos;
    }

    public void gotoMark() {
        _pos = _mark;
    }

    public DTMAxisIterator setStartNode(int node) {
        if (node == END) _nodes = EMPTY;
        return this;
    }

    public int getStartNode() {
        return END;
    }

    public boolean isReverse() {
        return false;
    }

    public DTMAxisIterator cloneIterator() {
        return new ArrayNodeListIterator(_nodes);
    }

    public void setRestartable(boolean isRestartable) {
    }

    public int getNodeByPosition(int position) {
        return _nodes[position - 1];
    }

}
