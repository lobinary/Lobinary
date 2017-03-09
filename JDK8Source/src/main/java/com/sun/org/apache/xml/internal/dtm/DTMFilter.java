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
 * $Id: DTMFilter.java,v 1.2.4.1 2005/09/15 08:14:53 suresh_emailid Exp $
 * <p>
 *  $ Id：DTMFilter.java,v 1.2.4.1 2005/09/15 08:14:53 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.dtm;

/**
 * Simple filter for doing node tests.  Note the semantics of this are
 * somewhat different that the DOM's NodeFilter.
 * <p>
 *  用于进行节点测试的简单过滤器。注意这个语义和DOM的NodeFilter有些不同。
 * 
 */
public interface DTMFilter
{

  // Constants for whatToShow.  These are used to set the node type that will
  // be traversed. These values may be ORed together before being passed to
  // the DTMIterator.

  /**
   * Show all <code>Nodes</code>.
   * <p>
   *  显示所有<code>节点</code>。
   * 
   */
  public static final int SHOW_ALL = 0xFFFFFFFF;

  /**
   * Show <code>Element</code> nodes.
   * <p>
   *  显示<code>元素</code>节点。
   * 
   */
  public static final int SHOW_ELEMENT = 0x00000001;

  /**
   * Show <code>Attr</code> nodes. This is meaningful only when creating an
   * iterator or tree-walker with an attribute node as its
   * <code>root</code>; in this case, it means that the attribute node
   * will appear in the first position of the iteration or traversal.
   * Since attributes are never children of other nodes, they do not
   * appear when traversing over the main document tree.
   * <p>
   *  显示<code> Attr </code>节点。
   * 这只有在创建一个迭代器或者具有属性节点作为其<code> root </code>的tree-walker时才有意义;在这种情况下,它意味着属性节点将出现在迭代或遍历的第一个位置。
   * 由于属性从不是其他节点的子节点,因此它们在遍历主文档树时不会出现。
   * 
   */
  public static final int SHOW_ATTRIBUTE = 0x00000002;

  /**
   * Show <code>Text</code> nodes.
   * <p>
   *  显示<code>文本</code>节点。
   * 
   */
  public static final int SHOW_TEXT = 0x00000004;

  /**
   * Show <code>CDATASection</code> nodes.
   * <p>
   *  显示<code> CDATASection </code>节点。
   * 
   */
  public static final int SHOW_CDATA_SECTION = 0x00000008;

  /**
   * Show <code>EntityReference</code> nodes. Note that if Entity References
   * have been fully expanded while the tree was being constructed, these
   * nodes will not appear and this mask has no effect.
   * <p>
   * 显示<code> EntityReference </code>节点。请注意,如果在构建树时实体引用已完全展开,这些节点将不会出现,并且此掩码不起作用。
   * 
   */
  public static final int SHOW_ENTITY_REFERENCE = 0x00000010;

  /**
   * Show <code>Entity</code> nodes. This is meaningful only when creating
   * an iterator or tree-walker with an<code> Entity</code> node as its
   * <code>root</code>; in this case, it means that the <code>Entity</code>
   *  node will appear in the first position of the traversal. Since
   * entities are not part of the document tree, they do not appear when
   * traversing over the main document tree.
   * <p>
   *  显示<code>实体</code>节点。
   * 这只有在创建一个迭代器或者具有<code> Entity </code>节点作为<code> root </code>的tree-walker时才有意义;在这种情况下,它意味着<code> Entity
   *  </code>节点将出现在遍历的第一个位置。
   *  显示<code>实体</code>节点。由于实体不是文档树的一部分,因此它们在遍历主文档树时不会出现。
   * 
   */
  public static final int SHOW_ENTITY = 0x00000020;

  /**
   * Show <code>ProcessingInstruction</code> nodes.
   * <p>
   *  显示<code> ProcessingInstruction </code>节点。
   * 
   */
  public static final int SHOW_PROCESSING_INSTRUCTION = 0x00000040;

  /**
   * Show <code>Comment</code> nodes.
   * <p>
   *  显示<code>注释</code>节点。
   * 
   */
  public static final int SHOW_COMMENT = 0x00000080;

  /**
   * Show <code>Document</code> nodes. (Of course, as with Attributes
   * and such, this is meaningful only when the iteration root is the
   * Document itself, since Document has no parent.)
   * <p>
   *  显示<code>文档</code>节点。 (当然,和Attributes等一样,只有当迭代根是Document本身时,这是有意义的,因为Document没有父类)。
   * 
   */
  public static final int SHOW_DOCUMENT = 0x00000100;

  /**
   * Show <code>DocumentType</code> nodes.
   * <p>
   *  显示<code> DocumentType </code>节点。
   * 
   */
  public static final int SHOW_DOCUMENT_TYPE = 0x00000200;

  /**
   * Show <code>DocumentFragment</code> nodes. (Of course, as with
   * Attributes and such, this is meaningful only when the iteration
   * root is the Document itself, since DocumentFragment has no parent.)
   * <p>
   *  显示<code> DocumentFragment </code>节点。
   *  (当然,和Attributes等一样,只有当迭代根是Document本身时,这是有意义的,因为DocumentFragment没有父类)。
   * 
   */
  public static final int SHOW_DOCUMENT_FRAGMENT = 0x00000400;

  /**
   * Show <code>Notation</code> nodes. This is meaningful only when creating
   * an iterator or tree-walker with a <code>Notation</code> node as its
   * <code>root</code>; in this case, it means that the
   * <code>Notation</code> node will appear in the first position of the
   * traversal. Since notations are not part of the document tree, they do
   * not appear when traversing over the main document tree.
   * <p>
   *  显示<code>符号</code>节点。
   * 这只有在创建一个迭代器或者具有<code> Notation </code>节点作为其<code> root </code>的tree-walker时才有意义;在这种情况下,这意味着<code> Not
   * ation </code>节点将出现在遍历的第一个位置。
   *  显示<code>符号</code>节点。由于符号不是文档树的一部分,因此它们在遍历主文档树时不会出现。
   * 
   */
  public static final int SHOW_NOTATION = 0x00000800;

  /**

   * This bit instructs the iterator to show namespace nodes, which
   * are modeled by DTM but not by the DOM.  Make sure this does not
   * conflict with {@link org.w3c.dom.traversal.NodeFilter}.
   * <p>
   * %REVIEW% Might be safer to start from higher bits and work down,
   * to leave room for the DOM to expand its set of constants... Or,
   * possibly, to create a DTM-specific field for these additional bits.
   * <p>
   * 该位指示迭代器显示命名空间节点,这些节点由DTM而不是由DOM建模。请确保这不与{@link org.w3c.dom.traversal.NodeFilter}冲突。
   * <p>
   *  ％REVIEW％从更高的位开始向下工作可能更安全,为DOM扩展其常量集留出空间...或者,可能为这些附加位创建一个DTM特定的字段。
   * 
   */
  public static final int SHOW_NAMESPACE = 0x00001000;

  /**
   * Special bit for filters implementing match patterns starting with
   * a function.  Make sure this does not conflict with
   * {@link org.w3c.dom.traversal.NodeFilter}.
   * <p>
   * %REVIEW% Might be safer to start from higher bits and work down,
   * to leave room for the DOM to expand its set of constants... Or,
   * possibly, to create a DTM-specific field for these additional bits.
   * <p>
   *  用于实现以函数开始的匹配模式的过滤器的特殊位。请确保这不与{@link org.w3c.dom.traversal.NodeFilter}冲突。
   * <p>
   *  ％REVIEW％从更高的位开始向下工作可能更安全,为DOM扩展其常量集留出空间...或者,可能为这些附加位创建一个DTM特定的字段。
   * 
   */
  public static final int SHOW_BYFUNCTION = 0x00010000;

  /**
   * Test whether a specified node is visible in the logical view of a
   * <code>DTMIterator</code>. Normally, this function
   * will be called by the implementation of <code>DTMIterator</code>;
   * it is not normally called directly from
   * user code.
   *
   * <p>
   *  测试指定的节点是否在<code> DTMIterator </code>的逻辑视图中可见。
   * 通常,该函数将通过实现<code> DTMIterator </code>来调用;它通常不直接从用户代码调用。
   * 
   * 
   * @param nodeHandle int Handle of the node.
   * @param whatToShow one of SHOW_XXX values.
   * @return one of FILTER_ACCEPT, FILTER_REJECT, or FILTER_SKIP.
   */
  public short acceptNode(int nodeHandle, int whatToShow);

  /**
   * Test whether a specified node is visible in the logical view of a
   * <code>DTMIterator</code>. Normally, this function
   * will be called by the implementation of <code>DTMIterator</code>;
   * it is not normally called directly from
   * user code.
   * <p>
   * TODO: Should this be setNameMatch(expandedName) followed by accept()?
   * Or will we really be testing a different name at every invocation?
   *
   * <p>%REVIEW% Under what circumstances will this be used? The cases
   * I've considered are just as easy and just about as efficient if
   * the name test is performed in the DTMIterator... -- Joe</p>
   *
   * <p>%REVIEW% Should that 0xFFFF have a mnemonic assigned to it?
   * Also: This representation is assuming the expanded name is indeed
   * split into high/low 16-bit halfwords. If we ever change the
   * balance between namespace and localname bits (eg because we
   * decide there are many more localnames than namespaces, which is
   * fairly likely), this is going to break. It might be safer to
   * encapsulate the details with a makeExpandedName method and make
   * that responsible for setting up the wildcard version as well.</p>
   *
   * <p>
   *  测试指定的节点是否在<code> DTMIterator </code>的逻辑视图中可见。
   * 通常,该函数将通过实现<code> DTMIterator </code>来调用;它通常不直接从用户代码调用。
   * <p>
   *  TODO：应该是setNameMatch(expandedName)后跟accept()吗?或者我们真的会在每次调用时测试不同的名称吗?
   * 
   * <p>％REVIEW％在什么情况下会被使用?我考虑的情况下,如果名称测试是在DTMIterator中执行一样简单,只是有效的...  -  Joe </p>
   * 
   * @param nodeHandle int Handle of the node.
   * @param whatToShow one of SHOW_XXX values.
   * @param expandedName a value defining the exanded name as defined in
   *                     the DTM interface.  Wild cards will be defined
   *                     by 0xFFFF in the namespace and/or localname
   *                     portion of the expandedName.
   * @return one of FILTER_ACCEPT, FILTER_REJECT, or FILTER_SKIP.  */
  public short acceptNode(int nodeHandle, int whatToShow, int expandedName);

}
