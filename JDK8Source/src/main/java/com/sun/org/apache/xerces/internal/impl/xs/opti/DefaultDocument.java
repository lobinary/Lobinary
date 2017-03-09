/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2001, 2002,2004 The Apache Software Foundation.
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
 *  版权所有2001,2002,2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.impl.xs.opti;

import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.Attr;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.w3c.dom.Element;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.DocumentType;
import org.w3c.dom.CDATASection;
import org.w3c.dom.EntityReference;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.ProcessingInstruction;

import org.w3c.dom.DOMException;


/**
 * @xerces.internal
 *
 * <p>
 *  @ xerces.internal
 * 
 * 
 * @author Rahul Srivastava, Sun Microsystems Inc.
 *
 */
public class DefaultDocument extends NodeImpl
                             implements Document {

    private String fDocumentURI = null;

    // default constructor
    public DefaultDocument() {
    }

    //
    // org.w3c.dom.Document methods
    //

    public DocumentType getDoctype() {
        return null;
    }


    public DOMImplementation getImplementation() {
        return null;
    }


    public Element getDocumentElement() {
        return null;
    }


    public NodeList getElementsByTagName(String tagname) {
        return null;
    }


    public NodeList getElementsByTagNameNS(String namespaceURI, String localName) {
        return null;
    }


    public Element getElementById(String elementId) {
        return null;
    }


    public Node importNode(Node importedNode, boolean deep) throws DOMException {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Method not supported");
    }


    public Element createElement(String tagName) throws DOMException {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Method not supported");
    }


    public DocumentFragment createDocumentFragment() {
        return null;
    }


    public Text createTextNode(String data) {
        return null;
    }

    public Comment createComment(String data) {
        return null;
    }


    public CDATASection createCDATASection(String data) throws DOMException {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Method not supported");
    }


    public ProcessingInstruction createProcessingInstruction(String target, String data) throws DOMException {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Method not supported");
    }


    public Attr createAttribute(String name) throws DOMException {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Method not supported");
    }


    public EntityReference createEntityReference(String name) throws DOMException {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Method not supported");
    }


    public Element createElementNS(String namespaceURI, String qualifiedName) throws DOMException {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Method not supported");
    }


    public Attr createAttributeNS(String namespaceURI, String qualifiedName) throws DOMException {
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Method not supported");
    }

    // DOM Level 3 methods.

    public String getInputEncoding(){
        return null;
    }

    /**
    public void setInputEncoding(String actualEncoding){
       throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Method not supported");
    }
    /* <p>
    /*  public void setInputEncoding(String actualEncoding){throw new DOMException(DOMException.NOT_SUPPORTED_ERR,"Method not supported"); }
    /* }。
    /* 
        */

    public String getXmlEncoding(){
        return null;
    }


    /**
     * An attribute specifying, as part of the XML declaration, the encoding
     * of this document. This is <code>null</code> when unspecified.
     * <p>
     *  作为XML声明的一部分的属性,指定此文档的编码。未指定时,这是<code> null </code>。
     * 
     * 
     * @since DOM Level 3
    public void setXmlEncoding(String encoding){
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Method not supported");
    }
     */

    /**
     * An attribute specifying, as part of the XML declaration, whether this
     * document is standalone.
     * <br> This attribute represents the property [standalone] defined in .
     * <p>
     *  作为XML声明的一部分的属性,指定此文档是独立的。 <br>此属性表示在中定义的属性[standalone]。
     * 
     * 
     * @since DOM Level 3
     */
    public boolean getXmlStandalone(){
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Method not supported");
    }
    /**
     * An attribute specifying, as part of the XML declaration, whether this
     * document is standalone.
     * <br> This attribute represents the property [standalone] defined in .
     * <p>
     *  作为XML声明的一部分的属性,指定此文档是独立的。 <br>此属性表示在中定义的属性[standalone]。
     * 
     * 
     * @since DOM Level 3
     */
    public void setXmlStandalone(boolean standalone){
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Method not supported");
    }

    /**
     * An attribute specifying, as part of the XML declaration, the version
     * number of this document. This is <code>null</code> when unspecified.
     * <br> This attribute represents the property [version] defined in .
     * <p>
     * 作为XML声明的一部分的属性,指定此文档的版本号。未指定时,这是<code> null </code>。 <br>此属性表示在中定义的属性[version]。
     * 
     * 
     * @exception DOMException
     *   NOT_SUPPORTED_ERR: Raised if the version is set to a value that is
     *   not supported by this <code>Document</code>.
     * @since DOM Level 3
     */
    public String getXmlVersion(){
        return null;
    }
    /**
     * An attribute specifying, as part of the XML declaration, the version
     * number of this document. This is <code>null</code> when unspecified.
     * <br> This attribute represents the property [version] defined in .
     * <p>
     *  作为XML声明的一部分的属性,指定此文档的版本号。未指定时,这是<code> null </code>。 <br>此属性表示在中定义的属性[version]。
     * 
     * 
     * @exception DOMException
     *   NOT_SUPPORTED_ERR: Raised if the version is set to a value that is
     *   not supported by this <code>Document</code>.
     * @since DOM Level 3
     */
    public void setXmlVersion(String version) throws DOMException{
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Method not supported");
    }

    /**
     * An attribute specifying whether errors checking is enforced or not.
     * When set to <code>false</code>, the implementation is free to not
     * test every possible error case normally defined on DOM operations,
     * and not raise any <code>DOMException</code>. In case of error, the
     * behavior is undefined. This attribute is <code>true</code> by
     * defaults.
     * <p>
     *  指定是否强制执行错误检查的属性。
     * 当设置为<code> false </code>时,实现可以不测试通常在DOM操作上定义的每个可能的错误情况,而不会引发任何<code> DOMException </code>。
     * 如果发生错误,行为是未定义的。默认情况下,此属性为<code> true </code>。
     * 
     * 
     * @since DOM Level 3
     */
    public boolean getStrictErrorChecking(){
        return false;
    }
    /**
     * An attribute specifying whether errors checking is enforced or not.
     * When set to <code>false</code>, the implementation is free to not
     * test every possible error case normally defined on DOM operations,
     * and not raise any <code>DOMException</code>. In case of error, the
     * behavior is undefined. This attribute is <code>true</code> by
     * defaults.
     * <p>
     *  指定是否强制执行错误检查的属性。
     * 当设置为<code> false </code>时,实现可以不测试通常在DOM操作上定义的每个可能的错误情况,而不会引发任何<code> DOMException </code>。
     * 如果发生错误,行为是未定义的。默认情况下,此属性为<code> true </code>。
     * 
     * 
     * @since DOM Level 3
     */
    public void setStrictErrorChecking(boolean strictErrorChecking){
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Method not supported");
    }

    /**
     * The location of the document or <code>null</code> if undefined.
     * <br>Beware that when the <code>Document</code> supports the feature
     * "HTML" , the href attribute of the HTML BASE element takes precedence
     * over this attribute.
     * <p>
     *  文档的位置或<code> null </code>(如果未定义)。 <br>请注意,当<code>文档</code>支持特性"HTML"时,HTML BASE元素的href属性优先于此属性。
     * 
     * 
     * @since DOM Level 3
     */
    public String getDocumentURI() {
        return fDocumentURI;
    }

    /**
     * The location of the document or <code>null</code> if undefined.
     * <br>Beware that when the <code>Document</code> supports the feature
     * "HTML" , the href attribute of the HTML BASE element takes precedence
     * over this attribute.
     * <p>
     * 文档的位置或<code> null </code>(如果未定义)。 <br>请注意,当<code>文档</code>支持特性"HTML"时,HTML BASE元素的href属性优先于此属性。
     * 
     * 
     * @since DOM Level 3
     */
    public void setDocumentURI(String documentURI) {
        fDocumentURI = documentURI;
    }

    /** DOM Level 3*/
    public Node adoptNode(Node source) throws DOMException{
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Method not supported");
    }

    /** DOM Level 3*/
    public void normalizeDocument(){
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Method not supported");
    }

    /**
     *  The configuration used when <code>Document.normalizeDocument</code> is
     * invoked.
     * <p>
     *  调用<code> Document.normalizeDocument </code>时使用的配置。
     * 
     * @since DOM Level 3
     */
    public DOMConfiguration getDomConfig(){
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Method not supported");
    }

    /** DOM Level 3*/
    public Node renameNode(Node n,String namespaceURI, String name) throws DOMException{
        throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Method not supported");
    }








}
