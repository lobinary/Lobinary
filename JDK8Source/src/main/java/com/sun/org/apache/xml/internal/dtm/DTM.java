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
 * $Id: DTM.java,v 1.2.4.1 2005/09/15 08:14:51 suresh_emailid Exp $
 * <p>
 *  $ Id：DTM.java,v 1.2.4.1 2005/09/15 08:14:51 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.dtm;

import javax.xml.transform.SourceLocator;

import com.sun.org.apache.xml.internal.utils.XMLString;

/**
 * <code>DTM</code> is an XML document model expressed as a table
 * rather than an object tree. It attempts to provide an interface to
 * a parse tree that has very little object creation. (DTM
 * implementations may also support incremental construction of the
 * model, but that's hidden from the DTM API.)
 *
 * <p>Nodes in the DTM are identified by integer "handles".  A handle must
 * be unique within a process, and carries both node identification and
 * document identification.  It must be possible to compare two handles
 * (and thus their nodes) for identity with "==".</p>
 *
 * <p>Namespace URLs, local-names, and expanded-names can all be
 * represented by and tested as integer ID values.  An expanded name
 * represents (and may or may not directly contain) a combination of
 * the URL ID, and the local-name ID.  Note that the namespace URL id
 * can be 0, which should have the meaning that the namespace is null.
 * For consistancy, zero should not be used for a local-name index. </p>
 *
 * <p>Text content of a node is represented by an index and length,
 * permitting efficient storage such as a shared FastStringBuffer.</p>
 *
 * <p>The model of the tree, as well as the general navigation model,
 * is that of XPath 1.0, for the moment.  The model will eventually be
 * adapted to match the XPath 2.0 data model, XML Schema, and
 * InfoSet.</p>
 *
 * <p>DTM does _not_ directly support the W3C's Document Object
 * Model. However, it attempts to come close enough that an
 * implementation of DTM can be created that wraps a DOM and vice
 * versa.</p>
 *
 * <p><strong>Please Note:</strong> The DTM API is still
 * <strong>Subject To Change.</strong> This wouldn't affect most
 * users, but might require updating some extensions.</p>
 *
 * <p> The largest change being contemplated is a reconsideration of
 * the Node Handle representation.  We are still not entirely sure
 * that an integer packed with two numeric subfields is really the
 * best solution. It has been suggested that we move up to a Long, to
 * permit more nodes per document without having to reduce the number
 * of slots in the DTMManager. There's even been a proposal that we
 * replace these integers with "cursor" objects containing the
 * internal node id and a pointer to the actual DTM object; this might
 * reduce the need to continuously consult the DTMManager to retrieve
 * the latter, and might provide a useful "hook" back into normal Java
 * heap management.  But changing this datatype would have huge impact
 * on Xalan's internals -- especially given Java's lack of C-style
 * typedefs -- so we won't cut over unless we're convinced the new
 * solution really would be an improvement!</p>
 * <p>
 *  <code> DTM </code>是一个表示为表而不是对象树的XML文档模型。它试图提供一个接口到一个解析树,只有很少的对象创建。
 *  (DTM实现也可以支持模型的增量构建,但是它对DTM API是隐藏的。)。
 * 
 *  <p> DTM中的节点由整数"句柄"标识。句柄在进程内必须是唯一的,并且携带节点标识和文档标识。必须可以比较两个句柄(以及它们的节点)与"=="的身份。</p>
 * 
 * <p>命名空间网址,本地名称和展开式名称都可以由整数ID值表示和测试。扩展名称表示(并且可以或可以不直接包含)URL ID和本地名称ID的组合。
 * 请注意,命名空间URL id可以为0,其应该具有命名空间为null的含义。对于一致性,不应将零用于local-name索引。 </p>。
 * 
 *  <p>节点的文本内容由索引和长度表示,允许高效存储,例如共享的FastStringBuffer。</p>
 * 
 *  <p>树的模型,以及一般的导航模型,是目前XPath 1.0的模型。该模型最终将被调整为匹配XPath 2.0数据模型,XML模式和InfoSet。</p>
 * 
 *  <p> DTM不_not_直接支持W3C的文档对象模型。然而,它尝试接近到足以创建包裹DOM的DTM的实现,反之亦然。</p>
 * 
 *  <p> <strong>请注意</strong>：DTM API仍然<strong>要更改。</strong>这不会影响大多数用户,但可能需要更新一些扩展程序。</p>
 * 
 * 预期的最大变化是重新考虑节点句柄表示。我们仍然不能完全确定包含两个数字子字段的整数是真正最好的解决方案。
 * 已经建议我们移动到Long,以允许每个文档更多的节点,而不必减少DTMManager中的槽的数量。
 * 甚至有一个建议,我们用包含内部节点id和指向实际DTM对象的指针的"cursor"对象替换这些整数;这可能减少持续查询DTMManager以检索后者的需要,并可能提供一个有用的"钩子"回到正常的Java
 * 堆管理。
 * 已经建议我们移动到Long,以允许每个文档更多的节点,而不必减少DTMManager中的槽的数量。
 * 但是改变这个数据类型会对Xalan的内部结构产生巨大的影响 - 特别是因为Java缺少C风格的typedef,所以我们不会削减,除非我们相信新的解决方案真的会是一个改进！</p>。
 * 
 * 
 * */
public interface DTM
{

  /**
   * Null node handles are represented by this value.
   * <p>
   *  空节点句柄由此值表示。
   * 
   */
  public static final int NULL = -1;

  // These nodeType mnemonics and values are deliberately the same as those
  // used by the DOM, for convenient mapping
  //
  // %REVIEW% Should we actually define these as initialized to,
  // eg. org.w3c.dom.Document.ELEMENT_NODE?

  /**
   * The node is a <code>Root</code>.
   * <p>
   *  该节点是一个<code> Root </code>。
   * 
   */
  public static final short ROOT_NODE = 0;

  /**
   * The node is an <code>Element</code>.
   * <p>
   *  该节点是一个<code> Element </code>。
   * 
   */
  public static final short ELEMENT_NODE = 1;

  /**
   * The node is an <code>Attr</code>.
   * <p>
   *  该节点是一个<code> Attr </code>。
   * 
   */
  public static final short ATTRIBUTE_NODE = 2;

  /**
   * The node is a <code>Text</code> node.
   * <p>
   *  该节点是一个<code> Text </code>节点。
   * 
   */
  public static final short TEXT_NODE = 3;

  /**
   * The node is a <code>CDATASection</code>.
   * <p>
   *  该节点是一个<code> CDATASection </code>。
   * 
   */
  public static final short CDATA_SECTION_NODE = 4;

  /**
   * The node is an <code>EntityReference</code>.
   * <p>
   *  该节点是一个<code> EntityReference </code>。
   * 
   */
  public static final short ENTITY_REFERENCE_NODE = 5;

  /**
   * The node is an <code>Entity</code>.
   * <p>
   *  该节点是一个<code> Entity </code>。
   * 
   */
  public static final short ENTITY_NODE = 6;

  /**
   * The node is a <code>ProcessingInstruction</code>.
   * <p>
   *  该节点是一个<code> ProcessingInstruction </code>。
   * 
   */
  public static final short PROCESSING_INSTRUCTION_NODE = 7;

  /**
   * The node is a <code>Comment</code>.
   * <p>
   *  该节点是一个<code> Comment </code>。
   * 
   */
  public static final short COMMENT_NODE = 8;

  /**
   * The node is a <code>Document</code>.
   * <p>
   *  该节点是<code> Document </code>。
   * 
   */
  public static final short DOCUMENT_NODE = 9;

  /**
   * The node is a <code>DocumentType</code>.
   * <p>
   *  该节点是<code> DocumentType </code>。
   * 
   */
  public static final short DOCUMENT_TYPE_NODE = 10;

  /**
   * The node is a <code>DocumentFragment</code>.
   * <p>
   *  该节点是一个<code> DocumentFragment </code>。
   * 
   */
  public static final short DOCUMENT_FRAGMENT_NODE = 11;

  /**
   * The node is a <code>Notation</code>.
   * <p>
   *  该节点是<code>符号</code>。
   * 
   */
  public static final short NOTATION_NODE = 12;

  /**
   * The node is a <code>namespace node</code>. Note that this is not
   * currently a node type defined by the DOM API.
   * <p>
   * 该节点是一个<code>命名空间节点</code>。请注意,这不是当前由DOM API定义的节点类型。
   * 
   */
  public static final short NAMESPACE_NODE = 13;

  /**
   * The number of valid nodetypes.
   * <p>
   *  有效节点数的数量。
   * 
   */
  public static final short  NTYPES = 14;

  // ========= DTM Implementation Control Functions. ==============
  // %TBD% RETIRED -- do via setFeature if needed. Remove from impls.
  // public void setParseBlockSize(int blockSizeSuggestion);

  /**
   * Set an implementation dependent feature.
   * <p>
   * %REVIEW% Do we really expect to set features on DTMs?
   *
   * <p>
   *  设置实现相关的功能。
   * <p>
   *  ％REVIEW％我们真的希望在DTM上设置功能吗?
   * 
   * 
   * @param featureId A feature URL.
   * @param state true if this feature should be on, false otherwise.
   */
  public void setFeature(String featureId, boolean state);

  /**
   * Set a run time property for this DTM instance.
   *
   * <p>
   *  为此DTM实例设置运行时属性。
   * 
   * 
   * @param property a <code>String</code> value
   * @param value an <code>Object</code> value
   */
  public void setProperty(String property, Object value);

  // ========= Document Navigation Functions =========

  /**
   * This returns a stateless "traverser", that can navigate over an
   * XPath axis, though not in document order.
   *
   * <p>
   *  这返回一个无状态的"遍历器",可以在XPath轴上导航,尽管不是按照文档顺序。
   * 
   * 
   * @param axis One of Axes.ANCESTORORSELF, etc.
   *
   * @return A DTMAxisIterator, or null if the givin axis isn't supported.
   */
  public DTMAxisTraverser getAxisTraverser(final int axis);

  /**
   * This is a shortcut to the iterators that implement
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
   * @return A DTMAxisIterator, or null if the givin axis isn't supported.
   */
  public DTMAxisIterator getAxisIterator(final int axis);

  /**
   * Get an iterator that can navigate over an XPath Axis, predicated by
   * the extended type ID.
   *
   * <p>
   *  获取一个可以在XPath Axis上导航的迭代器,由扩展类型ID预测。
   * 
   * 
   * @param axis
   * @param type An extended type ID.
   *
   * @return A DTMAxisIterator, or null if the givin axis isn't supported.
   */
  public DTMAxisIterator getTypedAxisIterator(final int axis, final int type);

  /**
   * Given a node handle, test if it has child nodes.
   * <p> %REVIEW% This is obviously useful at the DOM layer, where it
   * would permit testing this without having to create a proxy
   * node. It's less useful in the DTM API, where
   * (dtm.getFirstChild(nodeHandle)!=DTM.NULL) is just as fast and
   * almost as self-evident. But it's a convenience, and eases porting
   * of DOM code to DTM.  </p>
   *
   * <p>
   *  给定一个节点句柄,测试它是否有子节点。 <p>％REVIEW％这在DOM层显然有用,它允许在不必创建代理节点的情况下进行测试。
   * 它在DTM API中不太有用,其中(dtm.getFirstChild(nodeHandle)！= DTM.NULL)也同样快速,几乎是不言而喻的。但它是一个方便,并简化了将DOM代码移植到DTM。
   *  </p>。
   * 
   * 
   * @param nodeHandle int Handle of the node.
   * @return int true if the given node has child nodes.
   */
  public boolean hasChildNodes(int nodeHandle);

  /**
   * Given a node handle, get the handle of the node's first child.
   *
   * <p>
   *  给定一个节点句柄,获取节点的第一个孩子的句柄。
   * 
   * 
   * @param nodeHandle int Handle of the node.
   * @return int DTM node-number of first child,
   * or DTM.NULL to indicate none exists.
   */
  public int getFirstChild(int nodeHandle);

  /**
   * Given a node handle, get the handle of the node's last child.
   *
   * <p>
   *  给定一个节点句柄,获取节点的最后一个孩子的句柄。
   * 
   * 
   * @param nodeHandle int Handle of the node.
   * @return int Node-number of last child,
   * or DTM.NULL to indicate none exists.
   */
  public int getLastChild(int nodeHandle);

  /**
   * Retrieves an attribute node by local name and namespace URI
   *
   * %TBD% Note that we currently have no way to support
   * the DOM's old getAttribute() call, which accesses only the qname.
   *
   * <p>
   *  通过本地名称和命名空间URI检索属性节点
   * 
   *  ％TBD％注意,我们目前没有办法支持DOM的老的getAttribute()调用,它只访问qname。
   * 
   * 
   * @param elementHandle Handle of the node upon which to look up this attribute.
   * @param namespaceURI The namespace URI of the attribute to
   *   retrieve, or null.
   * @param name The local name of the attribute to
   *   retrieve.
   * @return The attribute node handle with the specified name (
   *   <code>nodeName</code>) or <code>DTM.NULL</code> if there is no such
   *   attribute.
   */
  public int getAttributeNode(int elementHandle, String namespaceURI,
                              String name);

  /**
   * Given a node handle, get the index of the node's first attribute.
   *
   * <p>
   *  给定一个节点句柄,获取节点的第一个属性的索引。
   * 
   * 
   * @param nodeHandle int Handle of the node.
   * @return Handle of first attribute, or DTM.NULL to indicate none exists.
   */
  public int getFirstAttribute(int nodeHandle);

  /**
   * Given a node handle, get the index of the node's first namespace node.
   *
   * <p>
   *  给定一个节点句柄,获取节点的第一个命名空间节点的索引。
   * 
   * 
   * @param nodeHandle handle to node, which should probably be an element
   *                   node, but need not be.
   *
   * @param inScope true if all namespaces in scope should be
   *                   returned, false if only the node's own
   *                   namespace declarations should be returned.
   * @return handle of first namespace,
   * or DTM.NULL to indicate none exists.
   */
  public int getFirstNamespaceNode(int nodeHandle, boolean inScope);

  /**
   * Given a node handle, advance to its next sibling.
   * <p>
   * 给定一个节点句柄,前进到下一个兄弟节点。
   * 
   * 
   * @param nodeHandle int Handle of the node.
   * @return int Node-number of next sibling,
   * or DTM.NULL to indicate none exists.
   */
  public int getNextSibling(int nodeHandle);

  /**
   * Given a node handle, find its preceeding sibling.
   * WARNING: DTM implementations may be asymmetric; in some,
   * this operation has been resolved by search, and is relatively expensive.
   *
   * <p>
   *  给定一个节点句柄,找到它的前面的兄弟。警告：DTM实现可能是不对称的;在一些,这个操作已经通过搜索解决,并且相对昂贵。
   * 
   * 
   * @param nodeHandle the id of the node.
   * @return int Node-number of the previous sib,
   * or DTM.NULL to indicate none exists.
   */
  public int getPreviousSibling(int nodeHandle);

  /**
   * Given a node handle, advance to the next attribute. If an
   * element, we advance to its first attribute; if an attr, we advance to
   * the next attr of the same element.
   *
   * <p>
   *  给定一个节点句柄,前进到下一个属性。如果一个元素,我们前进到它的第一个属性;如果一个attr,我们前进到同一个元素的下一个attr。
   * 
   * 
   * @param nodeHandle int Handle of the node.
   * @return int DTM node-number of the resolved attr,
   * or DTM.NULL to indicate none exists.
   */
  public int getNextAttribute(int nodeHandle);

  /**
   * Given a namespace handle, advance to the next namespace in the same scope
   * (local or local-plus-inherited, as selected by getFirstNamespaceNode)
   *
   * <p>
   *  给定一个命名空间句柄,前进到同一范围内的下一个命名空间(local或local-plus-inherited,由getFirstNamespaceNode选择)
   * 
   * 
   * @param baseHandle handle to original node from where the first child
   * was relative to (needed to return nodes in document order).
   * @param namespaceHandle handle to node which must be of type
   * NAMESPACE_NODE.
   * NEEDSDOC @param inScope
   * @return handle of next namespace,
   * or DTM.NULL to indicate none exists.
   */
  public int getNextNamespaceNode(int baseHandle, int namespaceHandle,
                                  boolean inScope);

  /**
   * Given a node handle, find its parent node.
   *
   * <p>
   *  给定一个节点句柄,找到它的父节点。
   * 
   * 
   * @param nodeHandle the id of the node.
   * @return int Node handle of parent,
   * or DTM.NULL to indicate none exists.
   */
  public int getParent(int nodeHandle);

  /**
   * Given a DTM which contains only a single document,
   * find the Node Handle of the  Document node. Note
   * that if the DTM is configured so it can contain multiple
   * documents, this call will return the Document currently
   * under construction -- but may return null if it's between
   * documents. Generally, you should use getOwnerDocument(nodeHandle)
   * or getDocumentRoot(nodeHandle) instead.
   *
   * <p>
   *  给定一个仅包含单个文档的DTM,找到文档节点的节点句柄。请注意,如果DTM配置为可以包含多个文档,则此调用将返回当前正在构建的文档 - 但如果它在文档之间,则可能返回null。
   * 通常,应该使用getOwnerDocument(nodeHandle)或getDocumentRoot(nodeHandle)。
   * 
   * 
   * @return int Node handle of document, or DTM.NULL if a shared DTM
   * can not tell us which Document is currently active.
   */
  public int getDocument();

  /**
   * Given a node handle, find the owning document node. This version mimics
   * the behavior of the DOM call by the same name.
   *
   * <p>
   *  给定一个节点句柄,找到拥有的文档节点。此版本模仿同名的DOM调用的行为。
   * 
   * 
   * @param nodeHandle the id of the node.
   * @return int Node handle of owning document, or DTM.NULL if the node was
   * a Document.
   * @see #getDocumentRoot(int nodeHandle)
   */
  public int getOwnerDocument(int nodeHandle);

  /**
   * Given a node handle, find the owning document node.
   *
   * <p>
   *  给定一个节点句柄,找到拥有的文档节点。
   * 
   * 
   * @param nodeHandle the id of the node.
   * @return int Node handle of owning document, or the node itself if it was
   * a Document. (Note difference from DOM, where getOwnerDocument returns
   * null for the Document node.)
   * @see #getOwnerDocument(int nodeHandle)
   */
  public int getDocumentRoot(int nodeHandle);

  /**
   * Get the string-value of a node as a String object
   * (see http://www.w3.org/TR/xpath#data-model
   * for the definition of a node's string-value).
   *
   * <p>
   *  将节点的字符串值作为String对象获取(有关节点的字符串值的定义,请参阅http://www.w3.org/TR/xpath#data-model)。
   * 
   * 
   * @param nodeHandle The node ID.
   *
   * @return A string object that represents the string-value of the given node.
   */
  public XMLString getStringValue(int nodeHandle);

  /**
   * Get number of character array chunks in
   * the string-value of a node.
   * (see http://www.w3.org/TR/xpath#data-model
   * for the definition of a node's string-value).
   * Note that a single text node may have multiple text chunks.
   *
   * <p>
   *  获取节点的字符串值中的字符数组块数。 (有关节点的字符串值的定义,请参见http://www.w3.org/TR/xpath#data-model)。注意,单个文本节点可以具有多个文本块。
   * 
   * 
   * @param nodeHandle The node ID.
   *
   * @return number of character array chunks in
   *         the string-value of a node.
   */
  public int getStringValueChunkCount(int nodeHandle);

  /**
   * Get a character array chunk in the string-value of a node.
   * (see http://www.w3.org/TR/xpath#data-model
   * for the definition of a node's string-value).
   * Note that a single text node may have multiple text chunks.
   *
   * <p>
   * 在节点的字符串值中获取字符数组块。 (有关节点的字符串值的定义,请参见http://www.w3.org/TR/xpath#data-model)。注意,单个文本节点可以具有多个文本块。
   * 
   * 
   * @param nodeHandle The node ID.
   * @param chunkIndex Which chunk to get.
   * @param startAndLen  A two-integer array which, upon return, WILL
   * BE FILLED with values representing the chunk's start position
   * within the returned character buffer and the length of the chunk.
   * @return The character array buffer within which the chunk occurs,
   * setting startAndLen's contents as a side-effect.
   */
  public char[] getStringValueChunk(int nodeHandle, int chunkIndex,
                                    int[] startAndLen);

  /**
   * Given a node handle, return an ID that represents the node's expanded name.
   *
   * <p>
   *  给定一个节点句柄,返回一个表示节点扩展名的ID。
   * 
   * 
   * @param nodeHandle The handle to the node in question.
   *
   * @return the expanded-name id of the node.
   */
  public int getExpandedTypeID(int nodeHandle);

  /**
   * Given an expanded name, return an ID.  If the expanded-name does not
   * exist in the internal tables, the entry will be created, and the ID will
   * be returned.  Any additional nodes that are created that have this
   * expanded name will use this ID.
   *
   * <p>
   *  给定扩展名称,返回ID。如果扩展名不存在于内部表中,则将创建该条目,并返回ID。创建的具有此扩展名称的任何其他节点将使用此标识。
   * 
   * 
   * NEEDSDOC @param namespace
   * NEEDSDOC @param localName
   * NEEDSDOC @param type
   *
   * @return the expanded-name id of the node.
   */
  public int getExpandedTypeID(String namespace, String localName, int type);

  /**
   * Given an expanded-name ID, return the local name part.
   *
   * <p>
   *  给定扩展名称ID,返回本地名称部分。
   * 
   * 
   * @param ExpandedNameID an ID that represents an expanded-name.
   * @return String Local name of this node.
   */
  public String getLocalNameFromExpandedNameID(int ExpandedNameID);

  /**
   * Given an expanded-name ID, return the namespace URI part.
   *
   * <p>
   *  给定扩展名称ID,返回名称空间URI部分。
   * 
   * 
   * @param ExpandedNameID an ID that represents an expanded-name.
   * @return String URI value of this node's namespace, or null if no
   * namespace was resolved.
   */
  public String getNamespaceFromExpandedNameID(int ExpandedNameID);

  /**
   * Given a node handle, return its DOM-style node name. This will
   * include names such as #text or #document.
   *
   * <p>
   *  给定一个节点句柄,返回其DOM样式的节点名称。这将包括诸如#text或#document的名称。
   * 
   * 
   * @param nodeHandle the id of the node.
   * @return String Name of this node, which may be an empty string.
   * %REVIEW% Document when empty string is possible...
   */
  public String getNodeName(int nodeHandle);

  /**
   * Given a node handle, return the XPath node name.  This should be
   * the name as described by the XPath data model, NOT the DOM-style
   * name.
   *
   * <p>
   *  给定一个节点句柄,返回XPath节点名称。这应该是XPath数据模型描述的名称,而不是DOM风格的名称。
   * 
   * 
   * @param nodeHandle the id of the node.
   * @return String Name of this node.
   */
  public String getNodeNameX(int nodeHandle);

  /**
   * Given a node handle, return its DOM-style localname.
   * (As defined in Namespaces, this is the portion of the name after the
   * prefix, if present, or the whole node name if no prefix exists)
   *
   * <p>
   *  给定一个节点句柄,返回其DOM样式的本地名。 (如命名空间中定义,这是前缀之后的名称的部分,如果存在,或者如果不存在前缀,则是整个节点名称)
   * 
   * 
   * @param nodeHandle the id of the node.
   * @return String Local name of this node.
   */
  public String getLocalName(int nodeHandle);

  /**
   * Given a namespace handle, return the prefix that the namespace decl is
   * mapping.
   * Given a node handle, return the prefix used to map to the namespace.
   * (As defined in Namespaces, this is the portion of the name before any
   * colon character).
   *
   * <p> %REVIEW% Are you sure you want "" for no prefix?  </p>
   *
   * <p>
   *  给定一个命名空间句柄,返回命名空间decl正在映射的前缀。给定一个节点句柄,返回用于映射到命名空间的前缀。 (如命名空间中所定义,这是任何冒号字符之前的名称部分)。
   * 
   *  <p>％REVIEW％您确定要""没有前缀吗? </p>
   * 
   * 
   * @param nodeHandle the id of the node.
   * @return String prefix of this node's name, or "" if no explicit
   * namespace prefix was given.
   */
  public String getPrefix(int nodeHandle);

  /**
   * Given a node handle, return its DOM-style namespace URI
   * (As defined in Namespaces, this is the declared URI which this node's
   * prefix -- or default in lieu thereof -- was mapped to.)
   * <p>
   * 给定一个节点句柄,返回它的DOM风格的命名空间URI(如命名空间中定义的,这是这个节点的前缀 - 或默认替代)被声明的URI。
   * 
   * 
   * @param nodeHandle the id of the node.
   * @return String URI value of this node's namespace, or null if no
   * namespace was resolved.
   */
  public String getNamespaceURI(int nodeHandle);

  /**
   * Given a node handle, return its node value. This is mostly
   * as defined by the DOM, but may ignore some conveniences.
   * <p>
   * <p>
   *  给定一个节点句柄,返回其节点值。这主要是由DOM定义的,但可能忽略一些方便。
   * <p>
   * 
   * @param nodeHandle The node id.
   * @return String Value of this node, or null if not
   * meaningful for this node type.
   */
  public String getNodeValue(int nodeHandle);

  /**
   * Given a node handle, return its DOM-style node type.
   *
   * <p>%REVIEW% Generally, returning short is false economy. Return int?</p>
   *
   * <p>
   *  给定一个节点句柄,返回其DOM样式的节点类型。
   * 
   *  <p>％REVIEW％一般来说,回报短是虚假经济。返回int?</p>
   * 
   * 
   * @param nodeHandle The node id.
   * @return int Node type, as per the DOM's Node._NODE constants.
   */
  public short getNodeType(int nodeHandle);

  /**
   * Get the depth level of this node in the tree (equals 1 for
   * a parentless node).
   *
   * <p>
   *  获取树中此节点的深度级别(对于无父节点,等于1)。
   * 
   * 
   * @param nodeHandle The node id.
   * @return the number of ancestors, plus one
   * @xsl.usage internal
   */
  public short getLevel(int nodeHandle);

  // ============== Document query functions ==============

  /**
   * Tests whether DTM DOM implementation implements a specific feature and
   * that feature is supported by this node.
   * <p>
   *  测试DTM DOM实现是否实现特定功能,并且此节点支持该功能。
   * 
   * 
   * @param feature The name of the feature to test.
   * @param version This is the version number of the feature to test.
   *   If the version is not
   *   specified, supporting any version of the feature will cause the
   *   method to return <code>true</code>.
   * @return Returns <code>true</code> if the specified feature is
   *   supported on this node, <code>false</code> otherwise.
   */
  public boolean isSupported(String feature, String version);

  /**
   * Return the base URI of the document entity. If it is not known
   * (because the document was parsed from a socket connection or from
   * standard input, for example), the value of this property is unknown.
   *
   * <p>
   *  返回文档实体的基本URI。如果不知道(因为文档是从套接字连接或标准输入解析的),此属性的值是未知的。
   * 
   * 
   * @return the document base URI String object or null if unknown.
   */
  public String getDocumentBaseURI();

  /**
   * Set the base URI of the document entity.
   *
   * <p>
   *  设置文档实体的基本URI。
   * 
   * 
   * @param baseURI the document base URI String object or null if unknown.
   */
  public void setDocumentBaseURI(String baseURI);

  /**
   * Return the system identifier of the document entity. If
   * it is not known, the value of this property is null.
   *
   * <p>
   *  返回文档实体的系统标识符。如果不知道,此属性的值为null。
   * 
   * 
   * @param nodeHandle The node id, which can be any valid node handle.
   * @return the system identifier String object or null if unknown.
   */
  public String getDocumentSystemIdentifier(int nodeHandle);

  /**
   * Return the name of the character encoding scheme
   *        in which the document entity is expressed.
   *
   * <p>
   *  返回表示文档实体的字符编码方案的名称。
   * 
   * 
   * @param nodeHandle The node id, which can be any valid node handle.
   * @return the document encoding String object.
   */
  public String getDocumentEncoding(int nodeHandle);

  /**
   * Return an indication of the standalone status of the document,
   *        either "yes" or "no". This property is derived from the optional
   *        standalone document declaration in the XML declaration at the
   *        beginning of the document entity, and has no value if there is no
   *        standalone document declaration.
   *
   * <p>
   *  返回文档的独立状态的指示,"是"或"否"。此属性派生自文档实体开头处的XML声明中的可选独立文档声明,如果没有独立文档声明,则没有值。
   * 
   * 
   * @param nodeHandle The node id, which can be any valid node handle.
   * @return the document standalone String object, either "yes", "no", or null.
   */
  public String getDocumentStandalone(int nodeHandle);

  /**
   * Return a string representing the XML version of the document. This
   * property is derived from the XML declaration optionally present at the
   * beginning of the document entity, and has no value if there is no XML
   * declaration.
   *
   * <p>
   * 返回表示文档的XML版本的字符串。此属性派生自可选地存在于文档实体开头的XML声明,如果没有XML声明,则没有值。
   * 
   * 
   * @param documentHandle the document handle
   * @return the document version String object
   */
  public String getDocumentVersion(int documentHandle);

  /**
   * Return an indication of
   * whether the processor has read the complete DTD. Its value is a
   * boolean. If it is false, then certain properties (indicated in their
   * descriptions below) may be unknown. If it is true, those properties
   * are never unknown.
   *
   * <p>
   *  返回处理器是否已读取完整DTD的指示。它的值是一个布尔值。如果它是假的,那么某些属性(在下面的描述中指示)可能是未知的。如果它是真的,那些属性永远不会是未知的。
   * 
   * 
   * @return <code>true</code> if all declarations were processed;
   *         <code>false</code> otherwise.
   */
  public boolean getDocumentAllDeclarationsProcessed();

  /**
   *   A document type declaration information item has the following properties:
   *
   *     1. [system identifier] The system identifier of the external subset, if
   *        it exists. Otherwise this property has no value.
   *
   * <p>
   *  文档类型声明信息项具有以下属性：
   * 
   *  1. [系统标识符]外部子集的系统标识符(如果存在)。否则此属性没有值。
   * 
   * 
   * @return the system identifier String object, or null if there is none.
   */
  public String getDocumentTypeDeclarationSystemIdentifier();

  /**
   * Return the public identifier of the external subset,
   * normalized as described in 4.2.2 External Entities [XML]. If there is
   * no external subset or if it has no public identifier, this property
   * has no value.
   *
   * <p>
   *  返回外部子集的公共标识符,如4.2.2外部实体[XML]中所述进行规范化。如果没有外部子集或者没有公共标识符,则此属性没有值。
   * 
   * 
   * @return the public identifier String object, or null if there is none.
   */
  public String getDocumentTypeDeclarationPublicIdentifier();

  /**
   * Returns the <code>Element</code> whose <code>ID</code> is given by
   * <code>elementId</code>. If no such element exists, returns
   * <code>DTM.NULL</code>. Behavior is not defined if more than one element
   * has this <code>ID</code>. Attributes (including those
   * with the name "ID") are not of type ID unless so defined by DTD/Schema
   * information available to the DTM implementation.
   * Implementations that do not know whether attributes are of type ID or
   * not are expected to return <code>DTM.NULL</code>.
   *
   * <p>%REVIEW% Presumably IDs are still scoped to a single document,
   * and this operation searches only within a single document, right?
   * Wouldn't want collisions between DTMs in the same process.</p>
   *
   * <p>
   *  返回<code> Element </code>,其<code> ID </code>由<code> elementId </code>给出。
   * 如果没有这样的元素,返回<code> DTM.NULL </code>。如果多个元素具有此<code> ID </code>,则不定义行为。
   * 属性(包括名称为"ID"的属性)不是类型ID,除非由DTM实现的DTD /模式信息如此定义。不知道属性是否为ID类型的实现应该返回<code> DTM.NULL </code>。
   * 
   * <p>％REVIEW％推测ID仍然限于单个文档,此操作仅在单个文档中搜索,对吗?不希望在同一过程中DTM之间发生冲突。</p>
   * 
   * 
   * @param elementId The unique <code>id</code> value for an element.
   * @return The handle of the matching element.
   */
  public int getElementById(String elementId);

  /**
   * The getUnparsedEntityURI function returns the URI of the unparsed
   * entity with the specified name in the same document as the context
   * node (see [3.3 Unparsed Entities]). It returns the empty string if
   * there is no such entity.
   * <p>
   * XML processors may choose to use the System Identifier (if one
   * is provided) to resolve the entity, rather than the URI in the
   * Public Identifier. The details are dependent on the processor, and
   * we would have to support some form of plug-in resolver to handle
   * this properly. Currently, we simply return the System Identifier if
   * present, and hope that it a usable URI or that our caller can
   * map it to one.
   * %REVIEW% Resolve Public Identifiers... or consider changing function name.
   * <p>
   * If we find a relative URI
   * reference, XML expects it to be resolved in terms of the base URI
   * of the document. The DOM doesn't do that for us, and it isn't
   * entirely clear whether that should be done here; currently that's
   * pushed up to a higher level of our application. (Note that DOM Level
   * 1 didn't store the document's base URI.)
   * %REVIEW% Consider resolving Relative URIs.
   * <p>
   * (The DOM's statement that "An XML processor may choose to
   * completely expand entities before the structure model is passed
   * to the DOM" refers only to parsed entities, not unparsed, and hence
   * doesn't affect this function.)
   *
   * <p>
   *  getUnparsedEntityURI函数返回在与上下文节点相同的文档中具有指定名称的未解析实体的URI(参见[3.3 Unparsed Entities])。如果没有这样的实体,它返回空字符串。
   * <p>
   *  XML处理器可以选择使用系统标识符(如果提供了一个)来解析实体,而不是公共标识符中的URI。细节取决于处理器,我们将不得不支持某种形式的插件解析器来正确处理这些。
   * 目前,我们只返回系统标识符(如果存在),并希望它是一个可用的URI,或者我们的调用者可以将它映射到一个。 ％REVIEW％解决公共标识符...或考虑更改函数名称。
   * <p>
   *  如果我们找到一个相对URI引用,XML期望它根据文档的基本URI解析。 DOM不为我们这样做,并不完全清楚是否应该在这里做;目前已经上升到我们的应用程序的更高水平。
   *  (请注意,DOM级别1不存储文档的基本URI。)％REVIEW％请考虑解析相对URI。
   * <p>
   *  (DOM的声明"一个XML处理器可能选择在结构模型被传递给DOM之前完全展开实体"仅指解析的实体,而不是解析的,因此不影响这个函数)。
   * 
   * 
   * @param name A string containing the Entity Name of the unparsed
   * entity.
   *
   * @return String containing the URI of the Unparsed Entity, or an
   * empty string if no such entity exists.
   */
  public String getUnparsedEntityURI(String name);

  // ============== Boolean methods ================

  /**
   * Return true if the xsl:strip-space or xsl:preserve-space was processed
   * during construction of the document contained in this DTM.
   *
   * <p>
   * 如果在构建此DTM中包含的文档期间处理了xsl：strip-space或xsl：preserve-space,则返回true。
   * 
   * 
   * NEEDSDOC ($objectName$) @return
   */
  public boolean supportsPreStripping();

  /**
   * Figure out whether nodeHandle2 should be considered as being later
   * in the document than nodeHandle1, in Document Order as defined
   * by the XPath model. This may not agree with the ordering defined
   * by other XML applications.
   * <p>
   * There are some cases where ordering isn't defined, and neither are
   * the results of this function -- though we'll generally return true.
   * <p>
   * %REVIEW% Make sure this does the right thing with attribute nodes!!!
   * <p>
   * %REVIEW% Consider renaming for clarity. Perhaps isDocumentOrder(a,b)?
   *
   * <p>
   *  在XPath模型定义的文档顺序中,确定nodeHandle2是否应该被认为是文档中的晚于nodeHandle1。这可能与其他XML应用程序定义的顺序不一致。
   * <p>
   *  有些情况下,没有定义顺序,并且这个函数的结果也没有定义,尽管我们通常返回true。
   * <p>
   *  ％REVIEW％确保这做正确的事情与属性节点！
   * <p>
   *  ％REVIEW％考虑重命名为了清晰。也许isDocumentOrder(a,b)?
   * 
   * 
   * @param firstNodeHandle DOM Node to perform position comparison on.
   * @param secondNodeHandle DOM Node to perform position comparison on.
   *
   * @return false if secondNode comes before firstNode, otherwise return true.
   * You can think of this as
   * <code>(firstNode.documentOrderPosition &lt;= secondNode.documentOrderPosition)</code>.
   */
  public boolean isNodeAfter(int firstNodeHandle, int secondNodeHandle);

  /**
   * 2. [element content whitespace] A boolean indicating whether a
   * text node represents white space appearing within element content
   * (see [XML], 2.10 "White Space Handling").  Note that validating
   * XML processors are required by XML 1.0 to provide this
   * information... but that DOM Level 2 did not support it, since it
   * depends on knowledge of the DTD which DOM2 could not guarantee
   * would be available.
   * <p>
   * If there is no declaration for the containing element, an XML
   * processor must assume that the whitespace could be meaningful and
   * return false. If no declaration has been read, but the [all
   * declarations processed] property of the document information item
   * is false (so there may be an unread declaration), then the value
   * of this property is indeterminate for white space characters and
   * should probably be reported as false. It is always false for text
   * nodes that contain anything other than (or in addition to) white
   * space.
   * <p>
   * Note too that it always returns false for non-Text nodes.
   * <p>
   * %REVIEW% Joe wants to rename this isWhitespaceInElementContent() for clarity
   *
   * <p>
   *  2. [element content whitespace]一个布尔值,表示文本节点是否表示元素内容中出现的空白(参见[XML],2.10"空白处理")。
   * 注意,XML 1.0需要验证XML处理器以提供此信息...但是DOM Level 2不支持它,因为它取决于DOM2不能保证可用的DTD的知识。
   * <p>
   * 如果没有声明包含元素,XML处理器必须假设空格可以有意义,并返回false。
   * 如果没有读取任何声明,但文档信息项的[all declarations processed]属性为false(因此可能存在未读取的声明),则该属性的值对于空格字符是不确定的,应该报告为假。
   * 对于包含除空格之外(或除空格之外)的文本节点,它始终为false。
   * <p>
   *  还要注意,对于非文本节点,它总是返回false。
   * <p>
   *  ％REVIEW％Joe要重命名此isWhitespaceInElementContent()以清楚
   * 
   * 
   * @param nodeHandle the node ID.
   * @return <code>true</code> if the node definitely represents whitespace in
   * element content; <code>false</code> otherwise.
   */
  public boolean isCharacterElementContentWhitespace(int nodeHandle);

  /**
   *    10. [all declarations processed] This property is not strictly speaking
   *        part of the infoset of the document. Rather it is an indication of
   *        whether the processor has read the complete DTD. Its value is a
   *        boolean. If it is false, then certain properties (indicated in their
   *        descriptions below) may be unknown. If it is true, those properties
   *        are never unknown.
   *
   * <p>
   *  10. [所有声明已处理]此属性不严格地说是文档信息集的一部分。而是它是处理器是否已读取完整DTD的指示。它的值是一个布尔值。如果它是假的,那么某些属性(在下面的描述中指示)可能是未知的。
   * 如果它是真的,那些属性永远不会是未知的。
   * 
   * 
   * @param documentHandle A node handle that must identify a document.
   * @return <code>true</code> if all declarations were processed;
   *         <code>false</code> otherwise.
   */
  public boolean isDocumentAllDeclarationsProcessed(int documentHandle);

  /**
   *     5. [specified] A flag indicating whether this attribute was actually
   *        specified in the start-tag of its element, or was defaulted from the
   *        DTD (or schema).
   *
   * <p>
   *  5. [specified]一个标志,指示此属性是实际上在其元素的开始标签中指定,还是默认自DTD(或模式)。
   * 
   * 
   * @param attributeHandle The attribute handle
   * @return <code>true</code> if the attribute was specified;
   *         <code>false</code> if it was defaulted or the handle doesn't
   *            refer to an attribute node.
   */
  public boolean isAttributeSpecified(int attributeHandle);

  // ========== Direct SAX Dispatch, for optimization purposes ========

  /**
   * Directly call the
   * characters method on the passed ContentHandler for the
   * string-value of the given node (see http://www.w3.org/TR/xpath#data-model
   * for the definition of a node's string-value). Multiple calls to the
   * ContentHandler's characters methods may well occur for a single call to
   * this method.
   *
   * <p>
   *  在传递的ContentHandler上直接调用给定节点的字符串值的字符方法(有关节点的字符串值的定义,请参阅http://www.w3.org/TR/xpath#data-model)。
   * 对ContentHandler的字符方法的多次调用很可能发生在对此方法的单个调用中。
   * 
   * 
   * @param nodeHandle The node ID.
   * @param ch A non-null reference to a ContentHandler.
   * @param normalize true if the content should be normalized according to
   * the rules for the XPath
   * <a href="http://www.w3.org/TR/xpath#function-normalize-space">normalize-space</a>
   * function.
   *
   * @throws org.xml.sax.SAXException
   */
  public void dispatchCharactersEvents(
    int nodeHandle, org.xml.sax.ContentHandler ch, boolean normalize)
      throws org.xml.sax.SAXException;

  /**
   * Directly create SAX parser events representing the XML content of
   * a DTM subtree. This is a "serialize" operation.
   *
   * <p>
   * 直接创建表示DTM子树的XML内容的SAX解析器事件。这是一个"serialize"操作。
   * 
   * 
   * @param nodeHandle The node ID.
   * @param ch A non-null reference to a ContentHandler.
   *
   * @throws org.xml.sax.SAXException
   */
  public void dispatchToEvents(int nodeHandle, org.xml.sax.ContentHandler ch)
    throws org.xml.sax.SAXException;

  /**
   * Return an DOM node for the given node.
   *
   * <p>
   *  返回给定节点的DOM节点。
   * 
   * 
   * @param nodeHandle The node ID.
   *
   * @return A node representation of the DTM node.
   */
  public org.w3c.dom.Node getNode(int nodeHandle);

  // ==== Construction methods (may not be supported by some implementations!) =====
  // %REVIEW% What response occurs if not supported?

  /**
  /* <p>
  /* 
   * @return true iff we're building this model incrementally (eg
   * we're partnered with a CoroutineParser) and thus require that the
   * transformation and the parse run simultaneously. Guidance to the
   * DTMManager.
   */
  public boolean needsTwoThreads();

  // %REVIEW% Do these appends make any sense, should we support a
  // wider set of methods (like the "append" methods in the
  // current DTMDocumentImpl draft), or should we just support SAX
  // listener interfaces?  Should it be a separate interface to
  // make that distinction explicit?

  /**
   * Return this DTM's content handler, if it has one.
   *
   * <p>
   *  返回此DTM的内容处理程序(如果有)。
   * 
   * 
   * @return null if this model doesn't respond to SAX events.
   */
  public org.xml.sax.ContentHandler getContentHandler();

  /**
   * Return this DTM's lexical handler, if it has one.
   *
   * %REVIEW% Should this return null if constrution already done/begun?
   *
   * <p>
   *  返回这个DTM的词法句柄,如果它有一个。
   * 
   *  ％REVIEW％如果解析已经完成/开始,这应该返回null吗?
   * 
   * 
   * @return null if this model doesn't respond to lexical SAX events.
   */
  public org.xml.sax.ext.LexicalHandler getLexicalHandler();

  /**
   * Return this DTM's EntityResolver, if it has one.
   *
   * <p>
   *  返回此DTM的EntityResolver(如果有)。
   * 
   * 
   * @return null if this model doesn't respond to SAX entity ref events.
   */
  public org.xml.sax.EntityResolver getEntityResolver();

  /**
   * Return this DTM's DTDHandler, if it has one.
   *
   * <p>
   *  返回此DTM的DTDHandler(如果有)。
   * 
   * 
   * @return null if this model doesn't respond to SAX dtd events.
   */
  public org.xml.sax.DTDHandler getDTDHandler();

  /**
   * Return this DTM's ErrorHandler, if it has one.
   *
   * <p>
   *  返回此DTM的ErrorHandler(如果有)。
   * 
   * 
   * @return null if this model doesn't respond to SAX error events.
   */
  public org.xml.sax.ErrorHandler getErrorHandler();

  /**
   * Return this DTM's DeclHandler, if it has one.
   *
   * <p>
   *  返回这个DTM的DeclHandler,如果有的话。
   * 
   * 
   * @return null if this model doesn't respond to SAX Decl events.
   */
  public org.xml.sax.ext.DeclHandler getDeclHandler();

  /**
   * Append a child to "the end of the document". Please note that
   * the node is always cloned in a base DTM, since our basic behavior
   * is immutable so nodes can't be removed from their previous
   * location.
   *
   * <p> %REVIEW%  DTM maintains an insertion cursor which
   * performs a depth-first tree walk as nodes come in, and this operation
   * is really equivalent to:
   *    insertionCursor.appendChild(document.importNode(newChild)))
   * where the insert point is the last element that was appended (or
   * the last one popped back to by an end-element operation).</p>
   *
   * <p>
   *  将孩子追加到"文档的结尾"。请注意,节点总是克隆在基本DTM中,因为我们的基本行为是不可变的,因此无法从先前的位置删除节点。
   * 
   *  </p>％REVIEW％DTM维护一个插入游标,当节点进入时执行深度优先的树走向,这个操作实际上等效于：insertionCursor.appendChild(document.importNode(
   * newChild)))其中插入点是被追加的最后一个元素(或最后一个元素操作弹出的元素)。
   * </p>。
   * 
   * 
   * @param newChild Must be a valid new node handle.
   * @param clone true if the child should be cloned into the document.
   * @param cloneDepth if the clone argument is true, specifies that the
   *                   clone should include all it's children.
   */
  public void appendChild(int newChild, boolean clone, boolean cloneDepth);

  /**
   * Append a text node child that will be constructed from a string,
   * to the end of the document. Behavior is otherwise like appendChild().
   *
   * <p>
   *  将将从字符串构造的文本节点子句追加到文档的结尾。行为是像appendChild()。
   * 
   * 
   * @param str Non-null reference to a string.
   */
  public void appendTextChild(String str);

  /**
   * Get the location of a node in the source document.
   *
   * <p>
   *  获取源文档中节点的位置。
   * 
   * 
   * @param node an <code>int</code> value
   * @return a <code>SourceLocator</code> value or null if no location
   * is available
   */
  public SourceLocator getSourceLocatorFor(int node);

  /**
   * As the DTM is registered with the DTMManager, this method
   * will be called. This will give the DTM implementation a
   * chance to initialize any subsystems that are required to
   * build the DTM
   * <p>
   *  当DTM向DTMManager注册时,将调用此方法。这将使DTM实现有机会初始化构建DTM所需的任何子系统
   * 
   */
  public void documentRegistration();

  /**
   * As documents are released from the DTMManager, the DTM implementation
   * will be notified of the event. This will allow the DTM implementation
   * to shutdown any subsystem activity that may of been assoiated with
   * the active DTM Implementation.
   * <p>
   * 当文档从DTMManager释放时,DTM实现将被通知事件。这将允许DTM实现关闭可能与主动DTM实现相关联的任何子系统活动。
   * 
   */

   public void documentRelease();

   /**
    * Migrate a DTM built with an old DTMManager to a new DTMManager.
    * After the migration, the new DTMManager will treat the DTM as
    * one that is built by itself.
    * This is used to support DTM sharing between multiple transformations.
    * <p>
    *  将使用旧DTMManager构建的DTM迁移到新的DTMManager。迁移后,新的DTMManager会将DTM视为由其自身构建的DTM。这用于支持多个转换之间的DTM共享。
    * 
    * @param manager the DTMManager
    */
   public void migrateTo(DTMManager manager);
}
