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

import org.w3c.dom.Entity;
import org.w3c.dom.Node;
import org.w3c.dom.DOMException;

/**
 * Entity nodes hold the reference data for an XML Entity -- either
 * parsed or unparsed. The nodeName (inherited from Node) will contain
 * the name (if any) of the Entity. Its data will be contained in the
 * Entity's children, in exactly the structure which an
 * EntityReference to this name will present within the document's
 * body.
 * <P>
 * Note that this object models the actual entity, _not_ the entity
 * declaration or the entity reference.
 * <P>
 * An XML processor may choose to completely expand entities before
 * the structure model is passed to the DOM; in this case, there will
 * be no EntityReferences in the DOM tree.
 * <P>
 * Quoting the 10/01 DOM Proposal,
 * <BLOCKQUOTE>
 * "The DOM Level 1 does not support editing Entity nodes; if a user
 * wants to make changes to the contents of an Entity, every related
 * EntityReference node has to be replaced in the structure model by
 * a clone of the Entity's contents, and then the desired changes
 * must be made to each of those clones instead. All the
 * descendants of an Entity node are readonly."
 * </BLOCKQUOTE>
 * I'm interpreting this as: It is the parser's responsibilty to call
 * the non-DOM operation setReadOnly(true,true) after it constructs
 * the Entity. Since the DOM explicitly decided not to deal with this,
 * _any_ answer will involve a non-DOM operation, and this is the
 * simplest solution.
 *
 * @xerces.internal
 *
 * <p>
 *  实体节点保存XML实体的参考数据 - 已解析或未解析。 nodeName(继承自Node)将包含实体的名称(如果有)。
 * 它的数据将包含在Entity的孩子中,在这个结构中,这个名字的EntityReference将出现在文档的正文中。
 * <P>
 *  请注意,此对象建模实际实体,_not_实体声明或实体引用。
 * <P>
 *  XML处理器可以选择在将结构模型传递给DOM之前完全展开实体;在这种情况下,在DOM树中将没有EntityReferences。
 * <P>
 *  引用10/01 DOM提案,
 * <BLOCKQUOTE>
 * "DOM级别1不支持编辑实体节点;如果用户想要更改实体的内容,则必须在结构模型中将每个相关的EntityReference节点替换为实体内容的克隆,然后将所需的必须对每个克隆进行更改。
 * 实体节点的所有后代都是只读的。
 * </BLOCKQUOTE>
 *  我将其解释为：解析器的责任是在它构造实体之后调用非DOM操作setReadOnly(true,true)。
 * 由于DOM明确决定不处理这一点,_any_ answer将涉及非DOM操作,这是最简单的解决方案。
 * 
 *  @ xerces.internal
 * 
 * 
 * @author Elena Litani, IBM
 * @since PR-DOM-Level-1-19980818.
 */
public class EntityImpl
    extends ParentNode
    implements Entity {

    //
    // Constants
    //

    /** Serialization version. */
    static final long serialVersionUID = -3575760943444303423L;

    //
    // Data
    //

    /** Entity name. */
    protected String name;

    /** Public identifier. */
    protected String publicId;

    /** System identifier. */
    protected String systemId;

    /** Encoding */
    protected String encoding;


    /** Input Encoding */
    protected String inputEncoding;

    /** Version */
    protected String version;


    /** Notation name. */
    protected String notationName;

    /** base uri*/
    protected String baseURI;

    //
    // Constructors
    //

    /** Factory constructor. */
    public EntityImpl(CoreDocumentImpl ownerDoc, String name) {
        super(ownerDoc);
        this.name = name;
        isReadOnly(true);
    }

    //
    // Node methods
    //

    /**
     * A short integer indicating what type of node this is. The named
     * constants for this value are defined in the org.w3c.dom.Node interface.
     * <p>
     *  指示这是什么类型的节点的短整数。此值的命名常量在org.w3c.dom.Node接口中定义。
     * 
     */
    public short getNodeType() {
        return Node.ENTITY_NODE;
    }

    /**
     * Returns the entity name
     * <p>
     *  返回实体名称
     * 
     */
    public String getNodeName() {
        if (needsSyncData()) {
            synchronizeData();
        }
        return name;
    }
    /**
     * Sets the node value.
     * <p>
     *  设置节点值。
     * 
     * 
     * @throws DOMException(NO_MODIFICATION_ALLOWED_ERR)
     */
    public void setNodeValue(String x)
        throws DOMException {
        if (ownerDocument.errorChecking && isReadOnly()) {
            String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null);
            throw new DOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR, msg);
        }
    }
    /**
     * The namespace prefix of this node
     * <p>
     *  此节点的命名空间前缀
     * 
     * 
     * @exception DOMException
     *   <br>NO_MODIFICATION_ALLOWED_ERR: Raised if this node is readonly.
     */
    public void setPrefix(String prefix)
        throws DOMException
    {
        if (ownerDocument.errorChecking && isReadOnly()) {
            throw new DOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR,
                  DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN,
                    "NO_MODIFICATION_ALLOWED_ERR", null));
        }
    }
    /** Clone node. */
    public Node cloneNode(boolean deep) {
        EntityImpl newentity = (EntityImpl)super.cloneNode(deep);
        newentity.setReadOnly(true, deep);
        return newentity;
    }

    //
    // Entity methods
    //

    /**
     * The public identifier associated with the entity. If not specified,
     * this will be null.
     * <p>
     *  与实体相关联的公共标识符。如果未指定,则此值将为null。
     * 
     */
    public String getPublicId() {

        if (needsSyncData()) {
            synchronizeData();
        }
        return publicId;

    } // getPublicId():String

    /**
     * The system identifier associated with the entity. If not specified,
     * this will be null.
     * <p>
     *  与实体相关联的系统标识符。如果未指定,则此值将为null。
     * 
     */
    public String getSystemId() {

        if (needsSyncData()) {
            synchronizeData();
        }
        return systemId;

    } // getSystemId():String

    /**
      * DOM Level 3 WD - experimental
      * the version number of this entity, when it is an external parsed entity.
      * <p>
      *  DOM Level 3 WD  - 实验性的实体,当它是一个外部解析实体的版本号。
      * 
      */
    public String getXmlVersion() {

       if (needsSyncData()) {
           synchronizeData();
       }
       return version;

   } // getVersion():String


    /**
     * DOM Level 3 WD - experimental
     * the encoding of this entity, when it is an external parsed entity.
     * <p>
     *  DOM Level 3 WD  - 实验编码这个实体,当它是一个外部解析实体。
     * 
     */
    public String getXmlEncoding() {

       if (needsSyncData()) {
           synchronizeData();
       }

       return encoding;

   } // getVersion():String





    /**
     * Unparsed entities -- which contain non-XML data -- have a
     * "notation name" which tells applications how to deal with them.
     * Parsed entities, which <em>are</em> in XML format, don't need this and
     * set it to null.
     * <p>
     *  未解析的实体 - 其中包含非XML数据 - 有一个"符号名称",告诉应用程序如何处理它们。以XML格式</em>的解析实体不需要此属性,并将其设置为null。
     * 
     */
    public String getNotationName() {

        if (needsSyncData()) {
            synchronizeData();
        }
        return notationName;

    } // getNotationName():String

    //
    // Public methods
    //

    /**
     * DOM Level 2: The public identifier associated with the entity. If not specified,
     * <p>
     * DOM级别2：与实体相关联的公共标识符。如果没有指定,
     * 
     * 
     * this will be null. */
    public void setPublicId(String id) {

        if (needsSyncData()) {
            synchronizeData();
        }
        publicId = id;

    } // setPublicId(String)

    /**
     * NON-DOM
     * encoding - An attribute specifying, as part of the text declaration,
     * the encoding of this entity, when it is an external parsed entity.
     * This is null otherwise
     *
     * <p>
     *  NON-DOM encoding  - 一个属性,作为文本声明的一部分,指定此实体的编码,当它是外部解析实体时。否则为空
     * 
     */
    public void setXmlEncoding(String value) {
        if (needsSyncData()) {
            synchronizeData();
        }
        encoding = value;
    } // setEncoding (String)


    /**
     * An attribute specifying the encoding used for this entity at the tiome
     * of parsing, when it is an external parsed entity. This is
     * <code>null</code> if it an entity from the internal subset or if it
     * is not known..
     * <p>
     *  一个属性,指定在解析的时候用于此实体的编码,当它是一个外部解析实体时。这是<code> null </code>如果它是来自内部子集的实体,或者如果它不是已知的..
     * 
     * 
     * @since DOM Level 3
     */
    public String getInputEncoding(){
        if (needsSyncData()) {
            synchronizeData();
        }
        return inputEncoding;
    }

    /**
     * NON-DOM, used to set the input encoding.
     * <p>
     *  NON-DOM,用于设置输入编码。
     * 
     */
    public void setInputEncoding(String inputEncoding){
        if (needsSyncData()) {
            synchronizeData();
        }
        this.inputEncoding = inputEncoding;
    }

    /**
      * NON-DOM
      * version - An attribute specifying, as part of the text declaration,
      * the version number of this entity, when it is an external parsed entity.
      * This is null otherwise
      * <p>
      *  NON-DOM version  - 作为文本声明的一部分的属性,当它是外部解析实体时,指定此实体的版本号。否则为空
      * 
      */
    public void setXmlVersion(String value) {
        if (needsSyncData()) {
            synchronizeData();
        }
        version = value;
    } // setVersion (String)


    /**
     * DOM Level 2: The system identifier associated with the entity. If not
     * specified, this will be null.
     * <p>
     *  DOM级别2：与实体相关联的系统标识符。如果未指定,则此值将为null。
     * 
     */
    public void setSystemId(String id) {
        if (needsSyncData()) {
            synchronizeData();
        }
        systemId = id;

    } // setSystemId(String)

    /**
     * DOM Level 2: Unparsed entities -- which contain non-XML data -- have a
     * "notation name" which tells applications how to deal with them.
     * Parsed entities, which <em>are</em> in XML format, don't need this and
     * set it to null.
     * <p>
     *  DOM级别2：未解析的实体 - 其中包含非XML数据 - 具有一个"符号名称",告诉应用程序如何处理它们。以XML格式</em>的解析实体不需要此属性,并将其设置为null。
     * 
     */
    public void setNotationName(String name) {
        if (needsSyncData()) {
            synchronizeData();
        }
        notationName = name;

    } // setNotationName(String)



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
        return (baseURI!=null)?baseURI:((CoreDocumentImpl)getOwnerDocument()).getBaseURI();
    }

    /** NON-DOM: set base uri*/
    public void setBaseURI(String uri){
        if (needsSyncData()) {
            synchronizeData();
        }
        baseURI = uri;
    }



} // class EntityImpl
