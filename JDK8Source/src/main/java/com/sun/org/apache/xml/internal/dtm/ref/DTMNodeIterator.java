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
 * $Id: DTMNodeIterator.java,v 1.2.4.1 2005/09/15 08:15:03 suresh_emailid Exp $
 * <p>
 *  $ Id：DTMNodeIterator.java,v 1.2.4.1 2005/09/15 08:15:03 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.dtm.ref;

import com.sun.org.apache.xml.internal.dtm.DTM;
import com.sun.org.apache.xml.internal.dtm.DTMDOMException;
import com.sun.org.apache.xml.internal.dtm.DTMIterator;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeFilter;

/**
 * <code>DTMNodeIterator</code> gives us an implementation of the
 * DTMNodeIterator which returns DOM nodes.
 *
 * Please note that this is not necessarily equivlaent to a DOM
 * NodeIterator operating over the same document. In particular:
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
 * promise to implement the DOM NodeIterator's "maintain current
 * position" response to document mutation. </li>
 *
 * <li>Since our design for XPath NodeIterators builds a stateful
 * filter directly into the traversal object, getNodeFilter() is not
 * supported.</li>
 *
 * </ul>
 *
 * <p>State: In progress!!</p>
 * <p>
 *  <code> DTMNodeIterator </code>为我们提供了一个返回DOM节点的DTMNodeIterator的实现。
 * 
 *  请注意,这不一定等同于在同一文档上操作的DOM NodeIterator。尤其是：
 * <ul>
 * 
 *  <li>如果有多个逻辑连续的Text节点(即跨越CDATASection和EntityReference边界),我们将只返回第一个;调用者负责遍历它们。
 *  (％REVIEW％)提供一个方便的例程来协助,等待建议的DOM Level 3 getAdjacentText()操作?)</li>。
 * 
 * <li>由于整个XPath / XSLT架构假设源文档在我们使用它时没有被更改,我们不承诺实现DOM NodeIterator的"维护当前位置"响应文档变异。 </li>
 * 
 *  <li>由于我们针对XPath NodeIterator的设计直接在遍历对象中构建状态过滤器,因此不支持getNodeFilter()。</li>
 * 
 * </ul>
 * 
 *  <p>状态：正在进行中！</p>
 * 
 * 
 * */
public class DTMNodeIterator implements org.w3c.dom.traversal.NodeIterator
{
  private DTMIterator dtm_iter;
  private boolean valid=true;

  //================================================================
  // Methods unique to this class

  /** Public constructor: Wrap a DTMNodeIterator around an existing
   * and preconfigured DTMIterator
   * <p>
   *  和预配置的DTMIterator
   * 
   * 
   * */
  public DTMNodeIterator(DTMIterator dtmIterator)
    {
      try
      {
        dtm_iter=(DTMIterator)dtmIterator.clone();
      }
      catch(CloneNotSupportedException cnse)
      {
        throw new com.sun.org.apache.xml.internal.utils.WrappedRuntimeException(cnse);
      }
    }

  /** Access the wrapped DTMIterator. I'm not sure whether anyone will
   * need this or not, but let's write it and think about it.
   * <p>
   *  需要这个或不,但让我们写它,想想它。
   * 
   * 
   * */
  public DTMIterator getDTMIterator()
    {
      return dtm_iter;
    }


  //================================================================
  // org.w3c.dom.traversal.NodeFilter API follows

  /** Detaches the NodeIterator from the set which it iterated over,
   * releasing any computational resources and placing the iterator in
   * the INVALID state.
   * <p>
   *  释放任何计算资源并将迭代器置于INVALID状态。
   * 
   * 
   * */
  public void detach()
    {
      // Theoretically, we could release dtm_iter at this point. But
      // some of the operations may still want to consult it even though
      // navigation is now invalid.
      valid=false;
    }

  /** The value of this flag determines whether the children
   * of entity reference nodes are visible to the iterator.
   *
   * <p>
   *  的实体引用节点对迭代器是可见的。
   * 
   * 
   * @return false, always (the DTM model flattens entity references)
   * */
  public boolean getExpandEntityReferences()
    {
      return false;
    }

  /** Return a handle to the filter used to screen nodes.
   *
   * This is ill-defined in Xalan's usage of Nodeiterator, where we have
   * built stateful XPath-based filtering directly into the traversal
   * object. We could return something which supports the NodeFilter interface
   * and allows querying whether a given node would be permitted if it appeared
   * as our next node, but in the current implementation that would be very
   * complex -- and just isn't all that useful.
   *
   * <p>
   *  这在Xalan对Nodeiterator的使用中没有定义,我们已经建立了基于状态的基于XPath的过滤直接进入遍历对象。
   * 我们可以返回一些支持NodeFilter接口的东西,并允许查询给定的节点是否将被允许,如果它出现作为我们的下一个节点,但在当前的实现将是非常复杂 - 只是不是所有的有用。
   * 
   * 
   * @throws DOMException -- NOT_SUPPORTED_ERROR because I can't think
   * of anything more useful to do in this case
   * */
  public NodeFilter getFilter()
    {
      throw new DTMDOMException(DOMException.NOT_SUPPORTED_ERR);
    }


  /** @return The root node of the NodeIterator, as specified
   * when it was created.
   * <p>
   *  当它被创建时。
   * 
   * 
   * */
  public Node getRoot()
    {
      int handle=dtm_iter.getRoot();
      return dtm_iter.getDTM(handle).getNode(handle);
    }


  /** Return a mask describing which node types are presented via the
   * iterator.
   * <p>
   *  迭代器。
   * 
   * 
   **/
  public int getWhatToShow()
    {
      return dtm_iter.getWhatToShow();
    }

  /** @return the next node in the set and advance the position of the
   * iterator in the set.
   *
   * <p>
   *  迭代器中的集合。
   * 
   * 
   * @throws DOMException - INVALID_STATE_ERR Raised if this method is
   * called after the detach method was invoked.
   *  */
  public Node nextNode() throws DOMException
    {
      if(!valid)
        throw new DTMDOMException(DOMException.INVALID_STATE_ERR);

      int handle=dtm_iter.nextNode();
      if (handle==DTM.NULL)
        return null;
      return dtm_iter.getDTM(handle).getNode(handle);
    }


  /** @return the next previous in the set and advance the position of the
   * iterator in the set.
   *
   * <p>
   *  迭代器中的集合。
   * 
   * @throws DOMException - INVALID_STATE_ERR Raised if this method is
   * called after the detach method was invoked.
   *  */
  public Node previousNode()
    {
      if(!valid)
        throw new DTMDOMException(DOMException.INVALID_STATE_ERR);

      int handle=dtm_iter.previousNode();
      if (handle==DTM.NULL)
        return null;
      return dtm_iter.getDTM(handle).getNode(handle);
    }
}
