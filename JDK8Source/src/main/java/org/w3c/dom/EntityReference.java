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
 * <code>EntityReference</code> nodes may be used to represent an entity
 * reference in the tree. Note that character references and references to
 * predefined entities are considered to be expanded by the HTML or XML
 * processor so that characters are represented by their Unicode equivalent
 * rather than by an entity reference. Moreover, the XML processor may
 * completely expand references to entities while building the
 * <code>Document</code>, instead of providing <code>EntityReference</code>
 * nodes. If it does provide such nodes, then for an
 * <code>EntityReference</code> node that represents a reference to a known
 * entity an <code>Entity</code> exists, and the subtree of the
 * <code>EntityReference</code> node is a copy of the <code>Entity</code>
 * node subtree. However, the latter may not be true when an entity contains
 * an unbound namespace prefix. In such a case, because the namespace prefix
 * resolution depends on where the entity reference is, the descendants of
 * the <code>EntityReference</code> node may be bound to different namespace
 * URIs. When an <code>EntityReference</code> node represents a reference to
 * an unknown entity, the node has no children and its replacement value,
 * when used by <code>Attr.value</code> for example, is empty.
 * <p>As for <code>Entity</code> nodes, <code>EntityReference</code> nodes and
 * all their descendants are readonly.
 * <p ><b>Note:</b> <code>EntityReference</code> nodes may cause element
 * content and attribute value normalization problems when, such as in XML
 * 1.0 and XML Schema, the normalization is performed after entity reference
 * are expanded.
 * <p>See also the <a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>Document Object Model (DOM) Level 3 Core Specification</a>.
 * <p>
 * <code> EntityReference </code>节点可以用来表示树中的实体引用注意,字符引用和对预定义实体的引用被认为是由HTML或XML处理器扩展的,因此字符由它们的Unicode等效表示
 * ,而不是由实体引用。
 * 此外,XML处理器可以在构建<code> Document </code>时完全扩展对实体的引用,而不是提供<code> EntityReference </code>节点。
 * 如果它提供这样的节点,代表对已知实体的引用的<code> EntityReference </code>节点<code> Entity </code>存在,并且<code> EntityReferenc
 * e </code>节点的子树是<code>实体</code>节点子树然而,当实体包含未绑定的命名空间前缀时,后者可能不是真实的。
 * 此外,XML处理器可以在构建<code> Document </code>时完全扩展对实体的引用,而不是提供<code> EntityReference </code>节点。
 * 在这种情况下,因为命名空间前缀解析取决于实体引用的位置,所以可以绑定<code> EntityReference </code>节点的后代到不同的命名空间URI当一个<code> EntityRefer
 */
public interface EntityReference extends Node {
}
