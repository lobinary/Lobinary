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
 * Copyright (c) 2002 World Wide Web Consortium,
 * (Massachusetts Institute of Technology, Institut National de
 * Recherche en Informatique et en Automatique, Keio University). All
 * Rights Reserved. This program is distributed under the W3C's Software
 * Intellectual Property License. This program is distributed in the
 * hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.
 * See W3C License http://www.w3.org/Consortium/Legal/ for more details.
 * <p>
 *  版权所有(c)2002万维网联盟,(马萨诸塞理工学院,庆应义藩大学信息技术研究所)保留所有权利本程序根据W3C的软件知识产权许可分发本程序分发于希望这将是有用的,但没有任何保证;甚至没有对适销性或适用
 * 于特定用途的隐含保证,请参阅W3C许可证http：// wwww3org / Consortium / Legal /了解更多详情。
 * 
 */

package org.w3c.dom.xpath;


import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * The <code>XPathNamespace</code> interface is returned by
 * <code>XPathResult</code> interfaces to represent the XPath namespace node
 * type that DOM lacks. There is no public constructor for this node type.
 * Attempts to place it into a hierarchy or a NamedNodeMap result in a
 * <code>DOMException</code> with the code <code>HIERARCHY_REQUEST_ERR</code>
 * . This node is read only, so methods or setting of attributes that would
 * mutate the node result in a DOMException with the code
 * <code>NO_MODIFICATION_ALLOWED_ERR</code>.
 * <p>The core specification describes attributes of the <code>Node</code>
 * interface that are different for different node node types but does not
 * describe <code>XPATH_NAMESPACE_NODE</code>, so here is a description of
 * those attributes for this node type. All attributes of <code>Node</code>
 * not described in this section have a <code>null</code> or
 * <code>false</code> value.
 * <p><code>ownerDocument</code> matches the <code>ownerDocument</code> of the
 * <code>ownerElement</code> even if the element is later adopted.
 * <p><code>prefix</code> is the prefix of the namespace represented by the
 * node.
 * <p><code>nodeName</code> is the same as <code>prefix</code>.
 * <p><code>nodeType</code> is equal to <code>XPATH_NAMESPACE_NODE</code>.
 * <p><code>namespaceURI</code> is the namespace URI of the namespace
 * represented by the node.
 * <p><code>adoptNode</code>, <code>cloneNode</code>, and
 * <code>importNode</code> fail on this node type by raising a
 * <code>DOMException</code> with the code <code>NOT_SUPPORTED_ERR</code>.In
 * future versions of the XPath specification, the definition of a namespace
 * node may be changed incomatibly, in which case incompatible changes to
 * field values may be required to implement versions beyond XPath 1.0.
 * <p>See also the <a href='http://www.w3.org/2002/08/WD-DOM-Level-3-XPath-20020820'>Document Object Model (DOM) Level 3 XPath Specification</a>.
 * <p>
 * <code> XPathNamespace </code>接口由<code> XPathResult </code>接口返回,表示DOM缺少的XPath命名空间节点类型此节点类型没有公共构造函数尝试将其
 * 放置到层次结构或NamedNodeMap使用代码<code>导致<code> DOMException </code> <code> HIERARCHY_REQUEST_ERR </code>此节点是只
 * 读的,因此使节点变异的属性的方法或设置导致一个DOMException,代码为<code> NO_MODIFICATION_ALLOWED_ERR </code> <p>核心规范描述了<code> No
 * de </code>接口的属性,它们对于不同的节点节点类型是不同的,但不描述<code> XPATH_NAMESPACE_NODE </code>,因此这里是一个描述的这些节点类型的属性本节中未描述的<code>
 *  Node </code>的所有属性都具有<code> null </code>或<code> false </code>值<p> <code> ownerDocument </code>即使后来采用该
 * 元素</p> <code> </code> </code> </code>是<p> <code>前缀</code>是由节点<p> <code > nodeName </code>与<code>前缀相同</code>
 *  <p> <code> nodeType </code>等于<code> XPATH_NAMESPACE_NODE </code> <p> <code> namespaceURI </code >是由节
 * 点<p> <code> adoptNode </code>,<code> cloneNode </code>和<code> importNode </code>表示的命名空间的命名空间URI, <code>
 *  DOMException </code>与代码<code> NOT_SUPPORTED_ERR </code>在XPath规范的未来版本中,命名空间节点的定义可能会不相容地更改,在这种情况下,可能需要
 */
public interface XPathNamespace extends Node {
    // XPathNodeType
    /**
     * The node is a <code>Namespace</code>.
     * <p>
     * 对字段值进行不兼容的更改以实现XPath 10之外的版本<p>另请参阅<a href ='http： / wwww3org / 2002/08 / WD-DOM-Level-3-XPath-20020820'>
     * 文档对象模型(DOM)3级XPath规范</a>。
     * 
     */
    public static final short XPATH_NAMESPACE_NODE      = 13;

    /**
     * The <code>Element</code> on which the namespace was in scope when it
     * was requested. This does not change on a returned namespace node even
     * if the document changes such that the namespace goes out of scope on
     * that element and this node is no longer found there by XPath.
     * <p>
     * 该节点是一个<code>命名空间</code>
     * 
     */
    public Element getOwnerElement();

}
