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
 * $Id: ContextNodeList.java,v 1.1.2.1 2005/08/01 01:30:20 jeffsuttor Exp $
 * <p>
 *  $ Id：ContextNodeList.java,v 1.1.2.1 2005/08/01 01:30:20 jeffsuttor Exp $
 * 
 */
package com.sun.org.apache.xpath.internal.axes;

import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;

/**
 * Classes who implement this interface can be a
 * <a href="http://www.w3.org/TR/xslt#dt-current-node-list">current node list</a>,
 * also refered to here as a <term>context node list</term>.
 * @xsl.usage advanced
 * <p>
 *  实现此接口的类可以是<a href="http://www.w3.org/TR/xslt#dt-current-node-list">当前节点列表</a>,这里也称为<term>上下文节点列表</term>
 * 。
 *  @ xsl.usage advanced。
 * 
 */
public interface ContextNodeList
{

  /**
   * Get the <a href="http://www.w3.org/TR/xslt#dt-current-node">current node</a>.
   *
   *
   * <p>
   *  获取<a href="http://www.w3.org/TR/xslt#dt-current-node">当前节点</a>。
   * 
   * 
   * @return The current node, or null.
   */
  public Node getCurrentNode();

  /**
   * Get the current position, which is one less than
   * the next nextNode() call will retrieve.  i.e. if
   * you call getCurrentPos() and the return is 0, the next
   * fetch will take place at index 1.
   *
   * <p>
   *  获取当前位置,比下一个nextNode()调用少一个。即如果调用getCurrentPos()并且返回值为0,则下一次提取将在索引1处进行。
   * 
   * 
   * @return The position of the
   * <a href="http://www.w3.org/TR/xslt#dt-current-node">current node</a>
   * in the  <a href="http://www.w3.org/TR/xslt#dt-current-node-list">current node list</a>.
   */
  public int getCurrentPos();

  /**
   * Reset the iterator.
   * <p>
   *  重置迭代器。
   * 
   */
  public void reset();

  /**
   * If setShouldCacheNodes(true) is called, then nodes will
   * be cached.  They are not cached by default.
   *
   * <p>
   *  如果调用setShouldCacheNodes(true),则将缓存节点。默认情况下,它们不缓存。
   * 
   * 
   * @param b true if the nodes should be cached.
   */
  public void setShouldCacheNodes(boolean b);

  /**
   * If an index is requested, NodeSetDTM will call this method
   * to run the iterator to the index.  By default this sets
   * m_next to the index.  If the index argument is -1, this
   * signals that the iterator should be run to the end.
   *
   * <p>
   * 如果请求索引,NodeSetDTM将调用此方法来运行迭代器到索引。默认情况下,这将m_next设置为索引。如果index参数是-1,这表示迭代器应该运行到结束。
   * 
   * 
   * @param index The index to run to, or -1 if the iterator should be run
   *              to the end.
   */
  public void runTo(int index);

  /**
   * Set the current position in the node set.
   * <p>
   *  设置节点集中的当前位置。
   * 
   * 
   * @param i Must be a valid index.
   */
  public void setCurrentPos(int i);

  /**
   * Get the length of the list.
   *
   * <p>
   *  获取列表的长度。
   * 
   * 
   * @return The number of nodes in this node list.
   */
  public int size();

  /**
   * Tells if this NodeSetDTM is "fresh", in other words, if
   * the first nextNode() that is called will return the
   * first node in the set.
   *
   * <p>
   *  告诉这个NodeSetDTM是否"新鲜",换句话说,如果被调用的第一个nextNode()将返回集合中的第一个节点。
   * 
   * 
   * @return true if the iteration of this list has not yet begun.
   */
  public boolean isFresh();

  /**
   * Get a cloned Iterator that is reset to the start of the iteration.
   *
   * <p>
   *  获取被复位到迭代开始的克隆迭代器。
   * 
   * 
   * @return A clone of this iteration that has been reset.
   *
   * @throws CloneNotSupportedException
   */
  public NodeIterator cloneWithReset() throws CloneNotSupportedException;

  /**
   * Get a clone of this iterator.  Be aware that this operation may be
   * somewhat expensive.
   *
   *
   * <p>
   *  获取此迭代器的克隆。请注意,此操作可能有点贵。
   * 
   * 
   * @return A clone of this object.
   *
   * @throws CloneNotSupportedException
   */
  public Object clone() throws CloneNotSupportedException;

  /**
   * Get the index of the last node in this list.
   *
   *
   * <p>
   *  获取此列表中最后一个节点的索引。
   * 
   * 
   * @return the index of the last node in this list.
   */
  public int getLast();

  /**
   * Set the index of the last node in this list.
   *
   *
   * <p>
   *  设置此列表中最后一个节点的索引。
   * 
   * @param last the index of the last node in this list.
   */
  public void setLast(int last);
}
