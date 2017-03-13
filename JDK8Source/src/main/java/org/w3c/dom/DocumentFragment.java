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

package org.w3c.dom;

/**
 * <code>DocumentFragment</code> is a "lightweight" or "minimal"
 * <code>Document</code> object. It is very common to want to be able to
 * extract a portion of a document's tree or to create a new fragment of a
 * document. Imagine implementing a user command like cut or rearranging a
 * document by moving fragments around. It is desirable to have an object
 * which can hold such fragments and it is quite natural to use a Node for
 * this purpose. While it is true that a <code>Document</code> object could
 * fulfill this role, a <code>Document</code> object can potentially be a
 * heavyweight object, depending on the underlying implementation. What is
 * really needed for this is a very lightweight object.
 * <code>DocumentFragment</code> is such an object.
 * <p>Furthermore, various operations -- such as inserting nodes as children
 * of another <code>Node</code> -- may take <code>DocumentFragment</code>
 * objects as arguments; this results in all the child nodes of the
 * <code>DocumentFragment</code> being moved to the child list of this node.
 * <p>The children of a <code>DocumentFragment</code> node are zero or more
 * nodes representing the tops of any sub-trees defining the structure of
 * the document. <code>DocumentFragment</code> nodes do not need to be
 * well-formed XML documents (although they do need to follow the rules
 * imposed upon well-formed XML parsed entities, which can have multiple top
 * nodes). For example, a <code>DocumentFragment</code> might have only one
 * child and that child node could be a <code>Text</code> node. Such a
 * structure model represents neither an HTML document nor a well-formed XML
 * document.
 * <p>When a <code>DocumentFragment</code> is inserted into a
 * <code>Document</code> (or indeed any other <code>Node</code> that may
 * take children) the children of the <code>DocumentFragment</code> and not
 * the <code>DocumentFragment</code> itself are inserted into the
 * <code>Node</code>. This makes the <code>DocumentFragment</code> very
 * useful when the user wishes to create nodes that are siblings; the
 * <code>DocumentFragment</code> acts as the parent of these nodes so that
 * the user can use the standard methods from the <code>Node</code>
 * interface, such as <code>Node.insertBefore</code> and
 * <code>Node.appendChild</code>.
 * <p>See also the <a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>Document Object Model (DOM) Level 3 Core Specification</a>.
 * <p>
 * <code> DocumentFragment </code>是一个"轻量的"或"最小的"<code> Document </code>对象很常见的想要能够提取文档树的一部分或创建一个新的片段一个文档想
 * 象一下,实现一个用户命令,比如剪切或者通过移动片段来重新排列文档。
 * 希望有一个可以容纳这样的片段的对象,并且为此目的使用一个节点是非常自然的。
 * 虽然一个<code>文档</code>对象可以实现这个角色,一个<code> Document </code>对象可能是一个重量级的对象,这取决于底层实现真正需要的是一个非常轻量级的对象<code> D
 * ocumentFragment <代码>是这样的对象此外,各种操作(例如,将节点作为另一<code> Node </code>的子节点插入)可以将<code> DocumentFragment </code>
 * 对象作为参数;这导致<code> DocumentFragment </code>的所有子节点被移动到该节点的子列表<p> <code> DocumentFragment </code>节点的子节点是零
 * 个或多个节点,定义文档结构的任何子树<code> DocumentFragment </code>节点不需要是格式良好的XML文档(尽管它们需要遵循强加在格式良好的XML解析实体上的规则,这可以具有多个
 * 顶节点)例如,<code> DocumentFragment </code>可能只有一个子节点,该子节点可以是<code> Text </code>节点这样的结构模型既不表示HTML文档也不表示格式良好
 */
public interface DocumentFragment extends Node {
}
