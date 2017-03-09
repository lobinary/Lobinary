/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * <p>
 *  根据一个或多个贡献者许可协议授予Apache软件基金会(ASF)。有关版权所有权的其他信息,请参阅随此作品分发的NOTICE文件。
 *  ASF根据Apache许可证2.0版("许可证")向您授予此文件;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本。
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
package com.sun.org.apache.xml.internal.security.signature;

import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
import com.sun.org.apache.xml.internal.security.utils.Constants;
import com.sun.org.apache.xml.internal.security.utils.SignatureElementProxy;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


/**
 * Handles <code>&lt;ds:Object&gt;</code> elements
 * <code>Object<code> {@link Element} supply facility which can contain any kind data
 *
 * <p>
 *  Handles <code>&lt; ds：Object&gt; </code>元素<code> Object <code> {@link Element}供应设施,可以包含任何类型的数据
 * 
 * 
 * @author Christian Geuer-Pollmann
 * $todo$ if we remove childen, the boolean values are not updated
 */
public class ObjectContainer extends SignatureElementProxy {

    /**
     * Constructs {@link ObjectContainer}
     *
     * <p>
     *  构造{@link ObjectContainer}
     * 
     * 
     * @param doc the {@link Document} in which <code>Object</code> element is placed
     */
    public ObjectContainer(Document doc) {
        super(doc);
    }

    /**
     * Constructs {@link ObjectContainer} from {@link Element}
     *
     * <p>
     *  构造{@link ObjectContainer} from {@link Element}
     * 
     * 
     * @param element is <code>Object</code> element
     * @param baseURI the URI of the resource where the XML instance was stored
     * @throws XMLSecurityException
     */
    public ObjectContainer(Element element, String baseURI) throws XMLSecurityException {
        super(element, baseURI);
    }

    /**
     * Sets the <code>Id</code> attribute
     *
     * <p>
     *  设置<code> Id </code>属性
     * 
     * 
     * @param Id <code>Id</code> attribute
     */
    public void setId(String Id) {
        if (Id != null) {
            this.constructionElement.setAttributeNS(null, Constants._ATT_ID, Id);
            this.constructionElement.setIdAttributeNS(null, Constants._ATT_ID, true);
        }
    }

    /**
     * Returns the <code>Id</code> attribute
     *
     * <p>
     *  返回<code> Id </code>属性
     * 
     * 
     * @return the <code>Id</code> attribute
     */
    public String getId() {
        return this.constructionElement.getAttributeNS(null, Constants._ATT_ID);
    }

    /**
     * Sets the <code>MimeType</code> attribute
     *
     * <p>
     *  设置<code> MimeType </code>属性
     * 
     * 
     * @param MimeType the <code>MimeType</code> attribute
     */
    public void setMimeType(String MimeType) {
        if (MimeType != null) {
            this.constructionElement.setAttributeNS(null, Constants._ATT_MIMETYPE, MimeType);
        }
    }

    /**
     * Returns the <code>MimeType</code> attribute
     *
     * <p>
     *  返回<code> MimeType </code>属性
     * 
     * 
     * @return the <code>MimeType</code> attribute
     */
    public String getMimeType() {
        return this.constructionElement.getAttributeNS(null, Constants._ATT_MIMETYPE);
    }

    /**
     * Sets the <code>Encoding</code> attribute
     *
     * <p>
     *  设置<code> Encoding </code>属性
     * 
     * 
     * @param Encoding the <code>Encoding</code> attribute
     */
    public void setEncoding(String Encoding) {
        if (Encoding != null) {
            this.constructionElement.setAttributeNS(null, Constants._ATT_ENCODING, Encoding);
        }
    }

    /**
     * Returns the <code>Encoding</code> attribute
     *
     * <p>
     *  返回<code> Encoding </code>属性
     * 
     * 
     * @return the <code>Encoding</code> attribute
     */
    public String getEncoding() {
        return this.constructionElement.getAttributeNS(null, Constants._ATT_ENCODING);
    }

    /**
     * Adds child Node
     *
     * <p>
     *  添加子节点
     * 
     * @param node child Node
     * @return the new node in the tree.
     */
    public Node appendChild(Node node) {
        return this.constructionElement.appendChild(node);
    }

    /** @inheritDoc */
    public String getBaseLocalName() {
        return Constants._TAG_OBJECT;
    }
}
