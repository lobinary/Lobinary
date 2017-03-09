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
 * $Id: DTMAxisIteratorBase.java,v 1.2.4.1 2005/09/15 08:14:59 suresh_emailid Exp $
 * <p>
 *  $ Id：DTMAxisIteratorBase.java,v 1.2.4.1 2005/09/15 08:14:59 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.dtm.ref;

import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;

/**
 * This class serves as a default base for implementations of mutable
 * DTMAxisIterators.
 * <p>
 *  此类用作可变DTMAxisIterator的实现的默认基础。
 * 
 */
public abstract class DTMAxisIteratorBase implements DTMAxisIterator
{

  /** The position of the last node within the iteration, as defined by XPath.
   * Note that this is _not_ the node's handle within the DTM. Also, don't
   * confuse it with the current (most recently returned) position.
   * <p>
   *  注意这是_not_在DTM中的节点的句柄。此外,不要混淆当前(最近返回)的位置。
   * 
   */
  protected int _last = -1;

  /** The position of the current node within the iteration, as defined by XPath.
   * Note that this is _not_ the node's handle within the DTM!
   * <p>
   *  注意这是_not_节点在DTM中的句柄！
   * 
   */
  protected int _position = 0;

  /** The position of the marked node within the iteration;
   * a saved itaration state that we may want to come back to.
   * Note that only one mark is maintained; there is no stack.
   * <p>
   *  一个保存的itaration状态,我们可能想回来。注意,只保留一个标记;没有堆栈。
   * 
   */
  protected int _markedNode;

  /** The handle to the start, or root, of the iteration.
   * Set this to END to construct an empty iterator.
   * <p>
   *  将此设置为END以构造空的迭代器。
   * 
   */
  protected int _startNode = DTMAxisIterator.END;

  /** True if the start node should be considered part of the iteration.
   * False will cause it to be skipped.
   * <p>
   *  False将导致其被跳过。
   * 
   */
  protected boolean _includeSelf = false;

  /** True if this iteration can be restarted. False otherwise (eg, if
   * we are iterating over a stream that can not be re-scanned, or if
   * the iterator was produced by cloning another iterator.)
   * <p>
   *  我们在不能被重新扫描的流上迭代,或者如果迭代器是通过克隆另一个迭代器来生成的)。
   * 
   */
  protected boolean _isRestartable = true;

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
  public int getStartNode()
  {
    return _startNode;
  }

  /**
  /* <p>
  /* 
   * @return A DTMAxisIterator which has been reset to the start node,
   * which may or may not be the same as this iterator.
   * */
  public DTMAxisIterator reset()
  {

    final boolean temp = _isRestartable;

    _isRestartable = true;

    setStartNode(_startNode);

    _isRestartable = temp;

    return this;
  }

  /**
   * Set the flag to include the start node in the iteration.
   *
   *
   * <p>
   * 设置标志以在迭代中包括开始节点。
   * 
   * 
   * @return This default method returns just returns this DTMAxisIterator,
   * after setting the flag.
   * (Returning "this" permits C++-style chaining of
   * method calls into a single expression.)
   */
  public DTMAxisIterator includeSelf()
  {

    _includeSelf = true;

    return this;
  }

  /** Returns the position of the last node within the iteration, as
   * defined by XPath.  In a forward iterator, I believe this equals the number of nodes which this
   * iterator will yield. In a reverse iterator, I believe it should return
   * 1 (since the "last" is the first produced.)
   *
   * This may be an expensive operation when called the first time, since
   * it may have to iterate through a large part of the document to produce
   * its answer.
   *
   * <p>
   *  由XPath定义。在前向迭代器中,我相信这等于该迭代器将产生的节点数。在反向迭代器中,我相信它应该返回1(因为"last"是第一个产生的。)
   * 
   *  当第一次调用时,这可能是昂贵的操作,因为它可能必须遍历文档的大部分以产生其答案。
   * 
   * 
   * @return The number of nodes in this iterator (forward) or 1 (reverse).
   */
  public int getLast()
  {

    if (_last == -1)            // Not previously established
    {
      // Note that we're doing both setMark() -- which saves _currentChild
      // -- and explicitly saving our position counter (number of nodes
      // yielded so far).
      //
      // %REVIEW% Should position also be saved by setMark()?
      // (It wasn't in the XSLTC version, but I don't understand why not.)

      final int temp = _position; // Save state
      setMark();

      reset();                  // Count the nodes found by this iterator
      do
      {
        _last++;
      }
      while (next() != END);

      gotoMark();               // Restore saved state
      _position = temp;
    }

    return _last;
  }

  /**
  /* <p>
  /* 
   * @return The position of the current node within the set, as defined by
   * XPath. Note that this is one-based, not zero-based.
   */
  public int getPosition()
  {
    return _position == 0 ? 1 : _position;
  }

  /**
  /* <p>
  /* 
   * @return true if this iterator has a reversed axis, else false
   */
  public boolean isReverse()
  {
    return false;
  }

  /**
   * Returns a deep copy of this iterator. Cloned iterators may not be
   * restartable. The iterator being cloned may or may not become
   * non-restartable as a side effect of this operation.
   *
   * <p>
   *  返回此迭代器的深度副本。克隆的迭代器可能无法重新启动。被克隆的迭代器可能或可能不会变得不可重新启动作为此操作的副作用。
   * 
   * 
   * @return a deep copy of this iterator.
   */
  public DTMAxisIterator cloneIterator()
  {

    try
    {
      final DTMAxisIteratorBase clone = (DTMAxisIteratorBase) super.clone();

      clone._isRestartable = false;

      // return clone.reset();
      return clone;
    }
    catch (CloneNotSupportedException e)
    {
      throw new com.sun.org.apache.xml.internal.utils.WrappedRuntimeException(e);
    }
  }

  /**
   * Do any final cleanup that is required before returning the node that was
   * passed in, and then return it. The intended use is
   * <br />
   * <code>return returnNode(node);</code>
   *
   * %REVIEW% If we're calling it purely for side effects, should we really
   * be bothering with a return value? Something like
   * <br />
   * <code> accept(node); return node; </code>
   * <br />
   * would probably optimize just about as well and avoid questions
   * about whether what's returned could ever be different from what's
   * passed in.
   *
   * <p>
   *  在返回传入的节点之前执行所需的任何最终清理,然后返回它。目的用途是
   * <br />
   *  <code> return returnNode(node); </code>
   * 
   *  ％REVIEW％如果我们调用它纯粹的副作用,我们应该真的麻烦一个返回值吗?就像是
   * <br />
   *  <code> accept(node);返回节点; </code>
   * <br />
   *  可能会优化,以及避免关于返回的内容是否可能与传递的内容不同的问题。
   * 
   * 
   * @param node Node handle which iteration is about to yield.
   *
   * @return The node handle passed in.  */
  protected final int returnNode(final int node)
  {
    _position++;

    return node;
  }

  /**
   * Reset the position to zero. NOTE that this does not change the iteration
   * state, only the position number associated with that state.
   *
   * %REVIEW% Document when this would be used?
   *
   * <p>
   *  将位置重置为零。注意,这不会更改迭代状态,只会更改与该状态关联的位置编号。
   * 
   *  ％REVIEW％将在何时使用文档?
   * 
   * 
   * @return This instance.
   */
  protected final DTMAxisIterator resetPosition()
  {

    _position = 0;

    return this;
  }

  /**
   * Returns true if all the nodes in the iteration well be returned in document
   * order.
   *
   * <p>
   *  如果迭代中的所有节点都以文档顺序返回,则返回true。
   * 
   * 
   * @return true as a default.
   */
  public boolean isDocOrdered()
  {
    return true;
  }

  /**
   * Returns the axis being iterated, if it is known.
   *
   * <p>
   *  返回正在迭代的轴(如果已知)。
   * 
   * 
   * @return Axis.CHILD, etc., or -1 if the axis is not known or is of multiple
   * types.
   */
  public int getAxis()
  {
    return -1;
  }

  public void setRestartable(boolean isRestartable) {
    _isRestartable = isRestartable;
  }

  /**
   * Return the node at the given position.
   *
   * <p>
   *  返回给定位置的节点。
   * 
   * @param position The position
   * @return The node at the given position.
   */
  public int getNodeByPosition(int position)
  {
    if (position > 0) {
      final int pos = isReverse() ? getLast() - position + 1
                                   : position;
      int node;
      while ((node = next()) != DTMAxisIterator.END) {
        if (pos == getPosition()) {
          return node;
        }
      }
    }
    return END;
  }

}
