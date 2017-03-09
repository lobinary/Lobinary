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
 * $Id: DTMDefaultBaseIterators.java,v 1.2.4.1 2005/09/15 08:15:00 suresh_emailid Exp $
 * <p>
 *  $ Id：DTMDefaultBaseIterators.java,v 1.2.4.1 2005/09/15 08:15:00 suresh_emailid Exp $
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
 * <p>
 *  这个类实现了DTMDefaultBase的遍历器。
 * 
 */
public abstract class DTMDefaultBaseIterators extends DTMDefaultBaseTraversers
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
  public DTMDefaultBaseIterators(DTMManager mgr, Source source,
                                 int dtmIdentity,
                                 DTMWSFilter whiteSpaceFilter,
                                 XMLStringFactory xstringfactory,
                                 boolean doIndexing)
  {
    super(mgr, source, dtmIdentity, whiteSpaceFilter,
          xstringfactory, doIndexing);
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
  public DTMDefaultBaseIterators(DTMManager mgr, Source source,
                                 int dtmIdentity,
                                 DTMWSFilter whiteSpaceFilter,
                                 XMLStringFactory xstringfactory,
                                 boolean doIndexing,
                                 int blocksize,
                                 boolean usePrevsib,
                                 boolean newNameTable)
  {
    super(mgr, source, dtmIdentity, whiteSpaceFilter,
          xstringfactory, doIndexing, blocksize, usePrevsib, newNameTable);
  }

  /**
   * Get an iterator that can navigate over an XPath Axis, predicated by
   * the extended type ID.
   * Returns an iterator that must be initialized
   * with a start node (using iterator.setStartNode()).
   *
   * <p>
   *  获取一个可以在XPath Axis上导航的迭代器,由扩展类型ID预测。返回一个必须用起始节点初始化的迭代器(使用iterator.setStartNode())。
   * 
   * 
   * @param axis One of Axes.ANCESTORORSELF, etc.
   * @param type An extended type ID.
   *
   * @return A DTMAxisIterator, or null if the given axis isn't supported.
   */
  public DTMAxisIterator getTypedAxisIterator(int axis, int type)
  {

    DTMAxisIterator iterator = null;

    /* This causes an error when using patterns for elements that
       do not exist in the DOM (translet types which do not correspond
       to a DOM type are mapped to the DOM.ELEMENT type).
    /* <p>
    /*  不存在于DOM中(与DOM类型不对应的translet类型映射到DOM.ELEMENT类型)。
    /* 
    */

    //        if (type == NO_TYPE) {
    //            return(EMPTYITERATOR);
    //        }
    //        else if (type == ELEMENT) {
    //            iterator = new FilterIterator(getAxisIterator(axis),
    //                                          getElementFilter());
    //        }
    //        else
    {
      switch (axis)
      {
      case Axis.SELF :
        iterator = new TypedSingletonIterator(type);
        break;
      case Axis.CHILD :
        iterator = new TypedChildrenIterator(type);
        break;
      case Axis.PARENT :
        return (new ParentIterator().setNodeType(type));
      case Axis.ANCESTOR :
        return (new TypedAncestorIterator(type));
      case Axis.ANCESTORORSELF :
        return ((new TypedAncestorIterator(type)).includeSelf());
      case Axis.ATTRIBUTE :
        return (new TypedAttributeIterator(type));
      case Axis.DESCENDANT :
        iterator = new TypedDescendantIterator(type);
        break;
      case Axis.DESCENDANTORSELF :
        iterator = (new TypedDescendantIterator(type)).includeSelf();
        break;
      case Axis.FOLLOWING :
        iterator = new TypedFollowingIterator(type);
        break;
      case Axis.PRECEDING :
        iterator = new TypedPrecedingIterator(type);
        break;
      case Axis.FOLLOWINGSIBLING :
        iterator = new TypedFollowingSiblingIterator(type);
        break;
      case Axis.PRECEDINGSIBLING :
        iterator = new TypedPrecedingSiblingIterator(type);
        break;
      case Axis.NAMESPACE :
        iterator = new TypedNamespaceIterator(type);
        break;
      case Axis.ROOT :
        iterator = new TypedRootIterator(type);
        break;
      default :
        throw new DTMException(XMLMessages.createXMLMessage(
            XMLErrorResources.ER_TYPED_ITERATOR_AXIS_NOT_IMPLEMENTED,
            new Object[]{Axis.getNames(axis)}));
            //"Error: typed iterator for axis "
                               //+ Axis.names[axis] + "not implemented");
      }
    }

    return (iterator);
  }

  /**
   * This is a shortcut to the iterators that implement the
   * XPath axes.
   * Returns a bare-bones iterator that must be initialized
   * with a start node (using iterator.setStartNode()).
   *
   * <p>
   *  这是实现XPath轴的迭代器的快捷方式。返回一个裸体迭代器,它必须使用一个起始节点初始化(使用iterator.setStartNode())。
   * 
   * 
   * @param axis One of Axes.ANCESTORORSELF, etc.
   *
   * @return A DTMAxisIterator, or null if the given axis isn't supported.
   */
  public DTMAxisIterator getAxisIterator(final int axis)
  {

    DTMAxisIterator iterator = null;

    switch (axis)
    {
    case Axis.SELF :
      iterator = new SingletonIterator();
      break;
    case Axis.CHILD :
      iterator = new ChildrenIterator();
      break;
    case Axis.PARENT :
      return (new ParentIterator());
    case Axis.ANCESTOR :
      return (new AncestorIterator());
    case Axis.ANCESTORORSELF :
      return ((new AncestorIterator()).includeSelf());
    case Axis.ATTRIBUTE :
      return (new AttributeIterator());
    case Axis.DESCENDANT :
      iterator = new DescendantIterator();
      break;
    case Axis.DESCENDANTORSELF :
      iterator = (new DescendantIterator()).includeSelf();
      break;
    case Axis.FOLLOWING :
      iterator = new FollowingIterator();
      break;
    case Axis.PRECEDING :
      iterator = new PrecedingIterator();
      break;
    case Axis.FOLLOWINGSIBLING :
      iterator = new FollowingSiblingIterator();
      break;
    case Axis.PRECEDINGSIBLING :
      iterator = new PrecedingSiblingIterator();
      break;
    case Axis.NAMESPACE :
      iterator = new NamespaceIterator();
      break;
    case Axis.ROOT :
      iterator = new RootIterator();
      break;
    default :
      throw new DTMException(XMLMessages.createXMLMessage(
        XMLErrorResources.ER_ITERATOR_AXIS_NOT_IMPLEMENTED,
        new Object[]{Axis.getNames(axis)}));
        //"Error: iterator for axis '" + Axis.names[axis]
                             //+ "' not implemented");
    }

    return (iterator);
  }

  /**
   * Abstract superclass defining behaviors shared by all DTMDefault's
   * internal implementations of DTMAxisIterator. Subclass this (and
   * override, if necessary) to implement the specifics of an
   * individual axis iterator.
   *
   * Currently there isn't a lot here
   * <p>
   * 抽象超类定义由所有DTMDefault的DTMAxisIterator的内部实现共享的行为。将此子类(如果需要,覆盖)实现单个轴迭代器的细节。
   * 
   *  目前这里没有很多
   * 
   */
  public abstract class InternalAxisIteratorBase extends DTMAxisIteratorBase
  {

    // %REVIEW% We could opt to share _nodeType and setNodeType() as
    // well, and simply ignore them in iterators which don't use them.
    // But Scott's worried about the overhead involved in cloning
    // these, and wants them to have as few fields as possible. Note
    // that we can't create a TypedInternalAxisIteratorBase because
    // those are often based on the untyped versions and Java doesn't
    // support multiple inheritance. <sigh/>

    /**
     * Current iteration location. Usually this is the last location
     * returned (starting point for the next() search); for single-node
     * iterators it may instead be initialized to point to that single node.
     * <p>
     *  当前迭代位置。通常这是返回的最后一个位置(下一个()搜索的起始点);对于单节点迭代器,它可以被初始化为指向该单个节点。
     * 
     */
    protected int _currentNode;

    /**
     * Remembers the current node for the next call to gotoMark().
     *
     * %REVIEW% Should this save _position too?
     * <p>
     *  记住下一次调用gotoMark()的当前节点。
     * 
     *  ％REVIEW％这应该保存_position吗?
     * 
     */
    public void setMark()
    {
      _markedNode = _currentNode;
    }

    /**
     * Restores the current node remembered by setMark().
     *
     * %REVEIW% Should this restore _position too?
     * <p>
     *  恢复由setMark()记住的当前节点。
     * 
     *  ％REVEIW％这个还原_position吗?
     * 
     */
    public void gotoMark()
    {
      _currentNode = _markedNode;
    }

  }  // end of InternalAxisIteratorBase

  /**
   * Iterator that returns all immediate children of a given node
   * <p>
   *  迭代器返回给定节点的所有直接子节点
   * 
   */
  public final class ChildrenIterator extends InternalAxisIteratorBase
  {

    /**
     * Setting start to END should 'close' the iterator,
     * i.e. subsequent call to next() should return END.
     *
     * If the iterator is not restartable, this has no effect.
     * %REVIEW% Should it return/throw something in that case,
     * or set current node to END, to indicate request-not-honored?
     *
     * <p>
     *  设置开始到END应该"关闭"迭代器,即,next()的后续调用应该返回END。
     * 
     *  如果迭代器不可重新启动,这没有效果。 ％REVIEW％在这种情况下是否应该返回/ throw一些东西,或者将当前节点设置为END,以指示请求不成功?
     * 
     * 
     * @param node Sets the root of the iteration.
     *
     * @return A DTMAxisIterator set to the start of the iteration.
     */
    public DTMAxisIterator setStartNode(int node)
    {
//%HZ%: Added reference to DTMDefaultBase.ROOTNODE back in, temporarily
      if (node == DTMDefaultBase.ROOTNODE)
        node = getDocument();
      if (_isRestartable)
      {
        _startNode = node;
        _currentNode = (node == DTM.NULL) ? DTM.NULL
                                          : _firstch(makeNodeIdentity(node));

        return resetPosition();
      }

      return this;
    }

    /**
     * Get the next node in the iteration.
     *
     * <p>
     *  获取迭代中的下一个节点。
     * 
     * 
     * @return The next node handle in the iteration, or END if no more
     * are available.
     */
    public int next()
    {
      if (_currentNode != NULL) {
        int node = _currentNode;
        _currentNode = _nextsib(node);
        return returnNode(makeNodeHandle(node));
      }

      return END;
    }
  }  // end of ChildrenIterator

  /**
   * Iterator that returns the parent of a given node. Note that
   * this delivers only a single node; if you want all the ancestors,
   * see AncestorIterator.
   * <p>
   *  返回给定节点的父代的迭代器。注意,这只传递一个节点;如果你想要所有的祖先,请参见AncestorIterator。
   * 
   */
  public final class ParentIterator extends InternalAxisIteratorBase
  {

    /** The extended type ID that was requested. */
    private int _nodeType = -1;

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
    public DTMAxisIterator setStartNode(int node)
    {
//%HZ%: Added reference to DTMDefaultBase.ROOTNODE back in, temporarily
      if (node == DTMDefaultBase.ROOTNODE)
        node = getDocument();
      if (_isRestartable)
      {
        _startNode = node;
        _currentNode = getParent(node);

        return resetPosition();
      }

      return this;
    }

    /**
     * Set the node type of the parent that we're looking for.
     * Note that this does _not_ mean "find the nearest ancestor of
     * this type", but "yield the parent if it is of this type".
     *
     *
     * <p>
     *  设置我们要查找的父节点的节点类型。注意,这不是_not_意味着"找到这个类型的最近的祖先",而是"如果它是这种类型,则产生父类"。
     * 
     * 
     * @param type extended type ID.
     *
     * @return ParentIterator configured with the type filter set.
     */
    public DTMAxisIterator setNodeType(final int type)
    {

      _nodeType = type;

      return this;
    }

    /**
     * Get the next node in the iteration. In this case, we return
     * only the immediate parent, _if_ it matches the requested nodeType.
     *
     * <p>
     * 获取迭代中的下一个节点。在这种情况下,我们只返回直接父,_if_它匹配请求的nodeType。
     * 
     * 
     * @return The next node handle in the iteration, or END.
     */
    public int next()
    {
      int result = _currentNode;

      if (_nodeType >= DTM.NTYPES) {
        if (_nodeType != getExpandedTypeID(_currentNode)) {
          result = END;
        }
      } else if (_nodeType != NULL) {
        if (_nodeType != getNodeType(_currentNode)) {
          result = END;
        }
      }

      _currentNode = END;

      return returnNode(result);
    }
  }  // end of ParentIterator

  /**
   * Iterator that returns children of a given type for a given node.
   * The functionality chould be achieved by putting a filter on top
   * of a basic child iterator, but a specialised iterator is used
   * for efficiency (both speed and size of translet).
   * <p>
   *  返回给定节点的给定类型的子类的迭代器。该功能可以通过在基本子迭代器的顶部放置一个过滤器来实现,但是一个专门的迭代器用于效率(速度和translet的速度和大小)。
   * 
   */
  public final class TypedChildrenIterator extends InternalAxisIteratorBase
  {

    /** The extended type ID that was requested. */
    private final int _nodeType;

    /**
     * Constructor TypedChildrenIterator
     *
     *
     * <p>
     *  构造函数TypedChildrenIterator
     * 
     * 
     * @param nodeType The extended type ID being requested.
     */
    public TypedChildrenIterator(int nodeType)
    {
      _nodeType = nodeType;
    }

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
    public DTMAxisIterator setStartNode(int node)
    {
//%HZ%: Added reference to DTMDefaultBase.ROOTNODE back in, temporarily
      if (node == DTMDefaultBase.ROOTNODE)
        node = getDocument();
      if (_isRestartable)
      {
        _startNode = node;
        _currentNode = (node == DTM.NULL)
                                   ? DTM.NULL
                                   : _firstch(makeNodeIdentity(_startNode));

        return resetPosition();
      }

      return this;
    }

    /**
     * Get the next node in the iteration.
     *
     * <p>
     *  获取迭代中的下一个节点。
     * 
     * 
     * @return The next node handle in the iteration, or END.
     */
    public int next()
    {
      int eType;
      int node = _currentNode;

      int nodeType = _nodeType;

      if (nodeType >= DTM.NTYPES) {
        while (node != DTM.NULL && _exptype(node) != nodeType) {
          node = _nextsib(node);
        }
      } else {
        while (node != DTM.NULL) {
          eType = _exptype(node);
          if (eType < DTM.NTYPES) {
            if (eType == nodeType) {
              break;
            }
          } else if (m_expandedNameTable.getType(eType) == nodeType) {
            break;
          }
          node = _nextsib(node);
        }
      }

      if (node == DTM.NULL) {
        _currentNode = DTM.NULL;
        return DTM.NULL;
      } else {
        _currentNode = _nextsib(node);
        return returnNode(makeNodeHandle(node));
      }

    }
  }  // end of TypedChildrenIterator

  /**
   * Iterator that returns children within a given namespace for a
   * given node. The functionality chould be achieved by putting a
   * filter on top of a basic child iterator, but a specialised
   * iterator is used for efficiency (both speed and size of translet).
   * <p>
   *  Iterator返回给定节点的给定命名空间内的子节点。该功能可以通过在基本子迭代器的顶部放置一个过滤器来实现,但是一个专门的迭代器用于效率(速度和translet的速度和大小)。
   * 
   */
  public final class NamespaceChildrenIterator
          extends InternalAxisIteratorBase
  {

    /** The extended type ID being requested. */
    private final int _nsType;

    /**
     * Constructor NamespaceChildrenIterator
     *
     *
     * <p>
     *  构造函数NamespaceChildrenIterator
     * 
     * 
     * @param type The extended type ID being requested.
     */
    public NamespaceChildrenIterator(final int type)
    {
      _nsType = type;
    }

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
    public DTMAxisIterator setStartNode(int node)
    {
//%HZ%: Added reference to DTMDefaultBase.ROOTNODE back in, temporarily
      if (node == DTMDefaultBase.ROOTNODE)
        node = getDocument();
      if (_isRestartable)
      {
        _startNode = node;
        _currentNode = (node == DTM.NULL) ? DTM.NULL : NOTPROCESSED;

        return resetPosition();
      }

      return this;
    }

    /**
     * Get the next node in the iteration.
     *
     * <p>
     *  获取迭代中的下一个节点。
     * 
     * 
     * @return The next node handle in the iteration, or END.
     */
    public int next()
    {
      if (_currentNode != DTM.NULL) {
        for (int node = (NOTPROCESSED == _currentNode)
                                  ? _firstch(makeNodeIdentity(_startNode))
                                  : _nextsib(_currentNode);
             node != END;
             node = _nextsib(node)) {
          if (m_expandedNameTable.getNamespaceID(_exptype(node)) == _nsType) {
            _currentNode = node;

            return returnNode(node);
          }
        }
      }

      return END;
    }
  }  // end of NamespaceChildrenIterator

  /**
   * Iterator that returns the namespace nodes as defined by the XPath data model
   * for a given node.
   * <p>
   *  Iterator返回给定节点的XPath数据模型定义的命名空间节点。
   * 
   */
  public class NamespaceIterator
          extends InternalAxisIteratorBase
  {

    /**
     * Constructor NamespaceAttributeIterator
     * <p>
     *  构造函数NamespaceAttributeIterator
     * 
     */
    public NamespaceIterator()
    {

      super();
    }

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
    public DTMAxisIterator setStartNode(int node)
    {
//%HZ%: Added reference to DTMDefaultBase.ROOTNODE back in, temporarily
      if (node == DTMDefaultBase.ROOTNODE)
        node = getDocument();
      if (_isRestartable)
      {
        _startNode = node;
        _currentNode = getFirstNamespaceNode(node, true);

        return resetPosition();
      }

      return this;
    }

    /**
     * Get the next node in the iteration.
     *
     * <p>
     *  获取迭代中的下一个节点。
     * 
     * 
     * @return The next node handle in the iteration, or END.
     */
    public int next()
    {

      int node = _currentNode;

      if (DTM.NULL != node)
        _currentNode = getNextNamespaceNode(_startNode, node, true);

      return returnNode(node);
    }
  }  // end of NamespaceIterator

  /**
   * Iterator that returns the namespace nodes as defined by the XPath data model
   * for a given node, filtered by extended type ID.
   * <p>
   *  Iterator返回由扩展类型ID过滤的给定节点的XPath数据模型定义的命名空间节点。
   * 
   */
  public class TypedNamespaceIterator extends NamespaceIterator
  {

    /** The extended type ID that was requested. */
    private final int _nodeType;

    /**
     * Constructor TypedNamespaceIterator
     *
     *
     * <p>
     *  构造函数TypedNamespaceIterator
     * 
     * 
     * @param nodeType The extended type ID being requested.
     */
    public TypedNamespaceIterator(int nodeType)
    {
      super();
      _nodeType = nodeType;
    }

    /**
     * Get the next node in the iteration.
     *
     * <p>
     *  获取迭代中的下一个节点。
     * 
     * 
     * @return The next node handle in the iteration, or END.
     */
    public int next()
    {
        int node;

      for (node = _currentNode;
           node != END;
           node = getNextNamespaceNode(_startNode, node, true)) {
        if (getExpandedTypeID(node) == _nodeType
            || getNodeType(node) == _nodeType
            || getNamespaceType(node) == _nodeType) {
          _currentNode = node;

          return returnNode(node);
        }
      }

      return (_currentNode =END);
    }
  }  // end of TypedNamespaceIterator

  /**
   * Iterator that returns the the root node as defined by the XPath data model
   * for a given node.
   * <p>
   * 迭代器,返回由给定节点的XPath数据模型定义的根节点。
   * 
   */
  public class RootIterator
          extends InternalAxisIteratorBase
  {

    /**
     * Constructor RootIterator
     * <p>
     *  构造函数RootIterator
     * 
     */
    public RootIterator()
    {

      super();
    }

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
    public DTMAxisIterator setStartNode(int node)
    {

      if (_isRestartable)
      {
        _startNode = getDocumentRoot(node);
        _currentNode = NULL;

        return resetPosition();
      }

      return this;
    }

    /**
     * Get the next node in the iteration.
     *
     * <p>
     *  获取迭代中的下一个节点。
     * 
     * 
     * @return The next node handle in the iteration, or END.
     */
    public int next()
    {
      if(_startNode == _currentNode)
        return NULL;

      _currentNode = _startNode;

      return returnNode(_startNode);
    }
  }  // end of RootIterator

  /**
   * Iterator that returns the namespace nodes as defined by the XPath data model
   * for a given node, filtered by extended type ID.
   * <p>
   *  Iterator返回由扩展类型ID过滤的给定节点的XPath数据模型定义的命名空间节点。
   * 
   */
  public class TypedRootIterator extends RootIterator
  {

    /** The extended type ID that was requested. */
    private final int _nodeType;

    /**
     * Constructor TypedRootIterator
     *
     * <p>
     *  构造函数TypedRootIterator
     * 
     * 
     * @param nodeType The extended type ID being requested.
     */
    public TypedRootIterator(int nodeType)
    {
      super();
      _nodeType = nodeType;
    }

    /**
     * Get the next node in the iteration.
     *
     * <p>
     *  获取迭代中的下一个节点。
     * 
     * 
     * @return The next node handle in the iteration, or END.
     */
    public int next()
    {
        if(_startNode == _currentNode)
        return NULL;

      int nodeType = _nodeType;
      int node = _startNode;
      int expType = getExpandedTypeID(node);

      _currentNode = node;

      if (nodeType >= DTM.NTYPES) {
        if (nodeType == expType) {
          return returnNode(node);
        }
      } else {
        if (expType < DTM.NTYPES) {
          if (expType == nodeType) {
            return returnNode(node);
          }
        } else {
          if (m_expandedNameTable.getType(expType) == nodeType) {
            return returnNode(node);
          }
        }
      }

      return END;
    }
  }  // end of TypedRootIterator

  /**
   * Iterator that returns attributes within a given namespace for a node.
   * <p>
   *  返回节点的给定命名空间内的属性的迭代器。
   * 
   */
  public final class NamespaceAttributeIterator
          extends InternalAxisIteratorBase
  {

    /** The extended type ID being requested. */
    private final int _nsType;

    /**
     * Constructor NamespaceAttributeIterator
     *
     *
     * <p>
     *  构造函数NamespaceAttributeIterator
     * 
     * 
     * @param nsType The extended type ID being requested.
     */
    public NamespaceAttributeIterator(int nsType)
    {

      super();

      _nsType = nsType;
    }

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
    public DTMAxisIterator setStartNode(int node)
    {
//%HZ%: Added reference to DTMDefaultBase.ROOTNODE back in, temporarily
      if (node == DTMDefaultBase.ROOTNODE)
        node = getDocument();
      if (_isRestartable)
      {
        _startNode = node;
        _currentNode = getFirstNamespaceNode(node, false);

        return resetPosition();
      }

      return this;
    }

    /**
     * Get the next node in the iteration.
     *
     * <p>
     *  获取迭代中的下一个节点。
     * 
     * 
     * @return The next node handle in the iteration, or END.
     */
    public int next()
    {

      int node = _currentNode;

      if (DTM.NULL != node)
        _currentNode = getNextNamespaceNode(_startNode, node, false);

      return returnNode(node);
    }
  }  // end of NamespaceAttributeIterator

  /**
   * Iterator that returns all siblings of a given node.
   * <p>
   *  返回给定节点的所有兄弟节点的迭代器。
   * 
   */
  public class FollowingSiblingIterator extends InternalAxisIteratorBase
  {

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
    public DTMAxisIterator setStartNode(int node)
    {
//%HZ%: Added reference to DTMDefaultBase.ROOTNODE back in, temporarily
      if (node == DTMDefaultBase.ROOTNODE)
        node = getDocument();
      if (_isRestartable)
      {
        _startNode = node;
        _currentNode = makeNodeIdentity(node);

        return resetPosition();
      }

      return this;
    }

    /**
     * Get the next node in the iteration.
     *
     * <p>
     *  获取迭代中的下一个节点。
     * 
     * 
     * @return The next node handle in the iteration, or END.
     */
    public int next()
    {
      _currentNode = (_currentNode == DTM.NULL) ? DTM.NULL
                                                : _nextsib(_currentNode);
      return returnNode(makeNodeHandle(_currentNode));
    }
  }  // end of FollowingSiblingIterator

  /**
   * Iterator that returns all following siblings of a given node.
   * <p>
   *  返回给定节点的所有后续兄弟节点的Iterator。
   * 
   */
  public final class TypedFollowingSiblingIterator
          extends FollowingSiblingIterator
  {

    /** The extended type ID that was requested. */
    private final int _nodeType;

    /**
     * Constructor TypedFollowingSiblingIterator
     *
     *
     * <p>
     *  构造函数TypedFollowingSiblingIterator
     * 
     * 
     * @param type The extended type ID being requested.
     */
    public TypedFollowingSiblingIterator(int type)
    {
      _nodeType = type;
    }

    /**
     * Get the next node in the iteration.
     *
     * <p>
     *  获取迭代中的下一个节点。
     * 
     * 
     * @return The next node handle in the iteration, or END.
     */
    public int next()
    {
      if (_currentNode == DTM.NULL) {
        return DTM.NULL;
      }

      int node = _currentNode;
      int eType;
      int nodeType = _nodeType;

      if (nodeType >= DTM.NTYPES) {
        do {
          node = _nextsib(node);
        } while (node != DTM.NULL && _exptype(node) != nodeType);
      } else {
        while ((node = _nextsib(node)) != DTM.NULL) {
          eType = _exptype(node);
          if (eType < DTM.NTYPES) {
            if (eType == nodeType) {
              break;
            }
          } else if (m_expandedNameTable.getType(eType) == nodeType) {
            break;
          }
        }
      }

      _currentNode = node;

      return (_currentNode == DTM.NULL)
                      ? DTM.NULL
                      : returnNode(makeNodeHandle(_currentNode));
    }
  }  // end of TypedFollowingSiblingIterator

  /**
   * Iterator that returns attribute nodes (of what nodes?)
   * <p>
   *  迭代器返回属性节点(什么节点?)
   * 
   */
  public final class AttributeIterator extends InternalAxisIteratorBase
  {

    // assumes caller will pass element nodes

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
    public DTMAxisIterator setStartNode(int node)
    {
//%HZ%: Added reference to DTMDefaultBase.ROOTNODE back in, temporarily
      if (node == DTMDefaultBase.ROOTNODE)
        node = getDocument();
      if (_isRestartable)
      {
        _startNode = node;
        _currentNode = getFirstAttributeIdentity(makeNodeIdentity(node));

        return resetPosition();
      }

      return this;
    }

    /**
     * Get the next node in the iteration.
     *
     * <p>
     *  获取迭代中的下一个节点。
     * 
     * 
     * @return The next node handle in the iteration, or END.
     */
    public int next()
    {

      final int node = _currentNode;

      if (node != NULL) {
        _currentNode = getNextAttributeIdentity(node);
        return returnNode(makeNodeHandle(node));
      }

      return NULL;
    }
  }  // end of AttributeIterator

  /**
   * Iterator that returns attribute nodes of a given type
   * <p>
   *  返回给定类型的属性节点的迭代器
   * 
   */
  public final class TypedAttributeIterator extends InternalAxisIteratorBase
  {

    /** The extended type ID that was requested. */
    private final int _nodeType;

    /**
     * Constructor TypedAttributeIterator
     *
     *
     * <p>
     *  构造函数TypedAttributeIterator
     * 
     * 
     * @param nodeType The extended type ID that is requested.
     */
    public TypedAttributeIterator(int nodeType)
    {
      _nodeType = nodeType;
    }

    // assumes caller will pass element nodes

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
    public DTMAxisIterator setStartNode(int node)
    {
      if (_isRestartable)
      {
        _startNode = node;

        _currentNode = getTypedAttribute(node, _nodeType);

        return resetPosition();
      }

      return this;
    }

    /**
     * Get the next node in the iteration.
     *
     * <p>
     *  获取迭代中的下一个节点。
     * 
     * 
     * @return The next node handle in the iteration, or END.
     */
    public int next()
    {

      final int node = _currentNode;

      // singleton iterator, since there can only be one attribute of
      // a given type.
      _currentNode = NULL;

      return returnNode(node);
    }
  }  // end of TypedAttributeIterator

  /**
   * Iterator that returns preceding siblings of a given node
   * <p>
   *  返回给定节点的先前兄弟节点的迭代器
   * 
   */
  public class PrecedingSiblingIterator extends InternalAxisIteratorBase
  {

    /**
     * The node identity of _startNode for this iterator
     * <p>
     * 此迭代器的_startNode的节点标识
     * 
     */
    protected int _startNodeID;

    /**
     * True if this iterator has a reversed axis.
     *
     * <p>
     *  如果此迭代器具有反转轴,则为true。
     * 
     * 
     * @return true.
     */
    public boolean isReverse()
    {
      return true;
    }

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
    public DTMAxisIterator setStartNode(int node)
    {
//%HZ%: Added reference to DTMDefaultBase.ROOTNODE back in, temporarily
      if (node == DTMDefaultBase.ROOTNODE)
        node = getDocument();
      if (_isRestartable)
      {
        _startNode = node;
        node = _startNodeID = makeNodeIdentity(node);

        if(node == NULL)
        {
          _currentNode = node;
          return resetPosition();
        }

        int type = m_expandedNameTable.getType(_exptype(node));
        if(ExpandedNameTable.ATTRIBUTE == type
           || ExpandedNameTable.NAMESPACE == type )
        {
          _currentNode = node;
        }
        else
        {
          // Be careful to handle the Document node properly
          _currentNode = _parent(node);
          if(NULL!=_currentNode)
            _currentNode = _firstch(_currentNode);
          else
            _currentNode = node;
        }

        return resetPosition();
      }

      return this;
    }

    /**
     * Get the next node in the iteration.
     *
     * <p>
     *  获取迭代中的下一个节点。
     * 
     * 
     * @return The next node handle in the iteration, or END.
     */
    public int next()
    {

      if (_currentNode == _startNodeID || _currentNode == DTM.NULL)
      {
        return NULL;
      }
      else
      {
        final int node = _currentNode;
        _currentNode = _nextsib(node);

        return returnNode(makeNodeHandle(node));
      }
    }
  }  // end of PrecedingSiblingIterator

  /**
   * Iterator that returns preceding siblings of a given type for
   * a given node
   * <p>
   *  迭代器,返回给定节点的给定类型的先前兄弟节点
   * 
   */
  public final class TypedPrecedingSiblingIterator
          extends PrecedingSiblingIterator
  {

    /** The extended type ID that was requested. */
    private final int _nodeType;

    /**
     * Constructor TypedPrecedingSiblingIterator
     *
     *
     * <p>
     *  构造函数TypedPrecedingSiblingIterator
     * 
     * 
     * @param type The extended type ID being requested.
     */
    public TypedPrecedingSiblingIterator(int type)
    {
      _nodeType = type;
    }

    /**
     * Get the next node in the iteration.
     *
     * <p>
     *  获取迭代中的下一个节点。
     * 
     * 
     * @return The next node handle in the iteration, or END.
     */
    public int next()
    {
      int node = _currentNode;
      int expType;

      int nodeType = _nodeType;
      int startID = _startNodeID;

      if (nodeType >= DTM.NTYPES) {
        while (node != NULL && node != startID && _exptype(node) != nodeType) {
          node = _nextsib(node);
        }
      } else {
        while (node != NULL && node != startID) {
          expType = _exptype(node);
          if (expType < DTM.NTYPES) {
            if (expType == nodeType) {
              break;
            }
          } else {
            if (m_expandedNameTable.getType(expType) == nodeType) {
              break;
            }
          }
          node = _nextsib(node);
        }
      }

      if (node == DTM.NULL || node == _startNodeID) {
        _currentNode = NULL;
        return NULL;
      } else {
        _currentNode = _nextsib(node);
        return returnNode(makeNodeHandle(node));
      }
    }
  }  // end of TypedPrecedingSiblingIterator

  /**
   * Iterator that returns preceding nodes of a given node.
   * This includes the node set {root+1, start-1}, but excludes
   * all ancestors, attributes, and namespace nodes.
   * <p>
   *  返回给定节点的前一节点的迭代器。这包括节点集{root + 1,start-1},但排除所有祖先,属性和命名空间节点。
   * 
   */
  public class PrecedingIterator extends InternalAxisIteratorBase
  {

    /** The max ancestors, but it can grow... */
    private final int _maxAncestors = 8;

    /**
     * The stack of start node + ancestors up to the root of the tree,
     *  which we must avoid.
     * <p>
     *  堆栈的起始节点+祖先到树的根,这是我们必须避免的。
     * 
     */
    protected int[] _stack = new int[_maxAncestors];

    /** (not sure yet... -sb) */
    protected int _sp, _oldsp;

    protected int _markedsp, _markedNode, _markedDescendant;

    /* _currentNode precedes candidates.  This is the identity, not the handle! */

    /**
     * True if this iterator has a reversed axis.
     *
     * <p>
     *  如果此迭代器具有反转轴,则为true。
     * 
     * 
     * @return true since this iterator is a reversed axis.
     */
    public boolean isReverse()
    {
      return true;
    }

    /**
     * Returns a deep copy of this iterator.   The cloned iterator is not reset.
     *
     * <p>
     *  返回此迭代器的深度副本。克隆的迭代器不会重置。
     * 
     * 
     * @return a deep copy of this iterator.
     */
    public DTMAxisIterator cloneIterator()
    {
      _isRestartable = false;

      try
      {
        final PrecedingIterator clone = (PrecedingIterator) super.clone();
        final int[] stackCopy = new int[_stack.length];
        System.arraycopy(_stack, 0, stackCopy, 0, _stack.length);

        clone._stack = stackCopy;

        // return clone.reset();
        return clone;
      }
      catch (CloneNotSupportedException e)
      {
        throw new DTMException(XMLMessages.createXMLMessage(XMLErrorResources.ER_ITERATOR_CLONE_NOT_SUPPORTED, null)); //"Iterator clone not supported.");
      }
    }

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
    public DTMAxisIterator setStartNode(int node)
    {
//%HZ%: Added reference to DTMDefaultBase.ROOTNODE back in, temporarily
      if (node == DTMDefaultBase.ROOTNODE)
        node = getDocument();
      if (_isRestartable)
      {
        node = makeNodeIdentity(node);

        // iterator is not a clone
        int parent, index;

       if (_type(node) == DTM.ATTRIBUTE_NODE)
        node = _parent(node);

        _startNode = node;
        _stack[index = 0] = node;



                parent=node;
                while ((parent = _parent(parent)) != NULL)
                {
                        if (++index == _stack.length)
                        {
                                final int[] stack = new int[index + 4];
                                System.arraycopy(_stack, 0, stack, 0, index);
                                _stack = stack;
                        }
                        _stack[index] = parent;
        }
        if(index>0)
                --index; // Pop actual root node (if not start) back off the stack

        _currentNode=_stack[index]; // Last parent before root node

        _oldsp = _sp = index;

        return resetPosition();
      }

      return this;
    }

    /**
     * Get the next node in the iteration.
     *
     * <p>
     *  获取迭代中的下一个节点。
     * 
     * 
     * @return The next node handle in the iteration, or END.
     */
    public int next()
    {
        // Bugzilla 8324: We were forgetting to skip Attrs and NS nodes.
        // Also recoded the loop controls for clarity and to flatten out
        // the tail-recursion.
                for(++_currentNode;
                        _sp>=0;
                        ++_currentNode)
                {
                        if(_currentNode < _stack[_sp])
                        {
                                if(_type(_currentNode) != ATTRIBUTE_NODE &&
                                        _type(_currentNode) != NAMESPACE_NODE)
                                        return returnNode(makeNodeHandle(_currentNode));
                        }
                        else
                                --_sp;
                }
                return NULL;
    }

    // redefine DTMAxisIteratorBase's reset

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
    public DTMAxisIterator reset()
    {

      _sp = _oldsp;

      return resetPosition();
    }

    public void setMark() {
        _markedsp = _sp;
        _markedNode = _currentNode;
        _markedDescendant = _stack[0];
    }

    public void gotoMark() {
        _sp = _markedsp;
        _currentNode = _markedNode;
    }
  }  // end of PrecedingIterator

  /**
   * Iterator that returns preceding nodes of agiven type for a
   * given node. This includes the node set {root+1, start-1}, but
   * excludes all ancestors.
   * <p>
   *  迭代器,返回给定节点的给定类型的先前节点。这包括节点集{root + 1,start-1},但排除所有祖先。
   * 
   */
  public final class TypedPrecedingIterator extends PrecedingIterator
  {

    /** The extended type ID that was requested. */
    private final int _nodeType;

    /**
     * Constructor TypedPrecedingIterator
     *
     *
     * <p>
     *  构造函数TypedPrecedingIterator
     * 
     * 
     * @param type The extended type ID being requested.
     */
    public TypedPrecedingIterator(int type)
    {
      _nodeType = type;
    }

    /**
     * Get the next node in the iteration.
     *
     * <p>
     *  获取迭代中的下一个节点。
     * 
     * 
     * @return The next node handle in the iteration, or END.
     */
    public int next()
    {
      int node = _currentNode;
      int nodeType = _nodeType;

      if (nodeType >= DTM.NTYPES) {
        while (true) {
          node = node + 1;

          if (_sp < 0) {
            node = NULL;
            break;
          } else if (node >= _stack[_sp]) {
            if (--_sp < 0) {
              node = NULL;
              break;
            }
          } else if (_exptype(node) == nodeType) {
            break;
          }
        }
      } else {
        int expType;

        while (true) {
          node = node + 1;

          if (_sp < 0) {
            node = NULL;
            break;
          } else if (node >= _stack[_sp]) {
            if (--_sp < 0) {
              node = NULL;
              break;
            }
          } else {
            expType = _exptype(node);
            if (expType < DTM.NTYPES) {
              if (expType == nodeType) {
                break;
              }
            } else {
              if (m_expandedNameTable.getType(expType) == nodeType) {
                break;
              }
            }
          }
        }
      }

      _currentNode = node;

      return (node == NULL) ? NULL : returnNode(makeNodeHandle(node));
    }
  }  // end of TypedPrecedingIterator

  /**
   * Iterator that returns following nodes of for a given node.
   * <p>
   *  迭代器返回给定节点的以下节点。
   * 
   */
  public class FollowingIterator extends InternalAxisIteratorBase
  {
    DTMAxisTraverser m_traverser; // easier for now

    public FollowingIterator()
    {
      m_traverser = getAxisTraverser(Axis.FOLLOWING);
    }

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
    public DTMAxisIterator setStartNode(int node)
    {
//%HZ%: Added reference to DTMDefaultBase.ROOTNODE back in, temporarily
      if (node == DTMDefaultBase.ROOTNODE)
        node = getDocument();
      if (_isRestartable)
      {
        _startNode = node;

        // ?? -sb
        // find rightmost descendant (or self)
        // int current;
        // while ((node = getLastChild(current = node)) != NULL){}
        // _currentNode = current;
        _currentNode = m_traverser.first(node);

        // _currentNode precedes possible following(node) nodes
        return resetPosition();
      }

      return this;
    }

    /**
     * Get the next node in the iteration.
     *
     * <p>
     *  获取迭代中的下一个节点。
     * 
     * 
     * @return The next node handle in the iteration, or END.
     */
    public int next()
    {

      int node = _currentNode;

      _currentNode = m_traverser.next(_startNode, _currentNode);

      return returnNode(node);
    }
  }  // end of FollowingIterator

  /**
   * Iterator that returns following nodes of a given type for a given node.
   * <p>
   *  迭代器,返回给定节点的给定类型的以下节点。
   * 
   */
  public final class TypedFollowingIterator extends FollowingIterator
  {

    /** The extended type ID that was requested. */
    private final int _nodeType;

    /**
     * Constructor TypedFollowingIterator
     *
     *
     * <p>
     *  构造函数TypedFollowingIterator
     * 
     * 
     * @param type The extended type ID being requested.
     */
    public TypedFollowingIterator(int type)
    {
      _nodeType = type;
    }

    /**
     * Get the next node in the iteration.
     *
     * <p>
     *  获取迭代中的下一个节点。
     * 
     * 
     * @return The next node handle in the iteration, or END.
     */
    public int next()
    {

      int node;

      do{
       node = _currentNode;

      _currentNode = m_traverser.next(_startNode, _currentNode);

      }
      while (node != DTM.NULL
             && (getExpandedTypeID(node) != _nodeType && getNodeType(node) != _nodeType));

      return (node == DTM.NULL ? DTM.NULL :returnNode(node));
    }
  }  // end of TypedFollowingIterator

  /**
   * Iterator that returns the ancestors of a given node in document
   * order.  (NOTE!  This was changed from the XSLTC code!)
   * <p>
   * 迭代器,以文档顺序返回给定节点的祖先。 (注意！这从XSLTC代码改变了！)
   * 
   */
  public class AncestorIterator extends InternalAxisIteratorBase
  {
    com.sun.org.apache.xml.internal.utils.NodeVector m_ancestors =
         new com.sun.org.apache.xml.internal.utils.NodeVector();

    int m_ancestorsPos;

    int m_markedPos;

    /** The real start node for this axes, since _startNode will be adjusted. */
    int m_realStartNode;

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
      return m_realStartNode;
    }

    /**
     * True if this iterator has a reversed axis.
     *
     * <p>
     *  如果此迭代器具有反转轴,则为true。
     * 
     * 
     * @return true since this iterator is a reversed axis.
     */
    public final boolean isReverse()
    {
      return true;
    }

    /**
     * Returns a deep copy of this iterator.  The cloned iterator is not reset.
     *
     * <p>
     *  返回此迭代器的深度副本。克隆的迭代器不会重置。
     * 
     * 
     * @return a deep copy of this iterator.
     */
    public DTMAxisIterator cloneIterator()
    {
      _isRestartable = false;  // must set to false for any clone

      try
      {
        final AncestorIterator clone = (AncestorIterator) super.clone();

        clone._startNode = _startNode;

        // return clone.reset();
        return clone;
      }
      catch (CloneNotSupportedException e)
      {
        throw new DTMException(XMLMessages.createXMLMessage(XMLErrorResources.ER_ITERATOR_CLONE_NOT_SUPPORTED, null)); //"Iterator clone not supported.");
      }
    }

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
    public DTMAxisIterator setStartNode(int node)
    {
//%HZ%: Added reference to DTMDefaultBase.ROOTNODE back in, temporarily
      if (node == DTMDefaultBase.ROOTNODE)
        node = getDocument();
      m_realStartNode = node;

      if (_isRestartable)
      {
        int nodeID = makeNodeIdentity(node);

        if (!_includeSelf && node != DTM.NULL) {
          nodeID = _parent(nodeID);
          node = makeNodeHandle(nodeID);
        }

        _startNode = node;

        while (nodeID != END) {
          m_ancestors.addElement(node);
          nodeID = _parent(nodeID);
          node = makeNodeHandle(nodeID);
        }
        m_ancestorsPos = m_ancestors.size()-1;

        _currentNode = (m_ancestorsPos>=0)
                               ? m_ancestors.elementAt(m_ancestorsPos)
                               : DTM.NULL;

        return resetPosition();
      }

      return this;
    }

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
    public DTMAxisIterator reset()
    {

      m_ancestorsPos = m_ancestors.size()-1;

      _currentNode = (m_ancestorsPos>=0) ? m_ancestors.elementAt(m_ancestorsPos)
                                         : DTM.NULL;

      return resetPosition();
    }

    /**
     * Get the next node in the iteration.
     *
     * <p>
     *  获取迭代中的下一个节点。
     * 
     * 
     * @return The next node handle in the iteration, or END.
     */
    public int next()
    {

      int next = _currentNode;

      int pos = --m_ancestorsPos;

      _currentNode = (pos >= 0) ? m_ancestors.elementAt(m_ancestorsPos)
                                : DTM.NULL;

      return returnNode(next);
    }

    public void setMark() {
        m_markedPos = m_ancestorsPos;
    }

    public void gotoMark() {
        m_ancestorsPos = m_markedPos;
        _currentNode = m_ancestorsPos>=0 ? m_ancestors.elementAt(m_ancestorsPos)
                                         : DTM.NULL;
    }
  }  // end of AncestorIterator

  /**
   * Typed iterator that returns the ancestors of a given node.
   * <p>
   *  类型化迭代器,返回给定节点的祖先。
   * 
   */
  public final class TypedAncestorIterator extends AncestorIterator
  {

    /** The extended type ID that was requested. */
    private final int _nodeType;

    /**
     * Constructor TypedAncestorIterator
     *
     *
     * <p>
     *  构造函数TypedAncestorIterator
     * 
     * 
     * @param type The extended type ID being requested.
     */
    public TypedAncestorIterator(int type)
    {
      _nodeType = type;
    }

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
    public DTMAxisIterator setStartNode(int node)
    {
//%HZ%: Added reference to DTMDefaultBase.ROOTNODE back in, temporarily
      if (node == DTMDefaultBase.ROOTNODE)
        node = getDocument();
      m_realStartNode = node;

      if (_isRestartable)
      {
        int nodeID = makeNodeIdentity(node);
        int nodeType = _nodeType;

        if (!_includeSelf && node != DTM.NULL) {
          nodeID = _parent(nodeID);
        }

        _startNode = node;

        if (nodeType >= DTM.NTYPES) {
          while (nodeID != END) {
            int eType = _exptype(nodeID);

            if (eType == nodeType) {
              m_ancestors.addElement(makeNodeHandle(nodeID));
            }
            nodeID = _parent(nodeID);
          }
        } else {
          while (nodeID != END) {
            int eType = _exptype(nodeID);

            if ((eType >= DTM.NTYPES
                    && m_expandedNameTable.getType(eType) == nodeType)
                || (eType < DTM.NTYPES && eType == nodeType)) {
              m_ancestors.addElement(makeNodeHandle(nodeID));
            }
            nodeID = _parent(nodeID);
          }
        }
        m_ancestorsPos = m_ancestors.size()-1;

        _currentNode = (m_ancestorsPos>=0)
                               ? m_ancestors.elementAt(m_ancestorsPos)
                               : DTM.NULL;

        return resetPosition();
      }

      return this;
    }
  }  // end of TypedAncestorIterator

  /**
   * Iterator that returns the descendants of a given node.
   * <p>
   *  迭代器,返回给定节点的后代。
   * 
   */
  public class DescendantIterator extends InternalAxisIteratorBase
  {

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
    public DTMAxisIterator setStartNode(int node)
    {
//%HZ%: Added reference to DTMDefaultBase.ROOTNODE back in, temporarily
      if (node == DTMDefaultBase.ROOTNODE)
        node = getDocument();
      if (_isRestartable)
      {
        node = makeNodeIdentity(node);
        _startNode = node;

        if (_includeSelf)
          node--;

        _currentNode = node;

        return resetPosition();
      }

      return this;
    }

    /**
     * Tell if this node identity is a descendant.  Assumes that
     * the node info for the element has already been obtained.
     *
     * This one-sided test works only if the parent has been
     * previously tested and is known to be a descendent. It fails if
     * the parent is the _startNode's next sibling, or indeed any node
     * that follows _startNode in document order.  That may suffice
     * for this iterator, but it's not really an isDescendent() test.
     * %REVIEW% rename?
     *
     * <p>
     *  告诉这个节点标识是否是一个子孙。假设已经获得了元素的节点信息。
     * 
     *  这种单方面测试只有当父母以前已经过测试并且已知是后代时才会工作。如果父节点是_startNode的下一个兄弟节点,或者是文档顺序中跟随_startNode的任何节点,它将失败。
     * 这可能就足够了这个迭代器,但它不是一个真正的isDescendent()测试。 ％REVIEW％rename?。
     * 
     * 
     * @param identity The index number of the node in question.
     * @return true if the index is a descendant of _startNode.
     */
    protected boolean isDescendant(int identity)
    {
      return (_parent(identity) >= _startNode) || (_startNode == identity);
    }

    /**
     * Get the next node in the iteration.
     *
     * <p>
     *  获取迭代中的下一个节点。
     * 
     * 
     * @return The next node handle in the iteration, or END.
     */
    public int next()
    {
      if (_startNode == NULL) {
        return NULL;
      }

      if (_includeSelf && (_currentNode + 1) == _startNode)
          return returnNode(makeNodeHandle(++_currentNode)); // | m_dtmIdent);

      int node = _currentNode;
      int type;

      do {
        node++;
        type = _type(node);

        if (NULL == type ||!isDescendant(node)) {
          _currentNode = NULL;
          return END;
        }
      } while(ATTRIBUTE_NODE == type || TEXT_NODE == type
                 || NAMESPACE_NODE == type);

      _currentNode = node;
      return returnNode(makeNodeHandle(node));  // make handle.
    }

    /**
     * Reset.
     *
     * <p>
     *  重启。
     * 
     */
  public DTMAxisIterator reset()
  {

    final boolean temp = _isRestartable;

    _isRestartable = true;

    setStartNode(makeNodeHandle(_startNode));

    _isRestartable = temp;

    return this;
  }

  }  // end of DescendantIterator

  /**
   * Typed iterator that returns the descendants of a given node.
   * <p>
   *  类型化迭代器,返回给定节点的后代。
   * 
   */
  public final class TypedDescendantIterator extends DescendantIterator
  {

    /** The extended type ID that was requested. */
    private final int _nodeType;

    /**
     * Constructor TypedDescendantIterator
     *
     *
     * <p>
     *  构造函数TypedDescendantIterator
     * 
     * 
     * @param nodeType Extended type ID being requested.
     */
    public TypedDescendantIterator(int nodeType)
    {
      _nodeType = nodeType;
    }

    /**
     * Get the next node in the iteration.
     *
     * <p>
     *  获取迭代中的下一个节点。
     * 
     * 
     * @return The next node handle in the iteration, or END.
     */
    public int next()
    {
      int node;
      int type;

      if (_startNode == NULL) {
        return NULL;
      }

      node = _currentNode;

      do
      {
        node++;
        type = _type(node);

        if (NULL == type ||!isDescendant(node)) {
          _currentNode = NULL;
          return END;
        }
      }
      while (type != _nodeType && _exptype(node) != _nodeType);

      _currentNode = node;
      return returnNode(makeNodeHandle(node));
    }
  }  // end of TypedDescendantIterator

  /**
   * Iterator that returns the descendants of a given node.
   * I'm not exactly clear about this one... -sb
   * <p>
   * 迭代器,返回给定节点的后代。我不清楚这一个...
   * 
   */
  public class NthDescendantIterator extends DescendantIterator
  {

    /** The current nth position. */
    int _pos;

    /**
     * Constructor NthDescendantIterator
     *
     *
     * <p>
     *  构造函数NthDescendantIterator
     * 
     * 
     * @param pos The nth position being requested.
     */
    public NthDescendantIterator(int pos)
    {
      _pos = pos;
    }

    /**
     * Get the next node in the iteration.
     *
     * <p>
     *  获取迭代中的下一个节点。
     * 
     * 
     * @return The next node handle in the iteration, or END.
     */
    public int next()
    {

      // I'm not exactly clear yet what this is doing... -sb
      int node;

      while ((node = super.next()) != END)
      {
        node = makeNodeIdentity(node);

        int parent = _parent(node);
        int child = _firstch(parent);
        int pos = 0;

        do
        {
          int type = _type(child);

          if (ELEMENT_NODE == type)
            pos++;
        }
        while ((pos < _pos) && (child = _nextsib(child)) != END);

        if (node == child)
          return node;
      }

      return (END);
    }
  }  // end of NthDescendantIterator

  /**
   * Class SingletonIterator.
   * <p>
   *  类SingletonIterator。
   * 
   */
  public class SingletonIterator extends InternalAxisIteratorBase
  {

    /** (not sure yet what this is.  -sb)  (sc & sb remove final to compile in JDK 1.1.8) */
    private boolean _isConstant;

    /**
     * Constructor SingletonIterator
     *
     * <p>
     *  构造函数SingletonIterator
     * 
     */
    public SingletonIterator()
    {
      this(Integer.MIN_VALUE, false);
    }

    /**
     * Constructor SingletonIterator
     *
     *
     * <p>
     *  构造函数SingletonIterator
     * 
     * 
     * @param node The node handle to return.
     */
    public SingletonIterator(int node)
    {
      this(node, false);
    }

    /**
     * Constructor SingletonIterator
     *
     *
     * <p>
     *  构造函数SingletonIterator
     * 
     * 
     * @param node the node handle to return.
     * @param constant (Not sure what this is yet.  -sb)
     */
    public SingletonIterator(int node, boolean constant)
    {
      _currentNode = _startNode = node;
      _isConstant = constant;
    }

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
    public DTMAxisIterator setStartNode(int node)
    {
//%HZ%: Added reference to DTMDefaultBase.ROOTNODE back in, temporarily
      if (node == DTMDefaultBase.ROOTNODE)
        node = getDocument();
      if (_isConstant)
      {
        _currentNode = _startNode;

        return resetPosition();
      }
      else if (_isRestartable)
      {
          _currentNode = _startNode = node;

        return resetPosition();
      }

      return this;
    }

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
    public DTMAxisIterator reset()
    {

      if (_isConstant)
      {
        _currentNode = _startNode;

        return resetPosition();
      }
      else
      {
        final boolean temp = _isRestartable;

        _isRestartable = true;

        setStartNode(_startNode);

        _isRestartable = temp;
      }

      return this;
    }

    /**
     * Get the next node in the iteration.
     *
     * <p>
     *  获取迭代中的下一个节点。
     * 
     * 
     * @return The next node handle in the iteration, or END.
     */
    public int next()
    {

      final int result = _currentNode;

      _currentNode = END;

      return returnNode(result);
    }
  }  // end of SingletonIterator

  /**
   * Iterator that returns a given node only if it is of a given type.
   * <p>
   *  只有给定节点属于给定类型时才返回给定节点的迭代器。
   * 
   */
  public final class TypedSingletonIterator extends SingletonIterator
  {

    /** The extended type ID that was requested. */
    private final int _nodeType;

    /**
     * Constructor TypedSingletonIterator
     *
     *
     * <p>
     *  构造函数TypedSingletonIterator
     * 
     * 
     * @param nodeType The extended type ID being requested.
     */
    public TypedSingletonIterator(int nodeType)
    {
      _nodeType = nodeType;
    }

    /**
     * Get the next node in the iteration.
     *
     * <p>
     *  获取迭代中的下一个节点。
     * 
     * @return The next node handle in the iteration, or END.
     */
    public int next()
    {

      //final int result = super.next();
      final int result = _currentNode;
      int nodeType = _nodeType;

      _currentNode = END;

      if (nodeType >= DTM.NTYPES) {
        if (getExpandedTypeID(result) == nodeType) {
          return returnNode(result);
        }
      } else {
        if (getNodeType(result) == nodeType) {
          return returnNode(result);
        }
      }

      return NULL;
    }
  }  // end of TypedSingletonIterator
}
