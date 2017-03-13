/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2002-2004 The Apache Software Foundation.
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
 *  版权所有2002-2004 Apache软件基金会
 * 
 *  根据Apache许可证第20版("许可证")授权;您不得使用此文件,除非符合许可证您可以在获取许可证的副本
 * 
 *  http：// wwwapacheorg / licenses / LICENSE-20
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件将按"原样"基础分发,无任何明示或暗示的保证或条件。请参阅许可证管理权限和限制许可证
 * 
 */
/*
 * $Id: XPathNamespaceImpl.java,v 1.2.4.1 2005/09/10 04:10:02 jeffsuttor Exp $
 * <p>
 *  $ Id：XPathNamespaceImpljava,v 1241 2005/09/10 04:10:02 jeffsuttor Exp $
 * 
 */


package com.sun.org.apache.xpath.internal.domapi;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.xpath.XPathNamespace;

import org.w3c.dom.UserDataHandler;

/**
 *
 *
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
 * <p>See also the <a href='http://www.w3.org/TR/2004/NOTE-DOM-Level-3-XPath-20040226'>Document Object Model (DOM) Level 3 XPath Specification</a>.
 *
 * This implementation wraps the DOM attribute node that contained the
 * namespace declaration.
 * @xsl.usage internal
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
 * 对字段值进行不兼容的更改以实现XPath 10之外的版本<p>另请参阅<a href ='http： / wwww3org / TR / 2004 / NOTE-DOM-Level-3-XPath-20040226'>
 * 文档对象模型(DOM)3级XPath规范</a>。
 * 
 * 这个实现包装了包含命名空间声明@xslusage internal的DOM属性节点
 * 
 */

class XPathNamespaceImpl implements XPathNamespace {

    // Node that XPathNamespaceImpl wraps
    final private Node m_attributeNode;

    /**
     * Constructor for XPathNamespaceImpl.
     * <p>
     *  XPathNamespaceImpl的构造函数
     * 
     */
    XPathNamespaceImpl(Node node) {
        m_attributeNode = node;
    }

    /**
    /* <p>
    /* 
     * @see com.sun.org.apache.xalan.internal.dom3.xpath.XPathNamespace#getOwnerElement()
     */
    public Element getOwnerElement() {
        return ((Attr)m_attributeNode).getOwnerElement();
    }

    /**
    /* <p>
    /* 
     * @see org.w3c.dom.Node#getNodeName()
     */
    public String getNodeName() {
        return "#namespace";
    }

    /**
    /* <p>
    /* 
     * @see org.w3c.dom.Node#getNodeValue()
     */
    public String getNodeValue() throws DOMException {
        return m_attributeNode.getNodeValue();
    }

    /**
    /* <p>
    /* 
     * @see org.w3c.dom.Node#setNodeValue(String)
     */
    public void setNodeValue(String arg0) throws DOMException {
    }

    /**
    /* <p>
    /* 
     * @see org.w3c.dom.Node#getNodeType()
     */
    public short getNodeType() {
        return XPathNamespace.XPATH_NAMESPACE_NODE;
    }

    /**
    /* <p>
    /* 
     * @see org.w3c.dom.Node#getParentNode()
     */
    public Node getParentNode() {
        return m_attributeNode.getParentNode();
    }

    /**
    /* <p>
    /* 
     * @see org.w3c.dom.Node#getChildNodes()
     */
    public NodeList getChildNodes() {
        return m_attributeNode.getChildNodes();
    }

    /**
    /* <p>
    /* 
     * @see org.w3c.dom.Node#getFirstChild()
     */
    public Node getFirstChild() {
        return m_attributeNode.getFirstChild();
    }

    /**
    /* <p>
    /* 
     * @see org.w3c.dom.Node#getLastChild()
     */
    public Node getLastChild() {
        return m_attributeNode.getLastChild();
    }

    /**
    /* <p>
    /* 
     * @see org.w3c.dom.Node#getPreviousSibling()
     */
    public Node getPreviousSibling() {
        return m_attributeNode.getPreviousSibling();
    }

    /**
    /* <p>
    /* 
     * @see org.w3c.dom.Node#getNextSibling()
     */
    public Node getNextSibling() {
        return m_attributeNode.getNextSibling();
    }

    /**
    /* <p>
    /* 
     * @see org.w3c.dom.Node#getAttributes()
     */
    public NamedNodeMap getAttributes() {
        return m_attributeNode.getAttributes();
    }

    /**
    /* <p>
    /* 
     * @see org.w3c.dom.Node#getOwnerDocument()
     */
    public Document getOwnerDocument() {
        return m_attributeNode.getOwnerDocument();
    }

    /**
    /* <p>
    /* 
     * @see org.w3c.dom.Node#insertBefore(Node, Node)
     */
    public Node insertBefore(Node arg0, Node arg1) throws DOMException {
        return null;
    }

    /**
    /* <p>
    /* 
     * @see org.w3c.dom.Node#replaceChild(Node, Node)
     */
    public Node replaceChild(Node arg0, Node arg1) throws DOMException {
        return null;
    }

    /**
    /* <p>
    /* 
     * @see org.w3c.dom.Node#removeChild(Node)
     */
    public Node removeChild(Node arg0) throws DOMException {
        return null;
    }

    /**
    /* <p>
    /* 
     * @see org.w3c.dom.Node#appendChild(Node)
     */
    public Node appendChild(Node arg0) throws DOMException {
        return null;
    }

    /**
    /* <p>
    /* 
     * @see org.w3c.dom.Node#hasChildNodes()
     */
    public boolean hasChildNodes() {
        return false;
    }

    /**
    /* <p>
    /* 
     * @see org.w3c.dom.Node#cloneNode(boolean)
     */
    public Node cloneNode(boolean arg0) {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR,null);
    }

    /**
    /* <p>
    /* 
     * @see org.w3c.dom.Node#normalize()
     */
    public void normalize() {
        m_attributeNode.normalize();
    }

    /**
    /* <p>
    /* 
     * @see org.w3c.dom.Node#isSupported(String, String)
     */
    public boolean isSupported(String arg0, String arg1) {
        return m_attributeNode.isSupported(arg0, arg1);
    }

    /**
    /* <p>
    /* 
     * @see org.w3c.dom.Node#getNamespaceURI()
     */
    public String getNamespaceURI() {

        // For namespace node, the namespaceURI is the namespace URI
        // of the namespace represented by the node.
        return m_attributeNode.getNodeValue();
    }

    /**
    /* <p>
    /* 
     * @see org.w3c.dom.Node#getPrefix()
     */
    public String getPrefix() {
        return m_attributeNode.getPrefix();
    }

    /**
    /* <p>
    /* 
     * @see org.w3c.dom.Node#setPrefix(String)
     */
    public void setPrefix(String arg0) throws DOMException {
    }

    /**
    /* <p>
    /* 
     * @see org.w3c.dom.Node#getLocalName()
     */
    public String getLocalName() {

        // For namespace node, the local name is the same as the prefix
        return m_attributeNode.getPrefix();
    }

    /**
    /* <p>
    /* 
     * @see org.w3c.dom.Node#hasAttributes()
     */
    public boolean hasAttributes() {
        return m_attributeNode.hasAttributes();
    }

    public String getBaseURI ( ) {
        return null;
    }

    public short compareDocumentPosition(Node other) throws DOMException {
        return 0;
    }

    private String textContent;

    public String getTextContent() throws DOMException {
        return textContent;
    }

    public void setTextContent(String textContent) throws DOMException {
        this.textContent = textContent;
    }

    public boolean isSameNode(Node other) {
        return false;
    }

    public String lookupPrefix(String namespaceURI) {
        return ""; //PENDING
    }

    public boolean isDefaultNamespace(String namespaceURI) {
        return false;
    }

    public String lookupNamespaceURI(String prefix) {
        return null;
    }

    public boolean isEqualNode(Node arg) {
        return false;
    }

    public Object getFeature(String feature, String version) {
        return null; //PENDING
    }

    public Object setUserData(String key,
                              Object data,
                              UserDataHandler handler) {
        return null; //PENDING
    }

    public Object getUserData(String key) {
        return null;
    }
}
