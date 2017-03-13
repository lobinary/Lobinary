/***** Lobxxx Translate Finished ******/
/*
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

/*
 *
 *
 *
 *
 *
 * Copyright (c) 2004 World Wide Web Consortium,
 *
 * (Massachusetts Institute of Technology, European Research Consortium for
 * Informatics and Mathematics, Keio University). All Rights Reserved. This
 * work is distributed under the W3C(r) Software License [1] in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * [1] http://www.w3.org/Consortium/Legal/2002/copyright-software-20021231
 * <p>
 *  版权所有(c)2004万维网联盟,
 * 
 *  (马萨诸塞理工学院,欧洲研究信息学和数学联合会,庆应大学)保留所有权利本作品根据W3C(r)软件许可证[1]分发,希望它有用,但没有任何保证;甚至没有对适销性或适用于特定用途的隐含保证
 * 
 *  [1] http：// wwww3org / Consortium / Legal / 2002 / copyright-software-20021231
 * 
 */

package org.w3c.dom.ls;

import org.w3c.dom.traversal.NodeFilter;

/**
 *  <code>LSSerializerFilter</code>s provide applications the ability to
 * examine nodes as they are being serialized and decide what nodes should
 * be serialized or not. The <code>LSSerializerFilter</code> interface is
 * based on the <code>NodeFilter</code> interface defined in [<a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Traversal-Range-20001113'>DOM Level 2 Traversal and      Range</a>]
 * .
 * <p> <code>Document</code>, <code>DocumentType</code>,
 * <code>DocumentFragment</code>, <code>Notation</code>, <code>Entity</code>
 * , and children of <code>Attr</code> nodes are not passed to the filter.
 * The child nodes of an <code>EntityReference</code> node are only passed
 * to the filter if the <code>EntityReference</code> node is skipped by the
 * method <code>LSParserFilter.acceptNode()</code>.
 * <p> When serializing an <code>Element</code>, the element is passed to the
 * filter before any of its attributes are passed to the filter. Namespace
 * declaration attributes, and default attributes (except in the case when "
 * discard-default-content" is set to <code>false</code>), are never passed
 * to the filter.
 * <p> The result of any attempt to modify a node passed to a
 * <code>LSSerializerFilter</code> is implementation dependent.
 * <p> DOM applications must not raise exceptions in a filter. The effect of
 * throwing exceptions from a filter is DOM implementation dependent.
 * <p> For efficiency, a node passed to the filter may not be the same as the
 * one that is actually in the tree. And the actual node (node object
 * identity) may be reused during the process of filtering and serializing a
 * document.
 * <p>See also the <a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-LS-20040407'>Document Object Model (DOM) Level 3 Load
and Save Specification</a>.
 * <p>
 * <code> LSSerializerFilter </code>为应用程序提供了在序列化时检查节点的能力,并决定应该序列化哪些节点。
 * <code> LSSerializerFilter </code>接口基于<code> NodeFilter <代码>接口在[<a href='http://wwww3org/TR/2000/REC-DOM-Level-2-Traversal-Range-20001113'>
 *  DOM Level 2 Traversal and Range </a>]中定义] <p> <code> Document </code>,<code> DocumentType </code>,<code>
 *  DocumentFragment </code>,<code>记法</code>,<code> Entity </code> > Attr </code>节点不被传递给过滤器如果<code> Enti
 * tyReference </code>节点被方法</code>跳过,则<code> EntityReference </code>节点的子节点仅传递到过滤器,代码> LSParserFilteracce
 * ptNode()</code><p>当序列化一个<code> Element </code>时,该元素在它的任何属性被传递给过滤器Namespace声明属性和默认属性之前被传递给过滤器(除非在"disc
 * ard-default-内容"设置为<code> false </code>)永远不会传递到过滤器<p>任何尝试修改传递给<code> LSSerializerFilter </code>的节点的结果是
 * 依赖于实现<p> DOM应用程序不能在过滤器中引发异常从过滤器抛出异常的效果是DOM实现依赖的<p>为了效率,传递给过滤器的节点可能与实际在树中的节点不同。
 * <code> LSSerializerFilter </code>为应用程序提供了在序列化时检查节点的能力,并决定应该序列化哪些节点。
 * 节点(节点对象标识)可以在过滤和序列化文档的过程中被重用<p>另请参阅<a href='http://wwww3org/TR/2004/REC-DOM-Level-3-LS-20040407'>文档对象
 * 模型(DOM)3级加载和保存规范</a>。
 */
public interface LSSerializerFilter extends NodeFilter {
    /**
     *  Tells the <code>LSSerializer</code> what types of nodes to show to the
     * filter. If a node is not shown to the filter using this attribute, it
     * is automatically serialized. See <code>NodeFilter</code> for
     * definition of the constants. The constants <code>SHOW_DOCUMENT</code>
     * , <code>SHOW_DOCUMENT_TYPE</code>, <code>SHOW_DOCUMENT_FRAGMENT</code>
     * , <code>SHOW_NOTATION</code>, and <code>SHOW_ENTITY</code> are
     * meaningless here, such nodes will never be passed to a
     * <code>LSSerializerFilter</code>.
     * <br> Unlike [<a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Traversal-Range-20001113'>DOM Level 2 Traversal and      Range</a>]
     * , the <code>SHOW_ATTRIBUTE</code> constant indicates that the
     * <code>Attr</code> nodes are shown and passed to the filter.
     * <br> The constants used here are defined in [<a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Traversal-Range-20001113'>DOM Level 2 Traversal and      Range</a>]
     * .
     * <p>
     * <code> LSSerializerFilter </code>为应用程序提供了在序列化时检查节点的能力,并决定应该序列化哪些节点。
     * 
     */
    public int getWhatToShow();

}
