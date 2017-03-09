/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 1999-2002,2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  版权所有1999-2002,2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.dom;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import org.w3c.dom.TypeInfo;
import com.sun.org.apache.xerces.internal.util.URI;

/**
 * Elements represent most of the "markup" and structure of the
 * document.  They contain both the data for the element itself
 * (element name and attributes), and any contained nodes, including
 * document text (as children).
 * <P>
 * Elements may have Attributes associated with them; the API for this is
 * defined in Node, but the function is implemented here. In general, XML
 * applications should retrive Attributes as Nodes, since they may contain
 * entity references and hence be a fairly complex sub-tree. HTML users will
 * be dealing with simple string values, and convenience methods are provided
 * to work in terms of Strings.
 * <P>
 * ElementImpl does not support Namespaces. ElementNSImpl, which inherits from
 * it, does.
 * <p>
 *  元素表示文档的大多数"标记"和结构。它们包含元素本身的数据(元素名称和属性)和任何包含的节点,包括文档文本(作为子节点)。
 * <P>
 *  元素可以具有与它们相关联的属性;这个API是在Node中定义的,但是函数在这里实现。一般来说,XML应用程序应该捕获属性为节点,因为它们可能包含实体引用,因此是一个相当复杂的子树。
 *  HTML用户将处理简单的字符串值,并提供方便的方法来处理字符串。
 * <P>
 *  ElementImpl不支持命名空间。 ElementNSImpl,它继承它。
 * 
 * 
 * @see ElementNSImpl
 *
 * @xerces.internal
 *
 * @author Arnaud  Le Hors, IBM
 * @author Joe Kesselman, IBM
 * @author Andy Clark, IBM
 * @author Ralf Pfeiffer, IBM
 * @since  PR-DOM-Level-1-19980818.
 */
public class ElementImpl
    extends ParentNode
    implements Element, TypeInfo {

    //
    // Constants
    //

    /** Serialization version. */
    static final long serialVersionUID = 3717253516652722278L;
    //
    // Data
    //

    /** Element name. */
    protected String name;

    /** Attributes. */
    protected AttributeMap attributes;

    //
    // Constructors
    //

    /** Factory constructor. */
    public ElementImpl(CoreDocumentImpl ownerDoc, String name) {
        super(ownerDoc);
        this.name = name;
        needsSyncData(true);    // synchronizeData will initialize attributes
    }

    // for ElementNSImpl
    protected ElementImpl() {}

    // Support for DOM Level 3 renameNode method.
    // Note: This only deals with part of the pb. CoreDocumentImpl
    // does all the work.
    void rename(String name) {
        if (needsSyncData()) {
            synchronizeData();
        }
            this.name = name;
        reconcileDefaultAttributes();
    }

    //
    // Node methods
    //


    /**
     * A short integer indicating what type of node this is. The named
     * constants for this value are defined in the org.w3c.dom.Node interface.
     * <p>
     * 指示这是什么类型的节点的短整数。此值的命名常量在org.w3c.dom.Node接口中定义。
     * 
     */
    public short getNodeType() {
        return Node.ELEMENT_NODE;
    }

    /**
     * Returns the element name
     * <p>
     *  返回元素名称
     * 
     */
    public String getNodeName() {
        if (needsSyncData()) {
            synchronizeData();
        }
        return name;
    }

    /**
     * Retrieve all the Attributes as a set. Note that this API is inherited
     * from Node rather than specified on Element; in fact only Elements will
     * ever have Attributes, but they want to allow folks to "blindly" operate
     * on the tree as a set of Nodes.
     * <p>
     *  将所有属性检索为一组。请注意,此API是继承自Node,而不是在Element上指定;实际上只有元素将有属性,但他们希望允许人们"盲目"作为一组节点在树上操作。
     * 
     */
    public NamedNodeMap getAttributes() {

        if (needsSyncData()) {
            synchronizeData();
        }
        if (attributes == null) {
            attributes = new AttributeMap(this, null);
        }
        return attributes;

    } // getAttributes():NamedNodeMap

    /**
     * Return a duplicate copy of this Element. Note that its children
     * will not be copied unless the "deep" flag is true, but Attributes
     * are <i>always</i> replicated.
     *
     * <p>
     *  返回此元素的副本。注意,除非"deep"标志为真,但属性<i>始终</i>被复制,否则不会复制其子代。
     * 
     * 
     * @see org.w3c.dom.Node#cloneNode(boolean)
     */
    public Node cloneNode(boolean deep) {

        ElementImpl newnode = (ElementImpl) super.cloneNode(deep);
        // Replicate NamedNodeMap rather than sharing it.
        if (attributes != null) {
            newnode.attributes = (AttributeMap) attributes.cloneMap(newnode);
        }
        return newnode;

    } // cloneNode(boolean):Node

   /**
     * DOM Level 3 WD - Experimental.
     * Retrieve baseURI
     * <p>
     *  DOM 3级WD  - 实验。检索baseURI
     * 
     */
    public String getBaseURI() {

        if (needsSyncData()) {
            synchronizeData();
        }
        // Absolute base URI is computed according to
        // XML Base (http://www.w3.org/TR/xmlbase/#granularity)
        // 1. The base URI specified by an xml:base attribute on the element,
        // if one exists
        if (attributes != null) {
            Attr attrNode = (Attr)attributes.getNamedItem("xml:base");
            if (attrNode != null) {
                String uri =  attrNode.getNodeValue();
                if (uri.length() != 0 ) {// attribute value is always empty string
                    try {
                       uri = new URI(uri).toString();
                    }
                    catch (com.sun.org.apache.xerces.internal.util.URI.MalformedURIException e) {
                        // This may be a relative URI.

                        // Make any parentURI into a URI object to use with the URI(URI, String) constructor
                        String parentBaseURI = (this.ownerNode != null) ? this.ownerNode.getBaseURI() : null;
                        if (parentBaseURI != null){
                            try{
                                uri = new URI(new URI(parentBaseURI), uri).toString();
                            }
                            catch (com.sun.org.apache.xerces.internal.util.URI.MalformedURIException ex){
                                // This should never happen: parent should have checked the URI and returned null if invalid.
                                return null;
                            }
                            return uri;
                        }
                        return null;
                    }
                    return uri;
                }
            }
        }

        // 2.the base URI of the element's parent element within the
        // document or external entity, if one exists
                // 3. the base URI of the document entity or external entity
                // containing the element

                // ownerNode serves as a parent or as document
                String baseURI = (this.ownerNode != null) ? this.ownerNode.getBaseURI() : null ;
        //base URI of parent element is not null
        if(baseURI != null){
            try {
                //return valid absolute base URI
               return new URI(baseURI).toString();
            }
            catch (com.sun.org.apache.xerces.internal.util.URI.MalformedURIException e){
                return null;
            }
        }
        return null;
    } //getBaseURI



    /**
     * NON-DOM
     * set the ownerDocument of this node, its children, and its attributes
     * <p>
     *  NON-DOM设置此节点的ownerDocument,其子代及其属性
     * 
     */
    void setOwnerDocument(CoreDocumentImpl doc) {
        super.setOwnerDocument(doc);
        if (attributes != null) {
            attributes.setOwnerDocument(doc);
        }
    }

    //
    // Element methods
    //

    /**
     * Look up a single Attribute by name. Returns the Attribute's
     * string value, or an empty string (NOT null!) to indicate that the
     * name did not map to a currently defined attribute.
     * <p>
     * Note: Attributes may contain complex node trees. This method
     * returns the "flattened" string obtained from Attribute.getValue().
     * If you need the structure information, see getAttributeNode().
     * <p>
     *  按名称查找单个属性。返回Attribute的字符串值或空字符串(NOT null！),以指示名称未映射到当前定义的属性。
     * <p>
     *  注意：属性可能包含复杂节点树。此方法返回从Attribute.getValue()获取的"flattened"字符串。如果需要结构信息,请参阅getAttributeNode()。
     * 
     */
    public String getAttribute(String name) {

        if (needsSyncData()) {
            synchronizeData();
        }
        if (attributes == null) {
            return "";
        }
        Attr attr = (Attr)(attributes.getNamedItem(name));
        return (attr == null) ? "" : attr.getValue();

    } // getAttribute(String):String


    /**
     * Look up a single Attribute by name. Returns the Attribute Node,
     * so its complete child tree is available. This could be important in
     * XML, where the string rendering may not be sufficient information.
     * <p>
     * If no matching attribute is available, returns null.
     * <p>
     *  按名称查找单个属性。返回Attribute节点,因此它的完整子树是可用的。这在XML中可能很重要,其中字符串呈现可能不是足够的信息。
     * <p>
     *  如果没有匹配属性可用,则返回null。
     * 
     */
    public Attr getAttributeNode(String name) {

        if (needsSyncData()) {
            synchronizeData();
        }
        if (attributes == null) {
            return null;
        }
        return (Attr)attributes.getNamedItem(name);

    } // getAttributeNode(String):Attr


    /**
     * Returns a NodeList of all descendent nodes (children,
     * grandchildren, and so on) which are Elements and which have the
     * specified tag name.
     * <p>
     * Note: NodeList is a "live" view of the DOM. Its contents will
     * change as the DOM changes, and alterations made to the NodeList
     * will be reflected in the DOM.
     *
     * <p>
     *  返回所有派生节点(子节点,孙子节点等)的NodeList,这些节点是Elements并且具有指定的标记名称。
     * <p>
     * 注意：NodeList是DOM的"实时"视图。其内容将随着DOM更改而改变,对NodeList所做的更改将反映在DOM中。
     * 
     * 
     * @param tagname The type of element to gather. To obtain a list of
     * all elements no matter what their names, use the wild-card tag
     * name "*".
     *
     * @see DeepNodeListImpl
     */
    public NodeList getElementsByTagName(String tagname) {
        return new DeepNodeListImpl(this,tagname);
    }

    /**
     * Returns the name of the Element. Note that Element.nodeName() is
     * defined to also return the tag name.
     * <p>
     * This is case-preserving in XML. HTML should uppercasify it on the
     * way in.
     * <p>
     *  返回元素的名称。注意,Element.nodeName()被定义为也返回标签名称。
     * <p>
     *  这是XML中的情况保留。 HTML应该在进入时高亮显示它。
     * 
     */
    public String getTagName() {
        if (needsSyncData()) {
            synchronizeData();
        }
        return name;
    }

    /**
     * In "normal form" (as read from a source file), there will never be two
     * Text children in succession. But DOM users may create successive Text
     * nodes in the course of manipulating the document. Normalize walks the
     * sub-tree and merges adjacent Texts, as if the DOM had been written out
     * and read back in again. This simplifies implementation of higher-level
     * functions that may want to assume that the document is in standard form.
     * <p>
     * To normalize a Document, normalize its top-level Element child.
     * <p>
     * As of PR-DOM-Level-1-19980818, CDATA -- despite being a subclass of
     * Text -- is considered "markup" and will _not_ be merged either with
     * normal Text or with other CDATASections.
     * <p>
     *  在"正常形式"(从源文件读取)中,将不会有两个文本孩子连续。但DOM用户可能在操作文档的过程中创建连续的Text节点。规范化遍历子树并合并相邻的文本,好像DOM已经被写出并再次读回一样。
     * 这简化了可能想要假定文档是标准形式的更高级别函数的实现。
     * <p>
     *  要标准化文档,请对其顶级Element子项进行标准化。
     * <p>
     *  从PR-DOM-Level-1-19980818开始,尽管CDATA是Text的子类,但CDATA被认为是"标记",并且不会与正常文本或其他CDATASections合并。
     * 
     */
    public void normalize() {
        // No need to normalize if already normalized.
        if (isNormalized()) {
            return;
        }
        if (needsSyncChildren()) {
            synchronizeChildren();
        }
        ChildNode kid, next;
        for (kid = firstChild; kid != null; kid = next) {
            next = kid.nextSibling;

            // If kid is a text node, we need to check for one of two
            // conditions:
            //   1) There is an adjacent text node
            //   2) There is no adjacent text node, but kid is
            //      an empty text node.
            if ( kid.getNodeType() == Node.TEXT_NODE )
            {
                // If an adjacent text node, merge it with kid
                if ( next!=null && next.getNodeType() == Node.TEXT_NODE )
                {
                    ((Text)kid).appendData(next.getNodeValue());
                    removeChild( next );
                    next = kid; // Don't advance; there might be another.
                }
                else
                {
                    // If kid is empty, remove it
                    if ( kid.getNodeValue() == null || kid.getNodeValue().length() == 0 ) {
                        removeChild( kid );
                    }
                }
            }

            // Otherwise it might be an Element, which is handled recursively
            else if (kid.getNodeType() == Node.ELEMENT_NODE) {
                kid.normalize();
            }
        }

        // We must also normalize all of the attributes
        if ( attributes!=null )
        {
            for( int i=0; i<attributes.getLength(); ++i )
            {
                Node attr = attributes.item(i);
                attr.normalize();
            }
        }

        // changed() will have occurred when the removeChild() was done,
        // so does not have to be reissued.

        isNormalized(true);
    } // normalize()

    /**
     * Remove the named attribute from this Element. If the removed
     * Attribute has a default value, it is immediately replaced thereby.
     * <P>
     * The default logic is actually implemented in NamedNodeMapImpl.
     * PR-DOM-Level-1-19980818 doesn't fully address the DTD, so some
     * of this behavior is likely to change in future versions. ?????
     * <P>
     * Note that this call "succeeds" even if no attribute by this name
     * existed -- unlike removeAttributeNode, which will throw a not-found
     * exception in that case.
     *
     * <p>
     *  从此元素中删除命名属性。如果移除的属性具有默认值,则立即被替换。
     * <P>
     *  默认逻辑实际上是在NamedNodeMapImpl中实现的。 PR-DOM-Level-1-19980818没有完全寻址DTD,因此这种行为在将来的版本中可能会改变。 ?????
     * <P>
     *  请注意,即使此名称不存在属性,此调用"成功" - 与removeAttributeNode不同,在这种情况下,将抛出一个未找到的异常。
     * 
     * 
     * @throws DOMException(NO_MODIFICATION_ALLOWED_ERR) if the node is
     * readonly.
     */
    public void removeAttribute(String name) {

        if (ownerDocument.errorChecking && isReadOnly()) {
            String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null);
            throw new DOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR, msg);
        }

        if (needsSyncData()) {
            synchronizeData();
        }

        if (attributes == null) {
            return;
        }

        attributes.safeRemoveNamedItem(name);

    } // removeAttribute(String)


    /**
     * Remove the specified attribute/value pair. If the removed
     * Attribute has a default value, it is immediately replaced.
     * <p>
     * NOTE: Specifically removes THIS NODE -- not the node with this
     * name, nor the node with these contents. If the specific Attribute
     * object passed in is not stored in this Element, we throw a
     * DOMException.  If you really want to remove an attribute by name,
     * use removeAttribute().
     *
     * <p>
     * 删除指定的属性/值对。如果删除的属性具有默认值,则会立即替换。
     * <p>
     *  注意：特别删除此节点 - 不是具有此名称的节点,也不是具有这些内容的节点。如果传入的特定Attribute对象没有存储在这个Element中,我们抛出一个DOMException。
     * 如果真的要按名称删除属性,请使用removeAttribute()。
     * 
     * 
     * @return the Attribute object that was removed.
     * @throws DOMException(NOT_FOUND_ERR) if oldattr is not an attribute of
     * this Element.
     * @throws DOMException(NO_MODIFICATION_ALLOWED_ERR) if the node is
     * readonly.
     */
    public Attr removeAttributeNode(Attr oldAttr)
        throws DOMException {

        if (ownerDocument.errorChecking && isReadOnly()) {
            String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null);
            throw new DOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR, msg);
        }

        if (needsSyncData()) {
            synchronizeData();
        }

        if (attributes == null) {
            String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NOT_FOUND_ERR", null);
            throw new DOMException(DOMException.NOT_FOUND_ERR, msg);
        }
        return (Attr) attributes.removeItem(oldAttr, true);

    } // removeAttributeNode(Attr):Attr


    /**
     * Add a new name/value pair, or replace the value of the existing
     * attribute having that name.
     *
     * Note: this method supports only the simplest kind of Attribute,
     * one whose value is a string contained in a single Text node.
     * If you want to assert a more complex value (which XML permits,
     * though HTML doesn't), see setAttributeNode().
     *
     * The attribute is created with specified=true, meaning it's an
     * explicit value rather than inherited from the DTD as a default.
     * Again, setAttributeNode can be used to achieve other results.
     *
     * <p>
     *  添加新的名称/值对,或替换具有该名称的现有属性的值。
     * 
     *  注意：此方法仅支持最简单的属性,其值为包含在单个Text节点中的字符串。如果您想声明一个更复杂的值(XML允许,尽管HTML不允许),请参见setAttributeNode()。
     * 
     *  该属性是使用specified = true创建的,这意味着它是一个显式值,而不是作为默认值从DTD继承。同样,setAttributeNode可以用来实现其他结果。
     * 
     * 
     * @throws DOMException(INVALID_NAME_ERR) if the name is not acceptable.
     * (Attribute factory will do that test for us.)
     *
     * @throws DOMException(NO_MODIFICATION_ALLOWED_ERR) if the node is
     * readonly.
     */
        public void setAttribute(String name, String value) {

                if (ownerDocument.errorChecking && isReadOnly()) {
                        String msg =
                                DOMMessageFormatter.formatMessage(
                                        DOMMessageFormatter.DOM_DOMAIN,
                                        "NO_MODIFICATION_ALLOWED_ERR",
                                        null);
                        throw new DOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR, msg);
                }

                if (needsSyncData()) {
                        synchronizeData();
                }

                Attr newAttr = getAttributeNode(name);
                if (newAttr == null) {
                        newAttr = getOwnerDocument().createAttribute(name);

                        if (attributes == null) {
                                attributes = new AttributeMap(this, null);
                        }

                        newAttr.setNodeValue(value);
                        attributes.setNamedItem(newAttr);
                }
                else {
                        newAttr.setNodeValue(value);
                }

        } // setAttribute(String,String)

    /**
     * Add a new attribute/value pair, or replace the value of the
     * existing attribute with that name.
     * <P>
     * This method allows you to add an Attribute that has already been
     * constructed, and hence avoids the limitations of the simple
     * setAttribute() call. It can handle attribute values that have
     * arbitrarily complex tree structure -- in particular, those which
     * had entity references mixed into their text.
     *
     * <p>
     *  添加新的属性/值对,或用该名称替换现有属性的值。
     * <P>
     *  此方法允许您添加已构建的属性,因此避免了简单setAttribute()调用的限制。它可以处理具有任意复杂的树结构的属性值 - 特别是那些具有混合到其文本中的实体引用的属性值。
     * 
     * 
     * @throws DOMException(INUSE_ATTRIBUTE_ERR) if the Attribute object
     * has already been assigned to another Element.
     */
    public Attr setAttributeNode(Attr newAttr)
        throws DOMException
        {

        if (needsSyncData()) {
            synchronizeData();
        }

        if (ownerDocument.errorChecking) {
            if (isReadOnly()) {
                String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null);
                throw new DOMException(
                                     DOMException.NO_MODIFICATION_ALLOWED_ERR,
                                     msg);
            }

            if (newAttr.getOwnerDocument() != ownerDocument) {
                String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "WRONG_DOCUMENT_ERR", null);
                    throw new DOMException(DOMException.WRONG_DOCUMENT_ERR, msg);
            }
        }

        if (attributes == null) {
            attributes = new AttributeMap(this, null);
        }
        // This will throw INUSE if necessary
        return (Attr) attributes.setNamedItem(newAttr);

    } // setAttributeNode(Attr):Attr

    //
    // DOM2: Namespace methods
    //

    /**
     * Introduced in DOM Level 2. <p>
     *
     * Retrieves an attribute value by local name and namespace URI.
     *
     * <p>
     *  在DOM级别2中引入。<p>
     * 
     *  通过本地名称和命名空间URI检索属性值。
     * 
     * 
     * @param namespaceURI
     *                      The namespace URI of the attribute to
     *                      retrieve.
     * @param localName     The local name of the attribute to retrieve.
     * @return String       The Attr value as a string, or empty string
     *                      if that attribute
     *                      does not have a specified or default value.
     * @since WD-DOM-Level-2-19990923
     */
    public String getAttributeNS(String namespaceURI, String localName) {

        if (needsSyncData()) {
            synchronizeData();
        }

        if (attributes == null) {
            return "";
        }

        Attr attr = (Attr)(attributes.getNamedItemNS(namespaceURI, localName));
        return (attr == null) ? "" : attr.getValue();

    } // getAttributeNS(String,String):String

    /**
     * Introduced in DOM Level 2. <p>
     *
     *  Adds a new attribute.
     *  If the given namespaceURI is null or an empty string and the
     *  qualifiedName has a prefix that is "xml", the new attribute is bound to
     *  the predefined namespace "http://www.w3.org/XML/1998/namespace"
     *  [Namespaces].  If an attribute with the same local name and namespace
     *  URI is already present on the element, its prefix is changed to be the
     *  prefix part of the qualifiedName, and its value is changed to be the
     *  value parameter. This value is a simple string, it is not parsed as it
     *  is being set. So any markup (such as syntax to be recognized as an
     *  entity reference) is treated as literal text, and needs to be
     *  appropriately escaped by the implementation when it is written out. In
     *  order to assign an attribute value that contains entity references, the
     *  user must create an Attr node plus any Text and EntityReference nodes,
     *  build the appropriate subtree, and use setAttributeNodeNS or
     *  setAttributeNode to assign it as the value of an attribute.
     *
     * <p>
     *  在DOM级别2中引入。<p>
     * 
     * 添加新属性。
     * 如果给定的namespaceURI为null或空字符串,并且qualifiedName的前缀为"xml",则新属性将绑定到预定义的命名空间"http://www.w3.org/XML/1998/name
     * space"[Namespaces 如果元素上已存在具有相同本地名称和命名空间URI的属性,则其前缀将更改为qualifiedName的前缀部分,并将其值更改为value参数。
     * 添加新属性。此值是一个简单的字符串,它不会被解析,因为它正在设置。所以任何标记(例如被识别为实体引用的语法)被当作文本文本,并且当它被写出时需要被实现适当地转义。
     * 为了分配包含实体引用的属性值,用户必须创建一个Attr节点以及任何Text和EntityReference节点,构建相应的子树,并使用setAttributeNodeNS或setAttributeNod
     * e将其指定为属性的值。
     * 添加新属性。此值是一个简单的字符串,它不会被解析,因为它正在设置。所以任何标记(例如被识别为实体引用的语法)被当作文本文本,并且当它被写出时需要被实现适当地转义。
     * 
     * 
     * @param namespaceURI      The namespace URI of the attribute to create
     *                          or alter.
     * @param qualifiedName     The qualified name of the attribute to create or
     *                          alter.
     * @param value             The value to set in string form.
     * @throws                  INVALID_CHARACTER_ERR: Raised if the specified
     *                          name contains an invalid character.
     *
     * @throws                  NO_MODIFICATION_ALLOWED_ERR: Raised if this
     *                          node is readonly.
     *
     * @throws                  NAMESPACE_ERR: Raised if the qualifiedName
     *                          has a prefix that is "xml" and the namespaceURI
     *                          is neither null nor an empty string nor
     *                          "http://www.w3.org/XML/1998/namespace", or if
     *                          the qualifiedName has a prefix that is "xmlns"
     *                          but the namespaceURI is neither null nor an
     *                          empty string, or if if the qualifiedName has a
     *                          prefix different from "xml" and "xmlns" and the
     *                          namespaceURI is null or an empty string.
     * @since WD-DOM-Level-2-19990923
     */
     public void setAttributeNS(String namespaceURI,String qualifiedName,
                                          String value) {
                if (ownerDocument.errorChecking && isReadOnly()) {
                        String msg =
                                DOMMessageFormatter.formatMessage(
                                        DOMMessageFormatter.DOM_DOMAIN,
                                        "NO_MODIFICATION_ALLOWED_ERR",
                                        null);
                        throw new DOMException(
                                DOMException.NO_MODIFICATION_ALLOWED_ERR,
                                msg);
                }
                if (needsSyncData()) {
                        synchronizeData();
                }
                int index = qualifiedName.indexOf(':');
                String prefix, localName;
                if (index < 0) {
                        prefix = null;
                        localName = qualifiedName;
                }
                else {
                        prefix = qualifiedName.substring(0, index);
                        localName = qualifiedName.substring(index + 1);
                }
                Attr newAttr = getAttributeNodeNS(namespaceURI, localName);
                if (newAttr == null) {
            // REVISIT: this is not efficient, we are creating twice the same
            //          strings for prefix and localName.
                        newAttr = getOwnerDocument().createAttributeNS(
                                        namespaceURI,
                                        qualifiedName);
                        if (attributes == null) {
                                attributes = new AttributeMap(this, null);
                        }
                        newAttr.setNodeValue(value);
                        attributes.setNamedItemNS(newAttr);
                }
                else {
            if (newAttr instanceof AttrNSImpl){
                String origNodeName = ((AttrNSImpl) newAttr).name;
                String newName = (prefix!=null) ? (prefix+":"+localName) : localName;

                ((AttrNSImpl) newAttr).name = newName;

                if (!newName.equals(origNodeName)) {
                    // Note: we can't just change the name of the attribute. Names have to be in sorted
                    // order in the attributes vector because a binary search is used to locate them.
                    // If the new name has a different prefix, the list may become unsorted.
                    // Maybe it would be better to resort the list, but the simplest
                    // fix seems to be to remove the old attribute and re-insert it.
                    // -- Norman.Walsh@Sun.COM, 2 Feb 2007
                    newAttr = (Attr) attributes.removeItem(newAttr, false);
                    attributes.addItem(newAttr);
                }
            }
            else {
                // This case may happen if user calls:
                //      elem.setAttribute("name", "value");
                //      elem.setAttributeNS(null, "name", "value");
                // This case is not defined by the DOM spec, we choose
                // to create a new attribute in this case and remove an old one from the tree
                // note this might cause events to be propagated or user data to be lost
                newAttr = new AttrNSImpl((CoreDocumentImpl)getOwnerDocument(), namespaceURI, qualifiedName, localName);
                attributes.setNamedItemNS(newAttr);
            }

                        newAttr.setNodeValue(value);
                }

    } // setAttributeNS(String,String,String)


    /**
     * Introduced in DOM Level 2. <p>
     *
     * Removes an attribute by local name and namespace URI. If the removed
     * attribute has a default value it is immediately replaced.
     * The replacing attribute has the same namespace URI and local name,
     * as well as the original prefix.<p>
     *
     * <p>
     *  在DOM级别2中引入。<p>
     * 
     *  通过本地名称和命名空间URI删除属性。如果被删除的属性有一个默认值,它会立即被替换。 replacement属性具有相同的命名空间URI和本地名称,以及原始前缀。<p>
     * 
     * 
     * @param namespaceURI  The namespace URI of the attribute to remove.
     *
     * @param localName     The local name of the attribute to remove.
     * @throws                  NO_MODIFICATION_ALLOWED_ERR: Raised if this
     *                          node is readonly.
     * @since WD-DOM-Level-2-19990923
     */
    public void removeAttributeNS(String namespaceURI, String localName) {

        if (ownerDocument.errorChecking && isReadOnly()) {
            String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null);
            throw new DOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR, msg);
        }

        if (needsSyncData()) {
            synchronizeData();
        }

        if (attributes == null) {
            return;
        }

        attributes.safeRemoveNamedItemNS(namespaceURI, localName);

    } // removeAttributeNS(String,String)

    /**
     * Retrieves an Attr node by local name and namespace URI.
     *
     * <p>
     *  通过本地名称和命名空间URI检索Attr节点。
     * 
     * 
     * @param namespaceURI  The namespace URI of the attribute to
     *                      retrieve.
     * @param localName     The local name of the attribute to retrieve.
     * @return Attr         The Attr node with the specified attribute
     *                      local name and namespace
     *                      URI or null if there is no such attribute.
     * @since WD-DOM-Level-2-19990923
     */
    public Attr getAttributeNodeNS(String namespaceURI, String localName){

        if (needsSyncData()) {
            synchronizeData();
        }
        if (attributes == null) {
            return null;
        }
        return (Attr)attributes.getNamedItemNS(namespaceURI, localName);

    } // getAttributeNodeNS(String,String):Attr

    /**
     * Introduced in DOM Level 2. <p>
     *
     * Adds a new attribute. If an attribute with that local name and
     * namespace URI is already present in the element, it is replaced
     * by the new one.
     *
     * <p>
     *  在DOM级别2中引入。<p>
     * 
     *  添加新属性。如果具有该本地名称和命名空间URI的属性已经存在于元素中,则它将被新的替换。
     * 
     * 
     * @param Attr      The Attr node to add to the attribute list. When
     *                  the Node has no namespaceURI, this method behaves
     *                  like setAttributeNode.
     * @return Attr     If the newAttr attribute replaces an existing attribute
     *                  with the same local name and namespace URI, the *
     *                  previously existing Attr node is returned, otherwise
     *                  null is returned.
     * @throws          WRONG_DOCUMENT_ERR: Raised if newAttr
     *                  was created from a different document than the one that
     *                  created the element.
     *
     * @throws          NO_MODIFICATION_ALLOWED_ERR: Raised if
     *                  this node is readonly.
     *
     * @throws          INUSE_ATTRIBUTE_ERR: Raised if newAttr is
     *                  already an attribute of another Element object. The
     *                  DOM user must explicitly clone Attr nodes to re-use
     *                  them in other elements.
     * @since WD-DOM-Level-2-19990923
     */
    public Attr setAttributeNodeNS(Attr newAttr)
        throws DOMException
        {

        if (needsSyncData()) {
            synchronizeData();
        }
        if (ownerDocument.errorChecking) {
            if (isReadOnly()) {
                String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null);
                    throw new DOMException(
                                     DOMException.NO_MODIFICATION_ALLOWED_ERR,
                                     msg);
            }
            if (newAttr.getOwnerDocument() != ownerDocument) {
                String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "WRONG_DOCUMENT_ERR", null);
                throw new DOMException(DOMException.WRONG_DOCUMENT_ERR, msg);
            }
        }

        if (attributes == null) {
            attributes = new AttributeMap(this, null);
        }
        // This will throw INUSE if necessary
        return (Attr) attributes.setNamedItemNS(newAttr);

    } // setAttributeNodeNS(Attr):Attr

    /**
      * NON-DOM: sets attribute node for this element
      * <p>
      * NON-DOM：设置此元素的属性节点
      * 
      */
    protected int setXercesAttributeNode (Attr attr){

        if (needsSyncData()) {
            synchronizeData();
        }

        if (attributes == null) {
            attributes = new AttributeMap(this, null);
        }
        return attributes.addItem(attr);

    }

    /**
      * NON-DOM: get inded of an attribute
      * <p>
      *  NON-DOM：获取一个属性
      * 
      */
    protected int getXercesAttribute(String namespaceURI, String localName){

        if (needsSyncData()) {
            synchronizeData();
        }
        if (attributes == null) {
            return -1;
        }
        return attributes.getNamedItemIndex(namespaceURI, localName);

    }

    /**
     * Introduced in DOM Level 2.
     * <p>
     *  在DOM级别2中引入。
     * 
     */
    public boolean hasAttributes() {
        if (needsSyncData()) {
            synchronizeData();
        }
        return (attributes != null && attributes.getLength() != 0);
    }

    /**
     * Introduced in DOM Level 2.
     * <p>
     *  在DOM级别2中引入。
     * 
     */
    public boolean hasAttribute(String name) {
        return getAttributeNode(name) != null;
    }

    /**
     * Introduced in DOM Level 2.
     * <p>
     *  在DOM级别2中引入。
     * 
     */
    public boolean hasAttributeNS(String namespaceURI, String localName) {
        return getAttributeNodeNS(namespaceURI, localName) != null;
    }

    /**
     * Introduced in DOM Level 2. <p>
     *
     * Returns a NodeList of all the Elements with a given local name and
     * namespace URI in the order in which they would be encountered in a
     * preorder traversal of the Document tree, starting from this node.
     *
     * <p>
     *  在DOM级别2中引入。<p>
     * 
     *  返回具有给定本地名称和命名空间URI的所有元素的NodeList,按照从文档树开始的预先遍历遍历该节点的顺序返回它们。
     * 
     * 
     * @param namespaceURI The namespace URI of the elements to match
     *                     on. The special value "*" matches all
     *                     namespaces. When it is null or an empty
     *                     string, this method behaves like
     *                     getElementsByTagName.
     * @param localName    The local name of the elements to match on.
     *                     The special value "*" matches all local names.
     * @return NodeList    A new NodeList object containing all the matched
     *                     Elements.
     * @since WD-DOM-Level-2-19990923
     */
    public NodeList getElementsByTagNameNS(String namespaceURI,
                                           String localName) {
        return new DeepNodeListImpl(this, namespaceURI, localName);
    }

    /**
     * DOM Level 3 WD- Experimental.
     * Override inherited behavior from NodeImpl and ParentNode to check on
     * attributes
     * <p>
     *  DOM级别3。覆盖从NodeImpl和ParentNode继承的行为以检查属性
     * 
     */
    public boolean isEqualNode(Node arg) {
        if (!super.isEqualNode(arg)) {
            return false;
        }
        boolean hasAttrs = hasAttributes();
        if (hasAttrs != ((Element) arg).hasAttributes()) {
            return false;
        }
        if (hasAttrs) {
            NamedNodeMap map1 = getAttributes();
            NamedNodeMap map2 = ((Element) arg).getAttributes();
            int len = map1.getLength();
            if (len != map2.getLength()) {
                return false;
            }
            for (int i = 0; i < len; i++) {
                Node n1 = map1.item(i);
                if (n1.getLocalName() == null) { // DOM Level 1 Node
                    Node n2 = map2.getNamedItem(n1.getNodeName());
                    if (n2 == null || !((NodeImpl) n1).isEqualNode(n2)) {
                        return false;
                    }
                }
                else {
                    Node n2 = map2.getNamedItemNS(n1.getNamespaceURI(),
                                                  n1.getLocalName());
                    if (n2 == null || !((NodeImpl) n1).isEqualNode(n2)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * DOM Level 3: register the given attribute node as an ID attribute
     * <p>
     *  DOM级别3：将给定属性节点注册为ID属性
     * 
     */
    public void setIdAttributeNode(Attr at, boolean makeId) {
        if (needsSyncData()) {
            synchronizeData();
        }
        if (ownerDocument.errorChecking) {
            if (isReadOnly()) {
                String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null);
                throw new DOMException(
                                     DOMException.NO_MODIFICATION_ALLOWED_ERR,
                                     msg);
            }

            if (at.getOwnerElement() != this) {
                String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NOT_FOUND_ERR", null);
                throw new DOMException(DOMException.NOT_FOUND_ERR, msg);
            }
        }
        ((AttrImpl) at).isIdAttribute(makeId);
        if (!makeId) {
            ownerDocument.removeIdentifier(at.getValue());
        }
        else {
            ownerDocument.putIdentifier(at.getValue(), this);
        }
    }

    /**
     * DOM Level 3: register the given attribute node as an ID attribute
     * <p>
     *  DOM级别3：将给定属性节点注册为ID属性
     * 
     */
    public void setIdAttribute(String name, boolean makeId) {
        if (needsSyncData()) {
            synchronizeData();
        }
        Attr at = getAttributeNode(name);

                if( at == null){
                String msg = DOMMessageFormatter.formatMessage(
                                                                        DOMMessageFormatter.DOM_DOMAIN,
                                                                        "NOT_FOUND_ERR", null);
            throw new DOMException(DOMException.NOT_FOUND_ERR, msg);
                }

                if (ownerDocument.errorChecking) {
            if (isReadOnly()) {
                String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null);
                throw new DOMException(
                                     DOMException.NO_MODIFICATION_ALLOWED_ERR,
                                     msg);
            }

            if (at.getOwnerElement() != this) {
                String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NOT_FOUND_ERR", null);
                throw new DOMException(DOMException.NOT_FOUND_ERR, msg);
            }
        }

        ((AttrImpl) at).isIdAttribute(makeId);
        if (!makeId) {
            ownerDocument.removeIdentifier(at.getValue());
        }
        else {
            ownerDocument.putIdentifier(at.getValue(), this);
        }
    }

    /**
     * DOM Level 3: register the given attribute node as an ID attribute
     * <p>
     *  DOM级别3：将给定属性节点注册为ID属性
     * 
     */
    public void setIdAttributeNS(String namespaceURI, String localName,
                                    boolean makeId) {
        if (needsSyncData()) {
            synchronizeData();
        }
        //if namespace uri is empty string, set it to 'null'
        if (namespaceURI != null) {
            namespaceURI = (namespaceURI.length() == 0)? null : namespaceURI;
        }
        Attr at = getAttributeNodeNS(namespaceURI, localName);

                if( at == null){
                String msg = DOMMessageFormatter.formatMessage(
                                                                        DOMMessageFormatter.DOM_DOMAIN,
                                                                        "NOT_FOUND_ERR", null);
            throw new DOMException(DOMException.NOT_FOUND_ERR, msg);
                }

                if (ownerDocument.errorChecking) {
            if (isReadOnly()) {
                String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null);
                throw new DOMException(
                                     DOMException.NO_MODIFICATION_ALLOWED_ERR,
                                     msg);
            }

            if (at.getOwnerElement() != this) {
                String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NOT_FOUND_ERR", null);
                throw new DOMException(DOMException.NOT_FOUND_ERR, msg);
            }
        }
        ((AttrImpl) at).isIdAttribute(makeId);
        if (!makeId) {
            ownerDocument.removeIdentifier(at.getValue());
        }
        else {
            ownerDocument.putIdentifier(at.getValue(), this);
        }
   }

    /**
    /* <p>
    /* 
     * @see org.w3c.dom.TypeInfo#getTypeName()
     */
     public String getTypeName() {
        return null;
     }

    /**
    /* <p>
    /* 
     * @see org.w3c.dom.TypeInfo#getTypeNamespace()
     */
    public String getTypeNamespace() {
        return null;
    }

    /**
     * Introduced in DOM Level 3. <p>
     * Checks if a type is derived from another by restriction. See:
     * http://www.w3.org/TR/DOM-Level-3-Core/core.html#TypeInfo-isDerivedFrom
     *
     * <p>
     *  在DOM级别3中引入。<p>检查类型是否通过限制从另一个派生。
     * 参见：http://www.w3.org/TR/DOM-Level-3-Core/core.html#TypeInfo-isDerivedFrom。
     * 
     * 
     * @param ancestorNS
     *        The namspace of the ancestor type declaration
     * @param ancestorName
     *        The name of the ancestor type declaration
     * @param type
     *        The reference type definition
     *
     * @return boolean True if the type is derived by restriciton for the
     *         reference type
     */
    public boolean isDerivedFrom(String typeNamespaceArg,
                                 String typeNameArg,
                                 int derivationMethod) {

        return false;
    }

        /**
         * Method getSchemaTypeInfo.
         * <p>
         *  方法getSchemaTypeInfo。
         * 
         * 
         * @return TypeInfo
         */
    public TypeInfo getSchemaTypeInfo(){
        if(needsSyncData()) {
            synchronizeData();
        }
        return this;
    }

    //
    // Public methods
    //

    /**
     * NON-DOM: Subclassed to flip the attributes' readonly switch as well.
     * <p>
     *  NON-DOM：子类翻转属性的readonly开关。
     * 
     * @see NodeImpl#setReadOnly
     */
    public void setReadOnly(boolean readOnly, boolean deep) {
        super.setReadOnly(readOnly,deep);
        if (attributes != null) {
            attributes.setReadOnly(readOnly,true);
        }
    }



    //
    // Protected methods
    //

    /** Synchronizes the data (name and value) for fast nodes. */
    protected void synchronizeData() {

        // no need to sync in the future
        needsSyncData(false);

        // we don't want to generate any event for this so turn them off
        boolean orig = ownerDocument.getMutationEvents();
        ownerDocument.setMutationEvents(false);

        // attributes
        setupDefaultAttributes();

        // set mutation events flag back to its original value
        ownerDocument.setMutationEvents(orig);

    } // synchronizeData()

    // support for DOM Level 3 renameNode method
    // @param el The element from which to take the attributes
    void moveSpecifiedAttributes(ElementImpl el) {
        if (needsSyncData()) {
            synchronizeData();
        }
        if (el.hasAttributes()) {
            if (attributes == null) {
                attributes = new AttributeMap(this, null);
            }
            attributes.moveSpecifiedAttributes(el.attributes);
        }
    }

    /** Setup the default attributes. */
    protected void setupDefaultAttributes() {
        NamedNodeMapImpl defaults = getDefaultAttributes();
        if (defaults != null) {
            attributes = new AttributeMap(this, defaults);
        }
    }

    /** Reconcile default attributes. */
    protected void reconcileDefaultAttributes() {
        if (attributes != null) {
            NamedNodeMapImpl defaults = getDefaultAttributes();
            attributes.reconcileDefaults(defaults);
        }
    }

    /** Get the default attributes. */
    protected NamedNodeMapImpl getDefaultAttributes() {

        DocumentTypeImpl doctype =
            (DocumentTypeImpl) ownerDocument.getDoctype();
        if (doctype == null) {
            return null;
        }
        ElementDefinitionImpl eldef =
            (ElementDefinitionImpl)doctype.getElements()
                                               .getNamedItem(getNodeName());
        if (eldef == null) {
            return null;
        }
        return (NamedNodeMapImpl) eldef.getAttributes();

    } // getDefaultAttributes()

} // class ElementImpl
