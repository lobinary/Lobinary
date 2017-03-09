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
 * $Id: DTMAxisIterator.java,v 1.2.4.1 2005/09/15 08:14:52 suresh_emailid Exp $
 * <p>
 *  $ Id：DTMAxisIterator.java,v 1.2.4.1 2005/09/15 08:14:52 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.dtm;

/**
 * This class iterates over a single XPath Axis, and returns node handles.
 * <p>
 *  此类遍历单个XPath Axis,并返回节点句柄。
 * 
 */
public interface DTMAxisIterator extends Cloneable
{

  /** Specifies the end of the iteration, and is the same as DTM.NULL.  */
  public static final int END = DTM.NULL;

  /**
   * Get the next node in the iteration.
   *
   * <p>
   *  获取迭代中的下一个节点。
   * 
   * 
   * @return The next node handle in the iteration, or END.
   */
  public int next();


  /**
   * Resets the iterator to the last start node.
   *
   * <p>
   *  将迭代器重置为最后一个起始节点。
   * 
   * 
   * @return A DTMAxisIterator, which may or may not be the same as this
   *         iterator.
   */
  public DTMAxisIterator reset();

  /**
  /* <p>
  /* 
   * @return the number of nodes in this iterator.  This may be an expensive
   * operation when called the first time.
   */
  public int getLast();

  /**
  /* <p>
  /* 
   * @return The position of the current node in the set, as defined by XPath.
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
   *
   * <p>
   *  将start设置为END应该'关闭'迭代器,即对next()的后续调用应该返回END。
   * 
   * 
   * @param node Sets the root of the iteration.
   *
   * @return A DTMAxisIterator set to the start of the iteration.
   */
  public DTMAxisIterator setStartNode(int node);

  /**
   * Get start to END should 'close' the iterator,
   * i.e. subsequent call to next() should return END.
   *
   * <p>
   *  获取开始到END应该"关闭"迭代器,即,next()的后续调用应该返回END。
   * 
   * 
   * @return The root node of the iteration.
   */
  public int getStartNode();

  /**
  /* <p>
  /* 
   * @return true if this iterator has a reversed axis, else false.
   */
  public boolean isReverse();

  /**
  /* <p>
  /* 
   * @return a deep copy of this iterator. The clone should not be reset
   * from its current position.
   */
  public DTMAxisIterator cloneIterator();

  /**
   * Set if restartable.
   * <p>
   *  如果可重新启动,请设置。
   * 
   */
  public void setRestartable(boolean isRestartable);

  /**
   * Return the node at the given position.
   *
   * <p>
   *  返回给定位置的节点。
   * 
   * @param position The position
   * @return The node at the given position.
   */
  public int getNodeByPosition(int position);
}
