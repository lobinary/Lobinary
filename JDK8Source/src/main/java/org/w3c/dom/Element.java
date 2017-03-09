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
 *  (马萨诸塞理工学院,欧洲研究联合会信息学和数学,庆应大学)。版权所有。这项工作是根据W3C(r)软件许可证[1]分发的,希望它将是有用的,但没有任何保证;甚至没有对适销性或适用于特定用途的隐含保证。
 * 
 *  [1] http://www.w3.org/Consortium/Legal/2002/copyright-software-20021231
 * 
 */

package org.w3c.dom;

/**
 * The <code>Element</code> interface represents an element in an HTML or XML
 * document. Elements may have attributes associated with them; since the
 * <code>Element</code> interface inherits from <code>Node</code>, the
 * generic <code>Node</code> interface attribute <code>attributes</code> may
 * be used to retrieve the set of all attributes for an element. There are
 * methods on the <code>Element</code> interface to retrieve either an
 * <code>Attr</code> object by name or an attribute value by name. In XML,
 * where an attribute value may contain entity references, an
 * <code>Attr</code> object should be retrieved to examine the possibly
 * fairly complex sub-tree representing the attribute value. On the other
 * hand, in HTML, where all attributes have simple string values, methods to
 * directly access an attribute value can safely be used as a convenience.
 * <p ><b>Note:</b> In DOM Level 2, the method <code>normalize</code> is
 * inherited from the <code>Node</code> interface where it was moved.
 * <p>See also the <a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>Document Object Model (DOM) Level 3 Core Specification</a>.
 * <p>
 * <code> Element </code>接口表示HTML或XML文档中的元素。
 * 元素可以具有与它们相关联的属性;由于<code> Element </code>接口继承自<code> Node </code>,所以通用的<code> Node </code>接口属性<code> a
 * ttributes </code>元素的所有属性。
 * <code> Element </code>接口表示HTML或XML文档中的元素。
 * 在<code> Element </code>接口上有一些方法可以按名称检索<code> Attr </code>对象,或按名称检索属性值。
 * 在XML中,其中属性值可以包含实体引用,应当检索<code> Attr </code>对象以检查表示属性值的可能相当复杂的子树。
 * 另一方面,在HTML中,其中所有属性具有简单的字符串值,可以安全地使用直接访问属性值的方法作为方便。
 *  <p> <b>注意：</b>在DOM 2级中,方法<code> normalize </code>从继承它被移动的<code> Node </code>接口。
 *  <p>另请参阅<a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>文档对象模型(DOM)3级核心规范< a>。
 * 
 */
public interface Element extends Node {
    /**
     * The name of the element. If <code>Node.localName</code> is different
     * from <code>null</code>, this attribute is a qualified name. For
     * example, in:
     * <pre> &lt;elementExample id="demo"&gt; ...
     * &lt;/elementExample&gt; , </pre>
     *  <code>tagName</code> has the value
     * <code>"elementExample"</code>. Note that this is case-preserving in
     * XML, as are all of the operations of the DOM. The HTML DOM returns
     * the <code>tagName</code> of an HTML element in the canonical
     * uppercase form, regardless of the case in the source HTML document.
     * <p>
     * 元素的名称。如果<code> Node.localName </code>与<code> null </code>不同,则此属性是限定名称。
     * 例如,在：<pre>&lt; elementExample id ="demo"&gt; ...&lt; / elementExample&gt; ,</pre> <code> tagName </code>
     * 的值为<code>"elementExample"</code>。
     * 元素的名称。如果<code> Node.localName </code>与<code> null </code>不同,则此属性是限定名称。注意,这是XML中的情况保留,DOM的所有操作也是如此。
     *  HTML DOM返回规范大写形式的HTML元素的<code> tagName </code>,而不考虑源HTML文档中的大小写。
     * 
     */
    public String getTagName();

    /**
     * Retrieves an attribute value by name.
     * <p>
     *  按名称检索属性值。
     * 
     * 
     * @param name The name of the attribute to retrieve.
     * @return The <code>Attr</code> value as a string, or the empty string
     *   if that attribute does not have a specified or default value.
     */
    public String getAttribute(String name);

    /**
     * Adds a new attribute. If an attribute with that name is already present
     * in the element, its value is changed to be that of the value
     * parameter. This value is a simple string; it is not parsed as it is
     * being set. So any markup (such as syntax to be recognized as an
     * entity reference) is treated as literal text, and needs to be
     * appropriately escaped by the implementation when it is written out.
     * In order to assign an attribute value that contains entity
     * references, the user must create an <code>Attr</code> node plus any
     * <code>Text</code> and <code>EntityReference</code> nodes, build the
     * appropriate subtree, and use <code>setAttributeNode</code> to assign
     * it as the value of an attribute.
     * <br>To set an attribute with a qualified name and namespace URI, use
     * the <code>setAttributeNS</code> method.
     * <p>
     *  添加新属性。如果具有该名称的属性已经存在于元素中,则其值将更改为value参数的值。这个值是一个简单的字符串;它不会被解析,因为它被设置。
     * 所以任何标记(例如被识别为实体引用的语法)被当作文本文本,并且当它被写出时需要被实现适当地转义。
     * 为了分配包含实体引用的属性值,用户必须创建一个<code> Attr </code>节点以及任何<code> Text </code>和<code> EntityReference </code>节点,
     * 子树,并使用<code> setAttributeNode </code>将其指定为属性的值。
     * 所以任何标记(例如被识别为实体引用的语法)被当作文本文本,并且当它被写出时需要被实现适当地转义。
     *  <br>要使用限定名称和命名空间URI设置属性,请使用<code> setAttributeNS </code>方法。
     * 
     * 
     * @param name The name of the attribute to create or alter.
     * @param value Value to set in string form.
     * @exception DOMException
     *   INVALID_CHARACTER_ERR: Raised if the specified name is not an XML
     *   name according to the XML version in use specified in the
     *   <code>Document.xmlVersion</code> attribute.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this node is readonly.
     */
    public void setAttribute(String name,
                             String value)
                             throws DOMException;

    /**
     * Removes an attribute by name. If a default value for the removed
     * attribute is defined in the DTD, a new attribute immediately appears
     * with the default value as well as the corresponding namespace URI,
     * local name, and prefix when applicable. The implementation may handle
     * default values from other schemas similarly but applications should
     * use <code>Document.normalizeDocument()</code> to guarantee this
     * information is up-to-date.
     * <br>If no attribute with this name is found, this method has no effect.
     * <br>To remove an attribute by local name and namespace URI, use the
     * <code>removeAttributeNS</code> method.
     * <p>
     * 按名称删除属性。如果在DTD中定义了已删除属性的默认值,那么新属性将立即显示为默认值以及相应的命名空间URI,本地名称和前缀(如果适用)。
     * 实现可以类似地处理来自其他模式的默认值,但应用程序应使用<code> Document.normalizeDocument()</code>以保证此信息是最新的。
     *  <br>如果找不到具有此名称的属性,则此方法无效。 <br>要按本地名称和命名空间URI删除属性,请使用<code> removeAttributeNS </code>方法。
     * 
     * 
     * @param name The name of the attribute to remove.
     * @exception DOMException
     *   NO_MODIFICATION_ALLOWED_ERR: Raised if this node is readonly.
     */
    public void removeAttribute(String name)
                                throws DOMException;

    /**
     * Retrieves an attribute node by name.
     * <br>To retrieve an attribute node by qualified name and namespace URI,
     * use the <code>getAttributeNodeNS</code> method.
     * <p>
     *  按名称检索属性节点。 <br>要通过限定名称和命名空间URI检索属性节点,请使用<code> getAttributeNodeNS </code>方法。
     * 
     * 
     * @param name The name (<code>nodeName</code>) of the attribute to
     *   retrieve.
     * @return The <code>Attr</code> node with the specified name (
     *   <code>nodeName</code>) or <code>null</code> if there is no such
     *   attribute.
     */
    public Attr getAttributeNode(String name);

    /**
     * Adds a new attribute node. If an attribute with that name (
     * <code>nodeName</code>) is already present in the element, it is
     * replaced by the new one. Replacing an attribute node by itself has no
     * effect.
     * <br>To add a new attribute node with a qualified name and namespace
     * URI, use the <code>setAttributeNodeNS</code> method.
     * <p>
     *  添加一个新的属性节点。如果具有该名称(<code> nodeName </code>)的属性已经存在于元素中,它将被新的属性替换。替换属性节点本身不起作用。
     *  <br>要添加具有限定名称和命名空间URI的新属性节点,请使用<code> setAttributeNodeNS </code>方法。
     * 
     * 
     * @param newAttr The <code>Attr</code> node to add to the attribute list.
     * @return If the <code>newAttr</code> attribute replaces an existing
     *   attribute, the replaced <code>Attr</code> node is returned,
     *   otherwise <code>null</code> is returned.
     * @exception DOMException
     *   WRONG_DOCUMENT_ERR: Raised if <code>newAttr</code> was created from a
     *   different document than the one that created the element.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this node is readonly.
     *   <br>INUSE_ATTRIBUTE_ERR: Raised if <code>newAttr</code> is already an
     *   attribute of another <code>Element</code> object. The DOM user must
     *   explicitly clone <code>Attr</code> nodes to re-use them in other
     *   elements.
     */
    public Attr setAttributeNode(Attr newAttr)
                                 throws DOMException;

    /**
     * Removes the specified attribute node. If a default value for the
     * removed <code>Attr</code> node is defined in the DTD, a new node
     * immediately appears with the default value as well as the
     * corresponding namespace URI, local name, and prefix when applicable.
     * The implementation may handle default values from other schemas
     * similarly but applications should use
     * <code>Document.normalizeDocument()</code> to guarantee this
     * information is up-to-date.
     * <p>
     * 删除指定的属性节点。如果在DTD中定义了已删除的<code> Attr </code>节点的默认值,则新节点将立即显示为默认值以及相应的命名空间URI,本地名称和前缀(如果适用)。
     * 实现可以类似地处理来自其他模式的默认值,但应用程序应使用<code> Document.normalizeDocument()</code>以保证此信息是最新的。
     * 
     * 
     * @param oldAttr The <code>Attr</code> node to remove from the attribute
     *   list.
     * @return The <code>Attr</code> node that was removed.
     * @exception DOMException
     *   NO_MODIFICATION_ALLOWED_ERR: Raised if this node is readonly.
     *   <br>NOT_FOUND_ERR: Raised if <code>oldAttr</code> is not an attribute
     *   of the element.
     */
    public Attr removeAttributeNode(Attr oldAttr)
                                    throws DOMException;

    /**
     * Returns a <code>NodeList</code> of all descendant <code>Elements</code>
     * with a given tag name, in document order.
     * <p>
     *  以文档顺序返回具有给定标记名称的所有后代<code> Elements </code>的<code> NodeList </code>。
     * 
     * 
     * @param name The name of the tag to match on. The special value "*"
     *   matches all tags.
     * @return A list of matching <code>Element</code> nodes.
     */
    public NodeList getElementsByTagName(String name);

    /**
     * Retrieves an attribute value by local name and namespace URI.
     * <br>Per [<a href='http://www.w3.org/TR/1999/REC-xml-names-19990114/'>XML Namespaces</a>]
     * , applications must use the value <code>null</code> as the
     * <code>namespaceURI</code> parameter for methods if they wish to have
     * no namespace.
     * <p>
     *  通过本地名称和命名空间URI检索属性值。
     *  <br>根据[<a href='http://www.w3.org/TR/1999/REC-xml-names-19990114/'> XML命名空间</a>],应用程序必须使用值<code> nul
     * l </code>作为方法的<code> namespaceURI </code>参数,如果他们希望没有命名空间。
     *  通过本地名称和命名空间URI检索属性值。
     * 
     * 
     * @param namespaceURI The namespace URI of the attribute to retrieve.
     * @param localName The local name of the attribute to retrieve.
     * @return The <code>Attr</code> value as a string, or the empty string
     *   if that attribute does not have a specified or default value.
     * @exception DOMException
     *   NOT_SUPPORTED_ERR: May be raised if the implementation does not
     *   support the feature <code>"XML"</code> and the language exposed
     *   through the Document does not support XML Namespaces (such as [<a href='http://www.w3.org/TR/1999/REC-html401-19991224/'>HTML 4.01</a>]).
     * @since DOM Level 2
     */
    public String getAttributeNS(String namespaceURI,
                                 String localName)
                                 throws DOMException;

    /**
     * Adds a new attribute. If an attribute with the same local name and
     * namespace URI is already present on the element, its prefix is
     * changed to be the prefix part of the <code>qualifiedName</code>, and
     * its value is changed to be the <code>value</code> parameter. This
     * value is a simple string; it is not parsed as it is being set. So any
     * markup (such as syntax to be recognized as an entity reference) is
     * treated as literal text, and needs to be appropriately escaped by the
     * implementation when it is written out. In order to assign an
     * attribute value that contains entity references, the user must create
     * an <code>Attr</code> node plus any <code>Text</code> and
     * <code>EntityReference</code> nodes, build the appropriate subtree,
     * and use <code>setAttributeNodeNS</code> or
     * <code>setAttributeNode</code> to assign it as the value of an
     * attribute.
     * <br>Per [<a href='http://www.w3.org/TR/1999/REC-xml-names-19990114/'>XML Namespaces</a>]
     * , applications must use the value <code>null</code> as the
     * <code>namespaceURI</code> parameter for methods if they wish to have
     * no namespace.
     * <p>
     * 添加新属性。如果元素上已存在具有相同本地名称和命名空间URI的属性,则其前缀将更改为<code> qualifiedName </code>的前缀部分,并将其值更改为<code>值</code>参数。
     * 这个值是一个简单的字符串;它不会被解析,因为它被设置。所以任何标记(例如被识别为实体引用的语法)被当作文本文本,并且当它被写出时需要被实现适当地转义。
     * 为了分配包含实体引用的属性值,用户必须创建一个<code> Attr </code>节点以及任何<code> Text </code>和<code> EntityReference </code>节点,
     * 子树,并使用<code> setAttributeNodeNS </code>或<code> setAttributeNode </code>将其指定为属性的值。
     * 这个值是一个简单的字符串;它不会被解析,因为它被设置。所以任何标记(例如被识别为实体引用的语法)被当作文本文本,并且当它被写出时需要被实现适当地转义。
     *  <br>根据[<a href='http://www.w3.org/TR/1999/REC-xml-names-19990114/'> XML命名空间</a>],应用程序必须使用值<code> nul
     * l </code>作为方法的<code> namespaceURI </code>参数,如果他们希望没有命名空间。
     * 这个值是一个简单的字符串;它不会被解析,因为它被设置。所以任何标记(例如被识别为实体引用的语法)被当作文本文本,并且当它被写出时需要被实现适当地转义。
     * 
     * 
     * @param namespaceURI The namespace URI of the attribute to create or
     *   alter.
     * @param qualifiedName The qualified name of the attribute to create or
     *   alter.
     * @param value The value to set in string form.
     * @exception DOMException
     *   INVALID_CHARACTER_ERR: Raised if the specified qualified name is not
     *   an XML name according to the XML version in use specified in the
     *   <code>Document.xmlVersion</code> attribute.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this node is readonly.
     *   <br>NAMESPACE_ERR: Raised if the <code>qualifiedName</code> is
     *   malformed per the Namespaces in XML specification, if the
     *   <code>qualifiedName</code> has a prefix and the
     *   <code>namespaceURI</code> is <code>null</code>, if the
     *   <code>qualifiedName</code> has a prefix that is "xml" and the
     *   <code>namespaceURI</code> is different from "<a href='http://www.w3.org/XML/1998/namespace'>
     *   http://www.w3.org/XML/1998/namespace</a>", if the <code>qualifiedName</code> or its prefix is "xmlns" and the
     *   <code>namespaceURI</code> is different from "<a href='http://www.w3.org/2000/xmlns/'>http://www.w3.org/2000/xmlns/</a>", or if the <code>namespaceURI</code> is "<a href='http://www.w3.org/2000/xmlns/'>http://www.w3.org/2000/xmlns/</a>" and neither the <code>qualifiedName</code> nor its prefix is "xmlns".
     *   <br>NOT_SUPPORTED_ERR: May be raised if the implementation does not
     *   support the feature <code>"XML"</code> and the language exposed
     *   through the Document does not support XML Namespaces (such as [<a href='http://www.w3.org/TR/1999/REC-html401-19991224/'>HTML 4.01</a>]).
     * @since DOM Level 2
     */
    public void setAttributeNS(String namespaceURI,
                               String qualifiedName,
                               String value)
                               throws DOMException;

    /**
     * Removes an attribute by local name and namespace URI. If a default
     * value for the removed attribute is defined in the DTD, a new
     * attribute immediately appears with the default value as well as the
     * corresponding namespace URI, local name, and prefix when applicable.
     * The implementation may handle default values from other schemas
     * similarly but applications should use
     * <code>Document.normalizeDocument()</code> to guarantee this
     * information is up-to-date.
     * <br>If no attribute with this local name and namespace URI is found,
     * this method has no effect.
     * <br>Per [<a href='http://www.w3.org/TR/1999/REC-xml-names-19990114/'>XML Namespaces</a>]
     * , applications must use the value <code>null</code> as the
     * <code>namespaceURI</code> parameter for methods if they wish to have
     * no namespace.
     * <p>
     * 通过本地名称和命名空间URI删除属性。如果在DTD中定义了已删除属性的默认值,那么新属性将立即显示为默认值以及相应的命名空间URI,本地名称和前缀(如果适用)。
     * 实现可以类似地处理来自其他模式的默认值,但应用程序应使用<code> Document.normalizeDocument()</code>以保证此信息是最新的。
     *  <br>如果找不到具有此本地名称和命名空间URI的属性,则此方法不起作用。
     *  <br>根据[<a href='http://www.w3.org/TR/1999/REC-xml-names-19990114/'> XML命名空间</a>],应用程序必须使用值<code> nul
     * l </code>作为方法的<code> namespaceURI </code>参数,如果他们希望没有命名空间。
     *  <br>如果找不到具有此本地名称和命名空间URI的属性,则此方法不起作用。
     * 
     * 
     * @param namespaceURI The namespace URI of the attribute to remove.
     * @param localName The local name of the attribute to remove.
     * @exception DOMException
     *   NO_MODIFICATION_ALLOWED_ERR: Raised if this node is readonly.
     *   <br>NOT_SUPPORTED_ERR: May be raised if the implementation does not
     *   support the feature <code>"XML"</code> and the language exposed
     *   through the Document does not support XML Namespaces (such as [<a href='http://www.w3.org/TR/1999/REC-html401-19991224/'>HTML 4.01</a>]).
     * @since DOM Level 2
     */
    public void removeAttributeNS(String namespaceURI,
                                  String localName)
                                  throws DOMException;

    /**
     * Retrieves an <code>Attr</code> node by local name and namespace URI.
     * <br>Per [<a href='http://www.w3.org/TR/1999/REC-xml-names-19990114/'>XML Namespaces</a>]
     * , applications must use the value <code>null</code> as the
     * <code>namespaceURI</code> parameter for methods if they wish to have
     * no namespace.
     * <p>
     *  通过本地名称和命名空间URI检索<code> Attr </code>节点。
     *  <br>根据[<a href='http://www.w3.org/TR/1999/REC-xml-names-19990114/'> XML命名空间</a>],应用程序必须使用值<code> nul
     * l </code>作为方法的<code> namespaceURI </code>参数,如果他们希望没有命名空间。
     *  通过本地名称和命名空间URI检索<code> Attr </code>节点。
     * 
     * 
     * @param namespaceURI The namespace URI of the attribute to retrieve.
     * @param localName The local name of the attribute to retrieve.
     * @return The <code>Attr</code> node with the specified attribute local
     *   name and namespace URI or <code>null</code> if there is no such
     *   attribute.
     * @exception DOMException
     *   NOT_SUPPORTED_ERR: May be raised if the implementation does not
     *   support the feature <code>"XML"</code> and the language exposed
     *   through the Document does not support XML Namespaces (such as [<a href='http://www.w3.org/TR/1999/REC-html401-19991224/'>HTML 4.01</a>]).
     * @since DOM Level 2
     */
    public Attr getAttributeNodeNS(String namespaceURI,
                                   String localName)
                                   throws DOMException;

    /**
     * Adds a new attribute. If an attribute with that local name and that
     * namespace URI is already present in the element, it is replaced by
     * the new one. Replacing an attribute node by itself has no effect.
     * <br>Per [<a href='http://www.w3.org/TR/1999/REC-xml-names-19990114/'>XML Namespaces</a>]
     * , applications must use the value <code>null</code> as the
     * <code>namespaceURI</code> parameter for methods if they wish to have
     * no namespace.
     * <p>
     * 添加新属性。如果具有该本地名称和该命名空间URI的属性已经存在于元素中,它将被新的替换。替换属性节点本身不起作用。
     *  <br>根据[<a href='http://www.w3.org/TR/1999/REC-xml-names-19990114/'> XML命名空间</a>],应用程序必须使用值<code> nul
     * l </code>作为方法的<code> namespaceURI </code>参数,如果他们希望没有命名空间。
     * 添加新属性。如果具有该本地名称和该命名空间URI的属性已经存在于元素中,它将被新的替换。替换属性节点本身不起作用。
     * 
     * 
     * @param newAttr The <code>Attr</code> node to add to the attribute list.
     * @return If the <code>newAttr</code> attribute replaces an existing
     *   attribute with the same local name and namespace URI, the replaced
     *   <code>Attr</code> node is returned, otherwise <code>null</code> is
     *   returned.
     * @exception DOMException
     *   WRONG_DOCUMENT_ERR: Raised if <code>newAttr</code> was created from a
     *   different document than the one that created the element.
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this node is readonly.
     *   <br>INUSE_ATTRIBUTE_ERR: Raised if <code>newAttr</code> is already an
     *   attribute of another <code>Element</code> object. The DOM user must
     *   explicitly clone <code>Attr</code> nodes to re-use them in other
     *   elements.
     *   <br>NOT_SUPPORTED_ERR: May be raised if the implementation does not
     *   support the feature <code>"XML"</code> and the language exposed
     *   through the Document does not support XML Namespaces (such as [<a href='http://www.w3.org/TR/1999/REC-html401-19991224/'>HTML 4.01</a>]).
     * @since DOM Level 2
     */
    public Attr setAttributeNodeNS(Attr newAttr)
                                   throws DOMException;

    /**
     * Returns a <code>NodeList</code> of all the descendant
     * <code>Elements</code> with a given local name and namespace URI in
     * document order.
     * <p>
     *  以文档顺序返回具有给定本地名称和命名空间URI的所有后代<code> Elements </code>的<code> NodeList </code>。
     * 
     * 
     * @param namespaceURI The namespace URI of the elements to match on. The
     *   special value "*" matches all namespaces.
     * @param localName The local name of the elements to match on. The
     *   special value "*" matches all local names.
     * @return A new <code>NodeList</code> object containing all the matched
     *   <code>Elements</code>.
     * @exception DOMException
     *   NOT_SUPPORTED_ERR: May be raised if the implementation does not
     *   support the feature <code>"XML"</code> and the language exposed
     *   through the Document does not support XML Namespaces (such as [<a href='http://www.w3.org/TR/1999/REC-html401-19991224/'>HTML 4.01</a>]).
     * @since DOM Level 2
     */
    public NodeList getElementsByTagNameNS(String namespaceURI,
                                           String localName)
                                           throws DOMException;

    /**
     * Returns <code>true</code> when an attribute with a given name is
     * specified on this element or has a default value, <code>false</code>
     * otherwise.
     * <p>
     *  当在此元素上指定具有给定名称的属性时,返回<code> true </code>,否则返回默认值<code> false </code>。
     * 
     * 
     * @param name The name of the attribute to look for.
     * @return <code>true</code> if an attribute with the given name is
     *   specified on this element or has a default value, <code>false</code>
     *    otherwise.
     * @since DOM Level 2
     */
    public boolean hasAttribute(String name);

    /**
     * Returns <code>true</code> when an attribute with a given local name and
     * namespace URI is specified on this element or has a default value,
     * <code>false</code> otherwise.
     * <br>Per [<a href='http://www.w3.org/TR/1999/REC-xml-names-19990114/'>XML Namespaces</a>]
     * , applications must use the value <code>null</code> as the
     * <code>namespaceURI</code> parameter for methods if they wish to have
     * no namespace.
     * <p>
     *  当具有给定本地名称和命名空间URI的属性在此元素上指定或具有默认值<code> false </code>时,返回<code> true </code> <br>根据[<a href='http://www.w3.org/TR/1999/REC-xml-names-19990114/'>
     *  XML命名空间</a>],应用程序必须使用值<code> null </code>作为方法的<code> namespaceURI </code>参数,如果他们希望没有命名空间。
     * 
     * 
     * @param namespaceURI The namespace URI of the attribute to look for.
     * @param localName The local name of the attribute to look for.
     * @return <code>true</code> if an attribute with the given local name
     *   and namespace URI is specified or has a default value on this
     *   element, <code>false</code> otherwise.
     * @exception DOMException
     *   NOT_SUPPORTED_ERR: May be raised if the implementation does not
     *   support the feature <code>"XML"</code> and the language exposed
     *   through the Document does not support XML Namespaces (such as [<a href='http://www.w3.org/TR/1999/REC-html401-19991224/'>HTML 4.01</a>]).
     * @since DOM Level 2
     */
    public boolean hasAttributeNS(String namespaceURI,
                                  String localName)
                                  throws DOMException;

    /**
     *  The type information associated with this element.
     * <p>
     *  与此元素相关联的类型信息。
     * 
     * 
     * @since DOM Level 3
     */
    public TypeInfo getSchemaTypeInfo();

    /**
     *  If the parameter <code>isId</code> is <code>true</code>, this method
     * declares the specified attribute to be a user-determined ID attribute
     * . This affects the value of <code>Attr.isId</code> and the behavior
     * of <code>Document.getElementById</code>, but does not change any
     * schema that may be in use, in particular this does not affect the
     * <code>Attr.schemaTypeInfo</code> of the specified <code>Attr</code>
     * node. Use the value <code>false</code> for the parameter
     * <code>isId</code> to undeclare an attribute for being a
     * user-determined ID attribute.
     * <br> To specify an attribute by local name and namespace URI, use the
     * <code>setIdAttributeNS</code> method.
     * <p>
     * 如果参数<code> isId </code>是<code> true </code>,则此方法将指定的属性声明为用户确定的ID属性。
     * 这会影响<code> Attr.isId </code>的值和<code> Document.getElementById </code>的行为,但不会更改可能正在使用的任何模式,特别是这不会影响<代码>
     *  Attr.schemaTypeInfo </code>指定的<code> Attr </code>节点。
     * 如果参数<code> isId </code>是<code> true </code>,则此方法将指定的属性声明为用户确定的ID属性。
     * 对参数<code> isId </code>使用值<code> false </code>来取消声明作为用户确定的ID属性的属性。
     *  <br>要按本地名称和命名空间URI指定属性,请使用<code> setIdAttributeNS </code>方法。
     * 
     * 
     * @param name The name of the attribute.
     * @param isId Whether the attribute is a of type ID.
     * @exception DOMException
     *   NO_MODIFICATION_ALLOWED_ERR: Raised if this node is readonly.
     *   <br>NOT_FOUND_ERR: Raised if the specified node is not an attribute
     *   of this element.
     * @since DOM Level 3
     */
    public void setIdAttribute(String name,
                               boolean isId)
                               throws DOMException;

    /**
     *  If the parameter <code>isId</code> is <code>true</code>, this method
     * declares the specified attribute to be a user-determined ID attribute
     * . This affects the value of <code>Attr.isId</code> and the behavior
     * of <code>Document.getElementById</code>, but does not change any
     * schema that may be in use, in particular this does not affect the
     * <code>Attr.schemaTypeInfo</code> of the specified <code>Attr</code>
     * node. Use the value <code>false</code> for the parameter
     * <code>isId</code> to undeclare an attribute for being a
     * user-determined ID attribute.
     * <p>
     *  如果参数<code> isId </code>是<code> true </code>,则此方法将指定的属性声明为用户确定的ID属性。
     * 这会影响<code> Attr.isId </code>的值和<code> Document.getElementById </code>的行为,但不会更改可能正在使用的任何模式,特别是这不会影响<代码>
     *  Attr.schemaTypeInfo </code>指定的<code> Attr </code>节点。
     *  如果参数<code> isId </code>是<code> true </code>,则此方法将指定的属性声明为用户确定的ID属性。
     * 对参数<code> isId </code>使用值<code> false </code>来取消声明作为用户确定的ID属性的属性。
     * 
     * 
     * @param namespaceURI The namespace URI of the attribute.
     * @param localName The local name of the attribute.
     * @param isId Whether the attribute is a of type ID.
     * @exception DOMException
     *   NO_MODIFICATION_ALLOWED_ERR: Raised if this node is readonly.
     *   <br>NOT_FOUND_ERR: Raised if the specified node is not an attribute
     *   of this element.
     * @since DOM Level 3
     */
    public void setIdAttributeNS(String namespaceURI,
                                 String localName,
                                 boolean isId)
                                 throws DOMException;

    /**
     *  If the parameter <code>isId</code> is <code>true</code>, this method
     * declares the specified attribute to be a user-determined ID attribute
     * . This affects the value of <code>Attr.isId</code> and the behavior
     * of <code>Document.getElementById</code>, but does not change any
     * schema that may be in use, in particular this does not affect the
     * <code>Attr.schemaTypeInfo</code> of the specified <code>Attr</code>
     * node. Use the value <code>false</code> for the parameter
     * <code>isId</code> to undeclare an attribute for being a
     * user-determined ID attribute.
     * <p>
     * 如果参数<code> isId </code>是<code> true </code>,则此方法将指定的属性声明为用户确定的ID属性。
     * 这会影响<code> Attr.isId </code>的值和<code> Document.getElementById </code>的行为,但不会更改可能正在使用的任何模式,特别是这不会影响<代码>
     *  Attr.schemaTypeInfo </code>指定的<code> Attr </code>节点。
     * 
     * @param idAttr The attribute node.
     * @param isId Whether the attribute is a of type ID.
     * @exception DOMException
     *   NO_MODIFICATION_ALLOWED_ERR: Raised if this node is readonly.
     *   <br>NOT_FOUND_ERR: Raised if the specified node is not an attribute
     *   of this element.
     * @since DOM Level 3
     */
    public void setIdAttributeNode(Attr idAttr,
                                   boolean isId)
                                   throws DOMException;

}
