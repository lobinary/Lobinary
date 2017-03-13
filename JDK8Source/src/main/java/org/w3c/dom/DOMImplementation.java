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
 * The <code>DOMImplementation</code> interface provides a number of methods
 * for performing operations that are independent of any particular instance
 * of the document object model.
 * <p>See also the <a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>Document Object Model (DOM) Level 3 Core Specification</a>.
 * <p>
 * <code> DOMImplementation </code>接口提供了许多用于执行独立于文档对象模型的任何特定实例的操作的方法<p>另请参见<a href ='http：// wwww3org / TR / 2004 / REC-DOM-Level-3-Core-20040407'>
 * 文档对象模型(DOM)3级核心规范</a>。
 * 
 */
public interface DOMImplementation {
    /**
     * Test if the DOM implementation implements a specific feature and
     * version, as specified in <a href="http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407/core.html#DOMFeatures">DOM Features</a>.
     * <p>
     *  测试DOM实施是否实现了特定功能和版本,如<a href=\"http://wwww3org/TR/2004/REC-DOM-Level-3-Core-20040407/corehtml#DOMFeatures\">
     *  DOM功能< / a>。
     * 
     * 
     * @param feature  The name of the feature to test.
     * @param version  This is the version number of the feature to test.
     * @return <code>true</code> if the feature is implemented in the
     *   specified version, <code>false</code> otherwise.
     */
    public boolean hasFeature(String feature,
                              String version);

    /**
     * Creates an empty <code>DocumentType</code> node. Entity declarations
     * and notations are not made available. Entity reference expansions and
     * default attribute additions do not occur..
     * <p>
     *  创建空的<code> DocumentType </code>节点实体声明和符号不可用实体引用扩展和默认属性添加不会发生
     * 
     * 
     * @param qualifiedName The qualified name of the document type to be
     *   created.
     * @param publicId The external subset public identifier.
     * @param systemId The external subset system identifier.
     * @return A new <code>DocumentType</code> node with
     *   <code>Node.ownerDocument</code> set to <code>null</code>.
     * @exception DOMException
     *   INVALID_CHARACTER_ERR: Raised if the specified qualified name is not
     *   an XML name according to [<a href='http://www.w3.org/TR/2004/REC-xml-20040204'>XML 1.0</a>].
     *   <br>NAMESPACE_ERR: Raised if the <code>qualifiedName</code> is
     *   malformed.
     *   <br>NOT_SUPPORTED_ERR: May be raised if the implementation does not
     *   support the feature "XML" and the language exposed through the
     *   Document does not support XML Namespaces (such as [<a href='http://www.w3.org/TR/1999/REC-html401-19991224/'>HTML 4.01</a>]).
     * @since DOM Level 2
     */
    public DocumentType createDocumentType(String qualifiedName,
                                           String publicId,
                                           String systemId)
                                           throws DOMException;

    /**
     * Creates a DOM Document object of the specified type with its document
     * element.
     * <br>Note that based on the <code>DocumentType</code> given to create
     * the document, the implementation may instantiate specialized
     * <code>Document</code> objects that support additional features than
     * the "Core", such as "HTML" [<a href='http://www.w3.org/TR/2003/REC-DOM-Level-2-HTML-20030109'>DOM Level 2 HTML</a>]
     * . On the other hand, setting the <code>DocumentType</code> after the
     * document was created makes this very unlikely to happen.
     * Alternatively, specialized <code>Document</code> creation methods,
     * such as <code>createHTMLDocument</code> [<a href='http://www.w3.org/TR/2003/REC-DOM-Level-2-HTML-20030109'>DOM Level 2 HTML</a>]
     * , can be used to obtain specific types of <code>Document</code>
     * objects.
     * <p>
     * 使用其文档元素创建指定类型的DOM文档对象<br>请注意,根据给定创建文档的<code> DocumentType </code>,实现可以实例化专门的<code> Document </code>对象
     * 支持比"Core"更多的功能,例如"HTML"[<a href='http://wwww3org/TR/2003/REC-DOM-Level-2-HTML-20030109'> DOM Level 2 
     * HTML </a >]另一方面,在文档创建之后设置<code> DocumentType </code>使得这种情况不太可能发生。
     * 或者,专门的<code> Document </code>创建方法,例如<code> createHTMLDocument <代码> [<a href='http://wwww3org/TR/2003/REC-DOM-Level-2-HTML-20030109'>
     *  DOM Level 2 HTML </a>],可用于获取特定类型的<代码>文档</code>对象。
     * 
     * @param namespaceURI The namespace URI of the document element to
     *   create or <code>null</code>.
     * @param qualifiedName The qualified name of the document element to be
     *   created or <code>null</code>.
     * @param doctype The type of document to be created or <code>null</code>.
     *   When <code>doctype</code> is not <code>null</code>, its
     *   <code>Node.ownerDocument</code> attribute is set to the document
     *   being created.
     * @return A new <code>Document</code> object with its document element.
     *   If the <code>NamespaceURI</code>, <code>qualifiedName</code>, and
     *   <code>doctype</code> are <code>null</code>, the returned
     *   <code>Document</code> is empty with no document element.
     * @exception DOMException
     *   INVALID_CHARACTER_ERR: Raised if the specified qualified name is not
     *   an XML name according to [<a href='http://www.w3.org/TR/2004/REC-xml-20040204'>XML 1.0</a>].
     *   <br>NAMESPACE_ERR: Raised if the <code>qualifiedName</code> is
     *   malformed, if the <code>qualifiedName</code> has a prefix and the
     *   <code>namespaceURI</code> is <code>null</code>, or if the
     *   <code>qualifiedName</code> is <code>null</code> and the
     *   <code>namespaceURI</code> is different from <code>null</code>, or
     *   if the <code>qualifiedName</code> has a prefix that is "xml" and
     *   the <code>namespaceURI</code> is different from "<a href='http://www.w3.org/XML/1998/namespace'>
     *   http://www.w3.org/XML/1998/namespace</a>" [<a href='http://www.w3.org/TR/1999/REC-xml-names-19990114/'>XML Namespaces</a>]
     *   , or if the DOM implementation does not support the
     *   <code>"XML"</code> feature but a non-null namespace URI was
     *   provided, since namespaces were defined by XML.
     *   <br>WRONG_DOCUMENT_ERR: Raised if <code>doctype</code> has already
     *   been used with a different document or was created from a different
     *   implementation.
     *   <br>NOT_SUPPORTED_ERR: May be raised if the implementation does not
     *   support the feature "XML" and the language exposed through the
     *   Document does not support XML Namespaces (such as [<a href='http://www.w3.org/TR/1999/REC-html401-19991224/'>HTML 4.01</a>]).
     * @since DOM Level 2
     */
    public Document createDocument(String namespaceURI,
                                   String qualifiedName,
                                   DocumentType doctype)
                                   throws DOMException;

    /**
     *  This method returns a specialized object which implements the
     * specialized APIs of the specified feature and version, as specified
     * in <a href="http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407/core.html#DOMFeatures">DOM Features</a>. The specialized object may also be obtained by using
     * binding-specific casting methods but is not necessarily expected to,
     * as discussed in . This method also allow the implementation to
     * provide specialized objects which do not support the
     * <code>DOMImplementation</code> interface.
     * <p>
     * 
     * 
     * @param feature  The name of the feature requested. Note that any plus
     *   sign "+" prepended to the name of the feature will be ignored since
     *   it is not significant in the context of this method.
     * @param version  This is the version number of the feature to test.
     * @return  Returns an object which implements the specialized APIs of
     *   the specified feature and version, if any, or <code>null</code> if
     *   there is no object which implements interfaces associated with that
     *   feature. If the <code>DOMObject</code> returned by this method
     *   implements the <code>DOMImplementation</code> interface, it must
     *   delegate to the primary core <code>DOMImplementation</code> and not
     *   return results inconsistent with the primary core
     *   <code>DOMImplementation</code> such as <code>hasFeature</code>,
     *   <code>getFeature</code>, etc.
     * @since DOM Level 3
     */
    public Object getFeature(String feature,
                             String version);

}
