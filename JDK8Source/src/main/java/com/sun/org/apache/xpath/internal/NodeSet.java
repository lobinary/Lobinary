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
 * $Id: NodeSet.java,v 1.2.4.1 2005/09/10 17:39:49 jeffsuttor Exp $
 * <p>
 *  $ Id：NodeSet.java,v 1.2.4.1 2005/09/10 17:39:49 jeffsuttor Exp $
 * 
 */
package com.sun.org.apache.xpath.internal;

import com.sun.org.apache.xalan.internal.res.XSLMessages;
import com.sun.org.apache.xml.internal.utils.DOM2Helper;
import com.sun.org.apache.xpath.internal.axes.ContextNodeList;
import com.sun.org.apache.xpath.internal.res.XPATHErrorResources;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;


/**
 * <p>The NodeSet class can act as either a NodeVector,
 * NodeList, or NodeIterator.  However, in order for it to
 * act as a NodeVector or NodeList, it's required that
 * setShouldCacheNodes(true) be called before the first
 * nextNode() is called, in order that nodes can be added
 * as they are fetched.  Derived classes that implement iterators
 * must override runTo(int index), in order that they may
 * run the iteration to the given index. </p>
 *
 * <p>Note that we directly implement the DOM's NodeIterator
 * interface. We do not emulate all the behavior of the
 * standard NodeIterator. In particular, we do not guarantee
 * to present a "live view" of the document ... but in XSLT,
 * the source document should never be mutated, so this should
 * never be an issue.</p>
 *
 * <p>Thought: Should NodeSet really implement NodeList and NodeIterator,
 * or should there be specific subclasses of it which do so? The
 * advantage of doing it all here is that all NodeSets will respond
 * to the same calls; the disadvantage is that some of them may return
 * less-than-enlightening results when you do so.</p>
 * @xsl.usage advanced
 * <p>
 *  <p> NodeSet类可以充当NodeVector,NodeList或NodeIterator。
 * 但是,为了使其充当NodeVector或NodeList,需要在调用第一个nextNode()之前调用setShouldCacheNodes(true),以便可以在获取节点时进行添加。
 * 实现迭代器的派生类必须重写runTo(int index),以便它们可以对给定的索引运行迭代。 </p>。
 * 
 * <p>请注意,我们直接实现DOM的NodeIterator接口。我们不模拟标准NodeIterator的所有行为。
 * 特别是,我们不保证提供文档的"实时视图"...但是在XSLT中,源文档不应该被改变,所以这不应该是一个问题。</p>。
 * 
 *  <p>思想：NodeSet真的应该实现NodeList和NodeIterator,还是应该有特定的子类呢?这里做的所有的优点是所有NodeSets将响应相同的调用;缺点是,当你这样做时,他们中的一些可
 * 能返回不那么启发的结果。
 * </p> @ xsl.usage advanced。
 * 
 */
public class NodeSet
        implements NodeList, NodeIterator, Cloneable, ContextNodeList
{

  /**
   * Create an empty nodelist.
   * <p>
   *  创建一个空节点列表。
   * 
   */
  public NodeSet()
  {
    m_blocksize = 32;
    m_mapSize = 0;
  }

  /**
   * Create an empty, using the given block size.
   *
   * <p>
   *  使用给定的块大小创建一个空。
   * 
   * 
   * @param blocksize Size of blocks to allocate
   */
  public NodeSet(int blocksize)
  {
    m_blocksize = blocksize;
    m_mapSize = 0;
  }

  /**
   * Create a NodeSet, and copy the members of the
   * given nodelist into it.
   *
   * <p>
   *  创建一个NodeSet,并将给定节点列表的成员复制到其中。
   * 
   * 
   * @param nodelist List of Nodes to be made members of the new set.
   */
  public NodeSet(NodeList nodelist)
  {

    this(32);

    addNodes(nodelist);
  }

  /**
   * Create a NodeSet, and copy the members of the
   * given NodeSet into it.
   *
   * <p>
   *  创建一个NodeSet,并将给定NodeSet的成员复制到其中。
   * 
   * 
   * @param nodelist Set of Nodes to be made members of the new set.
   */
  public NodeSet(NodeSet nodelist)
  {

    this(32);

    addNodes((NodeIterator) nodelist);
  }

  /**
   * Create a NodeSet, and copy the members of the
   * given NodeIterator into it.
   *
   * <p>
   *  创建一个NodeSet,并将给定的NodeIterator的成员复制到其中。
   * 
   * 
   * @param ni Iterator which yields Nodes to be made members of the new set.
   */
  public NodeSet(NodeIterator ni)
  {

    this(32);

    addNodes(ni);
  }

  /**
   * Create a NodeSet which contains the given Node.
   *
   * <p>
   *  创建一个包含给定Node的NodeSet。
   * 
   * 
   * @param node Single node to be added to the new set.
   */
  public NodeSet(Node node)
  {

    this(32);

    addNode(node);
  }

  /**
  /* <p>
  /* 
   * @return The root node of the Iterator, as specified when it was created.
   * For non-Iterator NodeSets, this will be null.
   */
  public Node getRoot()
  {
    return null;
  }

  /**
   * Get a cloned Iterator, and reset its state to the beginning of the
   * iteration.
   *
   * <p>
   *  获取克隆的迭代器,并将其状态重置为迭代的开始。
   * 
   * 
   * @return a new NodeSet of the same type, having the same state...
   * except that the reset() operation has been called.
   *
   * @throws CloneNotSupportedException if this subclass of NodeSet
   * does not support the clone() operation.
   */
  public NodeIterator cloneWithReset() throws CloneNotSupportedException
  {

    NodeSet clone = (NodeSet) clone();

    clone.reset();

    return clone;
  }

  /**
   * Reset the iterator. May have no effect on non-iterator Nodesets.
   * <p>
   *  重置迭代器。可能对非迭代器Nodesets没有影响。
   * 
   */
  public void reset()
  {
    m_next = 0;
  }

  /**
   *  This attribute determines which node types are presented via the
   * iterator. The available set of constants is defined in the
   * <code>NodeFilter</code> interface. For NodeSets, the mask has been
   * hardcoded to show all nodes except EntityReference nodes, which have
   * no equivalent in the XPath data model.
   *
   * <p>
   *  此属性确定通过迭代器呈现哪些节点类型。可用的常量集在<code> NodeFilter </code>接口中定义。
   * 对于NodeSets,掩码已经硬编码以显示除了EntityReference节点之外的所有节点,这些节点在XPath数据模型中没有等效项。
   * 
   * 
   * @return integer used as a bit-array, containing flags defined in
   * the DOM's NodeFilter class. The value will be
   * <code>SHOW_ALL & ~SHOW_ENTITY_REFERENCE</code>, meaning that
   * only entity references are suppressed.
   */
  public int getWhatToShow()
  {
    return NodeFilter.SHOW_ALL & ~NodeFilter.SHOW_ENTITY_REFERENCE;
  }

  /**
   * The filter object used to screen nodes. Filters are applied to
   * further reduce (and restructure) the NodeIterator's view of the
   * document. In our case, we will be using hardcoded filters built
   * into our iterators... but getFilter() is part of the DOM's
   * NodeIterator interface, so we have to support it.
   *
   * <p>
   * 用于屏幕节点的过滤器对象。应用过滤器以进一步减少(和重构)NodeIterator对文档的视图。
   * 在我们的例子中,我们将使用内置在迭代器中的硬编码过滤器...但是getFilter()是DOM的NodeIterator接口的一部分,因此我们必须支持它。
   * 
   * 
   * @return null, which is slightly misleading. True, there is no
   * user-written filter object, but in fact we are doing some very
   * sophisticated custom filtering. A DOM purist might suggest
   * returning a placeholder object just to indicate that this is
   * not going to return all nodes selected by whatToShow.
   */
  public NodeFilter getFilter()
  {
    return null;
  }

  /**
   *  The value of this flag determines whether the children of entity
   * reference nodes are visible to the iterator. If false, they will be
   * skipped over.
   * <br> To produce a view of the document that has entity references
   * expanded and does not expose the entity reference node itself, use the
   * whatToShow flags to hide the entity reference node and set
   * expandEntityReferences to true when creating the iterator. To produce
   * a view of the document that has entity reference nodes but no entity
   * expansion, use the whatToShow flags to show the entity reference node
   * and set expandEntityReferences to false.
   *
   * <p>
   *  此标志的值确定实体引用节点的子项是否对迭代器可见。如果为false,它们将被跳过。
   *  <br>要生成具有实体引用扩展并且不公开实体引用节点本身的文档的视图,请在创建迭代器时使用whatToShow标记来隐藏实体引用节点并将expandEntityReferences设置为true。
   * 要生成具有实体引用节点但没有实体扩展的文档的视图,请使用whatToShow标志来显示实体引用节点,并将expandEntityReferences设置为false。
   * 
   * 
   * @return true for all iterators based on NodeSet, meaning that the
   * contents of EntityRefrence nodes may be returned (though whatToShow
   * says that the EntityReferences themselves are not shown.)
   */
  public boolean getExpandEntityReferences()
  {
    return true;
  }

  /**
   *  Returns the next node in the set and advances the position of the
   * iterator in the set. After a NodeIterator is created, the first call
   * to nextNode() returns the first node in the set.
   * <p>
   *  返回集合中的下一个节点,并推进迭代器在集合中的位置。创建NodeIterator之后,第一次调用nextNode()返回集合中的第一个节点。
   * 
   * 
   * @return  The next <code>Node</code> in the set being iterated over, or
   *   <code>null</code> if there are no more members in that set.
   * @throws DOMException
   *    INVALID_STATE_ERR: Raised if this method is called after the
   *   <code>detach</code> method was invoked.
   */
  public Node nextNode() throws DOMException
  {

    if ((m_next) < this.size())
    {
      Node next = this.elementAt(m_next);

      m_next++;

      return next;
    }
    else
      return null;
  }

  /**
   *  Returns the previous node in the set and moves the position of the
   * iterator backwards in the set.
   * <p>
   *  返回集合中的上一个节点,并在集合中向后移动迭代器的位置。
   * 
   * 
   * @return  The previous <code>Node</code> in the set being iterated over,
   *   or<code>null</code> if there are no more members in that set.
   * @throws DOMException
   *    INVALID_STATE_ERR: Raised if this method is called after the
   *   <code>detach</code> method was invoked.
   * @throws RuntimeException thrown if this NodeSet is not of
   * a cached type, and hence doesn't know what the previous node was.
   */
  public Node previousNode() throws DOMException
  {

    if (!m_cacheNodes)
      throw new RuntimeException(
        XSLMessages.createXPATHMessage(XPATHErrorResources.ER_NODESET_CANNOT_ITERATE, null)); //"This NodeSet can not iterate to a previous node!");

    if ((m_next - 1) > 0)
    {
      m_next--;

      return this.elementAt(m_next);
    }
    else
      return null;
  }

  /**
   * Detaches the iterator from the set which it iterated over, releasing
   * any computational resources and placing the iterator in the INVALID
   * state. After<code>detach</code> has been invoked, calls to
   * <code>nextNode</code> or<code>previousNode</code> will raise the
   * exception INVALID_STATE_ERR.
   * <p>
   * This operation is a no-op in NodeSet, and will not cause
   * INVALID_STATE_ERR to be raised by later operations.
   * </p>
   * <p>
   *  从迭代的集合中分离迭代器,释放任何计算资源并将迭代器置于INVALID状态。
   * 调用<code> detach </code>后,调用<code> nextNode </code>或<code> previousNode </code>会引发异常INVALID_STATE_ERR。
   *  从迭代的集合中分离迭代器,释放任何计算资源并将迭代器置于INVALID状态。
   * <p>
   * 此操作是NodeSet中的无操作,并且不会导致稍后操作引发INVALID_STATE_ERR。
   * </p>
   */
  public void detach(){}

  /**
   * Tells if this NodeSet is "fresh", in other words, if
   * the first nextNode() that is called will return the
   * first node in the set.
   *
   * <p>
   *  告诉这个NodeSet是否"新鲜",换句话说,如果被调用的第一个nextNode()将返回集合中的第一个节点。
   * 
   * 
   * @return true if nextNode() would return the first node in the set,
   * false if it would return a later one.
   */
  public boolean isFresh()
  {
    return (m_next == 0);
  }

  /**
   * If an index is requested, NodeSet will call this method
   * to run the iterator to the index.  By default this sets
   * m_next to the index.  If the index argument is -1, this
   * signals that the iterator should be run to the end.
   *
   * <p>
   *  如果请求索引,NodeSet将调用此方法来运行迭代器到索引。默认情况下,这将m_next设置为索引。如果index参数是-1,这表示迭代器应该运行到结束。
   * 
   * 
   * @param index Position to advance (or retreat) to, with
   * 0 requesting the reset ("fresh") position and -1 (or indeed
   * any out-of-bounds value) requesting the final position.
   * @throws RuntimeException thrown if this NodeSet is not
   * one of the types which supports indexing/counting.
   */
  public void runTo(int index)
  {

    if (!m_cacheNodes)
      throw new RuntimeException(
        XSLMessages.createXPATHMessage(XPATHErrorResources.ER_NODESET_CANNOT_INDEX, null)); //"This NodeSet can not do indexing or counting functions!");

    if ((index >= 0) && (m_next < m_firstFree))
      m_next = index;
    else
      m_next = m_firstFree - 1;
  }

  /**
   * Returns the <code>index</code>th item in the collection. If
   * <code>index</code> is greater than or equal to the number of nodes in
   * the list, this returns <code>null</code>.
   *
   * TODO: What happens if index is out of range?
   *
   * <p>
   *  返回集合中的<code> index </code>项。如果<code> index </code>大于或等于列表中的节点数,则返回<code> null </code>。
   * 
   *  TODO：如果指数超出范围会发生什么?
   * 
   * 
   * @param index Index into the collection.
   * @return The node at the <code>index</code>th position in the
   *   <code>NodeList</code>, or <code>null</code> if that is not a valid
   *   index.
   */
  public Node item(int index)
  {

    runTo(index);

    return (Node) this.elementAt(index);
  }

  /**
   * The number of nodes in the list. The range of valid child node indices is
   * 0 to <code>length-1</code> inclusive. Note that this operation requires
   * finding all the matching nodes, which may defeat attempts to defer
   * that work.
   *
   * <p>
   *  列表中的节点数。有效子节点索引的范围是0到<code> length-1 </code>。请注意,此操作需要查找所有匹配的节点,这可能会失败推迟该工作的尝试。
   * 
   * 
   * @return integer indicating how many nodes are represented by this list.
   */
  public int getLength()
  {

    runTo(-1);

    return this.size();
  }

  /**
   * Add a node to the NodeSet. Not all types of NodeSets support this
   * operation
   *
   * <p>
   *  将节点添加到NodeSet。并非所有类型的NodeSets支持此操作
   * 
   * 
   * @param n Node to be added
   * @throws RuntimeException thrown if this NodeSet is not of
   * a mutable type.
   */
  public void addNode(Node n)
  {

    if (!m_mutable)
      throw new RuntimeException(XSLMessages.createXPATHMessage(XPATHErrorResources.ER_NODESET_NOT_MUTABLE, null)); //"This NodeSet is not mutable!");

    this.addElement(n);
  }

  /**
   * Insert a node at a given position.
   *
   * <p>
   *  在给定位置插入节点。
   * 
   * 
   * @param n Node to be added
   * @param pos Offset at which the node is to be inserted,
   * with 0 being the first position.
   * @throws RuntimeException thrown if this NodeSet is not of
   * a mutable type.
   */
  public void insertNode(Node n, int pos)
  {

    if (!m_mutable)
      throw new RuntimeException(XSLMessages.createXPATHMessage(XPATHErrorResources.ER_NODESET_NOT_MUTABLE, null)); //"This NodeSet is not mutable!");

    insertElementAt(n, pos);
  }

  /**
   * Remove a node.
   *
   * <p>
   *  删除节点。
   * 
   * 
   * @param n Node to be added
   * @throws RuntimeException thrown if this NodeSet is not of
   * a mutable type.
   */
  public void removeNode(Node n)
  {

    if (!m_mutable)
      throw new RuntimeException(XSLMessages.createXPATHMessage(XPATHErrorResources.ER_NODESET_NOT_MUTABLE, null)); //"This NodeSet is not mutable!");

    this.removeElement(n);
  }

  /**
   * Copy NodeList members into this nodelist, adding in
   * document order.  If a node is null, don't add it.
   *
   * <p>
   *  将NodeList成员复制到此节点列表中,以文档顺序添加。如果节点为null,则不要添加它。
   * 
   * 
   * @param nodelist List of nodes which should now be referenced by
   * this NodeSet.
   * @throws RuntimeException thrown if this NodeSet is not of
   * a mutable type.
   */
  public void addNodes(NodeList nodelist)
  {

    if (!m_mutable)
      throw new RuntimeException(XSLMessages.createXPATHMessage(XPATHErrorResources.ER_NODESET_NOT_MUTABLE, null)); //"This NodeSet is not mutable!");

    if (null != nodelist)  // defensive to fix a bug that Sanjiva reported.
    {
      int nChildren = nodelist.getLength();

      for (int i = 0; i < nChildren; i++)
      {
        Node obj = nodelist.item(i);

        if (null != obj)
        {
          addElement(obj);
        }
      }
    }

    // checkDups();
  }

  /**
   * <p>Copy NodeList members into this nodelist, adding in
   * document order.  Only genuine node references will be copied;
   * nulls appearing in the source NodeSet will
   * not be added to this one. </p>
   *
   * <p> In case you're wondering why this function is needed: NodeSet
   * implements both NodeIterator and NodeList. If this method isn't
   * provided, Java can't decide which of those to use when addNodes()
   * is invoked. Providing the more-explicit match avoids that
   * ambiguity.)</p>
   *
   * <p>
   *  <p>将NodeList成员复制到此节点列表中,以文档顺序添加。只有真正的节点引用将被复制;在源NodeSet中出现的空值将不会添加到此节点。 </p>
   * 
   * <p>如果你想知道为什么需要这个函数：NodeSet实现NodeIterator和NodeList。如果不提供此方法,Java不能决定在调用addNodes()时要使用哪个方法。
   * 提供更明确的匹配可避免模糊。)</p>。
   * 
   * 
   * @param ns NodeSet whose members should be merged into this NodeSet.
   * @throws RuntimeException thrown if this NodeSet is not of
   * a mutable type.
   */
  public void addNodes(NodeSet ns)
  {

    if (!m_mutable)
      throw new RuntimeException(XSLMessages.createXPATHMessage(XPATHErrorResources.ER_NODESET_NOT_MUTABLE, null)); //"This NodeSet is not mutable!");

    addNodes((NodeIterator) ns);
  }

  /**
   * Copy NodeList members into this nodelist, adding in
   * document order.  Null references are not added.
   *
   * <p>
   *  将NodeList成员复制到此节点列表中,以文档顺序添加。不添加空引用。
   * 
   * 
   * @param iterator NodeIterator which yields the nodes to be added.
   * @throws RuntimeException thrown if this NodeSet is not of
   * a mutable type.
   */
  public void addNodes(NodeIterator iterator)
  {

    if (!m_mutable)
      throw new RuntimeException(XSLMessages.createXPATHMessage(XPATHErrorResources.ER_NODESET_NOT_MUTABLE, null)); //"This NodeSet is not mutable!");

    if (null != iterator)  // defensive to fix a bug that Sanjiva reported.
    {
      Node obj;

      while (null != (obj = iterator.nextNode()))
      {
        addElement(obj);
      }
    }

    // checkDups();
  }

  /**
   * Copy NodeList members into this nodelist, adding in
   * document order.  If a node is null, don't add it.
   *
   * <p>
   *  将NodeList成员复制到此节点列表中,以文档顺序添加。如果节点为null,则不要添加它。
   * 
   * 
   * @param nodelist List of nodes to be added
   * @param support The XPath runtime context.
   * @throws RuntimeException thrown if this NodeSet is not of
   * a mutable type.
   */
  public void addNodesInDocOrder(NodeList nodelist, XPathContext support)
  {

    if (!m_mutable)
      throw new RuntimeException(XSLMessages.createXPATHMessage(XPATHErrorResources.ER_NODESET_NOT_MUTABLE, null)); //"This NodeSet is not mutable!");

    int nChildren = nodelist.getLength();

    for (int i = 0; i < nChildren; i++)
    {
      Node node = nodelist.item(i);

      if (null != node)
      {
        addNodeInDocOrder(node, support);
      }
    }
  }

  /**
   * Copy NodeList members into this nodelist, adding in
   * document order.  If a node is null, don't add it.
   *
   * <p>
   *  将NodeList成员复制到此节点列表中,以文档顺序添加。如果节点为null,则不要添加它。
   * 
   * 
   * @param iterator NodeIterator which yields the nodes to be added.
   * @param support The XPath runtime context.
   * @throws RuntimeException thrown if this NodeSet is not of
   * a mutable type.
   */
  public void addNodesInDocOrder(NodeIterator iterator, XPathContext support)
  {

    if (!m_mutable)
      throw new RuntimeException(XSLMessages.createXPATHMessage(XPATHErrorResources.ER_NODESET_NOT_MUTABLE, null)); //"This NodeSet is not mutable!");

    Node node;

    while (null != (node = iterator.nextNode()))
    {
      addNodeInDocOrder(node, support);
    }
  }

  /**
   * Add the node list to this node set in document order.
   *
   * <p>
   *  将节点列表添加到按文档顺序设置的此节点。
   * 
   * 
   * @param start index.
   * @param end index.
   * @param testIndex index.
   * @param nodelist The nodelist to add.
   * @param support The XPath runtime context.
   *
   * @return false always.
   * @throws RuntimeException thrown if this NodeSet is not of
   * a mutable type.
   */
  private boolean addNodesInDocOrder(int start, int end, int testIndex,
                                     NodeList nodelist, XPathContext support)
  {

    if (!m_mutable)
      throw new RuntimeException(XSLMessages.createXPATHMessage(XPATHErrorResources.ER_NODESET_NOT_MUTABLE, null)); //"This NodeSet is not mutable!");

    boolean foundit = false;
    int i;
    Node node = nodelist.item(testIndex);

    for (i = end; i >= start; i--)
    {
      Node child = (Node) elementAt(i);

      if (child == node)
      {
        i = -2;  // Duplicate, suppress insert

        break;
      }

      if (!DOM2Helper.isNodeAfter(node, child))
      {
        insertElementAt(node, i + 1);

        testIndex--;

        if (testIndex > 0)
        {
          boolean foundPrev = addNodesInDocOrder(0, i, testIndex, nodelist,
                                                 support);

          if (!foundPrev)
          {
            addNodesInDocOrder(i, size() - 1, testIndex, nodelist, support);
          }
        }

        break;
      }
    }

    if (i == -1)
    {
      insertElementAt(node, 0);
    }

    return foundit;
  }

  /**
   * Add the node into a vector of nodes where it should occur in
   * document order.
   * <p>
   *  将节点添加到节点的向量中,其中它应该以文档顺序出现。
   * 
   * 
   * @param node The node to be added.
   * @param test true if we should test for doc order
   * @param support The XPath runtime context.
   * @return insertIndex.
   * @throws RuntimeException thrown if this NodeSet is not of
   * a mutable type.
   */
  public int addNodeInDocOrder(Node node, boolean test, XPathContext support)
  {

    if (!m_mutable)
      throw new RuntimeException(XSLMessages.createXPATHMessage(XPATHErrorResources.ER_NODESET_NOT_MUTABLE, null)); //XSLMessages.createXPATHMessage(XPATHErrorResources.ER_NODESET_NOT_MUTABLE, null)); //"This NodeSet is not mutable!");

    int insertIndex = -1;

    if (test)
    {

      // This needs to do a binary search, but a binary search
      // is somewhat tough because the sequence test involves
      // two nodes.
      int size = size(), i;

      for (i = size - 1; i >= 0; i--)
      {
        Node child = (Node) elementAt(i);

        if (child == node)
        {
          i = -2;  // Duplicate, suppress insert

          break;
        }

        if (!DOM2Helper.isNodeAfter(node, child))
        {
          break;
        }
      }

      if (i != -2)
      {
        insertIndex = i + 1;

        insertElementAt(node, insertIndex);
      }
    }
    else
    {
      insertIndex = this.size();

      boolean foundit = false;

      for (int i = 0; i < insertIndex; i++)
      {
        if (this.item(i).equals(node))
        {
          foundit = true;

          break;
        }
      }

      if (!foundit)
        addElement(node);
    }

    // checkDups();
    return insertIndex;
  }  // end addNodeInDocOrder(Vector v, Object obj)

  /**
   * Add the node into a vector of nodes where it should occur in
   * document order.
   * <p>
   *  将节点添加到节点的向量中,其中它应该以文档顺序出现。
   * 
   * 
   * @param node The node to be added.
   * @param support The XPath runtime context.
   *
   * @return The index where it was inserted.
   * @throws RuntimeException thrown if this NodeSet is not of
   * a mutable type.
   */
  public int addNodeInDocOrder(Node node, XPathContext support)
  {

    if (!m_mutable)
      throw new RuntimeException(XSLMessages.createXPATHMessage(XPATHErrorResources.ER_NODESET_NOT_MUTABLE, null)); //"This NodeSet is not mutable!");

    return addNodeInDocOrder(node, true, support);
  }  // end addNodeInDocOrder(Vector v, Object obj)


  /** If this node is being used as an iterator, the next index that nextNode()
  /* <p>
  /* 
   *  will return.  */
  transient protected int m_next = 0;

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
   * @return The the current position index.
   */
  public int getCurrentPos()
  {
    return m_next;
  }

  /**
   * Set the current position in the node set.
   * <p>
   *  设置节点集中的当前位置。
   * 
   * 
   * @param i Must be a valid index.
   * @throws RuntimeException thrown if this NodeSet is not of
   * a cached type, and thus doesn't permit indexed access.
   */
  public void setCurrentPos(int i)
  {

    if (!m_cacheNodes)
      throw new RuntimeException(
        XSLMessages.createXPATHMessage(XPATHErrorResources.ER_NODESET_CANNOT_INDEX, null)); //"This NodeSet can not do indexing or counting functions!");

    m_next = i;
  }

  /**
   * Return the last fetched node.  Needed to support the UnionPathIterator.
   *
   * <p>
   *  返回最后一个获取的节点。需要支持UnionPathIterator。
   * 
   * 
   * @return the last fetched node.
   * @throws RuntimeException thrown if this NodeSet is not of
   * a cached type, and thus doesn't permit indexed access.
   */
  public Node getCurrentNode()
  {

    if (!m_cacheNodes)
      throw new RuntimeException(
        XSLMessages.createXPATHMessage(XPATHErrorResources.ER_NODESET_CANNOT_INDEX, null)); //"This NodeSet can not do indexing or counting functions!");

    int saved = m_next;
    Node n = (m_next < m_firstFree) ? elementAt(m_next) : null;
    m_next = saved; // HACK: I think this is a bit of a hack.  -sb
    return n;
  }

  /** True if this list can be mutated.  */
  transient protected boolean m_mutable = true;

  /** True if this list is cached.
  /* <p>
  /* 
   *  @serial  */
  transient protected boolean m_cacheNodes = true;

  /**
   * Get whether or not this is a cached node set.
   *
   *
   * <p>
   *  获取这是否是高速缓存节点集。
   * 
   * 
   * @return True if this list is cached.
   */
  public boolean getShouldCacheNodes()
  {
    return m_cacheNodes;
  }

  /**
   * If setShouldCacheNodes(true) is called, then nodes will
   * be cached.  They are not cached by default. This switch must
   * be set before the first call to nextNode is made, to ensure
   * that all nodes are cached.
   *
   * <p>
   *  如果调用setShouldCacheNodes(true),则将缓存节点。默认情况下,它们不缓存。此开关必须在第一次调用nextNode之前设置,以确保所有节点都已缓存。
   * 
   * 
   * @param b true if this node set should be cached.
   * @throws RuntimeException thrown if an attempt is made to
   * request caching after we've already begun stepping through the
   * nodes in this set.
  */
  public void setShouldCacheNodes(boolean b)
  {

    if (!isFresh())
      throw new RuntimeException(
        XSLMessages.createXPATHMessage(XPATHErrorResources.ER_CANNOT_CALL_SETSHOULDCACHENODE, null)); //"Can not call setShouldCacheNodes after nextNode has been called!");

    m_cacheNodes = b;
    m_mutable = true;
  }


  transient private int m_last = 0;

  public int getLast()
  {
    return m_last;
  }

  public void setLast(int last)
  {
    m_last = last;
  }

  /** Size of blocks to allocate.
  /* <p>
  /* 
   *  @serial          */
  private int m_blocksize;

  /** Array of nodes this points to.
  /* <p>
  /* 
   *  @serial          */
  Node m_map[];

  /** Number of nodes in this NodeVector.
  /* <p>
  /* 
   *  @serial          */
  protected int m_firstFree = 0;

  /** Size of the array this points to.
  /* <p>
  /* 
   *  @serial           */
  private int m_mapSize;  // lazy initialization

  /**
   * Get a cloned LocPathIterator.
   *
   * <p>
   *  获取克隆的LocPathIterator。
   * 
   * 
   * @return A clone of this
   *
   * @throws CloneNotSupportedException
   */
  public Object clone() throws CloneNotSupportedException
  {

    NodeSet clone = (NodeSet) super.clone();

    if ((null != this.m_map) && (this.m_map == clone.m_map))
    {
      clone.m_map = new Node[this.m_map.length];

      System.arraycopy(this.m_map, 0, clone.m_map, 0, this.m_map.length);
    }

    return clone;
  }

  /**
   * Get the length of the list.
   *
   * <p>
   *  获取列表的长度。
   * 
   * 
   * @return Number of nodes in this NodeVector
   */
  public int size()
  {
    return m_firstFree;
  }

  /**
   * Append a Node onto the vector.
   *
   * <p>
   *  将一个节点附加到向量。
   * 
   * 
   * @param value Node to add to the vector
   */
  public void addElement(Node value)
  {
    if (!m_mutable)
      throw new RuntimeException(XSLMessages.createXPATHMessage(XPATHErrorResources.ER_NODESET_NOT_MUTABLE, null)); //"This NodeSet is not mutable!");

    if ((m_firstFree + 1) >= m_mapSize)
    {
      if (null == m_map)
      {
        m_map = new Node[m_blocksize];
        m_mapSize = m_blocksize;
      }
      else
      {
        m_mapSize += m_blocksize;

        Node newMap[] = new Node[m_mapSize];

        System.arraycopy(m_map, 0, newMap, 0, m_firstFree + 1);

        m_map = newMap;
      }
    }

    m_map[m_firstFree] = value;

    m_firstFree++;
  }

  /**
   * Append a Node onto the vector.
   *
   * <p>
   *  将一个节点附加到向量。
   * 
   * 
   * @param value Node to add to the vector
   */
  public final void push(Node value)
  {

    int ff = m_firstFree;

    if ((ff + 1) >= m_mapSize)
    {
      if (null == m_map)
      {
        m_map = new Node[m_blocksize];
        m_mapSize = m_blocksize;
      }
      else
      {
        m_mapSize += m_blocksize;

        Node newMap[] = new Node[m_mapSize];

        System.arraycopy(m_map, 0, newMap, 0, ff + 1);

        m_map = newMap;
      }
    }

    m_map[ff] = value;

    ff++;

    m_firstFree = ff;
  }

  /**
   * Pop a node from the tail of the vector and return the result.
   *
   * <p>
   * 从向量的尾部弹出一个节点并返回结果。
   * 
   * 
   * @return the node at the tail of the vector
   */
  public final Node pop()
  {

    m_firstFree--;

    Node n = m_map[m_firstFree];

    m_map[m_firstFree] = null;

    return n;
  }

  /**
   * Pop a node from the tail of the vector and return the
   * top of the stack after the pop.
   *
   * <p>
   *  从向量的尾部弹出一个节点,并在弹出后返回堆栈的顶部。
   * 
   * 
   * @return The top of the stack after it's been popped
   */
  public final Node popAndTop()
  {

    m_firstFree--;

    m_map[m_firstFree] = null;

    return (m_firstFree == 0) ? null : m_map[m_firstFree - 1];
  }

  /**
   * Pop a node from the tail of the vector.
   * <p>
   *  从向量的尾部弹出节点。
   * 
   */
  public final void popQuick()
  {

    m_firstFree--;

    m_map[m_firstFree] = null;
  }

  /**
   * Return the node at the top of the stack without popping the stack.
   * Special purpose method for TransformerImpl, pushElemTemplateElement.
   * Performance critical.
   *
   * <p>
   *  返回堆栈顶部的节点,而不弹出堆栈。 TransformerImpl,pushElemTemplateElement的特殊用途方法。性能关键。
   * 
   * 
   * @return Node at the top of the stack or null if stack is empty.
   */
  public final Node peepOrNull()
  {
    return ((null != m_map) && (m_firstFree > 0))
           ? m_map[m_firstFree - 1] : null;
  }

  /**
   * Push a pair of nodes into the stack.
   * Special purpose method for TransformerImpl, pushElemTemplateElement.
   * Performance critical.
   *
   * <p>
   *  将一对节点推入堆栈。 TransformerImpl,pushElemTemplateElement的特殊用途方法。性能关键。
   * 
   * 
   * @param v1 First node to add to vector
   * @param v2 Second node to add to vector
   */
  public final void pushPair(Node v1, Node v2)
  {

    if (null == m_map)
    {
      m_map = new Node[m_blocksize];
      m_mapSize = m_blocksize;
    }
    else
    {
      if ((m_firstFree + 2) >= m_mapSize)
      {
        m_mapSize += m_blocksize;

        Node newMap[] = new Node[m_mapSize];

        System.arraycopy(m_map, 0, newMap, 0, m_firstFree);

        m_map = newMap;
      }
    }

    m_map[m_firstFree] = v1;
    m_map[m_firstFree + 1] = v2;
    m_firstFree += 2;
  }

  /**
   * Pop a pair of nodes from the tail of the stack.
   * Special purpose method for TransformerImpl, pushElemTemplateElement.
   * Performance critical.
   * <p>
   *  从堆栈的尾部弹出一对节点。 TransformerImpl,pushElemTemplateElement的特殊用途方法。性能关键。
   * 
   */
  public final void popPair()
  {

    m_firstFree -= 2;
    m_map[m_firstFree] = null;
    m_map[m_firstFree + 1] = null;
  }

  /**
   * Set the tail of the stack to the given node.
   * Special purpose method for TransformerImpl, pushElemTemplateElement.
   * Performance critical.
   *
   * <p>
   *  将堆栈的尾部设置为给定节点。 TransformerImpl,pushElemTemplateElement的特殊用途方法。性能关键。
   * 
   * 
   * @param n Node to set at the tail of vector
   */
  public final void setTail(Node n)
  {
    m_map[m_firstFree - 1] = n;
  }

  /**
   * Set the given node one position from the tail.
   * Special purpose method for TransformerImpl, pushElemTemplateElement.
   * Performance critical.
   *
   * <p>
   *  将给定节点从尾部设置一个位置。 TransformerImpl,pushElemTemplateElement的特殊用途方法。性能关键。
   * 
   * 
   * @param n Node to set
   */
  public final void setTailSub1(Node n)
  {
    m_map[m_firstFree - 2] = n;
  }

  /**
   * Return the node at the tail of the vector without popping
   * Special purpose method for TransformerImpl, pushElemTemplateElement.
   * Performance critical.
   *
   * <p>
   *  返回向量的尾部节点而不弹出TransformerImpl,pushElemTemplateElement的特殊用途方法。性能关键。
   * 
   * 
   * @return Node at the tail of the vector
   */
  public final Node peepTail()
  {
    return m_map[m_firstFree - 1];
  }

  /**
   * Return the node one position from the tail without popping.
   * Special purpose method for TransformerImpl, pushElemTemplateElement.
   * Performance critical.
   *
   * <p>
   *  从尾部返回节点一个位置而不弹出。 TransformerImpl,pushElemTemplateElement的特殊用途方法。性能关键。
   * 
   * 
   * @return Node one away from the tail
   */
  public final Node peepTailSub1()
  {
    return m_map[m_firstFree - 2];
  }

  /**
   * Inserts the specified node in this vector at the specified index.
   * Each component in this vector with an index greater or equal to
   * the specified index is shifted upward to have an index one greater
   * than the value it had previously.
   *
   * <p>
   *  在指定的索引处将指定的节点插入到此向量中。该向量中具有大于或等于指定索引的索引的每个分量向上移位,以使索引1大于其先前的值。
   * 
   * 
   * @param value Node to insert
   * @param at Position where to insert
   */
  public void insertElementAt(Node value, int at)
  {
    if (!m_mutable)
      throw new RuntimeException(XSLMessages.createXPATHMessage(XPATHErrorResources.ER_NODESET_NOT_MUTABLE, null)); //"This NodeSet is not mutable!");

    if (null == m_map)
    {
      m_map = new Node[m_blocksize];
      m_mapSize = m_blocksize;
    }
    else if ((m_firstFree + 1) >= m_mapSize)
    {
      m_mapSize += m_blocksize;

      Node newMap[] = new Node[m_mapSize];

      System.arraycopy(m_map, 0, newMap, 0, m_firstFree + 1);

      m_map = newMap;
    }

    if (at <= (m_firstFree - 1))
    {
      System.arraycopy(m_map, at, m_map, at + 1, m_firstFree - at);
    }

    m_map[at] = value;

    m_firstFree++;
  }

  /**
   * Append the nodes to the list.
   *
   * <p>
   *  将节点附加到列表。
   * 
   * 
   * @param nodes NodeVector to append to this list
   */
  public void appendNodes(NodeSet nodes)
  {

    int nNodes = nodes.size();

    if (null == m_map)
    {
      m_mapSize = nNodes + m_blocksize;
      m_map = new Node[m_mapSize];
    }
    else if ((m_firstFree + nNodes) >= m_mapSize)
    {
      m_mapSize += (nNodes + m_blocksize);

      Node newMap[] = new Node[m_mapSize];

      System.arraycopy(m_map, 0, newMap, 0, m_firstFree + nNodes);

      m_map = newMap;
    }

    System.arraycopy(nodes.m_map, 0, m_map, m_firstFree, nNodes);

    m_firstFree += nNodes;
  }

  /**
   * Inserts the specified node in this vector at the specified index.
   * Each component in this vector with an index greater or equal to
   * the specified index is shifted upward to have an index one greater
   * than the value it had previously.
   * <p>
   * 在指定的索引处将指定的节点插入到此向量中。该向量中具有大于或等于指定索引的索引的每个分量向上移位,以使索引1大于其先前的值。
   * 
   */
  public void removeAllElements()
  {

    if (null == m_map)
      return;

    for (int i = 0; i < m_firstFree; i++)
    {
      m_map[i] = null;
    }

    m_firstFree = 0;
  }

  /**
   * Removes the first occurrence of the argument from this vector.
   * If the object is found in this vector, each component in the vector
   * with an index greater or equal to the object's index is shifted
   * downward to have an index one smaller than the value it had
   * previously.
   *
   * <p>
   *  从此向量中删除参数的第一次出现。如果在该向量中找到对象,则具有大于或等于对象的索引的索引的向量中的每个分量向下移位,以使索引1小于其先前的值。
   * 
   * 
   * @param s Node to remove from the list
   *
   * @return True if the node was successfully removed
   */
  public boolean removeElement(Node s)
  {
    if (!m_mutable)
      throw new RuntimeException(XSLMessages.createXPATHMessage(XPATHErrorResources.ER_NODESET_NOT_MUTABLE, null)); //"This NodeSet is not mutable!");

    if (null == m_map)
      return false;

    for (int i = 0; i < m_firstFree; i++)
    {
      Node node = m_map[i];

      if ((null != node) && node.equals(s))
      {
        if (i < m_firstFree - 1)
          System.arraycopy(m_map, i + 1, m_map, i, m_firstFree - i - 1);

        m_firstFree--;
        m_map[m_firstFree] = null;

        return true;
      }
    }

    return false;
  }

  /**
   * Deletes the component at the specified index. Each component in
   * this vector with an index greater or equal to the specified
   * index is shifted downward to have an index one smaller than
   * the value it had previously.
   *
   * <p>
   *  删除指定索引处的组件。该向量中具有大于或等于指定索引的索引的每个分量向下移动,以使索引1小于其先前的值。
   * 
   * 
   * @param i Index of node to remove
   */
  public void removeElementAt(int i)
  {

    if (null == m_map)
      return;

    if (i >= m_firstFree)
      throw new ArrayIndexOutOfBoundsException(i + " >= " + m_firstFree);
    else if (i < 0)
      throw new ArrayIndexOutOfBoundsException(i);

    if (i < m_firstFree - 1)
      System.arraycopy(m_map, i + 1, m_map, i, m_firstFree - i - 1);

    m_firstFree--;
    m_map[m_firstFree] = null;
  }

  /**
   * Sets the component at the specified index of this vector to be the
   * specified object. The previous component at that position is discarded.
   *
   * The index must be a value greater than or equal to 0 and less
   * than the current size of the vector.
   *
   * <p>
   *  将该向量的指定索引处的组件设置为指定的对象。在该位置的前一个组件被丢弃。
   * 
   *  索引必须是大于或等于0且小于向量当前大小的值。
   * 
   * 
   * @param node Node to set
   * @param index Index of where to set the node
   */
  public void setElementAt(Node node, int index)
  {
    if (!m_mutable)
      throw new RuntimeException(XSLMessages.createXPATHMessage(XPATHErrorResources.ER_NODESET_NOT_MUTABLE, null)); //"This NodeSet is not mutable!");

    if (null == m_map)
    {
      m_map = new Node[m_blocksize];
      m_mapSize = m_blocksize;
    }

    m_map[index] = node;
  }

  /**
   * Get the nth element.
   *
   * <p>
   *  获取第n个元素。
   * 
   * 
   * @param i Index of node to get
   *
   * @return Node at specified index
   */
  public Node elementAt(int i)
  {

    if (null == m_map)
      return null;

    return m_map[i];
  }

  /**
   * Tell if the table contains the given node.
   *
   * <p>
   *  告诉表是否包含给定的节点。
   * 
   * 
   * @param s Node to look for
   *
   * @return True if the given node was found.
   */
  public boolean contains(Node s)
  {
    runTo(-1);

    if (null == m_map)
      return false;

    for (int i = 0; i < m_firstFree; i++)
    {
      Node node = m_map[i];

      if ((null != node) && node.equals(s))
        return true;
    }

    return false;
  }

  /**
   * Searches for the first occurence of the given argument,
   * beginning the search at index, and testing for equality
   * using the equals method.
   *
   * <p>
   *  搜索给定参数的第一次出现,在索引处开始搜索,并使用equals方法测试等式。
   * 
   * 
   * @param elem Node to look for
   * @param index Index of where to start the search
   * @return the index of the first occurrence of the object
   * argument in this vector at position index or later in the
   * vector; returns -1 if the object is not found.
   */
  public int indexOf(Node elem, int index)
  {
    runTo(-1);

    if (null == m_map)
      return -1;

    for (int i = index; i < m_firstFree; i++)
    {
      Node node = m_map[i];

      if ((null != node) && node.equals(elem))
        return i;
    }

    return -1;
  }

  /**
   * Searches for the first occurence of the given argument,
   * beginning the search at index, and testing for equality
   * using the equals method.
   *
   * <p>
   *  搜索给定参数的第一次出现,在索引处开始搜索,并使用equals方法测试等式。
   * 
   * @param elem Node to look for
   * @return the index of the first occurrence of the object
   * argument in this vector at position index or later in the
   * vector; returns -1 if the object is not found.
   */
  public int indexOf(Node elem)
  {
    runTo(-1);

    if (null == m_map)
      return -1;

    for (int i = 0; i < m_firstFree; i++)
    {
      Node node = m_map[i];

      if ((null != node) && node.equals(elem))
        return i;
    }

    return -1;
  }

}
