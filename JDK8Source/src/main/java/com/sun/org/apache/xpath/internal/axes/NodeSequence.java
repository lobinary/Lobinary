/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2002-2004 The Apache Software Foundation.
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
 *  版权所有2002-2004 Apache软件基金会
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
/*
 * $Id: NodeSequence.java,v 1.6 2007/01/12 19:26:42 spericas Exp $
 * <p>
 *  $ Id：NodeSequence.java,v 1.6 2007/01/12 19:26:42 spericas Exp $
 * 
 */
package com.sun.org.apache.xpath.internal.axes;

import java.util.Vector;

import com.sun.org.apache.xml.internal.dtm.DTM;
import com.sun.org.apache.xml.internal.dtm.DTMFilter;
import com.sun.org.apache.xml.internal.dtm.DTMIterator;
import com.sun.org.apache.xml.internal.dtm.DTMManager;
import com.sun.org.apache.xml.internal.utils.NodeVector;
import com.sun.org.apache.xpath.internal.NodeSetDTM;
import com.sun.org.apache.xpath.internal.XPathContext;
import com.sun.org.apache.xpath.internal.objects.XObject;

/**
 * This class is the dynamic wrapper for a Xalan DTMIterator instance, and
 * provides random access capabilities.
 * <p>
 *  此类是Xalan DTMIterator实例的动态包装器,并提供随机访问功能。
 * 
 */
public class NodeSequence extends XObject
  implements DTMIterator, Cloneable, PathComponent
{
    static final long serialVersionUID = 3866261934726581044L;
  /** The index of the last node in the iteration. */
  protected int m_last = -1;

  /**
   * The index of the next node to be fetched.  Useful if this
   * is a cached iterator, and is being used as random access
   * NodeList.
   * <p>
   *  要提取的下一个节点的索引。如果这是一个缓存迭代器,并且被用作随机访问NodeList,这很有用。
   * 
   */
  protected int m_next = 0;

  /**
   * A cache of a list of nodes obtained from the iterator so far.
   * This list is appended to until the iterator is exhausted and
   * the cache is complete.
   * <p>
   * Multiple NodeSequence objects may share the same cache.
   * <p>
   *  到目前为止从迭代器获得的节点列表的缓存。此列表将附加到迭代器耗尽并且缓存完成。
   * <p>
   *  多个NodeSequence对象可以共享相同的高速缓存。
   * 
   */
  private IteratorCache m_cache;

  /**
   * If this iterator needs to cache nodes that are fetched, they
   * are stored in the Vector in the generic object.
   * <p>
   *  如果这个迭代器需要缓存被获取的节点,它们就存储在通用对象中的Vector中。
   * 
   */
  protected NodeVector getVector() {
      NodeVector nv = (m_cache != null) ?  m_cache.getVector() : null;
      return nv;
  }

  /**
   * Get the cache (if any) of nodes obtained from
   * the iterator so far. Note that the cache keeps
   * growing until the iterator is walked to exhaustion,
   * at which point the cache is "complete".
   * <p>
   * 获取到目前为止从迭代器获得的节点的缓存(如果有)。注意,缓存持续增长,直到迭代器走到耗尽,此时缓存是"完成"的。
   * 
   */
  private IteratorCache getCache() {
      return m_cache;
  }

  /**
   * Set the vector where nodes will be cached.
   * <p>
   *  设置节点将被缓存的向量。
   * 
   */
  protected void SetVector(NodeVector v)
  {
        setObject(v);
  }


  /**
   * If the iterator needs to cache nodes as they are fetched,
   * then this method returns true.
   * <p>
   *  如果迭代器需要在获取缓存节点时缓存节点,则此方法返回true。
   * 
   */
  public boolean hasCache()
  {
    final NodeVector nv = getVector();
        return (nv != null);
  }

  /**
   * If this NodeSequence has a cache, and that cache is
   * fully populated then this method returns true, otherwise
   * if there is no cache or it is not complete it returns false.
   * <p>
   *  如果此NodeSequence有一个高速缓存,并且该高速缓存已完全填充,则此方法返回true,否则如果没有高速缓存或它未完成,则返回false。
   * 
   */
  private boolean cacheComplete() {
      final boolean complete;
      if (m_cache != null) {
          complete = m_cache.isComplete();
      } else {
          complete = false;
      }
      return complete;
  }

  /**
   * If this NodeSequence has a cache, mark that it is complete.
   * This method should be called after the iterator is exhausted.
   * <p>
   *  如果此NodeSequence有一个缓存,标记它是完整的。此方法应在迭代器耗尽后调用。
   * 
   */
  private void markCacheComplete() {
      NodeVector nv = getVector();
      if (nv != null) {
          m_cache.setCacheComplete(true);
      }
  }


  /**
   * The functional iterator that fetches nodes.
   * <p>
   *  获取节点的功能迭代器。
   * 
   */
  protected DTMIterator m_iter;

  /**
   * Set the functional iterator that fetches nodes.
   * <p>
   *  设置获取节点的功能迭代器。
   * 
   * 
   * @param iter The iterator that is to be contained.
   */
  public final void setIter(DTMIterator iter)
  {
        m_iter = iter;
  }

  /**
   * Get the functional iterator that fetches nodes.
   * <p>
   *  获取获取节点的功能迭代器。
   * 
   * 
   * @return The contained iterator.
   */
  public final DTMIterator getContainedIter()
  {
        return m_iter;
  }

  /**
   * The DTMManager to use if we're using a NodeVector only.
   * We may well want to do away with this, and store it in the NodeVector.
   * <p>
   *  如果我们仅使用NodeVector,则使用DTMManager。我们可能想要消除这一点,并将其存储在NodeVector中。
   * 
   */
  protected DTMManager m_dtmMgr;

  // ==== Constructors ====

  /**
   * Create a new NodeSequence from a (already cloned) iterator.
   *
   * <p>
   *  从(已克隆的)迭代器创建一个新的NodeSequence。
   * 
   * 
   * @param iter Cloned (not static) DTMIterator.
   * @param context The initial context node.
   * @param xctxt The execution context.
   * @param shouldCacheNodes True if this sequence can random access.
   */
  private NodeSequence(DTMIterator iter, int context, XPathContext xctxt, boolean shouldCacheNodes)
  {
        setIter(iter);
        setRoot(context, xctxt);
        setShouldCacheNodes(shouldCacheNodes);
  }

  /**
   * Create a new NodeSequence from a (already cloned) iterator.
   *
   * <p>
   *  从(已克隆的)迭代器创建一个新的NodeSequence。
   * 
   * 
   * @param nodeVector
   */
  public NodeSequence(Object nodeVector)
  {
        super(nodeVector);
    if (nodeVector instanceof NodeVector) {
        SetVector((NodeVector) nodeVector);
    }
        if(null != nodeVector)
        {
                assertion(nodeVector instanceof NodeVector,
                        "Must have a NodeVector as the object for NodeSequence!");
                if(nodeVector instanceof DTMIterator)
                {
                        setIter((DTMIterator)nodeVector);
                        m_last = ((DTMIterator)nodeVector).getLength();
                }

        }
  }

  /**
   * Construct an empty XNodeSet object.  This is used to create a mutable
   * nodeset to which random nodes may be added.
   * <p>
   *  构造一个空的XNodeSet对象。这用于创建可以添加随机节点的可变节点集。
   * 
   */
  private NodeSequence(DTMManager dtmMgr)
  {
    super(new NodeVector());
    m_last = 0;
    m_dtmMgr = dtmMgr;
  }


  /**
   * Create a new NodeSequence in an invalid (null) state.
   * <p>
   *  在无效(null)状态中创建新的NodeSequence。
   * 
   */
  public NodeSequence()
  {
      return;
  }


  /**
  /* <p>
  /* 
   * @see DTMIterator#getDTM(int)
   */
  public DTM getDTM(int nodeHandle)
  {
        DTMManager mgr = getDTMManager();
        if(null != mgr)
        return getDTMManager().getDTM(nodeHandle);
    else
    {
        assertion(false, "Can not get a DTM Unless a DTMManager has been set!");
        return null;
    }
  }

  /**
  /* <p>
  /* 
   * @see DTMIterator#getDTMManager()
   */
  public DTMManager getDTMManager()
  {
    return m_dtmMgr;
  }

  /**
  /* <p>
  /* 
   * @see DTMIterator#getRoot()
   */
  public int getRoot()
  {
        if(null != m_iter)
        return m_iter.getRoot();
        else
        {
                // NodeSetDTM will call this, and so it's not a good thing to throw
                // an assertion here.
                // assertion(false, "Can not get the root from a non-iterated NodeSequence!");
                return DTM.NULL;
        }
  }

  /**
  /* <p>
  /* 
   * @see DTMIterator#setRoot(int, Object)
   */
  public void setRoot(int nodeHandle, Object environment)
  {
        // If root is DTM.NULL, then something's wrong with the context
        if (nodeHandle == DTM.NULL)
        {
            throw new RuntimeException("Unable to evaluate expression using " +
                    "this context");
        }

        if(null != m_iter)
        {
                XPathContext xctxt = (XPathContext)environment;
                m_dtmMgr = xctxt.getDTMManager();
                m_iter.setRoot(nodeHandle, environment);
                if(!m_iter.isDocOrdered())
                {
                        if(!hasCache())
                                setShouldCacheNodes(true);
                        runTo(-1);
                        m_next=0;
                }
        }
        else
                assertion(false, "Can not setRoot on a non-iterated NodeSequence!");
  }

  /**
  /* <p>
  /* 
   * @see DTMIterator#reset()
   */
  public void reset()
  {
        m_next = 0;
        // not resetting the iterator on purpose!!!
  }

  /**
  /* <p>
  /* 
   * @see DTMIterator#getWhatToShow()
   */
  public int getWhatToShow()
  {
    return hasCache() ? (DTMFilter.SHOW_ALL & ~DTMFilter.SHOW_ENTITY_REFERENCE)
        : m_iter.getWhatToShow();
  }

  /**
  /* <p>
  /* 
   * @see DTMIterator#getExpandEntityReferences()
   */
  public boolean getExpandEntityReferences()
  {
        if(null != m_iter)
                return m_iter.getExpandEntityReferences();
        else
        return true;
  }

  /**
  /* <p>
  /* 
   * @see DTMIterator#nextNode()
   */
  public int nextNode()
  {
    // If the cache is on, and the node has already been found, then
    // just return from the list.
    NodeVector vec = getVector();
    if (null != vec)
    {
        // There is a cache
        if(m_next < vec.size())
        {
            // The node is in the cache, so just return it.
                        int next = vec.elementAt(m_next);
                m_next++;
                return next;
        }
        else if(cacheComplete() || (-1 != m_last) || (null == m_iter))
        {
                m_next++;
                return DTM.NULL;
        }
    }

  if (null == m_iter)
    return DTM.NULL;

        int next = m_iter.nextNode();
    if(DTM.NULL != next)
    {
        if(hasCache())
        {
                if(m_iter.isDocOrdered())
            {
                        getVector().addElement(next);
                        m_next++;
                }
                else
                {
                        int insertIndex = addNodeInDocOrder(next);
                        if(insertIndex >= 0)
                                m_next++;
                }
        }
        else
                m_next++;
    }
    else
    {
        // We have exhausted the iterator, and if there is a cache
        // it must have all nodes in it by now, so let the cache
        // know that it is complete.
        markCacheComplete();

        m_last = m_next;
        m_next++;
    }

    return next;
  }

  /**
  /* <p>
  /* 
   * @see DTMIterator#previousNode()
   */
  public int previousNode()
  {
        if(hasCache())
        {
                if(m_next <= 0)
                        return DTM.NULL;
                else
                {
                        m_next--;
                        return item(m_next);
                }
        }
        else
        {
            int n = m_iter.previousNode();
            m_next = m_iter.getCurrentPos();
            return m_next;
        }
  }

  /**
  /* <p>
  /* 
   * @see DTMIterator#detach()
   */
  public void detach()
  {
        if(null != m_iter)
                m_iter.detach();
        super.detach();
  }

  /**
   * Calling this with a value of false will cause the nodeset
   * to be cached.
   * <p>
   *  使用值false调用此方法将导致节点集被缓存。
   * 
   * 
   * @see DTMIterator#allowDetachToRelease(boolean)
   */
  public void allowDetachToRelease(boolean allowRelease)
  {
        if((false == allowRelease) && !hasCache())
        {
                setShouldCacheNodes(true);
        }

        if(null != m_iter)
                m_iter.allowDetachToRelease(allowRelease);
        super.allowDetachToRelease(allowRelease);
  }

  /**
  /* <p>
  /* 
   * @see DTMIterator#getCurrentNode()
   */
  public int getCurrentNode()
  {
        if(hasCache())
        {
                int currentIndex = m_next-1;
                NodeVector vec = getVector();
                if((currentIndex >= 0) && (currentIndex < vec.size()))
                        return vec.elementAt(currentIndex);
                else
                        return DTM.NULL;
        }

        if(null != m_iter)
        {
        return m_iter.getCurrentNode();
        }
        else
                return DTM.NULL;
  }

  /**
  /* <p>
  /* 
   * @see DTMIterator#isFresh()
   */
  public boolean isFresh()
  {
    return (0 == m_next);
  }

  /**
  /* <p>
  /* 
   * @see DTMIterator#setShouldCacheNodes(boolean)
   */
  public void setShouldCacheNodes(boolean b)
  {
    if (b)
    {
      if(!hasCache())
      {
        SetVector(new NodeVector());
      }
//        else
//          getVector().RemoveAllNoClear();  // Is this good?
    }
    else
      SetVector(null);
  }

  /**
  /* <p>
  /* 
   * @see DTMIterator#isMutable()
   */
  public boolean isMutable()
  {
    return hasCache(); // though may be surprising if it also has an iterator!
  }

  /**
  /* <p>
  /* 
   * @see DTMIterator#getCurrentPos()
   */
  public int getCurrentPos()
  {
    return m_next;
  }

  /**
  /* <p>
  /* 
   * @see DTMIterator#runTo(int)
   */
  public void runTo(int index)
  {
    int n;

    if (-1 == index)
    {
      int pos = m_next;
      while (DTM.NULL != (n = nextNode()));
      m_next = pos;
    }
    else if(m_next == index)
    {
      return;
    }
    else if(hasCache() && index < getVector().size())
    {
      m_next = index;
    }
    else if((null == getVector()) && (index < m_next))
    {
      while ((m_next >= index) && DTM.NULL != (n = previousNode()));
    }
    else
    {
      while ((m_next < index) && DTM.NULL != (n = nextNode()));
    }

  }

  /**
  /* <p>
  /* 
   * @see DTMIterator#setCurrentPos(int)
   */
  public void setCurrentPos(int i)
  {
        runTo(i);
  }

  /**
  /* <p>
  /* 
   * @see DTMIterator#item(int)
   */
  public int item(int index)
  {
        setCurrentPos(index);
        int n = nextNode();
        m_next = index;
        return n;
  }

  /**
  /* <p>
  /* 
   * @see DTMIterator#setItem(int, int)
   */
  public void setItem(int node, int index)
  {
        NodeVector vec = getVector();
        if(null != vec)
        {
        int oldNode = vec.elementAt(index);
        if (oldNode != node && m_cache.useCount() > 1) {
            /* If we are going to set the node at the given index
             * to a different value, and the cache is shared
             * (has a use count greater than 1)
             * then make a copy of the cache and use it
             * so we don't overwrite the value for other
             * users of the cache.
             * <p>
             *  到不同的值,并且高速缓存被共享(使用计数大于1),然后制作高速缓存的副本并使用它,因此我们不会覆盖高速缓存的其他用户的值。
             * 
             */
            IteratorCache newCache = new IteratorCache();
            final NodeVector nv;
            try {
                nv = (NodeVector) vec.clone();
            } catch (CloneNotSupportedException e) {
                // This should never happen
                e.printStackTrace();
                RuntimeException rte = new RuntimeException(e.getMessage());
                throw rte;
            }
            newCache.setVector(nv);
            newCache.setCacheComplete(true);
            m_cache = newCache;
            vec = nv;

            // Keep our superclass informed of the current NodeVector
            super.setObject(nv);

            /* When we get to here the new cache has
             * a use count of 1 and when setting a
             * bunch of values on the same NodeSequence,
             * such as when sorting, we will keep setting
             * values in that same copy which has a use count of 1.
             * <p>
             * 使用计数为1,并且当在同一个NodeSequence上设置一组值时,例如当排序时,我们将保持在使用计数为1的同一副本中设置值。
             * 
             */
        }
                vec.setElementAt(node, index);
                m_last = vec.size();
        }
        else
                m_iter.setItem(node, index);
  }

  /**
  /* <p>
  /* 
   * @see DTMIterator#getLength()
   */
  public int getLength()
  {
    IteratorCache cache = getCache();

        if(cache != null)
        {
        // Nodes from the iterator are cached
        if (cache.isComplete()) {
            // All of the nodes from the iterator are cached
            // so just return the number of nodes in the cache
            NodeVector nv = cache.getVector();
            return nv.size();
        }

        // If this NodeSequence wraps a mutable nodeset, then
        // m_last will not reflect the size of the nodeset if
        // it has been mutated...
        if (m_iter instanceof NodeSetDTM)
        {
            return m_iter.getLength();
        }

                if(-1 == m_last)
                {
                        int pos = m_next;
                        runTo(-1);
                        m_next = pos;
                }
            return m_last;
        }
        else
        {
                return (-1 == m_last) ? (m_last = m_iter.getLength()) : m_last;
        }
  }

  /**
   * Note: Not a deep clone.
   * <p>
   *  注意：不是深层克隆。
   * 
   * 
   * @see DTMIterator#cloneWithReset()
   */
  public DTMIterator cloneWithReset() throws CloneNotSupportedException
  {
        NodeSequence seq = (NodeSequence)super.clone();
    seq.m_next = 0;
    if (m_cache != null) {
        // In making this clone of an iterator we are making
        // another NodeSequence object it has a reference
        // to the same IteratorCache object as the original
        // so we need to remember that more than one
        // NodeSequence object shares the cache.
        m_cache.increaseUseCount();
    }

    return seq;
  }

  /**
   * Get a clone of this iterator, but don't reset the iteration in the
   * process, so that it may be used from the current position.
   * Note: Not a deep clone.
   *
   * <p>
   *  获取此迭代器的克隆,但不要重置进程中的迭代,以便可以从当前位置使用它。注意：不是深层克隆。
   * 
   * 
   * @return A clone of this object.
   *
   * @throws CloneNotSupportedException
   */
  public Object clone() throws CloneNotSupportedException
  {
          NodeSequence clone = (NodeSequence) super.clone();
          if (null != m_iter) clone.m_iter = (DTMIterator) m_iter.clone();
          if (m_cache != null) {
              // In making this clone of an iterator we are making
              // another NodeSequence object it has a reference
              // to the same IteratorCache object as the original
              // so we need to remember that more than one
              // NodeSequence object shares the cache.
              m_cache.increaseUseCount();
          }

          return clone;
  }


  /**
  /* <p>
  /* 
   * @see DTMIterator#isDocOrdered()
   */
  public boolean isDocOrdered()
  {
        if(null != m_iter)
                return m_iter.isDocOrdered();
        else
        return true; // can't be sure?
  }

  /**
  /* <p>
  /* 
   * @see DTMIterator#getAxis()
   */
  public int getAxis()
  {
        if(null != m_iter)
        return m_iter.getAxis();
    else
    {
        assertion(false, "Can not getAxis from a non-iterated node sequence!");
        return 0;
    }
  }

  /**
  /* <p>
  /* 
   * @see PathComponent#getAnalysisBits()
   */
  public int getAnalysisBits()
  {
        if((null != m_iter) && (m_iter instanceof PathComponent))
        return ((PathComponent)m_iter).getAnalysisBits();
    else
        return 0;
  }

  /**
  /* <p>
  /* 
   * @see org.apache.xpath.Expression#fixupVariables(Vector, int)
   */
  public void fixupVariables(Vector vars, int globalsSize)
  {
        super.fixupVariables(vars, globalsSize);
  }

  /**
   * Add the node into a vector of nodes where it should occur in
   * document order.
   * <p>
   *  将节点添加到节点的向量中,其中它应该以文档顺序出现。
   * 
   * 
   * @param node The node to be added.
   * @return insertIndex.
   * @throws RuntimeException thrown if this NodeSetDTM is not of
   * a mutable type.
   */
   protected int addNodeInDocOrder(int node)
   {
      assertion(hasCache(), "addNodeInDocOrder must be done on a mutable sequence!");

      int insertIndex = -1;

      NodeVector vec = getVector();

      // This needs to do a binary search, but a binary search
      // is somewhat tough because the sequence test involves
      // two nodes.
      int size = vec.size(), i;

      for (i = size - 1; i >= 0; i--)
      {
        int child = vec.elementAt(i);

        if (child == node)
        {
          i = -2; // Duplicate, suppress insert

          break;
        }

        DTM dtm = m_dtmMgr.getDTM(node);
        if (!dtm.isNodeAfter(node, child))
        {
          break;
        }
      }

      if (i != -2)
      {
        insertIndex = i + 1;

        vec.insertElementAt(node, insertIndex);
      }

      // checkDups();
      return insertIndex;
    } // end addNodeInDocOrder(Vector v, Object obj)

   /**
    * It used to be that many locations in the code simply
    * did an assignment to this.m_obj directly, rather than
    * calling the setObject(Object) method. The problem is
    * that our super-class would be updated on what the
    * cache associated with this NodeSequence, but
    * we wouldn't know ourselves.
    * <p>
    * All setting of m_obj is done through setObject() now,
    * and this method over-rides the super-class method.
    * So now we are in the loop have an opportunity
    * to update some caching information.
    *
    * <p>
    *  以前,代码中的许多位置只是直接赋值给this.m_obj,而不是调用setObject(Object)方法。问题是,我们的超类将更新与此NodeSequence相关联的缓存,但我们不会知道自己。
    * <p>
    *  m_obj的所有设置现在都通过setObject()完成,并且此方法覆盖了超类方法。所以现在我们在循环中有机会更新一些缓存信息。
    * 
    */
   protected void setObject(Object obj) {
       if (obj instanceof NodeVector) {
           // Keep our superclass informed of the current NodeVector
           // ... if we don't the smoketest fails (don't know why).
           super.setObject(obj);

           // A copy of the code of what SetVector() would do.
           NodeVector v = (NodeVector)obj;
           if (m_cache != null) {
               m_cache.setVector(v);
           } else if (v!=null) {
               m_cache = new IteratorCache();
               m_cache.setVector(v);
           }
       } else if (obj instanceof IteratorCache) {
           IteratorCache cache = (IteratorCache) obj;
           m_cache = cache;
           m_cache.increaseUseCount();

           // Keep our superclass informed of the current NodeVector
           super.setObject(cache.getVector());
       } else {
           super.setObject(obj);
       }

   }

   /**
    * Each NodeSequence object has an iterator which is "walked".
    * As an iterator is walked one obtains nodes from it.
    * As those nodes are obtained they may be cached, making
    * the next walking of a copy or clone of the iterator faster.
    * This field (m_cache) is a reference to such a cache,
    * which is populated as the iterator is walked.
    * <p>
    * Note that multiple NodeSequence objects may hold a
    * reference to the same cache, and also
    * (and this is important) the same iterator.
    * The iterator and its cache may be shared among
    * many NodeSequence objects.
    * <p>
    * If one of the NodeSequence objects walks ahead
    * of the others it fills in the cache.
    * As the others NodeSequence objects catch up they
    * get their values from
    * the cache rather than the iterator itself, so
    * the iterator is only ever walked once and everyone
    * benefits from the cache.
    * <p>
    * At some point the cache may be
    * complete due to walking to the end of one of
    * the copies of the iterator, and the cache is
    * then marked as "complete".
    * and the cache will have no more nodes added to it.
    * <p>
    * Its use-count is the number of NodeSequence objects that use it.
    * <p>
    *  每个NodeSequence对象都有一个"walked"的迭代器。因为迭代器是步行的,所以从它获得节点。当获得这些节点时,它们可以被高速缓存,使得迭代器的副本或克隆的下一步更快。
    * 这个字段(m_cache)是对这样的缓存的引用,当遍历迭代器时填充缓存。
    * <p>
    *  注意,多个NodeSequence对象可以保存对同一高速缓存的引用,并且(并且这是重要的)相同的迭代器。迭代器及其缓存可以在许多NodeSequence对象之间共享。
    * <p>
    * 如果其中一个NodeSequence对象超前于其他NodeSequence对象,它会填充缓存。
    * 当其他NodeSequence对象赶上时,它们从缓存而不是迭代器本身获得它们的值,所以迭代器只能走一次,每个人都从缓存中受益。
    * <p>
    *  在某一点,由于步行到迭代器的副本之一的末尾,高速缓存可能是完整的,并且高速缓存随后被标记为"完成"。并且缓存将没有更多的节点添加到它。
    * <p>
    *  它的use-count是使用它的NodeSequence对象的数量。
    * 
    */
   private final static class IteratorCache {
       /**
        * A list of nodes already obtained from the iterator.
        * As the iterator is walked the nodes obtained from
        * it are appended to this list.
        * <p>
        * Both an iterator and its corresponding cache can
        * be shared by multiple NodeSequence objects.
        * <p>
        * For example, consider three NodeSequence objects
        * ns1, ns2 and ns3 doing such sharing, and the
        * nodes to be obtaind from the iterator being
        * the sequence { 33, 11, 44, 22, 55 }.
        * <p>
        * If ns3.nextNode() is called 3 times the the
        * underlying iterator will have walked through
        * 33, 11, 55 and these three nodes will have been put
        * in the cache.
        * <p>
        * If ns2.nextNode() is called 2 times it will return
        * 33 and 11 from the cache, leaving the iterator alone.
        * <p>
        * If ns1.nextNode() is called 6 times it will return
        * 33 and 11 from the cache, then get 44, 22, 55 from
        * the iterator, and appending 44, 22, 55 to the cache.
        * On the sixth call it is found that the iterator is
        * exhausted and the cache is marked complete.
        * <p>
        * Should ns2 or ns3 have nextNode() called they will
        * know that the cache is complete, and they will
        * obtain all subsequent nodes from the cache.
        * <p>
        * Note that the underlying iterator, though shared
        * is only ever walked once.
        * <p>
        *  已经从迭代器获得的节点的列表。当迭代器被遍历时,从其获得的节点被附加到该列表。
        * <p>
        *  迭代器及其相应的缓存都可以由多个NodeSequence对象共享。
        * <p>
        *  例如,考虑三个进行这样的共享的NodeSequence对象ns1,ns2和ns3,以及要从迭代器获得的节点是序列{33,11,44,22,55}。
        * <p>
        *  如果调用ns3.nextNode()3次,底层迭代器将遍历33,11,55,这三个节点将被放入缓存中。
        * <p>
        *  如果ns2.nextNode()被调用2次,它将从高速缓存返回33和11,留下迭代器。
        * <p>
        *  如果ns1.nextNode()被调用6次,它将从缓存返回33和11,然后从迭代器获取44,22,55,并将44,22,55附加到缓存。在第六次调用中,发现迭代器耗尽并且高速缓存被标记为完成。
        * <p>
        * 如果ns2或ns3有nextNode()调用,他们将知道高速缓存已完成,并且它们将从高速缓存获取所有后续节点。
        * <p>
        *  注意,底层迭代器,虽然共享,只有一次。
        * 
        */
        private NodeVector m_vec2;

        /**
         * true if the associated iterator is exhausted and
         * all nodes obtained from it are in the cache.
         * <p>
         *  如果关联的迭代器耗尽,并且从其获取的所有节点都在缓存中,则为true。
         * 
         */
        private boolean m_isComplete2;

        private int m_useCount2;

        IteratorCache() {
            m_vec2 = null;
            m_isComplete2 = false;
            m_useCount2 = 1;
            return;
        }

        /**
         * Returns count of how many NodeSequence objects share this
         * IteratorCache object.
         * <p>
         *  返回有多少个NodeSequence对象共享此IteratorCache对象的计数。
         * 
         */
        private int useCount() {
            return m_useCount2;
        }

        /**
         * This method is called when yet another
         * NodeSequence object uses, or shares
         * this same cache.
         *
         * <p>
         *  当另一个NodeSequence对象使用或共享此相同的缓存时,将调用此方法。
         * 
         */
        private void increaseUseCount() {
            if (m_vec2 != null)
                m_useCount2++;

        }

        /**
         * Sets the NodeVector that holds the
         * growing list of nodes as they are appended
         * to the cached list.
         * <p>
         *  设置NodeVector,用于保存随着节点添加到缓存列表而增长的节点列表。
         * 
         */
        private void setVector(NodeVector nv) {
            m_vec2 = nv;
            m_useCount2 = 1;
        }

        /**
         * Get the cached list of nodes obtained from
         * the iterator so far.
         * <p>
         *  获取从迭代器获得的缓存节点列表到目前为止。
         * 
         */
        private NodeVector getVector() {
            return m_vec2;
        }

        /**
         * Call this method with 'true' if the
         * iterator is exhausted and the cached list
         * is complete, or no longer growing.
         * <p>
         *  如果迭代器耗尽且缓存列表已完成或不再增长,则使用"true"调用此方法。
         * 
         */
        private void setCacheComplete(boolean b) {
            m_isComplete2 = b;

        }

        /**
         * Returns true if no cache is complete
         * and immutable.
         * <p>
         *  如果没有缓存是完整的和不可变的,则返回true。
         * 
         */
        private boolean isComplete() {
            return m_isComplete2;
        }
    }

    /**
     * Get the cached list of nodes appended with
     * values obtained from the iterator as
     * a NodeSequence is walked when its
     * nextNode() method is called.
     * <p>
     *  获取缓存的节点列表,其中附加有从迭代器获得的值,当NodeSequence被调用时调用nextNode()方法。
     */
    protected IteratorCache getIteratorCache() {
        return m_cache;
    }
}
