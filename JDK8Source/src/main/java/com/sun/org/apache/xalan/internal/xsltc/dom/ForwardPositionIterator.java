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
 * $Id: ForwardPositionIterator.java,v 1.2.4.1 2005/09/06 06:22:05 pvedula Exp $
 * <p>
 *  $ Id：ForwardPositionIterator.java,v 1.2.4.1 2005/09/06 06:22:05 pvedula Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.dom;

import com.sun.org.apache.xalan.internal.xsltc.runtime.BasisLibrary;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.dtm.ref.DTMAxisIteratorBase;

/**
 * This iterator is a wrapper that always returns the position of
 * a node in document order. It is needed for the case where
 * a call to position() occurs in the context of an XSLT element
 * such as xsl:for-each, xsl:apply-templates, etc.
 *
 * The getPosition() methods in DTMAxisIterators defined
 * in DTMDefaultBaseIterators always return the position
 * in document order, which is backwards for XPath in the
 * case of the ancestor, ancestor-or-self, previous and
 * previous-sibling.
 *
 * XSLTC implements position() with the
 * BasisLibrary.positionF() method, and uses the
 * DTMAxisIterator.isReverse() method to determine
 * whether the result of getPosition() should be
 * interpreted as being equal to position().
 * But when the expression appears in apply-templates of
 * for-each, the position() function operates in document
 * order.
 *
 * The only effect of the ForwardPositionIterator is to force
 * the result of isReverse() to false, so that
 * BasisLibrary.positionF() calculates position() in a way
 * that's consistent with the context in which the
 * iterator is being used."
 *
 * (Apparently the correction of isReverse() occurs
 * implicitly, by inheritance. This class also appears
 * to maintain its own position counter, which seems
 * redundant.)
 *
 * <p>
 *  这个迭代器是一个包装器,它总是以文档顺序返回节点的位置。
 * 它对于在XSLT元素(例如xsl：for-each,xsl：apply-templates等)的上下文中调用position()的情况是需要的。
 * 
 *  在DTMDefaultBaseIterators中定义的DTMAxisIterators中的getPosition()方法总是返回文档顺序中的位置,在祖先,祖先或自己,上一个和上一个同级的情况下,XP
 * ath是反向的。
 * 
 * XSLTC使用BasisLibrary.positionF()方法实现position(),并使用DTMAxisIterator.isReverse()方法确定getPosition()的结果是否应解释
 * 为等于position()。
 * 
 * @deprecated This class exists only for backwards compatibility with old
 *             translets.  New code should not reference it.
 */
public final class ForwardPositionIterator extends DTMAxisIteratorBase {

    private DTMAxisIterator _source;

    public ForwardPositionIterator(DTMAxisIterator source) {
        _source = source;
    }

    public DTMAxisIterator cloneIterator() {
        try {
            final ForwardPositionIterator clone =
                (ForwardPositionIterator) super.clone();
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

    public int next() {
        return returnNode(_source.next());
    }

    public DTMAxisIterator setStartNode(int node) {
        _source.setStartNode(node);
        return this;
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
