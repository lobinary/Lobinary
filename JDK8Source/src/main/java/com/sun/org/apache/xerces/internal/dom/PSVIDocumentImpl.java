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
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  版权所有2002-2004 Apache软件基金会
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.dom;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.UserDataHandler;
import org.w3c.dom.*;

/**
 * Our own document implementation, which knows how to create an element
 * with PSVI information.
 *
 * @xerces.internal
 *
 * <p>
 *  我们自己的文档实现,它知道如何使用PSVI信息创建一个元素。
 * 
 *  @ xerces.internal
 * 
 * 
 * @author Sandy Gao, IBM
 *
 */
public class PSVIDocumentImpl extends DocumentImpl {

    /** Serialization version. */
    static final long serialVersionUID = -8822220250676434522L;

    /**
     * Create a document.
     * <p>
     *  创建文档。
     * 
     */
    public PSVIDocumentImpl() {
        super();
    }

    /**
     * For DOM2 support.
     * The createDocument factory method is in DOMImplementation.
     * <p>
     *  对于DOM2支持。 createDocument工厂方法在DOMImplementation中。
     * 
     */
    public PSVIDocumentImpl(DocumentType doctype) {
        super(doctype);
    }

    /**
     * Deep-clone a document, including fixing ownerDoc for the cloned
     * children. Note that this requires bypassing the WRONG_DOCUMENT_ERR
     * protection. I've chosen to implement it by calling importNode
     * which is DOM Level 2.
     *
     * <p>
     *  深度克隆文档,包括为克隆的孩子固定ownerDoc。请注意,这需要绕过WRONG_DOCUMENT_ERR保护。我选择实现它通过调用importNode是DOM级别2。
     * 
     * 
     * @return org.w3c.dom.Node
     * @param deep boolean, iff true replicate children
     */
    public Node cloneNode(boolean deep) {

        PSVIDocumentImpl newdoc = new PSVIDocumentImpl();
        callUserDataHandlers(this, newdoc, UserDataHandler.NODE_CLONED);
        cloneNode(newdoc, deep);

        // experimental
        newdoc.mutationEvents = mutationEvents;

        return newdoc;

    } // cloneNode(boolean):Node

    /**
     * Retrieve information describing the abilities of this particular
     * DOM implementation. Intended to support applications that may be
     * using DOMs retrieved from several different sources, potentially
     * with different underlying representations.
     * <p>
     *  检索描述此特定DOM实现的能力的信息。旨在支持可能使用从几个不同来源检索的DOM的应用程序,可能具有不同的底层表示。
     * 
     */
    public DOMImplementation getImplementation() {
        // Currently implemented as a singleton, since it's hardcoded
        // information anyway.
        return PSVIDOMImplementationImpl.getDOMImplementation();
    }

    /**
     * Create an element with PSVI information
     * <p>
     *  创建具有PSVI信息的元素
     * 
     */
    public Element createElementNS(String namespaceURI, String qualifiedName)
        throws DOMException {
        return new PSVIElementNSImpl(this, namespaceURI, qualifiedName);
    }

    /**
     * Create an element with PSVI information
     * <p>
     *  创建具有PSVI信息的元素
     * 
     */
    public Element createElementNS(String namespaceURI, String qualifiedName,
                                   String localpart) throws DOMException {
        return new PSVIElementNSImpl(this, namespaceURI, qualifiedName, localpart);
    }

    /**
     * Create an attribute with PSVI information
     * <p>
     * 使用PSVI信息创建属性
     * 
     */
    public Attr createAttributeNS(String namespaceURI, String qualifiedName)
        throws DOMException {
        return new PSVIAttrNSImpl(this, namespaceURI, qualifiedName);
    }

    /**
     * Create an attribute with PSVI information
     * <p>
     *  使用PSVI信息创建属性
     * 
     */
    public Attr createAttributeNS(String namespaceURI, String qualifiedName,
                                  String localName) throws DOMException {
        return new PSVIAttrNSImpl(this, namespaceURI, qualifiedName, localName);
    }

    /**
     *
     * The configuration used when <code>Document.normalizeDocument</code> is
     * invoked.
     * <p>
     *  调用<code> Document.normalizeDocument </code>时使用的配置。
     * 
     * @since DOM Level 3
     */
    public DOMConfiguration getDomConfig(){
        super.getDomConfig();
        return fConfiguration;
    }

    // REVISIT: Forbid serialization of PSVI DOM until
    // we support object serialization of grammars -- mrglavas

    private void writeObject(ObjectOutputStream out)
        throws IOException {
        throw new NotSerializableException(getClass().getName());
        }

    private void readObject(ObjectInputStream in)
        throws IOException, ClassNotFoundException {
        throw new NotSerializableException(getClass().getName());
    }

} // class PSVIDocumentImpl
