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
 * Handles <code>&lt;ds:SignatureProperty&gt;</code> elements
 * Additional information item concerning the generation of the signature(s) can
 * be placed in this Element
 *
 * <p>
 *  句柄<code>&lt; ds：SignatureProperty&gt; </code> elements有关生成签名的附加信息项可以放在此元素中
 * 
 * 
 * @author Christian Geuer-Pollmann
 */
public class SignatureProperty extends SignatureElementProxy {

    /**
     * Constructs{@link SignatureProperty} using specified <code>target</code> attribute
     *
     * <p>
     *  使用指定的<code> target </code>属性构造{@link SignatureProperty}
     * 
     * 
     * @param doc the {@link Document} in which <code>XMLsignature</code> is placed
     * @param target the <code>target</code> attribute references the <code>Signature</code>
     * element to which the property applies SignatureProperty
     */
    public SignatureProperty(Document doc, String target) {
        this(doc, target, null);
    }

    /**
     * Constructs {@link SignatureProperty} using sepcified <code>target</code> attribute and
     * <code>id</code> attribute
     *
     * <p>
     *  使用sepcified <code> target </code>属性和<code> id </code>属性构造{@link SignatureProperty}
     * 
     * 
     * @param doc the {@link Document} in which <code>XMLsignature</code> is placed
     * @param target the <code>target</code> attribute references the <code>Signature</code>
     *  element to which the property applies
     * @param id the <code>id</code> will be specified by {@link Reference#getURI} in validation
     */
    public SignatureProperty(Document doc, String target, String id) {
        super(doc);

        this.setTarget(target);
        this.setId(id);
    }

    /**
     * Constructs a {@link SignatureProperty} from an {@link Element}
     * <p>
     *  使用{@link Element}构造{@link SignatureProperty}
     * 
     * 
     * @param element <code>SignatureProperty</code> element
     * @param BaseURI the URI of the resource where the XML instance was stored
     * @throws XMLSecurityException
     */
    public SignatureProperty(Element element, String BaseURI) throws XMLSecurityException {
        super(element, BaseURI);
    }

    /**
     *   Sets the <code>id</code> attribute
     *
     * <p>
     *  设置<code> id </code>属性
     * 
     * 
     *   @param id the <code>id</code> attribute
     */
    public void setId(String id) {
        if (id != null) {
            this.constructionElement.setAttributeNS(null, Constants._ATT_ID, id);
            this.constructionElement.setIdAttributeNS(null, Constants._ATT_ID, true);
        }
    }

    /**
     * Returns the <code>id</code> attribute
     *
     * <p>
     *  返回<code> id </code>属性
     * 
     * 
     * @return the <code>id</code> attribute
     */
    public String getId() {
        return this.constructionElement.getAttributeNS(null, Constants._ATT_ID);
    }

    /**
     * Sets the <code>target</code> attribute
     *
     * <p>
     *  设置<code> target </code>属性
     * 
     * 
     * @param target the <code>target</code> attribute
     */
    public void setTarget(String target) {
        if (target != null) {
            this.constructionElement.setAttributeNS(null, Constants._ATT_TARGET, target);
        }
    }

    /**
     * Returns the <code>target</code> attribute
     *
     * <p>
     *  返回<code> target </code>属性
     * 
     * 
     * @return the <code>target</code> attribute
     */
    public String getTarget() {
        return this.constructionElement.getAttributeNS(null, Constants._ATT_TARGET);
    }

    /**
     * Method appendChild
     *
     * <p>
     *  方法appendChild
     * 
     * @param node
     * @return the node in this element.
     */
    public Node appendChild(Node node) {
        return this.constructionElement.appendChild(node);
    }

    /** @inheritDoc */
    public String getBaseLocalName() {
        return Constants._TAG_SIGNATUREPROPERTY;
    }
}
