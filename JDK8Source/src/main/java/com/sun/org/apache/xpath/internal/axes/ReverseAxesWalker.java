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
 * $Id: ReverseAxesWalker.java,v 1.2.4.1 2005/09/14 19:45:21 jeffsuttor Exp $
 * <p>
 *  $ Id：ReverseAxesWalker.java,v 1.2.4.1 2005/09/14 19:45:21 jeffsuttor Exp $
 * 
 */
package com.sun.org.apache.xpath.internal.axes;

import com.sun.org.apache.xml.internal.dtm.DTM;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xpath.internal.XPathContext;

/**
 * Walker for a reverse axes.
 * <p>
 *  沃克为反向轴。
 * 
 * 
 * @see <a href="http://www.w3.org/TR/xpath#predicates">XPath 2.4 Predicates</a>
 */
public class ReverseAxesWalker extends AxesWalker
{
    static final long serialVersionUID = 2847007647832768941L;

  /**
   * Construct an AxesWalker using a LocPathIterator.
   *
   * <p>
   *  使用LocPathIterator构造AxesWalker。
   * 
   * 
   * @param locPathIterator The location path iterator that 'owns' this walker.
   */
  ReverseAxesWalker(LocPathIterator locPathIterator, int axis)
  {
    super(locPathIterator, axis);
  }

  /**
   * Set the root node of the TreeWalker.
   * (Not part of the DOM2 TreeWalker interface).
   *
   * <p>
   *  设置TreeWalker的根节点。 (不是DOM2 TreeWalker接口的一部分)。
   * 
   * 
   * @param root The context node of this step.
   */
  public void setRoot(int root)
  {
    super.setRoot(root);
    m_iterator = getDTM(root).getAxisIterator(m_axis);
    m_iterator.setStartNode(root);
  }

  /**
   * Detaches the walker from the set which it iterated over, releasing
   * any computational resources and placing the iterator in the INVALID
   * state.
   * <p>
   *  将步行器从迭代的集合中分离,释放任何计算资源并将迭代器置于INVALID状态。
   * 
   */
  public void detach()
  {
    m_iterator = null;
    super.detach();
  }

  /**
   * Get the next node in document order on the axes.
   *
   * <p>
   *  获取轴上文档顺序中的下一个节点。
   * 
   * 
   * @return the next node in document order on the axes, or null.
   */
  protected int getNextNode()
  {
    if (m_foundLast)
      return DTM.NULL;

    int next = m_iterator.next();

    if (m_isFresh)
      m_isFresh = false;

    if (DTM.NULL == next)
      this.m_foundLast = true;

    return next;
  }


  /**
   * Tells if this is a reverse axes.  Overrides AxesWalker#isReverseAxes.
   *
   * <p>
   *  告诉这是否是一个反向轴。覆盖AxesWalker#isReverseAxes。
   * 
   * 
   * @return true for this class.
   */
  public boolean isReverseAxes()
  {
    return true;
  }

//  /**
//   *  Set the root node of the TreeWalker.
//   *
//   * <p>
//   *  // *设置TreeWalker的根节点。 // *
//   * 
//   * 
//   * @param root The context node of this step.
//   */
//  public void setRoot(int root)
//  {
//    super.setRoot(root);
//  }

  /**
   * Get the current sub-context position.  In order to do the
   * reverse axes count, for the moment this re-searches the axes
   * up to the predicate.  An optimization on this is to cache
   * the nodes searched, but, for the moment, this case is probably
   * rare enough that the added complexity isn't worth it.
   *
   * <p>
   * 获取当前子上下文位置。为了做反向轴计数,暂时重新搜索轴直到谓词。对此的一个优化是缓存搜索的节点,但是,这种情况可能是足够稀少,增加的复杂性是不值得的。
   * 
   * 
   * @param predicateIndex The predicate index of the proximity position.
   *
   * @return The pridicate index, or -1.
   */
  protected int getProximityPosition(int predicateIndex)
  {
    // A negative predicate index seems to occur with
    // (preceding-sibling::*|following-sibling::*)/ancestor::*[position()]/*[position()]
    // -sb
    if(predicateIndex < 0)
      return -1;

    int count = m_proximityPositions[predicateIndex];

    if (count <= 0)
    {
      AxesWalker savedWalker = wi().getLastUsedWalker();

      try
      {
        ReverseAxesWalker clone = (ReverseAxesWalker) this.clone();

        clone.setRoot(this.getRoot());

        clone.setPredicateCount(predicateIndex);

        clone.setPrevWalker(null);
        clone.setNextWalker(null);
        wi().setLastUsedWalker(clone);

        // Count 'em all
        count++;
        int next;

        while (DTM.NULL != (next = clone.nextNode()))
        {
          count++;
        }

        m_proximityPositions[predicateIndex] = count;
      }
      catch (CloneNotSupportedException cnse)
      {

        // can't happen
      }
      finally
      {
        wi().setLastUsedWalker(savedWalker);
      }
    }

    return count;
  }

  /**
   * Count backwards one proximity position.
   *
   * <p>
   *  // -sb if(predicateIndex <0)return -1;
   * 
   *  int count = m_proximityPositions [predicateIndex];
   * 
   *  if(count <= 0){AxesWalker savedWalker = wi()。getLastUsedWalker();
   * 
   *  try {ReverseAxesWalker clone =(ReverseAxesWalker)this.clone();
   * 
   *  clone.setRoot(this.getRoot());
   * 
   *  clone.setPredicateCount(predicateIndex);
   * 
   *  clone.setPrevWalker(null); clone.setNextWalker(null); wi()。setLastUsedWalker(clone);
   * 
   *  // Count'em all count ++; int next;
   * 
   * 
   * @param i The predicate index.
   */
  protected void countProximityPosition(int i)
  {
    if (i < m_proximityPositions.length)
      m_proximityPositions[i]--;
  }

  /**
   * Get the number of nodes in this node list.  The function is probably ill
   * named?
   *
   *
   * <p>
   *  while(DTM.NULL！=(next = clone.nextNode())){count ++; }}
   * 
   *  m_proximityPositions [predicateIndex] = count; } catch(CloneNotSupportedException cnse){
   * 
   *  //不能发生} finally {wi()。setLastUsedWalker(savedWalker); }}
   * 
   *  return count; }}
   * 
   * 
   * @param xctxt The XPath runtime context.
   *
   * @return the number of nodes in this node list.
   */
  public int getLastPos(XPathContext xctxt)
  {

    int count = 0;
    AxesWalker savedWalker = wi().getLastUsedWalker();

    try
    {
      ReverseAxesWalker clone = (ReverseAxesWalker) this.clone();

      clone.setRoot(this.getRoot());

      clone.setPredicateCount(this.getPredicateCount() - 1);

      clone.setPrevWalker(null);
      clone.setNextWalker(null);
      wi().setLastUsedWalker(clone);

      // Count 'em all
      // count = 1;
      int next;

      while (DTM.NULL != (next = clone.nextNode()))
      {
        count++;
      }
    }
    catch (CloneNotSupportedException cnse)
    {

      // can't happen
    }
    finally
    {
      wi().setLastUsedWalker(savedWalker);
    }

    return count;
  }

  /**
   * Returns true if all the nodes in the iteration well be returned in document
   * order.
   * Warning: This can only be called after setRoot has been called!
   *
   * <p>
   *  / **向后计算一个接近位置。
   * 
   * 
   * @return false.
   */
  public boolean isDocOrdered()
  {
    return false;  // I think.
  }

  /** The DTM inner traversal class, that corresponds to the super axis. */
  protected DTMAxisIterator m_iterator;
}
