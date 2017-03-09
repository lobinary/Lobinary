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
 * $Id: LocPathIterator.java,v 1.2.4.2 2005/09/14 19:45:22 jeffsuttor Exp $
 * <p>
 *  $ Id：LocPathIterator.java,v 1.2.4.2 2005/09/14 19:45:22 jeffsuttor Exp $
 * 
 */
package com.sun.org.apache.xpath.internal.axes;

import com.sun.org.apache.xalan.internal.res.XSLMessages;
import com.sun.org.apache.xml.internal.dtm.DTM;
import com.sun.org.apache.xml.internal.dtm.DTMFilter;
import com.sun.org.apache.xml.internal.dtm.DTMIterator;
import com.sun.org.apache.xml.internal.dtm.DTMManager;
import com.sun.org.apache.xml.internal.utils.PrefixResolver;
import com.sun.org.apache.xpath.internal.ExpressionOwner;
import com.sun.org.apache.xpath.internal.XPathContext;
import com.sun.org.apache.xpath.internal.XPathVisitor;
import com.sun.org.apache.xpath.internal.compiler.Compiler;
import com.sun.org.apache.xpath.internal.objects.XNodeSet;
import com.sun.org.apache.xpath.internal.objects.XObject;
import com.sun.org.apache.xpath.internal.res.XPATHErrorResources;

/**
 * This class extends NodeSetDTM, which implements NodeIterator,
 * and fetches nodes one at a time in document order based on a XPath
 * <a href="http://www.w3.org/TR/xpath#NT-LocationPath>LocationPath</a>.
 *
 * <p>If setShouldCacheNodes(true) is called,
 * as each node is iterated via nextNode(), the node is also stored
 * in the NodeVector, so that previousNode() can easily be done, except in
 * the case where the LocPathIterator is "owned" by a UnionPathIterator,
 * in which case the UnionPathIterator will cache the nodes.</p>
 * @xsl.usage advanced
 * <p>
 *  此类扩展了NodeSetDTM,它实现了NodeIterator,并根据XPath以文档顺序一次获取一个节点<a href="http://www.w3.org/TR/xpath#NT-LocationPath>
 *  LocationPath </a >。
 * 
 *  <p>如果调用setShouldCacheNodes(true),由于每个节点都通过nextNode()进行迭代,所以该节点也存储在NodeVector中,因此可以方便地完成previousNode(
 * ),除了LocPathIterator为"owned "通过UnionPathIterator,在这种情况下UnionPathIterator将缓存节点。
 * </p> @ xsl.usage advanced。
 * 
 */
public abstract class LocPathIterator extends PredicatedNodeTest
        implements Cloneable, DTMIterator, java.io.Serializable, PathComponent
{
    static final long serialVersionUID = -4602476357268405754L;

  /**
   * Create a LocPathIterator object.
   *
   * <p>
   *  创建LocPathIterator对象。
   * 
   */
  protected LocPathIterator()
  {
  }


  /**
   * Create a LocPathIterator object.
   *
   * <p>
   *  创建LocPathIterator对象。
   * 
   * 
   * @param nscontext The namespace context for this iterator,
   * should be OK if null.
   */
  protected LocPathIterator(PrefixResolver nscontext)
  {

    setLocPathIterator(this);
    m_prefixResolver = nscontext;
  }

  /**
   * Create a LocPathIterator object, including creation
   * of step walkers from the opcode list, and call back
   * into the Compiler to create predicate expressions.
   *
   * <p>
   * 创建LocPathIterator对象,包括从操作码列表创建步骤步行器,并回调到编译器以创建谓词表达式。
   * 
   * 
   * @param compiler The Compiler which is creating
   * this expression.
   * @param opPos The position of this iterator in the
   * opcode list from the compiler.
   *
   * @throws javax.xml.transform.TransformerException
   */
  protected LocPathIterator(Compiler compiler, int opPos, int analysis)
          throws javax.xml.transform.TransformerException
  {
    this(compiler, opPos, analysis, true);
  }

  /**
   * Create a LocPathIterator object, including creation
   * of step walkers from the opcode list, and call back
   * into the Compiler to create predicate expressions.
   *
   * <p>
   *  创建LocPathIterator对象,包括从操作码列表创建步骤步行器,并回调到编译器以创建谓词表达式。
   * 
   * 
   * @param compiler The Compiler which is creating
   * this expression.
   * @param opPos The position of this iterator in the
   * opcode list from the compiler.
   * @param shouldLoadWalkers True if walkers should be
   * loaded, or false if this is a derived iterator and
   * it doesn't wish to load child walkers.
   *
   * @throws javax.xml.transform.TransformerException
   */
  protected LocPathIterator(
          Compiler compiler, int opPos, int analysis, boolean shouldLoadWalkers)
            throws javax.xml.transform.TransformerException
  {
    setLocPathIterator(this);
  }

  /**
   * Get the analysis bits for this walker, as defined in the WalkerFactory.
   * <p>
   *  获取WalkerFactory中定义的此Walker的分析位。
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
   * Read the object from a serialization stream.
   *
   * <p>
   *  从序列化流中读取对象。
   * 
   * 
   * @param stream Input stream to read from
   *
   * @throws java.io.IOException
   * @throws javax.xml.transform.TransformerException
   */
  private void readObject(java.io.ObjectInputStream stream)
          throws java.io.IOException, javax.xml.transform.TransformerException
  {
    try
    {
      stream.defaultReadObject();
      m_clones =  new IteratorPool(this);
    }
    catch (ClassNotFoundException cnfe)
    {
      throw new javax.xml.transform.TransformerException(cnfe);
    }
  }

  /**
   * Set the environment in which this iterator operates, which should provide:
   * a node (the context node... same value as "root" defined below)
   * a pair of non-zero positive integers (the context position and the context size)
   * a set of variable bindings
   * a function library
   * the set of namespace declarations in scope for the expression.
   *
   * <p>At this time the exact implementation of this environment is application
   * dependent.  Probably a proper interface will be created fairly soon.</p>
   *
   * <p>
   *  设置迭代器操作的环境,其应该提供：节点(上下文节点...与下面定义的"根"相同的值)一对非零正整数(上下文位置和上下文大小)a集合变量绑定函数库表达式作用域中的命名空间声明集。
   * 
   *  <p>此时此环境的确切实现是与应用程序相关的。可能很快就会创建正确的界面。</p>
   * 
   * 
   * @param environment The environment object.
   */
  public void setEnvironment(Object environment)
  {
    // no-op for now.
  }

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
  public DTM getDTM(int nodeHandle)
  {
    // %OPT%
    return m_execContext.getDTM(nodeHandle);
  }

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
  public DTMManager getDTMManager()
  {
    return m_execContext.getDTMManager();
  }

  /**
   * Execute this iterator, meaning create a clone that can
   * store state, and initialize it for fast execution from
   * the current runtime state.  When this is called, no actual
   * query from the current context node is performed.
   *
   * <p>
   *  执行此迭代器,意味着创建一个可以存储状态的克隆,并初始化它以便从当前运行时状态快速执行。当这被调用时,不执行来自当前上下文节点的实际查询。
   * 
   * 
   * @param xctxt The XPath execution context.
   *
   * @return An XNodeSet reference that holds this iterator.
   *
   * @throws javax.xml.transform.TransformerException
   */
  public XObject execute(XPathContext xctxt)
          throws javax.xml.transform.TransformerException
  {

    XNodeSet iter = new XNodeSet((LocPathIterator)m_clones.getInstance());

    iter.setRoot(xctxt.getCurrentNode(), xctxt);

    return iter;
  }

  /**
   * Execute an expression in the XPath runtime context, and return the
   * result of the expression.
   *
   *
   * <p>
   * 在XPath运行时上下文中执行一个表达式,并返回表达式的结果。
   * 
   * 
   * @param xctxt The XPath runtime context.
   * @param handler The target content handler.
   *
   * @return The result of the expression in the form of a <code>XObject</code>.
   *
   * @throws javax.xml.transform.TransformerException if a runtime exception
   *         occurs.
   * @throws org.xml.sax.SAXException
   */
  public void executeCharsToContentHandler(
          XPathContext xctxt, org.xml.sax.ContentHandler handler)
            throws javax.xml.transform.TransformerException,
                   org.xml.sax.SAXException
  {
    LocPathIterator clone = (LocPathIterator)m_clones.getInstance();

    int current = xctxt.getCurrentNode();
    clone.setRoot(current, xctxt);

    int node = clone.nextNode();
    DTM dtm = clone.getDTM(node);
    clone.detach();

    if(node != DTM.NULL)
    {
      dtm.dispatchCharactersEvents(node, handler, false);
    }
  }

  /**
   * Given an select expression and a context, evaluate the XPath
   * and return the resulting iterator.
   *
   * <p>
   *  给定一个select表达式和一个上下文,评估XPath并返回结果迭代器。
   * 
   * 
   * @param xctxt The execution context.
   * @param contextNode The node that "." expresses.
   * @throws TransformerException thrown if the active ProblemListener decides
   * the error condition is severe enough to halt processing.
   *
   * @throws javax.xml.transform.TransformerException
   * @xsl.usage experimental
   */
  public DTMIterator asIterator(
          XPathContext xctxt, int contextNode)
            throws javax.xml.transform.TransformerException
  {
    XNodeSet iter = new XNodeSet((LocPathIterator)m_clones.getInstance());

    iter.setRoot(contextNode, xctxt);

    return iter;
  }


  /**
   * Tell if the expression is a nodeset expression.
   *
   * <p>
   *  告诉表达式是否是一个nodeet表达式。
   * 
   * 
   * @return true if the expression can be represented as a nodeset.
   */
  public boolean isNodesetExpr()
  {
    return true;
  }

  /**
   * Return the first node out of the nodeset, if this expression is
   * a nodeset expression.  This is the default implementation for
   * nodesets.  Derived classes should try and override this and return a
   * value without having to do a clone operation.
   * <p>
   *  如果此表达式是节点集表达式,则将第一个节点返回节点集。这是节点集的默认实现。派生类应该尝试并覆盖此并返回一个值,而不必进行克隆操作。
   * 
   * 
   * @param xctxt The XPath runtime context.
   * @return the first node out of the nodeset, or DTM.NULL.
   */
  public int asNode(XPathContext xctxt)
    throws javax.xml.transform.TransformerException
  {
    DTMIterator iter = (DTMIterator)m_clones.getInstance();

    int current = xctxt.getCurrentNode();

    iter.setRoot(current, xctxt);

    int next = iter.nextNode();
    // m_clones.freeInstance(iter);
    iter.detach();
    return next;
  }

  /**
   * Evaluate this operation directly to a boolean.
   *
   * <p>
   *  直接将此操作评估为布尔值。
   * 
   * 
   * @param xctxt The runtime execution context.
   *
   * @return The result of the operation as a boolean.
   *
   * @throws javax.xml.transform.TransformerException
   */
  public boolean bool(XPathContext xctxt)
          throws javax.xml.transform.TransformerException
  {
    return (asNode(xctxt) != DTM.NULL);
  }


  /**
   * Set if this is an iterator at the upper level of
   * the XPath.
   *
   * <p>
   *  设置如果这是一个迭代器在XPath的上层。
   * 
   * 
   * @param b true if this location path is at the top level of the
   *          expression.
   * @xsl.usage advanced
   */
  public void setIsTopLevel(boolean b)
  {
    m_isTopLevel = b;
  }

  /**
   * Get if this is an iterator at the upper level of
   * the XPath.
   *
   * <p>
   *  获取如果这是一个迭代器在XPath的上层。
   * 
   * 
   * @return true if this location path is at the top level of the
   *          expression.
   * @xsl.usage advanced
   */
  public boolean getIsTopLevel()
  {
    return m_isTopLevel;
  }

  /**
   * Initialize the context values for this expression
   * after it is cloned.
   *
   * <p>
   *  在克隆后,对此表达式的上下文值进行初始化。
   * 
   * 
   * @param context The XPath runtime context for this
   * transformation.
   */
  public void setRoot(int context, Object environment)
  {

    m_context = context;

    XPathContext xctxt = (XPathContext)environment;
    m_execContext = xctxt;
    m_cdtm = xctxt.getDTM(context);

    m_currentContextNode = context; // only if top level?

    // Yech, shouldn't have to do this.  -sb
    if(null == m_prefixResolver)
        m_prefixResolver = xctxt.getNamespaceContext();

    m_lastFetched = DTM.NULL;
    m_foundLast = false;
    m_pos = 0;
    m_length = -1;

    if (m_isTopLevel)
      this.m_stackFrame = xctxt.getVarStack().getStackFrame();

    // reset();
  }

  /**
   * Set the next position index of this iterator.
   *
   * <p>
   *  设置这个迭代器的下一个位置索引。
   * 
   * 
   * @param next A value greater than or equal to zero that indicates the next
   * node position to fetch.
   */
  protected void setNextPosition(int next)
  {
    assertion(false, "setNextPosition not supported in this iterator!");
  }

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
   * @return A value greater than or equal to zero that indicates the next
   * node position to fetch.
   */
  public final int getCurrentPos()
  {
    return m_pos;
  }


  /**
   * If setShouldCacheNodes(true) is called, then nodes will
   * be cached.  They are not cached by default.
   *
   * <p>
   *  如果调用setShouldCacheNodes(true),则将缓存节点。默认情况下,它们不缓存。
   * 
   * 
   * @param b True if this iterator should cache nodes.
   */
  public void setShouldCacheNodes(boolean b)
  {

    assertion(false, "setShouldCacheNodes not supported by this iterater!");
  }

  /**
   * Tells if this iterator can have nodes added to it or set via
   * the <code>setItem(int node, int index)</code> method.
   *
   * <p>
   *  告诉这个迭代器是否可以添加节点或通过<code> setItem(int node,int index)</code>方法设置节点。
   * 
   * 
   * @return True if the nodelist can be mutated.
   */
  public boolean isMutable()
  {
    return false;
  }

  /**
   * Set the current position in the node set.
   *
   * <p>
   *  设置节点集中的当前位置。
   * 
   * 
   * @param i Must be a valid index greater
   * than or equal to zero and less than m_cachedNodes.size().
   */
  public void setCurrentPos(int i)
  {
        assertion(false, "setCurrentPos not supported by this iterator!");
  }

  /**
   * Increment the current position in the node set.
   * <p>
   *  增加节点集中的当前位置。
   * 
   */
  public void incrementCurrentPos()
  {
        m_pos++;
  }


  /**
   * Get the length of the cached nodes.
   *
   * <p>Note: for the moment at least, this only returns
   * the size of the nodes that have been fetched to date,
   * it doesn't attempt to run to the end to make sure we
   * have found everything.  This should be reviewed.</p>
   *
   * <p>
   *  获取缓存节点的长度。
   * 
   * <p>注意：至少在这一刻,这只返回到目前为止获取的节点的大小,它不试图运行到结束,以确保我们找到了一切。这应该审查。</p>
   * 
   * 
   * @return The size of the current cache list.
   */
  public int size()
  {
        assertion(false, "size() not supported by this iterator!");
        return 0;
  }

  /**
   *  Returns the <code>index</code> th item in the collection. If
   * <code>index</code> is greater than or equal to the number of nodes in
   * the list, this returns <code>null</code> .
   * <p>
   *  返回集合中的<code> index </code>项。如果<code> index </code>大于或等于列表中的节点数,则返回<code> null </code>。
   * 
   * 
   * @param index  Index into the collection.
   * @return  The node at the <code>index</code> th position in the
   *   <code>NodeList</code> , or <code>null</code> if that is not a valid
   *   index.
   */
  public int item(int index)
  {
        assertion(false, "item(int index) not supported by this iterator!");
        return 0;
  }

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
  public void setItem(int node, int index)
  {
        assertion(false, "setItem not supported by this iterator!");
  }

  /**
   *  The number of nodes in the list. The range of valid child node indices
   * is 0 to <code>length-1</code> inclusive.
   *
   * <p>
   *  列表中的节点数。有效子节点索引的范围是0到<code> length-1 </code>。
   * 
   * 
   * @return The number of nodes in the list, always greater or equal to zero.
   */
  public int getLength()
  {
    // Tell if this is being called from within a predicate.
        boolean isPredicateTest = (this == m_execContext.getSubContextList());

    // And get how many total predicates are part of this step.
        int predCount = getPredicateCount();

    // If we have already calculated the length, and the current predicate
    // is the first predicate, then return the length.  We don't cache
    // the anything but the length of the list to the first predicate.
    if (-1 != m_length && isPredicateTest && m_predicateIndex < 1)
                return m_length;

    // I'm a bit worried about this one, since it doesn't have the
    // checks found above.  I suspect it's fine.  -sb
    if (m_foundLast)
                return m_pos;

    // Create a clone, and count from the current position to the end
    // of the list, not taking into account the current predicate and
    // predicates after the current one.
    int pos = (m_predicateIndex >= 0) ? getProximityPosition() : m_pos;

    LocPathIterator clone;

    try
    {
      clone = (LocPathIterator) clone();
    }
    catch (CloneNotSupportedException cnse)
    {
      return -1;
    }

    // We want to clip off the last predicate, but only if we are a sub
    // context node list, NOT if we are a context list.  See pos68 test,
    // also test against bug4638.
    if (predCount > 0 && isPredicateTest)
    {
      // Don't call setPredicateCount, because it clones and is slower.
      clone.m_predCount = m_predicateIndex;
      // The line above used to be:
      // clone.m_predCount = predCount - 1;
      // ...which looks like a dumb bug to me. -sb
    }

    int next;

    while (DTM.NULL != (next = clone.nextNode()))
    {
      pos++;
    }

    if (isPredicateTest && m_predicateIndex < 1)
      m_length = pos;

    return pos;
  }

  /**
   * Tells if this NodeSetDTM is "fresh", in other words, if
   * the first nextNode() that is called will return the
   * first node in the set.
   *
   * <p>
   *  告诉这个NodeSetDTM是否"新鲜",换句话说,如果被调用的第一个nextNode()将返回集合中的第一个节点。
   * 
   * 
   * @return true of nextNode has not been called.
   */
  public boolean isFresh()
  {
    return (m_pos == 0);
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
   */
  public int previousNode()
  {
    throw new RuntimeException(
      XSLMessages.createXPATHMessage(XPATHErrorResources.ER_NODESETDTM_CANNOT_ITERATE, null)); //"This NodeSetDTM can not iterate to a previous node!");
  }

  /**
   * This attribute determines which node types are presented via the
   * iterator. The available set of constants is defined in the
   * <code>NodeFilter</code> interface.
   *
   * <p>This is somewhat useless at this time, since it doesn't
   * really return information that tells what this iterator will
   * show.  It is here only to fullfill the DOM NodeIterator
   * interface.</p>
   *
   * <p>
   *  此属性确定通过迭代器呈现哪些节点类型。可用的常量集在<code> NodeFilter </code>接口中定义。
   * 
   *  <p>这在这个时候有点无用,因为它不真的返回信息,告诉这个迭代器将显示什么。它只在这里完全填充DOM NodeIterator接口。</p>
   * 
   * 
   * @return For now, always NodeFilter.SHOW_ALL & ~NodeFilter.SHOW_ENTITY_REFERENCE.
   * @see org.w3c.dom.traversal.NodeIterator
   */
  public int getWhatToShow()
  {

    // TODO: ??
    return DTMFilter.SHOW_ALL & ~DTMFilter.SHOW_ENTITY_REFERENCE;
  }

  /**
   *  The filter used to screen nodes.  Not used at this time,
   * this is here only to fullfill the DOM NodeIterator
   * interface.
   *
   * <p>
   * 用于筛选节点的过滤器。目前没有使用,这里只是为了完全填充DOM NodeIterator接口。
   * 
   * 
   * @return Always null.
   * @see org.w3c.dom.traversal.NodeIterator
   */
  public DTMFilter getFilter()
  {
    return null;
  }

  /**
   * The root node of the Iterator, as specified when it was created.
   *
   * <p>
   *  迭代器的根节点,在创建时指定。
   * 
   * 
   * @return The "root" of this iterator, which, in XPath terms,
   * is the node context for this iterator.
   */
  public int getRoot()
  {
    return m_context;
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
   * @return Always true, since entity reference nodes are not
   * visible in the XPath model.
   */
  public boolean getExpandEntityReferences()
  {
    return true;
  }

  /** Control over whether it is OK for detach to reset the iterator. */
  protected boolean m_allowDetach = true;

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
  public void allowDetachToRelease(boolean allowRelease)
  {
    m_allowDetach = allowRelease;
  }

  /**
   *  Detaches the iterator from the set which it iterated over, releasing
   * any computational resources and placing the iterator in the INVALID
   * state. After<code>detach</code> has been invoked, calls to
   * <code>nextNode</code> or<code>previousNode</code> will raise the
   * exception INVALID_STATE_ERR.
   * <p>
   *  从迭代的集合中分离迭代器,释放任何计算资源并将迭代器置于INVALID状态。
   * 调用<code> detach </code>后,调用<code> nextNode </code>或<code> previousNode </code>会引发异常INVALID_STATE_ERR。
   *  从迭代的集合中分离迭代器,释放任何计算资源并将迭代器置于INVALID状态。
   * 
   */
  public void detach()
  {
    if(m_allowDetach)
    {
      // sb: allow reusing of cached nodes when possible?
      // m_cachedNodes = null;
      m_execContext = null;
      // m_prefixResolver = null;  sb: Why would this ever want to be null?
      m_cdtm = null;
      m_length = -1;
      m_pos = 0;
      m_lastFetched = DTM.NULL;
      m_context = DTM.NULL;
      m_currentContextNode = DTM.NULL;

      m_clones.freeInstance(this);
    }
  }

  /**
   * Reset the iterator.
   * <p>
   *  重置迭代器。
   * 
   */
  public void reset()
  {
        assertion(false, "This iterator can not reset!");
  }

  /**
   * Get a cloned Iterator that is reset to the beginning
   * of the query.
   *
   * <p>
   *  获取复位到查询开头的克隆迭代器。
   * 
   * 
   * @return A cloned NodeIterator set of the start of the query.
   *
   * @throws CloneNotSupportedException
   */
  public DTMIterator cloneWithReset() throws CloneNotSupportedException
  {
    LocPathIterator clone;
//    clone = (LocPathIterator) clone();
    clone = (LocPathIterator)m_clones.getInstanceOrThrow();
    clone.m_execContext = m_execContext;
    clone.m_cdtm = m_cdtm;

    clone.m_context = m_context;
    clone.m_currentContextNode = m_currentContextNode;
    clone.m_stackFrame = m_stackFrame;

    // clone.reset();

    return clone;
  }

//  /**
//   * Get a cloned LocPathIterator that holds the same
//   * position as this iterator.
//   *
//   * <p>
//   *  // *获取一个克隆的LocPathIterator,它拥有与此迭代器相同的// *位置。 // *
//   * 
//   * 
//   * @return A clone of this iterator that holds the same node position.
//   *
//   * @throws CloneNotSupportedException
//   */
//  public Object clone() throws CloneNotSupportedException
//  {
//
//    LocPathIterator clone = (LocPathIterator) super.clone();
//
//    return clone;
//  }

  /**
   *  Returns the next node in the set and advances the position of the
   * iterator in the set. After a NodeIterator is created, the first call
   * to nextNode() returns the first node in the set.
   * <p>
   * 返回集合中的下一个节点,并推进迭代器在集合中的位置。创建NodeIterator之后,第一次调用nextNode()返回集合中的第一个节点。
   * 
   * 
   * @return  The next <code>Node</code> in the set being iterated over, or
   *   <code>null</code> if there are no more members in that set.
   */
  public abstract int nextNode();

  /**
   * Bottleneck the return of a next node, to make returns
   * easier from nextNode().
   *
   * <p>
   *  瓶颈返回下一个节点,使返回更容易从nextNode()。
   * 
   * 
   * @param nextNode The next node found, may be null.
   *
   * @return The same node that was passed as an argument.
   */
  protected int returnNextNode(int nextNode)
  {

    if (DTM.NULL != nextNode)
    {
      m_pos++;
    }

    m_lastFetched = nextNode;

    if (DTM.NULL == nextNode)
      m_foundLast = true;

    return nextNode;
  }

  /**
   * Return the last fetched node.  Needed to support the UnionPathIterator.
   *
   * <p>
   *  返回最后一个获取的节点。需要支持UnionPathIterator。
   * 
   * 
   * @return The last fetched node, or null if the last fetch was null.
   */
  public int getCurrentNode()
  {
    return m_lastFetched;
  }

  /**
   * If an index is requested, NodeSetDTM will call this method
   * to run the iterator to the index.  By default this sets
   * m_next to the index.  If the index argument is -1, this
   * signals that the iterator should be run to the end.
   *
   * <p>
   *  如果请求索引,NodeSetDTM将调用此方法来运行迭代器到索引。默认情况下,这将m_next设置为索引。如果index参数是-1,这表示迭代器应该运行到结束。
   * 
   * 
   * @param index The index to run to, or -1 if the iterator
   * should run to the end.
   */
  public void runTo(int index)
  {

    if (m_foundLast || ((index >= 0) && (index <= getCurrentPos())))
      return;

    int n;

    if (-1 == index)
    {
      while (DTM.NULL != (n = nextNode()));
    }
    else
    {
      while (DTM.NULL != (n = nextNode()))
      {
        if (getCurrentPos() >= index)
          break;
      }
    }
  }

  /**
   * Tells if we've found the last node yet.
   *
   * <p>
   *  告诉我们是否找到了最后一个节点。
   * 
   * 
   * @return true if the last nextNode returned null.
   */
  public final boolean getFoundLast()
  {
    return m_foundLast;
  }

  /**
   * The XPath execution context we are operating on.
   *
   * <p>
   *  我们正在操作的XPath执行上下文。
   * 
   * 
   * @return XPath execution context this iterator is operating on,
   * or null if setRoot has not been called.
   */
  public final XPathContext getXPathContext()
  {
    return m_execContext;
  }

  /**
   * The node context for the iterator.
   *
   * <p>
   *  迭代器的节点上下文。
   * 
   * 
   * @return The node context, same as getRoot().
   */
  public final int getContext()
  {
    return m_context;
  }

  /**
   * The node context from where the expression is being
   * executed from (i.e. for current() support).
   *
   * <p>
   *  正在执行表达式的节点上下文(即对于current()支持)。
   * 
   * 
   * @return The top-level node context of the entire expression.
   */
  public final int getCurrentContextNode()
  {
    return m_currentContextNode;
  }

  /**
   * Set the current context node for this iterator.
   *
   * <p>
   *  设置此迭代器的当前上下文节点。
   * 
   * 
   * @param n Must be a non-null reference to the node context.
   */
  public final void setCurrentContextNode(int n)
  {
    m_currentContextNode = n;
  }

//  /**
//   * Set the current context node for this iterator.
//   *
//   * <p>
//   *  // *为此迭代器设置当前上下文节点。 // *
//   * 
//   * 
//   * @param n Must be a non-null reference to the node context.
//   */
//  public void setRoot(int n)
//  {
//    m_context = n;
//    m_cdtm = m_execContext.getDTM(n);
//  }

  /**
   * Return the saved reference to the prefix resolver that
   * was in effect when this iterator was created.
   *
   * <p>
   *  将保存的引用返回到创建此迭代器时有效的前缀解析器。
   * 
   * 
   * @return The prefix resolver or this iterator, which may be null.
   */
  public final PrefixResolver getPrefixResolver()
  {
        if(null == m_prefixResolver)
        {
        m_prefixResolver = (PrefixResolver)getExpressionOwner();
        }

    return m_prefixResolver;
  }

//  /**
//   * Get the analysis pattern built by the WalkerFactory.
//   *
//   * <p>
//   *  // *获取由WalkerFactory构建的分析模式。 // *
//   * 
//   * 
//   * @return The analysis pattern built by the WalkerFactory.
//   */
//  int getAnalysis()
//  {
//    return m_analysis;
//  }

//  /**
//   * Set the analysis pattern built by the WalkerFactory.
//   *
//   * <p>
//   *  // *设置WalkerFactory构建的分析模式。 // *
//   * 
//   * 
//   * @param a The analysis pattern built by the WalkerFactory.
//   */
//  void setAnalysis(int a)
//  {
//    m_analysis = a;
//  }

  /**
  /* <p>
  /* 
   * @see com.sun.org.apache.xpath.internal.XPathVisitable#callVisitors(ExpressionOwner, XPathVisitor)
   */
  public void callVisitors(ExpressionOwner owner, XPathVisitor visitor)
  {
                if(visitor.visitLocationPath(owner, this))
                {
                        visitor.visitStep(owner, this);
                        callPredicateVisitors(visitor);
                }
  }


  //============= State Data =============

  /**
   * The pool for cloned iterators.  Iterators need to be cloned
   * because the hold running state, and thus the original iterator
   * expression from the stylesheet pool can not be used.
   * <p>
   *  克隆迭代器的池。迭代器需要克隆,因为保持运行状态,因此不能使用样式表池中的原始迭代器表达式。
   * 
   */
  transient protected IteratorPool m_clones = new IteratorPool(this);

  /**
   * The dtm of the context node.  Careful about using this... it may not
   * be the dtm of the current node.
   * <p>
   *  上下文节点的dtm。仔细使用这个...它可能不是当前节点的dtm。
   * 
   */
  transient protected DTM m_cdtm;

  /**
   * The stack frame index for this iterator.
   * <p>
   *  此迭代器的堆栈帧索引。
   * 
   */
  transient int m_stackFrame = -1;

  /**
   * Value determined at compile time, indicates that this is an
   * iterator at the top level of the expression, rather than inside
   * a predicate.
   * <p>
   * 在编译时确定的值指示这是表达式顶层的迭代器,而不是谓词内。
   * 
   * 
   * @serial
   */
  private boolean m_isTopLevel = false;

  /** The last node that was fetched, usually by nextNode. */
  transient public int m_lastFetched = DTM.NULL;

  /**
   * The context node for this iterator, which doesn't change through
   * the course of the iteration.
   * <p>
   *  此迭代器的上下文节点,它不会在迭代过程中更改。
   * 
   */
  transient protected int m_context = DTM.NULL;

  /**
   * The node context from where the expression is being
   * executed from (i.e. for current() support).  Different
   * from m_context in that this is the context for the entire
   * expression, rather than the context for the subexpression.
   * <p>
   *  正在执行表达式的节点上下文(即对于current()支持)。与m_context不同的是,这是整个表达式的上下文,而不是子表达式的上下文。
   * 
   */
  transient protected int m_currentContextNode = DTM.NULL;

  /**
   * The current position of the context node.
   * <p>
   *  上下文节点的当前位置。
   * 
   */
  transient protected int m_pos = 0;

  transient protected int m_length = -1;

  /**
   * Fast access to the current prefix resolver.  It isn't really
   * clear that this is needed.
   * <p>
   *  快速访问当前前缀解析器。这不是很清楚,这是需要的。
   * 
   * 
   * @serial
   */
  private PrefixResolver m_prefixResolver;

  /**
   * The XPathContext reference, needed for execution of many
   * operations.
   * <p>
   *  XPathContext引用,需要执行许多操作。
   * 
   */
  transient protected XPathContext m_execContext;

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


//  /**
//   * The analysis pattern built by the WalkerFactory.
//   * TODO: Move to LocPathIterator.
//   * <p>
//   *  // * WalkerFactory构建的分析模式。 // * TODO：Move to LocPathIterator。
//   * 
//   * 
//   * @see com.sun.org.apache.xpath.internal.axes.WalkerFactory
//   * @serial
//   */
//  protected int m_analysis = 0x00000000;
  /**
  /* <p>
  /* 
   * @see PredicatedNodeTest#getLastPos(XPathContext)
   */
  public int getLastPos(XPathContext xctxt)
  {
    return getLength();
  }

}
