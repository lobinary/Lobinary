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

import com.sun.org.apache.xerces.internal.util.URI;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.Notation;

/**
 * Notations are how the Document Type Description (DTD) records hints
 * about the format of an XML "unparsed entity" -- in other words,
 * non-XML data bound to this document type, which some applications
 * may wish to consult when manipulating the document. A Notation
 * represents a name-value pair, with its nodeName being set to the
 * declared name of the notation.
 * <P>
 * Notations are also used to formally declare the "targets" of
 * Processing Instructions.
 * <P>
 * Note that the Notation's data is non-DOM information; the DOM only
 * records what and where it is.
 * <P>
 * See the XML 1.0 spec, sections 4.7 and 2.6, for more info.
 * <P>
 * Level 1 of the DOM does not support editing Notation contents.
 *
 * @xerces.internal
 *
 * <p>
 *  符号是文档类型描述(DTD)记录关于XML"解析实体"的格式的提示 - 换句话说,绑定到此文档类型的非XML数据,某些应用程序在操作文档时可能需要参考。
 * 符号表示名称 - 值对,其中nodeName设置为符号的声明名称。
 * <P>
 *  符号也用于正式声明Processing Instructions的"目标"。
 * <P>
 *  注意,Notation的数据是非DOM信息; DOM只记录什么和它在哪里。
 * <P>
 *  有关详细信息,请参阅XML 1.0规范,第4.7节和第2.6节。
 * <P>
 *  DOM的第1级不支持编辑注释内容。
 * 
 *  @ xerces.internal
 * 
 * 
 * @since  PR-DOM-Level-1-19980818.
 */
public class NotationImpl
    extends NodeImpl
    implements Notation {

    //
    // Constants
    //

    /** Serialization version. */
    static final long serialVersionUID = -764632195890658402L;

    //
    // Data
    //

    /** Notation name. */
    protected String name;

    /** Public identifier. */
    protected String publicId;

    /** System identifier. */
    protected String systemId;

    /** Base URI*/
    protected String baseURI;

    //
    // Constructors
    //

    /** Factory constructor. */
    public NotationImpl(CoreDocumentImpl ownerDoc, String name) {
        super(ownerDoc);
        this.name = name;
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
        return Node.NOTATION_NODE;
    }

    /**
     * Returns the notation name
     * <p>
     *  返回符号名称
     * 
     */
    public String getNodeName() {
        if (needsSyncData()) {
            synchronizeData();
        }
        return name;
    }

    //
    // Notation methods
    //

    /**
     * The Public Identifier for this Notation. If no public identifier
     * was specified, this will be null.
     * <p>
     *  此符号的公共标识符。如果没有指定公共标识符,那么将为null。
     * 
     */
    public String getPublicId() {

        if (needsSyncData()) {
            synchronizeData();
        }
        return publicId;

    } // getPublicId():String

    /**
     * The System Identifier for this Notation. If no system identifier
     * was specified, this will be null.
     * <p>
     *  此符号的系统标识符。如果未指定系统标识符,那么将为null。
     * 
     */
    public String getSystemId() {

        if (needsSyncData()) {
            synchronizeData();
        }
        return systemId;

    } // getSystemId():String

    //
    // Public methods
    //

    /**
     * NON-DOM: The Public Identifier for this Notation. If no public
     * identifier was specified, this will be null.
     * <p>
     *  NON-DOM：此符号的公共标识符。如果没有指定公共标识符,那么将为null。
     * 
     */
    public void setPublicId(String id) {

        if (isReadOnly()) {
                throw new DOMException(
                DOMException.NO_MODIFICATION_ALLOWED_ERR,
                DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null));
        }
        if (needsSyncData()) {
            synchronizeData();
        }
        publicId = id;

    } // setPublicId(String)

    /**
     * NON-DOM: The System Identifier for this Notation. If no system
     * identifier was specified, this will be null.
     * <p>
     *  NON-DOM：此表示法的系统标识符。如果未指定系统标识符,那么将为null。
     * 
     */
    public void setSystemId(String id) {

        if(isReadOnly()) {
                throw new DOMException(
                DOMException.NO_MODIFICATION_ALLOWED_ERR,
                DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null));
        }
        if (needsSyncData()) {
            synchronizeData();
        }
        systemId = id;

    } // setSystemId(String)


    /**
     * Returns the absolute base URI of this node or null if the implementation
     * wasn't able to obtain an absolute URI. Note: If the URI is malformed, a
     * null is returned.
     *
     * <p>
     *  返回此节点的绝对基本URI,如果实现无法获取绝对URI,则返回null。注意：如果URI格式不正确,则返回null。
     * 
     * @return The absolute base URI of this node or null.
     * @since DOM Level 3
     */
    public String getBaseURI() {
        if (needsSyncData()) {
            synchronizeData();
        }
        if (baseURI != null && baseURI.length() != 0 ) {// attribute value is always empty string
            try {
                return new URI(baseURI).toString();
            }
            catch (com.sun.org.apache.xerces.internal.util.URI.MalformedURIException e){
                // REVISIT: what should happen in this case?
                return null;
            }
        }
        return baseURI;
    }

    /** NON-DOM: set base uri*/
    public void setBaseURI(String uri){
        if (needsSyncData()) {
            synchronizeData();
        }
        baseURI = uri;
    }

} // class NotationImpl
