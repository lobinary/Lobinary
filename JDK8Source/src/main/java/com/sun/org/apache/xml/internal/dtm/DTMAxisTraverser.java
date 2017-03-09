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
 * $Id: DTMAxisTraverser.java,v 1.2.4.1 2005/09/15 08:14:52 suresh_emailid Exp $
 * <p>
 *  $ Id：DTMAxisTraverser.java,v 1.2.4.1 2005/09/15 08:14:52 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.dtm;

/**
 * A class that implements traverses DTMAxisTraverser interface can traverse
 * a set of nodes, usually as defined by an XPath axis.  It is different from
 * an iterator, because it does not need to hold state, and, in fact, must not
 * hold any iteration-based state.  It is meant to be implemented as an inner
 * class of a DTM, and returned by the getAxisTraverser(final int axis)
 * function.
 *
 * <p>A DTMAxisTraverser can probably not traverse a reverse axis in
 * document order.</p>
 *
 * <p>Typical usage:</p>
 * <pre><code>
 * for(int nodeHandle=myTraverser.first(myContext);
 *     nodeHandle!=DTM.NULL;
 *     nodeHandle=myTraverser.next(myContext,nodeHandle))
 * { ... processing for node indicated by nodeHandle goes here ... }
 * </code></pre>
 *
 * <p>
 *  实现遍历DTMAxisTraverser接口的类可以遍历一组节点,通常由XPath轴定义。它不同于迭代器,因为它不需要保持状态,并且事实上不能保持任何基于迭代的状态。
 * 它被实现为DTM的内部类,并由getAxisTraverser(final int axis)函数返回。
 * 
 *  <p> DTMAxisTraverser可能无法以文档顺序遍历反向轴。</p>
 * 
 *  <p>典型用法：</p> <pre> <code> for(int nodeHandle = myTraverser.first(myContext); nodeHandle！= DTM.NULL; 
 * nodeHandle = myTraverser.next(myContext,nodeHandle)){...由nodeHandle指示的节点的处理在这里...} </code> </pre>。
 * 
 * 
 * @author Scott Boag
 */
public abstract class DTMAxisTraverser
{

  /**
   * By the nature of the stateless traversal, the context node can not be
   * returned or the iteration will go into an infinate loop.  So to traverse
   * an axis, the first function must be used to get the first node.
   *
   * <p>This method needs to be overloaded only by those axis that process
   * the self node. <\p>
   *
   * <p>
   * 由于无状态遍历的​​性质,上下文节点不能被返回或迭代将进入一个infimate循环。因此,要遍历一个轴,必须使用第一个函数来获取第一个节点。
   * 
   *  <p>此方法需要仅由处理自身节点的那些轴重载。 <\ p>
   * 
   * 
   * @param context The context node of this traversal. This is the point
   * that the traversal starts from.
   * @return the first node in the traversal.
   */
  public int first(int context)
  {
    return next(context, context);
  }

  /**
   * By the nature of the stateless traversal, the context node can not be
   * returned or the iteration will go into an infinate loop.  So to traverse
   * an axis, the first function must be used to get the first node.
   *
   * <p>This method needs to be overloaded only by those axis that process
   * the self node. <\p>
   *
   * <p>
   *  由于无状态遍历的​​性质,上下文节点不能被返回或迭代将进入一个infimate循环。因此,要遍历一个轴,必须使用第一个函数来获取第一个节点。
   * 
   *  <p>此方法需要仅由处理自身节点的那些轴重载。 <\ p>
   * 
   * 
   * @param context The context node of this traversal. This is the point
   * of origin for the traversal -- its "root node" or starting point.
   * @param extendedTypeID The extended type ID that must match.
   *
   * @return the first node in the traversal.
   */
  public int first(int context, int extendedTypeID)
  {
    return next(context, context, extendedTypeID);
  }

  /**
   * Traverse to the next node after the current node.
   *
   * <p>
   *  遍历到当前节点后的下一个节点。
   * 
   * 
   * @param context The context node of this traversal. This is the point
   * of origin for the traversal -- its "root node" or starting point.
   * @param current The current node of the traversal. This is the last known
   * location in the traversal, typically the node-handle returned by the
   * previous traversal step. For the first traversal step, context
   * should be set equal to current. Note that in order to test whether
   * context is in the set, you must use the first() method instead.
   *
   * @return the next node in the iteration, or DTM.NULL.
   * @see #first(int)
   */
  public abstract int next(int context, int current);

  /**
   * Traverse to the next node after the current node that is matched
   * by the extended type ID.
   *
   * <p>
   *  遍历到由扩展类型ID匹配的当前节点之后的下一个节点。
   * 
   * @param context The context node of this traversal. This is the point
   * of origin for the traversal -- its "root node" or starting point.
   * @param current The current node of the traversal. This is the last known
   * location in the traversal, typically the node-handle returned by the
   * previous traversal step. For the first traversal step, context
   * should be set equal to current. Note that in order to test whether
   * context is in the set, you must use the first() method instead.
   * @param extendedTypeID The extended type ID that must match.
   *
   * @return the next node in the iteration, or DTM.NULL.
   * @see #first(int,int)
   */
  public abstract int next(int context, int current, int extendedTypeID);
}
