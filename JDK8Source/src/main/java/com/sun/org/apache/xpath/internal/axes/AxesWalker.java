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
 * $Id: AxesWalker.java,v 1.2.4.1 2005/09/14 19:45:22 jeffsuttor Exp $
 * <p>
 *  $ Id：AxesWalker.java,v 1.2.4.1 2005/09/14 19:45:22 jeffsuttor Exp $
 * 
 */
package com.sun.org.apache.xpath.internal.axes;

import java.util.Vector;

import com.sun.org.apache.xalan.internal.res.XSLMessages;
import com.sun.org.apache.xml.internal.dtm.DTM;
import com.sun.org.apache.xml.internal.dtm.DTMAxisTraverser;
import com.sun.org.apache.xml.internal.dtm.DTMIterator;
import com.sun.org.apache.xpath.internal.Expression;
import com.sun.org.apache.xpath.internal.ExpressionOwner;
import com.sun.org.apache.xpath.internal.XPathContext;
import com.sun.org.apache.xpath.internal.XPathVisitor;
import com.sun.org.apache.xpath.internal.compiler.Compiler;
import com.sun.org.apache.xpath.internal.res.XPATHErrorResources;

/**
 * Serves as common interface for axes Walkers, and stores common
 * state variables.
 * <p>
 *  作为轴Walkers的通用接口,并存储常见的状态变量。
 * 
 */
public class AxesWalker extends PredicatedNodeTest
        implements Cloneable, PathComponent, ExpressionOwner
{
    static final long serialVersionUID = -2966031951306601247L;

  /**
   * Construct an AxesWalker using a LocPathIterator.
   *
   * <p>
   *  使用LocPathIterator构造AxesWalker。
   * 
   * 
   * @param locPathIterator non-null reference to the parent iterator.
   */
  public AxesWalker(LocPathIterator locPathIterator, int axis)
  {
    super( locPathIterator );
    m_axis = axis;
  }

  public final WalkingIterator wi()
  {
    return (WalkingIterator)m_lpi;
  }

  /**
   * Initialize an AxesWalker during the parse of the XPath expression.
   *
   * <p>
   *  在XPath表达式的解析期间初始化AxesWalker。
   * 
   * 
   * @param compiler The Compiler object that has information about this
   *                 walker in the op map.
   * @param opPos The op code position of this location step.
   * @param stepType  The type of location step.
   *
   * @throws javax.xml.transform.TransformerException
   */
  public void init(Compiler compiler, int opPos, int stepType)
          throws javax.xml.transform.TransformerException
  {

    initPredicateInfo(compiler, opPos);

    // int testType = compiler.getOp(nodeTestOpPos);
  }

  /**
   * Get a cloned AxesWalker.
   *
   * <p>
   *  获得一个克隆的AxesWalker。
   * 
   * 
   * @return A new AxesWalker that can be used without mutating this one.
   *
   * @throws CloneNotSupportedException
   */
  public Object clone() throws CloneNotSupportedException
  {
    // Do not access the location path itterator during this operation!

    AxesWalker clone = (AxesWalker) super.clone();

    //clone.setCurrentNode(clone.m_root);

    // clone.m_isFresh = true;

    return clone;
  }

  /**
   * Do a deep clone of this walker, including next and previous walkers.
   * If the this AxesWalker is on the clone list, don't clone but
   * return the already cloned version.
   *
   * <p>
   *  做一个这个步行者的深层克隆,包括下一个和以前的步行者。如果此AxesWalker在克隆列表上,请不要克隆,而是返回已克隆的版本。
   * 
   * 
   * @param cloneOwner non-null reference to the cloned location path
   *                   iterator to which this clone will be added.
   * @param cloneList non-null vector of sources in odd elements, and the
   *                  corresponding clones in even vectors.
   *
   * @return non-null clone, which may be a new clone, or may be a clone
   *         contained on the cloneList.
   */
  AxesWalker cloneDeep(WalkingIterator cloneOwner, Vector cloneList)
     throws CloneNotSupportedException
  {
    AxesWalker clone = findClone(this, cloneList);
    if(null != clone)
      return clone;
    clone = (AxesWalker)this.clone();
    clone.setLocPathIterator(cloneOwner);
    if(null != cloneList)
    {
      cloneList.addElement(this);
      cloneList.addElement(clone);
    }

    if(wi().m_lastUsedWalker == this)
      cloneOwner.m_lastUsedWalker = clone;

    if(null != m_nextWalker)
      clone.m_nextWalker = m_nextWalker.cloneDeep(cloneOwner, cloneList);

    // If you don't check for the cloneList here, you'll go into an
    // recursive infinate loop.
    if(null != cloneList)
    {
      if(null != m_prevWalker)
        clone.m_prevWalker = m_prevWalker.cloneDeep(cloneOwner, cloneList);
    }
    else
    {
      if(null != m_nextWalker)
        clone.m_nextWalker.m_prevWalker = clone;
    }
    return clone;
  }

  /**
   * Find a clone that corresponds to the key argument.
   *
   * <p>
   *  查找与关键参数相对应的克隆。
   * 
   * 
   * @param key The original AxesWalker for which there may be a clone.
   * @param cloneList vector of sources in odd elements, and the
   *                  corresponding clones in even vectors, may be null.
   *
   * @return A clone that corresponds to the key, or null if key not found.
   */
  static AxesWalker findClone(AxesWalker key, Vector cloneList)
  {
    if(null != cloneList)
    {
      // First, look for clone on list.
      int n = cloneList.size();
      for (int i = 0; i < n; i+=2)
      {
        if(key == cloneList.elementAt(i))
          return (AxesWalker)cloneList.elementAt(i+1);
      }
    }
    return null;
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
        m_currentNode = DTM.NULL;
        m_dtm = null;
        m_traverser = null;
        m_isFresh = true;
        m_root = DTM.NULL;
  }

  //=============== TreeWalker Implementation ===============

  /**
   * The root node of the TreeWalker, as specified in setRoot(int root).
   * Note that this may actually be below the current node.
   *
   * <p>
   *  TreeWalker的根节点,在setRoot(int root)中指定。注意,这实际上可能在当前节点之下。
   * 
   * 
   * @return The context node of the step.
   */
  public int getRoot()
  {
    return m_root;
  }

  /**
   * Get the analysis bits for this walker, as defined in the WalkerFactory.
   * <p>
   * 获取WalkerFactory中定义的此Walker的分析位。
   * 
   * 
   * @return One of WalkerFactory#BIT_DESCENDANT, etc.
   */
  public int getAnalysisBits()
  {
        int axis = getAxis();
        int bit = WalkerFactory.getAnalysisBitFromAxes(axis);
        return bit;
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
    // %OPT% Get this directly from the lpi.
    XPathContext xctxt = wi().getXPathContext();
    m_dtm = xctxt.getDTM(root);
    m_traverser = m_dtm.getAxisTraverser(m_axis);
    m_isFresh = true;
    m_foundLast = false;
    m_root = root;
    m_currentNode = root;

    if (DTM.NULL == root)
    {
      throw new RuntimeException(
        XSLMessages.createXPATHMessage(XPATHErrorResources.ER_SETTING_WALKER_ROOT_TO_NULL, null)); //"\n !!!! Error! Setting the root of a walker to null!!!");
    }

    resetProximityPositions();
  }

  /**
   * The node at which the TreeWalker is currently positioned.
   * <br> The value must not be null. Alterations to the DOM tree may cause
   * the current node to no longer be accepted by the TreeWalker's
   * associated filter. currentNode may also be explicitly set to any node,
   * whether or not it is within the subtree specified by the root node or
   * would be accepted by the filter and whatToShow flags. Further
   * traversal occurs relative to currentNode even if it is not part of the
   * current view by applying the filters in the requested direction (not
   * changing currentNode where no traversal is possible).
   *
   * <p>
   *  TreeWalker当前所在的节点。 <br>此值不能为空。对DOM树的更改可能导致当前节点不再被TreeWalker的关联过滤器接受。
   *  currentNode也可以显式地设置到任何节点,无论它是否在由根节点指定的子树中,或者将被过滤器和whatToShow标志接受。
   * 即使它不是当前视图的一部分,通过在请求的方向上应用过滤器(不改变当前节点,其中不可能进行遍历),相对于currentNode发生进一步遍历。
   * 
   * 
   * @return The node at which the TreeWalker is currently positioned, only null
   * if setRoot has not yet been called.
   */
  public final int getCurrentNode()
  {
    return m_currentNode;
  }

  /**
   * Set the next walker in the location step chain.
   *
   *
   * <p>
   *  在位置步骤链中设置下一个步行器。
   * 
   * 
   * @param walker Reference to AxesWalker derivative, or may be null.
   */
  public void setNextWalker(AxesWalker walker)
  {
    m_nextWalker = walker;
  }

  /**
   * Get the next walker in the location step chain.
   *
   *
   * <p>
   *  获取位置步骤链中的下一个步行者。
   * 
   * 
   * @return Reference to AxesWalker derivative, or null.
   */
  public AxesWalker getNextWalker()
  {
    return m_nextWalker;
  }

  /**
   * Set or clear the previous walker reference in the location step chain.
   *
   *
   * <p>
   *  在位置步骤链中设置或清除之前的助行器参考。
   * 
   * 
   * @param walker Reference to previous walker reference in the location
   *               step chain, or null.
   */
  public void setPrevWalker(AxesWalker walker)
  {
    m_prevWalker = walker;
  }

  /**
   * Get the previous walker reference in the location step chain.
   *
   *
   * <p>
   *  获取位置步骤链中的上一个助行器参考。
   * 
   * 
   * @return Reference to previous walker reference in the location
   *               step chain, or null.
   */
  public AxesWalker getPrevWalker()
  {
    return m_prevWalker;
  }

  /**
   * This is simply a way to bottle-neck the return of the next node, for
   * diagnostic purposes.
   *
   * <p>
   *  这是一个简单的方法来瓶颈下一个节点的返回,用于诊断目的。
   * 
   * 
   * @param n Node to return, or null.
   *
   * @return The argument.
   */
  private int returnNextNode(int n)
  {

    return n;
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

    if (m_isFresh)
    {
      m_currentNode = m_traverser.first(m_root);
      m_isFresh = false;
    }
    // I shouldn't have to do this the check for current node, I think.
    // numbering\numbering24.xsl fails if I don't do this.  I think
    // it occurs as the walkers are backing up. -sb
    else if(DTM.NULL != m_currentNode)
    {
      m_currentNode = m_traverser.next(m_root, m_currentNode);
    }

    if (DTM.NULL == m_currentNode)
      this.m_foundLast = true;

    return m_currentNode;
  }

  /**
   *  Moves the <code>TreeWalker</code> to the next visible node in document
   * order relative to the current node, and returns the new node. If the
   * current node has no next node,  or if the search for nextNode attempts
   * to step upward from the TreeWalker's root node, returns
   * <code>null</code> , and retains the current node.
   * <p>
   *  将<code> TreeWalker </code>移动到相对于当前节点的文档顺序中的下一个可见节点,并返回新节点。
   * 如果当前节点没有下一个节点,或者如果搜索nextNode试图从TreeWalker的根节点向上移动,则返回<code> null </code>,并保留当前节点。
   * 
   * 
   * @return  The new node, or <code>null</code> if the current node has no
   *   next node  in the TreeWalker's logical view.
   */
  public int nextNode()
  {
    int nextNode = DTM.NULL;
    AxesWalker walker = wi().getLastUsedWalker();

    while (true)
    {
      if (null == walker)
        break;

      nextNode = walker.getNextNode();

      if (DTM.NULL == nextNode)
      {

        walker = walker.m_prevWalker;
      }
      else
      {
        if (walker.acceptNode(nextNode) != DTMIterator.FILTER_ACCEPT)
        {
          continue;
        }

        if (null == walker.m_nextWalker)
        {
          wi().setLastUsedWalker(walker);

          // return walker.returnNextNode(nextNode);
          break;
        }
        else
        {
          AxesWalker prev = walker;

          walker = walker.m_nextWalker;

          walker.setRoot(nextNode);

          walker.m_prevWalker = prev;

          continue;
        }
      }  // if(null != nextNode)
    }  // while(null != walker)

    return nextNode;
  }

  //============= End TreeWalker Implementation =============

  /**
   * Get the index of the last node that can be itterated to.
   *
   *
   * <p>
   *  获取可以重写的最后一个节点的索引。
   * 
   * 
   * @param xctxt XPath runtime context.
   *
   * @return the index of the last node that can be itterated to.
   */
  public int getLastPos(XPathContext xctxt)
  {

    int pos = getProximityPosition();

    AxesWalker walker;

    try
    {
      walker = (AxesWalker) clone();
    }
    catch (CloneNotSupportedException cnse)
    {
      return -1;
    }

    walker.setPredicateCount(m_predicateIndex);
    walker.setNextWalker(null);
    walker.setPrevWalker(null);

    WalkingIterator lpi = wi();
    AxesWalker savedWalker = lpi.getLastUsedWalker();

    try
    {
      lpi.setLastUsedWalker(walker);

      int next;

      while (DTM.NULL != (next = walker.nextNode()))
      {
        pos++;
      }

      // TODO: Should probably save this in the iterator.
    }
    finally
    {
      lpi.setLastUsedWalker(savedWalker);
    }

    // System.out.println("pos: "+pos);
    return pos;
  }

  //============= State Data =============

  /**
   * The DTM for the root.  This can not be used, or must be changed,
   * for the filter walker, or any walker that can have nodes
   * from multiple documents.
   * Never, ever, access this value without going through getDTM(int node).
   * <p>
   * 根的DTM。对于过滤器步行者或可以具有来自多个文档的节点的任何步行者,这不能被使用,或者必须被改变。从来没有,没有访问这个值,而不通过getDTM(int节点)。
   * 
   */
  private DTM m_dtm;

  /**
   * Set the DTM for this walker.
   *
   * <p>
   *  设置此步行器的DTM。
   * 
   * 
   * @param dtm Non-null reference to a DTM.
   */
  public void setDefaultDTM(DTM dtm)
  {
    m_dtm = dtm;
  }

  /**
   * Get the DTM for this walker.
   *
   * <p>
   *  获取此步行者的DTM。
   * 
   * 
   * @return Non-null reference to a DTM.
   */
  public DTM getDTM(int node)
  {
    //
    return wi().getXPathContext().getDTM(node);
  }

  /**
   * Returns true if all the nodes in the iteration well be returned in document
   * order.
   * Warning: This can only be called after setRoot has been called!
   *
   * <p>
   *  如果迭代中的所有节点都以文档顺序返回,则返回true。警告：这只能在setRoot被调用后调用！
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
    return m_axis;
  }

  /**
   * This will traverse the heararchy, calling the visitor for
   * each member.  If the called visitor method returns
   * false, the subtree should not be called.
   *
   * <p>
   *  这将遍历听证,调用每个成员的访问者。如果被调用的visitor方法返回false,则不应该调用子树。
   * 
   * 
   * @param owner The owner of the visitor, where that path may be
   *              rewritten if needed.
   * @param visitor The visitor whose appropriate method will be called.
   */
  public void callVisitors(ExpressionOwner owner, XPathVisitor visitor)
  {
        if(visitor.visitStep(owner, this))
        {
                callPredicateVisitors(visitor);
                if(null != m_nextWalker)
                {
                        m_nextWalker.callVisitors(this, visitor);
                }
        }
  }

  /**
  /* <p>
  /* 
   * @see ExpressionOwner#getExpression()
   */
  public Expression getExpression()
  {
    return m_nextWalker;
  }

  /**
  /* <p>
  /* 
   * @see ExpressionOwner#setExpression(Expression)
   */
  public void setExpression(Expression exp)
  {
        exp.exprSetParent(this);
        m_nextWalker = (AxesWalker)exp;
  }

    /**
    /* <p>
    /* 
     * @see Expression#deepEquals(Expression)
     */
    public boolean deepEquals(Expression expr)
    {
      if (!super.deepEquals(expr))
                return false;

      AxesWalker walker = (AxesWalker)expr;
      if(this.m_axis != walker.m_axis)
        return false;

      return true;
    }

  /**
   *  The root node of the TreeWalker, as specified when it was created.
   * <p>
   *  创建时指定的TreeWalker的根节点。
   * 
   */
  transient int m_root = DTM.NULL;

  /**
   *  The node at which the TreeWalker is currently positioned.
   * <p>
   *  TreeWalker当前所在的节点。
   * 
   */
  private transient int m_currentNode = DTM.NULL;

  /** True if an itteration has not begun.  */
  transient boolean m_isFresh;

  /** The next walker in the location step chain.
  /* <p>
  /* 
   *  @serial  */
  protected AxesWalker m_nextWalker;

  /** The previous walker in the location step chain, or null.
  /* <p>
  /* 
   *  @serial   */
  AxesWalker m_prevWalker;

  /** The traversal axis from where the nodes will be filtered. */
  protected int m_axis = -1;

  /** The DTM inner traversal class, that corresponds to the super axis. */
  protected DTMAxisTraverser m_traverser;
}
