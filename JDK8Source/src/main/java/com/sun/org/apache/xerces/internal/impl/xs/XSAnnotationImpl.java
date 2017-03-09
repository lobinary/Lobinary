/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2001-2004 The Apache Software Foundation.
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
 *  版权所有2001-2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
package com.sun.org.apache.xerces.internal.impl.xs;

import java.io.IOException;
import java.io.StringReader;

import com.sun.org.apache.xerces.internal.dom.CoreDocumentImpl;
import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import com.sun.org.apache.xerces.internal.parsers.SAXParser;
import com.sun.org.apache.xerces.internal.xs.XSAnnotation;
import com.sun.org.apache.xerces.internal.xs.XSConstants;
import com.sun.org.apache.xerces.internal.xs.XSNamespaceItem;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
/**
 * This is an implementation of the XSAnnotation schema component.
 *
 * @xerces.internal
 * <p>
 *  这是XSAnnotation模式组件的实现。
 * 
 *  @ xerces.internal
 * 
 */
public class XSAnnotationImpl implements XSAnnotation {

    // Data

    // the content of the annotation node, including all children, along
    // with any non-schema attributes from its parent
    private String fData = null;

    // the grammar which owns this annotation; we get parsers
    // from here when we need them
    private SchemaGrammar fGrammar = null;

    // constructors
    public XSAnnotationImpl(String contents, SchemaGrammar grammar) {
        fData = contents;
        fGrammar = grammar;
    }

    /**
     *  Write contents of the annotation to the specified DOM object. If the
     * specified <code>target</code> object is a DOM in-scope namespace
     * declarations for <code>annotation</code> element are added as
     * attributes nodes of the serialized <code>annotation</code>, otherwise
     * the corresponding events for all in-scope namespace declaration are
     * sent via specified document handler.
     * <p>
     *  将注释的内容写入指定的DOM对象。
     * 如果指定的<code> target </code>对象是<code>注释</code>元素的DOM范围内命名空间声明,则作为序列化<code>注释</code>的属性节点添加,否则,所有范围内命名空间
     * 声明的事件都通过指定的文档处理程序发送。
     *  将注释的内容写入指定的DOM对象。
     * 
     * 
     * @param target  A target pointer to the annotation target object, i.e.
     *   <code>org.w3c.dom.Document</code>,
     *   <code>org.xml.sax.ContentHandler</code>.
     * @param targetType  A target type.
     * @return If the <code>target</code> is recognized type and supported by
     *   this implementation return true, otherwise return false.
     */
    public boolean writeAnnotation(Object target,
                                   short targetType) {
        if(targetType == XSAnnotation.W3C_DOM_ELEMENT || targetType == XSAnnotation.W3C_DOM_DOCUMENT) {
            writeToDOM((Node)target, targetType);
            return true;
        } else if (targetType == SAX_CONTENTHANDLER) {
            writeToSAX((ContentHandler)target);
            return true;
        }
        return false;
    }

    /**
     * A text representation of annotation.
     * <p>
     *  注释的文本表示。
     * 
     */
    public String getAnnotationString() {
        return fData;
    }

    // XSObject methods

    /**
     *  The <code>type</code> of this object, i.e.
     * <code>ELEMENT_DECLARATION</code>.
     * <p>
     *  此对象的<code> type </code>,即<code> ELEMENT_DECLARATION </code>。
     * 
     */
    public short getType() {
        return XSConstants.ANNOTATION;
    }

    /**
     * The name of type <code>NCName</code> of this declaration as defined in
     * XML Namespaces.
     * <p>
     *  在XML命名空间中定义的此声明的类型<code> NCName </code>的名称。
     * 
     */
    public String getName() {
        return null;
    }

    /**
     *  The [target namespace] of this object, or <code>null</code> if it is
     * unspecified.
     * <p>
     *  此对象的[目标命名空间],如果未指定,则为<code> null </code>。
     * 
     */
    public String getNamespace() {
        return null;
    }

    /**
     * A namespace schema information item corresponding to the target
     * namespace of the component, if it's globally declared; or null
     * otherwise.
     * <p>
     * 一个命名空间模式信息项,对应于组件的目标命名空间,如果它是全局声明的;否则为空。
     */
    public XSNamespaceItem getNamespaceItem() {
        return null;
    }

    // private methods
    private synchronized void writeToSAX(ContentHandler handler) {
        // nothing must go wrong with this parse...
        SAXParser parser = fGrammar.getSAXParser();
        StringReader aReader = new StringReader(fData);
        InputSource aSource = new InputSource(aReader);
        parser.setContentHandler(handler);
        try {
            parser.parse(aSource);
        }
        catch (SAXException e) {
            // this should never happen!
            // REVISIT:  what to do with this?; should really not
            // eat it...
        }
        catch (IOException i) {
            // ditto with above
        }
        // Release the reference to the user's ContentHandler.
        parser.setContentHandler(null);
    }

    // this creates the new Annotation element as the first child
    // of the Node
    private synchronized void writeToDOM(Node target, short type) {
        Document futureOwner = (type == XSAnnotation.W3C_DOM_ELEMENT) ?
                target.getOwnerDocument() : (Document)target;
        DOMParser parser = fGrammar.getDOMParser();
        StringReader aReader = new StringReader(fData);
        InputSource aSource = new InputSource(aReader);
        try {
            parser.parse(aSource);
        }
        catch (SAXException e) {
            // this should never happen!
            // REVISIT:  what to do with this?; should really not
            // eat it...
        }
        catch (IOException i) {
            // ditto with above
        }
        Document aDocument = parser.getDocument();
        parser.dropDocumentReferences();
        Element annotation = aDocument.getDocumentElement();
        Node newElem = null;
        if (futureOwner instanceof CoreDocumentImpl) {
            newElem = futureOwner.adoptNode(annotation);
            // adoptNode will return null when the DOM implementations are not compatible.
            if (newElem == null) {
                newElem = futureOwner.importNode(annotation, true);
            }
        }
        else {
            newElem = futureOwner.importNode(annotation, true);
        }
        target.insertBefore(newElem, target.getFirstChild());
    }

}
