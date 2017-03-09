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
 * $Id: DTMChildIterNodeList.java,v 1.2.4.1 2005/09/15 08:15:00 suresh_emailid Exp $
 * <p>
 *  $ Id：DTMChildIterNodeList.java,v 1.2.4.1 2005/09/15 08:15:00 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.dtm.ref;

import com.sun.org.apache.xml.internal.dtm.DTM;
import org.w3c.dom.Node;

/**
 * <code>DTMNodeList</code> gives us an implementation of the DOM's
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
 *  <code> DTMNodeList </code>为我们提供了一个包含在DTM迭代器上的DOM的NodeList接口的实现。
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
 * 
 * */
public class DTMChildIterNodeList extends DTMNodeListBase {
    private int m_firstChild;
    private DTM m_parentDTM;

    //================================================================
    // Methods unique to this class
    private DTMChildIterNodeList() {
    }

    /**
     * Public constructor: Create a NodeList to support
     * DTMNodeProxy.getChildren().
     *
     * Unfortunately AxisIterators and DTMIterators don't share an API,
     * so I can't use the existing Axis.CHILD iterator. Rather than
     * create Yet Another Class, let's set up a special case of this
     * one.
     *
     * <p>
     *  <p>状态：正在进行中！</p>
     * 
     * 
     * @param parentDTM The DTM containing this node
     * @param parentHandle DTM node-handle integer
     *
     */
    public DTMChildIterNodeList(DTM parentDTM,int parentHandle) {
        m_parentDTM=parentDTM;
        m_firstChild=parentDTM.getFirstChild(parentHandle);
    }


    //================================================================
    // org.w3c.dom.NodeList API follows

    /**
     * Returns the <code>index</code>th item in the collection. If
     * <code>index</code> is greater than or equal to the number of nodes in
     * the list, this returns <code>null</code>.
     * <p>
     *  公共构造函数：创建一个NodeList来支持DTMNodeProxy.getChildren()。
     * 
     *  不幸的是,AxisIterators和DTMIterators不共享一个API,所以我不能使用现有的Axis.CHILD迭代器。而不是创建另一个类,让我们设置一个特殊的情况下这一个。
     * 
     * 
     * @param index Index into the collection.
     * @return The node at the <code>index</code>th position in the
     *   <code>NodeList</code>, or <code>null</code> if that is not a valid
     *   index.
     */
    public Node item(int index) {
        int handle=m_firstChild;
        while(--index>=0 && handle!=DTM.NULL) {
            handle=m_parentDTM.getNextSibling(handle);
        }
        if (handle == DTM.NULL) {
            return null;
        }
        return m_parentDTM.getNode(handle);
    }

    /**
     * The number of nodes in the list. The range of valid child node indices
     * is 0 to <code>length-1</code> inclusive.
     * <p>
     *  返回集合中的<code> index </code>项。如果<code> index </code>大于或等于列表中的节点数,则返回<code> null </code>。
     * 
     */
    public int getLength() {
        int count=0;
        for (int handle=m_firstChild;
             handle!=DTM.NULL;
             handle=m_parentDTM.getNextSibling(handle)) {
            ++count;
        }
        return count;
    }
}
