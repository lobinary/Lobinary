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

import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

/**
 * The DOMImplementation class is description of a particular
 * implementation of the Document Object Model. As such its data is
 * static, shared by all instances of this implementation.
 * <P>
 * The DOM API requires that it be a real object rather than static
 * methods. However, there's nothing that says it can't be a singleton,
 * so that's how I've implemented it.
 *
 * @xerces.internal
 *
 * <p>
 *  DOMImplementation类是对文档对象模型的特定实现的描述。因此,其数据是静态的,由该实现的所有实例共享。
 * <P>
 *  DOM API要求它是一个真正的对象,而不是静态方法。然而,没有什么说它不能是一个单身,所以这是我如何实现它。
 * 
 *  @ xerces.internal
 * 
 * 
 * @since  PR-DOM-Level-1-19980818.
 */
public class PSVIDOMImplementationImpl extends CoreDOMImplementationImpl {

    //
    // Data
    //

    // static

    /** Dom implementation singleton. */
    static PSVIDOMImplementationImpl singleton = new PSVIDOMImplementationImpl();

    //
    // Public methods
    //

    /** NON-DOM: Obtain and return the single shared object */
    public static DOMImplementation getDOMImplementation() {
        return singleton;
    }

    //
    // DOMImplementation methods
    //

    /**
     * Test if the DOM implementation supports a specific "feature" --
     * currently meaning language and level thereof.
     *
     * <p>
     *  测试DOM实现是否支持特定的"特征" - 目前意味着语言和其级别。
     * 
     * 
     * @param feature      The package name of the feature to test.
     * In Level 1, supported values are "HTML" and "XML" (case-insensitive).
     * At this writing, com.sun.org.apache.xerces.internal.dom supports only XML.
     *
     * @param version      The version number of the feature being tested.
     * This is interpreted as "Version of the DOM API supported for the
     * specified Feature", and in Level 1 should be "1.0"
     *
     * @return    true iff this implementation is compatable with the specified
     * feature and version.
     */
    public boolean hasFeature(String feature, String version) {
        return super.hasFeature(feature, version) ||
               feature.equalsIgnoreCase("psvi");
    } // hasFeature(String,String):boolean

    /**
     * Introduced in DOM Level 2. <p>
     *
     * Creates an XML Document object of the specified type with its document
     * element.
     *
     * <p>
     *  在DOM级别2中引入。<p>
     * 
     * 
     * @param namespaceURI     The namespace URI of the document
     *                         element to create, or null.
     * @param qualifiedName    The qualified name of the document
     *                         element to create.
     * @param doctype          The type of document to be created or null.<p>
     *
     *                         When doctype is not null, its
     *                         Node.ownerDocument attribute is set to
     *                         the document being created.
     * @return Document        A new Document object.
     * @throws DOMException    WRONG_DOCUMENT_ERR: Raised if doctype has
     *                         already been used with a different document.
     * @since WD-DOM-Level-2-19990923
     */
    public Document           createDocument(String namespaceURI,
                                             String qualifiedName,
                                             DocumentType doctype)
                                             throws DOMException
    {
        if (doctype != null && doctype.getOwnerDocument() != null) {
            throw new DOMException(DOMException.WRONG_DOCUMENT_ERR,
                                   DOMMessageFormatter.formatMessage(
                                   DOMMessageFormatter.XML_DOMAIN,
                                                       "WRONG_DOCUMENT_ERR", null));
        }
        DocumentImpl doc = new PSVIDocumentImpl(doctype);
        Element e = doc.createElementNS( namespaceURI, qualifiedName);
        doc.appendChild(e);
        return doc;
    }


} // class DOMImplementationImpl
