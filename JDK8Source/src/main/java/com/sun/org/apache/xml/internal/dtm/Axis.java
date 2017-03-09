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
 * $Id: Axis.java,v 1.2.4.1 2005/09/15 08:14:51 suresh_emailid Exp $
 * <p>
 *  $ Id：Axis.java,v 1.2.4.1 2005/09/15 08:14:51 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.dtm;

/**
 * Specifies values related to XPath Axes.
 * <p>The ancestor, descendant, following, preceding and self axes partition a
 * document (ignoring attribute and namespace nodes): they do not overlap
 * and together they contain all the nodes in the document.</p>
 *
 * <p>
 *  指定与XPath轴相关的值。 <p>祖先,子孙,后续,前置和自我分割文档(忽略属性和命名空间节点)：它们不重叠,它们一起包含文档中的所有节点。</p>
 * 
 */
public final class Axis
{

  /**
   * The ancestor axis contains the ancestors of the context node;
   *  the ancestors of the context node consist of the parent of context
   *  node and the parent's parent and so on; thus, the ancestor axis will
   *  always include the root node, unless the context node is the root node.
   * <p>
   *  祖先轴包含上下文节点的祖先;上下文节点的祖先由上下文节点的父节点和父节点的父节点组成,等等;因此,祖先轴将始终包括根节点,除非上下文节点是根节点。
   * 
   */
  public static final int ANCESTOR = 0;

  /**
   * the ancestor-or-self axis contains the context node and the ancestors of
   *  the context node; thus, the ancestor axis will always include the
   *  root node.
   * <p>
   *  祖先或自身轴包含上下文节点和上下文节点的祖先;因此,祖先轴将始终包括根节点。
   * 
   */
  public static final int ANCESTORORSELF = 1;

  /**
   * the attribute axis contains the attributes of the context node; the axis
   *  will be empty unless the context node is an element.
   * <p>
   * 属性轴包含上下文节点的属性;该轴将为空,除非上下文节点是一个元素。
   * 
   */
  public static final int ATTRIBUTE = 2;

  /** The child axis contains the children of the context node. */
  public static final int CHILD = 3;

  /**
   * The descendant axis contains the descendants of the context node;
   *  a descendant is a child or a child of a child and so on; thus the
   *  descendant axis never contains attribute or namespace nodes.
   * <p>
   *  后代轴包含上下文节点的后代;子女是孩子或孩子的孩子,等等;因此后代轴从不包含属性或命名空间节点。
   * 
   */
  public static final int DESCENDANT = 4;

  /**
   * The descendant-or-self axis contains the context node and the
   *  descendants of the context node.
   * <p>
   *  后代或自身轴包含上下文节点和上下文节点的后代。
   * 
   */
  public static final int DESCENDANTORSELF = 5;

  /**
   * the following axis contains all nodes in the same document as the
   *  context node that are after the context node in document order, excluding
   *  any descendants and excluding attribute nodes and namespace nodes.
   * <p>
   *  跟随轴包含与文档顺序中的上下文节点之后的上下文节点相同的文档中的所有节点,排除任何后代并且排除属性节点和命名空间节点。
   * 
   */
  public static final int FOLLOWING = 6;

  /**
   * The following-sibling axis contains all the following siblings of the
   *  context node; if the context node is an attribute node or namespace node,
   *  the following-sibling axis is empty.
   * <p>
   *  下面的同级轴包含上下文节点的所有以下同级;如果上下文节点是属性节点或命名空间节点,则下面的同轴轴为空。
   * 
   */
  public static final int FOLLOWINGSIBLING = 7;

  /**
   * The namespace axis contains the namespace nodes of the context node; the
   *  axis will be empty unless the context node is an element.
   * <p>
   *  命名空间轴包含上下文节点的命名空间节点;该轴将为空,除非上下文节点是一个元素。
   * 
   */
  public static final int NAMESPACEDECLS = 8;

  /**
   * The namespace axis contains the namespace nodes of the context node; the
   *  axis will be empty unless the context node is an element.
   * <p>
   *  命名空间轴包含上下文节点的命名空间节点;该轴将为空,除非上下文节点是一个元素。
   * 
   */
  public static final int NAMESPACE = 9;

  /**
   * The parent axis contains the parent of the context node,
   *  if there is one.
   * <p>
   *  父轴包含上下文节点的父级(如果有的话)。
   * 
   */
  public static final int PARENT = 10;

  /**
   * The preceding axis contains all nodes in the same document as the context
   *  node that are before the context node in document order, excluding any
   *  ancestors and excluding attribute nodes and namespace nodes
   * <p>
   *  前一个轴包含与文档顺序中上下文节点之前的上下文节点相同的文档中的所有节点,排除任何祖先,并排除属性节点和命名空间节点
   * 
   */
  public static final int PRECEDING = 11;

  /**
   * The preceding-sibling axis contains all the preceding siblings of the
   *  context node; if the context node is an attribute node or namespace node,
   *  the preceding-sibling axis is empty.
   * <p>
   * 前面的同级轴包含上下文节点的所有前面的同级;如果上下文节点是属性节点或命名空间节点,则前同轴轴为空。
   * 
   */
  public static final int PRECEDINGSIBLING = 12;

  /** The self axis contains just the context node itself. */
  public static final int SELF = 13;

  /**
   * A non-xpath axis, traversing the subtree including the subtree
   *  root, descendants, attributes, and namespace node decls.
   * <p>
   *  非xpath轴,遍历子树,包括子树根,后代,属性和命名空间节点decls。
   * 
   */
  public static final int ALLFROMNODE = 14;

  /**
   * A non-xpath axis, traversing the the preceding and the ancestor nodes,
   * needed for inverseing select patterns to match patterns.
   * <p>
   *  非xpath轴,遍历前面的和祖先节点,需要用于反转选择模式以匹配模式。
   * 
   */
  public static final int PRECEDINGANDANCESTOR = 15;

  // ===========================================
  // All axis past this are absolute.

  /**
   * A non-xpath axis, returns all nodes in the tree from and including the
   * root.
   * <p>
   *  非xpath轴,返回树中来自并包括根的所有节点。
   * 
   */
  public static final int ALL = 16;

  /**
   * A non-xpath axis, returns all nodes that aren't namespaces or attributes,
   * from and including the root.
   * <p>
   *  非xpath轴返回来自并包括根的所有不是命名空间或属性的节点。
   * 
   */
  public static final int DESCENDANTSFROMROOT = 17;

  /**
   * A non-xpath axis, returns all nodes that aren't namespaces or attributes,
   * from and including the root.
   * <p>
   *  非xpath轴返回来自并包括根的所有不是命名空间或属性的节点。
   * 
   */
  public static final int DESCENDANTSORSELFFROMROOT = 18;

  /**
   * A non-xpath axis, returns root only.
   * <p>
   *  非xpath轴,仅返回根。
   * 
   */
  public static final int ROOT = 19;

  /**
   * A non-xpath axis, for functions.
   * <p>
   *  非xpath轴,用于函数。
   * 
   */
  public static final int FILTEREDLIST = 20;

  /**
   * A table to identify whether an axis is a reverse axis;
   * <p>
   *  用于识别轴是否是反向轴的表;
   */
  private static final boolean[] isReverse = {
      true,  // ancestor
      true,  // ancestor-or-self
      false, // attribute
      false, // child
      false, // descendant
      false, // descendant-or-self
      false, // following
      false, // following-sibling
      false, // namespace
      false, // namespace-declarations
      false, // parent (one node, has no order)
      true,  // preceding
      true,  // preceding-sibling
      false  // self (one node, has no order)
  };

    /** The names of the axes for diagnostic purposes. */
    private static final String[] names =
    {
      "ancestor",  // 0
      "ancestor-or-self",  // 1
      "attribute",  // 2
      "child",  // 3
      "descendant",  // 4
      "descendant-or-self",  // 5
      "following",  // 6
      "following-sibling",  // 7
      "namespace-decls",  // 8
      "namespace",  // 9
      "parent",  // 10
      "preceding",  // 11
      "preceding-sibling",  // 12
      "self",  // 13
      "all-from-node",  // 14
      "preceding-and-ancestor",  // 15
      "all",  // 16
      "descendants-from-root",  // 17
      "descendants-or-self-from-root",  // 18
      "root",  // 19
      "filtered-list"  // 20
    };

  public static boolean isReverse(int axis){
      return isReverse[axis];
  }

    public static String getNames(int index){
        return names[index];
    }

    public static int getNamesLength(){
        return names.length;
    }

}
