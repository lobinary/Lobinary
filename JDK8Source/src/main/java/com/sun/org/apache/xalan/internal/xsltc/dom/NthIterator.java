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
 * $Id: NthIterator.java,v 1.2.4.1 2005/09/06 09:57:04 pvedula Exp $
 * <p>
 *  $ Id：NthIterator.java,v 1.2.4.1 2005/09/06 09:57:04 pvedula Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.dom;

import com.sun.org.apache.xalan.internal.xsltc.runtime.BasisLibrary;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.dtm.ref.DTMAxisIteratorBase;

/**
/* <p>
/* 
 * @author Jacek Ambroziak
 * @author Morten Jorgensen
 */
public final class NthIterator extends DTMAxisIteratorBase {
    // ...[N]
    private DTMAxisIterator _source;
    private final int _position;
    private boolean _ready;

    public NthIterator(DTMAxisIterator source, int n) {
        _source = source;
        _position = n;
    }

    public void setRestartable(boolean isRestartable) {
        _isRestartable = isRestartable;
        _source.setRestartable(isRestartable);
    }

    public DTMAxisIterator cloneIterator() {
        try {
            final NthIterator clone = (NthIterator) super.clone();
            clone._source = _source.cloneIterator();    // resets source
            clone._isRestartable = false;
            return clone;
        }
        catch (CloneNotSupportedException e) {
            BasisLibrary.runTimeError(BasisLibrary.ITERATOR_CLONE_ERR,
                                      e.toString());
            return null;
        }
    }

    public int next() {
        if (_ready) {
            _ready = false;
            return _source.getNodeByPosition(_position);
        }
        return DTMAxisIterator.END;
        /*
        if (_ready && _position > 0) {
            final int pos = _source.isReverse()
                                       ? _source.getLast() - _position + 1
                                       : _position;

            _ready = false;
            int node;
            while ((node = _source.next()) != DTMAxisIterator.END) {
                if (pos == _source.getPosition()) {
                    return node;
                }
            }
        }
        return DTMAxisIterator.END;
        /* <p>
        /*  if(_ready && _position> 0){final int pos = _source.isReverse()? _source.getLast() -  _position + 1：_position;。
        /* 
        /*  _ready = false; int node; while((node = _source.next())！= DTMAxisIterator.END){if(pos == _source.getPosition()){return node; }
        */
    }

    public DTMAxisIterator setStartNode(final int node) {
        if (_isRestartable) {
            _source.setStartNode(node);
            _ready = true;
        }
        return this;
    }

    public DTMAxisIterator reset() {
        _source.reset();
        _ready = true;
        return this;
    }

    public int getLast() {
        return 1;
    }

    public int getPosition() {
        return 1;
    }

    public void setMark() {
        _source.setMark();
    }

    public void gotoMark() {
        _source.gotoMark();
    }
}
