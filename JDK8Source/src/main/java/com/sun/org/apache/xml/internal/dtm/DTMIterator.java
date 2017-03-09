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
 * $Id: DTMIterator.java,v 1.2.4.1 2005/09/15 08:14:54 suresh_emailid Exp $
 * <p>
 *  $ Id：DTMIterator.java,v 1.2.4.1 2005/09/15 08:14:54 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.dtm;

/**

 * <code>DTMIterators</code> are used to step through a (possibly
 * filtered) set of nodes.  Their API is modeled largely after the DOM
 * NodeIterator.
 *
 * <p>A DTMIterator is a somewhat unusual type of iterator, in that it
 * can serve both single node iteration and random access.</p>
 *
 * <p>The DTMIterator's traversal semantics, i.e. how it walks the tree,
 * are specified when it is created, possibly and probably by an XPath
 * <a href="http://www.w3.org/TR/xpath#NT-LocationPath>LocationPath</a> or
 * a <a href="http://www.w3.org/TR/xpath#NT-UnionExpr">UnionExpr</a>.</p>
 *
 * <p>A DTMIterator is meant to be created once as a master static object, and
 * then cloned many times for runtime use.  Or the master object itself may
 * be used for simpler use cases.</p>
 *
 * <p>At this time, we do not expect DTMIterator to emulate
 * NodeIterator's "maintain relative position" semantics under
 * document mutation.  It's likely to respond more like the
 * TreeWalker's "current node" semantics. However, since the base DTM
 * is immutable, this issue currently makes no practical
 * difference.</p>
 *
 * <p>
 *  <code> DTMIterators </code>用于遍历(可能过滤的)一组节点。他们的API主要是在DOM NodeIterator之后。
 * 
 *  <p> DTMIterator是一种有点不寻常的迭代器,因为它可以提供单节点迭代和随机访问。</p>
 * 
 *  <p> DTMIterator的遍历语义,即它如何遍历树,是在创建时指定的,可能是由一个XPath <a href ="http://www.w3.org/TR/xpath#NT-LocationPath >
 *  LocationPath </a>或<a href="http://www.w3.org/TR/xpath#NT-UnionExpr"> UnionExpr </a>。
 * </p>。
 * 
 * <p> DTMIterator是作为主静态对象创建一次,然后克隆多次以供运行时使用。或者主对象本身可用于更简单的用例。</p>
 * 
 *  <p>此时,我们不希望DTMIterator在文档突变下模拟NodeIterator的"维护相对位置"语义。它可能更像TreeWalker的"当前节点"语义。
 * 然而,由于基本DTM是不可变的,这个问题目前没有实际的区别。</p>。
 * 
 * 
 * <p>State: In progress!!</p> */
public interface DTMIterator
{

  // Constants returned by acceptNode, borrowed from the DOM Traversal chapter
  // %REVIEW% Should we explicitly initialize them from, eg,
  // org.w3c.dom.traversal.NodeFilter.FILTER_ACCEPT?

  /**
   * Accept the node.
   * <p>
   *  接受节点。
   * 
   */
  public static final short FILTER_ACCEPT = 1;

  /**
   * Reject the node. Same behavior as FILTER_SKIP. (In the DOM these
   * differ when applied to a TreeWalker but have the same result when
   * applied to a NodeIterator).
   * <p>
   *  拒绝节点。与FILTER_SKIP行为相同。 (在DOM中,当应用于TreeWalker时,这些不同,但在应用于NodeIterator时具有相同的结果)。
   * 
   */
  public static final short FILTER_REJECT = 2;

  /**
   * Skip this single node.
   * <p>
   *  跳过此单个节点。
   * 
   */
  public static final short FILTER_SKIP = 3;

  /**
   * Get an instance of a DTM that "owns" a node handle.  Since a node
   * iterator may be passed without a DTMManager, this allows the
   * caller to easily get the DTM using just the iterator.
   *
   * <p>
   *  获取"拥有"节点句柄的DTM的实例。由于节点迭代器可以在没有DTMManager的情况下传递,这允许调用者仅使用迭代器来容易地获得DTM。
   * 
   * 
   * @param nodeHandle the nodeHandle.
   *
   * @return a non-null DTM reference.
   */
  public DTM getDTM(int nodeHandle);

  /**
   * Get an instance of the DTMManager.  Since a node
   * iterator may be passed without a DTMManager, this allows the
   * caller to easily get the DTMManager using just the iterator.
   *
   * <p>
   *  获取DTMManager的实例。由于节点迭代器可以在没有DTMManager的情况下传递,这允许调用者只使用迭代器来容易地获得DTMManager。
   * 
   * 
   * @return a non-null DTMManager reference.
   */
  public DTMManager getDTMManager();

  /**
   * The root node of the <code>DTMIterator</code>, as specified when it
   * was created.  Note the root node is not the root node of the
   * document tree, but the context node from where the iteration
   * begins and ends.
   *
   * <p>
   *  创建时指定的<code> DTMIterator </code>的根节点。注意根节点不是文档树的根节点,而是从迭代开始和结束的上下文节点。
   * 
   * 
   * @return nodeHandle int Handle of the context node.
   */
  public int getRoot();

  /**
   * Reset the root node of the <code>DTMIterator</code>, overriding
   * the value specified when it was created.  Note the root node is
   * not the root node of the document tree, but the context node from
   * where the iteration begins.
   *
   * <p>
   *  重置<code> DTMIterator </code>的根节点,覆盖创建时指定的值。注意根节点不是文档树的根节点,而是从迭代开始的上下文节点。
   * 
   * 
   * @param nodeHandle int Handle of the context node.
   * @param environment The environment object.
   * The environment in which this iterator operates, which should provide:
   * <ul>
   * <li>a node (the context node... same value as "root" defined below) </li>
   * <li>a pair of non-zero positive integers (the context position and the context size) </li>
   * <li>a set of variable bindings </li>
   * <li>a function library </li>
   * <li>the set of namespace declarations in scope for the expression.</li>
   * <ul>
   *
   * <p>At this time the exact implementation of this environment is application
   * dependent.  Probably a proper interface will be created fairly soon.</p>
   *
   */
  public void setRoot(int nodeHandle, Object environment);

  /**
   * Reset the iterator to the start. After resetting, the next node returned
   * will be the root node -- or, if that's filtered out, the first node
   * within the root's subtree which is _not_ skipped by the filters.
   * <p>
   * 将迭代器重置为开始。复位后,返回的下一个节点将是根节点 - 或者,如果它被过滤掉,根子树中被过滤器跳过_not_的第一个节点。
   * 
   */
  public void reset();

  /**
   * This attribute determines which node types are presented via the
   * iterator. The available set of constants is defined above.
   * Nodes not accepted by
   * <code>whatToShow</code> will be skipped, but their children may still
   * be considered.
   *
   * <p>
   *  此属性确定通过迭代器呈现哪些节点类型。可用的常数集如上定义。不会被<code> whatToShow </code>接受的节点将被跳过,但是他们的孩子仍然可以被考虑。
   * 
   * 
   * @return one of the SHOW_XXX constants, or several ORed together.
   */
  public int getWhatToShow();

  /**
   * <p>The value of this flag determines whether the children of entity
   * reference nodes are visible to the iterator. If false, they  and
   * their descendants will be rejected. Note that this rejection takes
   * precedence over <code>whatToShow</code> and the filter. </p>
   *
   * <p> To produce a view of the document that has entity references
   * expanded and does not expose the entity reference node itself, use
   * the <code>whatToShow</code> flags to hide the entity reference node
   * and set <code>expandEntityReferences</code> to true when creating the
   * iterator. To produce a view of the document that has entity reference
   * nodes but no entity expansion, use the <code>whatToShow</code> flags
   * to show the entity reference node and set
   * <code>expandEntityReferences</code> to false.</p>
   *
   * <p>NOTE: In Xalan's use of DTM we will generally have fully expanded
   * entity references when the document tree was built, and thus this
   * flag will have no effect.</p>
   *
   * <p>
   *  <p>此标记的值确定实体引用节点的子项是否对迭代器可见。如果为假,他们及其后代将被拒绝。请注意,此拒绝优先于<code> whatToShow </code>和过滤器。 </p>
   * 
   *  <p>要生成具有实体引用扩展并且不公开实体引用节点本身的文档的视图,请使用<code> whatToShow </code>标志隐藏实体引用节点,并设置<code> expandEntityRefer
   * ences <代码>为true时创建迭代器。
   * 要生成具有实体引用节点但没有实体扩展的文档的视图,请使用<code> whatToShow </code>标志显示实体引用节点,并将<code> expandEntityReferences </code>
   * 设置为false。
   * </p >。
   * 
   *  <p>注意：在Xalan使用DTM时,我们通常会在构建文档树时完全展开实体引用,因此此标志不起作用。</p>
   * 
   * 
   * @return true if entity references will be expanded.  */
  public boolean getExpandEntityReferences();

  /**
   * Returns the next node in the set and advances the position of the
   * iterator in the set. After a <code>DTMIterator</code> has setRoot called,
   * the first call to <code>nextNode()</code> returns that root or (if it
   * is rejected by the filters) the first node within its subtree which is
   * not filtered out.
   * <p>
   * 返回集合中的下一个节点,并推进迭代器在集合中的位置。
   * 在<code> DTMIterator </code>调用setRoot之后,第一次调用<code> nextNode()</code>返回根或者(如果它被过滤器拒绝)其子树内的第一个节点过滤掉。
   * 
   * 
   * @return The next node handle in the set being iterated over, or
   *  <code>DTM.NULL</code> if there are no more members in that set.
   */
  public int nextNode();

  /**
   * Returns the previous node in the set and moves the position of the
   * <code>DTMIterator</code> backwards in the set.
   * <p>
   *  返回集合中的上一个节点,并在集合中向后移动<code> DTMIterator </code>的位置。
   * 
   * 
   * @return The previous node handle in the set being iterated over,
   *   or <code>DTM.NULL</code> if there are no more members in that set.
   */
  public int previousNode();

  /**
   * Detaches the <code>DTMIterator</code> from the set which it iterated
   * over, releasing any computational resources and placing the iterator
   * in the INVALID state. After <code>detach</code> has been invoked,
   * calls to <code>nextNode</code> or <code>previousNode</code> will
   * raise a runtime exception.
   * <p>
   *  从迭代的集合中分离<code> DTMIterator </code>,释放任何计算资源并将迭代器置于INVALID状态。
   * 调用<code> detach </code>后,对<code> nextNode </code>或<code> previousNode </code>的调用将引发运行时异常。
   * 
   */
  public void detach();

  /**
   * Specify if it's OK for detach to release the iterator for reuse.
   *
   * <p>
   *  指定detach是否可以释放迭代器以便重用。
   * 
   * 
   * @param allowRelease true if it is OK for detach to release this iterator
   * for pooling.
   */
  public void allowDetachToRelease(boolean allowRelease);

  /**
   * Get the current node in the iterator. Note that this differs from
   * the DOM's NodeIterator, where the current position lies between two
   * nodes (as part of the maintain-relative-position semantic).
   *
   * <p>
   *  获取迭代器中的当前节点。注意,这不同于DOM的NodeIterator,其中当前位置位于两个节点之间(作为维持相对位置语义的一部分)。
   * 
   * 
   * @return The current node handle, or -1.
   */
  public int getCurrentNode();

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

  //========= Random Access ==========

  /**
   * If setShouldCacheNodes(true) is called, then nodes will
   * be cached, enabling random access, and giving the ability to do
   * sorts and the like.  They are not cached by default.
   *
   * %REVIEW% Shouldn't the other random-access methods throw an exception
   * if they're called on a DTMIterator with this flag set false?
   *
   * <p>
   *  如果调用setShouldCacheNodes(true),那么节点将被缓存,启用随机访问,并提供执行排序等的能力。默认情况下,它们不缓存。
   * 
   *  ％REVIEW％如果在DTMIterator上调用这个标志设置为false,那么其他随机访问方法是否应该抛出异常?
   * 
   * 
   * @param b true if the nodes should be cached.
   */
  public void setShouldCacheNodes(boolean b);

  /**
   * Tells if this iterator can have nodes added to it or set via
   * the <code>setItem(int node, int index)</code> method.
   *
   * <p>
   * 告诉这个迭代器是否可以添加节点或通过<code> setItem(int node,int index)</code>方法设置节点。
   * 
   * 
   * @return True if the nodelist can be mutated.
   */
  public boolean isMutable();

  /** Get the current position within the cached list, which is one
   * less than the next nextNode() call will retrieve.  i.e. if you
   * call getCurrentPos() and the return is 0, the next fetch will
   * take place at index 1.
   *
   * <p>
   *  少于nextNode()调用将检索。即如果调用getCurrentPos()并且返回值为0,则下一次提取将在索引1处进行。
   * 
   * 
   * @return The position of the iteration.
   */
  public int getCurrentPos();

  /**
   * If an index is requested, NodeSetDTM will call this method
   * to run the iterator to the index.  By default this sets
   * m_next to the index.  If the index argument is -1, this
   * signals that the iterator should be run to the end and
   * completely fill the cache.
   *
   * <p>
   *  如果请求索引,NodeSetDTM将调用此方法来运行迭代器到索引。默认情况下,这将m_next设置为索引。如果索引参数是-1,这表示迭代器应该运行到结束并完全填充缓存。
   * 
   * 
   * @param index The index to run to, or -1 if the iterator should be run
   *              to the end.
   */
  public void runTo(int index);

  /**
   * Set the current position in the node set.
   *
   * <p>
   *  设置节点集中的当前位置。
   * 
   * 
   * @param i Must be a valid index.
   */
  public void setCurrentPos(int i);

  /**
   * Returns the <code>node handle</code> of an item in the collection. If
   * <code>index</code> is greater than or equal to the number of nodes in
   * the list, this returns <code>null</code>.
   *
   * <p>
   *  返回集合中项目的<code>节点句柄</code>。如果<code> index </code>大于或等于列表中的节点数,则返回<code> null </code>。
   * 
   * 
   * @param index of the item.
   * @return The node handle at the <code>index</code>th position in the
   *   <code>DTMIterator</code>, or <code>-1</code> if that is not a valid
   *   index.
   */
  public int item(int index);

  /**
   * Sets the node at the specified index of this vector to be the
   * specified node. The previous component at that position is discarded.
   *
   * <p>The index must be a value greater than or equal to 0 and less
   * than the current size of the vector.
   * The iterator must be in cached mode.</p>
   *
   * <p>Meant to be used for sorted iterators.</p>
   *
   * <p>
   *  将此向量的指定索引处的节点设置为指定的节点。在该位置的前一个组件被丢弃。
   * 
   *  <p>索引必须是大于或等于0且小于向量当前大小的值。迭代器必须处于缓存模式。</p>
   * 
   *  <p>适用于排序迭代器。</p>
   * 
   * 
   * @param node Node to set
   * @param index Index of where to set the node
   */
  public void setItem(int node, int index);

  /**
   * The number of nodes in the list. The range of valid child node indices
   * is 0 to <code>length-1</code> inclusive. Note that this requires running
   * the iterator to completion, and presumably filling the cache.
   *
   * <p>
   *  列表中的节点数。有效子节点索引的范围是0到<code> length-1 </code>。注意,这需要运行迭代器完成,并且可能填充缓存。
   * 
   * 
   * @return The number of nodes in the list.
   */
  public int getLength();

  //=========== Cloning operations. ============

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
  public DTMIterator cloneWithReset() throws CloneNotSupportedException;

  /**
   * Get a clone of this iterator, but don't reset the iteration in the
   * process, so that it may be used from the current position.
   *
   * <p>
   *  获取此迭代器的克隆,但不要重置进程中的迭代,以便可以从当前位置使用它。
   * 
   * 
   * @return A clone of this object.
   *
   * @throws CloneNotSupportedException
   */
  public Object clone() throws CloneNotSupportedException;

  /**
   * Returns true if all the nodes in the iteration well be returned in document
   * order.
   *
   * <p>
   * 如果迭代中的所有节点都以文档顺序返回,则返回true。
   * 
   * 
   * @return true if all the nodes in the iteration well be returned in document
   * order.
   */
  public boolean isDocOrdered();

  /**
   * Returns the axis being iterated, if it is known.
   *
   * <p>
   *  返回正在迭代的轴(如果已知)。
   * 
   * @return Axis.CHILD, etc., or -1 if the axis is not known or is of multiple
   * types.
   */
  public int getAxis();

}
