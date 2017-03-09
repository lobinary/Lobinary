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
 * $Id: NodeIterator.java,v 1.2.4.1 2005/08/31 10:26:27 pvedula Exp $
 * <p>
 *  $ Id：NodeIterator.java,v 1.2.4.1 2005/08/31 10:26:27 pvedula Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc;

import com.sun.org.apache.xml.internal.dtm.DTM;

/**
/* <p>
/* 
 * @author Jacek Ambroziak
 * @author Santiago Pericas-Geertsen
 */
public interface NodeIterator extends Cloneable {
    public static final int END = DTM.NULL;

    /**
     * Callers should not call next() after it returns END.
     * <p>
     *  调用者在返回END后不应调用next()。
     * 
     */
    public int next();

    /**
     * Resets the iterator to the last start node.
     * <p>
     *  将迭代器重置为最后一个起始节点。
     * 
     */
    public NodeIterator reset();

    /**
     * Returns the number of elements in this iterator.
     * <p>
     *  返回此迭代器中的元素数。
     * 
     */
    public int getLast();

    /**
     * Returns the position of the current node in the set.
     * <p>
     *  返回集合中当前节点的位置。
     * 
     */
    public int getPosition();

    /**
     * Remembers the current node for the next call to gotoMark().
     * <p>
     *  记住下一次调用gotoMark()的当前节点。
     * 
     */
    public void setMark();

    /**
     * Restores the current node remembered by setMark().
     * <p>
     *  恢复由setMark()记住的当前节点。
     * 
     */
    public void gotoMark();

    /**
     * Set start to END should 'close' the iterator,
     * i.e. subsequent call to next() should return END.
     * <p>
     *  将start设置为END应该'关闭'迭代器,即对next()的后续调用应该返回END。
     * 
     */
    public NodeIterator setStartNode(int node);

    /**
     * True if this iterator has a reversed axis.
     * <p>
     *  如果此迭代器具有反转轴,则为true。
     * 
     */
    public boolean isReverse();

    /**
     * Returns a deep copy of this iterator.
     * <p>
     *  返回此迭代器的深度副本。
     * 
     */
    public NodeIterator cloneIterator();

    /**
     * Prevents or allows iterator restarts.
     * <p>
     *  阻止或允许迭代器重新启动。
     */
    public void setRestartable(boolean isRestartable);

}
