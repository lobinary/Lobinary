/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 1999-2004 The Apache Software Foundation.
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
 *  版权所有1999-2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
/*
 * $Id: DTMAxisIterNodeList.java,v 1.2.4.1 2005/09/15 08:14:59 suresh_emailid Exp $
 * <p>
 *  $ Id：DTMAxisIterNodeList.java,v 1.2.4.1 2005/09/15 08:14:59 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.dtm.ref;

import com.sun.org.apache.xml.internal.dtm.DTM;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.utils.IntVector;

import org.w3c.dom.Node;

/**
 * <code>DTMAxisNodeList</code> gives us an implementation of the DOM's
 * NodeList interface wrapped around a DTM Iterator. The author
 * considers this something of an abominations, since NodeList was not
 * intended to be a general purpose "list of nodes" API and is
 * generally considered by the DOM WG to have be a mistake... but I'm
 * told that some of the XPath/XSLT folks say they must have this
 * solution.
 *
 * Please note that this is not necessarily equivlaent to a DOM
 * NodeList operating over the same document. In particular:
 * <ul>
 *
 * <li>If there are several Text nodes in logical succession (ie,
 * across CDATASection and EntityReference boundaries), we will return
 * only the first; the caller is responsible for stepping through
 * them.
 * (%REVIEW% Provide a convenience routine here to assist, pending
 * proposed DOM Level 3 getAdjacentText() operation?) </li>
 *
 * <li>Since the whole XPath/XSLT architecture assumes that the source
 * document is not altered while we're working with it, we do not
 * promise to implement the DOM NodeList's "live view" response to
 * document mutation. </li>
 *
 * </ul>
 *
 * <p>State: In progress!!</p>
 * <p>
 *  <code> DTMAxisNodeList </code>为我们提供了一个包含在DTM迭代器上的DOM的NodeList接口的实现。
 * 作者认为这是可憎的,因为NodeList不是一个通用的"节点列表"API,通常被DOM工作组认为是一个错误...但我被告知,一些XPath / XSLT的人说他们必须有这个解决方案。
 * 
 *  请注意,这不一定等同于在同一文档上操作的DOM NodeList。尤其是：
 * <ul>
 * 
 * <li>如果有多个逻辑连续的Text节点(即跨越CDATASection和EntityReference边界),我们将只返回第一个;调用者负责遍历它们。
 *  (％REVIEW％)提供一个方便的例程来协助,等待建议的DOM Level 3 getAdjacentText()操作?)</li>。
 * 
 *  <li>由于整个XPath / XSLT架构假设源文档在我们使用它时没有被更改,我们不保证实现DOM NodeList的"实时视图"响应文档变异。 </li>
 * 
 * </ul>
 * 
 *  <p>状态：正在进行中！</p>
 * 
 * */
public class DTMAxisIterNodeList extends DTMNodeListBase {
    private DTM m_dtm;
    private DTMAxisIterator m_iter;
    private IntVector m_cachedNodes;
    private int m_last = -1;
    //================================================================
    // Methods unique to this class
    private DTMAxisIterNodeList() {
    }

    /**
     * Public constructor: Wrap a DTMNodeList around an existing
     * and preconfigured DTMAxisIterator
     * <p>
     * 
     */
    public DTMAxisIterNodeList(DTM dtm, DTMAxisIterator dtmAxisIterator) {
        if (dtmAxisIterator == null) {
            m_last = 0;
        } else {
            m_cachedNodes = new IntVector();
            m_dtm = dtm;
        }
        m_iter = dtmAxisIterator;
    }

    /**
     * Access the wrapped DTMIterator. I'm not sure whether anyone will
     * need this or not, but let's write it and think about it.
     *
     * <p>
     *  公共构造函数：在现有和预配置的DTMAxisIterator周围封装DTMNodeList
     * 
     */
    public DTMAxisIterator getDTMAxisIterator() {
        return m_iter;
    }


    //================================================================
    // org.w3c.dom.NodeList API follows

    /**
     * Returns the <code>index</code>th item in the collection. If
     * <code>index</code> is greater than or equal to the number of nodes in
     * the list, this returns <code>null</code>.
     * <p>
     *  访问封装的DTMIterator。我不知道是否有人会需要这个或不,但让我们写它,并考虑它。
     * 
     * 
     * @param index Index into the collection.
     * @return The node at the <code>index</code>th position in the
     *   <code>NodeList</code>, or <code>null</code> if that is not a valid
     *   index.
     */
    public Node item(int index) {
        if (m_iter != null) {
            int node = 0;
            int count = m_cachedNodes.size();

            if (count > index) {
                node = m_cachedNodes.elementAt(index);
                return m_dtm.getNode(node);
            } else if (m_last == -1) {
                while (count <= index
                        && ((node = m_iter.next()) != DTMAxisIterator.END)) {
                    m_cachedNodes.addElement(node);
                    count++;
                }
                if (node == DTMAxisIterator.END) {
                    m_last = count;
                } else {
                    return m_dtm.getNode(node);
                }
            }
        }
        return null;
    }

    /**
     * The number of nodes in the list. The range of valid child node indices
     * is 0 to <code>length-1</code> inclusive.
     * <p>
     *  返回集合中的<code> index </code>项。如果<code> index </code>大于或等于列表中的节点数,则返回<code> null </code>。
     * 
     */
    public int getLength() {
        if (m_last == -1) {
            int node;
            while ((node = m_iter.next()) != DTMAxisIterator.END) {
                m_cachedNodes.addElement(node);
            }
            m_last = m_cachedNodes.size();
        }
        return m_last;
    }
}
