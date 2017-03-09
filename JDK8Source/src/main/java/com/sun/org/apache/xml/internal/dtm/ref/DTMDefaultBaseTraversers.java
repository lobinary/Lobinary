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
 * $Id: DTMDefaultBaseTraversers.java,v 1.2.4.1 2005/09/15 08:15:00 suresh_emailid Exp $
 * <p>
 *  $ Id：DTMDefaultBaseTraversers.java,v 1.2.4.1 2005/09/15 08:15:00 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.dtm.ref;

import com.sun.org.apache.xml.internal.dtm.*;

import javax.xml.transform.Source;

import com.sun.org.apache.xml.internal.utils.XMLStringFactory;

import com.sun.org.apache.xml.internal.res.XMLErrorResources;
import com.sun.org.apache.xml.internal.res.XMLMessages;
import com.sun.org.apache.xalan.internal.xsltc.dom.NodeCounter;

/**
 * This class implements the traversers for DTMDefaultBase.
 *
 * PLEASE NOTE that the public interface for all traversers should be
 * in terms of DTM Node Handles... but they may use the internal node
 * identity indices within their logic, for efficiency's sake. Be very
 * careful to avoid confusing these when maintaining this code.
 * <p>
 *  这个类实现了DTMDefaultBase的遍历器。
 * 
 *  请注意,所有遍历器的公共接口应该是以DTM节点句柄...,但是他们可以在其逻辑内使用内部节点身份索引,以提高效率。在维护此代码时要非常小心,避免混淆这些。
 * 
 * 
 * */
public abstract class DTMDefaultBaseTraversers extends DTMDefaultBase
{

  /**
   * Construct a DTMDefaultBaseTraversers object from a DOM node.
   *
   * <p>
   *  从DOM节点构造一个DTMDefaultBaseTraversers对象。
   * 
   * 
   * @param mgr The DTMManager who owns this DTM.
   * @param source The object that is used to specify the construction source.
   * @param dtmIdentity The DTM identity ID for this DTM.
   * @param whiteSpaceFilter The white space filter for this DTM, which may
   *                         be null.
   * @param xstringfactory The factory to use for creating XMLStrings.
   * @param doIndexing true if the caller considers it worth it to use
   *                   indexing schemes.
   */
  public DTMDefaultBaseTraversers(DTMManager mgr, Source source,
                                  int dtmIdentity,
                                  DTMWSFilter whiteSpaceFilter,
                                  XMLStringFactory xstringfactory,
                                  boolean doIndexing)
  {
    super(mgr, source, dtmIdentity, whiteSpaceFilter, xstringfactory,
          doIndexing);
  }

  /**
   * Construct a DTMDefaultBaseTraversers object from a DOM node.
   *
   * <p>
   *  从DOM节点构造一个DTMDefaultBaseTraversers对象。
   * 
   * 
   * @param mgr The DTMManager who owns this DTM.
   * @param source The object that is used to specify the construction source.
   * @param dtmIdentity The DTM identity ID for this DTM.
   * @param whiteSpaceFilter The white space filter for this DTM, which may
   *                         be null.
   * @param xstringfactory The factory to use for creating XMLStrings.
   * @param doIndexing true if the caller considers it worth it to use
   *                   indexing schemes.
   * @param blocksize The block size of the DTM.
   * @param usePrevsib true if we want to build the previous sibling node array.
   * @param newNameTable true if we want to use a new ExpandedNameTable for this DTM.
   */
  public DTMDefaultBaseTraversers(DTMManager mgr, Source source,
                                  int dtmIdentity,
                                  DTMWSFilter whiteSpaceFilter,
                                  XMLStringFactory xstringfactory,
                                  boolean doIndexing,
                                  int blocksize,
                                  boolean usePrevsib,
                                  boolean newNameTable)
  {
    super(mgr, source, dtmIdentity, whiteSpaceFilter, xstringfactory,
          doIndexing, blocksize, usePrevsib, newNameTable);
  }

  /**
   * This returns a stateless "traverser", that can navigate
   * over an XPath axis, though perhaps not in document order.
   *
   * <p>
   *  这返回一个无状态的"遍历器",可以在XPath轴上导航,虽然也许不是按照文档顺序。
   * 
   * 
   * @param axis One of Axes.ANCESTORORSELF, etc.
   *
   * @return A DTMAxisTraverser, or null if the given axis isn't supported.
   */
  public DTMAxisTraverser getAxisTraverser(final int axis)
  {

    DTMAxisTraverser traverser;

    if (null == m_traversers)  // Cache of stateless traversers for this DTM
    {
      m_traversers = new DTMAxisTraverser[Axis.getNamesLength()];
      traverser = null;
    }
    else
    {
      traverser = m_traversers[axis];  // Share/reuse existing traverser

      if (traverser != null)
        return traverser;
    }

    switch (axis)  // Generate new traverser
    {
    case Axis.ANCESTOR :
      traverser = new AncestorTraverser();
      break;
    case Axis.ANCESTORORSELF :
      traverser = new AncestorOrSelfTraverser();
      break;
    case Axis.ATTRIBUTE :
      traverser = new AttributeTraverser();
      break;
    case Axis.CHILD :
      traverser = new ChildTraverser();
      break;
    case Axis.DESCENDANT :
      traverser = new DescendantTraverser();
      break;
    case Axis.DESCENDANTORSELF :
      traverser = new DescendantOrSelfTraverser();
      break;
    case Axis.FOLLOWING :
      traverser = new FollowingTraverser();
      break;
    case Axis.FOLLOWINGSIBLING :
      traverser = new FollowingSiblingTraverser();
      break;
    case Axis.NAMESPACE :
      traverser = new NamespaceTraverser();
      break;
    case Axis.NAMESPACEDECLS :
      traverser = new NamespaceDeclsTraverser();
      break;
    case Axis.PARENT :
      traverser = new ParentTraverser();
      break;
    case Axis.PRECEDING :
      traverser = new PrecedingTraverser();
      break;
    case Axis.PRECEDINGSIBLING :
      traverser = new PrecedingSiblingTraverser();
      break;
    case Axis.SELF :
      traverser = new SelfTraverser();
      break;
    case Axis.ALL :
      traverser = new AllFromRootTraverser();
      break;
    case Axis.ALLFROMNODE :
      traverser = new AllFromNodeTraverser();
      break;
    case Axis.PRECEDINGANDANCESTOR :
      traverser = new PrecedingAndAncestorTraverser();
      break;
    case Axis.DESCENDANTSFROMROOT :
      traverser = new DescendantFromRootTraverser();
      break;
    case Axis.DESCENDANTSORSELFFROMROOT :
      traverser = new DescendantOrSelfFromRootTraverser();
      break;
    case Axis.ROOT :
      traverser = new RootTraverser();
      break;
    case Axis.FILTEREDLIST :
      return null; // Don't want to throw an exception for this one.
    default :
      throw new DTMException(XMLMessages.createXMLMessage(XMLErrorResources.ER_UNKNOWN_AXIS_TYPE, new Object[]{Integer.toString(axis)})); //"Unknown axis traversal type: "+axis);
    }

    if (null == traverser)
      throw new DTMException(XMLMessages.createXMLMessage(XMLErrorResources.ER_AXIS_TRAVERSER_NOT_SUPPORTED, new Object[]{Axis.getNames(axis)}));
      // "Axis traverser not supported: "
      //                       + Axis.names[axis]);

    m_traversers[axis] = traverser;

    return traverser;
  }

  /**
   * Implements traversal of the Ancestor access, in reverse document order.
   * <p>
   *  以逆向文档顺序执行Ancestor访问的遍历。
   * 
   */
  private class AncestorTraverser extends DTMAxisTraverser
  {

    /**
     * Traverse to the next node after the current node.
     *
     * <p>
     *  遍历到当前节点后的下一个节点。
     * 
     * 
     * @param context The context node if this iteration.
     * @param current The current node of the iteration.
     *
     * @return the next node in the iteration, or DTM.NULL.
     */
    public int next(int context, int current)
    {
                        return getParent(current);
    }

    /**
     * Traverse to the next node after the current node that is matched
     * by the expanded type ID.
     *
     * <p>
     * 遍历到由扩展类型ID匹配的当前节点后的下一个节点。
     * 
     * 
     * @param context The context node of this iteration.
     * @param current The current node of the iteration.
     * @param expandedTypeID The expanded type ID that must match.
     *
     * @return the next node in the iteration, or DTM.NULL.
     */
    public int next(int context, int current, int expandedTypeID)
    {
                        // Process using identities
      current = makeNodeIdentity(current);

      while (DTM.NULL != (current = m_parent.elementAt(current)))
      {
        if (m_exptype.elementAt(current) == expandedTypeID)
          return makeNodeHandle(current);
      }

      return NULL;
    }
  }

  /**
   * Implements traversal of the Ancestor access, in reverse document order.
   * <p>
   *  以逆向文档顺序执行Ancestor访问的遍历。
   * 
   */
  private class AncestorOrSelfTraverser extends AncestorTraverser
  {

    /**
     * By the nature of the stateless traversal, the context node can not be
     * returned or the iteration will go into an infinate loop.  To see if
     * the self node should be processed, use this function.
     *
     * <p>
     *  由于无状态遍历的​​性质,上下文节点不能被返回或迭代将进入一个infimate循环。要查看是否应该处理自身节点,请使用此函数。
     * 
     * 
     * @param context The context node of this traversal.
     *
     * @return the first node in the traversal.
     */
    public int first(int context)
    {
      return context;
    }

    /**
     * By the nature of the stateless traversal, the context node can not be
     * returned or the iteration will go into an infinate loop.  To see if
     * the self node should be processed, use this function.  If the context
     * node does not match the expanded type ID, this function will return
     * false.
     *
     * <p>
     *  由于无状态遍历的​​性质,上下文节点不能被返回或迭代将进入一个infimate循环。要查看是否应该处理自身节点,请使用此函数。如果上下文节点与扩展类型ID不匹配,则此函数将返回false。
     * 
     * 
     * @param context The context node of this traversal.
     * @param expandedTypeID The expanded type ID that must match.
     *
     * @return the first node in the traversal.
     */
    public int first(int context, int expandedTypeID)
    {
                        return (getExpandedTypeID(context) == expandedTypeID)
             ? context : next(context, context, expandedTypeID);
    }
  }

  /**
   * Implements traversal of the Attribute access
   * <p>
   *  实现属性访问的遍历
   * 
   */
  private class AttributeTraverser extends DTMAxisTraverser
  {

    /**
     * Traverse to the next node after the current node.
     *
     * <p>
     *  遍历到当前节点后的下一个节点。
     * 
     * 
     * @param context The context node of this iteration.
     * @param current The current node of the iteration.
     *
     * @return the next node in the iteration, or DTM.NULL.
     */
    public int next(int context, int current)
    {
      return (context == current)
             ? getFirstAttribute(context) : getNextAttribute(current);
    }

    /**
     * Traverse to the next node after the current node that is matched
     * by the expanded type ID.
     *
     * <p>
     *  遍历到由扩展类型ID匹配的当前节点之后的下一个节点。
     * 
     * 
     * @param context The context node of this iteration.
     * @param current The current node of the iteration.
     * @param expandedTypeID The expanded type ID that must match.
     *
     * @return the next node in the iteration, or DTM.NULL.
     */
    public int next(int context, int current, int expandedTypeID)
    {

      current = (context == current)
                ? getFirstAttribute(context) : getNextAttribute(current);

      do
      {
        if (getExpandedTypeID(current) == expandedTypeID)
          return current;
      }
      while (DTM.NULL != (current = getNextAttribute(current)));

      return NULL;
    }
  }

  /**
   * Implements traversal of the Ancestor access, in reverse document order.
   * <p>
   *  以逆向文档顺序执行Ancestor访问的遍历。
   * 
   */
  private class ChildTraverser extends DTMAxisTraverser
  {

    /**
     * Get the next indexed node that matches the expanded type ID.  Before
     * calling this function, one should first call
     * {@link #isIndexed(int) isIndexed} to make sure that the index can
     * contain nodes that match the given expanded type ID.
     *
     * <p>
     *  获取与扩展类型ID匹配的下一个索引节点。在调用此函数之前,应首先调用{@link #isIndexed(int)isIndexed},以确保索引可以包含与给定扩展类型ID匹配的节点。
     * 
     * 
     * @param axisRoot The root identity of the axis.
     * @param nextPotential The node found must match or occur after this node.
     * @param expandedTypeID The expanded type ID for the request.
     *
     * @return The node ID or NULL if not found.
     */
    protected int getNextIndexed(int axisRoot, int nextPotential,
                                 int expandedTypeID)
    {

      int nsIndex = m_expandedNameTable.getNamespaceID(expandedTypeID);
      int lnIndex = m_expandedNameTable.getLocalNameID(expandedTypeID);

      for (; ; )
      {
        int nextID = findElementFromIndex(nsIndex, lnIndex, nextPotential);

        if (NOTPROCESSED != nextID)
        {
          int parentID = m_parent.elementAt(nextID);

          // Is it a child?
          if(parentID == axisRoot)
            return nextID;

          // If the parent occured before the subtree root, then
          // we know it is past the child axis.
          if(parentID < axisRoot)
              return NULL;

          // Otherwise, it could be a descendant below the subtree root
          // children, or it could be after the subtree root.  So we have
          // to climb up until the parent is less than the subtree root, in
          // which case we return NULL, or until it is equal to the subtree
          // root, in which case we continue to look.
          do
          {
            parentID = m_parent.elementAt(parentID);
            if(parentID < axisRoot)
              return NULL;
          }
            while(parentID > axisRoot);

          // System.out.println("Found node via index: "+first);
          nextPotential = nextID+1;
          continue;
        }

        nextNode();

        if(!(m_nextsib.elementAt(axisRoot) == NOTPROCESSED))
          break;
      }

      return DTM.NULL;
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
     * that the traversal starts from.
     * @return the first node in the traversal.
     */
    public int first(int context)
    {
      return getFirstChild(context);
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
     * 由于无状态遍历的​​性质,上下文节点不能被返回或迭代将进入一个infimate循环。因此,要遍历一个轴,必须使用第一个函数来获取第一个节点。
     * 
     *  <p>此方法需要仅由处理自身节点的那些轴重载。 <\ p>
     * 
     * 
     * @param context The context node of this traversal. This is the point
     * of origin for the traversal -- its "root node" or starting point.
     * @param expandedTypeID The expanded type ID that must match.
     *
     * @return the first node in the traversal.
     */
    public int first(int context, int expandedTypeID)
    {
      if(true)
      {
        int identity = makeNodeIdentity(context);

        int firstMatch = getNextIndexed(identity, _firstch(identity),
                                 expandedTypeID);

        return makeNodeHandle(firstMatch);
      }
      else
      {
                                // %REVIEW% Dead code. Eliminate?
        for (int current = _firstch(makeNodeIdentity(context));
             DTM.NULL != current;
             current = _nextsib(current))
        {
          if (m_exptype.elementAt(current) == expandedTypeID)
              return makeNodeHandle(current);
        }
        return NULL;
      }
    }

    /**
     * Traverse to the next node after the current node.
     *
     * <p>
     *  遍历到当前节点后的下一个节点。
     * 
     * 
     * @param context The context node of this iteration.
     * @param current The current node of the iteration.
     *
     * @return the next node in the iteration, or DTM.NULL.
     */
    public int next(int context, int current)
    {
      return getNextSibling(current);
    }

    /**
     * Traverse to the next node after the current node that is matched
     * by the expanded type ID.
     *
     * <p>
     *  遍历到由扩展类型ID匹配的当前节点之后的下一个节点。
     * 
     * 
     * @param context The context node of this iteration.
     * @param current The current node of the iteration.
     * @param expandedTypeID The expanded type ID that must match.
     *
     * @return the next node in the iteration, or DTM.NULL.
     */
    public int next(int context, int current, int expandedTypeID)
    {
                        // Process in Identifier space
      for (current = _nextsib(makeNodeIdentity(current));
           DTM.NULL != current;
           current = _nextsib(current))
      {
        if (m_exptype.elementAt(current) == expandedTypeID)
            return makeNodeHandle(current);
      }

      return NULL;
    }
  }

  /**
   * Super class for derived classes that want a convenient way to access
   * the indexing mechanism.
   * <p>
   *  对于想要一种方便的方式访问索引机制的派生类的超类。
   * 
   */
  private abstract class IndexedDTMAxisTraverser extends DTMAxisTraverser
  {

    /**
     * Tell if the indexing is on and the given expanded type ID matches
     * what is in the indexes.  Derived classes should call this before
     * calling {@link #getNextIndexed(int, int, int) getNextIndexed} method.
     *
     * <p>
     *  告诉索引是否已打开,并且给定的扩展类型ID与索引中的内容匹配。派生类应该在调用{@link #getNextIndexed(int,int,int)getNextIndexed}方法之前调用此方法。
     * 
     * 
     * @param expandedTypeID The expanded type ID being requested.
     *
     * @return true if it is OK to call the
     *         {@link #getNextIndexed(int, int, int) getNextIndexed} method.
     */
    protected final boolean isIndexed(int expandedTypeID)
    {
      return (m_indexing
              && ExpandedNameTable.ELEMENT
                 == m_expandedNameTable.getType(expandedTypeID));
    }

    /**
     * Tell if a node is outside the axis being traversed.  This method must be
     * implemented by derived classes, and must be robust enough to handle any
     * node that occurs after the axis root.
     *
     * <p>
     *  告诉节点是否在被遍历的轴的外部。此方法必须由派生类实现,并且必须足够强大以处理轴根后发生的任何节点。
     * 
     * 
     * @param axisRoot The root identity of the axis.
     * @param identity The node in question.
     *
     * @return true if the given node falls outside the axis being traversed.
     */
    protected abstract boolean isAfterAxis(int axisRoot, int identity);

    /**
     * Tell if the axis has been fully processed to tell if a the wait for
     * an arriving node should terminate.  This method must be implemented
     * be a derived class.
     *
     * <p>
     *  告诉轴是否已经完全处理,以告诉是否等待到达的节点应该终止。此方法必须实现为派生类。
     * 
     * 
     * @param axisRoot The root identity of the axis.
     *
     * @return true if the axis has been fully processed.
     */
    protected abstract boolean axisHasBeenProcessed(int axisRoot);

    /**
     * Get the next indexed node that matches the expanded type ID.  Before
     * calling this function, one should first call
     * {@link #isIndexed(int) isIndexed} to make sure that the index can
     * contain nodes that match the given expanded type ID.
     *
     * <p>
     *  获取与扩展类型ID匹配的下一个索引节点。在调用此函数之前,应首先调用{@link #isIndexed(int)isIndexed},以确保索引可以包含与给定扩展类型ID匹配的节点。
     * 
     * 
     * @param axisRoot The root identity of the axis.
     * @param nextPotential The node found must match or occur after this node.
     * @param expandedTypeID The expanded type ID for the request.
     *
     * @return The node ID or NULL if not found.
     */
    protected int getNextIndexed(int axisRoot, int nextPotential,
                                 int expandedTypeID)
    {

      int nsIndex = m_expandedNameTable.getNamespaceID(expandedTypeID);
      int lnIndex = m_expandedNameTable.getLocalNameID(expandedTypeID);

      while(true)
      {
        int next = findElementFromIndex(nsIndex, lnIndex, nextPotential);

        if (NOTPROCESSED != next)
        {
          if (isAfterAxis(axisRoot, next))
            return NULL;

          // System.out.println("Found node via index: "+first);
          return next;
        }
        else if(axisHasBeenProcessed(axisRoot))
          break;

        nextNode();
      }

      return DTM.NULL;
    }
  }

  /**
   * Implements traversal of the Ancestor access, in reverse document order.
   * <p>
   *  以逆向文档顺序执行Ancestor访问的遍历。
   * 
   */
  private class DescendantTraverser extends IndexedDTMAxisTraverser
  {
    /**
     * Get the first potential identity that can be returned.  This should
     * be overridded by classes that need to return the self node.
     *
     * <p>
     * 获取可以返回的第一个可能的身份。这应该被需要返回自节点的类覆盖。
     * 
     * 
     * @param identity The node identity of the root context of the traversal.
     *
     * @return The first potential node that can be in the traversal.
     */
    protected int getFirstPotential(int identity)
    {
      return identity + 1;
    }

    /**
     * Tell if the axis has been fully processed to tell if a the wait for
     * an arriving node should terminate.
     *
     * <p>
     *  告诉轴是否已经完全处理,以告诉是否等待到达的节点应该终止。
     * 
     * 
     * @param axisRoot The root identity of the axis.
     *
     * @return true if the axis has been fully processed.
     */
    protected boolean axisHasBeenProcessed(int axisRoot)
    {
      return !(m_nextsib.elementAt(axisRoot) == NOTPROCESSED);
    }

    /**
     * Get the subtree root identity from the handle that was passed in by
     * the caller.  Derived classes may override this to change the root
     * context of the traversal.
     *
     * <p>
     *  从调用者传递的句柄中获取子树根标识。派生类可以覆盖此更改遍历的根上下文。
     * 
     * 
     * @param handle handle to the root context.
     * @return identity of the root of the subtree.
     */
    protected int getSubtreeRoot(int handle)
    {
      return makeNodeIdentity(handle);
    }

    /**
     * Tell if this node identity is a descendant.  Assumes that
     * the node info for the element has already been obtained.
     *
     * %REVIEW% This is really parentFollowsRootInDocumentOrder ...
     * which fails if the parent starts after the root ends.
     * May be sufficient for this class's logic, but misleadingly named!
     *
     * <p>
     *  告诉这个节点标识是否是一个子孙。假设已经获得了元素的节点信息。
     * 
     *  ％REVIEW％这是真正的parentFollowsRootInDocumentOrder ...如果父启动在根结束后失败。可能足够了这个类的逻辑,但误导性的命名！
     * 
     * 
     * @param subtreeRootIdentity The root context of the subtree in question.
     * @param identity The index number of the node in question.
     * @return true if the index is a descendant of _startNode.
     */
    protected boolean isDescendant(int subtreeRootIdentity, int identity)
    {
      return _parent(identity) >= subtreeRootIdentity;
    }

    /**
     * Tell if a node is outside the axis being traversed.  This method must be
     * implemented by derived classes, and must be robust enough to handle any
     * node that occurs after the axis root.
     *
     * <p>
     *  告诉节点是否在被遍历的轴的外部。此方法必须由派生类实现,并且必须足够强大以处理轴根后发生的任何节点。
     * 
     * 
     * @param axisRoot The root identity of the axis.
     * @param identity The node in question.
     *
     * @return true if the given node falls outside the axis being traversed.
     */
    protected boolean isAfterAxis(int axisRoot, int identity)
    {
      // %REVIEW% Is there *any* cheaper way to do this?
                        // Yes. In ID space, compare to axisRoot's successor
                        // (next-sib or ancestor's-next-sib). Probably shallower search.
      do
      {
        if(identity == axisRoot)
          return false;
        identity = m_parent.elementAt(identity);
      }
        while(identity >= axisRoot);

      return true;
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
     * @param expandedTypeID The expanded type ID that must match.
     *
     * @return the first node in the traversal.
     */
    public int first(int context, int expandedTypeID)
    {

      if (isIndexed(expandedTypeID))
      {
        int identity = getSubtreeRoot(context);
        int firstPotential = getFirstPotential(identity);

        return makeNodeHandle(getNextIndexed(identity, firstPotential, expandedTypeID));
      }

      return next(context, context, expandedTypeID);
    }

    /**
     * Traverse to the next node after the current node.
     *
     * <p>
     *  遍历到当前节点后的下一个节点。
     * 
     * 
     * @param context The context node of this iteration.
     * @param current The current node of the iteration.
     *
     * @return the next node in the iteration, or DTM.NULL.
     */
    public int next(int context, int current)
    {

      int subtreeRootIdent = getSubtreeRoot(context);

      for (current = makeNodeIdentity(current) + 1; ; current++)
      {
        int type = _type(current);  // may call nextNode()

        if (!isDescendant(subtreeRootIdent, current))
          return NULL;

        if (ATTRIBUTE_NODE == type || NAMESPACE_NODE == type)
          continue;

        return makeNodeHandle(current);  // make handle.
      }
    }

    /**
     * Traverse to the next node after the current node that is matched
     * by the expanded type ID.
     *
     * <p>
     *  遍历到由扩展类型ID匹配的当前节点之后的下一个节点。
     * 
     * 
     * @param context The context node of this iteration.
     * @param current The current node of the iteration.
     * @param expandedTypeID The expanded type ID that must match.
     *
     * @return the next node in the iteration, or DTM.NULL.
     */
    public int next(int context, int current, int expandedTypeID)
    {

      int subtreeRootIdent = getSubtreeRoot(context);

      current = makeNodeIdentity(current) + 1;

      if (isIndexed(expandedTypeID))
      {
        return makeNodeHandle(getNextIndexed(subtreeRootIdent, current, expandedTypeID));
      }

      for (; ; current++)
      {
        int exptype = _exptype(current);  // may call nextNode()

        if (!isDescendant(subtreeRootIdent, current))
          return NULL;

        if (exptype != expandedTypeID)
          continue;

        return makeNodeHandle(current);  // make handle.
      }
    }
  }

  /**
   * Implements traversal of the Ancestor access, in reverse document order.
   * <p>
   *  以逆向文档顺序执行Ancestor访问的遍历。
   * 
   */
  private class DescendantOrSelfTraverser extends DescendantTraverser
  {

    /**
     * Get the first potential identity that can be returned, which is the
     * axis context, in this case.
     *
     * <p>
     *  获取可以返回的第一个可能的标识,在这种情况下,这是轴上下文。
     * 
     * 
     * @param identity The node identity of the root context of the traversal.
     *
     * @return The axis context.
     */
    protected int getFirstPotential(int identity)
    {
      return identity;
    }

    /**
     * By the nature of the stateless traversal, the context node can not be
     * returned or the iteration will go into an infinate loop.  To see if
     * the self node should be processed, use this function.
     *
     * <p>
     * 由于无状态遍历的​​性质,上下文节点不能被返回或迭代将进入一个infimate循环。要查看是否应该处理自身节点,请使用此函数。
     * 
     * 
     * @param context The context node of this traversal.
     *
     * @return the first node in the traversal.
     */
    public int first(int context)
    {
      return context;
    }
  }

  /**
   * Implements traversal of the entire subtree, including the root node.
   * <p>
   *  实现整个子树的遍历,包括根节点。
   * 
   */
  private class AllFromNodeTraverser extends DescendantOrSelfTraverser
  {

    /**
     * Traverse to the next node after the current node.
     *
     * <p>
     *  遍历到当前节点后的下一个节点。
     * 
     * 
     * @param context The context node of this iteration.
     * @param current The current node of the iteration.
     *
     * @return the next node in the iteration, or DTM.NULL.
     */
    public int next(int context, int current)
    {

      int subtreeRootIdent = makeNodeIdentity(context);

      for (current = makeNodeIdentity(current) + 1; ; current++)
      {
        // Trickological code: _exptype() has the side-effect of
        // running nextNode until the specified node has been loaded,
        // and thus can be used to ensure that incremental construction of
        // the DTM has gotten this far. Using it just for that side-effect
        // is quite a kluge...
        _exptype(current);  // make sure it's here.

        if (!isDescendant(subtreeRootIdent, current))
          return NULL;

        return makeNodeHandle(current);  // make handle.
      }
    }
  }

  /**
   * Implements traversal of the following access, in document order.
   * <p>
   *  按文档顺序执行以下访问的遍历。
   * 
   */
  private class FollowingTraverser extends DescendantTraverser
  {

    /**
     * Get the first of the following.
     *
     * <p>
     *  获得以下的第一个。
     * 
     * 
     * @param context The context node of this traversal. This is the point
     * that the traversal starts from.
     * @return the first node in the traversal.
     */
    public int first(int context)
    {
                        // Compute in ID space
                        context=makeNodeIdentity(context);

      int first;
      int type = _type(context);

      if ((DTM.ATTRIBUTE_NODE == type) || (DTM.NAMESPACE_NODE == type))
      {
        context = _parent(context);
        first = _firstch(context);

        if (NULL != first)
          return makeNodeHandle(first);
      }

      do
      {
        first = _nextsib(context);

        if (NULL == first)
          context = _parent(context);
      }
      while (NULL == first && NULL != context);

      return makeNodeHandle(first);
    }

    /**
     * Get the first of the following.
     *
     * <p>
     *  获得以下的第一个。
     * 
     * 
     * @param context The context node of this traversal. This is the point
     * of origin for the traversal -- its "root node" or starting point.
     * @param expandedTypeID The expanded type ID that must match.
     *
     * @return the first node in the traversal.
     */
    public int first(int context, int expandedTypeID)
    {
                        // %REVIEW% This looks like it might want shift into identity space
                        // to avoid repeated conversion in the individual functions
      int first;
      int type = getNodeType(context);

      if ((DTM.ATTRIBUTE_NODE == type) || (DTM.NAMESPACE_NODE == type))
      {
        context = getParent(context);
        first = getFirstChild(context);

        if (NULL != first)
        {
          if (getExpandedTypeID(first) == expandedTypeID)
            return first;
          else
            return next(context, first, expandedTypeID);
        }
      }

      do
      {
        first = getNextSibling(context);

        if (NULL == first)
          context = getParent(context);
        else
        {
          if (getExpandedTypeID(first) == expandedTypeID)
            return first;
          else
            return next(context, first, expandedTypeID);
        }
      }
      while (NULL == first && NULL != context);

      return first;
    }

    /**
     * Traverse to the next node after the current node.
     *
     * <p>
     *  遍历到当前节点后的下一个节点。
     * 
     * 
     * @param context The context node of this iteration.
     * @param current The current node of the iteration.
     *
     * @return the next node in the iteration, or DTM.NULL.
     */
    public int next(int context, int current)
    {
                        // Compute in identity space
                        current=makeNodeIdentity(current);

      while (true)
      {
        current++; // Only works on IDs, not handles.

                                // %REVIEW% Are we using handles or indexes?
        int type = _type(current);  // may call nextNode()

        if (NULL == type)
          return NULL;

        if (ATTRIBUTE_NODE == type || NAMESPACE_NODE == type)
          continue;

        return makeNodeHandle(current);  // make handle.
      }
    }

    /**
     * Traverse to the next node after the current node that is matched
     * by the expanded type ID.
     *
     * <p>
     *  遍历到由扩展类型ID匹配的当前节点之后的下一个节点。
     * 
     * 
     * @param context The context node of this iteration.
     * @param current The current node of the iteration.
     * @param expandedTypeID The expanded type ID that must match.
     *
     * @return the next node in the iteration, or DTM.NULL.
     */
    public int next(int context, int current, int expandedTypeID)
    {
                        // Compute in ID space
                        current=makeNodeIdentity(current);

      while (true)
      {
        current++;

        int etype = _exptype(current);  // may call nextNode()

        if (NULL == etype)
          return NULL;

        if (etype != expandedTypeID)
          continue;

        return makeNodeHandle(current);  // make handle.
      }
    }
  }

  /**
   * Implements traversal of the Ancestor access, in reverse document order.
   * <p>
   *  以逆向文档顺序执行Ancestor访问的遍历。
   * 
   */
  private class FollowingSiblingTraverser extends DTMAxisTraverser
  {

    /**
     * Traverse to the next node after the current node.
     *
     * <p>
     *  遍历到当前节点后的下一个节点。
     * 
     * 
     * @param context The context node of this iteration.
     * @param current The current node of the iteration.
     *
     * @return the next node in the iteration, or DTM.NULL.
     */
    public int next(int context, int current)
    {
      return getNextSibling(current);
    }

    /**
     * Traverse to the next node after the current node that is matched
     * by the expanded type ID.
     *
     * <p>
     *  遍历到由扩展类型ID匹配的当前节点之后的下一个节点。
     * 
     * 
     * @param context The context node of this iteration.
     * @param current The current node of the iteration.
     * @param expandedTypeID The expanded type ID that must match.
     *
     * @return the next node in the iteration, or DTM.NULL.
     */
    public int next(int context, int current, int expandedTypeID)
    {

      while (DTM.NULL != (current = getNextSibling(current)))
      {
        if (getExpandedTypeID(current) == expandedTypeID)
          return current;
      }

      return NULL;
    }
  }

  /**
   * Implements traversal of the Ancestor access, in reverse document order.
   * <p>
   *  以逆向文档顺序执行Ancestor访问的遍历。
   * 
   */
  private class NamespaceDeclsTraverser extends DTMAxisTraverser
  {

    /**
     * Traverse to the next node after the current node.
     *
     * <p>
     *  遍历到当前节点后的下一个节点。
     * 
     * 
     * @param context The context node of this iteration.
     * @param current The current node of the iteration.
     *
     * @return the next node in the iteration, or DTM.NULL.
     */
    public int next(int context, int current)
    {

      return (context == current)
             ? getFirstNamespaceNode(context, false)
             : getNextNamespaceNode(context, current, false);
    }

    /**
     * Traverse to the next node after the current node that is matched
     * by the expanded type ID.
     *
     * <p>
     *  遍历到由扩展类型ID匹配的当前节点之后的下一个节点。
     * 
     * 
     * @param context The context node of this iteration.
     * @param current The current node of the iteration.
     * @param expandedTypeID The expanded type ID that must match.
     *
     * @return the next node in the iteration, or DTM.NULL.
     */
    public int next(int context, int current, int expandedTypeID)
    {

      current = (context == current)
                ? getFirstNamespaceNode(context, false)
                : getNextNamespaceNode(context, current, false);

      do
      {
        if (getExpandedTypeID(current) == expandedTypeID)
          return current;
      }
      while (DTM.NULL
             != (current = getNextNamespaceNode(context, current, false)));

      return NULL;
    }
  }

  /**
   * Implements traversal of the Ancestor access, in reverse document order.
   * <p>
   *  以逆向文档顺序执行Ancestor访问的遍历。
   * 
   */
  private class NamespaceTraverser extends DTMAxisTraverser
  {

    /**
     * Traverse to the next node after the current node.
     *
     * <p>
     *  遍历到当前节点后的下一个节点。
     * 
     * 
     * @param context The context node of this iteration.
     * @param current The current node of the iteration.
     *
     * @return the next node in the iteration, or DTM.NULL.
     */
    public int next(int context, int current)
    {

      return (context == current)
             ? getFirstNamespaceNode(context, true)
             : getNextNamespaceNode(context, current, true);
    }

    /**
     * Traverse to the next node after the current node that is matched
     * by the expanded type ID.
     *
     * <p>
     *  遍历到由扩展类型ID匹配的当前节点之后的下一个节点。
     * 
     * 
     * @param context The context node of this iteration.
     * @param current The current node of the iteration.
     * @param expandedTypeID The expanded type ID that must match.
     *
     * @return the next node in the iteration, or DTM.NULL.
     */
    public int next(int context, int current, int expandedTypeID)
    {

      current = (context == current)
                ? getFirstNamespaceNode(context, true)
                : getNextNamespaceNode(context, current, true);

      do
      {
        if (getExpandedTypeID(current) == expandedTypeID)
          return current;
      }
      while (DTM.NULL
             != (current = getNextNamespaceNode(context, current, true)));

      return NULL;
    }
  }

  /**
   * Implements traversal of the Ancestor access, in reverse document order.
   * <p>
   *  以逆向文档顺序执行Ancestor访问的遍历。
   * 
   */
  private class ParentTraverser extends DTMAxisTraverser
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
      return getParent(context);
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
     * @param expandedTypeID The expanded type ID that must match.
     *
     * @return the first node in the traversal.
     */
    public int first(int current, int expandedTypeID)
    {
                        // Compute in ID space
      current = makeNodeIdentity(current);

      while (NULL != (current = m_parent.elementAt(current)))
      {
        if (m_exptype.elementAt(current) == expandedTypeID)
          return makeNodeHandle(current);
      }

      return NULL;
    }


    /**
     * Traverse to the next node after the current node.
     *
     * <p>
     *  遍历到当前节点后的下一个节点。
     * 
     * 
     * @param context The context node of this iteration.
     * @param current The current node of the iteration.
     *
     * @return the next node in the iteration, or DTM.NULL.
     */
    public int next(int context, int current)
    {

      return NULL;
    }



    /**
     * Traverse to the next node after the current node that is matched
     * by the expanded type ID.
     *
     * <p>
     *  遍历到由扩展类型ID匹配的当前节点之后的下一个节点。
     * 
     * 
     * @param context The context node of this iteration.
     * @param current The current node of the iteration.
     * @param expandedTypeID The expanded type ID that must match.
     *
     * @return the next node in the iteration, or DTM.NULL.
     */
    public int next(int context, int current, int expandedTypeID)
    {

      return NULL;
    }
  }

  /**
   * Implements traversal of the Ancestor access, in reverse document order.
   * <p>
   *  以逆向文档顺序执行Ancestor访问的遍历。
   * 
   */
  private class PrecedingTraverser extends DTMAxisTraverser
  {

    /**
     * Tell if the current identity is an ancestor of the context identity.
     * This is an expensive operation, made worse by the stateless traversal.
     * But the preceding axis is used fairly infrequently.
     *
     * <p>
     *  告诉当前的身份是否是上下文标识的祖先。这是一个昂贵的操作,由无状态遍历更糟。但前面的轴很少使用。
     * 
     * 
     * @param contextIdent The context node of the axis traversal.
     * @param currentIdent The node in question.
     * @return true if the currentIdent node is an ancestor of contextIdent.
     */
    protected boolean isAncestor(int contextIdent, int currentIdent)
    {
                        // %REVIEW% See comments in IsAfterAxis; using the "successor" of
                        // contextIdent is probably more efficient.
      for (contextIdent = m_parent.elementAt(contextIdent); DTM.NULL != contextIdent;
              contextIdent = m_parent.elementAt(contextIdent))
      {
        if (contextIdent == currentIdent)
          return true;
      }

      return false;
    }

    /**
     * Traverse to the next node after the current node.
     *
     * <p>
     *  遍历到当前节点后的下一个节点。
     * 
     * 
     * @param context The context node of this iteration.
     * @param current The current node of the iteration.
     *
     * @return the next node in the iteration, or DTM.NULL.
     */
    public int next(int context, int current)
    {
                        // compute in ID space
      int subtreeRootIdent = makeNodeIdentity(context);

      for (current = makeNodeIdentity(current) - 1; current >= 0; current--)
      {
        short type = _type(current);

        if (ATTRIBUTE_NODE == type || NAMESPACE_NODE == type
                || isAncestor(subtreeRootIdent, current))
          continue;

        return makeNodeHandle(current);  // make handle.
      }

      return NULL;
    }

    /**
     * Traverse to the next node after the current node that is matched
     * by the expanded type ID.
     *
     * <p>
     *  遍历到由扩展类型ID匹配的当前节点之后的下一个节点。
     * 
     * 
     * @param context The context node of this iteration.
     * @param current The current node of the iteration.
     * @param expandedTypeID The expanded type ID that must match.
     *
     * @return the next node in the iteration, or DTM.NULL.
     */
    public int next(int context, int current, int expandedTypeID)
    {
                        // Compute in ID space
      int subtreeRootIdent = makeNodeIdentity(context);

      for (current = makeNodeIdentity(current) - 1; current >= 0; current--)
      {
        int exptype = m_exptype.elementAt(current);

        if (exptype != expandedTypeID
                || isAncestor(subtreeRootIdent, current))
          continue;

        return makeNodeHandle(current);  // make handle.
      }

      return NULL;
    }
  }

  /**
   * Implements traversal of the Ancestor and the Preceding axis,
   * in reverse document order.
   * <p>
   *  以逆向文档顺序实现祖先和前导轴的遍历。
   * 
   */
  private class PrecedingAndAncestorTraverser extends DTMAxisTraverser
  {

    /**
     * Traverse to the next node after the current node.
     *
     * <p>
     *  遍历到当前节点后的下一个节点。
     * 
     * 
     * @param context The context node of this iteration.
     * @param current The current node of the iteration.
     *
     * @return the next node in the iteration, or DTM.NULL.
     */
    public int next(int context, int current)
    {
                        // Compute in ID space
      int subtreeRootIdent = makeNodeIdentity(context );

      for (current = makeNodeIdentity(current) - 1; current >= 0; current--)
      {
        short type = _type(current);

        if (ATTRIBUTE_NODE == type || NAMESPACE_NODE == type)
          continue;

        return makeNodeHandle(current);  // make handle.
      }

      return NULL;
    }

    /**
     * Traverse to the next node after the current node that is matched
     * by the expanded type ID.
     *
     * <p>
     *  遍历到由扩展类型ID匹配的当前节点之后的下一个节点。
     * 
     * 
     * @param context The context node of this iteration.
     * @param current The current node of the iteration.
     * @param expandedTypeID The expanded type ID that must match.
     *
     * @return the next node in the iteration, or DTM.NULL.
     */
    public int next(int context, int current, int expandedTypeID)
    {
                        // Compute in ID space
      int subtreeRootIdent = makeNodeIdentity(context);

      for (current = makeNodeIdentity(current) - 1; current >= 0; current--)
      {
        int exptype = m_exptype.elementAt(current);

        if (exptype != expandedTypeID)
          continue;

        return makeNodeHandle(current);  // make handle.
      }

      return NULL;
    }
  }

  /**
   * Implements traversal of the Ancestor access, in reverse document order.
   * <p>
   *  以逆向文档顺序执行Ancestor访问的遍历。
   * 
   */
  private class PrecedingSiblingTraverser extends DTMAxisTraverser
  {

    /**
     * Traverse to the next node after the current node.
     *
     * <p>
     * 遍历到当前节点后的下一个节点。
     * 
     * 
     * @param context The context node of this iteration.
     * @param current The current node of the iteration.
     *
     * @return the next node in the iteration, or DTM.NULL.
     */
    public int next(int context, int current)
    {
      return getPreviousSibling(current);
    }

    /**
     * Traverse to the next node after the current node that is matched
     * by the expanded type ID.
     *
     * <p>
     *  遍历到由扩展类型ID匹配的当前节点之后的下一个节点。
     * 
     * 
     * @param context The context node of this iteration.
     * @param current The current node of the iteration.
     * @param expandedTypeID The expanded type ID that must match.
     *
     * @return the next node in the iteration, or DTM.NULL.
     */
    public int next(int context, int current, int expandedTypeID)
    {

      while (DTM.NULL != (current = getPreviousSibling(current)))
      {
        if (getExpandedTypeID(current) == expandedTypeID)
          return current;
      }

      return NULL;
    }
  }

  /**
   * Implements traversal of the Self axis.
   * <p>
   *  实现自轴的遍历。
   * 
   */
  private class SelfTraverser extends DTMAxisTraverser
  {

    /**
     * By the nature of the stateless traversal, the context node can not be
     * returned or the iteration will go into an infinate loop.  To see if
     * the self node should be processed, use this function.
     *
     * <p>
     *  由于无状态遍历的​​性质,上下文节点不能被返回或迭代将进入一个infimate循环。要查看是否应该处理自身节点,请使用此函数。
     * 
     * 
     * @param context The context node of this traversal.
     *
     * @return the first node in the traversal.
     */
    public int first(int context)
    {
      return context;
    }

    /**
     * By the nature of the stateless traversal, the context node can not be
     * returned or the iteration will go into an infinate loop.  To see if
     * the self node should be processed, use this function.  If the context
     * node does not match the expanded type ID, this function will return
     * false.
     *
     * <p>
     *  由于无状态遍历的​​性质,上下文节点不能被返回或迭代将进入一个infimate循环。要查看是否应该处理自身节点,请使用此函数。如果上下文节点与扩展类型ID不匹配,则此函数将返回false。
     * 
     * 
     * @param context The context node of this traversal.
     * @param expandedTypeID The expanded type ID that must match.
     *
     * @return the first node in the traversal.
     */
    public int first(int context, int expandedTypeID)
    {
      return (getExpandedTypeID(context) == expandedTypeID) ? context : NULL;
    }

    /**
     * Traverse to the next node after the current node.
     *
     * <p>
     *  遍历到当前节点后的下一个节点。
     * 
     * 
     * @param context The context node of this iteration.
     * @param current The current node of the iteration.
     *
     * @return Always return NULL for this axis.
     */
    public int next(int context, int current)
    {
      return NULL;
    }

    /**
     * Traverse to the next node after the current node that is matched
     * by the expanded type ID.
     *
     * <p>
     *  遍历到由扩展类型ID匹配的当前节点之后的下一个节点。
     * 
     * 
     * @param context The context node of this iteration.
     * @param current The current node of the iteration.
     * @param expandedTypeID The expanded type ID that must match.
     *
     * @return the next node in the iteration, or DTM.NULL.
     */
    public int next(int context, int current, int expandedTypeID)
    {
      return NULL;
    }
  }

  /**
   * Implements traversal of the Ancestor access, in reverse document order.
   * <p>
   *  以逆向文档顺序执行Ancestor访问的遍历。
   * 
   */
  private class AllFromRootTraverser extends AllFromNodeTraverser
  {

    /**
     * Return the root.
     *
     * <p>
     *  返回根。
     * 
     * 
     * @param context The context node of this traversal.
     *
     * @return the first node in the traversal.
     */
    public int first(int context)
    {
      return getDocumentRoot(context);
    }

    /**
     * Return the root if it matches the expanded type ID.
     *
     * <p>
     *  如果与扩展类型ID匹配,则返回根。
     * 
     * 
     * @param context The context node of this traversal.
     * @param expandedTypeID The expanded type ID that must match.
     *
     * @return the first node in the traversal.
     */
    public int first(int context, int expandedTypeID)
    {
      return (getExpandedTypeID(getDocumentRoot(context)) == expandedTypeID)
             ? context : next(context, context, expandedTypeID);
    }

    /**
     * Traverse to the next node after the current node.
     *
     * <p>
     *  遍历到当前节点后的下一个节点。
     * 
     * 
     * @param context The context node of this iteration.
     * @param current The current node of the iteration.
     *
     * @return the next node in the iteration, or DTM.NULL.
     */
    public int next(int context, int current)
    {
                        // Compute in ID space
      int subtreeRootIdent = makeNodeIdentity(context);

      for (current = makeNodeIdentity(current) + 1; ; current++)
      {
                                // Kluge test: Just make sure +1 yielded a real node
        int type = _type(current);  // may call nextNode()
        if (type == NULL)
          return NULL;

        return makeNodeHandle(current);  // make handle.
      }
    }

    /**
     * Traverse to the next node after the current node that is matched
     * by the expanded type ID.
     *
     * <p>
     *  遍历到由扩展类型ID匹配的当前节点之后的下一个节点。
     * 
     * 
     * @param context The context node of this iteration.
     * @param current The current node of the iteration.
     * @param expandedTypeID The expanded type ID that must match.
     *
     * @return the next node in the iteration, or DTM.NULL.
     */
    public int next(int context, int current, int expandedTypeID)
    {
                        // Compute in ID space
      int subtreeRootIdent = makeNodeIdentity(context);

      for (current = makeNodeIdentity(current) + 1; ; current++)
      {
        int exptype = _exptype(current);  // may call nextNode()

        if (exptype == NULL)
          return NULL;

        if (exptype != expandedTypeID)
          continue;

        return makeNodeHandle(current);  // make handle.
      }
    }
  }

  /**
   * Implements traversal of the Self axis.
   * <p>
   *  实现自轴的遍历。
   * 
   */
  private class RootTraverser extends AllFromRootTraverser
  {
    /**
     * Return the root if it matches the expanded type ID,
     * else return null (nothing found)
     *
     * <p>
     *  返回根,如果它匹配扩展类型ID,否则返回null(没有找到)
     * 
     * 
     * @param context The context node of this traversal.
     * @param expandedTypeID The expanded type ID that must match.
     *
     * @return the first node in the traversal.
     */
    public int first(int context, int expandedTypeID)
    {
      int root=getDocumentRoot(context);
      return (getExpandedTypeID(root) == expandedTypeID)
        ? root : NULL;
    }

    /**
     * Traverse to the next node after the current node.
     *
     * <p>
     *  遍历到当前节点后的下一个节点。
     * 
     * 
     * @param context The context node of this iteration.
     * @param current The current node of the iteration.
     *
     * @return Always return NULL for this axis.
     */
    public int next(int context, int current)
    {
      return NULL;
    }

    /**
     * Traverse to the next node after the current node that is matched
     * by the expanded type ID.
     *
     * <p>
     *  遍历到由扩展类型ID匹配的当前节点之后的下一个节点。
     * 
     * 
     * @param context The context node of this iteration.
     * @param current The current node of the iteration.
     * @param expandedTypeID The expanded type ID that must match.
     *
     * @return the next node in the iteration, or DTM.NULL.
     */
    public int next(int context, int current, int expandedTypeID)
    {
      return NULL;
    }
  }

  /**
   * A non-xpath axis, returns all nodes that aren't namespaces or attributes,
   * from and including the root.
   * <p>
   *  非xpath轴返回来自并包括根的所有不是命名空间或属性的节点。
   * 
   */
  private class DescendantOrSelfFromRootTraverser extends DescendantTraverser
  {

    /**
     * Get the first potential identity that can be returned, which is the axis
     * root context in this case.
     *
     * <p>
     * 获取可以返回的第一个可能的标识,在这种情况下是轴根上下文。
     * 
     * 
     * @param identity The node identity of the root context of the traversal.
     *
     * @return The identity argument.
     */
    protected int getFirstPotential(int identity)
    {
      return identity;
    }

    /**
     * Get the first potential identity that can be returned.
     * <p>
     *  获取可以返回的第一个可能的身份。
     * 
     * 
     * @param handle handle to the root context.
     * @return identity of the root of the subtree.
     */
    protected int getSubtreeRoot(int handle)
    {
                        // %REVIEW% Shouldn't this always be 0?
      return makeNodeIdentity(getDocument());
    }

    /**
     * Return the root.
     *
     * <p>
     *  返回根。
     * 
     * 
     * @param context The context node of this traversal.
     *
     * @return the first node in the traversal.
     */
    public int first(int context)
    {
      return getDocumentRoot(context);
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
     * @param expandedTypeID The expanded type ID that must match.
     *
     * @return the first node in the traversal.
     */
    public int first(int context, int expandedTypeID)
    {
      if (isIndexed(expandedTypeID))
      {
        int identity = 0;
        int firstPotential = getFirstPotential(identity);

        return makeNodeHandle(getNextIndexed(identity, firstPotential, expandedTypeID));
      }

      int root = first(context);
      return next(root, root, expandedTypeID);
    }
  }

  /**
   * A non-xpath axis, returns all nodes that aren't namespaces or attributes,
   * from but not including the root.
   * <p>
   *  非xpath轴返回不是命名空间或属性的所有节点,但不包括根。
   * 
   */
  private class DescendantFromRootTraverser extends DescendantTraverser
  {

    /**
     * Get the first potential identity that can be returned, which is the axis
     * root context in this case.
     *
     * <p>
     *  获取可以返回的第一个可能的标识,在这种情况下是轴根上下文。
     * 
     * 
     * @param identity The node identity of the root context of the traversal.
     *
     * @return The identity argument.
     */
    protected int getFirstPotential(int identity)
    {
      return _firstch(0);
    }

    /**
     * Get the first potential identity that can be returned.
     * <p>
     *  获取可以返回的第一个可能的身份。
     * 
     * 
     * @param handle handle to the root context.
     * @return identity of the root of the subtree.
     */
    protected int getSubtreeRoot(int handle)
    {
      return 0;
    }

    /**
     * Return the root.
     *
     * <p>
     *  返回根。
     * 
     * 
     * @param context The context node of this traversal.
     *
     * @return the first node in the traversal.
     */
    public int first(int context)
    {
      return makeNodeHandle(_firstch(0));
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
     * 
     * @param context The context node of this traversal. This is the point
     * of origin for the traversal -- its "root node" or starting point.
     * @param expandedTypeID The expanded type ID that must match.
     *
     * @return the first node in the traversal.
     */
    public int first(int context, int expandedTypeID)
    {
      if (isIndexed(expandedTypeID))
      {
        int identity = 0;
        int firstPotential = getFirstPotential(identity);

        return makeNodeHandle(getNextIndexed(identity, firstPotential, expandedTypeID));
      }

      int root = getDocumentRoot(context);
      return next(root, root, expandedTypeID);
    }

  }

}
