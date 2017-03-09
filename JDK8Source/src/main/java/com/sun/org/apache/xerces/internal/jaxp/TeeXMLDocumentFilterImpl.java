/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2005 The Apache Software Foundation.
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
 *  版权所有2005 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.jaxp;

import com.sun.org.apache.xerces.internal.xni.Augmentations;
import com.sun.org.apache.xerces.internal.xni.NamespaceContext;
import com.sun.org.apache.xerces.internal.xni.QName;
import com.sun.org.apache.xerces.internal.xni.XMLAttributes;
import com.sun.org.apache.xerces.internal.xni.XMLDocumentHandler;
import com.sun.org.apache.xerces.internal.xni.XMLLocator;
import com.sun.org.apache.xerces.internal.xni.XMLResourceIdentifier;
import com.sun.org.apache.xerces.internal.xni.XMLString;
import com.sun.org.apache.xerces.internal.xni.XNIException;
import com.sun.org.apache.xerces.internal.xni.parser.XMLDocumentFilter;
import com.sun.org.apache.xerces.internal.xni.parser.XMLDocumentSource;

/**
 * <p>XMLDocumentHandler which forks the pipeline to two other components.</p>
 *
 * <p>
 *  <p> XMLDocumentHandler将管道分为两个其他组件。</p>
 * 
 * 
 * @author Kohsuke Kawaguchi (kohsuke.kawaguchi@sun.com)
 */
class TeeXMLDocumentFilterImpl implements XMLDocumentFilter {

    /**
     * The next component in the pipeline who receives the event.
     * This component receives events after the "side" handler
     * receives them.
     * <p>
     *  接收事件的管道中的下一个组件。这个组件在"side"处理程序接收到它们之后接收事件。
     * 
     */
    private XMLDocumentHandler next;

    /**
     * The component who intercepts events.
     * <p>
     *  拦截事件的组件。
     * 
     */
    private XMLDocumentHandler side;

    /**
     * The source of the event.
     * <p>
     *  事件的来源。
     */
    private XMLDocumentSource source;

    public XMLDocumentHandler getSide() {
        return side;
    }

    public void setSide(XMLDocumentHandler side) {
        this.side = side;
    }

    public XMLDocumentSource getDocumentSource() {
        return source;
    }

    public void setDocumentSource(XMLDocumentSource source) {
        this.source = source;
    }

    public XMLDocumentHandler getDocumentHandler() {
        return next;
    }

    public void setDocumentHandler(XMLDocumentHandler handler) {
        next = handler;
    }

    //
    //
    //  XMLDocumentHandler implementation
    //
    //

    public void characters(XMLString text, Augmentations augs) throws XNIException {
        side.characters(text, augs);
        next.characters(text, augs);
    }

    public void comment(XMLString text, Augmentations augs) throws XNIException {
        side.comment(text, augs);
        next.comment(text, augs);
    }

    public void doctypeDecl(String rootElement, String publicId, String systemId, Augmentations augs)
        throws XNIException {
        side.doctypeDecl(rootElement, publicId, systemId, augs);
        next.doctypeDecl(rootElement, publicId, systemId, augs);
    }

    public void emptyElement(QName element, XMLAttributes attributes, Augmentations augs) throws XNIException {
        side.emptyElement(element, attributes, augs);
        next.emptyElement(element, attributes, augs);
    }

    public void endCDATA(Augmentations augs) throws XNIException {
        side.endCDATA(augs);
        next.endCDATA(augs);
    }

    public void endDocument(Augmentations augs) throws XNIException {
        side.endDocument(augs);
        next.endDocument(augs);
    }

    public void endElement(QName element, Augmentations augs) throws XNIException {
        side.endElement(element, augs);
        next.endElement(element, augs);
    }

    public void endGeneralEntity(String name, Augmentations augs) throws XNIException {
        side.endGeneralEntity(name, augs);
        next.endGeneralEntity(name, augs);
    }

    public void ignorableWhitespace(XMLString text, Augmentations augs) throws XNIException {
        side.ignorableWhitespace(text, augs);
        next.ignorableWhitespace(text, augs);
    }

    public void processingInstruction(String target, XMLString data, Augmentations augs) throws XNIException {
        side.processingInstruction(target, data, augs);
        next.processingInstruction(target, data, augs);
    }

    public void startCDATA(Augmentations augs) throws XNIException {
        side.startCDATA(augs);
        next.startCDATA(augs);
    }

    public void startDocument(
            XMLLocator locator,
            String encoding,
            NamespaceContext namespaceContext,
            Augmentations augs)
        throws XNIException {
        side.startDocument(locator, encoding, namespaceContext, augs);
        next.startDocument(locator, encoding, namespaceContext, augs);
    }

    public void startElement(QName element, XMLAttributes attributes, Augmentations augs) throws XNIException {
        side.startElement(element, attributes, augs);
        next.startElement(element, attributes, augs);
    }

    public void startGeneralEntity(String name, XMLResourceIdentifier identifier, String encoding, Augmentations augs)
        throws XNIException {
        side.startGeneralEntity(name, identifier, encoding, augs);
        next.startGeneralEntity(name, identifier, encoding, augs);
    }

    public void textDecl(String version, String encoding, Augmentations augs) throws XNIException {
        side.textDecl(version, encoding, augs);
        next.textDecl(version, encoding, augs);
    }

    public void xmlDecl(String version, String encoding, String standalone, Augmentations augs) throws XNIException {
        side.xmlDecl(version, encoding, standalone, augs);
        next.xmlDecl(version, encoding, standalone, augs);
    }

}
